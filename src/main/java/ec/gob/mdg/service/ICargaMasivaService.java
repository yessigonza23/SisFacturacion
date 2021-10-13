package ec.gob.mdg.service;

import java.util.List;

import ec.gob.mdg.model.CargaMasiva;

public interface ICargaMasivaService extends IService<CargaMasiva> {
	Integer registrarLista(List<CargaMasiva> t);

	// LISTAR CARGA PARA PAF
	List<CargaMasiva> listarCargaMasivaSinComprobante(Integer id_puntorecaudacion, String tipo);

	// ELIMINAR CARGA MASIVA PENDIENTE DE GENERAR COMPROBANTE
	Integer eliminarCargaMasivaPendiente(Integer id_puntorecaudacion, String tipo);

	// VERIFICAR CODIGO PAF QUE NO SE ENCUENTRE FACTURADO
	Integer validarCodigoPaf(String codigoPaf);
}
