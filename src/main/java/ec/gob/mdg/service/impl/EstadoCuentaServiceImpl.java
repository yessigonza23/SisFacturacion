package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import ec.gob.mdg.dao.IEstadoCuentaDAO;
import ec.gob.mdg.model.EstadoCuenta;
import ec.gob.mdg.service.IEstadoCuentaService;

@Named
public class EstadoCuentaServiceImpl implements IEstadoCuentaService, Serializable {
	
	
	private static final long serialVersionUID = -2031544501498091433L;
	
	@EJB
	private IEstadoCuentaDAO dao;
		
	@Override
	public Integer registrar(EstadoCuenta t) throws Exception {
		return dao.registrar(t);
	}

	@Override
	public Integer modificar(EstadoCuenta t) throws Exception {
		return dao.modificar(t);
	}

	@Override
	public Integer eliminar(EstadoCuenta t) throws Exception {
		return dao.eliminar(t);
	}

	@Override
	public List<EstadoCuenta> listar() throws Exception {
		return dao.listar();
	}

	@Override
	public EstadoCuenta listarPorId(EstadoCuenta t) throws Exception {
		
		return dao.listarPorId(t);
	}

	@Override
	public Integer registrarLista(List<EstadoCuenta> t) {
		// TODO Auto-generated method stub
		return dao.registrarLista(t);
	}

	@Override
	public List<EstadoCuenta> listarEstadoCuentaCargado(Integer anio, Integer mes) {
		return dao.listarEstadoCuentaCargado(anio, mes);
	}

	@Override
	public List<EstadoCuenta> listarEstadoCuentaSinFactura(Integer anio) {
		return dao.listarEstadoCuentaSinFactura(anio);
	}

	@Override
	public List<EstadoCuenta> listarEstadoCuentaCargado(Integer anio, Integer mes, Date fecha) {
		// TODO Auto-generated method stub
		return dao.listarEstadoCuentaCargado(anio, mes, fecha);
	}

	@Override
	public EstadoCuenta estadoCuentaPorId(Integer id) {
		// TODO Auto-generated method stub
		return dao.estadoCuentaPorId(id);
	}

	@Override
	public List<EstadoCuenta> listarEstadoCuentaPendiente(Integer anio) {
		// TODO Auto-generated method stub
		return dao.listarEstadoCuentaPendiente(anio);
	}


	
}
