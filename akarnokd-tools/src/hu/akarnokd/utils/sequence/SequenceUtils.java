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

package hu.akarnokd.utils.sequence;

import java.io.IOException;
import java.util.Iterator;

import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * Utility methods for Iterable and Iterator sequences
 * commonly missing from Guava or R4J.
 * @author akarnokd, 2013.04.19.
 */
public final class SequenceUtils {
	/** Utility class. */
	private SequenceUtils() { }
	/**
	 * Joins a sequence of values with the given separator between elements.
	 * @param sequence the sequence to join
	 * @param separator the separator between elements
	 * @param append the output appender
	 * @throws IOException if the append methods throw
	 */
	public static void join(@NonNull Iterable<?> sequence, CharSequence separator, @NonNull Appendable append) throws IOException {
		join(sequence.iterator(), separator, append);
	}
	/**
	 * Joins a sequence of values with the given separator between elements.
	 * @param sequence the sequence to join
	 * @param separator the separator between elements
	 * @param append the output appender
	 * @throws IOException if the append methods throw
	 */
	public static void join(@NonNull Iterator<?> sequence, CharSequence separator, @NonNull Appendable append) throws IOException {
		int i = 0;
		while (sequence.hasNext()) {
			if (i > 0) {
				append.append(separator);
			}
			append.append(String.valueOf(sequence.next()));
			i = 1;
		}
	}
	/**
	 * Joins a sequence of values with the given separator between elements
	 * and uses the supplied format string.
	 * @param sequence the sequence to join
	 * @param separator the separator between elements
	 * @param format the format string
	 * @param append the output appender
	 * @throws IOException if the append methods throw
	 */
	public static void join(@NonNull Iterable<?> sequence, CharSequence separator, @NonNull String format, @NonNull Appendable append) throws IOException {
		join(sequence.iterator(), separator, format, append);
	}
	/**
	 * Joins a sequence of values with the given separator between elements
	 * and uses the supplied format string.
	 * @param sequence the sequence to join
	 * @param separator the separator between elements
	 * @param format the format string
	 * @param append the output appender
	 * @throws IOException if the append methods throw
	 */
	public static void join(@NonNull Iterator<?> sequence, CharSequence separator, @NonNull String format, @NonNull Appendable append) throws IOException {
		int i = 0;
		while (sequence.hasNext()) {
			if (i > 0) {
				append.append(separator);
			}
			append.append(String.format(format, sequence.next()));
			i = 1;
		}
	}
	/**
	 * Joins a sequence of values with the given separator between elements.
	 * @param sequence the sequence to join
	 * @param separator the separator between elements
	 * @return the joined string
	 */
	public static String join(@NonNull Iterable<?> sequence, CharSequence separator) {
		return join(sequence.iterator(), separator);
	}
	/**
	 * Joins a sequence of values with the given separator between elements.
	 * @param sequence the sequence to join
	 * @param separator the separator between elements
	 * @return the joined string
	 */
	public static String join(@NonNull Iterator<?> sequence, CharSequence separator) {
		StringBuilder result = new StringBuilder();
		try {
			join(sequence, separator, result);
		} catch (IOException ex) {
			// should not happen
		}
		return result.toString();
	}
	/**
	 * Joins a sequence of values with the given separator between elements
	 * and uses the supplied format string.
	 * @param sequence the sequence to join
	 * @param separator the separator between elements
	 * @param format the format string
	 * @return the joined string
	 */
	public static String join(@NonNull Iterable<?> sequence, CharSequence separator, @NonNull String format) {
		return join(sequence.iterator(), separator, format);
	}
	/**
	 * Joins a sequence of values with the given separator between elements
	 * and uses the supplied format string.
	 * @param sequence the sequence to join
	 * @param separator the separator between elements
	 * @param format the format string
	 * @return the joined string
	 */
	public static String join(@NonNull Iterator<?> sequence, CharSequence separator, @NonNull String format) {
		StringBuilder result = new StringBuilder();
		try {
			join(sequence, separator, format, result);
		} catch (IOException ex) {
			// should not happen
		}
		return result.toString();
	}
}
