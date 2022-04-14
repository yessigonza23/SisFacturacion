package ec.gob.mdg.service;

import java.util.List;

import ec.gob.mdg.model.Usuario;

public interface IUsuarioService extends IService<Usuario> {
	Usuario login(Usuario usu);

	Usuario leerPorNombreUsuario(String us);

	// MOSTRAR USUARIO PARA CONSULTAR EN LA VISTA DE USUARIO PUNTO
	Usuario mostrarUsuarioPorId(Integer id_usuario);

	// VALIDAR SI EL USUARIO EXISTE CON EL NUMERO DE CEDULA
	boolean validaUsuarioCedula(String cedula);

	// MUESTRA USUARIO POR CEDULA, PARA RECUPERAR CONTRASE�A
	Usuario muestraUsuarioPorCi(String ci);

	// EXISTENCIA DE USUARIO
	boolean existeUsuario(String us);
	
	//LISTA ANALISTAS
		List<Usuario> listarAnalistas();
}
