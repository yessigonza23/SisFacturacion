package ec.gob.mdg.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "contable", schema = "financiero")
public class Contable implements Serializable{

	private static final long serialVersionUID = -1243010807021129942L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "id_puntorecaudacion")
	private PuntoRecaudacion puntoRecaudacion;
	
	@ManyToOne
	@JoinColumn(name = "id_usuariopunto")
	private UsuarioPunto usuarioPunto;
	
    @Column(name = "id_cierre")
	private Integer idcierre;
	
	@Column(name = "fecha")
	private Date fecha;
	
	@Column(name = "valor")
	private double valor;
	
	@Column(name = "detalle")
	private String detalle;

	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PuntoRecaudacion getPuntoRecaudacion() {
		return puntoRecaudacion;
	}

	public void setPuntoRecaudacion(PuntoRecaudacion puntoRecaudacion) {
		this.puntoRecaudacion = puntoRecaudacion;
	}

	public UsuarioPunto getUsuarioPunto() {
		return usuarioPunto;
	}

	public void setUsuarioPunto(UsuarioPunto usuarioPunto) {
		this.usuarioPunto = usuarioPunto;
	}

	

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	
	public Integer getIdcierre() {
		return idcierre;
	}

	public void setIdcierre(Integer idcierre) {
		this.idcierre = idcierre;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getDetalle() {
		return detalle;
	}

	public void setDetalle(String detalle) {
		this.detalle = detalle;
	}
	

}
