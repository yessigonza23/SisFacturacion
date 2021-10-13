package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import ec.gob.mdg.dao.IContableDAO;
import ec.gob.mdg.model.Contable;
import ec.gob.mdg.service.IContableService;

@Named
public class ContableServiceImpl implements IContableService,Serializable {


	private static final long serialVersionUID = 5248899828418846136L;
	@EJB
	private IContableDAO dao;
	
	
	@Override
	public Integer registrar(Contable t) throws Exception {
		return dao.registrar(t);
	}

	@Override
	public Integer modificar(Contable t) throws Exception {
		return dao.modificar(t);
	}

	@Override
	public Integer eliminar(Contable t) throws Exception {
		return dao.eliminar(t);
	}


	@Override
	public List<Contable> listar() throws Exception {
		return dao.listar();
	}

	@Override
	public Contable listarPorId(Contable t) throws Exception {
		return dao.listarPorId(t);
	}

	@Override
	public List<Contable> listarContablePorFechas(Date fecha_inicio, Date fecha_fin, Integer id_punto) {
		return dao.listarContablePorFechas(fecha_inicio, fecha_fin, id_punto);
	}

	@Override
	public Contable mostrarContablePorId(Integer id_contable) {
		return dao.mostrarContablePorId(id_contable);
	}
	
	
}
