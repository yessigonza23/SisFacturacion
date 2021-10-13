package ec.gob.mdg.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.dao.IComprobanteDepositosDAO;
import ec.gob.mdg.model.ComprobanteDepositos;

@Stateless
public class ComprobanteDepositosDAOImpl implements IComprobanteDepositosDAO, Serializable {

	private static final long serialVersionUID = 8731922923210922432L;
	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;

	@Override
	public Integer registrar(ComprobanteDepositos t) throws Exception {
		try {

			em.persist(t);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return t.getId();
	}

	@Override
	public Integer modificar(ComprobanteDepositos t) throws Exception {
		em.merge(t);
		return t.getId();
	}

	@Override
	public Integer eliminar(ComprobanteDepositos t) throws Exception {
		em.remove(em.merge(t));
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComprobanteDepositos> listar() throws Exception {
		List<ComprobanteDepositos> lstComDep = new ArrayList<ComprobanteDepositos>();
		try {
			Query q = em.createQuery("SELECT c FROM ComprobanteDepositos c"); // query de la entidad del model
			lstComDep = (List<ComprobanteDepositos>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lstComDep;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ComprobanteDepositos listarPorId(ComprobanteDepositos t) throws Exception {

		List<ComprobanteDepositos> lstComdep = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM ComprobanteDepositos c where c.id =?1");
			query.setParameter(1, t.getId());

			lstComdep = (List<ComprobanteDepositos>) query.getResultList();
			@SuppressWarnings("unused")
			ComprobanteDepositos cd = new ComprobanteDepositos();
			if (lstComdep != null && !lstComdep.isEmpty()) {
				cd = lstComdep.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComprobanteDepositos> listarComDepPorIdComp(Integer c) {

		List<ComprobanteDepositos> lista = new ArrayList<ComprobanteDepositos>();
		try {
			Query q = em.createQuery("SELECT c FROM ComprobanteDepositos c WHERE c.comprobante.id=?1");
			q.setParameter(1, c);
			lista = (List<ComprobanteDepositos>) q.getResultList();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComprobanteDepositos> listarComDepPorNumeroDeposito(String d) {
		List<ComprobanteDepositos> lista = new ArrayList<ComprobanteDepositos>();
		try {
			Query q = em.createQuery("SELECT c FROM ComprobanteDepositos c WHERE c.num_deposito=?1"); // query de la
																										// entidad del
																										// model
			q.setParameter(1, d);
			lista = (List<ComprobanteDepositos>) q.getResultList();

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer eliminarComprobanteDeposito(Integer id) {

		List<ComprobanteDepositos> lista = new ArrayList<ComprobanteDepositos>();
		try {

			Query q = em.createQuery("SELECT c FROM ComprobanteDepositos c  WHERE c.id = ?1");
			q.setParameter(1, id);

			lista = (List<ComprobanteDepositos>) q.getResultList();

			for (ComprobanteDepositos cd : lista) {

				em.remove(em.merge(cd));
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ComprobanteDepositos mostrarComprobanteDepositoPorId(Integer id_comprobanteDeposito) {
		List<ComprobanteDepositos> lista = new ArrayList<>();
		ComprobanteDepositos cd = new ComprobanteDepositos();
		try {
			Query query = em.createQuery("FROM ComprobanteDepositos c where c.id =?1");
			query.setParameter(1, id_comprobanteDeposito);
			lista = (List<ComprobanteDepositos>) query.getResultList();
			if (lista != null && !lista.isEmpty()) {
				cd = lista.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		return cd;
	}

}
