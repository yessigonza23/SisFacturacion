package ec.gob.mdg.control.model;

import java.io.Serializable;

import javax.persistence.Column;

public class Fin_Cal_Ren_DTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "codigo")
	private Integer codigo;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "ruc")
	private String ruc;
	
	@Column(name = "codigo_renovacion")
	private Integer codigo_renovacion;
	
	@Column(name = "tipo")
	private String tipo;
	
	@Column(name = "actividades")
	private String actividades;
	
	@Column(name = "codigo_recaudacion")
	private String codigo_recaudacion;
	
	@Column(name = "cantidad")
	private Integer cantidad;

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getCodigo_renovacion() {
		return codigo_renovacion;
	}

	public void setCodigo_renovacion(Integer codigo_renovacion) {
		this.codigo_renovacion = codigo_renovacion;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getActividades() {
		return actividades;
	}

	public void setActividades(String actividades) {
		this.actividades = actividades;
	}

	public String getCodigo_recaudacion() {
		return codigo_recaudacion;
	}

	public void setCodigo_recaudacion(String codigo_recaudacion) {
		this.codigo_recaudacion = codigo_recaudacion;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	
	
	
}
