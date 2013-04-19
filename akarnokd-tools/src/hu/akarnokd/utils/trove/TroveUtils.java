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

package hu.akarnokd.utils.trove;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import edu.umd.cs.findbugs.annotations.NonNull;
import gnu.trove.TByteCollection;
import gnu.trove.TCharCollection;
import gnu.trove.TDoubleCollection;
import gnu.trove.TFloatCollection;
import gnu.trove.TIntCollection;
import gnu.trove.TLongCollection;
import gnu.trove.TShortCollection;
import gnu.trove.iterator.TByteIterator;
import gnu.trove.iterator.TCharIterator;
import gnu.trove.iterator.TDoubleIterator;
import gnu.trove.iterator.TFloatIterator;
import gnu.trove.iterator.TIntIterator;
import gnu.trove.iterator.TLongIterator;
import gnu.trove.iterator.TShortIterator;
import gnu.trove.list.TByteList;
import gnu.trove.list.TCharList;
import gnu.trove.list.TDoubleList;
import gnu.trove.list.TFloatList;
import gnu.trove.list.TIntList;
import gnu.trove.list.TLongList;
import gnu.trove.list.TShortList;
import gnu.trove.list.array.TByteArrayList;
import gnu.trove.list.array.TCharArrayList;
import gnu.trove.list.array.TDoubleArrayList;
import gnu.trove.list.array.TFloatArrayList;
import gnu.trove.list.array.TIntArrayList;
import gnu.trove.list.array.TLongArrayList;
import gnu.trove.list.array.TShortArrayList;
import hu.akarnokd.reactive4java.base.Func0;

/**
 * Utility methods for Trove classes.
 * @author akarnokd, 2013.04.19.
 */
public final class TroveUtils {
	/** Utility class. */
	private TroveUtils() { }
	/**
	 * Splits the collection into chunks.
	 * @param collection the collection to split
	 * @param splitSize the splitting size
	 * @return the list of chunks
	 */
	@NonNull
	public static List<TByteList> split(@NonNull TByteCollection collection, int splitSize) {
		List<TByteList> result = new ArrayList<>();
		TByteList list = null;
		TByteIterator it = collection.iterator();
		while (it.hasNext()) {
			if (list == null || list.size() == splitSize) {
				list = new TByteArrayList(splitSize);
				result.add(list);
			}
			list.add(it.next());
		}
		return result;
	}
	/**
	 * Splits the collection into chunks.
	 * @param collection the collection to split
	 * @param splitSize the splitting size
	 * @return the list of chunks
	 */
	@NonNull
	public static List<TShortList> split(@NonNull TShortCollection collection, int splitSize) {
		List<TShortList> result = new ArrayList<>();
		TShortList list = null;
		TShortIterator it = collection.iterator();
		while (it.hasNext()) {
			if (list == null || list.size() == splitSize) {
				list = new TShortArrayList(splitSize);
				result.add(list);
			}
			list.add(it.next());
		}
		return result;
	}
	/**
	 * Splits the collection into chunks.
	 * @param collection the collection to split
	 * @param splitSize the splitting size
	 * @return the list of chunks
	 */
	@NonNull
	public static List<TCharList> split(@NonNull TCharCollection collection, int splitSize) {
		List<TCharList> result = new ArrayList<>();
		TCharList list = null;
		TCharIterator it = collection.iterator();
		while (it.hasNext()) {
			if (list == null || list.size() == splitSize) {
				list = new TCharArrayList(splitSize);
				result.add(list);
			}
			list.add(it.next());
		}
		return result;
	}
	/**
	 * Splits the collection into chunks.
	 * @param collection the collection to split
	 * @param splitSize the splitting size
	 * @return the list of chunks
	 */
	@NonNull
	public static List<TIntList> split(@NonNull TIntCollection collection, int splitSize) {
		List<TIntList> result = new ArrayList<>();
		TIntList list = null;
		TIntIterator it = collection.iterator();
		while (it.hasNext()) {
			if (list == null || list.size() == splitSize) {
				list = new TIntArrayList(splitSize);
				result.add(list);
			}
			list.add(it.next());
		}
		return result;
	}
	/**
	 * Splits the collection into chunks.
	 * @param collection the collection to split
	 * @param splitSize the splitting size
	 * @return the list of chunks
	 */
	@NonNull
	public static List<TLongList> split(@NonNull TLongCollection collection, int splitSize) {
		List<TLongList> result = new ArrayList<>();
		TLongList list = null;
		TLongIterator it = collection.iterator();
		while (it.hasNext()) {
			if (list == null || list.size() == splitSize) {
				list = new TLongArrayList(splitSize);
				result.add(list);
			}
			list.add(it.next());
		}
		return result;
	}
	/**
	 * Splits the collection into chunks.
	 * @param collection the collection to split
	 * @param splitSize the splitting size
	 * @return the list of chunks
	 */
	@NonNull
	public static List<TFloatList> split(@NonNull TFloatCollection collection, int splitSize) {
		List<TFloatList> result = new ArrayList<>();
		TFloatList list = null;
		TFloatIterator it = collection.iterator();
		while (it.hasNext()) {
			if (list == null || list.size() == splitSize) {
				list = new TFloatArrayList(splitSize);
				result.add(list);
			}
			list.add(it.next());
		}
		return result;
	}
	/**
	 * Splits the collection into chunks.
	 * @param collection the collection to split
	 * @param splitSize the splitting size
	 * @return the list of chunks
	 */
	@NonNull
	public static List<TDoubleList> split(@NonNull TDoubleCollection collection, int splitSize) {
		List<TDoubleList> result = new ArrayList<>();
		TDoubleList list = null;
		TDoubleIterator it = collection.iterator();
		while (it.hasNext()) {
			if (list == null || list.size() == splitSize) {
				list = new TDoubleArrayList(splitSize);
				result.add(list);
			}
			list.add(it.next());
		}
		return result;
	}
	/**
	 * Splits the collection into chunks.
	 * @param collection the collection to split
	 * @param splitSize the splitting size
	 * @param factory the collection creator
	 * @return the list of chunks
	 */
	@NonNull
	public static List<TByteCollection> split(@NonNull TByteCollection collection, int splitSize, @NonNull Func0<? extends TByteCollection> factory) {
		List<TByteCollection> result = new ArrayList<>();
		TByteCollection list = null;
		TByteIterator it = collection.iterator();
		while (it.hasNext()) {
			if (list == null || list.size() == splitSize) {
				list = factory.invoke();
				result.add(list);
			}
			list.add(it.next());
		}
		return result;
	}
	/**
	 * Splits the collection into chunks.
	 * @param collection the collection to split
	 * @param splitSize the splitting size
	 * @param factory the collection creator
	 * @return the list of chunks
	 */
	@NonNull
	public static List<TShortCollection> split(@NonNull TShortCollection collection, int splitSize, @NonNull Func0<? extends TShortCollection> factory) {
		List<TShortCollection> result = new ArrayList<>();
		TShortCollection list = null;
		TShortIterator it = collection.iterator();
		while (it.hasNext()) {
			if (list == null || list.size() == splitSize) {
				list = factory.invoke();
				result.add(list);
			}
			list.add(it.next());
		}
		return result;
	}
	/**
	 * Splits the collection into chunks.
	 * @param collection the collection to split
	 * @param splitSize the splitting size
	 * @param factory the collection creator
	 * @return the list of chunks
	 */
	@NonNull
	public static List<TCharCollection> split(@NonNull TCharCollection collection, int splitSize, @NonNull Func0<? extends TCharCollection> factory) {
		List<TCharCollection> result = new ArrayList<>();
		TCharCollection list = null;
		TCharIterator it = collection.iterator();
		while (it.hasNext()) {
			if (list == null || list.size() == splitSize) {
				list = factory.invoke();
				result.add(list);
			}
			list.add(it.next());
		}
		return result;
	}
	/**
	 * Splits the collection into chunks.
	 * @param collection the collection to split
	 * @param splitSize the splitting size
	 * @param factory the collection creator
	 * @return the list of chunks
	 */
	@NonNull
	public static List<TIntCollection> split(@NonNull TIntCollection collection, int splitSize, @NonNull Func0<? extends TIntCollection> factory) {
		List<TIntCollection> result = new ArrayList<>();
		TIntCollection list = null;
		TIntIterator it = collection.iterator();
		while (it.hasNext()) {
			if (list == null || list.size() == splitSize) {
				list = factory.invoke();
				result.add(list);
			}
			list.add(it.next());
		}
		return result;
	}
	/**
	 * Splits the collection into chunks.
	 * @param collection the collection to split
	 * @param splitSize the splitting size
	 * @param factory the collection creator
	 * @return the list of chunks
	 */
	@NonNull
	public static List<TLongCollection> split(@NonNull TLongCollection collection, int splitSize, @NonNull Func0<? extends TLongCollection> factory) {
		List<TLongCollection> result = new ArrayList<>();
		TLongCollection list = null;
		TLongIterator it = collection.iterator();
		while (it.hasNext()) {
			if (list == null || list.size() == splitSize) {
				list = factory.invoke();
				result.add(list);
			}
			list.add(it.next());
		}
		return result;
	}
	/**
	 * Splits the collection into chunks.
	 * @param collection the collection to split
	 * @param splitSize the splitting size
	 * @param factory the collection creator
	 * @return the list of chunks
	 */
	@NonNull
	public static List<TFloatCollection> split(@NonNull TFloatCollection collection, int splitSize, @NonNull Func0<? extends TFloatCollection> factory) {
		List<TFloatCollection> result = new ArrayList<>();
		TFloatCollection list = null;
		TFloatIterator it = collection.iterator();
		while (it.hasNext()) {
			if (list == null || list.size() == splitSize) {
				list = factory.invoke();
				result.add(list);
			}
			list.add(it.next());
		}
		return result;
	}
	/**
	 * Splits the collection into chunks.
	 * @param collection the collection to split
	 * @param splitSize the splitting size
	 * @param factory the collection creator
	 * @return the list of chunks
	 */
	@NonNull
	public static List<TDoubleCollection> split(@NonNull TDoubleCollection collection, int splitSize, @NonNull Func0<? extends TDoubleCollection> factory) {
		List<TDoubleCollection> result = new ArrayList<>();
		TDoubleCollection list = null;
		TDoubleIterator it = collection.iterator();
		while (it.hasNext()) {
			if (list == null || list.size() == splitSize) {
				list = factory.invoke();
				result.add(list);
			}
			list.add(it.next());
		}
		return result;
	}
	/**
	 * Join the collection values into a string with the given separator between
	 * elements.
	 * @param collection the collection to joint
	 * @param separator the separator between elements
	 * @param append the output of the append
	 * @throws IOException if the append's methods throw it
	 */
	public static void join(@NonNull TByteCollection collection, CharSequence separator, @NonNull Appendable append) throws IOException {
		int i = 0;
		TByteIterator it = collection.iterator();
		while (it.hasNext()) {
			if (i > 0) {
				append.append(separator);
			}
			append.append(Byte.toString(it.next()));
		}
	}
	/**
	 * Join the collection values into a string with the given separator between
	 * elements.
	 * @param collection the collection to joint
	 * @param separator the separator between elements
	 * @return the joint string
	 */
	public static String join(@NonNull TByteCollection collection, CharSequence separator) {
		StringBuilder result = new StringBuilder();
		try {
			join(collection, separator, result);
		} catch (IOException ex) {
			// should not happen
		}
		return result.toString();
	}
	/**
	 * Join the collection values into a string with the given separator between
	 * elements.
	 * @param collection the collection to joint
	 * @param separator the separator between elements
	 * @param append the output of the append
	 * @throws IOException if the append's methods throw it
	 */
	public static void join(@NonNull TShortCollection collection, CharSequence separator, @NonNull Appendable append) throws IOException {
		int i = 0;
		TShortIterator it = collection.iterator();
		while (it.hasNext()) {
			if (i > 0) {
				append.append(separator);
			}
			append.append(Short.toString(it.next()));
		}
	}
	/**
	 * Join the collection values into a string with the given separator between
	 * elements.
	 * @param collection the collection to joint
	 * @param separator the separator between elements
	 * @return the joint string
	 */
	public static String join(@NonNull TShortCollection collection, CharSequence separator) {
		StringBuilder result = new StringBuilder();
		try {
			join(collection, separator, result);
		} catch (IOException ex) {
			// should not happen
		}
		return result.toString();
	}
	/**
	 * Join the collection values into a string with the given separator between
	 * elements.
	 * @param collection the collection to joint
	 * @param separator the separator between elements
	 * @param append the output of the append
	 * @throws IOException if the append's methods throw it
	 */
	public static void join(@NonNull TCharCollection collection, CharSequence separator, @NonNull Appendable append) throws IOException {
		int i = 0;
		TCharIterator it = collection.iterator();
		while (it.hasNext()) {
			if (i > 0) {
				append.append(separator);
			}
			append.append(it.next());
		}
	}
	/**
	 * Join the collection values into a string with the given separator between
	 * elements.
	 * @param collection the collection to joint
	 * @param separator the separator between elements
	 * @return the joint string
	 */
	public static String join(@NonNull TCharCollection collection, CharSequence separator) {
		StringBuilder result = new StringBuilder();
		try {
			join(collection, separator, result);
		} catch (IOException ex) {
			// should not happen
		}
		return result.toString();
	}
	/**
	 * Join the collection values into a string with the given separator between
	 * elements.
	 * @param collection the collection to joint
	 * @param separator the separator between elements
	 * @param append the output of the append
	 * @throws IOException if the append's methods throw it
	 */
	public static void join(@NonNull TIntCollection collection, CharSequence separator, @NonNull Appendable append) throws IOException {
		int i = 0;
		TIntIterator it = collection.iterator();
		while (it.hasNext()) {
			if (i > 0) {
				append.append(separator);
			}
			append.append(Integer.toString(it.next()));
		}
	}
	/**
	 * Join the collection values into a string with the given separator between
	 * elements.
	 * @param collection the collection to joint
	 * @param separator the separator between elements
	 * @return the joint string
	 */
	public static String join(@NonNull TIntCollection collection, CharSequence separator) {
		StringBuilder result = new StringBuilder();
		try {
			join(collection, separator, result);
		} catch (IOException ex) {
			// should not happen
		}
		return result.toString();
	}
	/**
	 * Join the collection values into a string with the given separator between
	 * elements.
	 * @param collection the collection to joint
	 * @param separator the separator between elements
	 * @param append the output of the append
	 * @throws IOException if the append's methods throw it
	 */
	public static void join(@NonNull TLongCollection collection, CharSequence separator, @NonNull Appendable append) throws IOException {
		int i = 0;
		TLongIterator it = collection.iterator();
		while (it.hasNext()) {
			if (i > 0) {
				append.append(separator);
			}
			append.append(Long.toString(it.next()));
		}
	}
	/**
	 * Join the collection values into a string with the given separator between
	 * elements.
	 * @param collection the collection to joint
	 * @param separator the separator between elements
	 * @return the joint string
	 */
	public static String join(@NonNull TLongCollection collection, CharSequence separator) {
		StringBuilder result = new StringBuilder();
		try {
			join(collection, separator, result);
		} catch (IOException ex) {
			// should not happen
		}
		return result.toString();
	}
	/**
	 * Join the collection values into a string with the given separator between
	 * elements.
	 * @param collection the collection to joint
	 * @param separator the separator between elements
	 * @param append the output of the append
	 * @throws IOException if the append's methods throw it
	 */
	public static void join(@NonNull TFloatCollection collection, CharSequence separator, @NonNull Appendable append) throws IOException {
		int i = 0;
		TFloatIterator it = collection.iterator();
		while (it.hasNext()) {
			if (i > 0) {
				append.append(separator);
			}
			append.append(Float.toString(it.next()));
		}
	}
	/**
	 * Join the collection values into a string with the given separator between
	 * elements.
	 * @param collection the collection to joint
	 * @param separator the separator between elements
	 * @return the joint string
	 */
	public static String join(@NonNull TFloatCollection collection, CharSequence separator) {
		StringBuilder result = new StringBuilder();
		try {
			join(collection, separator, result);
		} catch (IOException ex) {
			// should not happen
		}
		return result.toString();
	}
	/**
	 * Join the collection values into a string with the given separator between
	 * elements.
	 * @param collection the collection to joint
	 * @param separator the separator between elements
	 * @param append the output of the append
	 * @throws IOException if the append's methods throw it
	 */
	public static void join(@NonNull TDoubleCollection collection, CharSequence separator, @NonNull Appendable append) throws IOException {
		int i = 0;
		TDoubleIterator it = collection.iterator();
		while (it.hasNext()) {
			if (i > 0) {
				append.append(separator);
			}
			append.append(Double.toString(it.next()));
		}
	}
	/**
	 * Join the collection values into a string with the given separator between
	 * elements.
	 * @param collection the collection to joint
	 * @param separator the separator between elements
	 * @return the joint string
	 */
	public static String join(@NonNull TDoubleCollection collection, CharSequence separator) {
		StringBuilder result = new StringBuilder();
		try {
			join(collection, separator, result);
		} catch (IOException ex) {
			// should not happen
		}
		return result.toString();
	}
	/**
	 * Join the collection values into a string with the given separator between
	 * elements.
	 * @param collection the collection to joint
	 * @param separator the separator between elements
	 * @param format the string formatting
	 * @param append the output of the append
	 * @throws IOException if the append's methods throw it
	 */
	public static void join(@NonNull TByteCollection collection, CharSequence separator, String format, @NonNull Appendable append) throws IOException {
		int i = 0;
		TByteIterator it = collection.iterator();
		while (it.hasNext()) {
			if (i > 0) {
				append.append(separator);
			}
			append.append(String.format(format, it.next()));
		}
	}
	/**
	 * Join the collection values into a string with the given separator between
	 * elements.
	 * @param collection the collection to joint
	 * @param separator the separator between elements
	 * @param format the string formatting
	 * @return the joint string
	 */
	public static String join(@NonNull TByteCollection collection, CharSequence separator, String format) {
		StringBuilder result = new StringBuilder();
		try {
			join(collection, separator, format, result);
		} catch (IOException ex) {
			// should not happen
		}
		return result.toString();
	}
	/**
	 * Join the collection values into a string with the given separator between
	 * elements.
	 * @param collection the collection to joint
	 * @param separator the separator between elements
	 * @param format the string formatting
	 * @param append the output of the append
	 * @throws IOException if the append's methods throw it
	 */
	public static void join(@NonNull TShortCollection collection, CharSequence separator, String format, @NonNull Appendable append) throws IOException {
		int i = 0;
		TShortIterator it = collection.iterator();
		while (it.hasNext()) {
			if (i > 0) {
				append.append(separator);
			}
			append.append(String.format(format, it.next()));
		}
	}
	/**
	 * Join the collection values into a string with the given separator between
	 * elements.
	 * @param collection the collection to joint
	 * @param separator the separator between elements
	 * @param format the string formatting
	 * @return the joint string
	 */
	public static String join(@NonNull TShortCollection collection, CharSequence separator, String format) {
		StringBuilder result = new StringBuilder();
		try {
			join(collection, separator, format, result);
		} catch (IOException ex) {
			// should not happen
		}
		return result.toString();
	}
	/**
	 * Join the collection values into a string with the given separator between
	 * elements.
	 * @param collection the collection to joint
	 * @param separator the separator between elements
	 * @param format the string formatting
	 * @param append the output of the append
	 * @throws IOException if the append's methods throw it
	 */
	public static void join(@NonNull TCharCollection collection, CharSequence separator, String format, @NonNull Appendable append) throws IOException {
		int i = 0;
		TCharIterator it = collection.iterator();
		while (it.hasNext()) {
			if (i > 0) {
				append.append(separator);
			}
			append.append(String.format(format, it.next()));
		}
	}
	/**
	 * Join the collection values into a string with the given separator between
	 * elements.
	 * @param collection the collection to joint
	 * @param separator the separator between elements
	 * @param format the string formatting
	 * @return the joint string
	 */
	public static String join(@NonNull TCharCollection collection, CharSequence separator, String format) {
		StringBuilder result = new StringBuilder();
		try {
			join(collection, separator, format, result);
		} catch (IOException ex) {
			// should not happen
		}
		return result.toString();
	}
	/**
	 * Join the collection values into a string with the given separator between
	 * elements.
	 * @param collection the collection to joint
	 * @param separator the separator between elements
	 * @param format the string formatting
	 * @param append the output of the append
	 * @throws IOException if the append's methods throw it
	 */
	public static void join(@NonNull TIntCollection collection, CharSequence separator, String format, @NonNull Appendable append) throws IOException {
		int i = 0;
		TIntIterator it = collection.iterator();
		while (it.hasNext()) {
			if (i > 0) {
				append.append(separator);
			}
			append.append(String.format(format, it.next()));
		}
	}
	/**
	 * Join the collection values into a string with the given separator between
	 * elements.
	 * @param collection the collection to joint
	 * @param separator the separator between elements
	 * @param format the string formatting
	 * @return the joint string
	 */
	public static String join(@NonNull TIntCollection collection, CharSequence separator, String format) {
		StringBuilder result = new StringBuilder();
		try {
			join(collection, separator, format, result);
		} catch (IOException ex) {
			// should not happen
		}
		return result.toString();
	}
	/**
	 * Join the collection values into a string with the given separator between
	 * elements.
	 * @param collection the collection to joint
	 * @param separator the separator between elements
	 * @param format the string formatting
	 * @param append the output of the append
	 * @throws IOException if the append's methods throw it
	 */
	public static void join(@NonNull TLongCollection collection, CharSequence separator, String format, @NonNull Appendable append) throws IOException {
		int i = 0;
		TLongIterator it = collection.iterator();
		while (it.hasNext()) {
			if (i > 0) {
				append.append(separator);
			}
			append.append(String.format(format, it.next()));
		}
	}
	/**
	 * Join the collection values into a string with the given separator between
	 * elements.
	 * @param collection the collection to joint
	 * @param separator the separator between elements
	 * @param format the string formatting
	 * @return the joint string
	 */
	public static String join(@NonNull TLongCollection collection, CharSequence separator, String format) {
		StringBuilder result = new StringBuilder();
		try {
			join(collection, separator, format, result);
		} catch (IOException ex) {
			// should not happen
		}
		return result.toString();
	}
	/**
	 * Join the collection values into a string with the given separator between
	 * elements.
	 * @param collection the collection to joint
	 * @param separator the separator between elements
	 * @param format the string formatting
	 * @param append the output of the append
	 * @throws IOException if the append's methods throw it
	 */
	public static void join(@NonNull TFloatCollection collection, CharSequence separator, String format, @NonNull Appendable append) throws IOException {
		int i = 0;
		TFloatIterator it = collection.iterator();
		while (it.hasNext()) {
			if (i > 0) {
				append.append(separator);
			}
			append.append(String.format(format, it.next()));
		}
	}
	/**
	 * Join the collection values into a string with the given separator between
	 * elements.
	 * @param collection the collection to joint
	 * @param separator the separator between elements
	 * @param format the string formatting
	 * @return the joint string
	 */
	public static String join(@NonNull TFloatCollection collection, CharSequence separator, String format) {
		StringBuilder result = new StringBuilder();
		try {
			join(collection, separator, format, result);
		} catch (IOException ex) {
			// should not happen
		}
		return result.toString();
	}
	/**
	 * Join the collection values into a string with the given separator between
	 * elements.
	 * @param collection the collection to joint
	 * @param separator the separator between elements
	 * @param format the string formatting
	 * @param append the output of the append
	 * @throws IOException if the append's methods throw it
	 */
	public static void join(@NonNull TDoubleCollection collection, CharSequence separator, String format, @NonNull Appendable append) throws IOException {
		int i = 0;
		TDoubleIterator it = collection.iterator();
		while (it.hasNext()) {
			if (i > 0) {
				append.append(separator);
			}
			append.append(String.format(format, it.next()));
		}
	}
	/**
	 * Join the collection values into a string with the given separator between
	 * elements.
	 * @param collection the collection to joint
	 * @param separator the separator between elements
	 * @param format the string formatting
	 * @return the joint string
	 */
	public static String join(@NonNull TDoubleCollection collection, CharSequence separator, String format) {
		StringBuilder result = new StringBuilder();
		try {
			join(collection, separator, format, result);
		} catch (IOException ex) {
			// should not happen
		}
		return result.toString();
	}
	/**
	 * Adds the values from the source collection to 
	 * the target collection.
	 * @param target the target collection
	 * @param source the source collection
	 */
	public static void addAll(@NonNull TShortCollection target, @NonNull TByteCollection source) {
		TByteIterator it = source.iterator();
		while (it.hasNext()) {
			target.add(it.next());
		}
	}
	/**
	 * Adds the values from the source collection to 
	 * the target collection.
	 * @param target the target collection
	 * @param source the source collection
	 */
	public static void addAll(@NonNull TIntCollection target, @NonNull TByteCollection source) {
		TByteIterator it = source.iterator();
		while (it.hasNext()) {
			target.add(it.next());
		}
	}
	/**
	 * Adds the values from the source collection to 
	 * the target collection.
	 * @param target the target collection
	 * @param source the source collection
	 */
	public static void addAll(@NonNull TLongCollection target, @NonNull TByteCollection source) {
		TByteIterator it = source.iterator();
		while (it.hasNext()) {
			target.add(it.next());
		}
	}
	/**
	 * Adds the values from the source collection to 
	 * the target collection.
	 * @param target the target collection
	 * @param source the source collection
	 */
	public static void addAll(@NonNull TFloatCollection target, @NonNull TByteCollection source) {
		TByteIterator it = source.iterator();
		while (it.hasNext()) {
			target.add(it.next());
		}
	}
	/**
	 * Adds the values from the source collection to 
	 * the target collection.
	 * @param target the target collection
	 * @param source the source collection
	 */
	public static void addAll(@NonNull TDoubleCollection target, @NonNull TByteCollection source) {
		TByteIterator it = source.iterator();
		while (it.hasNext()) {
			target.add(it.next());
		}
	}
	/**
	 * Adds the values from the source collection to 
	 * the target collection.
	 * @param target the target collection
	 * @param source the source collection
	 */
	public static void addAll(@NonNull TCharCollection target, @NonNull TByteCollection source) {
		TByteIterator it = source.iterator();
		while (it.hasNext()) {
			target.add((char)it.next());
		}
	}
	/**
	 * Adds the values from the source collection to 
	 * the target collection.
	 * @param target the target collection
	 * @param source the source collection
	 */
	public static void addAll(@NonNull TCharCollection target, @NonNull TShortCollection source) {
		TShortIterator it = source.iterator();
		while (it.hasNext()) {
			target.add((char)it.next());
		}
	}
	/**
	 * Adds the values from the source collection to 
	 * the target collection.
	 * @param target the target collection
	 * @param source the source collection
	 */
	public static void addAll(@NonNull TShortCollection target, @NonNull TCharCollection source) {
		TCharIterator it = source.iterator();
		while (it.hasNext()) {
			target.add((short)it.next());
		}
	}
	/**
	 * Adds the values from the source collection to 
	 * the target collection.
	 * @param target the target collection
	 * @param source the source collection
	 */
	public static void addAll(@NonNull TIntCollection target, @NonNull TShortCollection source) {
		TShortIterator it = source.iterator();
		while (it.hasNext()) {
			target.add(it.next());
		}
	}
	/**
	 * Adds the values from the source collection to 
	 * the target collection.
	 * @param target the target collection
	 * @param source the source collection
	 */
	public static void addAll(@NonNull TIntCollection target, @NonNull TCharCollection source) {
		TCharIterator it = source.iterator();
		while (it.hasNext()) {
			target.add(it.next());
		}
	}
	/**
	 * Adds the values from the source collection to 
	 * the target collection.
	 * @param target the target collection
	 * @param source the source collection
	 */
	public static void addAll(@NonNull TLongCollection target, @NonNull TShortCollection source) {
		TShortIterator it = source.iterator();
		while (it.hasNext()) {
			target.add(it.next());
		}
	}
	/**
	 * Adds the values from the source collection to 
	 * the target collection.
	 * @param target the target collection
	 * @param source the source collection
	 */
	public static void addAll(@NonNull TLongCollection target, @NonNull TCharCollection source) {
		TCharIterator it = source.iterator();
		while (it.hasNext()) {
			target.add(it.next());
		}
	}
	/**
	 * Adds the values from the source collection to 
	 * the target collection.
	 * @param target the target collection
	 * @param source the source collection
	 */
	public static void addAll(@NonNull TLongCollection target, @NonNull TIntCollection source) {
		TIntIterator it = source.iterator();
		while (it.hasNext()) {
			target.add(it.next());
		}
	}
	/**
	 * Adds the values from the source collection to 
	 * the target collection.
	 * @param target the target collection
	 * @param source the source collection
	 */
	public static void addAll(@NonNull TDoubleCollection target, @NonNull TShortCollection source) {
		TShortIterator it = source.iterator();
		while (it.hasNext()) {
			target.add(it.next());
		}
	}
	/**
	 * Adds the values from the source collection to 
	 * the target collection.
	 * @param target the target collection
	 * @param source the source collection
	 */
	public static void addAll(@NonNull TDoubleCollection target, @NonNull TCharCollection source) {
		TCharIterator it = source.iterator();
		while (it.hasNext()) {
			target.add(it.next());
		}
	}
	/**
	 * Adds the values from the source collection to 
	 * the target collection.
	 * @param target the target collection
	 * @param source the source collection
	 */
	public static void addAll(@NonNull TDoubleCollection target, @NonNull TIntCollection source) {
		TIntIterator it = source.iterator();
		while (it.hasNext()) {
			target.add(it.next());
		}
	}
	/**
	 * Adds the values from the source collection to 
	 * the target collection.
	 * @param target the target collection
	 * @param source the source collection
	 */
	public static void addAll(@NonNull TDoubleCollection target, @NonNull TFloatCollection source) {
		TFloatIterator it = source.iterator();
		while (it.hasNext()) {
			target.add(it.next());
		}
	}
	/**
	 * Adds the values from the source collection to 
	 * the target collection.
	 * @param target the target collection
	 * @param source the source collection
	 */
	public static void addAll(@NonNull TDoubleCollection target, @NonNull TLongCollection source) {
		TLongIterator it = source.iterator();
		while (it.hasNext()) {
			target.add(it.next());
		}
	}
	/**
	 * Adds the values from the source collection to 
	 * the target collection.
	 * @param target the target collection
	 * @param source the source collection
	 */
	public static void addAll(@NonNull TFloatCollection target, @NonNull TShortCollection source) {
		TShortIterator it = source.iterator();
		while (it.hasNext()) {
			target.add(it.next());
		}
	}
	/**
	 * Adds the values from the source collection to 
	 * the target collection.
	 * @param target the target collection
	 * @param source the source collection
	 */
	public static void addAll(@NonNull TFloatCollection target, @NonNull TCharCollection source) {
		TCharIterator it = source.iterator();
		while (it.hasNext()) {
			target.add(it.next());
		}
	}
	/**
	 * Adds the values from the source collection to 
	 * the target collection.
	 * @param target the target collection
	 * @param source the source collection
	 */
	public static void addAll(@NonNull TFloatCollection target, @NonNull TIntCollection source) {
		TIntIterator it = source.iterator();
		while (it.hasNext()) {
			target.add(it.next());
		}
	}
	/**
	 * Adds the values from the source collection to 
	 * the target collection.
	 * @param target the target collection
	 * @param source the source collection
	 */
	public static void addAll(@NonNull TByteCollection target, Iterable<? extends Number> source) {
		for (Number n : source) {
			target.add(n.byteValue());
		}
	}
	/**
	 * Adds the values from the source collection to 
	 * the target collection.
	 * @param target the target collection
	 * @param source the source collection
	 */
	public static void addAll(@NonNull TShortCollection target, Iterable<? extends Number> source) {
		for (Number n : source) {
			target.add(n.shortValue());
		}
	}
	/**
	 * Adds the values from the source collection to 
	 * the target collection.
	 * @param target the target collection
	 * @param source the source collection
	 */
	public static void addAll(@NonNull TCharCollection target, Iterable<? extends Character> source) {
		for (Character n : source) {
			target.add(n);
		}
	}
	/**
	 * Adds the values from the source collection to 
	 * the target collection.
	 * @param target the target collection
	 * @param source the source collection
	 */
	public static void addAll(@NonNull TIntCollection target, Iterable<? extends Number> source) {
		for (Number n : source) {
			target.add(n.intValue());
		}
	}
	/**
	 * Adds the values from the source collection to 
	 * the target collection.
	 * @param target the target collection
	 * @param source the source collection
	 */
	public static void addAll(@NonNull TLongCollection target, Iterable<? extends Number> source) {
		for (Number n : source) {
			target.add(n.longValue());
		}
	}
	/**
	 * Adds the values from the source collection to 
	 * the target collection.
	 * @param target the target collection
	 * @param source the source collection
	 */
	public static void addAll(@NonNull TFloatCollection target, Iterable<? extends Number> source) {
		for (Number n : source) {
			target.add(n.floatValue());
		}
	}
	/**
	 * Adds the values from the source collection to 
	 * the target collection.
	 * @param target the target collection
	 * @param source the source collection
	 */
	public static void addAll(@NonNull TDoubleCollection target, Iterable<? extends Number> source) {
		for (Number n : source) {
			target.add(n.doubleValue());
		}
	}
	/**
	 * Adds the elements of the source collection into the autoboxed collection.
	 * @param target the target autoboxed collection
	 * @param source the source collection
	 */
	public static void addAllBytes(
			@NonNull Collection<? super Byte> target, 
			@NonNull TByteCollection source) {
		TByteIterator it = source.iterator();
		while (it.hasNext()) {
			target.add(it.next());
		}
	}
	/**
	 * Adds the elements of the source collection into the autoboxed collection.
	 * @param target the target autoboxed collection
	 * @param source the source collection
	 */
	public static void addAllShorts(
			@NonNull Collection<? super Short> target, 
			@NonNull TShortCollection source) {
		TShortIterator it = source.iterator();
		while (it.hasNext()) {
			target.add(it.next());
		}
	}
	/**
	 * Adds the elements of the source collection into the autoboxed collection.
	 * @param target the target autoboxed collection
	 * @param source the source collection
	 */
	public static void addAllChars(
			@NonNull Collection<? super Character> target, 
			@NonNull TCharCollection source) {
		TCharIterator it = source.iterator();
		while (it.hasNext()) {
			target.add(it.next());
		}
	}
	/**
	 * Adds the elements of the source collection into the autoboxed collection.
	 * @param target the target autoboxed collection
	 * @param source the source collection
	 */
	public static void addAllInts(
			@NonNull Collection<? super Integer> target, 
			@NonNull TIntCollection source) {
		TIntIterator it = source.iterator();
		while (it.hasNext()) {
			target.add(it.next());
		}
	}
	/**
	 * Adds the elements of the source collection into the autoboxed collection.
	 * @param target the target autoboxed collection
	 * @param source the source collection
	 */
	public static void addAllLongs(
			@NonNull Collection<? super Long> target, 
			@NonNull TLongCollection source) {
		TLongIterator it = source.iterator();
		while (it.hasNext()) {
			target.add(it.next());
		}
	}
	/**
	 * Adds the elements of the source collection into the autoboxed collection.
	 * @param target the target autoboxed collection
	 * @param source the source collection
	 */
	public static void addAllFloats(
			@NonNull Collection<? super Float> target, 
			@NonNull TFloatCollection source) {
		TFloatIterator it = source.iterator();
		while (it.hasNext()) {
			target.add(it.next());
		}
	}
	/**
	 * Adds the elements of the source collection into the autoboxed collection.
	 * @param target the target autoboxed collection
	 * @param source the source collection
	 */
	public static void addAllDoubles(
			@NonNull Collection<? super Double> target, 
			@NonNull TDoubleCollection source) {
		TDoubleIterator it = source.iterator();
		while (it.hasNext()) {
			target.add(it.next());
		}
	}
}
