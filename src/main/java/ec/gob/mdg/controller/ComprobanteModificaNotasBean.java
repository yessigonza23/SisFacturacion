package ec.gob.mdg.controller;

import static ec.gob.mdg.sri.util.StringUtil.converBase64;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
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
import org.w3c.dom.Document;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.TimeZone;

import ec.gob.mdg.model.Cliente;
import ec.gob.mdg.model.Comprobante;
import ec.gob.mdg.model.ComprobanteDetalle;
import ec.gob.mdg.model.Institucion;
import ec.gob.mdg.model.Mensaje;
import ec.gob.mdg.model.PuntoRecaudacion;
import ec.gob.mdg.model.RecaudacionDetalle;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.service.IAuditoriaService;
import ec.gob.mdg.service.IClienteService;
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
import ec.gob.mdg.util.UtilsArchivos;
import ec.gob.mdg.util.UtilsDate;
import ec.gob.mdg.util.ValorMod11;
import ec.gob.mdg.validaciones.FunValidaciones;
import ec.gob.mdg.validaciones.Funciones;

@Named
@ViewScoped
public class ComprobanteModificaNotasBean implements Serializable {

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
	private IRecaudacionDetalleService serviceRecaudacionDetalle;

	@Inject
	private Funciones fun;

	@Inject
	private GenerarDOMNotaBean genXml;

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
	private Comprobante comprobantetmp;
	private ComprobanteDetalle comprobanteDetalle = new ComprobanteDetalle();
	private ComprobanteDetalle comprobanteDetalleNotas = new ComprobanteDetalle();

	private RecaudacionDetalle recaudacionDetalle;
	private Institucion institucion;

	private List<Cliente> listaClientes = new ArrayList<Cliente>();
	private List<Comprobante> listaComprobante = new ArrayList<Comprobante>();
	private List<ComprobanteDetalle> listaComprobanteDet = new ArrayList<ComprobanteDetalle>();
	private List<ComprobanteDetalle> listaComprobanteDetNot = new ArrayList<ComprobanteDetalle>();
	private List<ComprobanteDetalle> listaComprobanteDetEliminadas = new ArrayList<ComprobanteDetalle>();

	private List<RecaudacionDetalle> listaRecaudacionDetalle;
	private List<RecaudacionDetalle> listaRecaudacionDetalleTodas;
	private List<ComprobanteDetalle> listaComprobanteDetTmp = new ArrayList<ComprobanteDetalle>();
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
	boolean estadeshabilitado = false;
	boolean estadeshabilitadoA = false;
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

	/// variables para auditoria
	String autorizacion;
	String estadoAnterior;
	double valorAnterior;
	String MensajeSri;
	String MensajeSriError;

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
			if (comprobante.getAutorizacion() != null) {
				estadeshabilitado = true;
				estadeshabilitadoA = true;
			}
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
			this.listaComprobanteDetNot = this.serviceComprobanteDetalle.listarComDetPorIdComp(id);
			this.listaComprobanteDet = this.serviceComprobanteDetalle.listarComDetPorIdComp(id);
			this.cliente = this.serviceCliente.ClientePorCiParametro(comprobante.getClienteruc());
			this.listaRecaudacionDetalle = this.serviceRecaudacionDetalle.listar();
			totalDetalle();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void mostrarAutorizacion() {
		this.comprobante = serviceComprobante.listarComprobantePorId(id);
	}

	// VALIDA AUTORIZACIN
	public void validadAutorizacion() {
		if (comprobante.getAutorizacion() != null) {
			estadeshabilitado = true;
			estadeshabilitadoA = true;
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
						"No puede borrar la Información del SRI, la autorización de la Factura", "Error"));
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
							new FacesMessage(FacesMessage.SEVERITY_INFO, "Se borra con éxito", "Aviso"));
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

	// REGISTRAR DETALLE TEMPORAL
	public void registrarDetalle() {
		listaComprobanteDetTmp.add(comprobanteDetalle);
		totalDetalle();
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

	@Transactional
	public void modificarComprobante() {
		try {

			if (comprobante.getAutorizacion() != null && estadoAnterior == comprobante.getEstado()) {
				estadeshabilitado = true;
				estadeshabilitadoA = true;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"No puede realizar cambios el comprobante ya está autorizada por el SRI", "Error"));
			} else if ((comprobante.getAutorizacion() != null) && (!estadoAnterior.equals(comprobante.getEstado()))) {

				comprobante.setEstado(estadoAnterior);
				serviceComprobante.modificar(comprobante);
				validaestado = false;
				estadeshabilitado = true;
				estadeshabilitadoA = true;
				// CAMPO comprobante.getEstado())
				serviceAuditoria.insertaModificacion("ComprobanteNotas", "estado", "U",
						usuPunto.getUsuario().getUsername(), fechaActual, estadoAnterior, comprobante.getEstado(),
						comprobante.getId());

			} else if (comprobante.getAutorizacion() == null) {

				validaestado = true;

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
				comprobante.setEstado(comprobante.getEstado());
				comprobante.setValor(totaldet);
				comprobante.setDetalle(comprobante.getDetalle());
				comprobante.setMotivonotacredito(comprobante.getMotivonotacredito());
				
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

				claveA = dia + mes + anio + "04" + institucion.getRuc() + institucion.getAmbiente()
						+ punto.getEstablecimiento() + punto.getPuntoemision()
						+ StringUtils.leftPad(String.valueOf(comprobante.getNumero()), 9, "0") + "12345678" + "1";

				String verificador = String.valueOf(ValorMod11.mod11(claveA));

				claveA = claveA + verificador;

				comprobante.setClaveacceso(claveA);
				
				serviceComprobante.modificar(comprobante);
				/// GENERAR XML PARA LA FACTURA
				System.out.println("punto.getId(), comprobante.getNumero(): "+punto.getId() +"-" + comprobante.getNumero());
				genXml.generarXmlArchivo(punto.getId(), comprobante.getNumero());

				//// AUDITORIA TABLA COMPROBANTE
				// CAMPO comprobante.getValor)
				if (totaldet != valorAnterior) {
					serviceAuditoria.insertaModificacion("ComprobanteNotas", "valor", "U",
							usuPunto.getUsuario().getUsername(), fechaActual, String.valueOf(totaldet),
							String.valueOf(valorAnterior), comprobante.getId());
				}
				//// FIN AUDITORIA TABLA COMPROBANTE

				for (ComprobanteDetalle cd : listaComprobanteDetEliminadas) {
					serviceComprobanteDetalle.eliminarComprobanteDetalle(cd.getId());
				}

				//// AUDITORIA TABLA COMPROBANTE DETALLE
				for (ComprobanteDetalle c : listaComprobanteDetEliminadas) {
					serviceAuditoria.insertaModificacion("ComprobanteDetalleNotas", "id_recaudaciondetalle", "D",
							usuPunto.getUsuario().getUsername(), fechaActual, c.getRecaudaciondetalle().getNombre(),
							c.getRecaudaciondetalle().getNombre(), c.getId());
				}

				genXml.generarXmlArchivo(comprobante.getPuntoRecaudacion().getId(), comprobante.getNumero());

				estadeshabilitado = true;
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "se grabó con éxito", "Aviso"));

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	///////////////////////////////////
	/// CARGAR NUEVA RECAUDACION
	public void cargaRecaudacion() {
		comprobanteDetalle.setCantidad(0);
		comprobanteDetalle.setValorcero(comprobanteDetalle.getRecaudaciondetalle().getValor());
		comprobanteDetalle.setValoriva(comprobanteDetalle.getRecaudaciondetalle().getValoriva());
		comprobanteDetalle.setSubtotal(0.00);

	}

	public void cargaSRI() {

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
	public void eliminarCompDetalle(int index, Integer id_comprobanteDetalle) {
		for (ComprobanteDetalle cd : listaComprobanteDet) {
			ComprobanteDetalle compdet = new ComprobanteDetalle();
			compdet = cd;
			if (cd.getId() == id_comprobanteDetalle) {
				listaComprobanteDetEliminadas.add(compdet);
			} else {
				listaComprobanteDetNot.add(compdet);
			}
		}
		listaComprobanteDet.remove(index);
		totalDetalle();
	}

		//////////////////////PROCESO SRI

	public static XML_Utilidades xml_utilidades = new XML_Utilidades();

	public String firmarDocumentoXmlXades(Comprobante c) throws FileNotFoundException, IOException {

		XadesSign x = new XadesSign();
//		System.out.println("firma xml " + c.getXml());
		byte[] xmlSigned = x.firmarDocumentoXmlXades(c.getXml().getBytes());
//		System.out.println("pasa firmando");
		String xml64 = converBase64(xmlSigned);
		// GUARDA EL DOCUMENTO XML EN EL PATH DE GENERADOS
		FileUtil.writeXml(c, c.getXml().getBytes());
		// GUARDA EL DOCUMENTO XML EN EL PATH DE FIRMADOS
		FileUtil.writeSignedXml(c, xmlSigned);

		return xml64;
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////
	public void enviar(Integer id_comprobante) {// GEN-FIRST:event_btnEnviarActionPerformed
		//		System.out.println("entra a envair id_comprobante" + id_comprobante);
		comprobante = serviceComprobante.listarComprobantePorId(id_comprobante);

		if (comprobante.getAutorizacion() != null) {
			estadeshabilitadoEnv = true;
			estadeshabilitado = true;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"No puede realizar cambios el comprobante se encuentra autorizado por el SRI", "Error"));
		} else {
			//			System.out.println("entra a firmar " + comprobante.getNumero());

			// estadeshabilitadoEnv = false;
			SoapRecepcion n = new SoapRecepcion();

			ambiente = comprobante.getUsuarioPunto().getPuntoRecaudacion().getInstitucion().getAmbiente();
			//			System.out.println("------Ambiente-------- " + ambiente);

			if (ambiente.equals("1")) {
				//				System.out.println("------Entra Enviar Ambiente de Pruebas -------- ");
				//// AMBIENTE DE PRUEBAS
				url = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";
				host = "celcer.sri.gob.ec";
			} else if (ambiente.equals("2")) {
				//				System.out.println("------Entra Enviar Ambiente de PRODUCCIÓN -------- ");
				//// AMBIENTE DE PRODUCCIoN
				url = "https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";
				host = "cel.sri.gob.ec";
			}

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
					//					System.out.println(sb.toString());
					Document doc = xml_utilidades.convertStringToDocument(sb.toString());
					String estado = xml_utilidades.getNodes("RespuestaRecepcionComprobante", "estado", doc);

					if (estado.equals("RECIBIDA"))
						try {

							comprobante.setEstadosri("E");
							comprobante.setEstadoerror("S");
							serviceComprobante.modificar(comprobante);
							//							System.out.println("deshabilita booton enviar");
							estadeshabilitadoEnv = true; // PARA DESHABILITAR EL BOTON ENVIAR EN LA FACTURA
							//							System.out.println("habilita booton autorizar");
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
			//			System.out.println("------Ambiente-------- " + ambiente);			

			if (ambiente.equals("1")) {
				//				System.out.println("------Entra Autoriza Ambiente de Pruebas -------- ");
				//// AMBIENTE DE PRUEBAS
				url = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl";
				host = "celcer.sri.gob.ec";

			} else if (ambiente.equals("2")) {
				//				System.out.println("------Entra Autoriza Ambiente de PRODUCCIÓN -------- ");
				//// AMBIENTE DE PRODUCCIoN
				url = "https://cel.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl";
				host = "cel.sri.gob.ec";
			}

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
				//				System.out.println("2");
				java.io.BufferedReader rd = new java.io.BufferedReader(
						new java.io.InputStreamReader(con.getInputStream(), "UTF8"));
				String line = "";
				StringBuilder sb = new StringBuilder();
				while ((line = rd.readLine()) != null) {
					sb.append(line);
				}

               //				System.out.println(sb.toString());
				Document doc = xml_utilidades.convertStringToDocument(sb.toString());
				// SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
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

                    ////INGRESA A LA TABLA MENSAJES EL ERROR
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
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true"); // Para conectar de manera segura al servidor SMTP
		props.put("mail.smtp.port", "587"); // El puerto SMTP seguro de Google
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
				+ "<br><br>Atentamente,<br>" + "Ministerio de Gobierno <br><br>" + "</body></html>";

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

            ////AGREGAR UNA CONDICION PARA CUANDO NO HAY EL ADJUNTO -----PEENDIENTE

			MimeMultipart multiParte = new MimeMultipart();
			multiParte.addBodyPart(textoMensaje);
			multiParte.addBodyPart(adjunto);

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(institucion.getUsuariocorreo(), institucion.getNombre()));
			message.addRecipients(Message.RecipientType.TO, comprobante.getCliente().getCorreo());
			message.setSubject(asuntoMensaje);
			message.setContent(multiParte);
			Transport transport = session.getTransport("smtp");
			transport.connect("mail.ministeriodegobierno.gob.ec", institucion.getUsuariocorreo(),
					institucion.getClavecorreo());
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Se envia con éxito", "Aviso"));

		} catch (Exception e) {
			System.out.println("Error " + e);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR: No se Envió por correo", "ERROR"));

		}
	}

////////////////////////////////////////////////

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
		return comprobantetmp;
	}

	public void setComprobanteNot(Comprobante comprobanteNot) {
		this.comprobantetmp = comprobanteNot;
	}

	public List<ComprobanteDetalle> getListaComprobanteDetTmp() {
		return listaComprobanteDetTmp;
	}

	public void setListaComprobanteDetTmp(List<ComprobanteDetalle> listaComprobanteDetTmp) {
		this.listaComprobanteDetTmp = listaComprobanteDetTmp;
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

	public Comprobante getComprobantetmp() {
		return comprobantetmp;
	}

	public void setComprobantetmp(Comprobante comprobantetmp) {
		this.comprobantetmp = comprobantetmp;
	}

	public List<ComprobanteDetalle> getListaComprobanteDetEliminadas() {
		return listaComprobanteDetEliminadas;
	}

	public void setListaComprobanteDetEliminadas(List<ComprobanteDetalle> listaComprobanteDetEliminadas) {
		this.listaComprobanteDetEliminadas = listaComprobanteDetEliminadas;
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

	public double getValorAnterior() {
		return valorAnterior;
	}

	public void setValorAnterior(double valorAnterior) {
		this.valorAnterior = valorAnterior;
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

}
