package ec.gob.mdg.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.gob.mdg.model.PuntoRecaudacion;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.model.VistaCierreDTO;
import ec.gob.mdg.model.VistaConciliacionCompDepositoEstcCUentaDTO;
import ec.gob.mdg.model.VistaConciliacionValNoFacturadosDTO;
import ec.gob.mdg.model.VistaRecaudacionDTO;
import ec.gob.mdg.model.VistaRecaudacionDepositoDTO;
import ec.gob.mdg.service.IPuntoRecaudacionService;
import ec.gob.mdg.service.IUsuarioPuntoService;
import ec.gob.mdg.service.IVistaConciliacionCompDepositoEstcCuentaDTOService;
import ec.gob.mdg.service.IVistaConciliacionValNoFacturadosDTOService;
import ec.gob.mdg.service.IVistaRecaudacioDepositoDTOService;
import ec.gob.mdg.validaciones.FunValidaciones;

@Named
@ViewScoped
public class ConsolidacionPorPuntoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IUsuarioPuntoService serviceUsuPunto;

	@Inject
	private IPuntoRecaudacionService servicePuntoRecaudacion;

	@Inject
	private IVistaRecaudacioDepositoDTOService serviceVistaRecaudacionDepositoDTO;

	@Inject
	private IVistaConciliacionValNoFacturadosDTOService serviceVistaConciliacionValNoFacturadosDTO;

	@Inject
	private IVistaConciliacionCompDepositoEstcCuentaDTOService serviceVistaConciliacionCompDepositoEstcCuentaDTO;

	private List<VistaCierreDTO> listaVistaCierreDTO = new ArrayList<VistaCierreDTO>();
	private List<VistaRecaudacionDTO> listaVistaRecaudacionDTO = new ArrayList<>();
	private List<VistaRecaudacionDepositoDTO> listaVistaRecaudacionDepositoDTO = new ArrayList<>();
	private List<VistaRecaudacionDepositoDTO> listaVistaRecaudacionDepositoDTO1 = new ArrayList<>();
	private List<VistaConciliacionValNoFacturadosDTO> listaVistaonciliacionValNoFacturadosDTO = new ArrayList<>();
	private List<VistaRecaudacionDepositoDTO> listaVistaRecaudacionDepositoDTO2 = new ArrayList<>();
	private List<VistaRecaudacionDepositoDTO> listaVistaRecaudacionDepositoDTO3 = new ArrayList<>();
	private List<VistaConciliacionCompDepositoEstcCUentaDTO> listaVistaConciliacionCompDepositoEstcCuentaDTO = new ArrayList<>();
	PuntoRecaudacion punto;
	PuntoRecaudacion nombre;

	Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	private UsuarioPunto usuPunto = new UsuarioPunto();

	Integer anio;
	Integer mes;

	// totales cosolidacion 1
	double totalF = 0.00;
	double totalB = 0.00;

	// consolidacion 2
	double totalF2 = 0.00;
	// consolidación 3
	double totalDep = 0.00;
	// totales cosolidacion 4
	double totalF4 = 0.00;
	// total comprobantes consolidados
	double totalB4 = 0.00;
	double totalB5 = 0.00;
	double totalB6 = 0.00;

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

	//// LISTAR CONSULTA DE COMPROBANTES VS BANCO
	public void listarComprobantesVsBanco() {

		if (nombre.getId() != null && anio != null && mes != null) {
			this.listaVistaRecaudacionDepositoDTO = this.serviceVistaRecaudacionDepositoDTO
					.listarConsultaBancoVsBanco(nombre.getId(), anio, mes);
			totalFacturas();
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sin Datos", "Especifique la información"));
		}
	}

	public void totalFacturas() {
		totalF = 0.0;
		totalB = 0.0;

		if (listaVistaRecaudacionDepositoDTO != null) {
			for (VistaRecaudacionDepositoDTO v : listaVistaRecaudacionDepositoDTO) {
				totalF = totalF + v.getDeposito_valor();
				totalF = FunValidaciones.formatearDecimales(totalF, 2);
				totalB = totalB + v.getEstadocuenta_valor();
				totalB = FunValidaciones.formatearDecimales(totalB, 2);
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sin Datos", "Especifique la información"));
		}

	}

	// LISTAR CONSULTA DE DEPOSITOS QUE NO CONSTA EN EL ESTADO DE CUENTA
	@SuppressWarnings("unused")
	public void listarDepositosNoBanco() {
		if (listaVistaRecaudacionDepositoDTO != null) {
			for (VistaRecaudacionDepositoDTO v : listaVistaRecaudacionDepositoDTO1) {
				this.listaVistaRecaudacionDepositoDTO1 = this.serviceVistaRecaudacionDepositoDTO
						.listarDepositosNoBanco(nombre.getId(), anio, mes);
				totalFacturas2();
			}

		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sin Datos", "Especifique la información"));
		}
	}

	public void totalFacturas2() {
		totalF2 = 0.0;
		if (listaVistaRecaudacionDepositoDTO1 != null) {
			for (VistaRecaudacionDepositoDTO v : listaVistaRecaudacionDepositoDTO1) {
				totalF2 = totalF2 + v.getDeposito_valor();
				totalF2 = FunValidaciones.formatearDecimales(totalF2, 2);
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sin Datos", "Especifique la información"));
		}

	}

	// LISTAR CONSULTA DE VALORES NO FACTURADOS DEL ESTADO DE CUENTA
	public void listarValoresNoFacturados() {
				
		if (anio != null && mes != null) {
			this.listaVistaonciliacionValNoFacturadosDTO = this.serviceVistaConciliacionValNoFacturadosDTO
					.listarValoresNoFacturados(anio, mes);
			totalDepositos();
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sin Datos", "Especifique la información"));
		}
		
	}

	public void totalDepositos() {
		if (listaVistaonciliacionValNoFacturadosDTO != null) {
			totalDep = 0.0;
			for (VistaConciliacionValNoFacturadosDTO v : listaVistaonciliacionValNoFacturadosDTO) {
				totalDep = totalDep + v.getValor();
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sin Datos", "Especifique la información"));
		}
		
	}

	///// LISTAR CONSULTA DE DEPOSITOS EN VARIAS FACTURAS
	public void listarDepositosVariasFacturas() {

		if (nombre.getId() != null && anio != null && mes != null) {
			this.listaVistaRecaudacionDepositoDTO2 = this.serviceVistaRecaudacionDepositoDTO
					.listarDepositosVariasFacturas(nombre.getId(), anio, mes);
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sin Datos", "Especifique la información"));
		}
	}

	//// LISTAR CONSULTA ESTADO DE CUENTA SIN CONCIALIAR
	public void listarSinConciliar() {
		if (nombre.getId() != null && anio != null && mes != null) {
			this.listaVistaRecaudacionDepositoDTO3 = this.serviceVistaRecaudacionDepositoDTO
					.listarEstadoSinColidar(nombre.getId(), anio, mes);
			totalFacturas4();
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sin Datos", "Especifique la información"));
		}

	}

	public void totalFacturas4() {
		if (listaVistaRecaudacionDepositoDTO3 != null) {
			totalF4 = 0.0;
			for (VistaRecaudacionDepositoDTO v : listaVistaRecaudacionDepositoDTO3) {
				totalF4 = totalF4 + v.getDeposito_valor();
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sin Datos", "Especifique la información"));
		}	
		
	}

	/// LISTAR COMPROBANTES CONSOLIDADOS
	public void listarConsolidados() {
		
		if (listaVistaConciliacionCompDepositoEstcCuentaDTO != null) {
			this.listaVistaConciliacionCompDepositoEstcCuentaDTO = this.serviceVistaConciliacionCompDepositoEstcCuentaDTO
					.listarConsolidados(nombre.getId(), anio, mes);
			totalFacturasCon();
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sin Datos", "Especifique la información"));
		}	
	}

	public void totalFacturasCon() {
	
		
		if (listaVistaConciliacionCompDepositoEstcCuentaDTO != null) {
			totalB4 = 0.0;
			totalB5 = 0.0;
			totalB6 = 0.0;
			for (VistaConciliacionCompDepositoEstcCUentaDTO v : listaVistaConciliacionCompDepositoEstcCuentaDTO) {
				totalB4 = totalB4 + v.getDeposito_valor();
				totalB5 = totalB5 + v.getEstadocuenta_valor();
				totalB6 = totalB6 + v.getEstadocuenta_saldo();
			}
			
		} else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sin Datos", "Especifique la información"));
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

	public double getTotalF() {
		return totalF;
	}

	public void setTotalF(double totalF) {
		this.totalF = totalF;
	}

	public double getTotalB() {
		return totalB;
	}

	public void setTotalB(double totalB) {
		this.totalB = totalB;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public List<VistaRecaudacionDepositoDTO> getListaVistaRecaudacionDepositoDTO1() {
		return listaVistaRecaudacionDepositoDTO1;
	}

	public void setListaVistaRecaudacionDepositoDTO1(
			List<VistaRecaudacionDepositoDTO> listaVistaRecaudacionDepositoDTO1) {
		this.listaVistaRecaudacionDepositoDTO1 = listaVistaRecaudacionDepositoDTO1;
	}

	public double getTotalF2() {
		return totalF2;
	}

	public void setTotalF2(double totalF2) {
		this.totalF2 = totalF2;
	}

	public List<VistaConciliacionValNoFacturadosDTO> getListaVistaonciliacionValNoFacturadosDTO() {
		return listaVistaonciliacionValNoFacturadosDTO;
	}

	public void setListaVistaonciliacionValNoFacturadosDTO(
			List<VistaConciliacionValNoFacturadosDTO> listaVistaonciliacionValNoFacturadosDTO) {
		this.listaVistaonciliacionValNoFacturadosDTO = listaVistaonciliacionValNoFacturadosDTO;
	}

	public double getTotalDep() {
		return totalDep;
	}

	public void setTotalDep(double totalDep) {
		this.totalDep = totalDep;
	}

	public List<VistaRecaudacionDepositoDTO> getListaVistaRecaudacionDepositoDTO2() {
		return listaVistaRecaudacionDepositoDTO2;
	}

	public void setListaVistaRecaudacionDepositoDTO2(
			List<VistaRecaudacionDepositoDTO> listaVistaRecaudacionDepositoDTO2) {
		this.listaVistaRecaudacionDepositoDTO2 = listaVistaRecaudacionDepositoDTO2;
	}

	public List<VistaRecaudacionDepositoDTO> getListaVistaRecaudacionDepositoDTO3() {
		return listaVistaRecaudacionDepositoDTO3;
	}

	public void setListaVistaRecaudacionDepositoDTO3(
			List<VistaRecaudacionDepositoDTO> listaVistaRecaudacionDepositoDTO3) {
		this.listaVistaRecaudacionDepositoDTO3 = listaVistaRecaudacionDepositoDTO3;
	}

	public double getTotalF4() {
		return totalF4;
	}

	public void setTotalF4(double totalF4) {
		this.totalF4 = totalF4;
	}

	public double getTotalB4() {
		return totalB4;
	}

	public void setTotalB4(double totalB4) {
		this.totalB4 = totalB4;
	}

	public List<VistaConciliacionCompDepositoEstcCUentaDTO> getListaVistaConciliacionCompDepositoEstcCuentaDTO() {
		return listaVistaConciliacionCompDepositoEstcCuentaDTO;
	}

	public void setListaVistaConciliacionCompDepositoEstcCuentaDTO(
			List<VistaConciliacionCompDepositoEstcCUentaDTO> listaVistaConciliacionCompDepositoEstcCuentaDTO) {
		this.listaVistaConciliacionCompDepositoEstcCuentaDTO = listaVistaConciliacionCompDepositoEstcCuentaDTO;
	}

	public double getTotalB5() {
		return totalB5;
	}

	public void setTotalB5(double totalB5) {
		this.totalB5 = totalB5;
	}

	public double getTotalB6() {
		return totalB6;
	}

	public void setTotalB6(double totalB6) {
		this.totalB6 = totalB6;
	}

}
