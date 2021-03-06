package ec.gob.mdg.control.dao;

import java.util.List;

import javax.ejb.Local;

import ec.gob.mdg.control.model.Fin_Cat_Act_Sit_Veh_Java_DTO;

@Local
public interface IFin_Cat_Act_Sit_Veh_Java_DTODAO extends ICRUD<Fin_Cat_Act_Sit_Veh_Java_DTO> {
	// Listar entidades y codigos para consulta de ampliaciones o cambio de
	// categoria
	List<Fin_Cat_Act_Sit_Veh_Java_DTO> listarEntidades();

	//Listar servicios por codigo de entidad
	List<Fin_Cat_Act_Sit_Veh_Java_DTO> listarServiciosPorCodigo(Integer codigo);
	
	//Mostrar información de entidad
    Fin_Cat_Act_Sit_Veh_Java_DTO mostrarEntidad(Integer codigo);

}
