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

@Named
@ViewScoped
public class ClienteBean implements Serializable {

	private static final long serialVersionUID = -2830034019923061747L;

	@Inject
	private IClienteService service;

	private List<Cliente> lista;
	private String tipoDialog;
	private Cliente cliente;
	private Cliente clientetmp ;
	boolean validador;

	@PostConstruct
	public void init() {
		this.cliente = new Cliente();
		this.listar();
		this.tipoDialog = "Nuevo";
	}

	public void listar() {
		try {
			this.lista = this.service.listar();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional
	public void modificar(Cliente cliente) {
		validaIdentificacion(cliente.getCi(),cliente.getTipoid());
		
		if(validador == false) {
		
		cliente.setTipoid(cliente.getTipoid());
		cliente.setCi(cliente.getCi());
		cliente.setDireccion(cliente.getDireccion().toUpperCase());
		cliente.setNombre(cliente.getNombre().toUpperCase());
		cliente.setTelefono(cliente.getTelefono());
		cliente.setCorreo(cliente.getCorreo());
		try {
			this.service.modificar(cliente);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}else {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Tipo de identificación erronea", "No puede modificar el registro"));
		}
	}
	
	// VALIDADOR DE CEDULA-RUC
		public void validaIdentificacion(String ci,String tipoid) {
			
			String validaIdentificacion = CedulaRuc.comprobacion(ci, tipoid);
			if (validaIdentificacion.equals("T")) {
				validador = false;
			} else {
				validador = true;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Tipo de identificacion erronea", validaIdentificacion));
			}
			
		}
	
	///VERIFICA SI EXISTE EL CLIENTE
		public void verificarExistencia(String ci) {
			
			this.clientetmp = service.ClientePorCiParametro(ci);	
			
			if(clientetmp.getCi()!=null) {
				validador=true;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Este cliente ya existe", "Error"));
			}
			
		}
	
	
	////GETTERS Y SETTERS

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

}
