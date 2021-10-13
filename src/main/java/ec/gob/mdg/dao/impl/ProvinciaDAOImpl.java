package ec.gob.mdg.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.dao.IProvinciaDAO;
import ec.gob.mdg.model.Provincia;

@Stateless
public class ProvinciaDAOImpl implements IProvinciaDAO, Serializable{

	private static final long serialVersionUID = 499342697849649249L;
	
	
	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;
	
	@Override
	public Integer registrar(Provincia t) throws Exception {
		try {
			em.persist(t);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return t.getId();
	}

	@Override
	public Integer modificar(Provincia t) throws Exception {
		em.merge(t);
		return t.getId();
	}

	@Override
	public Integer eliminar(Provincia t) throws Exception {
		em.remove(em.merge(t));
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Provincia> listar() throws Exception {
		List<Provincia> lstProvincia= new ArrayList<Provincia>();
		try {
			Query q = em.createQuery("SELECT c FROM Provincia c"); // query de la entidad del model
			lstProvincia = (List<Provincia>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lstProvincia;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Provincia listarPorId(Provincia t) throws Exception {
		new Provincia();
		List<Provincia> lstProvincia= new ArrayList<Provincia>();
		try {
			Query q = em.createQuery("SELECT c FROM Provincia c c.id =?1"); // query de la entidad del model
			q.setParameter(1, t.getId());
			
			lstProvincia = (List<Provincia>) q.getResultList();
			
			if (lstProvincia != null && !lstProvincia.isEmpty()) {
				lstProvincia.get(0);
			}
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return t;
	}

}
