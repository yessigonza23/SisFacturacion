package ec.gob.mdg.control.controller;

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

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.TimeZone;

import ec.gob.mdg.control.actualizaciones.DBcyf;
import ec.gob.mdg.control.actualizaciones.DBsanciones;
import ec.gob.mdg.control.model.Fin_Cal_Ren_DTO;
import ec.gob.mdg.control.model.Fin_Cat_Act_Sit_Veh_Java_DTO;
import ec.gob.mdg.control.model.Fin_Exportaciones_DTO;
import ec.gob.mdg.control.model.Fin_Guias_DTO;
import ec.gob.mdg.control.model.Fin_Importaciones_DTO;
import ec.gob.mdg.control.model.Fin_Importaciones_No_DTO;
import ec.gob.mdg.control.model.Fin_Reptecnicos_DTO;
import ec.gob.mdg.control.model.Sanciones_Admin_DTO;
import ec.gob.mdg.control.service.IFin_Cal_Ren_DTOService;
import ec.gob.mdg.control.service.IFin_Cat_Act_Sit_Veh_Java_DTOService;
import ec.gob.mdg.control.service.IFin_Exportaciones_DTOService;
import ec.gob.mdg.control.service.IFin_Guias_DTOService;
import ec.gob.mdg.control.service.IFin_Importaciones_DTOService;
import ec.gob.mdg.control.service.IFin_Importaciones_No_DTOService;
import ec.gob.mdg.control.service.IFin_Reptecnicos_DTOService;
import ec.gob.mdg.control.service.ISanciones_Admin_DTOService;
import ec.gob.mdg.controller.GenerarDOMBean;
import ec.gob.mdg.model.Cliente;
import ec.gob.mdg.model.Comprobante;
import ec.gob.mdg.model.ComprobanteDepositos;
import ec.gob.mdg.model.ComprobanteDetalle;
import ec.gob.mdg.model.Institucion;
import ec.gob.mdg.model.ParametroDetalle;
import ec.gob.mdg.model.PuntoRecaudacion;
import ec.gob.mdg.model.RecaudacionDetalle;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.service.IClienteService;
import ec.gob.mdg.service.IComprobanteDepositosService;
import ec.gob.mdg.service.IComprobanteDetalleService;
import ec.gob.mdg.service.IComprobanteService;
import ec.gob.mdg.service.IInstitucionService;
import ec.gob.mdg.service.IParametroDetalleService;
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
public class IngresoFacturaServiciosCyfBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IFin_Cat_Act_Sit_Veh_Java_DTOService serviceCambioCategoria;

	@Inject
	private IUsuarioPuntoService serviceUsuPunto;

	@Inject
	private IComprobanteService serviceComprobante;

	@Inject
	private IComprobanteDetalleService serviceComprobanteDetalle;

	@Inject
	private IComprobanteDepositosService serviceComprobanteDepositos;

	@Inject
	private Funciones fun;

	@Inject
	private IPuntoRecaudacionService servicePuntoRecaudacion;

	@Inject
	private IClienteService serviceCliente;

	@Inject
	private IRecaudacionDetalleService serviceRecaudacionDetalle;

	@Inject
	private GenerarDOMBean genXml;

	@Inject
	private IInstitucionService serviceInstitucion;

	@Inject
	private DBcyf dbcyf;

	@Inject
	private DBsanciones dbsanciones;

	@Inject
	private ISanciones_Admin_DTOService serviceSancionesAdmin;

	@Inject
	private IFin_Exportaciones_DTOService serviceExportacion;

	@Inject
	private IFin_Importaciones_DTOService serviceImportacion;

	@Inject
	private IFin_Importaciones_No_DTOService serviceImportacionNo;

	@Inject
	private IFin_Guias_DTOService serviceGuia;

	@Inject
	private IFin_Cal_Ren_DTOService serviceCalRen;

	@Inject
	private IParametroDetalleService serviceParametroDetalle;

	@Inject
	private IFin_Reptecnicos_DTOService serviceRepTecnicos;

	@Inject
	private IInstitucionService institucionService;

	private UsuarioPunto usuPunto = new UsuarioPunto();
	private Cliente cliente = new Cliente();
	private PuntoRecaudacion punto;
	private PuntoRecaudacion nombrePunto;
	private Institucion institucion;
	private RecaudacionDetalle recaudacionDetalle;
	private Comprobante comprobante = new Comprobante();
	private ComprobanteDetalle comprobanteDetalle;
	private ComprobanteDepositos comprobanteDepositos = new ComprobanteDepositos();

	private List<ComprobanteDetalle> listaComprobanteDetalle = new ArrayList<>();
	private List<ComprobanteDepositos> listaComprobanteDepositos = new ArrayList<>();

	private List<Fin_Cat_Act_Sit_Veh_Java_DTO> listaCambioCategoria;
	private Fin_Cat_Act_Sit_Veh_Java_DTO datosEntidad;

	private List<Sanciones_Admin_DTO> listaSanciones;
	private List<Sanciones_Admin_DTO> listaSancionesRec;
	private Sanciones_Admin_DTO datosEntidadSanciones;

	private List<Fin_Exportaciones_DTO> listaExportaciones;
	private List<Fin_Exportaciones_DTO> listaExportacionesRec;
	private Fin_Exportaciones_DTO datosEntidadExportaciones;

	private List<Fin_Importaciones_DTO> listaImportaciones = new ArrayList<>();
	private List<Fin_Importaciones_DTO> listaImportacionesRec = new ArrayList<>();
	private Fin_Importaciones_DTO datosEntidadImportaciones;

	private List<Fin_Importaciones_No_DTO> listaImportacionesNo = new ArrayList<>();
	private List<Fin_Importaciones_No_DTO> listaImportacionesNoRec = new ArrayList<>();
	private Fin_Importaciones_No_DTO datosEntidadImportacionesNo;

	private List<Fin_Guias_DTO> listaGuias = new ArrayList<>();
	private List<Fin_Guias_DTO> listaGuiasRec = new ArrayList<>();
	private Fin_Guias_DTO datosEntidadGuias;

	private List<Fin_Cal_Ren_DTO> listaCalRen;
	private Fin_Cal_Ren_DTO CalRenDto;

	private ParametroDetalle parametroDetalle;

	private List<Fin_Reptecnicos_DTO> listaCalRenRepTecnicos;
	private List<Fin_Reptecnicos_DTO> listaCalRenRepTecnicosRec;
	private Fin_Reptecnicos_DTO datosRepTecnico;

	Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");

	Date fechaActual;
	String tipo = null;
	Integer num = 0;
	Integer num1 = 0;
	String claveA;
	Integer id_comprobante = 0;
	String param2;
	String paramtipo2;
	String paramnumerojuicio2;
	String pnum_solicitud;
	String paramcodigoentidad2;
	Integer codigo;
	double total = 0.00;
	double totaldep = 0.00;
	double totaldet = 0.00;
	double subtotaldet = 0.00;
	Integer inst;
	boolean validador;
	boolean estadeshabilitado;
	String detalle = "Pago por ";
	Integer codentidad;
	String numsolicitud;
	String ruc;
	String nombre;
	String tipoIdentificacion;
	Integer id_tmp = 0;
	Integer idPunto;
	String valorParametroAnual;
	double valorRMU = 0.0;
	Integer idtec;
	Integer contador = 0;
	boolean validaFecha;

	@PostConstruct
	public void init() {
		try {
			fechaActual = UtilsDate.fechaActual();
			usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(us);
			punto = usuPunto.getPuntoRecaudacion();
			idPunto = usuPunto.getPuntoRecaudacion().getId();
			nombrePunto = servicePuntoRecaudacion.listarPorId(punto);
			inst = punto.getInstitucion().getId();
			institucion = serviceInstitucion.institucionPorPunto(inst);
			num = 0;
			listarServicios();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// PARAMETRO DEL CODIGO DE LA ENTIDAD
	public String getParam() {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
	}

	// PARAMETRO DEL TIPO DE SERVICIO
	public String getParamtipo() {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("paramtipo");
	}

	// PARAMETRO CODIGO DE EMPRESA DE SANCIONES
	public String getParamcodigoentidad() {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("paramcodigoentidad");
	}

	// EJECUTA LA LISTA SEGUN EL SERVICIO
	public void listarServicios() {
		// PARAMETRO PARA LISTAR TIPO DE SERVICIO
		paramtipo2 = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("paramtipo");
		if (paramtipo2 == "C") {
			param2 = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
			// PARAMETRO PARA CODIGO DE LA ENTDIDAD
			codigo = Integer.parseInt(param2);
			listarServiciosCambioCategoria();
		} else if (paramtipo2 == "S") {
			listarServiciosSancionesAdministrativas();
		} else if (paramtipo2 == "E") {
			param2 = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
			// PARAMETRO PARA CODIGO DE LA ENTDIDAD
			codigo = Integer.parseInt(param2);
			listarServiciosExportaciones();
		} else if (paramtipo2 == "I") {
			param2 = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
			// PARAMETRO PARA CODIGO DE LA ENTDIDAD
			codigo = Integer.parseInt(param2);
			listarServiciosImportaciones();
		} else if (paramtipo2 == "N") {
			param2 = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
			listarServiciosImportacionesNo();
		} else if (paramtipo2 == "G") {
			param2 = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
			// PARAMETRO PARA CODIGO DE LA ENTDIDAD
			codigo = Integer.parseInt(param2);
			listarServiciosGuias();
		} else if (paramtipo2 == "T") {
			param2 = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("paramidtec");
			// PARAMETRO PARA CODIGO DE LA ENTDIDAD
			idtec = Integer.parseInt(param2);
			listarServicioRepTecnicos();
		} else if (paramtipo2 == "R") {
			param2 = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
			// PARAMETRO PARA CODIGO DE LA ENTDIDAD
			codigo = Integer.parseInt(param2);

			listarServiciosCalRen();
		}
	}

	/// LISTAR SERVICIOS DE ENTIDAD SELECCIONADA PARA AMPLIACIONES DE CUPO
	public void listarServiciosCambioCategoria() {
		param2 = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
		codigo = Integer.parseInt(param2);
		tipoIdentificacion = "R";
		this.datosEntidad = serviceCambioCategoria.mostrarEntidad(codigo);
		ruc = datosEntidad.getRuc();
		nombre = datosEntidad.getNombre();
		tipo = "R";
		this.listaCambioCategoria = serviceCambioCategoria.listarServiciosPorCodigo(codigo);
		for (Fin_Cat_Act_Sit_Veh_Java_DTO f : listaCambioCategoria) {
			total = total + f.getValor();
		}
		Long a;
		try {
			a = fun.buscarCliente(datosEntidad.getRuc());
			if (a != 0) {
				this.cliente = serviceCliente.ClientePorCiParametro(datosEntidad.getRuc());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		detalle = detalle + "Cambio de categoría";
		for (Fin_Cat_Act_Sit_Veh_Java_DTO f : listaCambioCategoria) {
			ComprobanteDetalle det = new ComprobanteDetalle();
			this.recaudacionDetalle = serviceRecaudacionDetalle
					.mostrarRecaudacionDetallePorCodigo(f.getCodigo_recaudacion());
			det.setValorcero(f.getValor());
			det.setValoriva(0.00);
			det.setCantidad(f.getCantidad());
			double valorcero = f.getValor();
			double valor = valorcero * f.getCantidad();
			valor = Math.round(valor * 100.0) / 100.0;
			det.setSubtotal(valor);
			det.setComprobante(comprobante);
			det.setRecaudaciondetalle(recaudacionDetalle);
			listaComprobanteDetalle.add(det);
		}
		totalDetalle();
	}

	/// LISTAR SERVICIOS DE ENTIDAD SELECCIONADA PARA CALIFICACIONES RENOVACIONES
	public void listarServiciosCalRen() {
		param2 = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
		codigo = Integer.parseInt(param2);
		this.CalRenDto = serviceCalRen.mostrarEntidad(codigo);

		ruc = CalRenDto.getRuc();
		nombre = CalRenDto.getNombre();
		tipoIdentificacion = "R";
		this.listaCalRen = serviceCalRen.listarServiciosPorCodigo(codigo);
		Long a;
		try {
			a = fun.buscarCliente(CalRenDto.getRuc());
			if (a != 0) {
				this.cliente = serviceCliente.ClientePorCiParametro(CalRenDto.getRuc());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		detalle = detalle + "Calificacion/Renovacion";
		for (Fin_Cal_Ren_DTO f : listaCalRen) {
			ComprobanteDetalle det = new ComprobanteDetalle();

			this.recaudacionDetalle = serviceRecaudacionDetalle
					.mostrarRecaudacionDetallePorCodigo(f.getCodigo_recaudacion());
			parametroDetalle = serviceParametroDetalle.mostrarValorDescripcion("RMU");

			valorParametroAnual = parametroDetalle.getValor();
			valorRMU = Double.parseDouble(valorParametroAnual);
			double valorcero = recaudacionDetalle.getFactor() * valorRMU;
			valorcero = FunValidaciones.formatearDecimales(valorcero, 2);

			double valor = valorcero * f.getCantidad();
			valor = Math.round(valor * 100.0) / 100.0;

			det.setValorcero(valorcero);
			det.setValoriva(0.00);
			det.setCantidad(f.getCantidad());

			det.setSubtotal(valor);
			det.setComprobante(comprobante);
			det.setRecaudaciondetalle(recaudacionDetalle);
			listaComprobanteDetalle.add(det);
		}
		totalDetalle();
	}

	/// LISTAR SERVICIOS DE ENTIDAD SELECCIONADA SANCIONES ADMINISTRATIVAS
	public void listarServiciosSancionesAdministrativas() {
		ruc = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
		this.datosEntidadSanciones = serviceSancionesAdmin.mostrarEntidad(ruc);
		nombre = datosEntidadSanciones.getNombre();
		tipoIdentificacion = "R";

		this.listaSanciones = this.serviceSancionesAdmin.listarSancionesPorCodigoFact(ruc, idPunto);
		this.listaSancionesRec = this.serviceSancionesAdmin.listarSancionesPorCodigoFactRec(ruc, idPunto);
		for (Sanciones_Admin_DTO f : listaSancionesRec) {
			total = total + (f.getValor() * f.getCantidad());
		}
		Long a;
		try {
			a = fun.buscarCliente(datosEntidadSanciones.getRuc());
			if (a != 0) {
				this.cliente = serviceCliente.ClientePorCiParametro(datosEntidadSanciones.getRuc());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		detalle = detalle + "del (de los) juicio(s): ";

		for (Sanciones_Admin_DTO f : listaSanciones) {
			detalle = detalle + f.getNumero_juicio() + ", ";
		}
		;
		for (Sanciones_Admin_DTO f : listaSancionesRec) {
			ComprobanteDetalle det = new ComprobanteDetalle();
			this.recaudacionDetalle = serviceRecaudacionDetalle
					.mostrarRecaudacionDetallePorCodigo(f.getCodigo_recaudacion());
			det.setValorcero(f.getValor());
			det.setValoriva(0.00);
			det.setCantidad(f.getCantidad());
			double valorcero = f.getValor();
			double valor = valorcero * f.getCantidad();
			valor = Math.round(valor * 100.0) / 100.0;
			det.setSubtotal(valor);
			det.setComprobante(comprobante);
			det.setRecaudaciondetalle(recaudacionDetalle);
			listaComprobanteDetalle.add(det);
		}
		totalDetalle();
	}

	/// LISTAR SERVICIOS DE LICENCIAS DE EXPORTACION
	public void listarServiciosExportaciones() {

		this.datosEntidadExportaciones = serviceExportacion.mostarEntidadExportacion(codigo);
		ruc = datosEntidadExportaciones.getRuc();
		nombre = datosEntidadExportaciones.getNom_entidad();
		tipoIdentificacion = "R";

		this.listaExportaciones = this.serviceExportacion.listarExportacionesPorCodigoFact(codigo, idPunto);
		this.listaExportacionesRec = this.serviceExportacion.listarExportacionesPorCodigoRec(codigo, idPunto);
		for (Fin_Exportaciones_DTO f : listaExportacionesRec) {
			total = total + (f.getValor_pago() * f.getCantidad());
		}
		Long a;
		try {
			a = fun.buscarCliente(datosEntidadExportaciones.getRuc());
			if (a != 0) {
				this.cliente = serviceCliente.ClientePorCiParametro(datosEntidadExportaciones.getRuc());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}

		detalle = detalle + "de las licencias de exportaciones: ";

		for (Fin_Exportaciones_DTO f : listaExportaciones) {
			detalle = detalle + f.getNum_solicitud() + ", ";
		}
		;
		for (Fin_Exportaciones_DTO f : listaExportacionesRec) {
			ComprobanteDetalle det = new ComprobanteDetalle();
			this.recaudacionDetalle = serviceRecaudacionDetalle.mostrarRecaudacionDetallePorCodigo(f.getRecaudacion());

			det.setValorcero(f.getValor_pago());
			det.setValoriva(0.00);
			det.setCantidad(f.getCantidad());
			double valorcero = f.getValor_pago();
			double valor = valorcero * f.getCantidad();
			valor = Math.round(valor * 100.0) / 100.0;
			det.setSubtotal(valor);
			det.setComprobante(comprobante);
			det.setRecaudaciondetalle(recaudacionDetalle);
			listaComprobanteDetalle.add(det);
		}
		totalDetalle();
	}

	/// LISTAR SERVICIOS DE LICENCIAS DE IMPORTACIONES
	public void listarServiciosImportaciones() {
		this.datosEntidadImportaciones = serviceImportacion.mostrarEntidadImportacion(codigo);
		ruc = datosEntidadImportaciones.getRuc();
		nombre = datosEntidadImportaciones.getNom_entidad();
		tipoIdentificacion = "R";
		this.listaImportaciones = this.serviceImportacion.listarImportacionesPorCodigoFact(codigo, idPunto);

		this.listaImportacionesRec = this.serviceImportacion.listarImportacionesPorCodigoRec(codigo, idPunto);

		for (Fin_Importaciones_DTO f : listaImportacionesRec) {
			total = total + (f.getValor_pago() * f.getCantidad());
		}
		Long a;
		try {
			a = fun.buscarCliente(datosEntidadImportaciones.getRuc());
			if (a != 0) {
				this.cliente = serviceCliente.ClientePorCiParametro(datosEntidadImportaciones.getRuc());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		detalle = detalle + "de las licencias de importaciones: ";
		for (Fin_Importaciones_DTO f : listaImportaciones) {
			detalle = detalle + f.getNum_solicitud() + ", ";
		}
		;

		for (Fin_Importaciones_DTO f : listaImportacionesRec) {
			ComprobanteDetalle det = new ComprobanteDetalle();
			id_tmp++;
			double valorcero = f.getValor_pago();
			this.recaudacionDetalle = serviceRecaudacionDetalle.mostrarRecaudacionDetallePorCodigo(f.getRecaudacion());
			det.setId(null);
			det.setValorcero(valorcero);
			det.setValoriva(0.00);
			det.setCantidad(f.getCantidad());
			double valor = valorcero * f.getCantidad();
			valor = Math.round(valor * 100.0) / 100.0;
			det.setSubtotal(valor);
			det.setComprobante(comprobante);
			det.setRecaudaciondetalle(recaudacionDetalle);
			det.setId_tmp(id_tmp);
			listaComprobanteDetalle.add(det);

//			for (ComprobanteDetalle c : listaComprobanteDetalle) {
//				System.out.println(
//						"valor dentro del for:" + c.getValorcero() + " " + c.getRecaudaciondetalle().getCodigo());
//			}
		}

		totalDetalle();
	}

	/// LISTAR SERVICIOS DE LICENCIAS DE IMPORTACIONES NO CONTROLADOS
	public void listarServiciosImportacionesNo() {

		this.datosEntidadImportacionesNo = serviceImportacionNo.mostrarEntidadImportacionNo(param2);
		ruc = datosEntidadImportacionesNo.getRuc();
		nombre = datosEntidadImportacionesNo.getNom_entidad();
		tipoIdentificacion = "R";
		this.listaImportacionesNo = this.serviceImportacionNo.listarImportacionesNoPorCodigoFact(param2, idPunto);
		this.listaImportacionesNoRec = this.serviceImportacionNo.listarImportacionesNoPorCodigoRec(param2, idPunto);
		for (Fin_Importaciones_No_DTO f : listaImportacionesNoRec) {
			total = total + (f.getValor_pago() * f.getCantidad());
		}
		Long a;
		try {
			a = fun.buscarCliente(datosEntidadImportacionesNo.getRuc());
			if (a != 0) {
				this.cliente = serviceCliente.ClientePorCiParametro(datosEntidadImportacionesNo.getRuc());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		detalle = detalle + "de las licencias de certificados: ";
		for (Fin_Importaciones_No_DTO f : listaImportacionesNo) {
			detalle = detalle + f.getNum_solicitud() + ", ";
		}
		;

		parametroDetalle = serviceParametroDetalle.mostrarValorDescripcion("RMU");

		for (Fin_Importaciones_No_DTO f : listaImportacionesNoRec) {

			ComprobanteDetalle det = new ComprobanteDetalle();
			this.recaudacionDetalle = serviceRecaudacionDetalle.mostrarRecaudacionDetallePorCodigo(f.getRecaudacion());
			id_tmp++;
			valorParametroAnual = parametroDetalle.getValor();

			valorRMU = Double.parseDouble(valorParametroAnual);

			double valorcero = recaudacionDetalle.getFactor() * valorRMU;
			valorcero = FunValidaciones.formatearDecimales(valorcero, 2);

			det.setId(null);
			det.setValorcero(valorcero);
			det.setValoriva(0.00);
			det.setCantidad(f.getCantidad());
			double valor = valorcero * f.getCantidad();
			valor = Math.round(valor * 100.0) / 100.0;
			det.setSubtotal(valor);
			det.setComprobante(comprobante);
			det.setRecaudaciondetalle(recaudacionDetalle);
			det.setId_tmp(id_tmp);
			listaComprobanteDetalle.add(det);
		}

		totalDetalle();
//		System.out.println("3 Imp no");
	}

	/// LISTAR SERVICIOS DE GUIAS
	public void listarServiciosGuias() {
		this.datosEntidadGuias = serviceGuia.mostrarEntidadGuias(codigo);
		ruc = datosEntidadGuias.getRuc();
		nombre = datosEntidadGuias.getNom_entidad();
		tipoIdentificacion = "R";
		this.listaGuias = this.serviceGuia.listarGuiasPorCodigoFact(codigo, idPunto);
		this.listaGuiasRec = this.serviceGuia.listarGuiasPorCodigoRec(codigo, idPunto);

		for (Fin_Guias_DTO f : listaGuiasRec) {
			total = total + (f.getValor_pago() * f.getCantidad());
		}
		Long a;
		try {
			a = fun.buscarCliente(datosEntidadGuias.getRuc());
			if (a != 0) {
				this.cliente = serviceCliente.ClientePorCiParametro(datosEntidadGuias.getRuc());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		detalle = detalle + "de las guias: ";
		for (Fin_Guias_DTO f : listaGuias) {
			detalle = detalle + f.getNum_solicitud() + ", ";
		}
		;

		for (Fin_Guias_DTO f : listaGuiasRec) {
			ComprobanteDetalle det = new ComprobanteDetalle();
			id_tmp++;
			double valorcero = f.getValor_pago();
			this.recaudacionDetalle = serviceRecaudacionDetalle.mostrarRecaudacionDetallePorCodigo(f.getRecaudacion());
			det.setId(null);
			det.setValorcero(valorcero);
			det.setValoriva(0.00);
			det.setCantidad(f.getCantidad());
			double valor = valorcero * f.getCantidad();
			valor = Math.round(valor * 100.0) / 100.0;
			det.setSubtotal(valor);
			det.setComprobante(comprobante);
			det.setRecaudaciondetalle(recaudacionDetalle);
			det.setId_tmp(id_tmp);
			listaComprobanteDetalle.add(det);
		}

		totalDetalle();
	}

	/// LISTAR SERVICIOS DE REPRESENTANTES TECNICOS
	public void listarServicioRepTecnicos() {
		param2 = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("paramidtec");
		idtec = Integer.parseInt(param2);
		this.datosRepTecnico = serviceRepTecnicos.mostarRepTecnico(idtec);
		ruc = datosRepTecnico.getCedulatec();
		nombre = datosRepTecnico.getNombrestec();
		tipoIdentificacion = "C";
		this.listaCalRenRepTecnicos = this.serviceRepTecnicos.listarCalRenRepTecnicoPorIdTecFact(idtec, idPunto);
		this.listaCalRenRepTecnicosRec = this.serviceRepTecnicos.listarCalRenRepTecnicoPorIdTecRec(idtec, idPunto);
		for (Fin_Reptecnicos_DTO f : listaCalRenRepTecnicosRec) {
			this.recaudacionDetalle = serviceRecaudacionDetalle.mostrarRecaudacionDetallePorCodigo(f.getRecaudacion());
			total = total + (recaudacionDetalle.getValor() * f.getCantidad());
		}
		Long a;
		try {
			a = fun.buscarCliente(datosRepTecnico.getCedulatec());
			if (a != 0) {
				this.cliente = serviceCliente.ClientePorCiParametro(datosRepTecnico.getCedulatec());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.getMessage();
		}
		detalle = "Representante tecnico nombre: " + nombre + ", cedula: " + ruc;
		for (Fin_Reptecnicos_DTO f : listaCalRenRepTecnicos) {
			detalle = detalle + f.getCodigo_calren() + ", ";
		}
		;
		for (Fin_Reptecnicos_DTO f : listaCalRenRepTecnicosRec) {
			ComprobanteDetalle det = new ComprobanteDetalle();
			this.recaudacionDetalle = serviceRecaudacionDetalle.mostrarRecaudacionDetallePorCodigo(f.getRecaudacion());
			det.setValorcero(recaudacionDetalle.getValor());
			det.setValoriva(0.00);
			det.setCantidad(f.getCantidad());
			double valorcero = recaudacionDetalle.getValor();
			double valor = valorcero * f.getCantidad();
			valor = Math.round(valor * 100.0) / 100.0;
			det.setSubtotal(valor);
			det.setComprobante(comprobante);
			det.setRecaudaciondetalle(recaudacionDetalle);
			listaComprobanteDetalle.add(det);
		}
		totalDetalle();
	}

	public void totalDetalle() {
		totaldet = 0.00;
		for (ComprobanteDetalle det : listaComprobanteDetalle) {
			totaldet = totaldet + det.getSubtotal();
			totaldet = FunValidaciones.formatearDecimales(totaldet, 2);
		}
	}

	public void buscarClientes() {/// BUSCA EL CLIENTE PARA LLENAR LOS DATOS DEL CLIENTE
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
		if (this.cliente.getCi() == null) {
			cliente.setCi(ruc);
		}
		String validaIdentificacion = CedulaRuc.comprobacion(this.cliente.getCi(), this.cliente.getTipoid());
		if (validaIdentificacion.equals("T")) {
			validador = false;
		} else {
			validador = true;
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Tipo de identificacion erronea", validaIdentificacion));
		}
	}

	/// CARGAR LOS DATOS DEL DEPOSITO
	public void registrarCompDeposito() {
		validaFechaActual(comprobanteDepositos.getFecha());
		ComprobanteDepositos dep = new ComprobanteDepositos();
		dep.setComprobante(comprobante);
		dep.setNum_deposito(comprobanteDepositos.getNum_deposito());
		dep.setIdentificacion(comprobanteDepositos.getIdentificacion());
		dep.setFecha(comprobanteDepositos.getFecha());
		dep.setValor(comprobanteDepositos.getValor());
		dep.setTipotransaccion(comprobanteDepositos.getTipotransaccion());
		dep.setOrigen(comprobanteDepositos.getOrigen());
		listaComprobanteDepositos.add(dep);
		totalDeposito();
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

	// SUMAR TOTAL DEPOSITOS
	public void totalDeposito() {
		totaldep = 0.00;
		for (ComprobanteDepositos dep : listaComprobanteDepositos) {
			totaldep = totaldep + dep.getValor();
			totaldep = FunValidaciones.formatearDecimales(totaldep, 2);
		}
	}

	// LIMPIAR DIALOGO DEPOSITOS
	public void limpiarDialogoDep() {
		comprobanteDepositos = new ComprobanteDepositos();
	}

	// ELIMINAR DEPOSITO
	public void eliminarCompDeposito(int index) {
		listaComprobanteDepositos.remove(index);
		totalDeposito();
	}

	// GUARDAR COMPROBANTE
	@Transactional
	public void registrarComprobante() {
		try {
			contador = (Integer) serviceComprobante.validaCierre(usuPunto);
			if (contador > 0) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error, debe realizar el cierre del dia anterior, no podrá facturar", "Error"));
			} else {
				totalDetalle();
				totalDeposito();

				if (totaldet != totaldep) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error los valores de los detalles es diferente", "Error"));
				} else if (validaFecha == true) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Error, debe cambiar la fecha del depósito", "Error"));
				} else {

					// para validar la cedula
					Long a = fun.buscarCliente(cliente);

					if (a == 0) {

						Cliente c = new Cliente();
						c = cliente;
						String clave = this.ruc;
						String claveHash = BCrypt.hashpw(clave, BCrypt.gensalt());
						c.setClave(claveHash);
						c.setCi(ruc.toUpperCase());
						c.setNombre(nombre.toUpperCase());
						c.setTipoid(tipoIdentificacion);
						serviceCliente.registrar(c);
					} else {

						cliente.setCi(cliente.getCi().toUpperCase());
						cliente.setNombre(cliente.getNombre().toUpperCase());
						serviceCliente.modificar(cliente);
					}

					usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(us);
					// saca el secuencial +1
					num1 = (Integer) fun.secFactura(usuPunto);
					Comprobante comp = new Comprobante();
					comp.setNumero(num1);
					comp.setClienteruc(ruc.toUpperCase());
					comp.setClientenombre(nombre.toUpperCase());
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
					for (ComprobanteDetalle det : listaComprobanteDetalle) {
						det.setComprobante(comp);
						serviceComprobanteDetalle.registrar(det);
					}

					for (ComprobanteDepositos dep : listaComprobanteDepositos) {
						dep.setComprobante(comp);
						serviceComprobanteDepositos.registrar(dep);
					}

					comprobante = serviceComprobante.listarComprobantePorId(id_comprobante);
					/// GENERAR XML PARA LA FACTURA
					genXml.generarXmlArchivo(punto.getId(), num1);
					mostrarFactura();
					estadeshabilitado = true;
					// ejecutar(codigo, String.valueOf(num1) );
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, "Se grabó con éxito", "Aviso"));
				} // cierre de if
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// MOSTRAR FACTURA
	public Integer mostrarFactura() {
		try {
			usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(us);
			num = (Integer) fun.muestraFactura(usuPunto);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return num;
	}

	@Transactional
	public void ejecutar(Integer codigo, String factura) throws Exception {

		if (contador > 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Error, debe realizar el cierre del día anterior, no podrá facturar", "Error"));
		} else {
			int factura_int = Integer.parseInt(factura);

			if (paramtipo2 == "C" && factura_int != 0) {
				factura = punto.getEstablecimiento() + "-" + punto.getPuntoemision() + "-"
						+ StringUtils.leftPad(String.valueOf(factura), 9, "0");
				dbcyf.actualiza("A", Integer.parseInt(param2), factura, 0, "null");
			} else if (paramtipo2 == "E" && factura_int != 0) {
				for (Fin_Exportaciones_DTO exp : listaExportaciones) {
					dbcyf.actualiza("E", exp.getCod_entidad(), factura, 0, exp.getNum_solicitud());
				}
			} else if (paramtipo2 == "S" && factura_int != 0) {
				for (Sanciones_Admin_DTO s : listaSanciones) {
					dbsanciones.actualiza("S", s.getCodigo_empresa(), factura, s.getNumero_juicio());
				}
			} else if (paramtipo2 == "I" && factura_int != 0) {
				for (Fin_Importaciones_DTO imp : listaImportaciones) {
					dbcyf.actualiza("I", imp.getCod_entidad(), factura, 0, imp.getNum_solicitud());
				}
			} else if (paramtipo2 == "N" && factura_int != 0) {
				for (Fin_Importaciones_No_DTO imp : listaImportacionesNo) {

					dbcyf.actualizaimpno("N", imp.getCod_entidad(), factura, imp.getNum_solicitud());
				}
			} else if (paramtipo2 == "G" && factura_int != 0) {
				for (Fin_Guias_DTO imp : listaGuias) {
					dbcyf.actualiza("G", imp.getCod_entidad(), factura, 0, imp.getNum_solicitud());
				}
			} else if (paramtipo2 == "R" && factura_int != 0) {
				for (Fin_Cal_Ren_DTO emp : listaCalRen) {
					factura = punto.getEstablecimiento() + "-" + punto.getPuntoemision() + "-"
							+ StringUtils.leftPad(String.valueOf(factura), 9, "0");
					dbcyf.actualiza("R", emp.getCodigo(), factura, emp.getCodigo_renovacion(), null);
				}
			} else if (paramtipo2 == "T" && factura_int != 0) {
				for (Fin_Reptecnicos_DTO rt : listaCalRenRepTecnicos) {
					factura = punto.getEstablecimiento() + "-" + punto.getPuntoemision() + "-"
							+ StringUtils.leftPad(String.valueOf(factura), 9, "0");
					dbcyf.actualiza("T", rt.getIdtec(), factura, rt.getCodigo_calren(), " ");
				}
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
		props.put("mail.smtp.auth", "true"); // Usar autenticacin mediante usuario y clave
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
		// session.setDebug(true);

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
			transport.connect("mail.ministeriodegobierno.gob.ec", institucion.getUsuariocorreo(),
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

	///////////////////////////////////////////////////////////////////////
	///// GETTERS Y SETTERS

	public List<Fin_Cat_Act_Sit_Veh_Java_DTO> getListaCambioCategoria() {
		return listaCambioCategoria;
	}

	public void setListaCambioCategoria(List<Fin_Cat_Act_Sit_Veh_Java_DTO> listaCambioCategoria) {
		this.listaCambioCategoria = listaCambioCategoria;
	}

	public Fin_Cat_Act_Sit_Veh_Java_DTO getDatosEntidad() {
		return datosEntidad;
	}

	public void setDatosEntidad(Fin_Cat_Act_Sit_Veh_Java_DTO datosEntidad) {
		this.datosEntidad = datosEntidad;
	}

	public Integer getInst() {
		return inst;
	}

	public void setInst(Integer inst) {
		this.inst = inst;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public UsuarioPunto getUsuPunto() {
		return usuPunto;
	}

	public void setUsuPunto(UsuarioPunto usuPunto) {
		this.usuPunto = usuPunto;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public PuntoRecaudacion getPunto() {
		return punto;
	}

	public void setPunto(PuntoRecaudacion punto) {
		this.punto = punto;
	}

	public PuntoRecaudacion getNombrePunto() {
		return nombrePunto;
	}

	public void setNombrePunto(PuntoRecaudacion nombrePunto) {
		this.nombrePunto = nombrePunto;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public RecaudacionDetalle getRecaudacionDetalle() {
		return recaudacionDetalle;
	}

	public void setRecaudacionDetalle(RecaudacionDetalle recaudacionDetalle) {
		this.recaudacionDetalle = recaudacionDetalle;
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

	public List<ComprobanteDetalle> getListaComprobanteDetalle() {
		return listaComprobanteDetalle;
	}

	public void setListaComprobanteDetalle(List<ComprobanteDetalle> listaComprobanteDetalle) {
		this.listaComprobanteDetalle = listaComprobanteDetalle;
	}

	public List<ComprobanteDepositos> getListaComprobanteDepositos() {
		return listaComprobanteDepositos;
	}

	public void setListaComprobanteDepositos(List<ComprobanteDepositos> listaComprobanteDepositos) {
		this.listaComprobanteDepositos = listaComprobanteDepositos;
	}

	public Usuario getUs() {
		return us;
	}

	public void setUs(Usuario us) {
		this.us = us;
	}

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getNum1() {
		return num1;
	}

	public void setNum1(Integer num1) {
		this.num1 = num1;
	}

	public String getClaveA() {
		return claveA;
	}

	public void setClaveA(String claveA) {
		this.claveA = claveA;
	}

	public Integer getId_comprobante() {
		return id_comprobante;
	}

	public void setId_comprobante(Integer id_comprobante) {
		this.id_comprobante = id_comprobante;
	}

	public double getTotaldep() {
		return totaldep;
	}

	public void setTotaldep(double totaldep) {
		this.totaldep = totaldep;
	}

	public double getTotaldet() {
		return totaldet;
	}

	public void setTotaldet(double totaldet) {
		this.totaldet = totaldet;
	}

	public double getSubtotaldet() {
		return subtotaldet;
	}

	public void setSubtotaldet(double subtotaldet) {
		this.subtotaldet = subtotaldet;
	}

	public boolean isValidador() {
		return validador;
	}

	public void setValidador(boolean validador) {
		this.validador = validador;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public boolean isEstadeshabilitado() {
		return estadeshabilitado;
	}

	public void setEstadeshabilitado(boolean estadeshabilitado) {
		this.estadeshabilitado = estadeshabilitado;
	}

	public List<Sanciones_Admin_DTO> getListaSanciones() {
		return listaSanciones;
	}

	public void setListaSanciones(List<Sanciones_Admin_DTO> listaSanciones) {
		this.listaSanciones = listaSanciones;
	}

	public List<Fin_Exportaciones_DTO> getListaExportaciones() {
		return listaExportaciones;
	}

	public void setListaExportaciones(List<Fin_Exportaciones_DTO> listaExportaciones) {
		this.listaExportaciones = listaExportaciones;
	}

	public String getParamtipo2() {
		return paramtipo2;
	}

	public void setParamtipo2(String paramtipo2) {
		this.paramtipo2 = paramtipo2;
	}

	public String getParamnumerojuicio2() {
		return paramnumerojuicio2;
	}

	public void setParamnumerojuicio2(String paramnumerojuicio2) {
		this.paramnumerojuicio2 = paramnumerojuicio2;
	}

	public Sanciones_Admin_DTO getDatosEntidadSanciones() {
		return datosEntidadSanciones;
	}

	public void setDatosEntidadSanciones(Sanciones_Admin_DTO datosEntidadSanciones) {
		this.datosEntidadSanciones = datosEntidadSanciones;
	}

	public Fin_Exportaciones_DTO getDatosEntidadExportaciones() {
		return datosEntidadExportaciones;
	}

	public void setDatosEntidadExportaciones(Fin_Exportaciones_DTO datosEntidadExportaciones) {
		this.datosEntidadExportaciones = datosEntidadExportaciones;
	}

	public Funciones getFun() {
		return fun;
	}

	public void setFun(Funciones fun) {
		this.fun = fun;
	}

	public GenerarDOMBean getGenXml() {
		return genXml;
	}

	public void setGenXml(GenerarDOMBean genXml) {
		this.genXml = genXml;
	}

	public DBcyf getDbcyf() {
		return dbcyf;
	}

	public void setDbcyf(DBcyf dbcyf) {
		this.dbcyf = dbcyf;
	}

	public DBsanciones getDbsanciones() {
		return dbsanciones;
	}

	public void setDbsanciones(DBsanciones dbsanciones) {
		this.dbsanciones = dbsanciones;
	}

	public String getPnum_solicitud() {
		return pnum_solicitud;
	}

	public void setPnum_solicitud(String pnum_solicitud) {
		this.pnum_solicitud = pnum_solicitud;
	}

	public List<Fin_Importaciones_DTO> getListaImportaciones() {
		return listaImportaciones;
	}

	public void setListaImportaciones(List<Fin_Importaciones_DTO> listaImportaciones) {
		this.listaImportaciones = listaImportaciones;
	}

	public Fin_Importaciones_DTO getDatosEntidadImportaciones() {
		return datosEntidadImportaciones;
	}

	public void setDatosEntidadImportaciones(Fin_Importaciones_DTO datosEntidadImportaciones) {
		this.datosEntidadImportaciones = datosEntidadImportaciones;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}

	public List<Fin_Importaciones_DTO> getListaImportacionesRec() {
		return listaImportacionesRec;
	}

	public void setListaImportacionesRec(List<Fin_Importaciones_DTO> listaImportacionesRec) {
		this.listaImportacionesRec = listaImportacionesRec;
	}

	public Integer getCodentidad() {
		return codentidad;
	}

	public void setCodentidad(Integer codentidad) {
		this.codentidad = codentidad;
	}

	public String getNumsolicitud() {
		return numsolicitud;
	}

	public void setNumsolicitud(String numsolicitud) {
		this.numsolicitud = numsolicitud;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getTipoIdentificacion() {
		return tipoIdentificacion;
	}

	public void setTipoIdentificacion(String tipoIdentificacion) {
		this.tipoIdentificacion = tipoIdentificacion;
	}

	public List<Fin_Exportaciones_DTO> getListaExportacionesRec() {
		return listaExportacionesRec;
	}

	public void setListaExportacionesRec(List<Fin_Exportaciones_DTO> listaExportacionesRec) {
		this.listaExportacionesRec = listaExportacionesRec;
	}

	public List<Sanciones_Admin_DTO> getListaSancionesRec() {
		return listaSancionesRec;
	}

	public void setListaSancionesRec(List<Sanciones_Admin_DTO> listaSancionesRec) {
		this.listaSancionesRec = listaSancionesRec;
	}

	public List<Fin_Importaciones_No_DTO> getListaImportacionesNo() {
		return listaImportacionesNo;
	}

	public void setListaImportacionesNo(List<Fin_Importaciones_No_DTO> listaImportacionesNo) {
		this.listaImportacionesNo = listaImportacionesNo;
	}

	public List<Fin_Importaciones_No_DTO> getListaImportacionesNoRec() {
		return listaImportacionesNoRec;
	}

	public void setListaImportacionesNoRec(List<Fin_Importaciones_No_DTO> listaImportacionesNoRec) {
		this.listaImportacionesNoRec = listaImportacionesNoRec;
	}

	public List<Fin_Guias_DTO> getListaGuias() {
		return listaGuias;
	}

	public void setListaGuias(List<Fin_Guias_DTO> listaGuias) {
		this.listaGuias = listaGuias;
	}

	public List<Fin_Guias_DTO> getListaGuiasRec() {
		return listaGuiasRec;
	}

	public void setListaGuiasRec(List<Fin_Guias_DTO> listaGuiasRec) {
		this.listaGuiasRec = listaGuiasRec;
	}

	public List<Fin_Cal_Ren_DTO> getListaCalRen() {
		return listaCalRen;
	}

	public void setListaCalRen(List<Fin_Cal_Ren_DTO> listaCalRen) {
		this.listaCalRen = listaCalRen;
	}

	public Fin_Cal_Ren_DTO getCalRenDto() {
		return CalRenDto;
	}

	public void setCalRenDto(Fin_Cal_Ren_DTO calRenDto) {
		CalRenDto = calRenDto;
	}

	public String getParamcodigoentidad2() {
		return paramcodigoentidad2;
	}

	public void setParamcodigoentidad2(String paramcodigoentidad2) {
		this.paramcodigoentidad2 = paramcodigoentidad2;
	}

	public Integer getId_tmp() {
		return id_tmp;
	}

	public void setId_tmp(Integer id_tmp) {
		this.id_tmp = id_tmp;
	}

	public Integer getIdPunto() {
		return idPunto;
	}

	public void setIdPunto(Integer idPunto) {
		this.idPunto = idPunto;
	}

	public String getValorParametroAnual() {
		return valorParametroAnual;
	}

	public void setValorParametroAnual(String valorParametroAnual) {
		this.valorParametroAnual = valorParametroAnual;
	}

	public double getValorRMU() {
		return valorRMU;
	}

	public void setValorRMU(double valorRMU) {
		this.valorRMU = valorRMU;
	}

	public Fin_Importaciones_No_DTO getDatosEntidadImportacionesNo() {
		return datosEntidadImportacionesNo;
	}

	public void setDatosEntidadImportacionesNo(Fin_Importaciones_No_DTO datosEntidadImportacionesNo) {
		this.datosEntidadImportacionesNo = datosEntidadImportacionesNo;
	}

	public Fin_Guias_DTO getDatosEntidadGuias() {
		return datosEntidadGuias;
	}

	public void setDatosEntidadGuias(Fin_Guias_DTO datosEntidadGuias) {
		this.datosEntidadGuias = datosEntidadGuias;
	}

	public ParametroDetalle getParametroDetalle() {
		return parametroDetalle;
	}

	public void setParametroDetalle(ParametroDetalle parametroDetalle) {
		this.parametroDetalle = parametroDetalle;
	}

	public List<Fin_Reptecnicos_DTO> getListaCalRenRepTecnicos() {
		return listaCalRenRepTecnicos;
	}

	public void setListaCalRenRepTecnicos(List<Fin_Reptecnicos_DTO> listaCalRenRepTecnicos) {
		this.listaCalRenRepTecnicos = listaCalRenRepTecnicos;
	}

	public List<Fin_Reptecnicos_DTO> getListaCalRenRepTecnicosRec() {
		return listaCalRenRepTecnicosRec;
	}

	public void setListaCalRenRepTecnicosRec(List<Fin_Reptecnicos_DTO> listaCalRenRepTecnicosRec) {
		this.listaCalRenRepTecnicosRec = listaCalRenRepTecnicosRec;
	}

	public Fin_Reptecnicos_DTO getDatosRepTecnico() {
		return datosRepTecnico;
	}

	public void setDatosRepTecnico(Fin_Reptecnicos_DTO datosRepTecnico) {
		this.datosRepTecnico = datosRepTecnico;
	}

	public Integer getIdtec() {
		return idtec;
	}

	public void setIdtec(Integer idtec) {
		this.idtec = idtec;
	}

}
