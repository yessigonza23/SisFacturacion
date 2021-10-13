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

import ec.gob.mdg.dao.IComprobanteDAO;
import ec.gob.mdg.model.Cliente;
import ec.gob.mdg.model.Comprobante;
import ec.gob.mdg.model.PuntoRecaudacion;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.util.UtilsDate;

@Stateless
public class ComprobanteDAOImpl implements IComprobanteDAO, Serializable {

	private static final long serialVersionUID = -3995444805468231522L;

	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;

	@Override
	public Integer registrar(Comprobante t) throws Exception {
		try {
			em.persist(t);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return t.getId();
	}

	@Override
	public Integer modificar(Comprobante t) throws Exception {
		em.merge(t);
		return t.getId();
	}

	@Override
	public Integer eliminar(Comprobante t) throws Exception {
		em.remove(em.merge(t));
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comprobante> listar() throws Exception {
		List<Comprobante> lstCompro = new ArrayList<Comprobante>();
		try {
			Query q = em.createQuery("SELECT c FROM Comprobante c ORDER BY fechaemision DESC,numero DESC");
			lstCompro = (List<Comprobante>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lstCompro;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Comprobante listarPorId(Comprobante t) throws Exception {
		@SuppressWarnings("unused")
		Comprobante co = new Comprobante();
		List<Comprobante> lstCompro = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Comprobante c where c.id =?1");
			query.setParameter(1, t.getId());
			lstCompro = (List<Comprobante>) query.getResultList();
			if (lstCompro != null && !lstCompro.isEmpty()) {
				co = lstCompro.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comprobante> listarCompPorPto(PuntoRecaudacion p) {
	
		List<Comprobante> lstCompro = new ArrayList<Comprobante>();
		try {
			Query q = em.createQuery("SELECT c FROM Comprobante c"
					+ " WHERE c.puntoRecaudacion.id=?1 "
					+ " and c.cierre.id is null  and c.tipocomprobante='F' and c.estado='A' "
					+ " ORDER BY c.fechaemision DESC, c.numero DESC");
		
			q.setParameter(1, p.getId());
			lstCompro = (List<Comprobante>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	
		return lstCompro;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Comprobante comprobantePorPtoPorId(Integer id_punto, Integer id_comprobante) {
		Comprobante c = new Comprobante();
		List<Comprobante> lista = new ArrayList<>();
		try {
			Query query = em.createQuery(
					"SELECT c FROM Comprobante c WHERE c.puntoRecaudacion.id = ?1 and c.numero=?2 and c.tipocomprobante='F'");
			query.setParameter(1, id_punto);
			query.setParameter(2, id_comprobante);
			lista = (List<Comprobante>) query.getResultList();
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
	public List<Comprobante> comprobantePorCliente(Cliente c) {
		List<Comprobante> lista = new ArrayList<Comprobante>();
		try {
			Query q = em.createQuery(
					"SELECT c FROM Comprobante c WHERE c.cliente.id=?1 and c.tipocomprobante='F' ORDER BY c.fechaemision DESC, c.numero DESC");
			q.setParameter(1, c.getId());
			lista = (List<Comprobante>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Comprobante listarComprobantePorId(Integer id_comprobante) {
		Comprobante co = new Comprobante();
		List<Comprobante> lstCompro = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Comprobante c where c.id =?1 ");
			query.setParameter(1, id_comprobante);
			lstCompro = (List<Comprobante>) query.getResultList();
			if (lstCompro != null && !lstCompro.isEmpty()) {
				co = lstCompro.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return co;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Comprobante listarComprobanteNotaPorId(Integer id_comprobante) {
		Comprobante co = new Comprobante();
		List<Comprobante> lstCompro = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Comprobante c where c.id =?1 and c.tipocomprobante='C' ");
			query.setParameter(1, id_comprobante);
			lstCompro = (List<Comprobante>) query.getResultList();
			if (lstCompro != null && !lstCompro.isEmpty()) {
				co = lstCompro.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return co;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comprobante> listarNotasCreditoPorCliente(Cliente c) {
		List<Comprobante> lista = new ArrayList<Comprobante>();
		try {
			Query q = em.createQuery(
					"SELECT c FROM Comprobante c WHERE c.cliente.id=?1 and c.tipocomprobante='C' ORDER BY c.fechaemision DESC, c.numero DESC");
			q.setParameter(1, c.getId());
			lista = (List<Comprobante>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comprobante> listarCompPorPtoCon(PuntoRecaudacion p) {
		List<Comprobante> lstCompro = new ArrayList<Comprobante>();
		try {
			Query q = em.createQuery("SELECT c FROM Comprobante c WHERE c.puntoRecaudacion.id =?1 "
					+ "and c.tipocomprobante='F' ORDER BY c.fechaemision DESC, c.numero DESC");
			q.setParameter(1, p.getId());
			lstCompro = (List<Comprobante>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lstCompro;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comprobante> listarCompNotasPorPtoCon(PuntoRecaudacion p) {
		List<Comprobante> lstCompro = new ArrayList<Comprobante>();
		try {
			Query q = em.createQuery("SELECT c FROM Comprobante c WHERE c.puntoRecaudacion.id =?1 "
					+ "and c.tipocomprobante='C' ORDER BY c.fechaemision DESC, c.numero DESC");
			q.setParameter(1, p.getId());
			lstCompro = (List<Comprobante>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lstCompro;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comprobante> listarCompPorIdPto(Integer id_punto) {
		List<Comprobante> lista = new ArrayList<>();
		try {
			Query query = em.createQuery("SELECT c FROM Comprobante c "
					+ "WHERE c.puntoRecaudacion.id=?1 and c.tipocomprobante='F' "
					+ "ORDER BY c.fechaemision DESC, c.numero DESC");
			query.setParameter(1, id_punto);
			lista = (List<Comprobante>) query.getResultList();
		} catch (Exception e) {
			throw e;
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Comprobante comprobantePorPtoPorIdNotas(Integer id_punto, Integer id_comprobante) {
		Comprobante c = new Comprobante();
		List<Comprobante> lista = new ArrayList<>();
		try {
			Query query = em.createQuery("SELECT c FROM Comprobante c "
					+ "WHERE c.puntoRecaudacion.id=?1 and c.tipocomprobante='C' "
					+ "and c.numero=?2 ");
			query.setParameter(1, id_punto);
			query.setParameter(2, id_comprobante);
			lista = (List<Comprobante>) query.getResultList();
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
	public List<Comprobante> listarComprobantePorFechas(Date fecha_inicio, Date fecha_fin, Integer id_punto,
			String tipo) {
		/// SUMAR UN D�A PARA QUE SE MUESTRE LA INFORMACI�N DEL MISMO DIA
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha_fin); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		fecha_fin = calendar.getTime();
		List<Comprobante> lstCompro = new ArrayList<Comprobante>();
		try {
			Query q = em.createQuery("SELECT c FROM Comprobante c WHERE fechaemision "
					+ "between ?1 and ?2 and c.puntoRecaudacion.id=?3 " + "and c.tipocomprobante=?4 "
					+ "ORDER BY c.fechaemision DESC, c.numero DESC");
			q.setParameter(1, fecha_inicio);
			q.setParameter(2, fecha_fin);
			q.setParameter(3, id_punto);
			q.setParameter(4, tipo);
			lstCompro = (List<Comprobante>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lstCompro;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comprobante> listarComprobantePorIdCierre(Integer id_cierre) {
		List<Comprobante> lstCompro = new ArrayList<Comprobante>();
		try {
			Query q = em.createQuery(
					"SELECT c FROM Comprobante c WHERE c.cierre.id=?1" + "ORDER BY c.fechaemision DESC, c.numero DESC");
			q.setParameter(1, id_cierre);
			lstCompro = (List<Comprobante>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lstCompro;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer validaCierre(UsuarioPunto up) {
		Integer contador = 0;
		Date fechaActual = UtilsDate.fechaFormatoDate(UtilsDate.fechaActual());
		List<Comprobante> lstCompro = new ArrayList<Comprobante>();
		try {
			Query q = em.createQuery("SELECT c FROM Comprobante c " 
		            + "WHERE  c.puntoRecaudacion.id =?1 "
					+ "and c.cierre.id is null  and c.tipocomprobante='F' " + "and fechaemision < ?2 and c.estado='A' "
					+ "ORDER BY c.fechaemision DESC, c.numero DESC");
			q.setParameter(1, up.getPuntoRecaudacion().getId());
			q.setParameter(2, fechaActual);
			lstCompro = (List<Comprobante>) q.getResultList();
			contador = lstCompro.size();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return contador;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comprobante> listarNoAutorizadasPorPunto(Integer id_punto) {
		List<Comprobante> lista = new ArrayList<Comprobante>();
		try {
			Query q = em.createQuery("SELECT c FROM Comprobante c  "
					+ "WHERE c.puntoRecaudacion.id = ?1 "
					+ "and c.autorizacion is null  and c.tipocomprobante='F' "
					+ "ORDER BY c.fechaemision DESC, c.numero DESC"); 
			q.setParameter(1, id_punto);
			lista = (List<Comprobante>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comprobante> listarComprobantePorFechasSinAutor(Date fecha_inicio, Date fecha_fin, Integer id_punto,
			String tipo) {
		/// SUMAR UN D�A PARA QUE SE MUESTRE LA INFORMACI�N DEL MISMO DIA
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha_fin); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		fecha_fin = calendar.getTime();
		List<Comprobante> lstCompro = new ArrayList<Comprobante>();
		try {
			Query q = em.createQuery("SELECT c FROM Comprobante c WHERE fechaemision "
					+ "between ?1 and ?2 and c.puntoRecaudacion.id=?3 "
					+ "and c.tipocomprobante=?4 and c.autorizacion is null "
					+ " ORDER BY c.fechaemision DESC, c.numero DESC");
			q.setParameter(1, fecha_inicio);
			q.setParameter(2, fecha_fin);
			q.setParameter(3, id_punto);
			q.setParameter(4, tipo);
			lstCompro = (List<Comprobante>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lstCompro;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> listarClientesComprobantes(String tipoComprobante) {
				
		List<Cliente> lista = new ArrayList<Cliente>();
		try {
			Query q = em.createQuery(
					"SELECT DISTINCT cl FROM Comprobante c,Cliente cl WHERE c.cliente.id = cl.id AND c.tipocomprobante=?1 ORDER BY cl.nombre");

			q.setParameter(1, tipoComprobante);
			lista = (List<Cliente>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Comprobante> listarCompPorIdPtoNotas(Integer id_punto) {
		List<Comprobante> lista = new ArrayList<>();
		try {
			Query query = em.createQuery("SELECT c FROM Comprobante c "
					+ "WHERE c.puntoRecaudacion.id=?1 and c.tipocomprobante='F' and c.estado='A' "
					+ "ORDER BY c.fechaemision DESC, c.numero DESC");
			query.setParameter(1, id_punto);
			lista = (List<Comprobante>) query.getResultList();
		} catch (Exception e) {
			throw e;
		}
		return lista;
	}

}
