package ec.gob.mdg.service;

import ec.gob.mdg.model.PuntoRecaudacion;

public interface IPuntoRecaudacionService extends IService<PuntoRecaudacion> {
	// Punto de recaudacion por id
	PuntoRecaudacion puntoRecaudacionPorId(Integer id);
}
