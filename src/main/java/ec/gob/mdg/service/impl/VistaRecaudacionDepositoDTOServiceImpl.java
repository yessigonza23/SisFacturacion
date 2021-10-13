package ec.gob.mdg.service.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Named;

import ec.gob.mdg.dao.IVistaRecaudacionDepositoDTODAO;
import ec.gob.mdg.model.VistaRecaudacionDepositoDTO;
import ec.gob.mdg.service.IVistaRecaudacioDepositoDTOService;

@Named
public class VistaRecaudacionDepositoDTOServiceImpl implements IVistaRecaudacioDepositoDTOService,Serializable {
	private static final long serialVersionUID = 1L;

	@EJB
	private IVistaRecaudacionDepositoDTODAO dao;

	@Override
	public Integer registrar(VistaRecaudacionDepositoDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer modificar(VistaRecaudacionDepositoDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer eliminar(VistaRecaudacionDepositoDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VistaRecaudacionDepositoDTO> listar() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public VistaRecaudacionDepositoDTO listarPorId(VistaRecaudacionDepositoDTO t) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<VistaRecaudacionDepositoDTO> listarRecaudacionDeposito(Date fecha_inicio, Date fecha_fin,
			Integer id_punto) {
		return dao.listarRecaudacionDeposito(fecha_inicio, fecha_fin, id_punto);
	}

	@Override
	public Integer cuentaFacturas(Date fecha_inicio, Date fecha_fin, Integer id_punto) {
		return dao.cuentaFacturas(fecha_inicio, fecha_fin, id_punto);
	}

	@Override
	public List<VistaRecaudacionDepositoDTO> listarRecaudacionDepositoNac(Date fecha_inicio, Date fecha_fin) {
		return dao.listarRecaudacionDepositoNac(fecha_inicio, fecha_fin);
	}

	@Override
	public Integer cuentaFacturasNac(Date fecha_inicio, Date fecha_fin) {
		return dao.cuentaFacturasNac(fecha_inicio, fecha_fin);
	}

	@Override
	public List<VistaRecaudacionDepositoDTO> listarConsultaBancoVsBanco(Integer id_punto, Integer anio, Integer mes) {
		return dao.listarConsultaBancoVsBanco(id_punto, anio, mes);
	}

	@Override
	public List<VistaRecaudacionDepositoDTO> listarDepositosNoBanco(Integer id_punto, Integer anio, Integer mes) {
		return dao.listarDepositosNoBanco(id_punto, anio, mes);
	}

	@Override
	public List<VistaRecaudacionDepositoDTO> listarDepositosVariasFacturas(Integer id_punto, Integer anio,
			Integer mes) {
		return dao.listarDepositosVariasFacturas(id_punto, anio, mes);
	}

	@Override
	public List<VistaRecaudacionDepositoDTO> listarEstadoSinColidar(Integer id_punto, Integer anio, Integer mes) {
		return dao.listarEstadoSinColidar(id_punto, anio, mes);
	}

	@Override
	public List<VistaRecaudacionDepositoDTO> listarVista(Integer anio,Integer mes) {
		return dao.listarVista(anio,mes);
	}

	@Override
	public List<VistaRecaudacionDepositoDTO> listarVistaAnio(Integer anio) {
		return dao.listarVistaAnio(anio);
	}

	@Override
	public List<VistaRecaudacionDepositoDTO> listarConsultaBancoVsBancoNacional(Integer anio, Integer mes) {
		return dao.listarConsultaBancoVsBancoNacional(anio, mes);
	}

	@Override
	public List<VistaRecaudacionDepositoDTO> listarDepositosNoBancoNacional(Integer anio, Integer mes) {
		return dao.listarDepositosNoBancoNacional(anio, mes);
	}



	@Override
	public List<VistaRecaudacionDepositoDTO> listarComprobantesNumDeposito(String num_deposito, Integer anio) {
		return dao.listarComprobantesNumDeposito(num_deposito, anio);
	}

	@Override
	public VistaRecaudacionDepositoDTO ComprobantesDepNumDeposito(Integer id_deposito) {
		return dao.ComprobantesDepNumDeposito(id_deposito);
	}

}
