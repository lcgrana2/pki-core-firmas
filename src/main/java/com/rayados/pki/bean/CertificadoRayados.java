/**
 * 
 */
package com.rayados.pki.bean;

import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;

/**
 * @author LuisC
 *
 */
public class CertificadoRayados {
	private PrivateKey privateKey;
	private Certificate[] certificateChain;
	private String alias;
	private X509Certificate publicCertificate;

	/**
	 * @return the privateKey
	 */
	public PrivateKey getPrivateKey() {
		return privateKey;
	}

	/**
	 * @param privateKey the privateKey to set
	 */
	public void setPrivateKey(PrivateKey privateKey) {
		this.privateKey = privateKey;
	}

	/**
	 * @return the certificateChain
	 */
	public Certificate[] getCertificateChain() {
		return certificateChain;
	}

	/**
	 * @param certificateChain the certificateChain to set
	 */
	public void setCertificateChain(Certificate[] certificateChain) {
		this.certificateChain = certificateChain;
	}

	/**
	 * @return the alias
	 */
	public String getAlias() {
		return alias;
	}

	/**
	 * @param alias the alias to set
	 */
	public void setAlias(String alias) {
		this.alias = alias;
	}

	/**
	 * @return the publicCertificate
	 */
	public X509Certificate getPublicCertificate() {
		return publicCertificate;
	}

	/**
	 * @param publicCertificate the publicCertificate to set
	 */
	public void setPublicCertificate(X509Certificate publicCertificate) {
		this.publicCertificate = publicCertificate;
	}

}
