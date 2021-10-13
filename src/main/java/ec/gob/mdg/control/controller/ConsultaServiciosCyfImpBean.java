package ec.gob.mdg.control.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.gob.mdg.control.model.Fin_Importaciones_DTO;
import ec.gob.mdg.control.service.IFin_Importaciones_DTOService;

@Named
@ViewScoped
public class ConsultaServiciosCyfImpBean implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Inject
	private IFin_Importaciones_DTOService serviceImportaciones;

	private List<Fin_Importaciones_DTO> listaImportaciones;

	@PostConstruct
	public void init() {
		try {
			listarEntidadesImportaciones();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////
	// LISTAR ENTIDADES QUE REGISTRAN IMPORTACIONES
	public void listarEntidadesImportaciones() {
		try {
			this.listaImportaciones = serviceImportaciones.listarEntidadesImp();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	///// GETTERS Y SETTERS
	

	public List<Fin_Importaciones_DTO> getListaImportaciones() {
		return listaImportaciones;
	}

	public void setListaImportaciones(List<Fin_Importaciones_DTO> listaImportaciones) {
		this.listaImportaciones = listaImportaciones;
	}

	
}
