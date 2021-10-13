package ec.gob.mdg.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cliente", schema = "financiero")
public class Cliente implements Serializable{

	private static final long serialVersionUID = -6421697672554759979L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "ci", nullable = false, length = 13)
	private String ci;
	
	@Column(name = "nombre", nullable = false, length = 150)
	private String nombre;
	
	@Column(name = "direccion", nullable = false, length = 150)
    private String direccion;
	
	@Column(name = "telefono", nullable = false, length = 10)
    private String telefono;
	
	@Column(name = "correo", nullable = false, length = 150)
    private String correo;
	
	@Column(name = "tipoid", nullable = false, length = 1)
    private String tipoid;
	
	@Column(name = "clave")
	private String clave;
	

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

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getTipoid() {
		return tipoid;
	}

	public void setTipoid(String tipoid) {
		this.tipoid = tipoid;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}
	

	
}
