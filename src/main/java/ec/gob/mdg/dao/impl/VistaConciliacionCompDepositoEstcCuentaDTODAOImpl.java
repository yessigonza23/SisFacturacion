package ec.gob.mdg.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ec.gob.mdg.dao.IVistaConciliacionCompDepositoEstcCuentaDTODAO;
import ec.gob.mdg.model.VistaConciliacionCompDepositoEstcCUentaDTO;

@Stateless
public class VistaConciliacionCompDepositoEstcCuentaDTODAOImpl implements IVistaConciliacionCompDepositoEstcCuentaDTODAO,Serializable {

	private static final long serialVersionUID = 1L;

	@PersistenceContext(unitName = "finanPU")
	private EntityManager em;
	
	@Override
	public Integer registrar(VistaConciliacionCompDepositoEstcCUentaDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer modificar(VistaConciliacionCompDepositoEstcCUentaDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer eliminar(VistaConciliacionCompDepositoEstcCUentaDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VistaConciliacionCompDepositoEstcCUentaDTO> listar() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VistaConciliacionCompDepositoEstcCUentaDTO listarPorId(VistaConciliacionCompDepositoEstcCUentaDTO t)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VistaConciliacionCompDepositoEstcCUentaDTO> listarConsolidados(Integer id_punto, Integer anio,
			Integer mes) {
		
		String anioS = String.valueOf(anio);
		String mesS;
		if (mes < 10) {
			mesS = "0" + mes;
		} else {
			mesS = String.valueOf(mes);
		}
		List<Object[]> lista = new ArrayList<>();
		List<VistaConciliacionCompDepositoEstcCUentaDTO> listaFin = new ArrayList<>();
		
		try {
			Query q = em.createNativeQuery("SELECT v.comp_numero,v.comp_fechaemision,comp_valor,comp_estado,"
					+ " v.deposito_numero,v.deposito_fecha,v.deposito_valor,v.consdepositos_tipoconciliaciondesc ,v.punto_nombre,v.cliente_nombre,v.cliente_ci, "
					+ " v.estcuenta_valor,v.estcuenta_saldo"
					+ " FROM financiero.Vista_Conciliacion_CompDeposito_EstCUenta v WHERE v.punto_id=?1 and  v.comp_anio=?2 AND v.comp_mes=?3");
			q.setParameter(1, id_punto);
			q.setParameter(2, anioS);
			q.setParameter(3, mesS);
						
			lista = q.getResultList();
			lista.forEach(x -> {
				VistaConciliacionCompDepositoEstcCUentaDTO v = new VistaConciliacionCompDepositoEstcCUentaDTO();
				v.setComp_numero(Integer.parseInt(String.valueOf(x[0])));
				v.setComp_fechaemision(String.valueOf(x[1]));
				v.setComp_valor(Double.parseDouble(String.valueOf(x[2])));
				v.setComp_estado((String.valueOf(x[3])));
		        v.setDeposito_numero((String.valueOf(x[4])));
		        v.setDeposito_fecha((String.valueOf(x[5])));
		        v.setDeposito_valor(Double.parseDouble(String.valueOf(x[6])));
		        v.setConsdepositos_tipoconciliaciondesc(String.valueOf(x[7]));
		        v.setPunto_nombre(String.valueOf(x[8]));
		        v.setCliente_nombre(String.valueOf(x[9]));
		        v.setCliente_ci(String.valueOf(x[10]));
		        v.setEstadocuenta_valor(Double.parseDouble(String.valueOf(x[11])));
		        v.setEstadocuenta_saldo(Double.parseDouble(String.valueOf(x[12])));
				listaFin.add(v);
			});
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return listaFin;
	}

}
