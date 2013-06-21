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

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author akarnokd, 2013.06.21.
 *
 */
public class StringUtilsTest {

	/**
	 * Test method for {@link hu.akarnokd.utils.lang.StringUtils#replaceAll(java.lang.String, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testReplaceAll() {
		String s = StringUtils.replaceAll("ABCDEF", "A", "G");
		Assert.assertEquals(s, "GBCDEF");
		s = StringUtils.replaceAll("ABCDEF", "F", "G");
		Assert.assertEquals(s, "ABCDEG");
		s = StringUtils.replaceAll("ABABAB", "AB", "C");
		Assert.assertEquals(s, "CCC");
	}

}
