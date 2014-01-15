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
	/** Test the array indexOf with array. */
	@Test
	public void testArrayIndexOfArray() {
		boolean[] b = { false, false, true, true };
		boolean[] t = { false, true };
		boolean[] f = { true, false };
		boolean[] c = { false, false, true };
		boolean[] d = { true };
		
		Assert.assertEquals(1, ArrayUtils.indexOf(b, t));
		Assert.assertEquals(-1, ArrayUtils.indexOf(b, f));

		Assert.assertEquals(1, ArrayUtils.lastIndexOf(b, t));
		Assert.assertEquals(-1, ArrayUtils.lastIndexOf(b, f));
		
		Assert.assertEquals(2, ArrayUtils.indexOf(c, d));
		Assert.assertEquals(2, ArrayUtils.lastIndexOf(c, d));

		Assert.assertEquals(2, ArrayUtils.indexOf(b, 0, 3, t, 1, 2));
		Assert.assertEquals(2, ArrayUtils.indexOf(b, 2, 3, t, 1, 2));
		
		Assert.assertEquals(2, ArrayUtils.lastIndexOf(b, 0, 3, t, 1, 2));
		Assert.assertEquals(2, ArrayUtils.lastIndexOf(b, 2, 3, t, 1, 2));
	}
	/** Test odd reverse. */
	@Test
	public void testArrayReverse() {
		int[] array = ArrayUtils.reverse(1, 2, 3);
		
		Assert.assertArrayEquals(new int[] { 3, 2, 1}, array);
	}
	/** Test even reverse. */
	@Test
	public void testArrayReverse2() {
		int[] array = ArrayUtils.reverse(1, 2, 3, 4);
		
		Assert.assertArrayEquals(new int[] { 4, 3, 2, 1}, array);
	}
}
