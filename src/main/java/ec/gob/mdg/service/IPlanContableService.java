package ec.gob.mdg.service;

import ec.gob.mdg.model.PlanContable;

public interface IPlanContableService extends IService<PlanContable> {
	// CARGAR PLAN CONTABLE SEGUN EL CODIGO CONTABLE
	PlanContable muestraPlanContable(String codigo_contable);
	
	// CARGAR PLAN CONTABLE SEGUN EL ID DEL PLAN CONTABLE
		PlanContable cargaPlanContable(Integer id_plancontable);
}
