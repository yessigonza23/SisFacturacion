package ec.gob.mdg.controller;


import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.gob.mdg.model.Rol;
import ec.gob.mdg.service.IRolService;


@Named
@ViewScoped
public class RolBean implements Serializable {

	private static final long serialVersionUID = 8346668750642429805L;

	@Inject
	private IRolService service;
	

	private List<Rol> lista;
	private String tipoDialog;
	private Rol rol;
	String permiso="N";
	
	@PostConstruct
	public void init() {

		this.rol = new Rol();
		this.listar();
		this.tipoDialog = "Nuevo";
	}
	
	public void listar() {
		try {
			this.lista = this.service.listar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void operar(String accion) {
		try {
			if(accion.equalsIgnoreCase("R")) {
				this.service.registrar(this.rol);
			}else if(accion.equalsIgnoreCase("M")) {
				this.service.modificar(this.rol);
			}
			this.listar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void mostrarData(Rol i) {
		this.rol = i;
		this.tipoDialog = "Modificar Rol";
	}
	
	public void limpiarControles() {
		this.rol = new Rol();
		this.tipoDialog = "Nuevo Rol";
	}
	
	
	///////
	
	public List<Rol> getLista() {
		return lista;
	}
	public void setLista(List<Rol> lista) {
		this.lista = lista;
	}
	public String getTipoDialog() {
		return tipoDialog;
	}
	public void setTipoDialog(String tipoDialog) {
		this.tipoDialog = tipoDialog;
	}
	public Rol getRol() {
		return rol;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	

}
