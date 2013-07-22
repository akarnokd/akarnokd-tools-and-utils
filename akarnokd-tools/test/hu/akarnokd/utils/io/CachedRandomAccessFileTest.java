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

package hu.akarnokd.utils.io;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.io.Files;

/**
 * Test the Cached random access file object.
 * @author akarnokd, 2013.07.22.
 *
 */
public class CachedRandomAccessFileTest {
	/**
	 * Test simple and byte-array read.
	 * @throws Exception on error
	 */
	@Test
	public void test() throws Exception {
		File test = File.createTempFile("cached_random_access_file", ".dat");
		try {
			Files.write(new byte[] { 1, 0, 0, 0, 0, 0, 0, 0 }, test);
			try (CachedRandomAccessFile craf = new CachedRandomAccessFile(test, 2, 3)) {
				long v = craf.getLong(0);
				Assert.assertEquals(0x01_00_00_00_00_00_00_00L, v);
				
				byte[] b = new byte[8];
				
				craf.get(0, b, 0, 8);
				
				Assert.assertEquals(1, b[0]);
				Assert.assertEquals(0, b[1]);
			}
		} finally {
			test.delete();
		}
	}

}
