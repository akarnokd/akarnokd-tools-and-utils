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

import ix.Ix;

import java.util.Map;
import java.util.Map.Entry;

import rx.functions.Func1;

/**
 * A multi-level map with four key levels.
 * @param <K1> the first key type
 * @param <K2> the second key type
 * @param <K3> the third key type
 * @param <K4> the fourth key type
 * @param <V> the value type
 * @author karnokd, 2013.05.08.
 *
 */
public class AggregatorHashMap4<K1, K2, K3, K4, V> 
extends AggregatorHashMap3<K1, K2, K3, AggregatorMap1<K4, V>>
implements AggregatorMap4<K1, K2, K3, K4, V> {
	/**
	 * Default constructor.
	 */
	public AggregatorHashMap4() {
		super(new Func1<Object, AggregatorMap1<K4, V>>() {
			@Override
			public AggregatorMap1<K4, V> call(Object param1) {
				return new AggregatorHashMap1<>();
			}
		});
	}
	/**
	 * Constructor with the item creator.
	 * @param itemCreator the item creator
	 */
	public AggregatorHashMap4(final Func1<Object, ? extends V> itemCreator) {
		super(new Func1<Object, AggregatorMap1<K4, V>>() {
			@Override
			public AggregatorMap1<K4, V> call(Object param1) {
				return new AggregatorHashMap1<>(itemCreator);
			}
		});
	}
	@Override
	public V get(Object key1, Object key2, Object key3, Object key4) {
		return get(key1, key2, key3).get(key4);
	}
	@Override
	public V put(K1 key1, K2 key2, K3 key3, K4 key4, V value) {
		return get(key1, key2, key3).put(key4, value);
	}
	@Override
	public Iterable<Value4<K1, K2, K3, K4, V>> entries4() {
		return Ix.from(entries3()).flatMap(
		new Func1<Value3<K1, K2, K3, AggregatorMap1<K4, V>>, Iterable<Value4<K1, K2, K3, K4, V>>>() {
			@Override
			public Iterable<Value4<K1, K2, K3, K4, V>> call(
					final Value3<K1, K2, K3, AggregatorMap1<K4, V>> param1) {
				return Ix.from(param1.value.entries()).map(new Func1<Map.Entry<K4, V>, Value4<K1, K2, K3, K4, V>>() {
					@Override
					public Value4<K1, K2, K3, K4, V> call(Entry<K4, V> param2) {
						return new Value4<>(param1.key1, param1.key2, param1.key3, param2.getKey(), param2.getValue());
					}
				});
			}
		});
	}
	@Override
	public Iterable<K4> keys4() {
		return Ix.from(values3()).flatMap(new Func1<AggregatorMap1<K4, V>, Iterable<K4>>() {
			@Override
			public Iterable<K4> call(AggregatorMap1<K4, V> param1) {
				return param1.keys();
			}
		});
	}
	@Override
	public Iterable<Value3<K1, K2, K3, K4>> keys1234() {
		return Ix.from(entries3()).flatMap(
				new Func1<Value3<K1, K2, K3, AggregatorMap1<K4, V>>, Iterable<Value3<K1, K2, K3, K4>>>() { 
				@Override
				public Iterable<Value3<K1, K2, K3, K4>> call(
						final Value3<K1, K2, K3, AggregatorMap1<K4, V>> param1) {
					
					return Ix.from(param1.value.keys()).map(new Func1<K4, Value3<K1, K2, K3, K4>>() {
						@Override
						public Value3<K1, K2, K3, K4> call(K4 param2) {
							return new Value3<>(param1.key1, param1.key2, param1.key3, param2);
						}
					});
				}
			});
	}
	@Override
	public Iterable<V> values4() {
		return Ix.from(values3()).flatMap(new Func1<AggregatorMap1<K4, V>, Iterable<V>>() {
			@Override
			public Iterable<V> call(AggregatorMap1<K4, V> param1) {
				return param1.values();
			}
		});
	}
	@Override
	public V getValue(Object key1, Object key2, Object key3, Object key4,
			V defaultValue) {
		AggregatorMap1<K4, V> m = getValue(key1, key2, key3, null);
		return m != null ? m.getValue(key4, defaultValue) : defaultValue;
	}
}
