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

package hu.akarnokd.utils.xml;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.collect.Iterables;

/**
 * Test DOMUtils methods.
 * @author akarnokd, 2013.04.18.
 *
 */
public class DOMUtilsTest {
	/**
	 * Test the child nodes iteration.
	 * @throws Exception on error
	 */
	@Test
	public void testChildNodes() throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.newDocument();
		
		Element e = doc.createElement("root");
		for (int i = 0; i < 10; i++) {
			Element e0 = doc.createElement("e" + i);
			e.appendChild(e0);
		}

		int count = Iterables.size(DOMUtils.childNodes(e));
		
		Assert.assertEquals(10, count);
	}
	/**
	 * Test the child nodes iteration.
	 * @throws Exception on error
	 */
	@Test
	public void testEmptyChildNodes() throws Exception {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.newDocument();
		
		Element e = doc.createElement("root");

		int count = Iterables.size(DOMUtils.childNodes(e));
		
		Assert.assertEquals(0, count);
	}

}
