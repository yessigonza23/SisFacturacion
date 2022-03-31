package ec.gob.mdg.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.dao.IEstadoCuentaDAO;
import ec.gob.mdg.model.EstadoCuenta;

@Stateless
public class EstadoCuentaDAOImpl implements IEstadoCuentaDAO, Serializable {

	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;

	@Override
	public Integer registrar(EstadoCuenta t) throws Exception {
		try {
			em.persist(t);

		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return t.getId();
	}

	@Override
	public Integer modificar(EstadoCuenta t) throws Exception {
		em.merge(t);
		return t.getId();
	}

	@Override
	public Integer eliminar(EstadoCuenta t) throws Exception {
		em.merge(t);
		return t.getId();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EstadoCuenta> listar() throws Exception {
		List<EstadoCuenta> lista = new ArrayList<EstadoCuenta>();
		try {
			Query q = em.createQuery("SELECT c FROM EstadoCuenta c");
			lista = (List<EstadoCuenta>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}

	@Override
	public EstadoCuenta listarPorId(EstadoCuenta t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer registrarLista(List<EstadoCuenta> t) {
		// EstadoCuenta estado = new EstadoCuenta();
		for (EstadoCuenta est : t) {
			em.persist(est);
		}
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EstadoCuenta> listarEstadoCuentaCargado(Integer anio, Integer mes) {
		List<EstadoCuenta> lista = new ArrayList<EstadoCuenta>();
		try {
			Query q = em.createQuery("SELECT c FROM EstadoCuenta c WHERE c.anio=?1 AND c.mes=?2"); 
			q.setParameter(1,anio);
			q.setParameter(2,mes);
			lista = (List<EstadoCuenta>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EstadoCuenta> listarEstadoCuentaSinFactura(Integer anio) {
		List<EstadoCuenta> lista = new ArrayList<EstadoCuenta>();
		try {
			Query q = em.createQuery("SELECT c FROM EstadoCuenta c WHERE c.anio=?1  AND bloqueado = false"); // query de la entidad del model
			q.setParameter(1,anio);
			lista = (List<EstadoCuenta>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EstadoCuenta> listarEstadoCuentaCargado(Integer anio, Integer mes, Date fecha) {
		List<EstadoCuenta> lista = new ArrayList<EstadoCuenta>();
		try {
			Query q = em.createQuery("SELECT c FROM EstadoCuenta c WHERE c.anio=?1 AND c.mes=?2 AND c.fechacarga=?3"); // query de la entidad del model
			q.setParameter(1,anio);
			q.setParameter(2,mes);
			q.setParameter(3,fecha);
			lista = (List<EstadoCuenta>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public EstadoCuenta estadoCuentaPorId(Integer id) {
		List<EstadoCuenta> lista= new ArrayList<>();
		EstadoCuenta estadoCuenta = new EstadoCuenta();
		try {
			Query query = em.createQuery("FROM EstadoCuenta c where c.id =?1");
			query.setParameter(1, id);

			lista= (List<EstadoCuenta>) query.getResultList();
			if (lista != null && !lista.isEmpty()) {
				estadoCuenta = lista.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		return estadoCuenta;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EstadoCuenta> listarEstadoCuentaPendiente(Integer anio) {
		List<EstadoCuenta> lista = new ArrayList<EstadoCuenta>();
		try {
			Query q = em.createQuery("SELECT c FROM EstadoCuenta c WHERE c.anio=?1  AND bloqueado = false AND saldo=valor AND respuesta = 'Proceso O.K.'"); // query de la entidad del model
			q.setParameter(1,anio);
			lista = (List<EstadoCuenta>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}

	

}
