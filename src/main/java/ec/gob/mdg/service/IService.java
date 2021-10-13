package ec.gob.mdg.service;

import java.util.List;

public interface IService<T> {
	Integer registrar(T t) throws Exception;
	Integer modificar(T t) throws Exception;
	Integer eliminar(T t) throws Exception;
	List<T> listar() throws Exception;
	T listarPorId(T t) throws Exception;
}
