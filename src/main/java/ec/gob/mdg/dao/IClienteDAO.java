package ec.gob.mdg.dao;


import javax.ejb.Local;

import ec.gob.mdg.model.Cliente;

@Local
public interface IClienteDAO extends ICRUD<Cliente>{
	Cliente ClientePorCiParametro(String ci);
}
