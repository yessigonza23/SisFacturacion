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

import ec.gob.mdg.model.PuntoRecaudacion;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.model.VistaCierreDTO;
import ec.gob.mdg.model.VistaRecaudacionDTO;
import ec.gob.mdg.model.VistaRecaudacionDepositoDTO;
import ec.gob.mdg.service.IPuntoRecaudacionService;
import ec.gob.mdg.service.IUsuarioPuntoService;
import ec.gob.mdg.service.IVistaCierreDTOService;
import ec.gob.mdg.service.IVistaRecaudacioDepositoDTOService;
import ec.gob.mdg.service.IVistaRecaudacionDTOService;
import ec.gob.mdg.util.UtilsArchivos;
import ec.gob.mdg.validaciones.FunValidaciones;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Named
@ViewScoped
public class ReportesPorPuntoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Resource(mappedName = "java:/finanDS")
	DataSource datasource;

	@Inject
	private IUsuarioPuntoService serviceUsuPunto;

	@Inject
	private IPuntoRecaudacionService servicePuntoRecaudacion;

	@Inject
	private IVistaCierreDTOService serviceVistaCierreDTO;

	@Inject
	private IVistaRecaudacionDTOService serviceVistaRecaudacionDTO;

	@Inject
	private IVistaRecaudacioDepositoDTOService serviceVistaRecaudacionDepositoDTO;

	private List<VistaCierreDTO> listaVistaCierreDTO = new ArrayList<VistaCierreDTO>();
	private List<VistaRecaudacionDTO> listaVistaRecaudacionDTO = new ArrayList<>();
	private List<VistaRecaudacionDepositoDTO> listaVistaRecaudacionDepositoDTO = new ArrayList<>();

	PuntoRecaudacion punto;
	PuntoRecaudacion nombre;

	Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	private UsuarioPunto usuPunto = new UsuarioPunto();
	Date fecha_inicio;
	Date fecha_fin;
	String opcion;
	String nombreReporte;
	String nombreGuarda;
	// totales vista recaudacion
	double total = 0.00;

	// totales vista recaudacion deposito
	double totald = 0.00;
	Integer contadord = 0;
	// totales vista cierre
	double totalc = 0.00;
	Integer contadorc = 0;

	@PostConstruct
	public void init() {
		try {
			usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(us);
			punto = usuPunto.getPuntoRecaudacion();
			nombre = servicePuntoRecaudacion.listarPorId(punto);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/// METODO PARA LISTAR COMPROBANTES FACTURAS POR FECHAS
	public void reportesPorOpcion(String opcion) {
		try {
			if (opcion != null) {
				if (opcion.equals("1")) {
					System.out.println("reporte 1 " + us.getApellido() + "-" +us.getNombre());
					nombreReporte = "RepPorPuntoFactGeneral.jasper";
					nombreGuarda = "RepPorPuntoFactGeneral";
				} else if (opcion.equals("2")) {
					System.out.println("reporte 2 " + us.getApellido() + "-" +us.getNombre());
					nombreReporte = "RepPorPuntoFactCodBanco.jasper";
					nombreGuarda = "RepPorPuntoFactCodBanco";
				} else if (opcion.equals("3")) {
					System.out.println("reporte 3 " + us.getApellido() + "-" +us.getNombre());
					nombreReporte = "RepPorPuntoFactAnuladas.jasper";
					nombreGuarda = "RepPorPuntoFactAnuladas";
				} else if (opcion.equals("4")) {
					System.out.println("reporte 4 " + us.getApellido() + "-" +us.getNombre());
					nombreReporte = "RepPorPuntoFactNoAutorizadas.jasper";
					nombreGuarda = "RepPorPuntoFactNoAutorizadas";
				} else if (opcion.equals("5")) {
					System.out.println("reporte5 " + us.getApellido() + "-" +us.getNombre());
					nombreReporte = "RepPorPuntoCruceFacturasNotas.jasper";
					nombreGuarda = "RepPorPuntoCruceFacturasNotas";
				} else if (opcion.equals("6")) {
					System.out.println("reporte 6 " + us.getApellido() + "-" +us.getNombre());
					nombreReporte = "RepPorPuntoCierre.jasper";
					nombreGuarda = "RepPorPuntoCierre";
				} else if (opcion.equals("7")) {
					System.out.println("reporte 7 " + us.getApellido() + "-" +us.getNombre());
					nombreReporte = "RepPorPuntoDepositoNoConsolidado.jasper";
					nombreGuarda = "RepPorPuntoNoConsolidados";
				} 
				generarReporte(nombreReporte);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void generarReporte(String nombreReporte) {
		System.out.println(opcion);
		try {

			String rutaImagenLogo = UtilsArchivos.getRutaLogo() + "logomdg.png";
			String rutaImagenLogoPie = UtilsArchivos.getRutaLogo() + "footer.png";

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("PUNTO", punto.getId());
			parametros.put("FECHA_INICIAL", fecha_inicio);
			parametros.put("FECHA_FINAL", fecha_fin);
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

	public void listarVistaCierre(Date fecha_inicio, Date fecha_fin, Integer id_punto) {
		if (fecha_inicio != null && fecha_fin != null) {
			this.listaVistaCierreDTO = this.serviceVistaCierreDTO.listarCierre(fecha_inicio, fecha_fin, id_punto);
			this.contadorc = this.serviceVistaCierreDTO.cuentaFacturas(fecha_inicio, fecha_fin, id_punto);
			totalc();
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sin Datos", "Especifique las fechas"));
		}

	}

	public void totalc() {
		totalc = 0;
		for (VistaCierreDTO v : listaVistaCierreDTO) {
			totalc = totalc + v.getValor();
			totalc = FunValidaciones.formatearDecimales(totalc, 2);
		}
	}

///////////
	public void listarVistaRecaudacion(Date fecha_inicio, Date fecha_fin, Integer id_punto) {
		if (fecha_inicio != null && fecha_fin != null) {
			this.listaVistaRecaudacionDTO = this.serviceVistaRecaudacionDTO.listarRecaudacionDetalle(fecha_inicio,
					fecha_fin, id_punto);
			total();
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sin Datos", "Especifique las fechas"));
		}

	}

	public void total() {
		total = 0;
		for (VistaRecaudacionDTO v : listaVistaRecaudacionDTO) {
			total = total + v.getComp_valor();
			total = FunValidaciones.formatearDecimales(total, 2);
		}
	}
	////////////////////////

	public void listarVistaRecaudacionDeposito(Date fecha_inicio, Date fecha_fin, Integer id_punto) {
		if (fecha_inicio != null && fecha_fin != null) {
			this.listaVistaRecaudacionDepositoDTO = this.serviceVistaRecaudacionDepositoDTO
					.listarRecaudacionDeposito(fecha_inicio, fecha_fin, id_punto);

			this.contadord = this.serviceVistaRecaudacionDepositoDTO.cuentaFacturas(fecha_inicio, fecha_fin, id_punto);

			totald();
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sin Datos", "Especifique las fechas"));
		}

	}

	public void totald() {
		totald = 0;
		for (VistaRecaudacionDepositoDTO v : listaVistaRecaudacionDepositoDTO) {
			totald = totald + v.getComp_valor();
			totald = FunValidaciones.formatearDecimales(totald, 2);
		}
	}

	

	////// GETTERS Y SETTERS

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

	public Usuario getUs() {
		return us;
	}

	public void setUs(Usuario us) {
		this.us = us;
	}

	public UsuarioPunto getUsuPunto() {
		return usuPunto;
	}

	public void setUsuPunto(UsuarioPunto usuPunto) {
		this.usuPunto = usuPunto;
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

	public String getOpcion() {
		return opcion;
	}

	public void setOpcion(String opcion) {
		this.opcion = opcion;
	}

	public DataSource getDatasource() {
		return datasource;
	}

	public void setDatasource(DataSource datasource) {
		this.datasource = datasource;
	}

	public List<VistaCierreDTO> getListaVistaCierreDTO() {
		return listaVistaCierreDTO;
	}

	public void setListaVistaCierreDTO(List<VistaCierreDTO> listaVistaCierreDTO) {
		this.listaVistaCierreDTO = listaVistaCierreDTO;
	}

	public List<VistaRecaudacionDTO> getListaVistaRecaudacionDTO() {
		return listaVistaRecaudacionDTO;
	}

	public void setListaVistaRecaudacionDTO(List<VistaRecaudacionDTO> listaVistaRecaudacionDTO) {
		this.listaVistaRecaudacionDTO = listaVistaRecaudacionDTO;
	}

	public List<VistaRecaudacionDepositoDTO> getListaVistaRecaudacionDepositoDTO() {
		return listaVistaRecaudacionDepositoDTO;
	}

	public void setListaVistaRecaudacionDepositoDTO(
			List<VistaRecaudacionDepositoDTO> listaVistaRecaudacionDepositoDTO) {
		this.listaVistaRecaudacionDepositoDTO = listaVistaRecaudacionDepositoDTO;
	}

	public String getNombreReporte() {
		return nombreReporte;
	}

	public void setNombreReporte(String nombreReporte) {
		this.nombreReporte = nombreReporte;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public double getTotald() {
		return totald;
	}

	public void setTotald(double totald) {
		this.totald = totald;
	}

	public Integer getContadord() {
		return contadord;
	}

	public void setContadord(Integer contadord) {
		this.contadord = contadord;
	}

	public double getTotalc() {
		return totalc;
	}

	public void setTotalc(double totalc) {
		this.totalc = totalc;
	}

	public Integer getContadorc() {
		return contadorc;
	}

	public void setContadorc(Integer contadorc) {
		this.contadorc = contadorc;
	}

	public String getNombreGuarda() {
		return nombreGuarda;
	}

	public void setNombreGuarda(String nombreGuarda) {
		this.nombreGuarda = nombreGuarda;
	}

}
