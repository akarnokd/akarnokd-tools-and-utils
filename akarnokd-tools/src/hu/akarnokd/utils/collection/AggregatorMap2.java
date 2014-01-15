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

package hu.akarnokd.utils.collection;

import java.util.Map;

/**
 * The base interface for two-keyed aggregation map.
 * @author akarnokd, 2013.05.08.
 * @param <K1> the first key
 * @param <K2> the second key
 * @param <V> the value type
 */
public interface AggregatorMap2<K1, K2, V> extends AggregatorMap1<K1, AggregatorMap1<K2, V>> {
	/**
	 * Get or create a value with two keys.
	 * @param key1 the first key
	 * @param key2 the second key
	 * @return the value
	 */
	V get(Object key1, Object key2);
	/**
	 * Retrieve a value or return the default value if not present.
	 * @param key1 the first key
	 * @param key2 the second key
	 * @param defaultValue the default value
	 * @return the value
	 */
	V getValue(Object key1, Object key2, V defaultValue);
	/**
	 * Set a value with two keys.
	 * @param key1 the first key
	 * @param key2 the second key
	 * @param value the value
	 * @return the previous value
	 */
	V put(K1 key1, K2 key2, V value);
	/**
	 * Returns all values from the second level maps.
	 * @return the iterable sequence of values
	 */
	Iterable<V> allValues();
	/**
	 * Returns the contents as a flat two-keyed value sequence.
	 * @return the iterable sequence of keys-value
	 */
	Iterable<Value2<K1, K2, V>> entries2();
	/**
	 * Returns all second-level keys (non-unique).
	 * @return the second level key
	 */
	Iterable<K2> keys2();
	/**
	 * Returns all primary-secondary key pairs in a sequence.
	 * @return the primary-secondary key pair sequence
	 */
	Iterable<Map.Entry<K1, K2>> keys12();
	/**
	 * Returns the value sequence.
	 * @return the value sequence
	 */
	Iterable<V> values2();
}
