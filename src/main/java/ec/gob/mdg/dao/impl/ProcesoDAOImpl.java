package ec.gob.mdg.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.dao.IProcesoDAO;
import ec.gob.mdg.model.Proceso;

@Stateless
public class ProcesoDAOImpl implements IProcesoDAO, Serializable {
	
	
	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;
	
	@Override
	public Integer registrar(Proceso t) throws Exception {
		try {
			em.persist(t);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return t.getId();
	}

	@Override
	public Integer modificar(Proceso t) throws Exception {
		em.merge(t);
		return t.getId();
	}

	@Override
	public Integer eliminar(Proceso t) throws Exception {
		em.remove(em.merge(t));
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Proceso> listar() throws Exception {
		List<Proceso> lstProces = new ArrayList<Proceso>();
		try {
			 Query q= em.createQuery("SELECT p FROM Proceso p"); //query de la entidad del model
			 lstProces =(List<Proceso>) q.getResultList();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return lstProces;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public Proceso listarPorId(Proceso t) throws Exception {
		Proceso p = new Proceso();
		List<Proceso> lstProces = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Proceso p where p.id =?1");
			query.setParameter(1,t.getId());

			lstProces = (List<Proceso>) query.getResultList();
			
			if (lstProces != null && !lstProces.isEmpty()) {
				
				p = lstProces.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		return t;
	}

}
