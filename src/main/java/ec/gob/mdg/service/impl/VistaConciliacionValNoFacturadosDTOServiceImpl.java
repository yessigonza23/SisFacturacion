package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;
import ec.gob.mdg.dao.IVistaConciliacionValNoFacturadosDTODAO;
import ec.gob.mdg.model.VistaConciliacionValNoFacturadosDTO;
import ec.gob.mdg.service.IVistaConciliacionValNoFacturadosDTOService;

@Named
public class VistaConciliacionValNoFacturadosDTOServiceImpl implements IVistaConciliacionValNoFacturadosDTOService, Serializable {

	private static final long serialVersionUID = 1L;

	@EJB
	private IVistaConciliacionValNoFacturadosDTODAO dao;

	@Override
	public Integer registrar(VistaConciliacionValNoFacturadosDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer modificar(VistaConciliacionValNoFacturadosDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer eliminar(VistaConciliacionValNoFacturadosDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VistaConciliacionValNoFacturadosDTO> listar() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VistaConciliacionValNoFacturadosDTO listarPorId(VistaConciliacionValNoFacturadosDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VistaConciliacionValNoFacturadosDTO> listarValoresNoFacturados(Integer anio,
			Integer mes) {
		return dao.listarValoresNoFacturados(anio, mes);
	}

	
	
}
