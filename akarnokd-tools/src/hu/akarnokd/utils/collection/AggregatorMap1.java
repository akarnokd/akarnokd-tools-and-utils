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

import java.util.Collection;
import java.util.Map;

import rx.functions.Func1;
import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * Base interface for Aggregator Map with one key.
 * @author akarnokd, 2013.05.08.
 * @param <K> the key type
 * @param <V> the value type
 */
public interface AggregatorMap1<K, V> {
	/**
	 * Sets the item creator factory.
	 * @param itemCreator the item creator factory
	 */
	void setItemCreator(@NonNull Func1<Object, ? extends V> itemCreator);
	/**
	 * Returns the item creator.
	 * @return the item creator
	 */
	Func1<Object, ? extends V> getItemCreator();
	/**
	 * Retrieve or create a value with the given key.
	 * @param key the key
	 * @return the value
	 */
	V get(Object key);
	/**
	 * Replace a value by the given key.
	 * @param key the key
	 * @param value the new value
	 * @return the old value
	 */
	V put(K key, V value);
	/**
	 * Returns the number of items in this map.
	 * @return the number of items
	 */
	int size();
	/**
	 * Returns true if this map is empty.
	 * @return true if this map is empty
	 */
	boolean isEmpty();
	/**
	 * Returns a collection of values.
	 * @return the value collection
	 */
	Collection<V> values();
	/**
	 * Returns a collection of the keys. 
	 * @return the key collection
	 */
	Collection<K> keys();
	/**
	 * Returns a collection of the entries in the map.
	 * @return the entries in the map
	 */
	Collection<Map.Entry<K, V>> entries();
	/**
	 * Clears the contents of this map.
	 */
	void clear();
	/**
	 * Check if the given key is in the map.
	 * @param key the key
	 * @return true if in the map
	 */
	boolean containsKey(Object key);
	/**
	 * Retrieves the key or the default value if not present.
	 * @param key the key
	 * @param defaultValue the default value
	 * @return the value
	 */
	V getValue(Object key, V defaultValue);
	/**
	 * Removes the given key and value.
	 * @param key the key to remove
	 * @return the current value
	 */
	V remove(Object key);
}
