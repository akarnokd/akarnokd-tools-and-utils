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
}
