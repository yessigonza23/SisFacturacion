package ec.gob.mdg.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.dao.IConsolidaDepositosDAO;
import ec.gob.mdg.model.ConsolidaDepositos;

@Stateless
public class ConsolidaDepositosDAOImpl implements IConsolidaDepositosDAO,Serializable {

	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;
	
	@Override
	public Integer registrar(ConsolidaDepositos t) throws Exception {
		try {
			em.persist(t);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return t.getId();
	}

	@Override
	public Integer modificar(ConsolidaDepositos t) throws Exception {
		em.merge(t);
		return t.getId();
	}

	@Override
	public Integer eliminar(ConsolidaDepositos t) throws Exception {
		em.remove(em.merge(t));
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsolidaDepositos> listar() throws Exception {
		List<ConsolidaDepositos> lista = new ArrayList<ConsolidaDepositos>();
		try {
			Query q = em.createQuery("SELECT c FROM ConsolidaDepositos c"); 
			lista = (List<ConsolidaDepositos>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public ConsolidaDepositos listarPorId(ConsolidaDepositos t) throws Exception {
		ConsolidaDepositos c = new ConsolidaDepositos();
		List<ConsolidaDepositos> lista = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM ConsolidaDepositos c where c.id =?1");
			query.setParameter(1, t.getId());

			lista = (List<ConsolidaDepositos>) query.getResultList();

			if (lista != null && !lista.isEmpty()) {
				c = lista.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ConsolidaDepositos listarPorId(Integer id) {
		ConsolidaDepositos c = new ConsolidaDepositos();
		List<ConsolidaDepositos> lista = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM ConsolidaDepositos c where c.id =?1");
			query.setParameter(1, id);

			lista = (List<ConsolidaDepositos>) query.getResultList();

			if (lista != null && !lista.isEmpty()) {
				c = lista.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		return c;
	}

}
