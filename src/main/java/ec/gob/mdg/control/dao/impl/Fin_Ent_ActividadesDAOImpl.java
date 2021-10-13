package ec.gob.mdg.control.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.control.dao.IFin_Ent_ActividadesDAO;
import ec.gob.mdg.control.model.Fin_Ent_Actividades;

@Stateless
public class Fin_Ent_ActividadesDAOImpl implements IFin_Ent_ActividadesDAO, Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "controlPU")
	private EntityManager em;

	@Override
	public Integer registrar(Fin_Ent_Actividades t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer modificar(Fin_Ent_Actividades t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer eliminar(Fin_Ent_Actividades t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Fin_Ent_Actividades> listar() {
		List<Object[]> lista = new ArrayList<>();
		List<Fin_Ent_Actividades> consultas = new ArrayList<>();
		try {
			Query q = em.createNativeQuery("SELECT *  FROM Fin_Ent_Actividades "); // query de la entidad del model
			lista =  q.getResultList();
			lista.forEach(x -> {
				Fin_Ent_Actividades act = new Fin_Ent_Actividades();
				act.setNom_entidad(String.valueOf(x[0])); 
				act.setCod_entidad(Integer.parseInt(String.valueOf(x[1])));
				act.setCod_calrev_codigo(Integer.parseInt(String.valueOf(x[2])));
				act.setRuc(String.valueOf(x[3]));
				act.setActividades_calificacion(String.valueOf(x[4]));
				act.setCod_financiero(String.valueOf(x[5]));
				act.setFactura_int(String.valueOf(x[6]));
				act.setManeja_sustancias(String.valueOf(x[7]));				
				consultas.add(act);
			});
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return consultas;
	}

	@Override
	public Fin_Ent_Actividades listarPorId(Fin_Ent_Actividades t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
