package ec.gob.mdg.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.dao.IUsuarioDAO;
import ec.gob.mdg.model.Usuario;

@Stateless
public class UsuarioDAOImpl implements IUsuarioDAO, Serializable{

	private static final long serialVersionUID = 8645966089941526751L;
	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;
	
	@Override
	public Integer registrar(Usuario usu) throws Exception {
		try {
			em.persist(usu);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		
		return usu.getId();
	}

	@Override
	public Integer modificar(Usuario usu) throws Exception {
		em.merge(usu);
		return usu.getId();
	}

	@Override
	public Integer eliminar(Usuario usu) throws Exception {
		em.remove(em.merge(usu));
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> listar() throws Exception {
		List<Usuario> lista = new ArrayList<Usuario>();
		try {
			 Query q= em.createQuery("SELECT u FROM Usuario u"); //query de la entidad del model
			 lista =(List<Usuario>) q.getResultList();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Usuario listarPorId(Usuario usu) throws Exception {
		Usuario us = new Usuario();
		List<Usuario> lista = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Usuario u where u.id =?1");
			query.setParameter(1, usu.getId());

			lista = (List<Usuario>) query.getResultList();

			if (lista != null && !lista.isEmpty()) {
				us = lista.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		return us;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String traerPassHashed(String usu) {
		Usuario usuario = new Usuario();
		try {
			//SQL | select contrasena from usuario whrere usuario = '';
			Query query = em.createQuery("FROM Usuario u WHERE u.username = ?1");
			query.setParameter(1, usu);
			
			List<Usuario> lista = query.getResultList();
			if (!lista.isEmpty()) {
				usuario = lista.get(0);
			}
		}catch(Exception e) {
			throw e;
		}
		return usuario != null && usuario.getId() != null? usuario.getContrasena() : "$2y$12$BkSuESEFvw5Ce2jQnWkr/eD2CH/9mVwPpV2s8LQL9PeW6xAOs3R9.";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Usuario leerPorNombreUsuario(String usu) {
		Usuario usuario = new Usuario();
		List<Usuario> lista = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Usuario u where u.username =?1");
			query.setParameter(1, usu);

			lista = (List<Usuario>) query.getResultList();

			if (lista != null && !lista.isEmpty()) {
				usuario = lista.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		return usuario;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Usuario mostrarUsuarioPorId(Integer id_usuario) {
		Usuario us = new Usuario();
		List<Usuario> lista = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Usuario u where u.id =?1");
			query.setParameter(1, id_usuario);

			lista = (List<Usuario>) query.getResultList();

			if (lista != null && !lista.isEmpty()) {
				us = lista.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		return us;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public boolean validaUsuarioCedula(String cedula) {
		Usuario us = new Usuario();
		boolean valida=false;
		List<Usuario> lista = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Usuario u where u.ci =?1");
			query.setParameter(1, cedula);

			lista = (List<Usuario>) query.getResultList();

			if (lista != null && !lista.isEmpty()) {
				us = lista.get(0);
				valida=true;
			}

		} catch (Exception e) {
			throw e;
		}
		return valida;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Usuario muestraUsuarioPorCi(String ci) {
		Usuario us = new Usuario();
		List<Usuario> lista = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Usuario u where u.ci =?1");
			query.setParameter(1, ci);

			lista = (List<Usuario>) query.getResultList();

			if (lista != null && !lista.isEmpty()) {
				us = lista.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		
		
		return us;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public boolean existeUsuario(String us) {
		
		boolean respuesta=false;
		
		Usuario usuario = new Usuario();
		List<Usuario> lista = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Usuario u where u.username =?1");
			query.setParameter(1, us);

			lista = (List<Usuario>) query.getResultList();

			if (lista != null && !lista.isEmpty()) {
				usuario = lista.get(0);
				respuesta=true;
			}

		} catch (Exception e) {
			throw e;
		}
		return respuesta ;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> listarAnalistas() {
		List<Usuario> lista = new ArrayList<Usuario>();
		try {
			 Query q= em.createQuery("SELECT u FROM Usuario u WHERE u.tipo not in ('A','R','J')"); //query de la entidad del model
			 lista =(List<Usuario>) q.getResultList();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}	

}
