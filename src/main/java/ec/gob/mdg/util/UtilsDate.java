package ec.gob.mdg.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;

import com.ibm.icu.text.SimpleDateFormat;

public class UtilsDate {

	public static Timestamp fechaActual() {
		return new Timestamp(new Date().getTime());
	}

	public static Date fechaActualDate() {
		return new Date(new Date().getTime());
	}
	
	public static String FormatoFecha(String fecha) {
		Date fecharesul = null;
		String fechaEntrega = null;
		SimpleDateFormat f = new SimpleDateFormat("dd/mm/yyyy"); 
		try {
			fecharesul=f.parse(fecha);
			fechaEntrega=f.format(fecharesul);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fechaEntrega;
	}
		
	public static Date fechaFormatoDate(Date fecha) {
		return fechaFormatoDate(fechaFormatoString(fecha));
	}
	
	public static Date fechaFormatoDate(String fecha) {
		try {
			return formatoFecha.parse(fecha);
		} catch (ParseException e) {
			return new Date();
		}
	}
	
	public static String fechaFormatoString(Date fecha) {
		return formatoFecha.format(fecha);
	}
	
	public static Date fechaFormatoDate(String fecha, String formato) {
		try {
			return new SimpleDateFormat(formato).parse(fecha);
		} catch (ParseException e) {
			return null;
		}
	}
	
	public static String fechaFormatoString(Date fecha, String formato) {
		try {
			return new SimpleDateFormat(formato).format(fecha);
		} catch (Exception e) {
			return null;
		}
	}
	
	private static final SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");


}
