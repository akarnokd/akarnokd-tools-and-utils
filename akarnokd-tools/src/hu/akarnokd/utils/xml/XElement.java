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

package hu.akarnokd.utils.xml;

import hu.akarnokd.utils.collection.ParameterMap;
import hu.akarnokd.utils.lang.ReflectionUtils;

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
import java.lang.reflect.Field;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.zip.GZIPInputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import rx.functions.Action1;

/**
 * A simplified XML element model.
 * @author akarnokd
 */
public class XElement extends XElementBase implements ParameterMap {
	/**
	 * Parse an XML from the binary data.
	 * @param data the XML data
	 * @return the parsed xml
	 * @throws XMLStreamException on error
	 */
	public static XElement parseXML(byte[] data) throws XMLStreamException {
		return parseXML(new ByteArrayInputStream(data));
	}
	/**
	 * Parse an XML from the given local file.
	 * @param file the file object
	 * @return az XElement object
	 * @throws XMLStreamException on error
	 */
	public static XElement parseXML(File file) throws XMLStreamException {
		try (InputStream in = new FileInputStream(file)) {
			return parseXML(in);
		} catch (IOException ex) {
			throw new XMLStreamException(ex);
		}
	}
	/**
	 * Parse an XML document from the given input stream.
	 * Does not close the stream.
	 * @param in the input stream
	 * @return az XElement object
	 * @throws XMLStreamException on error
	 */
	public static XElement parseXML(InputStream in) throws XMLStreamException {
		XMLInputFactory inf = XMLInputFactory.newInstance();
		XMLStreamReader ir = inf.createXMLStreamReader(in);
		return parseXML(ir);
	}
	/**
	 * Parse an XML document from the given reader. Does not close the stream.
	 * @param in the InputStream object
	 * @return az XElement object
	 * @throws XMLStreamException on error
	 */
	public static XElement parseXML(Reader in) throws XMLStreamException {
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
	public static XElement parseXML(ResultSet rs, int index) 
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
	public static XElement parseXML(ResultSet rs, String column) 
			throws SQLException, IOException, XMLStreamException {
		try (InputStream is = rs.getBinaryStream(column)) {
			if (is != null) {
				return parseXML(is);
			}
			return null;
		}
	}
	/**
	 * Parse an XML from the given local filename.
	 * @param fileName the file name
	 * @return az XElement object
	 * @throws XMLStreamException on error
	 */
	public static XElement parseXML(String fileName) throws XMLStreamException {
		try (InputStream in = new FileInputStream(fileName)) {
			return parseXML(in);
		} catch (IOException ex) {
			throw new XMLStreamException(ex);
		}
	}
	/**
	 * Parses an XML from the given URL.
	 * @param u the url
	 * @return the parsed XML
	 * @throws XMLStreamException on error
	 * @throws IOException on error
	 */
	public static XElement parseXML(URL u) throws XMLStreamException, IOException {
		try (InputStream in = u.openStream()) {
			return parseXML(in);
		}
	}
	/**
	 * Parse an XML from an XML stream reader. Does not close the stream
	 * @param in the XMLStreamReader object
	 * @return az XElement object
	 * @throws XMLStreamException on error
	 */
	public static XElement parseXML(XMLStreamReader in) throws XMLStreamException {
		XElement root = parseXMLFragment(in);
		in.close();
		return root;
	}
	/**
	 * Parse an XML from an XML stream reader. Does not close the stream
	 * @param in the XMLStreamReader object
	 * @return az XElement object
	 * @throws XMLStreamException on error
	 */
	public static XElement parseXMLFragment(XMLStreamReader in) throws XMLStreamException {
		XElement node = null;
		XElement root = null;
		final StringBuilder emptyBuilder = new StringBuilder();
		StringBuilder b = null;
		Deque<StringBuilder> stack = new LinkedList<>();
		
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
				XElement n = new XElement(in.getName().getLocalPart());
				n.parent = node;
				int attCount = in.getAttributeCount();
				if (attCount > 0) {
					for (int i = 0; i < attCount; i++) {
						n.attributes.put(in.getAttributeLocalName(i), in.getAttributeValue(i));
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
				if (node != null) {
					if (b != null) {
						node.content = b.toString();
					}
					node = node.parent;
				}
				b = stack.pop();
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
	public static XElement parseXMLGZ(File file) throws XMLStreamException {
		try (GZIPInputStream gin = new GZIPInputStream(new BufferedInputStream(new FileInputStream(file), 64 * 1024))) {
			return parseXML(gin);
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
	public static XElement parseXMLGZ(String fileName) throws XMLStreamException {
		return parseXMLGZ(new File(fileName));
	}
	/** The attribute map. */
	protected final Map<String, String> attributes = new LinkedHashMap<>();
	/** The child elements. */
	protected final List<XElement> children = new ArrayList<>();
	/** The parent element. */
	public XElement parent;
	/**
	 * Constructor. Sets the name.
	 * @param name the element name
	 */
	public XElement(String name) {
		super(name);
	}
	/**
	 * Creates a new XElement and sets its content according to the supplied value.
	 * @param name the element name
	 * @param value the content value
	 */
	public XElement(String name, Object value) {
		this(name);
		setValue(value);
	}
	/**
	 * Add the iterable of elements as children.
	 * @param elements the elements to add
	 */
	public void add(Iterable<XElement> elements) {
		for (XElement e : elements) {
			e.parent = this;
			children.add(e);
		}
	}
	/**
	 * Add a new child element with the given name.
	 * @param name the element name
	 * @return the created XElement
	 */
	public XElement add(String name) {
		XElement result = new XElement(name);
		result.parent = this;
		children.add(result);
		return result;
	}
	/**
	 * Add an element with the supplied content text.
	 * @param name the name
	 * @param value the content
	 * @return the new element
	 */
	public XElement add(String name, Object value) {
		XElement result = add(name);
		result.parent = this;
		result.setValue(value);
		return result;
	}
	/**
	 * Add the array of elements as children.
	 * @param elements the elements to add
	 */
	public void add(XElement... elements) {
		for (XElement e : elements) {
			e.parent = this;
			children.add(e);
		}
	}
	/**
	 * Add the array of elements as children.
	 * @param element the element to add
	 */
	public void add(XElement element) {
		children.add(element);
	}
	/** @return the attribute map. */
	public Map<String, String> attributes() {
		return attributes;
	}
	/**
	 * Returns the first child element with the given name.
	 * @param name the child name
	 * @return the XElement or null if not present
	 */
	public XElement childElement(String name) {
		for (XElement e : children) {
			if (e.name.equals(name)) {
				return e;
			}
		}
		return null;
	}
	/**
	 * @return The child elements.
	 */
	public List<XElement> children() {
		return children;
	}
	/**
	 * Returns an iterator which enumerates all children with the given name.
	 * @param name the name of the children to select
	 * @return the iterator
	 */
	public Iterable<XElement> childrenWithName(String name) {
		List<XElement> result = new ArrayList<>(children.size() + 1);
		for (XElement e : children) {
			if (e.name.equals(name)) {
				result.add(e);
			}
		}
		return result;
	}
	/**
	 * Remove attributes and children.
	 */
	public void clear() {
		attributes.clear();
		children.clear();
	}
	@Override
	public XElement copy() {
		XElement e = new XElement(name);

		e.copyFrom(this);
		
		return e;
	}
	/**
	 * Copy the attributes, content and child elements from the other element
	 * in a deep-copy fashion.
	 * @param other the other element
	 */
	public void copyFrom(XElement other) {
		content = other.content;
		userObject = other.userObject;
		attributes.putAll(other.attributes);
		for (XElement c : other.children) {
			add(c.copy());
		}
	}
	/**
	 * Detach this element from its parent.
	 */
	public void detach() {
		if (parent != null) {
			parent.children.remove(this);
			parent = null;
		}
	}
	@Override
	public String get(String attributeName) {
		String s = attributes.get(attributeName);
		if (s == null) {
			throw new NoSuchElementException(this + ": missing attribute: " + attributeName);
		}
		return s;
	}
	@Override
	public String get(String attributeName, String def) {
		String s = attributes.get(attributeName); 
		return s != null ? s : def;
	}
	@Override
	public boolean getBoolean(String name) {
		return "true".equals(get(name));
	}
	@Override
	public boolean getBoolean(String name, boolean defaultValue) {
		String s = attributes.get(name);
		return s != null ? "true".equals(s) : defaultValue;
	}
	@Override
	public byte getByte(String key) {
		String attr = get(key);
		return Byte.parseByte(attr);
	}
	@Override
	public byte getByte(String key, byte defaultValue) {
		String attr = attributes.get(key);
		if (attr != null) {
			return Byte.parseByte(attr);
		}
		return defaultValue;
	}
	@Override
	public char getChar(String key) {
		String attr = get(key);
		if (attr != null && !attr.isEmpty()) {
			return attr.charAt(0);
		}
		throw new NoSuchElementException(key);
	}
	@Override
	public char getChar(String key, char defaultValue) {
		String attr = attributes.get(key);
		if (attr != null && !attr.isEmpty()) {
			return attr.charAt(0);
		}
		return defaultValue;
	}
	@Override
	public double getDouble(String name) {
		return Double.parseDouble(get(name));
	}
	@Override
	public double getDouble(String name, double defaultValue) {
		String s = attributes.get(name);
		return s != null ? Double.parseDouble(s) : defaultValue;
	}
	/**
	 * Get a double attribute as object or null if not present.
	 * @param attributeName the attribute name
	 * @return the integer value
	 */
	public Double getDoubleObject(String attributeName) {
		String val = get(attributeName, null);
		if (val != null) {
			return Double.valueOf(val);
		}
		return null;
	}
	@Override
	public <E extends Enum<E>> E getEnum(String name, Class<E> clazz) {
		String s = get(name);
		E[] values = clazz.getEnumConstants();
		for (E t : values) {
			if (t.toString().equals(s)) {
				return t;
			}
		}
		throw new NoSuchElementException(String.format("Attribute %s = %s is not a valid enum for %s", name, s, clazz.getName()));
	}
	@Override
	public <E extends Enum<E>> E getEnum(String name, Class<E> clazz, E defaultValue) {
		String s = attributes.get(name);
		if (s != null) {
			E[] values = clazz.getEnumConstants();
			for (E t : values) {
				if (t.toString().equals(s)) {
					return t;
				}
			}
		}
		return defaultValue;
	}
	@Override
	public <E extends Enum<E>> E getEnum(String key, E[] universe) {
		String attr = get(key);
		for (E e : universe) {
			if (e.name().equals(attr)) {
				return e;
			}
		}
		throw new NoSuchElementException(key);
	}
	@Override
	public <E extends Enum<E>> E getEnum(String key, E[] universe,
			E defaultValue) {
		String attr = attributes.get(key);
		for (E e : universe) {
			if (e.name().equals(attr)) {
				return e;
			}
		}
		return defaultValue;
	}
	@Override
	public float getFloat(String attributeName) {
		String val = get(attributeName);
		return Float.parseFloat(val);
	}
	@Override
	public float getFloat(String key, float defaultValue) {
		String attr = attributes.get(key);
		return Float.parseFloat(attr);
	}
	@Override
	public int getInt(String attributeName) {
		String val = get(attributeName);
		return Integer.parseInt(val);
	}
	@Override
	public int getInt(String attributeName, int def) {
		String val = attributes.get(attributeName);
		return val != null ? Integer.parseInt(val) : def;
	}
	/**
	 * Get an integer attribute as object or null if not present.
	 * @param attributeName the attribute name
	 * @return the integer value
	 */
	public Integer getIntObject(String attributeName) {
		String val = get(attributeName, null);
		if (val != null) {
			return Integer.valueOf(val);
		}
		return null;
	}
	@Override
	public long getLong(String attributeName) {
		String val = get(attributeName);
		return Long.parseLong(val);
	}
	@Override
	public long getLong(String attributeName, long def) {
		String val = attributes.get(attributeName);
		return val != null ? Long.parseLong(val) : def;
	}
	@Override
	public short getShort(String key) {
		String attr = get(key);
		return Short.parseShort(attr);
	}
	@Override
	public short getShort(String key, short defaultValue) {
		String attr = attributes.get(key);
		if (attr != null) {
			return Short.parseShort(attr);
		}
		return defaultValue;
	}
	@Override
	public boolean has(String attributeName) {
		return attributes.containsKey(attributeName);
	}
	/** @return are there any children? */
	public boolean hasChildren() {
		return !children.isEmpty();
	}
	/**
	 * Check if there is a valid integer attribute.
	 * @param attributeName the attribute name
	 * @return true if the attribute is a valid int
	 */
	public boolean hasDouble(String attributeName) {
		String attr = attributes.get(attributeName);
		if (attr == null || attr.isEmpty()) {
			return false;
		}
		try {
			Double.parseDouble(attr);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
	/**
	 * Check if there is a valid integer attribute.
	 * @param attributeName the attribute name
	 * @return true if the attribute is a valid int
	 */
	public boolean hasFloat(String attributeName) {
		String attr = attributes.get(attributeName);
		if (attr == null || attr.isEmpty()) {
			return false;
		}
		try {
			Float.parseFloat(attr);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
	/**
	 * Check if there is a valid integer attribute.
	 * @param attributeName the attribute name
	 * @return true if the attribute is a valid int
	 */
	public boolean hasInt(String attributeName) {
		String attr = attributes.get(attributeName);
		if (attr == null || attr.isEmpty()) {
			return false;
		}
		try {
			Integer.parseInt(attr);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
	/**
	 * Check if there is a valid integer attribute.
	 * @param attributeName the attribute name
	 * @return true if the attribute is a valid int
	 */
	public boolean hasLong(String attributeName) {
		String attr = attributes.get(attributeName);
		if (attr == null || attr.isEmpty()) {
			return false;
		}
		try {
			Long.parseLong(attr);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
	/**
	 * Check if there is a valid positive integer attribute.
	 * @param attributeName the attribute name
	 * @return true if the attribute is a valid positive int
	 */
	public boolean hasPositiveInt(String attributeName) {
		String attr = attributes.get(attributeName);
		if (attr == null || attr.isEmpty()) {
			return false;
		}
		try {
			return Integer.parseInt(attr) >= 0;
		} catch (NumberFormatException ex) {
			return false;
		}
	}
	/**
	 * Check if the given attribute is present an is not empty.
	 * @param attributeName the attribute name
	 * @return true if null or empty
	 */
	public boolean isNullOrEmpty(String attributeName) {
		String attr = attributes.get(attributeName);
		if (attr == null || attr.isEmpty()) {
			return true;
		}
		return false;
	}
	/**
	 * Load the simple fields of the given object.
	 * @param o the object, non-null
	 */
	public void loadFields(Object o) {
		for (Field f : ReflectionUtils.allDeclaredFields(o.getClass())) {
			try {
				if (f.getType() == Boolean.TYPE) {
					f.set(o, getBoolean(f.getName(), (Boolean)f.get(o)));
				} else
				if (f.getType() == Byte.TYPE) {
					f.set(o, (byte)getInt(f.getName(), (Byte)f.get(o)));
				} else
				if (f.getType() == Short.TYPE) {
					f.set(o, (short)getInt(f.getName(), (Short)f.get(o)));
				} else
				if (f.getType() == Integer.TYPE) {
					f.set(o, getInt(f.getName(), (Integer)f.get(o)));
				} else
				if (f.getType() == Long.TYPE) {
					f.set(o, getLong(f.getName(), (Long)f.get(o)));
				} else
				if (f.getType() == Float.TYPE) {
					f.set(o, (float)getDouble(f.getName(), (Float)f.get(o)));
				} else
				if (f.getType() == Double.TYPE) {
					f.set(o, getDouble(f.getName(), (Double)f.get(o)));
				} else
				if (f.getType() == Character.TYPE) {
					f.set(o, get(f.getName(), String.valueOf(f.get(o))).charAt(0));
				} else
				if (f.getType() == String.class) {
					f.set(o, get(f.getName(), (String)f.get(o)));
				}
				if (f.getType().isEnum()) {
					String e = get(f.getName());
					for (Object o2 : f.getType().getEnumConstants()) {
						if (o2 instanceof Enum<?>) {
							Enum<?> e2 = (Enum<?>) o2;
							if (e2.name().equals(e)) {
								f.set(o, e2);
							}
						}
					}
					
				}
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			} 
		}
	}
	/**
	 * Remove the given element from the children.
	 * @param element the element
	 */
	public void remove(XElement element) {
		element.parent = null;
		children.remove(element);
	}
	/**
	 * Removes all children with the given element name.
	 * @param name the element name
	 */
	public void removeChildrenWithName(String name) {
		for (int i = children.size() - 1; i >= 0; i--) {
			if (children.get(i).name.equals(name)) {
				children.remove(i).parent = null;
			}
		}
	}
	/**
	 * Replaces the specified child node with the new node. If the old node is not present
	 * the method does nothing.
	 * @param oldChild the old child
	 * @param newChild the new child
	 */
	public void replace(XElement oldChild, XElement newChild) {
		int idx = children.indexOf(oldChild);
		if (idx >= 0) {
			children.get(idx).parent = null;
			children.set(idx, newChild);
			newChild.parent = this;
		}
	}
	/**
	 * Save this XML into the given file.
	 * @param file the file
	 * @throws IOException on error
	 */
	public void save(File file) throws IOException {
		try (FileOutputStream out = new FileOutputStream(file)) {
			save(out);
		}
	}
	/**
	 * Save this XML into the supplied output stream.
	 * @param stream the output stream
	 * @throws IOException on error
	 */
	public void save(OutputStream stream) throws IOException {
		OutputStreamWriter out = new OutputStreamWriter(stream, "UTF-8");
		try {
			save(out);
		} finally {
			out.flush();
		}
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
	 * Save this XML into the supplied output writer.
	 * @param writer the output writer
	 * @throws IOException on error
	 */
	public void save(Writer writer) throws IOException {
		final PrintWriter out = new PrintWriter(new BufferedWriter(writer));
		try {
			out.println("<?xml version='1.0' encoding='UTF-8'?>");
			toStringRep("", new XAppender() {
				@Override
				public XAppender append(Object o) {
					out.print(o);
					return this;
				}
			});
		} finally {
			out.flush();
		}
	}
	/**
	 * Save the simple fields of the given object.
	 * @param o the object, non-null
	 */
	public void saveFields(Object o) {
		for (Field f : ReflectionUtils.allDeclaredFields(o.getClass())) {
			if (f.getType() == Boolean.TYPE
					|| f.getType() == Byte.TYPE
					|| f.getType() == Short.TYPE
					|| f.getType() == Integer.TYPE
					|| f.getType() == Long.TYPE
					|| f.getType() == Float.TYPE
					|| f.getType() == Double.TYPE
					|| f.getType() == Character.TYPE
					|| f.getType() == String.class
					|| f.getType().isEnum()) {
				try {
					set(f.getName(), f.get(o));
				} catch (IllegalAccessException ex) {
					ex.printStackTrace();
				}
			}
		}
	}
	/**
	 * Set an attribute value.
	 * @param name the attribute name
	 * @param value the content value, null will remove any existing
	 */
	public void set(String name, Object value) {
		if (value != null) {
			attributes.put(name, String.valueOf(value));
		} else {
			attributes.remove(name);
		}
		
	}
	@Override
	public String toString() {
		final StringBuilder b = new StringBuilder();
		toStringRep("", new XAppender() {
			@Override
			public XAppender append(Object o) {
				b.append(o);
				return this;
			}
		});
		return b.toString();
	}
	/**
	 * Convert the element into a pretty printed string representation.
	 * @param indent the current line indentation
	 * @param out the output
	 */
	void toStringRep(String indent, XAppender out) {
		out.append(indent).append("<");
		out.append(name);
		if (attributes.size() > 0) {
			for (String an : attributes.keySet()) {
				out.append(" ").append(an).append("='").append(sanitize(attributes.get(an))).append("'");
			}
		}
		
		if (children.size() == 0) {
			if (content == null) {
				out.append("/>");
			} else {
				out.append(">");
				out.append(sanitize(content));
				out.append("</");
				out.append(name);
				out.append(">");
			}
		} else {
			if (content == null) {
				out.append(String.format(">%n"));
			} else {
				out.append(">");
				out.append(sanitize(content));
				out.append(String.format("%n"));
			}
			for (XElement e : children) {
				e.toStringRep(indent + "  ", out);
			}
			out.append(indent).append("</");
			out.append(name);
			out.append(">");
		}
		out.append(String.format("%n"));
	}
	/**
	 * Iterate through the elements of this XElement and invoke the action for each.
	 * @param depthFirst do a depth first search?
	 * @param action the action to invoke, non-null
	 */
	public void visit(boolean depthFirst, Action1<? super XElement> action) {
		Deque<XElement> queue = new LinkedList<>();
		queue.add(this);
		while (!queue.isEmpty()) {
			XElement x = queue.removeFirst();
			action.call(x);
			if (depthFirst) {
				ListIterator<XElement> li = x.children.listIterator(x.children.size());
				while (li.hasPrevious()) {
					queue.addFirst(li.previous());
				}
			} else {
				for (XElement c : x.children) {
					queue.addLast(c);
				}
			}
		}
	}
	
	@Override
	public String childValue(String name) {
		for (XElement e : children) {
			if (e.name.equals(name)) {
				return e.content;
			}
		}
		return null;
	}
}
