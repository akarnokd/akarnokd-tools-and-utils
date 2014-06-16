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

package hu.akarnokd.utils.io;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author akarnokd, 2013.04.17.
 *
 */
public class ByteArrayTest {

	/**
	 * Test method for {@link hu.akarnokd.utils.io.ByteArrayStream#byteAccess(boolean)}.
	 * @throws IOException should never occrr
	 */
	@Test
	public void testByteAccessBoolean() throws IOException {
		try (ByteArrayStream ba = new ByteArrayStream()) {
		
			long c = 0x0123_4567_89AB_CDEFL;
			
			ba.setSize(8);
			ba.byteAccess().set(0, c);
			long d = ba.byteAccess().getLong(0);
			
			Assert.assertEquals(c, d);
		}
	}

	/**
	 * Test method for {@link hu.akarnokd.utils.io.ByteArrayStream#byteAccess(boolean)}.
	 * @throws IOException should never occrr
	 */
	@Test
	public void testByteAccessBoolean2() throws IOException {
		try (ByteArrayStream ba = new ByteArrayStream()) {
		
			long c = 0x0123_4567_89AB_CDEFL;
			
			ba.setSize(8);
			ba.byteAccess(true).set(0, c);
			long d = ba.byteAccess(true).getLong(0);
			
			Assert.assertEquals(c, d);
		}
	}

}
