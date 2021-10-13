package ec.gob.mdg.control.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.gob.mdg.control.model.Sanciones_Admin_DTO;
import ec.gob.mdg.control.service.ISanciones_Admin_DTOService;

@Named
@ViewScoped
public class ConsultaSancionesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ISanciones_Admin_DTOService serviceSancionesAdmin;

	private List<Sanciones_Admin_DTO> listaSanciones = new ArrayList<Sanciones_Admin_DTO>();
	private List<Sanciones_Admin_DTO> listaSancionesRuc;
	private Sanciones_Admin_DTO sanciones;

	Integer codigo;
	String ruc;
	String numerojuicio;
	Double total = 0.00;

	@PostConstruct
	public void init() {
		try {
			listarEntidadesSanciones();
			//listarServicios();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// LISTAR ENTIDADES CON SANCIONES Y COACTIVAS
	public void listarEntidadesSanciones() {
		try {
			this.listaSanciones = this.serviceSancionesAdmin.listarEntidadesSanciones();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/// IR A DETALLE DE SANCIONES POR ENTIDAD O EMPRESA
	public String irDetalleSanciones(String ruc) {
		
		final FacesContext context = FacesContext.getCurrentInstance();
		final Flash flash = context.getExternalContext().getFlash();
		flash.put("param", ruc);
		return "sancionesdetalle?faces-redirect=true";
	}

	

	///// GETTERS Y SETTERS

	public List<Sanciones_Admin_DTO> getListaSanciones() {
		return listaSanciones;
	}

	public void setListaSanciones(List<Sanciones_Admin_DTO> listaSanciones) {
		this.listaSanciones = listaSanciones;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}



	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public Sanciones_Admin_DTO getSanciones() {
		return sanciones;
	}

	public void setSanciones(Sanciones_Admin_DTO sanciones) {
		this.sanciones = sanciones;
	}

	public List<Sanciones_Admin_DTO> getListaSancionesRuc() {
		return listaSancionesRuc;
	}

	public void setListaSancionesRuc(List<Sanciones_Admin_DTO> listaSancionesRuc) {
		this.listaSancionesRuc = listaSancionesRuc;
	}
	
	
	

}
