package ec.gob.mdg.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DualListModel;

import ec.gob.mdg.model.Rol;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioRol;
import ec.gob.mdg.service.IRolService;
import ec.gob.mdg.service.IUsuarioRolService;
import ec.gob.mdg.service.IUsuarioService;

@Named
@ViewScoped
public class AsignarRolBean implements Serializable{

	private static final long serialVersionUID = -2566791226375242931L;

	@Inject
	private IUsuarioService usuarioService;
	
	@Inject
	private IRolService rolService;
	
	@Inject
	private IUsuarioRolService usuarioRolService;
	
	private List<Usuario> usuarios;
	private List<UsuarioRol> listaUsuarioRol;
	private List<Rol> listaRol;
	public List<UsuarioRol> getListaUsuarioRol() {
		return listaUsuarioRol;
	}

	public void setListaUsuarioRol(List<UsuarioRol> listaUsuarioRol) {
		this.listaUsuarioRol = listaUsuarioRol;
	}

	public List<Rol> getListaRol() {
		return listaRol;
	}

	public void setListaRol(List<Rol> listaRol) {
		this.listaRol = listaRol;
	}

	public UsuarioRol getUsuarioRol() {
		return usuarioRol;
	}

	public void setUsuarioRol(UsuarioRol usuarioRol) {
		this.usuarioRol = usuarioRol;
	}

	private Usuario usuario;
	private DualListModel<Rol> dual_rol ;	 
	private UsuarioRol usuarioRol;

	@PostConstruct
	public void init() {
			this.listarUsuarios();	
			this.listarRoles();
		
	}
	
	public void listarUsuarios() {
		try {
			this.usuarios = this.usuarioService.listar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listarRoles() {
		try {
		    List<Rol> roles = rolService.listar();
			dual_rol = new DualListModel<Rol>(roles,new ArrayList<Rol>());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void listarRolesUsuario() {
		listaUsuarioRol = usuarioRolService.listarRolesPorUsuario(usuario);
	}
	

	public void asignarRoles() {
		try {
			
			Usuario us = new Usuario();
			us.setId(this.usuarioRol.getId());			
			rolService.asignar(us, this.dual_rol.getTarget());
		}catch(Exception e) {	
		}	
		
	}	
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public DualListModel<Rol> getDual_rol() {
		return dual_rol;
	}

	public void setDual_rol(DualListModel<Rol> dual_rol) {
		this.dual_rol = dual_rol;
	}
	
	
	
}
