package ec.gob.mdg.controller;

import java.io.Serializable;
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
import ec.gob.mdg.service.IInstitucionService;
import ec.gob.mdg.service.IProvinciaService;
import ec.gob.mdg.service.IPuntoRecaudacionService;

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
	
	private List<PuntoRecaudacion> lista;	
	
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
		this.tipoDialog = "Nuevo";
	}
	
	public void operar(String accion) {
		try {
			if(accion.equalsIgnoreCase("R")) {
				PuntoRecaudacion p = new PuntoRecaudacion();
				p.setId(puntoRecaudacion.getId());
				p.setInstitucion(puntoRecaudacion.getInstitucion());
				p.setNombre(puntoRecaudacion.getNombre().toUpperCase());
				p.setProvincia(puntoRecaudacion.getProvincia());
				p.setTelefono(puntoRecaudacion.getTelefono());
				p.setDireccion(puntoRecaudacion.getDireccion().toUpperCase());
				p.setResponsable(puntoRecaudacion.getResponsable().toUpperCase());
				p.setResponsablecargo(puntoRecaudacion.getResponsablecargo().toUpperCase());
				p.setJefe(puntoRecaudacion.getJefe().toUpperCase());
				p.setJefecargo(puntoRecaudacion.getJefecargo().toUpperCase());
				p.setEstado(puntoRecaudacion.getEstado());
				p.setEstablecimiento(puntoRecaudacion.getEstablecimiento());
				p.setPuntoemision(puntoRecaudacion.getPuntoemision());
				p.setSecuencialfactura(puntoRecaudacion.getSecuencialfactura());
				p.setSecuencialnotas(puntoRecaudacion.getSecuencialnotas());
				this.service.registrar(p);
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"Se grabó con éxito", "Satisfactoriamente"));
			}else if(accion.equalsIgnoreCase("M")) {
				//PuntoRecaudacion p = puntoRecaudacion;
				puntoRecaudacion.setInstitucion(puntoRecaudacion.getInstitucion());
				puntoRecaudacion.setNombre(puntoRecaudacion.getNombre().toUpperCase());
				puntoRecaudacion.setProvincia(puntoRecaudacion.getProvincia());
				puntoRecaudacion.setTelefono(puntoRecaudacion.getTelefono());
				puntoRecaudacion.setDireccion(puntoRecaudacion.getDireccion().toUpperCase());
				puntoRecaudacion.setResponsable(puntoRecaudacion.getResponsable().toUpperCase());
				puntoRecaudacion.setResponsablecargo(puntoRecaudacion.getResponsablecargo().toUpperCase());
				puntoRecaudacion.setJefe(puntoRecaudacion.getJefe().toUpperCase());
				puntoRecaudacion.setJefecargo(puntoRecaudacion.getJefecargo().toUpperCase());
				puntoRecaudacion.setEstado(puntoRecaudacion.getEstado());
				puntoRecaudacion.setEstablecimiento(puntoRecaudacion.getEstablecimiento());
				puntoRecaudacion.setPuntoemision(puntoRecaudacion.getPuntoemision());
				puntoRecaudacion.setSecuencialfactura(puntoRecaudacion.getSecuencialfactura());
				puntoRecaudacion.setSecuencialnotas(puntoRecaudacion.getSecuencialnotas());
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

