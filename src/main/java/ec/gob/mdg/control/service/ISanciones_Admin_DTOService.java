package ec.gob.mdg.control.service;

import java.util.List;

import ec.gob.mdg.control.model.Sanciones_Admin_DTO;

public interface ISanciones_Admin_DTOService extends IService<Sanciones_Admin_DTO> {

	// Listar entidades y ruc con sanciones y coactivas
	List<Sanciones_Admin_DTO> listarEntidadesSanciones();

	// Listar servicios por codigo de entidad
	List<Sanciones_Admin_DTO> listarSancionesPorCodigo(String ruc);

	// Mostrar información de entidad o empresa
	Sanciones_Admin_DTO mostrarEntidad(String ruc);

	// Listar liecencias de sanciones por ruc de empresa
	List<Sanciones_Admin_DTO> listarSancionesPorCodigoFact(String ruc,Integer punto);

	// Listar liecencias de sanciones por ruc de empresas ya seleccionados
	List<Sanciones_Admin_DTO> listarSancionesPorCodigoFactRec(String ruc,Integer punto);

}
