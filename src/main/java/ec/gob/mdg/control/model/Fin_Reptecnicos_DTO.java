package ec.gob.mdg.control.model;

import java.io.Serializable;

import javax.persistence.Column;

public class Fin_Reptecnicos_DTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "idtec")
	private Integer idtec;
	
	@Column(name = "cedulatec")
	private String cedulatec;
	
	@Column(name = "nombrestec")
	private String nombrestec;
	
	@Column(name = "codigo_calren")
	private Integer codigo_calren;
	
	@Column(name = "recaudacion")
	private String recaudacion;
	
	@Column(name = "cantidad")
	private Integer cantidad;
	
	@Column(name = "aux")
	private boolean aux= true;
	
	@Column(name = "tipo")
	private String tipo;

	@Column(name = "punto")
	private Integer punto;
	
	//GETS AND SETS

	public Integer getIdtec() {
		return idtec;
	}

	public void setIdtec(Integer idtec) {
		this.idtec = idtec;
	}


	public String getCedulatec() {
		return cedulatec;
	}

	public void setCedulatec(String cedulatec) {
		this.cedulatec = cedulatec;
	}

	
	public String getNombrestec() {
		return nombrestec;
	}

	public void setNombrestec(String nombrestec) {
		this.nombrestec = nombrestec;
	}

	public Integer getCodigo_calren() {
		return codigo_calren;
	}

	public void setCodigo_calren(Integer codigo_calren) {
		this.codigo_calren = codigo_calren;
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

	public boolean isAux() {
		return aux;
	}

	public void setAux(boolean aux) {
		this.aux = aux;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getPunto() {
		return punto;
	}

	public void setPunto(Integer punto) {
		this.punto = punto;
	}

	
}
