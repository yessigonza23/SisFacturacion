package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import ec.gob.mdg.dao.IVistaCierreDTODAO;
import ec.gob.mdg.model.VistaCierreDTO;
import ec.gob.mdg.service.IVistaCierreDTOService;

@Named
public class VistaCierreDTOServiceImpl implements IVistaCierreDTOService, Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private IVistaCierreDTODAO dao;

	@Override
	public Integer registrar(VistaCierreDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer modificar(VistaCierreDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer eliminar(VistaCierreDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VistaCierreDTO> listar() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VistaCierreDTO listarPorId(VistaCierreDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VistaCierreDTO> listarCierre(Date fecha_inicio, Date fecha_fin, Integer id_punto) {
		return dao.listarCierre(fecha_inicio, fecha_fin, id_punto);
	}

	@Override
	public Integer cuentaFacturas(Date fecha_inicio, Date fecha_fin, Integer id_punto) {
		return dao.cuentaFacturas(fecha_inicio, fecha_fin, id_punto);
	}

	@Override
	public List<VistaCierreDTO> listarCierreNac(Date fecha_inicio, Date fecha_fin) {
		return dao.listarCierreNac(fecha_inicio, fecha_fin);
	}

	@Override
	public Integer cuentaFacturasNac(Date fecha_inicio, Date fecha_fin) {
		return dao.cuentaFacturasNac(fecha_inicio, fecha_fin);
	}

	
}
