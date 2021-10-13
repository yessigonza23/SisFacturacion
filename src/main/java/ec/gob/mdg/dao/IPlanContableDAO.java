package ec.gob.mdg.dao;

import javax.ejb.Local;

import ec.gob.mdg.model.PlanContable;

@Local
public interface IPlanContableDAO extends ICRUD<PlanContable> {
	// CARGAR PLAN CONTABLE SEGUN EL CODIGO CONTABLE
	PlanContable muestraPlanContable(String codigo_contable);
	
	// CARGAR PLAN CONTABLE SEGUN EL ID DEL PLAN CONTABLE
	PlanContable cargaPlanContable(Integer id_plancontable);
}
