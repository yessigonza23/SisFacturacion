package ec.gob.mdg.controller;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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

import ec.gob.mdg.model.Cliente;
import ec.gob.mdg.model.Comprobante;
import ec.gob.mdg.model.ComprobanteDepositos;
import ec.gob.mdg.model.ComprobanteDetalle;
import ec.gob.mdg.model.Institucion;
import ec.gob.mdg.model.PuntoRecaudacion;
import ec.gob.mdg.model.RecaudacionDetalle;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.service.IAuditoriaService;
import ec.gob.mdg.service.IClienteService;
import ec.gob.mdg.service.IComprobanteDepositosService;
import ec.gob.mdg.service.IComprobanteService;
import ec.gob.mdg.service.IInstitucionService;
import ec.gob.mdg.service.IPuntoRecaudacionService;
import ec.gob.mdg.service.IUsuarioPuntoService;
import ec.gob.mdg.util.UtilsDate;

@Named
@ViewScoped
public class ComprobanteModificaDepositosBean implements Serializable {

	private static final long serialVersionUID = -3453594861281598811L;

	@Inject
	private IInstitucionService institucionService;

	@Inject
	private IClienteService serviceCliente;

	@Inject
	private IComprobanteService serviceComprobante;

	@Inject
	private IComprobanteDepositosService serviceComprobanteDepositos;

	@Inject
	private IUsuarioPuntoService serviceUsuPunto;

	@Inject
	private IPuntoRecaudacionService servicePuntoRecaudacion;

	@Inject
	private IAuditoriaService serviceAuditoria;

	private Cliente cliente;
	private Cliente clienteNotas;
	private Comprobante comprobante;
	private Comprobante comprobanteAud;
	private Comprobante comprobanteNot;
	private ComprobanteDetalle comprobanteDetalle = new ComprobanteDetalle();
	private ComprobanteDetalle comprobanteDetalleNotas = new ComprobanteDetalle();
	private ComprobanteDepositos comprobanteDepositos = new ComprobanteDepositos();
	private RecaudacionDetalle recaudacionDetalle;
	private Institucion institucion;

	private List<Cliente> listaClientes = new ArrayList<Cliente>();
	private List<Comprobante> listaComprobante = new ArrayList<Comprobante>();
	private List<ComprobanteDepositos> listaComprobanteDep = new ArrayList<ComprobanteDepositos>();
	private List<ComprobanteDepositos> listaComprobanteDepTmp = new ArrayList<ComprobanteDepositos>();

	private UsuarioPunto usuPunto = new UsuarioPunto();

	Usuario p = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");

	Date fechaActual;
	PuntoRecaudacion punto;
	PuntoRecaudacion nombre;
	Integer id_tmp = 0;
	private String tipoDialog;
	double subtotaldet = 0.0;
	boolean estadeshabilitado;
	Integer inst;
	Integer rd;
	Integer id_comprobante = 0;
	String tipo_proceso = null;
	Integer id = 0;
	String param2;

	/// variables para auditoria
	String autorizacion;
	String estadoAnterior;
	double valorAnterior;

	String ambiente;
	String url;
	String host;

	@PostConstruct
	public void init() {
		try {
			fechaActual = UtilsDate.fechaActual();
			usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(p);
			punto = usuPunto.getPuntoRecaudacion();
			inst = punto.getInstitucion().getId();
			institucion = institucionService.institucionPorPunto(inst);
			nombre = servicePuntoRecaudacion.listarPorId(punto);
			mostrarComprobante();
			// mostrarComprobanteDetalle();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getParam() {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
	}

	// Listar informacin de la cabecera del comprobante: Facturas
	public void mostrarComprobante() {
		param2 = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
		id = Integer.parseInt(param2);
		
		try {
			// LLENAR CON LOS DATOS DE BD
			this.comprobante = serviceComprobante.listarComprobantePorId(id);
			this.cliente = this.serviceCliente.ClientePorCiParametro(comprobante.getClienteruc());
			this.listaComprobanteDep = this.serviceComprobanteDepositos.listarComDepPorIdComp(id);
			this.listaComprobanteDepTmp = this.serviceComprobanteDepositos.listarComDepPorIdComp(id);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Transactional
	public void modificarComprobanteDeposito(ComprobanteDepositos comprobanteDepositos) {

		if (comprobanteDepositos != null) {
			try {
				comprobanteDepositos.setFecha(comprobanteDepositos.getFecha());
				serviceComprobanteDepositos.modificar(comprobanteDepositos);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		//// AUDITORIA TABLA COMPROBANTE DEPOSITO
		for (ComprobanteDepositos c : listaComprobanteDep) {
			System.out.println(c.getIdentificacion());
			if (c.getId() != null) {
				System.out.println("entra diferente de null");
				for (ComprobanteDepositos ct : listaComprobanteDepTmp) {
					System.out.println("id dep " + c.getIdentificacion() +"-"+ct.getIdentificacion());
					if (c.getId().equals(ct.getId())) {
						// CAMPO RECAUDACIN
						System.out.println("entra a fondo "+ ct.getOrigen());
						if (!c.getOrigen().equals(ct.getOrigen())) {
							serviceAuditoria.insertaModificacion("ComprobanteDeposito", "Origen", "U",
									usuPunto.getUsuario().getUsername(), fechaActual, c.getOrigen(), ct.getOrigen(),
									c.getId());
						}
						if (!c.getTipotransaccion().equals(ct.getTipotransaccion())) {
							serviceAuditoria.insertaModificacion("ComprobanteDeposito", "tipoTransaccion", "U",
									usuPunto.getUsuario().getUsername(), fechaActual, c.getTipotransaccion(),
									ct.getTipotransaccion(), c.getId());
						}
						if (!c.getNum_deposito().equals(ct.getNum_deposito())) {
							serviceAuditoria.insertaModificacion("ComprobanteDeposito", "comprobante", "U",
									usuPunto.getUsuario().getUsername(), fechaActual, c.getNum_deposito(),
									ct.getNum_deposito(), c.getId());
						}
						if (!c.getIdentificacion().equals(ct.getIdentificacion())) {
							serviceAuditoria.insertaModificacion("ComprobanteDeposito", "identificacion", "U",
									usuPunto.getUsuario().getUsername(), fechaActual, c.getIdentificacion(),
									ct.getIdentificacion(), c.getId());
						}
						
						@SuppressWarnings("unused")
						Date date = new Date();
						SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
System.out.println("c.getfechcha " + c.getFecha() + "ct " +ct.getFecha());
						if (!c.getFecha().equals(ct.getFecha())) {
							serviceAuditoria.insertaModificacion("ComprobanteDeposito", "fecha", "U",
									usuPunto.getUsuario().getUsername(), fechaActual, formater.format(c.getFecha()),
									formater.format(ct.getFecha()), c.getId());
						}
					
					}
				}
			}
		}
		FacesContext.getCurrentInstance().addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_INFO, "se grabó con éxito", "Aviso"));

	}

	////////////////////////////////////////////////

	public String getEstadoAnterior() {
		return estadoAnterior;
	}

	public void setEstadoAnterior(String estadoAnterior) {
		this.estadoAnterior = estadoAnterior;
	}

	// Getters y Setters

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Comprobante getComprobante() {
		return comprobante;
	}

	public void setComprobante(Comprobante comprobante) {
		this.comprobante = comprobante;
	}

	public ComprobanteDetalle getComprobanteDetalle() {
		return comprobanteDetalle;
	}

	public void setComprobanteDetalle(ComprobanteDetalle comprobanteDetalle) {
		this.comprobanteDetalle = comprobanteDetalle;
	}

	public ComprobanteDepositos getComprobanteDepositos() {
		return comprobanteDepositos;
	}

	public void setComprobanteDepositos(ComprobanteDepositos comprobanteDepositos) {
		this.comprobanteDepositos = comprobanteDepositos;
	}

	public List<Cliente> getListaClientes() {
		return listaClientes;
	}

	public void setListaClientes(List<Cliente> listaClientes) {
		this.listaClientes = listaClientes;
	}

	public List<Comprobante> getListaComprobante() {
		return listaComprobante;
	}

	public void setListaComprobante(List<Comprobante> listaComprobante) {
		this.listaComprobante = listaComprobante;
	}

	public RecaudacionDetalle getRecaudacionDetalle() {
		return recaudacionDetalle;
	}

	public void setRecaudacionDetalle(RecaudacionDetalle recaudacionDetalle) {
		this.recaudacionDetalle = recaudacionDetalle;
	}

	public UsuarioPunto getUsuPunto() {
		return usuPunto;
	}

	public void setUsuPunto(UsuarioPunto usuPunto) {
		this.usuPunto = usuPunto;
	}

	public Usuario getP() {
		return p;
	}

	public void setP(Usuario p) {
		this.p = p;
	}

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
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

	public Integer getId_tmp() {
		return id_tmp;
	}

	public void setId_tmp(Integer id_tmp) {
		this.id_tmp = id_tmp;
	}

	public String getTipoDialog() {
		return tipoDialog;
	}

	public void setTipoDialog(String tipoDialog) {
		this.tipoDialog = tipoDialog;
	}

	public double getSubtotaldet() {
		return subtotaldet;
	}

	public void setSubtotaldet(double subtotaldet) {
		this.subtotaldet = subtotaldet;
	}

	public boolean isEstadeshabilitado() {
		return estadeshabilitado;
	}

	public void setEstadeshabilitado(boolean estadeshabilitado) {
		this.estadeshabilitado = estadeshabilitado;
	}

	public Integer getInst() {
		return inst;
	}

	public void setInst(Integer inst) {
		this.inst = inst;
	}

	public Integer getRd() {
		return rd;
	}

	public void setRd(Integer rd) {
		this.rd = rd;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public ComprobanteDetalle getComprobanteDetalleNotas() {
		return comprobanteDetalleNotas;
	}

	public void setComprobanteDetalleNotas(ComprobanteDetalle comprobanteDetalleNotas) {
		this.comprobanteDetalleNotas = comprobanteDetalleNotas;
	}

	public String getTipo_proceso() {
		return tipo_proceso;
	}

	public void setTipo_proceso(String tipo_proceso) {
		this.tipo_proceso = tipo_proceso;
	}

	public Integer getId_comprobante() {
		return id_comprobante;
	}

	public void setId_comprobante(Integer id_comprobante) {
		this.id_comprobante = id_comprobante;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public Cliente getClienteNotas() {
		return clienteNotas;
	}

	public void setClienteNotas(Cliente clienteNotas) {
		this.clienteNotas = clienteNotas;
	}

	public Comprobante getComprobanteNot() {
		return comprobanteNot;
	}

	public void setComprobanteNot(Comprobante comprobanteNot) {
		this.comprobanteNot = comprobanteNot;
	}

	public List<ComprobanteDepositos> getListaComprobanteDepTmp() {
		return listaComprobanteDepTmp;
	}

	public void setListaComprobanteDepTmp(List<ComprobanteDepositos> listaComprobanteDepTmp) {
		this.listaComprobanteDepTmp = listaComprobanteDepTmp;
	}

	public Comprobante getComprobanteAud() {
		return comprobanteAud;
	}

	public void setComprobanteAud(Comprobante comprobanteAud) {
		this.comprobanteAud = comprobanteAud;
	}

	public List<ComprobanteDepositos> getListaComprobanteDep() {
		return listaComprobanteDep;
	}

	public void setListaComprobanteDep(List<ComprobanteDepositos> listaComprobanteDep) {
		this.listaComprobanteDep = listaComprobanteDep;
	}

}
