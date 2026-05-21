/**
 * 
 */
package com.rayados.test;

import java.util.List;

import com.rayados.pki.bean.CertificadoRayados;
import com.rayados.pki.core.CertificateStore;

/**
 * @author LuisC
 *
 */
public class ServiceTest {
	
	public static void main(String[] args) {
		try {
//			CertificadoRayados certificado= CertificateStore.getCertificateFromFile(Constante.CERTIFICADO, Constante.CLAVE);
//			System.out.println(certificado.getAlias());
//			System.out.println("----------------------------------------------");
//			System.out.println(certificado.getPrivateKey().getAlgorithm());
//			System.out.println("----------------------------------------------");
//			System.out.println(certificado.getPublicCertificate().toString());
			
			List<CertificadoRayados> listCertificadoRayados = CertificateStore.listCertificateFromStore();
			for (CertificadoRayados certificadoRayados : listCertificadoRayados) {
				System.out.println(certificadoRayados.getAlias());
				System.out.println(certificadoRayados.getPublicCertificate().getIssuerX500Principal());
				System.out.println(certificadoRayados.getPublicCertificate().getNotAfter());
				System.out.println("----------------------------------------------");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
