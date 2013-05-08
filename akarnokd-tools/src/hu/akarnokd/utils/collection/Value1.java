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

import java.util.Map;
import java.util.Objects;

/**
 * The two element key record.
 * @author karnokd, 2013.05.08.
 *
 * @param <K1> the key 1 type
 * @param <V> the value type
 */
public class Value1<K1, V> implements Map.Entry<K1, V> {
	/** The first key element. */
	public final K1 key1;
	/** The value. */
	public final V value;
	/** The hash. */
	protected final int h;
	/**
	 * Constructor, sets the keys.
	 * @param key1 the first key
	 * @param value the value
	 */
	public Value1(K1 key1, V value) {
		this.key1 = key1;
		this.value = value;
		h = Objects.hash(this.key1, this.value);
	}
	/**
	 * Constructor, sets the keys and value.
	 * @param value the first key and value
	 */
	public Value1(Map.Entry<K1, V> value) {
		this.key1 = value.getKey();
		this.value = value.getValue();
		h = Objects.hash(this.key1, this.value);
	}
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Map.Entry<?, ?>) {
			Map.Entry<?, ?> entry = (Map.Entry<?, ?>) obj;
			return Objects.equals(key1, entry.getKey())
					&& Objects.equals(value, entry.getValue());
		}
		return false;
	}
	@Override
	public K1 getKey() {
		return key1;
	}
	@Override
	public V getValue() {
		return value;
	}
	@Override
	public int hashCode() {
		return h;
	}
	@Override
	public V setValue(V value) {
		throw new UnsupportedOperationException();
	}
}