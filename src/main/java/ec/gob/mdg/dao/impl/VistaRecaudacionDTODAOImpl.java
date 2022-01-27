package ec.gob.mdg.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import ec.gob.mdg.dao.IVistaRecaudacionDTODAO;
import ec.gob.mdg.model.VistaRecaudacionDTO;

@Stateless
public class VistaRecaudacionDTODAOImpl implements IVistaRecaudacionDTODAO, Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;

	@Override
	public Integer registrar(VistaRecaudacionDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer modificar(VistaRecaudacionDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer eliminar(VistaRecaudacionDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VistaRecaudacionDTO> listar() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VistaRecaudacionDTO listarPorId(VistaRecaudacionDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VistaRecaudacionDTO> listarRecaudacionDetalle(Date fecha_inicio, Date fecha_fin, Integer id_punto) {
		/// SUMAR UN D�A PARA QUE SE MUESTRE LA INFORMACI�N DEL MISMO DIA
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha_fin); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		fecha_fin = calendar.getTime();
		
		List<Object[]> lista = new ArrayList<>();
		List<VistaRecaudacionDTO> listaFin = new ArrayList<VistaRecaudacionDTO>();
		try {
			Query q = em.createNativeQuery(
					"SELECT c.punto_nombre,c.comp_numero,c.comp_fechaemision,c.cliente_nombre,c.cliente_ci,c.importe,c.recdetalle_codigo,c.recaudacion_codigobanco,c.usuario_nombre FROM financiero.vista_recaudacion c WHERE c.punto_id=?1 and c.comp_fechaemision between ?2 and ?3 and c.comp_tipo='F' and c.comp_estado <> 'N' ORDER BY 3,2");
			q.setParameter(1, id_punto);
			q.setParameter(2, fecha_inicio);
			q.setParameter(3, fecha_fin);

			lista = q.getResultList();
			lista.forEach(x -> {
				VistaRecaudacionDTO v = new VistaRecaudacionDTO();

				v.setPunto_nombre(String.valueOf(x[0]));
				v.setComp_numero(Integer.parseInt(String.valueOf(x[1])));
				v.setComp_fechaemision(String.valueOf(x[2]));
				v.setCliente_nombre(String.valueOf(x[3]));
				v.setCliente_ci(String.valueOf(x[4]));
				v.setComp_valor(Double.parseDouble(String.valueOf(x[5])));
				v.setRecdetalle_codigo(String.valueOf(x[6]));
				v.setRecaudacion_codigobanco(String.valueOf(x[7]));
				v.setUsuario_nombre(String.valueOf(x[8]));
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
		List<Object[]> lista = new ArrayList<>();
		List<VistaRecaudacionDTO> listaFin = new ArrayList<VistaRecaudacionDTO>();
		Integer contador = 0;
		try {
			Query q = em.createNativeQuery(
					"SELECT c.punto_nombre,c.comp_numero,c.comp_fechaemision,c.cliente_nombre,c.cliente_ci,c.comp_valor,c.recdetalle_codigo,c.recaudacion_codigobanco,c.usuario_nombre FROM financiero.vista_recaudacion c WHERE c.punto_id=?1 and c.comp_fechaemision between ?2 and ?3 and c.comp_tipo=?4 and c.comp_estado = ?5 ORDER BY 3,2");

			q.setParameter(1, id_punto);
			q.setParameter(2, fecha_inicio);
			q.setParameter(3, fecha_fin);
			q.setParameter(4, "F");
			q.setParameter(5, "A");

			lista = q.getResultList();
			lista.forEach(x -> {
				VistaRecaudacionDTO v = new VistaRecaudacionDTO();

				v.setPunto_nombre(String.valueOf(x[0]));
				v.setComp_numero(Integer.parseInt(String.valueOf(x[1])));
				v.setComp_fechaemision(String.valueOf(x[2]));
				v.setCliente_nombre(String.valueOf(x[3]));
				v.setCliente_ci(String.valueOf(x[4]));
				v.setComp_valor(Double.parseDouble(String.valueOf(x[5])));
				v.setRecdetalle_codigo(String.valueOf(x[6]));
				v.setRecaudacion_codigobanco(String.valueOf(x[7]));
				v.setUsuario_nombre(String.valueOf(x[8]));
				listaFin.add(v);
			});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		for (VistaRecaudacionDTO v : listaFin) {
			contador = contador + 1;
		}

		return contador;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VistaRecaudacionDTO> listarRecaudacionDetalleNac(Date fecha_inicio, Date fecha_fin,
			String proceso_tipo) {
		/// SUMAR UN D�A PARA QUE SE MUESTRE LA INFORMACI�N DEL MISMO DIA
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha_fin); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		fecha_fin = calendar.getTime();

		List<Object[]> lista = new ArrayList<>();
		List<VistaRecaudacionDTO> listaFin = new ArrayList<VistaRecaudacionDTO>();
		try {
			Query q = em.createNativeQuery(
					"SELECT c.punto_nombre,c.comp_numero,c.comp_fechaemision,c.cliente_nombre,c.cliente_ci,c.importe,c.recdetalle_codigo,c.recaudacion_codigobanco,c.usuario_nombre FROM financiero.vista_recaudacion c WHERE c.comp_fechaemision between ?1 and ?2 and proceso_tipo=?3 and c.comp_tipo='F' and c.comp_estado <> 'N' ORDER BY 3,2");
			q.setParameter(1, fecha_inicio);
			q.setParameter(2, fecha_fin);
			q.setParameter(3, proceso_tipo);

			lista = q.getResultList();
			lista.forEach(x -> {
				VistaRecaudacionDTO v = new VistaRecaudacionDTO();

				v.setPunto_nombre(String.valueOf(x[0]));
				v.setComp_numero(Integer.parseInt(String.valueOf(x[1])));
				v.setComp_fechaemision(String.valueOf(x[2]));
				v.setCliente_nombre(String.valueOf(x[3]));
				v.setCliente_ci(String.valueOf(x[4]));
				v.setComp_valor(Double.parseDouble(String.valueOf(x[5])));
				v.setRecdetalle_codigo(String.valueOf(x[6]));
				v.setRecaudacion_codigobanco(String.valueOf(x[7]));
				v.setUsuario_nombre(String.valueOf(x[8]));
				listaFin.add(v);
			});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return listaFin;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public Integer cuentaFacturasNac(Date fecha_inicio, Date fecha_fin, String proceso_tipo) {
		/// SUMAR UN D�A PARA QUE SE MUESTRE LA INFORMACI�N DEL MISMO DIA
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha_fin); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		fecha_fin = calendar.getTime();

		List<Object[]> lista = new ArrayList<>();
		List<VistaRecaudacionDTO> listaFin = new ArrayList<VistaRecaudacionDTO>();
		Integer contador = 0;
		try {
			Query q = em.createNativeQuery(
					"SELECT c.punto_nombre,c.comp_numero,c.comp_fechaemision,c.cliente_nombre,c.cliente_ci,c.comp_valor,c.recdetalle_codigo,c.recaudacion_codigobanco,c.usuario_nombre FROM financiero.vista_recaudacion c WHERE  c.comp_fechaemision between ?1 and ?2 and proceso_tipo = ?3 and c.comp_tipo='F' and c.comp_estado = 'A' ORDER BY 3,2");

			q.setParameter(1, fecha_inicio);
			q.setParameter(2, fecha_fin);
			q.setParameter(3, proceso_tipo);

			lista = q.getResultList();
			lista.forEach(x -> {
				VistaRecaudacionDTO v = new VistaRecaudacionDTO();

				v.setPunto_nombre(String.valueOf(x[0]));
				v.setComp_numero(Integer.parseInt(String.valueOf(x[1])));
				v.setComp_fechaemision(String.valueOf(x[2]));
				v.setCliente_nombre(String.valueOf(x[3]));
				v.setCliente_ci(String.valueOf(x[4]));
				v.setComp_valor(Double.parseDouble(String.valueOf(x[5])));
				v.setRecdetalle_codigo(String.valueOf(x[6]));
				v.setRecaudacion_codigobanco(String.valueOf(x[7]));
				v.setUsuario_nombre(String.valueOf(x[8]));
				listaFin.add(v);
			});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		for (VistaRecaudacionDTO v : listaFin) {
			contador = contador + 1;
		}

		return contador;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VistaRecaudacionDTO> listarRecaudacionAnuladasNac(Date fecha_inicio, Date fecha_fin,
			String proceso_tipo) {
		/// SUMAR UN D�A PARA QUE SE MUESTRE LA INFORMACI�N DEL MISMO DIA
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha_fin); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		fecha_fin = calendar.getTime();

		List<Object[]> lista = new ArrayList<>();
		List<VistaRecaudacionDTO> listaFin = new ArrayList<VistaRecaudacionDTO>();
		try {
			Query q = em.createNativeQuery(
					"SELECT c.punto_nombre,c.comp_numero,c.comp_fechaemision,c.cliente_nombre,c.cliente_ci,c.importe,c.recdetalle_codigo,c.recaudacion_codigobanco,c.usuario_nombre FROM financiero.vista_recaudacion c WHERE c.comp_fechaemision between ?1 and ?2 and proceso_tipo=?3 and c.comp_tipo='F' and c.comp_estado = 'N' ORDER BY 3,2");
			q.setParameter(1, fecha_inicio);
			q.setParameter(2, fecha_fin);
			q.setParameter(3, proceso_tipo);

			lista = q.getResultList();
			lista.forEach(x -> {
				VistaRecaudacionDTO v = new VistaRecaudacionDTO();

				v.setPunto_nombre(String.valueOf(x[0]));
				v.setComp_numero(Integer.parseInt(String.valueOf(x[1])));
				v.setComp_fechaemision(String.valueOf(x[2]));
				v.setCliente_nombre(String.valueOf(x[3]));
				v.setCliente_ci(String.valueOf(x[4]));
				v.setComp_valor(Double.parseDouble(String.valueOf(x[5])));
				v.setRecdetalle_codigo(String.valueOf(x[6]));
				v.setRecaudacion_codigobanco(String.valueOf(x[7]));
				v.setUsuario_nombre(String.valueOf(x[8]));
				listaFin.add(v);
			});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return listaFin;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public Integer cuentaFacturasAnuladasNac(Date fecha_inicio, Date fecha_fin, String proceso_tipo) {

		/// SUMAR UN D�A PARA QUE SE MUESTRE LA INFORMACI�N DEL MISMO DIA
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha_fin); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		fecha_fin = calendar.getTime();

		List<Object[]> lista = new ArrayList<>();
		List<VistaRecaudacionDTO> listaFin = new ArrayList<VistaRecaudacionDTO>();
		Integer contador = 0;
		try {
			Query q = em.createNativeQuery(
					"SELECT c.punto_nombre,c.comp_numero,c.comp_fechaemision,c.cliente_nombre,c.cliente_ci,c.comp_valor,c.recdetalle_codigo,c.recaudacion_codigobanco,c.usuario_nombre FROM financiero.vista_recaudacion c WHERE  c.comp_fechaemision between ?1 and ?2 and proceso_tipo = ?3 and c.comp_tipo='F' and c.comp_estado = 'N' ORDER BY 3,2");

			q.setParameter(1, fecha_inicio);
			q.setParameter(2, fecha_fin);
			q.setParameter(3, proceso_tipo);

			lista = q.getResultList();
			lista.forEach(x -> {
				VistaRecaudacionDTO v = new VistaRecaudacionDTO();

				v.setPunto_nombre(String.valueOf(x[0]));
				v.setComp_numero(Integer.parseInt(String.valueOf(x[1])));
				v.setComp_fechaemision(String.valueOf(x[2]));
				v.setCliente_nombre(String.valueOf(x[3]));
				v.setCliente_ci(String.valueOf(x[4]));
				v.setComp_valor(Double.parseDouble(String.valueOf(x[5])));
				v.setRecdetalle_codigo(String.valueOf(x[6]));
				v.setRecaudacion_codigobanco(String.valueOf(x[7]));
				v.setUsuario_nombre(String.valueOf(x[8]));
				listaFin.add(v);
			});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		for (VistaRecaudacionDTO v : listaFin) {
			contador = contador + 1;
		}

		return contador;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VistaRecaudacionDTO> listarRecaudacionNoAutorizadasNac(Date fecha_inicio, Date fecha_fin,
			String proceso_tipo) {
		/// SUMAR UN DiA PARA QUE SE MUESTRE LA INFORMACI�N DEL MISMO DIA
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha_fin); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		fecha_fin = calendar.getTime();

		List<Object[]> lista = new ArrayList<>();
		List<VistaRecaudacionDTO> listaFin = new ArrayList<VistaRecaudacionDTO>();
		try {
			Query q = em.createNativeQuery(
					"SELECT c.punto_nombre,c.comp_numero,c.comp_fechaemision,c.cliente_nombre,c.cliente_ci,c.importe,c.recdetalle_codigo,c.recaudacion_codigobanco,c.usuario_nombre FROM financiero.vista_recaudacion c WHERE c.comp_fechaemision between ?1 and ?2 and proceso_tipo=?3 and c.comp_tipo='F' and c.comp_estado = 'A' and c.comp_autorizacion is null ORDER BY 3,2");
			q.setParameter(1, fecha_inicio);
			q.setParameter(2, fecha_fin);
			q.setParameter(3, proceso_tipo);

			lista = q.getResultList();
			lista.forEach(x -> {
				VistaRecaudacionDTO v = new VistaRecaudacionDTO();

				v.setPunto_nombre(String.valueOf(x[0]));
				v.setComp_numero(Integer.parseInt(String.valueOf(x[1])));
				v.setComp_fechaemision(String.valueOf(x[2]));
				v.setCliente_nombre(String.valueOf(x[3]));
				v.setCliente_ci(String.valueOf(x[4]));
				v.setComp_valor(Double.parseDouble(String.valueOf(x[5])));
				v.setRecdetalle_codigo(String.valueOf(x[6]));
				v.setRecaudacion_codigobanco(String.valueOf(x[7]));
				v.setUsuario_nombre(String.valueOf(x[8]));
				listaFin.add(v);
			});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return listaFin;
	}

	@SuppressWarnings({ "unchecked", "unused" })
	@Override
	public Integer cuentaFacturasNoAutorizadasNac(Date fecha_inicio, Date fecha_fin, String proceso_tipo) {
		/// SUMAR UN D�A PARA QUE SE MUESTRE LA INFORMACI�N DEL MISMO DIA
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha_fin); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		fecha_fin = calendar.getTime();

		List<Object[]> lista = new ArrayList<>();
		List<VistaRecaudacionDTO> listaFin = new ArrayList<VistaRecaudacionDTO>();
		Integer contador = 0;
		try {
			Query q = em.createNativeQuery(
					"SELECT c.punto_nombre,c.comp_numero,c.comp_fechaemision,c.cliente_nombre,c.cliente_ci,c.comp_valor,c.recdetalle_codigo,c.recaudacion_codigobanco,c.usuario_nombre FROM financiero.vista_recaudacion c WHERE  c.comp_fechaemision between ?1 and ?2 and proceso_tipo = ?3 and c.comp_tipo='F' and c.comp_estado = 'A'  and c.comp_autorizacion is null  ORDER BY 3,2");

			q.setParameter(1, fecha_inicio);
			q.setParameter(2, fecha_fin);
			q.setParameter(3, proceso_tipo);

			lista = q.getResultList();
			lista.forEach(x -> {
				VistaRecaudacionDTO v = new VistaRecaudacionDTO();

				v.setPunto_nombre(String.valueOf(x[0]));
				v.setComp_numero(Integer.parseInt(String.valueOf(x[1])));
				v.setComp_fechaemision(String.valueOf(x[2]));
				v.setCliente_nombre(String.valueOf(x[3]));
				v.setCliente_ci(String.valueOf(x[4]));
				v.setComp_valor(Double.parseDouble(String.valueOf(x[5])));
				v.setRecdetalle_codigo(String.valueOf(x[6]));
				v.setRecaudacion_codigobanco(String.valueOf(x[7]));
				v.setUsuario_nombre(String.valueOf(x[8]));
				listaFin.add(v);
			});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		for (VistaRecaudacionDTO v : listaFin) {
			contador = contador + 1;
		}

		return contador;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VistaRecaudacionDTO> listarRecaudacionSinCierre(Date fecha_inicio, Date fecha_fin,
			String proceso_tipo) {
		/// SUMAR UN D�A PARA QUE SE MUESTRE LA INFORMACI�N DEL MISMO DIA
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha_fin); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		fecha_fin = calendar.getTime();

		List<Object[]> lista = new ArrayList<>();
		List<VistaRecaudacionDTO> listaFin = new ArrayList<VistaRecaudacionDTO>();
		try {
			Query q = em.createNativeQuery(
					"SELECT c.punto_nombre,count(*) contador FROM financiero.vista_recaudacion c WHERE c.comp_fechaemision between ?1 and ?2 and proceso_tipo=?3 and c.comp_tipo='F' and c.comp_estado = 'A' and c.comp_id_cierre is null GROUP BY c.punto_nombre ORDER BY 1");
			q.setParameter(1, fecha_inicio);
			q.setParameter(2, fecha_fin);
			q.setParameter(3, proceso_tipo);

			lista = q.getResultList();
			lista.forEach(x -> {
				VistaRecaudacionDTO v = new VistaRecaudacionDTO();
				v.setPunto_nombre(String.valueOf(x[0]));
				v.setContador(Integer.parseInt(String.valueOf(x[1])));
				listaFin.add(v);
			});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return listaFin;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VistaRecaudacionDTO> listarRecaudacioDif(Date fecha_inicio, Date fecha_fin, String proceso_tipo) {

		/// SUMAR UN D�A PARA QUE SE MUESTRE LA INFORMACI�N DEL MISMO DIA
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha_fin); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		fecha_fin = calendar.getTime();

		List<Object[]> lista = new ArrayList<>();
		List<VistaRecaudacionDTO> listaFin = new ArrayList<VistaRecaudacionDTO>();
		try {
			Query q = em.createNativeQuery(
					"SELECT PUNTO,NUMERO,FECHA,COALESCE(CODE,0),DETALLE FROM (\r\n" + 
					"SELECT A.PUNTO,A.NUMERO,A.FECHA,A.CODE,A.DETALLE,A.PROCESO_TIPO FROM (\r\n" + 
					"	SELECT c.punto_nombre PUNTO,c.comp_numero NUMERO,c.comp_fechaemision FECHA,c.comp_autorizacion,\r\n" + 
					"		   case when length(c.comp_autorizacion)>40\r\n" + 
					"		   then CAST (substring(c.comp_autorizacion,31,9) AS INTEGER)\r\n" + 
					"		   when length(c.comp_autorizacion)<40\r\n" + 
					"		   then CAST (c.comp_autorizacion AS INTEGER)\r\n" + 
					"		   end code,'Diferente numero de autorizacion' detalle,c.proceso_tipo\r\n" + 
					"	  FROM financiero.vista_recaudacion c \r\n" + 
					"	 WHERE c.comp_tipo='F' and c.comp_estado = 'A'\r\n" + 
					"	   AND c.comp_autorizacion is not null) A \r\n" + 
					" WHERE A.NUMERO<>A.CODE UNION\r\n" + 
					"SELECT A.PUNTO,A.NUMERO,A.FECHA,A.CODE,A.DETALLE,A.PROCESO_TIPO FROM (\r\n" + 
					"	SELECT c.punto_nombre PUNTO,c.comp_numero NUMERO,c.comp_fechaemision FECHA,c.comp_claveacceso,\r\n" + 
					"		   case when length(c.comp_claveacceso)>40\r\n" + 
					"		   then CAST (substring(c.comp_claveacceso,31,9) AS INTEGER)\r\n" + 
					"		   when length(c.comp_claveacceso)<40\r\n" + 
					"		   then CAST (c.comp_claveacceso AS INTEGER)\r\n" + 
					"		   end code,'Diferente numero de claveacceso' detalle,c.proceso_tipo\r\n" + 
					"	  FROM financiero.vista_recaudacion c \r\n" + 
					"	 WHERE c.comp_tipo='F' and c.comp_estado = 'A' AND c.comp_claveacceso is not null) A \r\n" + 
					"WHERE A.NUMERO<>A.CODE UNION\r\n" + 
					"SELECT A.PUNTO,A.NUMERO,A.FECHA,null,A.DETALLE,A.PROCESO_TIPO FROM (\r\n" + 
					"	SELECT c.punto_nombre PUNTO,c.comp_numero NUMERO,c.comp_fechaemision FECHA,c.comp_claveacceso,\r\n" + 
					"	null code,'Sin clave de acceso' detalle,c.proceso_tipo\r\n" + 
					"	FROM financiero.vista_recaudacion c \r\n" + 
					"	WHERE  c.comp_tipo='F' and c.comp_estado = 'A' AND c.comp_claveacceso is  null) A )X\r\n" + 
					"	WHERE FECHA BETWEEN ?1 AND ?2 AND PROCESO_TIPO= ?3");
			q.setParameter(1, fecha_inicio);
			q.setParameter(2, fecha_fin);
			q.setParameter(3, proceso_tipo);

			lista = q.getResultList();
			lista.forEach(x -> {
				VistaRecaudacionDTO v = new VistaRecaudacionDTO();

				v.setPunto_nombre(String.valueOf(x[0]));
				v.setComp_numero(Integer.parseInt(String.valueOf(x[1])));
				v.setComp_fechaemision(String.valueOf(x[2]));
				v.setNumero_factura(Integer.parseInt(String.valueOf(x[3])));
				v.setComp_detalle(String.valueOf(x[4]));
				listaFin.add(v);
			});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return listaFin;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VistaRecaudacionDTO> listarComprobantesPorCierreId(Integer id_cierre) {
		List<Object[]> lista = new ArrayList<>();
		List<VistaRecaudacionDTO> listaFin = new ArrayList<VistaRecaudacionDTO>();
		try {
			Query q = em.createNativeQuery(
					"SELECT c.comp_id_cierre,c.plancontable_codigo,sum(c.comp_valor) valor,sum(c.subt_vcer) subt_vcer,sum(c.subt_iva) subt_iva,sum(c.importe) importe FROM financiero.vista_recaudacion c WHERE c.comp_estado = 'A' and c.comp_check_cierre='true' and c.comp_id_cierre =?1 GROUP BY c.comp_id_cierre,c.plancontable_codigo");
			q.setParameter(1, id_cierre);

			lista = q.getResultList();
			lista.forEach(x -> {
				VistaRecaudacionDTO v = new VistaRecaudacionDTO();
				v.setComp_id_cierre(Integer.parseInt(String.valueOf(x[0])));
				v.setPlancontable_codigo(String.valueOf(x[1]));
				v.setComp_valor(Double.parseDouble(String.valueOf(x[2])));
				v.setSubt_vcer(Double.parseDouble(String.valueOf(x[3])));
				v.setSubt_iva(Double.parseDouble(String.valueOf(x[4])));
				v.setImporte(Double.parseDouble(String.valueOf(x[5])));
				listaFin.add(v);
			});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return listaFin;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VistaRecaudacionDTO> listarComprobantesPorCierreIdTotales(Integer id_cierre) {
//		System.out.println("entra a dao impo de la vistarecaudciondto " + id_cierre);
		
		List<Object[]> lista = new ArrayList<>();
		List<VistaRecaudacionDTO> listaFin = new ArrayList<VistaRecaudacionDTO>();
		try {
			Query q = em.createNativeQuery(
					"SELECT c.comp_id_cierre,sum(c.comp_valor) valor,sum(c.subt_vcer) subt_vcer,sum(c.subt_iva) subt_iva,sum(c.importe) importe FROM financiero.vista_recaudacion c WHERE c.comp_estado = 'A' and c.comp_check_cierre='true' and c.comp_id_cierre =?1 GROUP BY c.comp_id_cierre");
			q.setParameter(1, id_cierre);

			lista = q.getResultList();
			lista.forEach(x -> {
				VistaRecaudacionDTO v = new VistaRecaudacionDTO();
				v.setComp_id_cierre(Integer.parseInt(String.valueOf(x[0])));
				
				v.setComp_valor(Double.parseDouble(String.valueOf(x[1])));
				v.setSubt_vcer(Double.parseDouble(String.valueOf(x[2])));
				v.setSubt_iva(Double.parseDouble(String.valueOf(x[3])));
				v.setImporte(Double.parseDouble(String.valueOf(x[4])));
				listaFin.add(v);
			});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		
		
		return listaFin;
	}

}
