package ec.gob.mdg.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.gob.mdg.model.PuntoRecaudacion;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.model.VistaCierreDTO;
import ec.gob.mdg.model.VistaConciliacionValNoFacturadosDTO;
import ec.gob.mdg.model.VistaRecaudacionDTO;
import ec.gob.mdg.model.VistaRecaudacionDepositoDTO;
import ec.gob.mdg.service.IPuntoRecaudacionService;
import ec.gob.mdg.service.IUsuarioPuntoService;
import ec.gob.mdg.service.IVistaConciliacionValNoFacturadosDTOService;
import ec.gob.mdg.service.IVistaRecaudacioDepositoDTOService;

@Named
@ViewScoped
public class ConsolidacionXlsNacionalBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IUsuarioPuntoService serviceUsuPunto;

	@Inject
	private IPuntoRecaudacionService servicePuntoRecaudacion;

	@Inject
	private IVistaRecaudacioDepositoDTOService serviceVistaRecaudacionDepositoDTO;

	@Inject
	private IVistaConciliacionValNoFacturadosDTOService serviceVistaConciliacionValNoFacturadosDTO;

	private List<VistaCierreDTO> listaVistaCierreDTO = new ArrayList<VistaCierreDTO>();
	private List<VistaRecaudacionDTO> listaVistaRecaudacionDTO = new ArrayList<>();
	private List<VistaRecaudacionDepositoDTO> listaVistaRecaudacionDepositoDTO = new ArrayList<>();
	private List<VistaRecaudacionDepositoDTO> listaVistaRecaudacionDepositoDTO1 = new ArrayList<>();
	private List<VistaConciliacionValNoFacturadosDTO> listaVistaonciliacionValNoFacturadosDTO = new ArrayList<>();
	private List<VistaRecaudacionDepositoDTO> listaVistaRecaudacionDepositoDTO2 = new ArrayList<>();
	private List<VistaRecaudacionDepositoDTO> listaVistaRecaudacionDepositoDTO3 = new ArrayList<>();
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
	double totalB4 = 0.00;

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
		this.listaVistaRecaudacionDepositoDTO = this.serviceVistaRecaudacionDepositoDTO
				.listarConsultaBancoVsBancoNacional(anio, mes);
		totalFacturas();
	}

	public void totalFacturas() {
		totalF = 0.0;
		totalB = 0.0;
		for (VistaRecaudacionDepositoDTO v : listaVistaRecaudacionDepositoDTO) {
			totalF = totalF + v.getDeposito_valor();
			totalB = totalB + v.getEstadocuenta_valor();
		}
	}

	// LISTAR CONSULTA DE DEPOSITOS QUE NO CONSTA EN EL ESTADO DE CUENTA
	public void listarDepositosNoBanco() {
		this.listaVistaRecaudacionDepositoDTO1 = this.serviceVistaRecaudacionDepositoDTO
				.listarDepositosNoBancoNacional( anio, mes);
		totalFacturas2();
	}

	public void totalFacturas2() {
		totalF2 = 0.0;
		for (VistaRecaudacionDepositoDTO v : listaVistaRecaudacionDepositoDTO1) {
			totalF2 = totalF2 + v.getDeposito_valor();
		}
	}

	// LISTAR CONSULTA DE VALORES NO FACTURADOS DEL ESTADO DE CUENTA
	public void listarValoresNoFacturados() {
		this.listaVistaonciliacionValNoFacturadosDTO = this.serviceVistaConciliacionValNoFacturadosDTO
				.listarValoresNoFacturados(anio, mes);
		totalDepositos();
	}

	public void totalDepositos() {
		totalDep = 0.0;
		for (VistaConciliacionValNoFacturadosDTO v : listaVistaonciliacionValNoFacturadosDTO) {
			totalDep = totalDep + v.getValor();
		}
	}

	///// LISTAR CONSULTA DE DEPOSITOS EN VARIAS FACTURAS
	public void listarDepositosVariasFacturas() {
		this.listaVistaRecaudacionDepositoDTO2 = this.serviceVistaRecaudacionDepositoDTO
				.listarDepositosVariasFacturas(nombre.getId(), anio, mes);
	}

   ////LISTAR CONSULTA ESTADO DE CUENTA SIN CONCIALIAR 
	public void listarSinConciliar() {
		this.listaVistaRecaudacionDepositoDTO3 = this.serviceVistaRecaudacionDepositoDTO
				.listarEstadoSinColidar(nombre.getId(), anio, mes);
		totalFacturas4();
	}

	public void totalFacturas4() {
		totalF4 = 0.0;
		totalB4 = 0.0;
		for (VistaRecaudacionDepositoDTO v : listaVistaRecaudacionDepositoDTO) {
			totalF4 = totalF4 + v.getDeposito_valor();
			totalB4 = totalB4 + v.getEstadocuenta_valor();
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

}
