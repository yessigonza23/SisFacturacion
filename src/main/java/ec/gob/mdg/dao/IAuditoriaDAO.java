package ec.gob.mdg.dao;

import java.util.Date;

import javax.ejb.Local;

import ec.gob.mdg.model.Auditoria;

@Local
public interface IAuditoriaDAO extends ICRUD<Auditoria> {
	Integer insertaModificacion(String nombreTabla, String nombreCampo, String operacion, String usuario, Date fecha,
			String valorActual, String valorAnterior, Integer claveprimaria);
}
