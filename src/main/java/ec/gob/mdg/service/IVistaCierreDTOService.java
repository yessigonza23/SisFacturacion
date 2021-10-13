package ec.gob.mdg.service;

import java.util.Date;
import java.util.List;

import ec.gob.mdg.model.VistaCierreDTO;

public interface IVistaCierreDTOService extends IService<VistaCierreDTO> {
	// Reporte de cierres de caja por Punto y fechas
	List<VistaCierreDTO> listarCierre(Date fecha_inicio, Date fecha_fin, Integer id_punto);

	// Contador de facturas en la vista
	Integer cuentaFacturas(Date fecha_inicio, Date fecha_fin, Integer id_punto);

	// Reporte de cierres de caja Nacional y fechas
	List<VistaCierreDTO> listarCierreNac(Date fecha_inicio, Date fecha_fin);

	// Contador de facturas Nacional en la vista
	Integer cuentaFacturasNac(Date fecha_inicio, Date fecha_fin);
}
