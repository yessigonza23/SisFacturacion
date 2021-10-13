package ec.gob.mdg.dao;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import ec.gob.mdg.model.VistaRecaudacionDepositoDTO;

@Local
public interface IVistaRecaudacionDepositoDTODAO extends ICRUD<VistaRecaudacionDepositoDTO> {
	// Reporte recaudaci�n para xls con Dep�sitos por Punto y fechas
	List<VistaRecaudacionDepositoDTO> listarRecaudacionDeposito(Date fecha_inicio, Date fecha_fin, Integer id_punto);

	// Contador de facturas en la vista
	Integer cuentaFacturas(Date fecha_inicio, Date fecha_fin, Integer id_punto);

	// Reporte recaudacion para xls con Depositos por Punto y fechas
	List<VistaRecaudacionDepositoDTO> listarRecaudacionDepositoNac(Date fecha_inicio, Date fecha_fin);

	// Contador de facturas en la vista
	Integer cuentaFacturasNac(Date fecha_inicio, Date fecha_fin);

	// Reporte para Conciliacion Comprobantes VS Estado de cuenta
	List<VistaRecaudacionDepositoDTO> listarConsultaBancoVsBanco(Integer id_punto, Integer anio, Integer mes);

	// Reporte para Conciliacion DEPOSITOS QUE NO CONSTAN EN EL BANCO
	List<VistaRecaudacionDepositoDTO> listarDepositosNoBanco(Integer id_punto, Integer anio, Integer mes);

	// Reporte para Conciliacion DEPOSITOS VARIAS FACTURAS
	List<VistaRecaudacionDepositoDTO> listarDepositosVariasFacturas(Integer id_punto, Integer anio, Integer mes);

	// Reporte para Conciliacion DEPOSITOS ESTADOS SIN CONSOLIDAR
	List<VistaRecaudacionDepositoDTO> listarEstadoSinColidar(Integer id_punto, Integer anio, Integer mes);

	// LISTAR LA VISTA POR AniO Y MES
	List<VistaRecaudacionDepositoDTO> listarVista(Integer anio, Integer mes);

	// LISTAR LA VISTA POR AniO
	List<VistaRecaudacionDepositoDTO> listarVistaAnio(Integer anio);

	// CONSOLIDAR OPCION RESUMEN NACIONAL
	// Reporte para Conciliacion Comprobantes VS Estado de cuenta
	List<VistaRecaudacionDepositoDTO> listarConsultaBancoVsBancoNacional(Integer anio, Integer mes);

	// Reporte para Conciliacion DEPOSITOS QUE NO CONSTAN EN EL BANCO
	List<VistaRecaudacionDepositoDTO> listarDepositosNoBancoNacional(Integer anio, Integer mes);

	// CONSOLIDACION MANUAL
	List<VistaRecaudacionDepositoDTO> listarComprobantesNumDeposito(String num_deposito, Integer anio);

	// CONSOLIDACION MANUAL CARGAR DATOS DE COMPROBANTE DEPOSITO
	VistaRecaudacionDepositoDTO ComprobantesDepNumDeposito(Integer id_deposito);
}
