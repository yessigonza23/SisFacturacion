package ec.gob.mdg.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.dao.IMenuRolDAO;
import ec.gob.mdg.model.Menu;
import ec.gob.mdg.model.MenuRol;
import ec.gob.mdg.model.Usuario;

@Stateless
public class MenuRolDAOImpl implements IMenuRolDAO, Serializable{

	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;
	
	
	@Override
	public Integer registrar(MenuRol t) throws Exception {
		try {
			em.persist(t);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}		
		return t.getId();
	}

	@Override
	public Integer modificar(MenuRol t) throws Exception {
		em.merge(t);
		return t.getId();
	}

	@Override
	public Integer eliminar(MenuRol t) throws Exception {
		em.remove(em.merge(t));
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MenuRol> listar() throws Exception {
		List<MenuRol> lista = new ArrayList<MenuRol>();
		try {
			Query query = em.createQuery("SELECT m FROM MenuRol m ORDER BY m.id ");
			lista = (List<MenuRol>) query.getResultList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}

	@Override
	public MenuRol listarPorId(MenuRol t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> listaMenuRolporUsuario(Usuario u) {
		
		List<Menu> lista = new ArrayList<Menu>();
		try {
			Query query = em.createQuery("SELECT DISTINCT m FROM MenuRol mr,Menu m WHERE mr.menu.id = m.id AND mr.rol.id in (SELECT ur.rol.id FROM UsuarioRol ur WHERE ur.usuario.id=?1) ORDER BY  m.tipo desc, m.submenu, m.nombre_completo");
			query.setParameter(1, u.getId());
			
			lista = (List<Menu>) query.getResultList();
			
		} catch (Exception e) {
			System.out.println("Hola este es el error " +e.getMessage());
		}
		
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<MenuRol> listaMenuRolAsignados(Integer idRol) {
		List<MenuRol> lista = new ArrayList<MenuRol>();
		try {
			Query query = em.createQuery("SELECT DISTINCT mr FROM MenuRol mr WHERE mr.rol.id =?1 ORDER BY 1");
			query.setParameter(1,idRol);
			
			lista = (List<MenuRol>) query.getResultList();
			
		} catch (Exception e) {
			System.out.println("Hola este es el error " +e.getMessage());
		}
		
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> listaMenuPendientes(Integer idRol) {
		List<Menu> lista = new ArrayList<Menu>();
		try {
			Query query = em.createQuery("SELECT DISTINCT m FROM Menu m WHERE m.id not in (SELECT mr.menu.id FROM MenuRol mr WHERE mr.rol.id=?1)");
			query.setParameter(1,idRol);
			
			lista = (List<Menu>) query.getResultList();
			
		} catch (Exception e) {
			System.out.println("Hola este es el error " +e.getMessage());
		}
		
		return lista;
	}
	@SuppressWarnings("unchecked")
	@Override
	public Integer eliminarMenuRol(Integer id_menu,Integer id_rol) {
		List<MenuRol> lista = new ArrayList<>();
		try {
			Query q = em.createQuery("SELECT m FROM MenuRol m WHERE m.menu.id =?1 and m.rol.id=?2"); 
			q.setParameter(1, id_menu);
			q.setParameter(2, id_rol);
		
			
			lista = (List<MenuRol>) q.getResultList();
			
			for(MenuRol m : lista) {
				em.remove(em.merge(m));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	

}
