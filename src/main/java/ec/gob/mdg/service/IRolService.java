package ec.gob.mdg.service;

import java.util.List;

import ec.gob.mdg.model.Rol;
import ec.gob.mdg.model.Usuario;

public interface IRolService extends IService<Rol>{

	Integer asignar(Usuario us, List<Rol> roles);

	///MOSTRAR DATOS DEL ROL POR EL ID_ROL
	Rol mostrarRolPorId(Integer idRol);
}
