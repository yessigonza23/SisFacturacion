package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;


import ec.gob.mdg.dao.IVistaRecaudacionDTODAO;
import ec.gob.mdg.model.VistaRecaudacionDTO;
import ec.gob.mdg.service.IVistaRecaudacionDTOService;

@Named
public class VistaRecaudacionDTOServiceImpl implements IVistaRecaudacionDTOService,Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private IVistaRecaudacionDTODAO dao;

	@Override
	public Integer registrar(VistaRecaudacionDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer modificar(VistaRecaudacionDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer eliminar(VistaRecaudacionDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VistaRecaudacionDTO> listar() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VistaRecaudacionDTO listarPorId(VistaRecaudacionDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VistaRecaudacionDTO> listarRecaudacionDetalle(Date fecha_inicio, Date fecha_fin, Integer id_punto) {
		return dao.listarRecaudacionDetalle(fecha_inicio, fecha_fin, id_punto);
	}

	@Override
	public Integer cuentaFacturas(Date fecha_inicio, Date fecha_fin, Integer id_punto) {
		return dao.cuentaFacturas(fecha_inicio, fecha_fin, id_punto);
	}

	@Override
	public List<VistaRecaudacionDTO> listarRecaudacionDetalleNac(Date fecha_inicio, Date fecha_fin, String proceso_tipo) {
		return dao.listarRecaudacionDetalleNac(fecha_inicio, fecha_fin,proceso_tipo);
	}

	@Override
	public Integer cuentaFacturasNac(Date fecha_inicio, Date fecha_fin, String proceso_tipo) {
		return dao.cuentaFacturasNac(fecha_inicio, fecha_fin,proceso_tipo);
	}

	@Override
	public List<VistaRecaudacionDTO> listarRecaudacionAnuladasNac(Date fecha_inicio, Date fecha_fin,
			String proceso_tipo) {
		return dao.listarRecaudacionAnuladasNac(fecha_inicio, fecha_fin, proceso_tipo);
	}

	@Override
	public Integer cuentaFacturasAnuladasNac(Date fecha_inicio, Date fecha_fin, String proceso_tipo) {
		return dao.cuentaFacturasAnuladasNac(fecha_inicio, fecha_fin, proceso_tipo);
	}

	@Override
	public List<VistaRecaudacionDTO> listarRecaudacionNoAutorizadasNac(Date fecha_inicio, Date fecha_fin,
			String proceso_tipo) {
		return dao.listarRecaudacionNoAutorizadasNac(fecha_inicio, fecha_fin, proceso_tipo);
	}

	@Override
	public Integer cuentaFacturasNoAutorizadasNac(Date fecha_inicio, Date fecha_fin, String proceso_tipo) {
		return dao.cuentaFacturasNoAutorizadasNac(fecha_inicio, fecha_fin, proceso_tipo);
	}

	@Override
	public List<VistaRecaudacionDTO> listarRecaudacionSinCierre(Date fecha_inicio, Date fecha_fin,
			String proceso_tipo) {
		return dao.listarRecaudacionSinCierre(fecha_inicio, fecha_fin, proceso_tipo);
	}

	@Override
	public List<VistaRecaudacionDTO> listarRecaudacioDif(Date fecha_inicio, Date fecha_fin, String proceso_tipo) {
		return dao.listarRecaudacioDif(fecha_inicio, fecha_fin, proceso_tipo);
	}

	@Override
	public List<VistaRecaudacionDTO> listarComprobantesPorCierreId(Integer id_cierre) {
		return dao.listarComprobantesPorCierreId(id_cierre);
	}

	@Override
	public List<VistaRecaudacionDTO> listarComprobantesPorCierreIdTotales(Integer id_cierre) {
		return dao.listarComprobantesPorCierreIdTotales(id_cierre);
	}
}
