package ec.gob.mdg.control.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.gob.mdg.control.model.Fin_Cal_Ren_DTO;
import ec.gob.mdg.control.service.IFin_Cal_Ren_DTOService;

@Named
@ViewScoped
public class ConsultaServiciosCyfCalBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IFin_Cal_Ren_DTOService serviceFinCalRenDTO;

	private List<Fin_Cal_Ren_DTO> listaFinCalRenDTO;

	
	@PostConstruct
	public void init() {
		try {

			listarFinCalRenDTO();
		
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// LISTAR ENTIDADES PARA CALIFICACIONES Y RENOVACIONES (CUPO, ACTIVIDADES,
	// VEHICULOS, SITIOS)
	public void listarFinCalRenDTO() {
		try {
			this.listaFinCalRenDTO = serviceFinCalRenDTO.listarEntidades();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	///// GETTERS Y SETTERS
	public List<Fin_Cal_Ren_DTO> getListaFinCalRenDTO() {
		return listaFinCalRenDTO;
	}

	public void setListaFinCalRenDTO(List<Fin_Cal_Ren_DTO> listaFinCalRenDTO) {
		this.listaFinCalRenDTO = listaFinCalRenDTO;
	}

	
}
