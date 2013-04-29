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

package hu.akarnokd.utils.sequence;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

import com.google.common.collect.Sets;

/**
 * Test the CollectionUtils methods.
 * @author akarnokd, 2013.04.29.
 *
 */
public class CollectionUtilsTest {

	/**
	 * Test method for {@link hu.akarnokd.utils.sequence.CollectionUtils#minKey(java.util.Map)}.
	 */
	@Test
	public void testMinKey() {
		Map<Integer, Integer> map = new HashMap<>();
		map.put(1, 1);
		map.put(2, 2);
		
		Assert.assertEquals((Object)1, CollectionUtils.minKey(map));
	}

	/**
	 * Test method for {@link hu.akarnokd.utils.sequence.CollectionUtils#maxKey(java.util.Map)}.
	 */
	@Test
	public void testMaxKey() {
		Map<Integer, Integer> map = new HashMap<>();
		map.put(1, 1);
		map.put(2, 2);
		
		Assert.assertEquals((Object)2, CollectionUtils.maxKey(map));
	}

	/**
	 * Test method for {@link hu.akarnokd.utils.sequence.CollectionUtils#minKeys(java.util.Map)}.
	 */
	@Test
	public void testMinKeys() {
		Map<Integer, Integer> map = new HashMap<>();
		map.put(1, 1);
		map.put(2, 1);
		map.put(3, 2);
		
		Assert.assertEquals(Sets.newHashSet(1, 2), CollectionUtils.minKeys(map));
	}

	/**
	 * Test method for {@link hu.akarnokd.utils.sequence.CollectionUtils#maxKeys(java.util.Map)}.
	 */
	@Test
	public void testMaxKeys() {
		Map<Integer, Integer> map = new HashMap<>();
		map.put(1, 1);
		map.put(2, 2);
		map.put(3, 2);
		
		Assert.assertEquals(Sets.newHashSet(2, 3), CollectionUtils.maxKeys(map));
	}

}
