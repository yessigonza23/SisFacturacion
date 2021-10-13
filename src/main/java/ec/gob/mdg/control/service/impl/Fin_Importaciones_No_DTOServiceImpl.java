package ec.gob.mdg.control.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;

import ec.gob.mdg.control.dao.IFin_Importaciones_No_DTODAO;
import ec.gob.mdg.control.model.Fin_Importaciones_No_DTO;
import ec.gob.mdg.control.service.IFin_Importaciones_No_DTOService;

public class Fin_Importaciones_No_DTOServiceImpl implements IFin_Importaciones_No_DTOService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private IFin_Importaciones_No_DTODAO dao;
	
	@Override
	public Integer registrar(Fin_Importaciones_No_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Integer modificar(Fin_Importaciones_No_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Integer eliminar(Fin_Importaciones_No_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Fin_Importaciones_No_DTO> listar() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Fin_Importaciones_No_DTO listarPorId(Fin_Importaciones_No_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<Fin_Importaciones_No_DTO> listarEntidadesImpNo() {
		return dao.listarEntidadesImpNo();
	}
	@Override
	public List<Fin_Importaciones_No_DTO> listarImportacionesNoPorCodigo(String codigo) {
		return dao.listarImportacionesNoPorCodigo(codigo);
	}
	@Override
	public List<Fin_Importaciones_No_DTO> listarImportacionesNoPorCodigoFact(String codigo, Integer punto) {
		return dao.listarImportacionesNoPorCodigoFact(codigo, punto);
	}
	@Override
	public Fin_Importaciones_No_DTO mostrarEntidadImportacionNo(String codigo) {
		return dao.mostrarEntidadImportacionNo(codigo);
	}
	@Override
	public List<Fin_Importaciones_No_DTO> listarImportacionesNoPorCodigoRec(String codigo, Integer punto) {
		return dao.listarImportacionesNoPorCodigoRec(codigo, punto);
	}


}
