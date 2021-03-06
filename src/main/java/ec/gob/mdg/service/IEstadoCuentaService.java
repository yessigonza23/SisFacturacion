package ec.gob.mdg.service;


import java.util.Date;
import java.util.List;

import ec.gob.mdg.model.EstadoCuenta;

public interface IEstadoCuentaService extends IService<EstadoCuenta> {
	Integer registrarLista(List<EstadoCuenta> t);

	// LISTAR ESTADO DE CUENTA
	List<EstadoCuenta> listarEstadoCuentaCargado(Integer anio, Integer mes);

	// LISTAR ESTADO DE CUENTA SIN CONCILIAR
	List<EstadoCuenta> listarEstadoCuentaSinFactura(Integer anio);

	// LISTAR ESTADO DE CUENTA POR A�O, MES Y FECHA
	List<EstadoCuenta> listarEstadoCuentaCargado(Integer anio, Integer mes, Date fecha);

	// LISTAR ESTADO DE CUENTA POR ID
	EstadoCuenta estadoCuentaPorId(Integer id);

	// LISTAR ESTADO DE CUENTA SIN CONCILIAR
		List<EstadoCuenta> listarEstadoCuentaPendiente(Integer anio);


}
