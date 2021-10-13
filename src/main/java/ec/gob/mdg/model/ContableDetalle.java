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
@Table(name = "contabledetalle", schema = "financiero")
public class ContableDetalle implements Serializable {

	private static final long serialVersionUID = 8166606462175540239L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "id_contable")
	private Contable contable;
	
	@ManyToOne
	@JoinColumn(name = "id_plancontable")
	private PlanContable plancontable;
	
	@Column(name = "debe")
	private double debe;
	
	@Column(name = "haber")
	private double haber;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}



	public Contable getContable() {
		return contable;
	}

	public void setContable(Contable contable) {
		this.contable = contable;
	}

	

	public PlanContable getPlancontable() {
		return plancontable;
	}

	public void setPlancontable(PlanContable plancontable) {
		this.plancontable = plancontable;
	}

	public void setHaber(double haber) {
		this.haber = haber;
	}

	public double getDebe() {
		return debe;
	}

	public void setDebe(double debe) {
		this.debe = debe;
	}

	public double getHaber() {
		return haber;
	}

}
