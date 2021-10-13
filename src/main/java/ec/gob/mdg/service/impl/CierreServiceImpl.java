package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import ec.gob.mdg.dao.ICierreDAO;
import ec.gob.mdg.model.Cierre;
import ec.gob.mdg.model.PuntoRecaudacion;
import ec.gob.mdg.service.ICierreService;

@Named
public class CierreServiceImpl implements ICierreService, Serializable{

	private static final long serialVersionUID = -2031544501498091433L;

	@EJB
	private ICierreDAO dao;
	
	@Override
	public Integer registrar(Cierre t) throws Exception {
		return dao.registrar(t);
	}

	@Override
	public Integer modificar(Cierre t) throws Exception {
		return dao.modificar(t);
	}

	@Override
	public Integer eliminar(Cierre t) throws Exception {
		return dao.eliminar(t);
	}

	@Override
	public List<Cierre> listar() throws Exception {
		return dao.listar();
	}

	@Override
	public Cierre listarPorId(Cierre t) throws Exception {
		return dao.listarPorId(t);
    }
	
	@Override
	public List<Cierre> listarCierreSinContable(PuntoRecaudacion p) {
		// TODO Auto-generated method stub
		return dao.listarCierreSinContable(p);
	}

	@Override
	public List<Cierre> listarCierrePorFechasPorPto(Date fecha_inicio, Date fecha_fin, Integer id_punto) {
		// TODO Auto-generated method stub
		return dao.listarCierrePorFechasPorPto(fecha_inicio, fecha_fin, id_punto);
	}

	@Override
	public Cierre mostrarCierrePorId(Integer id_cierre) {
		// TODO Auto-generated method stub
		return dao.mostrarCierrePorId(id_cierre);
	}
	

}
