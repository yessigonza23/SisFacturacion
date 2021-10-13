package ec.gob.mdg.control.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


import ec.gob.mdg.control.dao.IFin_Cat_Act_Sit_Veh_Java_DTODAO;
import ec.gob.mdg.control.model.Fin_Cat_Act_Sit_Veh_Java_DTO;

@Stateless
public class Fin_Cat_Act_Sit_Veh_Java_DTODAOImpl implements IFin_Cat_Act_Sit_Veh_Java_DTODAO, Serializable{

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "controlPU")
	private EntityManager em;

	@Override
	public Integer registrar(Fin_Cat_Act_Sit_Veh_Java_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer modificar(Fin_Cat_Act_Sit_Veh_Java_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer eliminar(Fin_Cat_Act_Sit_Veh_Java_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fin_Cat_Act_Sit_Veh_Java_DTO> listar() throws Exception {
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Cat_Act_Sit_Veh_Java_DTO> consultas = new ArrayList<>();
		try {
			Query q = em.createNativeQuery("SELECT *  FROM Fin_Cat_Act_Sit_Veh_Java"); 
			lista =  q.getResultList();
			lista.forEach(x -> {
				Fin_Cat_Act_Sit_Veh_Java_DTO f = new Fin_Cat_Act_Sit_Veh_Java_DTO();
				f.setCodigo(Integer.parseInt(String.valueOf(x[0])));
				f.setNombre(String.valueOf(x[1]));
				f.setRuc(String.valueOf(x[2]));
				f.setCodigo_renovacion(Integer.parseInt(String.valueOf(x[3])));
				f.setCategoria_actualkg(Double.parseDouble(String.valueOf(x[4])));
				f.setCategoria_nuevakg(Double.parseDouble(String.valueOf(x[5])));
				f.setCategoria_actual(Integer.parseInt(String.valueOf(x[6])));
				f.setCategoria_nueva(Integer.parseInt(String.valueOf(x[7])));
				f.setTipo(String.valueOf(x[8]));
				f.setActividades(String.valueOf(x[9]));
				f.setCodigo_recaudacion(String.valueOf(x[10]));
				f.setCantidad(Integer.parseInt(String.valueOf(x[11])));	
				consultas.add(f);
			});			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}				
		return consultas;
	}

	@Override
	public Fin_Cat_Act_Sit_Veh_Java_DTO listarPorId(Fin_Cat_Act_Sit_Veh_Java_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fin_Cat_Act_Sit_Veh_Java_DTO> listarEntidades() {
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Cat_Act_Sit_Veh_Java_DTO> consultas = new ArrayList<>();
		try {
			Query q = em.createNativeQuery("SELECT codigo,nombre  FROM Fin_Cat_Act_Sit_Veh_Java ORDER BY nombre"); 
			lista =  q.getResultList();
			lista.forEach(x -> {
				Fin_Cat_Act_Sit_Veh_Java_DTO f = new Fin_Cat_Act_Sit_Veh_Java_DTO();
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
	public List<Fin_Cat_Act_Sit_Veh_Java_DTO> listarServiciosPorCodigo(Integer codigo) {
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Cat_Act_Sit_Veh_Java_DTO> consultas = new ArrayList<>();
		try {
			Query q = em.createNativeQuery("SELECT *  FROM Fin_Cat_Act_Sit_Veh_Java WHERE codigo=?1"); 
			q.setParameter(1, codigo);
			lista =  q.getResultList();
			lista.forEach(x -> {
				Fin_Cat_Act_Sit_Veh_Java_DTO f = new Fin_Cat_Act_Sit_Veh_Java_DTO();
				f.setCodigo(Integer.parseInt(String.valueOf(x[0])));
				f.setNombre(String.valueOf(x[1]));
				f.setRuc(String.valueOf(x[2]));
				f.setCodigo_renovacion(Integer.parseInt(String.valueOf(x[3])));
				f.setCategoria_actualkg(Double.parseDouble(String.valueOf(x[4])));
				f.setCategoria_nuevakg(Double.parseDouble(String.valueOf(x[5])));
				f.setCategoria_actual(Integer.parseInt(String.valueOf(x[6])));
				f.setCategoria_nueva(Integer.parseInt(String.valueOf(x[7])));
				f.setTipo(String.valueOf(x[8]));
				f.setActividades(String.valueOf(x[9]));
				f.setCodigo_recaudacion(String.valueOf(x[10]));
				f.setCantidad(Integer.parseInt(String.valueOf(x[11])));	
				f.setValor(Double.parseDouble(String.valueOf(x[12])));
				consultas.add(f);
			});			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}				
		return consultas;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Fin_Cat_Act_Sit_Veh_Java_DTO mostrarEntidad(Integer codigo) {
		Fin_Cat_Act_Sit_Veh_Java_DTO fin = new Fin_Cat_Act_Sit_Veh_Java_DTO();
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Cat_Act_Sit_Veh_Java_DTO> consultas = new ArrayList<>();
		try {
			Query q = em.createNativeQuery("SELECT codigo,nombre,ruc,codigo_renovacion FROM Fin_Cat_Act_Sit_Veh_Java WHERE codigo=?1"); 
			q.setParameter(1, codigo);
			lista =  q.getResultList();
			lista.forEach(x -> {
				Fin_Cat_Act_Sit_Veh_Java_DTO f = new Fin_Cat_Act_Sit_Veh_Java_DTO();
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
