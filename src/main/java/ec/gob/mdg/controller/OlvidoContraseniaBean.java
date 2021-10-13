package ec.gob.mdg.controller;

import java.io.Serializable;
import java.util.Date;
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
import ec.gob.mdg.util.UtilsDate;
import ec.gob.mdg.validaciones.FunValidaciones;


@Named
@ViewScoped
public class OlvidoContraseniaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IUsuarioService serviceUsuario;

	@Inject
	private FunValidaciones funValidaciones;
	
	@Inject
	private EnviarCorreoOlvidoContrasenia enviaCorreo;
	
	@Inject
	private IInstitucionService serviceInstitucion;

	String cedula = null;
	String claveNueva = null;
	Boolean resultado = false;
	Usuario usuario;
	Date fechaActual ;
	private Institucion institucion;

	@PostConstruct
	public void init() {
		try {

			cedula = null;
			claveNueva = null;
			fechaActual = UtilsDate.fechaActual();			
			claveNueva = generarSecuenciaAleatoria();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void validarCedula(String cedula) {
		try {
			if (cedula != null) {
				resultado = funValidaciones.cedulaExiste(cedula);	
				if (resultado == true) {
					modificar();
					
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"No. Identificaión no encontrada", "ERROR"));
				}
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_FATAL, "Ingrese el No. de identificación", "ERROR"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Se ha enviado la nueva clave a su correo electrónico registrado", "Cambio Exitoso"));
		modificar();
	}
	
	
	// registrar usuario
	public void modificar() {		
		usuario = serviceUsuario.muestraUsuarioPorCi(cedula);
		try {	
			
			String clave = claveNueva;			
			String claveHash = BCrypt.hashpw(clave, BCrypt.gensalt());
			
			usuario.setContrasena(claveHash);
			fechaActual = UtilsDate.fechaActual();
			usuario.setFechacambioclave(fechaActual);
			usuario.setFechareinicioclave(fechaActual);
			//usuario.setClavefirma(usuario.getClavefirma());
			this.serviceUsuario.modificar(usuario);
			eviarCorreo(cedula, claveNueva);
			
			

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean eviarCorreo(String ci, String clave) {

		usuario = serviceUsuario.muestraUsuarioPorCi(ci);
		institucion = serviceInstitucion.institucionActiva();
			
		Properties props = System.getProperties();
		props.put("mail.smtp.host", institucion.getServidorcorreo()); // El servidor SMTP de Google
		props.put("mail.smtp.user", institucion.getUsuariocorreo());
		props.put("mail.smtp.clave", institucion.getClavecorreo()); // La clave de la cuenta
		props.put("mail.smtp.auth", "true"); // Usar autenticacion mediante usuario y clave
		props.put("mail.smtp.starttls.enable", "true"); // Para conectar de manera segura al servidor SMTP
		props.put("mail.smtp.port", "587"); // El puerto SMTP seguro de Google
		props.put("mail.smtp.ssl.trust", "*");
	
		
//		@SuppressWarnings("resource")
//		Formatter obj = new Formatter();
		String asuntoMensaje = "MDG - Sistema Facturación - Recupera Contraseña del Usuario "
				+ usuario.getNombre() + " "+ usuario.getApellido();

		String cuerpoMensaje  = "<html><head><title></title></head><body>" + "Estimado (a) "+ usuario.getNombre() + " "+ usuario.getApellido();

		cuerpoMensaje += "<br><br>Le informamos que realiz&oacute; el proceso de recuperaci&oacute;n de clave del Sistema de Facturaci&oacute;n Electronica, su nueva clave es  "
		              + clave 
		              + "<br><br>Atentamente,<br>"
					  + "Ministerio de Gobierno - Sistema de Facturaci&oacute;n <br><br>"+ "</body></html>";
		
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
			transport.connect("mail.ministeriodegobierno.gob.ec", institucion.getUsuariocorreo(),
					institucion.getClavecorreo());
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Clave nueva generada, se envía al e-mail registrado", "Aviso"));
			return true;
		} catch (Exception e) {
			
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR: No se Envia por correo", "ERROR"));
			return false;
		}
	}

	
	
//getters & setter
	public String getCedula() {
		return cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public FunValidaciones getFunValidaciones() {
		return funValidaciones;
	}

	public void setFunValidaciones(FunValidaciones funValidaciones) {
		this.funValidaciones = funValidaciones;
	}

	public EnviarCorreoOlvidoContrasenia getEnviaCorreo() {
		return enviaCorreo;
	}

	public void setEnviaCorreo(EnviarCorreoOlvidoContrasenia enviaCorreo) {
		this.enviaCorreo = enviaCorreo;
	}

	public String getClaveNueva() {
		return claveNueva;
	}

	public void setClaveNueva(String claveNueva) {
		this.claveNueva = claveNueva;
	}

	public Boolean getResultado() {
		return resultado;
	}

	public void setResultado(Boolean resultado) {
		this.resultado = resultado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}


	
	

}
