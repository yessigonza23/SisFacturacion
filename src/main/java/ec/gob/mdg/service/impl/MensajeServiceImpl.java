package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import ec.gob.mdg.dao.IMensajeDAO;
import ec.gob.mdg.model.Mensaje;
import ec.gob.mdg.service.IMensajeService;

@Named
public class MensajeServiceImpl implements IMensajeService, Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	private IMensajeDAO dao;
	
	@Override
	public Integer registrar(Mensaje t) throws Exception {
		return dao.registrar(t);
	}

	@Override
	public Integer modificar(Mensaje t) throws Exception {
		return dao.modificar(t);
	}

	@Override
	public Integer eliminar(Mensaje t) throws Exception {
		return dao.eliminar(t);
	}

	@Override
	public List<Mensaje> listar() throws Exception {
		return dao.listar();
	}

	@Override
	public Mensaje listarPorId(Mensaje t) throws Exception {
		return dao.listarPorId(t);
	}

}
