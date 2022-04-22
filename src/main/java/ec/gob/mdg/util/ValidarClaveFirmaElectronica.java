package ec.gob.mdg.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import ec.gob.mdg.model.Usuario;
import ec.gob.mdg.sri.sign.pkstore.PassStoreKS;
import es.mityc.javasign.pkstore.CertStoreException;
import es.mityc.javasign.pkstore.IPKStoreManager;
import es.mityc.javasign.pkstore.keystore.KSStore;

public class ValidarClaveFirmaElectronica {

	public static PrivateKey privateKey;
	public static Provider provider;
	static Date fechacaducidad;
	static boolean respuesta ;

	Usuario p = (Usuario) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");

	@SuppressWarnings("unused")
	public static boolean LoadCertificate(String password, String nombreFirma) {
		provider = null;
		privateKey = null;
		String firmaPath = UtilsArchivos.getRutaCertificados() + nombreFirma;
		X509Certificate certificate = null;

		try {
			// Cargar certificado de fichero PFX
			KeyStore ks = KeyStore.getInstance("PKCS12");
			InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(firmaPath);
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
				fechacaducidad = certificate.getNotAfter();
				System.out.println("fechacaducidad : " + fechacaducidad);
				respuesta=false;
			} else {
				System.out.println("Certificado no fue cargado..");
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Certificado electrónico - No se encuentra cargado", "ERROR"));
				respuesta = true;
			}
               
		} catch (NoSuchAlgorithmException nsae) {// unsupported PKCS12 bag value type
			// logger.info(nsae.getStackTrace());
			// nsae.printStackTrace();
			System.out.println("1:" + nsae.getMessage());
			boolean respuesta = nsae.getMessage().equalsIgnoreCase("unsupported PKCS12 bag value type");
			boolean respuesta1 = nsae.getMessage().equalsIgnoreCase("PKCS12 keystore not in version 3 format");
			boolean respuesta2 = nsae.getMessage().equalsIgnoreCase("encrypted content not present!");
			if (respuesta || respuesta1 || respuesta2) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Certificado electrónico - Clave no encontrada", "ERROR"));
			}
			return respuesta;
		} catch (CertificateException ce) {
			// ce.printStackTrace();
			//////
			boolean respuesta = ce.getMessage().equalsIgnoreCase("unsupported PKCS12 bag value type");
			boolean respuesta1 = ce.getMessage().equalsIgnoreCase("PKCS12 keystore not in version 3 format");
			boolean respuesta2 = ce.getMessage().equalsIgnoreCase("encrypted content not present!");
			if (respuesta || respuesta1 || respuesta2) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Certificado electrónico es incorrecto", "ERROR"));
			}

			// logger.info(ce.getStackTrace());
			System.out.println("2:" + ce.getMessage());
			return respuesta;
		} catch (FileNotFoundException fnfe) {
			// fnfe.printStackTrace();
			// logger.info(fnfe.getStackTrace());
			System.out.println("3:" + fnfe.getMessage());
			return true;
		} catch (IOException ioe) {
			// ioe.printStackTrace();
			System.out.println("4: " + ioe.getMessage());
			boolean respuesta = ioe.getMessage().equalsIgnoreCase("keystore password was incorrect");
			if (respuesta) {
				System.out.println("4: " + ioe.getMessage().equalsIgnoreCase("keystore password was incorrect"));
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Clave para certificado electrónico es incorrecto", "ERROR"));
			}
			// logger.info(ioe.getStackTrace());
			return respuesta;
		} catch (KeyStoreException kse) {
			// kse.printStackTrace();
			// logger.info(kse.getStackTrace());
			System.out.println("5: " + kse.getMessage());
			boolean respuesta = kse.getMessage().equalsIgnoreCase("Private key is not encodedas PKCS#8");
			boolean respuesta1 = kse.getMessage().equalsIgnoreCase("Certificate chain is not valid");
			boolean respuesta2 = kse.getMessage().equalsIgnoreCase("Unsupported Key type");
			if (respuesta || respuesta1 || respuesta2) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"5. certificado electrónico es incorrecto", "ERROR"));
			}
			return respuesta;
		} catch (CertStoreException cse) {
			// cse.printStackTrace();
			// logger.info(cse.getStackTrace());
			System.out.println("6:" + cse.getMessage());
			return true;
		}
		return respuesta;
	}


}
