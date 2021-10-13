package ec.gob.mdg.dao;

import javax.ejb.Local;

import ec.gob.mdg.model.Menu;

@Local
public interface IMenuDAO extends ICRUD<Menu> {

	// MOSTRAR MENU SEGUN EL ID
	Menu mostraMenu(Integer id_menu);
}
