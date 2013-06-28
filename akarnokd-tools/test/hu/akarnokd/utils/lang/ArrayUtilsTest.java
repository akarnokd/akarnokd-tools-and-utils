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

package hu.akarnokd.utils.lang;

import org.junit.Assert;

import org.junit.Test;

/**
 * Test ArrayUtils.
 * @author akarnokd, 2013.06.28.
 */
public class ArrayUtilsTest {

	/**
	 * Test method for {@link hu.akarnokd.utils.lang.ArrayUtils#findFirst(byte[], int, int, byte)}.
	 */
	@Test
	public void testFindFirstByteArrayIntIntByte() {
		byte[] test = { 0, 1, 1, 2, 2, 2, 3, 3, 5, 5 };
		int idx = ArrayUtils.findFirst(test, 0, test.length, (byte)2);
		Assert.assertEquals(3, idx);
		
		idx = ArrayUtils.findFirst(test, 0, test.length, (byte)4);
		Assert.assertEquals(-9, idx);
		
		idx = ArrayUtils.findLast(test, 0, test.length, (byte)2);
		Assert.assertEquals(5, idx);
		
		idx = ArrayUtils.findLast(test, 0, test.length, (byte)4);
		Assert.assertEquals(-9, idx);
		
	}

}
