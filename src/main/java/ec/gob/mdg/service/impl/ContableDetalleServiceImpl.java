package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import ec.gob.mdg.dao.IContableDetalleDAO;
import ec.gob.mdg.model.ContableDetalle;
import ec.gob.mdg.service.IContableDetalleService;

@Named
public class ContableDetalleServiceImpl implements IContableDetalleService,Serializable{

	private static final long serialVersionUID = -431117062786695551L;
	@EJB
	private IContableDetalleDAO dao;
	
	@Override
	public Integer registrar(ContableDetalle t) throws Exception {
		return dao.registrar(t);
	}

	@Override
	public Integer modificar(ContableDetalle t) throws Exception {
		return dao.modificar(t);
	}

	@Override
	public Integer eliminar(ContableDetalle t) throws Exception {
		return dao.eliminar(t);
	}

	@Override
	public List<ContableDetalle> listar() throws Exception {
		return dao.listar();
	}

	@Override
	public ContableDetalle listarPorId(ContableDetalle t) throws Exception {
		return dao.listarPorId(t);
	}

	@Override
	public List<ContableDetalle> listarContableDetallePorId(Integer id_contable) {
		return dao.listarContableDetallePorId(id_contable);
	}

	
}
