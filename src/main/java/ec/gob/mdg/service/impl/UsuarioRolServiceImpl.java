package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import ec.gob.mdg.dao.IUsuarioRolDAO;
import ec.gob.mdg.model.Rol;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioRol;
import ec.gob.mdg.service.IUsuarioRolService;

@Named
public class UsuarioRolServiceImpl implements IUsuarioRolService, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8277418286815840083L;
	@EJB
	private IUsuarioRolDAO dao;
	
	@Override
	public Integer registrar(UsuarioRol t) throws Exception {
		return dao.registrar(t);
	}

	@Override
	public Integer modificar(UsuarioRol t) throws Exception {
		return dao.modificar(t);
	}

	@Override
	public Integer eliminar(UsuarioRol t) throws Exception {
		return dao.eliminar(t);
	}

	@Override
	public List<UsuarioRol> listar() throws Exception {
		return dao.listar();
	}

	@Override
	public UsuarioRol listarPorId(UsuarioRol t) throws Exception {
		return dao.listarPorId(t);
	}

	@Override
	public List<UsuarioRol> listarRolesPorUsuario(Usuario us) {
		return dao.listarRolesPorUsuario(us);
	}

	@Override
	public List<Rol> listarRolesPendientes(Usuario us) {
		// TODO Auto-generated method stub
		return dao.listarRolesPendientes(us);
	}
	
	@Override
	public Integer eliminarRolUsuario(Integer id_usuario, Integer id_rol) {
		return dao.eliminarRolUsuario(id_usuario, id_rol);
	}

}
