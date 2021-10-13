package ec.gob.mdg.service;

import java.util.List;

import ec.gob.mdg.model.ComprobanteDetalle;

public interface IComprobanteDetalleService extends IService<ComprobanteDetalle> {
	List<ComprobanteDetalle> listarComDetPorIdComp(Integer c);

	/// listar detalle para notas de cr�dito autom�ticas
	List<ComprobanteDetalle> listarComDetPorPtoNumComp(Integer id_punto, Integer numero);

	// ELIMINAR CARGA MASIVA PENDIENTE DE GENERAR COMPROBANTE
	Integer eliminarComprobanteDetalle(Integer id);

}
