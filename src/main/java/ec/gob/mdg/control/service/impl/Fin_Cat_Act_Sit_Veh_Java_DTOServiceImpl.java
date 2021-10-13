package ec.gob.mdg.control.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;

import ec.gob.mdg.control.dao.IFin_Cat_Act_Sit_Veh_Java_DTODAO;
import ec.gob.mdg.control.model.Fin_Cat_Act_Sit_Veh_Java_DTO;
import ec.gob.mdg.control.service.IFin_Cat_Act_Sit_Veh_Java_DTOService;

public class Fin_Cat_Act_Sit_Veh_Java_DTOServiceImpl implements IFin_Cat_Act_Sit_Veh_Java_DTOService, Serializable{

	private static final long serialVersionUID = 1L;
	
	@EJB
	private IFin_Cat_Act_Sit_Veh_Java_DTODAO dao;

	@Override
	public Integer registrar(Fin_Cat_Act_Sit_Veh_Java_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer modificar(Fin_Cat_Act_Sit_Veh_Java_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer eliminar(Fin_Cat_Act_Sit_Veh_Java_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Fin_Cat_Act_Sit_Veh_Java_DTO> listar() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fin_Cat_Act_Sit_Veh_Java_DTO listarPorId(Fin_Cat_Act_Sit_Veh_Java_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Fin_Cat_Act_Sit_Veh_Java_DTO> listarEntidades() {
		return dao.listarEntidades();
	}

	@Override
	public List<Fin_Cat_Act_Sit_Veh_Java_DTO> listarServiciosPorCodigo(Integer codigo) {
		return dao.listarServiciosPorCodigo(codigo);
	}

	@Override
	public Fin_Cat_Act_Sit_Veh_Java_DTO mostrarEntidad(Integer codigo) {
		return dao.mostrarEntidad(codigo);
	}
	

}
