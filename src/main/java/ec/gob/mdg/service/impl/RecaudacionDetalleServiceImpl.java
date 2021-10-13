package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import ec.gob.mdg.dao.IRecaudacionDetalleDAO;
import ec.gob.mdg.model.RecaudacionDetalle;
import ec.gob.mdg.service.IRecaudacionDetalleService;

@Named
public class RecaudacionDetalleServiceImpl implements IRecaudacionDetalleService, Serializable {

	private static final long serialVersionUID = 1567880709652697862L;
	@EJB
	private IRecaudacionDetalleDAO dao;

	@Override
	public Integer registrar(RecaudacionDetalle t) throws Exception {
		return dao.registrar(t);
	}

	@Override
	public Integer modificar(RecaudacionDetalle t) throws Exception {
		return dao.modificar(t);
	}

	@Override
	public Integer eliminar(RecaudacionDetalle t) throws Exception {
		return dao.eliminar(t);
	}

	@Override
	public List<RecaudacionDetalle> listar() throws Exception {
		return dao.listar();
	}

	@Override
	public RecaudacionDetalle listarPorId(RecaudacionDetalle t) throws Exception {
		return dao.listarPorId(t);
	}

	@Override
	public List<RecaudacionDetalle> listarRecaudacionDetallePorParametro(RecaudacionDetalle rd) {
		return dao.listarRecaudacionDetallePorParametro(rd);
	}

	@Override
	public List<RecaudacionDetalle> listarRecaudacionDetallePorProceso(String tipo_proceso) {
		return dao.listarRecaudacionDetallePorProceso(tipo_proceso);
	}

	@Override
	public RecaudacionDetalle mostrarRecaudacionDetallePorNombre(String nombreRecaudacion) {
		return dao.mostrarRecaudacionDetallePorNombre(nombreRecaudacion);
	}

	@Override
	public Boolean hayRecaudacionDetallePorNombre(String nombreRecaudacion) {
		return dao.hayRecaudacionDetallePorNombre(nombreRecaudacion);
	}

	@Override
	public RecaudacionDetalle mostrarRecaudacionDetallePorCodigo(String codigoServicio) {
		return dao.mostrarRecaudacionDetallePorCodigo(codigoServicio);
	}

	@Override
	public List<RecaudacionDetalle> listarRecaudacionDetallePorProcesoArriendos(String tipo_proceso) {
		return dao.listarRecaudacionDetallePorProcesoArriendos(tipo_proceso);
	}

	@Override
	public List<RecaudacionDetalle> listarRecaudacionDetallePorProcesoOtrosServicios(String tipo_proceso,
			String estado) {
		return dao.listarRecaudacionDetallePorProcesoOtrosServicios(tipo_proceso, estado);
	}

}
