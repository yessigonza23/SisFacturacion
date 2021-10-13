package ec.gob.mdg.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "estadocuenta", schema = "financiero")
public class EstadoCuenta implements Serializable{

	private static final long serialVersionUID = -2682493675076097387L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "anio")
	private Integer anio;

	@Column(name = "mes")
	private Integer mes;
	
	@Column(name = "numtransaccion")
	private String numtransaccion;
	
	@Column(name = "valor", nullable = false)
	private double valor;
	
	@Column(name = "servicio")
	private String servicio;
	
	@Column(name = "respuesta")
	private String respuesta;
	
	@Column(name = "cliente")
	private String cliente;
	
	@Column(name = "fecha")
	private Date fecha;
	
	@Column(name = "provincia")
	private String provincia;
	
	@Column(name = "codigoservicio")
	private String codigoservicio;
	
	@Column(name = "bloqueado")
	private boolean bloqueado;
	
	@Column(name = "fechahora")
	private String fechahora;
	
	@Column(name = "fechacarga")
	private Date fechacarga;
	
	@Column(name = "ruc")
	private String ruc;
	
	@Column(name = "usuario")
	private String usuario;
	
	@Column(name = "fechamodificacion")
	private Date fechamodificacion;
	
	@Column(name = "observacion")
	private String observacion;
	
	@Column(name = "id_punto")
	private Integer id_punto;
	
	@Column(name = "id_comprobante")
	private Integer id_comprobante;
	
	@Column(name = "comp_valor")
	private Double comp_valor;
	
	@Column(name = "saldo")
	private Double saldo;
	
	@Column(name = "tipotransaccion")
	private String tipotransaccion;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public String getNumtransaccion() {
		return numtransaccion;
	}

	public void setNumtransaccion(String numtransaccion) {
		this.numtransaccion = numtransaccion;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getServicio() {
		return servicio;
	}

	public void setServicio(String servicio) {
		this.servicio = servicio;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getRespuesta() {
		return respuesta;
	}

	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getCodigoservicio() {
		return codigoservicio;
	}

	public void setCodigoservicio(String codigoservicio) {
		this.codigoservicio = codigoservicio;
	}

	public boolean isBloqueado() {
		return bloqueado;
	}

	public void setBloqueado(boolean bloqueado) {
		this.bloqueado = bloqueado;
	}

	public String getFechahora() {
		return fechahora;
	}

	public void setFechahora(String fechahora) {
		this.fechahora = fechahora;
	}

	public Date getFechacarga() {
		return fechacarga;
	}

	public void setFechacarga(Date fechacarga) {
		this.fechacarga = fechacarga;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Date getFechamodificacion() {
		return fechamodificacion;
	}

	public void setFechamodificacion(Date fechamodificacion) {
		this.fechamodificacion = fechamodificacion;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
	

	public Integer getId_punto() {
		return id_punto;
	}

	public void setId_punto(Integer id_punto) {
		this.id_punto = id_punto;
	}

	public Integer getId_comprobante() {
		return id_comprobante;
	}

	public void setId_comprobante(Integer id_comprobante) {
		this.id_comprobante = id_comprobante;
	}

	public Double getComp_valor() {
		return comp_valor;
	}

	public void setComp_valor(Double comp_valor) {
		this.comp_valor = comp_valor;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	public String getTipotransaccion() {
		return tipotransaccion;
	}

	public void setTipotransaccion(String tipotransaccion) {
		this.tipotransaccion = tipotransaccion;
	}

	public EstadoCuenta( Integer anio, Integer mes, String numtransaccion, double valor, String servicio,
			String respuesta, String cliente, Date fecha, String provincia, String codigoservicio, boolean bloqueado,
			String fechahora, Date fechacarga, String ruc,Double saldo, String tipotransaccion) {
		super();
		//this.id = id;
		this.anio = anio;
		this.mes = mes;
		this.numtransaccion = numtransaccion;
		this.valor = valor;
		this.servicio = servicio;
		this.respuesta = respuesta;
		this.cliente = cliente;
		this.fecha = fecha;
		this.provincia = provincia;
		this.codigoservicio = codigoservicio;
		this.bloqueado = bloqueado;
		this.fechahora = fechahora;
		this.fechacarga = fechacarga;
		this.ruc = ruc;
		this.saldo = saldo;
		this.tipotransaccion=tipotransaccion;
	}

	public EstadoCuenta() {
		
		// TODO Auto-generated constructor stub
	}	
	
}
