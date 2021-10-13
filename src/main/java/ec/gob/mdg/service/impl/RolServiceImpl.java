package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import ec.gob.mdg.dao.IRolDAO;
import ec.gob.mdg.model.Rol;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioRol;
import ec.gob.mdg.service.IRolService;

@Named
public class RolServiceImpl implements IRolService, Serializable {

	private static final long serialVersionUID = 4162683462035721467L;
	@EJB
	private IRolDAO dao;
	
	@Override
	public Integer registrar(Rol t) throws Exception {
		return dao.registrar(t);
	}

	@Override
	public Integer modificar(Rol t) throws Exception {
		return dao.modificar(t);
	}

	@Override
	public Integer eliminar(Rol t) throws Exception {
		return dao.eliminar(t);
	}

	@Override
	public List<Rol> listar() throws Exception {
		return dao.listar();
	}

	@Override
	public Rol listarPorId(Rol t) throws Exception {
		return dao.listarPorId(t);
	}

	@Override
	public Integer asignar(Usuario us, List<Rol> roles) {		
		
		List<UsuarioRol> usuario_roles = new ArrayList<>();
		
		roles.forEach(r -> {
			UsuarioRol ur = new UsuarioRol();
			ur.setUsuario(us);
			ur.setRol(r);
			usuario_roles.add(ur);
		});
		
		return dao.asignar(us, usuario_roles);
	}

	@Override
	public Rol mostrarRolPorId(Integer idRol) {
		return dao.mostrarRolPorId(idRol);
	}


}
