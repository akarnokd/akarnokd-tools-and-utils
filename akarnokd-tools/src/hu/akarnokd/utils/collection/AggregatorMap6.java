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
 * @param <K4> the fourth key
 * @param <K5> the fifth key
 * @param <K6> the sixth key
 * @param <V> the value type
 */
public interface AggregatorMap6<K1, K2, K3, K4, K5, K6, V> 
extends AggregatorMap5<K1, K2, K3, K4, K5, AggregatorMap1<K6, V>> {
	/**
	 * Get or create a value with two keys.
	 * @param key1 the first key
	 * @param key2 the second key
	 * @param key3 the third key
	 * @param key4 the fourth key
	 * @param key5 the fifth key
	 * @param key6 the sixth key
	 * @return the value
	 */
	V get(Object key1, Object key2, Object key3, Object key4, Object key5, Object key6);
	/**
	 * Retrieve a value or return the default value if not present.
	 * @param key1 the first key
	 * @param key2 the second key
	 * @param key3 the third key
	 * @param key4 the fourth key
	 * @param key5 the fifth key
	 * @param key6 the sixth key
	 * @param defaultValue the default value
	 * @return the value
	 */
	V getValue(Object key1, Object key2, Object key3, Object key4, Object key5, Object key6, V defaultValue);
	/**
	 * Set a value with two keys.
	 * @param key1 the first key
	 * @param key2 the second key
	 * @param key3 the third key
	 * @param key4 the fourth key
	 * @param key5 the fifth key
	 * @param key6 the sixth key
	 * @param value the value
	 * @return the previous value
	 */
	V put(K1 key1, K2 key2, K3 key3, K4 key4, K5 key5, K6 key6, V value);
	/**
	 * Returns the contents as a flat four-keyed value sequence.
	 * @return the iterable sequence of keys-value
	 */
	Iterable<Value6<K1, K2, K3, K4, K5, K6, V>> entries6();
	/**
	 * Returns all fourth-level keys (non-unique).
	 * @return the second level key
	 */
	Iterable<K6> keys6();
	/**
	 * Returns all three key tuples in a sequence.
	 * @return the three key tuples sequence
	 */
	Iterable<Value5<K1, K2, K3, K4, K5, K6>> keys123456();
	/**
	 * Returns the value sequence.
	 * @return the value sequence
	 */
	Iterable<V> values6();
}
