package ec.gob.mdg.control.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.gob.mdg.control.model.Fin_Cat_Act_Sit_Veh_Java_DTO;
import ec.gob.mdg.control.service.IFin_Cat_Act_Sit_Veh_Java_DTOService;

@Named
@ViewScoped
public class ConsultaServiciosCyfAmpBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IFin_Cat_Act_Sit_Veh_Java_DTOService serviceCambioCategoria;

	private List<Fin_Cat_Act_Sit_Veh_Java_DTO> listaCambioCategoria;

	@PostConstruct
	public void init() {
		try {	
			listarCambioCategoria();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// LISTAR ENTIDADES PARA AMPLIACIONES DE CUPO - CAMBIO DE CATEGORIA
	public void listarCambioCategoria() {
		try {
			this.listaCambioCategoria = serviceCambioCategoria.listarEntidades();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	///// GETTERS Y SETTERS
	
	public List<Fin_Cat_Act_Sit_Veh_Java_DTO> getListaCambioCategoria() {
		return listaCambioCategoria;
	}

	public void setListaCambioCategoria(List<Fin_Cat_Act_Sit_Veh_Java_DTO> listaCambioCategoria) {
		this.listaCambioCategoria = listaCambioCategoria;
	}
	
}
