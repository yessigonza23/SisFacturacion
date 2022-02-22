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
import ec.gob.mdg.model.VistaRecaudacionDTO;
import ec.gob.mdg.model.VistaRecaudacionDepositoDTO;
import ec.gob.mdg.service.IPuntoRecaudacionService;
import ec.gob.mdg.service.IUsuarioPuntoService;
import ec.gob.mdg.service.IVistaRecaudacioDepositoDTOService;
import ec.gob.mdg.service.IVistaRecaudacionDTOService;
import ec.gob.mdg.util.UtilsArchivos;
import ec.gob.mdg.validaciones.FunValidaciones;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
@Named
@ViewScoped
public class ReportesNacionalBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Resource(mappedName = "java:/finanDS")
	DataSource datasource;

	@Inject
	private IUsuarioPuntoService serviceUsuPunto;

	@Inject
	private IPuntoRecaudacionService servicePuntoRecaudacion;

	@Inject
	private IVistaRecaudacionDTOService serviceVistaRecaudacionDTO;

	@Inject
	private IVistaRecaudacioDepositoDTOService serviceVistaRecaudacionDepositoDTO;

	private List<VistaRecaudacionDTO> listaVistaRecaudacionDTO = new ArrayList<>();
	private List<VistaRecaudacionDepositoDTO> listaVistaRecaudacionDepositoDTO = new ArrayList<>();
	private List<VistaRecaudacionDTO> listaVistaRecaudacionAnuladas = new ArrayList<>();
	private List<VistaRecaudacionDTO> listaVistaRecaudacionNoAutorizadas = new ArrayList<>();
	private List<VistaRecaudacionDTO> listaVistaRecaudacionSinCierre = new ArrayList<>();
	private List<VistaRecaudacionDTO> listaVistaRecaudacionDif = new ArrayList<>();

	PuntoRecaudacion punto;
	PuntoRecaudacion nombre;

	Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	private UsuarioPunto usuPunto = new UsuarioPunto();
	Date fecha_inicio;
	Date fecha_fin;
	String proceso;
	String opcion;

	String nombreReporte;
	String nombreGuarda;
	// totales vista recaudacion
	double total = 0.00;
	Integer contador = 0;
	// totales vista recaudacion deposito
	double totald = 0.00;
	Integer contadord = 0;
	// totales vista cierre
	double totalc = 0.00;
	Integer contadorc = 0;
	// totales vista cierre
	double totala = 0.00;
	Integer contadora = 0;
	// totales vista cierre
	double totaln = 0.00;
	Integer contadorn = 0;
	String proceso_tipo;

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
					System.out.println("reporte Nac 1 " + us.getApellido() + "-" +us.getNombre());
					nombreReporte = "RepConsNanPorPuntoFactSerBanCodPres.jasper";
					nombreGuarda = "RepConsNanPorPuntoFactSerBanCodPres";
				} else if (opcion.equals("2")) {
					System.out.println("reporte Nac 2 " + us.getApellido() + "-" +us.getNombre());
					nombreReporte = "RepConsNanProcesoCodPresupuestario.jasper";
					nombreGuarda = "RepConsNanProcesoCodPresupuestario";
				} else if (opcion.equals("3")) {
					System.out.println("reporte Nac 3 " + us.getApellido() + "-" +us.getNombre());
					nombreReporte = "RepConsNanServicioPunto.jasper";
					nombreGuarda = "RepConsNanServicioPunto";
				} else if (opcion.equals("4")) {
					System.out.println("reporte Nac4 " + us.getApellido() + "-" +us.getNombre());
					nombreReporte = "RepConsNanCodPartPresupuestari.jasper";
					nombreGuarda = "RepConsNanCodPartPresupuestari";
				} else if (opcion.equals("5")) {
					System.out.println("reporte Nac 6 " + us.getApellido() + "-" +us.getNombre());
					nombreReporte = "RepConsNanPlanPresBanProceso.jasper";
					nombreGuarda = "RepConsNanPlanPresBanProceso";
				}
				
				generarReporte(nombreReporte);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public void generarReporte(String nombreReporte) {
		try {

			String rutaImagenLogo = UtilsArchivos.getRutaLogo() + "logomdg.png";
			String rutaImagenLogoPie = UtilsArchivos.getRutaLogo() + "footer.png";

			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("PUNTO", punto.getId());
			parametros.put("FECHA_INICIAL", fecha_inicio);
			parametros.put("FECHA_FINAL", fecha_fin);
			parametros.put("PROCESO", proceso);
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

			
		    
			//////

	
			if (conn != null) {
				conn.close();
			}

			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	///////////
	public void listarVistaRecaudacionNac(Date fecha_inicio, Date fecha_fin, String proceso_tipo) {

		if (fecha_inicio != null && fecha_fin != null) {
			this.listaVistaRecaudacionDTO = this.serviceVistaRecaudacionDTO.listarRecaudacionDetalleNac(fecha_inicio,
					fecha_fin, proceso_tipo);
//			this.contador = this.serviceVistaRecaudacionDTO.cuentaFacturasNac(fecha_inicio, fecha_fin, proceso_tipo);
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

	public void listarVistaRecaudacionDeposito(Date fecha_inicio, Date fecha_fin) {

		if (fecha_inicio != null && fecha_fin != null) {

			this.listaVistaRecaudacionDepositoDTO = this.serviceVistaRecaudacionDepositoDTO
					.listarRecaudacionDepositoNac(fecha_inicio, fecha_fin);
//			this.contadord = this.serviceVistaRecaudacionDepositoDTO.cuentaFacturasNac(fecha_inicio, fecha_fin);
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

	////////////////////////////////////////

	public void listarVistaRecaudacionAnuladas(Date fecha_inicio, Date fecha_fin, String proceso_tipo) {
		if (fecha_inicio != null && fecha_fin != null) {

			this.listaVistaRecaudacionAnuladas = this.serviceVistaRecaudacionDTO
					.listarRecaudacionAnuladasNac(fecha_inicio, fecha_fin, proceso_tipo);
//			this.contadora = this.serviceVistaRecaudacionDTO.cuentaFacturasNac(fecha_inicio, fecha_fin, proceso_tipo);
			totala();
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sin Datos", "Especifique las fechas"));
		}

	}

	public void totala() {
		totala = 0;
		for (VistaRecaudacionDTO v : listaVistaRecaudacionAnuladas) {
			totala = totala + v.getComp_valor();
			totala = FunValidaciones.formatearDecimales(totala, 2);
		}
	}

	///////////////////////////////////////////

	public void listarVistaRecaudacionNoAutorizadas(Date fecha_inicio, Date fecha_fin, String proceso_tipo) {

		if (fecha_inicio != null && fecha_fin != null) {

			this.listaVistaRecaudacionNoAutorizadas = this.serviceVistaRecaudacionDTO
					.listarRecaudacionNoAutorizadasNac(fecha_inicio, fecha_fin, proceso_tipo);
//			this.contadorn = this.serviceVistaRecaudacionDTO.cuentaFacturasNoAutorizadasNac(fecha_inicio, fecha_fin,
//					proceso_tipo);
			totaln();
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sin Datos", "Especifique las fechas"));
		}
	}

	public void totaln() {
		totaln = 0;
		for (VistaRecaudacionDTO v : listaVistaRecaudacionNoAutorizadas) {
			totaln = totaln + v.getComp_valor();
			totaln = FunValidaciones.formatearDecimales(totaln, 2);
		}
	}

	///////////////////////////////////////////

	public void listarVistaRecaudacionSinCierre(Date fecha_inicio, Date fecha_fin, String proceso_tipo) {

		if (fecha_inicio != null && fecha_fin != null) {
			this.listaVistaRecaudacionSinCierre = this.serviceVistaRecaudacionDTO
					.listarRecaudacionSinCierre(fecha_inicio, fecha_fin, proceso_tipo);
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sin Datos", "Especifique las fechas"));
		}
	}

	////////////////////////////////////////////
	public void listarVistaRecaudacionDif(Date fecha_inicio, Date fecha_fin, String proceso_tipo) {
		if (fecha_inicio != null && fecha_fin != null) {
			this.listaVistaRecaudacionDif = this.serviceVistaRecaudacionDTO.listarRecaudacioDif(fecha_inicio, fecha_fin,
					proceso_tipo);
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sin Datos", "Especifique las fechas"));
		}

	}

	///////////////////////////// GENERAR XLS


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

	public Integer getContador() {
		return contador;
	}

	public void setContador(Integer contador) {
		this.contador = contador;
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

	public List<VistaRecaudacionDTO> getListaVistaRecaudacionAnuladas() {
		return listaVistaRecaudacionAnuladas;
	}

	public void setListaVistaRecaudacionAnuladas(List<VistaRecaudacionDTO> listaVistaRecaudacionAnuladas) {
		this.listaVistaRecaudacionAnuladas = listaVistaRecaudacionAnuladas;
	}

	public List<VistaRecaudacionDTO> getListaVistaRecaudacionNoAutorizadas() {
		return listaVistaRecaudacionNoAutorizadas;
	}

	public void setListaVistaRecaudacionNoAutorizadas(List<VistaRecaudacionDTO> listaVistaRecaudacionNoAutorizadas) {
		this.listaVistaRecaudacionNoAutorizadas = listaVistaRecaudacionNoAutorizadas;
	}

	public double getTotala() {
		return totala;
	}

	public void setTotala(double totala) {
		this.totala = totala;
	}

	public Integer getContadora() {
		return contadora;
	}

	public void setContadora(Integer contadora) {
		this.contadora = contadora;
	}

	public double getTotaln() {
		return totaln;
	}

	public void setTotaln(double totaln) {
		this.totaln = totaln;
	}

	public Integer getContadorn() {
		return contadorn;
	}

	public void setContadorn(Integer contadorn) {
		this.contadorn = contadorn;
	}

	public String getProceso_tipo() {
		return proceso_tipo;
	}

	public void setProceso_tipo(String proceso_tipo) {
		this.proceso_tipo = proceso_tipo;
	}

	public List<VistaRecaudacionDTO> getListaVistaRecaudacionSinCierre() {
		return listaVistaRecaudacionSinCierre;
	}

	public void setListaVistaRecaudacionSinCierre(List<VistaRecaudacionDTO> listaVistaRecaudacionSinCierre) {
		this.listaVistaRecaudacionSinCierre = listaVistaRecaudacionSinCierre;
	}

	public List<VistaRecaudacionDTO> getListaVistaRecaudacionDif() {
		return listaVistaRecaudacionDif;
	}

	public void setListaVistaRecaudacionDif(List<VistaRecaudacionDTO> listaVistaRecaudacionDif) {
		this.listaVistaRecaudacionDif = listaVistaRecaudacionDif;
	}

	public String getProceso() {
		return proceso;
	}

	public void setProceso(String proceso) {
		this.proceso = proceso;
	}

	public String getNombreGuarda() {
		return nombreGuarda;
	}

	public void setNombreGuarda(String nombreGuarda) {
		this.nombreGuarda = nombreGuarda;
	}

}
