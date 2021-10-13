package ec.gob.mdg.control.dao;

import java.util.List;

import javax.ejb.Local;

import ec.gob.mdg.control.model.Fin_Reptecnicos_DTO;

@Local
public interface IFin_Reptecnicos_DTODAO extends ICRUD<Fin_Reptecnicos_DTO> {
	// Listar representantes tecnicos unicos
		List<Fin_Reptecnicos_DTO> listarRepTecnico();

		// Listar representante tecnico por ruc
		List<Fin_Reptecnicos_DTO> listarCalRenRepTecnicoPorIdTec(Integer idtec);

		// Mostrar información de representante tecnico
		Fin_Reptecnicos_DTO mostarRepTecnico(Integer idtec);

		// Listar calificacion o renovacion de los representantes para pasar a factura
		List<Fin_Reptecnicos_DTO> listarCalRenRepTecnicoPorIdTecFact(Integer idtec, Integer punto);

		// Listar calificacio renovacion de representante tecnico por codigo de
		// recaudacion para generar detalle de factura
		List<Fin_Reptecnicos_DTO> listarCalRenRepTecnicoPorIdTecRec(Integer idtec, Integer punto);
}
