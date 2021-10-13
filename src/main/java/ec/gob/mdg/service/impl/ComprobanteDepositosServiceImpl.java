package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import ec.gob.mdg.dao.IComprobanteDepositosDAO;
import ec.gob.mdg.model.ComprobanteDepositos;
import ec.gob.mdg.service.IComprobanteDepositosService;

@Named
public class ComprobanteDepositosServiceImpl implements IComprobanteDepositosService, Serializable {

	private static final long serialVersionUID = -4656724549789651589L;
	
	@EJB
	private IComprobanteDepositosDAO dao;
	
	@Override
	public Integer registrar(ComprobanteDepositos t) throws Exception {
		return dao.registrar(t);
	}

	@Override
	public Integer modificar(ComprobanteDepositos t) throws Exception {
		return dao.modificar(t);
	}

	@Override
	public Integer eliminar(ComprobanteDepositos t) throws Exception {
		return dao.eliminar(t);
	}

	@Override
	public List<ComprobanteDepositos> listar() throws Exception {
		return dao.listar();
	}

	@Override
	public ComprobanteDepositos listarPorId(ComprobanteDepositos t) throws Exception {
		return dao.listarPorId(t);
	}

	@Override
	public List<ComprobanteDepositos> listarComDepPorIdComp(Integer c) {
		return dao.listarComDepPorIdComp(c);
	}

	@Override
	public List<ComprobanteDepositos> listarComDepPorNumeroDeposiro(String d) {
		return dao.listarComDepPorNumeroDeposito(d);
	}

	@Override
	public Integer eliminarComprobanteDeposito(Integer id) {
		// TODO Auto-generated method stub
		return dao.eliminarComprobanteDeposito(id);
	}

	@Override
	public ComprobanteDepositos mostrarComprobanteDepositoPorId(Integer id_comprobanteDeposito) {
		return dao.mostrarComprobanteDepositoPorId(id_comprobanteDeposito);
	}

	

}
