package ec.gob.mdg.dao;

import javax.ejb.Local;

import ec.gob.mdg.model.Institucion;

@Local
public interface IInstitucionDAO extends ICRUD<Institucion> {
	
	Institucion institucionPorPunto(Integer i);
	//DATOS INSTITUCION ACTIVA
	Institucion institucionActiva();

}
