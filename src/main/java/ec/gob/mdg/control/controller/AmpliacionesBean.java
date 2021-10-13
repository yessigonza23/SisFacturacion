package ec.gob.mdg.control.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.gob.mdg.control.model.Fin_Cat_Act_Sit_Veh_Java_DTO;
import ec.gob.mdg.control.service.IFin_Cat_Act_Sit_Veh_Java_DTOService;


@Named
@ViewScoped
public class AmpliacionesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IFin_Cat_Act_Sit_Veh_Java_DTOService serviceCambioCategoria;

	private List<Fin_Cat_Act_Sit_Veh_Java_DTO> listaCambioCategoria;
	private Fin_Cat_Act_Sit_Veh_Java_DTO cambioCategoria;

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
		this.cambioCategoria = serviceCambioCategoria.mostrarEntidad(codigo);
		this.listaCambioCategoria = serviceCambioCategoria.listarServiciosPorCodigo(codigo);

		for (Fin_Cat_Act_Sit_Veh_Java_DTO f : listaCambioCategoria) {
			total = total + f.getValor();
		}
	}
	
	///IR A INGRESO DE FACTURA
	public String irGenerarFactura(String codigo) {
		String tipo="C";
		final FacesContext context = FacesContext.getCurrentInstance();
		final Flash flash = context.getExternalContext().getFlash();
		flash.put("param", codigo);
		flash.put("paramtipo", tipo); 
		return "facturaingservicioscyf?faces-redirect=true";
	}

	///// GETTERS Y SETTERS

	public List<Fin_Cat_Act_Sit_Veh_Java_DTO> getListaCambioCategoria() {
		return listaCambioCategoria;
	}

	public void setListaCambioCategoria(List<Fin_Cat_Act_Sit_Veh_Java_DTO> listaCambioCategoria) {
		this.listaCambioCategoria = listaCambioCategoria;
	}

	public Fin_Cat_Act_Sit_Veh_Java_DTO getCambioCategoria() {
		return cambioCategoria;
	}

	public void setCambioCategoria(Fin_Cat_Act_Sit_Veh_Java_DTO cambioCategoria) {
		this.cambioCategoria = cambioCategoria;
	}

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

}
