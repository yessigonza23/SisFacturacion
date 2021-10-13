package ec.gob.mdg.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.gob.mdg.model.Menu;
import ec.gob.mdg.model.MenuRol;
import ec.gob.mdg.service.IMenuRolService;
import ec.gob.mdg.service.IMenuService;
import ec.gob.mdg.model.Rol;
import ec.gob.mdg.service.IRolService;

@Named
@ViewScoped
public class AsignarMenuRolBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IRolService serviceRol;

	@Inject
	private IMenuRolService serviceMenuRol;

	@Inject
	private IMenuService serviceMenu;

	private List<MenuRol> listaMenuRol;
	private List<Menu> listaMenu;
	private Menu menu;
	private Rol rol;
	String idRols = null;
	Integer idRol;

	public String getParam() {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("idRol");
	}

	@PostConstruct
	public void init() {
		this.listar();
	}

	public void listar() {
		idRols = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("idRol");
		idRol = Integer.parseInt(idRols);
		rol = serviceRol.mostrarRolPorId(idRol);
		listaMenuRol = serviceMenuRol.listaMenuRolAsignados(idRol);
		listaMenu = serviceMenuRol.listaMenuPendientes(idRol);
	}

	public void quitar(Integer id_menu, Integer id_rol) {
		this.serviceMenuRol.eliminarMenuRol(id_menu, id_rol);
		listaMenuRol = serviceMenuRol.listaMenuRolAsignados(id_rol);
		listaMenu = serviceMenuRol.listaMenuPendientes(id_rol);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Se quita el menú", "Exitósamente"));
	}

	public void asignar(Integer id_menu, Integer id_rol) {
		
		menu = this.serviceMenu.mostraMenu(id_menu);
		rol = this.serviceRol.mostrarRolPorId(id_rol);
		MenuRol menuRol = new MenuRol();
		try {
			menuRol.setMenu(menu);
			menuRol.setRol(rol);
			
			this.serviceMenuRol.registrar(menuRol);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listaMenuRol = serviceMenuRol.listaMenuRolAsignados(id_rol);
		
		listaMenu = serviceMenuRol.listaMenuPendientes(id_rol);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Se asigna el menú", "Exitosamente"));
	}

	//// GETTERS Y SETTERS
	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public List<MenuRol> getListaMenuRol() {
		return listaMenuRol;
	}

	public void setListaMenuRol(List<MenuRol> listaMenuRol) {
		this.listaMenuRol = listaMenuRol;
	}

	public List<Menu> getListaMenu() {
		return listaMenu;
	}

	public void setListaMenu(List<Menu> listaMenu) {
		this.listaMenu = listaMenu;
	}

	public String getIdRols() {
		return idRols;
	}

	public void setIdRols(String idRols) {
		this.idRols = idRols;
	}

	public Integer getIdRol() {
		return idRol;
	}

	public void setIdRol(Integer idRol) {
		this.idRol = idRol;
	}

}
