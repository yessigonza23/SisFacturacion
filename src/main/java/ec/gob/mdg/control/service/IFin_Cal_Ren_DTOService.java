package ec.gob.mdg.control.service;

import java.util.List;

import ec.gob.mdg.control.model.Fin_Cal_Ren_DTO;

public interface IFin_Cal_Ren_DTOService extends IService<Fin_Cal_Ren_DTO> {
	// Listar entidades y codigos para consulta de calificaciones y renovaciones
	List<Fin_Cal_Ren_DTO> listarEntidades();

	// Listar servicios por codigo de entidad
	List<Fin_Cal_Ren_DTO> listarServiciosPorCodigo(Integer codigo);

	// Mostrar información de entidad
	Fin_Cal_Ren_DTO mostrarEntidad(Integer codigo);
}
