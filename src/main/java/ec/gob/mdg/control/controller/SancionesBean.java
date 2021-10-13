package ec.gob.mdg.control.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import ec.gob.mdg.control.actualizaciones.DBsanciones;
import ec.gob.mdg.control.model.Sanciones_Admin_DTO;
import ec.gob.mdg.control.service.ISanciones_Admin_DTOService;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.service.IUsuarioPuntoService;

@Named
@ViewScoped
public class SancionesBean  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private ISanciones_Admin_DTOService serviceSancionesAdmin;
	
	@Inject
	private DBsanciones dbsanciones;
	
	@Inject
	private IUsuarioPuntoService serviceUsuPunto;
	
	private UsuarioPunto usuPunto = new UsuarioPunto();	
	Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	Integer punto;

	private List<Sanciones_Admin_DTO> listaSanciones = new ArrayList<Sanciones_Admin_DTO>();
	private Sanciones_Admin_DTO sanciones;

	Integer codigo;
	String ruc;
	String numjuicio;
	Double total = 0.00;
	String codigoentidad;

	@PostConstruct
	public void init() {
		try {
			usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(usuario);
			punto = usuPunto.getPuntoRecaudacion().getId();
			listarServicios();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public String getParam() {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
	}

	public void listarServicios() {
		ruc = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");

		try {
			//carga datos de empresa			
			this.sanciones = serviceSancionesAdmin.mostrarEntidad(ruc);
			//borrar el auxiliar para nueva seleccion
			dbsanciones.actualizaSancionesAuxNull(sanciones.getCodigo_empresa(),punto);
			codigoentidad = sanciones.getCodigo_empresa();
			this.listaSanciones = this.serviceSancionesAdmin.listarSancionesPorCodigo(ruc);
			
			for (Sanciones_Admin_DTO f : listaSanciones) {
				total = total + f.getValor();
				numjuicio = f.getNumero_juicio();				
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// IR A INGRESO DE FACTURAS SERVICIOS CYF
	public String irGenerarFacturas(String ruc,String codigoentidad) {
		
		final FacesContext context = FacesContext.getCurrentInstance();
		final Flash flash = context.getExternalContext().getFlash();
		flash.put("param", ruc);
		flash.put("paramtipo", "S");
		return "facturaingservicioscyf?faces-redirect=true";
	
	}	
	
	public void totalValor(boolean est, double mont) {
		if (est ) {
			total = total + mont;
			System.out.println("total : " + total);
		} else {
			total = total - mont;

		}
	}
	
	
	//Actualizar en la base de SISCYF  Sanciones que ponga el aux en S
	@Transactional
	public void actsancionfact() throws Exception {
		for (Sanciones_Admin_DTO san: listaSanciones) {
			if (san.isAux()) {
				ruc = san.getRuc();
				numjuicio = san.getNumero_juicio();
				dbsanciones.actualizaSancionesAux(san.getCodigo_empresa(), numjuicio,punto);
			}
		}
	}

	///// GETTERS Y SETTERS

	public List<Sanciones_Admin_DTO> getListaSanciones() {
		return listaSanciones;
	}

	public void setListaSanciones(List<Sanciones_Admin_DTO> listaSanciones) {
		this.listaSanciones = listaSanciones;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}



	public String getNumjuicio() {
		return numjuicio;
	}

	public void setNumjuicio(String numjuicio) {
		this.numjuicio = numjuicio;
	}

	public String getCodigoentidad() {
		return codigoentidad;
	}

	public void setCodigoentidad(String codigoentidad) {
		this.codigoentidad = codigoentidad;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public Sanciones_Admin_DTO getSanciones() {
		return sanciones;
	}

	public void setSanciones(Sanciones_Admin_DTO sanciones) {
		this.sanciones = sanciones;
	}


}
