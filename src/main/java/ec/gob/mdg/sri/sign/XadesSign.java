package ec.gob.mdg.sri.sign;

import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.model.UsuarioPunto;
import ec.gob.mdg.sri.sign.pkstore.PassStoreKS;
import ec.gob.mdg.util.UtilsArchivos;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.List;

import es.mityc.firmaJava.libreria.utilidades.UtilidadTratarNodo;
import es.mityc.firmaJava.libreria.xades.DataToSign;
import es.mityc.firmaJava.libreria.xades.EnumFormatoFirma;
import es.mityc.firmaJava.libreria.xades.FirmaXML;
import es.mityc.firmaJava.libreria.xades.XAdESSchemas;
import es.mityc.javasign.pkstore.CertStoreException;
import es.mityc.javasign.pkstore.IPKStoreManager;
import es.mityc.javasign.pkstore.keystore.KSStore;
import es.mityc.javasign.xml.refs.ObjectToSign;
import java.io.InputStream;

import javax.faces.context.FacesContext;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class XadesSign {

	public PrivateKey privateKey;
	public Provider provider;

	@SuppressWarnings("unused")
	private UsuarioPunto usuPunto = new UsuarioPunto();
	Usuario p = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
	@SuppressWarnings("unused")
	private final static String POLICY_TO_APPLY = "implied";

	public byte[] firmarDocumentoXmlXades(byte[] entrada) {
		X509Certificate certificate = LoadCertificate();
		// Si encontramos el certificado...
		if (certificate != null) {

			// Crear datos a firmar
			DataToSign dataToSign = new DataToSign();
			dataToSign.setXadesFormat(EnumFormatoFirma.XAdES_BES);
			dataToSign.setEsquema(XAdESSchemas.XAdES_132);

			dataToSign.setXMLEncoding("UTF-8");
			dataToSign.setEnveloped(true);
			dataToSign.addObject(
					new ObjectToSign(new mdgObjectToSign(), "Descripcion del documento", null, "text/xml", null));
			dataToSign.setDocument(LoadXML(entrada));

			Object[] res = null;
			try {
				FirmaXML f = new FirmaXML();
				res = f.signFile(certificate, dataToSign, privateKey, provider);
//				System.out.println("Xml firmado correctamente!!!");
			} catch (NoSuchMethodError ex) {
				// logger.info("error : "+ex.getStackTrace() + " ");
				ex.printStackTrace();

			} catch (Exception e) {
				// logger.info("Ha ocurrido una exceptio al momento de firmar el documento
				// "+e.getStackTrace());
				e.printStackTrace();
				return null;
			}

			ByteArrayOutputStream outStream = new ByteArrayOutputStream();

			// Guardamos el array de bytes firmados en un ByteArrayOutputStream.
			UtilidadTratarNodo.saveDocumentToOutputStream((Document) res[0], outStream, true);

			return outStream.toByteArray();
		}

		return null;
	}

	@SuppressWarnings("unused")
	private X509Certificate LoadCertificate() {
		provider = null;
		privateKey = null;

		String nombreFirma = p.getNombrefirmaelectronica();		
		String firmaPath = UtilsArchivos.getRutaCertificados() + nombreFirma;

		String firmaPassword = p.getClavefirma();
		firmaPassword = descifrarBase64(firmaPassword);
		String password = firmaPassword;

		X509Certificate certificate = null;

		try {
			// Cargar certificado de fichero PFX
			KeyStore ks = KeyStore.getInstance("PKCS12");
			InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(firmaPath);
			// ks.load(new BufferedInputStream(is), password.toCharArray());
			ks.load(new BufferedInputStream(new FileInputStream(firmaPath)), password.toCharArray());
			IPKStoreManager storeManager = new KSStore(ks, new PassStoreKS(password));
			List<X509Certificate> certificates = storeManager.getSignCertificates();
			// Si encontramos el certificado...
			if (certificates.size() >= 1) {
				// certificate = certificates.get(0);
				for (X509Certificate cert : certificates) {
					if (cert.getKeyUsage()[0]) {
						certificate = cert;
					}
				}
				// Obtencin de la clave privada asociada al certificado
				privateKey = storeManager.getPrivateKey(certificate);
				// Obtencin del provider encargado de las labores criptogr�ficas
				provider = storeManager.getProvider(certificate);
				
			} else {
				System.out.println("Certificado no fue cargado..");
			}

		} catch (NoSuchAlgorithmException nsae) {
			// logger.info(nsae.getStackTrace());
			nsae.printStackTrace();
			return null;
		} catch (CertificateException ce) {
			ce.printStackTrace();
			// logger.info(ce.getStackTrace());
			return null;
		} catch (FileNotFoundException fnfe) {
			fnfe.printStackTrace();
			// logger.info(fnfe.getStackTrace());
			return null;
		} catch (IOException ioe) {
			ioe.printStackTrace();
			// logger.info(ioe.getStackTrace());
			return null;
		} catch (KeyStoreException kse) {
			kse.printStackTrace();
			// logger.info(kse.getStackTrace());
			return null;
		} catch (CertStoreException cse) {
			cse.printStackTrace();
			// logger.info(cse.getStackTrace());
			return null;
		}

		return certificate;
	}

	private Document LoadXML(byte[] fileBytes) {
		Document doc = null;
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setNamespaceAware(true);
		try {
			doc = dbf.newDocumentBuilder().parse(new ByteArrayInputStream(fileBytes));
		} catch (ParserConfigurationException ex) {
			ex.printStackTrace();
			// logger.info(ex.getStackTrace());
			return null;
		} catch (SAXException ex) {
			ex.printStackTrace();
			// logger.info(ex.getStackTrace());
			System.out.println(new String(fileBytes));
			return null;
		} catch (IOException ex) {
			ex.printStackTrace();
			// logger.info(ex.getStackTrace());
			return null;
		} catch (IllegalArgumentException ex) {
			ex.printStackTrace();
			// logger.info(ex.getStackTrace());
			return null;
		}
		
		return doc;
	}
	
	////decodificar clave
	public static String descifrarBase64(String a) {
		Base64.Decoder decoder = Base64.getDecoder();
		byte[] decodedByteArray = decoder.decode(a);

		String b = new String(decodedByteArray);
		return b;
	}

}