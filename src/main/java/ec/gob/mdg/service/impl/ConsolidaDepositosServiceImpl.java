package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import ec.gob.mdg.dao.IConsolidaDepositosDAO;
import ec.gob.mdg.model.ConsolidaDepositos;
import ec.gob.mdg.service.IConsolidaDepositosService;

@Named
public class ConsolidaDepositosServiceImpl implements IConsolidaDepositosService,Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private IConsolidaDepositosDAO dao;

	@Override
	public Integer registrar(ConsolidaDepositos t) throws Exception {
		return dao.registrar(t);
	}

	@Override
	public Integer modificar(ConsolidaDepositos t) throws Exception {
		return dao.modificar(t);
	}

	@Override
	public Integer eliminar(ConsolidaDepositos t) throws Exception {
		return dao.eliminar(t);
	}

	@Override
	public List<ConsolidaDepositos> listar() throws Exception {
		return dao.listar();
	}

	@Override
	public ConsolidaDepositos listarPorId(ConsolidaDepositos t) throws Exception {
		return dao.listarPorId(t);
	}

	@Override
	public ConsolidaDepositos listarPorId(Integer id) {
		// TODO Auto-generated method stub
		return dao.listarPorId(id);
	}
}
