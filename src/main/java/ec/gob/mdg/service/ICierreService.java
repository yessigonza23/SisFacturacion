package ec.gob.mdg.service;

import java.util.Date;
import java.util.List;

import ec.gob.mdg.model.Cierre;
import ec.gob.mdg.model.PuntoRecaudacion;

public interface ICierreService extends IService<Cierre> {
	List<Cierre> listarCierreSinContable(PuntoRecaudacion P);

	// Lista de coierres por fecha de inicio y fin y punto de recaudación
	List<Cierre> listarCierrePorFechasPorPto(Date fecha_inicio, Date fecha_fin, Integer id_punto);


	//Mostrar cierre por id
	Cierre mostrarCierrePorId(Integer id_cierre);

}
