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
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.w3c.dom.Document;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.TimeZone;

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
import ec.gob.mdg.service.IComprobanteDepositosService;
import ec.gob.mdg.service.IComprobanteDetalleService;
import ec.gob.mdg.service.IComprobanteService;
import ec.gob.mdg.service.IMensajeService;
import ec.gob.mdg.service.IUsuarioPuntoService;
import ec.gob.mdg.sri.SoapAutorizacion;
import ec.gob.mdg.sri.SoapRecepcion;
import ec.gob.mdg.sri.sign.XadesSign;
import ec.gob.mdg.sri.util.FileUtil;
import ec.gob.mdg.sri.util.XML_Utilidades;
import ec.gob.mdg.util.UtilsDate;
import ec.gob.mdg.util.ValorMod11;
import ec.gob.mdg.validaciones.Funciones;

@Named
@ViewScoped
public class ConsultarDetalleFacturaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IComprobanteService serviceComprobante;

	@Inject
	private IComprobanteDetalleService serviceComprobanteDet;

	@Inject
	private IComprobanteDepositosService serviceComprobanteDep;

	@Inject
	private IAuditoriaService serviceAuditoria;

	@Inject
	private Funciones fun;

	@Inject
	private GenerarDOMBean genXml;

	@Inject
	private IUsuarioPuntoService serviceUsuPunto;

	@Inject
	private IMensajeService serviceMensaje;

	private Comprobante comprobante;
	private Comprobante comprobanteNotas;
	private ComprobanteDetalle comprobanteDet;
	private ComprobanteDepositos comprobanteDep;
	private Cliente cliente = new Cliente();
	private Institucion institucion;

	private List<Comprobante> listaComprobante;
	private List<ComprobanteDetalle> listaComprobanteDet;
	private List<ComprobanteDepositos> listaComprobanteDep;
	private List<RecaudacionDetalle> listaRecaudacionDetalle;
	private UsuarioPunto usuPunto = new UsuarioPunto();

	boolean estadeshabilitadoEnv;
	boolean estadeshabilitadoAut;

	private Mensaje mensaje = new Mensaje();
	String el_cliente = "";

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

	String ambiente;
	String url;
	String host;

	String mensaje_Sri;
	String mensaje_SriError;

	@PostConstruct
	public void init() {
		try {
			fechaActual = UtilsDate.fechaActual();
			usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(p);
			punto = usuPunto.getPuntoRecaudacion();
			mostrarComprobante();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getParam() {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
	}

	// Listar informacion de la cabecera del comprobante: Facturas
	public void mostrarComprobante() {
		param2 = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
		id = Integer.parseInt(param2);
		comprobante = serviceComprobante.listarComprobantePorId(id);
		listaComprobanteDet = this.serviceComprobanteDet.listarComDetPorIdComp(id);
		listaComprobanteDep = this.serviceComprobanteDep.listarComDepPorIdComp(id);
	}

	// Listar informacion de la cabecera del comprobante: Notas
	public void mostrarComprobanteNota() {
		param2 = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
		id = Integer.parseInt(param2);
		comprobanteNotas = serviceComprobante.listarComprobanteNotaPorId(id);
		listaComprobanteDet = this.serviceComprobanteDet.listarComDetPorIdComp(id);
	}

	// SUMAR TOTAL DETALLE
	public void totalDetalle() {
		totaldet = 0.00;
		for (ComprobanteDetalle det : listaComprobanteDet) {
			totaldet = totaldet + det.getSubtotal();
		}
	}

	// SUMAR TOTAL DEPOSITOS
	public void totalDeposito() {
		totaldep = 0.00;
		for (ComprobanteDepositos dep : listaComprobanteDep) {
			totaldep = totaldep + dep.getValor();
		}
	}

	// VALIDA QUE LA FACTURA NO SEA DIFERENTE DE LA AUTORIZACION
	public void validaFacturaAutorizacion() {
		if (comprobante.getAutorizacion() != null) {
			String facturaS = comprobante.getAutorizacion().substring(30, 39);
			Integer factura = Integer.parseInt(facturaS);

			if (comprobante.getNumero().equals(factura)) {
				System.out.println("entra a iguales: " + comprobante.getNumero() + " - " + factura);
				estadeshabilitado = true;
				estadeshabilitadoA = true;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"No puede borrar la Información del SRI, la autorización de la Factura", "Error"));
			} else if (!comprobante.getNumero().equals(factura)) {
				System.out.println("entra a diferentes " + comprobante.getId());
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

	@Transactional
	public void modificarComprobante() {
		try {

			if (comprobante.getAutorizacion() != null) {
				estadeshabilitado = true;
				estadeshabilitadoA = true;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"No puede realizar cambios la factura ya esta autorizada por el SRI", "Error"));

			} else if (comprobante.getAutorizacion() == null) {

				//// GENERAR CLAVE ACCESO
				Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
				calendar.setTime(comprobante.getFechaemision());
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
						+ StringUtils.leftPad(String.valueOf(comprobante.getNumero()), 9, "0") + "12345678" + "1";
				String verificador = String.valueOf(ValorMod11.mod11(claveA));
				claveA = claveA + verificador;
				comprobante.setClaveacceso(claveA);
				serviceComprobante.modificar(comprobante);
				
				genXml.generarXmlArchivo(comprobante.getPuntoRecaudacion().getId(), comprobante.getNumero());

				estadeshabilitado = true;
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Se generó con éxito el XML", "Aviso"));
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error - Diferencia de valores en detalle y deposito", "Error"));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

			try {
				String xml64 = this.firmarDocumentoXmlXades(comprobante);

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

							estadeshabilitadoEnv = true; // PARA DESHABILITAR EL BOTON ENVIAR EN LA FACTURA

							estadeshabilitadoAut = false;
							estadeshabilitado = true;

							mensaje_Sri = "Enviado con Exito";
							mensaje_SriError = "Sin errores";

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

							mensaje_Sri = mensaje;
							mensaje_SriError = mensaje;

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
//				String url = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl";
//				String host = "celcer.sri.gob.ec";

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

				java.io.BufferedReader rd = new java.io.BufferedReader(
						new java.io.InputStreamReader(con.getInputStream(), "UTF8"));
				String line = "";
				StringBuilder sb = new StringBuilder();
				while ((line = rd.readLine()) != null) {
					sb.append(line);
				}

				System.out.println(sb.toString());
				Document doc = xml_utilidades.convertStringToDocument(sb.toString());
//				SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
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
						mensaje_Sri = "Autorización con Exito";
						mensaje_SriError = "Sin errores";

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

					mensaje_Sri = mensaje;
					mensaje_SriError = informacionAdicional;
					System.out.println("TERMINA AUTORIZAR EN MENSAJE");
				}
				con.disconnect();
			} catch (Exception ex) {
				System.out.println(ex.getMessage());
			}
		}
	}

	// GETTERS Y SETTERS

	public Comprobante getComprobante() {
		return comprobante;
	}

	public void setComprobante(Comprobante comprobante) {
		this.comprobante = comprobante;
	}

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

	public ComprobanteDepositos getComprobanteDep() {
		return comprobanteDep;
	}

	public void setComprobanteDep(ComprobanteDepositos comprobanteDep) {
		this.comprobanteDep = comprobanteDep;
	}

	public List<ComprobanteDepositos> getListaComprobanteDep() {
		return listaComprobanteDep;
	}

	public void setListaComprobanteDep(List<ComprobanteDepositos> listaComprobanteDep) {
		this.listaComprobanteDep = listaComprobanteDep;
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

	public String getEl_cliente() {
		return el_cliente;
	}

	public void setEl_cliente(String el_cliente) {
		this.el_cliente = el_cliente;
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

	public String getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Mensaje getMensaje() {
		return mensaje;
	}

	public void setMensaje(Mensaje mensaje) {
		this.mensaje = mensaje;
	}

	public String getmensaje_Sri() {
		return mensaje_Sri;
	}

	public void setmensaje_Sri(String mensaje_Sri) {
		this.mensaje_Sri = mensaje_Sri;
	}

	public String getmensaje_SriError() {
		return mensaje_SriError;
	}

	public void setmensaje_SriError(String mensaje_SriError) {
		this.mensaje_SriError = mensaje_SriError;
	}

	public static XML_Utilidades getXml_utilidades() {
		return xml_utilidades;
	}

	public static void setXml_utilidades(XML_Utilidades xml_utilidades) {
		ConsultarDetalleFacturaBean.xml_utilidades = xml_utilidades;
	}

}
