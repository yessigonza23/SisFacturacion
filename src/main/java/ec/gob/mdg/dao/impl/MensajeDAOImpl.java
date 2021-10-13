package ec.gob.mdg.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.dao.IMensajeDAO;
import ec.gob.mdg.model.Mensaje;

@Stateless
public class MensajeDAOImpl implements IMensajeDAO, Serializable {

	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;
	
	@Override
	public Integer registrar(Mensaje t) throws Exception {
		try {
			em.persist(t);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return t.getId();
	}

	@Override
	public Integer modificar(Mensaje t) throws Exception {
		em.merge(t);
		return t.getId();
	}

	@Override
	public Integer eliminar(Mensaje t) throws Exception {
		em.remove(em.merge(t));
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Mensaje> listar() throws Exception {
		List<Mensaje> lstCierre = new ArrayList<Mensaje>();
		try {
			Query q = em.createQuery("SELECT c FROM Mensaje c"); // query de la entidad del model
			lstCierre = (List<Mensaje>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lstCierre;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Mensaje listarPorId(Mensaje t) throws Exception {
		new Mensaje();
		List<Mensaje> lista = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Mensaje c where c.id =?1");
			query.setParameter(1, t.getId());

			lista = (List<Mensaje>) query.getResultList();

			if (lista != null && !lista.isEmpty()) {
				lista.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		return t;
	}

}
