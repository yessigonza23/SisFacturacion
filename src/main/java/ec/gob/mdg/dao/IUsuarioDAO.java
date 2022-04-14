package ec.gob.mdg.dao;

import java.util.List;

import javax.ejb.Local;

import ec.gob.mdg.model.Usuario;

@Local
public interface IUsuarioDAO extends ICRUD<Usuario> {
	String traerPassHashed(String us);

	Usuario leerPorNombreUsuario(String us);

	// MOSTRAR USUARIO PARA CONSULTAR EN LA VISTA DE USUARIO PUNTO
	Usuario mostrarUsuarioPorId(Integer id_usuario);

	// VALIDAR SI EL USUARIO EXISTE CON EL NUMERO DE CEDULA
	boolean validaUsuarioCedula(String cedula);

	// MUESTRA USUARIO POR CEDULA, PARA RECUPERAR CONTRASE�A
	Usuario muestraUsuarioPorCi(String ci);
	
	//EXISTENCIA DE USUARIO
	boolean existeUsuario(String us);
	
	//LISTA ANALISTAS
	List<Usuario> listarAnalistas();
}
