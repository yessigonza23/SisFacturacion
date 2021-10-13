package ec.gob.mdg.control.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;

import ec.gob.mdg.control.dao.IFin_Exportaciones_DTODAO;
import ec.gob.mdg.control.model.Fin_Exportaciones_DTO;
import ec.gob.mdg.control.service.IFin_Exportaciones_DTOService;

public class Fin_Exportaciones_DTOServiceImpl implements IFin_Exportaciones_DTOService, Serializable{

	private static final long serialVersionUID = 1L;
	@EJB
	private IFin_Exportaciones_DTODAO dao;
	
	@Override
	public Integer registrar(Fin_Exportaciones_DTO t) throws Exception {
		return null;
	}

	@Override
	public Integer modificar(Fin_Exportaciones_DTO t) throws Exception {
		return null;
	}

	@Override
	public Integer eliminar(Fin_Exportaciones_DTO t) throws Exception {
		return null;
	}

	@Override
	public List<Fin_Exportaciones_DTO> listar() throws Exception {
		return null;
	}

	@Override
	public Fin_Exportaciones_DTO listarPorId(Fin_Exportaciones_DTO t) throws Exception {
		return null;
	}

	@Override
	public List<Fin_Exportaciones_DTO> listarEntidadesExp() {
		return dao.listarEntidadesExp();
	}

	@Override
	public List<Fin_Exportaciones_DTO> listarExportacionesPorCodigo(Integer codigo) {
		return dao.listarExportacionesPorCodigo(codigo);
	}

	@Override
	public Fin_Exportaciones_DTO mostarEntidadExportacion(Integer codigo) {
		return dao.mostarEntidadExportacion(codigo);
	}

	@Override
	public List<Fin_Exportaciones_DTO> listarExportacionesPorCodigoFact(Integer codigo,Integer punto) {
		return dao.listarExportacionesPorCodigoFact(codigo,punto);
	}

	@Override
	public List<Fin_Exportaciones_DTO> listarExportacionesPorCodigoRec(Integer codigo,Integer punto) {
		return dao.listarExportacionesPorCodigoRec(codigo,punto);
	}

}
