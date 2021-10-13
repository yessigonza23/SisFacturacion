package ec.gob.mdg.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.gob.mdg.model.Recaudacion;
import ec.gob.mdg.model.RecaudacionDetalle;
import ec.gob.mdg.service.IRecaudacionDetalleService;
import ec.gob.mdg.service.IRecaudacionService;

@Named
@ViewScoped
public class RecaudacionDetalleBean implements Serializable{

	private static final long serialVersionUID = -2095447481637152849L;

	@Inject
	private IRecaudacionDetalleService service;
	
	@Inject
	private IRecaudacionService servicer;
	
	private List<RecaudacionDetalle> lista;
	private String tipoDialog;
	private RecaudacionDetalle recaudacionDetalle;
	
	private List<Recaudacion> listar;
	private Recaudacion recaudacion;
	
	@PostConstruct
	public void init() {
		this.recaudacionDetalle = new RecaudacionDetalle();
		this.listar();
		this.listarr();
		this.tipoDialog = "NuevoReg";
	}
	
	public void operar(String accion) {
		try {
			if(accion.equalsIgnoreCase("R")) {
				this.service.registrar(this.recaudacionDetalle);
			}else if(accion.equalsIgnoreCase("M")) {
				this.service.modificar(this.recaudacionDetalle);
			}
			this.listar();
		} catch (Exception e) {
			//System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void listar() {
		try {
			this.lista = this.service.listar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
		
	private void listarr() {
		try {
			this.listar = this.servicer.listar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void mostrarData(RecaudacionDetalle i) {
		this.recaudacionDetalle = i;
		this.tipoDialog = "Modificar Detalle";
	}
	
	public void limpiarControles() {
		this.recaudacionDetalle = new RecaudacionDetalle();
		this.tipoDialog = "Nuevo Detalle";
	}
	
	public List<RecaudacionDetalle> getLista() {
		return lista;
	}
	public void setLista(List<RecaudacionDetalle> lista) {
		this.lista = lista;
	}
	public String getTipoDialog() {
		return tipoDialog;
	}
	public void setTipoDialog(String tipoDialog) {
		this.tipoDialog = tipoDialog;
	}
	public RecaudacionDetalle getRecaudacionDetalle() {
		return recaudacionDetalle;
	}
	public void setRecaudacionDetalle(RecaudacionDetalle recaudacionDetalle) {
		this.recaudacionDetalle = recaudacionDetalle;
	}
	public List<Recaudacion> getListar() {
		return listar;
	}
	public void setListar(List<Recaudacion> listar) {
		this.listar = listar;
	}
	public Recaudacion getRecaudacion() {
		return recaudacion;
	}
	public void setRecaudacion(Recaudacion recaudacion) {
		this.recaudacion = recaudacion;
	}
	
	
	
}
