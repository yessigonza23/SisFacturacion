package ec.gob.mdg.dao;

import javax.ejb.Local;

import ec.gob.mdg.model.PuntoRecaudacion;

@Local
public interface IPuntoRecaudacionDAO extends ICRUD<PuntoRecaudacion> {
	//Punto de recaudacion por id
	PuntoRecaudacion puntoRecaudacionPorId(Integer id);
}
