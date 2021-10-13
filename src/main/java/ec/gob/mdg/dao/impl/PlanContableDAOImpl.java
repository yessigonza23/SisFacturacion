package ec.gob.mdg.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.dao.IPlanContableDAO;
import ec.gob.mdg.model.PlanContable;

@Stateless
public class PlanContableDAOImpl implements IPlanContableDAO, Serializable{

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;
	
	@Override
	public Integer registrar(PlanContable t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer modificar(PlanContable t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer eliminar(PlanContable t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PlanContable> listar() throws Exception {
		List<PlanContable> lista = new ArrayList<PlanContable>();
		try {
			Query query = em.createQuery("FROM PlanContable c ");
			lista = (List<PlanContable>) query.getResultList();
		
		} catch (Exception e) {
			throw e;
		}
		return lista;
	}

	@Override
	public PlanContable listarPorId(PlanContable t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlanContable muestraPlanContable(String codigo_contable) {
		PlanContable plan = new PlanContable();
		List<PlanContable> lista = new ArrayList<PlanContable>();
		try {
			Query query = em.createQuery("FROM PlanContable c where c.codigo =?1");
			query.setParameter(1,codigo_contable);
			lista = (List<PlanContable>) query.getResultList();
			if (lista != null && !lista.isEmpty()) {
				plan = lista.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return plan;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PlanContable cargaPlanContable(Integer id_plancontable) {
		PlanContable plan = new PlanContable();
		List<PlanContable> lista = new ArrayList<PlanContable>();
		try {
			Query query = em.createQuery("FROM PlanContable c where c.id =?1");
			query.setParameter(1,id_plancontable);
			lista = (List<PlanContable>) query.getResultList();
			if (lista != null && !lista.isEmpty()) {
				plan = lista.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return plan;
	}

}
