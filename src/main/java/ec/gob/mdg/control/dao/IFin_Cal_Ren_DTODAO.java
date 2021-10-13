package ec.gob.mdg.control.dao;

import java.util.List;

import javax.ejb.Local;

import ec.gob.mdg.control.model.Fin_Cal_Ren_DTO;

@Local
public interface IFin_Cal_Ren_DTODAO extends ICRUD<Fin_Cal_Ren_DTO> {
	// Listar entidades y codigos para consulta de calificaciones y renovaciones
	List<Fin_Cal_Ren_DTO> listarEntidades();

	// Listar servicios por codigo de entidad
	List<Fin_Cal_Ren_DTO> listarServiciosPorCodigo(Integer codigo);

	// Mostrar información de entidad
	Fin_Cal_Ren_DTO mostrarEntidad(Integer codigo);
}
