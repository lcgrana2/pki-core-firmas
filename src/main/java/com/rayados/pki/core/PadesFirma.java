package com.rayados.pki.core;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfSignatureAppearance;
import com.itextpdf.text.pdf.PdfStamper;
import com.rayados.pki.bean.CertificadoRayados;

// IMPORTANTE: Paquete oficial moderno para toda la criptografía de iText 5
import com.itextpdf.text.pdf.security.*;

/**
 * @author  LuisC
 *
 */
public class PadesFirma {

	/**
	 * Firma básica utilizando la API moderna de iText 5
	 */
	public static byte[] firmaPdfBasico(byte[] data, CertificadoRayados certificado) throws Exception {
		try {
			PdfReader reader = new PdfReader(data);
			ByteArrayOutputStream nuevoDocumento = new ByteArrayOutputStream();

			PdfStamper stp = PdfStamper.createSignature(reader, nuevoDocumento, '\000', null, true);
			PdfSignatureAppearance sap = stp.getSignatureAppearance();

			sap.setReason("Firma Digital");
			sap.setLocation("Lima");
			sap.setVisibleSignature(new Rectangle(100, 100, 350, 200), 1, "sig");

			// API MODERNA DE FIRMA (Reemplazo definitivo de setCrypto)
			ExternalDigest digest = new BouncyCastleDigest();
			ExternalSignature signature = new PrivateKeySignature(certificado.getPrivateKey(), "SHA-256", null);

			MakeSignature.signDetached(sap, digest, signature, certificado.getCertificateChain(),
					null, null, null, 0, MakeSignature.CryptoStandard.CMS);

			stp.close();
			return nuevoDocumento.toByteArray();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Firma avanzada con personalización de textos en capa visual (Layer 2)
	 */
	public static byte[] firmarPdfAvanzado(byte[] dataDoc, CertificadoRayados certificado) throws Exception {
		try {
			PdfReader reader = new PdfReader(dataDoc);
			ByteArrayOutputStream nuevoDocumento = new ByteArrayOutputStream();

			PdfStamper stamper = PdfStamper.createSignature(reader, nuevoDocumento, '\000', null, true);
			PdfSignatureAppearance sap = stamper.getSignatureAppearance();

			sap.setReason("Firma Digital");
			sap.setLocation("Lima");

			Date fechaFirma = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(fechaFirma);
			sap.setSignDate(calendar);

			// =====================================================================
			// SOLUCIÓN AL ERROR: Extraemos el Common Name (CN) usando la clase 
			// CertificateInfo reubicada en el paquete de seguridad de iText 5.5.x
			// =====================================================================
			String cn = CertificateInfo.getSubjectFields(certificado.getPublicCertificate()).getField("CN");

			String firmado = "Firmado por " + cn;
			String razon = "Motivo: Firma Digital";
			String lugar = "Lugar: Lima";
			SimpleDateFormat dateformatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss Z");
			String fecha = "Fecha: " + dateformatter.format(fechaFirma);
			String firmaH = firmado + '\n' + razon + '\n' + lugar + '\n' + fecha;

			sap.setLayer2Text(firmaH); // Seteamos tu bloque de texto con saltos de línea
			sap.setVisibleSignature(new Rectangle(100, 100, 350, 200), 1, null);

			// =====================================================================
			// API MODERNA: Reemplaza limpiamente todo el flujo manual de bajo nivel.
			// MakeSignature creará y rellenará el diccionario de firma de forma automática.
			// =====================================================================
			ExternalDigest digest = new BouncyCastleDigest();
			ExternalSignature signature = new PrivateKeySignature(certificado.getPrivateKey(), "SHA-256", null);

			MakeSignature.signDetached(sap, digest, signature, certificado.getCertificateChain(),
					null, null, null, 0, MakeSignature.CryptoStandard.CMS);
			// =====================================================================

			stamper.close();
			reader.close();
			nuevoDocumento.flush();
			nuevoDocumento.close();

			return nuevoDocumento.toByteArray();

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}