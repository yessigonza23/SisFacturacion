package ec.gob.mdg.control.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;

import ec.gob.mdg.control.dao.IFin_Cal_Ren_DTODAO;
import ec.gob.mdg.control.model.Fin_Cal_Ren_DTO;
import ec.gob.mdg.control.service.IFin_Cal_Ren_DTOService;

public class Fin_Cal_Ren_DTOServiceImpl implements IFin_Cal_Ren_DTOService, Serializable{
  
	private static final long serialVersionUID = 1L;
	
	@EJB
	private IFin_Cal_Ren_DTODAO dao;

	@Override
	public Integer registrar(Fin_Cal_Ren_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer modificar(Fin_Cal_Ren_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer eliminar(Fin_Cal_Ren_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Fin_Cal_Ren_DTO> listar() throws Exception {
		return dao.listar();
	}

	@Override
	public Fin_Cal_Ren_DTO listarPorId(Fin_Cal_Ren_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Fin_Cal_Ren_DTO> listarEntidades() {
		return dao.listarEntidades();
	}

	@Override
	public List<Fin_Cal_Ren_DTO> listarServiciosPorCodigo(Integer codigo) {
		return dao.listarServiciosPorCodigo(codigo);
	}

	@Override
	public Fin_Cal_Ren_DTO mostrarEntidad(Integer codigo) {
		return dao.mostrarEntidad(codigo);
	}
	
}
