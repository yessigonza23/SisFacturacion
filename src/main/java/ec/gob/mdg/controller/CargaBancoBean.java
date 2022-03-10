package ec.gob.mdg.controller;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
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

import org.primefaces.model.UploadedFile;

import ec.gob.mdg.dao.IEstadoCuentaDAO;
import ec.gob.mdg.model.EstadoCuenta;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.util.UtilsArchivos;
import ec.gob.mdg.validaciones.Funciones;

@Named
@ViewScoped
public class CargaBancoBean implements Serializable {

	private static final long serialVersionUID = -6490646968982098807L;

	@Inject
	private IEstadoCuentaDAO service;
	
	@Inject
	private Funciones fun;

	EstadoCuenta estado = new EstadoCuenta();
	List<EstadoCuenta> estadoscuenta = new ArrayList<EstadoCuenta>();

	Usuario p = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	
	Integer anioext;
	Integer mesext;
	Date fechaext;
	Date fechamax;
	boolean verifica = false;
	Integer id_usuario;
	String tipoTransaccion;

	boolean estado1 = false;
	boolean estado2 = true;
	boolean estado3 = true;
	String path;
	Integer contador = 0;
	private UploadedFile file;

	@PostConstruct
	public void init() {
		try {
			estado1 = false;
			estado2 = true;
			estado3 = true;
			id_usuario = p.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void upload() {
		String nombreArchivo = null;
		try {
			if (file != null && tipoTransaccion!=null) {
				nombreArchivo = "EstadoCuenta.csv";
				UtilsArchivos.upload(file, nombreArchivo);
				estado1 = true;
				estado2 = false;
			} else {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia", "Seleccione un archivo y tipo de transacción"));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	@Transactional
	public void importarCSV() {
		List<EstadoCuenta> estadoscuenta = new ArrayList<EstadoCuenta>();
		verificaDatos();
		if (verifica) {
			path = UtilsArchivos.getRutaCargaMasiva() + "SEstadoCuenta.csv";
			String line = "";
			String cvsSplitBy = ";";

			try (BufferedReader br = new BufferedReader(new FileReader(path))) {
				contador = 1;
				while ((line = br.readLine()) != null) {

					// use comma as separator
					String[] leerEstados = line.split(cvsSplitBy);
					String respuesta = leerEstados[0];
					double valor = Double.parseDouble(leerEstados[1].replace(",", "."));
					String cliente = leerEstados[2];
					String fecha_tmp = leerEstados[3];
					java.text.DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
					Date fecha = (Date) formatter.parse(fecha_tmp);
					String provincia = leerEstados[4];
					String servicio = leerEstados[5];
					String codigoservicio = leerEstados[6];
					String numtransaccion = leerEstados[7];
					String ruc = leerEstados[8];
					boolean bloqueado = false;

					estadoscuenta.add(new EstadoCuenta(anioext, mesext, numtransaccion, valor, servicio, respuesta,
							cliente, fecha, provincia, codigoservicio, bloqueado, fecha_tmp, fechaext, ruc,valor,tipoTransaccion));
					contador++;
				} ////// TERMINA WHILE
				service.registrarLista(estadoscuenta);
				fechaMaxEstado();
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_INFO, "Se cargaron los datos", "Exitoso"));
				cargaLista();
			} catch (Exception e) {
				// TODO: handle exception
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
						"No se cargaron los datos revise su archivo en la línea: " + contador + "\r\n Error:" + e,
						"Fallido"));
				// e.printStackTrace();
			} //////////// TERMINA CATHC

		} // TERMINA IF

	}

	public void verificaDatos() {
		this.estadoscuenta = this.service.listarEstadoCuentaCargado(anioext, mesext, fechaext);
		if (estadoscuenta.size() != 0) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
					"Estos datos ya existen en la base de datos, no puede realizar la carga", "Fallido"));
		} else {
			verifica = true;
		}
	}
	
	public void fechaMaxEstado() {
		fechamax = fun.fechaMaxEstadoCuenta();
	}	
	
	public void consolida(Integer anio,Integer id_usuario) {
		try {		
			
			if (anio != null && id_usuario != null) {
				if (tipoTransaccion.equals("T")) {
					System.out.println("entra a Transferencias");
					BigInteger contadorFaltantes =   (BigInteger) fun.cuentaEstCuentaConsolidacionTransfer(anio);
					do{
						fun.consolidaDepositosTransfer(anio,id_usuario);
						contadorFaltantes =   (BigInteger) fun.cuentaEstCuentaConsolidacion(anio);
						
			        }while (contadorFaltantes.intValueExact()>0) ;
				}else {
					System.out.println("Diferente de transferencias");
					BigInteger contadorFaltantes =   (BigInteger) fun.cuentaEstCuentaConsolidacion(anio);
					do{
						fun.consolidaDepositos(anio,id_usuario);
						contadorFaltantes =   (BigInteger) fun.cuentaEstCuentaConsolidacion(anio);
						
			        }while (contadorFaltantes.intValueExact()>0) ;
				}
			}
						
			System.out.println("Termina consolidacion");
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_INFO, "Se consolidaron los datos", "Exitoso"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public void cargaLista() {
		this.estadoscuenta = this.service.listarEstadoCuentaCargado(anioext, mesext, fechaext);
	}

	public void cambiaFalseEstado3() {

		estado3 = false;
	}

	public void cambiaTrueeEstado3() {
		estado3 = true;
	}

	// getters y setters
	public EstadoCuenta getEstado() {
		return estado;
	}

	public void setEstado(EstadoCuenta estado) {
		this.estado = estado;
	}

	public List<EstadoCuenta> getEstadoscuenta() {
		return estadoscuenta;
	}

	public void setEstadoscuenta(List<EstadoCuenta> estadoscuenta) {
		this.estadoscuenta = estadoscuenta;
	}

	public Integer getAnioext() {
		return anioext;
	}

	public void setAnioext(Integer anioext) {
		this.anioext = anioext;
	}

	public Integer getMesext() {
		return mesext;
	}

	public void setMesext(Integer mesext) {
		this.mesext = mesext;
	}

	public Date getFechaext() {
		return fechaext;
	}

	public void setFechaext(Date fechaext) {
		this.fechaext = fechaext;
	}

	public boolean isVerifica() {
		return verifica;
	}

	public void setVerifica(boolean verifica) {
		this.verifica = verifica;
	}

	public UploadedFile getFile() {
		return file;
	}

	public void setFile(UploadedFile file) {
		this.file = file;
	}

	public boolean isEstado1() {
		return estado1;
	}

	public void setEstado1(boolean estado1) {
		this.estado1 = estado1;
	}

	public boolean isEstado2() {
		return estado2;
	}

	public void setEstado2(boolean estado2) {
		this.estado2 = estado2;
	}

	public boolean isEstado3() {
		return estado3;
	}

	public void setEstado3(boolean estado3) {
		this.estado3 = estado3;
	}

	
	public Date getFechamax() {
		fechamax = fun.fechaMaxEstadoCuenta();
		return fechamax;
	}

	public void setFechamax(Date fechamax) {
		this.fechamax = fechamax;
	}

	public Integer getContador() {
		return contador;
	}

	public void setContador(Integer contador) {
		this.contador = contador;
	}

	public Usuario getP() {
		return p;
	}

	public void setP(Usuario p) {
		this.p = p;
	}

	public Integer getId_usuario() {
		return id_usuario;
	}

	public void setId_usuario(Integer id_usuario) {
		this.id_usuario = id_usuario;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTipoTransaccion() {
		return tipoTransaccion;
	}

	public void setTipoTransaccion(String tipoTransaccion) {
		this.tipoTransaccion = tipoTransaccion;
	}
	

}
