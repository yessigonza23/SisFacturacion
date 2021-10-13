package ec.gob.mdg.control.model;

import java.io.Serializable;

import javax.persistence.Column;

public class Fin_Ent_Actividades implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "nom_entidad")
	private String nom_entidad;
	
	@Column(name = "cod_entidad")
	private Integer cod_entidad;
	
	@Column(name = "cod_calrev_codigo")
	private Integer cod_calrev_codigo;
	
	@Column(name = "ruc")
	private String ruc;
	
	@Column(name = "actividades_calificacion")
	private String actividades_calificacion;
	
	@Column(name = "cod_financiero")
	private String cod_financiero;
	
	@Column(name = "factura_int")
	private String factura_int;
	
	@Column(name = "maneja_sustancias")
	private String maneja_sustancias;

	
	
	
	

	public Fin_Ent_Actividades() {
		
		// TODO Auto-generated constructor stub
	}

	public String getNom_entidad() {
		return nom_entidad;
	}

	public void setNom_entidad(String nom_entidad) {
		this.nom_entidad = nom_entidad;
	}

	public Integer getCod_entidad() {
		return cod_entidad;
	}

	public void setCod_entidad(Integer cod_entidad) {
		this.cod_entidad = cod_entidad;
	}

	public Integer getCod_calrev_codigo() {
		return cod_calrev_codigo;
	}

	public void setCod_calrev_codigo(Integer cod_calrev_codigo) {
		this.cod_calrev_codigo = cod_calrev_codigo;
	}

	public String getRuc() {
		return ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}

	public String getActividades_calificacion() {
		return actividades_calificacion;
	}

	public void setActividades_calificacion(String actividades_calificacion) {
		this.actividades_calificacion = actividades_calificacion;
	}

	public String getCod_financiero() {
		return cod_financiero;
	}

	public void setCod_financiero(String cod_financiero) {
		this.cod_financiero = cod_financiero;
	}

	public String getFactura_int() {
		return factura_int;
	}

	public void setFactura_int(String factura_int) {
		this.factura_int = factura_int;
	}

	public String getManeja_sustancias() {
		return maneja_sustancias;
	}

	public void setManeja_sustancias(String maneja_sustancias) {
		this.maneja_sustancias = maneja_sustancias;
	}

	
	
}
