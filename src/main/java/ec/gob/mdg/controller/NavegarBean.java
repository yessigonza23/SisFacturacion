package ec.gob.mdg.controller;

import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@Named
@ViewScoped
public class NavegarBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public void irDetalle() {
        try {	
        	
            FacesContext.getCurrentInstance().getExternalContext().redirect("./../protegido/consultarfacturas.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	public void principal() {
		try {

			FacesContext.getCurrentInstance().getExternalContext().redirect("./../principal.xhtml");
		} catch (Exception e) {
			System.out.println("FALLO LA REDIRECCION A UN NUEVO FORMULARIO");
			e.printStackTrace();
		}
	}
}
