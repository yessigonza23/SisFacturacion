package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import ec.gob.mdg.dao.IInstitucionDAO;
import ec.gob.mdg.model.Institucion;
import ec.gob.mdg.service.IInstitucionService;

@Named
public class InstitucionServiceImpl implements IInstitucionService,Serializable {

	private static final long serialVersionUID = 3410954421100513693L;
	@EJB
	private IInstitucionDAO dao;
	
	@Override
	public Integer registrar(Institucion t) throws Exception {
		return dao.registrar(t);
	}

	@Override
	public Integer modificar(Institucion t) throws Exception {
		return dao.modificar(t);
	}

	@Override
	public Integer eliminar(Institucion t) throws Exception {
		return dao.eliminar(t);
	}

	@Override
	public List<Institucion> listar() throws Exception {
		return dao.listar();
	}

	@Override
	public Institucion listarPorId(Institucion t) throws Exception {
		return dao.listarPorId(t);
	}

	@Override
	public Institucion institucionPorPunto(Integer i) {
		return dao.institucionPorPunto(i);
		
	}

	@Override
	public Institucion institucionActiva() {
		return dao.institucionActiva();
	}

}
