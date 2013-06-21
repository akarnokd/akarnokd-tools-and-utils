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

import java.io.IOException;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

/**
 * String utilities.
 * @author akarnokd, 2013.05.28.
 */
public final class StringUtils {
	/** Utility class. */
	private StringUtils() { }
	/**
	 * Check if a char sequence is null or empty.
	 * @param s the character sequence
	 * @return true if null or empty
	 */
	public static boolean isNullOrEmpty(CharSequence s) {
		return s == null || s.length() == 0;
	}
	/**
	 * Returns a substring if the {@code s} is longer than the specified
	 * length, the {@code s} otherwise.
	 * @param s the string to limit
	 * @param length the maximum lenght
	 * @return the limited string
	 */
	@NonNull
	public static String limit(@NonNull String s, int length) {
		if (s.length() > length) {
			return s.substring(0, length);
		}
		return s;
	}
	/**
	 * A null-safe contains test.
	 * @param s the string to search for in {@code in}
	 * @param in the target
	 * @return true if in is not null and contains s, or both are null
	 */
	public static boolean contains(String s, @Nullable String in) {
		if (s == in) {
			return true;
		} else
		if (in != null) {
			return in.contains(s);
		}
		return false;
	}
	/**
	 * A null-safe startsWith test.
	 * @param s the string to search for in {@code in}
	 * @param in the target
	 * @return true if in is not null and contains s, or both are null
	 */
	public static boolean startsWith(String s, @Nullable String in) {
		if (s == in) {
			return true;
		} else
		if (in != null) {
			return in.startsWith(s);
		}
		return false;
	}
	/**
	 * A null-safe endsWith test.
	 * @param s the string to search for in {@code in}
	 * @param in the target
	 * @return true if in is not null and contains s, or both are null
	 */
	public static boolean endsWith(String s, @Nullable String in) {
		if (s == in) {
			return true;
		} else
		if (in != null) {
			return in.endsWith(s);
		}
		return false;
	}
	/**
	 * Trims the given string if not null, returns null otherwise.
	 * @param s the string to trim
	 * @return the trimmed string or null if s was null
	 */
	public static String trim(@Nullable String s) {
		if (s == null) {
			return null;
		}
		return s.trim();
	}
	/**
	 * Adds the characters to the string builder in a reversed order.
	 * @param csq the character sequence to add
	 * @param out the output string builder
	 * @return the builder
	 */
	public static StringBuilder appendReversed(@NonNull CharSequence csq, @NonNull StringBuilder out) {
		for (int i = csq.length() - 1; i >= 0; i--) {
			out.append(csq.charAt(i));
		}
		return out;
	}
	/**
	 * Adds the characters to the string builder in a reversed order.
	 * @param csq the character sequence to add
	 * @param out the output string builder
	 * @return the builder
	 */
	public static StringBuilder appendReversed(@NonNull char[] csq, @NonNull StringBuilder out) {
		for (int i = csq.length - 1; i >= 0; i--) {
			out.append(csq[i]);
		}
		return out;
	}
	/**
	 * Adds the characters to the string builder in a reversed order.
	 * @param csq the character sequence to add
	 * @param out the output string builder
	 * @return the builder
	 */
	public static StringBuffer appendReversed(@NonNull CharSequence csq, @NonNull StringBuffer out) {
		for (int i = csq.length() - 1; i >= 0; i--) {
			out.append(csq.charAt(i));
		}
		return out;
	}
	/**
	 * Adds the characters to the string builder in a reversed order.
	 * @param csq the character sequence to add
	 * @param out the output string builder
	 * @return the builder
	 */
	public static StringBuffer appendReversed(@NonNull char[] csq, @NonNull StringBuffer out) {
		for (int i = csq.length - 1; i >= 0; i--) {
			out.append(csq[i]);
		}
		return out;
	}
	/**
	 * Adds the characters to the string appendable in a reversed order.
	 * @param csq the character sequence to add
	 * @param out the output string builder
	 * @return the appendable
	 * @throws IOException if the appendable throws
	 */
	public static Appendable appendReversed(@NonNull CharSequence csq, @NonNull Appendable out) throws IOException {
		for (int i = csq.length() - 1; i >= 0; i--) {
			out.append(csq.charAt(i));
		}
		return out;
	}
	/**
	 * Adds the characters to the string appendable in a reversed order.
	 * @param csq the character sequence to add
	 * @param out the output string builder
	 * @return the appendable
	 * @throws IOException if the appendable throws
	 */
	public static Appendable appendReversed(char[] csq, Appendable out) throws IOException {
		for (int i = csq.length - 1; i >= 0; i--) {
			out.append(csq[i]);
		}
		return out;
	}
	/**
	 * Replaces all occurrences of {@code what} with {@code with} in {@code src}
	 * (non regexp).
	 * @param src the source string
	 * @param what the thing to replace
	 * @param with the replacement
	 * @return the string with replaced parts
	 */
	public static String replaceAll(String src, String what, String with) {
		StringBuilder b = new StringBuilder();
		int idx0 = 0;
		int idx = src.indexOf(what, idx0);
		while (idx >= 0) {
			b.append(src, idx0, idx);
			b.append(with);
			idx0 = idx + what.length();
			idx = src.indexOf(what, idx0);
		}
		b.append(src, idx0, src.length());
		return b.toString();
	}
	/** The hexadecimal characters. */
	private static final char[] HEX_CHARS = "0123456789ABCDEF".toCharArray();
	/**
	 * Convert an array of bytes to hexadecimal representation.
	 * @param data the data to convert
	 * @return the hexadecimal string
	 */
	public static String toHex(byte[] data) {
		StringBuilder b = new StringBuilder();
		
		for (byte d : data) {
			b.append(HEX_CHARS[(d & 0xF0) >> 4]);
			b.append(HEX_CHARS[(d & 0xF)]);
		}

		return b.toString();
	}
	/**
	 * Converts an array of bytes to hexadecimal representation and
	 * stores it in the given appendable.
	 * @param data the data to convert
	 * @param out the output appendable
	 * @throws IOException if the appendable throws
	 */
	public static void toHex(byte[] data, Appendable out) throws IOException {
		for (byte d : data) {
			out.append(HEX_CHARS[(d & 0xF0) >> 4]);
			out.append(HEX_CHARS[(d & 0xF)]);
		}
	}
	/**
	 * Converts a character sequence of hexadecimal characters
	 * to byte array.
	 * @param cs the char sequence, the length must be even
	 * @return the byte array output
	 */
	public static byte[] fromHex(CharSequence cs) {
		if (cs.length() % 2 != 0) {
			throw new IllegalArgumentException("Odd length: " + cs.length());
		}
		byte[] r = new byte[cs.length() / 2];
		for (int i = 0; i < r.length; i++) {
			r[i] = (byte)((fromHex(cs.charAt(i * 2)) << 4) | fromHex(cs.charAt(i * 2 + 1)));
		}
		return r;
	}
	/**
	 * Convert a hexadecimal character into a number.
	 * @param c the character, 0-9a-fA-F
	 * @return the decimal value
	 */
	private static int fromHex(char c) {
		if (c >= '0' && c <= '9') {
			return c - '0';
		}
		if (c >= 'a' && c <= 'f') {
			return c - 'a' + 10;
		}
		if (c >= 'A' && c <= 'F') {
			return c - 'A' + 10;
		}
		throw new IllegalArgumentException("Not a hex char: " + c);
	}
}
