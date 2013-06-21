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
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.IllegalBlockSizeException;

import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * Base class for password-based encryption routines.
 * @author akarnokd, 2013.05.30.
 */
public abstract class AbstractPBE implements Closeable {
	/** The provider. */
	protected String provider = "BC";
	/** The common salt. */
	protected byte[] salt;
	/** The iteration count. */
	protected int iteration = 11011;
	/** The password. */
	protected char[] password;
	/**
	 * Sets a new provider.
	 * @param provider the provider
	 */
	public final void setProvider(String provider) {
		this.provider = provider;
	}
	/**
	 * Sets the salt.
	 * @param salt the salt
	 */
	public final void setSalt(byte[] salt) {
		this.salt = salt.clone();
	}
	/**
	 * Generate the salt.
	 * @param size the size
	 */
	public final void generateSalt(int size) {
		this.salt = CryptoUtils.generateSalt(size);
	}
	/**
	 * Returns the current salt or null if salt is not yet set.
	 * @return the current salt
	 */
	public final byte[] getSalt() {
		return salt != null ? salt.clone() : null;
	}
	/**
	 * Set the iteration count.
	 * @param iteration the iteration count
	 */
	public final void setIteration(int iteration) {
		if (iteration <= 0) {
			throw new IllegalArgumentException("iteration <= 0");
		}
		this.iteration = iteration;
	}
	/**
	 * Set the password.
	 * @param password the password
	 */
	public final void setPassword(char[] password) {
		this.password = password == null ? new char[0] : password.clone();
	}
	/**
	 * Creates a cipher from the current settings.
	 * @param encrypt return an encrypting cipher?
	 * @return the cipher
	 */
	@NonNull
	protected abstract Cipher cipher(boolean encrypt);
	/**
	 * Encrypt the given data array.
	 * @param data the data to encrypt
	 * @return the encrypted bytes
	 */
	public byte[] encrypt(byte[] data) {
		try {
			Cipher cipher = cipher(true);
			return cipher.doFinal(data);
		} catch (BadPaddingException
				| IllegalBlockSizeException ex) {
			throw new IllegalStateException(ex);
		}
	}
	/**
	 * Wraps the given stream into an encrypting cipher stream.
	 * @param in the input stream
	 * @return the cipher stream
	 */
	public CipherInputStream encrypt(InputStream in) {
		return new CipherInputStream(in, cipher(true));
	}
	/**
	 * Wraps the given stream into an encrypting cipher stream.
	 * @param out the output stream
	 * @return the cipher stream
	 */
	public CipherOutputStream encrypt(OutputStream out) {
		return new CipherOutputStream(out, cipher(true));
	}
	/**
	 * Wraps the given stream into an decrypting cipher stream.
	 * @param out the output stream
	 * @return the cipher stream
	 */
	public CipherOutputStream decrypt(OutputStream out) {
		return new CipherOutputStream(out, cipher(false));
	}
	/**
	 * Decrypt the given data array.
	 * @param data the data to encrypt
	 * @return the encrypted bytes
	 */
	public byte[] decrypt(byte[] data) {
		try {
			Cipher cipher = cipher(false);
			return cipher.doFinal(data);
		} catch (BadPaddingException
				| IllegalBlockSizeException ex) {
			throw new IllegalStateException(ex);
		}
	}
	/**
	 * Wraps the given stream into an decrypting cipher stream.
	 * @param in the input stream
	 * @return the cipher stream
	 */
	public CipherInputStream decrypt(InputStream in) {
		return new CipherInputStream(in, cipher(false));
	}
	@Override
	public void close() {
		Arrays.fill(password, '\0');
	}
	/**
	 * Returns the iteration count.
	 * @return the iteration count
	 */
	public int getIteration() {
		return iteration;
	}
}
