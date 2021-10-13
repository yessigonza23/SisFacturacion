package ec.gob.mdg.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@SuppressWarnings("rawtypes")
@FacesConverter("tipoTransaccion")
public class TipoTransaccion implements Converter{
	@Override
	public Object getAsObject(FacesContext context, UIComponent component, String value) {
		return value;
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, Object value) {
		String tipoTransaccion = "";	
		
		if (value != null) {
			tipoTransaccion = (String) value;
			switch (tipoTransaccion) {
				case "I":
					tipoTransaccion = "Interbancario";
					break;
				case "D":
					tipoTransaccion = "Depósito";
					break;
				case "T":
					tipoTransaccion = "Transferencia";
					break;	
			}
		}
		return tipoTransaccion;
	}

}