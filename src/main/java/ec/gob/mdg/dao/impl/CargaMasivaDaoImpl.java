package ec.gob.mdg.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.dao.ICargaMasivaDAO;
import ec.gob.mdg.model.CargaMasiva;

@Stateless
public class CargaMasivaDaoImpl implements ICargaMasivaDAO, Serializable {

	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;

	@Override
	public Integer registrar(CargaMasiva t) throws Exception {
		try {
			em.persist(t);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return t.getId();
	}

	@Override
	public Integer modificar(CargaMasiva t) throws Exception {
		em.merge(t);
		return t.getId();
	}

	@Override
	public Integer eliminar(CargaMasiva t) throws Exception {
		em.remove(em.merge(t));
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CargaMasiva> listar() throws Exception {
		List<CargaMasiva> lista = new ArrayList<CargaMasiva>();
		try {
			Query q = em.createQuery("SELECT c FROM CargaMasiva c"); // query de la entidad del model
			lista = (List<CargaMasiva>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public CargaMasiva listarPorId(CargaMasiva t) throws Exception {
		new CargaMasiva();
		List<CargaMasiva> lista = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM CargaMasiva c where c.id =?1");
			query.setParameter(1, t.getId());

			lista = (List<CargaMasiva>) query.getResultList();

			if (lista != null && !lista.isEmpty()) {
				lista.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		return t;
	}

	@Override
	public Integer registrarLista(List<CargaMasiva> t) {
		for (CargaMasiva carga : t) {
			em.persist(carga);
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<CargaMasiva> listarCargaMasivaSinComprobante(Integer id_puntorecaudacion, String tipo) {
		List<CargaMasiva> lista = new ArrayList<CargaMasiva>();
		try {
			Query q = em.createQuery("SELECT c FROM CargaMasiva c WHERE c.id_comprobante = 0 "
					+ "AND c.id_puntorecaudacion = ?1 " + "AND c.tipo =?2 ORDER BY c.cliente"); 																						
			q.setParameter(1, id_puntorecaudacion);
			q.setParameter(2, tipo);
			lista = (List<CargaMasiva>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer eliminarCargaMasivaPendiente(Integer id_puntorecaudacion, String tipo) {
		List<CargaMasiva> lista = new ArrayList<CargaMasiva>();
		try {
			Query q = em.createQuery("SELECT c FROM CargaMasiva c WHERE c.id_comprobante = 0 "
					+ "AND c.id_puntorecaudacion = ?1 " + "AND c.tipo =?2 ORDER BY c.cliente"); 
			q.setParameter(1, id_puntorecaudacion);
			q.setParameter(2, tipo);
			lista = (List<CargaMasiva>) q.getResultList();			
			for (CargaMasiva carga : lista) {				
				em.remove(em.merge(carga));
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer validarCodigoPaf(String codigoPaf) {
		Integer respuesta =0;
		List<CargaMasiva> lista = new ArrayList<CargaMasiva>();
		try {
			Query q = em.createQuery("SELECT c FROM CargaMasiva c WHERE c.id_comprobante <> 0 "
					+ "AND c.codigopaf=?1"); 																						
			q.setParameter(1, codigoPaf);
			lista = (List<CargaMasiva>) q.getResultList();
			Integer tamaniolista=lista.size();
			
			if(tamaniolista!=0) {
				respuesta=1;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return respuesta;
	}

}
