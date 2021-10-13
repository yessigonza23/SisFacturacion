package ec.gob.mdg.control.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.control.dao.IFin_Importaciones_DTODAO;
import ec.gob.mdg.control.model.Fin_Importaciones_DTO;

@Stateless
public class Fin_Importaciones_DTODAOImpl implements IFin_Importaciones_DTODAO, Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "controlPU")
	private EntityManager em;

	@Override
	public Integer registrar(Fin_Importaciones_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer modificar(Fin_Importaciones_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer eliminar(Fin_Importaciones_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fin_Importaciones_DTO> listar() throws Exception {
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Importaciones_DTO> consultas = new ArrayList<>();
		try {
			Query q = em.createNativeQuery("SELECT *  FROM Fin_Importaciones "); // query de la entidad del model
			lista = q.getResultList();
			lista.forEach(x -> {
				Fin_Importaciones_DTO f = new Fin_Importaciones_DTO();
				f.setCod_entidad(Integer.parseInt(String.valueOf(x[0])));
				f.setNom_entidad(String.valueOf(x[1]));
				f.setRuc(String.valueOf(x[2]));
				f.setNum_solicitud(String.valueOf(x[3]));
				f.setValor_pago(Double.parseDouble(String.valueOf(x[4])));
				f.setFob(Double.parseDouble(String.valueOf(x[5])));
				f.setAux(Boolean.parseBoolean(String.valueOf(x[6])));
				f.setTipo(String.valueOf(x[7]));
				consultas.add(f);
			});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return consultas;
	}

	@Override
	public Fin_Importaciones_DTO listarPorId(Fin_Importaciones_DTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fin_Importaciones_DTO> listarEntidadesImp() {
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Importaciones_DTO> consultas = new ArrayList<>();
		try {
			Query q = em.createNativeQuery(
					"SELECT DISTINCT a.Cod_entidad, b.nombre Nom_entidad, b.ruc  FROM si_importaciones a, cyf_entidades b WHERE a.estado='I' and a.num_fact_int is null and a.cod_entidad =b.codigo   AND A.AUX IS NULL ORDER BY b.nombre "); 																											// model
			lista = q.getResultList();
			lista.forEach(x -> {
				Fin_Importaciones_DTO f = new Fin_Importaciones_DTO();
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
	public List<Fin_Importaciones_DTO> listarImportacionesPorCodigo(Integer codigo) {
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Importaciones_DTO> consultas = new ArrayList<>();
		try {
			Query q = em.createNativeQuery("SELECT * FROM Fin_Importaciones WHERE Cod_entidad=?1 and aux is null "); 
			q.setParameter(1, codigo);
			lista = q.getResultList();
			lista.forEach(x -> {
				Fin_Importaciones_DTO f = new Fin_Importaciones_DTO();
				f.setCod_entidad(Integer.parseInt(String.valueOf(x[0])));
				f.setNom_entidad(String.valueOf(x[1]));
				f.setRuc(String.valueOf(x[2]));
				f.setNum_solicitud(String.valueOf(x[3]));
				f.setValor_pago(Double.parseDouble(String.valueOf(x[4])));
				f.setFob(Double.parseDouble(String.valueOf(x[5])));
				f.setTipo(String.valueOf(x[6]));
				consultas.add(f);
			});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return consultas;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Fin_Importaciones_DTO mostrarEntidadImportacion(Integer codigo) {
		Fin_Importaciones_DTO fin = new Fin_Importaciones_DTO();
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Importaciones_DTO> consultas = new ArrayList<>();
		try {
			Query q = em.createNativeQuery(
					"SELECT DISTINCT cod_entidad, nom_entidad, ruc, num_solicitud, valor_pago, fob, aux,tipo,recaudacion  FROM Fin_Importaciones 	WHERE Cod_entidad=?1 "); 
			q.setParameter(1, codigo);
			lista = q.getResultList();
			lista.forEach(x -> {
				Fin_Importaciones_DTO f = new Fin_Importaciones_DTO();
				f.setCod_entidad(Integer.parseInt(String.valueOf(x[0])));
				f.setNom_entidad(String.valueOf(x[1]));
				f.setRuc(String.valueOf(x[2]));
				f.setNum_solicitud(String.valueOf(x[3]));
				f.setValor_pago(Double.parseDouble(String.valueOf(x[4])));
				f.setFob(Double.parseDouble(String.valueOf(x[5])));
				f.setAux(Boolean.parseBoolean(String.valueOf(x[6])));
				f.setTipo(String.valueOf(x[7]));
				f.setRecaudacion(String.valueOf(x[8]));
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
	public List<Fin_Importaciones_DTO> listarImportacionesPorCodigoFact(Integer codigo, Integer punto) {
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Importaciones_DTO> consultas = new ArrayList<>();
		try {
			Query q = em.createNativeQuery(
					"SELECT * FROM Fin_Importaciones WHERE Cod_entidad=?1 and Aux =?2 and punto=?3 "); 
			q.setParameter(1, codigo);
			q.setParameter(2, "S");
			q.setParameter(3, punto);
			lista = q.getResultList();
			lista.forEach(x -> {
				Fin_Importaciones_DTO f = new Fin_Importaciones_DTO();
				f.setCod_entidad(Integer.parseInt(String.valueOf(x[0])));
				f.setNom_entidad(String.valueOf(x[1]));
				f.setRuc(String.valueOf(x[2]));
				f.setNum_solicitud(String.valueOf(x[3]));
				f.setValor_pago(Double.parseDouble(String.valueOf(x[4])));
				f.setFob(Double.parseDouble(String.valueOf(x[5])));
				f.setAux(Boolean.parseBoolean(String.valueOf(x[6])));
				f.setTipo(String.valueOf(x[7]));
				f.setPunto(Integer.parseInt(String.valueOf(x[8])));
				f.setRecaudacion(String.valueOf(x[9]));
				f.setCantidad(Integer.parseInt(String.valueOf(x[10])));
				consultas.add(f);
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return consultas;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fin_Importaciones_DTO> listarImportacionesPorCodigoRec(Integer codigo, Integer punto) {
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Importaciones_DTO> consultas = new ArrayList<>();
		try {
			Query q = em.createNativeQuery(
					"SELECT cod_entidad,nom_entidad,ruc,valor_pago,recaudacion,count(*) cantidad FROM Fin_Importaciones WHERE Cod_entidad=?1 and Aux =?2 and punto = ?3 GROUP BY cod_entidad,nom_entidad,ruc,valor_pago,recaudacion"); 
			q.setParameter(1, codigo);
			q.setParameter(2, "S");
			q.setParameter(3, punto);
			lista = q.getResultList();
			lista.forEach(x -> {
				Fin_Importaciones_DTO f = new Fin_Importaciones_DTO();
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
