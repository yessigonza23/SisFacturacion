package ec.gob.mdg.dao;

import java.util.List;

import javax.ejb.Local;

import ec.gob.mdg.model.RecaudacionDetalle;

@Local
public interface IRecaudacionDetalleDAO extends ICRUD<RecaudacionDetalle> {
	List<RecaudacionDetalle> listarRecaudacionDetallePorParametro(RecaudacionDetalle rd);

	// LISTAR RECAUDACION POR PROCESO
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
