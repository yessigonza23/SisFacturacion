package ec.gob.mdg.control.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.gob.mdg.control.model.Fin_Cal_Ren_DTO;
import ec.gob.mdg.control.service.IFin_Cal_Ren_DTOService;

@Named
@ViewScoped
public class CalificacionesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IFin_Cal_Ren_DTOService serviceCalRen;

	private List<Fin_Cal_Ren_DTO> listaCalRen;
	private Fin_Cal_Ren_DTO Cal_RenDTO;

	private String param2;
	private Integer codigo;
	Double total=0.00;

	@PostConstruct
	public void init() {
		try {
			listarServicios();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public String getParam() {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
	}

	/// LISTAR SERVICIOS DE ENTIDAD SELECCIONADA
	public void listarServicios() {
		param2 = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
		codigo = Integer.parseInt(param2);
		this.Cal_RenDTO = serviceCalRen.mostrarEntidad(codigo);
		this.listaCalRen = serviceCalRen.listarServiciosPorCodigo(codigo);

	}
	
	///IR A INGRESO DE FACTURA
	public String irGenerarFactura(String codigo) {
		String tipo="R";
		final FacesContext context = FacesContext.getCurrentInstance();
		final Flash flash = context.getExternalContext().getFlash();
		flash.put("param", codigo);
		flash.put("paramtipo", tipo); 
				
		return "facturaingservicioscyf?faces-redirect=true";
	}

	///// GETTERS Y SETTERS

	
	public String getParam2() {
		return param2;
	}

	public List<Fin_Cal_Ren_DTO> getListaCalRen() {
		return listaCalRen;
	}

	public void setListaCalRen(List<Fin_Cal_Ren_DTO> listaCalRen) {
		this.listaCalRen = listaCalRen;
	}

	public Fin_Cal_Ren_DTO getCal_RenDTO() {
		return Cal_RenDTO;
	}

	public void setCal_RenDTO(Fin_Cal_Ren_DTO cal_RenDTO) {
		Cal_RenDTO = cal_RenDTO;
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
	
	

}
