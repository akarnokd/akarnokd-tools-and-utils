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

package hu.akarnokd.utils.trove;

import ix.util.Action1E;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import rx.functions.Func0;
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
import gnu.trove.map.TByteByteMap;
import gnu.trove.map.TByteCharMap;
import gnu.trove.map.TByteDoubleMap;
import gnu.trove.map.TByteFloatMap;
import gnu.trove.map.TByteIntMap;
import gnu.trove.map.TByteLongMap;
import gnu.trove.map.TByteObjectMap;
import gnu.trove.map.TByteShortMap;
import gnu.trove.map.TCharByteMap;
import gnu.trove.map.TCharCharMap;
import gnu.trove.map.TCharDoubleMap;
import gnu.trove.map.TCharFloatMap;
import gnu.trove.map.TCharIntMap;
import gnu.trove.map.TCharLongMap;
import gnu.trove.map.TCharObjectMap;
import gnu.trove.map.TCharShortMap;
import gnu.trove.map.TDoubleByteMap;
import gnu.trove.map.TDoubleCharMap;
import gnu.trove.map.TDoubleDoubleMap;
import gnu.trove.map.TDoubleFloatMap;
import gnu.trove.map.TDoubleIntMap;
import gnu.trove.map.TDoubleLongMap;
import gnu.trove.map.TDoubleObjectMap;
import gnu.trove.map.TDoubleShortMap;
import gnu.trove.map.TFloatByteMap;
import gnu.trove.map.TFloatCharMap;
import gnu.trove.map.TFloatDoubleMap;
import gnu.trove.map.TFloatFloatMap;
import gnu.trove.map.TFloatIntMap;
import gnu.trove.map.TFloatLongMap;
import gnu.trove.map.TFloatObjectMap;
import gnu.trove.map.TFloatShortMap;
import gnu.trove.map.TIntByteMap;
import gnu.trove.map.TIntCharMap;
import gnu.trove.map.TIntDoubleMap;
import gnu.trove.map.TIntFloatMap;
import gnu.trove.map.TIntIntMap;
import gnu.trove.map.TIntLongMap;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.TIntShortMap;
import gnu.trove.map.TLongByteMap;
import gnu.trove.map.TLongCharMap;
import gnu.trove.map.TLongDoubleMap;
import gnu.trove.map.TLongFloatMap;
import gnu.trove.map.TLongIntMap;
import gnu.trove.map.TLongLongMap;
import gnu.trove.map.TLongObjectMap;
import gnu.trove.map.TLongShortMap;
import gnu.trove.map.TObjectByteMap;
import gnu.trove.map.TObjectCharMap;
import gnu.trove.map.TObjectDoubleMap;
import gnu.trove.map.TObjectFloatMap;
import gnu.trove.map.TObjectIntMap;
import gnu.trove.map.TObjectLongMap;
import gnu.trove.map.TObjectShortMap;
import gnu.trove.map.TShortByteMap;
import gnu.trove.map.TShortCharMap;
import gnu.trove.map.TShortDoubleMap;
import gnu.trove.map.TShortFloatMap;
import gnu.trove.map.TShortIntMap;
import gnu.trove.map.TShortLongMap;
import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.TShortShortMap;
import gnu.trove.map.hash.TByteByteHashMap;
import gnu.trove.map.hash.TByteCharHashMap;
import gnu.trove.map.hash.TByteDoubleHashMap;
import gnu.trove.map.hash.TByteFloatHashMap;
import gnu.trove.map.hash.TByteIntHashMap;
import gnu.trove.map.hash.TByteLongHashMap;
import gnu.trove.map.hash.TByteObjectHashMap;
import gnu.trove.map.hash.TByteShortHashMap;
import gnu.trove.map.hash.TCharByteHashMap;
import gnu.trove.map.hash.TCharCharHashMap;
import gnu.trove.map.hash.TCharDoubleHashMap;
import gnu.trove.map.hash.TCharFloatHashMap;
import gnu.trove.map.hash.TCharIntHashMap;
import gnu.trove.map.hash.TCharLongHashMap;
import gnu.trove.map.hash.TCharObjectHashMap;
import gnu.trove.map.hash.TCharShortHashMap;
import gnu.trove.map.hash.TDoubleByteHashMap;
import gnu.trove.map.hash.TDoubleCharHashMap;
import gnu.trove.map.hash.TDoubleDoubleHashMap;
import gnu.trove.map.hash.TDoubleFloatHashMap;
import gnu.trove.map.hash.TDoubleIntHashMap;
import gnu.trove.map.hash.TDoubleLongHashMap;
import gnu.trove.map.hash.TDoubleObjectHashMap;
import gnu.trove.map.hash.TDoubleShortHashMap;
import gnu.trove.map.hash.TFloatByteHashMap;
import gnu.trove.map.hash.TFloatCharHashMap;
import gnu.trove.map.hash.TFloatDoubleHashMap;
import gnu.trove.map.hash.TFloatFloatHashMap;
import gnu.trove.map.hash.TFloatIntHashMap;
import gnu.trove.map.hash.TFloatLongHashMap;
import gnu.trove.map.hash.TFloatObjectHashMap;
import gnu.trove.map.hash.TFloatShortHashMap;
import gnu.trove.map.hash.TIntByteHashMap;
import gnu.trove.map.hash.TIntCharHashMap;
import gnu.trove.map.hash.TIntDoubleHashMap;
import gnu.trove.map.hash.TIntFloatHashMap;
import gnu.trove.map.hash.TIntIntHashMap;
import gnu.trove.map.hash.TIntLongHashMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import gnu.trove.map.hash.TIntShortHashMap;
import gnu.trove.map.hash.TLongByteHashMap;
import gnu.trove.map.hash.TLongCharHashMap;
import gnu.trove.map.hash.TLongDoubleHashMap;
import gnu.trove.map.hash.TLongFloatHashMap;
import gnu.trove.map.hash.TLongIntHashMap;
import gnu.trove.map.hash.TLongLongHashMap;
import gnu.trove.map.hash.TLongObjectHashMap;
import gnu.trove.map.hash.TLongShortHashMap;
import gnu.trove.map.hash.TObjectByteHashMap;
import gnu.trove.map.hash.TObjectCharHashMap;
import gnu.trove.map.hash.TObjectDoubleHashMap;
import gnu.trove.map.hash.TObjectFloatHashMap;
import gnu.trove.map.hash.TObjectIntHashMap;
import gnu.trove.map.hash.TObjectLongHashMap;
import gnu.trove.map.hash.TObjectShortHashMap;
import gnu.trove.map.hash.TShortByteHashMap;
import gnu.trove.map.hash.TShortCharHashMap;
import gnu.trove.map.hash.TShortDoubleHashMap;
import gnu.trove.map.hash.TShortFloatHashMap;
import gnu.trove.map.hash.TShortIntHashMap;
import gnu.trove.map.hash.TShortLongHashMap;
import gnu.trove.map.hash.TShortObjectHashMap;
import gnu.trove.map.hash.TShortShortHashMap;
import gnu.trove.procedure.TByteByteProcedure;
import gnu.trove.procedure.TByteCharProcedure;
import gnu.trove.procedure.TByteDoubleProcedure;
import gnu.trove.procedure.TByteFloatProcedure;
import gnu.trove.procedure.TByteIntProcedure;
import gnu.trove.procedure.TByteLongProcedure;
import gnu.trove.procedure.TByteObjectProcedure;
import gnu.trove.procedure.TByteShortProcedure;
import gnu.trove.procedure.TCharByteProcedure;
import gnu.trove.procedure.TCharCharProcedure;
import gnu.trove.procedure.TCharDoubleProcedure;
import gnu.trove.procedure.TCharFloatProcedure;
import gnu.trove.procedure.TCharIntProcedure;
import gnu.trove.procedure.TCharLongProcedure;
import gnu.trove.procedure.TCharObjectProcedure;
import gnu.trove.procedure.TCharShortProcedure;
import gnu.trove.procedure.TDoubleByteProcedure;
import gnu.trove.procedure.TDoubleCharProcedure;
import gnu.trove.procedure.TDoubleDoubleProcedure;
import gnu.trove.procedure.TDoubleFloatProcedure;
import gnu.trove.procedure.TDoubleIntProcedure;
import gnu.trove.procedure.TDoubleLongProcedure;
import gnu.trove.procedure.TDoubleObjectProcedure;
import gnu.trove.procedure.TDoubleShortProcedure;
import gnu.trove.procedure.TFloatByteProcedure;
import gnu.trove.procedure.TFloatCharProcedure;
import gnu.trove.procedure.TFloatDoubleProcedure;
import gnu.trove.procedure.TFloatFloatProcedure;
import gnu.trove.procedure.TFloatIntProcedure;
import gnu.trove.procedure.TFloatLongProcedure;
import gnu.trove.procedure.TFloatObjectProcedure;
import gnu.trove.procedure.TFloatShortProcedure;
import gnu.trove.procedure.TIntByteProcedure;
import gnu.trove.procedure.TIntCharProcedure;
import gnu.trove.procedure.TIntDoubleProcedure;
import gnu.trove.procedure.TIntFloatProcedure;
import gnu.trove.procedure.TIntIntProcedure;
import gnu.trove.procedure.TIntLongProcedure;
import gnu.trove.procedure.TIntObjectProcedure;
import gnu.trove.procedure.TIntShortProcedure;
import gnu.trove.procedure.TLongByteProcedure;
import gnu.trove.procedure.TLongCharProcedure;
import gnu.trove.procedure.TLongDoubleProcedure;
import gnu.trove.procedure.TLongFloatProcedure;
import gnu.trove.procedure.TLongIntProcedure;
import gnu.trove.procedure.TLongLongProcedure;
import gnu.trove.procedure.TLongObjectProcedure;
import gnu.trove.procedure.TLongShortProcedure;
import gnu.trove.procedure.TObjectByteProcedure;
import gnu.trove.procedure.TObjectCharProcedure;
import gnu.trove.procedure.TObjectDoubleProcedure;
import gnu.trove.procedure.TObjectFloatProcedure;
import gnu.trove.procedure.TObjectIntProcedure;
import gnu.trove.procedure.TObjectLongProcedure;
import gnu.trove.procedure.TObjectShortProcedure;
import gnu.trove.procedure.TShortByteProcedure;
import gnu.trove.procedure.TShortCharProcedure;
import gnu.trove.procedure.TShortDoubleProcedure;
import gnu.trove.procedure.TShortFloatProcedure;
import gnu.trove.procedure.TShortIntProcedure;
import gnu.trove.procedure.TShortLongProcedure;
import gnu.trove.procedure.TShortObjectProcedure;
import gnu.trove.procedure.TShortShortProcedure;
import gnu.trove.set.TByteSet;
import gnu.trove.set.TCharSet;
import gnu.trove.set.TDoubleSet;
import gnu.trove.set.TFloatSet;
import gnu.trove.set.TIntSet;
import gnu.trove.set.TLongSet;
import gnu.trove.set.TShortSet;
import gnu.trove.set.hash.TByteHashSet;
import gnu.trove.set.hash.TCharHashSet;
import gnu.trove.set.hash.TDoubleHashSet;
import gnu.trove.set.hash.TFloatHashSet;
import gnu.trove.set.hash.TIntHashSet;
import gnu.trove.set.hash.TLongHashSet;
import gnu.trove.set.hash.TShortHashSet;

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
				list = factory.call();
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
				list = factory.call();
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
				list = factory.call();
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
				list = factory.call();
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
				list = factory.call();
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
				list = factory.call();
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
				list = factory.call();
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
			i++;
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
			i++;
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
			i++;
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
			i++;
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
			i++;
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
			i++;
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
			i++;
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
			i++;
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
			i++;
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
			i++;
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
			i++;
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
			i++;
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
			i++;
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
			i++;
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
	/**
	 * Returns an action which adds the first column's data into the supplied collection.
	 * @param coll the target collection
	 * @return the action
	 */
	@NonNull
	public static Action1E<ResultSet, SQLException> into(@NonNull final TByteCollection coll) {
		return new Action1E<ResultSet, SQLException>() {
			@Override
			public void call(ResultSet t) throws SQLException {
				coll.add(t.getByte(1));
			}
		};
	}
	/**
	 * Returns an action which adds the first column's data into the supplied collection.
	 * @param coll the target collection
	 * @return the action
	 */
	@NonNull
	public static Action1E<ResultSet, SQLException> into(@NonNull final TShortCollection coll) {
		return new Action1E<ResultSet, SQLException>() {
			@Override
			public void call(ResultSet t) throws SQLException {
				coll.add(t.getShort(1));
			}
		};
	}
	/**
	 * Returns an action which adds the first column's data into the supplied collection.
	 * @param coll the target collection
	 * @return the action
	 */
	@NonNull
	public static Action1E<ResultSet, SQLException> into(@NonNull final TCharCollection coll) {
		return new Action1E<ResultSet, SQLException>() {
			@Override
			public void call(ResultSet t) throws SQLException {
				coll.add(t.getString(1).charAt(0));
			}
		};
	}
	/**
	 * Returns an action which adds the first column's data into the supplied collection.
	 * @param coll the target collection
	 * @return the action
	 */
	@NonNull
	public static Action1E<ResultSet, SQLException> into(@NonNull final TIntCollection coll) {
		return new Action1E<ResultSet, SQLException>() {
			@Override
			public void call(ResultSet t) throws SQLException {
				coll.add(t.getInt(1));
			}
		};
	}
	/**
	 * Returns an action which adds the first column's data into the supplied collection.
	 * @param coll the target collection
	 * @return the action
	 */
	@NonNull
	public static Action1E<ResultSet, SQLException> into(@NonNull final TLongCollection coll) {
		return new Action1E<ResultSet, SQLException>() {
			@Override
			public void call(ResultSet t) throws SQLException {
				coll.add(t.getLong(1));
			}
		};

	}
	/**
	 * Returns an action which adds the first column's data into the supplied collection.
	 * @param coll the target collection
	 * @return the action
	 */
	@NonNull
	public static Action1E<ResultSet, SQLException> into(@NonNull final TFloatCollection coll) {
		return new Action1E<ResultSet, SQLException>() {
			@Override
			public void call(ResultSet t) throws SQLException {
				coll.add(t.getFloat(1));
			}
		};
	}
	/**
	 * Returns an action which adds the first column's data into the supplied collection.
	 * @param coll the target collection
	 * @return the action
	 */
	@NonNull
	public static Action1E<ResultSet, SQLException> into(@NonNull final TDoubleCollection coll) {
		return new Action1E<ResultSet, SQLException>() {
			@Override
			public void call(ResultSet t) throws SQLException {
				coll.add(t.getDouble(1));
			}
		};
	}
	// -----------------------------
	// isNullOrEmpty START
	// -----------------------------

	/**
	 * Checks if the given collection is null or empty.
	 * @param coll the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TByteCollection coll) {
		return coll == null || coll.isEmpty();
	}
	/**
	 * Checks if the given collection is null or empty.
	 * @param coll the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TShortCollection coll) {
		return coll == null || coll.isEmpty();
	}
	/**
	 * Checks if the given collection is null or empty.
	 * @param coll the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TCharCollection coll) {
		return coll == null || coll.isEmpty();
	}
	/**
	 * Checks if the given collection is null or empty.
	 * @param coll the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TIntCollection coll) {
		return coll == null || coll.isEmpty();
	}
	/**
	 * Checks if the given collection is null or empty.
	 * @param coll the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TLongCollection coll) {
		return coll == null || coll.isEmpty();
	}
	/**
	 * Checks if the given collection is null or empty.
	 * @param coll the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TFloatCollection coll) {
		return coll == null || coll.isEmpty();
	}
	/**
	 * Checks if the given collection is null or empty.
	 * @param coll the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TDoubleCollection coll) {
		return coll == null || coll.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TByteByteMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TByteShortMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TByteCharMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TByteIntMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TByteLongMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TByteFloatMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TByteDoubleMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param <T> the object type
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static <T> boolean isNullOrEmpty(TByteObjectMap<T> map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TShortByteMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TShortShortMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TShortCharMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TShortIntMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TShortLongMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TShortFloatMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TShortDoubleMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param <T> the object type
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static <T> boolean isNullOrEmpty(TShortObjectMap<T> map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TCharByteMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TCharShortMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TCharCharMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TCharIntMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TCharLongMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TCharFloatMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TCharDoubleMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param <T> the object type
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static <T> boolean isNullOrEmpty(TCharObjectMap<T> map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TIntByteMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TIntShortMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TIntCharMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TIntIntMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TIntLongMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TIntFloatMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TIntDoubleMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param <T> the object type
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static <T> boolean isNullOrEmpty(TIntObjectMap<T> map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TLongByteMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TLongShortMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TLongCharMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TLongIntMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TLongLongMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TLongFloatMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TLongDoubleMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param <T> the object type
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static <T> boolean isNullOrEmpty(TLongObjectMap<T> map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TFloatByteMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TFloatShortMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TFloatCharMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TFloatIntMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TFloatLongMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TFloatFloatMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TFloatDoubleMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param <T> the object type
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static <T> boolean isNullOrEmpty(TFloatObjectMap<T> map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TDoubleByteMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TDoubleShortMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TDoubleCharMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TDoubleIntMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TDoubleLongMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TDoubleFloatMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static boolean isNullOrEmpty(TDoubleDoubleMap map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param <T> the object type
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static <T> boolean isNullOrEmpty(TDoubleObjectMap<T> map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param <T> the object type
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static <T> boolean isNullOrEmpty(TObjectByteMap<T> map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param <T> the object type
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static <T> boolean isNullOrEmpty(TObjectShortMap<T> map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param <T> the object type
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static <T> boolean isNullOrEmpty(TObjectCharMap<T> map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param <T> the object type
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static <T> boolean isNullOrEmpty(TObjectIntMap<T> map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param <T> the object type
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static <T> boolean isNullOrEmpty(TObjectLongMap<T> map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param <T> the object type
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static <T> boolean isNullOrEmpty(TObjectFloatMap<T> map) {
		return map == null || map.isEmpty();
	}
	/**
	 * Checks if the given map is null or empty.
	 * @param <T> the object type
	 * @param map the collection to check
	 * @return true if the collection is null or empty
	 */
	public static <T> boolean isNullOrEmpty(TObjectDoubleMap<T> map) {
		return map == null || map.isEmpty();
	}
	
	// -----------------------------
	// isNullOrEmpty END
	// -----------------------------
	
	// -----------------------------
	// singleton START
	// -----------------------------

	/**
	 * Creates a new set with the single value in it.
	 * @param value the single value
	 * @return the new set with a single value
	 */
	public static TByteSet singleton(byte value) {
		TByteSet result = new TByteHashSet();
		result.add(value);
		return result;
	}
	/**
	 * Creates a new set with the single value in it.
	 * @param value the single value
	 * @return the new set with a single value
	 */
	public static TShortSet singleton(short value) {
		TShortSet result = new TShortHashSet();
		result.add(value);
		return result;
	}
	/**
	 * Creates a new set with the single value in it.
	 * @param value the single value
	 * @return the new set with a single value
	 */
	public static TCharSet singleton(char value) {
		TCharSet result = new TCharHashSet();
		result.add(value);
		return result;
	}
	/**
	 * Creates a new set with the single value in it.
	 * @param value the single value
	 * @return the new set with a single value
	 */
	public static TIntSet singleton(int value) {
		TIntSet result = new TIntHashSet();
		result.add(value);
		return result;
	}
	/**
	 * Creates a new set with the single value in it.
	 * @param value the single value
	 * @return the new set with a single value
	 */
	public static TLongSet singleton(long value) {
		TLongSet result = new TLongHashSet();
		result.add(value);
		return result;
	}
	/**
	 * Creates a new set with the single value in it.
	 * @param value the single value
	 * @return the new set with a single value
	 */
	public static TFloatSet singleton(float value) {
		TFloatSet result = new TFloatHashSet();
		result.add(value);
		return result;
	}
	/**
	 * Creates a new set with the single value in it.
	 * @param value the single value
	 * @return the new set with a single value
	 */
	public static TDoubleSet singleton(double value) {
		TDoubleSet result = new TDoubleHashSet();
		result.add(value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TByteByteMap singletonMap(byte key, byte value) {
		TByteByteMap result = new TByteByteHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TByteShortMap singletonMap(byte key, short value) {
		TByteShortMap result = new TByteShortHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TByteCharMap singletonMap(byte key, char value) {
		TByteCharMap result = new TByteCharHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TByteIntMap singletonMap(byte key, int value) {
		TByteIntMap result = new TByteIntHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TByteLongMap singletonMap(byte key, long value) {
		TByteLongMap result = new TByteLongHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TByteFloatMap singletonMap(byte key, float value) {
		TByteFloatMap result = new TByteFloatHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TByteDoubleMap singletonMap(byte key, double value) {
		TByteDoubleMap result = new TByteDoubleHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param <T> the object type
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static <T> TByteObjectMap<T> singletonMap(byte key, T value) {
		TByteObjectMap<T> result = new TByteObjectHashMap<T>();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TShortByteMap singletonMap(short key, byte value) {
		TShortByteMap result = new TShortByteHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TShortShortMap singletonMap(short key, short value) {
		TShortShortMap result = new TShortShortHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TShortCharMap singletonMap(short key, char value) {
		TShortCharMap result = new TShortCharHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TShortIntMap singletonMap(short key, int value) {
		TShortIntMap result = new TShortIntHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TShortLongMap singletonMap(short key, long value) {
		TShortLongMap result = new TShortLongHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TShortFloatMap singletonMap(short key, float value) {
		TShortFloatMap result = new TShortFloatHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TShortDoubleMap singletonMap(short key, double value) {
		TShortDoubleMap result = new TShortDoubleHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param <T> the object type
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static <T> TShortObjectMap<T> singletonMap(short key, T value) {
		TShortObjectMap<T> result = new TShortObjectHashMap<T>();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TCharByteMap singletonMap(char key, byte value) {
		TCharByteMap result = new TCharByteHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TCharShortMap singletonMap(char key, short value) {
		TCharShortMap result = new TCharShortHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TCharCharMap singletonMap(char key, char value) {
		TCharCharMap result = new TCharCharHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TCharIntMap singletonMap(char key, int value) {
		TCharIntMap result = new TCharIntHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TCharLongMap singletonMap(char key, long value) {
		TCharLongMap result = new TCharLongHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TCharFloatMap singletonMap(char key, float value) {
		TCharFloatMap result = new TCharFloatHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TCharDoubleMap singletonMap(char key, double value) {
		TCharDoubleMap result = new TCharDoubleHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param <T> the object type
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static <T> TCharObjectMap<T> singletonMap(char key, T value) {
		TCharObjectMap<T> result = new TCharObjectHashMap<T>();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TIntByteMap singletonMap(int key, byte value) {
		TIntByteMap result = new TIntByteHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TIntShortMap singletonMap(int key, short value) {
		TIntShortMap result = new TIntShortHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TIntCharMap singletonMap(int key, char value) {
		TIntCharMap result = new TIntCharHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TIntIntMap singletonMap(int key, int value) {
		TIntIntMap result = new TIntIntHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TIntLongMap singletonMap(int key, long value) {
		TIntLongMap result = new TIntLongHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TIntFloatMap singletonMap(int key, float value) {
		TIntFloatMap result = new TIntFloatHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TIntDoubleMap singletonMap(int key, double value) {
		TIntDoubleMap result = new TIntDoubleHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param <T> the object type
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static <T> TIntObjectMap<T> singletonMap(int key, T value) {
		TIntObjectMap<T> result = new TIntObjectHashMap<T>();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TLongByteMap singletonMap(long key, byte value) {
		TLongByteMap result = new TLongByteHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TLongShortMap singletonMap(long key, short value) {
		TLongShortMap result = new TLongShortHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TLongCharMap singletonMap(long key, char value) {
		TLongCharMap result = new TLongCharHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TLongIntMap singletonMap(long key, int value) {
		TLongIntMap result = new TLongIntHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TLongLongMap singletonMap(long key, long value) {
		TLongLongMap result = new TLongLongHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TLongFloatMap singletonMap(long key, float value) {
		TLongFloatMap result = new TLongFloatHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TLongDoubleMap singletonMap(long key, double value) {
		TLongDoubleMap result = new TLongDoubleHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param <T> the object type
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static <T> TLongObjectMap<T> singletonMap(long key, T value) {
		TLongObjectMap<T> result = new TLongObjectHashMap<T>();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TFloatByteMap singletonMap(float key, byte value) {
		TFloatByteMap result = new TFloatByteHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TFloatShortMap singletonMap(float key, short value) {
		TFloatShortMap result = new TFloatShortHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TFloatCharMap singletonMap(float key, char value) {
		TFloatCharMap result = new TFloatCharHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TFloatIntMap singletonMap(float key, int value) {
		TFloatIntMap result = new TFloatIntHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TFloatLongMap singletonMap(float key, long value) {
		TFloatLongMap result = new TFloatLongHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TFloatFloatMap singletonMap(float key, float value) {
		TFloatFloatMap result = new TFloatFloatHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TFloatDoubleMap singletonMap(float key, double value) {
		TFloatDoubleMap result = new TFloatDoubleHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param <T> the object type
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static <T> TFloatObjectMap<T> singletonMap(float key, T value) {
		TFloatObjectMap<T> result = new TFloatObjectHashMap<T>();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TDoubleByteMap singletonMap(double key, byte value) {
		TDoubleByteMap result = new TDoubleByteHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TDoubleShortMap singletonMap(double key, short value) {
		TDoubleShortMap result = new TDoubleShortHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TDoubleCharMap singletonMap(double key, char value) {
		TDoubleCharMap result = new TDoubleCharHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TDoubleIntMap singletonMap(double key, int value) {
		TDoubleIntMap result = new TDoubleIntHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TDoubleLongMap singletonMap(double key, long value) {
		TDoubleLongMap result = new TDoubleLongHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TDoubleFloatMap singletonMap(double key, float value) {
		TDoubleFloatMap result = new TDoubleFloatHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static TDoubleDoubleMap singletonMap(double key, double value) {
		TDoubleDoubleMap result = new TDoubleDoubleHashMap();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param <T> the object type
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static <T> TDoubleObjectMap<T> singletonMap(double key, T value) {
		TDoubleObjectMap<T> result = new TDoubleObjectHashMap<T>();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param <T> the object type
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static <T> TObjectByteMap<T> singletonMap(T key, byte value) {
		TObjectByteMap<T> result = new TObjectByteHashMap<T>();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param <T> the object type
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static <T> TObjectShortMap<T> singletonMap(T key, short value) {
		TObjectShortMap<T> result = new TObjectShortHashMap<T>();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param <T> the object type
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static <T> TObjectCharMap<T> singletonMap(T key, char value) {
		TObjectCharMap<T> result = new TObjectCharHashMap<T>();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param <T> the object type
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static <T> TObjectIntMap<T> singletonMap(T key, int value) {
		TObjectIntMap<T> result = new TObjectIntHashMap<T>();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param <T> the object type
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static <T> TObjectLongMap<T> singletonMap(T key, long value) {
		TObjectLongMap<T> result = new TObjectLongHashMap<T>();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param <T> the object type
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static <T> TObjectFloatMap<T> singletonMap(T key, float value) {
		TObjectFloatMap<T> result = new TObjectFloatHashMap<T>();
		result.put(key, value);
		return result;
	}
	/**
	 * Creates a new map with the single key-value pair in it.
	 * @param <T> the object type
	 * @param key the key
	 * @param value the value
	 * @return the new map with a single key-value pair
	 */
	public static <T> TObjectDoubleMap<T> singletonMap(T key, double value) {
		TObjectDoubleMap<T> result = new TObjectDoubleHashMap<T>();
		result.put(key, value);
		return result;
	}
	
	// -----------------------------
	// singleton END
	// -----------------------------
	
	// -----------------------------
	// min-max-sum START
	// -----------------------------

	/**
	 * Finds the minimum value within the non-empty collection.
	 * @param coll the collection
	 * @return the minimum value
	 */
	public static byte min(TByteCollection coll) {
		TByteIterator it = coll.iterator();
		byte value = it.next();
		while (it.hasNext()) {
			byte item = it.next();
			value = item < value ? item : value;
		}
		return value;
	}
	/**
	 * Finds the maximum value within the non-empty collection.
	 * @param coll the collection
	 * @return the maximum value
	 */
	public static byte max(TByteCollection coll) {
		TByteIterator it = coll.iterator();
		byte value = it.next();
		while (it.hasNext()) {
			byte item = it.next();
			value = item > value ? item : value;
		}
		return value;
	}
	/**
	 * Computes the sum of values.
	 * @param coll the collection
	 * @return the sum
	 */
	public static long sum(TByteCollection coll) {
		TByteIterator it = coll.iterator();
		byte value = 0;
		while (it.hasNext()) {
			value += it.next();
		}
		return value;
	}
	/**
	 * Finds the minimum value within the non-empty collection.
	 * @param coll the collection
	 * @return the minimum value
	 */
	public static short min(TShortCollection coll) {
		TShortIterator it = coll.iterator();
		short value = it.next();
		while (it.hasNext()) {
			short item = it.next();
			value = item < value ? item : value;
		}
		return value;
	}
	/**
	 * Finds the maximum value within the non-empty collection.
	 * @param coll the collection
	 * @return the maximum value
	 */
	public static short max(TShortCollection coll) {
		TShortIterator it = coll.iterator();
		short value = it.next();
		while (it.hasNext()) {
			short item = it.next();
			value = item > value ? item : value;
		}
		return value;
	}
	/**
	 * Computes the sum of values.
	 * @param coll the collection
	 * @return the sum
	 */
	public static long sum(TShortCollection coll) {
		TShortIterator it = coll.iterator();
		short value = 0;
		while (it.hasNext()) {
			value += it.next();
		}
		return value;
	}
	/**
	 * Finds the minimum value within the non-empty collection.
	 * @param coll the collection
	 * @return the minimum value
	 */
	public static char min(TCharCollection coll) {
		TCharIterator it = coll.iterator();
		char value = it.next();
		while (it.hasNext()) {
			char item = it.next();
			value = item < value ? item : value;
		}
		return value;
	}
	/**
	 * Finds the maximum value within the non-empty collection.
	 * @param coll the collection
	 * @return the maximum value
	 */
	public static char max(TCharCollection coll) {
		TCharIterator it = coll.iterator();
		char value = it.next();
		while (it.hasNext()) {
			char item = it.next();
			value = item > value ? item : value;
		}
		return value;
	}
	/**
	 * Finds the minimum value within the non-empty collection.
	 * @param coll the collection
	 * @return the minimum value
	 */
	public static int min(TIntCollection coll) {
		TIntIterator it = coll.iterator();
		int value = it.next();
		while (it.hasNext()) {
			int item = it.next();
			value = item < value ? item : value;
		}
		return value;
	}
	/**
	 * Finds the maximum value within the non-empty collection.
	 * @param coll the collection
	 * @return the maximum value
	 */
	public static int max(TIntCollection coll) {
		TIntIterator it = coll.iterator();
		int value = it.next();
		while (it.hasNext()) {
			int item = it.next();
			value = item > value ? item : value;
		}
		return value;
	}
	/**
	 * Computes the sum of values.
	 * @param coll the collection
	 * @return the sum
	 */
	public static long sum(TIntCollection coll) {
		TIntIterator it = coll.iterator();
		int value = 0;
		while (it.hasNext()) {
			value += it.next();
		}
		return value;
	}
	/**
	 * Finds the minimum value within the non-empty collection.
	 * @param coll the collection
	 * @return the minimum value
	 */
	public static long min(TLongCollection coll) {
		TLongIterator it = coll.iterator();
		long value = it.next();
		while (it.hasNext()) {
			long item = it.next();
			value = item < value ? item : value;
		}
		return value;
	}
	/**
	 * Finds the maximum value within the non-empty collection.
	 * @param coll the collection
	 * @return the maximum value
	 */
	public static long max(TLongCollection coll) {
		TLongIterator it = coll.iterator();
		long value = it.next();
		while (it.hasNext()) {
			long item = it.next();
			value = item > value ? item : value;
		}
		return value;
	}
	/**
	 * Computes the sum of values.
	 * @param coll the collection
	 * @return the sum
	 */
	public static long sum(TLongCollection coll) {
		TLongIterator it = coll.iterator();
		long value = 0;
		while (it.hasNext()) {
			value += it.next();
		}
		return value;
	}
	/**
	 * Finds the minimum value within the non-empty collection.
	 * @param coll the collection
	 * @return the minimum value
	 */
	public static float min(TFloatCollection coll) {
		TFloatIterator it = coll.iterator();
		float value = it.next();
		while (it.hasNext()) {
			float item = it.next();
			value = item < value ? item : value;
		}
		return value;
	}
	/**
	 * Finds the maximum value within the non-empty collection.
	 * @param coll the collection
	 * @return the maximum value
	 */
	public static float max(TFloatCollection coll) {
		TFloatIterator it = coll.iterator();
		float value = it.next();
		while (it.hasNext()) {
			float item = it.next();
			value = item > value ? item : value;
		}
		return value;
	}
	/**
	 * Computes the sum of values.
	 * @param coll the collection
	 * @return the sum
	 */
	public static float sum(TFloatCollection coll) {
		TFloatIterator it = coll.iterator();
		float value = 0;
		while (it.hasNext()) {
			value += it.next();
		}
		return value;
	}
	/**
	 * Finds the minimum value within the non-empty collection.
	 * @param coll the collection
	 * @return the minimum value
	 */
	public static double min(TDoubleCollection coll) {
		TDoubleIterator it = coll.iterator();
		double value = it.next();
		while (it.hasNext()) {
			double item = it.next();
			value = item < value ? item : value;
		}
		return value;
	}
	/**
	 * Finds the maximum value within the non-empty collection.
	 * @param coll the collection
	 * @return the maximum value
	 */
	public static double max(TDoubleCollection coll) {
		TDoubleIterator it = coll.iterator();
		double value = it.next();
		while (it.hasNext()) {
			double item = it.next();
			value = item > value ? item : value;
		}
		return value;
	}
	/**
	 * Computes the sum of values.
	 * @param coll the collection
	 * @return the sum
	 */
	public static double sum(TDoubleCollection coll) {
		TDoubleIterator it = coll.iterator();
		double value = 0;
		while (it.hasNext()) {
			value += it.next();
		}
		return value;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static byte minKey(TByteByteMap map) {
		/** The aggregator. */
		class Proc implements TByteByteProcedure {
			/** The aggregation key. */
			byte bestKey;
			/** The aggregation value. */
			byte bestValue;
			@Override
			public boolean execute(byte a, byte b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static byte maxKey(TByteByteMap map) {
		/** The aggregator. */
		class Proc implements TByteByteProcedure {
			/** The aggregation key. */
			byte bestKey;
			/** The aggregation value. */
			byte bestValue;
			@Override
			public boolean execute(byte a, byte b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static byte minKey(TByteShortMap map) {
		/** The aggregator. */
		class Proc implements TByteShortProcedure {
			/** The aggregation key. */
			byte bestKey;
			/** The aggregation value. */
			short bestValue;
			@Override
			public boolean execute(byte a, short b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static byte maxKey(TByteShortMap map) {
		/** The aggregator. */
		class Proc implements TByteShortProcedure {
			/** The aggregation key. */
			byte bestKey;
			/** The aggregation value. */
			short bestValue;
			@Override
			public boolean execute(byte a, short b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static byte minKey(TByteCharMap map) {
		/** The aggregator. */
		class Proc implements TByteCharProcedure {
			/** The aggregation key. */
			byte bestKey;
			/** The aggregation value. */
			char bestValue;
			@Override
			public boolean execute(byte a, char b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static byte maxKey(TByteCharMap map) {
		/** The aggregator. */
		class Proc implements TByteCharProcedure {
			/** The aggregation key. */
			byte bestKey;
			/** The aggregation value. */
			char bestValue;
			@Override
			public boolean execute(byte a, char b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static byte minKey(TByteIntMap map) {
		/** The aggregator. */
		class Proc implements TByteIntProcedure {
			/** The aggregation key. */
			byte bestKey;
			/** The aggregation value. */
			int bestValue;
			@Override
			public boolean execute(byte a, int b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static byte maxKey(TByteIntMap map) {
		/** The aggregator. */
		class Proc implements TByteIntProcedure {
			/** The aggregation key. */
			byte bestKey;
			/** The aggregation value. */
			int bestValue;
			@Override
			public boolean execute(byte a, int b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static byte minKey(TByteLongMap map) {
		/** The aggregator. */
		class Proc implements TByteLongProcedure {
			/** The aggregation key. */
			byte bestKey;
			/** The aggregation value. */
			long bestValue;
			@Override
			public boolean execute(byte a, long b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static byte maxKey(TByteLongMap map) {
		/** The aggregator. */
		class Proc implements TByteLongProcedure {
			/** The aggregation key. */
			byte bestKey;
			/** The aggregation value. */
			long bestValue;
			@Override
			public boolean execute(byte a, long b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static byte minKey(TByteFloatMap map) {
		/** The aggregator. */
		class Proc implements TByteFloatProcedure {
			/** The aggregation key. */
			byte bestKey;
			/** The aggregation value. */
			float bestValue;
			@Override
			public boolean execute(byte a, float b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static byte maxKey(TByteFloatMap map) {
		/** The aggregator. */
		class Proc implements TByteFloatProcedure {
			/** The aggregation key. */
			byte bestKey;
			/** The aggregation value. */
			float bestValue;
			@Override
			public boolean execute(byte a, float b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static byte minKey(TByteDoubleMap map) {
		/** The aggregator. */
		class Proc implements TByteDoubleProcedure {
			/** The aggregation key. */
			byte bestKey;
			/** The aggregation value. */
			double bestValue;
			@Override
			public boolean execute(byte a, double b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static byte maxKey(TByteDoubleMap map) {
		/** The aggregator. */
		class Proc implements TByteDoubleProcedure {
			/** The aggregation key. */
			byte bestKey;
			/** The aggregation value. */
			double bestValue;
			@Override
			public boolean execute(byte a, double b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param <T> the value type
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static <T extends Comparable<? super T>> byte minKey(TByteObjectMap<T> map) {
		/** The aggregator. */
		class Proc implements TByteObjectProcedure<T> {
			/** The aggregation key. */
			byte bestKey;
			/** The aggregation value. */
			T bestValue;
			@Override
			public boolean execute(byte a, T b) {
				if (bestValue == null || b.compareTo(bestValue) < 0) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param <T> the value type
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static <T extends Comparable<? super T>> byte maxKey(TByteObjectMap<T> map) {
		/** The aggregator. */
		class Proc implements TByteObjectProcedure<T> {
			/** The aggregation key. */
			byte bestKey;
			/** The aggregation value. */
			T bestValue;
			@Override
			public boolean execute(byte a, T b) {
				if (bestValue == null || b.compareTo(bestValue) > 0) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param <T> the value type
	 * @param map the map
	 * @param comparator the value comparator
	 * @return the key of the minimum value
	 */
	public static <T> byte minKey(TByteObjectMap<T> map, final Comparator<? super T> comparator) {
		/** The aggregator. */
		class Proc implements TByteObjectProcedure<T> {
			/** The aggregation key. */
			byte bestKey;
			/** The aggregation value. */
			T bestValue;
			@Override
			public boolean execute(byte a, T b) {
				if (bestValue == null || comparator.compare(b, bestValue) < 0) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param <T> the value type
	 * @param map the map
	 * @param comparator the value comparator
	 * @return the key of the maximum value
	 */
	public static <T> byte maxKey(TByteObjectMap<T> map, final Comparator<? super T> comparator) {
		/** The aggregator. */
		class Proc implements TByteObjectProcedure<T> {
			/** The aggregation key. */
			byte bestKey;
			/** The aggregation value. */
			T bestValue;
			@Override
			public boolean execute(byte a, T b) {
				if (bestValue == null || comparator.compare(b, bestValue) > 0) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static short minKey(TShortByteMap map) {
		/** The aggregator. */
		class Proc implements TShortByteProcedure {
			/** The aggregation key. */
			short bestKey;
			/** The aggregation value. */
			byte bestValue;
			@Override
			public boolean execute(short a, byte b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static short maxKey(TShortByteMap map) {
		/** The aggregator. */
		class Proc implements TShortByteProcedure {
			/** The aggregation key. */
			short bestKey;
			/** The aggregation value. */
			byte bestValue;
			@Override
			public boolean execute(short a, byte b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static short minKey(TShortShortMap map) {
		/** The aggregator. */
		class Proc implements TShortShortProcedure {
			/** The aggregation key. */
			short bestKey;
			/** The aggregation value. */
			short bestValue;
			@Override
			public boolean execute(short a, short b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static short maxKey(TShortShortMap map) {
		/** The aggregator. */
		class Proc implements TShortShortProcedure {
			/** The aggregation key. */
			short bestKey;
			/** The aggregation value. */
			short bestValue;
			@Override
			public boolean execute(short a, short b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static short minKey(TShortCharMap map) {
		/** The aggregator. */
		class Proc implements TShortCharProcedure {
			/** The aggregation key. */
			short bestKey;
			/** The aggregation value. */
			char bestValue;
			@Override
			public boolean execute(short a, char b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static short maxKey(TShortCharMap map) {
		/** The aggregator. */
		class Proc implements TShortCharProcedure {
			/** The aggregation key. */
			short bestKey;
			/** The aggregation value. */
			char bestValue;
			@Override
			public boolean execute(short a, char b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static short minKey(TShortIntMap map) {
		/** The aggregator. */
		class Proc implements TShortIntProcedure {
			/** The aggregation key. */
			short bestKey;
			/** The aggregation value. */
			int bestValue;
			@Override
			public boolean execute(short a, int b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static short maxKey(TShortIntMap map) {
		/** The aggregator. */
		class Proc implements TShortIntProcedure {
			/** The aggregation key. */
			short bestKey;
			/** The aggregation value. */
			int bestValue;
			@Override
			public boolean execute(short a, int b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static short minKey(TShortLongMap map) {
		/** The aggregator. */
		class Proc implements TShortLongProcedure {
			/** The aggregation key. */
			short bestKey;
			/** The aggregation value. */
			long bestValue;
			@Override
			public boolean execute(short a, long b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static short maxKey(TShortLongMap map) {
		/** The aggregator. */
		class Proc implements TShortLongProcedure {
			/** The aggregation key. */
			short bestKey;
			/** The aggregation value. */
			long bestValue;
			@Override
			public boolean execute(short a, long b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static short minKey(TShortFloatMap map) {
		/** The aggregator. */
		class Proc implements TShortFloatProcedure {
			/** The aggregation key. */
			short bestKey;
			/** The aggregation value. */
			float bestValue;
			@Override
			public boolean execute(short a, float b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static short maxKey(TShortFloatMap map) {
		/** The aggregator. */
		class Proc implements TShortFloatProcedure {
			/** The aggregation key. */
			short bestKey;
			/** The aggregation value. */
			float bestValue;
			@Override
			public boolean execute(short a, float b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static short minKey(TShortDoubleMap map) {
		/** The aggregator. */
		class Proc implements TShortDoubleProcedure {
			/** The aggregation key. */
			short bestKey;
			/** The aggregation value. */
			double bestValue;
			@Override
			public boolean execute(short a, double b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static short maxKey(TShortDoubleMap map) {
		/** The aggregator. */
		class Proc implements TShortDoubleProcedure {
			/** The aggregation key. */
			short bestKey;
			/** The aggregation value. */
			double bestValue;
			@Override
			public boolean execute(short a, double b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param <T> the value type
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static <T extends Comparable<? super T>> short minKey(TShortObjectMap<T> map) {
		/** The aggregator. */
		class Proc implements TShortObjectProcedure<T> {
			/** The aggregation key. */
			short bestKey;
			/** The aggregation value. */
			T bestValue;
			@Override
			public boolean execute(short a, T b) {
				if (bestValue == null || b.compareTo(bestValue) < 0) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param <T> the value type
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static <T extends Comparable<? super T>> short maxKey(TShortObjectMap<T> map) {
		/** The aggregator. */
		class Proc implements TShortObjectProcedure<T> {
			/** The aggregation key. */
			short bestKey;
			/** The aggregation value. */
			T bestValue;
			@Override
			public boolean execute(short a, T b) {
				if (bestValue == null || b.compareTo(bestValue) > 0) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param <T> the value type
	 * @param map the map
	 * @param comparator the value comparator
	 * @return the key of the minimum value
	 */
	public static <T> short minKey(TShortObjectMap<T> map, final Comparator<? super T> comparator) {
		/** The aggregator. */
		class Proc implements TShortObjectProcedure<T> {
			/** The aggregation key. */
			short bestKey;
			/** The aggregation value. */
			T bestValue;
			@Override
			public boolean execute(short a, T b) {
				if (bestValue == null || comparator.compare(b, bestValue) < 0) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param <T> the value type
	 * @param map the map
	 * @param comparator the value comparator
	 * @return the key of the maximum value
	 */
	public static <T> short maxKey(TShortObjectMap<T> map, final Comparator<? super T> comparator) {
		/** The aggregator. */
		class Proc implements TShortObjectProcedure<T> {
			/** The aggregation key. */
			short bestKey;
			/** The aggregation value. */
			T bestValue;
			@Override
			public boolean execute(short a, T b) {
				if (bestValue == null || comparator.compare(b, bestValue) > 0) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static char minKey(TCharByteMap map) {
		/** The aggregator. */
		class Proc implements TCharByteProcedure {
			/** The aggregation key. */
			char bestKey;
			/** The aggregation value. */
			byte bestValue;
			@Override
			public boolean execute(char a, byte b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static char maxKey(TCharByteMap map) {
		/** The aggregator. */
		class Proc implements TCharByteProcedure {
			/** The aggregation key. */
			char bestKey;
			/** The aggregation value. */
			byte bestValue;
			@Override
			public boolean execute(char a, byte b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static char minKey(TCharShortMap map) {
		/** The aggregator. */
		class Proc implements TCharShortProcedure {
			/** The aggregation key. */
			char bestKey;
			/** The aggregation value. */
			short bestValue;
			@Override
			public boolean execute(char a, short b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static char maxKey(TCharShortMap map) {
		/** The aggregator. */
		class Proc implements TCharShortProcedure {
			/** The aggregation key. */
			char bestKey;
			/** The aggregation value. */
			short bestValue;
			@Override
			public boolean execute(char a, short b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static char minKey(TCharCharMap map) {
		/** The aggregator. */
		class Proc implements TCharCharProcedure {
			/** The aggregation key. */
			char bestKey;
			/** The aggregation value. */
			char bestValue;
			@Override
			public boolean execute(char a, char b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static char maxKey(TCharCharMap map) {
		/** The aggregator. */
		class Proc implements TCharCharProcedure {
			/** The aggregation key. */
			char bestKey;
			/** The aggregation value. */
			char bestValue;
			@Override
			public boolean execute(char a, char b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static char minKey(TCharIntMap map) {
		/** The aggregator. */
		class Proc implements TCharIntProcedure {
			/** The aggregation key. */
			char bestKey;
			/** The aggregation value. */
			int bestValue;
			@Override
			public boolean execute(char a, int b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static char maxKey(TCharIntMap map) {
		/** The aggregator. */
		class Proc implements TCharIntProcedure {
			/** The aggregation key. */
			char bestKey;
			/** The aggregation value. */
			int bestValue;
			@Override
			public boolean execute(char a, int b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static char minKey(TCharLongMap map) {
		/** The aggregator. */
		class Proc implements TCharLongProcedure {
			/** The aggregation key. */
			char bestKey;
			/** The aggregation value. */
			long bestValue;
			@Override
			public boolean execute(char a, long b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static char maxKey(TCharLongMap map) {
		/** The aggregator. */
		class Proc implements TCharLongProcedure {
			/** The aggregation key. */
			char bestKey;
			/** The aggregation value. */
			long bestValue;
			@Override
			public boolean execute(char a, long b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static char minKey(TCharFloatMap map) {
		/** The aggregator. */
		class Proc implements TCharFloatProcedure {
			/** The aggregation key. */
			char bestKey;
			/** The aggregation value. */
			float bestValue;
			@Override
			public boolean execute(char a, float b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static char maxKey(TCharFloatMap map) {
		/** The aggregator. */
		class Proc implements TCharFloatProcedure {
			/** The aggregation key. */
			char bestKey;
			/** The aggregation value. */
			float bestValue;
			@Override
			public boolean execute(char a, float b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static char minKey(TCharDoubleMap map) {
		/** The aggregator. */
		class Proc implements TCharDoubleProcedure {
			/** The aggregation key. */
			char bestKey;
			/** The aggregation value. */
			double bestValue;
			@Override
			public boolean execute(char a, double b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static char maxKey(TCharDoubleMap map) {
		/** The aggregator. */
		class Proc implements TCharDoubleProcedure {
			/** The aggregation key. */
			char bestKey;
			/** The aggregation value. */
			double bestValue;
			@Override
			public boolean execute(char a, double b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param <T> the value type
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static <T extends Comparable<? super T>> char minKey(TCharObjectMap<T> map) {
		/** The aggregator. */
		class Proc implements TCharObjectProcedure<T> {
			/** The aggregation key. */
			char bestKey;
			/** The aggregation value. */
			T bestValue;
			@Override
			public boolean execute(char a, T b) {
				if (bestValue == null || b.compareTo(bestValue) < 0) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param <T> the value type
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static <T extends Comparable<? super T>> char maxKey(TCharObjectMap<T> map) {
		/** The aggregator. */
		class Proc implements TCharObjectProcedure<T> {
			/** The aggregation key. */
			char bestKey;
			/** The aggregation value. */
			T bestValue;
			@Override
			public boolean execute(char a, T b) {
				if (bestValue == null || b.compareTo(bestValue) > 0) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param <T> the value type
	 * @param map the map
	 * @param comparator the value comparator
	 * @return the key of the minimum value
	 */
	public static <T> char minKey(TCharObjectMap<T> map, final Comparator<? super T> comparator) {
		/** The aggregator. */
		class Proc implements TCharObjectProcedure<T> {
			/** The aggregation key. */
			char bestKey;
			/** The aggregation value. */
			T bestValue;
			@Override
			public boolean execute(char a, T b) {
				if (bestValue == null || comparator.compare(b, bestValue) < 0) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param <T> the value type
	 * @param map the map
	 * @param comparator the value comparator
	 * @return the key of the maximum value
	 */
	public static <T> char maxKey(TCharObjectMap<T> map, final Comparator<? super T> comparator) {
		/** The aggregator. */
		class Proc implements TCharObjectProcedure<T> {
			/** The aggregation key. */
			char bestKey;
			/** The aggregation value. */
			T bestValue;
			@Override
			public boolean execute(char a, T b) {
				if (bestValue == null || comparator.compare(b, bestValue) > 0) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static int minKey(TIntByteMap map) {
		/** The aggregator. */
		class Proc implements TIntByteProcedure {
			/** The aggregation key. */
			int bestKey;
			/** The aggregation value. */
			byte bestValue;
			@Override
			public boolean execute(int a, byte b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static int maxKey(TIntByteMap map) {
		/** The aggregator. */
		class Proc implements TIntByteProcedure {
			/** The aggregation key. */
			int bestKey;
			/** The aggregation value. */
			byte bestValue;
			@Override
			public boolean execute(int a, byte b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static int minKey(TIntShortMap map) {
		/** The aggregator. */
		class Proc implements TIntShortProcedure {
			/** The aggregation key. */
			int bestKey;
			/** The aggregation value. */
			short bestValue;
			@Override
			public boolean execute(int a, short b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static int maxKey(TIntShortMap map) {
		/** The aggregator. */
		class Proc implements TIntShortProcedure {
			/** The aggregation key. */
			int bestKey;
			/** The aggregation value. */
			short bestValue;
			@Override
			public boolean execute(int a, short b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static int minKey(TIntCharMap map) {
		/** The aggregator. */
		class Proc implements TIntCharProcedure {
			/** The aggregation key. */
			int bestKey;
			/** The aggregation value. */
			char bestValue;
			@Override
			public boolean execute(int a, char b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static int maxKey(TIntCharMap map) {
		/** The aggregator. */
		class Proc implements TIntCharProcedure {
			/** The aggregation key. */
			int bestKey;
			/** The aggregation value. */
			char bestValue;
			@Override
			public boolean execute(int a, char b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static int minKey(TIntIntMap map) {
		/** The aggregator. */
		class Proc implements TIntIntProcedure {
			/** The aggregation key. */
			int bestKey;
			/** The aggregation value. */
			int bestValue;
			@Override
			public boolean execute(int a, int b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static int maxKey(TIntIntMap map) {
		/** The aggregator. */
		class Proc implements TIntIntProcedure {
			/** The aggregation key. */
			int bestKey;
			/** The aggregation value. */
			int bestValue;
			@Override
			public boolean execute(int a, int b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static int minKey(TIntLongMap map) {
		/** The aggregator. */
		class Proc implements TIntLongProcedure {
			/** The aggregation key. */
			int bestKey;
			/** The aggregation value. */
			long bestValue;
			@Override
			public boolean execute(int a, long b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static int maxKey(TIntLongMap map) {
		/** The aggregator. */
		class Proc implements TIntLongProcedure {
			/** The aggregation key. */
			int bestKey;
			/** The aggregation value. */
			long bestValue;
			@Override
			public boolean execute(int a, long b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static int minKey(TIntFloatMap map) {
		/** The aggregator. */
		class Proc implements TIntFloatProcedure {
			/** The aggregation key. */
			int bestKey;
			/** The aggregation value. */
			float bestValue;
			@Override
			public boolean execute(int a, float b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static int maxKey(TIntFloatMap map) {
		/** The aggregator. */
		class Proc implements TIntFloatProcedure {
			/** The aggregation key. */
			int bestKey;
			/** The aggregation value. */
			float bestValue;
			@Override
			public boolean execute(int a, float b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static int minKey(TIntDoubleMap map) {
		/** The aggregator. */
		class Proc implements TIntDoubleProcedure {
			/** The aggregation key. */
			int bestKey;
			/** The aggregation value. */
			double bestValue;
			@Override
			public boolean execute(int a, double b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static int maxKey(TIntDoubleMap map) {
		/** The aggregator. */
		class Proc implements TIntDoubleProcedure {
			/** The aggregation key. */
			int bestKey;
			/** The aggregation value. */
			double bestValue;
			@Override
			public boolean execute(int a, double b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param <T> the value type
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static <T extends Comparable<? super T>> int minKey(TIntObjectMap<T> map) {
		/** The aggregator. */
		class Proc implements TIntObjectProcedure<T> {
			/** The aggregation key. */
			int bestKey;
			/** The aggregation value. */
			T bestValue;
			@Override
			public boolean execute(int a, T b) {
				if (bestValue == null || b.compareTo(bestValue) < 0) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param <T> the value type
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static <T extends Comparable<? super T>> int maxKey(TIntObjectMap<T> map) {
		/** The aggregator. */
		class Proc implements TIntObjectProcedure<T> {
			/** The aggregation key. */
			int bestKey;
			/** The aggregation value. */
			T bestValue;
			@Override
			public boolean execute(int a, T b) {
				if (bestValue == null || b.compareTo(bestValue) > 0) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param <T> the value type
	 * @param map the map
	 * @param comparator the value comparator
	 * @return the key of the minimum value
	 */
	public static <T> int minKey(TIntObjectMap<T> map, final Comparator<? super T> comparator) {
		/** The aggregator. */
		class Proc implements TIntObjectProcedure<T> {
			/** The aggregation key. */
			int bestKey;
			/** The aggregation value. */
			T bestValue;
			@Override
			public boolean execute(int a, T b) {
				if (bestValue == null || comparator.compare(b, bestValue) < 0) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param <T> the value type
	 * @param map the map
	 * @param comparator the value comparator
	 * @return the key of the maximum value
	 */
	public static <T> int maxKey(TIntObjectMap<T> map, final Comparator<? super T> comparator) {
		/** The aggregator. */
		class Proc implements TIntObjectProcedure<T> {
			/** The aggregation key. */
			int bestKey;
			/** The aggregation value. */
			T bestValue;
			@Override
			public boolean execute(int a, T b) {
				if (bestValue == null || comparator.compare(b, bestValue) > 0) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static long minKey(TLongByteMap map) {
		/** The aggregator. */
		class Proc implements TLongByteProcedure {
			/** The aggregation key. */
			long bestKey;
			/** The aggregation value. */
			byte bestValue;
			@Override
			public boolean execute(long a, byte b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static long maxKey(TLongByteMap map) {
		/** The aggregator. */
		class Proc implements TLongByteProcedure {
			/** The aggregation key. */
			long bestKey;
			/** The aggregation value. */
			byte bestValue;
			@Override
			public boolean execute(long a, byte b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static long minKey(TLongShortMap map) {
		/** The aggregator. */
		class Proc implements TLongShortProcedure {
			/** The aggregation key. */
			long bestKey;
			/** The aggregation value. */
			short bestValue;
			@Override
			public boolean execute(long a, short b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static long maxKey(TLongShortMap map) {
		/** The aggregator. */
		class Proc implements TLongShortProcedure {
			/** The aggregation key. */
			long bestKey;
			/** The aggregation value. */
			short bestValue;
			@Override
			public boolean execute(long a, short b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static long minKey(TLongCharMap map) {
		/** The aggregator. */
		class Proc implements TLongCharProcedure {
			/** The aggregation key. */
			long bestKey;
			/** The aggregation value. */
			char bestValue;
			@Override
			public boolean execute(long a, char b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static long maxKey(TLongCharMap map) {
		/** The aggregator. */
		class Proc implements TLongCharProcedure {
			/** The aggregation key. */
			long bestKey;
			/** The aggregation value. */
			char bestValue;
			@Override
			public boolean execute(long a, char b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static long minKey(TLongIntMap map) {
		/** The aggregator. */
		class Proc implements TLongIntProcedure {
			/** The aggregation key. */
			long bestKey;
			/** The aggregation value. */
			int bestValue;
			@Override
			public boolean execute(long a, int b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static long maxKey(TLongIntMap map) {
		/** The aggregator. */
		class Proc implements TLongIntProcedure {
			/** The aggregation key. */
			long bestKey;
			/** The aggregation value. */
			int bestValue;
			@Override
			public boolean execute(long a, int b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static long minKey(TLongLongMap map) {
		/** The aggregator. */
		class Proc implements TLongLongProcedure {
			/** The aggregation key. */
			long bestKey;
			/** The aggregation value. */
			long bestValue;
			@Override
			public boolean execute(long a, long b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static long maxKey(TLongLongMap map) {
		/** The aggregator. */
		class Proc implements TLongLongProcedure {
			/** The aggregation key. */
			long bestKey;
			/** The aggregation value. */
			long bestValue;
			@Override
			public boolean execute(long a, long b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static long minKey(TLongFloatMap map) {
		/** The aggregator. */
		class Proc implements TLongFloatProcedure {
			/** The aggregation key. */
			long bestKey;
			/** The aggregation value. */
			float bestValue;
			@Override
			public boolean execute(long a, float b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static long maxKey(TLongFloatMap map) {
		/** The aggregator. */
		class Proc implements TLongFloatProcedure {
			/** The aggregation key. */
			long bestKey;
			/** The aggregation value. */
			float bestValue;
			@Override
			public boolean execute(long a, float b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static long minKey(TLongDoubleMap map) {
		/** The aggregator. */
		class Proc implements TLongDoubleProcedure {
			/** The aggregation key. */
			long bestKey;
			/** The aggregation value. */
			double bestValue;
			@Override
			public boolean execute(long a, double b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static long maxKey(TLongDoubleMap map) {
		/** The aggregator. */
		class Proc implements TLongDoubleProcedure {
			/** The aggregation key. */
			long bestKey;
			/** The aggregation value. */
			double bestValue;
			@Override
			public boolean execute(long a, double b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param <T> the value type
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static <T extends Comparable<? super T>> long minKey(TLongObjectMap<T> map) {
		/** The aggregator. */
		class Proc implements TLongObjectProcedure<T> {
			/** The aggregation key. */
			long bestKey;
			/** The aggregation value. */
			T bestValue;
			@Override
			public boolean execute(long a, T b) {
				if (bestValue == null || b.compareTo(bestValue) < 0) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param <T> the value type
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static <T extends Comparable<? super T>> long maxKey(TLongObjectMap<T> map) {
		/** The aggregator. */
		class Proc implements TLongObjectProcedure<T> {
			/** The aggregation key. */
			long bestKey;
			/** The aggregation value. */
			T bestValue;
			@Override
			public boolean execute(long a, T b) {
				if (bestValue == null || b.compareTo(bestValue) > 0) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param <T> the value type
	 * @param map the map
	 * @param comparator the value comparator
	 * @return the key of the minimum value
	 */
	public static <T> long minKey(TLongObjectMap<T> map, final Comparator<? super T> comparator) {
		/** The aggregator. */
		class Proc implements TLongObjectProcedure<T> {
			/** The aggregation key. */
			long bestKey;
			/** The aggregation value. */
			T bestValue;
			@Override
			public boolean execute(long a, T b) {
				if (bestValue == null || comparator.compare(b, bestValue) < 0) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param <T> the value type
	 * @param map the map
	 * @param comparator the value comparator
	 * @return the key of the maximum value
	 */
	public static <T> long maxKey(TLongObjectMap<T> map, final Comparator<? super T> comparator) {
		/** The aggregator. */
		class Proc implements TLongObjectProcedure<T> {
			/** The aggregation key. */
			long bestKey;
			/** The aggregation value. */
			T bestValue;
			@Override
			public boolean execute(long a, T b) {
				if (bestValue == null || comparator.compare(b, bestValue) > 0) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static float minKey(TFloatByteMap map) {
		/** The aggregator. */
		class Proc implements TFloatByteProcedure {
			/** The aggregation key. */
			float bestKey;
			/** The aggregation value. */
			byte bestValue;
			@Override
			public boolean execute(float a, byte b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static float maxKey(TFloatByteMap map) {
		/** The aggregator. */
		class Proc implements TFloatByteProcedure {
			/** The aggregation key. */
			float bestKey;
			/** The aggregation value. */
			byte bestValue;
			@Override
			public boolean execute(float a, byte b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static float minKey(TFloatShortMap map) {
		/** The aggregator. */
		class Proc implements TFloatShortProcedure {
			/** The aggregation key. */
			float bestKey;
			/** The aggregation value. */
			short bestValue;
			@Override
			public boolean execute(float a, short b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static float maxKey(TFloatShortMap map) {
		/** The aggregator. */
		class Proc implements TFloatShortProcedure {
			/** The aggregation key. */
			float bestKey;
			/** The aggregation value. */
			short bestValue;
			@Override
			public boolean execute(float a, short b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static float minKey(TFloatCharMap map) {
		/** The aggregator. */
		class Proc implements TFloatCharProcedure {
			/** The aggregation key. */
			float bestKey;
			/** The aggregation value. */
			char bestValue;
			@Override
			public boolean execute(float a, char b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static float maxKey(TFloatCharMap map) {
		/** The aggregator. */
		class Proc implements TFloatCharProcedure {
			/** The aggregation key. */
			float bestKey;
			/** The aggregation value. */
			char bestValue;
			@Override
			public boolean execute(float a, char b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static float minKey(TFloatIntMap map) {
		/** The aggregator. */
		class Proc implements TFloatIntProcedure {
			/** The aggregation key. */
			float bestKey;
			/** The aggregation value. */
			int bestValue;
			@Override
			public boolean execute(float a, int b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static float maxKey(TFloatIntMap map) {
		/** The aggregator. */
		class Proc implements TFloatIntProcedure {
			/** The aggregation key. */
			float bestKey;
			/** The aggregation value. */
			int bestValue;
			@Override
			public boolean execute(float a, int b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static float minKey(TFloatLongMap map) {
		/** The aggregator. */
		class Proc implements TFloatLongProcedure {
			/** The aggregation key. */
			float bestKey;
			/** The aggregation value. */
			long bestValue;
			@Override
			public boolean execute(float a, long b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static float maxKey(TFloatLongMap map) {
		/** The aggregator. */
		class Proc implements TFloatLongProcedure {
			/** The aggregation key. */
			float bestKey;
			/** The aggregation value. */
			long bestValue;
			@Override
			public boolean execute(float a, long b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static float minKey(TFloatFloatMap map) {
		/** The aggregator. */
		class Proc implements TFloatFloatProcedure {
			/** The aggregation key. */
			float bestKey;
			/** The aggregation value. */
			float bestValue;
			@Override
			public boolean execute(float a, float b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static float maxKey(TFloatFloatMap map) {
		/** The aggregator. */
		class Proc implements TFloatFloatProcedure {
			/** The aggregation key. */
			float bestKey;
			/** The aggregation value. */
			float bestValue;
			@Override
			public boolean execute(float a, float b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static float minKey(TFloatDoubleMap map) {
		/** The aggregator. */
		class Proc implements TFloatDoubleProcedure {
			/** The aggregation key. */
			float bestKey;
			/** The aggregation value. */
			double bestValue;
			@Override
			public boolean execute(float a, double b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static float maxKey(TFloatDoubleMap map) {
		/** The aggregator. */
		class Proc implements TFloatDoubleProcedure {
			/** The aggregation key. */
			float bestKey;
			/** The aggregation value. */
			double bestValue;
			@Override
			public boolean execute(float a, double b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param <T> the value type
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static <T extends Comparable<? super T>> float minKey(TFloatObjectMap<T> map) {
		/** The aggregator. */
		class Proc implements TFloatObjectProcedure<T> {
			/** The aggregation key. */
			float bestKey;
			/** The aggregation value. */
			T bestValue;
			@Override
			public boolean execute(float a, T b) {
				if (bestValue == null || b.compareTo(bestValue) < 0) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param <T> the value type
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static <T extends Comparable<? super T>> float maxKey(TFloatObjectMap<T> map) {
		/** The aggregator. */
		class Proc implements TFloatObjectProcedure<T> {
			/** The aggregation key. */
			float bestKey;
			/** The aggregation value. */
			T bestValue;
			@Override
			public boolean execute(float a, T b) {
				if (bestValue == null || b.compareTo(bestValue) > 0) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param <T> the value type
	 * @param map the map
	 * @param comparator the value comparator
	 * @return the key of the minimum value
	 */
	public static <T> float minKey(TFloatObjectMap<T> map, final Comparator<? super T> comparator) {
		/** The aggregator. */
		class Proc implements TFloatObjectProcedure<T> {
			/** The aggregation key. */
			float bestKey;
			/** The aggregation value. */
			T bestValue;
			@Override
			public boolean execute(float a, T b) {
				if (bestValue == null || comparator.compare(b, bestValue) < 0) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param <T> the value type
	 * @param map the map
	 * @param comparator the value comparator
	 * @return the key of the maximum value
	 */
	public static <T> float maxKey(TFloatObjectMap<T> map, final Comparator<? super T> comparator) {
		/** The aggregator. */
		class Proc implements TFloatObjectProcedure<T> {
			/** The aggregation key. */
			float bestKey;
			/** The aggregation value. */
			T bestValue;
			@Override
			public boolean execute(float a, T b) {
				if (bestValue == null || comparator.compare(b, bestValue) > 0) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static double minKey(TDoubleByteMap map) {
		/** The aggregator. */
		class Proc implements TDoubleByteProcedure {
			/** The aggregation key. */
			double bestKey;
			/** The aggregation value. */
			byte bestValue;
			@Override
			public boolean execute(double a, byte b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static double maxKey(TDoubleByteMap map) {
		/** The aggregator. */
		class Proc implements TDoubleByteProcedure {
			/** The aggregation key. */
			double bestKey;
			/** The aggregation value. */
			byte bestValue;
			@Override
			public boolean execute(double a, byte b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static double minKey(TDoubleShortMap map) {
		/** The aggregator. */
		class Proc implements TDoubleShortProcedure {
			/** The aggregation key. */
			double bestKey;
			/** The aggregation value. */
			short bestValue;
			@Override
			public boolean execute(double a, short b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static double maxKey(TDoubleShortMap map) {
		/** The aggregator. */
		class Proc implements TDoubleShortProcedure {
			/** The aggregation key. */
			double bestKey;
			/** The aggregation value. */
			short bestValue;
			@Override
			public boolean execute(double a, short b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static double minKey(TDoubleCharMap map) {
		/** The aggregator. */
		class Proc implements TDoubleCharProcedure {
			/** The aggregation key. */
			double bestKey;
			/** The aggregation value. */
			char bestValue;
			@Override
			public boolean execute(double a, char b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static double maxKey(TDoubleCharMap map) {
		/** The aggregator. */
		class Proc implements TDoubleCharProcedure {
			/** The aggregation key. */
			double bestKey;
			/** The aggregation value. */
			char bestValue;
			@Override
			public boolean execute(double a, char b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static double minKey(TDoubleIntMap map) {
		/** The aggregator. */
		class Proc implements TDoubleIntProcedure {
			/** The aggregation key. */
			double bestKey;
			/** The aggregation value. */
			int bestValue;
			@Override
			public boolean execute(double a, int b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static double maxKey(TDoubleIntMap map) {
		/** The aggregator. */
		class Proc implements TDoubleIntProcedure {
			/** The aggregation key. */
			double bestKey;
			/** The aggregation value. */
			int bestValue;
			@Override
			public boolean execute(double a, int b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static double minKey(TDoubleLongMap map) {
		/** The aggregator. */
		class Proc implements TDoubleLongProcedure {
			/** The aggregation key. */
			double bestKey;
			/** The aggregation value. */
			long bestValue;
			@Override
			public boolean execute(double a, long b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static double maxKey(TDoubleLongMap map) {
		/** The aggregator. */
		class Proc implements TDoubleLongProcedure {
			/** The aggregation key. */
			double bestKey;
			/** The aggregation value. */
			long bestValue;
			@Override
			public boolean execute(double a, long b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static double minKey(TDoubleFloatMap map) {
		/** The aggregator. */
		class Proc implements TDoubleFloatProcedure {
			/** The aggregation key. */
			double bestKey;
			/** The aggregation value. */
			float bestValue;
			@Override
			public boolean execute(double a, float b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static double maxKey(TDoubleFloatMap map) {
		/** The aggregator. */
		class Proc implements TDoubleFloatProcedure {
			/** The aggregation key. */
			double bestKey;
			/** The aggregation value. */
			float bestValue;
			@Override
			public boolean execute(double a, float b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static double minKey(TDoubleDoubleMap map) {
		/** The aggregator. */
		class Proc implements TDoubleDoubleProcedure {
			/** The aggregation key. */
			double bestKey;
			/** The aggregation value. */
			double bestValue;
			@Override
			public boolean execute(double a, double b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static double maxKey(TDoubleDoubleMap map) {
		/** The aggregator. */
		class Proc implements TDoubleDoubleProcedure {
			/** The aggregation key. */
			double bestKey;
			/** The aggregation value. */
			double bestValue;
			@Override
			public boolean execute(double a, double b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param <T> the value type
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static <T extends Comparable<? super T>> double minKey(TDoubleObjectMap<T> map) {
		/** The aggregator. */
		class Proc implements TDoubleObjectProcedure<T> {
			/** The aggregation key. */
			double bestKey;
			/** The aggregation value. */
			T bestValue;
			@Override
			public boolean execute(double a, T b) {
				if (bestValue == null || b.compareTo(bestValue) < 0) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param <T> the value type
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static <T extends Comparable<? super T>> double maxKey(TDoubleObjectMap<T> map) {
		/** The aggregator. */
		class Proc implements TDoubleObjectProcedure<T> {
			/** The aggregation key. */
			double bestKey;
			/** The aggregation value. */
			T bestValue;
			@Override
			public boolean execute(double a, T b) {
				if (bestValue == null || b.compareTo(bestValue) > 0) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param <T> the value type
	 * @param map the map
	 * @param comparator the value comparator
	 * @return the key of the minimum value
	 */
	public static <T> double minKey(TDoubleObjectMap<T> map, final Comparator<? super T> comparator) {
		/** The aggregator. */
		class Proc implements TDoubleObjectProcedure<T> {
			/** The aggregation key. */
			double bestKey;
			/** The aggregation value. */
			T bestValue;
			@Override
			public boolean execute(double a, T b) {
				if (bestValue == null || comparator.compare(b, bestValue) < 0) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param <T> the value type
	 * @param map the map
	 * @param comparator the value comparator
	 * @return the key of the maximum value
	 */
	public static <T> double maxKey(TDoubleObjectMap<T> map, final Comparator<? super T> comparator) {
		/** The aggregator. */
		class Proc implements TDoubleObjectProcedure<T> {
			/** The aggregation key. */
			double bestKey;
			/** The aggregation value. */
			T bestValue;
			@Override
			public boolean execute(double a, T b) {
				if (bestValue == null || comparator.compare(b, bestValue) > 0) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param <T> the value type
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static <T> T minKey(TObjectByteMap<T> map) {
		/** The aggregator. */
		class Proc implements TObjectByteProcedure<T> {
			/** The aggregation key. */
			T bestKey;
			/** The aggregation value. */
			byte bestValue;
			@Override
			public boolean execute(T a, byte b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param <T> the value type
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static <T> T maxKey(TObjectByteMap<T> map) {
		/** The aggregator. */
		class Proc implements TObjectByteProcedure<T> {
			/** The aggregation key. */
			T bestKey;
			/** The aggregation value. */
			byte bestValue;
			@Override
			public boolean execute(T a, byte b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param <T> the value type
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static <T> T minKey(TObjectShortMap<T> map) {
		/** The aggregator. */
		class Proc implements TObjectShortProcedure<T> {
			/** The aggregation key. */
			T bestKey;
			/** The aggregation value. */
			short bestValue;
			@Override
			public boolean execute(T a, short b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param <T> the value type
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static <T> T maxKey(TObjectShortMap<T> map) {
		/** The aggregator. */
		class Proc implements TObjectShortProcedure<T> {
			/** The aggregation key. */
			T bestKey;
			/** The aggregation value. */
			short bestValue;
			@Override
			public boolean execute(T a, short b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param <T> the value type
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static <T> T minKey(TObjectCharMap<T> map) {
		/** The aggregator. */
		class Proc implements TObjectCharProcedure<T> {
			/** The aggregation key. */
			T bestKey;
			/** The aggregation value. */
			char bestValue;
			@Override
			public boolean execute(T a, char b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param <T> the value type
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static <T> T maxKey(TObjectCharMap<T> map) {
		/** The aggregator. */
		class Proc implements TObjectCharProcedure<T> {
			/** The aggregation key. */
			T bestKey;
			/** The aggregation value. */
			char bestValue;
			@Override
			public boolean execute(T a, char b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param <T> the value type
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static <T> T minKey(TObjectIntMap<T> map) {
		/** The aggregator. */
		class Proc implements TObjectIntProcedure<T> {
			/** The aggregation key. */
			T bestKey;
			/** The aggregation value. */
			int bestValue;
			@Override
			public boolean execute(T a, int b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param <T> the value type
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static <T> T maxKey(TObjectIntMap<T> map) {
		/** The aggregator. */
		class Proc implements TObjectIntProcedure<T> {
			/** The aggregation key. */
			T bestKey;
			/** The aggregation value. */
			int bestValue;
			@Override
			public boolean execute(T a, int b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param <T> the value type
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static <T> T minKey(TObjectLongMap<T> map) {
		/** The aggregator. */
		class Proc implements TObjectLongProcedure<T> {
			/** The aggregation key. */
			T bestKey;
			/** The aggregation value. */
			long bestValue;
			@Override
			public boolean execute(T a, long b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param <T> the value type
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static <T> T maxKey(TObjectLongMap<T> map) {
		/** The aggregator. */
		class Proc implements TObjectLongProcedure<T> {
			/** The aggregation key. */
			T bestKey;
			/** The aggregation value. */
			long bestValue;
			@Override
			public boolean execute(T a, long b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param <T> the value type
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static <T> T minKey(TObjectFloatMap<T> map) {
		/** The aggregator. */
		class Proc implements TObjectFloatProcedure<T> {
			/** The aggregation key. */
			T bestKey;
			/** The aggregation value. */
			float bestValue;
			@Override
			public boolean execute(T a, float b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param <T> the value type
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static <T> T maxKey(TObjectFloatMap<T> map) {
		/** The aggregator. */
		class Proc implements TObjectFloatProcedure<T> {
			/** The aggregation key. */
			T bestKey;
			/** The aggregation value. */
			float bestValue;
			@Override
			public boolean execute(T a, float b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the minimum value.
	 * @param <T> the value type
	 * @param map the map
	 * @return the key of the minimum value
	 */
	public static <T> T minKey(TObjectDoubleMap<T> map) {
		/** The aggregator. */
		class Proc implements TObjectDoubleProcedure<T> {
			/** The aggregation key. */
			T bestKey;
			/** The aggregation value. */
			double bestValue;
			@Override
			public boolean execute(T a, double b) {
				if (b < bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}
	/**
	 * Finds the first key associated with the maximum value.
	 * @param <T> the value type
	 * @param map the map
	 * @return the key of the maximum value
	 */
	public static <T> T maxKey(TObjectDoubleMap<T> map) {
		/** The aggregator. */
		class Proc implements TObjectDoubleProcedure<T> {
			/** The aggregation key. */
			T bestKey;
			/** The aggregation value. */
			double bestValue;
			@Override
			public boolean execute(T a, double b) {
				if (b > bestValue) {
					bestKey = a;
					bestValue = b;
				}
				return true;
			}
		}
		Proc proc = new Proc();
		map.forEachEntry(proc);
		return proc.bestKey;
	}

	// -----------------------------
	// min-max-sum END
	// -----------------------------
	
	// -----------------------------
	// intersection START
	// -----------------------------

	/**
	 * Creates a new intersection set from the two sets.
	 * @param set1 the first set
	 * @param set2 the second set
	 * @return the intersection set
	 */
	@NonNull
	public static TByteSet intersection(@NonNull TByteSet set1, @NonNull TByteSet set2) {
		return intersection(set1, set2, new TByteHashSet());
	}
	/**
	 * Creates a new intersection set from the two sets.
	 * @param set1 the first set
	 * @param set2 the second set
	 * @param out the output set
	 * @return out the output set
	 */
	@NonNull
	public static TByteSet intersection(@NonNull TByteSet set1, @NonNull TByteSet set2, @NonNull TByteSet out) {
		TByteIterator it = set1.iterator();
		while (it.hasNext()) {
			byte s = it.next();
			if (set2.contains(s)) {
				out.add(s);
			}
		}
		return out;
	}
	/**
	 * Creates a new set from elements in set 1 which are not in set 2.
	 * @param set1 the first set
	 * @param set2 the second set
	 * @return the exluded values
	 */
	@NonNull
	public static TByteSet exclude(@NonNull TByteSet set1, @NonNull TByteSet set2) {
		return exclude(set1, set2, new TByteHashSet());
	}
	/**
	 * Creates a new set from elements in set 1 which are not in set 2.
	 * @param set1 the first set
	 * @param set2 the second set
	 * @param out the output set
	 * @return out the output set
	 */
	@NonNull
	public static TByteSet exclude(@NonNull TByteSet set1, @NonNull TByteSet set2, @NonNull TByteSet out) {
		TByteIterator it = set1.iterator();
		while (it.hasNext()) {
			byte s = it.next();
			if (!set2.contains(s)) {
				out.add(s);
			}
		}
		return out;
	}
	/**
	 * Creates a new intersection set from the two sets.
	 * @param set1 the first set
	 * @param set2 the second set
	 * @return the intersection set
	 */
	@NonNull
	public static TShortSet intersection(@NonNull TShortSet set1, @NonNull TShortSet set2) {
		return intersection(set1, set2, new TShortHashSet());
	}
	/**
	 * Creates a new intersection set from the two sets.
	 * @param set1 the first set
	 * @param set2 the second set
	 * @param out the output set
	 * @return out the output set
	 */
	@NonNull
	public static TShortSet intersection(@NonNull TShortSet set1, @NonNull TShortSet set2, @NonNull TShortSet out) {
		TShortIterator it = set1.iterator();
		while (it.hasNext()) {
			short s = it.next();
			if (set2.contains(s)) {
				out.add(s);
			}
		}
		return out;
	}
	/**
	 * Creates a new set from elements in set 1 which are not in set 2.
	 * @param set1 the first set
	 * @param set2 the second set
	 * @return the exluded values
	 */
	@NonNull
	public static TShortSet exclude(@NonNull TShortSet set1, @NonNull TShortSet set2) {
		return exclude(set1, set2, new TShortHashSet());
	}
	/**
	 * Creates a new set from elements in set 1 which are not in set 2.
	 * @param set1 the first set
	 * @param set2 the second set
	 * @param out the output set
	 * @return out the output set
	 */
	@NonNull
	public static TShortSet exclude(@NonNull TShortSet set1, @NonNull TShortSet set2, @NonNull TShortSet out) {
		TShortIterator it = set1.iterator();
		while (it.hasNext()) {
			short s = it.next();
			if (!set2.contains(s)) {
				out.add(s);
			}
		}
		return out;
	}
	/**
	 * Creates a new intersection set from the two sets.
	 * @param set1 the first set
	 * @param set2 the second set
	 * @return the intersection set
	 */
	@NonNull
	public static TCharSet intersection(@NonNull TCharSet set1, @NonNull TCharSet set2) {
		return intersection(set1, set2, new TCharHashSet());
	}
	/**
	 * Creates a new intersection set from the two sets.
	 * @param set1 the first set
	 * @param set2 the second set
	 * @param out the output set
	 * @return out the output set
	 */
	@NonNull
	public static TCharSet intersection(@NonNull TCharSet set1, @NonNull TCharSet set2, @NonNull TCharSet out) {
		TCharIterator it = set1.iterator();
		while (it.hasNext()) {
			char s = it.next();
			if (set2.contains(s)) {
				out.add(s);
			}
		}
		return out;
	}
	/**
	 * Creates a new set from elements in set 1 which are not in set 2.
	 * @param set1 the first set
	 * @param set2 the second set
	 * @return the exluded values
	 */
	@NonNull
	public static TCharSet exclude(@NonNull TCharSet set1, @NonNull TCharSet set2) {
		return exclude(set1, set2, new TCharHashSet());
	}
	/**
	 * Creates a new set from elements in set 1 which are not in set 2.
	 * @param set1 the first set
	 * @param set2 the second set
	 * @param out the output set
	 * @return out the output set
	 */
	@NonNull
	public static TCharSet exclude(@NonNull TCharSet set1, @NonNull TCharSet set2, @NonNull TCharSet out) {
		TCharIterator it = set1.iterator();
		while (it.hasNext()) {
			char s = it.next();
			if (!set2.contains(s)) {
				out.add(s);
			}
		}
		return out;
	}
	/**
	 * Creates a new intersection set from the two sets.
	 * @param set1 the first set
	 * @param set2 the second set
	 * @return the intersection set
	 */
	@NonNull
	public static TIntSet intersection(@NonNull TIntSet set1, @NonNull TIntSet set2) {
		return intersection(set1, set2, new TIntHashSet());
	}
	/**
	 * Creates a new intersection set from the two sets.
	 * @param set1 the first set
	 * @param set2 the second set
	 * @param out the output set
	 * @return out the output set
	 */
	@NonNull
	public static TIntSet intersection(@NonNull TIntSet set1, @NonNull TIntSet set2, @NonNull TIntSet out) {
		TIntIterator it = set1.iterator();
		while (it.hasNext()) {
			int s = it.next();
			if (set2.contains(s)) {
				out.add(s);
			}
		}
		return out;
	}
	/**
	 * Creates a new set from elements in set 1 which are not in set 2.
	 * @param set1 the first set
	 * @param set2 the second set
	 * @return the exluded values
	 */
	@NonNull
	public static TIntSet exclude(@NonNull TIntSet set1, @NonNull TIntSet set2) {
		return exclude(set1, set2, new TIntHashSet());
	}
	/**
	 * Creates a new set from elements in set 1 which are not in set 2.
	 * @param set1 the first set
	 * @param set2 the second set
	 * @param out the output set
	 * @return out the output set
	 */
	@NonNull
	public static TIntSet exclude(@NonNull TIntSet set1, @NonNull TIntSet set2, @NonNull TIntSet out) {
		TIntIterator it = set1.iterator();
		while (it.hasNext()) {
			int s = it.next();
			if (!set2.contains(s)) {
				out.add(s);
			}
		}
		return out;
	}
	/**
	 * Creates a new intersection set from the two sets.
	 * @param set1 the first set
	 * @param set2 the second set
	 * @return the intersection set
	 */
	@NonNull
	public static TLongSet intersection(@NonNull TLongSet set1, @NonNull TLongSet set2) {
		return intersection(set1, set2, new TLongHashSet());
	}
	/**
	 * Creates a new intersection set from the two sets.
	 * @param set1 the first set
	 * @param set2 the second set
	 * @param out the output set
	 * @return out the output set
	 */
	@NonNull
	public static TLongSet intersection(@NonNull TLongSet set1, @NonNull TLongSet set2, @NonNull TLongSet out) {
		TLongIterator it = set1.iterator();
		while (it.hasNext()) {
			long s = it.next();
			if (set2.contains(s)) {
				out.add(s);
			}
		}
		return out;
	}
	/**
	 * Creates a new set from elements in set 1 which are not in set 2.
	 * @param set1 the first set
	 * @param set2 the second set
	 * @return the exluded values
	 */
	@NonNull
	public static TLongSet exclude(@NonNull TLongSet set1, @NonNull TLongSet set2) {
		return exclude(set1, set2, new TLongHashSet());
	}
	/**
	 * Creates a new set from elements in set 1 which are not in set 2.
	 * @param set1 the first set
	 * @param set2 the second set
	 * @param out the output set
	 * @return out the output set
	 */
	@NonNull
	public static TLongSet exclude(@NonNull TLongSet set1, @NonNull TLongSet set2, @NonNull TLongSet out) {
		TLongIterator it = set1.iterator();
		while (it.hasNext()) {
			long s = it.next();
			if (!set2.contains(s)) {
				out.add(s);
			}
		}
		return out;
	}
	/**
	 * Creates a new intersection set from the two sets.
	 * @param set1 the first set
	 * @param set2 the second set
	 * @return the intersection set
	 */
	@NonNull
	public static TFloatSet intersection(@NonNull TFloatSet set1, @NonNull TFloatSet set2) {
		return intersection(set1, set2, new TFloatHashSet());
	}
	/**
	 * Creates a new intersection set from the two sets.
	 * @param set1 the first set
	 * @param set2 the second set
	 * @param out the output set
	 * @return out the output set
	 */
	@NonNull
	public static TFloatSet intersection(@NonNull TFloatSet set1, @NonNull TFloatSet set2, @NonNull TFloatSet out) {
		TFloatIterator it = set1.iterator();
		while (it.hasNext()) {
			float s = it.next();
			if (set2.contains(s)) {
				out.add(s);
			}
		}
		return out;
	}
	/**
	 * Creates a new set from elements in set 1 which are not in set 2.
	 * @param set1 the first set
	 * @param set2 the second set
	 * @return the exluded values
	 */
	@NonNull
	public static TFloatSet exclude(@NonNull TFloatSet set1, @NonNull TFloatSet set2) {
		return exclude(set1, set2, new TFloatHashSet());
	}
	/**
	 * Creates a new set from elements in set 1 which are not in set 2.
	 * @param set1 the first set
	 * @param set2 the second set
	 * @param out the output set
	 * @return out the output set
	 */
	@NonNull
	public static TFloatSet exclude(@NonNull TFloatSet set1, @NonNull TFloatSet set2, @NonNull TFloatSet out) {
		TFloatIterator it = set1.iterator();
		while (it.hasNext()) {
			float s = it.next();
			if (!set2.contains(s)) {
				out.add(s);
			}
		}
		return out;
	}
	/**
	 * Creates a new intersection set from the two sets.
	 * @param set1 the first set
	 * @param set2 the second set
	 * @return the intersection set
	 */
	@NonNull
	public static TDoubleSet intersection(@NonNull TDoubleSet set1, @NonNull TDoubleSet set2) {
		return intersection(set1, set2, new TDoubleHashSet());
	}
	/**
	 * Creates a new intersection set from the two sets.
	 * @param set1 the first set
	 * @param set2 the second set
	 * @param out the output set
	 * @return out the output set
	 */
	@NonNull
	public static TDoubleSet intersection(@NonNull TDoubleSet set1, @NonNull TDoubleSet set2, @NonNull TDoubleSet out) {
		TDoubleIterator it = set1.iterator();
		while (it.hasNext()) {
			double s = it.next();
			if (set2.contains(s)) {
				out.add(s);
			}
		}
		return out;
	}
	/**
	 * Creates a new set from elements in set 1 which are not in set 2.
	 * @param set1 the first set
	 * @param set2 the second set
	 * @return the exluded values
	 */
	@NonNull
	public static TDoubleSet exclude(@NonNull TDoubleSet set1, @NonNull TDoubleSet set2) {
		return exclude(set1, set2, new TDoubleHashSet());
	}
	/**
	 * Creates a new set from elements in set 1 which are not in set 2.
	 * @param set1 the first set
	 * @param set2 the second set
	 * @param out the output set
	 * @return out the output set
	 */
	@NonNull
	public static TDoubleSet exclude(@NonNull TDoubleSet set1, @NonNull TDoubleSet set2, @NonNull TDoubleSet out) {
		TDoubleIterator it = set1.iterator();
		while (it.hasNext()) {
			double s = it.next();
			if (!set2.contains(s)) {
				out.add(s);
			}
		}
		return out;
	}
	
	// -----------------------------
	// intersection END
	// -----------------------------

	/**
	 * Generate isNullOrEmpty() calls.
	 */
	static void generateIsNullOrEmpty() {
		String[] collTypes = {
			"Byte", "Short", "Char", "Int",
			"Long", "Float", "Double"
		};
		for (String t : collTypes) {
			System.out.printf("\t/**%n");
			System.out.printf("\t * Checks if the given collection is null or empty.%n");
			System.out.printf("\t * @param coll the collection to check%n");
			System.out.printf("\t * @return true if the collection is null or empty%n");
			System.out.printf("\t */%n");
			System.out.printf("\tpublic static boolean isNullOrEmpty(T%sCollection coll) {%n", t);
			System.out.printf("\t\treturn coll == null || coll.isEmpty();%n");
			System.out.printf("\t}%n");
		}
		String[] mapTypes = {
			"Byte", "Short", "Char", "Int",
			"Long", "Float", "Double", "Object"
		};
		for (String t1 : mapTypes) {
			for (String t2 : mapTypes) {
				if (t1.equals(t2) && "Object".equals(t1)) {
					continue;
				}
				String tp = " ";
				String tp1 = "";
				if (t1.equals("Object") || (t2.equals("Object"))) {
					tp = " <T> ";
					tp1 = "<T>";
				}
				System.out.printf("\t/**%n");
				System.out.printf("\t * Checks if the given map is null or empty.%n");
				if (t1.equals("Object") || (t2.equals("Object"))) {
					System.out.printf("\t * @param <T> the object type%n");
				}
				System.out.printf("\t * @param map the collection to check%n");
				System.out.printf("\t * @return true if the collection is null or empty%n");
				System.out.printf("\t */%n");
				System.out.printf("\tpublic static%sboolean isNullOrEmpty(T%s%sMap%s map) {%n", tp, t1, t2, tp1);
				System.out.printf("\t\treturn map == null || map.isEmpty();%n");
				System.out.printf("\t}%n");
			}
		}
	}
	/** Generate singleton() methods. */
	static void generateSingleton() {
		String[] collTypes = {
				"Byte", "Short", "Char", "Int",
				"Long", "Float", "Double"
		};
		for (String t : collTypes) {
			System.out.printf("\t/**%n");
			System.out.printf("\t * Creates a new set with the single value in it.%n");
			System.out.printf("\t * @param value the single value%n");
			System.out.printf("\t * @return the new set with a single value%n");
			System.out.printf("\t */%n");
			System.out.printf("\tpublic static T%sSet singleton(%s value) {%n", t, t.toLowerCase());
			System.out.printf("\t\tT%sSet result = new T%sHashSet();%n", t, t);
			System.out.printf("\t\tresult.add(value);%n");
			System.out.printf("\t\treturn result;%n");
			System.out.printf("\t}%n");
		}
		String[] mapTypes = {
			"Byte", "Short", "Char", "Int",
			"Long", "Float", "Double", "Object"
		};
		for (String t1 : mapTypes) {
			for (String t2 : mapTypes) {
				if (t1.equals(t2) && "Object".equals(t1)) {
					continue;
				}
				String tp = " ";
				String tp2 = "";
				String t1p = t1;
				String t2p = t2;
				if (t1.equals("Object") || (t2.equals("Object"))) {
					tp = " <T> ";
					tp2 = "<T>";
				}
				if (!t1.equals("Object")) {
					t1p = t1p.toLowerCase();
				} else {
					t1p = "T";
				}
				if (!t2.equals("Object")) {
					t2p = t2p.toLowerCase();
				} else {
					t2p = "T";
				}
				System.out.printf("\t/**%n");
				System.out.printf("\t * Creates a new map with the single key-value pair in it.%n");
				if (t1.equals("Object") || (t2.equals("Object"))) {
					System.out.printf("\t * @param <T> the object type%n");
				}
				System.out.printf("\t * @param key the key%n");
				System.out.printf("\t * @param value the value%n");
				System.out.printf("\t * @return the new map with a single key-value pair%n");
				System.out.printf("\t */%n");
				System.out.printf("\tpublic static%sT%s%sMap%s singletonMap(%s key, %s value) {%n", tp, t1, t2, tp2, t1p, t2p);
				System.out.printf("\t\tT%s%sMap%s result = new T%s%sHashMap%s();%n", t1, t2, tp2, t1, t2, tp2);
				System.out.printf("\t\tresult.put(key, value);%n");
				System.out.printf("\t\treturn result;%n");
				System.out.printf("\t}%n");
			}
		}
	}
	/**
	 * Generate min, max and sum methods.
	 */
	static void generateMinMaxSum() {
		String[] collTypes = {
				"Byte", "Short", "Char", "Int",
				"Long", "Float", "Double"
		};
		String[] sumTypes = {
				"Long", "Long", "Long", "Long",
				"Long", "Float", "Double"
		};
		int i = 0;
		for (String t : collTypes) {
			System.out.printf("\t/**%n");
			System.out.printf("\t * Finds the minimum value within the non-empty collection.%n");
			System.out.printf("\t * @param coll the collection%n");
			System.out.printf("\t * @return the minimum value%n");
			System.out.printf("\t */%n");
			System.out.printf("\tpublic static %s min(T%sCollection coll) {%n", t.toLowerCase(), t);
			System.out.printf("\t\tT%sIterator it = coll.iterator();%n", t);
			System.out.printf("\t\t%s value = it.next();%n", t.toLowerCase());
			System.out.printf("\t\twhile (it.hasNext()) {%n");
			System.out.printf("\t\t\t%s item = it.next();%n", t.toLowerCase());
			System.out.printf("\t\t\tvalue = item < value ? item : value;%n");
			System.out.printf("\t\t}%n");
			System.out.printf("\t\treturn value;%n");
			System.out.printf("\t}%n");
			
			System.out.printf("\t/**%n");
			System.out.printf("\t * Finds the maximum value within the non-empty collection.%n");
			System.out.printf("\t * @param coll the collection%n");
			System.out.printf("\t * @return the maximum value%n");
			System.out.printf("\t */%n");
			System.out.printf("\tpublic static %s max(T%sCollection coll) {%n", t.toLowerCase(), t);
			System.out.printf("\t\tT%sIterator it = coll.iterator();%n", t);
			System.out.printf("\t\t%s value = it.next();%n", t.toLowerCase());
			System.out.printf("\t\twhile (it.hasNext()) {%n");
			System.out.printf("\t\t\t%s item = it.next();%n", t.toLowerCase());
			System.out.printf("\t\t\tvalue = item > value ? item : value;%n");
			System.out.printf("\t\t}%n");
			System.out.printf("\t\treturn value;%n");
			System.out.printf("\t}%n");

			if (!t.equals("Char")) {
				System.out.printf("\t/**%n");
				System.out.printf("\t * Computes the sum of values.%n");
				System.out.printf("\t * @param coll the collection%n");
				System.out.printf("\t * @return the sum%n");
				System.out.printf("\t */%n");
				System.out.printf("\tpublic static %s sum(T%sCollection coll) {%n", sumTypes[i].toLowerCase(), t);
				System.out.printf("\t\tT%sIterator it = coll.iterator();%n", t);
				System.out.printf("\t\t%s value = 0;%n", t.toLowerCase());
				System.out.printf("\t\twhile (it.hasNext()) {%n");
				System.out.printf("\t\t\tvalue += it.next();%n");
				System.out.printf("\t\t}%n");
				System.out.printf("\t\treturn value;%n");
				System.out.printf("\t}%n");
			}
			i++;
		}
		String[] mapTypes = {
			"Byte", "Short", "Char", "Int",
			"Long", "Float", "Double", "Object"
		};
		for (String t1 : mapTypes) {
			for (String t2 : mapTypes) {
				if (t1.equals(t2) && "Object".equals(t1)) {
					continue;
				}
				String tp = "";
				String tp1 = "";
				String t1p = t1.toLowerCase();
				String t2p = t2.toLowerCase();
				if (t1.equals("Object")) {
					tp = "<T> ";
					tp1 = "<T>";
					t1p = "T";
				}
				if (t2.equals("Object")) {
					tp = "<T extends Comparable<? super T>> ";
					tp1 = "<T>";
					t2p = "T";
				}
				System.out.printf("\t/**%n");
				System.out.printf("\t * Finds the first key associated with the minimum value.%n");
				if (t1.equals("Object") || t2.equals("Object")) {
					System.out.printf("\t * @param <T> the value type%n");
				}
				System.out.printf("\t * @param map the map%n");
				System.out.printf("\t * @return the key of the minimum value%n");
				System.out.printf("\t */%n");
				System.out.printf("\tpublic static %s%s minKey(T%s%sMap%s map) {%n", tp, t1p, t1, t2, tp1);
				System.out.printf("\t\t/** The aggregator. */%n");
				System.out.printf("\t\tclass Proc implements T%s%sProcedure%s {%n", t1, t2, tp1);
				System.out.printf("\t\t\t/** The aggregation key. */%n");
				System.out.printf("\t\t\t%s bestKey;%n", t1p);
				System.out.printf("\t\t\t/** The aggregation value. */%n");
				System.out.printf("\t\t\t%s bestValue;%n", t2p);
				System.out.printf("\t\t\t@Override%n");
				System.out.printf("\t\t\tpublic boolean execute(%s a, %s b) {%n", t1p, t2p);
				if (t2.equals("Object")) {
					System.out.printf("\t\t\t\tif (bestValue == null || b.compareTo(bestValue) < 0) {%n");
				} else {
					System.out.printf("\t\t\t\tif (b < bestValue) {%n");
				}
					
				System.out.printf("\t\t\t\t\tbestKey = a;%n");
				System.out.printf("\t\t\t\t\tbestValue = b;%n");
				System.out.printf("\t\t\t\t}%n");
				System.out.printf("\t\t\t\treturn true;%n");
				System.out.printf("\t\t\t}%n");
				System.out.printf("\t\t}%n");
				System.out.printf("\t\tProc proc = new Proc();%n");
				System.out.printf("\t\tmap.forEachEntry(proc);%n");
				System.out.printf("\t\treturn proc.bestKey;%n");
				System.out.printf("\t}%n");

				System.out.printf("\t/**%n");
				System.out.printf("\t * Finds the first key associated with the maximum value.%n");
				if (t1.equals("Object") || t2.equals("Object")) {
					System.out.printf("\t * @param <T> the value type%n");
				}
				System.out.printf("\t * @param map the map%n");
				System.out.printf("\t * @return the key of the maximum value%n");
				System.out.printf("\t */%n");
				System.out.printf("\tpublic static %s%s maxKey(T%s%sMap%s map) {%n", tp, t1p, t1, t2, tp1);
				System.out.printf("\t\t/** The aggregator. */%n");
				System.out.printf("\t\tclass Proc implements T%s%sProcedure%s {%n", t1, t2, tp1);
				System.out.printf("\t\t\t/** The aggregation key. */%n");
				System.out.printf("\t\t\t%s bestKey;%n", t1p);
				System.out.printf("\t\t\t/** The aggregation value. */%n");
				System.out.printf("\t\t\t%s bestValue;%n", t2p);
				System.out.printf("\t\t\t@Override%n");
				System.out.printf("\t\t\tpublic boolean execute(%s a, %s b) {%n", t1p, t2p);
				if (t2.equals("Object")) {
					System.out.printf("\t\t\t\tif (bestValue == null || b.compareTo(bestValue) > 0) {%n");
				} else {
					System.out.printf("\t\t\t\tif (b > bestValue) {%n");
				}
				System.out.printf("\t\t\t\t\tbestKey = a;%n");
				System.out.printf("\t\t\t\t\tbestValue = b;%n");
				System.out.printf("\t\t\t\t}%n");
				System.out.printf("\t\t\t\treturn true;%n");
				System.out.printf("\t\t\t}%n");
				System.out.printf("\t\t}%n");
				System.out.printf("\t\tProc proc = new Proc();%n");
				System.out.printf("\t\tmap.forEachEntry(proc);%n");
				System.out.printf("\t\treturn proc.bestKey;%n");
				System.out.printf("\t}%n");

				if (t2.equals("Object")) {
					tp = "<T> ";
					System.out.printf("\t/**%n");
					System.out.printf("\t * Finds the first key associated with the minimum value.%n");
					System.out.printf("\t * @param <T> the value type%n");
					System.out.printf("\t * @param map the map%n");
					System.out.printf("\t * @param comparator the value comparator%n");
					System.out.printf("\t * @return the key of the minimum value%n");
					System.out.printf("\t */%n");
					System.out.printf("\tpublic static %s%s minKey(T%s%sMap%s map, final Comparator<? super T> comparator) {%n", tp, t1p, t1, t2, tp1);
					System.out.printf("\t\t/** The aggregator. */%n");
					System.out.printf("\t\tclass Proc implements T%s%sProcedure%s {%n", t1, t2, tp1);
					System.out.printf("\t\t\t/** The aggregation key. */%n");
					System.out.printf("\t\t\t%s bestKey;%n", t1p);
					System.out.printf("\t\t\t/** The aggregation value. */%n");
					System.out.printf("\t\t\t%s bestValue;%n", t2p);
					System.out.printf("\t\t\t@Override%n");
					System.out.printf("\t\t\tpublic boolean execute(%s a, %s b) {%n", t1p, t2p);
					System.out.printf("\t\t\t\tif (bestValue == null || comparator.compare(b, bestValue) < 0) {%n");
					System.out.printf("\t\t\t\t\tbestKey = a;%n");
					System.out.printf("\t\t\t\t\tbestValue = b;%n");
					System.out.printf("\t\t\t\t}%n");
					System.out.printf("\t\t\t\treturn true;%n");
					System.out.printf("\t\t\t}%n");
					System.out.printf("\t\t}%n");
					System.out.printf("\t\tProc proc = new Proc();%n");
					System.out.printf("\t\tmap.forEachEntry(proc);%n");
					System.out.printf("\t\treturn proc.bestKey;%n");
					System.out.printf("\t}%n");

					System.out.printf("\t/**%n");
					System.out.printf("\t * Finds the first key associated with the maximum value.%n");
					System.out.printf("\t * @param <T> the value type%n");
					System.out.printf("\t * @param map the map%n");
					System.out.printf("\t * @param comparator the value comparator%n");
					System.out.printf("\t * @return the key of the maximum value%n");
					System.out.printf("\t */%n");
					System.out.printf("\tpublic static %s%s maxKey(T%s%sMap%s map, final Comparator<? super T> comparator) {%n", tp, t1p, t1, t2, tp1);
					System.out.printf("\t\t/** The aggregator. */%n");
					System.out.printf("\t\tclass Proc implements T%s%sProcedure%s {%n", t1, t2, tp1);
					System.out.printf("\t\t\t/** The aggregation key. */%n");
					System.out.printf("\t\t\t%s bestKey;%n", t1p);
					System.out.printf("\t\t\t/** The aggregation value. */%n");
					System.out.printf("\t\t\t%s bestValue;%n", t2p);
					System.out.printf("\t\t\t@Override%n");
					System.out.printf("\t\t\tpublic boolean execute(%s a, %s b) {%n", t1p, t2p);
					System.out.printf("\t\t\t\tif (bestValue == null || comparator.compare(b, bestValue) > 0) {%n");
					System.out.printf("\t\t\t\t\tbestKey = a;%n");
					System.out.printf("\t\t\t\t\tbestValue = b;%n");
					System.out.printf("\t\t\t\t}%n");
					System.out.printf("\t\t\t\treturn true;%n");
					System.out.printf("\t\t\t}%n");
					System.out.printf("\t\t}%n");
					System.out.printf("\t\tProc proc = new Proc();%n");
					System.out.printf("\t\tmap.forEachEntry(proc);%n");
					System.out.printf("\t\treturn proc.bestKey;%n");
					System.out.printf("\t}%n");
				}
			}
		}
	}
	/** Generate intersection methods. */
	public static void generateIntersection() {
		String[] collTypes = {
				"Byte", "Short", "Char", "Int",
				"Long", "Float", "Double"
		};
		for (String t : collTypes) {
			
			System.out.printf("\t/**%n");
			System.out.printf("\t * Creates a new intersection set from the two sets.%n");
			System.out.printf("\t * @param set1 the first set%n");
			System.out.printf("\t * @param set2 the second set%n");
			System.out.printf("\t * @return the intersection set%n");
			System.out.printf("\t */%n");
			System.out.printf("\t@NonNull%n");
			System.out.printf("\tpublic static T%sSet intersection(@NonNull T%sSet set1, @NonNull T%sSet set2) {%n", t, t, t);
			System.out.printf("\t\treturn intersection(set1, set2, new T%sHashSet());%n", t);
			System.out.printf("\t}%n");

			System.out.printf("\t/**%n");
			System.out.printf("\t * Creates a new intersection set from the two sets.%n");
			System.out.printf("\t * @param set1 the first set%n");
			System.out.printf("\t * @param set2 the second set%n");
			System.out.printf("\t * @param out the output set%n");
			System.out.printf("\t * @return out the output set%n");
			System.out.printf("\t */%n");
			System.out.printf("\t@NonNull%n");
			System.out.printf("\tpublic static T%sSet intersection(@NonNull T%sSet set1, @NonNull T%sSet set2, @NonNull T%sSet out) {%n", t, t, t, t);
			System.out.printf("\t\tT%sIterator it = set1.iterator();%n", t);
			System.out.printf("\t\twhile (it.hasNext()) {%n");
			System.out.printf("\t\t\t%s s = it.next();%n", t.toLowerCase());
			System.out.printf("\t\t\tif (set2.contains(s)) {%n");
			System.out.printf("\t\t\t\tout.add(s);%n");
			System.out.printf("\t\t\t}%n");
			System.out.printf("\t\t}%n");
			System.out.printf("\t\treturn out;%n");
			System.out.printf("\t}%n");

			System.out.printf("\t/**%n");
			System.out.printf("\t * Creates a new set from elements in set 1 which are not in set 2.%n");
			System.out.printf("\t * @param set1 the first set%n");
			System.out.printf("\t * @param set2 the second set%n");
			System.out.printf("\t * @return the exluded values%n");
			System.out.printf("\t */%n");
			System.out.printf("\t@NonNull%n");
			System.out.printf("\tpublic static T%sSet exclude(@NonNull T%sSet set1, @NonNull T%sSet set2) {%n", t, t, t);
			System.out.printf("\t\treturn exclude(set1, set2, new T%sHashSet());%n", t);
			System.out.printf("\t}%n");

			System.out.printf("\t/**%n");
			System.out.printf("\t * Creates a new set from elements in set 1 which are not in set 2.%n");
			System.out.printf("\t * @param set1 the first set%n");
			System.out.printf("\t * @param set2 the second set%n");
			System.out.printf("\t * @param out the output set%n");
			System.out.printf("\t * @return out the output set%n");
			System.out.printf("\t */%n");
			System.out.printf("\t@NonNull%n");
			System.out.printf("\tpublic static T%sSet exclude(@NonNull T%sSet set1, @NonNull T%sSet set2, @NonNull T%sSet out) {%n", t, t, t, t);
			System.out.printf("\t\tT%sIterator it = set1.iterator();%n", t);
			System.out.printf("\t\twhile (it.hasNext()) {%n");
			System.out.printf("\t\t\t%s s = it.next();%n", t.toLowerCase());
			System.out.printf("\t\t\tif (!set2.contains(s)) {%n");
			System.out.printf("\t\t\t\tout.add(s);%n");
			System.out.printf("\t\t\t}%n");
			System.out.printf("\t\t}%n");
			System.out.printf("\t\treturn out;%n");
			System.out.printf("\t}%n");

		}
	}
	/**
	 * Generators for trove.
	 * @param args no arguments
	 */
	public static void main(String[] args) {
//		generateIsNullOrEmpty();
//		generateSingleton();
//		generateMinMaxSum();
//		generateIntersection();
	}
}
