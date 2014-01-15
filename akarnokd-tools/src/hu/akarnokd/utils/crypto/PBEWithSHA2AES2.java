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

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * Very simple password based encryptor and decryptor using
 * SHA-256 hash and AES-256 key generator.
 * Requires the unlimited juristiction libraries from Oracle.
 * @author akarnokd, 2013.05.30.
 *
 */
public class PBEWithSHA2AES2 extends AbstractPBE {
	/** The parameter specification. */
	private PBEParameterSpec pbeParamSpec;
	/** The secret key. */
	private SecretKey pbeKey;
	@NonNull
	@Override
	protected Cipher cipher(boolean encrypt) {
		try {
			String algorithm = "PBEWithSHA256And256BITAES-CBC-BC";
			if (pbeParamSpec == null) {
				pbeParamSpec = new PBEParameterSpec(salt, iteration);
				PBEKeySpec pbeKeySpec = new PBEKeySpec(password, salt, iteration, 256);
				SecretKeyFactory keyFac = null;
				if (provider != null) {
					keyFac = SecretKeyFactory.getInstance(algorithm, provider);
				} else {
					keyFac = SecretKeyFactory.getInstance(algorithm);
				}
				pbeKey = keyFac.generateSecret(pbeKeySpec);
			}
			
			Cipher cipher = null;
			if (provider != null) {
				cipher = Cipher.getInstance(algorithm, provider);
			} else {
				cipher = Cipher.getInstance(algorithm);
			}
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE, pbeKey, pbeParamSpec);
			return cipher;
		} catch (NoSuchAlgorithmException
				| NoSuchProviderException
				| InvalidKeySpecException
				| NoSuchPaddingException 
				| InvalidKeyException 
				| InvalidAlgorithmParameterException ex) {
			throw new IllegalStateException(ex);
		}
	}
	@Override
	public void close() {
		pbeParamSpec = null;
		pbeKey = null;
		super.close();
	}
}
