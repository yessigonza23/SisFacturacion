package ec.gob.mdg.service;

import java.util.List;

import ec.gob.mdg.model.Menu;
import ec.gob.mdg.model.MenuRol;
import ec.gob.mdg.model.Usuario;

public interface IMenuRolService extends IService<MenuRol> {
	// LISTA EL MENU PARA CREAR POR LOS ROLES ASIGNADOS AL ROL
	List<Menu> listaMenuRolporUsuario(Usuario u);

	// LISTA EL MENU POR EL ROL ASIGNADOS
	List<MenuRol> listaMenuRolAsignados(Integer idRol);

	// LISTA EL MENU POR EL ROL PENDIENTES
	List<Menu> listaMenuPendientes(Integer idRol);

	// ELIMINAR MENU DEL ROL ASIGNADOS
	Integer eliminarMenuRol(Integer id_menu, Integer id_rol);
}
