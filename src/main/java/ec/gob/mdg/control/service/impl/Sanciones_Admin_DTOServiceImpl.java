package ec.gob.mdg.control.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;

import ec.gob.mdg.control.dao.ISanciones_Admin_DTODAO;
import ec.gob.mdg.control.model.Sanciones_Admin_DTO;
import ec.gob.mdg.control.service.ISanciones_Admin_DTOService;

public class Sanciones_Admin_DTOServiceImpl implements ISanciones_Admin_DTOService, Serializable{


	private static final long serialVersionUID = 1L;

	@EJB
	private ISanciones_Admin_DTODAO dao;
	
	@Override
	public Integer registrar(Sanciones_Admin_DTO t) throws Exception {
		
		return null;
	}

	@Override
	public Integer modificar(Sanciones_Admin_DTO t) throws Exception {
		
		return null;
	}

	@Override
	public Integer eliminar(Sanciones_Admin_DTO t) throws Exception {
		
		return null;
	}

	@Override
	public List<Sanciones_Admin_DTO> listar() throws Exception {
		
		return null;
	}

	@Override
	public Sanciones_Admin_DTO listarPorId(Sanciones_Admin_DTO t) throws Exception {
		
		return null;
	}

	@Override
	public List<Sanciones_Admin_DTO> listarEntidadesSanciones() {
		return dao.listarEntidadesSanciones();
	}

	@Override
	public List<Sanciones_Admin_DTO> listarSancionesPorCodigo(String ruc) {
		return dao.listarSancionesPorCodigo(ruc);
	}

	@Override
	public Sanciones_Admin_DTO mostrarEntidad(String ruc) {
		return dao.mostrarEntidad(ruc);
	}

	@Override
	public List<Sanciones_Admin_DTO> listarSancionesPorCodigoFact(String ruc,Integer punto) {
		return dao.listarSancionesPorCodigoFact(ruc,punto);
	}

	@Override
	public List<Sanciones_Admin_DTO> listarSancionesPorCodigoFactRec(String ruc,Integer punto) {
		return dao.listarSancionesPorCodigoFactRec(ruc,punto);
	}

}
