package ec.gob.mdg.control.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.gob.mdg.control.model.Fin_Exportaciones_DTO;
import ec.gob.mdg.control.service.IFin_Exportaciones_DTOService;

@Named
@ViewScoped
public class ConsultaServiciosCyfExpBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IFin_Exportaciones_DTOService serviceExportaciones;

	private List<Fin_Exportaciones_DTO> listaExportaciones;

	@PostConstruct
	public void init() {
		try {

			listarEntidadesExportacion();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	////////////////////////////////////////////////////////////////////////////////////
	// LISTAR ENTIDADES QUE REGISTRAN EXPORTACIONES
	public void listarEntidadesExportacion() {
		try {
			this.listaExportaciones = serviceExportaciones.listarEntidadesExp();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	///// GETTERS Y SETTERS
	

	public List<Fin_Exportaciones_DTO> getListaExportaciones() {
		return listaExportaciones;
	}

	public void setListaExportaciones(List<Fin_Exportaciones_DTO> listaExportaciones) {
		this.listaExportaciones = listaExportaciones;
	}

	

}
