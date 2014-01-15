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

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author akarnokd, 2013.06.28.
 *
 */
public class CollectionUtilsTest {

	/**
	 * Test method for {@link hu.akarnokd.utils.collection.CollectionUtils#findFirst(java.util.List, int, int, java.lang.Object, java.util.Comparator)}.
	 */
	@Test
	public void testFindFirstListOfQextendsTIntIntTComparatorOfQsuperT() {
		List<Integer> test = Arrays.asList(0, 1, 1, 2, 2, 2, 3, 3, 5, 5);
		int idx = CollectionUtils.findFirst(test, 0, test.size(), 2);
		Assert.assertEquals(3, idx);
		
		idx = CollectionUtils.findFirst(test, 0, test.size(), 4);
		Assert.assertEquals(-9, idx);
		
		idx = CollectionUtils.findLast(test, 0, test.size(), 2);
		Assert.assertEquals(5, idx);
		
		idx = CollectionUtils.findLast(test, 0, test.size(), 4);
		Assert.assertEquals(-9, idx);
	}

}
