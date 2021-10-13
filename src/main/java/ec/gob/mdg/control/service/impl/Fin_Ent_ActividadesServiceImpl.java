package ec.gob.mdg.control.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;

import ec.gob.mdg.control.dao.IFin_Ent_ActividadesDAO;
import ec.gob.mdg.control.model.Fin_Ent_Actividades;
import ec.gob.mdg.control.service.IFin_Ent_ActividadesService;

public class Fin_Ent_ActividadesServiceImpl implements IFin_Ent_ActividadesService, Serializable {


	private static final long serialVersionUID = 1L;
	@EJB	
	private IFin_Ent_ActividadesDAO dao;
	
	@Override
	public Integer registrar(Fin_Ent_Actividades t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer modificar(Fin_Ent_Actividades t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer eliminar(Fin_Ent_Actividades t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Fin_Ent_Actividades> listar() throws Exception {
		// TODO Auto-generated method stub
		return dao.listar();
	}

	@Override
	public Fin_Ent_Actividades listarPorId(Fin_Ent_Actividades t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
