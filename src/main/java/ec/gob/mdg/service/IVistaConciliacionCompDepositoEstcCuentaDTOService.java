package ec.gob.mdg.service;

import java.util.List;

import ec.gob.mdg.model.VistaConciliacionCompDepositoEstcCUentaDTO;

public interface IVistaConciliacionCompDepositoEstcCuentaDTOService
		extends IService<VistaConciliacionCompDepositoEstcCUentaDTO> {
	// LISTAR CONSOLIDACIÓN
	List<VistaConciliacionCompDepositoEstcCUentaDTO> listarConsolidados(Integer id_punto, Integer anio, Integer mes);
}
