package ec.gob.mdg.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.mindrot.jbcrypt.BCrypt;

import ec.gob.mdg.model.Institucion;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.service.IInstitucionService;
import ec.gob.mdg.service.IUsuarioService;

@Named
@ViewScoped
public class UsuarioBean implements Serializable {

	private static final long serialVersionUID = -6912327508466146927L;

	@Inject
	private IUsuarioService serviceUsuario;

	@Inject
	private IInstitucionService serviceInstitucion;

	private Institucion institucion;
	private List<Usuario> listaUsuario = new ArrayList<>();
	private Usuario usuario = new Usuario();
	boolean estadeshabilitado;
	boolean estadeshabilitado_ap = true;
	Integer idusuario = 0;
	boolean valida=false;
	String claveNueva = null;

	public Integer getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(Integer idusuario) {
		this.idusuario = idusuario;
	}

	@PostConstruct
	public void init() {
		this.usuario = new Usuario();
		this.listarUsuarioPunto();
		claveNueva = generarSecuenciaAleatoria();
		desactivarAP();
	}

	// registrar usuario
	public void registrar() {

		try {
			Usuario usu = new Usuario();

			String clave = claveNueva;// this.usuario.getContrasena();
			String claveHash = BCrypt.hashpw(clave, BCrypt.gensalt());
			usu.setContrasena(claveHash);
			usu.setApellido(usuario.getApellido().toUpperCase());
			usu.setNombre(usuario.getNombre().toUpperCase().toUpperCase());
			usu.setCargo(usuario.getCargo().toUpperCase().toUpperCase());
			usu.setCi(usuario.getCi());
			usu.setTelefono(usuario.getTelefono());
			usu.setCorreoelectronico(usuario.getCorreoelectronico().toLowerCase());
			usu.setEstado(usuario.getEstado());
			usu.setTipo(usuario.getTipo());
			usu.setTitulo(usuario.getTitulo());
			usu.setUsername(usuario.getUsername().toLowerCase());

			idusuario = this.serviceUsuario.registrar(usu);

			estadeshabilitado = true;
			activarAP();
			enviarContrasenia(usuario.getCi(), claveNueva);
			
			

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	//VALIDA SI EXISTE EL USUARIO
	public boolean validaCedula(String cedula) {
		valida=serviceUsuario.validaUsuarioCedula(cedula);
//		System.out.println("imprime el valida : "+valida);
		if(valida==true) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Esta cédula ya fue ingresada", "ERROR"));
		}
		return valida;
	}

	public void listarUsuarioPunto() {
		try {
			this.listaUsuario = this.serviceUsuario.listar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// NUEVO REGISTR0
	public Boolean nuevo() {
		estadeshabilitado = false;
		return estadeshabilitado;
	}

	//// ACTIVAR EL BOTON DE ASIGNAR PUNTO
	public Boolean activarAP() {
		estadeshabilitado_ap = false;
		return estadeshabilitado_ap;
	}

	//// DESACTIVAR EL BOTON DE ASIGNAR PUNTO
	public Boolean desactivarAP() {
		estadeshabilitado_ap = true;
		return estadeshabilitado_ap;
	}
	
	public static String generarSecuenciaAleatoria() {
		String sec = "";
		do {
			if (generarNumeroAleatorios(0, 1) == 0)
				sec += generarNumeroAleatorios(0, 9);
			else
				sec += (char) generarNumeroAleatorios(65, 90);
		} while (sec.length() < 10);
		return sec;
	}
	public static int generarNumeroAleatorios(int minimo, int maximo) {
		return (int) Math.floor(Math.random() * (maximo - minimo + 1) + (minimo));
	}	
	public void enviarContrasenia(String cedula, String clave) {
		eviarCorreo(cedula, claveNueva);	
	}
	
	public boolean eviarCorreo(String ci, String clave) {

		usuario = serviceUsuario.muestraUsuarioPorCi(ci);
		institucion = serviceInstitucion.institucionActiva();
			
		Properties props = System.getProperties();
		props.put("mail.smtp.host", institucion.getServidorcorreo()); // El servidor SMTP de Google
		props.put("mail.smtp.user", institucion.getUsuariocorreo());
		props.put("mail.smtp.clave", institucion.getClavecorreo()); // La clave de la cuenta
		props.put("mail.smtp.auth", institucion.getAuth()); // Usar autenticacin mediante usuario y clave
		props.put("mail.smtp.starttls.enable", institucion.getStarttls()); // Para conectar de manera segura al servidor SMTP
		props.put("mail.smtp.port", institucion.getPuerto()); // El puerto SMTP seguro de Google
		props.put("mail.smtp.ssl.trust", "*");
	
		
//		@SuppressWarnings("resource")
//		Formatter obj = new Formatter();
		String asuntoMensaje = "MDG - Sistema Facturación - Credenciales del Usuario "
				+ usuario.getNombre() + " "+ usuario.getApellido();

		String cuerpoMensaje  = "<html><head><title></title></head><body>" + "Estimado(a) Usuario (a): "+ usuario.getNombre() + " "+ usuario.getApellido();

		cuerpoMensaje += "<br><br>Le informamos que se ha realizado la creación de su usuario para el Sistema de Facturaci&oacute;n Electr&oacutenica, su usuario es:  "
		              + usuario.getUsername()
				      + ", con la clave: "
				      + clave 
		              + "<br><br>Atentamente,<br>"
					  + institucion.getNombre() +" - Sistema de Facturaci&oacute;n <br><br>"+ "</body></html>";
		
		Session session = Session.getInstance(props, null);
		session.setDebug(true);

		try {
			MimeBodyPart textoMensaje = new MimeBodyPart();
			textoMensaje.setContent(cuerpoMensaje,"text/html");
			
			MimeMultipart multiParte = new MimeMultipart();
			multiParte.addBodyPart(textoMensaje);

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(institucion.getUsuariocorreo(),institucion.getNombre()));
			message.addRecipients(Message.RecipientType.TO, usuario.getCorreoelectronico()); 
			message.setSubject(asuntoMensaje);
			message.setContent(multiParte);
			Transport transport = session.getTransport("smtp");
			transport.connect(institucion.getNombre(), institucion.getUsuariocorreo(),
					institucion.getClavecorreo());
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Usuario registrado y se remite las credenciales al email registrado", "Aviso"));
			return true;
		} catch (Exception e) {
			
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR: No fue enviado el correo", "ERROR"));
			return false;
		}
	}

	////
	
	public boolean isValida() {
		return valida;
	}

	public void setValida(boolean valida) {
		this.valida = valida;
	}


	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isEstadeshabilitado() {
		return estadeshabilitado;
	}

	public void setEstadeshabilitado(boolean estadeshabilitado) {
		this.estadeshabilitado = estadeshabilitado;
	}

	public boolean isEstadeshabilitado_ap() {
		return estadeshabilitado_ap;
	}

	public void setEstadeshabilitado_ap(boolean estadeshabilitado_ap) {
		this.estadeshabilitado_ap = estadeshabilitado_ap;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public String getClaveNueva() {
		return claveNueva;
	}

	public void setClaveNueva(String claveNueva) {
		this.claveNueva = claveNueva;
	}
	

}
