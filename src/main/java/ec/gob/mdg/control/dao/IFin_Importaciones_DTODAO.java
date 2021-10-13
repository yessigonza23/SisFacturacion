package ec.gob.mdg.control.dao;

import java.util.List;

import javax.ejb.Local;

import ec.gob.mdg.control.model.Fin_Importaciones_DTO;

@Local
public interface IFin_Importaciones_DTODAO extends ICRUD<Fin_Importaciones_DTO> {

	// Listar entidades y codigos para consulta de importaciones
	List<Fin_Importaciones_DTO> listarEntidadesImp();

	// Listar liecencias de importaciones por codigo de entidad
	List<Fin_Importaciones_DTO> listarImportacionesPorCodigo(Integer codigo);

	// Listar liecencias de importaciones por codigo de entidad
	List<Fin_Importaciones_DTO> listarImportacionesPorCodigoFact(Integer codigo,Integer punto);

	// Mostrar información de entidad o empresa
	Fin_Importaciones_DTO mostrarEntidadImportacion(Integer codigo);
	
	//Listar importaciones por codigo de recaudacion para generar detalle de factura
	List<Fin_Importaciones_DTO> listarImportacionesPorCodigoRec(Integer codigo,Integer punto);

}
