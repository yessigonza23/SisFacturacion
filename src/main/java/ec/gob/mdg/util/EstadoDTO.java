package ec.gob.mdg.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

@Named
@ApplicationScoped
public class EstadoDTO {

	private List<String> estados;
	
	public EstadoDTO() {
		String[] arreglo = {"A","L"}; 
		
		this.estados= new ArrayList<>(Arrays.asList(arreglo));
		
	}

	public List<String> getEstados() {
		return estados;
	}	
}
