package ec.gob.mdg.control.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.control.dao.IFin_Reptecnicos_DTODAO;
import ec.gob.mdg.control.model.Fin_Reptecnicos_DTO;
@Stateless
public class Fin_Reptecnicos_DTODAOImpl implements IFin_Reptecnicos_DTODAO, Serializable {

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "controlPU")
	private EntityManager em;

	@Override
	public Integer registrar(Fin_Reptecnicos_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer modificar(Fin_Reptecnicos_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer eliminar(Fin_Reptecnicos_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fin_Reptecnicos_DTO> listar() throws Exception {
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Reptecnicos_DTO> consultas = new ArrayList<>();
		try {		
			Query q = em.createNativeQuery("SELECT *  FROM Fin_Reptecnicos"); 
			lista =  q.getResultList();
			lista.forEach(x -> {
				Fin_Reptecnicos_DTO f = new Fin_Reptecnicos_DTO();				
				f.setIdtec(Integer.parseInt(String.valueOf(x[0])));
				f.setCedulatec(String.valueOf(x[1]));
				f.setNombrestec(String.valueOf(x[2]));
				f.setCodigo_calren(Integer.parseInt(String.valueOf(x[3])));
				f.setRecaudacion(String.valueOf(x[4]));
				f.setCantidad(Integer.parseInt(String.valueOf(x[5])));
				f.setTipo(String.valueOf(x[6]));
				f.setPunto(Integer.parseInt(String.valueOf(x[7])));
				consultas.add(f);
			});			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return consultas;
	}

	@Override
	public Fin_Reptecnicos_DTO listarPorId(Fin_Reptecnicos_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;		
	}	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Fin_Reptecnicos_DTO> listarRepTecnico() {
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Reptecnicos_DTO> consultas = new ArrayList<>();
		try {			
			Query q = em.createNativeQuery("SELECT DISTINCT idtec, cedulatec, nombrestec  FROM Fin_Reptecnicos ORDER BY nombrestec"); 
			lista =  q.getResultList();
			lista.forEach(x -> {
				Fin_Reptecnicos_DTO f = new Fin_Reptecnicos_DTO();	
				f.setIdtec(Integer.parseInt(String.valueOf(x[0])));
				f.setCedulatec(String.valueOf(x[1]));
				f.setNombrestec(String.valueOf(x[2]));
				consultas.add(f);
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return consultas;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fin_Reptecnicos_DTO> listarCalRenRepTecnicoPorIdTec(Integer idtec) {
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Reptecnicos_DTO> consultas = new ArrayList<>();
		try {	
			Query q = em.createNativeQuery("SELECT idtec,cedulatec,nombrestec,codigo_calren  FROM Fin_Reptecnicos WHERE Idtec =?1 ");
			q.setParameter(1, idtec);
			lista =  q.getResultList();
			lista.forEach(x -> {
				Fin_Reptecnicos_DTO f = new Fin_Reptecnicos_DTO();			
				f.setIdtec(Integer.parseInt(String.valueOf(x[0])));
				f.setCedulatec(String.valueOf(x[1]));
				f.setNombrestec(String.valueOf(x[2]));
				f.setCodigo_calren(Integer.parseInt(String.valueOf(x[3])));					
				consultas.add(f);
			});		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return consultas;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Fin_Reptecnicos_DTO mostarRepTecnico(Integer idtec) {
		Fin_Reptecnicos_DTO fin = new Fin_Reptecnicos_DTO();
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Reptecnicos_DTO> consultas = new ArrayList<>();
		try {			
			Query q = em.createNativeQuery("SELECT DISTINCT idtec, cedulatec, nombrestec   FROM Fin_Reptecnicos WHERE Idtec =?1 ");
			q.setParameter(1, idtec);
			lista =  q.getResultList();
			lista.forEach(x -> {
				Fin_Reptecnicos_DTO f = new Fin_Reptecnicos_DTO();				
				f.setIdtec(Integer.parseInt(String.valueOf(x[0])));
				f.setCedulatec(String.valueOf(x[1]));
				f.setNombrestec(String.valueOf(x[2]));				
				consultas.add(f);
			});
			if (consultas != null && !consultas.isEmpty()) {
				fin = consultas.get(0);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return fin;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fin_Reptecnicos_DTO> listarCalRenRepTecnicoPorIdTecFact(Integer idtec, Integer punto) {
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Reptecnicos_DTO> consultas = new ArrayList<>();
		try {
			Query q = em.createNativeQuery("SELECT idtec,cedulatec,nombrestec,codigo_calren,recaudacion,cantidad,tipo,punto  FROM Fin_Reptecnicos WHERE Idtec =?1 and Aux ='S' and punto = ?2 ");
			q.setParameter(1, idtec);
			q.setParameter(2, punto);
			lista =  q.getResultList();
			lista.forEach(x -> {
				Fin_Reptecnicos_DTO f = new Fin_Reptecnicos_DTO();
				f.setIdtec(Integer.parseInt(String.valueOf(x[0])));
				f.setCedulatec(String.valueOf(x[1]));
				f.setNombrestec(String.valueOf(x[2]));
				f.setCodigo_calren(Integer.parseInt(String.valueOf(x[3])));
				f.setRecaudacion(String.valueOf(x[4]));
				f.setCantidad(Integer.parseInt(String.valueOf(x[5])));
				f.setTipo(String.valueOf(x[6]));
				f.setPunto(Integer.parseInt(String.valueOf(x[7])));
				consultas.add(f);
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return consultas;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fin_Reptecnicos_DTO> listarCalRenRepTecnicoPorIdTecRec(Integer idtec, Integer punto) {
		
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Reptecnicos_DTO> consultas = new ArrayList<>();
		try {
			Query q = em.createNativeQuery("SELECT idtec, cedulatec,nombrestec,recaudacion,count(*) cantidad  FROM Fin_Reptecnicos WHERE Idtec =?1 and Aux ='S' and punto = ?2 GROUP BY idtec, cedulatec,nombrestec,recaudacion ");
			q.setParameter(1, idtec);
			q.setParameter(2, punto);
			lista =  q.getResultList();
			lista.forEach(x -> {
				Fin_Reptecnicos_DTO f = new Fin_Reptecnicos_DTO();				
				f.setIdtec(Integer.parseInt(String.valueOf(x[0])));
				f.setCedulatec(String.valueOf(x[1]));
				f.setNombrestec(String.valueOf(x[2]));
				f.setRecaudacion(String.valueOf(x[3]));
				f.setCantidad(Integer.parseInt(String.valueOf(x[4])));			
				consultas.add(f);
			});		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return consultas;
		}
}
