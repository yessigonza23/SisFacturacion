package ec.gob.mdg.util;

public class ValorMod11 {

	public static Integer mod11(String claveacceso) {
	
		Integer i=0;
				
		i=claveacceso.length();
		Integer multiplo=2;
		Integer suma=0;
		Integer ClaveAccesoNumero=0;
		String parte;
		
		
		while(i>=1) {
			parte = claveacceso.substring(i-1, (i-1)+1);
			ClaveAccesoNumero = Integer.parseInt(parte);
			suma = suma + ClaveAccesoNumero  * multiplo;
			i = i - 1;
			multiplo = multiplo +1;
			
			if(multiplo == 8 ) {
				multiplo=2;
			}
		}
		Integer digito = 11 - (suma % 11) ;
		
		if(digito == 11) {
			digito = 0;
		}
		if(digito == 10) {
			digito = 1;
		}
		
		return digito;
		
	}
	
}
