package ec.gob.mdg.service;

import java.util.Date;
import java.util.List;

import ec.gob.mdg.model.Cliente;
import ec.gob.mdg.model.Comprobante;
import ec.gob.mdg.model.PuntoRecaudacion;
import ec.gob.mdg.model.UsuarioPunto;

public interface IComprobanteService extends IService<Comprobante> {
	/// lista de comprobantes para consulta de comprobantes sin cierre
	List<Comprobante> listarComprPorPto(PuntoRecaudacion p);

	// xml
	Comprobante comprobantePorPtoPorId(Integer id_punto, Integer id_comprobante);

	Comprobante comprobantePorPtoPorIdNotas(Integer id_punto, Integer id_comprobante);

	// Consulta de comprobantes a detalle luego de listarlos
	// Facturas
	Comprobante listarComprobantePorId(Integer id_comprobante);

	// Notas de cr�dito
	Comprobante listarComprobanteNotaPorId(Integer id_comprobante);

	// Metodos para consulta de comprobantes por cliente
	List<Comprobante> comprobantePorCliente(Cliente c);

	List<Comprobante> listarNotasCreditoPorCliente(Cliente c);

	/// Metodo para listar comprobantes en consulta por punto de recaudacion
	List<Comprobante> listarCompPorPtoCon(PuntoRecaudacion p);

	List<Comprobante> listarCompNotasPorPtoCon(PuntoRecaudacion p);

	// METODO PARA LISTAR COMPROBANTES POR ID DEL PUNTO DE RECAUDACION
	List<Comprobante> listarCompPorIdPto(Integer id_punto);

	// Lista de comprobantes por fecha de inicio y fin TODAS
	List<Comprobante> listarComprobantePorFechas(Date fecha_inicio, Date fecha_fin, Integer id_punto, String tipo);

	// Lista de comprobantes por fecha de inicio y fin, punto y tipo de comprobante
	// SIN AUTORIZACI�N
	List<Comprobante> listarComprobantePorFechasSinAutor(Date fecha_inicio, Date fecha_fin, Integer id_punto,
			String tipo);

	// Listar comprobantes por id de cierre
	List<Comprobante> listarComprobantePorIdCierre(Integer id_cierre);

	// Validar en comprobantes el cierre de caja
	Integer validaCierre(UsuarioPunto up);

	// Listar comprobantes no autorizados
	List<Comprobante> listarNoAutorizadasPorPunto(Integer id_punto);

	// LISTA DE CLIENTES SI TIENEN UN COMPROBANTE
	List<Cliente> listarClientesComprobantes(String tipoComprobante);

	// METODO PARA LISTAR COMPROBANTES POR ID DEL PUNTO DE RECAUDACION PARA NOTAS DE
	// CREDITO
	List<Comprobante> listarCompPorIdPtoNotas(Integer id_punto);
}
