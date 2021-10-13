package ec.gob.mdg.service;

import java.util.Date;

import ec.gob.mdg.model.Auditoria;

public interface IAuditoriaService extends IService<Auditoria> {
	Integer insertaModificacion(String nombreTabla, String nombreCampo, String operacion, String usuario, Date fechaActual,
			String valorActual, String valorAnterior, Integer claveprimaria);
}
