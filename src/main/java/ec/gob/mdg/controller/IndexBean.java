package ec.gob.mdg.controller;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioRol;
import ec.gob.mdg.service.IUsuarioService;
import ec.gob.mdg.util.UtilsDate;
import ec.gob.mdg.validaciones.Funciones;

@Named
@ViewScoped
public class IndexBean implements Serializable {

	private static final long serialVersionUID = 992085370965349203L;

	@Inject
	private IUsuarioService serviceUsuario;

	@Inject
	private Funciones fun;

	private Usuario us;
	private List<UsuarioRol> listaUsuarioRol;
	Date fechaActual;
	Integer contador;

	@PostConstruct
	public void init() {
		fechaActual = UtilsDate.fechaActual();
		this.us = new Usuario();
		contador = 0;
	}

	public String login() throws Exception {
		String redireccion = "";

		boolean respuesta = serviceUsuario.existeUsuario(us.getUsername());
		boolean respuestaB = fun.usuarioBloqueado(us.getUsername());

		if (respuesta == false) {
			try {
				System.out.println("entra a usuario no existe");
				fun.insertaUsuarioSesiones(fechaActual, us.getUsername(), "Fallido", "Usuario no Existe");

				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"Credenciales incorrectas, Usuario no existe ", "Aviso, "));
			} catch (Exception e) {
				System.out.println("error usuario existe null " + e);
				e.printStackTrace();
			}

		} else if (respuesta == true) {

			if (respuestaB == true) {
				fun.insertaUsuarioSesiones(fechaActual, us.getUsername(), "Fallido", "Usuario Bloqueado");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,
						"El usuario ha sido bloqueado, enviar correo a ingrid.weir@ministeriodegobierno.gob.ec", "Aviso"));
			} else if (respuestaB == false) {
				try {
					Usuario usuario = serviceUsuario.login(us);

					if (usuario != null && usuario.getEstado().equalsIgnoreCase("A")) {

						if (usuario.getFechacambioclave() == null) {
							FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario",
									usuario);
							redireccion = "/cambioClave?faces-redirect=true";
						} else if (usuario.getFechareinicioclave()!= null) {
							FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario",
									usuario);
							redireccion = "/cambioClave?faces-redirect=true";
						} else if (usuario.getFechacambioclave() != null) {
							Date fecha = usuario.getFechacambioclave();
							Calendar calendar = Calendar.getInstance();
							calendar.setTime(fecha);
							calendar.add(Calendar.DAY_OF_YEAR, 90);
							Date fechaS = calendar.getTime();

							if (fechaS.compareTo(fechaActual) < 0) {
								FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario",
										usuario);
								redireccion = "/cambioClave?faces-redirect=true";
							} else {
								FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("usuario",
										usuario);
								redireccion = "/protegido/principal?faces-redirect=true";
							}
						}

						fun.insertaUsuarioSesiones(fechaActual, usuario.getUsername(), "Exitoso", "Usuario correcto");

					} else {

						contador++;

						fun.insertaUsuarioSesiones(fechaActual, us.getUsername(), "Fallido", "Contraseña Incorrecta");

						if (contador < 3) {

							FacesContext.getCurrentInstance().addMessage(null,
									new FacesMessage(FacesMessage.SEVERITY_WARN,
											"Credenciales incorrectas, " + contador
													+ " de 3 intentos, al tercer intento el usuario será bloqueado",
											"Aviso, "));
						} else {
							System.out.println("ENTRA MAS DE 3");
							fun.actualizaEstadosUsuarioUsername(us.getUsername());

							FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
									FacesMessage.SEVERITY_WARN,
									"Credenciales incorrectas, el usuario fue bloqueado, comuníquese con la Dirección Financiera",
									"Aviso"));
						}

					}
				} catch (Exception e) {

					if (contador > 3) {
						try {
							fun.insertaUsuarioSesiones(fechaActual, us.getUsername(), "Fallido", "Usuario Bloqueado");
							FacesContext.getCurrentInstance().addMessage(null,
									new FacesMessage(FacesMessage.SEVERITY_WARN,
											"El usuario ha sido bloqueado, comuníquese con la Dirección Financiera",
											"Aviso"));
						} catch (Exception e1) {
							System.out.println("error " + e);
							e1.printStackTrace();
						}

					}
				}
			}

		}

		return redireccion;
	}

	//// getters y setters
	public List<UsuarioRol> getListaUsuarioRol() {
		return listaUsuarioRol;
	}

	public void setListaUsuarioRol(List<UsuarioRol> listaUsuarioRol) {
		this.listaUsuarioRol = listaUsuarioRol;
	}

	public Usuario getUs() {
		return us;
	}

	public void setUs(Usuario us) {
		this.us = us;
	}

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public Integer getContador() {
		return contador;
	}

	public void setContador(Integer contador) {
		this.contador = contador;
	}

}
