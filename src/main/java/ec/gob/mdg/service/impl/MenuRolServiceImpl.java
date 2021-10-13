package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;

import ec.gob.mdg.dao.IMenuRolDAO;
import ec.gob.mdg.model.Menu;
import ec.gob.mdg.model.MenuRol;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.service.IMenuRolService;

public class MenuRolServiceImpl implements IMenuRolService, Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private IMenuRolDAO dao;
	
	@Override
	public Integer registrar(MenuRol t) throws Exception {
		// TODO Auto-generated method stub
		return dao.registrar(t);
	}

	@Override
	public Integer modificar(MenuRol t) throws Exception {
		// TODO Auto-generated method stub
		return dao.modificar(t);
	}

	@Override
	public Integer eliminar(MenuRol t) throws Exception {
		// TODO Auto-generated method stub
		return dao.eliminar(t);
	}

	@Override
	public List<MenuRol> listar() throws Exception {
		// TODO Auto-generated method stub
		return dao.listar();
	}

	@Override
	public MenuRol listarPorId(MenuRol t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Menu> listaMenuRolporUsuario(Usuario u) {
		// TODO Auto-generated method stub
		return dao.listaMenuRolporUsuario(u);
	}

	@Override
	public List<MenuRol> listaMenuRolAsignados(Integer idRol) {
		return dao.listaMenuRolAsignados(idRol);
	}

	@Override
	public List<Menu> listaMenuPendientes(Integer idRol) {
		return dao.listaMenuPendientes(idRol);
	}
	
	@Override
	public Integer eliminarMenuRol(Integer id_menu, Integer id_rol) {
		return dao.eliminarMenuRol(id_menu, id_rol);
	}

}
