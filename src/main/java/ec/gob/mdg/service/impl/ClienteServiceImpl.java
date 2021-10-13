package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import ec.gob.mdg.dao.IClienteDAO;
import ec.gob.mdg.model.Cliente;
import ec.gob.mdg.service.IClienteService;

@Named
public class ClienteServiceImpl implements IClienteService, Serializable{

	private static final long serialVersionUID = -7453077211630108029L;
	
	@EJB
	private IClienteDAO dao;

	@Override
	public Integer registrar(Cliente t) throws Exception {
		
		return dao.registrar(t);
	}

	@Override
	public Integer modificar(Cliente t) throws Exception {
		return dao.modificar(t);
	}

	@Override
	public Integer eliminar(Cliente t) throws Exception {
		return dao.eliminar(t);
	}

	@Override
	public List<Cliente> listar() throws Exception {
		return dao.listar();
	}

	@Override
	public Cliente listarPorId(Cliente t) throws Exception {
		return dao.listarPorId(t);
	}

	@Override
	public Cliente ClientePorCiParametro(String ci) {
		// TODO Auto-generated method stub
		return dao.ClientePorCiParametro(ci);
	}




	

}
