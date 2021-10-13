package ec.gob.mdg.service;

import java.util.List;

import javax.ejb.Local;

import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;

@Local
public interface IUsuarioPuntoService extends IService<UsuarioPunto> {

	UsuarioPunto listarUsuarioPuntoPorIdLogueado(Usuario usu);

	// MOSTRAR USUARIO PUNTO CON EL INTEGER ID_USUARIO
	UsuarioPunto mostrarUsuarioPuntoPorIdUsuario(Integer id_usuario);

	// LISTAR LOS PUNTOS ASIGNADOS POR EL USUARIOPUNTO
	List<UsuarioPunto> listarUsuarioPunto(Usuario u);

	// MOSTRAR USUARIO PUNTO CON EL INTEGER ID_USUARIO
	UsuarioPunto usuarioPuntoPorId(Integer id_usuariopunto);

}
