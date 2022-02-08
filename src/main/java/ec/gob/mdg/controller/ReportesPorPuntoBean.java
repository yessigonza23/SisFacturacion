package ec.gob.mdg.controller;

import java.io.File;
import java.io.FileOutputStream;
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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

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
					nombreReporte = "RepPorPuntoFactGeneral.jasper";
					nombreGuarda = "RepPorPuntoFactGeneral";
				} else if (opcion.equals("2")) {
					nombreReporte = "RepPorPuntoFactCodBanco.jasper";
					nombreGuarda = "RepPorPuntoFactCodBanco";
				} else if (opcion.equals("3")) {
					nombreReporte = "RepPorPuntoFactAnuladas.jasper";
					nombreGuarda = "RepPorPuntoFactAnuladas";
				} else if (opcion.equals("4")) {
					nombreReporte = "RepPorPuntoFactNoAutorizadas.jasper";
					nombreGuarda = "RepPorPuntoFactNoAutorizadas";
				} else if (opcion.equals("5")) {
					nombreReporte = "RepPorPuntoCruceFacturasNotas.jasper";
					nombreGuarda = "RepPorPuntoCruceFacturasNotas";
				} else if (opcion.equals("6")) {
					nombreReporte = "RepPorPuntoCierre.jasper";
					nombreGuarda = "RepPorPuntoCierre";
				} else if (opcion.equals("7")) {
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
		}
	}

	////////////////////////////////////////////////////////

	int contador;
	int contadorc1;
	private HSSFWorkbook libro;

	// Generar archivo xls
	// LISTAR VISTA RECAUDACION Reporte 1
	@SuppressWarnings("unused")
	public void xlsRecaudacion() {

		libro = new HSSFWorkbook();
		HSSFSheet hoja = libro.createSheet();
		// this.listaVistaRecaudacionDTO =
		// this.serviceVistaRecaudacionDTO.listarRecaudacionDetalle(fecha_inicio,
		// fecha_fin, nombre.getId());
		/////////////////////////
		contador = 0;
		HSSFRow fila = hoja.createRow((short) contador);

		HSSFCell celdaFact = fila.createCell((short) 0);
		HSSFRichTextString factura = new HSSFRichTextString(String.valueOf("Factura"));
		celdaFact.setCellValue(factura);

		HSSFCell celdaFecha = fila.createCell((short) 1);
		HSSFRichTextString fecha = new HSSFRichTextString(String.valueOf("Fecha de Emisión"));
		celdaFecha.setCellValue(fecha);

		HSSFCell celdaCliente = fila.createCell((short) 2);
		HSSFRichTextString cliente = new HSSFRichTextString("Cliente");
		celdaCliente.setCellValue(cliente);

		HSSFCell celdaRuc = fila.createCell((short) 3);
		HSSFRichTextString ruc = new HSSFRichTextString("Ci/Ruc/Pasaporte");
		celdaRuc.setCellValue(ruc);

		HSSFCell celdaValor = fila.createCell((short) 4);
		HSSFRichTextString valor = new HSSFRichTextString("Valor");
		celdaValor.setCellValue(valor);

		HSSFCell celdaCodRecaudacion = fila.createCell((short) 5);
		HSSFRichTextString CodRecaudacion = new HSSFRichTextString("Codigo Recaudación");
		celdaCodRecaudacion.setCellValue(CodRecaudacion);

		HSSFCell celdaCodBanco = fila.createCell((short) 6);
		HSSFRichTextString CodBanco = new HSSFRichTextString("Código Banco");
		celdaCodBanco.setCellValue(CodBanco);

		HSSFCell celdaResponsable = fila.createCell((short) 7);
		HSSFRichTextString Responsable = new HSSFRichTextString("Responsable");
		celdaResponsable.setCellValue(Responsable);

		HSSFCell celdaPunto = fila.createCell((short) 8);
		HSSFRichTextString Punto = new HSSFRichTextString("Punto Recaudación");
		celdaPunto.setCellValue(Punto);
		////////////////////////////////
		contador = 1;
		for (VistaRecaudacionDTO v : listaVistaRecaudacionDTO) {
			fila = hoja.createRow((short) contador);

			for (VistaRecaudacionDTO v1 : listaVistaRecaudacionDTO) {

				celdaFact = fila.createCell((short) 0);
				Integer fac = v.getComp_numero();
				celdaFact.setCellValue(fac);

				celdaFecha = fila.createCell((short) 1);
				fecha = new HSSFRichTextString(String.valueOf(v.getComp_fechaemision()));
				celdaFecha.setCellValue(fecha);

				celdaCliente = fila.createCell((short) 2);
				cliente = new HSSFRichTextString(v.getCliente_nombre());
				celdaCliente.setCellValue(cliente);

				celdaRuc = fila.createCell((short) 3);
				ruc = new HSSFRichTextString(v.getCliente_ci());
				celdaRuc.setCellValue(ruc);

				celdaValor = fila.createCell((short) 4);
				Double compvalor = v.getComp_valor();
				celdaValor.setCellValue(compvalor);

				celdaCodRecaudacion = fila.createCell((short) 5);
				CodRecaudacion = new HSSFRichTextString(String.valueOf(v.getRecdetalle_codigo()));
				celdaCodRecaudacion.setCellValue(CodRecaudacion);

				celdaCodBanco = fila.createCell((short) 6);
				CodBanco = new HSSFRichTextString(String.valueOf(v.getRecaudacion_codigobanco()));
				celdaCodBanco.setCellValue(CodBanco);

				celdaResponsable = fila.createCell((short) 7);
				Responsable = new HSSFRichTextString(String.valueOf(v.getUsuario_nombre()));
				celdaResponsable.setCellValue(Responsable);

				celdaPunto = fila.createCell((short) 8);
				Punto = new HSSFRichTextString(String.valueOf(v.getPunto_nombre()));
				celdaPunto.setCellValue(Punto);

			}
			contador++;
		}

		try {

			FileOutputStream elFichero = new FileOutputStream(UtilsArchivos.getRutaReportes() + "RecDetalle"+ nombre.getId() +".xls");																									// ".xls");
			libro.write(elFichero);
			elFichero.close();
			
			String archivo="RecDetalle"+ nombre.getId() +".xls";
			UtilsArchivos.verXls(archivo);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/////REPORTE 2 RECAUDACIONES POR DEPOSITOS
	@SuppressWarnings("unused")
	public void xlsRecaudacionDep() {

		libro = new HSSFWorkbook();
		HSSFSheet hoja = libro.createSheet();
		/////////////////////////
		contador = 0;
		HSSFRow fila = hoja.createRow((short) contador);

		HSSFCell celdaFact = fila.createCell((short) 0);
		HSSFRichTextString factura = new HSSFRichTextString(String.valueOf("Factura"));
		celdaFact.setCellValue(factura);

		HSSFCell celdaFecha = fila.createCell((short) 1);
		HSSFRichTextString fecha = new HSSFRichTextString(String.valueOf("Fecha de Emisión"));
		celdaFecha.setCellValue(fecha);

		HSSFCell celdaCliente = fila.createCell((short) 2);
		HSSFRichTextString cliente = new HSSFRichTextString("Cliente");
		celdaCliente.setCellValue(cliente);

		HSSFCell celdaRuc = fila.createCell((short) 3);
		HSSFRichTextString ruc = new HSSFRichTextString("Ci/Ruc/Pasaporte");
		celdaRuc.setCellValue(ruc);
		
		HSSFCell celdaNumDeposito = fila.createCell((short) 4);
		HSSFRichTextString NumDeposito = new HSSFRichTextString("No. Depositos");
		celdaNumDeposito.setCellValue(NumDeposito);

		HSSFCell celdaFecDeposito = fila.createCell((short) 5);
		HSSFRichTextString FecDeposito = new HSSFRichTextString("Fecha Depósitos");
		celdaFecDeposito.setCellValue(FecDeposito);

		HSSFCell celdaValor = fila.createCell((short) 6);
		HSSFRichTextString valor = new HSSFRichTextString("Valor");
		celdaValor.setCellValue(valor);		

		HSSFCell celdaResponsable = fila.createCell((short) 7);
		HSSFRichTextString Responsable = new HSSFRichTextString("Responsable");
		celdaResponsable.setCellValue(Responsable);

		HSSFCell celdaPunto = fila.createCell((short) 8);
		HSSFRichTextString Punto = new HSSFRichTextString("Punto Recaudación");
		celdaPunto.setCellValue(Punto);
		////////////////////////////////
		contador = 1;
		for (VistaRecaudacionDepositoDTO v : listaVistaRecaudacionDepositoDTO) {
			fila = hoja.createRow((short) contador);

			for (VistaRecaudacionDepositoDTO v1 : listaVistaRecaudacionDepositoDTO) {

				celdaFact = fila.createCell((short) 0);
				Integer fac = v.getComp_numero();
				celdaFact.setCellValue(fac);

				celdaFecha = fila.createCell((short) 1);
				fecha = new HSSFRichTextString(String.valueOf(v.getComp_fechaemision()));
				celdaFecha.setCellValue(fecha);

				celdaCliente = fila.createCell((short) 2);
				cliente = new HSSFRichTextString(v.getCliente_nombre());
				celdaCliente.setCellValue(cliente);

				celdaRuc = fila.createCell((short) 3);
				ruc = new HSSFRichTextString(v.getCliente_ci());
				celdaRuc.setCellValue(ruc);

				celdaNumDeposito = fila.createCell((short) 4);
				NumDeposito = new HSSFRichTextString(String.valueOf(v.getDeposito_numero()));
				celdaNumDeposito.setCellValue(NumDeposito);

				celdaFecDeposito = fila.createCell((short) 5);
				FecDeposito = new HSSFRichTextString(String.valueOf(v.getDeposito_fecha()));
				celdaFecDeposito.setCellValue(FecDeposito);
				
				celdaValor = fila.createCell((short) 6);
				Double compvalor = v.getComp_valor();
				celdaValor.setCellValue(compvalor);

				celdaResponsable = fila.createCell((short) 7);
				Responsable = new HSSFRichTextString(String.valueOf(v.getUsuario_nombre()));
				celdaResponsable.setCellValue(Responsable);

				celdaPunto = fila.createCell((short) 8);
				Punto = new HSSFRichTextString(String.valueOf(v.getPunto_nombre()));
				celdaPunto.setCellValue(Punto);

			}
			contador++;
		}

		try {

			FileOutputStream elFichero = new FileOutputStream(UtilsArchivos.getRutaReportes() + "RecDeposito"+ nombre.getId() +".xls");																									// ".xls");
			libro.write(elFichero);
			elFichero.close();
			
			String archivo="RecDeposito"+ nombre.getId() +".xls";
			UtilsArchivos.verXls(archivo);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

     /////REPORTE 3 CIERRE DE CAJA
	@SuppressWarnings("unused")
	public void xlsCierreCaja() {

		libro = new HSSFWorkbook();
		HSSFSheet hoja = libro.createSheet();
		/////////////////////////
		contador = 0;
		HSSFRow fila = hoja.createRow((short) contador);

		HSSFCell celdaCierre = fila.createCell((short) 0);
		HSSFRichTextString cierre = new HSSFRichTextString(String.valueOf("No. Cierre"));
		celdaCierre.setCellValue(cierre);
		
		HSSFCell celdaFact = fila.createCell((short) 1);
		HSSFRichTextString factura = new HSSFRichTextString(String.valueOf("Factura"));
		celdaFact.setCellValue(factura);

		HSSFCell celdaFecha = fila.createCell((short) 2);
		HSSFRichTextString fecha = new HSSFRichTextString(String.valueOf("Fecha de Emisión"));
		celdaFecha.setCellValue(fecha);

		HSSFCell celdaValor = fila.createCell((short) 3);
		HSSFRichTextString valor = new HSSFRichTextString("Valor");
		celdaValor.setCellValue(valor);		

		HSSFCell celdaEstado = fila.createCell((short) 4);
		HSSFRichTextString Estado = new HSSFRichTextString("Estado");
		celdaEstado.setCellValue(Estado);

		HSSFCell celdaPunto = fila.createCell((short) 5);
		HSSFRichTextString Punto = new HSSFRichTextString("Punto Recaudación");
		celdaPunto.setCellValue(Punto);
		////////////////////////////////
		contador = 1;
		for (VistaCierreDTO v : listaVistaCierreDTO) {
			fila = hoja.createRow((short) contador);

			for (VistaCierreDTO v1 : listaVistaCierreDTO) {

				celdaCierre = fila.createCell((short) 0);
				celdaFact.setCellValue(v.getId_cierre());
				
				celdaFact = fila.createCell((short) 1);
				Integer fac = v.getNumeroFactura();
				celdaFact.setCellValue(fac);

				celdaFecha = fila.createCell((short) 2);
				fecha = new HSSFRichTextString(String.valueOf(v.getFechaemision()));
				celdaFecha.setCellValue(fecha);

				celdaValor = fila.createCell((short) 3);
				Double compvalor = v.getValor();
				celdaValor.setCellValue(compvalor);
				
				celdaEstado = fila.createCell((short) 4);
				Estado = new HSSFRichTextString(String.valueOf(v.getEstado()));
				celdaEstado.setCellValue(Estado);


				celdaPunto = fila.createCell((short) 5);
				Punto = new HSSFRichTextString(String.valueOf(v.getPuntoRecaudacion()));
				celdaPunto.setCellValue(Punto);

			}
			contador++;
		}

		try {

			FileOutputStream elFichero = new FileOutputStream(UtilsArchivos.getRutaReportes() + "RecCierre"+ nombre.getId() +".xls");																									// ".xls");
			libro.write(elFichero);
			elFichero.close();
			
			String archivo="RecCierre"+ nombre.getId() +".xls";
			UtilsArchivos.verXls(archivo);

		} catch (Exception e) {
			e.printStackTrace();
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
