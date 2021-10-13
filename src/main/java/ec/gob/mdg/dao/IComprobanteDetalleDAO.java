package ec.gob.mdg.dao;

import java.util.List;

import javax.ejb.Local;
import ec.gob.mdg.model.ComprobanteDetalle;

@Local
public interface IComprobanteDetalleDAO extends ICRUD<ComprobanteDetalle> {
	List<ComprobanteDetalle> listarComDetPorIdComp(Integer c);

	List<ComprobanteDetalle> listarComDetPorPtoNumComp(Integer id_punto, Integer numero);

	//ELIMINAR CARGA MASIVA PENDIENTE DE GENERAR COMPROBANTE
	Integer eliminarComprobanteDetalle(Integer id);
	
	
}
