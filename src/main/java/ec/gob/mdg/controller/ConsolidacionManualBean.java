package ec.gob.mdg.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import ec.gob.mdg.model.Comprobante;
import ec.gob.mdg.model.ComprobanteDepositos;
import ec.gob.mdg.model.ConsolidaDepositos;
import ec.gob.mdg.model.EstadoCuenta;
import ec.gob.mdg.model.PuntoRecaudacion;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.model.VistaRecaudacionDepositoDTO;
import ec.gob.mdg.service.IComprobanteDepositosService;
import ec.gob.mdg.service.IComprobanteService;
import ec.gob.mdg.service.IConsolidaDepositosService;
import ec.gob.mdg.service.IEstadoCuentaService;
import ec.gob.mdg.service.IPuntoRecaudacionService;
import ec.gob.mdg.service.IUsuarioPuntoService;
import ec.gob.mdg.service.IVistaRecaudacioDepositoDTOService;
import ec.gob.mdg.util.UtilsDate;

@Named
@ViewScoped
public class ConsolidacionManualBean implements Serializable {

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
	private IConsolidaDepositosService serviceConsolidaDepositos;

	@Inject
	private IComprobanteService serviceComprobante;

	@Inject
	private IComprobanteDepositosService serviceComprobanteDepositos;

	private ConsolidaDepositos consolidaDepositos = new ConsolidaDepositos();

	private Comprobante comprobante = new Comprobante();
	private ComprobanteDepositos comprobanteDepositos = new ComprobanteDepositos();
	private PuntoRecaudacion puntorecaudacion;
	private EstadoCuenta estadoCuenta = new EstadoCuenta();
	private VistaRecaudacionDepositoDTO vistaRecaudacionDepositoDTO = new VistaRecaudacionDepositoDTO();
	private PuntoRecaudacion puntoRecaudacionEstado = new PuntoRecaudacion();
	private List<EstadoCuenta> listaEstadoCuenta = new ArrayList<EstadoCuenta>();
	private List<VistaRecaudacionDepositoDTO> listaVistaRecaudacionDepositoDTO = new ArrayList<>();
	private List<Comprobante> listaComprobante = new ArrayList<Comprobante>();
	PuntoRecaudacion punto;
	PuntoRecaudacion nombre;
	Date fechaFactura;

	Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	private UsuarioPunto usuPunto = new UsuarioPunto();

	Integer anio;
	Integer mes;
	Date fechaActual;

	@PostConstruct
	public void init() {
		try {
			usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(us);
			punto = usuPunto.getPuntoRecaudacion();
			nombre = servicePuntoRecaudacion.listarPorId(punto);
			fechaActual = UtilsDate.fechaActualDate();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	///// LISTAR ESTADO DE CUENTA
	public void listarEstado(Integer anio) {
		listaEstadoCuenta = serviceEstadoCuenta.listarEstadoCuentaSinFactura(anio);
	}

	//// LISTAR COMPROBANTE DEPOSITO
	public void listarVistaDepositos(String num_deposito, Integer anio) {
		listaVistaRecaudacionDepositoDTO = serviceVistaRecaudacionDepositoDTO
				.listarComprobantesNumDeposito(num_deposito, anio);

	}

	// CARGAR ESTADO DE CUENTA UNA VEZ QUE SELECCIONE EL NUMERO DE DEPOSITO
	public void cargarEstadoCuenta(Integer id_estadoCuenta) {
		estadoCuenta = serviceEstadoCuenta.estadoCuentaPorId(id_estadoCuenta);
	}

	// CARGAR COMPROBANTE DETALLE UNA VEZ QUE SELECCIONE EL NUMERO DE FACTURA
	public void cargarComprobanteDeposito(Integer id_deposito) {
		vistaRecaudacionDepositoDTO = serviceVistaRecaudacionDepositoDTO.ComprobantesDepNumDeposito(id_deposito);
	}

	// MODIFICAR ESTADO
	@Transactional
	public void modificar(Integer id_comprobanteDeposito, Integer id_estadoCuenta) {
		try {
System.out.println(" consolidaDepositos.getObservacion() " + consolidaDepositos.getObservacion());
			comprobanteDepositos = serviceComprobanteDepositos.mostrarComprobanteDepositoPorId(id_comprobanteDeposito);
			estadoCuenta = serviceEstadoCuenta.estadoCuentaPorId(id_estadoCuenta);
			
			if (consolidaDepositos.getObservacion() == null) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Debe ingresar una observación del cabmio", "ERROR"));
			} else {
				
				puntorecaudacion = servicePuntoRecaudacion.listarPorId(puntoRecaudacionEstado);

				if (comprobanteDepositos.getValor() <= estadoCuenta.getSaldo()) {
					guardar(id_comprobanteDeposito, id_estadoCuenta);
					
					
					estadoCuenta.setSaldo(estadoCuenta.getSaldo() - comprobanteDepositos.getValor());
					if (estadoCuenta.getSaldo() == 0) {
						estadoCuenta.setBloqueado(true);
					}
					serviceEstadoCuenta.modificar(estadoCuenta);
					
					comprobanteDepositos.setId_tmp(1);
					serviceComprobanteDepositos.modificar(comprobanteDepositos);
					
					
					FacesContext.getCurrentInstance().addMessage(null,
							new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Se consolidó exitosamente"));
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Saldo negativo en la Factura, el valor facturado supere al valor del depósito", "ERROR"));
				}
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	// GUARDAR CONSOLIDA DEPOSITO
	@Transactional
	public void guardar(Integer id_comprobanteDeposito, Integer id_estadoCuenta) {

		try {
			comprobanteDepositos = serviceComprobanteDepositos.mostrarComprobanteDepositoPorId(id_comprobanteDeposito);
			estadoCuenta = serviceEstadoCuenta.estadoCuentaPorId(id_estadoCuenta);

			ConsolidaDepositos con = new ConsolidaDepositos();
			con.setComprobanteDepositos(comprobanteDepositos);
			con.setEstadoCuenta(estadoCuenta);
			con.setSaldo(estadoCuenta.getSaldo() - comprobanteDepositos.getValor());
			con.setTipoconsolidacion("M");
			con.setFecha(fechaActual);
			con.setUsuario(us);
			con.setObservacion(consolidaDepositos.getObservacion());
			con.setEstado(true);
			
			serviceConsolidaDepositos.registrar(con);
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// BUSCAR FACTURA LIGADA A LA NOTA DE CREDITO PARA DESPLEGAR INFORMACIN EN LA
	// NOTA DE CReDITO
	public void buscarFactura(Integer id_comprobante) {
		try {

			this.comprobante = serviceComprobante.listarComprobantePorId(id_comprobante);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	//// LISTAR COMPROBANTES POR REGIONAL PARA LA NOTA DE CREDITO
	public void listarComprobanteRegional(Integer id_punto) {

		this.listaComprobante = serviceComprobante.listarCompPorIdPto(id_punto);
	}

	//// GETTERS Y SETTERS/
	public PuntoRecaudacion getPuntorecaudacion() {
		return puntorecaudacion;
	}

	public void setPuntorecaudacion(PuntoRecaudacion puntorecaudacion) {
		this.puntorecaudacion = puntorecaudacion;
	}

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public List<EstadoCuenta> getListaEstadoCuenta() {
		return listaEstadoCuenta;
	}

	public void setListaEstadoCuenta(List<EstadoCuenta> listaEstadoCuenta) {
		this.listaEstadoCuenta = listaEstadoCuenta;
	}

	public List<VistaRecaudacionDepositoDTO> getListaVistaRecaudacionDepositoDTO() {
		return listaVistaRecaudacionDepositoDTO;
	}

	public void setListaVistaRecaudacionDepositoDTO(
			List<VistaRecaudacionDepositoDTO> listaVistaRecaudacionDepositoDTO) {
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

	public Date getFechaFactura() {
		return fechaFactura;
	}

	public void setFechaFactura(Date fechaFactura) {
		this.fechaFactura = fechaFactura;
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

	public EstadoCuenta getEstadoCuenta() {
		return estadoCuenta;
	}

	public void setEstadoCuenta(EstadoCuenta estadoCuenta) {
		this.estadoCuenta = estadoCuenta;
	}

	public ConsolidaDepositos getConsolidaDepositos() {
		return consolidaDepositos;
	}

	public void setConsolidaDepositos(ConsolidaDepositos consolidaDepositos) {
		this.consolidaDepositos = consolidaDepositos;
	}

	public PuntoRecaudacion getPuntoRecaudacionEstado() {
		return puntoRecaudacionEstado;
	}

	public void setPuntoRecaudacionEstado(PuntoRecaudacion puntoRecaudacionEstado) {
		this.puntoRecaudacionEstado = puntoRecaudacionEstado;
	}

	public VistaRecaudacionDepositoDTO getVistaRecaudacionDepositoDTO() {
		return vistaRecaudacionDepositoDTO;
	}

	public void setVistaRecaudacionDepositoDTO(VistaRecaudacionDepositoDTO vistaRecaudacionDepositoDTO) {
		this.vistaRecaudacionDepositoDTO = vistaRecaudacionDepositoDTO;
	}

	public ComprobanteDepositos getComprobanteDepositos() {
		return comprobanteDepositos;
	}

	public void setComprobanteDepositos(ComprobanteDepositos comprobanteDepositos) {
		this.comprobanteDepositos = comprobanteDepositos;
	}

}
