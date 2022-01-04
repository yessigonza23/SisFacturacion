package ec.gob.mdg.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comprobantedeposito", schema = "financiero")
public class ComprobanteDepositos implements Serializable {

	private static final long serialVersionUID = -3583897044099392475L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "id_comprobante")
	private Comprobante comprobante;
	
	@Column(name = "valor")
	private double valor;
	
	@Column(name = "num_deposito", length = 10)
	private String num_deposito;
	
	@Column(name = "fecha")
	private Date fecha;
	
	@Column(name = "observacion", length = 100)
	private String observacion;
	
	@Column(name = "usuariomod",  length = 10)
	private String usuariomod;
	
	@Column(name = "fechamod")
	private Date fechamod;
	
	@Column(name = "id_tmp")
	private Integer id_tmp;
	
	@Column(name = "tipotransaccion")
	private String tipotransaccion;
	
	@Column(name = "origen")
	private String origen;
	
	@Column(name = "identificacion")
	private String identificacion;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Comprobante getComprobante() {
		return comprobante;
	}

	public void setComprobante(Comprobante comprobante) {
		this.comprobante = comprobante;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getNum_deposito() {
		return num_deposito;
	}

	public void setNum_deposito(String num_deposito) {
		this.num_deposito = num_deposito;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public String getUsuariomod() {
		return usuariomod;
	}

	public void setUsuariomod(String usuariomod) {
		this.usuariomod = usuariomod;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFechamod() {
		return fechamod;
	}

	public void setFechamod(Date fechamod) {
		this.fechamod = fechamod;
	}

	

	public Integer getId_tmp() {
		return id_tmp;
	}

	public void setId_tmp(Integer id_tmp) {
		this.id_tmp = id_tmp;
	}

	public String getTipotransaccion() {
		return tipotransaccion;
	}

	public void setTipotransaccion(String tipotransaccion) {
		this.tipotransaccion = tipotransaccion;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}


	
	
	
}
