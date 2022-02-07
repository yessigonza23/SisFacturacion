package ec.gob.mdg.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.io.StringWriter;

import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

@Named
@ViewScoped
public class LeerArchivoXMLBean implements Serializable {

	private static final long serialVersionUID = 1L;

	public void imprimirPantalla() {
		 DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		 try {
			 
			 dbf.setValidating(false);
			    DocumentBuilder db = dbf.newDocumentBuilder();
			    Document doc = db.parse(new FileInputStream(new File("C:\\comprobantes\\firmados\\2402202101176000066000110010030000144561234567810.xml")));
			    String p = prettyPrint(doc);
//			    System.out.println("imprime atring " + p);
		} catch (Exception e) {
			// TODO: handle exception
		}  
		
	}

	private static String prettyPrint(Document document) throws TransformerException {
	    TransformerFactory transformerFactory = TransformerFactory
	            .newInstance();
	    Transformer transformer = transformerFactory.newTransformer();
	    transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	    transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
	    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
	    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
	    DOMSource source = new DOMSource(document);
	    StringWriter strWriter = new StringWriter();
	    StreamResult result = new StreamResult(strWriter);transformer.transform(source, result);
//	    System.out.println(strWriter.getBuffer().toString());

	    return strWriter.getBuffer().toString();

	}
}
