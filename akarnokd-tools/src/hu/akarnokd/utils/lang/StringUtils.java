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
}
