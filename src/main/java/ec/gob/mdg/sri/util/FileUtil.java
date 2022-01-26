package ec.gob.mdg.sri.util;

import java.io.FileOutputStream;

import ec.gob.mdg.model.Comprobante;
import ec.gob.mdg.util.UtilsArchivos;

public class FileUtil {        
    
    public static void writeXml(Comprobante c, byte[] xmlSigned){
        try {
        	        	
        	String pathxml = UtilsArchivos.getRutaGenerados();        	
            String file = pathxml + c.getTipocomprobante()+ "_" + c.getPuntoRecaudacion().getId() + "_" + c.getNumero() + ".xml";              
            FileOutputStream fo = new FileOutputStream(file);
            fo.write(xmlSigned);
            fo.close(); 
        } catch (Exception e) {
        }
    }
    
    public static void writeSignedXml(Comprobante c, byte[] xmlSigned){
        try {        	
        	String pathFirmados = UtilsArchivos.getRutaFirmados();        	
            String file =pathFirmados + c.getTipocomprobante()+ "_" + c.getPuntoRecaudacion().getId() +  "_" + c.getNumero() + ".xml";              
            FileOutputStream fo = new FileOutputStream(file);
            fo.write(xmlSigned);
            fo.close(); 
        } catch (Exception e) {
        }
    }
    
    public static void writeSignedAuth(Comprobante c, byte[] xmlAut){
        try {
        	String pathAutorizados = UtilsArchivos.getRutaAutorizados();
            String file = pathAutorizados + c.getTipocomprobante()+ "_" + c.getPuntoRecaudacion().getId() +  "_" + c.getNumero() + ".xml";
            FileOutputStream fo = new FileOutputStream(file);
            fo.write(xmlAut);
            fo.close(); 
        } catch (Exception e) {
        }
    }
    
}
