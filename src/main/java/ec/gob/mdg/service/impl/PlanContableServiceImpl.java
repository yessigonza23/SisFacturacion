package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import ec.gob.mdg.dao.IPlanContableDAO;
import ec.gob.mdg.model.PlanContable;
import ec.gob.mdg.service.IPlanContableService;

@Named
public class PlanContableServiceImpl implements IPlanContableService,Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private IPlanContableDAO dao;
		
	@Override
	public Integer registrar(PlanContable t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer modificar(PlanContable t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer eliminar(PlanContable t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PlanContable> listar() throws Exception {
		return dao.listar();
	}

	@Override
	public PlanContable listarPorId(PlanContable t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PlanContable muestraPlanContable(String codigo_contable) {
		return dao.muestraPlanContable(codigo_contable);
	}

	@Override
	public PlanContable cargaPlanContable(Integer id_plancontable) {
		return dao.cargaPlanContable(id_plancontable);
	}

}
