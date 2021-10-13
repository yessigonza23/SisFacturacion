package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import ec.gob.mdg.dao.IUsuarioPuntoDAO;
import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.service.IUsuarioPuntoService;

@Named
public class UsuarioPuntoServiceImpl implements IUsuarioPuntoService, Serializable {

	private static final long serialVersionUID = -6772788792495640618L;
	@EJB
	private IUsuarioPuntoDAO dao;

	@Override
	public Integer registrar(UsuarioPunto t) throws Exception {
		return dao.registrar(t);
	}

	@Override
	public Integer modificar(UsuarioPunto t) throws Exception {
		return dao.modificar(t);
	}

	@Override
	public Integer eliminar(UsuarioPunto t) throws Exception {
		return dao.eliminar(t);
	}

	@Override
	public List<UsuarioPunto> listar() throws Exception {
		return dao.listar();
	}

	@Override
	public UsuarioPunto listarPorId(UsuarioPunto t) throws Exception {
		return dao.listarPorId(t);
	}

	@Override
	public UsuarioPunto listarUsuarioPuntoPorIdLogueado(Usuario usu) {
		return dao.listarUsuarioPuntoPorIdLogueado(usu);
	}

	@Override
	public UsuarioPunto mostrarUsuarioPuntoPorIdUsuario(Integer id_usuario) {
		return dao.mostrarUsuarioPuntoPorIdUsuario(id_usuario);
	}

	@Override
	public List<UsuarioPunto> listarUsuarioPunto(Usuario u) {
		return dao.listarUsuarioPunto(u);
	}

	@Override
	public UsuarioPunto usuarioPuntoPorId(Integer id_usuariopunto) {
		return dao.usuarioPuntoPorId(id_usuariopunto);
	}


}
