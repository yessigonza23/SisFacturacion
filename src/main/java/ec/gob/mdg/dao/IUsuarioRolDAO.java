package ec.gob.mdg.dao;

import java.util.List;

import javax.ejb.Local;

import ec.gob.mdg.model.Rol;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioRol;

@Local
public interface IUsuarioRolDAO extends ICRUD<UsuarioRol> {

	// LISTA LOS ROLES ASIGNADOS AL USUARIO
	List<UsuarioRol> listarRolesPorUsuario(Usuario us);

	// LISTA ROLES PENDIENTES
	List<Rol> listarRolesPendientes(Usuario us);

	// ELIMINAR ROLES A USUARIOS ASIGNADOS
	Integer eliminarRolUsuario(Integer id_usuario, Integer id_rol);

}
