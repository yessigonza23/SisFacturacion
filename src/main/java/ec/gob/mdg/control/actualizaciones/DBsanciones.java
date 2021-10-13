package ec.gob.mdg.control.actualizaciones;

import java.io.Serializable;
import java.util.Date;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.util.UtilsDate;

@Named
@RequestScoped
public class DBsanciones implements Serializable {

	private static final long serialVersionUID = 1L;

	Usuario p = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");

	@PersistenceContext(unitName = "contraPU")
	private EntityManager em;

	// METODO PRINCIPAL
	public void actualiza(String servicio, String codigoEntidad, String numeroFactura, String numeroJuicio) {

		// CAMBIO DE CATEGORIA
		if (servicio.equals("S")) { ///
			
			try {
				actualizaSanciones(codigoEntidad, numeroJuicio, numeroFactura);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// ACTUALIZA FACTURA EN SANCIONES
	@Transactional
	public void actualizaSanciones(String codigoEntidad, String numeroJuicio, String numeroFactura) throws Exception {
		Date fechaActual = UtilsDate.fechaActual();
		try {
			Query query = em.createNativeQuery(
					"UPDATE tc_contravencion SET cont_numero_fatura =?1,cont_fecha_pago_multa=?2 WHERE empr_codigo=?3 and cont_codigo=?4  and cont_numero_fatura is null");
			query.setParameter(1, numeroFactura);
			query.setParameter(2, fechaActual);
			query.setParameter(3, codigoEntidad);
			query.setParameter(4, numeroJuicio);
			query.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

//ACTUALIZAR EN SANCIONES EL AUXILIAR CON NULL PARA ENCERAR PARA LA NUEVA SELECCIPON
	@Transactional
	public void actualizaSancionesAuxNull(String codentidad,Integer punto) throws Exception {
		
		try {
			Query query = em.createNativeQuery(
					"UPDATE tc_contravencion SET aux = null,punto=null WHERE empr_codigo=?1 and aux =?2  and cont_numero_fatura is null and punto=?3");
			query.setParameter(1, codentidad);
			query.setParameter(2, "S");
			query.setParameter(3, punto);
			query.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	// ACTUALIZA SANCIONES EL AUXILIAR PARA SABER CUAL SE VA HA ACTUALIZAR
	@Transactional
	public void actualizaSancionesAux(String codigo, String numjuicio,Integer punto) throws Exception {
		try {
						
			Query query = em.createNativeQuery(
					"UPDATE tc_contravencion SET aux=?1,punto=?2 WHERE empr_codigo=?3 and cont_codigo=?4  and cont_numero_fatura is null");
			query.setParameter(1, "S");
			query.setParameter(2, punto);
			query.setParameter(3, codigo);
			query.setParameter(4, numjuicio);
			query.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		}

	}


}
