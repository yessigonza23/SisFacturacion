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
import org.primefaces.model.UploadedFile;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.TimeZone;

import ec.gob.mdg.model.Cliente;
import ec.gob.mdg.model.Comprobante;
import ec.gob.mdg.model.ComprobanteDetalle;
import ec.gob.mdg.model.Institucion;
import ec.gob.mdg.model.PuntoRecaudacion;
import ec.gob.mdg.model.RecaudacionDetalle;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.service.IClienteService;
import ec.gob.mdg.service.IComprobanteDetalleService;
import ec.gob.mdg.service.IComprobanteService;
import ec.gob.mdg.service.IInstitucionService;
import ec.gob.mdg.service.IPuntoRecaudacionService;
import ec.gob.mdg.service.IRecaudacionDetalleService;
import ec.gob.mdg.service.IUsuarioPuntoService;
import ec.gob.mdg.util.UtilsArchivos;
import ec.gob.mdg.util.UtilsDate;
import ec.gob.mdg.util.ValorMod11;
import ec.gob.mdg.validaciones.Funciones;

@Named
@ViewScoped
public class IngresoNotasAutoBean implements Serializable {

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
	private IUsuarioPuntoService serviceUsuPunto;

	@Inject
	private IPuntoRecaudacionService servicePuntoRecaudacion;

	@Inject
	private GenerarDOMNotaBean genNotasXml;

	private Cliente cliente = new Cliente();
	private Comprobante comprobante = new Comprobante();
	private Comprobante comprobantetmp = new Comprobante();
	private ComprobanteDetalle comprobanteDetalle = new ComprobanteDetalle();
	private RecaudacionDetalle recaudacionDetalle;
	private Institucion institucion;

	private List<Cliente> listaClientes = new ArrayList<Cliente>();
	private List<Comprobante> listaComprobante = new ArrayList<Comprobante>();
	private List<ComprobanteDetalle> listaComprobanteDet = new ArrayList<ComprobanteDetalle>();
	private List<ComprobanteDetalle> listaComprobanteDetNot = new ArrayList<ComprobanteDetalle>();
	private List<RecaudacionDetalle> listaRecaudacionDetalle;

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
	Integer id_tmp = 0;
	private String tipoDialog;
	double subtotaldet = 0.0;
	boolean estadeshabilitado;
	String claveA;
	Integer inst;
	private UploadedFile file;
	double valorservicio = 0.0;
	Integer rd;
	Integer idcomprobante = 0;
	Integer idcomprobantedet = 0;
	Integer id_comprobante = 0;
	Integer id_punto = 0;

	@PostConstruct
	public void init() {
		cliente = new Cliente();
		try {
			el_cliente = "";
			listaClientes = serviceCliente.listar();
			listaRecaudacionDetalle = serviceRecaudacionDetalle.listar();
			fechaActual = UtilsDate.fechaActual();
			usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(p);
			punto = usuPunto.getPuntoRecaudacion();
			id_punto = punto.getId();
			nombre = servicePuntoRecaudacion.listarPorId(punto);
			num = 0;
			this.tipoDialog = "Nuevo";
			listarComprobanteRegional();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// SUMAR TOTAL DETALLE
	public void totalDetalle() {
		totaldet = 0.00;
		for (ComprobanteDetalle det : listaComprobanteDet) {
			totaldet = totaldet + det.getSubtotal();
		}
	}

	// ENCERAR DETALLES
	public void limpiarDialogo() {
		comprobanteDetalle = new ComprobanteDetalle();
	}

	// ELIMINAR DETALLE
	public void eliminarCompDetalle(int index) {
		listaComprobanteDet.remove(index);
		totalDetalle();
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

	public void limpiarControles() {
		this.comprobanteDetalle = new ComprobanteDetalle();
		this.tipoDialog = "Nuevo Detalle";
	}

	public void limpiarControlesFactura() {
		this.comprobante = new Comprobante();
		this.tipoDialog = "Nuevo Factura";
	}

	// BUSCAR FACTURA LIGADA A LA NOTA DE CREDITO PARA DESPLEGAR INFORMACIoN EN LA
	// NOTA DE CReDITO
	public void buscarFactura(Integer id_punto, Integer numero) {
		vaciarLista();
		try {
			this.comprobantetmp = serviceComprobante.comprobantePorPtoPorId(id_punto, numero);

			// LISTA DETALLE DE NOTA DE CREDITO A PARTIR DE LA INF DE LA FACTURA
			// SELECCIONADA
			this.listaComprobanteDetNot = serviceComprobanteDetalle.listarComDetPorPtoNumComp(id_punto, numero);
			totaldet = 0.0;

			for (ComprobanteDetalle det1 : listaComprobanteDetNot) {
				ComprobanteDetalle det = new ComprobanteDetalle();

				det.setCantidad(det1.getCantidad());
				det.setValorcero(det1.getValorcero());
				det.setValoriva(det1.getValoriva());
				det.setSubtotal(det1.getSubtotal());
				det.setRecaudaciondetalle(det1.getRecaudaciondetalle());
				det.setComprobante(comprobante);
				id_tmp++;
				det.setId_tmp(id_tmp);
				totaldet = totaldet + det1.getSubtotal();
				listaComprobanteDet.add(det);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/// VACIAR LA LISTA CUANDO CAMBIEN DE FACTURA
	public void vaciarLista() {
		listaComprobanteDet = new ArrayList<>();
	}

	//// LISTAR COMPROBANTES POR REGIONAL PARA LA NOTA DE CREDITO
	public void listarComprobanteRegional() {
		this.listaComprobante = serviceComprobante.listarCompPorIdPtoNotas(punto.getId());
	}

	////// REGISTRO DE NOTAS DE CREDITO DE MANERA AUTOMATICO
	@Transactional
	public void registrarNotaCreditoAut() {

		try {
			inst = punto.getInstitucion().getId();
			institucion = institucionService.institucionPorPunto(inst);
			usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(p);

			// saca el secuencial +1
			num1 = (Integer) fun.secNotas(usuPunto);
			Comprobante comp = new Comprobante();

			comp.setNumero(num1);
			comp.setClienteruc(comprobantetmp.getClienteruc());
			comp.setClientenombre(comprobantetmp.getClientenombre().toUpperCase());
			comp.setClientedireccion(comprobantetmp.getClientedireccion().toUpperCase());
			comp.setClientetelefono(comprobantetmp.getClientetelefono());
			comp.setValor(comprobante.getValor());
			fechaActual = UtilsDate.fechaActual();
			comp.setFechaemision(fechaActual);

			comp.setDetalle(comprobante.getDetalle());
			comp.setCliente(comprobantetmp.getCliente());
			comp.setUsuarioPunto(usuPunto);
			comp.setTipocomprobante("C");
			comp.setValor(totaldet);
			comp.setPuntoRecaudacion(punto);

			comp.setComprobantemod(comprobante.getComprobantemod());
			comp.setFechacomprmodificado(comprobantetmp.getFechaemision());
			comp.setMotivonotacredito(comprobante.getMotivonotacredito());
			comp.setComprobantepto(punto.getId());
			comp.setEstado("C");

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
					+ StringUtils.leftPad(String.valueOf(num1), 9, "0") + "12345678" + "1";

			String verificador = String.valueOf(ValorMod11.mod11(claveA));

			claveA = claveA + verificador;

			comp.setClaveacceso(claveA);

			////////////////////////////////////////////
			idcomprobante = serviceComprobante.registrar(comp);

			// actualiza la secuencia
			fun.actualizaSecuencialNotas(usuPunto, num1);
			try {
				for (ComprobanteDetalle det : listaComprobanteDet) {

					ComprobanteDetalle compdet = new ComprobanteDetalle();

					// det.setId(null);
					compdet.setCantidad(det.getCantidad());
					compdet.setValorcero(det.getValorcero());
					compdet.setValoriva(det.getValoriva());
					compdet.setComprobante(comp);
					compdet.setRecaudaciondetalle(det.getRecaudaciondetalle());
					compdet.setSubtotal(det.getSubtotal());
					compdet.setId_tmp(det.getId_tmp());

					idcomprobantedet = serviceComprobanteDetalle.registrar(compdet);
					comprobantetmp.setEstado("C");
					serviceComprobante.modificar(comprobantetmp);

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			comprobantetmp = serviceComprobante.listarComprobanteNotaPorId(idcomprobante);

			/// GENERAR XML PARA LA FACTURA
			genNotasXml.generarXmlArchivo(punto.getId(), num1);

			mostrarNota();
			estadeshabilitado = true;

			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "se grabó con éxito", "Aviso"));

		} catch (Exception e) {
			e.printStackTrace();
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

		} catch (Exception e) {
			System.out.println("Error " + e);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR: No se Envió por correo", "ERROR"));

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

	public Comprobante getComprobantetmp() {
		return comprobantetmp;
	}

	public void setComprobantetmp(Comprobante comprobantetmp) {
		this.comprobantetmp = comprobantetmp;
	}

	public Integer getIdcomprobante() {
		return idcomprobante;
	}

	public void setIdcomprobante(Integer idcomprobante) {
		this.idcomprobante = idcomprobante;
	}

	public Integer getIdcomprobantedet() {
		return idcomprobantedet;
	}

	public void setIdcomprobantedet(Integer idcomprobantedet) {
		this.idcomprobantedet = idcomprobantedet;
	}

	public Integer getId_comprobante() {
		return id_comprobante;
	}

	public void setId_comprobante(Integer id_comprobante) {
		this.id_comprobante = id_comprobante;
	}

	public Integer getId_punto() {
		return id_punto;
	}

	public void setId_punto(Integer id_punto) {
		this.id_punto = id_punto;
	}

}
