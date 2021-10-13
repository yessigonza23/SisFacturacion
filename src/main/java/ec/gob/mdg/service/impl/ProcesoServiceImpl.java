package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import ec.gob.mdg.dao.IProcesoDAO;
import ec.gob.mdg.model.Proceso;
import ec.gob.mdg.service.IProcesoService;

@Named
public class ProcesoServiceImpl implements IProcesoService, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6188996486744030313L;
	@EJB
	private IProcesoDAO dao;
	
	@Override
	public Integer registrar(Proceso t) throws Exception {
		return dao.registrar(t);
	}

	@Override
	public Integer modificar(Proceso t) throws Exception {
		return dao.modificar(t);
	}

	@Override
	public Integer eliminar(Proceso t) throws Exception {
		return dao.eliminar(t);
	}

	@Override
	public List<Proceso> listar() throws Exception {
		return dao.listar();
	}

	@Override
	public Proceso listarPorId(Proceso t) throws Exception {
		return dao.listarPorId(t);
	}

}
