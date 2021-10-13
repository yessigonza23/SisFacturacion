package ec.gob.mdg.dao;

import javax.ejb.Local;

import ec.gob.mdg.model.ParametroDetalle;

@Local
public interface IParametroDetalleDAO extends ICRUD<ParametroDetalle> {
	//MOSTRAR VALOR SEGUN LA DESCRIPCION
	ParametroDetalle mostrarValorDescripcion(String descripcion);
}
