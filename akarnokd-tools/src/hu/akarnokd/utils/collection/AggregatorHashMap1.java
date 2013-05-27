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

import hu.akarnokd.reactive4java.base.Func1;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * A map which creates missing elements via a creator function.
 * @author akarnokd, 2013.05.08.
 * @param <K> the key type
 * @param <V> the value type
 */
public class AggregatorHashMap1<K, V> implements AggregatorMap1<K, V> {
	/** The backing map. */
	protected final Map<K, V> map = new HashMap<K, V>();
	/** The item creator function. */
	@NonNull 
	protected Func1<Object, ? extends V> itemCreator = new Func1<Object, V>() {
		@Override
		public V invoke(Object param1) {
			return null;
		}
	};
	/**
	 * Default constructor.
	 */
	public AggregatorHashMap1() {
		
	}
	/**
	 * Constructor with the item creator.
	 * @param itemCreator the item creator
	 */
	public AggregatorHashMap1(@NonNull Func1<Object, ? extends V> itemCreator) {
		setItemCreator(itemCreator);
	}
	@Override
	public void setItemCreator(@NonNull Func1<Object, ? extends V> itemCreator) {
		if (itemCreator == null) {
			throw new IllegalArgumentException("null");
		}
		this.itemCreator = itemCreator;
	}
	@Override
	public Func1<Object, ? extends V> getItemCreator() {
		return itemCreator;
	}
	@Override
	public V put(K key, V value) {
		return map.put(key, value);
	}
	@Override
	@SuppressWarnings("unchecked")
	public V get(Object key) {
		V r = map.get(key);
		if (r == null) {
			r = itemCreator.invoke(key);
			map.put((K)key, r);
		}
		return r;
	}
	@Override
	public int size() {
		return map.size();
	}
	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}
	@Override
	public Collection<K> keys() {
		return map.keySet();
	}
	@Override
	public Collection<V> values() {
		return map.values();
	}
	@Override
	public Collection<Map.Entry<K, V>> entries() {
		return map.entrySet();
	}
	@Override
	public void clear() {
		map.clear();
	}
	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}
	@Override
	public V getValue(Object key, V defaultValue) {
		if (containsKey(key)) {
			return get(key);
		}
		return defaultValue;
	}
	@Override
	public V remove(Object key) {
		return map.remove(key);
	}
}
