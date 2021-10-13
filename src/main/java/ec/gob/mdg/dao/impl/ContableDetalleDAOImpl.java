package ec.gob.mdg.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.dao.IContableDetalleDAO;
import ec.gob.mdg.model.ContableDetalle;

@Stateless
public class ContableDetalleDAOImpl implements IContableDetalleDAO, Serializable{

	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;
	
	@Override
	public Integer registrar(ContableDetalle t) throws Exception {
		try {
			em.persist(t);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return t.getId();
	}

	@Override
	public Integer modificar(ContableDetalle t) throws Exception {
		em.merge(t);
		return t.getId();
	}

	@Override
	public Integer eliminar(ContableDetalle t) throws Exception {
		em.remove(em.merge(t));
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContableDetalle> listar() throws Exception {
		List<ContableDetalle> lstConDet = new ArrayList<ContableDetalle>();
		try {
			 Query q= em.createQuery("SELECT c FROM ContableDetalle c"); //query de la entidad del model
			 lstConDet =(List<ContableDetalle>) q.getResultList();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return lstConDet;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public ContableDetalle listarPorId(ContableDetalle t) throws Exception {
		ContableDetalle cd = new ContableDetalle();
		List<ContableDetalle> lstCondet = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM ContableDetalle c where c.id =?1");
			query.setParameter(1,t.getId());

			lstCondet = (List<ContableDetalle>) query.getResultList();
			
			if (lstCondet != null && !lstCondet.isEmpty()) {
				cd = lstCondet.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ContableDetalle> listarContableDetallePorId(Integer id_contable) {
		List<ContableDetalle> lista = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM ContableDetalle c where c.contable.id =?1 ORDER BY c.id");
			query.setParameter(1,id_contable);

			lista = (List<ContableDetalle>) query.getResultList();
			
		} catch (Exception e) {
			throw e;
		}
		return lista;
	}

}
