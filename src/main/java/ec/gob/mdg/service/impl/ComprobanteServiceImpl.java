package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import ec.gob.mdg.dao.IComprobanteDAO;
import ec.gob.mdg.model.Cliente;
import ec.gob.mdg.model.Comprobante;
import ec.gob.mdg.model.PuntoRecaudacion;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.service.IComprobanteService;

@Named
public class ComprobanteServiceImpl implements IComprobanteService, Serializable{

	private static final long serialVersionUID = 1L;
	
	@EJB
	private IComprobanteDAO dao;
	
	@Override
	public Integer registrar(Comprobante t) throws Exception {
		return dao.registrar(t);
		}

	@Override
	public Integer modificar(Comprobante t) throws Exception {
		return dao.modificar(t);
		}
	@Override
	public Integer eliminar(Comprobante t) throws Exception {
		return dao.eliminar(t);
	}

	@Override
	public List<Comprobante> listar() throws Exception {
		return dao.listar();
		}

	@Override
	public Comprobante listarPorId(Comprobante t) throws Exception {
		return dao.listarPorId(t);
    }

	@Override
	public List<Comprobante> listarComprPorPto(PuntoRecaudacion p) {
		return dao.listarCompPorPto(p);
	}

	@Override
	public Comprobante comprobantePorPtoPorId(Integer id_punto, Integer id_comprobante) {
		return dao.comprobantePorPtoPorId(id_punto, id_comprobante);
	}

	@Override
	public List<Comprobante> comprobantePorCliente(Cliente c) {
		return dao.comprobantePorCliente(c);
	}


	@Override
	public Comprobante listarComprobantePorId(Integer id_comprobante) {
		
		// TODO Auto-generated method stub
		return dao.listarComprobantePorId(id_comprobante);
	}

	@Override
	public List<Comprobante> listarNotasCreditoPorCliente(Cliente c) {
		// TODO Auto-generated method stub
		return dao.listarNotasCreditoPorCliente(c);
	}

	@Override
	public List<Comprobante> listarCompPorPtoCon(PuntoRecaudacion p) {
		// TODO Auto-generated method stub
		return dao.listarCompPorPtoCon(p);
	}

	@Override
	public List<Comprobante> listarCompNotasPorPtoCon(PuntoRecaudacion p) {
		// TODO Auto-generated method stub
		return dao.listarCompNotasPorPtoCon(p);
	}

	@Override
	public Comprobante listarComprobanteNotaPorId(Integer id_comprobante) {
		return dao.listarComprobanteNotaPorId(id_comprobante);
	}

	@Override
	public List<Comprobante> listarCompPorIdPto(Integer id_punto) {
		return dao.listarCompPorIdPto(id_punto);
	}

	@Override
	public Comprobante comprobantePorPtoPorIdNotas(Integer id_punto, Integer id_comprobante) {
		return dao.comprobantePorPtoPorIdNotas(id_punto, id_comprobante);
	}

	
	@Override
	public List<Comprobante> listarComprobantePorFechas(Date fecha_inicio,Date fecha_fin,Integer id_punto,String tipo) {
		return dao.listarComprobantePorFechas(fecha_inicio,fecha_fin,id_punto,tipo);
	}

	@Override
	public List<Comprobante> listarComprobantePorIdCierre(Integer id_cierre) {
		// TODO Auto-generated method stub
		return dao.listarComprobantePorIdCierre(id_cierre);
	}

	@Override
	public Integer validaCierre(UsuarioPunto up) {
		return dao.validaCierre(up);
	}

	@Override
	public List<Comprobante> listarNoAutorizadasPorPunto(Integer id_punto) {
		return dao.listarNoAutorizadasPorPunto(id_punto);
	}

	@Override
	public List<Comprobante> listarComprobantePorFechasSinAutor(Date fecha_inicio, Date fecha_fin, Integer id_punto,
			String tipo) {
		return dao.listarComprobantePorFechasSinAutor(fecha_inicio, fecha_fin, id_punto, tipo);
	}

	@Override
	public List<Cliente> listarClientesComprobantes(String tipoComprobante) {
		return dao.listarClientesComprobantes(tipoComprobante);
	}

	@Override
	public List<Comprobante> listarCompPorIdPtoNotas(Integer id_punto) {
		return dao.listarCompPorIdPtoNotas(id_punto);
	}
	

}
