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
import javax.transaction.Transactional;

import ec.gob.mdg.model.Auditoria;
import ec.gob.mdg.model.EstadoCuenta;
import ec.gob.mdg.model.PuntoRecaudacion;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.service.IAuditoriaService;
import ec.gob.mdg.service.IEstadoCuentaService;
import ec.gob.mdg.service.IPuntoRecaudacionService;
import ec.gob.mdg.service.IUsuarioPuntoService;
import ec.gob.mdg.util.UtilsDate;

@Named
@ViewScoped
public class ConsolidacionDevolucionBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IUsuarioPuntoService serviceUsuPunto;

	@Inject
	private IPuntoRecaudacionService servicePuntoRecaudacion;

	@Inject
	private IEstadoCuentaService serviceEstadoCuenta;

	@Inject
	private IAuditoriaService serviceAuditoria;

	private EstadoCuenta estadoCuenta = new EstadoCuenta();
	private List<EstadoCuenta> listaEstadoPendiente = new ArrayList<>();

	PuntoRecaudacion punto;
	PuntoRecaudacion nombre;

	Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	private UsuarioPunto usuPunto = new UsuarioPunto();

	Integer anio;
	String observacion;

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

	//// LISTAR COMPROBANTE DEPOSITO
	public void listarEstadoPendiente(Integer anio) {

		
		
		if (anio != null)
			try {
				listaEstadoPendiente = serviceEstadoCuenta.listarEstadoCuentaPendiente(anio);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		else {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Aviso", "Faltan datos para la consulta"));
		}
	}

	@Transactional
	public void actualizaRespuesta(EstadoCuenta estado,String observacion) {
		if (estado != null && anio != null) {
			try {
				estadoCuenta = serviceEstadoCuenta.estadoCuentaPorId(estado.getId());
				estadoCuenta.setRespuesta(estado.getRespuesta());
				estadoCuenta.setObservacion(observacion);
			//	System.out.println("estado respuesta y ober: " + estadoCuenta.getObservacion() + "-" + estadoCuenta.getRespuesta());
				serviceEstadoCuenta.modificar(estadoCuenta);
				//System.out.println("pasa grabando");

				Auditoria auditoria = new Auditoria();

				auditoria.setNombretabla("EstadoCuenta");
				auditoria.setNombrecampo("respuesta");
				auditoria.setOperacion("E");
				auditoria.setUsuario(us.getUsername());
				auditoria.setFecha(UtilsDate.fechaActual());
				auditoria.setValoranterior("Proceso O.K.");
				auditoria.setValoractual("Devolución.");
				auditoria.setClaveprimaria(estado.getId());
				serviceAuditoria.registrar(auditoria);
				 

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso",
						"Se realiza el cambio de respuesta a Devolución exitosamente"));

				listarEstadoPendiente(anio);

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Faltan datos para la nueva consulta", "Falta de datos para la consulta "));
		}

	}

	//// GETTERS Y SETTERS/

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

	public EstadoCuenta getEstadoCuenta() {
		return estadoCuenta;
	}

	public void setEstadoCuenta(EstadoCuenta estadoCuenta) {
		this.estadoCuenta = estadoCuenta;
	}

	public List<EstadoCuenta> getListaEstadoPendiente() {
		return listaEstadoPendiente;
	}

	public void setListaEstadoPendiente(List<EstadoCuenta> listaEstadoPendiente) {
		this.listaEstadoPendiente = listaEstadoPendiente;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	

}
