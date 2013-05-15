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

package hu.akarnokd.utils.collection;


/**
 * The base interface for two-keyed aggregation map.
 * @author akarnokd, 2013.05.08.
 * @param <K1> the first key
 * @param <K2> the second key
 * @param <K3> the third key
 * @param <V> the value type
 */
public interface AggregatorMap3<K1, K2, K3, V> extends AggregatorMap2<K1, K2, AggregatorMap1<K3, V>> {
	/**
	 * Get or create a value with two keys.
	 * @param key1 the first key
	 * @param key2 the second key
	 * @param key3 the third key
	 * @return the value
	 */
	V get(Object key1, Object key2, Object key3);
	/**
	 * Retrieve a value or return the default value if not present.
	 * @param key1 the first key
	 * @param key2 the second key
	 * @param key3 the third key
	 * @param defaultValue the default value
	 * @return the value
	 */
	V getValue(Object key1, Object key2, Object key3, V defaultValue);
	/**
	 * Set a value with two keys.
	 * @param key1 the first key
	 * @param key2 the second key
	 * @param key3 the third key
	 * @param value the value
	 * @return the previous value
	 */
	V put(K1 key1, K2 key2, K3 key3, V value);
	/**
	 * Returns the contents as a flat three-keyed value sequence.
	 * @return the iterable sequence of keys-value
	 */
	Iterable<Value3<K1, K2, K3, V>> entries3();
	/**
	 * Returns all third-level keys (non-unique).
	 * @return the second level key
	 */
	Iterable<K3> keys3();
	/**
	 * Returns all three key tuples in a sequence.
	 * @return the key tuple sequence
	 */
	Iterable<Value2<K1, K2, K3>> keys123();
	/**
	 * Returns the value sequence.
	 * @return the value sequence
	 */
	Iterable<V> values3();
}
