package ec.gob.mdg.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.gob.mdg.model.Menu;
import ec.gob.mdg.service.IMenuService;
@Named
@ViewScoped
public class MenuDinamicoBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@Inject
	private IMenuService serviceMenu;
	
	private List<Menu> listaMenu;
	private Menu menu;
	private String tipoDialog;
	
	@PostConstruct
	public void init() {
		this.listar();
		this.tipoDialog = "Nuevo";
	}
	
	public void listar() {
		try {
			listaMenu = serviceMenu.listar();
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	public void operar(String accion) {
		try {
			if(accion.equalsIgnoreCase("R")) {
				this.serviceMenu.registrar(this.menu);
			}else if(accion.equalsIgnoreCase("M")) {
				this.serviceMenu.modificar(this.menu);
			}
			this.listar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void mostrarData(Menu i) {
		this.menu = i;
		this.tipoDialog = "Modificar Menu";
	}
	
	public void limpiarControles() {
		this.menu = new Menu();
		this.tipoDialog = "Nuevo Menu";
	}
	

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public String getTipoDialog() {
		return tipoDialog;
	}

	public void setTipoDialog(String tipoDialog) {
		this.tipoDialog = tipoDialog;
	}

	public List<Menu> getListaMenu() {
		return listaMenu;
	}

	public void setListaMenu(List<Menu> listaMenu) {
		this.listaMenu = listaMenu;
	}


}
