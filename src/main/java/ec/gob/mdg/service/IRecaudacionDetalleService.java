package ec.gob.mdg.service;

import java.util.List;

import ec.gob.mdg.model.RecaudacionDetalle;

public interface IRecaudacionDetalleService extends IService<RecaudacionDetalle> {
	List<RecaudacionDetalle> listarRecaudacionDetallePorParametro(RecaudacionDetalle rd);

	// Lista las recaudaciones por proceso
	List<RecaudacionDetalle> listarRecaudacionDetallePorProceso(String tipo_proceso);

	// LISTAR RECAUDACION POR PROCESO
	List<RecaudacionDetalle> listarRecaudacionDetallePorProcesoArriendos(String tipo_proceso);

	// MOSTRAR RECAUDACION DETALLE POR RECAUDACION CARGA MASIVA
	RecaudacionDetalle mostrarRecaudacionDetallePorNombre(String nombreRecaudacion);

	// RESPUESTA DE RECAUDACION DETALLE CARGA MASIVA SI EXISTE O NO
	Boolean hayRecaudacionDetallePorNombre(String nombreRecaudacion);

	// MOSTRAR RECAUDACION DETALLE POR CODIGO DEL SERVICIO PARA SERVICIOS CYF
	RecaudacionDetalle mostrarRecaudacionDetallePorCodigo(String codigoServicio);
	
	// LISTAR RECAUDACION POR PROCESO, SIRVE PARA MIGRACION OTROS SERVICIOS
			List<RecaudacionDetalle> listarRecaudacionDetallePorProcesoOtrosServicios(String tipo_proceso, String estado);
}
