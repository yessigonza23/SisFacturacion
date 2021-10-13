package ec.gob.mdg.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "auditoria", schema = "financiero")

public class Auditoria implements Serializable {

	private static final long serialVersionUID = -6549260001814659614L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nombretabla")
	private String nombretabla;
	
	@Column(name = "nombrecampo")
	private String nombrecampo;
	
	@Column(name = "operacion")
	private String operacion;
	
	@Column(name = "usuario")
	private String usuario;
	
	@Column(name = "fecha")
	private Date fecha;
	
	@Column(name = "valoractual")
	private String valoractual;
	
	@Column(name = "valoranterior")
	private String valoranterior;
	
	@Column(name = "claveprimaria")
	private Integer claveprimaria;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombretabla() {
		return nombretabla;
	}

	public void setNombretabla(String nombretabla) {
		this.nombretabla = nombretabla;
	}

	public String getNombrecampo() {
		return nombrecampo;
	}

	public void setNombrecampo(String nombrecampo) {
		this.nombrecampo = nombrecampo;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getValoractual() {
		return valoractual;
	}

	public void setValoractual(String valoractual) {
		this.valoractual = valoractual;
	}

	public String getValoranterior() {
		return valoranterior;
	}

	public void setValoranterior(String valoranterior) {
		this.valoranterior = valoranterior;
	}

	public Integer getClaveprimaria() {
		return claveprimaria;
	}

	public void setClaveprimaria(Integer claveprimaria) {
		this.claveprimaria = claveprimaria;
	}
	
	
	
}
