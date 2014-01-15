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

import ix.Interactive;

import java.util.Map;
import java.util.Map.Entry;

import rx.util.functions.Func1;
import edu.umd.cs.findbugs.annotations.NonNull;


/**
 * A multi-level map which creates missing entries on the first level.
 * @param <K1> the first key type
 * @param <K2> the second key type
 * @param <V> the value type
 * @author karnokd, 2013.05.08.
 *
 */
public class AggregatorHashMap2<K1, K2, V> extends AggregatorHashMap1<K1, AggregatorMap1<K2, V>>
implements AggregatorMap2<K1, K2, V> {
	/**
	 * Default constructor.
	 */
	public AggregatorHashMap2() {
		super(new Func1<Object, AggregatorHashMap1<K2, V>>() {
			@Override
			public AggregatorHashMap1<K2, V> call(Object param1) {
				return new AggregatorHashMap1<K2, V>();
			}
		});
	}
	/**
	 * Constructor with the item creator.
	 * @param itemCreator the item creator
	 */
	public AggregatorHashMap2(@NonNull final Func1<Object, ? extends V> itemCreator) {
		super(new Func1<Object, AggregatorHashMap1<K2, V>>() {
			@Override
			public AggregatorHashMap1<K2, V> call(Object param1) {
				return new AggregatorHashMap1<K2, V>(itemCreator);
			}
		});
	}
	@Override
	public V get(Object key1, Object key2) {
		return get(key1).get(key2);
	}
	@Override
	public V put(K1 key1, K2 key2, V value) {
		return get(key1).put(key2, value);
	}
	@Override
	public Iterable<K2> keys2() {
		return Interactive.selectMany(values(), new Func1<AggregatorMap1<K2, ?>, Iterable<K2>>() {
			@Override
			public Iterable<K2> call(AggregatorMap1<K2, ?> param1) {
				return param1.keys();
			}
		});
	}
	@Override
	public Iterable<Entry<K1, K2>> keys12() {
		return Interactive.selectMany(entries(), 
		new Func1<Map.Entry<K1, AggregatorMap1<K2, V>>, Iterable<Map.Entry<K1, K2>>>() { 
			@Override
			public Iterable<Entry<K1, K2>> call(
					Entry<K1, AggregatorMap1<K2, V>> param1) {
				final K1 key1 = param1.getKey();
				return Interactive.select(param1.getValue().keys(), new Func1<K2, Entry<K1, K2>>() { 
					@Override
					public Entry<K1, K2> call(K2 param1) {
						return new Value1<>(key1, param1);
					}
				});
			}
		});
	}
	@Override
	public Iterable<Value2<K1, K2, V>> entries2() {
		return Interactive.selectMany(entries(), new Func1<Map.Entry<K1, AggregatorMap1<K2, V>>, Iterable<Value2<K1, K2, V>>>() {
			@Override
			public Iterable<Value2<K1, K2, V>> call(
					Entry<K1, AggregatorMap1<K2, V>> param1) {
				final K1 key1 = param1.getKey();
				return Interactive.select(param1.getValue().entries(), 
				new Func1<Map.Entry<K2, V>, Value2<K1, K2, V>>() {
					@Override
					public Value2<K1, K2, V> call(
							Entry<K2, V> param1) {
						return new Value2<>(key1, param1);
					}
				});
			}
		});
	}
	@Override
	public Iterable<V> allValues() {
		return Interactive.selectMany(values(), new Func1<AggregatorMap1<K2, V>, Iterable<V>>() { 
			@Override
			public Iterable<V> call(AggregatorMap1<K2, V> param1) {
				return param1.values();
			}
		});

	}
	@Override
	public Iterable<V> values2() {
		return allValues();
	}
	@Override
	public V getValue(Object key1, Object key2, V defaultValue) {
		AggregatorMap1<K2, V> m = getValue(key1, null);
		return m != null ? m.getValue(key2, defaultValue) : defaultValue;
	}
	
}
