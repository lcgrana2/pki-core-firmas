/**
 * 
 */
package com.rayados.test;

import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.rayados.pki.bean.CertificadoRayados;
import com.rayados.pki.core.CertificateStore;
import com.rayados.pki.core.XadesFirma;
import com.rayados.pki.util.Constante;

/**
 * @author LuisC
 *
 */
public class XMLTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CertificadoRayados certificado= CertificateStore.getCertificateFromFile(Constante.CERTIFICADO, Constante.CLAVE);
			Path path = Paths.get(Constante.XML);
			byte[] documento = Files.readAllBytes(path);
			documento = XadesFirma.firmaXmlBasico(documento, certificado);
			FileOutputStream out = new FileOutputStream(Constante.XML_FIRMADO);
			out.write(documento);
			out.close();
			System.out.println("xml firmado");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
