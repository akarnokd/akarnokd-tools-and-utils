/*
 * Copyright 2012-2014 David Karnok
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hu.akarnokd.utils.crypto;

import hu.akarnokd.utils.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.Provider;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.SignatureException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.DERObjectIdentifier;
import org.bouncycastle.asn1.DEROutputStream;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.DERTaggedObject;
import org.bouncycastle.asn1.DERUTF8String;
import org.bouncycastle.asn1.x509.GeneralName;
import org.bouncycastle.asn1.x509.GeneralNames;
import org.bouncycastle.asn1.x509.X509Extensions;
import org.bouncycastle.asn1.x509.X509Name;
import org.bouncycastle.jce.PKCS10CertificationRequest;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.x509.X509V3CertificateGenerator;
import org.bouncycastle.x509.extension.SubjectKeyIdentifierStructure;

/**
 * Manges keystore specific operations.
 * Allows to create, save and load a keystore. Can generate
 * new private key and certificate. Creates certificate signing
 * requests and allows the import of the reply from the CA.
 * Uses the Bouncy Castle JCE provider and 1024 bit RSA keys
 * Example:<p>
 * 		KeystoreManager km = new KeystoreManager();<br>
 *		km.create();<br>
 *		String dn = "CN=David Karnok, OU=EMI, O=MTA SZTAKI";<br>
 *
 *		km.generateRSACert("test", dn, dn, "localhost", "abc".toCharArray());<br>
 *		
 *		System.out.println(km.getCertificate("test"));<br>
 *		System.out.println(km.getPrivateKey("test", "abc".toCharArray()));<br>
 *		System.out.println(km.createRSASigningRequest("test", "abc".toCharArray()));<br>
 * <p>
 * Original idea: http://www.koders.com/java/fid165B893DAEA5F8D4AD44CCF9B8FBE75B01CE6575.aspx
 * @author akarnokd, 2007.12.06.
 */
@SuppressWarnings("deprecation")
public class KeystoreManager {
	/**
	 * The Logger object.
	 */
	private static final Logger LOG = Logger.getLogger(KeystoreManager.class.getName());
	/**
	 * The managed keystore object.
	 */
	private KeyStore keystore;
	/**
	 * The Bouncy castle cryptography provider.
	 */
	public static final Provider BC_PROVIDER = new BouncyCastleProvider();
	/**
	 * Register bouncy castle provider.
	 */
	static {
		Security.addProvider(BC_PROVIDER);
	}
	/**
	 * Constructor. Creates an empty keystore in memory.
	 */
	public KeystoreManager() {
		createEmpty();
	}
	/**
	 * Create a new JKS keystore.
	 * Throws KeystoreFault if the operation fails
	 */
	public void create() {
		createEmpty();
	}
	/**
	 * Create an empty keystore.
	 */
	private void createEmpty() {
		try {
			keystore = KeyStore.getInstance("JKS");
			keystore.load(null, null);
		} catch (Exception ex) {
			LOG.log(Level.SEVERE, ex.toString(), ex);
			throw new KeystoreFault(ex);
		}
	}
	/**
	 * Load a keystore from the given file.
	 * Throws KeystoreFault if something failed.
	 * @param fileName the filename
	 * @param password the keystore password if supplied
	 */
	public void load(String fileName, char[] password) {
		try {
			InputStream in = new FileInputStream(fileName);
			try {
				load(in, password);
			} finally {
				in.close();
			}
		} catch (IOException ex) {
			throw new KeystoreFault(ex);
		}
	}
	/**
	 * Load a keystore from the given input stream.
	 * Does not close the input stream.
	 * Throws KeystoreFault if something failed.
	 * @param in the input stream, should not be null
	 * @param password the keystore password if supplied
	 */
	public void load(InputStream in, char[] password) {
		try {
			keystore = KeyStore.getInstance("JKS");
			keystore.load(in, password);
		} catch (Exception ex) {
			throw new KeystoreFault(ex);
		}
	}
	/**
	 * Save the current keystore into the given file.
	 * @param fileName the filename
	 * @param password the password if supplied
	 */
	public void save(String fileName, char[] password) {
		try {
			OutputStream out = new FileOutputStream(fileName);
			try {
				save(out, password);
			} finally {
				out.close();
			}
		} catch (IOException ex) {
			throw new KeystoreFault(ex);
		}
	}
	/**
	 * Save the current keystore into the given output stream.
	 * Does not close the stream
	 * @param out the output stream
	 * @param password the password if supplied
	 */
	public void save(OutputStream out, char[] password) {
		try {
			keystore.store(out, password);
		} catch (Exception ex) {
			throw new KeystoreFault(ex);
		}
	}
	/**
	 * Generate a public/private keypair using the given algorithm and size.
	 * @param algorithm the algorithm (DSA/RSA)
	 * @param size the key size, usually 1024
	 * @return the generated keypair.
	 */
	public KeyPair generateKeyPair(String algorithm, int size) {
		try {
			KeyPairGenerator generator = KeyPairGenerator.getInstance(algorithm, BC_PROVIDER);
			generator.initialize(size, new SecureRandom());
			return generator.generateKeyPair();
		} catch (NoSuchAlgorithmException ex) {
			throw new KeystoreFault(ex);
		}
	}
	/**
	 * Generate a X509 certificate for the given keypair.
	 * The distinguished names must be in format: CN=cName, OU=orgUnit, O=org, L=city, S=state, C=countryCode
	 * use backslash to escape a comma
	 * @param keypair the keypair
	 * @param months the validity length in months
	 * @param issuerDN the issuer distinguished name: "CN=David Karnok,OU=EMI,O=MTA SZTAKI"
	 * @param subjectDN the subject distinguished name: "CN=David Karnok,OU=EMI,O=MTA SZTAKI"
	 * @param domain domain of the server to store in the subject alternative name extension
	 * @param signAlgorithm the signing algorithm to use
	 * @return the generated X509 certificate
	 */
	public X509Certificate createX509Certificate(KeyPair keypair, int months,
			String issuerDN, String subjectDN, String domain, String signAlgorithm) {
		try {
			// calendar for date calculations
			GregorianCalendar cal = new GregorianCalendar();
			
			// extract keypair components
			PublicKey pubKey = keypair.getPublic();
			PrivateKey privKey = keypair.getPrivate();
			
			// generate a random serial number
			SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
			random.setSeed(System.currentTimeMillis());
			byte[] serialNo = new byte[8];
			random.nextBytes(serialNo);
			BigInteger serial = new BigInteger(serialNo).abs();
			
			// create the certificate generator
			X509V3CertificateGenerator certGen = new X509V3CertificateGenerator();
			certGen.reset();
			
			// set certificate attributes
			certGen.setSerialNumber(serial);
			cal.setTimeInMillis(System.currentTimeMillis());
			certGen.setNotBefore(cal.getTime());
			cal.add(GregorianCalendar.MONTH, months);
			certGen.setNotAfter(cal.getTime());
			certGen.setPublicKey(pubKey);
			certGen.setSignatureAlgorithm(signAlgorithm);
			certGen.setIssuerDN(new X509Name(issuerDN));
			certGen.setSubjectDN(new X509Name(subjectDN));
                        
                        certGen.addExtension(X509Extensions.SubjectKeyIdentifier, false, new SubjectKeyIdentifierStructure(pubKey));
			
			// create subject alternative name
			boolean isCritical = subjectDN == null || "".equals(subjectDN.trim());
			DERSequence othernameSeq = new DERSequence(new ASN1Encodable[] {
				new DERObjectIdentifier("1.3.6.1.5.5.7.8.5"),
				new DERTaggedObject(true, 0, new DERUTF8String(domain))
			});
			GeneralName othernameGen = new GeneralName(GeneralName.otherName, othernameSeq);
			GeneralNames subjectAlternatives = new GeneralNames(othernameGen); 
			certGen.addExtension(X509Extensions.SubjectAlternativeName, isCritical, subjectAlternatives);
			
			// finally generate the certificate
			X509Certificate cert = certGen.generateX509Certificate(privKey, BC_PROVIDER.getName(), new SecureRandom());
			cert.checkValidity(new Date());
			cert.verify(pubKey);
			
			return cert;
		} catch (NoSuchAlgorithmException ex) {
			throw new KeystoreFault(ex);
		} catch (CertificateException ex) {
			throw new KeystoreFault(ex);
		} catch (SignatureException ex) {
			throw new KeystoreFault(ex);
		} catch (NoSuchProviderException ex) {
			throw new KeystoreFault(ex);
		} catch (InvalidKeyException ex) {
			throw new KeystoreFault(ex);
		}
	}
	/**
	 * Generate an RSA 1024 bit key and certificate and add it to the current keystore
	 * under the given alias.
	 * The distinguished names must be in format: CN=cName, OU=orgUnit, O=org, L=city, S=state, C=countryCode
	 * use backslash to escape a comma
	 * @param alias the certificate alias
	 * @param issuerDN the issuer distinguished name: "CN=David Karnok,OU=EMI,O=MTA SZTAKI"
	 * @param subjectDN the subject distinguished name: "CN=David Karnok,OU=EMI,O=MTA SZTAKI"
	 * @param domain domain of the server to store in the subject alternative name extension
	 * @param password the key password
	 * @return the generated X509Certificate
	 */
	public X509Certificate generateRSACert(String alias, String issuerDN, String subjectDN, String domain, char[] password) {
		KeyPair keypair = generateKeyPair("RSA", 1024);
		X509Certificate cert = createX509Certificate(keypair, 12, issuerDN, subjectDN, domain, "MD5withRSA");
		try {
			keystore.setKeyEntry(alias, keypair.getPrivate(), password, new X509Certificate[] { cert });
		} catch (KeyStoreException ex) {
			throw new KeystoreFault(ex);
		}
		return cert;
	}
	/**
	 * Create a certificate signing request.
	 * The created text can be sent to a Certificate Authority to request
	 * a countersigning.
	 * @param cert the local X509Certificate object
	 * @param privKey the private key of the certificate
	 * @return the request string
	 */
	public String createRSASigningRequest(X509Certificate cert, PrivateKey privKey) {
		X509Name xname = new X509Name(cert.getSubjectDN().getName());
		try {
			PKCS10CertificationRequest certReq =
				new PKCS10CertificationRequest("MD5withRSA", xname, cert.getPublicKey(), null, privKey, BC_PROVIDER.getName());
			
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			DEROutputStream dout = new DEROutputStream(bout);
			try {
				dout.writeObject(certReq.toASN1Primitive());
			} finally {
				dout.close();
			}
			
			String s = Base64.encodeBytes(bout.toByteArray());
			StringBuilder result = new StringBuilder(s.length() + 100);
			result.append("-----BEGIN NEW CERTIFICATE REQUEST-----\n");
			// split base64 string into 76 character lines
			int lineLen = 76;
			int len = s.length();
			int idx = 0;
			while (len > 0) {
				if (len > lineLen) {
					result.append(s.substring(idx, idx + lineLen)).append('\n');
					len -= lineLen;
					idx += lineLen;
				} else {
					result.append(s.substring(idx)).append('\n');
					break;
				}
			}
			result.append("-----END NEW CERTIFICATE REQUEST-----\n");
			return result.toString();
		} catch (Exception ex) {
			throw new KeystoreFault(ex);
		}
	}
	/**
	 * Generate an RSA signing request for the given key alias.
	 * @param alias the alias
	 * @param password the private key password
	 * @return the signing request text
	 */
	public String createRSASigningRequest(String alias, char[] password) {
		return createRSASigningRequest(getCertificate(alias), getPrivateKey(alias, password));
	}	
	/**
	 * Installs the signing request response to an existing certificate
	 * in the keystore.
	 * @param alias the alias of the original certificate
	 * @param password the password of the certificate
	 * @param caReply the input stream pointing to the CA reply data, must be closed by the caller
	 * @param verifyRoot verifiy the root of imported certificate using the keystore?
	 */
	public void installReply(String alias, char[] password, InputStream caReply,
			boolean verifyRoot) {
		try {
			X509Certificate cert = (X509Certificate)keystore.getCertificate(alias);
			if (cert == null) {
				throw new KeystoreFault("Certificate not found: " + alias);
			}
			PrivateKey privKey = (PrivateKey)keystore.getKey(alias, password);
			
			List<X509Certificate> certs = new ArrayList<X509Certificate>();
			CertificateFactory cf = CertificateFactory.getInstance("X509");
			// collect the certificate elements into a list
			for (Certificate c : cf.generateCertificates(caReply)) {
				certs.add((X509Certificate)c);
			}
			if (certs.isEmpty()) {
				throw new KeystoreFault("Empty CA response");
			}
			List<X509Certificate> newCerts = null;
			if (certs.size() == 1) {
				newCerts = establishCertChain(cert, certs.get(0));
			} else {
				newCerts = verifyReply(alias, cert, certs, verifyRoot);
			}
			// add only new certificates to the keystore
			if (newCerts != null) {
				keystore.setKeyEntry(alias, privKey, password, newCerts.toArray(new X509Certificate[newCerts.size()]));
			}
		} catch (Exception ex) {
			throw new KeystoreFault(ex);
		}
	}
	/**
	 * Establish a certificate chain list using only one caReply element.
	 * The public keys are checked to be equal.
	 * @param cert the local certificate
	 * @param caReply the reply certificate of the CA
	 * @return the list of the certificates
	 */
	private List<X509Certificate> establishCertChain(
			X509Certificate cert, X509Certificate caReply) {
		PublicKey pubKey = cert.getPublicKey();
		if (!pubKey.equals(caReply.getPublicKey())) {
			throw new KeystoreFault("The keys in reply and keystore don't match");
		}
		if (cert.equals(caReply)) {
			throw new KeystoreFault("The local and reply certificates are identical");
		}
		Map<Principal, Set<X509Certificate>> knownCerts = new HashMap<Principal, Set<X509Certificate>>();
		try {
			if (keystore.size() > 0) {
				knownCerts.putAll(getCertsByIssuer());
			}
		} catch (KeyStoreException ex) {
			throw new KeystoreFault(ex);
		}
		LinkedList<X509Certificate> result = new LinkedList<X509Certificate>();
		buildChain(caReply, result, knownCerts);
		return result;
	}
	/**
	 * Build the chain of certificates.
	 * @param cert the CA reply certificate
	 * @param chain the resulting chain
	 * @param knownCerts the known certificates of each Principal
	 */
	private void buildChain(X509Certificate cert, LinkedList<X509Certificate> chain, 
			Map<Principal, Set<X509Certificate>> knownCerts) {
		Principal subject = cert.getSubjectDN();
		Principal issuer = cert.getIssuerDN();
		if (subject.equals(issuer)) {
			chain.addFirst(cert);
			return;
		}
		Set<X509Certificate> issuerCerts = knownCerts.get(issuer);
		if (issuerCerts == null || issuerCerts.isEmpty()) {
			throw new KeystoreFault("No certificate found for " + issuer);
		}
		for (X509Certificate issuerCert : issuerCerts) {
			try {
				cert.verify(issuerCert.getPublicKey());
				buildChain(issuerCert, chain, knownCerts);
			} catch (Exception ex) {
				throw new KeystoreFault(ex);
			}
		}
		chain.addFirst(cert);
	}
	/**
	 * Groups the X509 certificates - found in keystore - by subject DN and returns it as a map.
	 * @return the grouped X509 certificates
	 */
	private Map<Principal, Set<X509Certificate>> getCertsByIssuer() {
		try {
			Map<Principal, Set<X509Certificate>> result = new HashMap<Principal, Set<X509Certificate>>();
			Enumeration<String> aliases = keystore.aliases();
			while (aliases.hasMoreElements()) {
				String alias = aliases.nextElement();
				X509Certificate cert = (X509Certificate)keystore.getCertificate(alias);
				if (cert != null) {
					Principal principal = cert.getSubjectDN();
					Set<X509Certificate> list = result.get(principal);
					if (list == null) {
						list = new HashSet<X509Certificate>();
						result.put(principal, list);
						list.add(cert);
					} else {
						if (!list.contains(cert)) {
							list.add(cert);
						}
					}
				}
			}
			return result;
		} catch (KeyStoreException ex) {
			throw new KeystoreFault(ex);
		}
	}
	/**
     * Validates chain in certification reply, and returns the ordered
     * elements of the chain (with user certificate first, and root
     * certificate last in the array).
     *
     * @param alias the alias name
     * @param localCert the user certificate of the alias
     * @param replyCerts the chain provided in the reply
     * @param verifyRoot verify the root CA?
	 * @return the ordered list of certificates
	 */
	private List<X509Certificate> verifyReply(String alias, X509Certificate localCert,
			List<X509Certificate> replyCerts, boolean verifyRoot) {
		PublicKey pubKey = localCert.getPublicKey();
		X509Certificate current = null;
		Map<Principal, X509Certificate> chainMap = new HashMap<Principal, X509Certificate>();
		for (X509Certificate replyCert : replyCerts) {
			if (pubKey.equals(replyCert.getPublicKey())) {
				current = replyCert;
			}
			chainMap.put(replyCert.getSubjectDN(), replyCert);
		}
		if (current == null) {
			throw new KeystoreFault("Certificate reply does not contain public key for " + alias);
		}
		List<X509Certificate> result = new ArrayList<X509Certificate>();
		// reorder certificates into chain where the following element signs the previous element
		while (result.size() < replyCerts.size()) {
			result.add(current);
			// find the current elements issuer certificate
			X509Certificate next = chainMap.get(current.getIssuerDN());
			if (next == null) {
				throw new KeystoreFault("Broken certificate chain");
			}
			// verify the chain
			try {
				current.verify(next.getPublicKey());
			} catch (Exception ex) {
				throw new KeystoreFault(ex);
			}
			// move to the issuer
			current = next;
		}
		if (verifyRoot) {
			// verify root issuer
			X509Certificate root = result.get(result.size() - 1);
			
			try {
				// check keystore for it
				String rootAlias = keystore.getCertificateAlias(root);
				if (rootAlias == null) {
					boolean verified = false;
					X509Certificate rootCert = null;
					// locate the true root be trying to verify the current root
					Enumeration<String> aliases = keystore.aliases();
					while (aliases.hasMoreElements()) {
						rootCert = (X509Certificate)keystore.getCertificate(aliases.nextElement());
						if (rootCert != null) {
							try {
								root.verify(rootCert.getPublicKey());
								verified = true;
								break;
							} catch (Exception ex) {
								// ignore
							}
						}
					}
					if (!verified) {
						throw new KeystoreFault("Root CA could not be verified");
					} else {
						// check if it is self signed
						if (!root.getSubjectDN().equals(root.getIssuerDN())) {
							result.add(rootCert);
						}
					}
				}
			} catch (Exception ex) {
				throw new KeystoreFault(ex);
			}
			
		}
		return result;
	}
	/**
	 * Set a certificate for an entry. Does not close the input stream.
	 * @param alias the alias
	 * @param caReply the certificate input stream
	 */
	public void importCertificate(String alias, InputStream caReply) {
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X509");
			// collect the certificate elements into a list
			Certificate cert = cf.generateCertificate(caReply);				
			keystore.setCertificateEntry(alias, cert);
		} catch (Exception ex) {
			throw new KeystoreFault(ex);
		}
	}
	/**
	 * Load a DER certificate from a textual or binary stream.
	 * Does not close the stream.
	 * @param in the input stream, not null
	 * @return the loaded certificate, not null
	 */
	public static Certificate loadCertificate(InputStream in) {
		try {
			CertificateFactory cf = CertificateFactory.getInstance("X509");
			// collect the certificate elements into a list
			return cf.generateCertificate(in);
		} catch (Exception ex) {
			throw new KeystoreFault(ex);
		}
	}
	/**
	 * Retrieve the JDK's most trusted certificates.
	 * Original idea: http://jug.org.ua/wiki/display/JavaAlmanac/Listing+the+Most-Trusted+Certificate+Authorities+%28CA%29+in+a+Key+Store
	 * @return the nonnul list of the most trusted certificates
	 */
	public static List<X509Certificate> getJDKTrustedCerts() {
		List<X509Certificate> result = new ArrayList<X509Certificate>();
		String filename = getJDKCertFile();
		try {
			FileInputStream fin = new FileInputStream(filename);
			try {
				KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
				ks.load(fin, "changeit".toCharArray());
				
				PKIXParameters params = new PKIXParameters(ks);
				for (TrustAnchor a : params.getTrustAnchors()) {
					result.add(a.getTrustedCert());
				}
			} finally {
				fin.close();
			}
		} catch (IOException ex) {
			throw new KeystoreFault(ex);
		} catch (CertificateException ex) {
			throw new KeystoreFault(ex);
		} catch (NoSuchAlgorithmException ex) {
			throw new KeystoreFault(ex);
		} catch (KeyStoreException ex) {
			throw new KeystoreFault(ex);
		} catch (InvalidAlgorithmParameterException ex) {
			throw new KeystoreFault(ex);
		}
		return result;
	}
	/**
	 * @return the JDK's own trusted certificate keystore location.
	 */
	public static String getJDKCertFile() {
		return System.getProperty("java.home") 
		+ "/lib/security/cacerts".replace('/', File.separatorChar);
	}
	/**
	 * Exports a given certificate into an output stream.
	 * Throws KeystoreFault if the alias cannot be found
	 * or there is a problem with the keystore operations.
	 * Does not close the output stream
	 * @param alias the alias
	 * @param out the output stream
	 * @param binary the output should be binary (true), or text (false)?
	 * @throws IOException when writing to the output stream fails
	 */
	public void exportCertificate(String alias, OutputStream out, boolean binary)
	throws IOException {
		try {
			Certificate cert = keystore.getCertificate(alias);
			if (cert != null) {
				serializeCertificate(out, binary, cert);
			} else {
				throw new KeystoreFault("Unknown certificate: " + alias);
			}
		} catch (CertificateEncodingException ex) {
			throw new KeystoreFault(ex);
		} catch (KeyStoreException ex) {
			throw new KeystoreFault(ex);
		}
	}
	/**
	 * Export a private key entry into a choosable file format.
	 * Might throw KeystoreFault if the alias is not found or not a private key
	 * or any KeyStore exception is thrown.
	 * @param alias the private key alias.
	 * @param password the key password, can be null
	 * @param out the output stream to write to, cannot be null
	 * @param binary do binary (true) or textual output?
	 * @throws IOException if the serialization throws IOException
	 */
	public void exportPrivateKey(String alias, char[] password, OutputStream out, boolean binary)
	throws IOException {
		try {
			Key key = keystore.getKey(alias, password);
			if (key instanceof PrivateKey) {
				serializePrivateKey(out, binary, (PrivateKey)key);
			} else {
				throw new KeystoreFault("Unknown key: " + alias);
			}
		} catch (UnrecoverableKeyException ex) {
			throw new KeystoreFault(ex);
		} catch (NoSuchAlgorithmException ex) {
			throw new KeystoreFault(ex);
		} catch (KeyStoreException ex) {
			throw new KeystoreFault(ex);
		}
	}
	/**
	 * Serialize the given private key into a binary or textual stream format.
	 * @param out the output stream, cannot be null
	 * @param binary binary export?
	 * @param key the private key, cannot be null
	 * @throws IOException if an error occurs during serialization.
	 */
	public static void serializePrivateKey(OutputStream out, boolean binary, PrivateKey key)
	throws IOException {
		if (binary) {
			out.write(key.getEncoded());
			out.flush();
		} else {
			Writer wr = new OutputStreamWriter(out, "UTF-8");
			wr.write(BEGIN_PRIVATE_KEY);
			wr.write("\n");
			wr.write(Base64.encodeBytes(key.getEncoded()));
			wr.write("\n");
			wr.write(END_PRIVATE_KEY);
			wr.write("\n");
			wr.flush();
		}
	}
	/**
	 * Serializes the given certificate into the given output stream
	 * in binary or text format the output stream gets flushed but not closed.
	 * @param out the output stream, cannot be null.
	 * @param binary binary export?
	 * @param cert the certificate
	 * @throws IOException when the IO operation fails
	 * @throws CertificateEncodingException on certificate problems
	 */
	public static void serializeCertificate(OutputStream out, boolean binary,
			Certificate cert) throws IOException, CertificateEncodingException {
		if (binary) {
			out.write(cert.getEncoded());
			out.flush();
		} else {
			Writer wr = new OutputStreamWriter(out, "UTF-8");
			wr.write("-----BEGIN CERTIFICATE-----\n");
			wr.write(Base64.encodeBytes(cert.getEncoded()));
			wr.write("\n-----END CERTIFICATE-----\n");
			wr.flush();
		}
	}
	/**
	 * Retrieve a certificate for the given private key or certificate.
	 * @param alias the alias
	 * @return the found X509 certificate or null if alias not found or the
	 * entry does not have any certificates
	 */
	public X509Certificate getCertificate(String alias) {
		if (alias == null) {
			return null;
		}
		try {
			return (X509Certificate)keystore.getCertificate(alias);
		} catch (Exception ex) {
			throw new KeystoreFault(ex);
		}
	}
	/**
	 * Retrieve a private key named by the alias.
	 * @param alias the alias
	 * @param password the key password
	 * @return the private key or null if not found or not a key entry
	 */
	public PrivateKey getPrivateKey(String alias, char[] password) {
		try {
			return (PrivateKey)keystore.getKey(alias, password);
		} catch (Exception ex) {
			throw new KeystoreFault(ex);
		}
	}
	/**
	 * @return the underlying keystore, can be null if the keystore was not loaded or created
	 */
	public KeyStore getKeyStore() {
		return keystore;
	}
	/**
	 * Main program. Generates a key with a self signing certificate
	 * and shows that it is in the keystore.
	 * @param args the arguments - not used
	 * @throws Exception on error
	 */
	public static void main(String[] args) throws Exception {
		KeystoreManager km = new KeystoreManager();
		km.create();
		DistinguishedName dn = new DistinguishedName("CN=David Karnok\\,, OU=EMI, O=MTA SZTAKI");

		LOG.info(String.valueOf(dn));
		
		km.generateRSACert("test", dn.toString(), dn.toString(), "localhost", "abc".toCharArray());
		
		LOG.info(String.valueOf(km.getPrivateKey("test", "abc".toCharArray())));
		LOG.info(String.valueOf(km.createRSASigningRequest("test", "abc".toCharArray())));
		LOG.info(String.valueOf(km.getCertificate("test")));
		
		km.load("conf/client.jks", "apache".toCharArray());
		
		LOG.info(String.valueOf(km.getCertificate("client")));
		
		LOG.info(String.valueOf(KeystoreManager.getJDKTrustedCerts()));
	}
	/**
	 * Import a private key from an input stream.
	 * Might throw KeystoreFault if there was an error with the keystore
	 * @param alias the private key alias, cannot be null
	 * @param password the optional key password
	 * @param key the input stream of the key data, cannot be null
	 * @param cert the input stream of the certificate data, cannot be null
	 * @throws IOException if error occurs when reading the input stream
	 */
	public void importPrivateKey(String alias, char[] password, InputStream key, InputStream cert) 
	throws IOException {
		try {
			KeyFactory kf = KeyFactory.getInstance("RSA");
			PKCS8EncodedKeySpec keyspec = new PKCS8EncodedKeySpec(getEncodedKeyFrom(key));
			
			PrivateKey privKey = kf.generatePrivate(keyspec);
			
			CertificateFactory cf = CertificateFactory.getInstance("X509");
			// collect the certificate elements into a list
			Collection<? extends Certificate> certs = cf.generateCertificates(cert);				
			
			
			keystore.setKeyEntry(alias, privKey, password, certs.toArray(new Certificate[certs.size()]));
		} catch (NoSuchAlgorithmException ex) {
			throw new KeystoreFault(ex);
		} catch (InvalidKeySpecException ex) {
			throw new KeystoreFault(ex);
		} catch (KeyStoreException ex) {
			throw new KeystoreFault(ex);
		} catch (CertificateException ex) {
			
		}
	}
	/**
	 * Extract a PKCS#8 encoded key spec from a potentially binary or textual
	 * input file.
	 * @param in the input stream, cannot be null
	 * @return the encoded key bytes
	 * @throws IOException if an IO error occurs or the text file is corrupted
	 */
	private byte[] getEncodedKeyFrom(InputStream in) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream(4096);
		byte[] buffer = new byte[4096];
		int read = 0;
		while ((read = in.read(buffer)) >= 0) {
			if (read > 0) {
				out.write(buffer, 0, read);
			}
		}
		buffer = out.toByteArray();
		String asText = new String(buffer, "ISO-8859-1");
		// check for the textual private key signal
		int idx = asText.indexOf(BEGIN_PRIVATE_KEY);
		if (idx >= 0) {
			int idxEnd = asText.indexOf(END_PRIVATE_KEY);
			if (idxEnd < 0) {
				throw new IOException("Missing " + END_PRIVATE_KEY);
			}
			StringBuilder nowhitespaces = new StringBuilder();
			for (int i = idx + BEGIN_PRIVATE_KEY.length(); i < idxEnd; i++) {
				char c = asText.charAt(i);
				if (!Character.isWhitespace(c)) {
					nowhitespaces.append(c);
				}
			}
			buffer = Base64.decode(nowhitespaces.toString());
		}
		return buffer;
	}
	/**
	 * Set the certificate for the given alias.
	 * Might throw KeyStoreFault.
	 * @param alias the alias, cannot be null
	 * @param cert the certificate, cannot be null
	 */
	public void setCertificate(String alias, Certificate cert) {
		try {
			keystore.setCertificateEntry(alias, cert);
		} catch (KeyStoreException ex) {
			throw new KeystoreFault(ex);
		}
	}
	/**
	 * Create a keystore with a single key from the another keystore.
	 * @param source the source keystore
	 * @param alias the key alias
	 * @param password the key password
	 * @return the new keystore
	 */
	public static KeyStore singleKey(KeyStore source, String alias, char[] password) {
		KeyStore result = null;
		try {
			result = KeyStore.getInstance("JKS");
			result.load(null, null);
	
			Certificate cert = source.getCertificate(alias);
			PrivateKey key = (PrivateKey)source.getKey(alias, password);
			
			result.setKeyEntry(alias, key, password, new Certificate[] { cert });
		} catch (KeyStoreException ex) {
			throw new KeystoreFault(ex);
		} catch (CertificateException ex) {
			throw new KeystoreFault(ex);
		} catch (NoSuchAlgorithmException ex) {
			throw new KeystoreFault(ex);
		} catch (IOException ex) {
			throw new KeystoreFault(ex);
		} catch (UnrecoverableKeyException ex) {
			throw new KeystoreFault(ex);
		}
		return result;
	}
	/**
	 * Create a managed keystore with the single key from this keystore.
	 * @param alias the key alias
	 * @param password the key password
	 * @return the new keystore
	 */
	public KeystoreManager singleKey(String alias, char[] password) {
		KeystoreManager result = new KeystoreManager();
		result.create();

		try {
			Certificate cert = keystore.getCertificate(alias);
			Key key = keystore.getKey(alias, password);
			
			result.keystore.setKeyEntry(alias, key, password, new Certificate[] { cert });
		} catch (KeyStoreException ex) {
			throw new KeystoreFault(ex);
		} catch (NoSuchAlgorithmException ex) {
			throw new KeystoreFault(ex);
		} catch (UnrecoverableKeyException ex) {
			throw new KeystoreFault(ex);
		}

		return result;
	}
	/** Begin private key constant. */
	private static final String BEGIN_PRIVATE_KEY = "-----BEGIN PRIVATE KEY-----";
	/** End private key constant. */
	private static final String END_PRIVATE_KEY = "-----END PRIVATE KEY-----";
}
