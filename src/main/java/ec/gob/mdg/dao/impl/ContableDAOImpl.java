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

import ec.gob.mdg.dao.IContableDAO;
import ec.gob.mdg.model.Contable;

@Stateless
public class ContableDAOImpl implements IContableDAO, Serializable {

	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;

	@Override
	public Integer registrar(Contable t) throws Exception {
		try {
			em.persist(t);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return t.getId();
	}

	@Override
	public Integer modificar(Contable t) throws Exception {
		em.merge(t);
		return t.getId();
	}

	@Override
	public Integer eliminar(Contable t) throws Exception {
		em.remove(em.merge(t));
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Contable> listar() throws Exception {
		List<Contable> lstContab = new ArrayList<Contable>();
		try {
			Query q = em.createQuery("SELECT c FROM Contable c"); // query de la entidad del model
			lstContab = (List<Contable>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lstContab;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public Contable listarPorId(Contable t) throws Exception {
		
		Contable c = new Contable();
		List<Contable> lstContab = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Contable c where c.id =?1");
			query.setParameter(1, t.getId());

			lstContab = (List<Contable>) query.getResultList();

			if (lstContab != null && !lstContab.isEmpty()) {
				c = lstContab.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Contable> listarContablePorFechas(Date fecha_inicio, Date fecha_fin, Integer id_punto) {
		
		///SUMAR UN DÍA PARA QUE SE MUESTRE LA INFORMACIÓN DEL MISMO DIA
		 Calendar calendar = Calendar.getInstance();	
		 calendar.setTime(fecha_fin); // Configuramos la fecha que se recibe
		 calendar.add(Calendar.DAY_OF_YEAR, 1); 
		 fecha_fin=calendar.getTime();
		List<Contable> lstContab = new ArrayList<Contable>();
		try {
			Query q = em.createQuery("SELECT c FROM Contable c WHERE fecha between ?1 and ?2  and c.puntoRecaudacion.id = ?3 ORDER BY c.fecha DESC, c.id DESC"); 
			q.setParameter(1, fecha_inicio);
			q.setParameter(2, fecha_fin);
			q.setParameter(3, id_punto);
			lstContab = (List<Contable>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	
		return lstContab;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Contable mostrarContablePorId(Integer id_contable) {
		Contable c = new Contable();
		List<Contable> lstContab = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Contable c where c.id =?1 ORDER BY c.fecha DESC, c.id DESC ");
			query.setParameter(1, id_contable);

			lstContab = (List<Contable>) query.getResultList();

			if (lstContab != null && !lstContab.isEmpty()) {
				c = lstContab.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		return c;
	}
}
