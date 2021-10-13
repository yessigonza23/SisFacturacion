package ec.gob.mdg.control.dao.impl;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.control.dao.IFin_Guias_DTODAO;
import ec.gob.mdg.control.model.Fin_Guias_DTO;

@Stateless
public class Fin_Guias_DTODAOImpl implements IFin_Guias_DTODAO, Serializable {

	private static final long serialVersionUID = 1L;
	
	@PersistenceContext(unitName = "controlPU")
	private EntityManager em;
	
	@Override
	public Integer registrar(Fin_Guias_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer modificar(Fin_Guias_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer eliminar(Fin_Guias_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fin_Guias_DTO> listar() throws Exception {
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Guias_DTO> consultas = new ArrayList<>();
		try {
			Query q = em.createNativeQuery("SELECT *  FROM Fin_Guias"); 
			lista =  q.getResultList();
			lista.forEach(x -> {
				Fin_Guias_DTO f = new Fin_Guias_DTO();
				f.setCod_entidad(Integer.parseInt(String.valueOf(x[0])));
				f.setCod_guia(Integer.parseInt(String.valueOf(x[1])));
				f.setNom_entidad(String.valueOf(x[2]));
				f.setRuc(String.valueOf(x[3]));
				f.setNum_solicitud(String.valueOf(x[4]));
				f.setValor_pago(Double.parseDouble(String.valueOf(x[5])));
				f.setNum_recibo(String.valueOf(x[6]));				
				String fecha_tmp = String.valueOf(x[7]);
				java.text.DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				try {
					Date fecha = (Date) formatter.parse(fecha_tmp);
					f.setFecha_elaboracion(fecha);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
				f.setJefatura_origen(Integer.parseInt(String.valueOf(x[8])));
				f.setCodigo_banco(String.valueOf(x[9]));
				f.setGuia_x_permiso(String.valueOf(x[10]));				
				consultas.add(f);
			});
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}		
		return consultas;
	}

	@Override
	public Fin_Guias_DTO listarPorId(Fin_Guias_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fin_Guias_DTO> listarEntidadesGuias() {		
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Guias_DTO> consultas = new ArrayList<>();
		try {
			Query q = em.createNativeQuery("SELECT DISTINCT Cod_entidad, Nom_entidad, ruc  FROM Fin_guias ORDER BY Nom_entidad"); // query de la entidad del model
			lista =  q.getResultList();						
			lista.forEach(x -> {			
				Fin_Guias_DTO f = new Fin_Guias_DTO();							 
				f.setCod_entidad(Integer.parseInt(String.valueOf(x[0])));				
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
	public List<Fin_Guias_DTO> listarGuiasPorCodigo(Integer codigo) {
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Guias_DTO> consultas = new ArrayList<>();
		try {
			Query q = em.createNativeQuery("SELECT cod_entidad,cod_guia,nom_entidad,ruc,num_solicitud,valor_pago,codigo_banco,cantidad FROM Fin_Guias WHERE Cod_entidad=?1 AND aux is null "); // query de la entidad del model
			q.setParameter(1, codigo);			
			lista =  q.getResultList();
			lista.forEach(x -> {
				Fin_Guias_DTO f = new Fin_Guias_DTO();								 
				f.setCod_entidad(Integer.parseInt(String.valueOf(x[0])));	
				f.setCod_guia(Integer.parseInt(String.valueOf(x[1])));
				f.setNom_entidad(String.valueOf(x[2]));
				f.setRuc(String.valueOf(x[3]));
				f.setNum_solicitud(String.valueOf(x[4]));
				f.setValor_pago(Double.parseDouble(String.valueOf(x[5])));
				f.setCodigo_banco(String.valueOf(x[6]));
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
	public List<Fin_Guias_DTO> listarGuiasPorCodigoFact(Integer codigo, Integer punto) {
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Guias_DTO> consultas = new ArrayList<>();
		try {
			Query q = em.createNativeQuery("SELECT cod_entidad,cod_guia,nom_entidad,ruc,num_solicitud,valor_pago,numero_recibo,codigo_banco,punto,cantidad FROM Fin_Guias WHERE Cod_entidad=?1 and Aux =?2 and punto=?3"); // query de la entidad del model
			q.setParameter(1, codigo);
			q.setParameter(2, "S");
			q.setParameter(3, punto);			
			lista =  q.getResultList();
			lista.forEach(x -> {
				Fin_Guias_DTO f = new Fin_Guias_DTO();								 
				f.setCod_entidad(Integer.parseInt(String.valueOf(x[0])));	
				f.setCod_guia(Integer.parseInt(String.valueOf(x[1])));
				f.setNom_entidad(String.valueOf(x[2]));
				f.setRuc(String.valueOf(x[3]));
				f.setNum_solicitud(String.valueOf(x[4]));
				f.setValor_pago(Double.parseDouble(String.valueOf(x[5])));
				f.setNum_recibo(String.valueOf(x[6]));
				f.setCodigo_banco(String.valueOf(x[7]));
				f.setPunto(Integer.parseInt(String.valueOf(x[8])));
				//f.setAux(Boolean.parseBoolean(String.valueOf(x[9])));
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
	public Fin_Guias_DTO mostrarEntidadGuias(Integer codigo) {
		Fin_Guias_DTO fin = new Fin_Guias_DTO();
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Guias_DTO> consultas = new ArrayList<>();
		try {
			Query q = em.createNativeQuery("SELECT DISTINCT cod_entidad,nom_entidad,ruc,num_solicitud,valor_pago,codigo_banco,aux,cantidad FROM Fin_Guias WHERE Cod_entidad=?1"); // query de la entidad del model
			q.setParameter(1, codigo);			
			lista =  q.getResultList();
			lista.forEach(x -> {
				Fin_Guias_DTO f = new Fin_Guias_DTO();								 
				f.setCod_entidad(Integer.parseInt(String.valueOf(x[0])));	
				f.setNom_entidad(String.valueOf(x[1]));
				f.setRuc(String.valueOf(x[2]));
				f.setNum_solicitud(String.valueOf(x[3]));
				f.setValor_pago(Double.parseDouble(String.valueOf(x[4])));
				f.setCodigo_banco(String.valueOf(x[5]));
				f.setAux(Boolean.parseBoolean(String.valueOf(x[6])));
				f.setCantidad(Integer.parseInt(String.valueOf(x[7])));
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
	public List<Fin_Guias_DTO> listarGuiasPorCodigoRec(Integer codigo, Integer punto) {
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Guias_DTO> consultas = new ArrayList<>();
		try {
			Query q = em.createNativeQuery("SELECT cod_entidad,nom_entidad,ruc,valor_pago,recaudacion,count(*) cantidad FROM Fin_Guias WHERE Cod_entidad=?1 and Aux =?2 and punto=?3 GROUP BY cod_entidad,nom_entidad,ruc,valor_pago,recaudacion "); // query de la entidad del model
			q.setParameter(1, codigo);
			q.setParameter(2, "S");
			q.setParameter(3, punto);			
			lista =  q.getResultList();
			lista.forEach(x -> {
				Fin_Guias_DTO f = new Fin_Guias_DTO();								 
				f.setCod_entidad(Integer.parseInt(String.valueOf(x[0])));
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
