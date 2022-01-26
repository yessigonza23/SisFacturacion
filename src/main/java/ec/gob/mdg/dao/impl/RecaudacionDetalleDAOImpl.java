package ec.gob.mdg.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.dao.IRecaudacionDetalleDAO;
import ec.gob.mdg.model.RecaudacionDetalle;

@Stateless
public class RecaudacionDetalleDAOImpl implements IRecaudacionDetalleDAO, Serializable {

	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;
	
	@Override
	public Integer registrar(RecaudacionDetalle t) throws Exception {
		try {
			em.persist(t);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return t.getId();
	}	

	@Override
	public Integer modificar(RecaudacionDetalle t) throws Exception {
		em.merge(t);
		return t.getId();
	}

	@Override
	public Integer eliminar(RecaudacionDetalle t) throws Exception {
		em.remove(em.merge(t));
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RecaudacionDetalle> listar() throws Exception {
		List<RecaudacionDetalle> lstRecDet = new ArrayList<RecaudacionDetalle>();
		try {
			 Query q= em.createQuery("SELECT r FROM RecaudacionDetalle r"); //query de la entidad del model
			 lstRecDet =(List<RecaudacionDetalle>) q.getResultList();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return lstRecDet;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public RecaudacionDetalle listarPorId(RecaudacionDetalle t) throws Exception {
		RecaudacionDetalle r = new RecaudacionDetalle();
		List<RecaudacionDetalle> lstRecDet= new ArrayList<>();
		try {
			Query query = em.createQuery("FROM RecaudacionDetalle r where r.id =?1");
			query.setParameter(1,t.getId());

			lstRecDet = (List<RecaudacionDetalle>) query.getResultList();
			
			if (lstRecDet != null && !lstRecDet.isEmpty()) {
				r = lstRecDet.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RecaudacionDetalle> listarRecaudacionDetallePorParametro(RecaudacionDetalle rd) {
		List<RecaudacionDetalle> lista = new ArrayList<RecaudacionDetalle>();
		try {
			 Query q= em.createQuery("FROM RecaudacionDetalle r where r.recaudacion =?1"); //query de la entidad del model
			 q.setParameter(1,rd.getRecaudacion());
			 lista =(List<RecaudacionDetalle>) q.getResultList();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RecaudacionDetalle> listarRecaudacionDetallePorProceso(String tipo_proceso) {
		List<RecaudacionDetalle> lstRecDet = new ArrayList<RecaudacionDetalle>();
		try {
			 Query q= em.createQuery("SELECT rd \r\n" + 
			 		"FROM Proceso p,Recaudacion r,RecaudacionDetalle rd\r\n" + 
			 		"WHERE p.id=r.proceso.id\r\n" + 
			 		" and r.id = rd.recaudacion.id\r\n" + 
			 		" and p.tipo_proceso=?1 and rd.estado = 'A' order by r.codigobanco ,rd.nombre"); 
			 q.setParameter(1,tipo_proceso);
			 lstRecDet =(List<RecaudacionDetalle>) q.getResultList();
			 
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return lstRecDet;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RecaudacionDetalle mostrarRecaudacionDetallePorNombre(String nombreRecaudacion) {
		List<RecaudacionDetalle> lstRecDet = new ArrayList<RecaudacionDetalle>();
		RecaudacionDetalle recaudacionDetalle = new RecaudacionDetalle();
		try {
			 Query q= em.createQuery("SELECT rd FROM RecaudacionDetalle rd WHERE rd.nombre=?1"); 
			 q.setParameter(1,nombreRecaudacion);
			 
			 lstRecDet =(List<RecaudacionDetalle>) q.getResultList();
			 
			 if (lstRecDet != null && !lstRecDet.isEmpty()) {
					recaudacionDetalle = lstRecDet.get(0);
				}
			 
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return recaudacionDetalle;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Boolean hayRecaudacionDetallePorNombre(String nombreRecaudacion) {
		Boolean respuesta = null;
		List<RecaudacionDetalle> lstRecDet = new ArrayList<RecaudacionDetalle>();
		@SuppressWarnings("unused")
		RecaudacionDetalle recaudacionDetalle = new RecaudacionDetalle();
		try {
			 Query q= em.createQuery("SELECT rd FROM RecaudacionDetalle rd WHERE rd.nombre=?1"); 
			 q.setParameter(1,nombreRecaudacion);
			 
			 lstRecDet =(List<RecaudacionDetalle>) q.getResultList();
			 
			 if (lstRecDet != null && !lstRecDet.isEmpty()) {
					recaudacionDetalle = lstRecDet.get(0);
				}
			 if (lstRecDet.size()>0) {
				 respuesta=true;
			 }else {
				respuesta = false;
			}
			 
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return respuesta;
	}

	@SuppressWarnings("unchecked")
	@Override
	public RecaudacionDetalle mostrarRecaudacionDetallePorCodigo(String codigoServicio) {
		List<RecaudacionDetalle> lstRecDet = new ArrayList<RecaudacionDetalle>();
		RecaudacionDetalle recaudacionDetalle = new RecaudacionDetalle();
		try {
			Query q = em.createQuery("SELECT rd "
					+ "FROM RecaudacionDetalle rd "
					+ "WHERE rd.codigo =?1");
			q.setParameter(1, codigoServicio);
			lstRecDet = (List<RecaudacionDetalle>) q.getResultList();

			if (lstRecDet != null && !lstRecDet.isEmpty()) {
				recaudacionDetalle = lstRecDet.get(0);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return recaudacionDetalle;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RecaudacionDetalle> listarRecaudacionDetallePorProcesoArriendos(String tipo_proceso) {
		
		List<RecaudacionDetalle> lstRecDet = new ArrayList<RecaudacionDetalle>();
		try {
			 Query q= em.createQuery("SELECT rd FROM Proceso p,Recaudacion r,RecaudacionDetalle rd WHERE p.id=r.proceso.id " + 
			 		" and r.id = rd.recaudacion.id" + 
			 		" and p.tipo_proceso=?1 and rd.estado = 'G' order by r.codigobanco,rd.nombre"); 
			 q.setParameter(1,tipo_proceso);
			 lstRecDet =(List<RecaudacionDetalle>) q.getResultList();
			 
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}		
		return lstRecDet;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RecaudacionDetalle> listarRecaudacionDetallePorProcesoOtrosServicios(String tipo_proceso,String estado) {
		
		
		List<RecaudacionDetalle> lstRecDet = new ArrayList<RecaudacionDetalle>();
		try {
			 Query q= em.createQuery("SELECT rd FROM Proceso p,Recaudacion r,RecaudacionDetalle rd WHERE p.id=r.proceso.id " + 
			 		" and r.id = rd.recaudacion.id" + 
			 		" and p.tipo_proceso=?1 and rd.estado = ?2 order by r.codigobanco,rd.nombre"); 
			 q.setParameter(1,tipo_proceso);
			 q.setParameter(2,estado);
			 lstRecDet =(List<RecaudacionDetalle>) q.getResultList();
						
			 
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}		
		return lstRecDet;
	}
	
	

}
