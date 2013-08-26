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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

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
public class XNElement {
	/** The XSD namespace. */
	public static final String XSD = "http://www.w3.org/2001/XMLSchema";
	/** The XSD instance URI. */
	public static final String XSI = "http://www.w3.org/2001/XMLSchema-instance";
	/** The namespace:name record. */
	public static class XAttributeName {
		/** The name. */
		public final String name;
		/** The namespace. */
		public final String namespace;
		/** The namespace prefix. */
		public final String prefix;
		/** The hash. */
		private int hash;
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
	/** The element name. */
	public final String name;
	/** The optional associated namespace uri. */
	public String namespace;
	/** The prefix used by this element. */
	public String prefix;
	/** The content of a simple node. */
	public String content;
	/** The parent element. */
	public XNElement parent;
	/** The attribute map. */
	protected final Map<XAttributeName, String> attributes = new LinkedHashMap<XAttributeName, String>();
	/** The child elements. */
	protected final List<XNElement> children = new ArrayList<XNElement>();
	/** The user object to tag along. */
	protected Object userObject;
	/**
	 * Constructor. Sets the name.
	 * @param name the element name
	 */
	public XNElement(String name) {
		this.name = name;
	}
	/**
	 * Constructor. Sets the name and namespace.
	 * @param name the element name
	 * @param namespace the element namespace
	 */
	public XNElement(String name, String namespace) {
		this.name = name;
		this.namespace = namespace;
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
	/**
	 * Returns the content of the first child which has the given name.
	 * @param name the child name
	 * @return the content or null if no such child
	 */
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
				// return to the parent's builded
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
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		toStringRep("", new HashMap<String, String>(), b, new Action1<Object>() {
			@Override
			public void invoke(Object value) {
				
			}
		});
		return b.toString();
	}
	/**
	 * The representation state.
	 * @author akarnokd, 2011.10.21.
	 */
	public enum XRepresentationState {
		/** Element start. */
		START_ELEMENT,
		/** Text start. */
		START_TEXT,
		/** Element end. */
		END_ELEMENT,
		/** Text end. */
		END_TEXT
	}
	/**
	 * The representation record.
	 * @author akarnokd, 2011.10.21.
	 */
	public static class XRepresentationRecord {
		/** The state. */
		public final XRepresentationState state;
		/** The current element. */
		public final XNElement element;
		/** The offset. */
		public final int charOffset;
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
	/**
	 * Convert the element into a pretty printed string representation.
	 * @param indent the current line indentation
	 * @param nss the namespace cache
	 * @param out the output
	 * @param callback the callback for each element and text position.
	 */
	public void toStringRep(String indent, Map<String, String> nss, 
			StringBuilder out, Action1<? super XRepresentationRecord> callback) {
		
		Map<String, String> nss0 = Maps.newHashMap(nss);
		
		out.append(indent);
		callback.invoke(new XRepresentationRecord(XRepresentationState.START_ELEMENT, this, out.length()));
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
				callback.invoke(new XRepresentationRecord(XRepresentationState.START_TEXT, this, out.length()));
				String s = sanitize(content);
				out.append(s);
				callback.invoke(new XRepresentationRecord(XRepresentationState.END_TEXT, this, out.length()));
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
				callback.invoke(new XRepresentationRecord(XRepresentationState.START_TEXT, this, out.length()));
				out.append(sanitize(content));
				callback.invoke(new XRepresentationRecord(XRepresentationState.END_TEXT, this, out.length()));

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
		callback.invoke(new XRepresentationRecord(XRepresentationState.END_ELEMENT, this, out.length()));
		out.append(String.format("%n"));
	}
	/**
	 * Converts all sensitive characters to its HTML entity equivalent.
	 * @param s the string to convert, can be null
	 * @return the converted string, or an empty string
	 */
	public static String sanitize(String s) {
		if (s != null) {
			StringBuilder b = new StringBuilder(s.length());
			for (int i = 0, count = s.length(); i < count; i++) {
				char c = s.charAt(i);
				switch (c) {
				case '<':
					b.append("&lt;");
					break;
				case '>':
					b.append("&gt;");
					break;
				case '\'':
					b.append("&#39;");
					break;
				case '"':
					b.append("&quot;");
					break;
				case '&':
					b.append("&amp;");
					break;
				default:
					if (c < 32 && c != '\r' && c != '\n' && c != '\t') {
						b.append("&#").append((int)c).append(";");
					}
					b.append(c);
				}
			}
			return b.toString();
		}
		return "";
	}
	/**
	 * @return Creates a deep copy of this element.
	 */
	public XNElement copy() {
		XNElement e = new XNElement(name);
		e.namespace = namespace;
		e.content = content;
		e.prefix = prefix;

		e.copyFrom(this);
		
		return e;
	}
	/**
	 * Copy the attributes and child elements from the other element.
	 * @param other the other element
	 */
	public void copyFrom(XNElement other) {
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
	/** @return the iterable for all children. */
	@NonNull 
	public List<XNElement> children() {
		return children;
	}
	/** @return if this node has children or not. */
	public boolean hasChildren() {
		return !children.isEmpty();
	}
	/** @return if this node has attributes or not. */
	public boolean hasAttributes() {
		return !attributes.isEmpty();
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
	 * Save this XML into the given file.
	 * @param fileName the file name
	 * @throws IOException on error
	 */
	public void save(String fileName) throws IOException {
		save(new File(fileName));
	}
	/**
	 * Save this XML into the given file.
	 * @param file the file
	 * @throws IOException on error
	 */
	public void save(File file) throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "UTF-8")));
		try {
			out.println("<?xml version='1.0' encoding='UTF-8'?>");
			out.print(toString());
		} finally {
			out.close();
		}
	}
	/**
	 * Save this XML into the given output stream.
	 * Does not close the given stream
	 * @param stream the output stream
	 * @throws IOException on error
	 */
	public void save(OutputStream stream) throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(stream, "UTF-8")));
		try {
			out.println("<?xml version='1.0' encoding='UTF-8'?>");
			out.print(toString());
		} finally {
			out.flush();
		}
	}
	/**
	 * Save this XML into the given writer.
	 * Does not close the writer.
	 * @param stream the output writer
	 * @throws IOException on error
	 */
	public void save(Writer stream) throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(stream));
		try {
			out.println("<?xml version='1.0' encoding='UTF-8'?>");
			out.print(toString());
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
	 * @return the attribute map
	 */
	public Map<XAttributeName, String> attributes() {
		return attributes;
	}
	/**
	 * Gregorian calendar for XSD dateTime.
	 */
	private static final ThreadLocal<GregorianCalendar> XSD_CALENDAR = new ThreadLocal<GregorianCalendar>() {
		@Override
		protected GregorianCalendar initialValue() {
			return new GregorianCalendar(TimeZone.getTimeZone("GMT"));
		}
	};
	/**
	 * Convert the given date to string.
	 * Always contains the milliseconds and timezone.
	 * @param date the date, not null
	 * @return the formatted date
	 */
	public static String formatDateTime(Date date) {
		StringBuilder b = new StringBuilder(24);
		
		GregorianCalendar cal = XSD_CALENDAR.get();
		cal.setTime(date);
		
		int value = 0;
		
		// Year-Month-Day
		value = cal.get(GregorianCalendar.YEAR);
		b.append(value);
		b.append('-');
		value = cal.get(GregorianCalendar.MONTH) + 1;
		if (value < 10) {
			b.append('0');
		}
		b.append(value);
		b.append('-');
		value = cal.get(GregorianCalendar.DATE);
		if (value < 10) {
			b.append('0');
		}
		b.append(value);
		
		b.append('T');
		// hour:minute:second:milliseconds
		value = cal.get(GregorianCalendar.HOUR_OF_DAY);
		if (value < 10) {
			b.append('0');
		}
		b.append(value);
		b.append(':');
		value = cal.get(GregorianCalendar.MINUTE);
		if (value < 10) {
			b.append('0');
		}
		b.append(value);
		b.append(':');
		value = cal.get(GregorianCalendar.SECOND);
		if (value < 10) {
			b.append('0');
		}
		b.append(value);
		b.append('.');
		
		value = cal.get(GregorianCalendar.MILLISECOND);
		// add leading zeros if needed
		if (value < 100) {
			b.append('0');
		}
		if (value < 10) {
			b.append('0');
		}
		b.append(value);
		
		value = cal.get(GregorianCalendar.DST_OFFSET) + cal.get(GregorianCalendar.ZONE_OFFSET);
		
		if (value == 0) {
			b.append('Z');
		} else {
			if (value < 0) {
				b.append('-');
				value = -value;
			} else {
				b.append('+');
			}
			int hour = value / 3600000;
			int minute = value / 60000 % 60;
			if (hour < 10) {
				b.append('0');
			}
			b.append(hour);
			b.append(':');
			if (minute < 10) {
				b.append('0');
			}
			b.append(minute);
		}
		
		
		return b.toString();
	}
	/**
	 * Parse an XSD dateTime.
	 * @param date the date string
	 * @return the date
	 * @throws ParseException format exception
	 */
	public static Date parseDateTime(String date) throws ParseException {
		GregorianCalendar cal = XSD_CALENDAR.get();
		cal.set(GregorianCalendar.MILLISECOND, 0);
		// format yyyy-MM-dd'T'HH:mm:ss[.sss][zzzzz] no milliseconds no timezone
		int offset = 0;
		try {
			offset = 0;
			cal.set(GregorianCalendar.YEAR, Integer.parseInt(date.substring(offset, offset + 4)));
			offset = 5;
			cal.set(GregorianCalendar.MONTH, Integer.parseInt(date.substring(offset, offset + 2)) - 1);
			offset = 8;
			cal.set(GregorianCalendar.DATE, Integer.parseInt(date.substring(offset, offset + 2)));
			offset = 11;
			cal.set(GregorianCalendar.HOUR_OF_DAY, Integer.parseInt(date.substring(offset, offset + 2)));
			offset = 14;
			cal.set(GregorianCalendar.MINUTE, Integer.parseInt(date.substring(offset, offset + 2)));
			offset = 17;
			cal.set(GregorianCalendar.SECOND, Integer.parseInt(date.substring(offset, offset + 2)));
			
			if (date.length() > 19) {
				offset = 19;
				char c = date.charAt(offset);
				// check milliseconds
				if (c == '.') {
					offset++;
					int endOffset = offset;
					// can be multiple
					while (endOffset < date.length() && Character.isDigit(date.charAt(endOffset))) {
						endOffset++;
					}
					int millisec = Integer.parseInt(date.substring(offset, endOffset));
					int len = endOffset - offset - 1;
					if (len >= 3) {
						while (len-- >= 3) {
							millisec /= 10;
						}
					} else {
						while (++len < 3) {
							millisec *= 10;
						}
					}
					cal.set(GregorianCalendar.MILLISECOND, millisec);
					if (date.length() > endOffset) {
						offset = endOffset;
						c = date.charAt(offset);
					} else {
						c = '\0';
					}
				}
				if (c == 'Z') {
					cal.set(GregorianCalendar.ZONE_OFFSET, 0);
				} else
				if (c == '-' || c == '+') {
					int sign = c == '-' ? -1 : 1;
					offset++;
					int tzHour = Integer.parseInt(date.substring(offset, offset + 2));
					offset += 3;
					int tzMinute = Integer.parseInt(date.substring(offset, offset + 2));
					cal.set(GregorianCalendar.ZONE_OFFSET, sign * (tzHour * 3600000 + tzMinute * 60000));
				} else
				if (c != '\0') {
					throw new ParseException("Unknown milliseconds or timezone", offset);
				}
			}
		} catch (NumberFormatException ex) {
			throw new ParseException(ex.toString(), offset);
		} catch (IndexOutOfBoundsException ex) {
			throw new ParseException(ex.toString(), offset);
		}
		return cal.getTime();
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
	/** Breaks the link with its parent XElement if any. */
	public void detach() {
		if (parent != null) {
			parent.children.remove(this);
			parent = null;
		}
	}
	/**
	 * Creates a new XElement and sets its content according to the supplied value.
	 * @param name the element name
	 * @param value the content value
	 */
	public XNElement(String name, Object value) {
		this(name);
		if (value instanceof Date) {
			content = formatDateTime((Date)value);
		} else
		if (value != null) {
			content = value.toString();
		}
	}
	/**
	 * Creates a new XElement and sets its content according to the supplied value.
	 * @param name the element name
	 * @param namespace the namespace
	 * @param value the content value
	 */
	public XNElement(String name, String namespace, Object value) {
		this(name, namespace);
		if (value instanceof Date) {
			content = formatDateTime((Date)value);
		} else
		if (value != null) {
			content = value.toString();
		}
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
	@Override
	public int hashCode() {
		return Objects.hashCode(attributes, content, children);
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
		return Double.parseDouble(get(name));
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
}
