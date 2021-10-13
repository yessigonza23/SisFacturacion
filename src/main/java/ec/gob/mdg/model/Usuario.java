package ec.gob.mdg.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "usuario", schema = "financiero")
public class Usuario implements Serializable {

	private static final long serialVersionUID = 9092602508286692359L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "ci", nullable = false, length = 10)
	private String ci;

	@Column(name = "nombre", nullable = false, length = 100)
	private String nombre;

	@Column(name = "apellido", nullable = false, length = 100)
	private String apellido;

	@Column(name = "username", nullable = false, length = 30)
	private String username;

	@Column(name = "constrasena", nullable = false, length = 80)
	private String contrasena;

	@Column(name = "estado", nullable = false, length = 1)
	private String estado = "A";

	@Column(name = "nombrefirmaelectronica", nullable = false, length = 30)
	private String nombrefirmaelectronica;

	@Column(name = "clavefirma", nullable = false, length = 20)
	private String clavefirma;

	@Column(name = "cargo", nullable = false, length = 100)
	private String cargo;
	
	@Column(name = "tipo")
	private String tipo;
	
	@Column(name = "titulo")
	private String titulo;
	
	@Column(name = "telefono")
	private String telefono;
	
	@Column(name = "correoelectronico")
	private String correoelectronico;
	
	@Column(name = "fechacambioclave")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechacambioclave;
	
	@Column(name = "fechareinicioclave")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechareinicioclave;
	
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreoelectronico() {
		return correoelectronico;
	}

	public void setCorreoelectronico(String correoelectronico) {
		this.correoelectronico = correoelectronico;
	}

	public Date getFechacambioclave() {
		return fechacambioclave;
	}

	public void setFechacambioclave(Date fechacambioclave) {
		this.fechacambioclave = fechacambioclave;
	}
	
	public Date getFechareinicioclave() {
		return fechareinicioclave;
	}

	public void setFechareinicioclave(Date fechareinicioclave) {
		this.fechareinicioclave = fechareinicioclave;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getContrasena() {
		return contrasena;
	}

	public void setContrasena(String contrasenia) {
		this.contrasena = contrasenia;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getNombrefirmaelectronica() {
		return nombrefirmaelectronica;
	}

	public void setNombrefirmaelectronica(String nombrefirmaelectronica) {
		this.nombrefirmaelectronica = nombrefirmaelectronica;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCi() {
		return ci;
	}

	public void setCi(String ci) {
		this.ci = ci;
	}

	public String getClavefirma() {
		return clavefirma;
	}

	public void setClavefirma(String clavefirma) {
		this.clavefirma = clavefirma;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}




}
