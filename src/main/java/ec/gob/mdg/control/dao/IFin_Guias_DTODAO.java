package ec.gob.mdg.control.dao;

import java.util.List;

import javax.ejb.Local;

import ec.gob.mdg.control.model.Fin_Guias_DTO;
@Local
public interface IFin_Guias_DTODAO extends ICRUD<Fin_Guias_DTO> {

	// Listar entidades y codigos para consulta de importaciones de no controlados
	List<Fin_Guias_DTO> listarEntidadesGuias();

	// Listar liecencias de importaciones de no controlados por codigo de entidad
	List<Fin_Guias_DTO> listarGuiasPorCodigo(Integer codigo);

	// Listar liecencias de importaciones de no controlados por codigo de entidad
	List<Fin_Guias_DTO> listarGuiasPorCodigoFact(Integer codigo,Integer punto);

	// Mostrar información de entidad o empresa
	Fin_Guias_DTO mostrarEntidadGuias(Integer codigo);
	
	//Listar importaciones de no controlados por codigo de recaudacion para generar detalle de factura
	List<Fin_Guias_DTO> listarGuiasPorCodigoRec(Integer codigo,Integer punto);
}
