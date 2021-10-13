package ec.gob.mdg.control.service;

import java.util.List;

import ec.gob.mdg.control.model.Fin_Importaciones_No_DTO;
import ec.gob.mdg.service.IService;

public interface IFin_Importaciones_No_DTOService extends IService<Fin_Importaciones_No_DTO> {
	// Listar entidades y codigos para consulta de importaciones de no controlados
		List<Fin_Importaciones_No_DTO> listarEntidadesImpNo();

		// Listar liecencias de importaciones de no controlados por codigo de entidad
		List<Fin_Importaciones_No_DTO> listarImportacionesNoPorCodigo(String codigo);

		// Listar liecencias de importaciones de no controlados por codigo de entidad
		List<Fin_Importaciones_No_DTO> listarImportacionesNoPorCodigoFact(String codigo,Integer punto);

		// Mostrar información de entidad o empresa
		Fin_Importaciones_No_DTO mostrarEntidadImportacionNo(String codigo);
		
		//Listar importaciones de no controlados por codigo de recaudacion para generar detalle de factura
		List<Fin_Importaciones_No_DTO> listarImportacionesNoPorCodigoRec(String codigo,Integer punto);
}
