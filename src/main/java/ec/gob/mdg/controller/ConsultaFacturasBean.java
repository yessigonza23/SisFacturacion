package ec.gob.mdg.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.gob.mdg.model.Cliente;
import ec.gob.mdg.model.Comprobante;
import ec.gob.mdg.model.ComprobanteDepositos;
import ec.gob.mdg.model.PuntoRecaudacion;
import ec.gob.mdg.service.IComprobanteDepositosService;
import ec.gob.mdg.service.IComprobanteService;
import ec.gob.mdg.validaciones.Funciones;

@Named
@ViewScoped
public class ConsultaFacturasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IComprobanteService serviceComprobante;

	@Inject
	private IComprobanteDepositosService serviceComprobanteDep;	
	
	@Inject
	private Funciones fun;

	private Cliente cliente;
	private Comprobante comprobante;
	private PuntoRecaudacion puntoRecaudacion;
	String numDeposito;
	String nomCliente;

	private List<Cliente> listaClientesB = new ArrayList<Cliente>();
	private List<Cliente> listaClientesF = new ArrayList<Cliente>();
	private List<ComprobanteDepositos> listaComprobanteDep = new ArrayList<ComprobanteDepositos>();
	private List<Comprobante> listaComprobante = new ArrayList<Comprobante>();

	
	// IR A DETALLE DE FACTURA A CONSULTAR
	public String irDetalle(String id) {
		final FacesContext context = FacesContext.getCurrentInstance();
		final Flash flash = context.getExternalContext().getFlash();
		flash.put("param", id);
		return "facturaconsultadetalle?faces-redirect=true";
	}

	// IR A DETALLE DE NOTA DE CREDITO
	public String irDetalleNotas(String id) {
		final FacesContext context = FacesContext.getCurrentInstance();
		final Flash flash = context.getExternalContext().getFlash();
		flash.put("param", id);
		return "notascreditoconsultadetalle?faces-redirect=true";
	}

	// CONSULTA DE COMPROBANTES POR PUNTO DE RECAUDACIoN: FACTURAS
	public void listarComprobante(PuntoRecaudacion punto) {
		try {
			
			this.listaComprobante = this.serviceComprobante.listarCompPorPtoCon(punto);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// CONSULTA DE COMPROBANTES POR NUMERO DE DEPOSITO: FACTURAS
	public void listarComprobantePorNumDeposito(String numDeposito ) {
		try {
			
			this.listaComprobanteDep = this.serviceComprobanteDep.listarComDepPorNumeroDeposiro(numDeposito);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// CONSULTA DE COMPROBANTES POR CLIENTE:FACTURAS
	public void listarComprobantePorCliente(Cliente cliente) {
		try {

			this.listaComprobante = this.serviceComprobante.comprobantePorCliente(cliente);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// CONSULTA DE COMPROBANTES POR PUNTO DE RECAUDACION: NOTAS DE CREDITO
	public void listarComprobanteNotas(PuntoRecaudacion punto) {
		try {
			this.listaComprobante = this.serviceComprobante.listarCompNotasPorPtoCon(punto);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// CONSULTA DE COMPROBANTES POR CLIENTE: NOTAS DE CREDITO
	public void listarComprobanteNotasPorCliente(Cliente cliente) {
		try {

			this.listaComprobante = this.serviceComprobante.listarNotasCreditoPorCliente(cliente);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// LISTAR CLIENTE NOTAS
	public void listarClientes(String nombre) {
//		System.out.println("entra a listar clientes " +nombre);
		try {
			if (nombre !=null) {
				this.listaClientesB = this.fun.ClientePorNombre(nombre);
//				for(Cliente c:listaClientesB) {
//					System.out.println(c.getNombre());
//				}
			}
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	// GETTER Y SETTERS

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

	public PuntoRecaudacion getPuntoRecaudacion() {
		return puntoRecaudacion;
	}

	public void setPuntoRecaudacion(PuntoRecaudacion puntoRecaudacion) {
		this.puntoRecaudacion = puntoRecaudacion;
	}

	public String getNumDeposito() {
		return numDeposito;
	}

	public void setNumDeposito(String numDeposito) {
		this.numDeposito = numDeposito;
	}

	public List<ComprobanteDepositos> getListaComprobanteDep() {
		return listaComprobanteDep;
	}

	public void setListaComprobanteDep(List<ComprobanteDepositos> listaComprobanteDep) {
		this.listaComprobanteDep = listaComprobanteDep;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}



	public List<Cliente> getListaClientesB() {
		return listaClientesB;
	}

	public void setListaClientesB(List<Cliente> listaClientesB) {
		this.listaClientesB = listaClientesB;
	}

	public List<Cliente> getListaClientesF() {
		return listaClientesF;
	}

	public void setListaClientesF(List<Cliente> listaClientesF) {
		this.listaClientesF = listaClientesF;
	}

	public String getNomCliente() {
		return nomCliente;
	}

	public void setNomCliente(String nomCliente) {
		this.nomCliente = nomCliente;
	}



}
