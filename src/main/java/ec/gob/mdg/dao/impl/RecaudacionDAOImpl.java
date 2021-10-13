package ec.gob.mdg.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.dao.IRecaudacionDAO;
import ec.gob.mdg.model.Recaudacion;
@Stateless
public class RecaudacionDAOImpl implements IRecaudacionDAO, Serializable{

	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;
	
	@Override
	public Integer registrar(Recaudacion t) throws Exception {
		try {
			em.persist(t);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return t.getId();
	}

	@Override
	public Integer modificar(Recaudacion t) throws Exception {
		em.merge(t);
		return t.getId();
	}

	@Override
	public Integer eliminar(Recaudacion t) throws Exception {
		em.remove(em.merge(t));
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Recaudacion> listar() throws Exception {
		List<Recaudacion> lstRecaud = new ArrayList<Recaudacion>();
		try {
			 Query q= em.createQuery("SELECT r FROM Recaudacion r ORDER BY r.codigobanco"); //query de la entidad del model
			 lstRecaud =(List<Recaudacion>) q.getResultList();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return lstRecaud;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public Recaudacion listarPorId(Recaudacion t) throws Exception {
		Recaudacion r = new Recaudacion();
		List<Recaudacion> lstRecaud = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Recaudacion r where r.id =?1");
			query.setParameter(1,t.getId());

			lstRecaud = (List<Recaudacion>) query.getResultList();
			
			if (lstRecaud != null && !lstRecaud.isEmpty()) {
				r = lstRecaud.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		return t;
	}

	

	

	
}
