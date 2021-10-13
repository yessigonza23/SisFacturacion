package ec.gob.mdg.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import ec.gob.mdg.model.Cierre;
import ec.gob.mdg.model.PuntoRecaudacion;

@Local
public interface ICierreDAO extends ICRUD<Cierre> {
	List<Cierre> listarCierreSinContable(PuntoRecaudacion P);

	// Lista de coierres por fecha de inicio y fin y punto de recaudacion
	List<Cierre> listarCierrePorFechasPorPto(Date fecha_inicio, Date fecha_fin, Integer id_punto);
	
	//Mostrar cierre por id
	Cierre mostrarCierrePorId(Integer id_cierre);

}
