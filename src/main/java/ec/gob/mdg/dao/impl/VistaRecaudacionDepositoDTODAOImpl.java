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

import ec.gob.mdg.dao.IVistaRecaudacionDepositoDTODAO;
import ec.gob.mdg.model.VistaRecaudacionDepositoDTO;

@Stateless
public class VistaRecaudacionDepositoDTODAOImpl implements IVistaRecaudacionDepositoDTODAO, Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;

	@Override
	public Integer registrar(VistaRecaudacionDepositoDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer modificar(VistaRecaudacionDepositoDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer eliminar(VistaRecaudacionDepositoDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VistaRecaudacionDepositoDTO> listar() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VistaRecaudacionDepositoDTO listarPorId(VistaRecaudacionDepositoDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VistaRecaudacionDepositoDTO> listarRecaudacionDeposito(Date fecha_inicio, Date fecha_fin,
			Integer id_punto) {
		/// SUMAR UN DiA PARA QUE SE MUESTRE LA INFORMACI�N DEL MISMO DIA
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha_fin);
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		fecha_fin = calendar.getTime();

		List<Object[]> lista = new ArrayList<>();
		List<VistaRecaudacionDepositoDTO> listaFin = new ArrayList<VistaRecaudacionDepositoDTO>();
		try {
			Query q = em.createNativeQuery(
					"SELECT c.punto_nombre,c.comp_numero,c.comp_fechaemision,c.cliente_nombre,c.cliente_ci,c.deposito_numero,c.deposito_fecha,c.deposito_valor,c.usuario_nombre,c.deposito_fechastring FROM financiero.vista_recaudacion_deposito c WHERE c.punto_id=?1 and c.comp_fechaemision between ?2 and ?3 and c.comp_tipo='F' and c.comp_estado <> 'N' ORDER BY 3,2");
			q.setParameter(1, id_punto);
			q.setParameter(2, fecha_inicio);
			q.setParameter(3, fecha_fin);
			

			lista = q.getResultList();
			lista.forEach(x -> {
				VistaRecaudacionDepositoDTO v = new VistaRecaudacionDepositoDTO();

				v.setPunto_nombre(String.valueOf(x[0]));
				v.setComp_numero(Integer.parseInt(String.valueOf(x[1])));
				v.setComp_fechaemision(String.valueOf(x[2]));
				v.setCliente_nombre(String.valueOf(x[3]));
				v.setCliente_ci(String.valueOf(x[4]));
				v.setDeposito_numero(String.valueOf(x[5]));
				v.setDeposito_fecha(String.valueOf(x[6]));
				v.setComp_valor(Double.parseDouble(String.valueOf(x[7])));
				v.setUsuario_nombre(String.valueOf(x[8]));
				v.setDeposito_fechastring(String.valueOf(x[9]));
				listaFin.add(v);
			});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return listaFin;

	}

	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public Integer cuentaFacturas(Date fecha_inicio, Date fecha_fin, Integer id_punto) {
		/// SUMAR UN D�A PARA QUE SE MUESTRE LA INFORMACI�N DEL MISMO DIA
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha_fin); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		fecha_fin = calendar.getTime();

		Integer contador = 0;
		List<Object[]> lista = new ArrayList<>();
		List<VistaRecaudacionDepositoDTO> listaFin = new ArrayList<VistaRecaudacionDepositoDTO>();
		try {
			Query q = em.createNativeQuery(
					"SELECT c.punto_nombre,c.comp_numero,c.comp_fechaemision,c.cliente_nombre,c.cliente_ci,c.deposito_numero,c.deposito_fecha,c.deposito_valor,c.usuario_nombre,c.deposito_fechastring FROM financiero.vista_recaudacion_deposito c WHERE c.punto_id=?1 and c.comp_fechaemision between ?2 and ?3 and c.comp_tipo='F' and c.comp_estado <> 'N' ORDER BY 3,2");
			q.setParameter(1, id_punto);
			q.setParameter(2, fecha_inicio);
			q.setParameter(3, fecha_fin);

			lista = q.getResultList();
			lista.forEach(x -> {
				VistaRecaudacionDepositoDTO v = new VistaRecaudacionDepositoDTO();

				v.setPunto_nombre(String.valueOf(x[0]));
				v.setComp_numero(Integer.parseInt(String.valueOf(x[1])));
				v.setComp_fechaemision(String.valueOf(x[2]));
				v.setCliente_nombre(String.valueOf(x[3]));
				v.setCliente_ci(String.valueOf(x[4]));
				v.setDeposito_numero(String.valueOf(x[5]));
				v.setDeposito_fecha(String.valueOf(x[6]));
				v.setDeposito_valor(Double.parseDouble(String.valueOf(x[7])));
				v.setUsuario_nombre(String.valueOf(x[8]));
				v.setDeposito_fechastring(String.valueOf(x[9]));
				listaFin.add(v);
			});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		for (VistaRecaudacionDepositoDTO v : listaFin) {
			contador++;
		}

		return contador;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VistaRecaudacionDepositoDTO> listarRecaudacionDepositoNac(Date fecha_inicio, Date fecha_fin) {
		/// SUMAR UN D�A PARA QUE SE MUESTRE LA INFORMACI�N DEL MISMO DIA
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha_fin); // Configuramos la fecha que se recibe
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		fecha_fin = calendar.getTime();
		List<Object[]> lista = new ArrayList<>();
		List<VistaRecaudacionDepositoDTO> listaFin = new ArrayList<VistaRecaudacionDepositoDTO>();
		try {
			Query q = em.createNativeQuery(
					"SELECT a.punto_nombre,a.comp_numero,a.comp_fechaemision,a.cliente_nombre,a.cliente_ci ,a.deposito_numero ,a.deposito_fecha,a.deposito_valor,to_char(a.deposito_fecha,'dd/mm/yyyy') fechastring FROM financiero.vista_recaudacion_deposito a WHERE  a.comp_fechaemision between ?1 and ?2  and a.comp_tipo ='F' and a.comp_estado = 'A' ORDER BY 3,2");
			q.setParameter(1, fecha_inicio);
			q.setParameter(2, fecha_fin);
			lista = q.getResultList();
			lista.forEach(x -> {
				VistaRecaudacionDepositoDTO v = new VistaRecaudacionDepositoDTO();

				v.setPunto_nombre(String.valueOf(x[0]));
				v.setComp_numero(Integer.parseInt(String.valueOf(x[1])));
				v.setComp_fechaemision(String.valueOf(x[2]));
				v.setCliente_nombre(String.valueOf(x[3]));
				v.setCliente_ci(String.valueOf(x[4]));
				v.setDeposito_numero(String.valueOf(x[5]));
				v.setDeposito_fecha(String.valueOf(x[6]));
				v.setComp_valor(Double.parseDouble(String.valueOf(x[7])));
				v.setDeposito_fechastring(String.valueOf(x[8]));
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
		List<VistaRecaudacionDepositoDTO> listaFin = new ArrayList<VistaRecaudacionDepositoDTO>();
		try {
			Query q = em.createNativeQuery(
					"SELECT c.punto_nombre,c.comp_numero,c.comp_fechaemision,c.cliente_nombre,c.cliente_ci,c.deposito_numero,c.deposito_fecha,c.deposito_valor,c.usuario_nombre,c.deposito_fechastring FROM financiero.vista_recaudacion_deposito c WHERE c.comp_fechaemision between ?1 and ?2 and c.comp_tipo='F' and c.comp_estado = 'A' ORDER BY 3,2");
			q.setParameter(1, fecha_inicio);
			q.setParameter(2, fecha_fin);
			lista = q.getResultList();
			lista.forEach(x -> {
				VistaRecaudacionDepositoDTO v = new VistaRecaudacionDepositoDTO();

				v.setPunto_nombre(String.valueOf(x[0]));
				v.setComp_numero(Integer.parseInt(String.valueOf(x[1])));
				v.setComp_fechaemision(String.valueOf(x[2]));
				v.setCliente_nombre(String.valueOf(x[3]));
				v.setCliente_ci(String.valueOf(x[4]));
				v.setDeposito_numero(String.valueOf(x[5]));
				v.setDeposito_fecha(String.valueOf(x[6]));
				v.setDeposito_valor(Double.parseDouble(String.valueOf(x[7])));
				v.setUsuario_nombre(String.valueOf(x[8]));
				v.setDeposito_fechastring(String.valueOf(x[9]));
				listaFin.add(v);
			});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		for (VistaRecaudacionDepositoDTO v : listaFin) {
			contador++;
		}

		return contador;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VistaRecaudacionDepositoDTO> listarConsultaBancoVsBanco(Integer id_punto, Integer anio, Integer mes) {

		String anioS = String.valueOf(anio);
		String mesS;
		if (mes < 10) {
			mesS = "0" + mes;
		} else {
			mesS = String.valueOf(mes);
		}

		List<Object[]> lista = new ArrayList<>();
		List<VistaRecaudacionDepositoDTO> listaFin = new ArrayList<VistaRecaudacionDepositoDTO>();
		try {
			Query q = em.createNativeQuery(
					"SELECT c.punto_nombre,c.anio,c.mes,c.comp_numero,c.comp_fechaemision,c.cliente_nombre,c.cliente_ci,c.deposito_numero,c.deposito_fecha,c.deposito_valor,c.usuario_nombre,c.estadocuenta_valor,c.estadocuenta_anio,c.deposito_fechastring FROM financiero.vista_conciliacion1 c WHERE c.punto_id=?1 and c.anio =?2 and c.mes=?3 ORDER BY 4");
			q.setParameter(1, id_punto);
			q.setParameter(2, anioS);
			q.setParameter(3, mesS);

			lista = q.getResultList();
			lista.forEach(x -> {
				VistaRecaudacionDepositoDTO v = new VistaRecaudacionDepositoDTO();
				v.setPunto_nombre(String.valueOf(x[0]));
				v.setAnio(Integer.parseInt(String.valueOf(x[1])));
				v.setMes(Integer.parseInt(String.valueOf(x[2])));
				v.setComp_numero(Integer.parseInt(String.valueOf(x[3])));
				v.setComp_fechaemision(String.valueOf(x[4]));
				v.setCliente_nombre(String.valueOf(x[5]));
				v.setCliente_ci(String.valueOf(x[6]));
				v.setDeposito_numero(String.valueOf(x[7]));
				v.setDeposito_fecha(String.valueOf(x[8]));
				v.setDeposito_valor(Double.parseDouble(String.valueOf(x[9])));
				v.setUsuario_nombre(String.valueOf(x[10]));
				v.setEstadocuenta_valor(Double.parseDouble(String.valueOf(x[11])));
				v.setEstadocuenta_anio(Integer.parseInt(String.valueOf(x[12])));
				v.setDeposito_fechastring(String.valueOf(x[13]));
				listaFin.add(v);
			});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return listaFin;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VistaRecaudacionDepositoDTO> listarDepositosNoBanco(Integer id_punto, Integer anio, Integer mes) {
		String anioS = String.valueOf(anio);
		String mesS;
		if (mes < 10) {
			mesS = "0" + mes;
		} else {
			mesS = String.valueOf(mes);
		}
		
//		System.out.println("año " +  anioS);
//		System.out.println("mes " +  mesS);
//		System.out.println("punto " +  id_punto);

		List<Object[]> lista = new ArrayList<>();
		List<VistaRecaudacionDepositoDTO> listaFin = new ArrayList<VistaRecaudacionDepositoDTO>();
		try {
			Query q = em.createNativeQuery(
					"SELECT c.punto_nombre,c.comp_anio,c.comp_mes,c.comp_numero,c.comp_fechaemision,c.cliente_nombre,c.cliente_ci,c.deposito_numero,c.deposito_fecha,c.deposito_valor,c.usuario_nombre,c.deposito_fechastring FROM financiero.vista_recaudacion_deposito c "
							+ "WHERE c.punto_id=?1 and c.comp_anio =?2 and c.comp_mes=?3 AND "
							+ "c.deposito_id_tmp is null ORDER BY 4");
			q.setParameter(1, id_punto);
			q.setParameter(2, anioS);
			q.setParameter(3, mesS);

			lista = q.getResultList();
			lista.forEach(x -> {
				VistaRecaudacionDepositoDTO v = new VistaRecaudacionDepositoDTO();
				v.setPunto_nombre(String.valueOf(x[0]));
				v.setAnio(Integer.parseInt(String.valueOf(x[1])));
				v.setMes(Integer.parseInt(String.valueOf(x[2])));
				v.setComp_numero(Integer.parseInt(String.valueOf(x[3])));
				v.setComp_fechaemision(String.valueOf(x[4]));
				v.setCliente_nombre(String.valueOf(x[5]));
				v.setCliente_ci(String.valueOf(x[6]));
				v.setDeposito_numero(String.valueOf(x[7]));
				v.setDeposito_fecha(String.valueOf(x[8]));
				v.setDeposito_valor(Double.parseDouble(String.valueOf(x[9])));
				v.setUsuario_nombre(String.valueOf(x[10]));
				v.setDeposito_fechastring(String.valueOf(x[11]));
				listaFin.add(v);
			});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return listaFin;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VistaRecaudacionDepositoDTO> listarDepositosVariasFacturas(Integer id_punto, Integer anio,
			Integer mes) {
		String anioS = String.valueOf(anio);
		String mesS;
		if (mes < 10) {
			mesS = "0" + mes;
		} else {
			mesS = String.valueOf(mes);
		}

		List<Object[]> lista = new ArrayList<>();
		List<VistaRecaudacionDepositoDTO> listaFin = new ArrayList<VistaRecaudacionDepositoDTO>();
		try {
			Query q = em.createNativeQuery(
					"SELECT a.deposito_numero,a.comp_numero,a.cliente_nombre,a.cliente_ci,a.punto_nombre,a.punto_id,a.comp_fechaemision FROM financiero.vista_recaudacion_deposito a "
							+ "WHERE a.punto_id=?1 AND a.comp_anio =?2 and a.comp_mes=?3 AND a.comp_estado in ('A','C')  "
							+ "AND a.deposito_numero IN (SELECT deposito_numero "
							+ " FROM (SELECT count(*) cantidad, a.deposito_numero "
							+ "FROM financiero.vista_recaudacion_deposito a " + "WHERE a.comp_estado in ('A','C') "
							+ "AND a.comp_anio=?2 and a.comp_mes=?3 " + "AND a.punto_id=?1 "
							+ "GROUP by a.deposito_numero)X " + " WHERE x.cantidad >1)");
			q.setParameter(1, id_punto);
			q.setParameter(2, anioS);
			q.setParameter(3, mesS);

			lista = q.getResultList();
			lista.forEach(x -> {
				VistaRecaudacionDepositoDTO v = new VistaRecaudacionDepositoDTO();

				v.setDeposito_numero(String.valueOf(x[0]));
				v.setComp_numero(Integer.parseInt(String.valueOf(x[1])));
				v.setCliente_nombre(String.valueOf(x[2]));
				v.setCliente_ci(String.valueOf(x[3]));
				v.setPunto_nombre(String.valueOf(x[4]));
				v.setPunto_id(Integer.parseInt(String.valueOf(x[5])));
				v.setComp_fechaemision(String.valueOf(x[6]));
				listaFin.add(v);
			});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return listaFin;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VistaRecaudacionDepositoDTO> listarEstadoSinColidar(Integer id_punto, Integer anio, Integer mes) {
		
		String anioS = String.valueOf(anio);
		String mesS;
		if (mes < 10) {
			mesS = "0" + mes;
		} else {
			mesS = String.valueOf(mes);
		}

		List<Object[]> lista = new ArrayList<>();
		List<VistaRecaudacionDepositoDTO> listaFin = new ArrayList<VistaRecaudacionDepositoDTO>();
		try {
			Query q = em.createNativeQuery(
					"SELECT  c.punto_nombre,c.comp_anio,c.comp_mes,c.comp_numero,c.comp_fechaemision,c.cliente_nombre,"
							+ " c.cliente_ci,c.deposito_numero,c.deposito_fecha,c.deposito_valor,c.usuario_nombre,c.deposito_fechastring "
							+ " FROM financiero.vista_recaudacion_deposito c "
							+ "WHERE c.punto_id=?1 and c.comp_anio =?2 and c.comp_mes=?3 and c.deposito_id_tmp is null ORDER BY 4");
			q.setParameter(1, id_punto);
			q.setParameter(2, anioS);
			q.setParameter(3, mesS);

			lista = q.getResultList();
			lista.forEach(x -> {
				VistaRecaudacionDepositoDTO v = new VistaRecaudacionDepositoDTO();
				v.setPunto_nombre(String.valueOf(x[0]));
				v.setAnio(Integer.parseInt(String.valueOf(x[1])));
				v.setMes(Integer.parseInt(String.valueOf(x[2])));
				v.setComp_numero(Integer.parseInt(String.valueOf(x[3])));
				v.setComp_fechaemision(String.valueOf(x[4]));
				v.setCliente_nombre(String.valueOf(x[5]));
				v.setCliente_ci(String.valueOf(x[6]));
				v.setDeposito_numero(String.valueOf(x[7]));
				v.setDeposito_fecha(String.valueOf(x[8]));
				v.setDeposito_valor(Double.parseDouble(String.valueOf(x[9])));
				v.setUsuario_nombre(String.valueOf(x[10]));
				v.setDeposito_fechastring(String.valueOf(x[11]));
				listaFin.add(v);
			});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return listaFin;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VistaRecaudacionDepositoDTO> listarVista(Integer anio, Integer mes) {
		String anioS = String.valueOf(anio);
		String mesS;
		if (mes < 10) {
			mesS = "0" + mes;
		} else {
			mesS = String.valueOf(mes);
		}

		List<Object[]> lista = new ArrayList<>();
		List<VistaRecaudacionDepositoDTO> listaFin = new ArrayList<VistaRecaudacionDepositoDTO>();
		try {
			Query q = em.createNativeQuery(
					"SELECT c.punto_nombre,c.comp_anio,c.comp_mes,c.comp_numero,c.comp_fechaemision,c.cliente_nombre,c.cliente_ci,c.deposito_numero,c.deposito_fecha,c.deposito_valor,c.usuario_nombre,COALESCE(c.deposito_observacion,'S/O') FROM financiero.vista_recaudacion_deposito c WHERE c.comp_anio =?1 and c.comp_mes=?2 ORDER BY 4");

			q.setParameter(1, anioS);
			q.setParameter(2, mesS);

			lista = q.getResultList();
			lista.forEach(x -> {
				VistaRecaudacionDepositoDTO v = new VistaRecaudacionDepositoDTO();
				v.setPunto_nombre(String.valueOf(x[0]));
				v.setAnio(Integer.parseInt(String.valueOf(x[1])));
				v.setMes(Integer.parseInt(String.valueOf(x[2])));
				v.setComp_numero(Integer.parseInt(String.valueOf(x[3])));
				v.setComp_fechaemision(String.valueOf(x[4]));
				v.setCliente_nombre(String.valueOf(x[5]));
				v.setCliente_ci(String.valueOf(x[6]));
				v.setDeposito_numero(String.valueOf(x[7]));
				v.setDeposito_fecha(String.valueOf(x[8]));
				v.setDeposito_valor(Double.parseDouble(String.valueOf(x[9])));
				v.setUsuario_nombre(String.valueOf(x[10]));
				v.setDeposito_observacion(String.valueOf(x[11]));
				listaFin.add(v);
			});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		

		return listaFin;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VistaRecaudacionDepositoDTO> listarVistaAnio(Integer anio) {
		String anioS = String.valueOf(anio);

		List<Object[]> lista = new ArrayList<>();
		List<VistaRecaudacionDepositoDTO> listaFin = new ArrayList<VistaRecaudacionDepositoDTO>();
		try {
			Query q = em.createNativeQuery(
					"SELECT c.punto_nombre,c.comp_anio,c.comp_mes,c.comp_numero,c.comp_fechaemision,c.cliente_nombre,c.cliente_ci,c.deposito_numero,c.deposito_fecha,c.deposito_valor,c.usuario_nombre,COALESCE(c.deposito_observacion,'S/O') FROM financiero.vista_recaudacion_deposito c WHERE c.comp_anio =?1 ORDER BY 4");

			q.setParameter(1, anioS);
			lista = q.getResultList();
			lista.forEach(x -> {
				VistaRecaudacionDepositoDTO v = new VistaRecaudacionDepositoDTO();
				v.setPunto_nombre(String.valueOf(x[0]));
				v.setAnio(Integer.parseInt(String.valueOf(x[1])));
				v.setMes(Integer.parseInt(String.valueOf(x[2])));
				v.setComp_numero(Integer.parseInt(String.valueOf(x[3])));
				v.setComp_fechaemision(String.valueOf(x[4]));
				v.setCliente_nombre(String.valueOf(x[5]));
				v.setCliente_ci(String.valueOf(x[6]));
				v.setDeposito_numero(String.valueOf(x[7]));
				v.setDeposito_fecha(String.valueOf(x[8]));
				v.setDeposito_valor(Double.parseDouble(String.valueOf(x[9])));
				v.setUsuario_nombre(String.valueOf(x[10]));
				v.setDeposito_observacion(String.valueOf(x[11]));
				listaFin.add(v);
			});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return listaFin;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VistaRecaudacionDepositoDTO> listarConsultaBancoVsBancoNacional(Integer anio, Integer mes) {
		String anioS = String.valueOf(anio);
		String mesS;
		if (mes < 10) {
			mesS = "0" + mes;
		} else {
			mesS = String.valueOf(mes);
		}

		List<Object[]> lista = new ArrayList<>();
		List<VistaRecaudacionDepositoDTO> listaFin = new ArrayList<VistaRecaudacionDepositoDTO>();
		try {
			Query q = em.createNativeQuery(
					"SELECT c.punto_nombre,c.anio,c.mes,c.comp_numero,c.comp_fechaemision,c.cliente_nombre,c.cliente_ci,c.deposito_numero,c.deposito_fecha,c.deposito_valor,c.usuario_nombre,c.estadocuenta_valor,c.estadocuenta_anio FROM financiero.vista_conciliacion1 c WHERE c.anio =?1 and c.mes=?2 ORDER BY 4");
			q.setParameter(1, anioS);
			q.setParameter(2, mesS);

			lista = q.getResultList();
			lista.forEach(x -> {
				VistaRecaudacionDepositoDTO v = new VistaRecaudacionDepositoDTO();
				v.setPunto_nombre(String.valueOf(x[0]));
				v.setAnio(Integer.parseInt(String.valueOf(x[1])));
				v.setMes(Integer.parseInt(String.valueOf(x[2])));
				v.setComp_numero(Integer.parseInt(String.valueOf(x[3])));
				v.setComp_fechaemision(String.valueOf(x[4]));
				v.setCliente_nombre(String.valueOf(x[5]));
				v.setCliente_ci(String.valueOf(x[6]));
				v.setDeposito_numero(String.valueOf(x[7]));
				v.setDeposito_fecha(String.valueOf(x[8]));
				v.setDeposito_valor(Double.parseDouble(String.valueOf(x[9])));
				v.setUsuario_nombre(String.valueOf(x[10]));
				v.setEstadocuenta_valor(Double.parseDouble(String.valueOf(x[11])));
				v.setEstadocuenta_anio(Integer.parseInt(String.valueOf(x[12])));
				listaFin.add(v);
			});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return listaFin;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VistaRecaudacionDepositoDTO> listarDepositosNoBancoNacional(Integer anio, Integer mes) {
		String anioS = String.valueOf(anio);
		String mesS;
		if (mes < 10) {
			mesS = "0" + mes;
		} else {
			mesS = String.valueOf(mes);
		}

		List<Object[]> lista = new ArrayList<>();
		List<VistaRecaudacionDepositoDTO> listaFin = new ArrayList<VistaRecaudacionDepositoDTO>();
		try {
			Query q = em.createNativeQuery(
					"SELECT c.punto_nombre,c.comp_anio,c.comp_mes,c.comp_numero,c.comp_fechaemision,c.cliente_nombre,c.cliente_ci,c.deposito_numero,c.deposito_fecha,c.deposito_valor,c.usuario_nombre FROM financiero.vista_recaudacion_deposito c "
							+ "WHERE c.comp_anio =?1 and c.comp_mes=?2 AND "
							+ "c.deposito_id_tmp is null ORDER BY 4");
		
			q.setParameter(1, anioS);
			q.setParameter(2, mesS);

			lista = q.getResultList();
			lista.forEach(x -> {
				VistaRecaudacionDepositoDTO v = new VistaRecaudacionDepositoDTO();
				v.setPunto_nombre(String.valueOf(x[0]));
				v.setAnio(Integer.parseInt(String.valueOf(x[1])));
				v.setMes(Integer.parseInt(String.valueOf(x[2])));
				v.setComp_numero(Integer.parseInt(String.valueOf(x[3])));
				v.setComp_fechaemision(String.valueOf(x[4]));
				v.setCliente_nombre(String.valueOf(x[5]));
				v.setCliente_ci(String.valueOf(x[6]));
				v.setDeposito_numero(String.valueOf(x[7]));
				v.setDeposito_fecha(String.valueOf(x[8]));
				v.setDeposito_valor(Double.parseDouble(String.valueOf(x[9])));
				v.setUsuario_nombre(String.valueOf(x[10]));
				listaFin.add(v);
			});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return listaFin;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VistaRecaudacionDepositoDTO> listarComprobantesNumDeposito(String num_deposito,Integer anio) {
	
		String s= String.valueOf(anio);
		Double anioD = Double.parseDouble(s);
		List<Object[]> lista = new ArrayList<>();
		List<VistaRecaudacionDepositoDTO> listaFin = new ArrayList<VistaRecaudacionDepositoDTO>();
		
		try {
			Query q = em.createNativeQuery(
					"SELECT c.punto_nombre,c.comp_anio,c.comp_mes,c.comp_numero,c.comp_fechaemision,c.cliente_nombre,c.cliente_ci,c.deposito_numero,c.deposito_fecha,c.deposito_valor,c.usuario_nombre,c.deposito_id FROM financiero.vista_recaudacion_deposito c "
							+ "WHERE date_part('year',c.deposito_fecha)=?1 AND "
							+ "c.deposito_numero = ?2 and c.deposito_id_tmp is null");
			q.setParameter(1, anioD);
			q.setParameter(2, num_deposito);
			
			lista = q.getResultList();
			 
			lista.forEach(x -> {
				VistaRecaudacionDepositoDTO v = new VistaRecaudacionDepositoDTO();
				v.setPunto_nombre(String.valueOf(x[0]));
				v.setAnio(Integer.parseInt(String.valueOf(x[1])));
				v.setMes(Integer.parseInt(String.valueOf(x[2])));
				v.setComp_numero(Integer.parseInt(String.valueOf(x[3])));
				v.setComp_fechaemision(String.valueOf(x[4]));
				v.setCliente_nombre(String.valueOf(x[5]));
				v.setCliente_ci(String.valueOf(x[6]));
				v.setDeposito_numero(String.valueOf(x[7]));
				v.setDeposito_fecha(String.valueOf(x[8]));
				v.setDeposito_valor(Double.parseDouble(String.valueOf(x[9])));
				v.setUsuario_nombre(String.valueOf(x[10]));
				v.setDeposito_id(Integer.parseInt(String.valueOf(x[11])));
				listaFin.add(v);
			});

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	

		return listaFin;
	}

	@SuppressWarnings("unchecked")
	@Override
	public VistaRecaudacionDepositoDTO ComprobantesDepNumDeposito(Integer id_deposito) {
		List<Object[]> lista = new ArrayList<>();
		List<VistaRecaudacionDepositoDTO> listaFin = new ArrayList<VistaRecaudacionDepositoDTO>();
		VistaRecaudacionDepositoDTO vistaRecaudacionDepositoDTO = new VistaRecaudacionDepositoDTO();
		try {
			Query q = em.createNativeQuery(
					"SELECT c.punto_nombre,c.comp_anio,c.comp_mes,c.comp_numero,c.comp_fechaemision,c.cliente_nombre,c.cliente_ci,c.deposito_numero,c.deposito_fecha,c.deposito_valor,c.usuario_nombre FROM financiero.vista_recaudacion_deposito c "
							+ "WHERE c.deposito_id = ?1");
			
			q.setParameter(1, id_deposito);

			lista = q.getResultList();
			lista.forEach(x -> {
				VistaRecaudacionDepositoDTO v = new VistaRecaudacionDepositoDTO();
				v.setPunto_nombre(String.valueOf(x[0]));
				
				v.setAnio(Integer.parseInt(String.valueOf(x[1])));
				v.setMes(Integer.parseInt(String.valueOf(x[2])));
				v.setComp_numero(Integer.parseInt(String.valueOf(x[3])));
				v.setComp_fechaemision(String.valueOf(x[4]));
				v.setCliente_nombre(String.valueOf(x[5]));
				v.setCliente_ci(String.valueOf(x[6]));
				v.setDeposito_numero(String.valueOf(x[7]));
				v.setDeposito_fecha(String.valueOf(x[8]));
				v.setDeposito_valor(Double.parseDouble(String.valueOf(x[9])));
				v.setUsuario_nombre(String.valueOf(x[10]));
				listaFin.add(v);
			});

			if (listaFin != null && !listaFin.isEmpty()) {
				vistaRecaudacionDepositoDTO = listaFin.get(0);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return vistaRecaudacionDepositoDTO;
	}
}
