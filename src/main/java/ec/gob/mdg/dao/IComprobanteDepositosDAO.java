package ec.gob.mdg.dao;

import java.util.List;

import javax.ejb.Local;

import ec.gob.mdg.model.ComprobanteDepositos;

@Local
public interface IComprobanteDepositosDAO extends ICRUD<ComprobanteDepositos> {
	List<ComprobanteDepositos> listarComDepPorIdComp(Integer c);

	List<ComprobanteDepositos> listarComDepPorNumeroDeposito(String d);

	// ELIMINAR CARGA MASIVA PENDIENTE DE GENERAR COMPROBANTE
	Integer eliminarComprobanteDeposito(Integer id);
	
	//COMPROBANTE DEPOSITO POR ID
	ComprobanteDepositos mostrarComprobanteDepositoPorId(Integer id_comprobanteDeposito);

}
