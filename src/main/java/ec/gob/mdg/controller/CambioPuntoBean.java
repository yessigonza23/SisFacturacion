package ec.gob.mdg.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import ec.gob.mdg.model.PuntoRecaudacion;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.service.IPuntoRecaudacionService;
import ec.gob.mdg.service.IUsuarioPuntoService;

@Named
@ViewScoped
public class CambioPuntoBean implements Serializable{


	private static final long serialVersionUID = 1L;

	@Inject
	private IUsuarioPuntoService serviceUsuPunto;

	@Inject
	private IPuntoRecaudacionService servicePuntoRecaudacion;

	private List<PuntoRecaudacion> listaPuntoRecaudacion;
	
	private UsuarioPunto usuPunto = new UsuarioPunto();
	Usuario p = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	PuntoRecaudacion punto_actual,punto_nuevo;
	
	@PostConstruct
	public void init() {
		try {	
			listarPuntos();
			//Punto en el que estoy logueada
			usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(p);
     		punto_actual = usuPunto.getPuntoRecaudacion();
			listarPuntos();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void listarPuntos() {
		try {
			this.listaPuntoRecaudacion = this.servicePuntoRecaudacion.listar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	@Transactional
	public void modificarPunto(PuntoRecaudacion punto_nuevo) {
		try {		
			System.out.println("1");
			usuPunto.setPuntoRecaudacion(punto_nuevo);
			System.out.println("2");
			serviceUsuPunto.modificar(usuPunto);
			System.out.println("3");
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Se cambió con éxito", "Haga clic en Continuar"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//getter y setters

	public UsuarioPunto getUsuPunto() {
		return usuPunto;
	}

	public void setUsuPunto(UsuarioPunto usuPunto) {
		this.usuPunto = usuPunto;
	}

	public Usuario getP() {
		return p;
	}

	public void setP(Usuario p) {
		this.p = p;
	}

	public PuntoRecaudacion getPunto_actual() {
		return punto_actual;
	}

	public void setPunto_actual(PuntoRecaudacion punto_actual) {
		this.punto_actual = punto_actual;
	}

	

	public List<PuntoRecaudacion> getListaPuntoRecaudacion() {
		return listaPuntoRecaudacion;
	}

	public void setListaPuntoRecaudacion(List<PuntoRecaudacion> listaPuntoRecaudacion) {
		this.listaPuntoRecaudacion = listaPuntoRecaudacion;
	}

	public PuntoRecaudacion getPunto_nuevo() {
		return punto_nuevo;
	}

	public void setPunto_nuevo(PuntoRecaudacion punto_nuevo) {
		this.punto_nuevo = punto_nuevo;
	}
	
	

}
