package ec.gob.mdg.service;

import ec.gob.mdg.model.Menu;

public interface IMenuService extends IService<Menu> {
	Menu mostraMenu(Integer id_menu);
}
