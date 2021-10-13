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
import ec.gob.mdg.control.model.Fin_Reptecnicos_DTO;
import ec.gob.mdg.control.service.IFin_Reptecnicos_DTOService;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.service.IUsuarioPuntoService;

@Named
@ViewScoped
public class RepTecnicoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IFin_Reptecnicos_DTOService serviceRepTecnicos;

	@Inject
	private DBcyf dbcyf;

	@Inject
	private IUsuarioPuntoService serviceUsuPunto;

	private UsuarioPunto usuPunto = new UsuarioPunto();
	Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	Integer punto;

	private List<Fin_Reptecnicos_DTO> listaCalRenRepTecnicos;

	private Fin_Reptecnicos_DTO repTecnico;

	Integer codigoidtec;
	String param2;
	String paramtipo;
	Double total = 0.00;
	Integer codigocalrenreptec;

	@PostConstruct
	public void init() {
		try {
			usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(usuario);
			punto = usuPunto.getPuntoRecaudacion().getId();
			listarCalRenRepTecnico();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public String getParam() {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
	}

	public void listarCalRenRepTecnico() {
		param2 = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
		codigoidtec = Integer.parseInt(param2);
		try {
			dbcyf.actualizaCalRenRepTecnicoAuxNull(codigoidtec, punto);
			this.repTecnico = serviceRepTecnicos.mostarRepTecnico(codigoidtec);
			this.listaCalRenRepTecnicos = this.serviceRepTecnicos.listarCalRenRepTecnicoPorIdTec(codigoidtec);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// IR A INGRESO DE FACTURAS SERVICIOS CYF
	public String irGenerarFacturas(String idtec) {
		final FacesContext context = FacesContext.getCurrentInstance();
		final Flash flash = context.getExternalContext().getFlash();
		flash.put("paramidtec", idtec);
		flash.put("paramtipo", "T"); 
		return "facturaingservicioscyf?faces-redirect=true";
	}

	// Actualizar en la base de SISCYF Reptecnicos el campo aux
	@Transactional
	public void actcalrenreptecnicofact() throws Exception {
		for (Fin_Reptecnicos_DTO crrt : listaCalRenRepTecnicos) {
			if (crrt.isAux()) {
				codigocalrenreptec = crrt.getCodigo_calren();
				dbcyf.actualizaCalRenRepTecnicoAux(codigoidtec, codigocalrenreptec, punto);
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

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getParamtipo() {
		return paramtipo;
	}

	public void setParamtipo(String paramtipo) {
		this.paramtipo = paramtipo;
	}

	public DBcyf getDbcyf() {
		return dbcyf;
	}

	public void setDbcyf(DBcyf dbcyf) {
		this.dbcyf = dbcyf;
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

	public List<Fin_Reptecnicos_DTO> getListaCalRenRepTecnicos() {
		return listaCalRenRepTecnicos;
	}

	public void setListaCalRenRepTecnicos(List<Fin_Reptecnicos_DTO> listaCalRenRepTecnicos) {
		this.listaCalRenRepTecnicos = listaCalRenRepTecnicos;
	}

	public Fin_Reptecnicos_DTO getRepTecnico() {
		return repTecnico;
	}

	public void setRepTecnico(Fin_Reptecnicos_DTO repTecnico) {
		this.repTecnico = repTecnico;
	}

	public Integer getCodigoidtec() {
		return codigoidtec;
	}

	public void setCodigoidtec(Integer codigoidtec) {
		this.codigoidtec = codigoidtec;
	}

	public Integer getCodigocalrenreptec() {
		return codigocalrenreptec;
	}

	public void setCodigocalrenreptec(Integer codigocalrenreptec) {
		this.codigocalrenreptec = codigocalrenreptec;
	}

}
