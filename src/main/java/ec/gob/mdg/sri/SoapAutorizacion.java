package ec.gob.mdg.sri;

import ec.gob.mdg.sri.util.XML_Utilidades;

public class SoapAutorizacion {

	public static XML_Utilidades xml_utilidades = new XML_Utilidades();

	public String formatSendPost(String codAcceso) {

		String xml = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:ec='http://ec.gob.sri.ws.autorizacion'>"
				+ "<soapenv:Header/>" + "<soapenv:Body>" + "<ec:autorizacionComprobante>" + "<claveAccesoComprobante>"
				+ codAcceso + "</claveAccesoComprobante>" + "</ec:autorizacionComprobante>" + "</soapenv:Body>"
				+ "</soapenv:Envelope>";

		return xml;
	}

//	public void getAutorizacion(Document doc, Comprobante comprobante) throws XPathExpressionException {
//
//		System.out.println("imprimir document " +  doc);
//		String pathLevelAutorizacon = "C:\\RespuestaAutorizacionComprobante\\autorizaciones\\autorizacion\\";
//		String pathLevelMensajes = "C:\\RespuestaAutorizacionComprobante\\autorizaciones\\autorizacion\\mensajes\\mensaje\\";
//
//		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
//
//		String estado = xml_utilidades.getLastNode(pathLevelAutorizacon, "estado", doc);
//
//		if (estado.equals("AUTORIZADO")) {
//			System.out.println("entra al AUTORIZADO");
//			try {
//				String fa = xml_utilidades.getLastNode(pathLevelAutorizacon, "fechaAutorizacion", doc);
//				String numero = comprobante.getClaveacceso();
//
//				String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" + "\n"
//						+ xml_utilidades.getNodeXml("RespuestaAutorizacionComprobante", doc);
//				FileUtil.writeSignedAuth(comprobante, xml.getBytes());
//
//			} catch (Exception e) {
//				e.printStackTrace();
//			} 
////	           
//		} else if (estado.equals("NO AUTORIZADO")) {
//
//			System.out.println("ENTRA A NO AUTORIZADO....");
//			Mensaje m = new Mensaje();
//		}
//
//	}

//	public boolean getRequestSoap(String urlWebServices, String method, String host, String getEncodeXML, Proxy proxy,
//			Comprobante comprobante) throws IOException {
//
//		try {
//			URL oURL = new URL(urlWebServices);
//			HttpURLConnection con = (HttpURLConnection) oURL.openConnection(proxy);
//			con.setDoOutput(true);
//			con.setRequestMethod(method);
//			con.setRequestProperty("Content-type", "text/xml; charset=utf-8");
//			con.setRequestProperty("SOAPAction", "");
//			con.setRequestProperty("Host", host);
//			OutputStream reqStreamOut = con.getOutputStream();
//			
//			reqStreamOut.write(getEncodeXML.getBytes());
//			
//			java.io.BufferedReader rd = new java.io.BufferedReader(
//					new java.io.InputStreamReader(con.getInputStream(), "UTF8"));
//
//			String line = "";
//			StringBuilder sb = new StringBuilder();
//
//			while ((line = rd.readLine()) != null) {
//				sb.append(line);
//			}
//
//			System.out.println("Respuesta: " + sb.toString().getBytes());
//	
//				//String a = "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><ns2:autorizacionComprobanteResponse xmlns:ns2=\"http://ec.gob.sri.ws.autorizacion\"><RespuestaAutorizacionComprobante><claveAccesoConsultada>1006202101176000066000111730030000115031234567810</claveAccesoConsultada><autorizaciones><autorizacion><estado>EN PROCESO</estado></autorizacion></autorizaciones></RespuestaAutorizacionComprobante></ns2:autorizacionComprobanteResponse></soap:Body></soap:Envelope>";
//		
//			Document doc = xml_utilidades.convertStringToDocument(sb.toString());
//			
//			System.out.println("document autorizar: " + doc);
//
//			//System.out.println("regresa doc " + xml_utilidades.convertStringToDocument(sb.toString()));
//
//			//this.getAutorizacion(xml_utilidades.convertStringToDocument(sb.toString()), comprobante);
//			this.getAutorizacion(doc, comprobante);
//			
//			System.out.println("vuelve get autorizacion");
//			con.disconnect();
//
//			return true;
//
//		} catch (Exception ex) {
//			System.out.println(ex.getMessage());
//		}
//		return false;
//	}

}
