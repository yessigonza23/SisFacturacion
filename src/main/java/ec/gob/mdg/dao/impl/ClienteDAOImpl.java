package ec.gob.mdg.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.dao.IClienteDAO;
import ec.gob.mdg.model.Cliente;

@Stateless
public class ClienteDAOImpl implements IClienteDAO, Serializable {

	private static final long serialVersionUID = 1895800455279460344L;
	
	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;
	
	@Override
	public Integer registrar(Cliente t) throws Exception {
		try {
			em.persist(t);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return t.getId();
	}

	@Override
	public Integer modificar(Cliente t) throws Exception {
		em.merge(t);
		return t.getId();
	}

	@Override
	public Integer eliminar(Cliente t) throws Exception {
		em.remove(em.merge(t));
		return 1;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> listar() throws Exception {
		
		List<Cliente> lstCliente = new ArrayList<Cliente>();
		try {
			 Query q= em.createQuery("SELECT c FROM Cliente c ORDER BY c.nombre"); 
			 lstCliente =(List<Cliente>) q.getResultList();
		} catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return lstCliente;
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public Cliente listarPorId(Cliente t) throws Exception {
		List<Cliente> lstCliente = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Cliente c where c.ci =?1");
			query.setParameter(1,t.getCi());
			lstCliente = (List<Cliente>) query.getResultList();
			if (lstCliente != null && !lstCliente.isEmpty()) {
			}
		} catch (Exception e) {
			throw e;
		}
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Cliente ClientePorCiParametro(String ci) {
		Cliente cli = new Cliente();
		List<Cliente> lstCliente = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Cliente c where c.ci =?1");
			query.setParameter(1,ci);
			lstCliente = (List<Cliente>) query.getResultList();
			if (lstCliente != null && !lstCliente.isEmpty()) {
				cli = lstCliente.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return cli;
	}





}
