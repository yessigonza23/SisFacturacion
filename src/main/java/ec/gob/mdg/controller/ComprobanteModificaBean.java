package ec.gob.mdg.controller;

import static ec.gob.mdg.sri.util.StringUtil.converBase64;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.text.SimpleDateFormat;
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

import org.mindrot.jbcrypt.BCrypt;
import org.primefaces.model.UploadedFile;
import org.w3c.dom.Document;

import ec.gob.mdg.model.Cliente;
import ec.gob.mdg.model.Comprobante;
import ec.gob.mdg.model.ComprobanteDepositos;
import ec.gob.mdg.model.ComprobanteDetalle;
import ec.gob.mdg.model.Institucion;
import ec.gob.mdg.model.Mensaje;
import ec.gob.mdg.model.PuntoRecaudacion;
import ec.gob.mdg.model.RecaudacionDetalle;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.service.IAuditoriaService;
import ec.gob.mdg.service.IClienteService;
import ec.gob.mdg.service.IComprobanteDepositosService;
import ec.gob.mdg.service.IComprobanteDetalleService;
import ec.gob.mdg.service.IComprobanteService;
import ec.gob.mdg.service.IInstitucionService;
import ec.gob.mdg.service.IMensajeService;
import ec.gob.mdg.service.IPuntoRecaudacionService;
import ec.gob.mdg.service.IRecaudacionDetalleService;
import ec.gob.mdg.service.IUsuarioPuntoService;
import ec.gob.mdg.sri.SoapAutorizacion;
import ec.gob.mdg.sri.SoapRecepcion;
import ec.gob.mdg.sri.sign.XadesSign;
import ec.gob.mdg.sri.util.FileUtil;
import ec.gob.mdg.sri.util.XML_Utilidades;
import ec.gob.mdg.util.CedulaRuc;
import ec.gob.mdg.util.UtilsArchivos;
import ec.gob.mdg.util.UtilsDate;
import ec.gob.mdg.validaciones.FunValidaciones;
import ec.gob.mdg.validaciones.Funciones;

@Named
@ViewScoped
public class ComprobanteModificaBean implements Serializable {

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

	@Inject
	private IUsuarioPuntoService serviceUsuPunto;

	@Inject
	private IPuntoRecaudacionService servicePuntoRecaudacion;

	@Inject
	private IAuditoriaService serviceAuditoria;

	@Inject
	private IMensajeService serviceMensaje;

	private Cliente cliente;
	private Cliente clienteNotas;
	private Comprobante comprobante;
	private Comprobante comprobanteAud;
	private Comprobante comprobanteNot;
	private ComprobanteDetalle comprobanteDetalle = new ComprobanteDetalle();
	private ComprobanteDetalle comprobanteDetalleNotas = new ComprobanteDetalle();
	private ComprobanteDepositos comprobanteDepositos = new ComprobanteDepositos();
	private RecaudacionDetalle recaudacionDetalle;
	private Institucion institucion;

	private List<Cliente> listaClientes = new ArrayList<Cliente>();
	private List<Comprobante> listaComprobante = new ArrayList<Comprobante>();
	private List<ComprobanteDetalle> listaComprobanteDet = new ArrayList<ComprobanteDetalle>();
	private List<ComprobanteDetalle> listaComprobanteDetNot = new ArrayList<ComprobanteDetalle>();
	private List<ComprobanteDepositos> listaComprobanteDep = new ArrayList<ComprobanteDepositos>();
	private List<RecaudacionDetalle> listaRecaudacionDetalle;
	private List<RecaudacionDetalle> listaRecaudacionDetalleTodas;
	private List<ComprobanteDetalle> listaComprobanteDetTmp = new ArrayList<ComprobanteDetalle>();
	private List<ComprobanteDepositos> listaComprobanteDepTmp = new ArrayList<ComprobanteDepositos>();

	private Mensaje mensaje = new Mensaje();
	String el_cliente = "";

	private UsuarioPunto usuPunto = new UsuarioPunto();

	Usuario p = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");

	Integer num = 0;
	Integer num1 = 0;
	Date fechaActual;
	PuntoRecaudacion punto;
	PuntoRecaudacion nombre;
	double totaldet = 0.0;
	double totaldep = 0.0;
	boolean validador;
	boolean validadorValor = false;
	boolean validadorValorIva = false;
	Integer id_tmp = 0;
	private String tipoDialog;
	double subtotaldet = 0.0;
	boolean estadeshabilitado;
	boolean estadeshabilitadoA;
	boolean estadeshabilitadoEnv;
	boolean estadeshabilitadoAut;
	boolean validaestado = false;
	String claveA;
	Integer inst;
	private UploadedFile file;
	double valorservicio = 0.0;
	Integer rd;
	Integer id_comprobante = 0;
	String tipo_proceso = null;
	Integer id = 0;
	String param2;
	Integer id_compDetalle;
	Integer id_compDeposito;

	String MensajeSri;
	String MensajeSriError;
	/// variables para auditoria
	String autorizacion;
	String estadoAnterior;
	double valorAnterior;

	String ambiente;
	String url;
	String host;

	@PostConstruct
	public void init() {

		try {
			fechaActual = UtilsDate.fechaActual();
			listaRecaudacionDetalleTodas = serviceRecaudacionDetalle.listar();
			usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(p);
			punto = usuPunto.getPuntoRecaudacion();
			inst = punto.getInstitucion().getId();
			institucion = institucionService.institucionPorPunto(inst);
			nombre = servicePuntoRecaudacion.listarPorId(punto);
			num = 0;
			mostrarComprobante();
			mostrarComprobanteDetalle();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getParam() {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
	}

	// Listar informacin de la cabecera del comprobante: Facturas
	public void mostrarComprobante() {
		param2 = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
		id = Integer.parseInt(param2);

		try {
			// LLENAR CON LOS DATOS DE BD
			this.comprobante = serviceComprobante.listarComprobantePorId(id);
			estadoAnterior = comprobante.getEstado();

			valorAnterior = comprobante.getValor();

			this.cliente = this.serviceCliente.ClientePorCiParametro(comprobante.getClienteruc());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mostrarComprobanteDetalle() {
		param2 = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
		id = Integer.parseInt(param2);

		try {
			this.comprobante = serviceComprobante.listarComprobantePorId(id);

			this.listaComprobanteDet = this.serviceComprobanteDetalle.listarComDetPorIdComp(id);
			this.listaComprobanteDep = this.serviceComprobanteDepositos.listarComDepPorIdComp(id);

			this.listaComprobanteDetTmp = this.serviceComprobanteDetalle.listarComDetPorIdComp(id);
			this.listaComprobanteDepTmp = this.serviceComprobanteDepositos.listarComDepPorIdComp(id);

			this.cliente = this.serviceCliente.ClientePorCiParametro(comprobante.getClienteruc());

			this.listaRecaudacionDetalle = this.serviceRecaudacionDetalle.listar();

			totalDetalle();
			totalDeposito();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mostrarAutorizacion() {
		this.comprobante = serviceComprobante.listarComprobantePorId(id);
		System.out.println("comprobante autorizacion " + comprobante.getAutorizacion());
	}

	// VALIDA AUTORIZACIN
	public void validadAutorizacion() {

		if (comprobante.getAutorizacion() != null) {
			estadeshabilitado = true;
			estadeshabilitadoA = true;
//			String factura = comprobante.getAutorizacion().substring(31, 9);

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"No puede realizar cambios la factura ya esta autorizada por el SRI", "Error"));
		} else {
			estadeshabilitado = false;
			estadeshabilitadoA = false;
		}
	}

	// VALIDA QUE LA FACTURA NO SEA DIFERENTE DE LA AUTORIZACIN
	public void validaFacturaAutorizacion() {

		if (comprobante.getAutorizacion() != null) {
			String facturaS = comprobante.getAutorizacion().substring(30, 39);

			Integer factura = Integer.parseInt(facturaS);

			if (comprobante.getNumero().equals(factura)) {
				estadeshabilitado = true;
				estadeshabilitadoA = true;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"No puede borrar la Información del SRI, el comprobante se encuentra autorizado", "Error"));
			} else if (!comprobante.getNumero().equals(factura)) {
				try {
					fun.borraSRI(comprobante.getId());
					// CAMPO comprobante.getAutorizacion)
					serviceAuditoria.insertaModificacion("Comprobante", "autorizacion", "D",
							usuPunto.getUsuario().getUsername(), fechaActual, autorizacion,
							comprobante.getAutorizacion(), comprobante.getId());

					estadeshabilitado = false;
					estadeshabilitadoA = false;
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, "Se borró con éxito", "Aviso"));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/// MOSTRAR DETALLE
	public void mostrarDetalle(ComprobanteDetalle i) {
		this.comprobanteDetalle = i;
		calculaSubtotalDetalle(comprobanteDetalle.getCantidad(), comprobanteDetalle.getValorcero(),
				comprobanteDetalle.getValoriva());
		this.tipoDialog = "Modificar";
	}

	/// MOSTRAR DEPOSITOS
	public void mostrarDeposito(ComprobanteDepositos i) {
		this.comprobanteDepositos = i;
		this.tipoDialog = "Modificar";
	}

	/// ENCERAR DETALLE
	public void limpiarControles() {
		this.comprobanteDetalle = new ComprobanteDetalle();
		subtotaldet = 0;
		this.tipoDialog = "Nuevo";
	}

	// ENCERAR DEPOSITO
	public void limpiarControlesDep() {
		this.comprobanteDepositos = new ComprobanteDepositos();
		this.tipoDialog = "Nuevo";
	}

	// REGISTRAR DETALLE TEMPORAL
	public void registrarDetalle() throws Exception {

		if (comprobante.getAutorizacion() == null) {
			ComprobanteDetalle det = new ComprobanteDetalle();
			comprobante.setValor(totaldet);
			det = comprobanteDetalle;
			det.setValorcero(comprobanteDetalle.getRecaudaciondetalle().getValor());
			det.setValoriva(comprobanteDetalle.getRecaudaciondetalle().getValoriva());
			det.setCantidad(comprobanteDetalle.getCantidad());
			det.setSubtotal(calculaSubtotalDetalle(comprobanteDetalle.getCantidad(), comprobanteDetalle.getValorcero(),
					comprobanteDetalle.getValoriva()));
			det.setId_tmp(id_tmp);
			det.setComprobante(comprobante);
			det.setRecaudaciondetalle(comprobanteDetalle.getRecaudaciondetalle());
			listaComprobanteDet.add(det);
			serviceComprobanteDetalle.registrar(det);

			totalDetalle();
			// mostrarComprobanteDetalle();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Detalle registrado", "verifique toda la información"));

			//// AUDITORIA TABLA COMPROBANTE DETALLE

			serviceAuditoria.insertaModificacion("ComprobanteDetalle", "id_recaudaciondetalle", "I",
					usuPunto.getUsuario().getUsername(), fechaActual, det.getRecaudaciondetalle().getNombre(),
					det.getRecaudaciondetalle().getNombre(), det.getId());
			serviceAuditoria.insertaModificacion("ComprobanteDetalle", "cantidad", "I",
					usuPunto.getUsuario().getUsername(), fechaActual, String.valueOf(det.getCantidad()),
					String.valueOf(det.getCantidad()), det.getId());
			serviceAuditoria.insertaModificacion("ComprobanteDetalle", "Valorcero", "I",
					usuPunto.getUsuario().getUsername(), fechaActual, String.valueOf(det.getValorcero()),
					String.valueOf(det.getValorcero()), det.getId());
			serviceAuditoria.insertaModificacion("ComprobanteDetalle", "Valoriva", "I",
					usuPunto.getUsuario().getUsername(), fechaActual, String.valueOf(det.getValoriva()),
					String.valueOf(det.getValoriva()), det.getId());
			serviceAuditoria.insertaModificacion("ComprobanteDetalle", "Subtotal", "I",
					usuPunto.getUsuario().getUsername(), fechaActual, String.valueOf(det.getSubtotal()),
					String.valueOf(det.getSubtotal()), det.getId());
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Factura autorizada no puede cambiar el detalle", "Solo inf.de depósitos"));
		}
	}

	//// REGISTRAR DEPOSITO TEMPORAL
	public void registrarDeposito() throws Exception {
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

		if (comprobante.getAutorizacion() != null && valorAnterior != totaldep) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Factura autorizada, el valor total de la factura no puede ser diferente al valor de depositos",
					"ERROR"));
		} else {
			serviceComprobanteDepositos.registrar(dep);
		}

		//// AUDITORIA TABLA COMPROBANTE DEPOSITO

		serviceAuditoria.insertaModificacion("ComprobanteDeposito", "Origen", "I", usuPunto.getUsuario().getUsername(),
				fechaActual, dep.getOrigen(), dep.getOrigen(), dep.getId());

		serviceAuditoria.insertaModificacion("ComprobanteDeposito", "tipoTransaccion", "I",
				usuPunto.getUsuario().getUsername(), fechaActual, dep.getTipotransaccion(), dep.getTipotransaccion(),
				dep.getId());

		serviceAuditoria.insertaModificacion("ComprobanteDeposito", "comprobante", "I",
				usuPunto.getUsuario().getUsername(), fechaActual, dep.getNum_deposito(), dep.getNum_deposito(),
				dep.getId());

		@SuppressWarnings("unused")
		Date date = new Date();
		SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");

		serviceAuditoria.insertaModificacion("ComprobanteDeposito", "fecha", "I", usuPunto.getUsuario().getUsername(),
				fechaActual, formater.format(dep.getFecha()), formater.format(dep.getFecha()), dep.getId());

		serviceAuditoria.insertaModificacion("ComprobanteDeposito", "Valor", "I", usuPunto.getUsuario().getUsername(),
				fechaActual, String.valueOf(dep.getValor()), String.valueOf(dep.getValor()), dep.getId());

	}

	// CALCULA SUBTOTAL DETALLE
	public double calculaSubtotalDetalle(Integer cantidad, Double valor, Double valorIva) {
		subtotaldet = 0;
		if (valorIva == 0) {
			subtotaldet = (cantidad * valor);
			subtotaldet = FunValidaciones.formatearDecimales(subtotaldet, 2);
		} else {
			subtotaldet = cantidad * (valorIva * fun.valorIva() / 100);
			subtotaldet = FunValidaciones.formatearDecimales(subtotaldet, 2) + valorIva;
		}
		return subtotaldet;
	}

	// SUMAR TOTAL DETALLE
	public void totalDetalle() {

		totaldet = 0.00;
		for (ComprobanteDetalle det : listaComprobanteDet) {

			totaldet = totaldet + ((det.getCantidad() * det.getValorcero()) + det.getCantidad() * det.getValoriva());
		}
	}

	// SUMAR TOTAL DEPOSITOS
	public void totalDeposito() {
		totaldep = 0.00;
		for (ComprobanteDepositos dep : listaComprobanteDep) {
			totaldep = totaldep + dep.getValor();
		}
	}

	// OPERAR DETALLE
	public void operarDet(String accion) {
		try {
			if (accion.equalsIgnoreCase("R")) {
				registrarDetalle();
				totalDetalle();
			} else if (accion.equalsIgnoreCase("M")) {

				totalDetalle();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// OPERAR DEPOSITOS
	public void operarDep(String accion) {
		try {
			if (accion.equalsIgnoreCase("R")) {
				registrarDeposito();
				totalDeposito();
			} else if (accion.equalsIgnoreCase("M")) {
				totalDeposito();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void buscarClientes() {
		try {
			if (cliente == null) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese el No. de Identificacion", "Sin Datos"));
			} else {

				Long a = fun.buscarCliente(cliente);

				if (a == 0) {
					String validaIdentificacion = CedulaRuc.comprobacion(this.cliente.getCi(),
							this.cliente.getTipoid());
					if (validaIdentificacion.equals("T")) {

					} else {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Tipo de identificacion erronea buscar", validaIdentificacion));
					}
				} else {
					this.cliente = serviceCliente.ClientePorCiParametro(cliente.getCi());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// VALIDADOR DE CEDULA-RUC
	public void validaIdentificacion() {
		String validaIdentificacion = CedulaRuc.comprobacion(this.cliente.getCi(), this.cliente.getTipoid());
		if (validaIdentificacion.equals("T")) {
			validador = false;
		} else {
			validador = true;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Tipo de identificacion erronea", validaIdentificacion));
		}
	}

	public void modificarComprobanteDeposito() {
		totalDetalle();
		totalDeposito();
		if (valorAnterior != totaldet && comprobante.getAutorizacion() != null) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Valores diferentes y la factura ya se encuentra autorizadas", "No puede grabar"));
		} else if (totaldet == totaldep) {

			for (ComprobanteDepositos c : listaComprobanteDep) {
				try {
					c.setComprobante(comprobante);
					serviceComprobanteDepositos.modificar(c);

				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
					"Si realizó cambio en depósitos se grabó con éxito", "Aviso"));
			//// AUDITORIA TABLA COMPROBANTE DEPOSITO
			for (ComprobanteDepositos c : listaComprobanteDep) {
				if (c.getId() != null) {
					for (ComprobanteDepositos ct : listaComprobanteDepTmp) {
						if (c.getId().equals(ct.getId())) {
							// CAMPO RECAUDACIN
							if (!c.getOrigen().equals(ct.getOrigen())) {
								serviceAuditoria.insertaModificacion("ComprobanteDeposito", "Origen", "U",
										usuPunto.getUsuario().getUsername(), fechaActual, c.getOrigen(), ct.getOrigen(),
										c.getId());
							}
							if (!c.getTipotransaccion().equals(ct.getTipotransaccion())) {
								serviceAuditoria.insertaModificacion("ComprobanteDeposito", "tipoTransaccion", "U",
										usuPunto.getUsuario().getUsername(), fechaActual, c.getTipotransaccion(),
										ct.getTipotransaccion(), c.getId());
							}
							if (!c.getNum_deposito().equals(ct.getNum_deposito())) {
								serviceAuditoria.insertaModificacion("ComprobanteDeposito", "comprobante", "U",
										usuPunto.getUsuario().getUsername(), fechaActual, c.getNum_deposito(),
										ct.getNum_deposito(), c.getId());
							}
							@SuppressWarnings("unused")
							Date date = new Date();
							SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");

							if (!c.getFecha().equals(ct.getFecha())) {
								serviceAuditoria.insertaModificacion("ComprobanteDeposito", "fecha", "U",
										usuPunto.getUsuario().getUsername(), fechaActual, formater.format(c.getFecha()),
										formater.format(ct.getFecha()), c.getId());
							}
							if (c.getValor() != ct.getValor()) {
								serviceAuditoria.insertaModificacion("ComprobanteDeposito", "Valor", "U",
										usuPunto.getUsuario().getUsername(), fechaActual, String.valueOf(c.getValor()),
										String.valueOf(ct.getValor()), c.getId());
							}
						}
					}
				}
			}

		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error - Diferencia de valores en detalle y deposito", "Error"));
		}
	}

	@Transactional
	public void modificarComprobante() {
		try {

			modificarComprobanteDeposito();
			if (comprobante.getAutorizacion() != null && estadoAnterior == comprobante.getEstado()) {
				estadeshabilitado = true;
				estadeshabilitadoA = true;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"No puede realizar cambios la factura ya esta autorizada por el SRI, para el caso de cambios en los depósitos se grabó con éxito",
						"Error"));
			} else if ((comprobante.getAutorizacion() != null) && (!estadoAnterior.equals(comprobante.getEstado()))) {
				comprobante.setEstado(estadoAnterior);
				comprobante.setFechaanulacion(fechaActual);
				serviceComprobante.modificar(comprobante);
				validaestado = false;
				estadeshabilitado = true;
				estadeshabilitadoA = true;

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
						"se grabó con éxito, el cambio de estado y si realizó cambios de depósitos", "Aviso"));
				// CAMPO comprobante.getEstado())
				serviceAuditoria.insertaModificacion("Comprobante", "estado", "U", usuPunto.getUsuario().getUsername(),
						fechaActual, estadoAnterior, comprobante.getEstado(), comprobante.getId());
			} else if ((comprobante.getAutorizacion() == null) && (!estadoAnterior.equals(comprobante.getEstado()))) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"No puede cambiar el estado de la factura, no se encuentra autorizada", "Error"));
			} else if (comprobante.getAutorizacion() == null) {
				validaestado = true;
				totalDetalle();
				totalDeposito();
				if (totaldet == totaldep) {
					// VALIDAR CLIENTE
					Long a = fun.buscarCliente(cliente);
					if (a == 0) {
						Cliente c = new Cliente();
						c = cliente;
						String clave = this.cliente.getCi();
						String claveHash = BCrypt.hashpw(clave, BCrypt.gensalt());
						c.setClave(claveHash);
						serviceCliente.registrar(c);
					} else {
						serviceCliente.modificar(cliente);
					}
					/// MODIFICAR COMPROBANTE
					comprobante.setValor(totaldet);
					comprobante.setClienteruc(cliente.getCi());
					comprobante.setClientenombre(cliente.getNombre().toUpperCase());
					comprobante.setClientedireccion(cliente.getDireccion().toUpperCase());
					comprobante.setClientetelefono(cliente.getTelefono());
					comprobante.setEstado(comprobante.getEstado());
					comprobante.setValor(totaldet);
					comprobante.setDetalle(comprobante.getDetalle());
					serviceComprobante.modificar(comprobante);

					//// AUDITORIA TABLA COMPROBANTE
					// CAMPO comprobante.getCliente().getCi())

					if (!cliente.getCi().equals(comprobante.getCliente().getCi())) {

						serviceAuditoria.insertaModificacion("Comprobante", "id_cliente", "U",
								usuPunto.getUsuario().getUsername(), fechaActual, cliente.getCi(),
								comprobante.getCliente().getCi(), comprobante.getId());
					}

					// CAMPO comprobante.getValor)
					if (totaldet != valorAnterior) {

						serviceAuditoria.insertaModificacion("Comprobante", "valor", "U",
								usuPunto.getUsuario().getUsername(), fechaActual, String.valueOf(totaldet),
								String.valueOf(valorAnterior), comprobante.getId());
					}

					//// FIN AUDITORIA TABLA COMPROBANTE

					for (ComprobanteDetalle c : listaComprobanteDet) {

						c.setComprobante(comprobante);
						serviceComprobanteDetalle.modificar(c);
					}

					//// AUDITORIA TABLA COMPROBANTE DETALLE
					for (ComprobanteDetalle c : listaComprobanteDet) {
						if (c.getId() != null) {
							for (ComprobanteDetalle ct : listaComprobanteDetTmp) {
								if (c.getId().equals(ct.getId())) {
									// CAMPO RECAUDACIN
									if (!c.getRecaudaciondetalle().getId().equals(ct.getRecaudaciondetalle().getId())) {
										serviceAuditoria.insertaModificacion("ComprobanteDetalle",
												"id_recaudaciondetalle", "U", usuPunto.getUsuario().getUsername(),
												fechaActual, c.getRecaudaciondetalle().getNombre(),
												ct.getRecaudaciondetalle().getNombre(), c.getId());
									}
									if (c.getCantidad() != ct.getCantidad()) {
										serviceAuditoria.insertaModificacion("ComprobanteDetalle", "cantidad", "U",
												usuPunto.getUsuario().getUsername(), fechaActual,
												String.valueOf(c.getCantidad()), String.valueOf(ct.getCantidad()),
												c.getId());
									}

									if (!c.getValorcero().equals(ct.getValorcero())) {
										serviceAuditoria.insertaModificacion("ComprobanteDetalle", "Valorcero", "U",
												usuPunto.getUsuario().getUsername(), fechaActual,
												String.valueOf(c.getValorcero()), String.valueOf(ct.getValorcero()),
												c.getId());
									}

									if (!c.getValoriva().equals(ct.getValoriva())) {
										serviceAuditoria.insertaModificacion("ComprobanteDetalle", "Valoriva", "U",
												usuPunto.getUsuario().getUsername(), fechaActual,
												String.valueOf(c.getValoriva()), String.valueOf(ct.getValoriva()),
												c.getId());
									}
									if (!c.getSubtotal().equals(ct.getSubtotal())) {
										serviceAuditoria.insertaModificacion("ComprobanteDetalle", "Subtotal", "U",
												usuPunto.getUsuario().getUsername(), fechaActual,
												String.valueOf(c.getSubtotal()), String.valueOf(ct.getSubtotal()),
												c.getId());
									}

								}
							}
						}
					}

					genXml.generarXmlArchivo(comprobante.getPuntoRecaudacion().getId(), comprobante.getNumero());

					estadeshabilitado = true;
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, "se grabó con éxito", "Aviso"));
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error - Diferencia de valores en detalle y deposito", "Error"));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/// CARGAR NUEVA RECAUDACION
	public void cargaRecaudacion() {

		comprobanteDetalle.setCantidad(0);
		comprobanteDetalle.setValorcero(comprobanteDetalle.getRecaudaciondetalle().getValor());
		comprobanteDetalle.setValoriva(comprobanteDetalle.getRecaudaciondetalle().getValoriva());
		comprobanteDetalle.setSubtotal(0.00);

	}

	public void cambioEstado(String e) {
		estadoAnterior = e;
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

	// ELIMINAR DETALLE
	@Transactional
	public void eliminarCompDetalle(ComprobanteDetalle comprobanteDetalle) {
		if (comprobante.getAutorizacion() == null) {
			serviceComprobanteDetalle.eliminarComprobanteDetalle(comprobanteDetalle.getId());
			this.listaComprobanteDet = this.serviceComprobanteDetalle.listarComDetPorIdComp(comprobante.getId());
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "se eliminó con éxito", "Aviso"));
			totalDetalle();
			//// AUDITORIA TABLA COMPROBANTE DETALLE
			for (ComprobanteDetalle c : listaComprobanteDet) {
				if (c.getId() != null) {
					for (ComprobanteDetalle ct : listaComprobanteDetTmp) {
						if (!c.getId().equals(ct.getId())) {
							// CAMPO RECAUDACIN

							serviceAuditoria.insertaModificacion("ComprobanteDetalle", "id_recaudaciondetalle", "E",
									usuPunto.getUsuario().getUsername(), fechaActual,
									ct.getRecaudaciondetalle().getNombre(), ct.getRecaudaciondetalle().getNombre(),
									ct.getId());

							serviceAuditoria.insertaModificacion("ComprobanteDetalle", "cantidad", "E",
									usuPunto.getUsuario().getUsername(), fechaActual, String.valueOf(ct.getCantidad()),
									String.valueOf(ct.getCantidad()), ct.getId());
							serviceAuditoria.insertaModificacion("ComprobanteDetalle", "Valorcero", "E",
									usuPunto.getUsuario().getUsername(), fechaActual, String.valueOf(ct.getValorcero()),
									String.valueOf(ct.getValorcero()), ct.getId());
							serviceAuditoria.insertaModificacion("ComprobanteDetalle", "Valoriva", "E",
									usuPunto.getUsuario().getUsername(), fechaActual, String.valueOf(ct.getValoriva()),
									String.valueOf(ct.getValoriva()), ct.getId());
							serviceAuditoria.insertaModificacion("ComprobanteDetalle", "Subtotal", "E",
									usuPunto.getUsuario().getUsername(), fechaActual, String.valueOf(ct.getSubtotal()),
									String.valueOf(ct.getSubtotal()), ct.getId());
						}

					}
				}
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "No puede eliminar", "Factura autorizada"));
		}

	}

	// ELIMINAR DEPOSITO

	public void eliminarCompDeposito(ComprobanteDepositos comprobanteDepositos, int index) {
		this.serviceComprobanteDepositos.eliminarComprobanteDeposito(comprobanteDepositos.getId());
		this.listaComprobanteDep = this.serviceComprobanteDepositos.listarComDepPorIdComp(comprobante.getId());
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,
				"Se elimina con éxito, recuerde que debe guardar", "Aviso"));
		totalDeposito();

		//// AUDITORIA TABLA COMPROBANTE DEPOSITO
		for (ComprobanteDepositos c : listaComprobanteDep) {
			if (c.getId() != null) {
				for (ComprobanteDepositos ct : listaComprobanteDepTmp) {
					if (!c.getId().equals(ct.getId())) {
						// CAMPO RECAUDACIN

						serviceAuditoria.insertaModificacion("ComprobanteDeposito", "Origen", "E",
								usuPunto.getUsuario().getUsername(), fechaActual, ct.getOrigen(), ct.getOrigen(),
								ct.getId());

						serviceAuditoria.insertaModificacion("ComprobanteDeposito", "tipoTransaccion", "E",
								usuPunto.getUsuario().getUsername(), fechaActual, ct.getTipotransaccion(),
								ct.getTipotransaccion(), ct.getId());

						serviceAuditoria.insertaModificacion("ComprobanteDeposito", "comprobante", "E",
								usuPunto.getUsuario().getUsername(), fechaActual, ct.getNum_deposito(),
								ct.getNum_deposito(), ct.getId());

						@SuppressWarnings("unused")
						Date date = new Date();
						SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");

						serviceAuditoria.insertaModificacion("ComprobanteDeposito", "fecha", "E",
								usuPunto.getUsuario().getUsername(), fechaActual, formater.format(ct.getFecha()),
								formater.format(ct.getFecha()), ct.getId());

						serviceAuditoria.insertaModificacion("ComprobanteDeposito", "Valor", "E",
								usuPunto.getUsuario().getUsername(), fechaActual, String.valueOf(ct.getValor()),
								String.valueOf(ct.getValor()), ct.getId());

					}
				}
			}
		}
	}

	////////////////////// PROCESO SRI
	////////////////////// ///////////////////////////////////////////////////////////////

	public static XML_Utilidades xml_utilidades = new XML_Utilidades();

	public String firmarDocumentoXmlXades(Comprobante c) throws FileNotFoundException, IOException {

		XadesSign x = new XadesSign();

		byte[] xmlSigned = x.firmarDocumentoXmlXades(c.getXml().getBytes());

		String xml64 = converBase64(xmlSigned);
		// GUARDA EL DOCUMENTO XML EN EL PATH DE GENERADOS
		FileUtil.writeXml(c, c.getXml().getBytes());
		// GUARDA EL DOCUMENTO XML EN EL PATH DE FIRMADOS
		FileUtil.writeSignedXml(c, xmlSigned);

		return xml64;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////
	public void enviar(Integer id_comprobante) {// GEN-FIRST:event_btnEnviarActionPerformed

		comprobante = serviceComprobante.listarComprobantePorId(id_comprobante);

		if (comprobante.getAutorizacion() != null) {
			estadeshabilitadoEnv = true;
			estadeshabilitado = true;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"No puede realizar cambios el comprobante se encuentra autorizado por el SRI", "Error"));
		} else {
			// System.out.println("entra a firmar " + comprobante.getNumero());

			// estadeshabilitadoEnv = false;
			SoapRecepcion n = new SoapRecepcion();

			ambiente = comprobante.getUsuarioPunto().getPuntoRecaudacion().getInstitucion().getAmbiente();
			System.out.println("------Ambiente-------- " + ambiente);

			if (ambiente.equals("1")) {
				System.out.println("------Entra Enviar Ambiente de Pruebas -------- ");
				//// AMBIENTE DE PRUEBAS
				url = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";
				host = "celcer.sri.gob.ec";

			} else if (ambiente.equals("2")) {
				System.out.println("------Entra Enviar Ambiente de PRODUCCIÓN -------- ");
				//// AMBIENTE DE PRODUCCIoN
				url = "https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";
				host = "cel.sri.gob.ec";

			}

			//// AMBIENTE DE PRUEBAS
//			String url = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";
//			String host = "celcer.sri.gob.ec";

			//// AMBIENTE DE PRODUCCIN
//				String url = "https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";
//				String host = "cel.sri.gob.ec";

			try {
				String xml64 = this.firmarDocumentoXmlXades(comprobante);
//				System.out.println("2");
				try {

					URL oURL = new URL(url);
					String method = "POST";

					HttpURLConnection con = (HttpURLConnection) oURL.openConnection(Proxy.NO_PROXY);
					con.setDoOutput(true);
					con.setRequestMethod(method);
					con.setRequestProperty("Content-type", "text/xml; charset=utf-8");
					con.setRequestProperty("SOAPAction", "");
					con.setRequestProperty("Host", host);
					OutputStream reqStreamOut = con.getOutputStream();
					reqStreamOut.write(n.formatSendPost(xml64).getBytes());

					java.io.BufferedReader rd = new java.io.BufferedReader(
							new java.io.InputStreamReader(con.getInputStream(), "UTF8"));

					String line = "";
					StringBuilder sb = new StringBuilder();

					while ((line = rd.readLine()) != null) {
						sb.append(line);
					}
					System.out.println(sb.toString());
					Document doc = xml_utilidades.convertStringToDocument(sb.toString());
					String estado = xml_utilidades.getNodes("RespuestaRecepcionComprobante", "estado", doc);

					if (estado.equals("RECIBIDA"))
						try {

							comprobante.setEstadosri("E");
							comprobante.setEstadoerror("S");
							serviceComprobante.modificar(comprobante);
							System.out.println("deshabilita booton enviar");
							estadeshabilitadoEnv = true; // PARA DESHABILITAR EL BOTON ENVIAR EN LA FACTURA
							System.out.println("habilita booton autorizar");
							estadeshabilitadoAut = false;
							estadeshabilitado = true;

							MensajeSri = "Enviado con Exito";
							MensajeSriError = "Sin errores";
						} catch (Exception e) {
							// TODO: handle exception
							System.out.println("ERROR " + e);
						}
					else if (estado.equals("DEVUELTA")) {

						try {
							this.comprobante.setEstadosri("R");
							this.comprobante.setEstadoerror("C");
							serviceComprobante.modificar(this.comprobante);

							String identificador = xml_utilidades.getNodes("mensaje", "identificador", doc);
							String mensaje = xml_utilidades.getNodes("mensaje", "mensaje", doc);
							String tipo = xml_utilidades.getNodes("mensaje", "tipo", doc);
							this.mensaje.setId_comprobante(this.comprobante.getId());
							this.mensaje.setIdentificador(identificador);
							this.mensaje.setMensaje(mensaje);
							this.mensaje.setInformacionAdicional("información Adicional");
							this.mensaje.setTipo(tipo);
							serviceMensaje.registrar(this.mensaje);

							MensajeSri = mensaje;
							MensajeSriError = mensaje;

						} catch (Exception e) {
							// TODO: handle exception
						}
					}

					con.disconnect();

				} catch (Exception ex) {
					System.out.println("Error: " + ex);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	///////////////////////////////////////////////////////////
	public void autorizar(Integer id_comprobante) {
		comprobante = serviceComprobante.listarComprobantePorId(id_comprobante);

		if (comprobante.getAutorizacion() != null) {
			estadeshabilitadoAut = true;
			estadeshabilitadoA = true;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"No puede realizar cambios el comprobante se encuentra autorizado por el SRI", "Error"));
		} else {
			// estadeshabilitadoA = false;
			SoapAutorizacion n = new SoapAutorizacion();

			ambiente = comprobante.getUsuarioPunto().getPuntoRecaudacion().getInstitucion().getAmbiente();
			System.out.println("------Ambiente-------- " + ambiente);

			if (ambiente.equals("1")) {
				System.out.println("------Entra Autoriza Ambiente de Pruebas -------- ");
				//// AMBIENTE DE PRUEBAS
				url = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl";
				host = "celcer.sri.gob.ec";

			} else if (ambiente.equals("2")) {
				System.out.println("------Entra Autoriza Ambiente de PRODUCCIÓN -------- ");
				//// AMBIENTE DE PRODUCCIoN
				url = "https://cel.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl";
				host = "cel.sri.gob.ec";
			}

			/// AMBIENTE DE PRUEBAS
//			String url = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl";
//			String host = "celcer.sri.gob.ec";

			/// AMBIENTE DE PRODUCCIN
			// String url =
			/// "https://cel.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl";
			// String host = "cel.sri.gob.ec";
			try {
				URL oURL = new URL(url);
				HttpURLConnection con = (HttpURLConnection) oURL.openConnection(Proxy.NO_PROXY);
				con.setDoOutput(true);
				String method = "POST";
				con.setRequestMethod(method);
				con.setRequestProperty("Content-type", "text/xml; charset=utf-8");
				con.setRequestProperty("SOAPAction", "");
				con.setRequestProperty("Host", host);
				OutputStream reqStreamOut = con.getOutputStream();
				reqStreamOut.write(n.formatSendPost(comprobante.getClaveacceso()).getBytes());
				System.out.println("2");
				java.io.BufferedReader rd = new java.io.BufferedReader(
						new java.io.InputStreamReader(con.getInputStream(), "UTF8"));
				String line = "";
				StringBuilder sb = new StringBuilder();
				while ((line = rd.readLine()) != null) {
					sb.append(line);
				}

				System.out.println(sb.toString());
				Document doc = xml_utilidades.convertStringToDocument(sb.toString());
//			SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
				String estado = xml_utilidades.getNodes("RespuestaAutorizacionComprobante", "estado", doc);

				if (estado.equals("AUTORIZADO")) {
					try {
						String fa = xml_utilidades.getNodes("RespuestaAutorizacionComprobante", "fechaAutorizacion",
								doc);
						String na = xml_utilidades.getNodes("RespuestaAutorizacionComprobante", "numeroAutorizacion",
								doc);

						///// ACTUALIZA INFORMACIN DE AUTORIZACIN EN EL COMPROBANTE
						comprobante.setEstadosri("A");
						comprobante.setAutorizacion(na);
						comprobante.setAutorizacionfecha(fa);
						this.comprobante.setEstadoerror("S");
						comprobante.setXmlsri(sb.toString());
						serviceComprobante.modificar(comprobante);

						////////// FIN ACTUALIZACION

						FileUtil.writeSignedAuth(comprobante, sb.toString().getBytes());
						estadeshabilitadoAut = true; // PARA DESHABILITAR EL BOTON ENVIAR EN LA FACTURA
						estadeshabilitadoA = true;
						MensajeSri = "Autorización con Exito";
						MensajeSriError = "Sin errores";

					} catch (Exception e) {
						e.printStackTrace();
					}

				} else if (estado.equals("NO AUTORIZADO")) {

					this.comprobante.setEstadosri("Z");
					this.comprobante.setEstadoerror("C");
					serviceComprobante.modificar(this.comprobante);

					String identificador = xml_utilidades.getNodes("mensaje", "identificador", doc);
					String mensaje = xml_utilidades.getNodes("mensaje", "mensaje", doc);
					String informacionAdicional = xml_utilidades.getNodes("mensaje", "informacionAdicional", doc);
					String tipo = xml_utilidades.getNodes("mensaje", "tipo", doc);

					//// INGRESA A LA TABLA MENSAJES EL ERROR
					this.mensaje.setId_comprobante(this.comprobante.getId());
					this.mensaje.setIdentificador(identificador);
					this.mensaje.setMensaje(mensaje);
					this.mensaje.setInformacionAdicional(informacionAdicional);
					this.mensaje.setTipo(tipo);
					serviceMensaje.registrar(this.mensaje);

					MensajeSri = mensaje;
					MensajeSriError = informacionAdicional;
					System.out.println("TERMINA AUTORIZAR EN MENSAJE");
				}
				con.disconnect();
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	}

	////////////////// ENVIAR CORREO

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
		session.setDebug(true);

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

		} catch (Exception e) {
			System.out.println("Error " + e);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR: No se Envió por correo", "ERROR"));

		}
	}

	////////////////////////////////////////////////

	public String getEstadoAnterior() {
		return estadoAnterior;
	}

	public void setEstadoAnterior(String estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}

	// Getters y Setters

	public Integer getId_compDetalle() {
		return id_compDetalle;
	}

	public void setId_compDetalle(Integer id_compDetalle) {
		this.id_compDetalle = id_compDetalle;
	}

	public Integer getId_compDeposito() {
		return id_compDeposito;
	}

	public void setId_compDeposito(Integer id_compDeposito) {
		this.id_compDeposito = id_compDeposito;
	}

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

	public ComprobanteDetalle getComprobanteDetalleNotas() {
		return comprobanteDetalleNotas;
	}

	public void setComprobanteDetalleNotas(ComprobanteDetalle comprobanteDetalleNotas) {
		this.comprobanteDetalleNotas = comprobanteDetalleNotas;
	}

	public String getTipo_proceso() {
		return tipo_proceso;
	}

	public void setTipo_proceso(String tipo_proceso) {
		this.tipo_proceso = tipo_proceso;
	}

	public List<RecaudacionDetalle> getListaRecaudacionDetalleTodas() {
		return listaRecaudacionDetalleTodas;
	}

	public void setListaRecaudacionDetalleTodas(List<RecaudacionDetalle> listaRecaudacionDetalleTodas) {
		this.listaRecaudacionDetalleTodas = listaRecaudacionDetalleTodas;
	}

	public Integer getId_comprobante() {
		return id_comprobante;
	}

	public void setId_comprobante(Integer id_comprobante) {
		this.id_comprobante = id_comprobante;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public Cliente getClienteNotas() {
		return clienteNotas;
	}

	public void setClienteNotas(Cliente clienteNotas) {
		this.clienteNotas = clienteNotas;
	}

	public Comprobante getComprobanteNot() {
		return comprobanteNot;
	}

	public void setComprobanteNot(Comprobante comprobanteNot) {
		this.comprobanteNot = comprobanteNot;
	}

	public List<ComprobanteDetalle> getListaComprobanteDetTmp() {
		return listaComprobanteDetTmp;
	}

	public void setListaComprobanteDetTmp(List<ComprobanteDetalle> listaComprobanteDetTmp) {
		this.listaComprobanteDetTmp = listaComprobanteDetTmp;
	}

	public List<ComprobanteDepositos> getListaComprobanteDepTmp() {
		return listaComprobanteDepTmp;
	}

	public void setListaComprobanteDepTmp(List<ComprobanteDepositos> listaComprobanteDepTmp) {
		this.listaComprobanteDepTmp = listaComprobanteDepTmp;
	}

	public Comprobante getComprobanteAud() {
		return comprobanteAud;
	}

	public void setComprobanteAud(Comprobante comprobanteAud) {
		this.comprobanteAud = comprobanteAud;
	}

	public boolean isEstadeshabilitadoA() {
		return estadeshabilitadoA;
	}

	public void setEstadeshabilitadoA(boolean estadeshabilitadoA) {
		this.estadeshabilitadoA = estadeshabilitadoA;
	}

	public String getAutorizacion() {
		return autorizacion;
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

	public boolean isValidaestado() {
		return validaestado;
	}

	public void setValidaestado(boolean validaestado) {
		this.validaestado = validaestado;
	}

	public void setAutorizacion(String autorizacion) {
		this.autorizacion = autorizacion;
	}

	public Mensaje getMensaje() {
		return mensaje;
	}

	public void setMensaje(Mensaje mensaje) {
		this.mensaje = mensaje;
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

	public void setEstadeshabilitadoAut(boolean estadeshabilitadoAut) {
		this.estadeshabilitadoAut = estadeshabilitadoAut;
	}

	public String getMensajeSri() {
		return MensajeSri;
	}

	public void setMensajeSri(String mensajeSri) {
		MensajeSri = mensajeSri;
	}

	public String getMensajeSriError() {
		return MensajeSriError;
	}

	public void setMensajeSriError(String mensajeSriError) {
		MensajeSriError = mensajeSriError;
	}

	public double getValorAnterior() {
		return valorAnterior;
	}

	public void setValorAnterior(double valorAnterior) {
		this.valorAnterior = valorAnterior;
	}

}
