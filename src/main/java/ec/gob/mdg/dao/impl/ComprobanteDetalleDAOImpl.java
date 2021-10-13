package ec.gob.mdg.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.dao.IComprobanteDetalleDAO;
import ec.gob.mdg.model.ComprobanteDetalle;

@Stateless
public class ComprobanteDetalleDAOImpl implements IComprobanteDetalleDAO,Serializable{

	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;
	
	@Override
	public Integer registrar(ComprobanteDetalle t) throws Exception {
		try {
			em.persist(t);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return t.getId();
	}


	@Override
	public Integer modificar(ComprobanteDetalle t) throws Exception {
		em.merge(t);
		return t.getId();
	}

	@Override
	public Integer eliminar(ComprobanteDetalle t) throws Exception {
		em.remove(em.merge(t));
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComprobanteDetalle> listar() throws Exception {
		List<ComprobanteDetalle> lstComDet = new ArrayList<ComprobanteDetalle>();
		try {
			 Query q= em.createQuery("SELECT c FROM ComprobanteDetalle c"); //query de la entidad del model
			 lstComDet =(List<ComprobanteDetalle>) q.getResultList();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return lstComDet;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public ComprobanteDetalle listarPorId(ComprobanteDetalle t) throws Exception {
		ComprobanteDetalle cd = new ComprobanteDetalle();
		List<ComprobanteDetalle> lstComdet = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM ComprobanteDetalle c where c.id =?1");
			query.setParameter(1,t.getId());

			lstComdet = (List<ComprobanteDetalle>) query.getResultList();
			
			if (lstComdet != null && !lstComdet.isEmpty()) {
				cd = lstComdet.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComprobanteDetalle> listarComDetPorIdComp(Integer t) {
		
		List<ComprobanteDetalle> lstComDet = new ArrayList<ComprobanteDetalle>();
		try {
			 Query q= em.createQuery("SELECT c FROM ComprobanteDetalle c WHERE c.comprobante.id=?1");
			 q.setParameter(1,t);
			 lstComDet =(List<ComprobanteDetalle>) q.getResultList();
			 
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return lstComDet;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ComprobanteDetalle> listarComDetPorPtoNumComp(Integer id_punto, Integer numero) {
		List<ComprobanteDetalle> lista = new ArrayList<>();
		try {
			Query query = em.createQuery("SELECT d FROM Comprobante c, ComprobanteDetalle d  WHERE c.id=d.comprobante.id and c.puntoRecaudacion.id=?1  and  c.numero=?2 and c.tipocomprobante=?3");
			query.setParameter(1,id_punto);
			query.setParameter(2,numero);
			query.setParameter(3, "F");
			lista = (List<ComprobanteDetalle>) query.getResultList();

		} catch (Exception e) {
			throw e;
		}
		
		return lista;
	}


	@SuppressWarnings("unchecked")
	@Override
	public Integer eliminarComprobanteDetalle(Integer id) {
		List<ComprobanteDetalle> lista = new ArrayList<>();
		try {
			Query q = em.createQuery("FROM ComprobanteDetalle c  WHERE c.id = ?1"); 
			q.setParameter(1, id);			
			lista = (List<ComprobanteDetalle>) q.getResultList();
			
			for (ComprobanteDetalle cd : lista) {
				em.remove(em.merge(cd));
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return 1;
	}


	

}
