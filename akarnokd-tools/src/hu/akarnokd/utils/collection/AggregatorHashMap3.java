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
import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * A multi-level map with three key levels.
 * @param <K1> the first key type
 * @param <K2> the second key type
 * @param <K3> the third key type
 * @param <V> the value type
 * @author karnokd, 2013.05.08.
 *
 */
public class AggregatorHashMap3<K1, K2, K3, V> 
extends AggregatorHashMap2<K1, K2, AggregatorMap1<K3, V>>
implements AggregatorMap3<K1, K2, K3, V> {
	/**
	 * Default constructor.
	 */
	public AggregatorHashMap3() {
		super(new Func1<Object, AggregatorMap1<K3, V>>() {
			@Override
			public AggregatorMap1<K3, V> call(Object param1) {
				return new AggregatorHashMap1<>();
			}
		});
	}
	/**
	 * Constructor with the item creator.
	 * @param itemCreator the item creator
	 */
	public AggregatorHashMap3(@NonNull final Func1<Object, ? extends V> itemCreator) {
		super(new Func1<Object, AggregatorMap1<K3, V>>() {
			@Override
			public AggregatorMap1<K3, V> call(Object param1) {
				return new AggregatorHashMap1<>(itemCreator);
			}
		});
	}
	@Override
	public V get(Object key1, Object key2, Object key3) {
		return get(key1, key2).get(key3);
	}
	@Override
	public V put(K1 key1, K2 key2, K3 key3, V value) {
		return get(key1, key2).put(key3, value);
	}
	@Override
	public Iterable<Value3<K1, K2, K3, V>> entries3() {
		return Ix.from(entries2()).flatMap(
			new Func1<Value2<K1, K2, AggregatorMap1<K3, V>>, Iterable<Value3<K1, K2, K3, V>>>() { 
			@Override
			public Iterable<Value3<K1, K2, K3, V>> call(
					final Value2<K1, K2, AggregatorMap1<K3, V>> param1) {
				
				return Ix.from(param1.value.entries()).map(new Func1<Map.Entry<K3, V>, Value3<K1, K2, K3, V>>() {
					@Override
					public Value3<K1, K2, K3, V> call(Entry<K3, V> param2) {
						return new Value3<>(param1.key1, param1.key2, param2.getKey(), param2.getValue());
					}
				});
			}
		});
	}
	@Override
	public Iterable<K3> keys3() {
		return Ix.from(values2()).flatMap(new Func1<AggregatorMap1<K3, V>, Iterable<K3>>() {
			@Override
			public Iterable<K3> call(AggregatorMap1<K3, V> param1) {
				return param1.keys();
			}
		});
	}
	@Override
	public Iterable<Value2<K1, K2, K3>> keys123() {
		return Ix.from(entries2()).flatMap(
			new Func1<Value2<K1, K2, AggregatorMap1<K3, V>>, Iterable<Value2<K1, K2, K3>>>() { 
			@Override
			public Iterable<Value2<K1, K2, K3>> call(
					final Value2<K1, K2, AggregatorMap1<K3, V>> param1) {
				
				return Ix.from(param1.value.keys()).map(new Func1<K3, Value2<K1, K2, K3>>() {
					@Override
					public Value2<K1, K2, K3> call(K3 param2) {
						return new Value2<>(param1.key1, param1.key2, param2);
					}
				});
			}
		});
	}
	@Override
	public Iterable<V> values3() {
		return Ix.from(values2()).flatMap(new Func1<AggregatorMap1<K3, V>, Iterable<V>>() {
			@Override
			public Iterable<V> call(AggregatorMap1<K3, V> param1) {
				return param1.values();
			}
		});
	}
	@Override
	public V getValue(Object key1, Object key2, Object key3, V defaultValue) {
		AggregatorMap1<K3, V> m = getValue(key1, key2, null);
		return m != null ? m.getValue(key3, defaultValue) : defaultValue;
	}
}
