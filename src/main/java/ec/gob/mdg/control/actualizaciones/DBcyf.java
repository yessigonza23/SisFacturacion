package ec.gob.mdg.control.actualizaciones;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import com.ibm.icu.util.Calendar;
import com.ibm.icu.util.TimeZone;

import ec.gob.mdg.control.model.Fin_Cal_Ren_DTO;
import ec.gob.mdg.control.service.IFin_Cal_Ren_DTOService;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.util.UtilsDate;

@Named
@RequestScoped
public class DBcyf implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private IFin_Cal_Ren_DTOService serviceCalRen;

	private List<Fin_Cal_Ren_DTO> listaCalRen;

	Usuario usuario = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");

	@PersistenceContext(unitName = "controlPU")
	private EntityManager em;

	// METODO PRINCIPAL
	public void actualiza(String servicio, Integer codigoEntidad, String numeroFactura, Integer codigoCalrev,
			String num_solicitud) throws Exception {

		// CAMBIO DE CATEGORIA
		if (servicio.equals("A")) {
			actualizaAmpliaciones(codigoEntidad, numeroFactura);
		} else if (servicio.equals("E")) {
			actualizaExportacion(codigoEntidad, numeroFactura, num_solicitud);
		} else if (servicio.equals("I")) {
			actualizaImportacion(codigoEntidad, numeroFactura, num_solicitud);
		} else if (servicio.equals("G")) {
			actualizaGuias(codigoEntidad, numeroFactura, num_solicitud);
		} else if (servicio.equals("T")) {
			actualizaCalRenRepTecnico(codigoEntidad, numeroFactura, codigoCalrev); 
		}else if (servicio.equals("R")) {
			this.listaCalRen = serviceCalRen.listarServiciosPorCodigo(codigoEntidad);
			Integer contador=0;
			for (Fin_Cal_Ren_DTO f : listaCalRen) {
				contador++;
								
			    if (f.getCodigo_recaudacion().equals("Q7")) {
					actualizaBodegas(codigoEntidad, numeroFactura);
				}else if (f.getCodigo_recaudacion().equals("Q6")) {
					actualizaPlantas(codigoEntidad, numeroFactura);
				}else if (f.getCodigo_recaudacion().equals("Q5")) {
					actualizaSucursales(codigoEntidad, numeroFactura);
				}else if (f.getCodigo_recaudacion().substring(0, 2).equals("RV")
						|| f.getCodigo_recaudacion().substring(0, 0).equals("C")) {
					actualizaActividades(codigoEntidad, numeroFactura);
				} else if (f.getCodigo_recaudacion().substring(0, 3).equals("RAC")
						|| f.getCodigo_recaudacion().substring(0, 2).equals("AC")) {
					actualizaCalren(codigoEntidad, numeroFactura, codigoCalrev);
				}else if (f.getCodigo_recaudacion().substring(0, 3).equals("EGT")
						|| f.getCodigo_recaudacion().substring(0, 2).equals("GT")) {
					actualizaVehiculos(codigoEntidad, numeroFactura, codigoCalrev);
				}
			}
		}
	}

	// Actualiza no controlados
    @Transactional
	public void actualizaimpno(String servicio, String codigoEntidad, String numeroFactura, String num_solicitud) {
		if (servicio.equals("N")) {
			try {
				actualizaImportacionNo(codigoEntidad, numeroFactura, num_solicitud);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// ACTUALIZA FACTURA EN SISCYF AMPLIACIONES
	@Transactional
	public void actualizaAmpliaciones(Integer codigoEntidad, String numeroFactura) throws Exception {
		Date fechaActual = UtilsDate.fechaActual();
		Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
		calendar.setTime(fechaActual);
		String anio = String.valueOf(calendar.get(Calendar.YEAR));
		try {
			Query query = em.createNativeQuery(
					"UPDATE cyf_ampliaciones_inf SET factura_int=?1 WHERE cyf_ent_codigo=?2 and imp_certificado='N' and to_char(fecha_ampliacion,'rrrr') =?3 and factura_int is null");
			query.setParameter(1, numeroFactura);
			query.setParameter(2, codigoEntidad);
			query.setParameter(3, anio);
			query.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// ACTUALIZA FACTURA EN SISCYF CALIFICACIONES FIN CAL REV
	// TABLA CYF_CALIFICACIONES_RENOVACIONE
	@Transactional
	public void actualizaCalren(Integer codigoEntidad, String numeroFactura, Integer codigoCalrev) throws Exception {
		try {
			Query query = em.createNativeQuery(
					"UPDATE cyf_calificaciones_renovacione SET num_factura=?1 WHERE cyf_ent_codigo=?2 and codigo=?3 and num_factura is null");
			query.setParameter(1, numeroFactura);
			query.setParameter(2, codigoEntidad);
			query.setParameter(3, codigoCalrev);
			query.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// TABLA DE ACTIVIDADES
	@Transactional
	public void actualizaActividades(Integer codigoEntidad, String numeroFactura) {
		try {
			Query query = em.createNativeQuery(
					"UPDATE cyf_entidades_actividades SET factura_int=?1 WHERE cyf_ent_codigo=?2 and factura_int is null");
			query.setParameter(1, numeroFactura);
			query.setParameter(2, codigoEntidad);
			query.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// TABLA CYF_MANEJO_VEHICULOS
	@Transactional
	public void actualizaVehiculos(Integer codigoEntidad, String numeroFactura, Integer codigoCalrev) throws Exception {
		try {
			Query query = em.createNativeQuery(
					"UPDATE cyf_manejo_vehiculos SET factura=?1 WHERE cyf_ent_codigo=?2 and cyf_calrev_codigo=?3 and factura is null");
			query.setParameter(1, numeroFactura);
			query.setParameter(2, codigoEntidad);
			query.setParameter(3, codigoCalrev);
			query.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// TABLA CYF_SUCURSALES
	public void actualizaSucursales(Integer codigoEntidad, String numeroFactura) throws Exception {
		try {
			Query query = em.createNativeQuery(
					"UPDATE cyf_sucursales SET factura_int=?1 WHERE cyf_ent_codigo=?2 and factura_int is null");
			query.setParameter(1, numeroFactura);
			query.setParameter(2, codigoEntidad);
			query.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// TABLA CYF_BODEGAS
	public void actualizaBodegas(Integer codigoEntidad, String numeroFactura) throws Exception {
		try {
			Query query = em.createNativeQuery(
					"UPDATE cyf_bodegas SET factura_int=?1 WHERE cyf_ent_codigo=?2 and factura_int is null");
			query.setParameter(1, numeroFactura);
			query.setParameter(2, codigoEntidad);
			query.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// TABLA CYF_PLANTAS
	public void actualizaPlantas(Integer codigoEntidad, String numeroFactura) throws Exception {
		try {
			Query query = em.createNativeQuery(
					"UPDATE cyf_plantas SET factura_int=?1 WHERE cyf_ent_codigo=?2 and factura_int is null");
			query.setParameter(1, numeroFactura);
			query.setParameter(2, codigoEntidad);
			query.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	////////////////////////////////TERMINA FIN CAL REV
	
	//////////////////////////////// EXPORTACIONES
	// ACTUALIZAR EN EXPORTACIONES EL AUXILIAR CON NULL PARA ENCERAR PARA LA NUEVA
	// SELECCIPON
	@Transactional
	public void actualizaExportacionesAuxNull(Integer codentidad, Integer punto) throws Exception {
		try {
			Query query = em.createNativeQuery(
					"UPDATE si_exportaciones SET aux=null,punto=null WHERE cod_entidad=?1 and aux =?2 and num_fact_int is null and punto =?3");
			query.setParameter(1, codentidad);
			query.setParameter(2, "S");
			query.setParameter(3, punto);
			query.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// ACTUALIZA exPORTACIONES EL AUXILIAR PARA SABER CUAL SE VA HA ACTUALIZAR
	@Transactional
	public void actualizaExportacionesAux(Integer codentidad, String numsolicitud, Integer punto) throws Exception {
		try {
			Query query = em.createNativeQuery(
					"UPDATE si_exportaciones SET aux=?1, punto=?2 WHERE cod_entidad=?3 and num_solicitud=?4  and num_fact_int is null");
			query.setParameter(1, "S");
			query.setParameter(2, punto);
			query.setParameter(3, codentidad);
			query.setParameter(4, numsolicitud);
			query.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

	// ACTUALIZA SECUENCIA EN FACTURA EN EXPORTACION
	@Transactional
	public void actualizaExportacion(Integer codigoEntidad, String numeroFactura, String num_solicitud)
			throws Exception {

		try {
			Query query = em.createNativeQuery("UPDATE si_exportaciones SET num_fact_int = ?1 "
					+ "WHERE cod_entidad = ?2 " + "and num_solicitud = ?3 " + "and num_fact_int is null");
			query.setParameter(1, numeroFactura);
			query.setParameter(2, codigoEntidad);
			query.setParameter(3, num_solicitud);
			query.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	///////////////////////////// FIN EXPORTACIONES

	////////////////////// IMPORTACIONES

	// ACTUALIZA IMPORTACIONES EL AUXILIAR PARA SABER CUAL SE VA HA ACTUALIZAR
	@Transactional
	public void actualizaImportacionesAux(Integer codentidad, String numsolicitud, Integer punto) throws Exception {

		try {
			Query query = em.createNativeQuery(
					"UPDATE si_importaciones SET aux=?1,punto=?2,fecha=SYSDATE WHERE cod_entidad=?3 and num_solicitud=?4  and num_fact_int is null");
			query.setParameter(1, "S");
			query.setParameter(2, punto);
			query.setParameter(3, codentidad);
			query.setParameter(4, numsolicitud);
			query.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	// ACTUALIZAR EN IMPORTACIONES EL AUXILIAR CON NULL PARA ENCERAR PARA LA NUEVA
	// SELECCION
	@Transactional
	public void actualizaImportacionesAuxNull(Integer codentidad, Integer punto) throws Exception {

		try {
			Query query = em.createNativeQuery(
					"UPDATE si_importaciones SET aux=null,punto=null WHERE cod_entidad=?1 and aux =?2 and num_fact_int is null and punto =?3");

			query.setParameter(1, codentidad);
			query.setParameter(2, "S");
			query.setParameter(3, punto);
			query.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	// ACTUALIZA IMPORTACIONES NUMERO DE FACTUARA DE PAGO
	@Transactional
	public void actualizaImportacion(Integer codigoEntidad, String numeroFactura, String num_solicitud)
			throws Exception {

		try {
			Query query = em.createNativeQuery(
					"UPDATE si_importaciones SET num_fact_int = ?1 WHERE cod_entidad = ?2 and num_solicitud = ?3 and num_fact_int is null");
			query.setParameter(1, numeroFactura);
			query.setParameter(2, codigoEntidad);
			query.setParameter(3, num_solicitud);
			query.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	////////////// FIN IMPORTACIONES

//////////////////////IMPORTACIONES NO CONTROLADOS

// ACTUALIZA IMPORTACIONES NO CONTROLADOS EL AUXILIAR PARA SABER CUAL SE VA HA ACTUALIZAR
	@Transactional
	public void actualizaImportacionesNoAux(String codentidad, String numsolicitud, Integer punto) throws Exception {

		try {
			Query query = em.createNativeQuery(
					"UPDATE si_importaciones_no SET aux=?1,punto=?2,fecha=SYSDATE WHERE ruc=?3 and num_solicitud=?4  and num_fact_int is null");
			query.setParameter(1, "S");
			query.setParameter(2, punto);
			query.setParameter(3, codentidad);
			query.setParameter(4, numsolicitud);
			query.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

// ACTUALIZAR EN IMPORTACIONES EL AUXILIAR CON NULL PARA ENCERAR PARA LA NUEVA SELECCIPON
	@Transactional
	public void actualizaImportacionesNoAuxNull(String codentidad, Integer punto) throws Exception {

		try {
			Query query = em.createNativeQuery(
					"UPDATE si_importaciones_no SET aux=null,punto=null WHERE ruc=?1 and aux =?2 and num_fact_int is null and punto =?3");

			query.setParameter(1, codentidad);
			query.setParameter(2, "S");
			query.setParameter(3, punto);
			query.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

// ACTUALIZA IMPORTACIONES NUMERO DE FACTUARA DE PAGO
	@Transactional
	public void actualizaImportacionNo(String codigoEntidad, String numeroFactura, String num_solicitud)
			throws Exception {

		try {
			Query query = em.createNativeQuery(
					"UPDATE si_importaciones_no SET num_fact_int = ?1 WHERE ruc = ?2 and num_solicitud = ?3 and num_fact_int is null");
			query.setParameter(1, numeroFactura);
			query.setParameter(2, codigoEntidad);
			query.setParameter(3, num_solicitud);
			query.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

//////////////FIN IMPORTACIONES NO CONTROLADOS

////////////////////////////////GUIAS

// ACTUALIZAR EN GUIAS EL AUXILIAR CON NULL PARA ENCERAR PARA LA NUEVA
// SELECCI�N
	@Transactional
	public void actualizaGuiasAuxNull(Integer codentidad, Integer punto) throws Exception {
		try {
			Query query = em.createNativeQuery(
					"UPDATE si_guias SET aux=null,punto=null,fecha_f=SYSDATE WHERE cod_entidad=?1 and aux =?2 and numero_recibo is null and punto =?3");

			query.setParameter(1, codentidad);
			query.setParameter(2, "S");
			query.setParameter(3, punto);
			query.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

// ACTUALIZA GUIAS EL AUXILIAR PARA SABER CUAL SE VA HA ACTUALIZAR
	@Transactional
	public void actualizaGuiasAux(Integer codentidad, String numsolicitud, Integer punto) throws Exception {
		try {
			Query query = em.createNativeQuery(
					"UPDATE si_guias SET aux=?1, punto=?2,fecha_f=SYSDATE WHERE cod_entidad=?3 and codigo_guia=?4  and numero_recibo is null");
			query.setParameter(1, "S");
			query.setParameter(2, punto);
			query.setParameter(3, codentidad);
			query.setParameter(4, numsolicitud);
			query.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

// ACTUALIZA SECUENCIA EN FACTURA EN GUIAS
	@Transactional
	public void actualizaGuias(Integer codigoEntidad, String numeroFactura, String num_solicitud) throws Exception {

		try {
			Query query = em.createNativeQuery("UPDATE si_guias SET numero_recibo = ?1 " + "WHERE cod_entidad = ?2 "
					+ "and codigo_guia = ?3 " + "and numero_recibo is null");
			query.setParameter(1, numeroFactura);
			query.setParameter(2, codigoEntidad);
			query.setParameter(3, num_solicitud);
			query.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

///////////////////////////// FIN GUIAS

////REPRESENTANTES TECNICOS////////////////////////////////

// ACTUALIZAR EN REPRESENTANTES TECNICOS EL AUXILIAR CON NULL PARA ENCERAR PARA LA NUEVA
// SELECCIPON
	@Transactional
	public void actualizaCalRenRepTecnicoAuxNull(Integer idtec, Integer punto) throws Exception {
		try {
			Query query = em.createNativeQuery(
					"UPDATE rep_califica_renova SET aux=null,punto=null WHERE idtec=?1 and aux =?2 and factura is null and punto =?3");

			query.setParameter(1, idtec);
			query.setParameter(2, "S");
			query.setParameter(3, punto);
			query.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

// ACTUALIZA REPTECNICOS EL AUXILIAR PARA SABER CUAL SE VA HA ACTUALIZAR
	@Transactional
	public void actualizaCalRenRepTecnicoAux(Integer idtec, Integer codigo_calren, Integer punto) throws Exception {
		try {
			Query query = em.createNativeQuery(
					"UPDATE rep_califica_renova SET aux=?1, punto=?2 WHERE idtec=?3 and codigo=?4  and factura is null");
			query.setParameter(1, "S");
			query.setParameter(2, punto);
			query.setParameter(3, idtec);
			query.setParameter(4, codigo_calren);
			query.executeUpdate();
		} catch (Exception e) {
			System.out.println(e);
		}

	}

// ACTUALIZA SECUENCIA EN FACTURA EN REPTECNICOS
	@Transactional
	public void actualizaCalRenRepTecnico(Integer idtec, String numeroFactura, Integer codigo_calren)
			throws Exception {

		try {
			Query query = em.createNativeQuery("UPDATE rep_califica_renova SET factura = ?1 "
					+ "WHERE idtec = ?2 " + "and codigo = ?3 " + "and factura is null");
			query.setParameter(1, numeroFactura);
			query.setParameter(2, idtec);
			query.setParameter(3, codigo_calren);
			query.executeUpdate();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

///////////////////////////// FIN REPRESENTANTES TECNICOS
	
}

