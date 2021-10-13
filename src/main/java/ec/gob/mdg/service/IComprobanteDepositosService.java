package ec.gob.mdg.service;

import java.util.List;

import ec.gob.mdg.model.ComprobanteDepositos;

public interface IComprobanteDepositosService extends IService<ComprobanteDepositos> {
	List<ComprobanteDepositos> listarComDepPorIdComp(Integer c);

	List<ComprobanteDepositos> listarComDepPorNumeroDeposiro(String d);

	// ELIMINAR CARGA MASIVA PENDIENTE DE GENERAR COMPROBANTE
	Integer eliminarComprobanteDeposito(Integer id);

	// COMPROBANTE DEPOSITO POR ID
	ComprobanteDepositos mostrarComprobanteDepositoPorId(Integer id_comprobanteDeposito);

}
