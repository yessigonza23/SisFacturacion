package ec.gob.mdg.dao;

import java.util.List;

import javax.ejb.Local;

import ec.gob.mdg.model.ContableDetalle;

@Local
public interface IContableDetalleDAO extends ICRUD<ContableDetalle> {
	//LISTAR DETALLE POR ID CONTABLE
	List<ContableDetalle> listarContableDetallePorId(Integer id_contable);
}
