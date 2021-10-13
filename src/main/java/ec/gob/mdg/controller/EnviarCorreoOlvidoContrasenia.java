package ec.gob.mdg.controller;

import java.io.Serializable;
import java.util.Properties;


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

import ec.gob.mdg.model.Institucion;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.service.IInstitucionService;
import ec.gob.mdg.service.IUsuarioService;

@Named
@ViewScoped
public class EnviarCorreoOlvidoContrasenia implements Serializable {

	private static final long serialVersionUID = 1L;
	
		
	@Inject
	private IUsuarioService serviceUsuario;	
	
	@Inject
	private IInstitucionService serviceInstitucion;

	private Institucion institucion;
	
	private Usuario usuario;
	

	// PARAMETROS EL ID DEL COMPROBANTE Y EL TIPO DE COMPROBANTE F-FACTURA Y N-NOTA
	// DE CREDITO
	public boolean eviarCorreo(String ci, String clave) {

		usuario = serviceUsuario.muestraUsuarioPorCi(ci);
		institucion = serviceInstitucion.institucionActiva();
			
		Properties props = System.getProperties();
		props.put("mail.smtp.host", institucion.getServidorcorreo()); // El servidor SMTP de Google
		props.put("mail.smtp.user", institucion.getUsuariocorreo());
		props.put("mail.smtp.clave", institucion.getClavecorreo()); // La clave de la cuenta
		props.put("mail.smtp.auth", "true"); // Usar autenticacin mediante usuario y clave
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
			System.out.println("1" );

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(institucion.getUsuariocorreo(),institucion.getNombre()));
			message.addRecipients(Message.RecipientType.TO, usuario.getCorreoelectronico()); 
			message.setSubject(asuntoMensaje);
			message.setContent(multiParte);
			Transport transport = session.getTransport("smtp");
			transport.connect("mail.ministeriodegobierno.gob.ec", institucion.getUsuariocorreo(),
					institucion.getClavecorreo());
			transport.sendMessage(message, message.getAllRecipients());
			System.out.println("2" );
			transport.close();
			System.out.println("3" );
			
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Se envió con éxito", "Aviso"));
			System.out.println("4" );
			return true;
		} catch (Exception e) {
			System.out.println("Error " + e);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR: No se Envió por correo", "ERROR"));
			return false;
		}
	}

	/// GETTERS Y SETTERS

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	
	


}
