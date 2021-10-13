package ec.gob.mdg.dao;

import java.util.List;

import javax.ejb.Local;

import ec.gob.mdg.model.Rol;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioRol;

@Local
public interface IRolDAO extends ICRUD<Rol> {

	Integer asignar (Usuario us, List<UsuarioRol> roles);
	
	///MOSTRAR DATOS DEL ROL POR EL ID_ROL
	Rol mostrarRolPorId(Integer idRol);
	


}
