package ec.gob.mdg.control.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.control.dao.ISanciones_Admin_DTODAO;
import ec.gob.mdg.control.model.Sanciones_Admin_DTO;

@Stateless
public class Sanciones_Admin_DTODAOImpl implements ISanciones_Admin_DTODAO, Serializable{
	private static final long serialVersionUID = 1L;
	@PersistenceContext(unitName = "contraPU")
	private EntityManager em;
	
	@Override
	public Integer registrar(Sanciones_Admin_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer modificar(Sanciones_Admin_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer eliminar(Sanciones_Admin_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Sanciones_Admin_DTO> listar() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Sanciones_Admin_DTO listarPorId(Sanciones_Admin_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Sanciones_Admin_DTO> listarEntidadesSanciones() {
		List<Object[]> lista = new ArrayList<>();
		List<Sanciones_Admin_DTO> consultas = new ArrayList<>();
		try {
			Query q = em.createNativeQuery("SELECT DISTINCT codigo, ruc, nombre  FROM V_CON_SANCIONES_ADMIN ORDER BY Nombre");
			lista =  q.getResultList();
			lista.forEach(x -> {
				Sanciones_Admin_DTO f = new Sanciones_Admin_DTO();
				f.setCodigo(Integer.parseInt(String.valueOf(x[0])));
				f.setRuc(String.valueOf(x[1]));				
				f.setNombre(String.valueOf(x[2]));							
				consultas.add(f);
			});
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return consultas;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Sanciones_Admin_DTO> listarSancionesPorCodigo(String ruc) {
		List<Object[]> lista = new ArrayList<>();
		List<Sanciones_Admin_DTO> consultas = new ArrayList<>();			
		try {
			Query q = em.createNativeQuery("SELECT codigo_empresa, ruc, nombre, numero_juicio, valor, codigo_recaudacion  FROM V_CON_SANCIONES_ADMIN WHERE ruc=?1"); 
			q.setParameter(1, ruc);
			lista =  q.getResultList();
			lista.forEach(x -> {
				Sanciones_Admin_DTO f = new Sanciones_Admin_DTO();
				f.setCodigo_empresa(String.valueOf(x[0]));		
				f.setRuc(String.valueOf(x[1]));				
				f.setNombre(String.valueOf(x[2]));
				f.setNumero_juicio(String.valueOf(x[3]));		
				f.setValor(Double.parseDouble(String.valueOf(x[4])));
				f.setCodigo_recaudacion(String.valueOf(x[5]));
				consultas.add(f);
			});			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}				
		return consultas;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Sanciones_Admin_DTO mostrarEntidad(String ruc) {		
		Sanciones_Admin_DTO fin = new Sanciones_Admin_DTO();
		List<Object[]> lista = new ArrayList<>();
		List<Sanciones_Admin_DTO> consultas = new ArrayList<>();		
		try {			
			Query q = em.createNativeQuery("SELECT DISTINCT codigo_empresa,nombre,ruc FROM V_CON_SANCIONES_ADMIN WHERE ruc=?1"); 
			q.setParameter(1, ruc);
			lista =  q.getResultList();
			lista.forEach(x -> {
				Sanciones_Admin_DTO f = new Sanciones_Admin_DTO();
				f.setCodigo_empresa(String.valueOf(x[0]));
				f.setNombre(String.valueOf(x[1]));
				f.setRuc(String.valueOf(x[2]));				
				consultas.add(f);
			});

			if (consultas != null && !consultas.isEmpty()) {
				fin = consultas.get(0);
			}
		} catch (Exception e) {
			throw e;
		}
		return fin;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Sanciones_Admin_DTO> listarSancionesPorCodigoFact(String ruc,Integer punto) {
		List<Object[]> lista = new ArrayList<>();
		List<Sanciones_Admin_DTO> consultas = new ArrayList<>();		
		try {
			Query q = em.createNativeQuery("SELECT codigo_empresa, ruc, nombre, numero_juicio, valor, codigo_recaudacion,cantidad,aux FROM V_CON_SANCIONES_ADMIN WHERE ruc=?1 and aux=?2 and punto=?3"); 
			q.setParameter(1, ruc);
			q.setParameter(2, "S");
			q.setParameter(3, punto);
			lista =  q.getResultList();
			lista.forEach(x -> {
				Sanciones_Admin_DTO f = new Sanciones_Admin_DTO();
				f.setCodigo_empresa(String.valueOf(x[0]));		
				f.setRuc(String.valueOf(x[1]));				
				f.setNombre(String.valueOf(x[2]));
				f.setNumero_juicio(String.valueOf(x[3]));		
				f.setValor(Double.parseDouble(String.valueOf(x[4])));
				f.setCodigo_recaudacion(String.valueOf(x[5]));
				f.setCantidad(Integer.parseInt(String.valueOf(x[6])));
				f.setAux(Boolean.parseBoolean(String.valueOf(x[7])));
				consultas.add(f);
			});
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}				
		return consultas;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Sanciones_Admin_DTO> listarSancionesPorCodigoFactRec(String ruc,Integer punto) {
		List<Object[]> lista = new ArrayList<>();
		List<Sanciones_Admin_DTO> consultas = new ArrayList<>();			
		try {
			Query q = em.createNativeQuery("SELECT codigo_empresa, ruc, nombre,valor, codigo_recaudacion,count(*) cantidad FROM V_CON_SANCIONES_ADMIN WHERE ruc=?1 and aux=?2 and punto =?3 GROUP BY codigo_empresa, ruc, nombre,valor, codigo_recaudacion"); 
			q.setParameter(1, ruc);
			q.setParameter(2, "S");
			q.setParameter(3, punto);
			lista =  q.getResultList();
			lista.forEach(x -> {
				Sanciones_Admin_DTO f = new Sanciones_Admin_DTO();
				f.setCodigo_empresa(String.valueOf(x[0]));		
				f.setRuc(String.valueOf(x[1]));				
				f.setNombre(String.valueOf(x[2]));		
				f.setValor(Double.parseDouble(String.valueOf(x[3])));
				f.setCodigo_recaudacion(String.valueOf(x[4]));
				f.setCantidad(Integer.parseInt(String.valueOf(x[5])));
				consultas.add(f);
			});
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}				
		return consultas;
	}

}
