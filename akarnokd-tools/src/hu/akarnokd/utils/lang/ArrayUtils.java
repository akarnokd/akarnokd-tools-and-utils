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
import java.util.Objects;

import com.google.common.primitives.UnsignedInts;
import com.google.common.primitives.UnsignedLongs;

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
	public static void join(@NonNull boolean[] array, CharSequence separator, @NonNull Appendable out) throws IOException {
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
	public static void join(@NonNull byte[] array, CharSequence separator, @NonNull Appendable out) throws IOException {
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
	public static void join(@NonNull short[] array, CharSequence separator, @NonNull Appendable out) throws IOException {
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
	public static void join(@NonNull char[] array, CharSequence separator, @NonNull Appendable out) throws IOException {
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
	public static void join(@NonNull int[] array, CharSequence separator, @NonNull Appendable out) throws IOException {
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
	public static void join(@NonNull long[] array, CharSequence separator, @NonNull Appendable out) throws IOException {
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
	public static void join(@NonNull float[] array, CharSequence separator, @NonNull Appendable out) throws IOException {
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
	public static void join(@NonNull double[] array, CharSequence separator, @NonNull Appendable out) throws IOException {
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
	public static void join(@NonNull Object[] array, CharSequence separator, @NonNull Appendable out) throws IOException {
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
	public static void join(@NonNull boolean[] array, CharSequence separator, String format, @NonNull Appendable out) throws IOException {
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
	public static void join(@NonNull byte[] array, CharSequence separator, String format, @NonNull Appendable out) throws IOException {
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
	public static void join(@NonNull short[] array, CharSequence separator, String format, @NonNull Appendable out) throws IOException {
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
	public static void join(@NonNull char[] array, CharSequence separator, String format, @NonNull Appendable out) throws IOException {
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
	public static void join(@NonNull int[] array, CharSequence separator, String format, @NonNull Appendable out) throws IOException {
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
	public static void join(@NonNull long[] array, CharSequence separator, String format, @NonNull Appendable out) throws IOException {
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
	public static void join(@NonNull float[] array, CharSequence separator, String format, @NonNull Appendable out) throws IOException {
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
	public static void join(@NonNull double[] array, CharSequence separator, String format, @NonNull Appendable out) throws IOException {
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
	public static void join(@NonNull Object[] array, CharSequence separator, String format, @NonNull Appendable out) throws IOException {
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
	 * @param out the output
	 */
	public static void join(@NonNull boolean[] array, CharSequence separator, @NonNull StringBuilder out) {
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
	 */
	public static void join(@NonNull byte[] array, CharSequence separator, @NonNull StringBuilder out) {
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
	 */
	public static void join(@NonNull short[] array, CharSequence separator, @NonNull StringBuilder out) {
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
	 */
	public static void join(@NonNull char[] array, CharSequence separator, @NonNull StringBuilder out) {
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
	 */
	public static void join(@NonNull int[] array, CharSequence separator, @NonNull StringBuilder out) {
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
	 */
	public static void join(@NonNull long[] array, CharSequence separator, @NonNull StringBuilder out) {
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
	 */
	public static void join(@NonNull float[] array, CharSequence separator, @NonNull StringBuilder out) {
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
	 */
	public static void join(@NonNull double[] array, CharSequence separator, @NonNull StringBuilder out) {
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
	 */
	public static void join(@NonNull Object[] array, CharSequence separator, @NonNull StringBuilder out) {
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
	 */
	public static void join(@NonNull boolean[] array, CharSequence separator, String format, @NonNull StringBuilder out) {
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
	 */
	public static void join(@NonNull byte[] array, CharSequence separator, String format, @NonNull StringBuilder out) {
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
	 */
	public static void join(@NonNull short[] array, CharSequence separator, String format, @NonNull StringBuilder out) {
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
	 */
	public static void join(@NonNull char[] array, CharSequence separator, String format, @NonNull StringBuilder out) {
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
	 */
	public static void join(@NonNull int[] array, CharSequence separator, String format, @NonNull StringBuilder out) {
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
	 */
	public static void join(@NonNull long[] array, CharSequence separator, String format, @NonNull StringBuilder out) {
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
	 */
	public static void join(@NonNull float[] array, CharSequence separator, String format, @NonNull StringBuilder out) {
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
	 */
	public static void join(@NonNull double[] array, CharSequence separator, String format, @NonNull StringBuilder out) {
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
	 */
	public static void join(@NonNull Object[] array, CharSequence separator, String format, @NonNull StringBuilder out) {
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
	public static String join(@NonNull boolean[] array, CharSequence separator) {
		StringBuilder out = new StringBuilder();
		join(array, separator, out);
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
	public static String join(@NonNull byte[] array, CharSequence separator) {
		StringBuilder out = new StringBuilder();
		join(array, separator, out);
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
	public static String join(@NonNull short[] array, CharSequence separator) {
		StringBuilder out = new StringBuilder();
		join(array, separator, out);
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
	public static String join(@NonNull char[] array, CharSequence separator) {
		StringBuilder out = new StringBuilder();
		join(array, separator, out);
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
	public static String join(@NonNull int[] array, CharSequence separator) {
		StringBuilder out = new StringBuilder();
		join(array, separator, out);
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
	public static String join(@NonNull long[] array, CharSequence separator) {
		StringBuilder out = new StringBuilder();
		join(array, separator, out);
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
	public static String join(@NonNull float[] array, CharSequence separator) {
		StringBuilder out = new StringBuilder();
		join(array, separator, out);
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
	public static String join(@NonNull double[] array, CharSequence separator) {
		StringBuilder out = new StringBuilder();
		join(array, separator, out);
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
	public static String join(@NonNull Object[] array, CharSequence separator) {
		StringBuilder out = new StringBuilder();
		join(array, separator, out);
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
	public static String join(@NonNull boolean[] array, CharSequence separator, String format) {
		StringBuilder out = new StringBuilder();
		join(array, separator, format, out);
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
	public static String join(@NonNull byte[] array, CharSequence separator, String format) {
		StringBuilder out = new StringBuilder();
		join(array, separator, format, out);
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
	public static String join(@NonNull short[] array, CharSequence separator, String format) {
		StringBuilder out = new StringBuilder();
		join(array, separator, format, out);
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
	public static String join(@NonNull char[] array, CharSequence separator, String format) {
		StringBuilder out = new StringBuilder();
		join(array, separator, format, out);
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
	public static String join(@NonNull int[] array, CharSequence separator, String format) {
		StringBuilder out = new StringBuilder();
		join(array, separator, format, out);
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
	public static String join(@NonNull long[] array, CharSequence separator, String format) {
		StringBuilder out = new StringBuilder();
		join(array, separator, format, out);
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
	public static String join(@NonNull float[] array, CharSequence separator, String format) {
		StringBuilder out = new StringBuilder();
		join(array, separator, format, out);
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
	public static String join(@NonNull double[] array, CharSequence separator, String format) {
		StringBuilder out = new StringBuilder();
		join(array, separator, format, out);
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
	public static String join(@NonNull Object[] array, CharSequence separator, String format) {
		StringBuilder out = new StringBuilder();
		join(array, separator, format, out);
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
	public static int findFirst(@NonNull byte[] array, byte value) {
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
	public static int findFirst(@NonNull byte[] array, int fromIndex, int toIndex, byte value) {
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
	public static int findLast(@NonNull byte[] array, byte value) {
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
	public static int findLast(@NonNull byte[] array, int fromIndex, int toIndex, byte value) {
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
	public static int findFirst(@NonNull short[] array, short value) {
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
	public static int findFirst(@NonNull short[] array, int fromIndex, int toIndex, short value) {
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
	public static int findLast(@NonNull short[] array, short value) {
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
	public static int findLast(@NonNull short[] array, int fromIndex, int toIndex, short value) {
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
	public static int findFirst(@NonNull char[] array, char value) {
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
	public static int findFirst(@NonNull char[] array, int fromIndex, int toIndex, char value) {
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
	public static int findLast(@NonNull char[] array, char value) {
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
	public static int findLast(@NonNull char[] array, int fromIndex, int toIndex, char value) {
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
	public static int findFirst(@NonNull int[] array, int value) {
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
	public static int findFirst(@NonNull int[] array, int fromIndex, int toIndex, int value) {
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
	public static int findLast(@NonNull int[] array, int value) {
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
	public static int findLast(@NonNull int[] array, int fromIndex, int toIndex, int value) {
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
	public static int findFirst(@NonNull long[] array, long value) {
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
	public static int findFirst(@NonNull long[] array, int fromIndex, int toIndex, long value) {
		int a = fromIndex;
		int b = toIndex;
		while (a <= b) {
			int mid = a + (b - a) / 2;
			long midVal = array[mid];
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
	public static int findLast(@NonNull long[] array, long value) {
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
	public static int findLast(@NonNull long[] array, int fromIndex, int toIndex, long value) {
		int a = fromIndex;
		int b = toIndex;
		while (a <= b) {
			int mid = a + (b - a) / 2;
			long midVal = array[mid];
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
	public static int findFirst(@NonNull float[] array, float value) {
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
	public static int findFirst(@NonNull float[] array, int fromIndex, int toIndex, float value) {
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
	public static int findLast(@NonNull float[] array, float value) {
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
	public static int findLast(@NonNull float[] array, int fromIndex, int toIndex, float value) {
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
	public static int findFirst(@NonNull double[] array, double value) {
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
	public static int findFirst(@NonNull double[] array, int fromIndex, int toIndex, double value) {
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
	public static int findLast(@NonNull double[] array, double value) {
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
	public static int findLast(@NonNull double[] array, int fromIndex, int toIndex, double value) {
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
	public static <T> int findFirst(@NonNull T[] array, T value, Comparator<? super T> comparator) {
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
	public static <T> int findFirst(@NonNull T[] array, int fromIndex, int toIndex, T value, Comparator<? super T> comparator) {
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
	public static <T> int findLast(@NonNull T[] array, T value, Comparator<? super T> comparator) {
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
	public static <T> int findLast(@NonNull T[] array, int fromIndex, int toIndex, T value, Comparator<? super T> comparator) {
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
	public static <T extends Comparable<? super T>> int findFirst(@NonNull T[] array, T value) {
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
	public static <T extends Comparable<? super T>> int findFirst(@NonNull T[] array, int fromIndex, int toIndex, T value) {
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
	public static <T extends Comparable<? super T>> int findLast(@NonNull T[] array, T value) {
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
	public static <T extends Comparable<? super T>> int findLast(@NonNull T[] array, int fromIndex, int toIndex, T value) {
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
	/**
	 * Finds the first index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements and
	 * uses unsigned comparison.
	 * @param array the array to search
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findFirstUnsigned(@NonNull byte[] array, byte value) {
		return findFirstUnsigned(array, 0, array.length, value);
	}
	/**
	 * Finds the first index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements and
	 * uses unsigned comparison.
	 * @param array the array to search
	 * @param fromIndex the starting index, inclusive
	 * @param toIndex the end index, exclusive
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findFirstUnsigned(@NonNull byte[] array, int fromIndex, int toIndex, byte value) {
		int a = fromIndex;
		int b = toIndex;
		int vi = value & 0xFF;
		while (a <= b) {
			int mid = a + (b - a) / 2;
			byte midVal = array[mid];
			int c = (midVal & 0xFF) - vi;
			if (c < 0) {
				a = mid + 1;
			} else
			if (c > 0) {
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
	 * binarySearch() methods, but it works with duplicate elements and
	 * uses unsigned comparison.
	 * @param array the array to search
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findLastUnsigned(@NonNull byte[] array, byte value) {
		return findLastUnsigned(array, 0, array.length, value);
	}
	/**
	 * Finds the last index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements and
	 * uses unsigned comparison.
	 * @param array the array to search
	 * @param fromIndex the starting index, inclusive
	 * @param toIndex the end index, exclusive
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findLastUnsigned(@NonNull byte[] array, int fromIndex, int toIndex, byte value) {
		int a = fromIndex;
		int b = toIndex;
		int vi = value & 0xFF;
		while (a <= b) {
			int mid = a + (b - a) / 2;
			byte midVal = array[mid];
			int c = (midVal & 0xFF) - vi;
			if (c < 0) {
				a = mid + 1;
			} else
			if (c > 0) {
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
	 * binarySearch() methods, but it works with duplicate elements and
	 * uses unsigned comparison.
	 * @param array the array to search
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findFirstUnsigned(@NonNull short[] array, short value) {
		return findFirstUnsigned(array, 0, array.length, value);
	}
	/**
	 * Finds the first index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements and
	 * uses unsigned comparison.
	 * @param array the array to search
	 * @param fromIndex the starting index, inclusive
	 * @param toIndex the end index, exclusive
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findFirstUnsigned(@NonNull short[] array, int fromIndex, int toIndex, short value) {
		int a = fromIndex;
		int b = toIndex;
		int vi = value & 0xFFFF;
		while (a <= b) {
			int mid = a + (b - a) / 2;
			short midVal = array[mid];
			int c = (midVal & 0xFFFF) - vi;
			if (c < 0) {
				a = mid + 1;
			} else
			if (c > 0) {
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
	 * binarySearch() methods, but it works with duplicate elements and
	 * uses unsigned comparison.
	 * @param array the array to search
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findLastUnsigned(@NonNull short[] array, short value) {
		return findLastUnsigned(array, 0, array.length, value);
	}
	/**
	 * Finds the last index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements and
	 * uses unsigned comparison.
	 * @param array the array to search
	 * @param fromIndex the starting index, inclusive
	 * @param toIndex the end index, exclusive
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findLastUnsigned(@NonNull short[] array, int fromIndex, int toIndex, short value) {
		int a = fromIndex;
		int b = toIndex;
		int vi = (value & 0xFFFF);
		while (a <= b) {
			int mid = a + (b - a) / 2;
			short midVal = array[mid];
			int c = (midVal & 0xFFFF) - vi;
			if (c < 0) {
				a = mid + 1;
			} else
			if (c > 0) {
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
	 * binarySearch() methods, but it works with duplicate elements and
	 * uses unsigned comparison.
	 * @param array the array to search
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findFirstUnsigned(@NonNull int[] array, int value) {
		return findFirstUnsigned(array, 0, array.length, value);
	}
	/**
	 * Finds the first index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements and
	 * uses unsigned comparison.
	 * @param array the array to search
	 * @param fromIndex the starting index, inclusive
	 * @param toIndex the end index, exclusive
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findFirstUnsigned(@NonNull int[] array, int fromIndex, int toIndex, int value) {
		int a = fromIndex;
		int b = toIndex;
		while (a <= b) {
			int mid = a + (b - a) / 2;
			int midVal = array[mid];
			int c = UnsignedInts.compare(midVal, value);
			if (c < 0) {
				a = mid + 1;
			} else
			if (c > 0) {
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
	 * binarySearch() methods, but it works with duplicate elements and
	 * uses unsigned comparison.
	 * @param array the array to search
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findLastUnsigned(@NonNull int[] array, int value) {
		return findLastUnsigned(array, 0, array.length, value);
	}
	/**
	 * Finds the last index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements and
	 * uses unsigned comparison.
	 * @param array the array to search
	 * @param fromIndex the starting index, inclusive
	 * @param toIndex the end index, exclusive
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findLastUnsigned(@NonNull int[] array, int fromIndex, int toIndex, int value) {
		int a = fromIndex;
		int b = toIndex;
		while (a <= b) {
			int mid = a + (b - a) / 2;
			int midVal = array[mid];
			int c = UnsignedInts.compare(midVal, value);
			if (c < 0) {
				a = mid + 1;
			} else
			if (c > 0) {
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
	 * binarySearch() methods, but it works with duplicate elements and
	 * uses unsigned comparison.
	 * @param array the array to search
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findFirstUnsigned(@NonNull long[] array, long value) {
		return findFirstUnsigned(array, 0, array.length, value);
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
	public static int findFirstUnsigned(@NonNull long[] array, int fromIndex, int toIndex, long value) {
		int a = fromIndex;
		int b = toIndex;
		while (a <= b) {
			int mid = a + (b - a) / 2;
			long midVal = array[mid];
			int c = UnsignedLongs.compare(midVal, value);
			if (c < 0) {
				a = mid + 1;
			} else
			if (c > 0) {
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
	 * binarySearch() methods, but it works with duplicate elements and
	 * uses unsigned comparison.
	 * @param array the array to search
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findLastUnsigned(@NonNull long[] array, long value) {
		return findLastUnsigned(array, 0, array.length, value);
	}
	/**
	 * Finds the last index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements and
	 * uses unsigned comparison.
	 * @param array the array to search
	 * @param fromIndex the starting index, inclusive
	 * @param toIndex the end index, exclusive
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static int findLastUnsigned(@NonNull long[] array, int fromIndex, int toIndex, long value) {
		int a = fromIndex;
		int b = toIndex;
		while (a <= b) {
			int mid = a + (b - a) / 2;
			long midVal = array[mid];
			int c = UnsignedLongs.compare(midVal, value);
			if (c < 0) {
				a = mid + 1;
			} else
			if (c > 0) {
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
	 * Finds the first occurrence of the value in the array.
	 * @param array the array to search
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int indexOf(@NonNull boolean[] array, boolean value) {
		return indexOf(array, 0, value);
	}
	/**
	 * Finds the next occurrence of the value in the array
	 * starting from the supplied index.
	 * @param array the array to search
	 * @param start the start index
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int indexOf(@NonNull boolean[] array, int start, boolean value) {
		for (int i = start; i < array.length; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Finds the first occurrence of the value in the array.
	 * @param array the array to search
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int lastIndexOf(@NonNull boolean[] array, boolean value) {
		return lastIndexOf(array, array.length - 1, value);
	}
	/**
	 * Finds the next occurrence of the value in the array
	 * starting from the supplied index.
	 * @param array the array to search
	 * @param start the start index
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int lastIndexOf(@NonNull boolean[] array, int start, boolean value) {
		for (int i = Math.min(start, array.length - 1); i >= 0; i--) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Finds the first occurrence of the value in the array.
	 * @param array the array to search
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int indexOf(@NonNull byte[] array, byte value) {
		return indexOf(array, 0, value);
	}
	/**
	 * Finds the next occurrence of the value in the array
	 * starting from the supplied index.
	 * @param array the array to search
	 * @param start the start index
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int indexOf(@NonNull byte[] array, int start, byte value) {
		for (int i = start; i < array.length; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Finds the first occurrence of the value in the array.
	 * @param array the array to search
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int lastIndexOf(@NonNull byte[] array, byte value) {
		return lastIndexOf(array, array.length - 1, value);
	}
	/**
	 * Finds the next occurrence of the value in the array
	 * starting from the supplied index.
	 * @param array the array to search
	 * @param start the start index
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int lastIndexOf(@NonNull byte[] array, int start, byte value) {
		for (int i = Math.min(start, array.length - 1); i >= 0; i--) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Finds the first occurrence of the value in the array.
	 * @param array the array to search
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int indexOf(@NonNull char[] array, char value) {
		return indexOf(array, 0, value);
	}
	/**
	 * Finds the next occurrence of the value in the array
	 * starting from the supplied index.
	 * @param array the array to search
	 * @param start the start index
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int indexOf(@NonNull char[] array, int start, char value) {
		for (int i = start; i < array.length; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Finds the first occurrence of the value in the array.
	 * @param array the array to search
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int lastIndexOf(@NonNull char[] array, char value) {
		return lastIndexOf(array, array.length - 1, value);
	}
	/**
	 * Finds the next occurrence of the value in the array
	 * starting from the supplied index.
	 * @param array the array to search
	 * @param start the start index
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int lastIndexOf(@NonNull char[] array, int start, char value) {
		for (int i = Math.min(start, array.length - 1); i >= 0; i--) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Finds the first occurrence of the value in the array.
	 * @param array the array to search
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int indexOf(@NonNull short[] array, short value) {
		return indexOf(array, 0, value);
	}
	/**
	 * Finds the next occurrence of the value in the array
	 * starting from the supplied index.
	 * @param array the array to search
	 * @param start the start index
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int indexOf(@NonNull short[] array, int start, short value) {
		for (int i = start; i < array.length; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Finds the first occurrence of the value in the array.
	 * @param array the array to search
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int lastIndexOf(@NonNull short[] array, short value) {
		return lastIndexOf(array, array.length - 1, value);
	}
	/**
	 * Finds the next occurrence of the value in the array
	 * starting from the supplied index.
	 * @param array the array to search
	 * @param start the start index
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int lastIndexOf(@NonNull short[] array, int start, short value) {
		for (int i = Math.min(start, array.length - 1); i >= 0; i--) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Finds the first occurrence of the value in the array.
	 * @param array the array to search
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int indexOf(@NonNull int[] array, int value) {
		return indexOf(array, 0, value);
	}
	/**
	 * Finds the next occurrence of the value in the array
	 * starting from the supplied index.
	 * @param array the array to search
	 * @param start the start index
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int indexOf(@NonNull int[] array, int start, int value) {
		for (int i = start; i < array.length; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Finds the first occurrence of the value in the array.
	 * @param array the array to search
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int lastIndexOf(@NonNull int[] array, int value) {
		return lastIndexOf(array, array.length - 1, value);
	}
	/**
	 * Finds the next occurrence of the value in the array
	 * starting from the supplied index.
	 * @param array the array to search
	 * @param start the start index
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int lastIndexOf(@NonNull int[] array, int start, int value) {
		for (int i = Math.min(start, array.length - 1); i >= 0; i--) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Finds the first occurrence of the value in the array.
	 * @param array the array to search
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int indexOf(@NonNull long[] array, long value) {
		return indexOf(array, 0, value);
	}
	/**
	 * Finds the next occurrence of the value in the array
	 * starting from the supplied index.
	 * @param array the array to search
	 * @param start the start index
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int indexOf(@NonNull long[] array, int start, long value) {
		for (int i = start; i < array.length; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Finds the first occurrence of the value in the array.
	 * @param array the array to search
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int lastIndexOf(@NonNull long[] array, long value) {
		return lastIndexOf(array, array.length - 1, value);
	}
	/**
	 * Finds the next occurrence of the value in the array
	 * starting from the supplied index.
	 * @param array the array to search
	 * @param start the start index
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int lastIndexOf(@NonNull long[] array, int start, long value) {
		for (int i = Math.min(start, array.length - 1); i >= 0; i--) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Finds the first occurrence of the value in the array.
	 * @param array the array to search
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int indexOf(@NonNull float[] array, float value) {
		return indexOf(array, 0, value);
	}
	/**
	 * Finds the next occurrence of the value in the array
	 * starting from the supplied index.
	 * @param array the array to search
	 * @param start the start index
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int indexOf(@NonNull float[] array, int start, float value) {
		for (int i = start; i < array.length; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Finds the first occurrence of the value in the array.
	 * @param array the array to search
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int lastIndexOf(@NonNull float[] array, float value) {
		return lastIndexOf(array, array.length - 1, value);
	}
	/**
	 * Finds the next occurrence of the value in the array
	 * starting from the supplied index.
	 * @param array the array to search
	 * @param start the start index
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int lastIndexOf(@NonNull float[] array, int start, float value) {
		for (int i = Math.min(start, array.length - 1); i >= 0; i--) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Finds the first occurrence of the value in the array.
	 * @param array the array to search
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int indexOf(@NonNull double[] array, double value) {
		return indexOf(array, 0, value);
	}
	/**
	 * Finds the next occurrence of the value in the array
	 * starting from the supplied index.
	 * @param array the array to search
	 * @param start the start index
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int indexOf(@NonNull double[] array, int start, double value) {
		for (int i = start; i < array.length; i++) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Finds the first occurrence of the value in the array.
	 * @param array the array to search
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int lastIndexOf(@NonNull double[] array, double value) {
		return lastIndexOf(array, array.length - 1, value);
	}
	/**
	 * Finds the next occurrence of the value in the array
	 * starting from the supplied index.
	 * @param array the array to search
	 * @param start the start index
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int lastIndexOf(@NonNull double[] array, int start, double value) {
		for (int i = Math.min(start, array.length - 1); i >= 0; i--) {
			if (array[i] == value) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Finds the first occurrence of the value in the array.
	 * @param array the array to search
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int indexOf(@NonNull Object[] array, Object value) {
		return indexOf(array, 0, value);
	}
	/**
	 * Finds the next occurrence of the value in the array
	 * starting from the supplied index.
	 * @param array the array to search
	 * @param start the start index
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int indexOf(@NonNull Object[] array, int start, Object value) {
		for (int i = start; i < array.length; i++) {
			if (Objects.equals(array[i], value)) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Finds the first occurrence of the value in the array.
	 * @param array the array to search
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int lastIndexOf(@NonNull Object[] array, Object value) {
		return lastIndexOf(array, array.length - 1, value);
	}
	/**
	 * Finds the next occurrence of the value in the array
	 * starting from the supplied index.
	 * @param array the array to search
	 * @param start the start index
	 * @param value the value to search for
	 * @return the index where it was found or -1 if not found
	 */
	public static int lastIndexOf(@NonNull Object[] array, int start, Object value) {
		for (int i = Math.min(start, array.length - 1); i >= 0; i--) {
			if (Objects.equals(array[i], value)) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * Locate a subarray in the main array.
	 * @param array the main array
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int indexOf(@NonNull boolean[] array, @NonNull boolean[] subarray) {
		return indexOf(array, 0, subarray);
	}
	/**
	 * Locate a subarray in the main array, starting from an index.
	 * @param array the main array
	 * @param start the start index
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int indexOf(@NonNull boolean[] array, int start, @NonNull boolean[] subarray) {
		outer:
		for (int i = start; i <= array.length - subarray.length; i++) {
			if (array[i] == subarray[0]) {
				for (int j = 1; j < subarray.length; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
	}
	/**
	 * Returns the start index of the sub array, considering the sub ranges
	 * on both.
	 * @param array the main array
	 * @param startIndex the start index of the main array
	 * @param endIndex the end index of the main array, exclusive
	 * @param subarray the sub array
	 * @param subStartIndex the start of the sub array
	 * @param subEndIndex the end of the sub array, exclusive
	 * @return the absolute index or -1 if not found
	 */
	public static int indexOf(
			@NonNull boolean[] array, int startIndex, int endIndex, 
			@NonNull boolean[] subarray, int subStartIndex, int subEndIndex) {
		int end = Math.min(endIndex - 1, array.length - subarray.length);
		int send = Math.min(subEndIndex, subarray.length);
		outer:
		for (int i = startIndex; i <= end; i++) {
			if (array[i] == subarray[subStartIndex]) {
				for (int j = subStartIndex + 1; j < send; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
		
	}
	/**
	 * Locate a subarray in the main array.
	 * @param array the main array
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int lastIndexOf(@NonNull boolean[] array, @NonNull boolean[] subarray) {
		return lastIndexOf(array, array.length - subarray.length, subarray);
	}
	/**
	 * Locate a subarray in the main array, starting from an index.
	 * @param array the main array
	 * @param start the start index
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int lastIndexOf(@NonNull boolean[] array, int start, @NonNull boolean[] subarray) {
		outer:
		for (int i = Math.min(array.length - subarray.length, start); i >= 0; i--) {
			if (array[i] == subarray[0]) {
				for (int j = 1; j < subarray.length; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
	}
	/**
	 * Returns the start index of the sub array, considering the sub ranges
	 * on both.
	 * @param array the main array
	 * @param startIndex the start index of the main array
	 * @param endIndex the end index of the main array, exclusive
	 * @param subarray the sub array
	 * @param subStartIndex the start of the sub array
	 * @param subEndIndex the end of the sub array, exclusive
	 * @return the absolute index or -1 if not found
	 */
	public static int lastIndexOf(
			@NonNull boolean[] array, int startIndex, int endIndex, 
			@NonNull boolean[] subarray, int subStartIndex, int subEndIndex) {
		int end = Math.min(endIndex - 1, array.length - subarray.length);
		int send = Math.min(subEndIndex, subarray.length);
		outer:
		for (int i = end; i >= startIndex; i--) {
			if (array[i] == subarray[subStartIndex]) {
				for (int j = subStartIndex + 1; j < send; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
		
	}
	/**
	 * Locate a subarray in the main array.
	 * @param array the main array
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int indexOf(@NonNull byte[] array, @NonNull byte[] subarray) {
		return indexOf(array, 0, subarray);
	}
	/**
	 * Locate a subarray in the main array, starting from an index.
	 * @param array the main array
	 * @param start the start index
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int indexOf(@NonNull byte[] array, int start, @NonNull byte[] subarray) {
		outer:
		for (int i = start; i <= array.length - subarray.length; i++) {
			if (array[i] == subarray[0]) {
				for (int j = 1; j < subarray.length; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
	}
	/**
	 * Returns the start index of the sub array, considering the sub ranges
	 * on both.
	 * @param array the main array
	 * @param startIndex the start index of the main array
	 * @param endIndex the end index of the main array, exclusive
	 * @param subarray the sub array
	 * @param subStartIndex the start of the sub array
	 * @param subEndIndex the end of the sub array, exclusive
	 * @return the absolute index or -1 if not found
	 */
	public static int indexOf(
			@NonNull byte[] array, int startIndex, int endIndex, 
			@NonNull byte[] subarray, int subStartIndex, int subEndIndex) {
		int end = Math.min(endIndex - 1, array.length - subarray.length);
		int send = Math.min(subEndIndex, subarray.length);
		outer:
		for (int i = startIndex; i <= end; i++) {
			if (array[i] == subarray[subStartIndex]) {
				for (int j = subStartIndex + 1; j < send; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
		
	}
	/**
	 * Locate a subarray in the main array.
	 * @param array the main array
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int lastIndexOf(@NonNull byte[] array, @NonNull byte[] subarray) {
		return lastIndexOf(array, array.length - subarray.length, subarray);
	}
	/**
	 * Locate a subarray in the main array, starting from an index.
	 * @param array the main array
	 * @param start the start index
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int lastIndexOf(@NonNull byte[] array, int start, @NonNull byte[] subarray) {
		outer:
		for (int i = Math.min(array.length - subarray.length, start); i >= 0; i--) {
			if (array[i] == subarray[0]) {
				for (int j = 1; j < subarray.length; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
	}
	/**
	 * Returns the start index of the sub array, considering the sub ranges
	 * on both.
	 * @param array the main array
	 * @param startIndex the start index of the main array
	 * @param endIndex the end index of the main array, exclusive
	 * @param subarray the sub array
	 * @param subStartIndex the start of the sub array
	 * @param subEndIndex the end of the sub array, exclusive
	 * @return the absolute index or -1 if not found
	 */
	public static int lastIndexOf(
			@NonNull byte[] array, int startIndex, int endIndex, 
			@NonNull byte[] subarray, int subStartIndex, int subEndIndex) {
		int end = Math.min(endIndex - 1, array.length - subarray.length);
		int send = Math.min(subEndIndex, subarray.length);
		outer:
		for (int i = end; i >= startIndex; i--) {
			if (array[i] == subarray[subStartIndex]) {
				for (int j = subStartIndex + 1; j < send; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
		
	}
	/**
	 * Locate a subarray in the main array.
	 * @param array the main array
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int indexOf(@NonNull short[] array, @NonNull short[] subarray) {
		return indexOf(array, 0, subarray);
	}
	/**
	 * Locate a subarray in the main array, starting from an index.
	 * @param array the main array
	 * @param start the start index
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int indexOf(@NonNull short[] array, int start, @NonNull short[] subarray) {
		outer:
		for (int i = start; i <= array.length - subarray.length; i++) {
			if (array[i] == subarray[0]) {
				for (int j = 1; j < subarray.length; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
	}
	/**
	 * Returns the start index of the sub array, considering the sub ranges
	 * on both.
	 * @param array the main array
	 * @param startIndex the start index of the main array
	 * @param endIndex the end index of the main array, exclusive
	 * @param subarray the sub array
	 * @param subStartIndex the start of the sub array
	 * @param subEndIndex the end of the sub array, exclusive
	 * @return the absolute index or -1 if not found
	 */
	public static int indexOf(
			@NonNull short[] array, int startIndex, int endIndex, 
			@NonNull short[] subarray, int subStartIndex, int subEndIndex) {
		int end = Math.min(endIndex - 1, array.length - subarray.length);
		int send = Math.min(subEndIndex, subarray.length);
		outer:
		for (int i = startIndex; i <= end; i++) {
			if (array[i] == subarray[subStartIndex]) {
				for (int j = subStartIndex + 1; j < send; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
		
	}
	/**
	 * Locate a subarray in the main array.
	 * @param array the main array
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int lastIndexOf(@NonNull short[] array, @NonNull short[] subarray) {
		return lastIndexOf(array, array.length - subarray.length, subarray);
	}
	/**
	 * Locate a subarray in the main array, starting from an index.
	 * @param array the main array
	 * @param start the start index
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int lastIndexOf(@NonNull short[] array, int start, @NonNull short[] subarray) {
		outer:
		for (int i = Math.min(array.length - subarray.length, start); i >= 0; i--) {
			if (array[i] == subarray[0]) {
				for (int j = 1; j < subarray.length; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
	}
	/**
	 * Returns the start index of the sub array, considering the sub ranges
	 * on both.
	 * @param array the main array
	 * @param startIndex the start index of the main array
	 * @param endIndex the end index of the main array, exclusive
	 * @param subarray the sub array
	 * @param subStartIndex the start of the sub array
	 * @param subEndIndex the end of the sub array, exclusive
	 * @return the absolute index or -1 if not found
	 */
	public static int lastIndexOf(
			@NonNull short[] array, int startIndex, int endIndex, 
			@NonNull short[] subarray, int subStartIndex, int subEndIndex) {
		int end = Math.min(endIndex - 1, array.length - subarray.length);
		int send = Math.min(subEndIndex, subarray.length);
		outer:
		for (int i = end; i >= startIndex; i--) {
			if (array[i] == subarray[subStartIndex]) {
				for (int j = subStartIndex + 1; j < send; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
		
	}
	/**
	 * Locate a subarray in the main array.
	 * @param array the main array
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int indexOf(@NonNull char[] array, @NonNull char[] subarray) {
		return indexOf(array, 0, subarray);
	}
	/**
	 * Locate a subarray in the main array, starting from an index.
	 * @param array the main array
	 * @param start the start index
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int indexOf(@NonNull char[] array, int start, @NonNull char[] subarray) {
		outer:
		for (int i = start; i <= array.length - subarray.length; i++) {
			if (array[i] == subarray[0]) {
				for (int j = 1; j < subarray.length; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
	}
	/**
	 * Returns the start index of the sub array, considering the sub ranges
	 * on both.
	 * @param array the main array
	 * @param startIndex the start index of the main array
	 * @param endIndex the end index of the main array, exclusive
	 * @param subarray the sub array
	 * @param subStartIndex the start of the sub array
	 * @param subEndIndex the end of the sub array, exclusive
	 * @return the absolute index or -1 if not found
	 */
	public static int indexOf(
			@NonNull char[] array, int startIndex, int endIndex, 
			@NonNull char[] subarray, int subStartIndex, int subEndIndex) {
		int end = Math.min(endIndex - 1, array.length - subarray.length);
		int send = Math.min(subEndIndex, subarray.length);
		outer:
		for (int i = startIndex; i <= end; i++) {
			if (array[i] == subarray[subStartIndex]) {
				for (int j = subStartIndex + 1; j < send; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
		
	}
	/**
	 * Locate a subarray in the main array.
	 * @param array the main array
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int lastIndexOf(@NonNull char[] array, @NonNull char[] subarray) {
		return lastIndexOf(array, array.length - subarray.length, subarray);
	}
	/**
	 * Locate a subarray in the main array, starting from an index.
	 * @param array the main array
	 * @param start the start index
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int lastIndexOf(@NonNull char[] array, int start, @NonNull char[] subarray) {
		outer:
		for (int i = Math.min(array.length - subarray.length, start); i >= 0; i--) {
			if (array[i] == subarray[0]) {
				for (int j = 1; j < subarray.length; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
	}
	/**
	 * Returns the start index of the sub array, considering the sub ranges
	 * on both.
	 * @param array the main array
	 * @param startIndex the start index of the main array
	 * @param endIndex the end index of the main array, exclusive
	 * @param subarray the sub array
	 * @param subStartIndex the start of the sub array
	 * @param subEndIndex the end of the sub array, exclusive
	 * @return the absolute index or -1 if not found
	 */
	public static int lastIndexOf(
			@NonNull char[] array, int startIndex, int endIndex, 
			@NonNull char[] subarray, int subStartIndex, int subEndIndex) {
		int end = Math.min(endIndex - 1, array.length - subarray.length);
		int send = Math.min(subEndIndex, subarray.length);
		outer:
		for (int i = end; i >= startIndex; i--) {
			if (array[i] == subarray[subStartIndex]) {
				for (int j = subStartIndex + 1; j < send; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
		
	}
	/**
	 * Locate a subarray in the main array.
	 * @param array the main array
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int indexOf(@NonNull int[] array, @NonNull int[] subarray) {
		return indexOf(array, 0, subarray);
	}
	/**
	 * Locate a subarray in the main array, starting from an index.
	 * @param array the main array
	 * @param start the start index
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int indexOf(@NonNull int[] array, int start, @NonNull int[] subarray) {
		outer:
		for (int i = start; i <= array.length - subarray.length; i++) {
			if (array[i] == subarray[0]) {
				for (int j = 1; j < subarray.length; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
	}
	/**
	 * Returns the start index of the sub array, considering the sub ranges
	 * on both.
	 * @param array the main array
	 * @param startIndex the start index of the main array
	 * @param endIndex the end index of the main array, exclusive
	 * @param subarray the sub array
	 * @param subStartIndex the start of the sub array
	 * @param subEndIndex the end of the sub array, exclusive
	 * @return the absolute index or -1 if not found
	 */
	public static int indexOf(
			@NonNull int[] array, int startIndex, int endIndex, 
			@NonNull int[] subarray, int subStartIndex, int subEndIndex) {
		int end = Math.min(endIndex - 1, array.length - subarray.length);
		int send = Math.min(subEndIndex, subarray.length);
		outer:
		for (int i = startIndex; i <= end; i++) {
			if (array[i] == subarray[subStartIndex]) {
				for (int j = subStartIndex + 1; j < send; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
		
	}
	/**
	 * Locate a subarray in the main array.
	 * @param array the main array
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int lastIndexOf(@NonNull int[] array, @NonNull int[] subarray) {
		return lastIndexOf(array, array.length - subarray.length, subarray);
	}
	/**
	 * Locate a subarray in the main array, starting from an index.
	 * @param array the main array
	 * @param start the start index
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int lastIndexOf(@NonNull int[] array, int start, @NonNull int[] subarray) {
		outer:
		for (int i = Math.min(array.length - subarray.length, start); i >= 0; i--) {
			if (array[i] == subarray[0]) {
				for (int j = 1; j < subarray.length; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
	}
	/**
	 * Returns the start index of the sub array, considering the sub ranges
	 * on both.
	 * @param array the main array
	 * @param startIndex the start index of the main array
	 * @param endIndex the end index of the main array, exclusive
	 * @param subarray the sub array
	 * @param subStartIndex the start of the sub array
	 * @param subEndIndex the end of the sub array, exclusive
	 * @return the absolute index or -1 if not found
	 */
	public static int lastIndexOf(
			@NonNull int[] array, int startIndex, int endIndex, 
			@NonNull int[] subarray, int subStartIndex, int subEndIndex) {
		int end = Math.min(endIndex - 1, array.length - subarray.length);
		int send = Math.min(subEndIndex, subarray.length);
		outer:
		for (int i = end; i >= startIndex; i--) {
			if (array[i] == subarray[subStartIndex]) {
				for (int j = subStartIndex + 1; j < send; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
		
	}
	/**
	 * Locate a subarray in the main array.
	 * @param array the main array
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int indexOf(@NonNull long[] array, @NonNull long[] subarray) {
		return indexOf(array, 0, subarray);
	}
	/**
	 * Locate a subarray in the main array, starting from an index.
	 * @param array the main array
	 * @param start the start index
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int indexOf(@NonNull long[] array, int start, @NonNull long[] subarray) {
		outer:
		for (int i = start; i <= array.length - subarray.length; i++) {
			if (array[i] == subarray[0]) {
				for (int j = 1; j < subarray.length; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
	}
	/**
	 * Returns the start index of the sub array, considering the sub ranges
	 * on both.
	 * @param array the main array
	 * @param startIndex the start index of the main array
	 * @param endIndex the end index of the main array, exclusive
	 * @param subarray the sub array
	 * @param subStartIndex the start of the sub array
	 * @param subEndIndex the end of the sub array, exclusive
	 * @return the absolute index or -1 if not found
	 */
	public static int indexOf(
			@NonNull long[] array, int startIndex, int endIndex, 
			@NonNull long[] subarray, int subStartIndex, int subEndIndex) {
		int end = Math.min(endIndex - 1, array.length - subarray.length);
		int send = Math.min(subEndIndex, subarray.length);
		outer:
		for (int i = startIndex; i <= end; i++) {
			if (array[i] == subarray[subStartIndex]) {
				for (int j = subStartIndex + 1; j < send; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
		
	}
	/**
	 * Locate a subarray in the main array.
	 * @param array the main array
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int lastIndexOf(@NonNull long[] array, @NonNull long[] subarray) {
		return lastIndexOf(array, array.length - subarray.length, subarray);
	}
	/**
	 * Locate a subarray in the main array, starting from an index.
	 * @param array the main array
	 * @param start the start index
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int lastIndexOf(@NonNull long[] array, int start, @NonNull long[] subarray) {
		outer:
		for (int i = Math.min(array.length - subarray.length, start); i >= 0; i--) {
			if (array[i] == subarray[0]) {
				for (int j = 1; j < subarray.length; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
	}
	/**
	 * Returns the start index of the sub array, considering the sub ranges
	 * on both.
	 * @param array the main array
	 * @param startIndex the start index of the main array
	 * @param endIndex the end index of the main array, exclusive
	 * @param subarray the sub array
	 * @param subStartIndex the start of the sub array
	 * @param subEndIndex the end of the sub array, exclusive
	 * @return the absolute index or -1 if not found
	 */
	public static int lastIndexOf(
			@NonNull long[] array, int startIndex, int endIndex, 
			@NonNull long[] subarray, int subStartIndex, int subEndIndex) {
		int end = Math.min(endIndex - 1, array.length - subarray.length);
		int send = Math.min(subEndIndex, subarray.length);
		outer:
		for (int i = end; i >= startIndex; i--) {
			if (array[i] == subarray[subStartIndex]) {
				for (int j = subStartIndex + 1; j < send; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
		
	}
	/**
	 * Locate a subarray in the main array.
	 * @param array the main array
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int indexOf(@NonNull float[] array, @NonNull float[] subarray) {
		return indexOf(array, 0, subarray);
	}
	/**
	 * Locate a subarray in the main array, starting from an index.
	 * @param array the main array
	 * @param start the start index
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int indexOf(@NonNull float[] array, int start, @NonNull float[] subarray) {
		outer:
		for (int i = start; i <= array.length - subarray.length; i++) {
			if (array[i] == subarray[0]) {
				for (int j = 1; j < subarray.length; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
	}
	/**
	 * Returns the start index of the sub array, considering the sub ranges
	 * on both.
	 * @param array the main array
	 * @param startIndex the start index of the main array
	 * @param endIndex the end index of the main array, exclusive
	 * @param subarray the sub array
	 * @param subStartIndex the start of the sub array
	 * @param subEndIndex the end of the sub array, exclusive
	 * @return the absolute index or -1 if not found
	 */
	public static int indexOf(
			@NonNull float[] array, int startIndex, int endIndex, 
			@NonNull float[] subarray, int subStartIndex, int subEndIndex) {
		int end = Math.min(endIndex - 1, array.length - subarray.length);
		int send = Math.min(subEndIndex, subarray.length);
		outer:
		for (int i = startIndex; i <= end; i++) {
			if (array[i] == subarray[subStartIndex]) {
				for (int j = subStartIndex + 1; j < send; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
		
	}
	/**
	 * Locate a subarray in the main array.
	 * @param array the main array
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int lastIndexOf(@NonNull float[] array, @NonNull float[] subarray) {
		return lastIndexOf(array, array.length - subarray.length, subarray);
	}
	/**
	 * Locate a subarray in the main array, starting from an index.
	 * @param array the main array
	 * @param start the start index
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int lastIndexOf(@NonNull float[] array, int start, @NonNull float[] subarray) {
		outer:
		for (int i = Math.min(array.length - subarray.length, start); i >= 0; i--) {
			if (array[i] == subarray[0]) {
				for (int j = 1; j < subarray.length; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
	}
	/**
	 * Returns the start index of the sub array, considering the sub ranges
	 * on both.
	 * @param array the main array
	 * @param startIndex the start index of the main array
	 * @param endIndex the end index of the main array, exclusive
	 * @param subarray the sub array
	 * @param subStartIndex the start of the sub array
	 * @param subEndIndex the end of the sub array, exclusive
	 * @return the absolute index or -1 if not found
	 */
	public static int lastIndexOf(
			@NonNull float[] array, int startIndex, int endIndex, 
			@NonNull float[] subarray, int subStartIndex, int subEndIndex) {
		int end = Math.min(endIndex - 1, array.length - subarray.length);
		int send = Math.min(subEndIndex, subarray.length);
		outer:
		for (int i = end; i >= startIndex; i--) {
			if (array[i] == subarray[subStartIndex]) {
				for (int j = subStartIndex + 1; j < send; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
		
	}
	/**
	 * Locate a subarray in the main array.
	 * @param array the main array
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int indexOf(@NonNull double[] array, @NonNull double[] subarray) {
		return indexOf(array, 0, subarray);
	}
	/**
	 * Locate a subarray in the main array, starting from an index.
	 * @param array the main array
	 * @param start the start index
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int indexOf(@NonNull double[] array, int start, @NonNull double[] subarray) {
		outer:
		for (int i = start; i <= array.length - subarray.length; i++) {
			if (array[i] == subarray[0]) {
				for (int j = 1; j < subarray.length; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
	}
	/**
	 * Returns the start index of the sub array, considering the sub ranges
	 * on both.
	 * @param array the main array
	 * @param startIndex the start index of the main array
	 * @param endIndex the end index of the main array, exclusive
	 * @param subarray the sub array
	 * @param subStartIndex the start of the sub array
	 * @param subEndIndex the end of the sub array, exclusive
	 * @return the absolute index or -1 if not found
	 */
	public static int indexOf(
			@NonNull double[] array, int startIndex, int endIndex, 
			@NonNull double[] subarray, int subStartIndex, int subEndIndex) {
		int end = Math.min(endIndex - 1, array.length - subarray.length);
		int send = Math.min(subEndIndex, subarray.length);
		outer:
		for (int i = startIndex; i <= end; i++) {
			if (array[i] == subarray[subStartIndex]) {
				for (int j = subStartIndex + 1; j < send; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
		
	}
	/**
	 * Locate a subarray in the main array.
	 * @param array the main array
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int lastIndexOf(@NonNull double[] array, @NonNull double[] subarray) {
		return lastIndexOf(array, array.length - subarray.length, subarray);
	}
	/**
	 * Locate a subarray in the main array, starting from an index.
	 * @param array the main array
	 * @param start the start index
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int lastIndexOf(@NonNull double[] array, int start, @NonNull double[] subarray) {
		outer:
		for (int i = Math.min(array.length - subarray.length, start); i >= 0; i--) {
			if (array[i] == subarray[0]) {
				for (int j = 1; j < subarray.length; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
	}
	/**
	 * Returns the start index of the sub array, considering the sub ranges
	 * on both.
	 * @param array the main array
	 * @param startIndex the start index of the main array
	 * @param endIndex the end index of the main array, exclusive
	 * @param subarray the sub array
	 * @param subStartIndex the start of the sub array
	 * @param subEndIndex the end of the sub array, exclusive
	 * @return the absolute index or -1 if not found
	 */
	public static int lastIndexOf(
			@NonNull double[] array, int startIndex, int endIndex, 
			@NonNull double[] subarray, int subStartIndex, int subEndIndex) {
		int end = Math.min(endIndex - 1, array.length - subarray.length);
		int send = Math.min(subEndIndex, subarray.length);
		outer:
		for (int i = end; i >= startIndex; i--) {
			if (array[i] == subarray[subStartIndex]) {
				for (int j = subStartIndex + 1; j < send; j++) {
					if (array[i + j] != subarray[j]) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
		
	}
	/**
	 * Locate a subarray in the main array.
	 * @param array the main array
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int indexOf(@NonNull Object[] array, @NonNull Object[] subarray) {
		return indexOf(array, 0, subarray);
	}
	/**
	 * Locate a subarray in the main array, starting from an index.
	 * @param array the main array
	 * @param start the start index
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int indexOf(@NonNull Object[] array, int start, @NonNull Object[] subarray) {
		outer:
		for (int i = start; i <= array.length - subarray.length; i++) {
			if (array[i] == subarray[0]) {
				for (int j = 1; j < subarray.length; j++) {
					if (!Objects.equals(array[i + j], subarray[j])) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
	}
	/**
	 * Returns the start index of the sub array, considering the sub ranges
	 * on both.
	 * @param array the main array
	 * @param startIndex the start index of the main array
	 * @param endIndex the end index of the main array, exclusive
	 * @param subarray the sub array
	 * @param subStartIndex the start of the sub array
	 * @param subEndIndex the end of the sub array, exclusive
	 * @return the absolute index or -1 if not found
	 */
	public static int indexOf(
			@NonNull Object[] array, int startIndex, int endIndex, 
			@NonNull Object[] subarray, int subStartIndex, int subEndIndex) {
		int end = Math.min(endIndex - 1, array.length - subarray.length);
		int send = Math.min(subEndIndex, subarray.length);
		outer:
		for (int i = startIndex; i <= end; i++) {
			if (array[i] == subarray[subStartIndex]) {
				for (int j = subStartIndex + 1; j < send; j++) {
					if (!Objects.equals(array[i + j], subarray[j])) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
		
	}
	/**
	 * Locate a subarray in the main array.
	 * @param array the main array
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int lastIndexOf(@NonNull Object[] array, @NonNull Object[] subarray) {
		return lastIndexOf(array, array.length - subarray.length, subarray);
	}
	/**
	 * Locate a subarray in the main array, starting from an index.
	 * @param array the main array
	 * @param start the start index
	 * @param subarray the subarray to find
	 * @return the index or -1 if not present
	 */
	public static int lastIndexOf(@NonNull Object[] array, int start, @NonNull Object[] subarray) {
		outer:
		for (int i = Math.min(array.length - subarray.length, start); i >= 0; i--) {
			if (array[i] == subarray[0]) {
				for (int j = 1; j < subarray.length; j++) {
					if (!Objects.equals(array[i + j], subarray[j])) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
	}
	/**
	 * Returns the start index of the sub array, considering the sub ranges
	 * on both.
	 * @param array the main array
	 * @param startIndex the start index of the main array
	 * @param endIndex the end index of the main array, exclusive
	 * @param subarray the sub array
	 * @param subStartIndex the start of the sub array
	 * @param subEndIndex the end of the sub array, exclusive
	 * @return the absolute index or -1 if not found
	 */
	public static int lastIndexOf(
			@NonNull Object[] array, int startIndex, int endIndex, 
			@NonNull Object[] subarray, int subStartIndex, int subEndIndex) {
		int end = Math.min(endIndex - 1, array.length - subarray.length);
		int send = Math.min(subEndIndex, subarray.length);
		outer:
		for (int i = end; i >= startIndex; i--) {
			if (array[i] == subarray[subStartIndex]) {
				for (int j = subStartIndex + 1; j < send; j++) {
					if (!Objects.equals(array[i + j], subarray[j])) {
						continue outer;
					}
				}
				return i;
			}
		}
		return -1;
	}
	/**
	 * Counts the total number of elements in the two-dimensional array.
	 * The array might be irregular, but not null.
	 * @param array the source two dimensional array
	 * @return the number of total elements.
	 */
	public static int count(boolean[][] array) {
		int c = 0;
		for (boolean[] b : array) {
			c += b.length;
		}
		return c;
	}
	/**
	 * Takes a two-dimensional array and creates a one dimensional
	 * array by placing the second dimension arrays one after another.
	 * @param array the array to flatten
	 * @return the flattened array
	 */
	public static boolean[] flatten(boolean[][] array) {
		int n = count(array);
		boolean[] result = new boolean[n];
		int o = 0;
		for (boolean[] b : array) {
			System.arraycopy(b, 0, result, o, b.length);
			o += b.length;
		}
		return result;
	}
	/**
	 * Counts the total number of elements in the two-dimensional array.
	 * The array might be irregular, but not null.
	 * @param array the source two dimensional array
	 * @return the number of total elements.
	 */
	public static int count(byte[][] array) {
		int c = 0;
		for (byte[] b : array) {
			c += b.length;
		}
		return c;
	}
	/**
	 * Takes a two-dimensional array and creates a one dimensional
	 * array by placing the second dimension arrays one after another.
	 * @param array the array to flatten
	 * @return the flattened array
	 */
	public static byte[] flatten(byte[][] array) {
		int n = count(array);
		byte[] result = new byte[n];
		int o = 0;
		for (byte[] b : array) {
			System.arraycopy(b, 0, result, o, b.length);
			o += b.length;
		}
		return result;
	}
	/**
	 * Counts the total number of elements in the two-dimensional array.
	 * The array might be irregular, but not null.
	 * @param array the source two dimensional array
	 * @return the number of total elements.
	 */
	public static int count(short[][] array) {
		int c = 0;
		for (short[] b : array) {
			c += b.length;
		}
		return c;
	}
	/**
	 * Takes a two-dimensional array and creates a one dimensional
	 * array by placing the second dimension arrays one after another.
	 * @param array the array to flatten
	 * @return the flattened array
	 */
	public static short[] flatten(short[][] array) {
		int n = count(array);
		short[] result = new short[n];
		int o = 0;
		for (short[] b : array) {
			System.arraycopy(b, 0, result, o, b.length);
			o += b.length;
		}
		return result;
	}
	/**
	 * Counts the total number of elements in the two-dimensional array.
	 * The array might be irregular, but not null.
	 * @param array the source two dimensional array
	 * @return the number of total elements.
	 */
	public static int count(char[][] array) {
		int c = 0;
		for (char[] b : array) {
			c += b.length;
		}
		return c;
	}
	/**
	 * Takes a two-dimensional array and creates a one dimensional
	 * array by placing the second dimension arrays one after another.
	 * @param array the array to flatten
	 * @return the flattened array
	 */
	public static char[] flatten(char[][] array) {
		int n = count(array);
		char[] result = new char[n];
		int o = 0;
		for (char[] b : array) {
			System.arraycopy(b, 0, result, o, b.length);
			o += b.length;
		}
		return result;
	}
	/**
	 * Counts the total number of elements in the two-dimensional array.
	 * The array might be irregular, but not null.
	 * @param array the source two dimensional array
	 * @return the number of total elements.
	 */
	public static int count(int[][] array) {
		int c = 0;
		for (int[] b : array) {
			c += b.length;
		}
		return c;
	}
	/**
	 * Takes a two-dimensional array and creates a one dimensional
	 * array by placing the second dimension arrays one after another.
	 * @param array the array to flatten
	 * @return the flattened array
	 */
	public static int[] flatten(int[][] array) {
		int n = count(array);
		int[] result = new int[n];
		int o = 0;
		for (int[] b : array) {
			System.arraycopy(b, 0, result, o, b.length);
			o += b.length;
		}
		return result;
	}
	/**
	 * Counts the total number of elements in the two-dimensional array.
	 * The array might be irregular, but not null.
	 * @param array the source two dimensional array
	 * @return the number of total elements.
	 */
	public static int count(long[][] array) {
		int c = 0;
		for (long[] b : array) {
			c += b.length;
		}
		return c;
	}
	/**
	 * Takes a two-dimensional array and creates a one dimensional
	 * array by placing the second dimension arrays one after another.
	 * @param array the array to flatten
	 * @return the flattened array
	 */
	public static long[] flatten(long[][] array) {
		int n = count(array);
		long[] result = new long[n];
		int o = 0;
		for (long[] b : array) {
			System.arraycopy(b, 0, result, o, b.length);
			o += b.length;
		}
		return result;
	}
	/**
	 * Counts the total number of elements in the two-dimensional array.
	 * The array might be irregular, but not null.
	 * @param array the source two dimensional array
	 * @return the number of total elements.
	 */
	public static int count(float[][] array) {
		int c = 0;
		for (float[] b : array) {
			c += b.length;
		}
		return c;
	}
	/**
	 * Takes a two-dimensional array and creates a one dimensional
	 * array by placing the second dimension arrays one after another.
	 * @param array the array to flatten
	 * @return the flattened array
	 */
	public static float[] flatten(float[][] array) {
		int n = count(array);
		float[] result = new float[n];
		int o = 0;
		for (float[] b : array) {
			System.arraycopy(b, 0, result, o, b.length);
			o += b.length;
		}
		return result;
	}
	/**
	 * Counts the total number of elements in the two-dimensional array.
	 * The array might be irregular, but not null.
	 * @param array the source two dimensional array
	 * @return the number of total elements.
	 */
	public static int count(double[][] array) {
		int c = 0;
		for (double[] b : array) {
			c += b.length;
		}
		return c;
	}
	/**
	 * Takes a two-dimensional array and creates a one dimensional
	 * array by placing the second dimension arrays one after another.
	 * @param array the array to flatten
	 * @return the flattened array
	 */
	public static double[] flatten(double[][] array) {
		int n = count(array);
		double[] result = new double[n];
		int o = 0;
		for (double[] b : array) {
			System.arraycopy(b, 0, result, o, b.length);
			o += b.length;
		}
		return result;
	}
	/**
	 * Counts the total number of elements in the two-dimensional array.
	 * The array might be irregular, but not null.
	 * @param array the source two dimensional array
	 * @return the number of total elements.
	 */
	public static int count(Object[][] array) {
		int c = 0;
		for (Object[] b : array) {
			c += b.length;
		}
		return c;
	}
	/**
	 * Takes a two-dimensional array and creates a one dimensional
	 * array by placing the second dimension arrays one after another.
	 * @param array the array to flatten
	 * @return the flattened array
	 */
	public static Object[] flatten(Object[][] array) {
		int n = count(array);
		Object[] result = new Object[n];
		int o = 0;
		for (Object[] b : array) {
			System.arraycopy(b, 0, result, o, b.length);
			o += b.length;
		}
		return result;
	}
	/**
	 * Reverses an array in-place.
	 * @param array the array to reverse
	 * @return the array
	 */
	public static boolean[] reverse(boolean... array) {
		for (int i = 0, j = array.length - 1; i < j; i++, j--) {
			boolean v = array[i];
			array[i] = array[j];
			array[j] = v;
		}
		return array;
	}
	/**
	 * Reverses an array in-place.
	 * @param array the array to reverse
	 * @return the array
	 */
	public static byte[] reverse(byte... array) {
		for (int i = 0, j = array.length - 1; i < j; i++, j--) {
			byte v = array[i];
			array[i] = array[j];
			array[j] = v;
		}
		return array;
	}
	/**
	 * Reverses an array in-place.
	 * @param array the array to reverse
	 * @return the array
	 */
	public static short[] reverse(short... array) {
		for (int i = 0, j = array.length - 1; i < j; i++, j--) {
			short v = array[i];
			array[i] = array[j];
			array[j] = v;
		}
		return array;
	}
	/**
	 * Reverses an array in-place.
	 * @param array the array to reverse
	 * @return the array
	 */
	public static int[] reverse(int... array) {
		for (int i = 0, j = array.length - 1; i < j; i++, j--) {
			int v = array[i];
			array[i] = array[j];
			array[j] = v;
		}
		return array;
	}
	/**
	 * Reverses an array in-place.
	 * @param array the array to reverse
	 * @return the array
	 */
	public static long[] reverse(long... array) {
		for (int i = 0, j = array.length - 1; i < j; i++, j--) {
			long v = array[i];
			array[i] = array[j];
			array[j] = v;
		}
		return array;
	}
	/**
	 * Reverses an array in-place.
	 * @param array the array to reverse
	 * @return the array
	 */
	public static float[] reverse(float... array) {
		for (int i = 0, j = array.length - 1; i < j; i++, j--) {
			float v = array[i];
			array[i] = array[j];
			array[j] = v;
		}
		return array;
	}
	/**
	 * Reverses an array in-place.
	 * @param array the array to reverse
	 * @return the array
	 */
	public static double[] reverse(double... array) {
		for (int i = 0, j = array.length - 1; i < j; i++, j--) {
			double v = array[i];
			array[i] = array[j];
			array[j] = v;
		}
		return array;
	}
	/**
	 * Reverses an array in-place.
	 * @param <T> the array type
	 * @param array the array to reverse
	 * @return the array
	 */
	public static <T> T[] reverse(T[] array) {
		for (int i = 0, j = array.length - 1; i < j; i++, j--) {
			T v = array[i];
			array[i] = array[j];
			array[j] = v;
		}
		return array;
	}
}
