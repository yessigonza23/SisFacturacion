package ec.gob.mdg.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;

public abstract class Utilitario implements Serializable {

    public static final String PROPIEDADES_NAME = "messages";

    private static final long serialVersionUID = 1L;

    public Utilitario() {

        super();
    }

    public static String buscarMensaje(String mensaje) {
    	
        FacesContext contexto = FacesContext.getCurrentInstance();
        ResourceBundle cadenaTexto = contexto.getApplication().getResourceBundle(contexto, PROPIEDADES_NAME);
        return cadenaTexto.getString(mensaje);
    }
    
    public static String buscarMensaje(String mensaje,
                                       Object... parametros) {

        FacesContext contexto = FacesContext.getCurrentInstance();
        ResourceBundle cadenaTexto = contexto.getApplication().getResourceBundle(contexto, PROPIEDADES_NAME);
        String mensajePreProceso = cadenaTexto.getString(mensaje);
        String mensajeFinal = MessageFormat.format(mensajePreProceso, parametros);
        return mensajeFinal;
    }

    public static String getRequestParameter(String name) {
        return FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(name);
    }


    public static HttpSession getSession(final boolean crearNueva) {
        HttpSession resultado = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(crearNueva);
        if (resultado == null) { throw new IllegalStateException("No existe sesión iniciada"); }
        return resultado;
    }

 
    public static void setSessionAttribute(final String nombreAtributo,
                                           final Object valor) {

        HttpSession sesion = getSession(false);
        sesion.setAttribute(nombreAtributo, valor);
    }


    public static Object getSessionAttribute(final String nombreAtributo) {

        HttpSession sesion = getSession(true);
        return sesion.getAttribute(nombreAtributo);
    }

    public static void setFlashScopeAttribute(@NotNull final String nombreAtributo,
                                              final Object valor) {
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put(nombreAtributo, valor);
    }

    public static Object getFlashScopeAttribute(@NotNull final String nombreAtributo) {
        return FacesContext.getCurrentInstance().getExternalContext().getFlash().get(nombreAtributo);
    }

  
    public static void removeSessionAtribute(final String nombreAtributo) {
        HttpSession sesion = getSession(false);
        sesion.removeAttribute(nombreAtributo);
    }


    public static void mostrarMensajeError(String mensaje) {
    	FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "¡Error!", mensaje));
    }
    
    public static void mostrarMensajeError2(@NotNull String mensaje) {
    	FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "¡Error! " + mensaje, mensaje));
    }

  
    public static void mostrarMensajeInformacion(String mensaje) {
    	FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", mensaje));
    }

    public static void mostrarMensajeInformacion(String id,
                                                 String mensaje) {
        FacesContext.getCurrentInstance().addMessage(id, new FacesMessage(FacesMessage.SEVERITY_INFO, "Info", mensaje));
    }


    public static void mostrarMensajeAdvertencia(String mensaje) {
    	FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", mensaje));
    }


    public static void mostrarMensajeConfirmacion(String mensaje) {
    	FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Confirmaci�n", mensaje));
    }


    public static void mostrarMensajeConfirmacion(String id,
                                                  String mensaje) {
        FacesContext.getCurrentInstance().addMessage(id, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Confirmaci�n", mensaje));
    }

   

    public static String hashMapToString(String prefijo,
                                         Map<String, Object> map) {
        if (prefijo == null) {
            prefijo = "";
        }
        StringBuffer sb = new StringBuffer(prefijo + "Map:");
        for (Entry<String, Object> entry : map.entrySet()) {
            Object key = entry.getKey();
            Object value = entry.getValue();
            sb.append("\n    " + prefijo + key + ": " + value);
        }
        return sb.toString();
    }

    public static boolean cadenaVacia(String s) {

        return (s == null) || (s != null && s.trim().isEmpty());
    }

    public static String concatenarCon(String sep,
                                       List<String> listadoAConcatenar) {

        if (sep == null) {
            sep = "";
        }
        StringBuffer resultado = new StringBuffer();
        List<String> listado = new ArrayList<String>();
        for (String c : listadoAConcatenar) {
            if (!Utilitario.cadenaVacia(c)) {
                listado.add(c);
            }
        }
        if (!listado.isEmpty()) {
            resultado.append(listado.get(0).toString());
        }
        for (int i = 1; i < listado.size(); i++) {
            resultado.append(sep);
            resultado.append(listado.get(i).toString());
        }
        return resultado.toString();
    }


    public byte[] fileToByteArray(File file) {

        FileInputStream fileInputStream = null;
        byte[] bFile = new byte[(int) file.length()];
        try {
            // convert file into array of bytes
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            fileInputStream.close();
            for (@SuppressWarnings("unused") byte element : bFile) {
                // //System.out.print((char) element);
            }
            return bFile;
        }
        catch (Exception e) {
            // Impresion.deExcepcion(e);
            return null;
        }
    }


    public static String getContextPath() {

        ServletContext c = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        return c.getRealPath("");
    }

    public static boolean cumpleConLaNormaDeClaves(String nuevaClave) {

        if (cadenaVacia(nuevaClave)) { 
        	return false; 
        }
        return true;
    }

    public static String obtenerMacAddress() {

        InetAddress ip;
        StringBuilder sb = new StringBuilder();
        try {
            ip = InetAddress.getLocalHost();
            NetworkInterface network = NetworkInterface.getByInetAddress(ip);
            byte[] mac = network.getHardwareAddress();
            for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
            }
        }
        catch (UnknownHostException e) {
        }
        catch (SocketException e) {
        }
        return sb.toString();
    }
    
    

}
