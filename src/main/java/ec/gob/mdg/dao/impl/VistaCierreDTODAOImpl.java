package ec.gob.mdg.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.dao.IVistaCierreDTODAO;
import ec.gob.mdg.model.VistaCierreDTO;

@Stateless
public class VistaCierreDTODAOImpl implements IVistaCierreDTODAO {

	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;

	@Override
	public Integer registrar(VistaCierreDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer modificar(VistaCierreDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer eliminar(VistaCierreDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VistaCierreDTO> listar() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VistaCierreDTO listarPorId(VistaCierreDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VistaCierreDTO> listarCierre(Date fecha_inicio, Date fecha_fin, Integer id_punto) {
		/// SUMAR UN D�A PARA QUE SE MUESTRE LA INFORMACI�N DEL MISMO DIA
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha_fin); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		fecha_fin = calendar.getTime();

		// System.out.println("entra");
		List<Object[]> lista = new ArrayList<>();
		List<VistaCierreDTO> listaFin = new ArrayList<VistaCierreDTO>();
		try {
			Query q = em.createNativeQuery(
					"SELECT c.puntorecaudacion,c.id_cierre,c.numero,c.fechaemision,c.valor,c.estado FROM financiero.vista_cierre_xls c WHERE c.id_puntorecaudacion=?1 and c.fechaemision between ?2 and ?3 ORDER BY 2,3");
			q.setParameter(1, id_punto);
			q.setParameter(2, fecha_inicio);
			q.setParameter(3, fecha_fin);

			lista = q.getResultList();
			lista.forEach(x -> {
				VistaCierreDTO v = new VistaCierreDTO();

				v.setPuntoRecaudacion(String.valueOf(x[0]));
				v.setId_cierre(Integer.parseInt(String.valueOf(x[1])));
				v.setNumeroFactura(Integer.parseInt(String.valueOf(x[2])));
				v.setFechaemision(String.valueOf(x[3]));
				v.setValor(Double.parseDouble(String.valueOf(x[4])));
				v.setEstado(String.valueOf(x[5]));
				listaFin.add(v);
			});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return listaFin;

	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public Integer cuentaFacturas(Date fecha_inicio, Date fecha_fin, Integer id_punto) {
		/// SUMAR UN D�A PARA QUE SE MUESTRE LA INFORMACI�N DEL MISMO DIA
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha_fin); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		fecha_fin = calendar.getTime();

		Integer contador = 0;
		List<Object[]> lista = new ArrayList<>();
		List<VistaCierreDTO> listaFin = new ArrayList<VistaCierreDTO>();
		try {
			Query q = em.createNativeQuery(
					"SELECT c.puntorecaudacion,c.id_cierre,c.numero,c.fechaemision,c.valor,c.estado FROM financiero.vista_cierre_xls c WHERE c.id_puntorecaudacion=?1 and c.fechaemision between ?2 and ?3 ORDER BY 3,2");
			q.setParameter(1, id_punto);
			q.setParameter(2, fecha_inicio);
			q.setParameter(3, fecha_fin);

			lista = q.getResultList();
			lista.forEach(x -> {
				VistaCierreDTO v = new VistaCierreDTO();

				v.setPuntoRecaudacion(String.valueOf(x[0]));
				v.setId_cierre(Integer.parseInt(String.valueOf(x[1])));
				v.setNumeroFactura(Integer.parseInt(String.valueOf(x[2])));
				v.setFechaemision(String.valueOf(x[3]));
				v.setValor(Double.parseDouble(String.valueOf(x[4])));
				v.setEstado(String.valueOf(x[5]));
				listaFin.add(v);
			});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		for (VistaCierreDTO v : listaFin) {
			contador = contador + 1;
		}

		return contador;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VistaCierreDTO> listarCierreNac(Date fecha_inicio, Date fecha_fin) {
		/// SUMAR UN D�A PARA QUE SE MUESTRE LA INFORMACI�N DEL MISMO DIA
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha_fin); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		fecha_fin = calendar.getTime();

		// System.out.println("entra");
		List<Object[]> lista = new ArrayList<>();
		List<VistaCierreDTO> listaFin = new ArrayList<VistaCierreDTO>();
		try {
			Query q = em.createNativeQuery(
					"SELECT c.puntorecaudacion,c.id_cierre,c.numero,c.fechaemision,c.valor,c.estado FROM financiero.vista_cierre_xls c WHERE c.fechaemision between ?1 and ?2 ORDER BY 2,3");
			q.setParameter(1, fecha_inicio);
			q.setParameter(2, fecha_fin);

			lista = q.getResultList();
			lista.forEach(x -> {
				VistaCierreDTO v = new VistaCierreDTO();

				v.setPuntoRecaudacion(String.valueOf(x[0]));
				v.setId_cierre(Integer.parseInt(String.valueOf(x[1])));
				v.setNumeroFactura(Integer.parseInt(String.valueOf(x[2])));
				v.setFechaemision(String.valueOf(x[3]));
				v.setValor(Double.parseDouble(String.valueOf(x[4])));
				v.setEstado(String.valueOf(x[5]));
				listaFin.add(v);
			});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return listaFin;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public Integer cuentaFacturasNac(Date fecha_inicio, Date fecha_fin) {

		/// SUMAR UN D�A PARA QUE SE MUESTRE LA INFORMACI�N DEL MISMO DIA
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha_fin); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		fecha_fin = calendar.getTime();
		
		Integer contador = 0;
		List<Object[]> lista = new ArrayList<>();
		List<VistaCierreDTO> listaFin = new ArrayList<VistaCierreDTO>();
		try {
			Query q = em.createNativeQuery(
					"SELECT c.puntorecaudacion,c.id_cierre,c.numero,c.fechaemision,c.valor,c.estado FROM financiero.vista_cierre_xls c WHERE c.fechaemision between ?1 and ?2 ORDER BY 3,2");
			q.setParameter(1, fecha_inicio);
			q.setParameter(2, fecha_fin);

			lista = q.getResultList();
			lista.forEach(x -> {
				VistaCierreDTO v = new VistaCierreDTO();

				v.setPuntoRecaudacion(String.valueOf(x[0]));
				v.setId_cierre(Integer.parseInt(String.valueOf(x[1])));
				v.setNumeroFactura(Integer.parseInt(String.valueOf(x[2])));
				v.setFechaemision(String.valueOf(x[3]));
				v.setValor(Double.parseDouble(String.valueOf(x[4])));
				v.setEstado(String.valueOf(x[5]));
				listaFin.add(v);
			});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		for (VistaCierreDTO v : listaFin) {
			contador = contador + 1;
		}

		return contador;

	}

}
