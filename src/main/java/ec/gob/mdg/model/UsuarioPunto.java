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
@Table(name = "usuariopunto", schema = "financiero")
public class UsuarioPunto implements Serializable {
	
	private static final long serialVersionUID = -3243256238205262482L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "id_puntorecaudacion")
	private PuntoRecaudacion puntoRecaudacion;
	
	@Column(name = "fechainicio")
	private Date fechainicio;
	
	@Column(name = "fechafin")
	private Date fechafin;
	
	@Column(name = "estado")
	private String estado = "A";

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public PuntoRecaudacion getPuntoRecaudacion() {
		return puntoRecaudacion;
	}

	public void setPuntoRecaudacion(PuntoRecaudacion puntoRecaudacion) {
		this.puntoRecaudacion = puntoRecaudacion;
	}

	

	public Date getFechainicio() {
		return fechainicio;
	}

	public void setFechainicio(Date fechainicio) {
		this.fechainicio = fechainicio;
	}

	public Date getFechafin() {
		return fechafin;
	}

	public void setFechafin(Date fechafin) {
		this.fechafin = fechafin;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}


}
