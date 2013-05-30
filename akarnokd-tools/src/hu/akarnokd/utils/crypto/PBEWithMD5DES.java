/*
 * Copyright 2012-2013 David Karnok
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

import java.io.Closeable;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

/**
 * Very simple and weak password based encryptor and decryptor using
 * MD5 hash and DES key generator.
 * @author akarnokd, 2013.05.30.
 *
 */
public class PBEWithMD5DES implements Closeable {
	/** The provider. */
	private String provider = "BC";
	/** The common salt. */
	private byte[] salt;
	/** The iteration count. */
	private int iteration = 11011;
	/** The password. */
	private char[] password;
	/** The initialized cipher. */
	private Cipher pbeCipher;
	/** The parameter specification. */
	private PBEParameterSpec pbeParamSpec;
	/** The secret key factory. */
	private SecretKeyFactory keyFac;
	/** The secret key. */
	private SecretKey pbeKey;
	/**
	 * Sets a new provider.
	 * @param provider the provider
	 */
	public void setProvider(String provider) {
		this.provider = provider;
	}
	/**
	 * Sets the salt.
	 * @param salt the salt
	 */
	public void setSalt(byte[] salt) {
		this.salt = salt.clone();
	}
	/**
	 * Set the iteration count.
	 * @param iteration the iteration count
	 */
	public void setIteration(int iteration) {
		if (iteration <= 0) {
			throw new IllegalArgumentException("iteration <= 0");
		}
		this.iteration = iteration;
	}
	/**
	 * Set the password.
	 * @param password the password
	 */
	public void setPassword(char[] password) {
		this.password = password == null ? new char[0] : password.clone();
	}
	/**
	 * Initialize the cipher.
	 */
	private void init() {
		try {
			pbeParamSpec = new PBEParameterSpec(salt, iteration);
			PBEKeySpec pbeKeySpec = new PBEKeySpec(password);
			keyFac = null;
			if (provider != null) {
				keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES", provider);
			} else {
				keyFac = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
			}
			pbeKey = keyFac.generateSecret(pbeKeySpec);
			
			if (provider != null) {
				pbeCipher = Cipher.getInstance("PBEWithMD5AndDES", provider);
			} else {
				pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
			}
		} catch (NoSuchAlgorithmException
				| NoSuchProviderException
				| InvalidKeySpecException
				| NoSuchPaddingException ex) {
			throw new IllegalStateException(ex);
		}
	}
	public void encrypt(byte[] data) {
		if (pbeCipher == null) {
			init();
		}
		pbeCipher.init(Cipher.ENCRYPT_MODE, pbeKey, pbeParamSpec);

	}
	@Override
	public void close() throws IOException {
		Arrays.fill(password, '\0');
	}
}
