package ec.gob.mdg.controller;

import static ec.gob.mdg.sri.util.StringUtil.converBase64;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
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
import ec.gob.mdg.service.IComprobanteDetalleService;
import ec.gob.mdg.service.IComprobanteService;
import ec.gob.mdg.service.IInstitucionService;
import ec.gob.mdg.service.IMensajeService;
import ec.gob.mdg.service.IUsuarioPuntoService;
import ec.gob.mdg.sri.SoapAutorizacion;
import ec.gob.mdg.sri.SoapRecepcion;
import ec.gob.mdg.sri.sign.XadesSign;
import ec.gob.mdg.sri.util.FileUtil;
import ec.gob.mdg.sri.util.XML_Utilidades;
import ec.gob.mdg.util.UtilsArchivos;
import ec.gob.mdg.util.UtilsDate;
import ec.gob.mdg.util.ValorMod11;
import ec.gob.mdg.validaciones.Funciones;

@Named
@ViewScoped
public class ConsultarDetalleNotasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IInstitucionService institucionService;

	@Inject
	private IComprobanteService serviceComprobante;

	@Inject
	private IComprobanteDetalleService serviceComprobanteDet;

	@Inject
	private GenerarDOMNotaBean genXml;

	@Inject
	private IAuditoriaService serviceAuditoria;

	@Inject
	private Funciones fun;
	@Inject
	private IUsuarioPuntoService serviceUsuPunto;
	@Inject
	private IMensajeService serviceMensaje;

	private Comprobante comprobanteNotas;
	private Comprobante comprobante;
	private Mensaje mensaje;
	private ComprobanteDetalle comprobanteDet;
	private Cliente cliente = new Cliente();
	private Institucion institucion;

	private List<Comprobante> listaComprobante;
	private List<ComprobanteDetalle> listaComprobanteDet;
	private List<RecaudacionDetalle> listaRecaudacionDetalle;
	private UsuarioPunto usuPunto = new UsuarioPunto();

	Usuario p = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	Integer id = 0;
	String param2;
	String num_factura;
	boolean estadeshabilitado = false;
	double totaldet = 0.0;
	double totaldep = 0.0;
	private String tipoDialog;
	Integer inst;
	PuntoRecaudacion punto;
	PuntoRecaudacion nombre;
	String claveA;
	double subtotaldet = 0.0;
	boolean estadeshabilitadoA = false;
	Date fechaActual;
	String autorizacion;
	boolean estadeshabilitadoEnv;
	boolean estadeshabilitadoAut;
	String MensajeSri;
	String MensajeSriError;

	@PostConstruct
	public void init() {
		try {
			fechaActual = UtilsDate.fechaActual();
			usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(p);
			punto = usuPunto.getPuntoRecaudacion();
			mostrarComprobanteNota();
			totalDetalle();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getParam() {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
	}

	// Listar informacin de la cabecera del comprobante: Notas
	public void mostrarComprobanteNota() {
		param2 = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
		id = Integer.parseInt(param2);
		comprobanteNotas = serviceComprobante.listarComprobanteNotaPorId(id);
		listaComprobanteDet = this.serviceComprobanteDet.listarComDetPorIdComp(id);

		totalDetalle();
	}

	// SUMAR TOTAL DETALLE
	public void totalDetalle() {
		totaldet = 0.00;
		for (ComprobanteDetalle det : listaComprobanteDet) {
			totaldet = totaldet + (det.getCantidad() * det.getValorcero()) + (det.getCantidad() * det.getValoriva());
		}
	}

	// VALIDA QUE LA FACTURA NO SEA DIFERENTE DE LA AUTORIZACIoN
	public void validaFacturaAutorizacion() {

		if (comprobanteNotas.getAutorizacion() != null) {

			String facturaS = comprobanteNotas.getAutorizacion().substring(30, 39);
			Integer factura = Integer.parseInt(facturaS);

			if (comprobanteNotas.getNumero().equals(factura)) {
//				System.out.println("entra a iguales");
				estadeshabilitado = true;
				estadeshabilitadoA = true;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"No puede borrar la Información del SRI, se encuentra autorizado el comprobante", "Error"));
			} else if (!comprobanteNotas.getNumero().equals(factura)) {

				try {
					fun.borraSRI(comprobanteNotas.getId());
					// CAMPO comprobante.getAutorizacion)
					serviceAuditoria.insertaModificacion("ComprobanteNotas", "autorizacion", "D",
							usuPunto.getUsuario().getUsername(), fechaActual, autorizacion,
							comprobanteNotas.getAutorizacion(), comprobanteNotas.getId());

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

	@Transactional
	public void modificarComprobante() {
		try {
			if (comprobanteNotas.getAutorizacion() != null) {
				estadeshabilitado = true;
				estadeshabilitadoA = true;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"No puede realizar cambios en el comprobante ya está autorizado por el SRI", "Error"));
			} else {

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
						+ StringUtils.leftPad(String.valueOf(comprobanteNotas.getNumero()), 9, "0") + "12345678" + "1";

				String verificador = String.valueOf(ValorMod11.mod11(claveA));

				claveA = claveA + verificador;

				comprobanteNotas.setClaveacceso(claveA);

				serviceComprobante.modificar(comprobanteNotas);
				/// GENERAR XML PARA LA FACTURA

				genXml.generarXmlArchivo(comprobanteNotas.getPuntoRecaudacion().getId(), comprobanteNotas.getNumero());

				estadeshabilitado = true;
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Se generó con éxito el XML", "Aviso"));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

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
		comprobanteNotas = serviceComprobante.listarComprobantePorId(id_comprobante);

		if (comprobanteNotas.getAutorizacion() != null) {
			estadeshabilitadoEnv = true;
			estadeshabilitado = true;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"No puede realizar cambios el comprobante se encuentra autorizado por el SRI", "Error"));
		} else {
//			System.out.println("entra a firmar " + comprobanteNotas.getNumero());

			// estadeshabilitadoEnv = false;
			SoapRecepcion n = new SoapRecepcion();

			//// AMBIENTE DE PRUEBAS
			String url = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";
			String host = "celcer.sri.gob.ec";

			//// AMBIENTE DE PRODUCCIoN
			// String url =
			//// "https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";
			// String host = "cel.sri.gob.ec";

			try {
				String xml64 = this.firmarDocumentoXmlXades(comprobanteNotas);
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

							comprobanteNotas.setEstadosri("E");
							comprobanteNotas.setEstadoerror("S");
							serviceComprobante.modificar(comprobanteNotas);
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
							this.comprobanteNotas.setEstadosri("R");
							this.comprobanteNotas.setEstadoerror("C");
							serviceComprobante.modificar(this.comprobanteNotas);

							String identificador = xml_utilidades.getNodes("mensaje", "identificador", doc);
							String mensaje = xml_utilidades.getNodes("mensaje", "mensaje", doc);
							String tipo = xml_utilidades.getNodes("mensaje", "tipo", doc);
							this.mensaje.setId_comprobante(this.comprobanteNotas.getId());
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
		comprobanteNotas = serviceComprobante.listarComprobantePorId(id_comprobante);

		if (comprobanteNotas.getAutorizacion() != null) {
			estadeshabilitadoAut = true;
			estadeshabilitadoA = true;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"No puede realizar cambios el comprobante se encuentra autorizado por el SRI", "Error"));
		} else {
			// estadeshabilitadoA = false;
			SoapAutorizacion n = new SoapAutorizacion();

			/// AMBIENTE DE PRUEBAS
			String url = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl";
			String host = "celcer.sri.gob.ec";

			/// AMBIENTE DE PRODUCCIóN
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
				reqStreamOut.write(n.formatSendPost(comprobanteNotas.getClaveacceso()).getBytes());
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
				String estado = xml_utilidades.getNodes("RespuestaAutorizacionComprobante", "estado", doc);

				if (estado.equals("AUTORIZADO")) {
					try {
						String fa = xml_utilidades.getNodes("RespuestaAutorizacionComprobante", "fechaAutorizacion",
								doc);
						String na = xml_utilidades.getNodes("RespuestaAutorizacionComprobante", "numeroAutorizacion",
								doc);

						///// ACTUALIZA INFORMACIóN DE AUTORIZACIóN EN EL COMPROBANTE
						comprobanteNotas.setEstadosri("A");
						comprobanteNotas.setAutorizacion(na);
						comprobanteNotas.setAutorizacionfecha(fa);
						this.comprobanteNotas.setEstadoerror("S");
						comprobanteNotas.setXmlsri(sb.toString());
						serviceComprobante.modificar(comprobanteNotas);

						////////// FIN ACTUALIZACION

						FileUtil.writeSignedAuth(comprobanteNotas, sb.toString().getBytes());
						estadeshabilitadoAut = true; // PARA DESHABILITAR EL BOTON ENVIAR EN LA FACTURA
						estadeshabilitadoA = true;
						MensajeSri = "Autorización con Exito";
						MensajeSriError = "Sin errores";

					} catch (Exception e) {
						e.printStackTrace();
					}

				} else if (estado.equals("NO AUTORIZADO")) {

					this.comprobanteNotas.setEstadosri("Z");
					this.comprobanteNotas.setEstadoerror("C");
					serviceComprobante.modificar(this.comprobanteNotas);

					String identificador = xml_utilidades.getNodes("mensaje", "identificador", doc);
					String mensaje = xml_utilidades.getNodes("mensaje", "mensaje", doc);
					String informacionAdicional = xml_utilidades.getNodes("mensaje", "informacionAdicional", doc);
					String tipo = xml_utilidades.getNodes("mensaje", "tipo", doc);

////INGRESA A LA TABLA MENSAJES EL ERROR
					this.mensaje.setId_comprobante(this.comprobanteNotas.getId());
					this.mensaje.setIdentificador(identificador);
					this.mensaje.setMensaje(mensaje);
					this.mensaje.setInformacionAdicional(informacionAdicional);
					this.mensaje.setTipo(tipo);
					serviceMensaje.registrar(this.mensaje);

					MensajeSri = mensaje;
					MensajeSriError = informacionAdicional;
//					System.out.println("TERMINA AUTORIZAR EN MENSAJE");
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

	// GETTERS Y SETTERS

	public List<Comprobante> getListaComprobante() {
		return listaComprobante;
	}

	public void setListaComprobante(List<Comprobante> listaComprobante) {
		this.listaComprobante = listaComprobante;
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

	public ComprobanteDetalle getComprobanteDet() {
		return comprobanteDet;
	}

	public void setComprobanteDet(ComprobanteDetalle comprobanteDet) {
		this.comprobanteDet = comprobanteDet;
	}

	public List<ComprobanteDetalle> getListaComprobanteDet() {
		return listaComprobanteDet;
	}

	public void setListaComprobanteDet(List<ComprobanteDetalle> listaComprobanteDet) {
		this.listaComprobanteDet = listaComprobanteDet;
	}

	public String getNum_factura() {
		return num_factura;
	}

	public void setNum_factura(String num_factura) {
		this.num_factura = num_factura;
	}

	public Comprobante getComprobanteNotas() {
		return comprobanteNotas;
	}

	public void setComprobanteNotas(Comprobante comprobanteNotas) {
		this.comprobanteNotas = comprobanteNotas;
	}

	public boolean isEstadeshabilitado() {
		return estadeshabilitado;
	}

	public void setEstadeshabilitado(boolean estadeshabilitado) {
		this.estadeshabilitado = estadeshabilitado;
	}

	public double getTotaldet() {
		return totaldet;
	}

	public void setTotaldet(double totaldet) {
		this.totaldet = totaldet;
	}

	public double getTotaldep() {
		return totaldep;
	}

	public void setTotaldep(double totaldep) {
		this.totaldep = totaldep;
	}

	public String getTipoDialog() {
		return tipoDialog;
	}

	public void setTipoDialog(String tipoDialog) {
		this.tipoDialog = tipoDialog;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public List<RecaudacionDetalle> getListaRecaudacionDetalle() {
		return listaRecaudacionDetalle;
	}

	public void setListaRecaudacionDetalle(List<RecaudacionDetalle> listaRecaudacionDetalle) {
		this.listaRecaudacionDetalle = listaRecaudacionDetalle;
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

	public Integer getInst() {
		return inst;
	}

	public void setInst(Integer inst) {
		this.inst = inst;
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

	public String getClaveA() {
		return claveA;
	}

	public void setClaveA(String claveA) {
		this.claveA = claveA;
	}

	public double getSubtotaldet() {
		return subtotaldet;
	}

	public void setSubtotaldet(double subtotaldet) {
		this.subtotaldet = subtotaldet;
	}

	public Comprobante getComprobante() {
		return comprobante;
	}

	public void setComprobante(Comprobante comprobante) {
		this.comprobante = comprobante;
	}

	public Mensaje getMensaje() {
		return mensaje;
	}

	public void setMensaje(Mensaje mensaje) {
		this.mensaje = mensaje;
	}

	public boolean isEstadeshabilitadoA() {
		return estadeshabilitadoA;
	}

	public void setEstadeshabilitadoA(boolean estadeshabilitadoA) {
		this.estadeshabilitadoA = estadeshabilitadoA;
	}

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public String getAutorizacion() {
		return autorizacion;
	}

	public void setAutorizacion(String autorizacion) {
		this.autorizacion = autorizacion;
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

}
