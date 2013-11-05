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

package hu.akarnokd.utils.generator;

import org.junit.Test;

/**
 * @author akarnokd, 2013.11.02.
 *
 */
public class CodeCreatorTest {
	/** The test reflective class. */
	public static class C1 {
		/** Default constructor. */
		public C1() {
			
		}
		/**
		 * One param constructor.
		 * @param p1 the parameter
		 */
		public C1(String p1) {
			
		}
		/**
		 * Two param constructor.
		 * @param p1 the first param
		 * @param p2 the second param
		 */
		public C1(String p1, String p2) {
			
		}
	}
	/** The test reflective class. */
	public static class C2 {
		
	}
	/**
	 * Test method for {@link hu.akarnokd.utils.generator.CodeCreator#createConstructor(java.lang.Class)}.
	 */
	@Test
	public void testCreateConstructorClassOfT() {
		CodeCreator.createConstructor(C1.class).invoke();
		CodeCreator.createConstructor(C2.class).invoke();
	}

	/**
	 * Test method for {@link hu.akarnokd.utils.generator.CodeCreator#createConstructor(java.lang.Class, java.lang.Class)}.
	 */
	@Test
	public void testCreateConstructorClassOfTClassOfU() {
		CodeCreator.createConstructor(C1.class, String.class).invoke("1");
	}

	/**
	 * Test method for {@link hu.akarnokd.utils.generator.CodeCreator#createConstructor(java.lang.Class, java.lang.Class, java.lang.Class)}.
	 */
	@Test
	public void testCreateConstructorClassOfTClassOfUClassOfV() {
		CodeCreator.createConstructor(C1.class, String.class, String.class).invoke("1", "2");
	}

}
