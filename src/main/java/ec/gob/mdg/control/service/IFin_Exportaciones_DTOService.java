package ec.gob.mdg.control.service;

import java.util.List;

import ec.gob.mdg.control.model.Fin_Exportaciones_DTO;

public interface IFin_Exportaciones_DTOService extends IService<Fin_Exportaciones_DTO> {

	// Listar entidades y codigos para consulta de exportaciones
	List<Fin_Exportaciones_DTO> listarEntidadesExp();

	// Listar liecencias de exportación por codigo de entidad
	List<Fin_Exportaciones_DTO> listarExportacionesPorCodigo(Integer codigo);

	// Mostrar información de entidad o empresa
	Fin_Exportaciones_DTO mostarEntidadExportacion(Integer codigo);
	
	
	// Listar licencias de exportaciones por codigo de entidad
	List<Fin_Exportaciones_DTO> listarExportacionesPorCodigoFact(Integer codigo,Integer punto);
	
	// Listar exportaciones por el codigo de recaudacion para generar detalle de
	// facturas
	List<Fin_Exportaciones_DTO> listarExportacionesPorCodigoRec(Integer codigo,Integer punto);

}
