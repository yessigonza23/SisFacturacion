package ec.gob.mdg.control.dao;

import java.util.List;

import javax.ejb.Local;

@Local
public interface ICRUD<T> {
	Integer registrar(T t) throws Exception;
	Integer modificar(T t) throws Exception;
	Integer eliminar(T t) throws Exception;
	List<T> listar() throws Exception;
	T listarPorId(T t) throws Exception; 

}
