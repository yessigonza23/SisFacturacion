package ec.gob.mdg.control.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.gob.mdg.control.model.Fin_Guias_DTO;
import ec.gob.mdg.control.service.IFin_Guias_DTOService;

@Named
@ViewScoped
public class ConsultaServiciosCyfGuiBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IFin_Guias_DTOService serviceGuias;

	private List<Fin_Guias_DTO> listaGuias;

	@PostConstruct
	public void init() {
		try {
			listarEntidadesGuias();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	////////////////////////////////////////////////////////////////////////////////////
	// LISTAR ENTIDADES QUE REGISTRAN GUIAS
	public void listarEntidadesGuias() {
		try {

			this.listaGuias = serviceGuias.listarEntidadesGuias();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	

	///// GETTERS Y SETTERS
	public List<Fin_Guias_DTO> getListaGuias() {
		return listaGuias;
	}

	public void setListaGuias(List<Fin_Guias_DTO> listaGuias) {
		this.listaGuias = listaGuias;
	}

	
}
