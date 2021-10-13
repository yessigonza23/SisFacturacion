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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "cierre", schema = "financiero")
public class Cierre implements Serializable {

	private static final long serialVersionUID = -6549260001814659614L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nombre", length = 100)
	private String nombre;
	
	@ManyToOne
	@JoinColumn(name = "id_puntorecaudacion")
	private PuntoRecaudacion puntoRecaudacion;
	
	@ManyToOne
	@JoinColumn(name = "id_usuariopunto")
	private UsuarioPunto usuarioPunto;
	
	@Column(name = "fecha")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fecha;
	
	@Column(name = "valor")
	private double valor;
	
	@Column(name = "detalle")
	private String detalle;
	
	@Column(name = "id_banco")
	private Integer id_banco;
	
	@Column(name = "id_cuenta")
	private Integer id_cuenta;
	
	@Column(name = "estado")
	private String estado;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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


	public Integer getId_banco() {
		return id_banco;
	}

	public void setId_banco(Integer id_banco) {
		this.id_banco = id_banco;
	}

	public Integer getId_cuenta() {
		return id_cuenta;
	}

	public void setId_cuenta(Integer id_cuenta) {
		this.id_cuenta = id_cuenta;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}


	
}
