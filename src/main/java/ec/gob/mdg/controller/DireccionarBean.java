package ec.gob.mdg.controller;

import java.io.Serializable;

import javax.faces.context.FacesContext;

import ec.gob.mdg.model.Usuario;

public class DireccionarBean implements Serializable {

	private static final long serialVersionUID = 3014868269188904664L;
	private String mensaje;
	
	Usuario p = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	
	public DireccionarBean() {
		mensaje = "Acción Correcta";
	}

	public void direccionarPrincipal() {
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("./../protegido/principal.xhtml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Usuario getP() {
		return p;
	}

	public void setP(Usuario p) {
		this.p = p;
	}
	
	
}
