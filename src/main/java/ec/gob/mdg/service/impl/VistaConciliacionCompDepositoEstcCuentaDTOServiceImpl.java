package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import ec.gob.mdg.dao.IVistaConciliacionCompDepositoEstcCuentaDTODAO;
import ec.gob.mdg.model.VistaConciliacionCompDepositoEstcCUentaDTO;
import ec.gob.mdg.service.IVistaConciliacionCompDepositoEstcCuentaDTOService;

@Named
public class VistaConciliacionCompDepositoEstcCuentaDTOServiceImpl
		implements IVistaConciliacionCompDepositoEstcCuentaDTOService, Serializable {

	private static final long serialVersionUID = 1L;
	
	@EJB
	private IVistaConciliacionCompDepositoEstcCuentaDTODAO dao;

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

	@Override
	public List<VistaConciliacionCompDepositoEstcCUentaDTO> listarConsolidados(Integer id_punto, Integer anio,
			Integer mes) {
		return dao.listarConsolidados(id_punto, anio, mes);
	}

}
