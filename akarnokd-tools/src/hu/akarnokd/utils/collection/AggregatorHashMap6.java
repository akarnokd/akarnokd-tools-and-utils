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
 * @param <K6> the sixth key type
 * @param <V> the value type
 * @author karnokd, 2013.05.08.
 *
 */
public class AggregatorHashMap6<K1, K2, K3, K4, K5, K6, V> 
extends AggregatorHashMap5<K1, K2, K3, K4, K5, AggregatorMap1<K6, V>>
implements AggregatorMap6<K1, K2, K3, K4, K5, K6, V> {
	/**
	 * Default constructor.
	 */
	public AggregatorHashMap6() {
		super(new Func1<Object, AggregatorMap1<K6, V>>() {
			@Override
			public AggregatorMap1<K6, V> invoke(Object param1) {
				return new AggregatorHashMap1<>();
			}
		});
	}
	/**
	 * Constructor with the item creator.
	 * @param itemCreator the item creator
	 */
	public AggregatorHashMap6(final Func1<Object, ? extends V> itemCreator) {
		super(new Func1<Object, AggregatorMap1<K6, V>>() {
			@Override
			public AggregatorMap1<K6, V> invoke(Object param1) {
				return new AggregatorHashMap1<>(itemCreator);
			}
		});
	}
	@Override
	public V get(Object key1, Object key2, Object key3, Object key4, Object key5, Object key6) {
		return get(key1, key2, key3, key4, key5).get(key6);
	}
	@Override
	public V put(K1 key1, K2 key2, K3 key3, K4 key4, K5 key5, K6 key6, V value) {
		return get(key1, key2, key3, key4, key5).put(key6, value);
	}
	@Override
	public Iterable<Value6<K1, K2, K3, K4, K5, K6, V>> entries6() {
		return Interactive.selectMany(entries5(), 
		new Func1<Value5<K1, K2, K3, K4, K5, AggregatorMap1<K6, V>>, Iterable<Value6<K1, K2, K3, K4, K5, K6, V>>>() {
			@Override
			public Iterable<Value6<K1, K2, K3, K4, K5, K6, V>> invoke(
					final Value5<K1, K2, K3, K4, K5, AggregatorMap1<K6, V>> param1) {
				return Interactive.select(param1.value.entries(), new Func1<Map.Entry<K6, V>, Value6<K1, K2, K3, K4, K5, K6, V>>() {
					@Override
					public Value6<K1, K2, K3, K4, K5, K6, V> invoke(Entry<K6, V> param2) {
						return new Value6<>(param1.key1, param1.key2, param1.key3, param1.key4, param1.key5, param2.getKey(), param2.getValue());
					}
				});
			}
		});
	}
	@Override
	public Iterable<K6> keys6() {
		return Interactive.selectMany(values5(), new Func1<AggregatorMap1<K6, V>, Iterable<K6>>() {
			@Override
			public Iterable<K6> invoke(AggregatorMap1<K6, V> param1) {
				return param1.keys();
			}
		});
	}
	@Override
	public Iterable<Value5<K1, K2, K3, K4, K5, K6>> keys123456() {
		return Interactive.selectMany(entries5(), 
				new Func1<Value5<K1, K2, K3, K4, K5, AggregatorMap1<K6, V>>, Iterable<Value5<K1, K2, K3, K4, K5, K6>>>() { 
				@Override
				public Iterable<Value5<K1, K2, K3, K4, K5, K6>> invoke(
						final Value5<K1, K2, K3, K4, K5, AggregatorMap1<K6, V>> param1) {
					
					return Interactive.select(param1.value.keys(), new Func1<K6, Value5<K1, K2, K3, K4, K5, K6>>() {
						@Override
						public Value5<K1, K2, K3, K4, K5, K6> invoke(K6 param2) {
							return new Value5<>(param1.key1, param1.key2, param1.key3, param1.key4, param1.key5, param2);
						}
					});
				}
			});
	}
	@Override
	public Iterable<V> values6() {
		return Interactive.selectMany(values5(), new Func1<AggregatorMap1<K6, V>, Iterable<V>>() {
			@Override
			public Iterable<V> invoke(AggregatorMap1<K6, V> param1) {
				return param1.values();
			}
		});
	}
	@Override
	public V getValue(Object key1, Object key2, Object key3, Object key4,
			Object key5, Object key6, V defaultValue) {
		AggregatorMap1<K6, V> m = getValue(key1, key2, key3, key4, key5, null);
		return m != null ? m.getValue(key6, defaultValue) : defaultValue;
	}
}
