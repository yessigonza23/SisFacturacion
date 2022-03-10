package ec.gob.mdg.validaciones;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.util.CedulaRuc;

@Named
@RequestScoped
public class FunValidaciones implements Serializable {

	private static final long serialVersionUID = 1L;

	Usuario p = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");

	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;
	private boolean fechaInicialFinalMayoraHoy;
	private boolean fechaInicialyFinMayor30Dias;
	public static final int NEGATIVO_TREINTA = -30;
	boolean validador;

	// VALIDAR FECHA QUE NO SEA MAYOR A LA FECHA ACTUAL
	public Boolean esMayorFechaHoy(Date fecha) throws Exception {
		Date hoy = new Date();
		Boolean resultado = false;

		if (fecha != null && fecha.after(hoy)) {
			resultado = true;
		}

		return resultado;
	}

	// VALIDAR QUE NO SEA MAYOR DE 30DIAS
	public Boolean esMayor30Dias(Date fechaInicial, Date fechaFinal) throws Exception {
		Date fhFinal = fechaFinal;
		Boolean resultado = false;
		if (fechaInicial != null && fhFinal != null) {
			Calendar c = Calendar.getInstance();
			c.setTime(fhFinal);
			c.add(Calendar.DATE, NEGATIVO_TREINTA);
			fhFinal = c.getTime();
			if (fechaInicial.getTime() < fhFinal.getTime()) {
				resultado = true;
			}
		}
		return resultado;
	}

	/// VALIDA FECHA QUE NO SEA MAYOR A LA ACTUAL
	public void validarFechasNoMayorHoy(Date fecha) throws Exception {
		fechaInicialFinalMayoraHoy = false;
		if (fecha != null) {
			if (esMayorFechaHoy(fecha)) {
				fechaInicialFinalMayoraHoy = true;
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Fecha es mayor a la fecha de hoy"));
			}

		}
	}

	// VALIDA LA FECHA QUE NO SEA MAYOR A LA ACTUAL
	public void validaFechaActual(Date fecha) {
		Date fechaactual = new Date(System.currentTimeMillis());
		if (fecha.after(fechaactual)) {
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Fecha es mayor a la fecha de hoy"));
		}
	}

	/// VALIDAR RANGO DE FECHAS
	public void validarFechas(Date fechaInicio, Date fechaFinal) throws Exception {

		fechaInicialFinalMayoraHoy = false;
		fechaInicialyFinMayor30Dias = false;
		if (fechaInicio != null && fechaFinal != null) {
			if (esMayorFechaHoy(fechaInicio) || esMayorFechaHoy(fechaFinal)) {
				fechaInicialFinalMayoraHoy = true;
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Error!", "Fecha Inicio o Fecha Fin es mayor a la fecha de hoy"));
			}
			if (esMayor30Dias(fechaInicio, fechaFinal)) {
				fechaInicialyFinMayor30Dias = true;
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Fecha es mayor a 30 dias."));
			}
		}
	}

	/// REDONDEAR DOUBLE
	public static Double formatearDecimales(Double numero, Integer numeroDecimales) {
		return Math.round(numero * Math.pow(10, numeroDecimales)) / Math.pow(10, numeroDecimales);
	}

	// VALIDADOR DE CEDULA-RUC
	public boolean validaIdentificacion(String ci, String tipo) {
		
		String validaIdentificacion = CedulaRuc.comprobacion(ci, tipo);
		if (validaIdentificacion.equals("T")) {
			validador = false;
		} else {
			validador = true;

		}
		return validador;
	}
	
	
	// VALIDAR SI LA CEDULA DEL USUARIO INGRESADA EXISTE 
	public Boolean cedulaExiste(String ci) throws Exception {
		
		Boolean resultado = false;
		String cedular;
		try {
			Query query = em.createNativeQuery("select distinct ci from financiero.usuario  where ci=?1");
			query.setParameter(1, ci);
			cedular = (String) query.getSingleResult();
			
			if (cedular != null) {
				resultado = true;
			}else {
				resultado= false;
			}
		
			
		} catch (Exception e) {
			System.out.println(e);
		}
		return resultado;	
	}

	

	public Usuario getP() {
		return p;
	}

	public void setP(Usuario p) {
		this.p = p;
	}

	public boolean isFechaInicialFinalMayoraHoy() {
		return fechaInicialFinalMayoraHoy;
	}

	public void setFechaInicialFinalMayoraHoy(boolean fechaInicialFinalMayoraHoy) {
		this.fechaInicialFinalMayoraHoy = fechaInicialFinalMayoraHoy;
	}

	public boolean isFechaInicialyFinMayor30Dias() {
		return fechaInicialyFinMayor30Dias;
	}

	public void setFechaInicialyFinMayor30Dias(boolean fechaInicialyFinMayor30Dias) {
		this.fechaInicialyFinMayor30Dias = fechaInicialyFinMayor30Dias;
	}

	public boolean isValidador() {
		return validador;
	}

	public void setValidador(boolean validador) {
		this.validador = validador;
	}

}
