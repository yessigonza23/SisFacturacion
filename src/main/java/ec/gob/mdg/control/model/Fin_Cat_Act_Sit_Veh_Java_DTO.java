package ec.gob.mdg.control.model;

import java.io.Serializable;

import javax.persistence.Column;

public class Fin_Cat_Act_Sit_Veh_Java_DTO implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "codigo")
	private Integer codigo;
	
	@Column(name = "nombre")
	private String nombre;
	
	@Column(name = "ruc")
	private String ruc;
	
	@Column(name = "codigo_renovacion")
	private Integer codigo_renovacion;

	@Column(name = "categoria_actualkg")
	private Double categoria_actualkg;
	
	@Column(name = "categoria_nuevakg")
	private Double categoria_nuevakg;
	
	@Column(name = "categoria_actual")
	private Integer categoria_actual;
	
	@Column(name = "categoria_nueva")
	private Integer categoria_nueva;
	
	@Column(name = "tipo")
	private String tipo;
	
	@Column(name = "actividades")
	private String actividades;
	
	@Column(name = "codigo_recaudacion")
	private String codigo_recaudacion;
	
	@Column(name = "cantidad")
	private Integer cantidad;
	
	@Column(name = "valor")
	private Double valor;

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

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public Integer getCodigo_renovacion() {
		return codigo_renovacion;
	}

	public void setCodigo_renovacion(Integer codigo_renovacion) {
		this.codigo_renovacion = codigo_renovacion;
	}

	
	public Double getCategoria_actualkg() {
		return categoria_actualkg;
	}

	public void setCategoria_actualkg(Double categoria_actualkg) {
		this.categoria_actualkg = categoria_actualkg;
	}

	public Double getCategoria_nuevakg() {
		return categoria_nuevakg;
	}

	public void setCategoria_nuevakg(Double categoria_nuevakg) {
		this.categoria_nuevakg = categoria_nuevakg;
	}

	public Integer getCategoria_actual() {
		return categoria_actual;
	}

	public void setCategoria_actual(Integer categoria_actual) {
		this.categoria_actual = categoria_actual;
	}

	public Integer getCategoria_nueva() {
		return categoria_nueva;
	}

	public void setCategoria_nueva(Integer categoria_nueva) {
		this.categoria_nueva = categoria_nueva;
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

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	
}
