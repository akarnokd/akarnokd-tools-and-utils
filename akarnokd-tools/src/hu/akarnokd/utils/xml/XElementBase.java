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

import java.text.ParseException;
import java.util.Date;

import org.joda.time.format.ISODateTimeFormat;

import edu.umd.cs.findbugs.annotations.Nullable;

/**
 * The base interface for XElement and XNElement's common methods.
 * @author akarnokd, 2013.08.26.
 */
public abstract class XElementBase {
	/** The XSD namespace. */
	public static final String XSD = "http://www.w3.org/2001/XMLSchema";
	/** The XSD instance URI. */
	public static final String XSI = "http://www.w3.org/2001/XMLSchema-instance";
	/**
	 * Convert the given date to string.
	 * Always contains the milliseconds and timezone.
	 * @param date the date, not null
	 * @return the formatted date
	 */
	public static String formatDateTime(Date date) {
		return ISODateTimeFormat.dateTime().print(date.getTime());
	}
	/**
	 * Parse an XSD dateTime.
	 * @param date the date string
	 * @return the date
	 * @throws ParseException format exception
	 */
	public static Date parseDateTime(String date) throws ParseException {
		return ISODateTimeFormat.dateTime().parseDateTime(date).toDate();
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
					b.append(c);
				}
			}
			return b.toString();
		}
		return "";
	}
	/** The content of a simple node. */
	public String content;
	/** The element name. */
	public final String name;
	/** The user object to tag along. */
	protected Object userObject;
	/**
	 * Constructor, sets the element name.
	 * @param name the element name
	 */
	public XElementBase(String name) {
		this.name = name;
	}
	/**
	 * Returns an integer value of the supplied child or throws an exception if missing.
	 * @param name the child element name
	 * @return the value
	 */
	public int intValue(String name) {
		String s = childValue(name);
		if (s != null) {
			return Integer.parseInt(s);
		}
		throw new IllegalArgumentException(this + ": content: " + name);
	}
	/**
	 * Returns an integer value or the default value if the element is missing or empty.
	 * @param name the element name
	 * @param defaultValue the default value
	 * @return the value
	 */
	public int intValue(String name, int defaultValue) {
		String s = childValue(name);
		if (s != null) {
			return Integer.parseInt(s);
		}
		return defaultValue;
	}
	/**
	 * Returns a long value of the supplied child or throws an exception if missing.
	 * @param name the child element name
	 * @return the value
	 */
	public long longValue(String name) {
		String s = childValue(name);
		if (s != null) {
			return Long.parseLong(s);
		}
		throw new IllegalArgumentException(this + ": content: " + name);
	}
	/**
	 * Returns a long value or the default value if the element is missing or empty.
	 * @param name the element name
	 * @param defaultValue the default value
	 * @return the value
	 */
	public long longValue(String name, long defaultValue) {
		String s = childValue(name);
		if (s != null) {
			return Long.parseLong(s);
		}
		return defaultValue;
	}
	/**
	 * Returns a double value of the supplied child or throws an exception if missing.
	 * @param name the child element name
	 * @return the value
	 */
	public double doubleValue(String name) {
		String s = childValue(name);
		if (s != null) {
			return Double.parseDouble(s);
		}
		throw new IllegalArgumentException(this + ": content: " + name);
	}
	/**
	 * Returns a double value or the default value if the element is missing or empty.
	 * @param name the element name
	 * @param defaultValue the default value
	 * @return the value
	 */
	public double doubleValue(String name, double defaultValue) {
		String s = childValue(name);
		if (s != null) {
			return Double.parseDouble(s);
		}
		return defaultValue;
	}
	/**
	 * Returns the content of the first child which has the given name.
	 * @param name the child name
	 * @return the content or null if no such child
	 */
	public abstract String childValue(String name);
	/**
	 * Sets or clears the content of this element.
	 * @param value the value set or null to clear
	 */
	public final void setValue(@Nullable Object value) {
		if (value instanceof Date) {
			content = formatDateTime((Date)value);
		} else
		if (value != null) {
			content = value.toString();
		} else {
			content = null;
		}
	}
	/**
	 * @return Creates a deep copy of this element.
	 */
	public abstract XElementBase copy();
}
