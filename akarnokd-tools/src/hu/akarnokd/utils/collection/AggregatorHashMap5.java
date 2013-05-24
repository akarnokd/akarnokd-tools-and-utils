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
import hu.akarnokd.reactive4java.interactive.Interactive;

import java.util.Map;
import java.util.Map.Entry;

/**
 * A multi-level map with four key levels.
 * @param <K1> the first key type
 * @param <K2> the second key type
 * @param <K3> the third key type
 * @param <K4> the fourth key type
 * @param <K5> the fifth key type
 * @param <V> the value type
 * @author karnokd, 2013.05.08.
 *
 */
public class AggregatorHashMap5<K1, K2, K3, K4, K5, V> 
extends AggregatorHashMap4<K1, K2, K3, K4, AggregatorMap1<K5, V>>
implements AggregatorMap5<K1, K2, K3, K4, K5, V> {
	/**
	 * Default constructor.
	 */
	public AggregatorHashMap5() {
		super(new Func1<Object, AggregatorMap1<K5, V>>() {
			@Override
			public AggregatorMap1<K5, V> invoke(Object param1) {
				return new AggregatorHashMap1<>();
			}
		});
	}
	/**
	 * Constructor with the item creator.
	 * @param itemCreator the item creator
	 */
	public AggregatorHashMap5(final Func1<Object, ? extends V> itemCreator) {
		super(new Func1<Object, AggregatorMap1<K5, V>>() {
			@Override
			public AggregatorMap1<K5, V> invoke(Object param1) {
				return new AggregatorHashMap1<>(itemCreator);
			}
		});
	}
	@Override
	public V get(Object key1, Object key2, Object key3, Object key4, Object key5) {
		return get(key1, key2, key3, key4).get(key5);
	}
	@Override
	public V put(K1 key1, K2 key2, K3 key3, K4 key4, K5 key5, V value) {
		return get(key1, key2, key3, key4).put(key5, value);
	}
	@Override
	public Iterable<Value5<K1, K2, K3, K4, K5, V>> entries5() {
		return Interactive.selectMany(entries4(), 
		new Func1<Value4<K1, K2, K3, K4, AggregatorMap1<K5, V>>, Iterable<Value5<K1, K2, K3, K4, K5, V>>>() {
			@Override
			public Iterable<Value5<K1, K2, K3, K4, K5, V>> invoke(
					final Value4<K1, K2, K3, K4, AggregatorMap1<K5, V>> param1) {
				return Interactive.select(param1.value.entries(), new Func1<Map.Entry<K5, V>, Value5<K1, K2, K3, K4, K5, V>>() {
					@Override
					public Value5<K1, K2, K3, K4, K5, V> invoke(Entry<K5, V> param2) {
						return new Value5<>(param1.key1, param1.key2, param1.key3, param1.key4, param2.getKey(), param2.getValue());
					}
				});
			}
		});
	}
	@Override
	public Iterable<K5> keys5() {
		return Interactive.selectMany(values4(), new Func1<AggregatorMap1<K5, V>, Iterable<K5>>() {
			@Override
			public Iterable<K5> invoke(AggregatorMap1<K5, V> param1) {
				return param1.keys();
			}
		});
	}
	@Override
	public Iterable<Value4<K1, K2, K3, K4, K5>> keys12345() {
		return Interactive.selectMany(entries4(), 
				new Func1<Value4<K1, K2, K3, K4, AggregatorMap1<K5, V>>, Iterable<Value4<K1, K2, K3, K4, K5>>>() { 
				@Override
				public Iterable<Value4<K1, K2, K3, K4, K5>> invoke(
						final Value4<K1, K2, K3, K4, AggregatorMap1<K5, V>> param1) {
					
					return Interactive.select(param1.value.keys(), new Func1<K5, Value4<K1, K2, K3, K4, K5>>() {
						@Override
						public Value4<K1, K2, K3, K4, K5> invoke(K5 param2) {
							return new Value4<>(param1.key1, param1.key2, param1.key3, param1.key4, param2);
						}
					});
				}
			});
	}
	@Override
	public Iterable<V> values5() {
		return Interactive.selectMany(values4(), new Func1<AggregatorMap1<K5, V>, Iterable<V>>() {
			@Override
			public Iterable<V> invoke(AggregatorMap1<K5, V> param1) {
				return param1.values();
			}
		});
	}
	@Override
	public V getValue(Object key1, Object key2, Object key3, Object key4,
			Object key5, V defaultValue) {
		AggregatorMap1<K5, V> m = getValue(key1, key2, key3, key4, null);
		return m != null ? m.getValue(key5, defaultValue) : defaultValue;
	}
}
