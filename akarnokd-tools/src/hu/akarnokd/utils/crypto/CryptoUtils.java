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

import ix.Pair;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.digests.SHA224Digest;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.digests.SHA384Digest;
import org.bouncycastle.crypto.digests.SHA512Digest;
import org.bouncycastle.crypto.prng.DigestRandomGenerator;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * Cryptographic utilities.
 * Requires unlimited juristiction JCE files, even with BouncyCastle!
 * @author akarnokd, 2013.05.30.
 */
public final class CryptoUtils {
	/** Utility class. */
	private CryptoUtils() { }
	/** The default key algorithm. */
	private static final String DEFAULT_PBE_KEY_ALG = "PBEWITHSHA%1$dAND%1$dBITAES-CBC-BC"; //"PBKDF2WithHmacSHA1";
	/** The default key length. */
	private static final int DEFAULT_PBE_KEY_BITS = 256;
	/** The default iteration count. */
	private static final int DEFAULT_PBE_ITERATION = 11087;
	/** The default cipher algorithm. */
	private static final String DEFAULT_PBE_CIPHER_ARG = "AES/CBC/PKCS5Padding";
	/** The default salt algorithm. */
	private static final String DEFAULT_SALT_ALG = "SHA%dPRNG";
	/** The default provider. */
	private static final String DEFAULT_PROVIDER = "BC";
	static {
		Security.addProvider(new BouncyCastleProvider());
	}
	/**
	 * Generates salt with the given length.
	 * @param size the number of bytes
	 * @return the salt bytes
	 */
	@NonNull
	public static byte[] generateSalt(int size) {
		Digest digest = null;
		
		switch (String.format(DEFAULT_SALT_ALG, DEFAULT_PBE_KEY_BITS)) {
		case "SHA224PRNG":
			digest = new SHA224Digest();
			break;
		case "SHA256PRNG":
			digest = new SHA256Digest();
			break;
		case "SHA384PRNG":
			digest = new SHA384Digest();
			break;
		case "SHA512PRNG":
			digest = new SHA512Digest();
			break;
		default:
			digest = new SHA1Digest();
		}
		
		DigestRandomGenerator drg = new DigestRandomGenerator(digest);
		drg.addSeedMaterial(System.currentTimeMillis());
		byte[] r = new byte[size];
		drg.nextBytes(r);
		return r;
	}
	/**
	 * Encrypt data with password based encryption using
	 * SHA256 and AES algorithms.
	 * @param password the password
	 * @param salt the salt
	 * @param data the data to encrypt or decrypt
	 * @return the pair of the initialization vector and the encrypted data.
	 */
	public static Pair<byte[], byte[]> passwordEncrypt(@NonNull char[] password, @NonNull byte[] salt, 
			@NonNull byte[] data) {
		if (salt.length != 8) {
			throw new IllegalArgumentException("Salt length must be 8 long");
		}
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance(String.format(DEFAULT_PBE_KEY_ALG, DEFAULT_PBE_KEY_BITS), DEFAULT_PROVIDER);
			KeySpec spec = new PBEKeySpec(password, salt, DEFAULT_PBE_ITERATION, DEFAULT_PBE_KEY_BITS);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
			
			Cipher cipher = Cipher.getInstance(DEFAULT_PBE_CIPHER_ARG, DEFAULT_PROVIDER);
			
			cipher.init(Cipher.ENCRYPT_MODE, secret);
			
			AlgorithmParameters params = cipher.getParameters();
			byte[] iv = params.getParameterSpec(IvParameterSpec.class).getIV();
			byte[] ct = cipher.doFinal(data);
			
			return Pair.of(iv, ct);
		} catch (NoSuchAlgorithmException | InvalidKeySpecException
				| NoSuchPaddingException | InvalidKeyException 
				| BadPaddingException | IllegalBlockSizeException
				| InvalidParameterSpecException 
				| NoSuchProviderException ex) {
			throw new IllegalArgumentException(ex);
		}
	}
	/**
	 * Decrypts the data using the given password, salt and initialization vector
	 * and the default specifications.
	 * @param password the password
	 * @param salt the salt
	 * @param iv the initialization vector
	 * @param data the data to decrypt
	 * @return the decrypted data
	 */
	public static byte[] passwordDecrypt(@NonNull char[] password, @NonNull byte[] salt, @NonNull byte[] iv, @NonNull byte[] data) {
		if (salt.length != 8) {
			throw new IllegalArgumentException("Salt length must be 8 long");
		}
		try {
			SecretKeyFactory factory = SecretKeyFactory.getInstance(String.format(DEFAULT_PBE_KEY_ALG, DEFAULT_PBE_KEY_BITS), DEFAULT_PROVIDER);
			KeySpec spec = new PBEKeySpec(password, salt, DEFAULT_PBE_ITERATION, DEFAULT_PBE_KEY_BITS);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKey secret = new SecretKeySpec(tmp.getEncoded(), "AES");
			
			Cipher cipher = Cipher.getInstance(DEFAULT_PBE_CIPHER_ARG, DEFAULT_PROVIDER);
			
			cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
		
			return cipher.doFinal(data);
			
		} catch (NoSuchAlgorithmException | InvalidKeySpecException
				| NoSuchPaddingException | InvalidKeyException 
				| BadPaddingException | IllegalBlockSizeException
				| InvalidAlgorithmParameterException 
				| NoSuchProviderException ex) {
			throw new IllegalArgumentException(ex);
		}
	}
}
