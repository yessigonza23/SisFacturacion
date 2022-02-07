package ec.gob.mdg.controller;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import ec.gob.mdg.model.PuntoRecaudacion;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.service.IAuditoriaService;
import ec.gob.mdg.service.IPuntoRecaudacionService;
import ec.gob.mdg.service.IUsuarioPuntoService;
import ec.gob.mdg.service.IUsuarioService;
import ec.gob.mdg.util.UtilsDate;
import ec.gob.mdg.validaciones.Funciones;

@Named
@ViewScoped
public class UsuarioPuntoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IUsuarioService serviceUsuario;

	@Inject
	private IUsuarioPuntoService serviceUsuarioPunto;

	@Inject
	private IPuntoRecaudacionService servicePuntoRecaudacion;

	@Inject
	private Funciones fun;
	
	@Inject
	private IAuditoriaService serviceAuditoria;

	private Usuario usuario;
	private UsuarioPunto usuarioPunto;

	private List<PuntoRecaudacion> listaPuntoRecaudacion;
	private List<UsuarioPunto> listaUsuarioPunto;

	String param2 = null;
	Integer id = 0;
	Integer id_usuariopunto;
	boolean estadeshabilitado;
	private String tipoDialog;
	Date fechaActual;

	Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	
	@PostConstruct
	public void init() {
		try {
			this.usuarioPunto = new UsuarioPunto();
			mostrarNombreUsuario();
			this.tipoDialog = "Nuevo Registro";
			listaPuntosRecaudacion();
			fechaActual = UtilsDate.fechaActual();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void mostrarNombreUsuario() {
		param2 = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
		id = Integer.parseInt(param2);
		this.usuario = serviceUsuario.mostrarUsuarioPorId(id);
//		this.usuarioPunto = serviceUsuarioPunto.mostrarUsuarioPuntoPorIdUsuario(id);
		listaUsuariosPuntos();
	}

	public void listaUsuariosPuntos() {
		this.listaUsuarioPunto = serviceUsuarioPunto.listarUsuarioPunto(usuario);
	}

	public void listaPuntosRecaudacion() {
		try {
			this.listaPuntoRecaudacion = servicePuntoRecaudacion.listar();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
@Transactional
	public void operar(String accion) {
		try {
			if (accion.equalsIgnoreCase("R")) {
				UsuarioPunto up = new UsuarioPunto();
				up.setEstado(usuarioPunto.getEstado());
				up.setFechainicio(usuarioPunto.getFechainicio());
//				up.setFechafin(usuarioPunto.getFechafin());
				up.setPuntoRecaudacion(usuarioPunto.getPuntoRecaudacion());
				up.setUsuario(usuario);
				id_usuariopunto = this.serviceUsuarioPunto.registrar(up);
		
				estadeshabilitado = true;

				fun.actualizaEstadosUsuarioPunto(id_usuariopunto,usuario.getId());
			
				////AUDITORIA
				serviceAuditoria.insertaModificacion("UsuarioPunto",
						"id_usuariopunto", "I", us.getUsername(),
						fechaActual, usuarioPunto.getEstado(),
						usuarioPunto.getEstado(), id_usuariopunto);
			
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Se grabó con éxito", "Aviso"));
			}


			listaUsuariosPuntos();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void mostrarData(UsuarioPunto i) {
		this.usuarioPunto = i;
		this.tipoDialog = "Modificar Registro";
	}

	public void limpiarControles() {
		this.usuarioPunto = new UsuarioPunto();
		this.tipoDialog = "Nuevo Registro";
	}

	// MODIFICAR USUARIO PUNTO
	@Transactional
	public void modificar(Integer id_usuariopunto, String estado) {
		try {

//			System.out.println("parametros " + id_usuariopunto + " - " + estado);
			if (estado.equals("A")) {
				
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"No puede volver a activar un usuario, debe ingresarlo nuevamente", "Error"));
			} else {
				for (UsuarioPunto up : listaUsuarioPunto) {
					
					if (up.getId().equals(id_usuariopunto)) {
						
						if (estado.equals("I")) {
							up.setFechafin(fechaActual);
						}
						up.setEstado(estado);
						fun.actualizaUsuarioPunto(id_usuariopunto, up.getFechafin(), up.getEstado());
						
						///AUDITORIA
						serviceAuditoria.insertaModificacion("UsuarioPunto",
								"estado", "U", us.getUsername(),
								fechaActual, 
								up.getEstado(),"A", id_usuariopunto);
					}
				}
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Se grab� la modificaci�n con �xito", "Aviso"));
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// GETTERS Y SETTERS
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public UsuarioPunto getUsuarioPunto() {
		return usuarioPunto;
	}

	public void setUsuarioPunto(UsuarioPunto usuarioPunto) {
		this.usuarioPunto = usuarioPunto;
	}

	public List<PuntoRecaudacion> getListaPuntoRecaudacion() {
		return listaPuntoRecaudacion;
	}

	public void setListaPuntoRecaudacion(List<PuntoRecaudacion> listaPuntoRecaudacion) {
		this.listaPuntoRecaudacion = listaPuntoRecaudacion;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public boolean isEstadeshabilitado() {
		return estadeshabilitado;
	}

	public void setEstadeshabilitado_(boolean estadeshabilitado) {
		this.estadeshabilitado = estadeshabilitado;
	}

	public List<UsuarioPunto> getListaUsuarioPunto() {
		return listaUsuarioPunto;
	}

	public void setListaUsuarioPunto(List<UsuarioPunto> listaUsuarioPunto) {
		this.listaUsuarioPunto = listaUsuarioPunto;
	}

	public void setEstadeshabilitado(boolean estadeshabilitado) {
		this.estadeshabilitado = estadeshabilitado;
	}

	public String getTipoDialog() {
		return tipoDialog;
	}

	public void setTipoDialog(String tipoDialog) {
		this.tipoDialog = tipoDialog;
	}

}
