package ec.gob.mdg.control.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;

import ec.gob.mdg.control.dao.IFin_Guias_DTODAO;
import ec.gob.mdg.control.model.Fin_Guias_DTO;
import ec.gob.mdg.control.service.IFin_Guias_DTOService;

public class Fin_Guias_DTOServiceImpl implements IFin_Guias_DTOService, Serializable{

	private static final long serialVersionUID = 1L;
	
	@EJB
	private IFin_Guias_DTODAO dao;

	@Override
	public Integer registrar(Fin_Guias_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer modificar(Fin_Guias_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer eliminar(Fin_Guias_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Fin_Guias_DTO> listar() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Fin_Guias_DTO listarPorId(Fin_Guias_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Fin_Guias_DTO> listarEntidadesGuias() {
		return dao.listarEntidadesGuias();
	}

	@Override
	public List<Fin_Guias_DTO> listarGuiasPorCodigo(Integer codigo) {
		return dao.listarGuiasPorCodigo(codigo);
	}

	@Override
	public List<Fin_Guias_DTO> listarGuiasPorCodigoFact(Integer codigo, Integer punto) {
		return dao.listarGuiasPorCodigoFact(codigo, punto);
	}

	@Override
	public Fin_Guias_DTO mostrarEntidadGuias(Integer codigo) {
		return dao.mostrarEntidadGuias(codigo);
	}

	@Override
	public List<Fin_Guias_DTO> listarGuiasPorCodigoRec(Integer codigo, Integer punto) {
		return dao.listarGuiasPorCodigoRec(codigo, punto);
	}
	
	
}
