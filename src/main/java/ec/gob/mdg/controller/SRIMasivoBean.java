package ec.gob.mdg.controller;

import static ec.gob.mdg.sri.util.StringUtil.converBase64;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.Proxy;
import java.net.URL;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.w3c.dom.Document;

import ec.gob.mdg.model.Cliente;
import ec.gob.mdg.model.Comprobante;
import ec.gob.mdg.model.Mensaje;
import ec.gob.mdg.model.PuntoRecaudacion;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.service.IComprobanteService;
import ec.gob.mdg.service.IMensajeService;
import ec.gob.mdg.service.IUsuarioPuntoService;
import ec.gob.mdg.sri.SoapAutorizacion;
import ec.gob.mdg.sri.SoapRecepcion;
import ec.gob.mdg.sri.sign.XadesSign;
import ec.gob.mdg.sri.util.FileUtil;
import ec.gob.mdg.sri.util.XML_Utilidades;
import ec.gob.mdg.validaciones.FunValidaciones;

@Named
@ViewScoped
public class SRIMasivoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IComprobanteService serviceComprobante;

	@Inject
	private IUsuarioPuntoService serviceUsuPunto;
	private List<Comprobante> listaComprobante;

	double total = 0.0;

	@Inject
	private IMensajeService serviceMensaje;

	private Comprobante comprobante = new Comprobante();
	private Mensaje mensaje = new Mensaje();

	private UsuarioPunto usuPunto = new UsuarioPunto();
	Usuario p = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	String claveA;
	PuntoRecaudacion punto;
	boolean estadeshabilitado;
	boolean estadeshabilitadoA;
	boolean estadeshabilitadoEnv;
	boolean estadeshabilitadoAut;
	String MensajeSri;
	String MensajeSriError;

	public static XML_Utilidades xml_utilidades = new XML_Utilidades();

	@PostConstruct
	public void init() {
		usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(p);
		punto = usuPunto.getPuntoRecaudacion();
		listarComprobantes();

	}

	//// INICIALIZADOR DE VARIABLES
	public void inicializar() {
		comprobante = new Comprobante();
		comprobante.setCliente(new Cliente());
	}

	public void listarComprobantes() {
		try {
			this.listaComprobante = serviceComprobante
					.listarNoAutorizadasPorPunto(usuPunto.getPuntoRecaudacion().getId());
			total();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// SUMA TOTAL
	public void total() {
		total = 0.0;
		for (Comprobante c : listaComprobante) {
			total = total + c.getValor();
			total = FunValidaciones.formatearDecimales(total, 2);
		}

	}

	public void envioMasivo() throws Exception {
		for (Comprobante c : listaComprobante) {
			/// FIRMAR Y ENVIAR
			enviar(c.getId());
			estadeshabilitado = true;

		}
	}

	public void autorizacionMasivo() throws Exception {
		for (Comprobante c : listaComprobante) {
			/// AUTORIZAR
			autorizar(c.getId());
			estadeshabilitadoA = true;
		}
	}

	//////////////////////////////// SRI
	public String firmarDocumentoXmlXades(Comprobante c) throws FileNotFoundException, IOException {

		XadesSign x = new XadesSign();
		System.out.println("firma xml " + c.getXml());
		byte[] xmlSigned = x.firmarDocumentoXmlXades(c.getXml().getBytes());
		System.out.println("pasa firmando");
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
			System.out.println("entra a firmar " + comprobante.getNumero());

			// estadeshabilitadoEnv = false;
			SoapRecepcion n = new SoapRecepcion();

			//// AMBIENTE DE PRUEBAS
			String url = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";
			String host = "celcer.sri.gob.ec";

			//// AMBIENTE DE PRODUCCIoN
//				String url = "https://cel.sri.gob.ec/comprobantes-electronicos-ws/RecepcionComprobantesOffline?wsdl";
//				String host = "cel.sri.gob.ec";

			try {
				String xml64 = this.firmarDocumentoXmlXades(comprobante);
				System.out.println("2");
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

			/// AMBIENTE DE PRUEBAS
			String url = "https://celcer.sri.gob.ec/comprobantes-electronicos-ws/AutorizacionComprobantesOffline?wsdl";
			String host = "celcer.sri.gob.ec";

			/// AMBIENTE DE PRODUCCIoN
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

						///// ACTUALIZA INFORMACIóN DE AUTORIZACIN EN EL COMPROBANTE
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

	/////////////////////////////////////////////////////////////

	// NUEVO REGISTR0
	public void nuevo() {
		estadeshabilitadoEnv = true;
		estadeshabilitadoAut = true;
		estadeshabilitado = false;
		estadeshabilitadoA = false;
	}

/////// GETTERS Y SETTERS
	public List<Comprobante> getListaComprobante() {
		return listaComprobante;
	}

	public void setListaComprobante(List<Comprobante> listaComprobante) {
		this.listaComprobante = listaComprobante;
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

	public String getClaveA() {
		return claveA;
	}

	public void setClaveA(String claveA) {
		this.claveA = claveA;
	}

	public PuntoRecaudacion getPunto() {
		return punto;
	}

	public void setPunto(PuntoRecaudacion punto) {
		this.punto = punto;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public boolean isEstadeshabilitado() {
		return estadeshabilitado;
	}

	public void setEstadeshabilitado(boolean estadeshabilitado) {
		this.estadeshabilitado = estadeshabilitado;
	}

	public boolean isEstadeshabilitadoA() {
		return estadeshabilitadoA;
	}

	public void setEstadeshabilitadoA(boolean estadeshabilitadoA) {
		this.estadeshabilitadoA = estadeshabilitadoA;
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

	public static XML_Utilidades getXml_utilidades() {
		return xml_utilidades;
	}

	public static void setXml_utilidades(XML_Utilidades xml_utilidades) {
		SRIMasivoBean.xml_utilidades = xml_utilidades;
	}

}
