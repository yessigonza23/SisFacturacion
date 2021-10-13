package ec.gob.mdg.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.gob.mdg.model.PlanContable;
import ec.gob.mdg.model.PlanPresupuestario;
import ec.gob.mdg.model.Proceso;
import ec.gob.mdg.model.Recaudacion;
import ec.gob.mdg.model.RecaudacionDetalle;
import ec.gob.mdg.service.IPlanContableService;
import ec.gob.mdg.service.IPlanPresupuestarioService;
import ec.gob.mdg.service.IProcesoService;
import ec.gob.mdg.service.IRecaudacionService;

@Named
@ViewScoped
public class RecaudacionBean implements Serializable {

	private static final long serialVersionUID = -2622304613361080515L;

	@Inject
	private IRecaudacionService service;
	
	@Inject
	private IPlanContableService servicePlanContable;
	
	@Inject
	private IPlanPresupuestarioService servicePlanPresupuestario;
	
	@Inject
	private IProcesoService servicep;
	
	private List<Recaudacion> lista;
	private List<RecaudacionDetalle> listad;
	private List<PlanContable> listaPlanContable;
	private List<PlanPresupuestario> listaPlanPresupuetario;
	private String tipoDialog;
	private Recaudacion recaudacion;
	
	private List<Proceso> listap;
	private Proceso proceso;
	
	@PostConstruct
	public void init() {
		this.recaudacion = new Recaudacion();
		this.listar();
		this.listarp();
		this.listarPlanContable();
		this.listarPlanPresupuestario();
		this.tipoDialog = "Nuevo";
	}
	
	public void listar() {
		try {
			this.lista = this.service.listar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void listarPlanContable() {
		try {
			this.listaPlanContable = this.servicePlanContable.listar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void listarPlanPresupuestario() {
		try {
			this.listaPlanPresupuetario = this.servicePlanPresupuestario.listar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public void operar(String accion) {
		try {
			if(accion.equalsIgnoreCase("R")) {
				this.service.registrar(this.recaudacion);
			}else if(accion.equalsIgnoreCase("M")) {
				this.service.modificar(this.recaudacion);
			}
			this.listar();
		} catch (Exception e) {
			//System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void listarp() {
		try {
			this.listap = this.servicep.listar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void mostrarData(Recaudacion i) {
		this.recaudacion = i;
		this.tipoDialog = "Modificar Recaudación";
	}
	
	public void limpiarControles() {
		this.recaudacion = new Recaudacion();
		this.tipoDialog = "Nueva Recaudación";
	}
	
	public List<RecaudacionDetalle> getListad() {
		return listad;
	}

	public void setListad(List<RecaudacionDetalle> listad) {
		this.listad = listad;
	}

	public List<Recaudacion> getLista() {
		return lista;
	}
	public void setLista(List<Recaudacion> lista) {
		this.lista = lista;
	}
	public String getTipoDialog() {
		return tipoDialog;
	}
	public void setTipoDialog(String tipoDialog) {
		this.tipoDialog = tipoDialog;
	}
	public Recaudacion getRecaudacion() {
		return recaudacion;
	}
	public void setRecaudacion(Recaudacion recaudacion) {
		this.recaudacion = recaudacion;
	}
	public List<Proceso> getListap() {
		return listap;
	}
	public void setListap(List<Proceso> listap) {
		this.listap = listap;
	}
	public Proceso getProceso() {
		return proceso;
	}
	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}

	public List<PlanContable> getListaPlanContable() {
		return listaPlanContable;
	}

	public void setListaPlanContable(List<PlanContable> listaPlanContable) {
		this.listaPlanContable = listaPlanContable;
	}

	public List<PlanPresupuestario> getListaPlanPresupuetario() {
		return listaPlanPresupuetario;
	}

	public void setListaPlanPresupuetario(List<PlanPresupuestario> listaPlanPresupuetario) {
		this.listaPlanPresupuetario = listaPlanPresupuetario;
	}
	
	
	

}
