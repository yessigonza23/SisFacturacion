package ec.gob.mdg.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.gob.mdg.model.Comprobante;
import ec.gob.mdg.model.PuntoRecaudacion;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.service.IComprobanteService;
import ec.gob.mdg.service.IPuntoRecaudacionService;
import ec.gob.mdg.service.IUsuarioPuntoService;

@Named
@ViewScoped
public class ComprobanteConsultaModificaDepositosBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IComprobanteService serviceComprobante;

	@Inject
	private IUsuarioPuntoService serviceUsuPunto;

	@Inject
	private IPuntoRecaudacionService servicePuntoRecaudacion;

	private List<Comprobante> listaComprobante = new ArrayList<Comprobante>();
	PuntoRecaudacion punto;
	PuntoRecaudacion nombre;

	Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	private UsuarioPunto usuPunto = new UsuarioPunto();
	Date fecha_inicio;
	Date fecha_fin;
	String tipoF = "F";
	String tipoC = "C";

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
	public void listarComprobantes() {
		try {
			if (fecha_inicio !=null && fecha_fin !=null) {
				this.listaComprobante = serviceComprobante.listarComprobantePorFechas(fecha_inicio, fecha_fin,
						punto.getId(), tipoF);
			}else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Sin Datos", "Especifique las fechas"));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	

	// IR A DETALLE DE FACTURA A MODIFICAR
	public String irDetalleMod(String id) {
		final FacesContext context = FacesContext.getCurrentInstance();
		final Flash flash = context.getExternalContext().getFlash();
		flash.put("param", id);
		System.out.println("id: " + id);
		return "facturamodificadepositosdetalle?faces-redirect=true";
	}
	
	
	////// GETTERS Y SETTERS

	public List<Comprobante> getListaComprobante() {
		return listaComprobante;
	}

	public void setListaComprobante(List<Comprobante> listaComprobante) {
		this.listaComprobante = listaComprobante;
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

	public String getTipoF() {
		return tipoF;
	}

	public void setTipoF(String tipoF) {
		this.tipoF = tipoF;
	}

	public String getTipoC() {
		return tipoC;
	}

	public void setTipoC(String tipoC) {
		this.tipoC = tipoC;
	}

}
