package ec.gob.mdg.model;

import java.io.Serializable;

import javax.persistence.Column;

public class VistaConciliacionValNoFacturadosDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "numtransaccion")
	private String numtransaccion;
	
	@Column(name = "fecha")
	private String fecha;
	
	@Column(name = "valor", nullable = false)
	private double valor;
	
	@Column(name = "respuesta")
	private String respuesta;
	
	@Column(name = "cliente")
	private String cliente;
		
	@Column(name = "provincia")
	private String provincia;
	
	@Column(name = "servicio")
	private String servicio;
	
	@Column(name = "ruc")
	private String ruc;
	
	@Column(name = "anio")
	private Integer anio;

	@Column(name = "mes")
	private Integer mes;
	
	

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

	

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	
	
}
