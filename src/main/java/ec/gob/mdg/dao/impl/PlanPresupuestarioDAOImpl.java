package ec.gob.mdg.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.dao.IPlanPresupuestarioDAO;
import ec.gob.mdg.model.PlanPresupuestario;

@Stateless
public class PlanPresupuestarioDAOImpl implements IPlanPresupuestarioDAO, Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;
	
	@Override
	public Integer registrar(PlanPresupuestario t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer modificar(PlanPresupuestario t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer eliminar(PlanPresupuestario t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlanPresupuestario> listar() throws Exception {
		List<PlanPresupuestario> lista = new ArrayList<PlanPresupuestario>();
		try {
			Query query = em.createQuery("FROM PlanPresupuestario c ");
			lista = (List<PlanPresupuestario>) query.getResultList();
		
		} catch (Exception e) {
			throw e;
		}
		return lista;
	}

	@Override
	public PlanPresupuestario listarPorId(PlanPresupuestario t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
