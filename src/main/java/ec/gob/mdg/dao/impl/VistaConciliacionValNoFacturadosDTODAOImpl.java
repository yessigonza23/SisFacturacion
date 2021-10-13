package ec.gob.mdg.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.dao.IVistaConciliacionValNoFacturadosDTODAO;
import ec.gob.mdg.model.VistaConciliacionValNoFacturadosDTO;

@Stateless
public class VistaConciliacionValNoFacturadosDTODAOImpl
		implements IVistaConciliacionValNoFacturadosDTODAO, Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;

	@Override
	public Integer registrar(VistaConciliacionValNoFacturadosDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer modificar(VistaConciliacionValNoFacturadosDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer eliminar(VistaConciliacionValNoFacturadosDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VistaConciliacionValNoFacturadosDTO> listar() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VistaConciliacionValNoFacturadosDTO listarPorId(VistaConciliacionValNoFacturadosDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VistaConciliacionValNoFacturadosDTO> listarValoresNoFacturados(Integer anio,
			Integer mes) {
		
		List<Object[]> lista = new ArrayList<>();
		List<VistaConciliacionValNoFacturadosDTO> listaFin = new ArrayList<>();
		
		try {
			Query q = em.createNativeQuery("SELECT v.numtransaccion,v.fecha,v.valor,v.respuesta,v.cliente,v.provincia,"
					+ " v.servicio,v.ruc,v.anio,v.mes "
					+ " FROM financiero.vista_conciliacionvalnofacturados v WHERE  v.anio=?1 AND v.mes=?2");
			q.setParameter(1, anio);
			q.setParameter(2, mes);
						
			lista = q.getResultList();
			lista.forEach(x -> {
				VistaConciliacionValNoFacturadosDTO v = new VistaConciliacionValNoFacturadosDTO();
				v.setNumtransaccion(String.valueOf(x[0]));
				v.setFecha(String.valueOf(x[1]));
				v.setValor(Double.parseDouble(String.valueOf(x[2])));
				v.setRespuesta(String.valueOf(x[3]));
				v.setCliente(String.valueOf(x[4]));
				v.setProvincia(String.valueOf(x[5]));
				v.setServicio(String.valueOf(x[6]));
				v.setRuc(String.valueOf(x[7]));
				
				v.setAnio(Integer.parseInt(String.valueOf(x[8])));
				v.setMes(Integer.parseInt(String.valueOf(x[9])));
				listaFin.add(v);
			});
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return listaFin;
	}

}
