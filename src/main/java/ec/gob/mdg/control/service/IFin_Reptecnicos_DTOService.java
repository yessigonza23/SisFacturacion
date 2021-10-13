package ec.gob.mdg.control.service;

import java.util.List;

import ec.gob.mdg.control.model.Fin_Reptecnicos_DTO;
import ec.gob.mdg.service.IService;

public interface IFin_Reptecnicos_DTOService extends IService<Fin_Reptecnicos_DTO> {
	// Listar representantes tecnicos unicos
		List<Fin_Reptecnicos_DTO> listarRepTecnico();

		// Listar representante tecnico por ruc
		List<Fin_Reptecnicos_DTO> listarCalRenRepTecnicoPorIdTec(Integer idtec);

		// Mostrar informacin de representante tecnico
		Fin_Reptecnicos_DTO mostarRepTecnico(Integer idtec);

		// Listar calificacion o renovacion de los representantes para pasar a factura
		List<Fin_Reptecnicos_DTO> listarCalRenRepTecnicoPorIdTecFact(Integer idtec, Integer punto);

		// Listar calificacio renovacion de representante tecnico por codigo de
		// recaudacion para generar detalle de factura
		List<Fin_Reptecnicos_DTO> listarCalRenRepTecnicoPorIdTecRec(Integer idtec, Integer punto);
}
