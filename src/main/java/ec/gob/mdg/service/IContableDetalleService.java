package ec.gob.mdg.service;

import java.util.List;

import ec.gob.mdg.model.ContableDetalle;

public interface IContableDetalleService extends IService<ContableDetalle> {
	// LISTAR DETALLE POR ID CONTABLE
	List<ContableDetalle> listarContableDetallePorId(Integer id_contable);
}
