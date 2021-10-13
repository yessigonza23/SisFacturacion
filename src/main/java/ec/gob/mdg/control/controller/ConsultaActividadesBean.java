package ec.gob.mdg.control.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.gob.mdg.control.model.Fin_Ent_Actividades;
import ec.gob.mdg.control.service.IFin_Ent_ActividadesService;

@Named
@ViewScoped
public class ConsultaActividadesBean implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Inject
	private IFin_Ent_ActividadesService service;
	
	private List<Fin_Ent_Actividades> lista;
	
	@PostConstruct
	public void init() {
		try {
			listar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	public void listar() {
		try {
			this.lista = service.listar();
//			for(Fin_Ent_Actividades f : lista) {
//				System.out.println("Empresa " + f.getActividades_calificacion());
//			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public List<Fin_Ent_Actividades> getLista() {
		return lista;
	}


	public void setLista(List<Fin_Ent_Actividades> lista) {
		this.lista = lista;
	}

	
	
}
