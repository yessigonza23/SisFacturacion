package ec.gob.mdg.controller;

import java.io.File;
import java.io.Serializable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import ec.gob.mdg.model.Cliente;
import ec.gob.mdg.model.Comprobante;
import ec.gob.mdg.service.IComprobanteService;
import ec.gob.mdg.util.UtilsArchivos;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

@Named
@ViewScoped
public class ImprimirFacturaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Resource(mappedName = "java:/finanDS")
	DataSource datasource;

	@Inject
	private IComprobanteService service;

	private List<Comprobante> listaComprobante = new ArrayList<Comprobante>();
	private Cliente cliente;
	String path;
	String nombreReporte ;
	
	String rutaImagenLogo;
	String rutaImagenLogoPie;
	String rutaImagenLogoAnulado;
	String pathJasper;
	
	public void imprimirFactura(Comprobante comprobante) {
		
		try {
			generarReporte(comprobante);

		} catch (Exception e) {
			System.out.println("error , revise");
		}
	}

	public void generarReporte(Comprobante comprobante) {
		try {
			
			rutaImagenLogo = UtilsArchivos.getRutaLogo()+ "logomdg.png";
			rutaImagenLogoPie = UtilsArchivos.getRutaLogo()+ "footer.png";
			rutaImagenLogoAnulado = UtilsArchivos.getRutaLogo()+ "anulada.jpg";
			pathJasper = UtilsArchivos.getRutaReportesJasper() ;
	//		String pathPdf = UtilsArchivos.getRutaReportes() ;		
			
			//String nombreReporte = "Factura_Off_Postgresql.jasper";

			
			comprobante = service.listarComprobantePorId(comprobante.getId());
			if(comprobante.getEstado().equals("N")) {
				nombreReporte = "Factura_Off_Postgresql_Anulada.jasper";
			}else {
				nombreReporte = "Factura_Off_Postgresql.jasper";
			}
				
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("NUMEROFACTURA", comprobante.getNumero());
			parametros.put("REGIONAL", comprobante.getPuntoRecaudacion().getId());
			parametros.put("LOGO", rutaImagenLogo);
			parametros.put("LOGOPIE", rutaImagenLogoPie);
			parametros.put("ANULADA", rutaImagenLogoAnulado);
			

			Connection conn = datasource.getConnection();
			///CAMBIO POR RUTA DONDE SE ENCUENTRA ALMACEADO EL REPORTE///

			File jasper = new File( pathJasper + nombreReporte);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, conn);

			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.addHeader("Content-disposition", "attachment; filename=" + comprobante.getClaveacceso() + ".pdf");

			path = UtilsArchivos.getRutaFirmados();

			JasperExportManager.exportReportToPdfFile(jasperPrint, path + comprobante.getClaveacceso() + ".pdf");

			
			ServletOutputStream stream = response.getOutputStream();
			JasperExportManager.exportReportToPdfStream(jasperPrint, stream);

			stream.flush();
			stream.close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	//// IMPRIMIR NOTAS DE CREDITO
	public void imprimirNotas(Comprobante comprobante) {
		try {
			generarReporteNotas(comprobante);

		} catch (Exception e) {
			System.out.println("error , revise");
		}
	}

	public void generarReporteNotas(Comprobante comprobante) {
		try {
			rutaImagenLogo = UtilsArchivos.getRutaLogo()+ "logomdg.png";
			rutaImagenLogoPie = UtilsArchivos.getRutaLogo()+ "footer.png";
			rutaImagenLogoAnulado = UtilsArchivos.getRutaLogo()+ "anulada.jpg";
			pathJasper = UtilsArchivos.getRutaReportesJasper() ;
			
			comprobante = service.listarComprobanteNotaPorId(comprobante.getId());
						
			nombreReporte = "NotaCredito.jasper";
						
			Map<String, Object> parametros = new HashMap<String, Object>();
			parametros.put("NUMEROFACTURA", comprobante.getNumero());
			parametros.put("REGIONAL", comprobante.getPuntoRecaudacion().getId());
			parametros.put("LOGO", rutaImagenLogo);
			parametros.put("LOGOPIE", rutaImagenLogoPie);
			parametros.put("ANULADA", rutaImagenLogoAnulado);
			
			
			Connection conn = datasource.getConnection();
			File jasper = new File( pathJasper + nombreReporte);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(), parametros, conn);
			
			HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext()
					.getResponse();
			response.addHeader("Content-disposition", "attachment; filename=" + comprobante.getClaveacceso() + ".pdf");
						
			path = UtilsArchivos.getRutaFirmados();
			JasperExportManager.exportReportToPdfFile(jasperPrint, path + comprobante.getClaveacceso() + ".pdf");
				
            
			ServletOutputStream stream = response.getOutputStream();
			
			JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
			stream.flush();
			stream.close();
			FacesContext.getCurrentInstance().responseComplete();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	// GETTERS Y SETTERS

	public List<Comprobante> getListaComprobante() {
		return listaComprobante;
	}

	public void setListaComprobante(List<Comprobante> listaComprobante) {
		this.listaComprobante = listaComprobante;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

}
