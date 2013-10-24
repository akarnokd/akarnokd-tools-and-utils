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

import hu.akarnokd.reactive4java.base.Action1E;
import hu.akarnokd.reactive4java.base.Func1;
import hu.akarnokd.reactive4java.base.Func1E;
import hu.akarnokd.reactive4java.query.IterableBuilder;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
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
		boolean first = true;
		while (sequence.hasNext()) {
			if (!first) {
				append.append(separator);
			}
			append.append(String.valueOf(sequence.next()));
			first = false;
		}
	}
	/**
	 * Joins a sequence of values with the given separator between elements.
	 * @param sequence the sequence to join
	 * @param separator the separator between elements
	 * @param append the output appender
	 */
	public static void join(@NonNull Iterator<?> sequence, CharSequence separator, @NonNull StringBuilder append) {
		boolean first = true;
		while (sequence.hasNext()) {
			if (!first) {
				append.append(separator);
			}
			append.append(String.valueOf(sequence.next()));
			first = false;
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
		boolean first = true;
		while (sequence.hasNext()) {
			if (!first) {
				append.append(separator);
			}
			append.append(String.format(format, sequence.next()));
			first = false;
		}
	}
	/**
	 * Joins a sequence of values with the given separator between elements
	 * and uses the supplied format string.
	 * @param sequence the sequence to join
	 * @param separator the separator between elements
	 * @param format the format string
	 * @param append the output appender
	 */
	public static void join(@NonNull Iterator<?> sequence, CharSequence separator, @NonNull String format, @NonNull StringBuilder append) {
		boolean first = true;
		while (sequence.hasNext()) {
			if (!first) {
				append.append(separator);
			}
			append.append(String.format(format, sequence.next()));
			first = false;
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
		join(sequence, separator, result);
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
		join(sequence, separator, format, result);
		return result.toString();
	}
	/**
	 * Creates an action which fills the target collection from
	 * the resultset by using the mapper function.
	 * @param <T> the element type
	 * @param out the output collection
	 * @param map the mapper function
	 * @return the action
	 */
	@NonNull
	public static <T> Action1E<ResultSet, SQLException> into(
			@NonNull final Collection<? super T> out, 
			@NonNull final Func1E<? super ResultSet, ? extends T, ? extends SQLException> map) {
		return new Action1E<ResultSet, SQLException>() {
			@Override
			public void invoke(ResultSet t) throws SQLException {
				out.add(map.invoke(t));
			}
		};
	}
    /**
     * Wrap the sequence into a trimmed sequence.
     * @param sequence the source sequence
     * @return the output sequence
     */
    @NonNull
    public static Iterable<String> trim(@NonNull Iterable<String> sequence) {
    	return IterableBuilder.from(sequence).select(new Func1<String, String>() {
    		@Override
    		public String invoke(String param1) {
    			return param1.trim();
    		}
    	});
    }
}
