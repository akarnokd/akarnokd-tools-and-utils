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

package hu.akarnokd.utils.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import rx.functions.Func1;

/**
 * Test the Tail list methods.
 * @author akarnokd, 2013.05.29.
 */
public class TailListTest {

	/**
	 * Test method for {@link hu.akarnokd.utils.collection.TailList#add(java.lang.Object)}.
	 */
	@Test
	public void testAdd() {
		TailList<Integer> tl = new TailList<>(1);
		Object[] a1 = tl.toArray();
		Assert.assertArrayEquals(new Object[] { 1 }, a1);
		
		TailList<Integer> tl2 = tl.add(2);
		Object[] a2 = tl2.toArray();
		Assert.assertArrayEquals(new Object[] { 1, 2 }, a2);
	}

	/**
	 * Test method for {@link hu.akarnokd.utils.collection.TailList#addFirst(java.lang.Object)}.
	 */
	@Test
	public void testAddFirst() {
		TailList<Integer> tl = new TailList<>(1);
		TailList<Integer> tl2 = tl.addFirst(2);
		
		Object[] a2 = tl2.toArray();
		Assert.assertArrayEquals(new Object[] { 2, 1 }, a2);
	}

	/**
	 * Test method for {@link hu.akarnokd.utils.collection.TailList#addTo(java.util.Collection)}.
	 */
	@Test
	public void testAddTo() {
		TailList<Integer> tl = new TailList<>(1);
		TailList<Integer> tl2 = tl.addFirst(2);
		List<Integer> out = new ArrayList<>();
		
		tl2.addTo(out);
		
		Assert.assertEquals(Arrays.asList(2, 1), out);
	}

	/**
	 * Test method for {@link hu.akarnokd.utils.collection.TailList#from(java.lang.Iterable)}.
	 */
	@Test
	public void testFrom() {
		TailList<Integer> tl = TailList.from(Arrays.asList(1, 2, 3));

		Object[] a1 = tl.toArray();
		Assert.assertArrayEquals(new Object[] { 1, 2, 3 }, a1);
	}
	/**
	 * Test addAll.
	 */
	@Test
	public void testAddAll() {
		TailList<Integer> tl = new TailList<>(1);
		TailList<Integer> tl1 = tl.addAll(Arrays.asList(2, 3));

		Object[] a1 = tl1.toArray();
		Assert.assertArrayEquals(new Object[] { 1, 2, 3 }, a1);
	}
	/**
	 * Test the removeIf method.
	 */
	@Test
	public void testRemoveIf() {
		TailList<Integer> tl = TailList.from(1, 2, 3, 4);
		
		TailList<Integer> tl1 = tl.removeIf(new Func1<Integer, Boolean>() {
			@Override
			public Boolean call(Integer param1) {
				return param1 % 2 == 0;
			}
		});
		
		Object[] a1 = tl1.toArray();
		Assert.assertArrayEquals(new Object[] { 1, 3 }, a1);
	}
	/** Test the sublist(startIndex) method. */
	@Test
	public void testSubList() {
		TailList<Integer> hl = TailList.from(1, 2, 3);
		TailList<Integer> hl1 = hl.subList(1);
		
		Object[] a = hl1.toArray();
		
		Assert.assertArrayEquals(new Object[] { 2, 3 }, a);
	}
	/** Test the sublist(startIndex, endIndex) method. */
	@Test
	public void testSubList2() {
		TailList<Integer> hl = TailList.from(1, 2, 3, 4);
		TailList<Integer> hl1 = hl.subList(1, 3);
		
		Object[] a = hl1.toArray();
		
		Assert.assertArrayEquals(new Object[] { 2, 3 }, a);
	}
	/** Test toHeadList(). */
	@Test
	public void toHeadList() {
		TailList<Integer> tl = TailList.from(1, 2, 3, 4);
		HeadList<Integer> hl = tl.toHeadList();

		Object[] a = hl.toArray();
		
		Assert.assertArrayEquals(new Object[] { 1, 2, 3, 4 }, a);
	}
}
