package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import ec.gob.mdg.dao.IParametroDAO;
import ec.gob.mdg.model.Parametro;
import ec.gob.mdg.service.IParametroService;

@Named
public class ParametroServiceImpl implements IParametroService, Serializable {
 
	/**
	 * 
	 */
	private static final long serialVersionUID = 7251911078751047910L;
	@EJB
	private IParametroDAO dao;
	
	@Override
	public Integer registrar(Parametro t) throws Exception {
		return dao.registrar(t);
	}

	@Override
	public Integer modificar(Parametro t) throws Exception {
		return dao.modificar(t);
	}

	@Override
	public Integer eliminar(Parametro t) throws Exception {
		return dao.eliminar(t);
	}

	@Override
	public List<Parametro> listar() throws Exception {
		return dao.listar();
	}

	@Override
	public Parametro listarPorId(Parametro t) throws Exception {
		return dao.listarPorId(t);
	}

}
