package ec.gob.mdg.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.dao.IMenuDAO;
import ec.gob.mdg.model.Menu;

@Stateless
public class MenuDAOImpl implements IMenuDAO, Serializable{

	
	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;
	
	@Override
	public Integer registrar(Menu t) throws Exception {
		try {
			em.persist(t);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}		
		return t.getId();
	}

	@Override
	public Integer modificar(Menu t) throws Exception {
		em.merge(t);
		return t.getId();
	}

	@Override
	public Integer eliminar(Menu t) throws Exception {
		em.remove(em.merge(t));
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Menu> listar() throws Exception {
		List<Menu> lista = new ArrayList<Menu>();
		try {
			Query query = em.createQuery("SELECT m FROM Menu m ORDER BY m.id ");
			
			lista = (List<Menu>) query.getResultList();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return lista;
	}

	@Override
	public Menu listarPorId(Menu t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Menu mostraMenu(Integer id_menu) {
		List<Menu> lista = new ArrayList<Menu>();
		Menu m = new Menu();
		try {
			Query query = em.createQuery("SELECT DISTINCT m FROM Menu m WHERE m.id=?1 ORDER BY  m.tipo desc, m.submenu, m.nombre_completo ");
			query.setParameter(1, id_menu);
			
			lista = (List<Menu>) query.getResultList();
			if (lista != null && !lista.isEmpty()) {
				m=lista.get(0);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return m;
	}



}
