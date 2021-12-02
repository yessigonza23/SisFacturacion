package ec.gob.mdg.control.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.control.dao.IFin_Cal_Ren_DTODAO;
import ec.gob.mdg.control.model.Fin_Cal_Ren_DTO;

@Stateless
public class Fin_Cal_Ren_DTODAOImpl implements IFin_Cal_Ren_DTODAO, Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "controlPU")
	private EntityManager em;
	
	@Override
	public Integer registrar(Fin_Cal_Ren_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer modificar(Fin_Cal_Ren_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer eliminar(Fin_Cal_Ren_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fin_Cal_Ren_DTO> listar() throws Exception {
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Cal_Ren_DTO> consultas = new ArrayList<>();
		try {
			Query q = em.createNativeQuery("SELECT *  FROM Fin_Cal_Ren"); 
			lista =  q.getResultList();
			lista.forEach(x -> {
				Fin_Cal_Ren_DTO f = new Fin_Cal_Ren_DTO();
				f.setCodigo(Integer.parseInt(String.valueOf(x[0])));
				f.setNombre(String.valueOf(x[1]));
				f.setRuc(String.valueOf(x[2]));
				f.setCodigo_renovacion(Integer.parseInt(String.valueOf(x[3])));
				f.setTipo(String.valueOf(x[4]));
				f.setActividades(String.valueOf(x[5]));
				f.setCodigo_recaudacion(String.valueOf(x[6]));
				f.setCantidad(Integer.parseInt(String.valueOf(x[7])));	
				consultas.add(f);
			});	
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return consultas;
	}

	@Override
	public Fin_Cal_Ren_DTO listarPorId(Fin_Cal_Ren_DTO t) throws Exception {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fin_Cal_Ren_DTO> listarEntidades() {
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Cal_Ren_DTO> consultas = new ArrayList<>();
		try {
			Query q = em.createNativeQuery("SELECT DISTINCT codigo,nombre  FROM Fin_Cal_Ren ORDER BY nombre"); 
			lista =  q.getResultList();
			lista.forEach(x -> {
				Fin_Cal_Ren_DTO f = new Fin_Cal_Ren_DTO();
				f.setCodigo(Integer.parseInt(String.valueOf(x[0])));
				f.setNombre(String.valueOf(x[1]));	
				consultas.add(f);
			});
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
		return consultas;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fin_Cal_Ren_DTO> listarServiciosPorCodigo(Integer codigo) {
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Cal_Ren_DTO> consultas = new ArrayList<>();
		try {
			Query q = em.createNativeQuery("SELECT codigo,nombre,ruc,codigo_renovacion,tipo,actividades,codigo_recaudacion,cantidad  FROM Fin_Cal_Ren WHERE codigo=?1"); 
			q.setParameter(1, codigo);			
			lista =  q.getResultList();
			lista.forEach(x -> {
				Fin_Cal_Ren_DTO f = new Fin_Cal_Ren_DTO();
				f.setCodigo(Integer.parseInt(String.valueOf(x[0])));
				f.setNombre(String.valueOf(x[1]));
				f.setRuc(String.valueOf(x[2]));
				f.setCodigo_renovacion(Integer.parseInt(String.valueOf(x[3])));
				f.setTipo(String.valueOf(x[4]));
				f.setActividades(String.valueOf(x[5]));
				f.setCodigo_recaudacion(String.valueOf(x[6]));
				f.setCantidad(Integer.parseInt(String.valueOf(x[7])));	
				consultas.add(f);
			});
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
		return consultas;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Fin_Cal_Ren_DTO mostrarEntidad(Integer codigo) {
		Fin_Cal_Ren_DTO fin = new Fin_Cal_Ren_DTO();
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Cal_Ren_DTO> consultas = new ArrayList<>();
		try {
			Query q = em.createNativeQuery("SELECT codigo,nombre,ruc,codigo_renovacion FROM Fin_Cal_Ren WHERE codigo=?1"); 
			q.setParameter(1, codigo);
			lista =  q.getResultList();
			lista.forEach(x -> {
				Fin_Cal_Ren_DTO f = new Fin_Cal_Ren_DTO();
				f.setCodigo(Integer.parseInt(String.valueOf(x[0])));
				f.setNombre(String.valueOf(x[1]));
				f.setRuc(String.valueOf(x[2]));
				f.setCodigo_renovacion(Integer.parseInt(String.valueOf(x[3])));
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

}
