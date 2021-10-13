package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;

import ec.gob.mdg.dao.ICargaMasivaDAO;
import ec.gob.mdg.model.CargaMasiva;
import ec.gob.mdg.service.ICargaMasivaService;

public class CargaMasivaServiceImpl implements ICargaMasivaService, Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private ICargaMasivaDAO dao;
	
	@Override
	public Integer registrar(CargaMasiva t) throws Exception {
		return dao.registrar(t);
	}

	@Override
	public Integer modificar(CargaMasiva t) throws Exception {
		return dao.modificar(t);
	}

	@Override
	public Integer eliminar(CargaMasiva t) throws Exception {
		return dao.eliminar(t);
	}
	
	@Override
	public List<CargaMasiva> listar() throws Exception {
		return dao.listar();
	}

	@Override
	public CargaMasiva listarPorId(CargaMasiva t) throws Exception {
		return dao.listarPorId(t);
	}

	@Override
	public Integer registrarLista(List<CargaMasiva> t) {
		// TODO Auto-generated method stub
		return dao.registrarLista(t);
	}

	@Override
	public List<CargaMasiva> listarCargaMasivaSinComprobante(Integer id_puntorecaudacion,String tipo) {
		return dao.listarCargaMasivaSinComprobante(id_puntorecaudacion,tipo);
	}

	@Override
	public Integer eliminarCargaMasivaPendiente(Integer id_puntorecaudacion, String tipo) {
		return dao.eliminarCargaMasivaPendiente(id_puntorecaudacion, tipo);
	}

	@Override
	public Integer validarCodigoPaf(String codigoPaf) {
		return dao.validarCodigoPaf(codigoPaf);
	}
	
	

}
