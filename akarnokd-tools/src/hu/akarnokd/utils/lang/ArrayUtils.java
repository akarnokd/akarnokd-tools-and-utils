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
import java.util.Comparator;

import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * Utility class for arrays.
 * @author akarnokd, 2013.05.28.
 */
public final class ArrayUtils {
	/** Utility class. */
	private ArrayUtils() { }
	/**
	 * Check if the array reference is null or empty.
	 * @param array the array to test
	 * @return true if it is null or empty
	 */
	public static boolean isNullOrEmpty(Object[] array) {
		return array == null || array.length == 0;
	}
	/**
	 * Check if the array reference is null or empty.
	 * @param array the array to test
	 * @return true if it is null or empty
	 */
	public static boolean isNullOrEmpty(boolean[] array) {
		return array == null || array.length == 0;
	}
	/**
	 * Check if the array reference is null or empty.
	 * @param array the array to test
	 * @return true if it is null or empty
	 */
	public static boolean isNullOrEmpty(byte[] array) {
		return array == null || array.length == 0;
	}
	/**
	 * Check if the array reference is null or empty.
	 * @param array the array to test
	 * @return true if it is null or empty
	 */
	public static boolean isNullOrEmpty(short[] array) {
		return array == null || array.length == 0;
	}
	/**
	 * Check if the array reference is null or empty.
	 * @param array the array to test
	 * @return true if it is null or empty
	 */
	public static boolean isNullOrEmpty(char[] array) {
		return array == null || array.length == 0;
	}
	/**
	 * Check if the array reference is null or empty.
	 * @param array the array to test
	 * @return true if it is null or empty
	 */
	public static boolean isNullOrEmpty(int[] array) {
		return array == null || array.length == 0;
	}
	/**
	 * Check if the array reference is null or empty.
	 * @param array the array to test
	 * @return true if it is null or empty
	 */
	public static boolean isNullOrEmpty(long[] array) {
		return array == null || array.length == 0;
	}
	/**
	 * Check if the array reference is null or empty.
	 * @param array the array to test
	 * @return true if it is null or empty
	 */
	public static boolean isNullOrEmpty(float[] array) {
		return array == null || array.length == 0;
	}
	/**
	 * Check if the array reference is null or empty.
	 * @param array the array to test
	 * @return true if it is null or empty
	 */
	public static boolean isNullOrEmpty(double[] array) {
		return array == null || array.length == 0;
	}
	/**
	 * Joins the elements of the array with the given separator.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.valueOf()</code>.
	 * @param array the array 
	 * @param separator the separator between elements
	 * @param out the output
	 * @throws IOException if the output throws
	 */
	public static void join(boolean[] array, CharSequence separator, @NonNull Appendable out) throws IOException {
		if (array != null && array.length > 0) {
			out.append(String.valueOf(array[0]));
			for (int i = 1; i < array.length; i++) {
				out.append(separator);
				out.append(String.valueOf(array[i]));
			}
		}
	}
	/**
	 * Joins the elements of the array with the given separator.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.valueOf()</code>.
	 * @param array the array 
	 * @param separator the separator between elements
	 * @param out the output
	 * @throws IOException if the output throws
	 */
	public static void join(byte[] array, CharSequence separator, @NonNull Appendable out) throws IOException {
		if (array != null && array.length > 0) {
			out.append(String.valueOf(array[0]));
			for (int i = 1; i < array.length; i++) {
				out.append(separator);
				out.append(String.valueOf(array[i]));
			}
		}
	}
	/**
	 * Joins the elements of the array with the given separator.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.valueOf()</code>.
	 * @param array the array 
	 * @param separator the separator between elements
	 * @param out the output
	 * @throws IOException if the output throws
	 */
	public static void join(short[] array, CharSequence separator, @NonNull Appendable out) throws IOException {
		if (array != null && array.length > 0) {
			out.append(String.valueOf(array[0]));
			for (int i = 1; i < array.length; i++) {
				out.append(separator);
				out.append(String.valueOf(array[i]));
			}
		}
	}
	/**
	 * Joins the elements of the array with the given separator.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.valueOf()</code>.
	 * @param array the array 
	 * @param separator the separator between elements
	 * @param out the output
	 * @throws IOException if the output throws
	 */
	public static void join(char[] array, CharSequence separator, @NonNull Appendable out) throws IOException {
		if (array != null && array.length > 0) {
			out.append(String.valueOf(array[0]));
			for (int i = 1; i < array.length; i++) {
				out.append(separator);
				out.append(String.valueOf(array[i]));
			}
		}
	}
	/**
	 * Joins the elements of the array with the given separator.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.valueOf()</code>.
	 * @param array the array 
	 * @param separator the separator between elements
	 * @param out the output
	 * @throws IOException if the output throws
	 */
	public static void join(int[] array, CharSequence separator, @NonNull Appendable out) throws IOException {
		if (array != null && array.length > 0) {
			out.append(String.valueOf(array[0]));
			for (int i = 1; i < array.length; i++) {
				out.append(separator);
				out.append(String.valueOf(array[i]));
			}
		}
	}
	/**
	 * Joins the elements of the array with the given separator.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.valueOf()</code>.
	 * @param array the array 
	 * @param separator the separator between elements
	 * @param out the output
	 * @throws IOException if the output throws
	 */
	public static void join(long[] array, CharSequence separator, @NonNull Appendable out) throws IOException {
		if (array != null && array.length > 0) {
			out.append(String.valueOf(array[0]));
			for (int i = 1; i < array.length; i++) {
				out.append(separator);
				out.append(String.valueOf(array[i]));
			}
		}
	}
	/**
	 * Joins the elements of the array with the given separator.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.valueOf()</code>.
	 * @param array the array 
	 * @param separator the separator between elements
	 * @param out the output
	 * @throws IOException if the output throws
	 */
	public static void join(float[] array, CharSequence separator, @NonNull Appendable out) throws IOException {
		if (array != null && array.length > 0) {
			out.append(String.valueOf(array[0]));
			for (int i = 1; i < array.length; i++) {
				out.append(separator);
				out.append(String.valueOf(array[i]));
			}
		}
	}
	/**
	 * Joins the elements of the array with the given separator.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.valueOf()</code>.
	 * @param array the array 
	 * @param separator the separator between elements
	 * @param out the output
	 * @throws IOException if the output throws
	 */
	public static void join(double[] array, CharSequence separator, @NonNull Appendable out) throws IOException {
		if (array != null && array.length > 0) {
			out.append(String.valueOf(array[0]));
			for (int i = 1; i < array.length; i++) {
				out.append(separator);
				out.append(String.valueOf(array[i]));
			}
		}
	}
	/**
	 * Joins the elements of the array with the given separator.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.valueOf()</code>.
	 * @param array the array 
	 * @param separator the separator between elements
	 * @param out the output
	 * @throws IOException if the output throws
	 */
	public static void join(Object[] array, CharSequence separator, @NonNull Appendable out) throws IOException {
		if (array != null && array.length > 0) {
			out.append(String.valueOf(array[0]));
			for (int i = 1; i < array.length; i++) {
				out.append(separator);
				out.append(String.valueOf(array[i]));
			}
		}
	}
	/**
	 * Joins the elements of the array with the given separator and using the formatter.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.format()</code>.
	 * @param array the array 
	 * @param separator the separator between elements
	 * @param format the value formatter
	 * @param out the output
	 * @throws IOException if the output throws
	 */
	public static void join(boolean[] array, CharSequence separator, String format, @NonNull Appendable out) throws IOException {
		if (array != null && array.length > 0) {
			out.append(String.format(format, array[0]));
			for (int i = 1; i < array.length; i++) {
				out.append(separator);
				out.append(String.format(format, array[i]));
			}
		}
	}
	/**
	 * Joins the elements of the array with the given separator and using the formatter.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.format()</code>.
	 * @param array the array 
	 * @param format the value formatter
	 * @param separator the separator between elements
	 * @param out the output
	 * @throws IOException if the output throws
	 */
	public static void join(byte[] array, CharSequence separator, String format, @NonNull Appendable out) throws IOException {
		if (array != null && array.length > 0) {
			out.append(String.format(format, array[0]));
			for (int i = 1; i < array.length; i++) {
				out.append(separator);
				out.append(String.format(format, array[i]));
			}
		}
	}
	/**
	 * Joins the elements of the array with the given separator and using the formatter.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.format()</code>.
	 * @param array the array 
	 * @param format the value formatter
	 * @param separator the separator between elements
	 * @param out the output
	 * @throws IOException if the output throws
	 */
	public static void join(short[] array, CharSequence separator, String format, @NonNull Appendable out) throws IOException {
		if (array != null && array.length > 0) {
			out.append(String.format(format, array[0]));
			for (int i = 1; i < array.length; i++) {
				out.append(separator);
				out.append(String.format(format, array[i]));
			}
		}
	}
	/**
	 * Joins the elements of the array with the given separator and using the formatter.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.format()</code>.
	 * @param array the array 
	 * @param format the value formatter
	 * @param separator the separator between elements
	 * @param out the output
	 * @throws IOException if the output throws
	 */
	public static void join(char[] array, CharSequence separator, String format, @NonNull Appendable out) throws IOException {
		if (array != null && array.length > 0) {
			out.append(String.format(format, array[0]));
			for (int i = 1; i < array.length; i++) {
				out.append(separator);
				out.append(String.format(format, array[i]));
			}
		}
	}
	/**
	 * Joins the elements of the array with the given separator and using the formatter.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.format()</code>.
	 * @param array the array 
	 * @param format the value formatter
	 * @param separator the separator between elements
	 * @param out the output
	 * @throws IOException if the output throws
	 */
	public static void join(int[] array, CharSequence separator, String format, @NonNull Appendable out) throws IOException {
		if (array != null && array.length > 0) {
			out.append(String.format(format, array[0]));
			for (int i = 1; i < array.length; i++) {
				out.append(separator);
				out.append(String.format(format, array[i]));
			}
		}
	}
	/**
	 * Joins the elements of the array with the given separator and using the formatter.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.format()</code>.
	 * @param array the array 
	 * @param format the value formatter
	 * @param separator the separator between elements
	 * @param out the output
	 * @throws IOException if the output throws
	 */
	public static void join(long[] array, CharSequence separator, String format, @NonNull Appendable out) throws IOException {
		if (array != null && array.length > 0) {
			out.append(String.format(format, array[0]));
			for (int i = 1; i < array.length; i++) {
				out.append(separator);
				out.append(String.format(format, array[i]));
			}
		}
	}
	/**
	 * Joins the elements of the array with the given separator and using the formatter.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.format()</code>.
	 * @param array the array 
	 * @param format the value formatter
	 * @param separator the separator between elements
	 * @param out the output
	 * @throws IOException if the output throws
	 */
	public static void join(float[] array, CharSequence separator, String format, @NonNull Appendable out) throws IOException {
		if (array != null && array.length > 0) {
			out.append(String.format(format, array[0]));
			for (int i = 1; i < array.length; i++) {
				out.append(separator);
				out.append(String.format(format, array[i]));
			}
		}
	}
	/**
	 * Joins the elements of the array with the given separator and using the formatter.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.format()</code>.
	 * @param array the array 
	 * @param format the value formatter
	 * @param separator the separator between elements
	 * @param out the output
	 * @throws IOException if the output throws
	 */
	public static void join(double[] array, CharSequence separator, String format, @NonNull Appendable out) throws IOException {
		if (array != null && array.length > 0) {
			out.append(String.format(format, array[0]));
			for (int i = 1; i < array.length; i++) {
				out.append(separator);
				out.append(String.format(format, array[i]));
			}
		}
	}
	/**
	 * Joins the elements of the array with the given separator and using the formatter.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.format()</code>.
	 * @param array the array 
	 * @param format the value formatter
	 * @param separator the separator between elements
	 * @param out the output
	 * @throws IOException if the output throws
	 */
	public static void join(Object[] array, CharSequence separator, String format, @NonNull Appendable out) throws IOException {
		if (array != null && array.length > 0) {
			out.append(String.format(format, array[0]));
			for (int i = 1; i < array.length; i++) {
				out.append(separator);
				out.append(String.format(format, array[i]));
			}
		}
	}
	/**
	 * Joins the elements of the array with the given separator.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.valueOf()</code>.
	 * @param array the array 
	 * @param separator the separator between elements
	 * @return the joint string
	 */
	public static String join(boolean[] array, CharSequence separator) {
		StringBuilder out = new StringBuilder();
		try {
			join(array, separator, out);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
		return out.toString();
	}
	/**
	 * Joins the elements of the array with the given separator.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.valueOf()</code>.
	 * @param array the array 
	 * @param separator the separator between elements
	 * @return the joint string
	 */
	public static String join(byte[] array, CharSequence separator) {
		StringBuilder out = new StringBuilder();
		try {
			join(array, separator, out);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
		return out.toString();
	}
	/**
	 * Joins the elements of the array with the given separator.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.valueOf()</code>.
	 * @param array the array 
	 * @param separator the separator between elements
	 * @return the joint string
	 */
	public static String join(short[] array, CharSequence separator) {
		StringBuilder out = new StringBuilder();
		try {
			join(array, separator, out);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
		return out.toString();
	}
	/**
	 * Joins the elements of the array with the given separator.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.valueOf()</code>.
	 * @param array the array 
	 * @param separator the separator between elements
	 * @return the joint string
	 */
	public static String join(char[] array, CharSequence separator) {
		StringBuilder out = new StringBuilder();
		try {
			join(array, separator, out);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
		return out.toString();
	}
	/**
	 * Joins the elements of the array with the given separator.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.valueOf()</code>.
	 * @param array the array 
	 * @param separator the separator between elements
	 * @return the joint string
	 */
	public static String join(int[] array, CharSequence separator) {
		StringBuilder out = new StringBuilder();
		try {
			join(array, separator, out);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
		return out.toString();
	}
	/**
	 * Joins the elements of the array with the given separator.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.valueOf()</code>.
	 * @param array the array 
	 * @param separator the separator between elements
	 * @return the joint string
	 */
	public static String join(long[] array, CharSequence separator) {
		StringBuilder out = new StringBuilder();
		try {
			join(array, separator, out);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
		return out.toString();
	}
	/**
	 * Joins the elements of the array with the given separator.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.valueOf()</code>.
	 * @param array the array 
	 * @param separator the separator between elements
	 * @return the joint string
	 */
	public static String join(float[] array, CharSequence separator) {
		StringBuilder out = new StringBuilder();
		try {
			join(array, separator, out);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
		return out.toString();
	}
	/**
	 * Joins the elements of the array with the given separator.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.valueOf()</code>.
	 * @param array the array 
	 * @param separator the separator between elements
	 * @return the joint string
	 */
	public static String join(double[] array, CharSequence separator) {
		StringBuilder out = new StringBuilder();
		try {
			join(array, separator, out);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
		return out.toString();
	}
	/**
	 * Joins the elements of the array with the given separator.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.valueOf()</code>.
	 * @param array the array 
	 * @param separator the separator between elements
	 * @return the joint string
	 */
	public static String join(Object[] array, CharSequence separator) {
		StringBuilder out = new StringBuilder();
		try {
			join(array, separator, out);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
		return out.toString();
	}
	/**
	 * Joins the elements of the array with the given separator.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.valueOf()</code>.
	 * @param array the array 
	 * @param separator the separator between elements
	 * @param format the element formatter
	 * @return the joint string
	 */
	public static String join(boolean[] array, CharSequence separator, String format) {
		StringBuilder out = new StringBuilder();
		try {
			join(array, separator, format, out);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
		return out.toString();
	}
	/**
	 * Joins the elements of the array with the given separator.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.valueOf()</code>.
	 * @param array the array 
	 * @param separator the separator between elements
	 * @param format the element formatter
	 * @return the joint string
	 */
	public static String join(byte[] array, CharSequence separator, String format) {
		StringBuilder out = new StringBuilder();
		try {
			join(array, separator, format, out);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
		return out.toString();
	}
	/**
	 * Joins the elements of the array with the given separator.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.valueOf()</code>.
	 * @param array the array 
	 * @param separator the separator between elements
	 * @param format the element formatter
	 * @return the joint string
	 */
	public static String join(short[] array, CharSequence separator, String format) {
		StringBuilder out = new StringBuilder();
		try {
			join(array, separator, format, out);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
		return out.toString();
	}
	/**
	 * Joins the elements of the array with the given separator.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.valueOf()</code>.
	 * @param array the array 
	 * @param separator the separator between elements
	 * @param format the element formatter
	 * @return the joint string
	 */
	public static String join(char[] array, CharSequence separator, String format) {
		StringBuilder out = new StringBuilder();
		try {
			join(array, separator, format, out);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
		return out.toString();
	}
	/**
	 * Joins the elements of the array with the given separator.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.valueOf()</code>.
	 * @param array the array 
	 * @param separator the separator between elements
	 * @param format the element formatter
	 * @return the joint string
	 */
	public static String join(int[] array, CharSequence separator, String format) {
		StringBuilder out = new StringBuilder();
		try {
			join(array, separator, format, out);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
		return out.toString();
	}
	/**
	 * Joins the elements of the array with the given separator.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.valueOf()</code>.
	 * @param array the array 
	 * @param separator the separator between elements
	 * @param format the element formatter
	 * @return the joint string
	 */
	public static String join(long[] array, CharSequence separator, String format) {
		StringBuilder out = new StringBuilder();
		try {
			join(array, separator, format, out);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
		return out.toString();
	}
	/**
	 * Joins the elements of the array with the given separator.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.valueOf()</code>.
	 * @param array the array 
	 * @param separator the separator between elements
	 * @param format the element formatter
	 * @return the joint string
	 */
	public static String join(float[] array, CharSequence separator, String format) {
		StringBuilder out = new StringBuilder();
		try {
			join(array, separator, format, out);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
		return out.toString();
	}
	/**
	 * Joins the elements of the array with the given separator.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.valueOf()</code>.
	 * @param array the array 
	 * @param separator the separator between elements
	 * @param format the element formatter
	 * @return the joint string
	 */
	public static String join(double[] array, CharSequence separator, String format) {
		StringBuilder out = new StringBuilder();
		try {
			join(array, separator, format, out);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
		return out.toString();
	}
	/**
	 * Joins the elements of the array with the given separator.
	 * If the array is null or empty, the out is not modified.
	 * The element conversion is done via <code>String.valueOf()</code>.
	 * @param array the array 
	 * @param separator the separator between elements
	 * @param format the element formatter
	 * @return the joint string
	 */
	public static String join(Object[] array, CharSequence separator, String format) {
		StringBuilder out = new StringBuilder();
		try {
			join(array, separator, format, out);
		} catch (IOException ex) {
			throw new IllegalStateException(ex);
		}
		return out.toString();
	}
	/**
	 * Finds the first index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param array the array to search
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findFirst(byte[] array, byte value) {
		return findFirst(array, 0, array.length, value);
	}
	/**
	 * Finds the first index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param array the array to search
	 * @param fromIndex the starting index, inclusive
	 * @param toIndex the end index, exclusive
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findFirst(byte[] array, int fromIndex, int toIndex, byte value) {
		int a = fromIndex;
		int b = toIndex;
		while (a <= b) {
			int mid = a + (b - a) / 2;
			byte midVal = array[mid];
			if (midVal < value) {
				a = mid + 1;
			} else
			if (midVal > value) {
				b = mid - 1;
			} else {
				if (mid > 0) {
					if (array[mid - 1] != value) {
						return mid;
					} else {
						b = mid - 1;
					}
				} else {
					return 0;
				}
			}
		}
		return -(a + 1);
	}
	/**
	 * Finds the last index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param array the array to search
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findLast(byte[] array, byte value) {
		return findLast(array, 0, array.length, value);
	}
	/**
	 * Finds the last index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param array the array to search
	 * @param fromIndex the starting index, inclusive
	 * @param toIndex the end index, exclusive
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findLast(byte[] array, int fromIndex, int toIndex, byte value) {
		int a = fromIndex;
		int b = toIndex;
		while (a <= b) {
			int mid = a + (b - a) / 2;
			byte midVal = array[mid];
			if (midVal < value) {
				a = mid + 1;
			} else
			if (midVal > value) {
				b = mid - 1;
			} else {
				if (mid < toIndex - 1) {
					if (array[mid + 1] != value) {
						return mid;
					} else {
						a = mid + 1;
					}
				} else {
					return 0;
				}
			}
		}
		return -(a + 1);
	}
	/**
	 * Finds the first index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param array the array to search
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findFirst(short[] array, short value) {
		return findFirst(array, 0, array.length, value);
	}
	/**
	 * Finds the first index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param array the array to search
	 * @param fromIndex the starting index, inclusive
	 * @param toIndex the end index, exclusive
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findFirst(short[] array, int fromIndex, int toIndex, short value) {
		int a = fromIndex;
		int b = toIndex;
		while (a <= b) {
			int mid = a + (b - a) / 2;
			short midVal = array[mid];
			if (midVal < value) {
				a = mid + 1;
			} else
			if (midVal > value) {
				b = mid - 1;
			} else {
				if (mid > 0) {
					if (array[mid - 1] != value) {
						return mid;
					} else {
						b = mid - 1;
					}
				} else {
					return 0;
				}
			}
		}
		return -(a + 1);
	}
	/**
	 * Finds the last index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param array the array to search
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findLast(short[] array, short value) {
		return findLast(array, 0, array.length, value);
	}
	/**
	 * Finds the last index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param array the array to search
	 * @param fromIndex the starting index, inclusive
	 * @param toIndex the end index, exclusive
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findLast(short[] array, int fromIndex, int toIndex, short value) {
		int a = fromIndex;
		int b = toIndex;
		while (a <= b) {
			int mid = a + (b - a) / 2;
			short midVal = array[mid];
			if (midVal < value) {
				a = mid + 1;
			} else
			if (midVal > value) {
				b = mid - 1;
			} else {
				if (mid < toIndex - 1) {
					if (array[mid + 1] != value) {
						return mid;
					} else {
						a = mid + 1;
					}
				} else {
					return 0;
				}
			}
		}
		return -(a + 1);
	}
	/**
	 * Finds the first index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param array the array to search
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findFirst(char[] array, char value) {
		return findFirst(array, 0, array.length, value);
	}
	/**
	 * Finds the first index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param array the array to search
	 * @param fromIndex the starting index, inclusive
	 * @param toIndex the end index, exclusive
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findFirst(char[] array, int fromIndex, int toIndex, char value) {
		int a = fromIndex;
		int b = toIndex;
		while (a <= b) {
			int mid = a + (b - a) / 2;
			char midVal = array[mid];
			if (midVal < value) {
				a = mid + 1;
			} else
			if (midVal > value) {
				b = mid - 1;
			} else {
				if (mid > 0) {
					if (array[mid - 1] != value) {
						return mid;
					} else {
						b = mid - 1;
					}
				} else {
					return 0;
				}
			}
		}
		return -(a + 1);
	}
	/**
	 * Finds the last index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param array the array to search
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findLast(char[] array, char value) {
		return findLast(array, 0, array.length, value);
	}
	/**
	 * Finds the last index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param array the array to search
	 * @param fromIndex the starting index, inclusive
	 * @param toIndex the end index, exclusive
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findLast(char[] array, int fromIndex, int toIndex, char value) {
		int a = fromIndex;
		int b = toIndex;
		while (a <= b) {
			int mid = a + (b - a) / 2;
			char midVal = array[mid];
			if (midVal < value) {
				a = mid + 1;
			} else
			if (midVal > value) {
				b = mid - 1;
			} else {
				if (mid < toIndex - 1) {
					if (array[mid + 1] != value) {
						return mid;
					} else {
						a = mid + 1;
					}
				} else {
					return 0;
				}
			}
		}
		return -(a + 1);
	}
	/**
	 * Finds the first index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param array the array to search
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findFirst(int[] array, int value) {
		return findFirst(array, 0, array.length, value);
	}
	/**
	 * Finds the first index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param array the array to search
	 * @param fromIndex the starting index, inclusive
	 * @param toIndex the end index, exclusive
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findFirst(int[] array, int fromIndex, int toIndex, int value) {
		int a = fromIndex;
		int b = toIndex;
		while (a <= b) {
			int mid = a + (b - a) / 2;
			int midVal = array[mid];
			if (midVal < value) {
				a = mid + 1;
			} else
			if (midVal > value) {
				b = mid - 1;
			} else {
				if (mid > 0) {
					if (array[mid - 1] != value) {
						return mid;
					} else {
						b = mid - 1;
					}
				} else {
					return 0;
				}
			}
		}
		return -(a + 1);
	}
	/**
	 * Finds the last index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param array the array to search
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findLast(int[] array, int value) {
		return findLast(array, 0, array.length, value);
	}
	/**
	 * Finds the last index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param array the array to search
	 * @param fromIndex the starting index, inclusive
	 * @param toIndex the end index, exclusive
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findLast(int[] array, int fromIndex, int toIndex, int value) {
		int a = fromIndex;
		int b = toIndex;
		while (a <= b) {
			int mid = a + (b - a) / 2;
			int midVal = array[mid];
			if (midVal < value) {
				a = mid + 1;
			} else
			if (midVal > value) {
				b = mid - 1;
			} else {
				if (mid < toIndex - 1) {
					if (array[mid + 1] != value) {
						return mid;
					} else {
						a = mid + 1;
					}
				} else {
					return 0;
				}
			}
		}
		return -(a + 1);
	}
	/**
	 * Finds the first index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param array the array to search
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findFirst(float[] array, float value) {
		return findFirst(array, 0, array.length, value);
	}
	/**
	 * Finds the first index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param array the array to search
	 * @param fromIndex the starting index, inclusive
	 * @param toIndex the end index, exclusive
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findFirst(float[] array, int fromIndex, int toIndex, float value) {
		int a = fromIndex;
		int b = toIndex;
		while (a <= b) {
			int mid = a + (b - a) / 2;
			float midVal = array[mid];
			if (midVal < value) {
				a = mid + 1;
			} else
			if (midVal > value) {
				b = mid - 1;
			} else {
				if (mid > 0) {
					if (array[mid - 1] != value) {
						return mid;
					} else {
						b = mid - 1;
					}
				} else {
					return 0;
				}
			}
		}
		return -(a + 1);
	}
	/**
	 * Finds the last index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param array the array to search
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findLast(float[] array, float value) {
		return findLast(array, 0, array.length, value);
	}
	/**
	 * Finds the last index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param array the array to search
	 * @param fromIndex the starting index, inclusive
	 * @param toIndex the end index, exclusive
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findLast(float[] array, int fromIndex, int toIndex, float value) {
		int a = fromIndex;
		int b = toIndex;
		while (a <= b) {
			int mid = a + (b - a) / 2;
			float midVal = array[mid];
			if (midVal < value) {
				a = mid + 1;
			} else
			if (midVal > value) {
				b = mid - 1;
			} else {
				if (mid < toIndex - 1) {
					if (array[mid + 1] != value) {
						return mid;
					} else {
						a = mid + 1;
					}
				} else {
					return 0;
				}
			}
		}
		return -(a + 1);
	}
	/**
	 * Finds the first index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param array the array to search
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findFirst(double[] array, double value) {
		return findFirst(array, 0, array.length, value);
	}
	/**
	 * Finds the first index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param array the array to search
	 * @param fromIndex the starting index, inclusive
	 * @param toIndex the end index, exclusive
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findFirst(double[] array, int fromIndex, int toIndex, double value) {
		int a = fromIndex;
		int b = toIndex;
		while (a <= b) {
			int mid = a + (b - a) / 2;
			double midVal = array[mid];
			if (midVal < value) {
				a = mid + 1;
			} else
			if (midVal > value) {
				b = mid - 1;
			} else {
				if (mid > 0) {
					if (array[mid - 1] != value) {
						return mid;
					} else {
						b = mid - 1;
					}
				} else {
					return 0;
				}
			}
		}
		return -(a + 1);
	}
	/**
	 * Finds the last index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param array the array to search
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findLast(double[] array, double value) {
		return findLast(array, 0, array.length, value);
	}
	/**
	 * Finds the last index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param array the array to search
	 * @param fromIndex the starting index, inclusive
	 * @param toIndex the end index, exclusive
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findLast(double[] array, int fromIndex, int toIndex, double value) {
		int a = fromIndex;
		int b = toIndex;
		while (a <= b) {
			int mid = a + (b - a) / 2;
			double midVal = array[mid];
			if (midVal < value) {
				a = mid + 1;
			} else
			if (midVal > value) {
				b = mid - 1;
			} else {
				if (mid < toIndex - 1) {
					if (array[mid + 1] != value) {
						return mid;
					} else {
						a = mid + 1;
					}
				} else {
					return 0;
				}
			}
		}
		return -(a + 1);
	}
	/**
	 * Finds the first index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param <T> the element type
	 * @param array the array to search
	 * @param value the value to search
	 * @param comparator the comparator
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static <T> int findFirst(T[] array, T value, Comparator<? super T> comparator) {
		return findFirst(array, 0, array.length, value, comparator);
	}
	/**
	 * Finds the first index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param <T> the element type
	 * @param array the array to search
	 * @param fromIndex the starting index, inclusive
	 * @param toIndex the end index, exclusive
	 * @param value the value to search
	 * @param comparator the comparator
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static <T> int findFirst(T[] array, int fromIndex, int toIndex, T value, Comparator<? super T> comparator) {
		int a = fromIndex;
		int b = toIndex;
		while (a <= b) {
			int mid = a + (b - a) / 2;
			T midVal = array[mid];
			int c = comparator.compare(midVal, value);
			if (c < 0) {
				a = mid + 1;
			} else
			if (c > 0) {
				b = mid - 1;
			} else {
				if (mid > 0) {
					if (comparator.compare(array[mid - 1], value) != 0) {
						return mid;
					} else {
						b = mid - 1;
					}
				} else {
					return 0;
				}
			}
		}
		return -(a + 1);
	}
	/**
	 * Finds the last index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param <T> the element type
	 * @param array the array to search
	 * @param value the value to search
	 * @param comparator the comparator
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static <T> int findLast(T[] array, T value, Comparator<? super T> comparator) {
		return findLast(array, 0, array.length, value, comparator);
	}
	/**
	 * Finds the last index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param <T> the element type
	 * @param array the array to search
	 * @param fromIndex the starting index, inclusive
	 * @param toIndex the end index, exclusive
	 * @param value the value to search
	 * @param comparator the comparator
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static <T> int findLast(T[] array, int fromIndex, int toIndex, T value, Comparator<? super T> comparator) {
		int a = fromIndex;
		int b = toIndex;
		while (a <= b) {
			int mid = a + (b - a) / 2;
			T midVal = array[mid];
			int c = comparator.compare(midVal, value);
			if (c < 0) {
				a = mid + 1;
			} else
			if (c > 0) {
				b = mid - 1;
			} else {
				if (mid < toIndex - 1) {
					if (comparator.compare(array[mid + 1], value) != 0) {
						return mid;
					} else {
						a = mid + 1;
					}
				} else {
					return 0;
				}
			}
		}
		return -(a + 1);
	}
	/**
	 * Finds the first index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param <T> the element type, self comparable
	 * @param array the array to search
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static <T extends Comparable<? super T>> int findFirst(T[] array, T value) {
		return findFirst(array, 0, array.length, value);
	}
	/**
	 * Finds the first index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param <T> the element type, self comparable
	 * @param array the array to search
	 * @param fromIndex the starting index, inclusive
	 * @param toIndex the end index, exclusive
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static <T extends Comparable<? super T>> int findFirst(T[] array, int fromIndex, int toIndex, T value) {
		int a = fromIndex;
		int b = toIndex;
		while (a <= b) {
			int mid = a + (b - a) / 2;
			T midVal = array[mid];
			int c = midVal.compareTo(value);
			if (c < 0) {
				a = mid + 1;
			} else
			if (c > 0) {
				b = mid - 1;
			} else {
				if (mid > 0) {
					if (array[mid - 1].compareTo(value) != 0) {
						return mid;
					} else {
						b = mid - 1;
					}
				} else {
					return 0;
				}
			}
		}
		return -(a + 1);
	}
	/**
	 * Finds the last index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param <T> the element type, self comparable
	 * @param array the array to search
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static <T extends Comparable<? super T>> int findLast(T[] array, T value) {
		return findLast(array, 0, array.length, value);
	}
	/**
	 * Finds the last index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param <T> the element type, self comparable
	 * @param array the array to search
	 * @param fromIndex the starting index, inclusive
	 * @param toIndex the end index, exclusive
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static <T extends Comparable<? super T>> int findLast(T[] array, int fromIndex, int toIndex, T value) {
		int a = fromIndex;
		int b = toIndex;
		while (a <= b) {
			int mid = a + (b - a) / 2;
			T midVal = array[mid];
			int c = midVal.compareTo(value);
			if (c < 0) {
				a = mid + 1;
			} else
			if (c > 0) {
				b = mid - 1;
			} else {
				if (mid < toIndex - 1) {
					if (array[mid + 1].compareTo(value) != 0) {
						return mid;
					} else {
						a = mid + 1;
					}
				} else {
					return 0;
				}
			}
		}
		return -(a + 1);
	}
}
