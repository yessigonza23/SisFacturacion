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
@Table(name = "consolidadepositos", schema = "financiero")
public class ConsolidaDepositos implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "id_comprobantedeposito")
	private ComprobanteDepositos comprobanteDepositos;
	
	@ManyToOne
	@JoinColumn(name = "id_estadocuenta")
	private EstadoCuenta estadoCuenta;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@Column(name = "saldo")
	private double saldo;
	
	@Column(name = "estado")
	private Boolean estado;	
	
	@Column(name = "tipoconsolidacion")
	private String tipoconsolidacion;
	
	@Column(name = "fecha")
	private Date fecha;
	
	@Column(name = "observacion")
	private String observacion;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ComprobanteDepositos getComprobanteDepositos() {
		return comprobanteDepositos;
	}

	public void setComprobanteDepositos(ComprobanteDepositos comprobanteDepositos) {
		this.comprobanteDepositos = comprobanteDepositos;
	}

	public EstadoCuenta getEstadoCuenta() {
		return estadoCuenta;
	}

	public void setEstadoCuenta(EstadoCuenta estadoCuenta) {
		this.estadoCuenta = estadoCuenta;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public String getTipoconsolidacion() {
		return tipoconsolidacion;
	}

	public void setTipoconsolidacion(String tipoconsolidacion) {
		this.tipoconsolidacion = tipoconsolidacion;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	
	
}
