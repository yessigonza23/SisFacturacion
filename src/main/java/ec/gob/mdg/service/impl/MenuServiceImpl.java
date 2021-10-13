package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;

import ec.gob.mdg.dao.IMenuDAO;
import ec.gob.mdg.model.Menu;
import ec.gob.mdg.service.IMenuService;

public class MenuServiceImpl implements IMenuService, Serializable {

	private static final long serialVersionUID = -1705606622147278320L;

	@EJB
	private IMenuDAO dao;
	
	@Override
	public Integer registrar(Menu t) throws Exception {
		// TODO Auto-generated method stub
		return dao.registrar(t);
	}

	@Override
	public Integer modificar(Menu t) throws Exception {
		// TODO Auto-generated method stub
		return dao.modificar(t);
	}

	@Override
	public Integer eliminar(Menu t) throws Exception {
		// TODO Auto-generated method stub
		return dao.eliminar(t);
	}

	@Override
	public List<Menu> listar() throws Exception {
		// TODO Auto-generated method stub
		return dao.listar();
	}

	@Override
	public Menu listarPorId(Menu t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Menu mostraMenu(Integer id_menu) {
		return dao.mostraMenu(id_menu);
	}

}
