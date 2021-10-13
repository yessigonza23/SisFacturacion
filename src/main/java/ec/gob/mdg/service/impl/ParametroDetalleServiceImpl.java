package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import ec.gob.mdg.dao.IParametroDetalleDAO;
import ec.gob.mdg.model.ParametroDetalle;
import ec.gob.mdg.service.IParametroDetalleService;

@Named
public class ParametroDetalleServiceImpl implements IParametroDetalleService, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8184879313739730050L;
	@EJB
	private IParametroDetalleDAO dao;
	
	@Override
	public Integer registrar(ParametroDetalle t) throws Exception {
		return dao.registrar(t);
	}

	@Override
	public Integer modificar(ParametroDetalle t) throws Exception {
		return dao.modificar(t);
	}

	@Override
	public Integer eliminar(ParametroDetalle t) throws Exception {
		return dao.eliminar(t);
	}

	@Override
	public List<ParametroDetalle> listar() throws Exception {
		return dao.listar();
	}

	@Override
	public ParametroDetalle listarPorId(ParametroDetalle t) throws Exception {
		return dao.listarPorId(t);
	}

	@Override
	public ParametroDetalle mostrarValorDescripcion(String descripcion) {
		return dao.mostrarValorDescripcion(descripcion);
	}

}
