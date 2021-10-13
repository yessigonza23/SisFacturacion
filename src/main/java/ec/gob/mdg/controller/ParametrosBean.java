package ec.gob.mdg.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.gob.mdg.model.Parametro;
import ec.gob.mdg.service.IParametroService;

@Named
@ViewScoped
public class ParametrosBean implements Serializable{

	private static final long serialVersionUID = -1980131397658073010L;

	@Inject
	private IParametroService service;
	
	private List<Parametro> lista;
	private String tipoDialog;
	private Parametro parametro;
	
	@PostConstruct
	public void init() {
		this.parametro = new Parametro();
		this.listar();
		this.tipoDialog = "Nuevo";
	}
	
	public void operar(String accion) {
		try {
			if(accion.equalsIgnoreCase("R")) {
				System.out.println("entraR " +accion);
				this.service.registrar(this.parametro);
			}else if(accion.equalsIgnoreCase("M")) {
				System.out.println("entraM " +accion);
				this.service.modificar(this.parametro);
			}
			this.listar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void listar() {
		try {
			this.lista = this.service.listar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
		
	public void mostrarData(Parametro i) {
		this.parametro = i;
		this.tipoDialog = "Modificar Parametro";
	}
	
	public void limpiarControles() {
		this.parametro = new Parametro();
		this.tipoDialog = "Nuevo Parametro";
	}

	public List<Parametro> getLista() {
		return lista;
	}

	public void setLista(List<Parametro> lista) {
		this.lista = lista;
	}

	public String getTipoDialog() {
		return tipoDialog;
	}

	public void setTipoDialog(String tipoDialog) {
		this.tipoDialog = tipoDialog;
	}

	public Parametro getParametro() {
		return parametro;
	}

	public void setParametro(Parametro parametro) {
		this.parametro = parametro;
	}
	
	
	
}
