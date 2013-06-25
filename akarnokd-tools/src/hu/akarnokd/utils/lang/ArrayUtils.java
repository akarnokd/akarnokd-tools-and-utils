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
}
