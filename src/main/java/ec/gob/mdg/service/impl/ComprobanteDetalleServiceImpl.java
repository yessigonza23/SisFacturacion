package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import ec.gob.mdg.dao.IComprobanteDetalleDAO;
import ec.gob.mdg.model.ComprobanteDetalle;
import ec.gob.mdg.service.IComprobanteDetalleService;

@Named
public class ComprobanteDetalleServiceImpl implements IComprobanteDetalleService,Serializable{

	private static final long serialVersionUID = 1L;
	
	@EJB
	private IComprobanteDetalleDAO dao;
	
	@Override
	public Integer registrar(ComprobanteDetalle t) throws Exception {
		return dao.registrar(t);
	}

	@Override
	public Integer modificar(ComprobanteDetalle t) throws Exception {
		return dao.modificar(t);
	}

	@Override
	public Integer eliminar(ComprobanteDetalle t) throws Exception {
		return dao.eliminar(t);
	}

	@Override
	public List<ComprobanteDetalle> listar() throws Exception {
		return dao.listar();
	}

	@Override
	public ComprobanteDetalle listarPorId(ComprobanteDetalle t) throws Exception {
		return dao.listarPorId(t);
	}

	@Override
	public List<ComprobanteDetalle> listarComDetPorIdComp(Integer c) {
		return dao.listarComDetPorIdComp(c);	
	}

	@Override
	public List<ComprobanteDetalle> listarComDetPorPtoNumComp(Integer id_punto, Integer numero) {
		return dao.listarComDetPorPtoNumComp(id_punto, numero);
	}

	@Override
	public Integer eliminarComprobanteDetalle(Integer id) {
		// TODO Auto-generated method stub
		return dao.eliminarComprobanteDetalle(id);
	}

}
