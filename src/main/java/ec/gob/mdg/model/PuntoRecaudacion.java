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
@Table(name = "puntorecaudacion", schema = "financiero")
public class PuntoRecaudacion implements Serializable {

	private static final long serialVersionUID = 2769951967698491816L;

	@Id
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "id_institucion", nullable = false)
	private Institucion institucion;
	
	@ManyToOne
	@JoinColumn(name = "id_provincia", nullable = false)
	private Provincia provincia;
	
	@Column(name = "nombre", nullable = false, length = 150)
	private String nombre;
	
	@Column(name = "direccion", nullable = false, length = 150)
	private String direccion;
	
	@Column(name = "telefono", nullable = false, length = 10)
	private String telefono;
	
	@Column(name = "puntoemision", nullable = false, length = 3)
	private String puntoemision;
	
	@Column(name = "establecimiento", nullable = false, length = 3)
	private String establecimiento;
	
	@Column(name = "secuencialfactura", nullable = false)
	private Integer secuencialfactura;
	
	@Column(name = "secuencialnotas")
	private Integer secuencialnotas;
	
	@Column(name = "estado")
	private Boolean estado;
	
	@Column(name = "responsable")
	private String responsable;
	
	@Column(name = "responsablecargo")
	private String responsablecargo;
	
	@Column(name = "jefe")
	private String jefe;
	
	@Column(name = "jefecargo")
	private String jefecargo;
	

	
	///////
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PuntoRecaudacion other = (PuntoRecaudacion) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Institucion getInstitucion() {
		return institucion;
	}

	public void setInstitucion(Institucion institucion) {
		this.institucion = institucion;
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

	public String getPuntoemision() {
		return puntoemision;
	}

	public void setPuntoemision(String puntoemision) {
		this.puntoemision = puntoemision;
	}

	public String getEstablecimiento() {
		return establecimiento;
	}

	public void setEstablecimiento(String establecimiento) {
		this.establecimiento = establecimiento;
	}

	
	public Integer getSecuencialfactura() {
		return secuencialfactura;
	}

	public void setSecuencialfactura(Integer secuencialfactura) {
		this.secuencialfactura = secuencialfactura;
	}

	public Integer getSecuencialnotas() {
		return secuencialnotas;
	}

	public void setSecuencialnotas(Integer secuencialnotas) {
		this.secuencialnotas = secuencialnotas;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public String getResponsable() {
		return responsable;
	}

	public void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	public String getResponsablecargo() {
		return responsablecargo;
	}

	public void setResponsablecargo(String responsablecargo) {
		this.responsablecargo = responsablecargo;
	}

	public String getJefe() {
		return jefe;
	}

	public void setJefe(String jefe) {
		this.jefe = jefe;
	}

	public String getJefecargo() {
		return jefecargo;
	}

	public void setJefecargo(String jefecargo) {
		this.jefecargo = jefecargo;
	}

	public Provincia getProvincia() {
		return provincia;
	}

	public void setProvincia(Provincia provincia) {
		this.provincia = provincia;
	}
	
	
		
}
