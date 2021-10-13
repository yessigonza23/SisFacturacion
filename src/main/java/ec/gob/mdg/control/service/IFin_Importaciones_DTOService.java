package ec.gob.mdg.control.service;

import java.util.List;

import ec.gob.mdg.control.model.Fin_Importaciones_DTO;
import ec.gob.mdg.service.IService;

public interface IFin_Importaciones_DTOService extends IService<Fin_Importaciones_DTO> {

	// Listar entidades y codigos para consulta de importaciones
	List<Fin_Importaciones_DTO> listarEntidadesImp();

	// Listar liecencias de importaciones por codigo de entidad
	List<Fin_Importaciones_DTO> listarImportacionesPorCodigo(Integer codigo);

	// Listar liecencias de importaciones por codigo de entidad
	List<Fin_Importaciones_DTO> listarImportacionesPorCodigoFact(Integer codigo,Integer punto);

	// Mostrar información de entidad o empresa
	Fin_Importaciones_DTO mostrarEntidadImportacion(Integer codigo);

	// Listar importaciones por codigo de recaudacion para generar detalle de factura
	List<Fin_Importaciones_DTO> listarImportacionesPorCodigoRec(Integer codigo,Integer punto);

}
