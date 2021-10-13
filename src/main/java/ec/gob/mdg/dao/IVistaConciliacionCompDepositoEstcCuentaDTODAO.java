package ec.gob.mdg.dao;

import java.util.List;

import javax.ejb.Local;

import ec.gob.mdg.model.VistaConciliacionCompDepositoEstcCUentaDTO;
@Local
public interface IVistaConciliacionCompDepositoEstcCuentaDTODAO extends ICRUD<VistaConciliacionCompDepositoEstcCUentaDTO> {

	//LISTAR CONSOLIDACIÓN
	List<VistaConciliacionCompDepositoEstcCUentaDTO> listarConsolidados(Integer id_punto, Integer anio,Integer mes);
}
