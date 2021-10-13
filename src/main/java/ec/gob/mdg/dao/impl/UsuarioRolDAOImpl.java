package ec.gob.mdg.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.dao.IUsuarioRolDAO;
import ec.gob.mdg.model.Rol;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioRol;

@Stateless
public class UsuarioRolDAOImpl implements IUsuarioRolDAO, Serializable {

	private static final long serialVersionUID = -3887932062881535461L;
	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;

	@Override
	public Integer registrar(UsuarioRol t) throws Exception {
		try {
			em.persist(t);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return t.getId();
	}

	@Override
	public Integer modificar(UsuarioRol t) throws Exception {
		em.merge(t);
		return t.getId();
	}

	@Override
	public Integer eliminar(UsuarioRol t) throws Exception {
		em.remove(em.merge(t));
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioRol> listar() throws Exception {
		List<UsuarioRol> lstUsuRol = new ArrayList<UsuarioRol>();
		try {
			Query q = em.createQuery("SELECT ur FROM UsuarioRol ur"); // query de la entidad del model
			lstUsuRol = (List<UsuarioRol>) q.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lstUsuRol;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public UsuarioRol listarPorId(UsuarioRol t) throws Exception {
		UsuarioRol usuRol = new UsuarioRol();
		List<UsuarioRol> lstUsuRol = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM UsuarioRol ur where ur.id =?1");
			query.setParameter(1, t.getId());
			lstUsuRol = (List<UsuarioRol>) query.getResultList();
			if (lstUsuRol != null && !lstUsuRol.isEmpty()) {
				usuRol = lstUsuRol.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UsuarioRol> listarRolesPorUsuario(Usuario us) {
		List<UsuarioRol> lista = new ArrayList<>();
		try {
			Query query = em.createQuery("SELECT ur FROM UsuarioRol ur where ur.usuario.id =?1");
			query.setParameter(1, us.getId());
			lista = (List<UsuarioRol>) query.getResultList();
		} catch (Exception e) {

		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Rol> listarRolesPendientes(Usuario us) {
		List<Rol> lista = new ArrayList<>();
		try {
			Query query = em.createQuery(
					"SELECT r FROM Rol r WHERE r.id not in (SELECT ur.rol.id FROM UsuarioRol ur WHERE ur.usuario.id = ?1) ORDER BY r.tipo"); 
			query.setParameter(1, us.getId());
			lista = (List<Rol>) query.getResultList();
		} catch (Exception e) {
			// TODO: handle exception
		}
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer eliminarRolUsuario(Integer id_usuario, Integer id_rol) {
		
		List<UsuarioRol> lista = new ArrayList<>();
		try {
			Query q = em.createQuery("FROM UsuarioRol m WHERE m.usuario.id =?1 and m.rol.id=?2");
			q.setParameter(1, id_usuario);
			q.setParameter(2, id_rol);
			lista = (List<UsuarioRol>) q.getResultList();
						
			for (UsuarioRol m : lista) {
			
				em.remove(em.merge(m));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}

}
