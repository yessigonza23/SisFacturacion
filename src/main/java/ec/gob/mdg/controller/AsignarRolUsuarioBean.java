package ec.gob.mdg.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.gob.mdg.model.Rol;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioRol;
import ec.gob.mdg.service.IRolService;
import ec.gob.mdg.service.IUsuarioRolService;
import ec.gob.mdg.service.IUsuarioService;

@Named
@ViewScoped
public class AsignarRolUsuarioBean implements Serializable {

	private static final long serialVersionUID = 7758987860831881860L;

	@Inject
	private IUsuarioService serviceUsuario;

	@Inject
	private IUsuarioRolService serviceUsuarioRol;

	@Inject
	private IRolService serviceRol;

	private Usuario usuario;
	private Rol rol;

	private List<UsuarioRol> listaUsuarioRol;
	private List<Rol> listaRol;

	String idUsuarios;
	Integer idUsuario;

	public String getParam() {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("idUsuario");
	}

	@PostConstruct
	public void init() {
		listar();
	}

	public void listar() {
		idUsuarios = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("idUsuario");
		idUsuario = Integer.parseInt(idUsuarios);
		usuario = serviceUsuario.mostrarUsuarioPorId(idUsuario);		
//		listaUsuarioRol = serviceUsuarioRol.listarRolesPorUsuario(usuario);
//		listaRol = serviceUsuarioRol.listarRolesPendientes(usuario);
	}
	
	public void quitar(Integer id_usuario, Integer id_rol) {		
		usuario = serviceUsuario.mostrarUsuarioPorId(id_usuario);		
//		System.out.println(" id usuario " + id_usuario + " , id rol " + id_rol);
		this.serviceUsuarioRol.eliminarRolUsuario(id_usuario, id_rol);	
		
		listaUsuarioRol = serviceUsuarioRol.listarRolesPorUsuario(usuario);
		listaRol = serviceUsuarioRol.listarRolesPendientes(usuario);

		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Se elimina el rol", "Exitósamente"));
	}

	public void asignar(Integer id_usuario, Integer id_rol) {
//		System.out.println("");
		usuario = serviceUsuario.mostrarUsuarioPorId(id_usuario);
		rol = this.serviceRol.mostrarRolPorId(id_rol);
		UsuarioRol usuarioRol = new UsuarioRol();
		try {
			usuarioRol.setUsuario(usuario);
			usuarioRol.setRol(rol);
			this.serviceUsuarioRol.registrar(usuarioRol);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		listaUsuarioRol = serviceUsuarioRol.listarRolesPorUsuario(usuario);
		listaRol = serviceUsuarioRol.listarRolesPendientes(usuario);
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "Se asigna el rol", "Exitósamente"));
	}

	// Getter y Setters

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getP_idUsuario() {
		return idUsuarios;
	}

	public void setP_idUsuario(String p_idUsuario) {
		this.idUsuarios = p_idUsuario;
	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public List<UsuarioRol> getListaUsuarioRol() {
		listaUsuarioRol = serviceUsuarioRol.listarRolesPorUsuario(usuario);
		return listaUsuarioRol;
	}

	public void setListaUsuarioRol(List<UsuarioRol> listaUsuarioRol) {
		this.listaUsuarioRol = listaUsuarioRol;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public List<Rol> getListaRol() {
		listaRol = serviceUsuarioRol.listarRolesPendientes(usuario);
		return listaRol;
	}

	public void setListaRol(List<Rol> listaRol) {
		this.listaRol = listaRol;
	}

	public String getIdUsuarios() {
		return idUsuarios;
	}

	public void setIdUsuarios(String idUsuarios) {
		this.idUsuarios = idUsuarios;
	}

	
}
