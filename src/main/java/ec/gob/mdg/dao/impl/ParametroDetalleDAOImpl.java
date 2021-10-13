package ec.gob.mdg.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.dao.IParametroDetalleDAO;
import ec.gob.mdg.model.ParametroDetalle;


@Stateless
public class ParametroDetalleDAOImpl implements IParametroDetalleDAO, Serializable{

	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;
	
	@Override
	public Integer registrar(ParametroDetalle t) throws Exception {
		try {
			em.persist(t);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return t.getId();
	}

	@Override
	public Integer modificar(ParametroDetalle t) throws Exception {
		em.merge(t);
		return t.getId();
	}

	@Override
	public Integer eliminar(ParametroDetalle t) throws Exception {
			em.remove(em.merge(t));
			return 1;
		}

	@SuppressWarnings("unchecked")
	@Override
	public List<ParametroDetalle> listar() throws Exception {
		List<ParametroDetalle> lstParDet = new ArrayList<ParametroDetalle>();
		try {
			 Query q= em.createQuery("SELECT p FROM ParametroDetalle p"); //query de la entidad del model
			 lstParDet =(List<ParametroDetalle>) q.getResultList();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return lstParDet;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public ParametroDetalle listarPorId(ParametroDetalle t) throws Exception {
		ParametroDetalle p = new ParametroDetalle();
		List<ParametroDetalle> lstParDet = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM ParametroDetalle p where p.id =?1");
			query.setParameter(1,t.getId());

			lstParDet = (List<ParametroDetalle>) query.getResultList();
			
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
	public ParametroDetalle mostrarValorDescripcion(String descripcion) {
		
		ParametroDetalle p = new ParametroDetalle();
	
		List<ParametroDetalle> lstParDet = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM ParametroDetalle p where p.descripcion =?1");
			query.setParameter(1,descripcion);

			lstParDet = (List<ParametroDetalle>) query.getResultList();
			
			if (lstParDet != null && !lstParDet.isEmpty()) {
				p = lstParDet.get(0);
			}
			
		} catch (Exception e) {
			throw e;
		}
		
		return p;
	}
	

}
