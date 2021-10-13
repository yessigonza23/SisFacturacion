package ec.gob.mdg.control.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;

import ec.gob.mdg.control.dao.IFin_Importaciones_DTODAO;
import ec.gob.mdg.control.model.Fin_Importaciones_DTO;
import ec.gob.mdg.control.service.IFin_Importaciones_DTOService;

public class Fin_Importaciones_DTOServiceImpl implements IFin_Importaciones_DTOService, Serializable {

	private static final long serialVersionUID = 1L;
	@EJB
	private IFin_Importaciones_DTODAO dao;
	@Override
	public Integer registrar(Fin_Importaciones_DTO t) throws Exception {
		return null;
	}
	@Override
	public Integer modificar(Fin_Importaciones_DTO t) throws Exception {
		return null;
	}
	@Override
	public Integer eliminar(Fin_Importaciones_DTO t) throws Exception {
		return null;
	}
	@Override
	public List<Fin_Importaciones_DTO> listar() throws Exception {
		return null;
	}
	@Override
	public Fin_Importaciones_DTO listarPorId(Fin_Importaciones_DTO t) throws Exception {
		return null;
	}
	@Override
	public List<Fin_Importaciones_DTO> listarEntidadesImp() {
		return dao.listarEntidadesImp();
	}
	@Override
	public List<Fin_Importaciones_DTO> listarImportacionesPorCodigo(Integer codigo) {
		return dao.listarImportacionesPorCodigo(codigo);
	}
	@Override
	public Fin_Importaciones_DTO mostrarEntidadImportacion(Integer codigo) {
		return dao.mostrarEntidadImportacion(codigo);
	}
	@Override
	public List<Fin_Importaciones_DTO> listarImportacionesPorCodigoFact(Integer codigo,Integer punto) {
		return dao.listarImportacionesPorCodigoFact(codigo,punto);
	}
	@Override
	public List<Fin_Importaciones_DTO> listarImportacionesPorCodigoRec(Integer codigo,Integer punto) {
		// TODO Auto-generated method stub
		return dao.listarImportacionesPorCodigoRec(codigo,punto);
	}
	

}
