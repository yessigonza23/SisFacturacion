package ec.gob.mdg.control.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import ec.gob.mdg.control.actualizaciones.DBcyf;
import ec.gob.mdg.control.model.Fin_Guias_DTO;
import ec.gob.mdg.control.service.IFin_Guias_DTOService;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.service.IUsuarioPuntoService;
import ec.gob.mdg.validaciones.FunValidaciones;

@Named
@ViewScoped
public class GuiasBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IFin_Guias_DTOService serviceGuias;

	@Inject
	private DBcyf dbcyf;

	@Inject
	private IUsuarioPuntoService serviceUsuPunto;

	private UsuarioPunto usuPunto = new UsuarioPunto();	
	
	Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	
	private List<Fin_Guias_DTO> listaGuias;
	private Fin_Guias_DTO Guias_DTO;

	Integer codigo;
	String param2;
	String paramnumsolicitud;
	String paramtipo;
	String tipo;
	Integer codentidad;
	String numsolicitud;
	Integer punto;

	String num_solicitud;
	Double total = 0.00;

	@PostConstruct
	public void init() {
		try {
			
			usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(usuario);
			punto = usuPunto.getPuntoRecaudacion().getId();
			listarServiciosGuias();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public String getParam() {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
	}

	public void listarServiciosGuias() {
		
		param2 = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
		codigo = Integer.parseInt(param2);
		try {
			
			//dbcyf.actualizaGuiasAuxNull(codigo,punto);
			this.Guias_DTO = serviceGuias.mostrarEntidadGuias(codigo);
			this.listaGuias = this.serviceGuias.listarGuiasPorCodigo(codigo);
			for (Fin_Guias_DTO f : listaGuias) {
				total = total + f.getValor_pago();
				total =  FunValidaciones.formatearDecimales(total, 2);
				num_solicitud = f.getNum_solicitud();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// IR A INGRESO DE FACTURAS SERVICIOS CYF
	public String irGenerarFacturas(String codigo) {
//		System.out.println("ir a facturar codigo " + codigo);
		String tipo = "G";
		final FacesContext context = FacesContext.getCurrentInstance();
		final Flash flash = context.getExternalContext().getFlash();
		flash.put("param", codigo);
		flash.put("paramtipo", tipo); // parametro para guias
		return "facturaingservicioscyf?faces-redirect=true";

	}

	public void totalValor(boolean est, double mont) {
		if (est) {
			total = total + mont;
			total =  FunValidaciones.formatearDecimales(total, 2);
		} else {
			total = total - mont;
			total =  FunValidaciones.formatearDecimales(total, 2);
		}
	}

	// Actualizar en la base de SISCYF Importaciones el campo aux
	@Transactional
	public void actguiasfact() throws Exception {
		for (Fin_Guias_DTO gui : listaGuias) {
			if (gui.isAux()) {
				dbcyf.actualizaGuiasAux(gui.getCod_entidad(), gui.getNum_solicitud(),punto);
			}
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

	
	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public DBcyf getDbcyf() {
		return dbcyf;
	}

	public void setDbcyf(DBcyf dbcyf) {
		this.dbcyf = dbcyf;
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

	public UsuarioPunto getUsuPunto() {
		return usuPunto;
	}

	public void setUsuPunto(UsuarioPunto usuPunto) {
		this.usuPunto = usuPunto;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Integer getPunto() {
		return punto;
	}

	public void setPunto(Integer punto) {
		this.punto = punto;
	}

	public List<Fin_Guias_DTO> getListaGuias() {
		return listaGuias;
	}

	public void setListaGuias(List<Fin_Guias_DTO> listaGuias) {
		this.listaGuias = listaGuias;
	}

	public Fin_Guias_DTO getGuias_DTO() {
		return Guias_DTO;
	}

	public void setGuias_DTO(Fin_Guias_DTO guias_DTO) {
		Guias_DTO = guias_DTO;
	}

	
	
}
