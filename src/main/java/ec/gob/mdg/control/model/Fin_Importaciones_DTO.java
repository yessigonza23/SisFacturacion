package ec.gob.mdg.control.model;

import java.io.Serializable;

import javax.persistence.Column;

public class Fin_Importaciones_DTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "cod_entidad")
	private Integer cod_entidad;

	@Column(name = "nom_entidad")
	private String nom_entidad;

	@Column(name = "ruc")
	private String ruc;

	@Column(name = "num_solicitud")
	private String num_solicitud;

	@Column(name = "valor_pago")
	private Double valor_pago;

	@Column(name = "fob")
	private Double fob;
	
	@Column(name = "aux")
	private boolean aux= true;
	
	@Column(name = "tipo")
	private String tipo;
	
	@Column(name = "recaudacion")
	private String recaudacion;
	
	@Column(name = "cantidad")
	private Integer cantidad;
	
	@Column(name = "punto")
	private Integer punto;
	//GETS AND SETS

	public Integer getCod_entidad() {
		return cod_entidad;
	}

	public void setCod_entidad(Integer cod_entidad) {
		this.cod_entidad = cod_entidad;
	}

	public String getNom_entidad() {
		return nom_entidad;
	}

	public void setNom_entidad(String nom_entidad) {
		this.nom_entidad = nom_entidad;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getNum_solicitud() {
		return num_solicitud;
	}

	public void setNum_solicitud(String num_solicitud) {
		this.num_solicitud = num_solicitud;
	}

	public Double getValor_pago() {
		return valor_pago;
	}

	public void setValor_pago(Double valor_pago) {
		this.valor_pago = valor_pago;
	}

	public Double getFob() {
		return fob;
	}

	public void setFob(Double fob) {
		this.fob = fob;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public boolean isAux() {
		return aux;
	}

	public void setAux(boolean aux) {
		this.aux = aux;
	}

	public String getRecaudacion() {
		return recaudacion;
	}

	public void setRecaudacion(String recaudacion) {
		this.recaudacion = recaudacion;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Integer getPunto() {
		return punto;
	}

	public void setPunto(Integer punto) {
		this.punto = punto;
	}
	
	
	
}
