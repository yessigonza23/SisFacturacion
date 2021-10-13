package ec.gob.mdg.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.dao.ICierreDAO;
import ec.gob.mdg.model.Cierre;
import ec.gob.mdg.model.PuntoRecaudacion;

@Stateless
public class CierreDAOImpl implements ICierreDAO, Serializable {

	private static final long serialVersionUID = 5187847426842965254L;
	
	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;

	@Override
	public Integer registrar(Cierre t) throws Exception {
		try {
			em.persist(t);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return t.getId();
	}

	@Override
	public Integer modificar(Cierre t) throws Exception {
		em.merge(t);
		return t.getId();
	}

	@Override
	public Integer eliminar(Cierre t) throws Exception {
		em.remove(em.merge(t));
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cierre> listar() throws Exception {
		List<Cierre> lstCierre = new ArrayList<Cierre>();
		try {
			Query q = em.createQuery("SELECT c FROM Cierre c"); // query de la entidad del model
			lstCierre = (List<Cierre>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lstCierre;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Cierre listarPorId(Cierre t) throws Exception {
		new Cierre();
		List<Cierre> lstCierre = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Cierre c where c.id =?1");
			query.setParameter(1, t.getId());

			lstCierre = (List<Cierre>) query.getResultList();

			if (lstCierre != null && !lstCierre.isEmpty()) {
				lstCierre.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cierre> listarCierreSinContable(PuntoRecaudacion p) {
		List<Cierre> lista = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Cierre c WHERE c.puntorecaudacion=?1"); 
			query.setParameter(1, p.getId());
			lista = (List<Cierre>) query.getResultList();
		} catch (Exception e) {
			throw e;
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cierre> listarCierrePorFechasPorPto(Date fecha_inicio, Date fecha_fin, Integer id_punto) {
		///SUMAR UN DÍA PARA QUE SE MUESTRE LA INFORMACIÓN DEL MISMO DIA
		 Calendar calendar = Calendar.getInstance();	
		 calendar.setTime(fecha_fin); // Configuramos la fecha que se recibe
		 calendar.add(Calendar.DAY_OF_YEAR, 1); 
		 fecha_fin=calendar.getTime();
		List<Cierre> lista = new ArrayList<Cierre>();
		try {
			Query q = em.createQuery("SELECT c FROM Cierre c WHERE fecha "
					+ "between ?1 and ?2 and c.puntoRecaudacion.id=?3 " 
					+ "ORDER BY c.id DESC"); 
			q.setParameter(1, fecha_inicio);
			q.setParameter(2, fecha_fin);
			q.setParameter(3, id_punto);
			lista = (List<Cierre>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Cierre mostrarCierrePorId(Integer id_cierre) {
		Cierre ci = new Cierre();
		List<Cierre> lstCierre = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Cierre c where c.id =?1");
			query.setParameter(1,id_cierre);
			lstCierre = (List<Cierre>) query.getResultList();
			if (lstCierre != null && !lstCierre.isEmpty()) {
				ci = lstCierre.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return ci;
	
	}

}
