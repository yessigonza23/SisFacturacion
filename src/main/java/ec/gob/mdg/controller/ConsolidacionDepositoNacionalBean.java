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

import ec.gob.mdg.model.ComprobanteDepositos;
import ec.gob.mdg.model.ConsolidaDepositos;
import ec.gob.mdg.model.EstadoCuenta;
import ec.gob.mdg.model.PuntoRecaudacion;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.model.VistaConciliacionCompDepositoEstcCUentaDTO;
import ec.gob.mdg.service.IComprobanteDepositosService;
import ec.gob.mdg.service.IConsolidaDepositosService;
import ec.gob.mdg.service.IEstadoCuentaService;
import ec.gob.mdg.service.IPuntoRecaudacionService;
import ec.gob.mdg.service.IUsuarioPuntoService;
import ec.gob.mdg.service.IVistaConciliacionCompDepositoEstcCuentaDTOService;

@Named
@ViewScoped
public class ConsolidacionDepositoNacionalBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IUsuarioPuntoService serviceUsuPunto;

	@Inject
	private IPuntoRecaudacionService servicePuntoRecaudacion;

	@Inject
	private IComprobanteDepositosService serviceComprobanteDepositos;
	
	@Inject
	private IEstadoCuentaService serviceEstadoCuenta;
	
	@Inject
	private IConsolidaDepositosService serviceConsolidaDeposito;
	
	@Inject
	private IVistaConciliacionCompDepositoEstcCuentaDTOService vistaConciliacionCompDepositoEstcCuentaDTOService;
	
	private EstadoCuenta estadoCuenta =new EstadoCuenta();
	private ComprobanteDepositos comprobanteDepositos = new ComprobanteDepositos() ;
	private ConsolidaDepositos consolidaDepositos = new ConsolidaDepositos();
	
	private List<VistaConciliacionCompDepositoEstcCUentaDTO> listaVistaConciliacionCompDepositoEstcCUentaDTOs = new ArrayList<>();

	 
	PuntoRecaudacion punto;
	PuntoRecaudacion nombre;

	Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	private UsuarioPunto usuPunto = new UsuarioPunto();

	Integer anio;
	Integer mes;

	
	@PostConstruct
	public void init() {
		try {
			usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(us);
			punto = usuPunto.getPuntoRecaudacion();
			nombre = servicePuntoRecaudacion.listarPorId(punto);
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

		
	////LISTAR COMPROBANTE DEPOSITO
	public void listarVistaConsolidaDepositos(Integer anio, Integer mes) {	
		try {
			listaVistaConciliacionCompDepositoEstcCUentaDTOs = vistaConciliacionCompDepositoEstcCuentaDTOService.listaConsolidaDepositos(anio, mes);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

    public void actualizaEstadoComprobante(Integer id_estado,Integer id_comprobantedeposito, Integer id_consolidadepositos) {
    	System.out.println("entra a actualizar " + id_comprobantedeposito + "-"+id_estado+ "-"+id_consolidadepositos);
    	if (id_comprobantedeposito != null && id_estado != null && id_consolidadepositos != null) {
    		comprobanteDepositos=serviceComprobanteDepositos.mostrarComprobanteDepositoPorId(id_comprobantedeposito);
        	try {
        		estadoCuenta = serviceEstadoCuenta.estadoCuentaPorId(id_estado);
            	
            	estadoCuenta.setBloqueado(false);
            	estadoCuenta.setSaldo(estadoCuenta.getSaldo()+comprobanteDepositos.getValor());
    			serviceEstadoCuenta.modificar(estadoCuenta);
    			
    			comprobanteDepositos.setId_tmp(null);
    			serviceComprobanteDepositos.modificar(comprobanteDepositos);
    			
    			consolidaDepositos=serviceConsolidaDeposito.listarPorId(id_consolidadepositos);
    			
    			serviceConsolidaDeposito.eliminar(consolidaDepositos);
    			
    			FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Aviso", "Se reversa exitosamente"));
    		} catch (Exception e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
		} else {

		}
    	
    	
    	
    }
	
	
	
	////GETTERS Y SETTERS/
	

	public PuntoRecaudacion getPunto() {
		return punto;
	}

	public void setPunto(PuntoRecaudacion punto) {
		this.punto = punto;
	}

	public PuntoRecaudacion getNombre() {
		return nombre;
	}

	public void setNombre(PuntoRecaudacion nombre) {
		this.nombre = nombre;
	}

	public Usuario getUs() {
		return us;
	}

	public void setUs(Usuario us) {
		this.us = us;
	}

	public UsuarioPunto getUsuPunto() {
		return usuPunto;
	}

	public void setUsuPunto(UsuarioPunto usuPunto) {
		this.usuPunto = usuPunto;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	

	public EstadoCuenta getEstadoCuenta() {
		return estadoCuenta;
	}

	public void setEstadoCuenta(EstadoCuenta estadoCuenta) {
		this.estadoCuenta = estadoCuenta;
	}


	public ComprobanteDepositos getComprobanteDepositos() {
		return comprobanteDepositos;
	}


	public void setComprobanteDepositos(ComprobanteDepositos comprobanteDepositos) {
		this.comprobanteDepositos = comprobanteDepositos;
	}


	public ConsolidaDepositos getConsolidaDepositos() {
		return consolidaDepositos;
	}


	public void setConsolidaDepositos(ConsolidaDepositos consolidaDepositos) {
		this.consolidaDepositos = consolidaDepositos;
	}


	public List<VistaConciliacionCompDepositoEstcCUentaDTO> getListaVistaConciliacionCompDepositoEstcCUentaDTOs() {
		return listaVistaConciliacionCompDepositoEstcCUentaDTOs;
	}


	public void setListaVistaConciliacionCompDepositoEstcCUentaDTOs(
			List<VistaConciliacionCompDepositoEstcCUentaDTO> listaVistaConciliacionCompDepositoEstcCUentaDTOs) {
		this.listaVistaConciliacionCompDepositoEstcCUentaDTOs = listaVistaConciliacionCompDepositoEstcCUentaDTOs;
	}

	

}
