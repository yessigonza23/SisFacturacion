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
@Table(name = "institucion", schema = "financiero" )
public class Institucion implements Serializable {
	
	private static final long serialVersionUID = 130672173486432805L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "nombre", nullable = false, length = 200)
	private String nombre;
	
	@Column(name = "estado", nullable = false, length = 1)
	private String estado="A";
	
	@Column(name = "fechainicial", nullable = false)
	private Date fechainicial;
	
	@Column(name = "fechafinal", nullable = false)
	private Date fechafinal;
	
	@Column(name = "logo", nullable = true)
	private byte[] logo;
	
	@Column(name = "ruc", nullable = false, length = 13)
	private String ruc;
	
	@Column(name = "direccion", nullable = false, length = 200)
	private String direccion;
	
	@Column(name = "ambiente")
	private String ambiente;
		
	@Column(name = "emision")
	private String emision;
	
	@Column(name = "servidorcorreo")
	private String servidorcorreo;

	@Column(name = "puerto")
	private String puerto;
	
	@Column(name = "usuariocorreo")
	private String usuariocorreo;
	
	@Column(name = "clavecorreo")
	private String clavecorreo;
	
	@Column(name = "remitente")
	private String remitente;
	
	@Column(name = "mensaje")
	private String mensaje;
	
	@Column(name = "obligadocontabilidad")
	private String obligadocontabilidad;
	
	@Column(name = "rutaarchivo")
	private String rutaarchivo;
	
	public String getRutaarchivo() {
		return rutaarchivo;
	}
	public void setRutaarchivo(String rutaarchivo) {
		this.rutaarchivo = rutaarchivo;
	}
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
		Institucion other = (Institucion) obj;
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
	public String getNombre() {
		return nombre;
	}

	public String getEstado() {
		return estado="A";
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechainicial() {
		return fechainicial;
	}
	public void setFechainicial(Date fechainicial) {
		this.fechainicial = fechainicial;
	}
	public Date getFechafinal() {
		return fechafinal;
	}
	public void setFechafinal(Date fechafinal) {
		this.fechafinal = fechafinal;
	}
	public byte[] getLogo() {
		return logo;
	}
	public void setLogo(byte[] logo) {
		this.logo = logo;
	}
	public String getRuc() {
		return ruc;
	}
	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getAmbiente() {
		return ambiente;
	}
	public void setAmbiente(String ambiente) {
		this.ambiente = ambiente;
	}
	public String getEmision() {
		return emision;
	}
	public void setEmision(String emision) {
		this.emision = emision;
	}
	public String getServidorcorreo() {
		return servidorcorreo;
	}
	public void setServidorcorreo(String servidorcorreo) {
		this.servidorcorreo = servidorcorreo;
	}
	public String getPuerto() {
		return puerto;
	}
	public void setPuerto(String puerto) {
		this.puerto = puerto;
	}
	public String getUsuariocorreo() {
		return usuariocorreo;
	}
	public void setUsuariocorreo(String usuariocorreo) {
		this.usuariocorreo = usuariocorreo;
	}
	public String getClavecorreo() {
		return clavecorreo;
	}
	public void setClavecorreo(String clavecorreo) {
		this.clavecorreo = clavecorreo;
	}
	public String getRemitente() {
		return remitente;
	}
	public void setRemitente(String remitente) {
		this.remitente = remitente;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public String getObligadocontabilidad() {
		return obligadocontabilidad;
	}
	public void setObligadocontabilidad(String obligadocontabilidad) {
		this.obligadocontabilidad = obligadocontabilidad;
	}
	
	
	
}
