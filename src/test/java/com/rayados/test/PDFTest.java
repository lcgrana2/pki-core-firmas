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
import com.rayados.pki.core.PadesFirma;
import com.rayados.pki.util.Constante;

/**
 * @author LuisC
 *
 */
public class PDFTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			CertificadoRayados certificado= CertificateStore.getCertificateFromFile(Constante.CERTIFICADO, Constante.CLAVE);
			Path path = Paths.get(Constante.PDF);
			byte[] documento = Files.readAllBytes(path);
			documento = PadesFirma.firmaPdfBasico(documento, certificado);
			
			FileOutputStream out = new FileOutputStream(Constante.PDF_FIRMADO);
			out.write(documento);
			out.close();
			System.out.println("pdf firmado");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}