package ec.gob.mdg.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.gob.mdg.model.Parametro;
import ec.gob.mdg.model.ParametroDetalle;
import ec.gob.mdg.service.IParametroDetalleService;
import ec.gob.mdg.service.IParametroService;

@Named
@ViewScoped
public class ParametrosDetalleBean implements Serializable{

	private static final long serialVersionUID = 4794115144069842001L;

	@Inject
	private IParametroDetalleService service;
	
	@Inject
	private IParametroService servicep;
	
	private List<ParametroDetalle> lista;
	private String tipoDialog;
	private ParametroDetalle parametroDetalle;
	
	private List<Parametro> listap;
	private Parametro parametro;
	
	@PostConstruct
	public void init() {
		this.parametroDetalle = new ParametroDetalle();
		this.listar();
		this.listarp();
		this.tipoDialog = "Nuevo";
	}
	
	public void listar() {
		try {
			this.lista = this.service.listar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<Parametro> getListap() {
		return listap;
	}

	public void setListap(List<Parametro> listap) {
		this.listap = listap;
	}

	public Parametro getParametro() {
		return parametro;
	}

	public void setParametro(Parametro parametro) {
		this.parametro = parametro;
	}

	private void listarp() {
		try {
			this.listap = this.servicep.listar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public void operar(String accion) {
		try {
			if(accion.equalsIgnoreCase("R")) {
				this.service.registrar(this.parametroDetalle);
			}else if(accion.equalsIgnoreCase("M")) {
				this.service.modificar(this.parametroDetalle);
			}
			this.listar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void mostrarData(ParametroDetalle i) {
		this.parametroDetalle = i;
		this.tipoDialog = "Modificar Detalle";
	}
	
	public void limpiarControles() {
		this.parametroDetalle = new ParametroDetalle();
		this.tipoDialog = "Nuevo Detalle";
	}

	public List<ParametroDetalle> getLista() {
		return lista;
	}

	public void setLista(List<ParametroDetalle> lista) {
		this.lista = lista;
	}

	public String getTipoDialog() {
		return tipoDialog;
	}

	public void setTipoDialog(String tipoDialog) {
		this.tipoDialog = tipoDialog;
	}

	public ParametroDetalle getParametroDetalle() {
		return parametroDetalle;
	}

	public void setParametroDetalle(ParametroDetalle parametroDetalle) {
		this.parametroDetalle = parametroDetalle;
	}
	
	
}
