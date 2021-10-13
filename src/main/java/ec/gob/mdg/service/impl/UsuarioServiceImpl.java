package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import org.mindrot.jbcrypt.BCrypt;

import ec.gob.mdg.dao.IUsuarioDAO;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.service.IUsuarioService;

@Named
public class UsuarioServiceImpl implements IUsuarioService,  Serializable
{

	private static final long serialVersionUID = -3775158929845234973L;
	@EJB
	private IUsuarioDAO dao;
	
	@Override
	public Integer registrar(Usuario t) throws Exception {
		return dao.registrar(t);
	}

	@Override
	public Integer modificar(Usuario t) throws Exception {
		return dao.modificar(t);
	}

	@Override
	public Integer eliminar(Usuario t) throws Exception {
		return dao.eliminar(t);
	}

	@Override
	public List<Usuario> listar() throws Exception {
		return dao.listar();
	}

	@Override
	public Usuario listarPorId(Usuario t) throws Exception {
		return dao.listarPorId(t);
	}
	
	@Override
	public Usuario login(Usuario usu) {
		String clave = usu.getContrasena();
		String claveHash = dao.traerPassHashed(usu.getUsername());
		
		if (BCrypt.checkpw(clave, claveHash)) {
			//usu.setContrasena("");
			return dao.leerPorNombreUsuario(usu.getUsername());		
		}		
		return null;
	}

	@Override
	public Usuario mostrarUsuarioPorId(Integer id_usuario) {
		return dao.mostrarUsuarioPorId(id_usuario);
	}

	@Override
	public boolean validaUsuarioCedula(String cedula) {
		return dao.validaUsuarioCedula(cedula);
	}

	@Override
	public Usuario muestraUsuarioPorCi(String ci) {
		// TODO Auto-generated method stub
		return dao.muestraUsuarioPorCi(ci);
	}

	@Override
	public Usuario leerPorNombreUsuario(String us) {
		return dao.leerPorNombreUsuario(us);
	}

	@Override
	public boolean existeUsuario(String us) {
		return dao.existeUsuario(us);
	}

	
}
