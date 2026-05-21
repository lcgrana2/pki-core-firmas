package com.rayados.firmas;

import java.util.List;
import com.rayados.pki.bean.CertificadoRayados;
import com.rayados.pki.core.CertificateStore;
import com.rayados.pki.util.Constante;

public class Main {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("      SISTEMA DE GESTIÓN DE CERTIFICADOS PKI       ");
        System.out.println("==================================================\n");

        // -----------------------------------------------------------------
        // EJERCICIO 1: Extraer información del archivo certificado (.pfx)
        // -----------------------------------------------------------------
        System.out.println(">>> [1] Leyendo Certificado desde Archivo Físico...");
        System.out.println("Ruta configurada: " + Constante.CERTIFICADO);

        try {
            // Invocamos el método enviando las constantes de tu utilidad
            CertificadoRayados certFisico = CertificateStore.getCertificateFromFile(Constante.CERTIFICADO, Constante.CLAVE);

            if (certFisico != null && certFisico.getAlias() != null) {
                System.out.println("✅ ¡Certificado de archivo procesado con éxito!");
                System.out.println("    • Propietario (Subject DN) : " + certFisico.getAlias());

                if (certFisico.getPrivateKey() != null) {
                    System.out.println("    • Algoritmo Llave Privada  : " + certFisico.getPrivateKey().getAlgorithm());
                }
                if (certFisico.getPublicCertificate() != null) {
                    System.out.println("    • Emisor (Issuer DN)       : " + certFisico.getPublicCertificate().getIssuerX500Principal());
                    System.out.println("    • Vencimiento (Not After)  : " + certFisico.getPublicCertificate().getNotAfter());
                }
                if (certFisico.getCertificateChain() != null) {
                    System.out.println("    • Longitud de la Cadena    : " + certFisico.getCertificateChain().length + " nivel(es)");
                }
            } else {
                System.out.println("⚠️ Alerta: El objeto retornado está vacío. Verifica la integridad del archivo.");
            }
        } catch (Exception e) {
            System.err.println("❌ Error al procesar el certificado físico: " + e.getMessage());
        }

        System.out.println("\n==================================================\n");

        // -----------------------------------------------------------------
        // EJERCICIO 2: Listar certificados del almacén personal de Windows
        // -----------------------------------------------------------------
        System.out.println(">>> [2] Escaneando Almacén de Certificados Windows (Windows-MY)...");

        try {
            List<CertificadoRayados> listaCertificados = CertificateStore.listCertificateFromStore();

            if (listaCertificados == null || listaCertificados.isEmpty()) {
                System.out.println("ℹ️ No se encontraron certificados personales instalados en el sistema operativo Windows.");
            } else {
                System.out.println("Se encontraron " + listaCertificados.size() + " certificado(s) disponible(s):\n");
                int index = 1;

                for (CertificadoRayados certSO : listaCertificados) {
                    System.out.println("  📌 Certificado #" + index);
                    System.out.println("    • Nombre / Alias : " + certSO.getAlias());

                    if (certSO.getPublicCertificate() != null) {
                        System.out.println("    • Emisor (Issuer): " + certSO.getPublicCertificate().getIssuerX500Principal());
                        System.out.println("    • Válido Hasta   : " + certSO.getPublicCertificate().getNotAfter());
                    }
                    System.out.println("    ------------------------------------------------");
                    index++;
                }
            }
        } catch (Exception e) {
            System.err.println("❌ Error al acceder al almacén SunMSCAPI de Windows: " + e.getMessage());
        }

        System.out.println("\n==================================================");
        System.out.println("            FIN DE LA EJECUCIÓN                    ");
        System.out.println("==================================================");
    }
}