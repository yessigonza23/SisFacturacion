package ec.gob.mdg.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.dao.IInstitucionDAO;
import ec.gob.mdg.model.Institucion;

@Stateless
public class InstitucionDAOImpl implements IInstitucionDAO, Serializable{

	private static final long serialVersionUID = -4279746549602666426L;
	
	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;
		
	@Override
	public Integer registrar(Institucion t) throws Exception {
		try {
			em.persist(t);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return t.getId();
	}

	@Override
	public Integer modificar(Institucion t) throws Exception {
		em.merge(t);
		return t.getId();
	}

	@Override
	public Integer eliminar(Institucion t) throws Exception {
		em.remove(em.merge(t));
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Institucion> listar() throws Exception {
		List<Institucion> lstinstit = new ArrayList<Institucion>();
		try {
			 Query q= em.createQuery("SELECT i FROM Institucion i"); //query de la entidad del model
			 lstinstit =(List<Institucion>) q.getResultList();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return lstinstit;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public Institucion listarPorId(Institucion t) throws Exception {
		Institucion c = new Institucion();
		List<Institucion> lstInstit = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Institucion i where i.id =?1");
			query.setParameter(1,t.getId());

			lstInstit = (List<Institucion>) query.getResultList();
			
			if (lstInstit != null && !lstInstit.isEmpty()) {
				c = lstInstit.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Institucion institucionPorPunto(Integer i) {
		
		Institucion c = new Institucion();
		List<Institucion> lista= new ArrayList<>();
		
		try {
			Query query = em.createQuery("FROM Institucion i where i.id =?1 AND i.estado='A'");
			query.setParameter(1,i);

			lista = (List<Institucion>) query.getResultList();
			
			if (lista != null && !lista.isEmpty()) {
				c = lista.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		return c;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Institucion institucionActiva() {

		Institucion c = new Institucion();
		List<Institucion> lista= new ArrayList<>();
		
		try {
			Query query = em.createQuery("FROM Institucion i where i.estado =?1");
			query.setParameter(1,"A");

			lista = (List<Institucion>) query.getResultList();
			
			if (lista != null && !lista.isEmpty()) {
				c = lista.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		return c;
	}

}
