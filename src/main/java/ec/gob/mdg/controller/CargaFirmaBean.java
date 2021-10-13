package ec.gob.mdg.controller;

import java.io.Serializable;
import java.util.Date;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.UploadedFile;
import ec.gob.mdg.model.PuntoRecaudacion;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.service.IPuntoRecaudacionService;
import ec.gob.mdg.service.IUsuarioPuntoService;
import ec.gob.mdg.service.IUsuarioService;
import ec.gob.mdg.util.UtilsArchivos;

@Named
@ViewScoped
public class CargaFirmaBean implements Serializable {

	private static final long serialVersionUID = -6490646968982098807L;

	@Inject
	private IPuntoRecaudacionService servicePuntoRecaudacion;

	@Inject
	private IUsuarioPuntoService serviceUsuPunto;

	@Inject
	private IUsuarioService serviceUsuario;

	private UsuarioPunto usuPunto = new UsuarioPunto();
	private PuntoRecaudacion punto;

	PuntoRecaudacion nombre;
	Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");

	Date fechaActual;
	String path;
	String nombreArchivo = null;
	boolean desactiva = false;
	private UploadedFile file;

	@PostConstruct
	public void init() {
		try {
			usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(usuario);
			punto = usuPunto.getPuntoRecaudacion();
			nombre = servicePuntoRecaudacion.listarPorId(punto);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void upload() {
		try {
			if (file != null) {
				nombreArchivo = "certificado" + punto.getId() + ".p12";
				UtilsArchivos.uploadFirma(file, nombreArchivo);
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Seleccione un archivo"));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public void modificaUsuario() {

		try {
			usuario.setNombrefirmaelectronica(nombreArchivo);
			String clave = this.usuario.getClavefirma();
			String claveBase64 = cifrarBase64(clave);			
			usuario.setClavefirma(claveBase64);
			serviceUsuario.modificar(usuario);
			desactiva = true;

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Se guarda satisfactoriamente", "Exitoso"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String cifrarBase64(String a) {
		Base64.Encoder encoder = Base64.getEncoder();
		String b = encoder.encodeToString(a.getBytes(StandardCharsets.UTF_8));
		return b;
	}

	
	////////////// GETTERS Y SETTERS

	public PuntoRecaudacion getNombre() {
		return nombre;
	}

	public void setNombre(PuntoRecaudacion nombre) {
		this.nombre = nombre;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public UsuarioPunto getUsuPunto() {
		return usuPunto;
	}

	public void setUsuPunto(UsuarioPunto usuPunto) {
		this.usuPunto = usuPunto;
	}

	public PuntoRecaudacion getPunto() {
		return punto;
	}

	public void setPunto(PuntoRecaudacion punto) {
		this.punto = punto;
	}

	public boolean isDesactiva() {
		return desactiva;
	}

	public void setDesactiva(boolean desactiva) {
		this.desactiva = desactiva;
	}

}
