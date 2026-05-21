/**
 *
 */
package com.rayados.pki.core;

import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import com.rayados.pki.bean.CertificadoRayados;

/**
 * @author LuisC
 *
 */
public class CertificateStore {

    public static CertificadoRayados getCertificateFromFile(String path, String key) {

        CertificadoRayados certificado = new CertificadoRayados();
        try {

            KeyStore jks = KeyStore.getInstance("PKCS12");
            InputStream in = new FileInputStream(path);
            jks.load(in, key.toCharArray());
            in.close();

            String aliasJks = jks.aliases().nextElement();
            PrivateKey pk = (PrivateKey) jks.getKey(aliasJks, key.toCharArray());
            Certificate[] chain = jks.getCertificateChain(aliasJks);
            X509Certificate oPublicCertificate = (X509Certificate) chain[0];

            certificado.setAlias(oPublicCertificate.getSubjectX500Principal().getName());
            certificado.setPublicCertificate(oPublicCertificate);
            certificado.setPrivateKey(pk);
            certificado.setCertificateChain(chain);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return certificado;
    }

    public static List<CertificadoRayados> listCertificateFromStore() {

        List<CertificadoRayados> listCertificadoRayados = new ArrayList<>();
        try {
            KeyStore jks = KeyStore.getInstance("Windows-MY", "SunMSCAPI");
            jks.load(null, null);

            Enumeration<String> en = jks.aliases();
            while (en.hasMoreElements()) {
                CertificadoRayados certificado = new CertificadoRayados();
                String aliasKey = (String) en.nextElement();

                PrivateKey pk = (PrivateKey) jks.getKey(aliasKey, null);
                Certificate[] chain = jks.getCertificateChain(aliasKey);
                X509Certificate oPublicCertificate = (X509Certificate) chain[0];

                certificado.setAlias(oPublicCertificate.getSubjectX500Principal().getName());
                certificado.setPublicCertificate(oPublicCertificate);
                certificado.setPrivateKey(pk);
                certificado.setCertificateChain(chain);

                listCertificadoRayados.add(certificado);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCertificadoRayados;
    }

}
