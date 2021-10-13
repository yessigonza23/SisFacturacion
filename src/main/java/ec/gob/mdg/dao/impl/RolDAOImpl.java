package ec.gob.mdg.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.dao.IRolDAO;
import ec.gob.mdg.model.Rol;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioRol;

@Stateless
public class RolDAOImpl implements IRolDAO, Serializable{

	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;
	
	@Override
	public Integer registrar(Rol t) throws Exception {
		
		try {
			em.persist(t);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return t.getId();
	}
 
	@Override
	public Integer modificar(Rol t) throws Exception {
		em.merge(t);
		return t.getId();
	}

	@Override
	public Integer eliminar(Rol t) throws Exception {
		em.remove(em.merge(t));
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Rol> listar() throws Exception {
		Query q= em.createQuery("SELECT r FROM Rol r");
		List<Rol> lista =(List<Rol>) q.getResultList();
		return lista;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Rol listarPorId(Rol t) throws Exception {
		Query q = em.createQuery("FROM Rol r WHERE r.id = ?1");
		q.setParameter(1, t.getId());	
		List<Rol> lista = (List<Rol>) q.getResultList();
		
		return lista != null && !lista.isEmpty() ? lista.get(0) : new Rol();
	}
	
	@Override
	public Integer asignar(Usuario us, List<UsuarioRol> roles) {
		
		Query q = em.createNativeQuery("DELETE FROM usuario_rol ur where ur.id_usuario =?1");
		q.setParameter(1, us.getId());
		//q.setParameter(1, us.get)
		q.executeUpdate();
		
		int[] i = { 0 };
		roles.forEach(r -> {
			em.persist(r);
			if(i[0] % 100 == 0) {
				em.flush();
				em.clear();
			}
			i[0]++;
		});
		
		return i[0];
	}

	@SuppressWarnings("unchecked")
	@Override
	public Rol mostrarRolPorId(Integer idRol) {
		Rol r = new Rol();
		List<Rol> lista = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Rol c where c.id =?1");
			query.setParameter(1, idRol);

			lista = (List<Rol>) query.getResultList();

			if (lista != null && !lista.isEmpty()) {
				r = lista.get(0);
			}

		} catch (Exception e) {
			throw e;
		}
		return r;
	}

	
	
}


