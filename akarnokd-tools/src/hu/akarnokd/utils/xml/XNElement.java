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

import hu.akarnokd.reactive4java.base.Action1;
import hu.akarnokd.reactive4java.base.Func1;
import hu.akarnokd.reactive4java.base.Pair;
import hu.akarnokd.reactive4java.interactive.Interactive;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

/**
 * A simplified XML element model with namespace support.
 * @author akarnokd
 */
public class XNElement extends XElementBase {
	/** The namespace:name record. */
	public static class XAttributeName {
		/** The hash. */
		private int hash;
		/** The name. */
		public final String name;
		/** The namespace. */
		public final String namespace;
		/** The namespace prefix. */
		public final String prefix;
		/**
		 * Construct an XName entity.
		 * @param name the name
		 * @param namespace the namespace
		 * @param prefix the namespace prefix
		 */
		public XAttributeName(String name, String namespace, String prefix) {
			this.name = name;
			this.namespace = namespace;
			this.prefix = prefix;
		}
		@Override
		public boolean equals(Object obj) {
			if (obj instanceof XAttributeName) {
				XAttributeName that = (XAttributeName)obj;
				return Objects.equal(this.name, that.name) && Objects.equal(this.namespace, that.namespace);
			}
			return false;
		}
		@Override
		public int hashCode() {
			if (hash == 0) {
				hash = Objects.hashCode(name, namespace);
			}
			return hash;
		}
		@Override
		public String toString() {
			return "{" + namespace + "}" + name;
		}
	}
	/**
	 * The representation record.
	 * @author akarnokd, 2011.10.21.
	 */
	public static class XRepresentationRecord {
		/** The offset. */
		public final int charOffset;
		/** The current element. */
		public final XNElement element;
		/** The state. */
		public final XRepresentationState state;
		/**
		 * Create the record.
		 * @param state the state
		 * @param element the element
		 * @param charOffset the offset
		 */
		public XRepresentationRecord(XRepresentationState state,
				XNElement element, int charOffset) {
			super();
			this.state = state;
			this.element = element;
			this.charOffset = charOffset;
		}
		
	}
	/**
	 * The representation state.
	 * @author akarnokd, 2011.10.21.
	 */
	public enum XRepresentationState {
		/** Element end. */
		END_ELEMENT,
		/** Text end. */
		END_TEXT,
		/** Element start. */
		START_ELEMENT,
		/** Text start. */
		START_TEXT
	}
	/**
	 * Parse an XML from the binary data.
	 * @param data the XML data
	 * @return the parsed xml
	 * @throws XMLStreamException on error
	 */
	public static XNElement parseXML(byte[] data) throws XMLStreamException {
		return parseXML(new ByteArrayInputStream(data));
	}
	/**
	 * Parse an XML from the given file.
	 * @param file the file
	 * @return the parsed XElement tree
	 * @throws XMLStreamException if an error occurs
	 * @throws IOException if the file could not be found or other I/O error occurs
	 */
	public static XNElement parseXML(File file) throws IOException, XMLStreamException {
		InputStream in = new FileInputStream(file);
		try {
			return parseXML(in);
		} finally {
			in.close();
		}
	}
	/**
	 * Parse XML from the input stream. Does not close the stream.
	 * @param in the InputStream object
	 * @return the parsed XElement tree
	 * @throws XMLStreamException if an error occurs
	 */
	public static XNElement parseXML(InputStream in) throws XMLStreamException {
		XMLInputFactory inf = XMLInputFactory.newInstance();
		XMLStreamReader ir = inf.createXMLStreamReader(in);
		return parseXML(ir);
	}
	/**
	 * Parse an XML from the given Reader.
	 * @param in the Reader
	 * @return the parsed XElement tree
	 * @throws XMLStreamException if an error occurs
	 */
	public static XNElement parseXML(Reader in) throws XMLStreamException {
		XMLInputFactory inf = XMLInputFactory.newInstance();
		XMLStreamReader ir = inf.createXMLStreamReader(in);
		return parseXML(ir);
	}
	/**
	 * Reads the contents of a indexed column as an XML.
	 * @param rs the result set to read from
	 * @param index the column index
	 * @return the parsed XNElement or null if the column contained null
	 * @throws SQLException on SQL error
	 * @throws IOException on IO error
	 * @throws XMLStreamException on parsing error
	 */
	public static XNElement parseXML(ResultSet rs, int index) 
			throws SQLException, IOException, XMLStreamException {
		try (InputStream is = rs.getBinaryStream(index)) {
			if (is != null) {
				return parseXML(is);
			}
			return null;
		}
	}
	/**
	 * Reads the contents of a named column as an XML.
	 * @param rs the result set to read from
	 * @param column the column name
	 * @return the parsed XNElement or null if the column contained null
	 * @throws SQLException on SQL error
	 * @throws IOException on IO error
	 * @throws XMLStreamException on parsing error
	 */
	public static XNElement parseXML(ResultSet rs, String column) 
			throws SQLException, IOException, XMLStreamException {
		try (InputStream is = rs.getBinaryStream(column)) {
			if (is != null) {
				return parseXML(is);
			}
			return null;
		}
	}
	/**
	 * Parse an XML from the given file.
	 * @param fileName the file name
	 * @return the parsed XElement tree
	 * @throws XMLStreamException if an error occurs
	 * @throws IOException if the file could not be found or other I/O error occurs
	 */
	public static XNElement parseXML(String fileName) throws IOException, XMLStreamException {
		InputStream in = new FileInputStream(fileName);
		try {
			return parseXML(in);
		} finally {
			in.close();
		}
	}
	/**
	 * Parse an XML from the supplied URL.
	 * @param url the target URL
	 * @return the parsed XML
	 * @throws IOException if an I/O error occurs
	 * @throws XMLStreamException if a parser error occurs
	 */
	public static XNElement parseXML(URL url) throws IOException, XMLStreamException {
		InputStream in = new BufferedInputStream(url.openStream());
		try {
			return parseXML(in);
		} finally {
			in.close();
		}
	}
	/**
	 * Parse an XML from the given XML Stream reader.
	 * Closes the {@code in} stream.
	 * @param in the XMLStreamReader
	 * @return the parsed XElement tree
	 * @throws XMLStreamException if an error occurs
	 */
	public static XNElement parseXML(XMLStreamReader in) throws XMLStreamException {
		XNElement root = parseXMLFragment(in);
		in.close();
		return root;
	}
	/**
	 * Parses the stream until the end element of the start element is reached, then
	 * returns. The method can be used to parse streamed fragment XML on a per node basis.
	 * It does not close the {@code in} reader.
	 * @param in the input reader
	 * @return the parsed XElement tree
	 * @throws XMLStreamException if an error occurs
	 */
	public static XNElement parseXMLFragment(XMLStreamReader in)
			throws XMLStreamException {
		XNElement node = null;
		XNElement root = null;
		final StringBuilder emptyBuilder = new StringBuilder();
		StringBuilder b = null;
		Deque<StringBuilder> stack = new LinkedList<StringBuilder>();
		
		while (in.hasNext()) {
			int type = in.next();
			switch(type) {
			case XMLStreamConstants.START_ELEMENT:
				if (b != null) {
					stack.push(b);
					b = null;
				} else {
					stack.push(emptyBuilder);
				}
				XNElement n = new XNElement(in.getName().getLocalPart());
				n.namespace = in.getNamespaceURI();
				n.prefix = in.getPrefix();
				n.parent = node;
				int attCount = in.getAttributeCount();
				if (attCount > 0) {
					for (int i = 0; i < attCount; i++) {
						n.attributes.put(new XAttributeName(
								in.getAttributeLocalName(i), 
								in.getAttributeNamespace(i),
								in.getAttributePrefix(i)
						), in.getAttributeValue(i));
					}
				}
				if (node != null) {
					node.children.add(n);
				}
				node = n;
				if (root == null) {
					root = n;
				}
				break;
			case XMLStreamConstants.CDATA:
			case XMLStreamConstants.CHARACTERS:
				if (node != null && !in.isWhiteSpace()) {
					if (b == null) {
						b = new StringBuilder();
					}
					b.append(in.getText());
				}
				break;
			case XMLStreamConstants.END_ELEMENT:
				// if text was found, assign the accumulated text to the node
				if (b != null) {
					node.content = b.toString();
				}
				if (node != null) {
					node = node.parent;
				}
				// return to the parent's builder
				b = stack.pop();
				// if the parent had no text so far
				if (b == emptyBuilder) {
					b = null;
				}
				if (stack.isEmpty()) {
					return root;
				}
				break;
			default:
				// ignore others.
			}
		}
		return root;
	}
	/**
	 * Parse an XML file compressed by GZIP.
	 * @param file the file
	 * @return the parsed XML
	 * @throws XMLStreamException on error
	 */
	public static XNElement parseXMLGZ(File file) throws XMLStreamException {
		try {
			GZIPInputStream gin = new GZIPInputStream(new BufferedInputStream(new FileInputStream(file), 64 * 1024));
			try {
				return parseXML(gin);
			} finally {
				gin.close();
			}
		} catch (IOException ex) {
			throw new XMLStreamException(ex);
		}
	}
	/**
	 * Parse an XML file compressed by GZIP.
	 * @param fileName the filename
	 * @return the parsed XML
	 * @throws XMLStreamException on error
	 */
	public static XNElement parseXMLGZ(String fileName) throws XMLStreamException {
		return parseXMLGZ(new File(fileName));
	}
	/** The attribute map. */
	protected final Map<XAttributeName, String> attributes = new LinkedHashMap<XAttributeName, String>();
	/** The child elements. */
	protected final List<XNElement> children = new ArrayList<XNElement>();
	/** The optional associated namespace uri. */
	public String namespace;
	/** The parent element. */
	public XNElement parent;
	/** The prefix used by this element. */
	public String prefix;
	/**
	 * Constructor. Sets the name.
	 * @param name the element name
	 */
	public XNElement(String name) {
		super(name);
	}
	/**
	 * Creates a new XElement and sets its content according to the supplied value.
	 * @param name the element name
	 * @param value the content value
	 */
	public XNElement(String name, Object value) {
		this(name);
		setValue(value);
	}
	/**
	 * Constructor. Sets the name and namespace.
	 * @param name the element name
	 * @param namespace the element namespace
	 */
	public XNElement(String name, String namespace) {
		super(name);
		this.namespace = namespace;
	}
	/**
	 * Creates a new XElement and sets its content according to the supplied value.
	 * @param name the element name
	 * @param namespace the namespace
	 * @param value the content value
	 */
	public XNElement(String name, String namespace, Object value) {
		this(name, namespace);
		setValue(value);
	}
	/**
	 * Add a new XElement with the given local name and no namespace.
	 * @param name the name of the new element
	 * @return the created XElement child
	 */
	public XNElement add(@NonNull String name) {
		XNElement e = new XNElement(name);
		e.parent = this;
		children.add(e);
		return e;
	}
	/**
	 * Add a new XElement with the given local name and namespace.
	 * @param name the name of the new element
	 * @param namespace the namespace of the new element
	 * @return the created XElement child
	 */
	public XNElement add(@NonNull String name, @NonNull String namespace) {
		XNElement e = new XNElement(name);
		e.namespace = namespace;
		e.parent = this;
		children.add(e);
		return e;
	}
	/**
	 * Add the given existing child to this element.
	 * @param child the child element
	 * @return the child element
	 */
	public XNElement add(@NonNull XNElement child) {
		child.parent = this;
		children.add(child);
		return child;
	}
	/**
	 * @return the attribute map
	 */
	public Map<XAttributeName, String> attributes() {
		return attributes;
	}
	/**
	 * Returns the first child element with the given name.
	 * @param name the child name
	 * @return the XElement or null if not present
	 */
	public XNElement childElement(String name) {
		for (XNElement e : children) {
			if (e.name.equals(name)) {
				return e;
			}
		}
		return null;
	}
	/**
	 * Returns the first child element with the given name.
	 * @param name the child name
	 * @param namespace the namespace
	 * @return the XElement or null if not present
	 */
	public XNElement childElement(String name, String namespace) {
		for (XNElement e : children) {
			if (Objects.equal(e.name, name) && Objects.equal(e.namespace, namespace)) {
				return e;
			}
		}
		return null;
	}
	/** @return the iterable for all children. */
	@NonNull 
	public List<XNElement> children() {
		return children;
	}
	/*
	@Override
	public Iterator<XElement> iterator() {
		return children.iterator();
	}
	*/
	/**
	 * Returns an iterator which enumerates all children with the given name.
	 * @param name the name of the children to select
	 * @return the iterator
	 */
	public Iterable<XNElement> childrenWithName(final String name) {
		return Interactive.where(children, new Func1<XNElement, Boolean>() {
			@Override
			public Boolean invoke(XNElement param1) {
				return Objects.equal(param1.name, name);
			}
		});
	}
	/**
	 * Returns an iterator which enumerates all children with the given name.
	 * @param name the name of the children to select
	 * @param namespace the namespace URI
	 * @return the iterator
	 */
	public Iterable<XNElement> childrenWithName(final String name, final String namespace) {
		return Interactive.where(children, new Func1<XNElement, Boolean>() {
			@Override
			public Boolean invoke(XNElement param1) {
				return Objects.equal(param1.name, name) && Objects.equal(param1.namespace, namespace);
			}
		});
	}
	@Override
	public String childValue(String name) {
		for (XNElement e : children) {
			if (e.name.equals(name)) {
				return e.content;
			}
		}
		return null;
	}
	/**
	 * Returns the content of the first child which has the given name.
	 * @param name the child name
	 * @param namespace the namespace URI
	 * @return the content or null if no such child
	 */
	public String childValue(String name, String namespace) {
		for (XNElement e : children) {
			if (Objects.equal(e.name, name) && Objects.equal(e.namespace, namespace)) {
				return e.content;
			}
		}
		return null;
	}
	@Override
	public XNElement copy() {
		XNElement e = new XNElement(name);
		e.namespace = namespace;
		e.prefix = prefix;

		e.copyFrom(this);
		
		return e;
	}
	/**
	 * Copy the attributes and child elements from the other element.
	 * @param other the other element
	 */
	public void copyFrom(XNElement other) {
		content = other.content;
		userObject = other.userObject;
		for (Map.Entry<XAttributeName, String> me : other.attributes.entrySet()) {
			XAttributeName an = new XAttributeName(me.getKey().name, me.getKey().namespace, me.getKey().prefix);
			attributes.put(an, me.getValue());
		}
		for (XNElement c : children) {
			XNElement c0 = c.copy();
			c0.parent = this;
			children.add(c0);
		}
	}
	/**
	 * Generate a new prefix if the current is empty or already exists but with a different URI.
	 * @param nss the namespace to prefix cache.
	 * @param currentNamespace the current namespace
	 * @param currentPrefix the current prefix
	 * @return a pair of the derived prefix and (the xmlns declaration if needed, null otherwise)
	 */
	protected Pair<String, String> createPrefix(Map<String, String> nss, String currentNamespace, String currentPrefix) {
		if (currentNamespace == null) {
			return Pair.of(null, null);
		}
		String pf = nss.get(currentNamespace);
		
		if (pf != null) {
			return Pair.of(pf, null);
		}
		int nsc = 0;
		pf = currentPrefix;
		if (pf == null) {
			pf = "ns" + nsc;
		}
		outer:
		while (!Thread.currentThread().isInterrupted()) {
			for (Map.Entry<String, String> e : nss.entrySet()) {
				if (Objects.equal(e.getValue(), pf)) {
					nsc++;
					pf = "ns" + nsc;
					continue outer;
				}
			}
			// if we get here, the prefix is not yet used
			break;
		}
		
		nss.put(currentNamespace, pf);
		
		StringBuilder xmlns = new StringBuilder();
		xmlns.append(" xmlns").append(pf != null && pf.length() > 0 ? ":" : "")
		.append(pf != null && pf.length() > 0 ? pf : "").append("='")
		.append(sanitize(currentNamespace)).append("'");
		;
		
		return Pair.of(pf, xmlns.toString());
	}
	/** Breaks the link with its parent XElement if any. */
	public void detach() {
		if (parent != null) {
			parent.children.remove(this);
			parent = null;
		}
	}
	/**
	 * Returns a double value of the supplied child or throws an exception if missing.
	 * @param name the child element name
	 * @param namespace the element namespace
	 * @return the value
	 */
	public double doubleValue(String name, String namespace) {
		String s = childValue(name, namespace);
		if (s != null) {
			return Double.parseDouble(s);
		}
		throw new IllegalArgumentException(this + ": content: " + name);
	}
	/**
	 * Returns a double value or the default value if the element is missing or empty.
	 * @param name the element name
	 * @param namespace the element namespace
	 * @param defaultValue the default value
	 * @return the value
	 */
	public double doubleValue(String name, String namespace, double defaultValue) {
		String s = childValue(name, namespace);
		if (s != null) {
			return Double.parseDouble(s);
		}
		return defaultValue;
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof XNElement)) {
			return false;
		}
		XNElement other = (XNElement)obj;
		if (!Objects.equal(this.content, other.content)) {
			return false;
		}
		if (!this.attributes.equals(other.attributes)) {
			return false;
		}
		if (!this.children.equals(other.children)) {
			return false;
		}
		return true;
	}
	/**
	 * Retrieve the currently associated user object.
	 * @param <T> the expected object type
	 * @return the user object
	 */
	@SuppressWarnings("unchecked")
	@Nullable
	public <T> T get() {
		return (T)userObject;
	}
	/**
	 * Retrieve the first attribute which has the given local attribute name.
	 * @param attributeName the attribute name
	 * @return the attribute value or null if no such attribute
	 */
	@Nullable
	public String get(String attributeName) {
		// check first for a namespace-less attribute
		String attr = attributes.get(new XAttributeName(attributeName, null, null));
		if (attr == null) {
			// find any attribute ignoring namespace
			for (XAttributeName n : attributes.keySet()) {
				if (Objects.equal(n.name, attributeName)) {
					return attributes.get(n);
				}
			}
		}
		return attr;
	}
	/**
	 * Retrieve the specific attribute.
	 * @param attributeName the attribute name
	 * @param attributeNamespace the attribute namespace URI
	 * @return the attribute value or null if not present
	 */
	@Nullable
	public String get(String attributeName, String attributeNamespace) {
		return attributes.get(new XAttributeName(attributeName, attributeNamespace, null));
	}
	/**
	 * Retrieve the attribute names.
	 * @return the list of attribute names
	 */
	public List<XAttributeName> getAttributeNames() {
		return new ArrayList<XAttributeName>(attributes.keySet());
	}
	/**
	 * Retrieve a value of a boolean attribute.
	 * Throws IllegalArgumentException if the attribute is missing or not of type boolean.
	 * @param attribute the attribute name
	 * @return the value
	 */
	public boolean getBoolean(String attribute) {
		String s = get(attribute);
		if ("true".equals(s) || "1".equals(s)) {
			return true;
		} else
		if ("false".equals(s) || "0".equals(s)) {
			return false;
		}
		throw new IllegalArgumentException("Attribute " + attribute + " is non-boolean");
	}
	/**
	 * Retrieve a value of a boolean attribute.
	 * @param attribute the attribute name
	 * @param defaultValue the value to return if no such attribute
	 * @return the value
	 */
	public boolean getBoolean(String attribute, boolean defaultValue) {
		String s = get(attribute);
		if ("true".equals(s) || "1".equals(s)) {
			return true;
		} else
		if ("false".equals(s) || "0".equals(s)) {
			return false;
		}
		return defaultValue;
	}
	/**
	 * Retrieve a value of a boolean attribute.
	 * Throws IllegalArgumentException if the attribute is missing or not of type boolean.
	 * @param attribute the attribute name
	 * @param namespace the attribute namespace
	 * @return the value
	 */
	public boolean getBoolean(String attribute, String namespace) {
		String s = get(attribute, namespace);
		if ("true".equals(s) || "1".equals(s)) {
			return true;
		} else
		if ("false".equals(s) || "0".equals(s)) {
			return false;
		}
		throw new IllegalArgumentException("Attribute " + attribute + " is non-boolean");
	}
	/**
	 * Retrieve a value of a boolean attribute.
	 * Throws IllegalArgumentException if the attribute is missing or not of type boolean.
	 * @param attribute the attribute name
	 * @param namespace the attribute namespace
	 * @param defaultValue the value to return if no such attribute
	 * @return the value
	 */
	public boolean getBoolean(String attribute, String namespace, boolean defaultValue) {
		String s = get(attribute, namespace);
		if ("true".equals(s) || "1".equals(s)) {
			return true;
		} else
		if ("false".equals(s) || "0".equals(s)) {
			return false;
		}
		return defaultValue;
	}
	/**
	 * Returns the attribute as a double value.
	 * @param name the attribute name
	 * @return the value
	 */
	public double getDouble(String name) {
		return Double.parseDouble(get(name));
	}
	/**
	 * Returns the attribute as a double value or the default.
	 * @param name the attribute name
	 * @param defaultValue the default if the attribute is missing
	 * @return the value
	 */
	public double getDouble(String name, double defaultValue) {
		String v = get(name);
		return v != null ? Double.parseDouble(v) : defaultValue;
	}
	/**
	 * Returns the attribute as a double value.
	 * @param name the attribute name
	 * @param namespace the attribute namespace
	 * @return the value
	 */
	public double getDouble(String name, String namespace) {
		return Double.parseDouble(get(name, namespace));
	}
	/**
	 * Returns the attribute as a double value or the default.
	 * @param name the attribute name
	 * @param namespace the attribute namespace
	 * @param defaultValue the default if the attribute is missing
	 * @return the value
	 */
	public double getDouble(String name, String namespace, double defaultValue) {
		String v = get(name, namespace);
		return v != null ? Double.parseDouble(v) : defaultValue;
	}
	/**
	 * Retrieve an integer attribute. Throws exception if the attribute is missing.
	 * @param attribute the attribute name
	 * @return the value
	 */
	public int getInt(String attribute) {
		return Integer.parseInt(get(attribute));
	}
	/**
	 * Retrieve an integer attribute or the default value if not exists.
	 * @param attribute the attribute name
	 * @param defaultValue the default value to return
	 * @return the value
	 */
	public int getInt(String attribute, int defaultValue) {
		String value = get(attribute);
		if (value == null) {
			return defaultValue;
		}
		return Integer.parseInt(value);
	}
	/**
	 * Retrieve an integer attribute or the default value if not exists.
	 * @param attribute the attribute name
	 * @param namespace the attribute namespace URI
	 * @return the value
	 */
	public int getInt(String attribute, String namespace) {
		return Integer.parseInt(get(attribute, namespace));
	}
	/**
	 * Retrieve an integer attribute or the default value if not exists.
	 * @param attribute the attribute name
	 * @param namespace the namespace URI
	 * @param defaultValue the default value to return
	 * @return the value
	 */
	public int getInt(String attribute, String namespace, int defaultValue) {
		String value = get(attribute, namespace);
		if (value == null) {
			return defaultValue;
		}
		return Integer.parseInt(value);
	}
	/**
	 * Retrieve an long attribute or throw an exception if not exists.
	 * @param attribute the attribute name
	 * @return the value
	 */
	public long getLong(String attribute) {
		return Long.parseLong(get(attribute));
	}
	/**
	 * Retrieve an long attribute or the default value if not exists.
	 * @param attribute the attribute name
	 * @param defaultValue the default value to return
	 * @return the value
	 */
	public long getLong(String attribute, long defaultValue) {
		String value = get(attribute);
		if (value == null) {
			return defaultValue;
		}
		return Long.parseLong(value);
	}
	/**
	 * Retrieve an integer attribute or throw an exception if not exists.
	 * @param attribute the attribute name
	 * @param namespace the attribute namespace URI
	 * @return the value
	 */
	public long getLong(String attribute, String namespace) {
		return Long.parseLong(get(attribute, namespace));
	}
	/**
	 * Retrieve an integer attribute or the default value if not exists.
	 * @param attribute the attribute name
	 * @param namespace the attribute namespace URI
	 * @param defaultValue the default value to return
	 * @return the value
	 */
	public long getLong(String attribute, String namespace, long defaultValue) {
		String value = get(attribute, namespace);
		if (value == null) {
			return defaultValue;
		}
		return Long.parseLong(value);
	}
	/**
	 * @return Construct the XPath expression to locate this element. 
	 */
	public String getXPath() {
		StringBuilder r = new StringBuilder();
		if (parent != null) {
			XNElement q = this;
			XNElement p = parent;
			while (p != null) {
				if (p.children.size() > 1) {
					int count = 0;
					int idx = 0;
					for (XNElement c : p.children) {
						if (q.name.equals(c.name) && Objects.equal(q.namespace, c.namespace)) {
							if (c == q) {
								idx = count;
								break;
							}
							count++;
						}
					}
					if (idx > 0) {
						r.insert(0, ']');
						r.insert(0, idx);
						r.insert(0, '[');
						r.insert(0, p.name);
						r.insert(0, '/');
					} else {
						r.insert(0, p.name);
						r.insert(0, '/');
					}
				} else {
					r.insert(0, p.name);
					r.insert(0, '/');
				}
				
				q = p;
				p = p.parent;
			}
		}
		r.append('/').append(name);
		return r.toString();
	}
	/**
	 * Check if the simply-named attribute exists.
	 * @param name the name of the attribute
	 * @return true if the attribute exists
	 */
	public boolean hasAttribute(@NonNull String name) {
		return get(name) != null;
	}
	/**
	 * Check if the attribute exists.
	 * @param name the attribute name
	 * @param namespace the attribute namespace
	 * @return true if the attribute exists
	 */
	public boolean hasAttribute(@NonNull String name, @Nullable String namespace) {
		return get(name, namespace) != null;
	}
	/** @return if this node has attributes or not. */
	public boolean hasAttributes() {
		return !attributes.isEmpty();
	}
	/** @return if this node has children or not. */
	public boolean hasChildren() {
		return !children.isEmpty();
	}
	@Override
	public int hashCode() {
		return Objects.hashCode(attributes, content, children);
	}
	/**
	 * Test if this XElement has the given name.
	 * @param name the name to test against
	 * @return true if the names equal
	 */
	public boolean hasName(@NonNull String name) {
		return Objects.equal(this.name, name);
	}
	/**
	 * Test if this XElement has the given name and namespace.
	 * @param name the name to test against
	 * @param namespace the namespace to test against
	 * @return true if the names and namespaces equal
	 */
	public boolean hasName(@NonNull String name, @Nullable String namespace) {
		return hasName(name) && Objects.equal(this.namespace, namespace);
	}
	/**
	 * Returns an integer value of the supplied child or throws an exception if missing.
	 * @param name the child element name
	 * @param namespace the element namespace
	 * @return the value
	 */
	public int intValue(String name, String namespace) {
		String s = childValue(name, namespace);
		if (s != null) {
			return Integer.parseInt(s);
		}
		throw new IllegalArgumentException(this + ": content: " + name);
	}
	/**
	 * Returns an integer value or the default value if the element is missing or empty.
	 * @param name the element name
	 * @param namespace the element namespace
	 * @param defaultValue the default value
	 * @return the value
	 */
	public int intValue(String name, String namespace, int defaultValue) {
		String s = childValue(name, namespace);
		if (s != null) {
			return Integer.parseInt(s);
		}
		return defaultValue;
	}
	/**
	 * Returns a long value of the supplied child or throws an exception if missing.
	 * @param name the child element name
	 * @param namespace the element namespace
	 * @return the value
	 */
	public long longValue(String name, String namespace) {
		String s = childValue(name, namespace);
		if (s != null) {
			return Long.parseLong(s);
		}
		throw new IllegalArgumentException(this + ": content: " + name);
	}
	/**
	 * Returns a long value or the default value if the element is missing or empty.
	 * @param name the element name
	 * @param namespace the element namespace
	 * @param defaultValue the default value
	 * @return the value
	 */
	public long longValue(String name, String namespace, long defaultValue) {
		String s = childValue(name, namespace);
		if (s != null) {
			return Long.parseLong(s);
		}
		return defaultValue;
	}
	/**
	 * Save this XML into the given file.
	 * @param file the file
	 * @throws IOException on error
	 */
	public void save(File file) throws IOException {
		try (FileOutputStream fout = new FileOutputStream(file)) {
			save(fout);
		}
	}
	/**
	 * Save this XML into the given output stream.
	 * Does not close the given stream
	 * @param stream the output stream
	 * @throws IOException on error
	 */
	public void save(OutputStream stream) throws IOException {
		save(new OutputStreamWriter(stream, "UTF-8"));
	}
	/**
	 * Save this XML into the given file.
	 * @param fileName the file name
	 * @throws IOException on error
	 */
	public void save(String fileName) throws IOException {
		save(new File(fileName));
	}
	/**
	 * Save this XML into the given writer.
	 * Does not close the writer.
	 * @param stream the output writer
	 * @throws IOException on error
	 */
	public void save(Writer stream) throws IOException {
		final PrintWriter out = new PrintWriter(new BufferedWriter(stream));
		try {
			out.println("<?xml version='1.0' encoding='UTF-8'?>");
			toStringRep("", new HashMap<String, String>(), new XNAppender() {
				@Override
				public XNAppender append(Object o) {
					out.print(o);
					return this;
				}
				@Override
				public int length() {
					return 0; // Not used here
				}
			}, null);
		} finally {
			out.flush();
		}
	}
	/**
	 * Save the tree into the given XML stream writer.
	 * @param stream the stream writer
	 * @throws XMLStreamException if an error occurs
	 */
	public void save(XMLStreamWriter stream) throws XMLStreamException {
		stream.writeStartDocument("UTF-8", "1.0");
		
		saveInternal(stream);
		
		stream.writeEndDocument();
	}
	/**
	 * Store the element's content and recursively call itself for children.
	 * @param stream the stream output
	 * @throws XMLStreamException if an error occurs
	 */
	protected void saveInternal(XMLStreamWriter stream) throws XMLStreamException {
		if (namespace != null) {
			stream.writeStartElement(prefix, namespace, name);
		} else {
			stream.writeStartElement(name);
		}
		for (Map.Entry<XAttributeName, String> a : attributes.entrySet()) {
			XAttributeName an = a.getKey();
			if (an.namespace != null) {
				stream.writeAttribute(an.prefix, an.namespace, an.name, a.getValue());
			} else {
				stream.writeAttribute(an.name, a.getValue());
			}
		}
		if (content != null) {
			stream.writeCharacters(content);
		} else {
			for (XNElement e : children) {
				e.saveInternal(stream);
			}
		}
		
		stream.writeEndElement();
	}
	/**
	 * Set a multitude of attribute names and values.
	 * @param nameValues the attribute name and attribute value pairs.
	 */
	public void set(Object... nameValues) {
		if (nameValues.length % 2 != 0) {
			throw new IllegalArgumentException("Names and values must be in pairs");
		}
		for (int i = 0; i < nameValues.length; i += 2) {
			set((String)nameValues[i], nameValues[i + 1]);
		}
	}
	/**
	 * Set an attribute value identified by a local name and no namespace.
	 * @param name the attribute name
	 * @param value the value, if null, the attribute will be removed
	 */
	public void set(@NonNull String name, Object value) {
		set(name, null, value);
	}
	/**
	 * Set an attribute value identified by a local name and namespace.
	 * @param name the attribute name
	 * @param namespace the attribute namespace
	 * @param value the value, if null, the attribute will be removed
	 */
	public void set(@NonNull String name, String namespace, Object value) {
		if (value != null) {
			if (value instanceof Date) {
				attributes.put(new XAttributeName(name, namespace, null), formatDateTime((Date)value));
			} else {
				attributes.put(new XAttributeName(name, namespace, null), value.toString());
			}
		} else {
			attributes.remove(new XAttributeName(name, namespace, null));
		}
	}
	/**
	 * Replace the associated user object with a new object.
	 * @param <T> the object type
	 * @param newUserObject the new user object
	 * @return the original user object
	 */
	@SuppressWarnings("unchecked")
	@Nullable
	public <T> T set(@Nullable T newUserObject) {
		T result = (T)userObject;
		userObject = newUserObject;
		return result;
	}
	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();
		toStringRep("", new HashMap<String, String>(), new XNAppender() {
			@Override
			public XNAppender append(Object o) {
				b.append(o);
				return this;
			}
			@Override
			public int length() {
				return b.length();
			}
		}, null);
		return b.toString();
	}
	/**
	 * Convert the element into a pretty printed string representation.
	 * @param indent the current line indentation
	 * @param nss the namespace cache
	 * @param out the output
	 * @param callback the callback for each element and text position.
	 */
	public void toStringRep(String indent, Map<String, String> nss, 
			XNAppender out, @Nullable Action1<? super XRepresentationRecord> callback) {
		
		Map<String, String> nss0 = Maps.newHashMap(nss);
		
		out.append(indent);
		
		if (callback != null) {
			callback.invoke(new XRepresentationRecord(XRepresentationState.START_ELEMENT, this, 
					out.length()));
		}
		out.append("<");
		
		Pair<String, String> pf = createPrefix(nss0, namespace, prefix);
		
		String prefix = pf.first;
		if (prefix != null && prefix.length() > 0) {
			out.append(prefix).append(":");
		}
		out.append(name);

		if (pf.second != null) {
			out.append(pf.second);
		}
		
		if (attributes.size() > 0) {
			for (XAttributeName an : attributes.keySet()) {
				Pair<String, String> pfa = createPrefix(nss0, an.namespace, an.prefix);
				out.append(" ");
				if (pfa.first != null && pfa.first.length() > 0) {
					out.append(pfa.first).append(":");
				}
				out.append(an.name).append("='").append(sanitize(attributes.get(an))).append("'");
				
				if (pfa.second != null) {
					out.append(pfa.second);
				}
				
			}
		}
		
		if (children.size() == 0) {
			if (content == null || content.isEmpty()) {
				out.append("/>");
			} else {
				out.append(">");
				if (callback != null) {
					callback.invoke(new XRepresentationRecord(XRepresentationState.START_TEXT, this, out.length()));
				}
				String s = sanitize(content);
				out.append(s);
				if (callback != null) {
					callback.invoke(new XRepresentationRecord(XRepresentationState.END_TEXT, this, out.length()));
				}
				if (s.endsWith("\n")) {
					out.append(indent);
				}
				out.append("</");
				if (prefix != null && prefix.length() > 0) {
					out.append(prefix).append(":");
				}
				out.append(name);
				out.append(">");
			}
		} else {
			if (content == null || content.isEmpty()) {
				out.append(String.format(">%n"));
			} else {
				out.append(">");
				if (callback != null) {
					callback.invoke(new XRepresentationRecord(XRepresentationState.START_TEXT, this, out.length()));
				}
				out.append(sanitize(content));
				if (callback != null) {
					callback.invoke(new XRepresentationRecord(XRepresentationState.END_TEXT, this, out.length()));
				}

				out.append(String.format("%n"));
			}
			for (XNElement e : children) {
				e.toStringRep(indent + "  ", nss0, out, callback);
			}
			out.append(indent).append("</");
			if (prefix != null && prefix.length() > 0) {
				out.append(prefix).append(":");
			}
			out.append(name);
			out.append(">");
		}
		if (callback != null) {
			callback.invoke(new XRepresentationRecord(XRepresentationState.END_ELEMENT, this, out.length()));
		}
		out.append(String.format("%n"));
	}
}
