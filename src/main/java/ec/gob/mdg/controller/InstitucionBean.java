package ec.gob.mdg.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.gob.mdg.model.Institucion;
import ec.gob.mdg.service.IInstitucionService;

@Named
@ViewScoped
public class InstitucionBean implements Serializable {

	private static final long serialVersionUID = -3058788986113968170L;

	@Inject
	private IInstitucionService service;
	
	private List<Institucion> lista;
	private String tipoDialog;
	private Institucion institucion;
	
	@PostConstruct
	public void init() {
		this.institucion = new Institucion();
		this.listar();
		this.tipoDialog = "Nuevo";
	}
	
	public void operar(String accion) {
		try {
			if(accion.equalsIgnoreCase("R")) {
				this.service.registrar(this.institucion);
			}else if(accion.equalsIgnoreCase("M")) {
				this.service.modificar(this.institucion);
			}
			this.listar();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void listar() {
		try {
			this.lista = this.service.listar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		
	public void mostrarData(Institucion i) {
		this.institucion = i;
		this.tipoDialog = "Modificar Institución";
	}
	
	public void limpiarControles() {
		this.institucion = new Institucion();
		this.tipoDialog = "Nueva Institución";
	}
	
	public String getTipoDialog() {
		return tipoDialog;
	}

	public void setTipoDialog(String tipoDialog) {
		this.tipoDialog = tipoDialog;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
	}

	public List<Institucion> getLista() {
		return lista;
	}

	public void setLista(List<Institucion> lista) {
		this.lista = lista;
	}
}
