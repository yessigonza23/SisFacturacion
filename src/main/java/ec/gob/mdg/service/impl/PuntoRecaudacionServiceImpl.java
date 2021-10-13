package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import ec.gob.mdg.dao.IPuntoRecaudacionDAO;
import ec.gob.mdg.model.PuntoRecaudacion;
import ec.gob.mdg.service.IPuntoRecaudacionService;

@Named
public class PuntoRecaudacionServiceImpl implements IPuntoRecaudacionService, Serializable{
	
	private static final long serialVersionUID = -8594465617911970998L;
	@EJB
	private IPuntoRecaudacionDAO dao;
	
	@Override
	public Integer registrar(PuntoRecaudacion t) throws Exception {
		return dao.registrar(t);
	}

	@Override
	public Integer modificar(PuntoRecaudacion t) throws Exception {
		return dao.modificar(t);
	}

	@Override
	public Integer eliminar(PuntoRecaudacion t) throws Exception {
		return dao.eliminar(t);
	}

	@Override
	public List<PuntoRecaudacion> listar() throws Exception {
		return dao.listar();
	}

	@Override
	public PuntoRecaudacion listarPorId(PuntoRecaudacion t) throws Exception {
		return dao.listarPorId(t);
	}

	@Override
	public PuntoRecaudacion puntoRecaudacionPorId(Integer id) {
		// TODO Auto-generated method stub
		return dao.puntoRecaudacionPorId(id);
	}

}
