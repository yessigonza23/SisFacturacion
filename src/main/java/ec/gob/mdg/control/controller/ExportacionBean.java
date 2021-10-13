package ec.gob.mdg.control.controller;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;

import ec.gob.mdg.control.actualizaciones.DBcyf;
import ec.gob.mdg.control.model.Fin_Exportaciones_DTO;
import ec.gob.mdg.control.service.IFin_Exportaciones_DTOService;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.service.IUsuarioPuntoService;

@Named
@ViewScoped
public class ExportacionBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IFin_Exportaciones_DTOService serviceExportacion;

	@Inject
	private DBcyf dbcyf;
	
	@Inject
	private IUsuarioPuntoService serviceUsuPunto;
	
	private UsuarioPunto usuPunto = new UsuarioPunto();	
	Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	Integer punto;
	
	private List<Fin_Exportaciones_DTO> listaExportaciones;
	private Fin_Exportaciones_DTO exportacionLicencia;

	Integer codigo;
	String param2;
	String paramnumsolicitud;
	String paramtipo;
	
	String num_solicitud;
	Double total = 0.00;
	
	Integer codentidad;
	String numsolicitud;

	@PostConstruct
	public void init() {
		try {
			usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(usuario);
			punto = usuPunto.getPuntoRecaudacion().getId();
			listarServiciosExportaciones();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public String getParam() {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
	}

	public void listarServiciosExportaciones() {
		param2 = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
		codigo = Integer.parseInt(param2);
			
		try {
			
			dbcyf.actualizaExportacionesAuxNull(codigo,punto);
			this.exportacionLicencia = serviceExportacion.mostarEntidadExportacion(codigo);
			this.listaExportaciones = this.serviceExportacion.listarExportacionesPorCodigo(codigo);
			for (Fin_Exportaciones_DTO f : listaExportaciones) {
				total = total + f.getValor_pago();
				num_solicitud = f.getNum_solicitud();
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// IR A INGRESO DE FACTURAS SERVICIOS CYF
	public String irGenerarFacturas(String codigo) {
				
		final FacesContext context = FacesContext.getCurrentInstance();
		final Flash flash = context.getExternalContext().getFlash();
		flash.put("param", codigo);
		flash.put("paramtipo", "E"); // parametro para exportaciones
		//flash.put("paramnumsolicitud", num_solicitud);
	
		return "facturaingservicioscyf?faces-redirect=true";

	}
	
	// Actualizar en la base de SISCYF Exportaciones el campo aux
		@Transactional
		public void actexportacionfact() throws Exception {
			for (Fin_Exportaciones_DTO imp : listaExportaciones) {
				if (imp.isAux()) {
					codentidad = imp.getCod_entidad();
					numsolicitud = imp.getNum_solicitud();
					dbcyf.actualizaExportacionesAux(codentidad, numsolicitud,punto);
				}
			}
		}

		public void totalValor(boolean est, double mont) {
			if (est) {
				total = total + mont;
			} else {
				total = total - mont;

			}
		}
		
	/// GETTERS y SETTERS

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public List<Fin_Exportaciones_DTO> getListaExportaciones() {
		return listaExportaciones;
	}

	public void setListaExportaciones(List<Fin_Exportaciones_DTO> listaExportaciones) {
		this.listaExportaciones = listaExportaciones;
	}

	public Fin_Exportaciones_DTO getExportacionLicencia() {
		return exportacionLicencia;
	}

	public void setExportacionLicencia(Fin_Exportaciones_DTO exportacionLicencia) {
		this.exportacionLicencia = exportacionLicencia;
	}

	public String getNum_solicitud() {
		return num_solicitud;
	}

	public void setNum_solicitud(String num_solicitud) {
		this.num_solicitud = num_solicitud;
	}

	public String getParamnumsolicitud() {
		return paramnumsolicitud;
	}

	public void setParamnumsolicitud(String paramnumsolicitud) {
		this.paramnumsolicitud = paramnumsolicitud;
	}

	public String getParamtipo() {
		return paramtipo;
	}

	public void setParamtipo(String paramtipo) {
		this.paramtipo = paramtipo;
	}

	public Integer getCodentidad() {
		return codentidad;
	}

	public void setCodentidad(Integer codentidad) {
		this.codentidad = codentidad;
	}

	public String getNumsolicitud() {
		return numsolicitud;
	}

	public void setNumsolicitud(String numsolicitud) {
		this.numsolicitud = numsolicitud;
	}
	
	

}
