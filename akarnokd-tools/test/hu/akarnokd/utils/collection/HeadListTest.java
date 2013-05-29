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

package hu.akarnokd.utils.collection;

import hu.akarnokd.reactive4java.base.Pred1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test the HeadList.
 * @author akarnokd, 2013.05.29.
 */
public class HeadListTest {

	/**
	 * Test method for {@link hu.akarnokd.utils.collection.HeadList#add(java.lang.Object)}.
	 */
	@Test
	public void testAdd() {
		HeadList<Integer> hl = HeadList.from(1);
		HeadList<Integer> hl1 = hl.add(2);
		
		Object[] a = hl1.toArray();
		
		Assert.assertArrayEquals(new Object[] { 1, 2 }, a);
	}

	/**
	 * Test method for {@link hu.akarnokd.utils.collection.HeadList#addTo(java.util.List)}.
	 */
	@Test
	public void testAddToListOfQsuperT() {
		HeadList<Integer> hl = HeadList.from(1, 2);
		List<Integer> lst = new ArrayList<>();
		lst.add(3);
		
		hl.addTo(lst);
		
		Assert.assertEquals(Arrays.asList(3, 1, 2), lst);
	}

	/**
	 * Test method for {@link hu.akarnokd.utils.collection.HeadList#removeIf(hu.akarnokd.reactive4java.base.Func1)}.
	 */
	@Test
	public void testRemoveIf() {
		HeadList<Integer> hl = HeadList.from(1, 2, 3, 4, 5, 6);
		
		HeadList<Integer> hl1 = hl.removeIf(new Pred1<Integer>() {
			@Override
			public Boolean invoke(Integer param1) {
				return param1 % 2 == 0;
			}
		});

		Object[] a = hl1.toArray();
		
		Assert.assertArrayEquals(new Object[] { 1, 3, 5 }, a);
	}
	/** Test the addFirst method. */
	@Test
	public void testAddFirst() {
		HeadList<Integer> hl = HeadList.from(2, 3);
		HeadList<Integer> hl1 = hl.addFirst(1);
		Object[] a = hl1.toArray();
		
		Assert.assertArrayEquals(new Object[] { 1, 2, 3 }, a);
	}
	/** Test the toString method. */
	@Test
	public void testToString() {
		HeadList<Integer> hl = HeadList.from(1, 2, 3);
		String s = hl.toString();
		
		Assert.assertEquals("[1, 2, 3]", s);
	}
	/** Test the sublist(startIndex) method. */
	@Test
	public void testSubList() {
		HeadList<Integer> hl = HeadList.from(1, 2, 3);
		HeadList<Integer> hl1 = hl.subList(1);
		
		Object[] a = hl1.toArray();
		
		Assert.assertArrayEquals(new Object[] { 2, 3 }, a);
	}
	/** Test the sublist(startIndex, endIndex) method. */
	@Test
	public void testSubList2() {
		HeadList<Integer> hl = HeadList.from(1, 2, 3, 4);
		HeadList<Integer> hl1 = hl.subList(1, 3);
		
		Object[] a = hl1.toArray();
		
		Assert.assertArrayEquals(new Object[] { 2, 3 }, a);
	}
	/** Test toTailList(). */
	@Test
	public void toTailList() {
		HeadList<Integer> hl = HeadList.from(1, 2, 3, 4);
		TailList<Integer> tl = hl.toTailList();

		Object[] a = tl.toArray();
		
		Assert.assertArrayEquals(new Object[] { 1, 2, 3, 4 }, a);
	}
}
