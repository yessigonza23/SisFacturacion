package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;

import ec.gob.mdg.dao.IAuditoriaDAO;
import ec.gob.mdg.model.Auditoria;
import ec.gob.mdg.service.IAuditoriaService;

public class AuditoriaServiceImpl implements IAuditoriaService, Serializable{

	private static final long serialVersionUID = 1L;

	@EJB
	private IAuditoriaDAO dao;
	
	@Override
	public Integer registrar(Auditoria t) throws Exception {
		return dao.registrar(t);
	}

	@Override
	public Integer modificar(Auditoria t) throws Exception {
		return null;
	}

	@Override
	public Integer eliminar(Auditoria t) throws Exception {
		return null;
	}
	
	@Override
	public List<Auditoria> listar() throws Exception {
		return null;
	}

	@Override
	public Auditoria listarPorId(Auditoria t) throws Exception {
		return null;
	}

	@Override
	public Integer insertaModificacion(String nombreTabla, String nombreCampo, String operacion, String usuario,
			Date fecha, String valorActual, String valorAnterior, Integer claveprimaria) {
		// TODO Auto-generated method stub
		return dao.insertaModificacion(nombreTabla, nombreCampo, operacion, usuario, fecha, valorActual, valorAnterior, claveprimaria);
	}

	
	
	

}
