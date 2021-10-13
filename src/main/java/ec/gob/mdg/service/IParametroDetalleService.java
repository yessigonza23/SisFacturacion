package ec.gob.mdg.service;

import ec.gob.mdg.model.ParametroDetalle;

public interface IParametroDetalleService extends IService<ParametroDetalle> {
	//MOSTRAR VALOR SEGUN LA DESCRIPCION
	ParametroDetalle mostrarValorDescripcion(String descripcion);
}
