package ec.gob.mdg.dao;

import java.util.List;

import javax.ejb.Local;

import ec.gob.mdg.model.CargaMasiva;

@Local
public interface ICargaMasivaDAO extends ICRUD<CargaMasiva>{
    //REGISTRAR LISTA DE LA CARGA MASIVA
	Integer registrarLista(List<CargaMasiva> t);
	
	//LISTAR CARGA PARA PAF
	List<CargaMasiva> listarCargaMasivaSinComprobante(Integer id_puntorecaudacion, String tipo);
	
	//ELIMINAR CARGA MASIVA PENDIENTE DE GENERAR COMPROBANTE
	Integer eliminarCargaMasivaPendiente(Integer id_puntorecaudacion, String tipo);
	
	//VERIFICAR CODIGO PAF QUE NO SE ENCUENTRE FACTURADO
	Integer validarCodigoPaf(String codigoPaf);
}
