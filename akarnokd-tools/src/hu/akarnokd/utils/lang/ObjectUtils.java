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

package hu.akarnokd.utils.lang;

/**
 * General object-related utilities.
 * @author akarnokd, 2013.06.21.
 */
public final class ObjectUtils {
	/** Utility class. */
	private ObjectUtils() { }
	/**
	 * Returns the default value if the value is null.
	 * @param value the value to test for nullness
	 * @param defaultValue if {@code value} is null, return this
	 * @return the value or defaultValue
	 * @param <T> the value type
	 */
	public static <T> T nvl(T value, T defaultValue) {
		return value != null ? value : defaultValue;
	}
}
