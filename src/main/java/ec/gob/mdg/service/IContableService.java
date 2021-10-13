package ec.gob.mdg.service;

import java.util.Date;
import java.util.List;

import ec.gob.mdg.model.Contable;

public interface IContableService extends IService<Contable> {
	// Lista de contable por punto recaudacion, fecha de inicio y fin
	List<Contable> listarContablePorFechas(Date fecha_inicio, Date fecha_fin, Integer id_punto);

	// MOSTRAR CONTABLE POR ID
	Contable mostrarContablePorId(Integer id_contable);
}
