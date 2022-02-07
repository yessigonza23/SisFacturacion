package ec.gob.mdg.controller;

import java.io.IOException;
import java.io.Serializable;

import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import ec.gob.mdg.model.Usuario;

@Named
@ViewScoped
public class MasterBean implements Serializable{

	private static final long serialVersionUID = 1486972864947926588L;

	public void verificarSesion() throws IOException {
		FacesContext context = FacesContext.getCurrentInstance();
		try {
			Usuario us = (Usuario) context.getExternalContext().getSessionMap().get("usuario");
			
			if(us == null ) {
				context.getExternalContext().redirect("./../index.xhtml");
			}
		}catch(Exception e) {
			context.getExternalContext().redirect("./../index.xhtml");
		}
	}
	
	public String mostrarUsuarioLogueado(){
        //captura del tipo de usuario que ha iniciado sesion
        Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
        return us.getNombre() +' ' + us.getApellido();
    }
	
	public void timeout() throws IOException {
	    cerrarSesion();
	    index();

	}
	
	public void index() {
		try {
			FacesContext.getCurrentInstance().getExternalContext().redirect("./../index.xhtml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String cerrarSesion() {
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
		return "index?faces-redirect=true"; 
	}
	
	
}
