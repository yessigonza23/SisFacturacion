package ec.gob.mdg.control.model;

import java.io.Serializable;

import javax.persistence.Column;

public class Sanciones_Admin_DTO implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "codigo_empresa")
	private String codigo_empresa;
	
	@Column(name = "ruc")
	private String ruc;
	
	@Column(name = "nombre")
	private String nombre;	
	
	@Column(name = "numero_juicio")
	private String numero_juicio;	
	
	@Column(name = "anio")
	private String anio;

	
	@Column(name = "valor")
	private Double valor;
	
	@Column(name = "tipo")
	private String tipo;
	
	
	@Column(name = "codigo_recaudacion")
	private String codigo_recaudacion;
	
	@Column(name = "codigo")
	private Integer codigo;
	
	@Column(name = "cantidad")
	private Integer cantidad;
	
	@Column(name = "aux")
	private boolean aux= true;
	
	@Column(name = "punto")
	private Integer punto;

	public String getCodigo_empresa() {
		return codigo_empresa;
	}

	public void setCodigo_empresa(String codigo_empresa) {
		this.codigo_empresa = codigo_empresa;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNumero_juicio() {
		return numero_juicio;
	}

	public void setNumero_juicio(String numero_juicio) {
		this.numero_juicio = numero_juicio;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	
	public String getCodigo_recaudacion() {
		return codigo_recaudacion;
	}

	public void setCodigo_recaudacion(String codigo_recaudacion) {
		this.codigo_recaudacion = codigo_recaudacion;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public boolean isAux() {
		return aux;
	}

	public void setAux(boolean aux) {
		this.aux = aux;
	}

	public Integer getPunto() {
		return punto;
	}

	public void setPunto(Integer punto) {
		this.punto = punto;
	}
	

	
}
