package ec.gob.mdg.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.primefaces.model.UploadedFile;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.TimeZone;

import ec.gob.mdg.model.Cliente;
import ec.gob.mdg.model.Comprobante;
import ec.gob.mdg.model.ComprobanteDepositos;
import ec.gob.mdg.model.ComprobanteDetalle;
import ec.gob.mdg.model.Institucion;
import ec.gob.mdg.model.PuntoRecaudacion;
import ec.gob.mdg.model.RecaudacionDetalle;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.service.IClienteService;
import ec.gob.mdg.service.IComprobanteDepositosService;
import ec.gob.mdg.service.IComprobanteDetalleService;
import ec.gob.mdg.service.IComprobanteService;
import ec.gob.mdg.service.IInstitucionService;
import ec.gob.mdg.service.IPuntoRecaudacionService;
import ec.gob.mdg.service.IRecaudacionDetalleService;
import ec.gob.mdg.service.IUsuarioPuntoService;
import ec.gob.mdg.util.CedulaRuc;
import ec.gob.mdg.util.UtilsArchivos;
import ec.gob.mdg.util.UtilsDate;
import ec.gob.mdg.util.ValorMod11;
import ec.gob.mdg.validaciones.FunValidaciones;
import ec.gob.mdg.validaciones.Funciones;

@Named
@ViewScoped
public class ingresoFactura implements Serializable {

	private static final long serialVersionUID = -3453594861281598811L;

	@Inject
	private IInstitucionService institucionService;

	@Inject
	private IClienteService serviceCliente;

	@Inject
	private IComprobanteService serviceComprobante;

	@Inject
	private IComprobanteDetalleService serviceComprobanteDetalle;

	@Inject
	private IComprobanteDepositosService serviceComprobanteDepositos;

	@Inject
	private IRecaudacionDetalleService serviceRecaudacionDetalle;

	@Inject
	private Funciones fun;

	@Inject
	private GenerarDOMBean genXml;

//	@Inject
//	private GenerarDOMNotaBean genNotasXml;

	@Inject
	private IUsuarioPuntoService serviceUsuPunto;

	@Inject
	private IPuntoRecaudacionService servicePuntoRecaudacion;

	private Cliente cliente = new Cliente();
	private Comprobante comprobante = new Comprobante();
	private Comprobante comprobantetmp = new Comprobante();
	private Comprobante comprobantenot = new Comprobante();
	private ComprobanteDetalle comprobanteDetalle = new ComprobanteDetalle();
	private ComprobanteDetalle comprobanteDetalleNotas = new ComprobanteDetalle();
	private ComprobanteDepositos comprobanteDepositos = new ComprobanteDepositos();
	private RecaudacionDetalle recaudacionDetalle = new RecaudacionDetalle();
	private Institucion institucion;

	private List<Cliente> listaClientes = new ArrayList<Cliente>();
	private List<Comprobante> listaComprobante = new ArrayList<Comprobante>();
	private List<ComprobanteDetalle> listaComprobanteDet = new ArrayList<ComprobanteDetalle>();
	private List<ComprobanteDetalle> listaComprobanteDetNot = new ArrayList<ComprobanteDetalle>();
	private List<ComprobanteDepositos> listaComprobanteDep = new ArrayList<ComprobanteDepositos>();
	private List<RecaudacionDetalle> listaRecaudacionDetalle;
	private List<RecaudacionDetalle> listaRecaudacionDetalleTodas;
	private List<RecaudacionDetalle> listaRecaudacionDetalleArr;
	String el_cliente = "";

	private UsuarioPunto usuPunto = new UsuarioPunto();

	Usuario p = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");

	Integer num = 0;
	Integer num1 = 0;
	Integer cantidad = 0;
	Date fechaActual;
	PuntoRecaudacion punto;
	PuntoRecaudacion nombre;
	double totaldet = 0.0;
	double totaldep = 0.0;
	boolean validador = false;
	boolean validadorValor = false;
	boolean validadorValorIva = false;
	Integer id_tmp = 0;
	private String tipoDialog;
	double subtotaldet = 0.0;
	double valorcero = 0.0;
	double valoriva = 0.0;
	double valIva = 0.0;
	// boolean desahabilitabtncorreo=false;
	boolean estadeshabilitado = false;
	boolean estadeshabilitadoEnv = true;
	boolean estadeshabilitadoAut = true;

	Integer respuesta = 0;

	boolean validaFecha;

	public boolean isValidaFecha() {
		return validaFecha;
	}

	public void setValidaFecha(boolean validaFecha) {
		this.validaFecha = validaFecha;
	}

	String claveA;
	Integer inst;
	private UploadedFile file;
	double valorservicio = 0.0;
	Integer rd;
	Integer id_comprobante = 0;
	String tipo_proceso = null;
	String detalle = null;

	@PostConstruct
	public void init() {
		cliente = new Cliente();
		try {
			inicializar(); ///// INICIALIZA LAS VARIABLES
			el_cliente = "";
			listaClientes = serviceCliente.listar();
			listaRecaudacionDetalleTodas = serviceRecaudacionDetalle.listar();
			fechaActual = UtilsDate.fechaActual();
			usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(p);
			punto = usuPunto.getPuntoRecaudacion();
			inst = punto.getInstitucion().getId();
			institucion = institucionService.institucionPorPunto(inst);
			nombre = servicePuntoRecaudacion.listarPorId(punto);
			num = 0;

			this.tipoDialog = "Nuevo";
			validador = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//// INICIALIZADOR DE VARIABLES
	public void inicializar() {
		comprobanteDetalle = new ComprobanteDetalle();
		comprobanteDetalle.setRecaudaciondetalle(new RecaudacionDetalle());
		comprobanteDepositos = new ComprobanteDepositos();
	}

	public void buscarClientes() {

		try {
			if (cliente == null) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese el No. de Identificación", "Sin Datos"));
			} else {

				Long a = fun.buscarCliente(cliente);

				if (a == 0) {

					if (this.cliente.getCi() != null && this.cliente.getTipoid() != null) {
						String validaIdentificacion = CedulaRuc.comprobacion(this.cliente.getCi(),
								this.cliente.getTipoid());
						if (!validaIdentificacion.equals("T")) {
							FacesContext.getCurrentInstance().addMessage(null,
									new FacesMessage(FacesMessage.SEVERITY_ERROR,
											"Tipo de identificacion erronea buscar", validaIdentificacion));
						}
					}

				} else {
					this.cliente = serviceCliente.ClientePorCiParametro(cliente.getCi());

				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public Integer validaCamposCliente() {
		respuesta = 0;
		// System.out.println("ci" + cliente.getCi());
		if (cliente.getCi() == null) {
			respuesta = 1;
			// System.out.println("ci nulla " + respuesta);
		} else if (cliente.getTipoid() == null) {
			respuesta = 1;
			// System.out.println("cliente.getTipoid() " + respuesta);
		} else if (cliente.getNombre() == null) {
			respuesta = 1;
			// System.out.println("nombre: " + respuesta);
		} else if (cliente.getDireccion() == null) {
			respuesta = 1;
			// System.out.println("tipo: " + respuesta);
		} else if (cliente.getCorreo() == null) {
			respuesta = 1;
			// System.out.println("coore " + respuesta);
		}
		return respuesta;
	}

	// VALIDA LA FECHA QUE NO SEA MAYOR A LA ACTUAL
	public void validaFechaActual(Date fecha) {
		if (fecha != null) {
			Date fechaactual = new Date(System.currentTimeMillis());
			validaFecha = false;
			if (fecha.after(fechaactual)) {
				validaFecha = true;
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Fecha es mayor a la fecha de hoy"));
			}
		}

	}

	// VALIDADOR DE CEDULA-RUC
	public void validaIdentificacion() {

		if (cliente == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese el No. de Identificación", "Sin Datos"));
		} else {
			String validaIdentificacion = CedulaRuc.comprobacion(this.cliente.getCi(), this.cliente.getTipoid());
			if (validaIdentificacion.equals("T")) {
				validador = false;
			} else {
				validador = true;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Tipo de identificacion erronea", validaIdentificacion));
			}
		}
	}

	// VALIDADOR DE CAMPO VALOR CERO E IVA
	public void validaCantidad() {
		if (comprobanteDetalle.getRecaudaciondetalle().getValor() != 0) {
			validadorValorIva = true;
			validadorValor = true;
		} else if (comprobanteDetalle.getRecaudaciondetalle().getValor() == 0
				&& comprobanteDetalle.getRecaudaciondetalle().getValoriva() == 0) {
			validadorValor = false;
			validadorValorIva = false;
		}
	}

	public void validaValor() {
		if (comprobanteDetalle.getRecaudaciondetalle().getValor() != 0) {
			validadorValorIva = true;
		}
	}

	public void validaValorIva() {
		if (comprobanteDetalle.getRecaudaciondetalle().getValoriva() != 0) {
			validadorValor = true;
		}
	}

	// METODO PARA LISTAR LA RECAUDACION POR PROCESO
	public void listarRecaudacionDetallePorProceso(String tipo_proceso) {

		this.listaRecaudacionDetalle = this.serviceRecaudacionDetalle.listarRecaudacionDetallePorProceso(tipo_proceso);
	}

	// METODO PARA LISTAR LA RECAUDACION POR PROCESO ARRIENDOS
	public void listarRecaudacionDetallePorProcesoArriendos(String tipo_proceso) {

		this.listaRecaudacionDetalleArr = this.serviceRecaudacionDetalle
				.listarRecaudacionDetallePorProcesoArriendos(tipo_proceso);
	}

	// METODO PARA LISTAR LA RECAUDACION POR PROCESO OTROS SERVICIOS
	public void listarRecaudacionDetallePorProcesoOtrosServicios(String tipo_proceso, String estado) {
		this.listaRecaudacionDetalleArr = this.serviceRecaudacionDetalle
				.listarRecaudacionDetallePorProcesoOtrosServicios(tipo_proceso, estado);
	}

	// REGISTRAR LA LISTA DE DETALLE TMP
	public void registrarCompDetalle() {
		ComprobanteDetalle det = new ComprobanteDetalle();
		comprobante.setValor(totaldet);
		id_tmp++;
		det = comprobanteDetalle;
		det.setValorcero(comprobanteDetalle.getRecaudaciondetalle().getValor());
		det.setValoriva(comprobanteDetalle.getRecaudaciondetalle().getValoriva());
		det.setCantidad(comprobanteDetalle.getCantidad());

		// det.setSubtotal(subtotaldet);
		det.setSubtotal(calculaSubtotalDetalle(comprobanteDetalle.getCantidad(), comprobanteDetalle.getValorcero(),
				comprobanteDetalle.getValoriva()));
		det.setId_tmp(id_tmp);
		det.setComprobante(comprobante);
		det.setRecaudaciondetalle(comprobanteDetalle.getRecaudaciondetalle());
		listaComprobanteDet.add(det);
		totalDetalle();

		this.tipoDialog = "Agregar Detalle";
		// System.out.println("termina el registro de la tabla");
	}

	/// LLENAR CAMPO DETALLE
	public void llenaDetalle() {

		for (ComprobanteDetalle det : listaComprobanteDet) {
			detalle = detalle + det.getRecaudaciondetalle().getNombre() + "-";
		}
	}

	// CALCULA SUBTOTAL DETALLE
	public double calculaSubtotalDetalle(Integer cantidad, Double valor, Double valorIva) {
		subtotaldet = 0;
		if (cantidad != null && valor != null && valorIva != null) {

			if (valorIva == 0) {
				subtotaldet = (cantidad * valor);
				subtotaldet = FunValidaciones.formatearDecimales(subtotaldet, 2);
			} else {
				subtotaldet = cantidad * (valorIva * fun.valorIva() / 100);
				valIva = (valorIva * fun.valorIva() / 100);
				subtotaldet = FunValidaciones.formatearDecimales(subtotaldet, 2) + valorIva;
			}
		}

		return subtotaldet;
	}

	// SACAR EL VALOR SEGUN EL SERVICIO///////////////////////////////////////
	public void valorServicio(Integer idRecaudacionDetalle) {

		valorservicio = fun.valorRecaudacionDetalle(idRecaudacionDetalle);
	}

	// REGISTRAR LA LISTA DE DEPOSITO TMP
	public void registrarCompDeposito() {
		llenaDetalle();
		validaFechaActual(comprobanteDepositos.getFecha());

		ComprobanteDepositos dep = new ComprobanteDepositos();
		dep.setOrigen(comprobanteDepositos.getOrigen());
		dep.setTipotransaccion(comprobanteDepositos.getTipotransaccion());
		dep.setIdentificacion(comprobanteDepositos.getIdentificacion());
		dep.setComprobante(comprobante);
		dep.setNum_deposito(comprobanteDepositos.getNum_deposito());
		dep.setFecha(comprobanteDepositos.getFecha());
		dep.setValor(comprobanteDepositos.getValor());
		listaComprobanteDep.add(dep);
		totalDeposito();
	}

	// SUMAR TOTAL DETALLE
	public void totalDetalle() {
		totaldet = 0.00;
		for (ComprobanteDetalle det : listaComprobanteDet) {
			totaldet = totaldet + det.getSubtotal();
			totaldet = FunValidaciones.formatearDecimales(totaldet, 2);
		}
	}

	// SUMAR TOTAL DEPOSITOS
	public void totalDeposito() {
		totaldep = 0.00;
		for (ComprobanteDepositos dep : listaComprobanteDep) {
			totaldep = totaldep + dep.getValor();
			totaldep = FunValidaciones.formatearDecimales(totaldep, 2);
		}
	}

	// ENCERAR DETALLES
	public void limpiarDialogo() {
		/// validaCamposCliente();
		comprobanteDetalle = new ComprobanteDetalle();
	}

	public void limpiarDialogoDep() {
		comprobanteDepositos = new ComprobanteDepositos();
	}

	// ELIMINAR DETALLE
	public void eliminarCompDetalle(int index) {
		listaComprobanteDet.remove(index);
		totalDetalle();
	}

	// ELIMINAR DEPOSITO
	public void eliminarCompDeposito(int index) {
		listaComprobanteDep.remove(index);
		totalDeposito();
	}

	// MOSTRAR FACTURA
	public void mostrarFactura() {
		try {
			usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(p);
			num = (Integer) fun.muestraFactura(usuPunto);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// MOSTRAR NOTA CREDITO
	public void mostrarNota() {

		try {
			usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(p);
			num = (Integer) fun.muestraNotas(usuPunto);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// MOSTRAR DATOS DE DETALLE
	public void mostrarData(ComprobanteDetalle i) {
		this.comprobanteDetalle = i;
		this.tipoDialog = "Modificar Detalle";
	}

	// MOSTRAR DATOS DE DEPOSITO
	public void mostrarDataDep(ComprobanteDepositos i) {
		this.comprobanteDepositos = i;
		this.tipoDialog = "Modificar Deposito";
	}

	public void limpiarControlesFactura() {
		this.comprobante = new Comprobante();
		this.tipoDialog = "Nuevo Factura";
	}

	public void limpiarControles() {
		this.comprobanteDetalle = new ComprobanteDetalle();
		this.tipoDialog = "Nuevo Detalle";
	}

	public void limpiarControlesDep() {
		this.comprobanteDepositos = new ComprobanteDepositos();
		this.tipoDialog = "Nuevo Deposito";
	}

	// NUEVO REGISTR0
	public Boolean nuevo() {
		limpiarControlesFactura();
		limpiarControles();
		limpiarControlesDep();
		estadeshabilitado = false;
		return estadeshabilitado;
	}

	@Transactional
	public void registrarComprobante() {
		try {
			if (subtotaldet == 0) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error, No existen datos", "Error"));
			} else {

				respuesta = 0;
				validaCamposCliente();

				if (respuesta == 1) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error, no existen datos de cliente", "Error"));
				} else {
					Integer contador = 0;
					contador = (Integer) serviceComprobante.validaCierre(usuPunto);
					if (contador > 0) {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error, debe realizar el cierre del día anterior, no podrá facturar", "Error"));
					} else if (validaFecha == true) {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error, debe cambiar la fecha del depósito", "Error"));
					} else {
						totalDetalle();
						totalDeposito();
						if (totaldet != totaldep) {
							FacesContext.getCurrentInstance().addMessage(null,
									new FacesMessage(FacesMessage.SEVERITY_ERROR,
											"Error los valores de los detalles es diferente", "Error"));
						} else {
							// para validar la cedula
							Long a = fun.buscarCliente(cliente);

							if (a == 0) {
								Cliente c = new Cliente();
								c = cliente;
								String clave = this.cliente.getCi();
								String claveHash = BCrypt.hashpw(clave, BCrypt.gensalt());
								c.setClave(claveHash);
								cliente.setCi(cliente.getCi());
								cliente.setDireccion(cliente.getDireccion());
								cliente.setNombre(cliente.getNombre());
								cliente.setCorreo(cliente.getCorreo());

								serviceCliente.registrar(c);
							} else {
								cliente.setCi(cliente.getCi());
								cliente.setDireccion(cliente.getDireccion());
								cliente.setNombre(cliente.getNombre());
								cliente.setCorreo(cliente.getCorreo());
								serviceCliente.modificar(cliente);
							}
							usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(p);
							// saca el secuencial +1
							num1 = (Integer) fun.secFactura(usuPunto);
							Comprobante comp = new Comprobante();
							comp.setNumero(num1);
							comp.setClienteruc(cliente.getCi());
							comp.setClientenombre(cliente.getNombre());
							comp.setClientedireccion(cliente.getDireccion());
							comp.setClientetelefono(cliente.getTelefono());
							fechaActual = UtilsDate.fechaActual();
							comp.setFechaemision(fechaActual);
							comp.setDetalle(detalle);
							comp.setCliente(cliente);
							comp.setUsuarioPunto(usuPunto);
							comp.setTipocomprobante("F");
							comp.setValor(totaldet);
							comp.setPuntoRecaudacion(punto);
							//// GENERAR CLAVE ACCESO
							Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
							calendar.setTime(fechaActual);
							String anio = String.valueOf(calendar.get(Calendar.YEAR));
							String mes = String.valueOf(calendar.get(Calendar.MONTH) + 1);
							String dia = String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));
							if (calendar.get(Calendar.MONTH) < 10) {
								mes = "0" + mes;
							}
							if (calendar.get(Calendar.DAY_OF_MONTH) < 10) {
								dia = "0" + dia;
							}
							claveA = dia + mes + anio + "01" + institucion.getRuc() + institucion.getAmbiente()
									+ punto.getEstablecimiento() + punto.getPuntoemision()
									+ StringUtils.leftPad(String.valueOf(num1), 9, "0") + "12345678" + "1";
							String verificador = String.valueOf(ValorMod11.mod11(claveA));
							claveA = claveA + verificador;
							comp.setClaveacceso(claveA);
							////////////////////////////////////////////
							id_comprobante = serviceComprobante.registrar(comp);
							// actualiza la secuencia
							fun.actualizaSecuencialFactura(usuPunto, num1);
							for (ComprobanteDetalle det : listaComprobanteDet) {
								ComprobanteDetalle det1 = new ComprobanteDetalle();
								det1.setValorcero(det.getRecaudaciondetalle().getValor());
								det1.setValoriva(det.getRecaudaciondetalle().getValoriva());
								det1.setCantidad(det.getCantidad());
								det1.setSubtotal(det.getSubtotal());
								det1.setComprobante(comp);
								det1.setRecaudaciondetalle(det.getRecaudaciondetalle());
								serviceComprobanteDetalle.registrar(det1);
							}
							for (ComprobanteDepositos dep : listaComprobanteDep) {
								dep.setComprobante(comp);
								serviceComprobanteDepositos.registrar(dep);
							}
							comprobante = serviceComprobante.listarComprobantePorId(id_comprobante);
							/// GENERAR XML PARA LA FACTURA
							genXml.generarXmlArchivo(punto.getId(), num1);
							mostrarFactura();

							estadeshabilitado = true;
							estadeshabilitadoEnv = false;
							validador = true;
							FacesContext.getCurrentInstance().addMessage(null,
									new FacesMessage(FacesMessage.SEVERITY_INFO, "Se grabó con éxito", "Aviso"));
						} // cierre de if
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// BUSCAR FACTURA PARA DESPLEGAR INFORMACIoN EN LA NOTA DE CRéDITO
	public void buscarFactura(Integer id_punto, Integer numero) {
		try {
			this.comprobantetmp = serviceComprobante.comprobantePorPtoPorId(id_punto, numero);

			// LISTA DETALLE DE NOTA DE CREDITO A PARTIR DE LA INF DE LA FACTURA
			// SELECCIONADA
			this.listaComprobanteDetNot = serviceComprobanteDetalle.listarComDetPorPtoNumComp(id_punto, numero);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

//////////////////ENVIAR CORREO

	public void eviarCorreo(Integer id_comprobante, String tipo) {

		institucion = institucionService.institucionActiva();

		if (tipo.equals("F")) {
			comprobante = serviceComprobante.listarComprobantePorId(id_comprobante);
		} else {
			comprobante = serviceComprobante.listarComprobanteNotaPorId(id_comprobante);
		}

		Properties props = System.getProperties();
		props.put("mail.smtp.host", institucion.getServidorcorreo()); // El servidor SMTP de Google
		props.put("mail.smtp.user", institucion.getUsuariocorreo());
		props.put("mail.smtp.clave", institucion.getClavecorreo()); // La clave de la cuenta
		props.put("mail.smtp.auth", institucion.getAuth()); // Usar autenticacin mediante usuario y clave
		props.put("mail.smtp.starttls.enable", institucion.getStarttls()); // Para conectar de manera segura al servidor
																			// SMTP
		props.put("mail.smtp.port", institucion.getPuerto()); // El puerto SMTP seguro de Google
		props.put("mail.smtp.ssl.trust", "*");

		@SuppressWarnings("resource")
		Formatter obj = new Formatter();
		String asuntoMensaje = "Comprobante Electrónico - Ministerio de Gobierno - "
				+ comprobante.getPuntoRecaudacion().getEstablecimiento() + "- "
				+ comprobante.getPuntoRecaudacion().getPuntoemision() + "- "
				+ String.valueOf(obj.format("%09d", comprobante.getNumero()));

		String cuerpoMensaje = "<html><head><title></title></head><body>" + "Estimado (a) "
				+ comprobante.getClientenombre();

		cuerpoMensaje += "<br><br>Le informamos que tiene un comprobante electr&oacute;nico No.  "
				+ comprobante.getPuntoRecaudacion().getEstablecimiento() + "- "
				+ comprobante.getPuntoRecaudacion().getPuntoemision() + "- "
				+ String.valueOf(obj.format("%09d", comprobante.getNumero())) + ", con fecha: "
				+ comprobante.getFechaemision()
				+ ", se encuentra disponible para su visualizaci&oacute;n y descarga<br><br>"
				+ "<br><br>Atentamente,<br>" + institucion.getNombre() + "<br><br>" + "</body></html>";

		Session session = Session.getInstance(props, null);
//		session.setDebug(true);

		try {
			MimeBodyPart textoMensaje = new MimeBodyPart();
			textoMensaje.setContent(cuerpoMensaje, "text/html");

			BodyPart adjunto = new MimeBodyPart();

			String pathFirmados = UtilsArchivos.getRutaFirmados();

			adjunto.setDataHandler(
					new DataHandler(new FileDataSource(pathFirmados + comprobante.getClaveacceso() + ".pdf")));
			adjunto.setFileName(comprobante.getClaveacceso() + ".pdf");

			//// AGREGAR UNA CONDICION PARA CUANDO NO HAY EL ADJUNTO -----PEENDIENTE

			MimeMultipart multiParte = new MimeMultipart();
			multiParte.addBodyPart(textoMensaje);
			multiParte.addBodyPart(adjunto);

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(institucion.getUsuariocorreo(), institucion.getNombre()));
			message.addRecipients(Message.RecipientType.TO, comprobante.getCliente().getCorreo());
			message.setSubject(asuntoMensaje);
			message.setContent(multiParte);
			Transport transport = session.getTransport("smtp");
			transport.connect(institucion.getServidorcorreo(), institucion.getUsuariocorreo(),
					institucion.getClavecorreo());
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Se envió con éxito", "Aviso"));
			// return true;
		} catch (Exception e) {
			System.out.println("Error " + e);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR: No se Envió por correo", "ERROR"));
			// return false;
		}
	}

////////////////////////////////////////////////

//	Getters y Setters
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Comprobante getComprobante() {
		return comprobante;
	}

	public void setComprobante(Comprobante comprobante) {
		this.comprobante = comprobante;
	}

	public ComprobanteDetalle getComprobanteDetalle() {
		return comprobanteDetalle;
	}

	public void setComprobanteDetalle(ComprobanteDetalle comprobanteDetalle) {
		this.comprobanteDetalle = comprobanteDetalle;
	}

	public ComprobanteDepositos getComprobanteDepositos() {
		return comprobanteDepositos;
	}

	public void setComprobanteDepositos(ComprobanteDepositos comprobanteDepositos) {
		this.comprobanteDepositos = comprobanteDepositos;
	}

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public List<Comprobante> getListaComprobante() {
		return listaComprobante;
	}

	public void setListaComprobante(List<Comprobante> listaComprobante) {
		this.listaComprobante = listaComprobante;
	}

	public List<ComprobanteDetalle> getListaComprobanteDet() {
		return listaComprobanteDet;
	}

	public void setListaComprobanteDet(List<ComprobanteDetalle> listaComprobanteDet) {
		this.listaComprobanteDet = listaComprobanteDet;
	}

	public List<ComprobanteDepositos> getListaComprobanteDep() {
		return listaComprobanteDep;
	}

	public void setListaComprobanteDep(List<ComprobanteDepositos> listaComprobanteDep) {
		this.listaComprobanteDep = listaComprobanteDep;
	}

	public RecaudacionDetalle getRecaudacionDetalle() {
		return recaudacionDetalle;
	}

	public void setRecaudacionDetalle(RecaudacionDetalle recaudacionDetalle) {
		this.recaudacionDetalle = recaudacionDetalle;
	}

	public List<RecaudacionDetalle> getListaRecaudacionDetalle() {
		return listaRecaudacionDetalle;
	}

	public void setListaRecaudacionDetalle(List<RecaudacionDetalle> listaRecaudacionDetalle) {
		this.listaRecaudacionDetalle = listaRecaudacionDetalle;
	}

	public String getEl_cliente() {
		return el_cliente;
	}

	public void setEl_cliente(String el_cliente) {
		this.el_cliente = el_cliente;
	}

	public UsuarioPunto getUsuPunto() {
		return usuPunto;
	}

	public void setUsuPunto(UsuarioPunto usuPunto) {
		this.usuPunto = usuPunto;
	}

	public Usuario getP() {
		return p;
	}

	public void setP(Usuario p) {
		this.p = p;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public double getTotaldet() {
		return totaldet;
	}

	public void setTotaldet(double totaldet) {
		this.totaldet = totaldet;
	}

	public boolean isValidador() {
		return validador;
	}

	public void setValidador(boolean validador) {
		this.validador = validador;
	}

	public double getTotaldep() {
		return totaldep;
	}

	public void setTotaldep(double totaldep) {
		this.totaldep = totaldep;
	}

	public PuntoRecaudacion getPunto() {
		return punto;
	}

	public void setPunto(PuntoRecaudacion punto) {
		this.punto = punto;
	}

	public PuntoRecaudacion getNombre() {
		return nombre;
	}

	public void setNombre(PuntoRecaudacion nombre) {
		this.nombre = nombre;
	}

	public Integer getId_tmp() {
		return id_tmp;
	}

	public void setId_tmp(Integer id_tmp) {
		this.id_tmp = id_tmp;
	}

	public String getTipoDialog() {
		return tipoDialog;
	}

	public void setTipoDialog(String tipoDialog) {
		this.tipoDialog = tipoDialog;
	}

	public double getSubtotaldet() {
		return subtotaldet;
	}

	public void setSubtotaldet(double subtotaldet) {
		this.subtotaldet = subtotaldet;
	}

	public boolean isEstadeshabilitado() {
		return estadeshabilitado;
	}

	public void setEstadeshabilitado(boolean estadeshabilitado) {
		this.estadeshabilitado = estadeshabilitado;
	}

	public String getClaveA() {
		return claveA;
	}

	public void setClaveA(String claveA) {
		this.claveA = claveA;
	}

	public Integer getInst() {
		return inst;
	}

	public void setInst(Integer inst) {
		this.inst = inst;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public Integer getNum1() {
		return num1;
	}

	public void setNum1(Integer num1) {
		this.num1 = num1;
	}

	public double getValorservicio() {
		return valorservicio;
	}

	public void setValorservicio(double valorservicio) {
		this.valorservicio = valorservicio;
	}

	public Integer getRd() {
		return rd;
	}

	public void setRd(Integer rd) {
		this.rd = rd;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public List<ComprobanteDetalle> getListaComprobanteDetNot() {
		return listaComprobanteDetNot;
	}

	public void setListaComprobanteDetNot(List<ComprobanteDetalle> listaComprobanteDetNot) {
		this.listaComprobanteDetNot = listaComprobanteDetNot;
	}

	public Comprobante getComprobantetmp() {
		return comprobantetmp;
	}

	public void setComprobantetmp(Comprobante comprobantetmp) {
		this.comprobantetmp = comprobantetmp;
	}

	public ComprobanteDetalle getComprobanteDetalleNotas() {
		return comprobanteDetalleNotas;
	}

	public void setComprobanteDetalleNotas(ComprobanteDetalle comprobanteDetalleNotas) {
		this.comprobanteDetalleNotas = comprobanteDetalleNotas;
	}

	public Comprobante getComprobantenot() {
		return comprobantenot;
	}

	public void setComprobantenot(Comprobante comprobantenot) {
		this.comprobantenot = comprobantenot;
	}

	public String getTipo_proceso() {
		return tipo_proceso;
	}

	public void setTipo_proceso(String tipo_proceso) {
		this.tipo_proceso = tipo_proceso;
	}

	public List<RecaudacionDetalle> getListaRecaudacionDetalleArr() {
		return listaRecaudacionDetalleArr;
	}

	public void setListaRecaudacionDetalleArr(List<RecaudacionDetalle> listaRecaudacionDetalleArr) {
		this.listaRecaudacionDetalleArr = listaRecaudacionDetalleArr;
	}

	public Integer getId_comprobante() {
		return id_comprobante;
	}

	public void setId_comprobante(Integer id_comprobante) {
		this.id_comprobante = id_comprobante;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public double getValorcero() {
		return valorcero;
	}

	public void setValorcero(double valorcero) {
		this.valorcero = valorcero;
	}

	public double getValoriva() {
		return valoriva;
	}

	public void setValoriva(double valoriva) {
		this.valoriva = valoriva;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public boolean isValidadorValor() {
		return validadorValor;
	}

	public void setValidadorValor(boolean validadorValor) {
		this.validadorValor = validadorValor;
	}

	public boolean isValidadorValorIva() {
		return validadorValorIva;
	}

	public void setValidadorValorIva(boolean validadorValorIva) {
		this.validadorValorIva = validadorValorIva;
	}

	public List<RecaudacionDetalle> getListaRecaudacionDetalleTodas() {
		return listaRecaudacionDetalleTodas;
	}

	public void setListaRecaudacionDetalleTodas(List<RecaudacionDetalle> listaRecaudacionDetalleTodas) {
		this.listaRecaudacionDetalleTodas = listaRecaudacionDetalleTodas;
	}

	public boolean isEstadeshabilitadoEnv() {
		return estadeshabilitadoEnv;
	}

	public void setEstadeshabilitadoEnv(boolean estadeshabilitadoEnv) {
		this.estadeshabilitadoEnv = estadeshabilitadoEnv;
	}

	public boolean isEstadeshabilitadoAut() {
		return estadeshabilitadoAut;
	}

	public void setEstadeshabilitadoAct(boolean estadeshabilitadoAut) {
		this.estadeshabilitadoAut = estadeshabilitadoAut;
	}

	public Integer getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(Integer respuesta) {
		this.respuesta = respuesta;
	}

	public void setEstadeshabilitadoAut(boolean estadeshabilitadoAut) {
		this.estadeshabilitadoAut = estadeshabilitadoAut;
	}

	public double getValIva() {
		return valIva;
	}

	public void setValIva(double valIva) {
		this.valIva = valIva;
	}

}
