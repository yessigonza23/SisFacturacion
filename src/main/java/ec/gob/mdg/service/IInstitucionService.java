package ec.gob.mdg.service;

import ec.gob.mdg.model.Institucion;

public interface IInstitucionService extends IService<Institucion> {

	Institucion institucionPorPunto(Integer i);

	// DATOS INSTITUCION ACTIVA
	Institucion institucionActiva();

}
