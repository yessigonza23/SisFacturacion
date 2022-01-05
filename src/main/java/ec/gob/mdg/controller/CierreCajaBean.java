package ec.gob.mdg.controller;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import ec.gob.mdg.dao.ICierreDAO;
import ec.gob.mdg.model.Cierre;
import ec.gob.mdg.model.Comprobante;
import ec.gob.mdg.model.Contable;
import ec.gob.mdg.model.ContableDetalle;
import ec.gob.mdg.model.PlanContable;
import ec.gob.mdg.model.PuntoRecaudacion;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.model.VistaRecaudacionDTO;
import ec.gob.mdg.service.IComprobanteService;
import ec.gob.mdg.service.IContableDetalleService;
import ec.gob.mdg.service.IContableService;
import ec.gob.mdg.service.IPlanContableService;
import ec.gob.mdg.service.IPuntoRecaudacionService;
import ec.gob.mdg.service.IUsuarioPuntoService;
import ec.gob.mdg.service.IVistaRecaudacionDTOService;
import ec.gob.mdg.util.UtilsArchivos;
import ec.gob.mdg.util.UtilsDate;
import ec.gob.mdg.validaciones.FunValidaciones;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Named
@ViewScoped
public class CierreCajaBean implements Serializable {
	private static final long serialVersionUID = 1L;
	@Resource(mappedName = "java:/finanDS")
	DataSource datasource;
	@Inject
	private ICierreDAO serviceCierre;

	@Inject
	private IUsuarioPuntoService serviceUsuPunto;

	@Inject
	private IPuntoRecaudacionService servicePuntoRecaudacion;

	@Inject
	private IComprobanteService serviceComprobante;

	@Inject
	private IContableService serviceContable;

	@Inject
	private IContableDetalleService serviceContableDetalle;

	@Inject
	private IVistaRecaudacionDTOService serviceVistaRecaudacionDTO;

	@Inject
	private IPlanContableService servicePlanContable;

	private Cierre cierre = new Cierre();
	private PuntoRecaudacion puntoRecaudacion;
	private Comprobante comprobante;
	private PlanContable plancontable;

	private List<PuntoRecaudacion> listaPuntoRecaudacion;
	private List<Cierre> listaCierre = new ArrayList<Cierre>();
	private List<Comprobante> listaComprobante;
	private List<VistaRecaudacionDTO> listaVistaRecaudacionDTO = new ArrayList<>();
	private List<VistaRecaudacionDTO> listaVistaRecaudacionDTOTotales = new ArrayList<>();

	private UsuarioPunto usuPunto = new UsuarioPunto();
	Usuario p = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");

	private UtilsDate utilsDate;
	PuntoRecaudacion punto;
	PuntoRecaudacion nombre;

	Date fechaActual;
	double totaldet = 0.0;

	Integer num = 0;
	Integer codcierre = 0;
	Integer contador = 0;
	Date fecha_inicio;
	Date fecha_fin;
	
	String nombreReporte;
	String nombreGuarda;

	boolean estadeshabilitado;

	@PostConstruct
	public void init() {
		try {
			usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(p);
			punto = usuPunto.getPuntoRecaudacion();
			nombre = servicePuntoRecaudacion.listarPorId(punto);
			fechaActual = UtilsDate.fechaActual();
			this.listarComprobante();
			this.totalComprobantes();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// sumar comprobantes
	public void totalComprobantes() {
		for (Comprobante comp : listaComprobante) {
			if (comp.isCierre_b()) {
				totaldet = totaldet + comp.getValor();
			}
			totaldet = FunValidaciones.formatearDecimales(totaldet, 2);
		}

	}

	public void totalValor(boolean est, int mont) {
		if (est) {
			totaldet = totaldet + mont;
		} else {
			totaldet = totaldet - mont;
		}
		totaldet = FunValidaciones.formatearDecimales(totaldet, 2);
	}

	// guarda cierre
	@SuppressWarnings("static-access")
	@Transactional
	public void registrarCierre() {
		try {
			usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(p);
			Cierre cie = new Cierre();
			if (listaComprobante.size() > 0) {
				cie.setDetalle(cierre.getDetalle());
				fechaActual = utilsDate.fechaActual();
				cie.setFecha(fechaActual);
				cie.setUsuarioPunto(usuPunto);
				cie.setPuntoRecaudacion(punto);
				cie.setEstado("A");
				cie.setId_banco(cierre.getId_banco());
				cie.setId_cuenta(cierre.getId_cuenta());
				cie.setValor(totaldet);
				codcierre = serviceCierre.registrar(cie);
				for (Comprobante compc : listaComprobante) {
					if (compc.isCierre_b()) {
						compc.setCierre(cie);
						serviceComprobante.modificar(compc);
					}
				}
				this.cierre = this.serviceCierre.mostrarCierrePorId(codcierre);
				//// REGISTRA CONTABLE
				Contable contable = new Contable();

				contable.setDetalle(cierre.getDetalle());
				contable.setFecha(fechaActual);
				contable.setValor(totaldet);
				contable.setPuntoRecaudacion(punto);
				contable.setUsuarioPunto(usuPunto);
				contable.setIdcierre(codcierre);
				serviceContable.registrar(contable);
				///// REGISTRO CONTABLE DETALLE

				listaVistaRecaudacionDTOTotales = serviceVistaRecaudacionDTO
						.listarComprobantesPorCierreIdTotales(codcierre);

				for (VistaRecaudacionDTO v : listaVistaRecaudacionDTOTotales) {

					if (v.getSubt_iva() != 0) {
						ContableDetalle cd = new ContableDetalle();
						cd.setDebe(0);
						cd.setHaber(v.getSubt_iva());
						cd.setContable(contable);
						plancontable = servicePlanContable.cargaPlanContable(17);
						cd.setPlancontable(plancontable);
						serviceContableDetalle.registrar(cd);

					}
					if (v.getSubt_vcer() != 0) {

						ContableDetalle cd = new ContableDetalle();
						cd.setDebe(v.getComp_valor());
						cd.setHaber(0);
						cd.setContable(contable);
						plancontable = servicePlanContable.cargaPlanContable(8);
						cd.setPlancontable(plancontable);
						serviceContableDetalle.registrar(cd);
					}

				}
				listaVistaRecaudacionDTO = serviceVistaRecaudacionDTO.listarComprobantesPorCierreId(codcierre);
				for (VistaRecaudacionDTO v : listaVistaRecaudacionDTO) {

					ContableDetalle cd = new ContableDetalle();
					cd.setDebe(0);
					cd.setHaber(v.getImporte());
					cd.setContable(contable);
					plancontable = servicePlanContable.muestraPlanContable(v.getPlancontable_codigo());
					cd.setPlancontable(plancontable);
					serviceContableDetalle.registrar(cd);

				}
				/////////////////////

				estadeshabilitado = true;

				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "se Grabo con exito", "Aviso"));

			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
						"No dispone de comprobantes para hacer el cierre", "ERROR"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// lista comprobantes por punto

	public void listarComprobante() {
		try {
			this.listaComprobante = this.serviceComprobante.listarComprPorPto(punto);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// MOSTRAR CIERRE
	public void mostrarCierre() {
		try {
			cierre.getId();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	/// LISTA DE CONSULTA DE CIERRE POR PUNTO Y POR FECHAS
	public void listarCierre() {
		try {
			this.listaCierre = serviceCierre.listarCierrePorFechasPorPto(fecha_inicio, fecha_fin, punto.getId());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// IR A DETALLE DE FACTURA A CONSULTAR
	public String irDetalle(String id) {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("param", id);
		return "cierrecajaconsultadetalle?faces-redirect=true";
	}

	public void generarReporte(Cierre cierre) {
		try {
			nombreReporte = "RepCierre.jasper";
			nombreGuarda = "RepCierre";
			
			String rutaImagenLogo = UtilsArchivos.getRutaLogo() + "logomdg.png";
			String rutaImagenLogoPie = UtilsArchivos.getRutaLogo() + "footer.png";

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("CIERRE", cierre.getId());
			parametros.put("LOGO", rutaImagenLogo);
			parametros.put("LOGOPIE", rutaImagenLogoPie);
			
			String path = UtilsArchivos.getRutaReportesJasper();
			String pathPdf = UtilsArchivos.getRutaReportes();

			Connection conn = datasource.getConnection();
			File jasper = new File(path + nombreReporte);

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, conn);

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.addHeader("Content-disposition", "attachment; filename=" + nombreGuarda + punto.getId() + ".pdf");

			JasperExportManager.exportReportToPdfFile(jasperPrint, pathPdf + nombreGuarda + punto.getId() + ".pdf");

			ServletOutputStream stream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

			stream.flush();
			stream.close();

			FacesContext.getCurrentInstance().responseComplete();
			if (conn != null) {
				conn.close();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

//	getter y setter

	public PuntoRecaudacion getPuntoRecaudacion() {
		return puntoRecaudacion;
	}

	public void setPuntoRecaudacion(PuntoRecaudacion puntoRecaudacion) {
		this.puntoRecaudacion = puntoRecaudacion;
	}

	public Cierre getCierre() {
		return cierre;
	}

	public void setCierre(Cierre cierre) {
		this.cierre = cierre;
	}

	public List<PuntoRecaudacion> getListaPuntoRecaudacion() {
		return listaPuntoRecaudacion;
	}

	public void setListaPuntoRecaudacion(List<PuntoRecaudacion> listaPuntoRecaudacion) {
		this.listaPuntoRecaudacion = listaPuntoRecaudacion;
	}

	public List<Cierre> getListaCierre() {
		return listaCierre;
	}

	public void setListaCierre(List<Cierre> listaCierre) {
		this.listaCierre = listaCierre;
	}

	public UsuarioPunto getUsuPunto() {
		return usuPunto;
	}

	public void setUsuPunto(UsuarioPunto usuPunto) {
		this.usuPunto = usuPunto;
	}

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

	public Usuario getP() {
		return p;
	}

	public void setP(Usuario p) {
		this.p = p;
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

	public UtilsDate getUtilsDate() {
		return utilsDate;
	}

	public void setUtilsDate(UtilsDate utilsDate) {
		this.utilsDate = utilsDate;
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

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Integer getCodcierre() {
		return codcierre;
	}

	public void setCodcierre(Integer codcierre) {
		this.codcierre = codcierre;
	}

	public boolean isEstadeshabilitado() {
		return estadeshabilitado;
	}

	public void setEstadeshabilitado(boolean estadeshabilitado) {
		this.estadeshabilitado = estadeshabilitado;
	}

	public Integer getContador() {
		return contador;
	}

	public void setContador(Integer contador) {
		this.contador = contador;
	}

	public Date getFecha_inicio() {
		return fecha_inicio;
	}

	public void setFecha_inicio(Date fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	public Date getFecha_fin() {
		return fecha_fin;
	}

	public void setFecha_fin(Date fecha_fin) {
		this.fecha_fin = fecha_fin;
	}

}
