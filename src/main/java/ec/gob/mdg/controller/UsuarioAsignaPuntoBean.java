package ec.gob.mdg.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.mindrot.jbcrypt.BCrypt;

import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.service.IUsuarioService;

@Named
@ViewScoped
public class UsuarioAsignaPuntoBean implements Serializable {

	private static final long serialVersionUID = -6912327508466146927L;

	@Inject
	private IUsuarioService serviceUsuario;



	private List<Usuario> listaUsuario = new ArrayList<>();
	boolean estadeshabilitado;
	boolean estadeshabilitado_ap = true;
	Integer idusuario = 0;
	boolean valida=false;
	private String tipoDialog;
	String contrasena;
	
	private Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("param");
	
	@PostConstruct
	public void init() {
		
		usuario = new Usuario();
		cargaDatosUsuario();
	}

	// Modificar usuario
		public void modificar() {

			try {
				//Usuario usu = new Usuario();
				String clave = this.usuario.getContrasena();	
				System.out.println("contrasenia " + usuario.getContrasena());
				String claveHash = BCrypt.hashpw(clave, BCrypt.gensalt());
				
				usuario.setContrasena(claveHash);
				usuario.setNombre(usuario.getNombre().toUpperCase());
				usuario.setApellido(usuario.getApellido().toUpperCase());
				usuario.setTelefono(usuario.getTelefono());
				usuario.setCorreoelectronico(usuario.getCorreoelectronico().toLowerCase());
				usuario.setCargo(usuario.getCargo().toUpperCase());
				usuario.setUsername(usuario.getUsername().toLowerCase());
				usuario.setEstado(usuario.getEstado());
				this.serviceUsuario.modificar(usuario);

				estadeshabilitado = true;
				
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Se grabó con éxito", "Aviso"));

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	//VALIDA SI EXISTE EL USUARIO
	public boolean validaCedula(String cedula) {
		valida=serviceUsuario.validaUsuarioCedula(cedula);
		System.out.println("imprime el valida : "+valida);
		if(valida==true) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Esta cédula ya fue ingresada", "ERROR"));
		}
		return valida;
	}

	public void listarUsuarioPunto() {
		try {
			this.listaUsuario = this.serviceUsuario.listar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public void cargaDatosUsuario() {
		String param2 = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
		// PARAMETRO PARA CODIGO DE LA ENTDIDAD
		Integer id_usuario = Integer.parseInt(param2);
		usuario = serviceUsuario.mostrarUsuarioPorId(id_usuario);
	}
	
	
	
	public void operar(String accion) {
		try {
			 if(accion.equalsIgnoreCase("M")) {
				 contrasena="1";
//				 String clave = contrasena;	
//					System.out.println("contrase operador " + contrasena);
//					String claveHash = BCrypt.hashpw(clave, BCrypt.gensalt());
//					
//					usuario.setContrasena(claveHash);
					usuario.setNombre(usuario.getNombre().toUpperCase());
					usuario.setApellido(usuario.getApellido().toUpperCase());
					usuario.setTelefono(usuario.getTelefono());
					usuario.setCorreoelectronico(usuario.getCorreoelectronico().toLowerCase());
					usuario.setCargo(usuario.getCargo().toUpperCase());
					usuario.setUsername(usuario.getUsername().toLowerCase());
					usuario.setEstado(usuario.getEstado());
					
				this.serviceUsuario.modificar(this.usuario);
				
			}
			this.listarUsuarioPunto();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void mostrarData(Usuario i) {
		this.usuario = i;
		this.tipoDialog = "Modificar Usuario";
	}
	////
	
	public Integer getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(Integer idusuario) {
		this.idusuario = idusuario;
	}
	
	public boolean isValida() {
		return valida;
	}

	public void setValida(boolean valida) {
		this.valida = valida;
	}


	public List<Usuario> getListaUsuario() {
		return listaUsuario;
	}

	public void setListaUsuario(List<Usuario> listaUsuario) {
		this.listaUsuario = listaUsuario;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isEstadeshabilitado() {
		return estadeshabilitado;
	}

	public void setEstadeshabilitado(boolean estadeshabilitado) {
		this.estadeshabilitado = estadeshabilitado;
	}

	public boolean isEstadeshabilitado_ap() {
		return estadeshabilitado_ap;
	}

	public void setEstadeshabilitado_ap(boolean estadeshabilitado_ap) {
		this.estadeshabilitado_ap = estadeshabilitado_ap;
	}

	public String getTipoDialog() {
		return tipoDialog;
	}

	public void setTipoDialog(String tipoDialog) {
		this.tipoDialog = tipoDialog;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasena) {
		this.contrasena = contrasena;
	}
	

}
