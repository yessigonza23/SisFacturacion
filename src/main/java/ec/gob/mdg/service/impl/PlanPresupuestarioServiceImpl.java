package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;

import ec.gob.mdg.dao.IPlanPresupuestarioDAO;
import ec.gob.mdg.model.PlanPresupuestario;
import ec.gob.mdg.service.IPlanPresupuestarioService;

public class PlanPresupuestarioServiceImpl implements IPlanPresupuestarioService, Serializable{
	private static final long serialVersionUID = 1L;

	@EJB
	private IPlanPresupuestarioDAO dao;

	@Override
	public Integer registrar(PlanPresupuestario t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer modificar(PlanPresupuestario t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer eliminar(PlanPresupuestario t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PlanPresupuestario> listar() throws Exception {
		// TODO Auto-generated method stub
		return dao.listar();
	}

	@Override
	public PlanPresupuestario listarPorId(PlanPresupuestario t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
