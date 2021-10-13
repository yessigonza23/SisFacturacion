package ec.gob.mdg.control.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.gob.mdg.control.model.Fin_Importaciones_No_DTO;
import ec.gob.mdg.control.service.IFin_Importaciones_No_DTOService;

@Named
@ViewScoped
public class ConsultaServiciosCyfImpNoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Inject
	private IFin_Importaciones_No_DTOService serviceImportacionesNo;

	private List<Fin_Importaciones_No_DTO> listaImportacionesNo;
	
	@PostConstruct
	public void init() {
		try {
			listarEntidadesImportacionesNo();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	////////////////////////////////////////////////////////////////////////////////////
	// LISTAR ENTIDADES QUE REGISTRAN IMPORTACIONES NO CONTROLADOS
	public void listarEntidadesImportacionesNo() {
		try {

			this.listaImportacionesNo = serviceImportacionesNo.listarEntidadesImpNo();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	///// GETTERS Y SETTERS

	public List<Fin_Importaciones_No_DTO> getListaImportacionesNo() {
		return listaImportacionesNo;
	}

	public void setListaImportacionesNo(List<Fin_Importaciones_No_DTO> listaImportacionesNo) {
		this.listaImportacionesNo = listaImportacionesNo;
	}

}
