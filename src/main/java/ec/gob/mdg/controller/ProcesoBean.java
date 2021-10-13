package ec.gob.mdg.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.gob.mdg.model.Institucion;
import ec.gob.mdg.model.Proceso;
import ec.gob.mdg.service.IInstitucionService;
import ec.gob.mdg.service.IProcesoService;

@Named
@ViewScoped
public class ProcesoBean implements Serializable{

	private static final long serialVersionUID = -932196271143767334L;

	@Inject
	private IProcesoService service;
	
	@Inject
	private IInstitucionService servicei;
	
	private List<Proceso> lista;
	private String tipoDialog;
	private Proceso proceso;
	
	private List<Institucion> listai;
	private Institucion institucion;
	
	@PostConstruct
	public void init() {
		this.proceso = new Proceso();
		this.listar();
		this.listari();
		this.tipoDialog = "Nuevo";
	}
	
	public void listar() {
		try {
			this.lista = this.service.listar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	private void listari() {
		try {
			this.listai = this.servicei.listar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void operar(String accion) {
		try {
			if(accion.equalsIgnoreCase("R")) {
				this.service.registrar(this.proceso);
			}else if(accion.equalsIgnoreCase("M")) {
				this.service.modificar(this.proceso);
			}
			this.listar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void mostrarData(Proceso i) {
		this.proceso = i;
		this.tipoDialog = "Modificar Proceso";
	}
	
	public void limpiarControles() {
		this.proceso = new Proceso();
		this.tipoDialog = "Nuevo Proceso";
	}
	
	public List<Proceso> getLista() {
		return lista;
	}
	public void setLista(List<Proceso> lista) {
		this.lista = lista;
	}
	public String getTipoDialog() {
		return tipoDialog;
	}
	public void setTipoDialog(String tipoDialog) {
		this.tipoDialog = tipoDialog;
	}
	public Proceso getProceso() {
		return proceso;
	}
	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
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
	
	

}
