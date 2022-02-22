package ec.gob.mdg.control.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.control.dao.IFin_Importaciones_No_DTODAO;
import ec.gob.mdg.control.model.Fin_Importaciones_No_DTO;

@Stateless
public class Fin_Importaciones_No_DTODAOImpl implements IFin_Importaciones_No_DTODAO, Serializable {

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "controlPU")
	private EntityManager em;

	@Override
	public Integer registrar(Fin_Importaciones_No_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer modificar(Fin_Importaciones_No_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer eliminar(Fin_Importaciones_No_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fin_Importaciones_No_DTO> listar() throws Exception {
	//	System.out.println("entra a lista dao fin importaciones no ");

		List<Object[]> lista = new ArrayList<>();
		List<Fin_Importaciones_No_DTO> consultas = new ArrayList<>();
		try {
			Query q = em.createNativeQuery("SELECT *  FROM Fin_Importaciones_No "); // query de la entidad del model
			lista =  q.getResultList();
			lista.forEach(x -> {
				Fin_Importaciones_No_DTO f = new Fin_Importaciones_No_DTO();
				f.setCod_entidad(String.valueOf(x[1]));				
				f.setNom_entidad(String.valueOf(x[1]));
				f.setRuc(String.valueOf(x[3]));
				f.setNum_solicitud(String.valueOf(x[4]));
				f.setValor_pago(Double.parseDouble(String.valueOf(x[5])));
				f.setCantidad(Integer.parseInt(String.valueOf(x[6])));
				consultas.add(f);
			});
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return consultas;
	}

	@Override
	public Fin_Importaciones_No_DTO listarPorId(Fin_Importaciones_No_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fin_Importaciones_No_DTO> listarEntidadesImpNo() {
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Importaciones_No_DTO> consultas = new ArrayList<>();
		try {
			Query q = em.createNativeQuery("SELECT DISTINCT Cod_entidad, Nom_entidad, ruc  FROM Fin_importaciones_no ORDER BY Nom_entidad "); // query de la entidad del model
			lista =  q.getResultList();
			lista.forEach(x -> {
				Fin_Importaciones_No_DTO f = new Fin_Importaciones_No_DTO();
				f.setCod_entidad(String.valueOf(x[0]));				
				f.setNom_entidad(String.valueOf(x[1]));
				f.setRuc(String.valueOf(x[2]));
				consultas.add(f);
			});
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return consultas;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fin_Importaciones_No_DTO> listarImportacionesNoPorCodigo(String codigo) {
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Importaciones_No_DTO> consultas = new ArrayList<>();
		try {
			Query q = em.createNativeQuery("SELECT cod_entidad,nom_entidad,ruc,num_solicitud,valor_pago,recaudacion,cantidad FROM Fin_Importaciones_no WHERE Cod_entidad=?1  and aux is null "); // query de la entidad del model
			q.setParameter(1, codigo);
			lista =  q.getResultList();
			lista.forEach(x -> {
				Fin_Importaciones_No_DTO f = new Fin_Importaciones_No_DTO();
				f.setCod_entidad(String.valueOf(x[0]));				
				f.setNom_entidad(String.valueOf(x[1]));
				f.setRuc(String.valueOf(x[2]));
				f.setNum_solicitud(String.valueOf(x[3]));
				f.setValor_pago(Double.parseDouble(String.valueOf(x[4])));
				f.setRecaudacion(String.valueOf(x[5]));
				f.setCantidad(Integer.parseInt(String.valueOf(x[6])));
				consultas.add(f);
			});
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return consultas;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Fin_Importaciones_No_DTO mostrarEntidadImportacionNo(String codigo) {
		Fin_Importaciones_No_DTO fin = new Fin_Importaciones_No_DTO();
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Importaciones_No_DTO> consultas = new ArrayList<>();
		try {
			Query q = em.createNativeQuery("SELECT DISTINCT cod_entidad, nom_entidad, ruc, num_solicitud, valor_pago, recaudacion  FROM Fin_Importaciones_no 	WHERE Cod_entidad=?1 "); // query de la entidad del model
			q.setParameter(1, codigo);
			lista =  q.getResultList();
			lista.forEach(x -> {
				Fin_Importaciones_No_DTO f = new Fin_Importaciones_No_DTO();
				f.setCod_entidad(String.valueOf(x[0]));				
				f.setNom_entidad(String.valueOf(x[1]));
				f.setRuc(String.valueOf(x[2]));
				f.setNum_solicitud(String.valueOf(x[3]));
				f.setValor_pago(Double.parseDouble(String.valueOf(x[4])));
				f.setRecaudacion(String.valueOf(x[5]));
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
	public List<Fin_Importaciones_No_DTO> listarImportacionesNoPorCodigoFact(String codigo, Integer punto) {
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Importaciones_No_DTO> consultas = new ArrayList<>();
		try {
			Query q = em.createNativeQuery("SELECT * FROM Fin_Importaciones_No WHERE Cod_entidad=?1 and Aux ='S' and punto=?2  and fecha = (select max(fecha) from Fin_importaciones_no where  Cod_entidad=?1 and Aux ='S' and punto =?2)   "); // query de la entidad del model
			q.setParameter(1, codigo);
			q.setParameter(2, punto);
			lista =  q.getResultList();
			lista.forEach(x -> {
				Fin_Importaciones_No_DTO f = new Fin_Importaciones_No_DTO();
				f.setCod_entidad(String.valueOf(x[0]));				
				f.setNom_entidad(String.valueOf(x[1]));
				f.setRuc(String.valueOf(x[2]));
				f.setNum_solicitud(String.valueOf(x[3]));
				f.setValor_pago(Double.parseDouble(String.valueOf(x[4])));
				f.setTipo(String.valueOf(x[5]));
				f.setAux(Boolean.parseBoolean(String.valueOf(x[6])));
				f.setPunto(Integer.parseInt(String.valueOf(x[7])));
				f.setRecaudacion(String.valueOf(x[8]));
				f.setCantidad(Integer.parseInt(String.valueOf(x[9])));
				consultas.add(f);
			});
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
		return consultas;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Fin_Importaciones_No_DTO> listarImportacionesNoPorCodigoRec(String codigo, Integer punto) {
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Importaciones_No_DTO> consultas = new ArrayList<>();
		try {
			Query q = em.createNativeQuery("SELECT cod_entidad,nom_entidad,ruc,valor_pago,recaudacion,sum(cantidad) cantidad FROM Fin_Importaciones_no WHERE Cod_entidad=?1 and Aux ='S' and punto = ?2 and fecha = (select max(fecha) from Fin_importaciones_no where  Cod_entidad=?1 and Aux ='S' and punto =?2) GROUP BY cod_entidad,nom_entidad,ruc,valor_pago,recaudacion"); 
			q.setParameter(1, codigo);
			q.setParameter(2, punto);
			lista =  q.getResultList();
			lista.forEach(x -> {
				Fin_Importaciones_No_DTO f = new Fin_Importaciones_No_DTO();
				f.setCod_entidad(String.valueOf(x[0]));				
				f.setNom_entidad(String.valueOf(x[1]));
				f.setRuc(String.valueOf(x[2]));
				f.setValor_pago(Double.parseDouble(String.valueOf(x[3])));
				f.setRecaudacion(String.valueOf(x[4]));
				f.setCantidad(Integer.parseInt(String.valueOf(x[5])));
				consultas.add(f);
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return consultas;
	}



	

}
