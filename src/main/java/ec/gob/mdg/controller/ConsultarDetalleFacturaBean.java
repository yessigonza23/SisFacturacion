package ec.gob.mdg.controller;

import java.io.Serializable;
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
import ec.gob.mdg.service.IComprobanteDepositosService;
import ec.gob.mdg.service.IComprobanteDetalleService;
import ec.gob.mdg.service.IComprobanteService;
import ec.gob.mdg.service.IUsuarioPuntoService;
import ec.gob.mdg.util.UtilsDate;
import ec.gob.mdg.validaciones.Funciones;

@Named
@ViewScoped
public class ConsultarDetalleFacturaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IComprobanteService serviceComprobante;

	@Inject
	private IComprobanteDetalleService serviceComprobanteDet;

	@Inject
	private IComprobanteDepositosService serviceComprobanteDep;
	
	@Inject
	private IAuditoriaService serviceAuditoria;
	
	@Inject
	private Funciones fun;
	
	@Inject
	private GenerarDOMBean genXml;
	
	@Inject
	private IUsuarioPuntoService serviceUsuPunto;

	private Comprobante comprobante;
	private Comprobante comprobanteNotas;
	private ComprobanteDetalle comprobanteDet;
	private ComprobanteDepositos comprobanteDep;
	private Cliente cliente = new Cliente();
	private Institucion institucion;

	private List<Comprobante> listaComprobante;
	private List<ComprobanteDetalle> listaComprobanteDet;
	private List<ComprobanteDepositos> listaComprobanteDep;
	private List<RecaudacionDetalle> listaRecaudacionDetalle;
	private UsuarioPunto usuPunto = new UsuarioPunto();

	Usuario p = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	Integer id = 0;
	String param2;
	String num_factura;
	boolean estadeshabilitado = false;
	double totaldet = 0.0;
	double totaldep = 0.0;
	private String tipoDialog;
	Integer inst;
	PuntoRecaudacion punto;
	PuntoRecaudacion nombre;
	String claveA;
	double subtotaldet = 0.0;
	boolean estadeshabilitadoA= false;
	Date fechaActual;
	String autorizacion;

	@PostConstruct
	public void init() {
		try {
			fechaActual = UtilsDate.fechaActual();
			usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(p);
			punto = usuPunto.getPuntoRecaudacion();
			mostrarComprobante();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getParam() {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
	}

	// Listar informacion de la cabecera del comprobante: Facturas
	public void mostrarComprobante() {
		param2 = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
		id = Integer.parseInt(param2);		
		comprobante = serviceComprobante.listarComprobantePorId(id);
		listaComprobanteDet = this.serviceComprobanteDet.listarComDetPorIdComp(id);
		listaComprobanteDep = this.serviceComprobanteDep.listarComDepPorIdComp(id);
	}
	

	// Listar informacion de la cabecera del comprobante: Notas
	public void mostrarComprobanteNota() {
		param2 = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
		id = Integer.parseInt(param2);
		comprobanteNotas = serviceComprobante.listarComprobanteNotaPorId(id);
		listaComprobanteDet = this.serviceComprobanteDet.listarComDetPorIdComp(id);	
	}

	// SUMAR TOTAL DETALLE
	public void totalDetalle() {
		totaldet = 0.00;
		for (ComprobanteDetalle det : listaComprobanteDet) {
			totaldet = totaldet + det.getSubtotal();
		}
	}

	// SUMAR TOTAL DEPOSITOS
	public void totalDeposito() {
		totaldep = 0.00;
		for (ComprobanteDepositos dep : listaComprobanteDep) {
			totaldep = totaldep + dep.getValor();
		}
	}
	
	// VALIDA QUE LA FACTURA NO SEA DIFERENTE DE LA AUTORIZACION
		public void validaFacturaAutorizacion() {
			if (comprobante.getAutorizacion() != null) {
				String facturaS = comprobante.getAutorizacion().substring(30, 39);
				Integer factura = Integer.parseInt(facturaS);

				if (comprobante.getNumero().equals(factura)) {
					System.out.println("entra a iguales: " + comprobante.getNumero() + " - " + factura);
					estadeshabilitado = true;
					estadeshabilitadoA = true;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"No puede borrar la Información del SRI, la autorización de la Factura", "Error"));
				} else if (!comprobante.getNumero().equals(factura)){
					System.out.println("entra a diferentes " + comprobante.getId());
					try {
						fun.borraSRI(comprobante.getId());
						// CAMPO comprobante.getAutorizacion)
						serviceAuditoria.insertaModificacion("Comprobante", "autorizacion", "D",
								usuPunto.getUsuario().getUsername(), fechaActual, autorizacion,
								comprobante.getAutorizacion(), comprobante.getId());

						estadeshabilitado = false;
						estadeshabilitadoA = false;
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Se borró con éxito", "Aviso"));
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}

		@Transactional
		public void modificarComprobante() {
			try {
				
				if (comprobante.getAutorizacion() != null ) {
					estadeshabilitado = true;
					estadeshabilitadoA = true;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"No puede realizar cambios la factura ya esta autorizada por el SRI", "Error"));
				
				} else if (comprobante.getAutorizacion() == null) {				
						genXml.generarXmlArchivo(comprobante.getPuntoRecaudacion().getId(), comprobante.getNumero());
					
						estadeshabilitado = true;
						FacesContext.getCurrentInstance().addMessage(null,
								new FacesMessage(FacesMessage.SEVERITY_INFO, "Se generó con éxito el XML", "Aviso"));
					} else {
						FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
								"Error - Diferencia de valores en detalle y deposito", "Error"));
					}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	// GETTERS Y SETTERS

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

	public ComprobanteDetalle getComprobanteDet() {
		return comprobanteDet;
	}

	public void setComprobanteDet(ComprobanteDetalle comprobanteDet) {
		this.comprobanteDet = comprobanteDet;
	}

	public List<ComprobanteDetalle> getListaComprobanteDet() {
		return listaComprobanteDet;
	}

	public void setListaComprobanteDet(List<ComprobanteDetalle> listaComprobanteDet) {
		this.listaComprobanteDet = listaComprobanteDet;
	}

	public ComprobanteDepositos getComprobanteDep() {
		return comprobanteDep;
	}

	public void setComprobanteDep(ComprobanteDepositos comprobanteDep) {
		this.comprobanteDep = comprobanteDep;
	}

	public List<ComprobanteDepositos> getListaComprobanteDep() {
		return listaComprobanteDep;
	}

	public void setListaComprobanteDep(List<ComprobanteDepositos> listaComprobanteDep) {
		this.listaComprobanteDep = listaComprobanteDep;
	}

	public String getNum_factura() {
		return num_factura;
	}

	public void setNum_factura(String num_factura) {
		this.num_factura = num_factura;
	}

	public Comprobante getComprobanteNotas() {
		return comprobanteNotas;
	}

	public void setComprobanteNotas(Comprobante comprobanteNotas) {
		this.comprobanteNotas = comprobanteNotas;
	}

	public boolean isEstadeshabilitado() {
		return estadeshabilitado;
	}

	public void setEstadeshabilitado(boolean estadeshabilitado) {
		this.estadeshabilitado = estadeshabilitado;
	}

	public double getTotaldet() {
		return totaldet;
	}

	public void setTotaldet(double totaldet) {
		this.totaldet = totaldet;
	}

	public double getTotaldep() {
		return totaldep;
	}

	public void setTotaldep(double totaldep) {
		this.totaldep = totaldep;
	}

	public String getTipoDialog() {
		return tipoDialog;
	}

	public void setTipoDialog(String tipoDialog) {
		this.tipoDialog = tipoDialog;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public List<RecaudacionDetalle> getListaRecaudacionDetalle() {
		return listaRecaudacionDetalle;
	}

	public void setListaRecaudacionDetalle(List<RecaudacionDetalle> listaRecaudacionDetalle) {
		this.listaRecaudacionDetalle = listaRecaudacionDetalle;
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

	public Integer getInst() {
		return inst;
	}

	public void setInst(Integer inst) {
		this.inst = inst;
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

	public String getClaveA() {
		return claveA;
	}

	public void setClaveA(String claveA) {
		this.claveA = claveA;
	}

	public double getSubtotaldet() {
		return subtotaldet;
	}

	public void setSubtotaldet(double subtotaldet) {
		this.subtotaldet = subtotaldet;
	}

}
