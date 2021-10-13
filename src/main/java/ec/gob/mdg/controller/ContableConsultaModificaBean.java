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

import ec.gob.mdg.model.Contable;
import ec.gob.mdg.model.ContableDetalle;
import ec.gob.mdg.model.PlanContable;
import ec.gob.mdg.model.PuntoRecaudacion;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.service.IContableDetalleService;
import ec.gob.mdg.service.IContableService;
import ec.gob.mdg.service.IPlanContableService;
import ec.gob.mdg.service.IPuntoRecaudacionService;
import ec.gob.mdg.service.IUsuarioPuntoService;

@Named
@ViewScoped
public class ContableConsultaModificaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IContableService serviceContable;

	@Inject
	private IContableDetalleService serviceContableDetable;

	@Inject
	private IUsuarioPuntoService serviceUsuPunto;

	@Inject
	private IPuntoRecaudacionService servicePuntoRecaudacion;

	@Inject
	private IPlanContableService servicePlanContable;

	PuntoRecaudacion punto;
	PuntoRecaudacion nombre;
	private Contable contable;
	private ContableDetalle contableDetalle;
	private List<PlanContable> listaPlanContable;
	private List<ContableDetalle> listaContableDetalle;

	Integer id = 0;
	String param2;
	boolean estadeshabilitado;
	double totaldeb = 0.0;
	double totalhab = 0.0;
	private String tipoDialog;
	Integer id_contable=0 ;
	Usuario us = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	private UsuarioPunto usuPunto = new UsuarioPunto();

	@PostConstruct
	public void init() {
		try {
			usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(us);
			punto = usuPunto.getPuntoRecaudacion();
			nombre = servicePuntoRecaudacion.listarPorId(punto);
			listarDetalleContable();
			this.listarPlanContable();
			this.tipoDialog = "Nuevo";
			totalDetalle();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public String getParam() {
		return (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
	}

	// Listar informacion del contable
	public void listarDetalleContable() {
		param2 = (String) FacesContext.getCurrentInstance().getExternalContext().getFlash().get("param");
		id = Integer.parseInt(param2);
		this.contable = this.serviceContable.mostrarContablePorId(id);
		this.listaContableDetalle = this.serviceContableDetable.listarContableDetallePorId(id);
		totalDetalle();
	}

	// SUMAR TOTAL DETABLE HABER
	public void totalDetalle() {
		totalhab = 0.00;
		totaldeb = 0.00;

		for (ContableDetalle det : listaContableDetalle) {
			totalhab = totalhab + det.getHaber();
			totaldeb = totaldeb + det.getDebe();
		}
	}

////LISTAR PLAN CONTABLE
	public void listarPlanContable() {
		try {
			this.listaPlanContable = this.servicePlanContable.listar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Transactional
	public void modificarContable() {

		try {
			totalDetalle();
			if (totaldeb == totalhab) {
				
				contable.setValor(totaldeb);
				serviceContable.modificar(contable);
				
				for (ContableDetalle c : listaContableDetalle) {
					if(c.getId()==null) {
						c.setContable(contable);
						id_contable = serviceContableDetable.registrar(c);
					}else {
						serviceContableDetable.modificar(c);
					}	
				}
				estadeshabilitado = true;
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "se grabó con éxito", "Aviso"));
			} else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error - Diferencia de valores en debe y haber", "Error"));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/// MOSTRAR DETALLE DE CONTABLE
	public void mostrarDataDetalle(ContableDetalle i) {
		this.contableDetalle = i;
		this.tipoDialog = "Modificar";
	}

	public void operar(String accion) {
		try {
			if (accion.equalsIgnoreCase("R")) {
				listaContableDetalle.add(contableDetalle);
				totalDetalle();
			} else if (accion.equalsIgnoreCase("M")) {	
				totalDetalle();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	//// REGISTRAR DETALLE TEMPORAL
	public void registrarContDetalle() {
		listaContableDetalle.add(contableDetalle);
		totalDetalle();
	}

	public void limpiarControles() {
		this.contableDetalle = new ContableDetalle();
		this.tipoDialog = "Nuevo";

	}

	////// GETTERS Y SETTERS

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

	public double getTotaldeb() {
		return totaldeb;
	}

	public void setTotaldeb(double totaldeb) {
		this.totaldeb = totaldeb;
	}

	public double getTotalhab() {
		return totalhab;
	}

	public void setTotalhab(double totalhab) {
		this.totalhab = totalhab;
	}

	public Contable getContable() {
		return contable;
	}

	public void setContable(Contable contable) {
		this.contable = contable;
	}

	public List<ContableDetalle> getListaContableDetalle() {
		return listaContableDetalle;
	}

	public void setListaContableDetalle(List<ContableDetalle> listaContableDetalle) {
		this.listaContableDetalle = listaContableDetalle;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getParam2() {
		return param2;
	}

	public void setParam2(String param2) {
		this.param2 = param2;
	}

	public boolean isEstadeshabilitado() {
		return estadeshabilitado;
	}

	public void setEstadeshabilitado(boolean estadeshabilitado) {
		this.estadeshabilitado = estadeshabilitado;
	}

	public ContableDetalle getContableDetalle() {
		return contableDetalle;
	}

	public void setContableDetalle(ContableDetalle contableDetalle) {
		this.contableDetalle = contableDetalle;
	}

	public List<PlanContable> getListaPlanContable() {
		return listaPlanContable;
	}

	public void setListaPlanContable(List<PlanContable> listaPlanContable) {
		this.listaPlanContable = listaPlanContable;
	}

	public Integer getId_contable() {
		return id_contable;
	}

	public void setId_contable(Integer id_contable) {
		this.id_contable = id_contable;
	}

	public String getTipoDialog() {
		return tipoDialog;
	}

	public void setTipoDialog(String tipoDialog) {
		this.tipoDialog = tipoDialog;
	}

}
