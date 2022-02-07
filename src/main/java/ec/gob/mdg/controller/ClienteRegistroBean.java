package ec.gob.mdg.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import ec.gob.mdg.model.Cliente;
import ec.gob.mdg.service.IClienteService;
import ec.gob.mdg.util.CedulaRuc;
import ec.gob.mdg.validaciones.Funciones;

@Named
@ViewScoped
public class ClienteRegistroBean implements Serializable {

	private static final long serialVersionUID = -2830034019923061747L;

	@Inject
	private IClienteService service;

	@Inject
	private IClienteService serviceCliente;

	@Inject
	private Funciones fun;

	private List<Cliente> lista;
	private String tipoDialog;
	private Cliente cliente;
	private Cliente clientetmp;
	boolean validador = false;
	boolean valida = false;
	boolean estadeshabilitado = false;

	@PostConstruct
	public void init() {
		this.cliente = new Cliente();
		this.clientetmp = new Cliente();
	}

	/// VERIFICA SI EXISTE EL CLIENTE
	public void verificarExistencia(String ci) {
		if (ci == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "erro sin CI", "Error"));

		} else {
			this.clientetmp = service.ClientePorCiParametro(ci);
			if (clientetmp.getCi() != null) {
				validador = true;
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Este cliente ya existe", "Error"));
			}
		}

	}

	@Transactional
	public void registrar() {
		if (cliente == null) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Sin Datos", "Sin Datos"));
		} else {
			Cliente c = new Cliente();
			c.setTipoid(cliente.getTipoid());
			c.setCi(cliente.getCi());
			c.setDireccion(cliente.getDireccion().toUpperCase());
			c.setNombre(cliente.getNombre().toUpperCase());
			c.setTelefono(cliente.getTelefono());
			c.setCorreo(cliente.getCorreo().toLowerCase());
			try {
				this.service.registrar(c);
				estadeshabilitado = true;
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Se grabó con éxito", "Satisfactoriamente"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// NUEVO REGISTR0
	public Boolean nuevo() {
		estadeshabilitado = false;
		return estadeshabilitado;
	}

	public void limpiarControles() {
		this.cliente = new Cliente();
	}

	// VALIDADOR DE CEDULA-RUC
	public void validaIdentificacion() {
		if (cliente == null) {
			validador = true;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Tipo de identificacion erronea", "error"));
		} else {
			String validaIdentificacion = CedulaRuc.comprobacion(this.cliente.getCi(), this.cliente.getTipoid());
			if (validaIdentificacion.equals("T")) {
				validador = false;
			} else {
				validador = true;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Tipo de identificacion erronea", validaIdentificacion));
			}
		}

	}

	public void buscarClientes() {
		try {
			if (cliente == null) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ingrese el No. de Identificación", "Sin Datos"));
			} else {
				Long a = fun.buscarCliente(cliente);
				if (a == 0) {

					if (this.cliente.getCi() != null && this.cliente.getTipoid() != null) {
						validador = false;
						String validaIdentificacion = CedulaRuc.comprobacion(this.cliente.getCi(),
								this.cliente.getTipoid());
						if (!validaIdentificacion.equals("T")) {
							validador = true;
							FacesContext.getCurrentInstance().addMessage(null,
									new FacesMessage(FacesMessage.SEVERITY_ERROR,
											"Tipo de identificacion erronea buscar", validaIdentificacion));
						}
					}

				} else {
					validador = true;
					this.cliente = serviceCliente.ClientePorCiParametro(cliente.getCi());
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	///// GETTER Y SETTERS

	public List<Cliente> getLista() {
		return lista;
	}

	public void setLista(List<Cliente> lista) {
		this.lista = lista;
	}

	public String getTipoDialog() {
		return tipoDialog;
	}

	public void setTipoDialog(String tipoDialog) {
		this.tipoDialog = tipoDialog;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Cliente getClientetmp() {
		return clientetmp;
	}

	public void setClientetmp(Cliente clientetmp) {
		this.clientetmp = clientetmp;
	}

	public boolean isValidador() {
		return validador;
	}

	public void setValidador(boolean validador) {
		this.validador = validador;
	}

	public boolean isValida() {
		return valida;
	}

	public void setValida(boolean valida) {
		this.valida = valida;
	}

	public boolean isEstadeshabilitado() {
		return estadeshabilitado;
	}

	public void setEstadeshabilitado(boolean estadeshabilitado) {
		this.estadeshabilitado = estadeshabilitado;
	}

}
