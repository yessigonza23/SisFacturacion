package ec.gob.mdg.control.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import ec.gob.mdg.control.model.Fin_Reptecnicos_DTO;
import ec.gob.mdg.control.service.IFin_Reptecnicos_DTOService;

@Named
@ViewScoped
public class ConsultaServiciosCyfRepTecBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IFin_Reptecnicos_DTOService serviceRepTecnicos;

	private List<Fin_Reptecnicos_DTO> listaRepTecnicos;

	@PostConstruct
	public void init() {
		try {
			listarRepTecnicos();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

////////////////////////////////////////////////////////////////////////////////////
// LISTAR ENTIDADES QUE REGISTRAN REPRESENTANTES TECNICOS
	public void listarRepTecnicos() {
		try {
			this.listaRepTecnicos = serviceRepTecnicos.listarRepTecnico();
		} catch (Exception e) {
       // TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	///// GETTERS Y SETTERS
	
	public List<Fin_Reptecnicos_DTO> getListaRepTecnicos() {
		return listaRepTecnicos;
	}

	public void setListaRepTecnicos(List<Fin_Reptecnicos_DTO> listaRepTecnicos) {
		this.listaRepTecnicos = listaRepTecnicos;
	}

}
