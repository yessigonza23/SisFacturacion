package ec.gob.mdg.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import ec.gob.mdg.control.dao.ICRUD;
import ec.gob.mdg.model.VistaRecaudacionDTO;

@Local
public interface IVistaRecaudacionDTODAO extends ICRUD<VistaRecaudacionDTO> {
	// Reporte recaudación para xls con detalle por Punto y fechas
	List<VistaRecaudacionDTO> listarRecaudacionDetalle(Date fecha_inicio, Date fecha_fin, Integer id_punto);

	// Contador de facturas en la vista
	Integer cuentaFacturas(Date fecha_inicio, Date fecha_fin, Integer id_punto);

	// Reporte recaudación para xls con detalle Nacional y fechas
	List<VistaRecaudacionDTO> listarRecaudacionDetalleNac(Date fecha_inicio, Date fecha_fin, String proceso_tipo);

	// Contador de facturas en la vista Nacional
	Integer cuentaFacturasNac(Date fecha_inicio, Date fecha_fin, String proceso_tipo);

	// Listar recaudaciones anuladas Nacional por fechas
	List<VistaRecaudacionDTO> listarRecaudacionAnuladasNac(Date fecha_inicio, Date fecha_fin, String proceso_tipo);

	// Contador de facturas anuladas en la vista Nacional
	Integer cuentaFacturasAnuladasNac(Date fecha_inicio, Date fecha_fin, String proceso_tipo);

	// Listar recaudaciones no autorizadas Nacional por fechas
	List<VistaRecaudacionDTO> listarRecaudacionNoAutorizadasNac(Date fecha_inicio, Date fecha_fin, String proceso_tipo);

	// Contador de facturas no autorizadas en la vista Nacional
	Integer cuentaFacturasNoAutorizadasNac(Date fecha_inicio, Date fecha_fin, String proceso_tipo);

	// Listar recaudaciones sin cierre Nacional por fechas
	List<VistaRecaudacionDTO> listarRecaudacionSinCierre(Date fecha_inicio, Date fecha_fin, String proceso_tipo);

	// Listar recaudaciones Diferente clave de acceso, autorizacion
	List<VistaRecaudacionDTO> listarRecaudacioDif(Date fecha_inicio, Date fecha_fin, String proceso_tipo);

	// listar los comprobantes para generar el contable
	List<VistaRecaudacionDTO> listarComprobantesPorCierreId(Integer id_cierre);

	// listar los comprobantes para generar el contable
	List<VistaRecaudacionDTO> listarComprobantesPorCierreIdTotales(Integer id_cierre);
}
