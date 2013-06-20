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
 * Utility methods for comparable objects. 
 * @author akarnokd, 2013.06.10.
 *
 */
public final class ComparableUtils {
	/** Utility class. */
	private ComparableUtils() { }
	/**
	 * Compares two, potentially null, comparable objects.
	 * @param o1 the first object
	 * @param o2 the second object
	 * @return the usual comparison value
	 * @param <T> the self-comparable type
	 */
	public static <T extends Comparable<? super T>> int nullsFirst(T o1, T o2) {
		if (o1 == o2) {
			return 0;
		} else
		if (o1 == null) {
			return -1;
		} else
		if (o2 == null) {
			return 1;
		}
		return o1.compareTo(o2);
	}
	/**
	 * Compares two, potentially null, comparable objects.
	 * @param o1 the first object
	 * @param o2 the second object
	 * @return the usual comparison value
	 * @param <T> the self-comparable type
	 */
	public static <T extends Comparable<? super T>> int nullsLast(T o1, T o2) {
		if (o1 == o2) {
			return 0;
		} else
		if (o1 == null) {
			return 1;
		} else
		if (o2 == null) {
			return -1;
		}
		return o1.compareTo(o2);
	}
}
