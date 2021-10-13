package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import ec.gob.mdg.dao.IProvinciaDAO;
import ec.gob.mdg.model.Provincia;
import ec.gob.mdg.service.IProvinciaService;

@Named
public class ProvinciaServiceImpl  implements IProvinciaService, Serializable{
	
	private static final long serialVersionUID = 1L;
	@EJB
	private IProvinciaDAO dao;
	
	@Override
	public Integer registrar(Provincia t) throws Exception {
		return dao.registrar(t);
	}
	@Override
	public Integer modificar(Provincia t) throws Exception {
		return dao.modificar(t);
	}
	@Override
	public Integer eliminar(Provincia t) throws Exception {
		return dao.eliminar(t);
	}
	@Override
	public List<Provincia> listar() throws Exception {
		return dao.listar();
	}
	@Override
	public Provincia listarPorId(Provincia t) throws Exception {
		return dao.listarPorId(t);
	}


}
