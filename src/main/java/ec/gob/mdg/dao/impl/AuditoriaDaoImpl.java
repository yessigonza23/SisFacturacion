package ec.gob.mdg.dao.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.dao.IAuditoriaDAO;
import ec.gob.mdg.model.Auditoria;

@Stateless
public class AuditoriaDaoImpl implements IAuditoriaDAO, Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;
	
	@Override
	public Integer registrar(Auditoria t) throws Exception {
		try {
			em.persist(t);
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e.getMessage());
		}
		return t.getId();
	}

	@Override
	public Integer modificar(Auditoria t) throws Exception {
		em.merge(t);
		return t.getId();
	}

	@Override
	public Integer eliminar(Auditoria t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Auditoria> listar() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Auditoria listarPorId(Auditoria t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer insertaModificacion(String nombreTabla, String nombreCampo, String operacion, String usuario,
			Date fecha, String valorActual, String valorAnterior, Integer claveprimaria) {
			try {
				Query query = em.createNativeQuery("insert into financiero.auditoria(nombretabla,nombrecampo,operacion,usuario,fecha,valoractual,valoranterior,claveprimaria) values(?1,?2,?3,?4,?5,?6,?7,?8) ");
				query.setParameter(1, nombreTabla);
				query.setParameter(2, nombreCampo);
				query.setParameter(3, operacion);
				query.setParameter(4, usuario);
				query.setParameter(5, fecha);
				query.setParameter(6, valorActual);
				query.setParameter(7, valorAnterior);
				query.setParameter(8, claveprimaria);
				query.executeUpdate();
			} catch (Exception e) {
				// TODO: handle exception
			}
		
		return 1;
	}

}
