package ec.gob.mdg.control.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;

import ec.gob.mdg.control.dao.IFin_Reptecnicos_DTODAO;
import ec.gob.mdg.control.model.Fin_Reptecnicos_DTO;
import ec.gob.mdg.control.service.IFin_Reptecnicos_DTOService;

public class Fin_Reptecnicos_DTOServiceImpl implements IFin_Reptecnicos_DTOService, Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	private IFin_Reptecnicos_DTODAO dao;

	@Override
	public Integer registrar(Fin_Reptecnicos_DTO t) throws Exception {
		return null;
	}

	@Override
	public Integer modificar(Fin_Reptecnicos_DTO t) throws Exception {
		return null;
	}

	@Override
	public Integer eliminar(Fin_Reptecnicos_DTO t) throws Exception {
		return null;
	}

	@Override
	public List<Fin_Reptecnicos_DTO> listar() throws Exception {
		return null;
	}

	@Override
	public Fin_Reptecnicos_DTO listarPorId(Fin_Reptecnicos_DTO t) throws Exception {
		return null;
	}

	@Override
	public List<Fin_Reptecnicos_DTO> listarRepTecnico() {
		return dao.listarRepTecnico();
	}

	@Override
	public List<Fin_Reptecnicos_DTO> listarCalRenRepTecnicoPorIdTec(Integer idtec) {
		return dao.listarCalRenRepTecnicoPorIdTec(idtec);
	}

	@Override
	public Fin_Reptecnicos_DTO mostarRepTecnico(Integer idtec) {
		return dao.mostarRepTecnico(idtec);
	}

	@Override
	public List<Fin_Reptecnicos_DTO> listarCalRenRepTecnicoPorIdTecFact(Integer idtec, Integer punto) {
		return dao.listarCalRenRepTecnicoPorIdTecFact(idtec, punto);
	}

	@Override
	public List<Fin_Reptecnicos_DTO> listarCalRenRepTecnicoPorIdTecRec(Integer idtec, Integer punto) {
		return dao.listarCalRenRepTecnicoPorIdTecRec(idtec, punto);
	}

}
