package ec.gob.mdg.sri;

import java.io.Serializable;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import ec.gob.mdg.sri.util.XML_Utilidades;
@Named
@ViewScoped
public class SoapRecepcion implements Serializable {

	private static final long serialVersionUID = 1L;

	public static XML_Utilidades xml_utilidades = new XML_Utilidades();


//	public void sendPostSoap(String urlWebServices, String method, String host, String getEncodeXML, Proxy proxy,
//			Comprobante comprobante) {
//
//		try {
//
//			URL oURL = new URL(urlWebServices);
//
//			HttpURLConnection con = (HttpURLConnection) oURL.openConnection(proxy);
//			con.setDoOutput(true);
//			con.setRequestMethod(method);
//			con.setRequestProperty("Content-type", "text/xml; charset=utf-8");
//			con.setRequestProperty("SOAPAction", "");
//			con.setRequestProperty("Host", host);
//
//			OutputStream reqStreamOut = con.getOutputStream();
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
//			System.out.println(sb.toString());
//
//			this.getEstadoPostSoap(xml_utilidades.convertStringToDocument(sb.toString()),
//					"RespuestaRecepcionComprobante", "estado", comprobante);// está extrae la data de los nodos en un
//																			// archivo XML
//			con.disconnect();
//
//		} catch (Exception ex) {
//			System.out.println("Error: " + ex);
//		}
//	}

	public String formatSendPost(String bytesEncodeBase64) {
		String xml = "<soapenv:Envelope xmlns:soapenv='http://schemas.xmlsoap.org/soap/envelope/' xmlns:ec='http://ec.gob.sri.ws.recepcion'>"
				+ "<soapenv:Header/>" + "<soapenv:Body>" + "<ec:validarComprobante>" + "<xml>" + bytesEncodeBase64
				+ "</xml>" + "</ec:validarComprobante>" + "</soapenv:Body>" + "</soapenv:Envelope>";

		return xml;
	}

//	public void getEstadoPostSoap(Document doc, String nodoRaiz, String nodoElemento, Comprobante comprobante)
//			 {
//
//		String estado = xml_utilidades.getNodes(nodoRaiz, nodoElemento, doc);
//		// String estado = "RECIBIDA";
//
//		System.out.println("Estado Recuperado: " + estado);
//
//		if (estado.equals("RECIBIDA"))
//		try {
//			System.out.println("ENTRA");
//			comprobante.setEstadosri("E");
//			serviceComprabante.modificar(comprobante);
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.out.println("ERROR " + e);
//		}
//		}

//		Connection conn = null;
//
//		final String url = "jdbc:postgresql://localhost:5432/";
//		final String dbName = "dbfinanciero";
//		final String driver = "org.postgresql.Driver";
//		final String userName = "postgres";
//		final String password = "123";
//
//		try {
//			Class.forName(driver).newInstance();
//			conn = DriverManager.getConnection(url + dbName, userName, password);
//
//			if (!conn.isClosed())
//				System.out.println("Databes connection ..................");
//
//			if (estado.equals("DEVUELTA")) {
//				System.out.println("devuelta...............");
//
//				String identificador = xml_utilidades.getNodes("mensaje", "identificador", doc);
//				String mensaje = xml_utilidades.getNodes("mensaje", "mensaje", doc);
//				String informacionAdicional = xml_utilidades.getNodes("mensaje", "informacionAdicional", doc);
//				String tipo = xml_utilidades.getNodes("mensaje", "tipo", doc);
//
//				PreparedStatement stmt = conn
//						.prepareStatement("INSERT INTO financiero.mensaje(id_comprobante,identificador,mensaje,informacion_adicional,tipo) VALUES (?,?,?,?,?)");
//			 
//				stmt.setInt(1, comprobante.getId());
//				stmt.setString(2, identificador);
//				stmt.setString(3, mensaje);
//				stmt.setString(4, informacionAdicional);
//				stmt.setString(5, tipo);
//				
//				int count = stmt.executeUpdate();
//				
//				System.out.println("Inserted count : " + count);
//				stmt.close();
//				
//				PreparedStatement stmt1 = conn
//						.prepareStatement("UPDATE financiero.comprobante SET estadosri='R' WHERE id = ?");
//				stmt1.setInt(1, comprobante.getId());
//
//				int count1 = stmt1.executeUpdate();
//				stmt.close();
//				            
//			} else if (estado.equals("RECIBIDA")) {
//				System.out.println("RECIBIDA");
//				PreparedStatement stmt = conn
//						.prepareStatement("UPDATE financiero.comprobante SET estadosri='E' WHERE id = ?");
//				// stmt.setString(1, estado);
//				stmt.setInt(1, comprobante.getId());
//
//				int count = stmt.executeUpdate();
//				stmt.close();
//			}
//
//		} catch (Exception e) {
//			// TODO: handle exception
//			System.err.println("exveption: " + e.getMessage());
//
//		} finally {
//			// TODO: handle finally clause
//			try {
//				if (conn != null)
//					conn.close();
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//		}

		// EntityManager em = EntityManagerUtil.getEntityManager();

//	}
}