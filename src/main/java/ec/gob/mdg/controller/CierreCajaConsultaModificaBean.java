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
import ec.gob.mdg.model.PuntoRecaudacion;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.service.IAuditoriaService;
import ec.gob.mdg.service.IComprobanteService;
import ec.gob.mdg.service.IPuntoRecaudacionService;
import ec.gob.mdg.service.IUsuarioPuntoService;
import ec.gob.mdg.util.UtilsArchivos;
import ec.gob.mdg.util.UtilsDate;
import ec.gob.mdg.validaciones.Funciones;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Named
@ViewScoped
public class CierreCajaConsultaModificaBean implements Serializable {

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

	@SuppressWarnings("unused")
	@Inject
	private Funciones fun;

	@Inject
	private IAuditoriaService serviceAuditoria;

	private Cierre cierre = new Cierre();
	private PuntoRecaudacion puntoRecaudacion;
	private Comprobante comprobante;

	private List<PuntoRecaudacion> listaPuntoRecaudacion;
	private List<Cierre> listaCierre = new ArrayList<Cierre>();
	private List<Comprobante> listaComprobante;
	private List<Comprobante> listaComprobanteTmp;

	private UsuarioPunto usuPunto = new UsuarioPunto();
	Usuario p = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");

	private UtilsDate utilsDate;
	PuntoRecaudacion punto;
	PuntoRecaudacion nombre;
	String nombreReporte;
	String nombreGuarda;
	
	Date fechaActual;
	double totaldet = 0.0;
	Integer num = 0;
	Integer codcierre = 0;
	Integer contador = 0;
	Date fecha_inicio;
	Date fecha_fin;
	Integer id = 0;
	String param2;
	boolean estadeshabilitado;
	double valorAnterior = 0.0;

	@PostConstruct
	public void init() {
		try {
			usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(p);
			punto = usuPunto.getPuntoRecaudacion();
			nombre = servicePuntoRecaudacion.listarPorId(punto);
			fechaActual = UtilsDate.fechaActual();
			listarDetalleCierre();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getParam() {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
	}

	// Listar informacin de la cabecera del comprobante: Facturas
	public void listarDetalleCierre() {
		param2 = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
		id = Integer.parseInt(param2);
		this.cierre = this.serviceCierre.mostrarCierrePorId(id);
		valorAnterior = cierre.getValor();
		this.listaComprobante = this.serviceComprobante.listarComprobantePorIdCierre(id);
		this.listaComprobanteTmp = this.serviceComprobante.listarComprobantePorIdCierre(id);
		totalComprobantes();
	}

	// sumar comprobantes
	public void totalComprobantes() {
		for (Comprobante comp : listaComprobante) {
			if (comp.isCierre_b()) {
				totaldet = totaldet + comp.getValor();
			}
		}
	}

	public void totalValor(boolean est, int mont) {
		if (est) {
			totaldet = totaldet + mont;
		} else {
			totaldet = totaldet - mont;
		}
	}

	// guarda cierre
	@Transactional
	public void modificarCierre() {
		try {
			if (listaComprobante.size() > 0) {
				cierre.setValor(totaldet);
				serviceCierre.modificar(cierre);
				//// AUDITORIA DE CIERRES
				if (valorAnterior != totaldet) {
					serviceAuditoria.insertaModificacion("Cierre", "Valor", "U", usuPunto.getUsuario().getUsername(),
							fechaActual, String.valueOf(totaldet), String.valueOf(valorAnterior), cierre.getId());
					
				}
				///
				for (Comprobante compc : listaComprobante) {
					if (compc.isCierre_b()) {
						compc.setCierre_b(compc.isCierre_b());
						serviceComprobante.modificar(compc);
					} else {
						compc.setCierre_b(false);
						serviceComprobante.modificar(compc);
					}
				}
				for (Comprobante compc : listaComprobante) {
					for (Comprobante compctmp : listaComprobanteTmp) {
						if (compc.isCierre_b()!=compctmp.isCierre_b() && compc.getId().equals(compctmp.getId())) {
							serviceAuditoria.insertaModificacion("Comprobante", "cierre_b", "U", usuPunto.getUsuario().getUsername(),
									fechaActual, String.valueOf(compc.isCierre_b()), String.valueOf(compctmp.isCierre_b()), compc.getId());
							
						}
					}
				}

				estadeshabilitado = true;
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "se guarda satisfactoriamente", "Aviso"));
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
						"No dispone de comprobantes para hacer el cierre", "ERROR"));
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	// getter y setter

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public double getValorAnterior() {
		return valorAnterior;
	}

	public void setValorAnterior(double valorAnterior) {
		this.valorAnterior = valorAnterior;
	}
	
	

}
