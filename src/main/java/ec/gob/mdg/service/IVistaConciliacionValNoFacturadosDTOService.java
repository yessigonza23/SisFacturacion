package ec.gob.mdg.service;

import java.util.List;

import ec.gob.mdg.model.VistaConciliacionValNoFacturadosDTO;

public interface IVistaConciliacionValNoFacturadosDTOService extends IService<VistaConciliacionValNoFacturadosDTO> {
	// Reporte para Conciliaci�n VALORES QUE NO ESTAN FACTURADOS
	List<VistaConciliacionValNoFacturadosDTO> listarValoresNoFacturados(Integer anio, Integer mes);
}
