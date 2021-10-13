package ec.gob.mdg.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.dao.IUsuarioPuntoDAO;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;

@Stateless
public class UsuarioPuntoDAOImpl implements IUsuarioPuntoDAO, Serializable{

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;
	
	@Override
	public Integer registrar(UsuarioPunto t) throws Exception {
		try {
			em.persist(t);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return t.getId();
	}
	@Override
	public Integer modificar(UsuarioPunto t) throws Exception {
		em.merge(t);
		return t.getId();
	}

	@Override
	public Integer eliminar(UsuarioPunto t) throws Exception {
		em.remove(em.merge(t));
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioPunto> listar() throws Exception {
		List<UsuarioPunto> lstUsuPto = new ArrayList<UsuarioPunto>();
		try {
			 Query q= em.createQuery("SELECT u FROM UsuarioPunto u"); //query de la entidad del model
			 lstUsuPto =(List<UsuarioPunto>) q.getResultList();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return lstUsuPto;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public UsuarioPunto listarPorId(UsuarioPunto t) throws Exception {
		UsuarioPunto u = new UsuarioPunto();
		List<UsuarioPunto> lstUsuPto = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM UsuarioPunto u where u.id =?1");
			query.setParameter(1,t.getId());

			lstUsuPto = (List<UsuarioPunto>) query.getResultList();
			
			if (lstUsuPto != null && !lstUsuPto.isEmpty()) {
				u = lstUsuPto.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		return t;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public UsuarioPunto listarUsuarioPuntoPorIdLogueado(Usuario usu) {
		
		UsuarioPunto u = new UsuarioPunto();
		List<UsuarioPunto> lstUsuPto = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM UsuarioPunto u WHERE u.usuario.id = ?1  and u.estado='A'");
			query.setParameter(1, usu.getId());

			lstUsuPto = (List<UsuarioPunto>) query.getResultList();
			
			if (lstUsuPto != null && !lstUsuPto.isEmpty()) {
				u = lstUsuPto.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		
		return u;
	}
	@SuppressWarnings("unchecked")
	@Override
	public UsuarioPunto mostrarUsuarioPuntoPorIdUsuario(Integer id_usuario) {
		UsuarioPunto u = new UsuarioPunto();
		List<UsuarioPunto> lstUsuPto = new ArrayList<>();

		
		try {
			Query query = em.createQuery("FROM UsuarioPunto u WHERE u.usuario.id = ?1 ");
			query.setParameter(1, id_usuario);

			lstUsuPto = (List<UsuarioPunto>) query.getResultList();
			
			if (lstUsuPto != null && !lstUsuPto.isEmpty()) {
				u = lstUsuPto.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		return u;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioPunto> listarUsuarioPunto(Usuario u) {
		List<UsuarioPunto> lstUsuPto = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM UsuarioPunto u where u.usuario.id =?1 ORDER BY u.fechainicio DESC");
			query.setParameter(1,u.getId());

			lstUsuPto = (List<UsuarioPunto>) query.getResultList();
			
		} catch (Exception e) {
			throw e;
		}
		return lstUsuPto;
	}
	@SuppressWarnings("unchecked")
	@Override
	public UsuarioPunto usuarioPuntoPorId(Integer id_usuariopunto) {
		UsuarioPunto u = new UsuarioPunto();
		List<UsuarioPunto> lstUsuPto = new ArrayList<>();
		
		try {
			Query query = em.createQuery("FROM UsuarioPunto u WHERE u.id = ?1 ");
			query.setParameter(1, id_usuariopunto);

			lstUsuPto = (List<UsuarioPunto>) query.getResultList();
			
			if (lstUsuPto != null && !lstUsuPto.isEmpty()) {
				u = lstUsuPto.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		return u;
	}

}
