package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import ec.gob.mdg.dao.IRecaudacionDAO;
import ec.gob.mdg.model.Recaudacion;
import ec.gob.mdg.service.IRecaudacionService;

@Named
public class RecaudacionServiceImpl implements IRecaudacionService, Serializable {
	
	private static final long serialVersionUID = 3164491884281160654L;
	@EJB
	private IRecaudacionDAO dao;

	@Override
	public Integer registrar(Recaudacion t) throws Exception {
		return dao.registrar(t);
	}

	@Override
	public Integer modificar(Recaudacion t) throws Exception {
		return dao.modificar(t);
	}

	@Override
	public Integer eliminar(Recaudacion t) throws Exception {
		return dao.eliminar(t);
	}

	@Override
	public List<Recaudacion> listar() throws Exception {
		return dao.listar();
	}

	@Override
	public Recaudacion listarPorId(Recaudacion t) throws Exception {
		return dao.listarPorId(t);
	}


	
}
