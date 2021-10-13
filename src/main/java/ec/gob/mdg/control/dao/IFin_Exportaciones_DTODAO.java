package ec.gob.mdg.control.dao;

import java.util.List;

import javax.ejb.Local;

import ec.gob.mdg.control.model.Fin_Exportaciones_DTO;

@Local
public interface IFin_Exportaciones_DTODAO extends ICRUD<Fin_Exportaciones_DTO> {

	// Listar entidades y codigos para consulta de exportaciones
	List<Fin_Exportaciones_DTO> listarEntidadesExp();

	// Listar liecencias de exportación por codigo de entidad
	List<Fin_Exportaciones_DTO> listarExportacionesPorCodigo(Integer codigo);

	// Mostrar información de entidad o empresa
	Fin_Exportaciones_DTO mostarEntidadExportacion(Integer codigo);

	// Listar liecencias de exportaciones por codigo de entidad
	List<Fin_Exportaciones_DTO> listarExportacionesPorCodigoFact(Integer codigo,Integer punto);

	// Listar exportaciones por codigo de recaudacion para generar detalle de factura
	List<Fin_Exportaciones_DTO> listarExportacionesPorCodigoRec(Integer codigo,Integer punto);
}
