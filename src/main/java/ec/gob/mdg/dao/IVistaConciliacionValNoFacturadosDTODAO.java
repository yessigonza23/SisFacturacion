package ec.gob.mdg.dao;

import java.util.List;

import javax.ejb.Local;

import ec.gob.mdg.control.dao.ICRUD;
import ec.gob.mdg.model.VistaConciliacionValNoFacturadosDTO;

@Local
public interface IVistaConciliacionValNoFacturadosDTODAO extends ICRUD<VistaConciliacionValNoFacturadosDTO> {
	// Reporte para Conciliacion VALORES QUE NO ESTAN FACTURADOS
	List<VistaConciliacionValNoFacturadosDTO> listarValoresNoFacturados(Integer anio, Integer mes);
}
