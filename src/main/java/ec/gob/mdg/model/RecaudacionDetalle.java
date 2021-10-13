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
@Table(name = "recaudaciondetalle", schema = "financiero")
public class RecaudacionDetalle implements Serializable {

	private static final long serialVersionUID = -349426395717611086L;

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "id_recaudacion", nullable = false)
	private Recaudacion recaudacion;
	
	@Column(name = "factor", nullable = false)
	private Double factor;	
	
	@Column(name = "nombre", nullable = false, length = 200)
	private String nombre;
	
	@Column(name = "valor", nullable = false)
	private Double valor;
	
	@Column(name = "valoriva", nullable = false)
	private Double valoriva;
	
	@Column(name = "estado", nullable = false, length = 1 )
	private String estado="A";
	
	@Column(name = "rangoinicial", nullable = false)
	private Double rangoinicial;
	
	@Column(name = "rangofinal", nullable = false)
	private Double rangofinal;
	
	@Column(name = "codigo", nullable = false, length = 10)
	private String codigo;	
	
	/////HASHCODE
	
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
		RecaudacionDetalle other = (RecaudacionDetalle) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	////GETTERS Y SETTERS
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
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
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
	public Double getValor() {
		return valor;
	}
	public void setValor(Double valor) {
		this.valor = valor;
	}
	public Double getRangoinicial() {
		return rangoinicial;
	}
	public void setRangoinicial(Double rangoinicial) {
		this.rangoinicial = rangoinicial;
	}
	public Double getRangofinal() {
		return rangofinal;
	}
	public void setRangofinal(Double rangofinal) {
		this.rangofinal = rangofinal;
	}
	public Double getFactor() {
		return factor;
	}
	public void setFactor(Double factor) {
		this.factor = factor;
	}
	public Recaudacion getRecaudacion() {
		return recaudacion;
	}
	public void setRecaudacion(Recaudacion recaudacion) {
		this.recaudacion = recaudacion;
	}
	public Double getValoriva() {
		return valoriva;
	}
	public void setValoriva(Double valoriva) {
		this.valoriva = valoriva;
	}
	

}
