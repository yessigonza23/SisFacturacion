package ec.gob.mdg.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.gob.mdg.model.Comprobante;
import ec.gob.mdg.model.EstadoCuenta;
import ec.gob.mdg.model.PuntoRecaudacion;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.model.VistaRecaudacionDepositoDTO;
import ec.gob.mdg.service.IComprobanteService;
import ec.gob.mdg.service.IEstadoCuentaService;
import ec.gob.mdg.service.IPuntoRecaudacionService;
import ec.gob.mdg.service.IUsuarioPuntoService;
import ec.gob.mdg.service.IVistaRecaudacioDepositoDTOService;

@Named
@ViewScoped
public class ConsolidacionNacionalBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IUsuarioPuntoService serviceUsuPunto;

	@Inject
	private IPuntoRecaudacionService servicePuntoRecaudacion;

	@Inject
	private IVistaRecaudacioDepositoDTOService serviceVistaRecaudacionDepositoDTO;

	@Inject
	private IEstadoCuentaService serviceEstadoCuenta;
	
	@Inject
	private IComprobanteService serviceComprobante;

	private List<EstadoCuenta> listaEstadoCuenta = new ArrayList<EstadoCuenta>();
	private List<VistaRecaudacionDepositoDTO> listaVistaRecaudacionDepositoDTO = new ArrayList<>();
	private List<Comprobante> listaComprobantes = new ArrayList<>();
	PuntoRecaudacion punto;
	PuntoRecaudacion nombre;

	Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	private UsuarioPunto usuPunto = new UsuarioPunto();

	Integer anio;
	Integer mes;

	
	@PostConstruct
	public void init() {
		try {
			usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(us);
			punto = usuPunto.getPuntoRecaudacion();
			nombre = servicePuntoRecaudacion.listarPorId(punto);
			listarComprobantes();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/////LISTAR ESTADO DE CUENTA
	public void listarEstado() {
		
		listaEstadoCuenta = serviceEstadoCuenta.listarEstadoCuentaCargado(anio, mes);
	}
		
	////LISTAR COMPROBANTE DEPOSITO
	public void listarVistaDepositos() {
	
		listaVistaRecaudacionDepositoDTO = serviceVistaRecaudacionDepositoDTO.listarVista(anio, mes);
	}

	
	///LISTAR COMPROBANTES
	public void listarComprobantes() {
		try {
			listaComprobantes = serviceComprobante.listar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	////GETTERS Y SETTERS/
	public List<EstadoCuenta> getListaEstadoCuenta() {
		return listaEstadoCuenta;
	}

	public void setListaEstadoCuenta(List<EstadoCuenta> listaEstadoCuenta) {
		this.listaEstadoCuenta = listaEstadoCuenta;
	}

	public List<VistaRecaudacionDepositoDTO> getListaVistaRecaudacionDepositoDTO() {
		return listaVistaRecaudacionDepositoDTO;
	}

	public void setListaVistaRecaudacionDepositoDTO(List<VistaRecaudacionDepositoDTO> listaVistaRecaudacionDepositoDTO) {
		this.listaVistaRecaudacionDepositoDTO = listaVistaRecaudacionDepositoDTO;
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

	public List<Comprobante> getListaComprobantes() {
		return listaComprobantes;
	}

	public void setListaComprobantes(List<Comprobante> listaComprobantes) {
		this.listaComprobantes = listaComprobantes;
	}


}
