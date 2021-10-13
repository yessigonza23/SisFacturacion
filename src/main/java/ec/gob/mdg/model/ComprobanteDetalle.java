package ec.gob.mdg.model;

import java.io.Serializable;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "comprobantedetalle", schema = "financiero")
public class ComprobanteDetalle implements Serializable{

	private static final long serialVersionUID = 6515458854356290221L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "id_comprobante")
	private Comprobante comprobante;
	
	@ManyToOne
	@JoinColumn(name = "id_recaudaciondetalle")
	private RecaudacionDetalle recaudaciondetalle ;
	
	@Column(name = "cantidad")
	private Integer cantidad;
	
	@Column(name = "valorcero")
	private Double valorcero;
	
	@Column(name = "valoriva")
	private Double valoriva;
	
	@Column(name = "subtotal")
	private Double subtotal;
	
	@Column(name = "id_tmp")
	private Integer id_tmp;
	
	
	public RecaudacionDetalle getRecaudaciondetalle() {
		return recaudaciondetalle;
	}

	public void setRecaudaciondetalle(RecaudacionDetalle recaudaciondetalle) {
		this.recaudaciondetalle = recaudaciondetalle;
	}



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




	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Double getValorcero() {
		return valorcero;
	}

	public void setValorcero(Double valorcero) {
		this.valorcero = valorcero;
	}

	public Double getValoriva() {
		return valoriva;
	}

	public void setValoriva(Double valoriva) {
		this.valoriva = valoriva;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public Integer getId_tmp() {
		return id_tmp;
	}

	public void setId_tmp(Integer id_tmp) {
		this.id_tmp = id_tmp;
	}
	
	

}
