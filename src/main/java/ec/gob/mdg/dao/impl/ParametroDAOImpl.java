package ec.gob.mdg.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.dao.IParametroDAO;
import ec.gob.mdg.model.Parametro;

@Stateless
public class ParametroDAOImpl implements IParametroDAO, Serializable{


	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;
	
	@Override
	public Integer registrar(Parametro t) throws Exception {
		try {
			em.persist(t);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return t.getId();
	}

	@Override
	public Integer modificar(Parametro t) throws Exception {
		em.merge(t);
		return t.getId();
	}

	@Override
	public Integer eliminar(Parametro t) throws Exception {
		em.remove(em.merge(t));
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Parametro> listar() throws Exception {
		List<Parametro> lstParame = new ArrayList<Parametro>();
		try {
			 Query q= em.createQuery("SELECT p FROM Parametro p"); //query de la entidad del model
			 lstParame =(List<Parametro>) q.getResultList();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return lstParame;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public Parametro listarPorId(Parametro t) throws Exception {
		Parametro p = new Parametro();
		List<Parametro> lstParame = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Parametro p where p.id =?1");
			query.setParameter(1,t.getId());

			lstParame = (List<Parametro>) query.getResultList();
			
			if (lstParame != null && !lstParame.isEmpty()) {
				p = lstParame.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		return t;
	}

}
