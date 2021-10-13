package ec.gob.mdg.model;

import java.io.Serializable;

import javax.persistence.Column;

public class VistaCierreDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "puntoRecaudacion")
	private String puntoRecaudacion;
	
	@Column(name = "id_cierre")
	private Integer id_cierre;
	
	@Column(name = "numeroFactura")
	private Integer numeroFactura;
	
	@Column(name = "fechaemision")
	private String fechaemision;
	
	@Column(name = "valor")
	private Double valor;
	
	@Column(name = "estado")
	private String estado;
	
	@Column(name = "id_puntorecaudacion")
	private Integer id_puntorecaudacion;

	public String getPuntoRecaudacion() {
		return puntoRecaudacion;
	}

	public void setPuntoRecaudacion(String puntoRecaudacion) {
		this.puntoRecaudacion = puntoRecaudacion;
	}

	public Integer getId_cierre() {
		return id_cierre;
	}

	public void setId_cierre(Integer id_cierre) {
		this.id_cierre = id_cierre;
	}

	public Integer getNumeroFactura() {
		return numeroFactura;
	}

	public void setNumeroFactura(Integer numeroFactura) {
		this.numeroFactura = numeroFactura;
	}


	public String getFechaemision() {
		return fechaemision;
	}

	public void setFechaemision(String fechaemision) {
		this.fechaemision = fechaemision;
	}

	public Integer getId_puntorecaudacion() {
		return id_puntorecaudacion;
	}

	public void setId_puntorecaudacion(Integer id_puntorecaudacion) {
		this.id_puntorecaudacion = id_puntorecaudacion;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	
}
