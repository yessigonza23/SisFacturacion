package ec.gob.mdg.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.gob.mdg.model.Institucion;
import ec.gob.mdg.model.Provincia;
import ec.gob.mdg.model.PuntoRecaudacion;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.service.IInstitucionService;
import ec.gob.mdg.service.IProvinciaService;
import ec.gob.mdg.service.IPuntoRecaudacionService;
import ec.gob.mdg.service.IUsuarioService;

@Named
@ViewScoped
public class PuntoRecaudacionBean implements Serializable {
	
	private static final long serialVersionUID = -1880117470518909108L;
	
	@Inject
	private IPuntoRecaudacionService service;
	
	@Inject
	private IInstitucionService servicei;
	
	@Inject
	private IProvinciaService serviceProvincia;
	
	@Inject
	private IUsuarioService serviceUsuario;
	
	public IUsuarioService getServiceUsuario() {
		return serviceUsuario;
	}

	public void setServiceUsuario(IUsuarioService serviceUsuario) {
		this.serviceUsuario = serviceUsuario;
	}

	public List<Usuario> getListaUsuarioAnalista() {
		return serviceUsuario.listarAnalistas();
	}

	public void setListaUsuarioAnalista(List<Usuario> listaUsuarioAnalista) {
		this.listaUsuarioAnalista = listaUsuarioAnalista;
	}

	private List<PuntoRecaudacion> lista;
	@SuppressWarnings("unused")
	private List<Usuario> listaUsuarioAnalista = new ArrayList<Usuario>() ;
	
	private String tipoDialog;
	private PuntoRecaudacion puntoRecaudacion;
	
	private List<Institucion> listai;
	private Institucion institucion;
	
	private List<Provincia> listaProvincia;
	private Provincia provincia;
	
	@PostConstruct
	public void init() {
		this.puntoRecaudacion = new PuntoRecaudacion();
		this.listar();
		this.listari();
		this.listarProvincia();
//		this.listarUsuarioAnalista();
		this.tipoDialog = "Nuevo";
	}
	
	public void operar(String accion) {
		try {
			if(accion.equalsIgnoreCase("R")) {
				PuntoRecaudacion p = new PuntoRecaudacion();
				this.service.registrar(p);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Se grabó con éxito", "Satisfactoriamente"));
			}else if(accion.equalsIgnoreCase("M")) {
					this.service.modificar(puntoRecaudacion);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Se grabó con éxito", "Satisfactoriamente"));
			}
			this.listar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void listar() {
		try {
			this.lista = this.service.listar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void listari() {
		try {
			this.listai = this.servicei.listar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private void listarProvincia() {
		try {
			this.listaProvincia = this.serviceProvincia.listar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
//	public void listarUsuarioAnalista() {
//		try {
//			this.listaUsuarioAnalista = this.serviceUsuario.listarAnalistas();
//			for (Usuario u:listaUsuarioAnalista) {
//				System.out.println("usuario " + u.getApellido());
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	public void mostrarData(PuntoRecaudacion i) {
		this.puntoRecaudacion = i;
		this.tipoDialog = "Modificar Punto de Recaudación";
	}
	
	public void limpiarControles() {
		this.puntoRecaudacion = new PuntoRecaudacion();
		this.tipoDialog = "Nuevo Punto de Recaudación";
	}
	
		
	public List<Institucion> getListai() {
		return listai;
	}


	public void setListai(List<Institucion> listai) {
		this.listai = listai;
	}


	public Institucion getInstitucion() {
		return institucion;
	}


	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}
	
	public List<PuntoRecaudacion> getLista() {
		return lista;
	}

	public void setLista(List<PuntoRecaudacion> lista) {
		this.lista = lista;
	}
	
	
	public String getTipoDialog() {
		return tipoDialog;
	}


	public void setTipoDialog(String tipoDialog) {
		this.tipoDialog = tipoDialog;
	}


	public PuntoRecaudacion getPuntoRecaudacion() {
		return puntoRecaudacion;
	}


	public void setPuntoRecaudacion(PuntoRecaudacion puntoRecaudacion) {
		this.puntoRecaudacion = puntoRecaudacion;
	}

	public List<Provincia> getListaProvincia() {
		return listaProvincia;
	}

	public void setListaProvincia(List<Provincia> listaProvincia) {
		this.listaProvincia = listaProvincia;
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}
	
	


}

