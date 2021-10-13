package ec.gob.mdg.service;

import ec.gob.mdg.model.Cliente;

public interface IClienteService extends IService<Cliente>{
	Cliente ClientePorCiParametro(String ci);
}
