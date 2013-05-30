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

import org.junit.Assert;
import org.junit.Test;

/**
 * Test the crypto utils.
 * @author akarnokd, 2013.05.30.
 *
 */
public class PBEWithSHA2AES2Test {
	/**
	 * Test method for {@link hu.akarnokd.utils.crypto.CryptoUtils#passwordEncryptDecrypt(char[], byte[], int, java.lang.String, byte[], boolean)}.
	 */
	@Test
	public void testPasswordEncryptDecrypt() {
		char[] password = "password".toCharArray();
		byte[] data = "secret".getBytes();

		try (AbstractPBE pbe = new PBEWithSHA2AES2()) {
			pbe.setPassword(password);
			pbe.generateSalt(8);
			
			byte[] edata = pbe.encrypt(data);
			byte[] ddata = pbe.decrypt(edata);
			
			Assert.assertArrayEquals(data, ddata);
		}
	}

}
