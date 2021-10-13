package ec.gob.mdg.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.dao.IPuntoRecaudacionDAO;
import ec.gob.mdg.model.PuntoRecaudacion;

@Stateless
public class PuntoRecaudacionDAOImpl implements IPuntoRecaudacionDAO, Serializable{

	private static final long serialVersionUID = -5549381841466730933L;
	
	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;
	
	@Override
	public Integer registrar(PuntoRecaudacion t) throws Exception {
		try {
			em.persist(t);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return t.getId();
	}

	@Override
	public Integer modificar(PuntoRecaudacion t) throws Exception {
		em.merge(t);
		return t.getId();
	}

	@Override
	public Integer eliminar(PuntoRecaudacion t) throws Exception {
		em.remove(em.merge(t));
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PuntoRecaudacion> listar() throws Exception {
		List<PuntoRecaudacion> lstPtoRec = new ArrayList<PuntoRecaudacion>();
		try {
			 Query q= em.createQuery("SELECT p FROM PuntoRecaudacion p ORDER BY p.nombre"); //query de la entidad del model
			 lstPtoRec =(List<PuntoRecaudacion>) q.getResultList();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return lstPtoRec;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public PuntoRecaudacion listarPorId(PuntoRecaudacion t) throws Exception {
		PuntoRecaudacion p = new PuntoRecaudacion();
		List<PuntoRecaudacion> lstParDet = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM PuntoRecaudacion p where p.id =?1");
			query.setParameter(1,t.getId());

			lstParDet = (List<PuntoRecaudacion>) query.getResultList();
			
			if (lstParDet != null && !lstParDet.isEmpty()) {
				p = lstParDet.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PuntoRecaudacion puntoRecaudacionPorId(Integer id) {
		List<PuntoRecaudacion> lista= new ArrayList<>();
		PuntoRecaudacion puntoRecaudacion = new PuntoRecaudacion();
		try {
			Query query = em.createQuery("FROM PuntoRecaudacion c where c.id =?1");
			query.setParameter(1, id);

			lista= (List<PuntoRecaudacion>) query.getResultList();

			if (lista != null && !lista.isEmpty()) {
				puntoRecaudacion = lista.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		return puntoRecaudacion;
		}

}
