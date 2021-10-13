package ec.gob.mdg.control.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

public class Fin_Guias_DTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "cod_entidad")
	private Integer cod_entidad;
	
	@Column(name = "cod_guia")
	private Integer cod_guia;
	
	@Column(name = "nom_entidad")
	private String nom_entidad;
	
	@Column(name = "ruc")
	private String ruc;
	
	@Column(name = "num_solicitud")
	private String num_solicitud;
	
	@Column(name = "valor_pago")
	private Double valor_pago;
	
	@Column(name = "num_recibo")
	private String num_recibo;
	
	@Column(name = "fecha_elaboracion")
	private Date fecha_elaboracion;
	
	@Column(name = "jefatura_origen")
	private Integer jefatura_origen;
	
	@Column(name = "codigo_banco")
	private String codigo_banco;
	
	@Column(name = "guia_x_permiso")
	private String guia_x_permiso;
		
	@Column(name = "punto")
	private Integer punto;	
	
	@Column(name = "aux")
	private boolean aux= true;

	@Column(name = "cantidad")
	private Integer cantidad;
	
	@Column(name = "recaudacion")
	private String recaudacion;
	
	public String getRecaudacion() {
		return recaudacion;
	}

	public void setRecaudacion(String recaudacion) {
		this.recaudacion = recaudacion;
	}

	public Integer getCod_entidad() {
		return cod_entidad;
	}

	public void setCod_entidad(Integer cod_entidad) {
		this.cod_entidad = cod_entidad;
	}

	public Integer getCod_guia() {
		return cod_guia;
	}

	public void setCod_guia(Integer cod_guia) {
		this.cod_guia = cod_guia;
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

	public String getNum_recibo() {
		return num_recibo;
	}

	public void setNum_recibo(String num_recibo) {
		this.num_recibo = num_recibo;
	}

	public Date getFecha_elaboracion() {
		return fecha_elaboracion;
	}

	public void setFecha_elaboracion(Date fecha_elaboracion) {
		this.fecha_elaboracion = fecha_elaboracion;
	}

	public Integer getJefatura_origen() {
		return jefatura_origen;
	}

	public void setJefatura_origen(Integer jefatura_origen) {
		this.jefatura_origen = jefatura_origen;
	}

	public String getCodigo_banco() {
		return codigo_banco;
	}

	public void setCodigo_banco(String codigo_banco) {
		this.codigo_banco = codigo_banco;
	}

	public String getGuia_x_permiso() {
		return guia_x_permiso;
	}

	public void setGuia_x_permiso(String guia_x_permiso) {
		this.guia_x_permiso = guia_x_permiso;
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

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	
}
