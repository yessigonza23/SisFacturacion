package ec.gob.mdg.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
import ec.gob.mdg.model.Recaudacion;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.service.IContableDetalleService;
import ec.gob.mdg.service.IContableService;
import ec.gob.mdg.service.IPlanContableService;
import ec.gob.mdg.service.IPuntoRecaudacionService;
import ec.gob.mdg.service.IUsuarioPuntoService;
import ec.gob.mdg.util.UtilsDate;

@Named
@ViewScoped
public class ContableBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IContableDetalleService serviceContableDet;

	@Inject
	private IContableService serviceContable;

	@Inject
	private IUsuarioPuntoService serviceUsuPunto;

	@Inject
	private IPuntoRecaudacionService servicePuntoRecaudacion;

	@Inject
	private IPlanContableService servicePlanContable;

	private Contable contable = new Contable();
	private ContableDetalle contableDetalle = new ContableDetalle();
	private PuntoRecaudacion puntoRecaudacion;
	private Recaudacion recaudacion = new Recaudacion();
	private List<Contable> listaContable = new ArrayList<Contable>();
	private List<ContableDetalle> listaContableDetalle = new ArrayList<ContableDetalle>();
	private List<PlanContable> listaPlanContable = new ArrayList<PlanContable>();

	private UsuarioPunto usuPunto = new UsuarioPunto();
	Usuario p = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");

	private UtilsDate utilsDate;
	PuntoRecaudacion punto;
	PuntoRecaudacion nombre;
	Date fechaActual;
	Integer idcontable = 0;
	private String tipoDialog;
	double totaldeb = 0.0;
	double totalhab = 0.0;
	boolean estadeshabilitado;
	boolean estadeshabilitadoDebe=false;
	boolean estadeshabilitadoHaber=false;
	Integer num = 0;
	Date fecha_inicio;
	Date fecha_fin;

	@PostConstruct
	public void init() {
		try {
			usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(p);
			punto = usuPunto.getPuntoRecaudacion();
			nombre = servicePuntoRecaudacion.listarPorId(punto);
			fechaActual = UtilsDate.fechaActual();
			this.listarPlanContable();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// REGISTRAR CONTABLE
	@Transactional
	public void registrarContable() {
		try {
			usuPunto = serviceUsuPunto.listarUsuarioPuntoPorIdLogueado(p);
			Contable cont = new Contable();
			cont.setDetalle(contable.getDetalle());
			fechaActual = UtilsDate.fechaActual();
			cont.setFecha(fechaActual);
			cont.setPuntoRecaudacion(punto);
			cont.setUsuarioPunto(usuPunto);
			cont.setValor(contable.getValor());
			cont.setIdcierre(contable.getIdcierre());
			idcontable = serviceContable.registrar(cont);

			for (ContableDetalle det : listaContableDetalle) {

				det.setContable(cont);
				det.setDebe(contableDetalle.getDebe());
				det.setHaber(contableDetalle.getHaber());
				serviceContableDet.registrar(det);
			}
			mostrarSecuencial();
			estadeshabilitado = true;
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "se grabó con éxito", "Aviso"));

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/// REGISTRAR DETALLE TMP DE CONTABLE
	public void registrarContDetalle() {
		ContableDetalle det = new ContableDetalle();
		det = contableDetalle;
		det.setContable(contable);
		det.setDebe(contableDetalle.getDebe());
		det.setHaber(contableDetalle.getHaber());
		det.setPlancontable(contableDetalle.getPlancontable());

		listaContableDetalle.add(det);
		totalDetalle();

	}
	
	////VERIFICA SI EL VALOR DE DEBE Y HABER ESTA LLENOS PARA QUE BLOQUEEN LOS CAMPOS
	public void validaHaber() {
		if(contableDetalle.getHaber()!=0) {
			estadeshabilitadoDebe=true;
		}
	}
	
	public void validaDebe() {
		if(contableDetalle.getDebe()!=0) {
			estadeshabilitadoHaber=true;
		}
	}


	// SUMAR TOTAL DETABLE HABER Y DEBE
	public void totalDetalle() {
		totalhab = 0.00;
		totaldeb = 0.00;
		for (ContableDetalle det : listaContableDetalle) {
			totalhab = totalhab + det.getHaber();
			totaldeb = totaldeb + det.getDebe();
		}
	}

	// ENCERAR DETALLES
	public void limpiarDialogo() {
		contableDetalle = new ContableDetalle();
	}
	
	// ELIMINAR DETALLE
		public void eliminarContableDetalle(int index) {
			listaContableDetalle.remove(index);
			totalDetalle();
		}


	//// LISTAR PLAN CONTABLE
	public void listarPlanContable() {
		try {
			this.listaPlanContable = this.servicePlanContable.listar();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// MOSTRAR SECUENCIAL DE COMPROBANTE DE INGRESO(CONTABLE)
	public void mostrarSecuencial() {
		try {
			contable.getId();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/// METODO PARA LISTAR CONTABLES POR FECHAS
	public void listarContable() {
		try {
			
			this.listaContable = serviceContable.listarContablePorFechas(fecha_inicio, fecha_fin, punto.getId());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// IR A DETALLE DE CONTABLE A CONSULTAR
	public String irDetalle(String id) {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("param", id);
		return "contableconsultadetalle?faces-redirect=true";
	}

	// GETTERS Y SETTERS
	public Contable getContable() {
		return contable;
	}

	public void setContable(Contable contable) {
		this.contable = contable;
	}

	public ContableDetalle getContableDetalle() {
		return contableDetalle;
	}

	public void setContableDetalle(ContableDetalle contableDetalle) {
		this.contableDetalle = contableDetalle;
	}

	public List<ContableDetalle> getListaContableDetalle() {
		return listaContableDetalle;
	}

	public void setListaContableDetalle(List<ContableDetalle> listaContableDetalle) {
		this.listaContableDetalle = listaContableDetalle;
	}

	public PuntoRecaudacion getPuntoRecaudacion() {
		return puntoRecaudacion;
	}

	public void setPuntoRecaudacion(PuntoRecaudacion puntoRecaudacion) {
		this.puntoRecaudacion = puntoRecaudacion;
	}

	public UsuarioPunto getUsuPunto() {
		return usuPunto;
	}

	public void setUsuPunto(UsuarioPunto usuPunto) {
		this.usuPunto = usuPunto;
	}

	public Usuario getP() {
		return p;
	}

	public void setP(Usuario p) {
		this.p = p;
	}

	public UtilsDate getUtilsDate() {
		return utilsDate;
	}

	public void setUtilsDate(UtilsDate utilsDate) {
		this.utilsDate = utilsDate;
	}

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

	public Date getFechaActual() {
		return fechaActual;
	}

	public void setFechaActual(Date fechaActual) {
		this.fechaActual = fechaActual;
	}

	public Integer getIdcontable() {
		return idcontable;
	}

	public void setIdcontable(Integer idcontable) {
		this.idcontable = idcontable;
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

	public Recaudacion getRecaudacion() {
		return recaudacion;
	}

	public void setRecaudacion(Recaudacion recaudacion) {
		this.recaudacion = recaudacion;
	}

	public List<PlanContable> getListaPlanContable() {
		return listaPlanContable;
	}

	public void setListaPlanContable(List<PlanContable> listaPlanContable) {
		this.listaPlanContable = listaPlanContable;
	}

	public String getTipoDialog() {
		return tipoDialog;
	}

	public void setTipoDialog(String tipoDialog) {
		this.tipoDialog = tipoDialog;
	}

	public boolean isEstadeshabilitado() {
		return estadeshabilitado;
	}

	public void setEstadeshabilitado(boolean estadeshabilitado) {
		this.estadeshabilitado = estadeshabilitado;
	}

	public List<Contable> getListaContable() {
		return listaContable;
	}

	public void setListaContable(List<Contable> listaContable) {
		this.listaContable = listaContable;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}

	public Date getFecha_inicio() {
		return fecha_inicio;
	}

	public void setFecha_inicio(Date fecha_inicio) {
		this.fecha_inicio = fecha_inicio;
	}

	public Date getFecha_fin() {
		return fecha_fin;
	}

	public void setFecha_fin(Date fecha_fin) {
		this.fecha_fin = fecha_fin;
	}

	public boolean isEstadeshabilitadoDebe() {
		return estadeshabilitadoDebe;
	}

	public void setEstadeshabilitadoDebe(boolean estadeshabilitadoDebe) {
		this.estadeshabilitadoDebe = estadeshabilitadoDebe;
	}

	public boolean isEstadeshabilitadoHaber() {
		return estadeshabilitadoHaber;
	}

	public void setEstadeshabilitadoHaber(boolean estadeshabilitadoHaber) {
		this.estadeshabilitadoHaber = estadeshabilitadoHaber;
	}
	
	

}
