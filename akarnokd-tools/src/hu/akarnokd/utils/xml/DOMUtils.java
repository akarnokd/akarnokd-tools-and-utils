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

import hu.akarnokd.reactive4java.base.Pred1;
import hu.akarnokd.reactive4java.query.IterableBuilder;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * Utility class to convert DOM access into
 * more convenient iterables.
 * @author akarnokd, 2013.04.18.
 */
public final class DOMUtils {
	/** Utility class. */
	private DOMUtils() { }
	/**
	 * Returns a sequence of child nodes.
	 * @param parent the parent node
	 * @return the sequence
	 */
	@NonNull
	public static Iterable<Node> childNodes(@NonNull final Node parent) {
		return new Iterable<Node>() {
			@Override
			public Iterator<Node> iterator() {
				return new Iterator<Node>() {
					/** The current view element. */
					Node current = parent.getFirstChild();
					/** Is the stream finished? */
					boolean done = current == null;
					/** We have moved already? */
					boolean moved = true;
					@Override
					public boolean hasNext() {
						if (!done) {
							if (!moved) {
								moved = true;
								current = current.getNextSibling();
								done = current == null;
							}
						}
						return !done;
					}
					@Override
					public Node next() {
						if (hasNext()) {
							moved = false;
							return current;
						}
						throw new NoSuchElementException();
					}
					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}
	/**
	 * Returns the sequence of elements only from the parent node.
	 * @param parent the parent node
	 * @return the iterable sequence
	 */
	@NonNull
	public static Iterable<Element> childElements(@NonNull Node parent) {
		return IterableBuilder.from(childNodes(parent))
		.where(new Pred1<Node>() {
			@Override
			public Boolean invoke(Node param1) {
				return param1.getNodeType() == Node.ELEMENT_NODE;
			}
		}).cast(Element.class);
	}
	/**
	 * Returns the child elements with the specified name.
	 * @param parent the parent node
	 * @param name the names
	 * @return the iterable sequence of the named children
	 */
	public static Iterable<Element> childrenWithName(
			@NonNull Node parent, 
			@NonNull final String name) {
		return IterableBuilder.from(childNodes(parent))
		.where(new Pred1<Node>() {
			@Override
			public Boolean invoke(Node param1) {
				return param1.getNodeType() == Node.ELEMENT_NODE
						&& param1.getNodeName().equals(name);
			}
		}).cast(Element.class);
	}
	/**
	 * Returns the child elements with the specified name.
	 * @param parent the parent node
	 * @param name the names
	 * @param namespaceURI the namespace uri
	 * @return the iterable sequence of the named children
	 */
	public static Iterable<Element> childrenWithName(
			@NonNull Node parent, 
			@NonNull final String name,
			@NonNull final String namespaceURI) {
		return IterableBuilder.from(childNodes(parent))
		.where(new Pred1<Node>() {
			@Override
			public Boolean invoke(Node param1) {
				return param1.getNodeType() == Node.ELEMENT_NODE
						&& param1.getNodeName().equals(name)
						&& Objects.equals(param1.getNamespaceURI(), namespaceURI);
			}
		}).cast(Element.class);
	}
	/**
	 * Returns the value of the first child with the given name.
	 * @param parent the parent node
	 * @param name the element name
	 * @return the textual value or null if not found
	 */
	@NonNull
	public static String childValue(
			@NonNull Node parent, 
			@NonNull String name) {
		Element e = childElement(parent, name);
		return e != null ? e.getTextContent() : null;
	}
	/**
	 * Returns the value of the first child with the given name.
	 * @param parent the parent node
	 * @param name the element name
	 * @param namespaceURI the namespace uri
	 * @return the textual value or null if not found
	 */
	@NonNull
	public static String childValue(
			@NonNull Node parent, 
			@NonNull String name,
			@NonNull final String namespaceURI) {
		Element e = childElement(parent, name, namespaceURI);
		return e != null ? e.getTextContent() : null;
	}
	/**
	 * Returns the first child with the given name.
	 * @param parent the parent node
	 * @param name the element name
	 * @return the textual value or null if not found
	 */
	@NonNull
	public static Element childElement(
			@NonNull Node parent, 
			@NonNull String name) {
		for (Element e : childrenWithName(parent, name)) {
			return e;
		}
		return null;
	}
	/**
	 * Returns the first child with the given name.
	 * @param parent the parent node
	 * @param name the element name
	 * @param namespaceURI the namespace uri
	 * @return the textual value or null if not found
	 */
	@NonNull
	public static Element childElement(
			@NonNull Node parent, 
			@NonNull String name,
			@NonNull final String namespaceURI) {
		for (Element e : childrenWithName(parent, name, namespaceURI)) {
			return e;
		}
		return null;
	}
}
