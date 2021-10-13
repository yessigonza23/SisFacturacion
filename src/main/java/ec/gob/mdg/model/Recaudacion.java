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
@Table(name = "recaudacion", schema = "financiero")
public class Recaudacion implements Serializable {

	private static final long serialVersionUID = -5163269823555345756L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "id_proceso")
	private Proceso proceso;
	
	@ManyToOne
	@JoinColumn(name = "id_plancontable")
	private PlanContable plancontable;
	
	@ManyToOne
	@JoinColumn(name = "id_planpresupuestario")
	private PlanPresupuestario planpresupuestario;
	
	@Column(name = "codigobanco", nullable = false, length = 10)
	private String codigobanco;
	
	@Column(name = "nombre", nullable = false, length = 200)
	private String nombre;

	
	
	
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
		Recaudacion other = (Recaudacion) obj;
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

	public Proceso getProceso() {
		return proceso;
	}

	public void setProceso(Proceso proceso) {
		this.proceso = proceso;
	}

	public PlanContable getPlancontable() {
		return plancontable;
	}

	public void setPlancontable(PlanContable plancontable) {
		this.plancontable = plancontable;
	}

	public PlanPresupuestario getPlanpresupuestario() {
		return planpresupuestario;
	}

	public void setPlanpresupuestario(PlanPresupuestario planpresupuestario) {
		this.planpresupuestario = planpresupuestario;
	}
	
	public String getCodigobanco() {
		return codigobanco;
	}

	public void setCodigobanco(String codigobanco) {
		this.codigobanco = codigobanco;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	

}

