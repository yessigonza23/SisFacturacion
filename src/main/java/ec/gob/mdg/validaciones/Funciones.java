package ec.gob.mdg.validaciones;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import ec.gob.mdg.model.Cliente;
import ec.gob.mdg.model.Comprobante;
import ec.gob.mdg.model.ParametroDetalle;
import ec.gob.mdg.model.RecaudacionDetalle;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.util.UtilsDate;

@Named
@RequestScoped
public class Funciones implements Serializable {

	private static final long serialVersionUID = 1L;

	Usuario p = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");

	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;

	// BUSCAR CLIENTE POR OBJETO
	public Long buscarCliente(Cliente cli) throws Exception {
		Long valor = null;
		try {
			Query query = em.createQuery("select count(*) from Cliente a where a.ci =?1");
			query.setParameter(1, cli.getCi());
			valor = ((Long) query.getSingleResult()).longValue();
		} catch (Exception em) {
			System.out.println(em);
		}
		return valor;
	}

	// BUSCAR CLIENTE POR RUC
	public Long buscarCliente(String ruc) throws Exception {
		Long valor = null;
		try {
			Query query = em.createQuery("select count(*) from Cliente a where a.ci =?1");
			query.setParameter(1, ruc);
			valor = ((Long) query.getSingleResult()).longValue();
		} catch (Exception em) {
			System.out.println(em);
		}
		return valor;
	}

	///// SECUENCIAL DE FACTURAS
	public Number secFactura(UsuarioPunto p) throws Exception {
		Number factura = null;
		// usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(p);
		try {
			Query query = em
					.createNativeQuery("select (secuencialfactura)+1 from financiero.puntorecaudacion where id=?1");
			query.setParameter(1, p.getPuntoRecaudacion());
			factura = (Number) query.getSingleResult();
		} catch (Exception e) {
			System.out.println(e);
		}
		return factura;
	}

	///// SECUENCIAL DE NOTAS DE CREDITO
	public Number secNotas(UsuarioPunto p) throws Exception {
		Number nota = null;
		// usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(p);
		try {
			Query query = em
					.createNativeQuery("select (secuencialnotas)+1 from financiero.puntorecaudacion where id=?1");
			query.setParameter(1, p.getPuntoRecaudacion());
			nota = (Number) query.getSingleResult();
		} catch (Exception e) {
			System.out.println(e);
		}
		return nota;
	}

	// MUESTRA EL NUMERO DE FACTURA
	public Number muestraFactura(UsuarioPunto p) throws Exception {
		Number factura = null;
		// usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(p);
		try {
			Query query = em
					.createNativeQuery("select (secuencialfactura) from financiero.puntorecaudacion where id=?1");
			query.setParameter(1, p.getPuntoRecaudacion());
			factura = (Number) query.getSingleResult();
		} catch (Exception e) {
			System.out.println(e);
		}
		return factura;
	}

	// MUESTRA EL NUMERO DE NOTAS DE CREDITO
	public Number muestraNotas(UsuarioPunto p) throws Exception {
		Number nota = null;
		try {
			Query query = em
					.createNativeQuery("select (p.secuencialnotas) from financiero.puntorecaudacion p where p.id=?1");
			query.setParameter(1, p.getPuntoRecaudacion());
			nota = (Number) query.getSingleResult();
		} catch (Exception e) {
			System.out.println(e);
		}
		return nota;
	}

	// ACTUALIZA SECUENCIA EN FACTURA EN PUNTO DE RECAUDACI�N
	@Transactional
	public void actualizaSecuencialFactura(UsuarioPunto p, Number secuencia) throws Exception {
		try {
			Query query = em
					.createNativeQuery("update financiero.puntorecaudacion set secuencialfactura=?1 where id=?2 ");
			query.setParameter(1, secuencia);
			query.setParameter(2, p.getPuntoRecaudacion());
			query.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// ACTUALIZA SECUENCIA EN NOTA EN PUNTO DE RECAUDACI�N
	@Transactional
	public void actualizaSecuencialNotas(UsuarioPunto p, Number secuencia) throws Exception {

		try {
			Query query = em
					.createNativeQuery("update financiero.puntorecaudacion set secuencialnotas=?1 where id=?2 ");
			query.setParameter(1, secuencia);
			query.setParameter(2, p.getPuntoRecaudacion());
			query.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// VALOR IVA
	@SuppressWarnings("unchecked")
	public Double valorIva() {
		Double valorIVA = 0.00;
		ParametroDetalle p = new ParametroDetalle();
		List<ParametroDetalle> lstPar = new ArrayList<>();

		/// SACAR PARAMETRO DEL RMU
		Query q = em.createQuery("FROM ParametroDetalle pd where pd.parametro = 1");
		lstPar = (List<ParametroDetalle>) q.getResultList();
		if (lstPar != null && !lstPar.isEmpty()) {
			p = lstPar.get(0);
		}
		valorIVA = Double.parseDouble(p.getValor());

		return valorIVA;
	}

	// VALOR DE LA RECAUDACION
	@SuppressWarnings("unchecked")
	public Double valorRecaudacionDetalle(Integer rd) {
		Double valor = 0.00;
		Double valorRMU = 0.00;
		ParametroDetalle p = new ParametroDetalle();
		List<ParametroDetalle> lstPar = new ArrayList<>();
		RecaudacionDetalle r = new RecaudacionDetalle();
		List<RecaudacionDetalle> lstRecDet = new ArrayList<>();

		try {
			/// SACAR PARAMETRO DEL RMU
			Query q = em.createQuery("FROM ParametroDetalle pd where pd.parametro = 2");
			lstPar = (List<ParametroDetalle>) q.getResultList();
			if (lstPar != null && !lstPar.isEmpty()) {
				p = lstPar.get(0);
			}
			valorRMU = Double.parseDouble(p.getValor());

			// SACA DATOS DEL DETALLE DE RECAUDACION
			Query query = em.createQuery("FROM RecaudacionDetalle r where r.id =?1");
			query.setParameter(1, rd);
			lstRecDet = (List<RecaudacionDetalle>) query.getResultList();
			if (lstRecDet != null && !lstRecDet.isEmpty()) {
				r = lstRecDet.get(0);
			}
		} catch (Exception e) {
			throw e;
		}

		valor = r.getFactor() * valorRMU + r.getValor() + r.getValoriva();

		return valor;
	}

	// VALOR DE LA RECAUDACION CON EL OBJETO RECAUDACION DETALLE
	@SuppressWarnings("unchecked")
	public Double valorRecaudacionDetalle(RecaudacionDetalle rd) {
		Double valor = 0.00;
		Double valorRMU = 0.00;

		ParametroDetalle p = new ParametroDetalle();
		List<ParametroDetalle> lstPar = new ArrayList<>();

		RecaudacionDetalle r = new RecaudacionDetalle();
		List<RecaudacionDetalle> lstRecDet = new ArrayList<>();

		try {
			/// SACAR PARAMETRO DEL RMU
			Query q = em.createQuery("FROM ParametroDetalle pd where pd.parametro = 2");
			lstPar = (List<ParametroDetalle>) q.getResultList();
			if (lstPar != null && !lstPar.isEmpty()) {
				p = lstPar.get(0);
			}
			valorRMU = Double.parseDouble(p.getValor());

			// SACA DATOS DEL DETALLE DE RECAUDACION
			Query query = em.createQuery("FROM RecaudacionDetalle r where r.id =?1");
			query.setParameter(1, rd.getId());
			lstRecDet = (List<RecaudacionDetalle>) query.getResultList();
			if (lstRecDet != null && !lstRecDet.isEmpty()) {
				r = lstRecDet.get(0);
			}
		} catch (Exception e) {
			throw e;
		}

		valor = r.getFactor() * valorRMU + r.getValor() + r.getValoriva();

		return valor;
	}

	// VALOR DE RECAUDACION DETALLE PARA EXPORTACIONES POR FBO
	@SuppressWarnings({ "unchecked", "unused" })
	public Double valorRecaudacionDetalleFBOExportacion(Double pfbo) {

		List<ParametroDetalle> lstPar = new ArrayList<>();
		ParametroDetalle p = new ParametroDetalle();

		List<RecaudacionDetalle> lstRecDet = new ArrayList<>();
		RecaudacionDetalle recaudacionDetalle = new RecaudacionDetalle();

		Double valor = 0.00;
		Double valorRMU = 0.00;
		Double pfactor = 0.00;
		Date fechaActual;
		Integer anio;
		String aux = "PF";
		String aux1 = "P%";
		Integer cod = 1;
		Integer anioa = 2021;

		try {
			// SACA DATOS DEL DETALLE DE RECAUDACION

			Query query = em.createQuery("SELECT rd \r\n" + "FROM Proceso p,Recaudacion r,RecaudacionDetalle rd\r\n"
					+ "WHERE p.id=r.proceso.id\r\n" + " and r.id = rd.recaudacion.id\r\n" + " and p.id=?1 "
					+ "and substring(rd.codigo,1,2)!= ?2 and rd.codigo like ?3 ORDER BY rd.rangoinicial ");
			query.setParameter(1, cod);
			query.setParameter(2, aux);
			query.setParameter(3, aux1);

			lstRecDet = (List<RecaudacionDetalle>) query.getResultList();
			for (RecaudacionDetalle rd : lstRecDet) {

				if (pfbo >= rd.getRangoinicial() && pfbo <= rd.getRangofinal()) {
					pfactor = rd.getFactor();
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"el factor esta fuera de rango, no hay codigo de recaudacion", "ERROR"));
				}
			}

			fechaActual = UtilsDate.fechaActual();
			Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
			calendar.setTime(fechaActual);
			anio = calendar.get(Calendar.YEAR);

			/// SACAR PARAMETRO DEL RMU
			Query q = em.createQuery("FROM ParametroDetalle pd where pd.parametro.id = 2 ");
//			query.setParameter(1, anioa);
//			and pd.anio= ?1 /// Yessi falta transformar el a�o, porque no me calcula con el parametro anio

			lstPar = (List<ParametroDetalle>) q.getResultList();
			if (lstRecDet != null && !lstRecDet.isEmpty()) {
				p = lstPar.get(0);
			}

			valorRMU = Double.parseDouble(p.getValor());

		} catch (Exception e) {
			throw e;
		}

		valor = pfactor * valorRMU;
		return valor;
	}

	// VALOR DE RECAUDACION DETALLE PARA EXPORTACIONES POR FBO
	@SuppressWarnings({ "unused", "unchecked" })
	public String codigoRecaudacionDetalleFBOExportacion(Double pfbo) {

		RecaudacionDetalle r = new RecaudacionDetalle();
		List<RecaudacionDetalle> lstRecDet = new ArrayList<>();

		String pcodigorecaudacion = null;
		String aux = "PF";
		String aux1 = "P%";
		Integer cod = 1;

		try {
			// SACA DATOS DEL DETALLE DE RECAUDACION
			Query query = em.createQuery("SELECT rd \r\n" + "FROM Proceso p,Recaudacion r,RecaudacionDetalle rd\r\n"
					+ "WHERE p.id=r.proceso.id\r\n" + " and r.id = rd.recaudacion.id\r\n" + " and p.id=?1 "
					+ "and substring(rd.codigo,1,2)!= ?2 and rd.codigo like ?3 ");
			query.setParameter(1, cod);
			query.setParameter(2, aux);
			query.setParameter(3, aux1);

			lstRecDet = (List<RecaudacionDetalle>) query.getResultList();
			for (RecaudacionDetalle rd : lstRecDet) {

				if (pfbo >= rd.getRangoinicial() && pfbo <= rd.getRangofinal()) {
					pcodigorecaudacion = rd.getCodigo();
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"el factor esta fuera de rango, no hay codigo de recaudacion", "ERROR"));
				}
			}

		} catch (Exception e) {
			throw e;
		}

		return pcodigorecaudacion;
	}

	// VALOR DE RECAUDACION DETALLE PARA IMPORTACIONES POR FBO
	@SuppressWarnings({ "unchecked", "unused" })
	public Double valorRecaudacionDetalleFBOImportacion(Double pfbo) {

		List<ParametroDetalle> lstPar = new ArrayList<>();
		ParametroDetalle p = new ParametroDetalle();

		List<RecaudacionDetalle> lstRecDet = new ArrayList<>();
		RecaudacionDetalle recaudacionDetalle = new RecaudacionDetalle();

		Double valor = 0.00;
		Double valorRMU = 0.00;
		Double pfactor = 0.00;
		Date fechaActual;
		Integer anio;
		String aux = "I%";
		Integer cod = 1;
		Integer anioa = 2021;

		try {
			// SACA DATOS DEL DETALLE DE RECAUDACION

			Query query = em.createQuery("SELECT rd FROM Proceso p,Recaudacion r,RecaudacionDetalle rd "
					+ "WHERE p.id=r.proceso.id and r.id = rd.recaudacion.id and p.id=?1 "
					+ "and rd.codigo like ?2 ORDER BY rd.rangoinicial ");
			query.setParameter(1, cod);
			query.setParameter(2, aux);

			lstRecDet = (List<RecaudacionDetalle>) query.getResultList();
			for (RecaudacionDetalle rd : lstRecDet) {

				if (pfbo >= rd.getRangoinicial() && pfbo <= rd.getRangofinal()) {
					pfactor = rd.getFactor();
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"el factor esta fuera de rango, no hay codigo de recaudacion", "ERROR"));
				}
			}

			fechaActual = UtilsDate.fechaActual();
			Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
			calendar.setTime(fechaActual);
			anio = calendar.get(Calendar.YEAR);

			/// SACAR PARAMETRO DEL RMU
			Query q = em.createQuery("FROM ParametroDetalle pd where pd.parametro.id = 2 ");
//				query.setParameter(1, anioa);
//				and pd.anio= ?1 /// Yessi falta transformar el a�o, porque no me calcula con el parametro anio

			lstPar = (List<ParametroDetalle>) q.getResultList();
			if (lstRecDet != null && !lstRecDet.isEmpty()) {
				p = lstPar.get(0);
			}

			valorRMU = Double.parseDouble(p.getValor());

		} catch (Exception e) {
			throw e;
		}

		valor = pfactor * valorRMU;
		return valor;
	}

	// VALOR DE RECAUDACION DETALLE PARA IMPORTACIONES POR FBO
	@SuppressWarnings({ "unchecked", "unused" })
	public String codigoRecaudacionDetalleFBOImportacion(Double pfbo) {

		RecaudacionDetalle r = new RecaudacionDetalle();
		List<RecaudacionDetalle> lstRecDet = new ArrayList<>();

		String pcodigorecaudacion = null;
		String aux = "I%";
		Integer cod = 1;

		try {
			// SACA DATOS DEL DETALLE DE RECAUDACION
			Query query = em.createQuery("SELECT rd FROM Proceso p,Recaudacion r,RecaudacionDetalle rd "
					+ "WHERE p.id=r.proceso.id and r.id = rd.recaudacion.id and p.id=?1 " + "and rd.codigo like ?2 ");
			query.setParameter(1, cod);
			query.setParameter(2, aux);

			lstRecDet = (List<RecaudacionDetalle>) query.getResultList();
			for (RecaudacionDetalle rd : lstRecDet) {

				if (pfbo >= rd.getRangoinicial() && pfbo <= rd.getRangofinal()) {
					pcodigorecaudacion = rd.getCodigo();
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
							"el factor esta fuera de rango, no hay codigo de recaudacion", "ERROR"));
				}
			}

		} catch (Exception e) {
			throw e;
		}

		return pcodigorecaudacion;
	}

	/// ACTUALIZACION DE DATOS EN COMPROBANTE PROCESO DE ENV�O SRI CAMPO LAST SEND
	@Transactional
	public void actualizaLastSend(Integer id_comprobante) {
		// System.out.println("entra a actualiza last send Funciones" + id_comprobante);
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, 5);

		// System.out.println("calendar.getTime(): " + calendar.getTime());
		try {
			Query query = em.createNativeQuery("update financiero.comprobante set lastend=?1 where id=?2");
			query.setParameter(1, calendar.getTime());
			query.setParameter(2, id_comprobante);
			query.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/// ACTUALIZACION DE DATOS EN COMPROBANTE PROCESO DE ENV�O SRI CAMPO ESTADOSRI
	@Transactional
	public void actualizaEstadoSRI(String estado, Comprobante c) {
		try {
			Query query = em.createNativeQuery("update financiero.comprobante set estadosri=?1 where id=?2");
			query.setParameter(1, estado);
			query.setParameter(2, c.getId());
			query.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// ACTUALIZA INFORMACI�N DE SRI PARA FACTURAS PARA VOLVER APROCESAR
	@Transactional
	public void borraSRI(Integer id_comprobante) throws Exception {
		try {
			Query query = em.createNativeQuery(
					"update financiero.comprobante set autorizacion=null,autorizacionfecha=null,estadoerror='A',estadosri='A',xml=null,xmlsri=null where id=?1 ");
			query.setParameter(1, id_comprobante);
			query.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// ACTUALIZA DATOS PARA USUARIO PUNTO
	@Transactional
	public void actualizaUsuarioPunto(Integer id_usuariopunto, Date fechafin, String estado) throws Exception {
		try {

			if (fechafin != null) {
				Query query = em
						.createNativeQuery("update financiero.usuariopunto set fechafin=?1,estado=?2 where id=?3 ");
				query.setParameter(1, fechafin);
				query.setParameter(2, estado);
				query.setParameter(3, id_usuariopunto);
				query.executeUpdate();
			} else if (fechafin == null) {
				Query query = em.createNativeQuery("update financiero.usuariopunto set estado=?1 where id=?2 ");
				query.setParameter(1, estado);
				query.setParameter(2, id_usuariopunto);
				query.executeUpdate();
			}

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// ACTUALIZA ESTADO PARA OTROS QUE TENGA COMO ACTIVO PARA USUARIO PUNTO
	@Transactional
	public void actualizaEstadosUsuarioPunto(Integer id_usuariopunto, Integer id_usuario) throws Exception {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, 5);
		try {
			Query query = em.createNativeQuery(
					"update financiero.usuariopunto set estado='I',fechafin=?1 where id<>?2 and id_usuario=?3 and estado='A' and fechafin is null ");

			query.setParameter(1, calendar.getTime());
			query.setParameter(2, id_usuariopunto);
			query.setParameter(3, id_usuario);
			query.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// ACTUALIZA ESTADO PARA USUARIO LUEGO DE INTENTAR 3 VECES
	@Transactional
	public void actualizaEstadosUsuario(Integer id_usuario) throws Exception {
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MINUTE, 5);
		try {
			Query query = em.createNativeQuery("update financiero.usuario set estado='B' where id=?1");
			query.setParameter(1, id_usuario);
			query.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// ACTUALIZA ESTADO PARA USUARIO LUEGO DE INTENTAR 3 VECES
	@Transactional
	public void actualizaEstadosUsuarioUsername(String username) throws Exception {
		try {
			Query query = em.createNativeQuery("update financiero.usuario set estado='B' where username=?1");
			query.setParameter(1, username);
			query.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// ACTUALIZA ESTADO PARA OTROS QUE TENGA COMO ACTIVO PARA USUARIO PUNTO
	@Transactional
	public void insertaUsuarioSesiones(Date fecha, String username, String tipo, String mensaje) throws Exception {
		try {
			Query query = em.createNativeQuery(
					"insert into financiero.usuariosesiones(fecha,username,tipo,mensaje) values(?1,?2,?3,?4) ");
			query.setParameter(1, fecha);
			query.setParameter(2, username);
			query.setParameter(3, tipo);
			query.setParameter(4, mensaje);
			query.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// USUARIO BLOQUEADO
	@SuppressWarnings({ "unchecked", "unused" })
	public boolean usuarioBloqueado(String username) throws Exception {
		boolean respuesta = false;
		Usuario usuario = new Usuario();
		List<Usuario> lista = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Usuario u where u.username =?1 and u.estado='B'");
			query.setParameter(1, username);

			lista = (List<Usuario>) query.getResultList();

			if (lista != null && !lista.isEmpty()) {
				usuario = lista.get(0);
				respuesta = true;
			}

		} catch (Exception e) {
			throw e;
		}
		return respuesta;

	}

	public Date fechaMaxEstadoCuenta() {
		Date fecha;
		Query query = em.createQuery("SELECT max(fechacarga) FROM EstadoCuenta c");
		fecha = (Date) query.getSingleResult();
		return fecha;
	}

	@Transactional
	public void consolidaDepositos(Integer anio, Integer id_usuario) throws Exception {
		try {
			// System.out.println("entra a funciones7 " + anio + "- " + id_usuario);
			Query query = em.createNativeQuery("CALL financiero.consolidadepositos(:panio,:pusuario)");
			query.setParameter("panio", anio);
			query.setParameter("pusuario", id_usuario);
			query.executeUpdate();
			// System.out.println("sale7");
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public boolean letras(String cadena) {
		boolean tipoCadena = false;
		if (cadena != null) {
			for (int x = 0; x < cadena.length(); x++) {
				char c = cadena.charAt(x);
				// Si no está entre a y z, ni entre A y Z, ni es un espacio
				if (((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == ' ' || c == '.' || c == '&'
						|| (c >= 'Á' && c <= 'Ú') || c == ';')) {
					tipoCadena = false;

				} else {
					tipoCadena = true;
					break;
				}
			}
		}
		return tipoCadena;
	}

	public Number cuentaEstCuentaConsolidacion(Integer anio) throws Exception {/// para recorrer la consolidacion

		Number contador = null;
		// usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(p);
		try {
			Query query = em.createNativeQuery("select count(*)from(\r\n"
					+ "SELECT DISTINCT  a.punto_id, a.punto_nombre, a.comp_anio,a.comp_mes,a.cliente_nombre, c.cliente,a.cliente_ci,b.num_deposito num_deposito,c.numtransaccion,b.fecha,b.valor as comdep_valor,a.comp_estado,\r\n"
					+ "	c.fecha,c.valor as estcue_valor,c.servicio,	c.ruc,c.anio,c.mes,max(b.id) comdep_id,c.id estcue_id,c.saldo\r\n"
					+ "	FROM financiero.vista_recaudacion a,financiero.comprobantedeposito b,financiero.estadocuenta c\r\n"
					+ "	WHERE a.comp_id = b.id_comprobante 	and c.numtransaccion = b.num_deposito\r\n"
					+ "	and a.comp_estado = 'A' and a.comp_tipo = 'F'\r\n"
					+ "	and b.num_deposito=c.numtransaccion	and (c.saldo >= b.valor and c.saldo>0)\r\n"
					+ "	and b.identificacion =c.ruc and c.anio = a.comp_anio_i\r\n"
					+ "	and c.anio=?1 and c.bloqueado =false and b.id_tmp is null\r\n"
					+ "	group by a.punto_id, a.punto_nombre, a.comp_anio,a.comp_mes,a.cliente_nombre, c.cliente,a.cliente_ci,b.num_deposito,c.numtransaccion,b.fecha,b.valor,a.comp_estado,\r\n"
					+ "	c.fecha,c.valor,c.servicio,	c.ruc,c.anio,c.mes,c.id,c.saldo)a");

			query.setParameter(1, anio);
			contador = (Number) query.getSingleResult();
		} catch (Exception e) {
			System.out.println(e);
		}

		return contador;
	}
	
	//CONSULTA DE CLIENTES POR EL NOMBRE
	@SuppressWarnings("unchecked")
	public List<Cliente> ClientePorNombre(String nombre) {
		String nombreSep=nombre.replace(' ', '%');
		System.out.println("nombreSep Funciones " + nombreSep);
		List<Cliente> lstCliente = new ArrayList<>();
		try {
			Query query = em.createQuery("FROM Cliente c where c.nombre like ?1");
			query.setParameter(1,nombreSep);
			lstCliente = (List<Cliente>) query.getResultList();
			
			
		} catch (Exception e) {
			throw e;
		}
		return lstCliente;
	}

}
