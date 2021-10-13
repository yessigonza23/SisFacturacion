package ec.gob.mdg.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import ec.gob.mdg.dao.IMenuDAO;
import ec.gob.mdg.model.Menu;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioRol;
import ec.gob.mdg.service.IMenuRolService;
import lombok.Data;

@Data
@Named
@SessionScoped
public class MenuBean implements Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private IMenuDAO MenuDao;
	
	
	@Inject
	private IMenuRolService servicioMenuRol;
	
	Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	
	private List<Menu> listaMenu;
	private MenuModel model;
	
	private List<UsuarioRol> listaUsuarioRol;
	
	@PostConstruct
	public void init() {
		
		this.listarMenus();
		model = new DefaultMenuModel();
		this.establecerPermisos();
	}
	
	public void listarMenus() {
		try {
			listaMenu = servicioMenuRol.listaMenuRolporUsuario(us);
		} catch (Exception e) { 
			e.printStackTrace();
		}
	}
	
	
	
	public void establecerPermisos() {

	        for (Menu m : listaMenu) {
	        	if (m.getTipo().equals("S")) {
	                DefaultSubMenu firstSubmenu = new DefaultSubMenu(m.getNombre());

	                for (Menu i : listaMenu) {
	                	Menu submenu = i.getSubmenu();
	                    if (submenu != null) {
	                    	if (submenu.getId() == m.getId()) {
	                        	DefaultMenuItem item = new DefaultMenuItem(i.getNombre());
	                            item.setUrl(i.getLink());
	                            firstSubmenu.addElement(item);
	                        }
	                    }
	                }	                
	                model.addElement(firstSubmenu);
	              } 
	        	else {
	            	if (m.getSubmenu() == null) {
	                    DefaultMenuItem item = new DefaultMenuItem(m.getNombre());
	                    item.setUrl(m.getLink());
	                    model.addElement(item);
	                }
	            }
	            
	        }
	}

	public IMenuDAO getMenuDao() {
		return MenuDao;
	}

	public void setMenuDao(IMenuDAO menuDao) {
		MenuDao = menuDao;
	}

	public Usuario getUs() {
		return us;
	}

	public void setUs(Usuario us) {
		this.us = us;
	}

	public List<Menu> getListaMenu() {
		return listaMenu;
	}

	public void setListaMenu(List<Menu> listaMenu) {
		this.listaMenu = listaMenu;
	}

	public List<UsuarioRol> getListaUsuarioRol() {
		return listaUsuarioRol;
	}

	public void setListaUsuarioRol(List<UsuarioRol> listaUsuarioRol) {
		this.listaUsuarioRol = listaUsuarioRol;
	}

	public MenuModel getModel() {
		return model;
	}

	public void setModel(MenuModel model) {
		this.model = model;
	}
 

}
