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
 * The two element key record.
 * @author karnokd, 2013.05.08.
 *
 * @param <K1> the key 1 type
 * @param <K2> the key 2 type
 * @param <K3> the key 3 type
 * @param <K4> the key 4 type
 * @param <K5> the key 5 type
 * @param <K6> the key 6 type
 * @param <V> the value type
 */
public class Value6<K1, K2, K3, K4, K5, K6, V> {
	/** The first key element. */
	public final K1 key1;
	/** The second key element. */
	public final K2 key2;
	/** The third key. */
	public final K3 key3;
	/** The fourth key. */
	public final K4 key4;
	/** The fifth key. */
	public final K5 key5;
	/** The sixth key. */
	public final K6 key6;
	/** The value. */
	public final V value;
	/**
	 * Constructor, sets the keys.
	 * @param key1 the first key
	 * @param key2 the second key
	 * @param key3 the third key
	 * @param key4 the fourth key
	 * @param key5 the fifth key
	 * @param key6 the sixth key
	 * @param value the value
	 */
	public Value6(K1 key1, K2 key2, K3 key3, K4 key4, K5 key5, K6 key6, V value) {
		this.key1 = key1;
		this.key2 = key2;
		this.key3 = key3;
		this.key4 = key4;
		this.key5 = key5;
		this.key6 = key6;
		this.value = value;
	}
	/**
	 * Constructor, sets the keys.
	 * @param key1 the first key
	 * @param value5 the Value5 object
	 */
	public Value6(K1 key1, Value5<K2, K3, K4, K5, K6, V> value5) {
		this.key1 = key1;
		this.key2 = value5.key1;
		this.key3 = value5.key2;
		this.key4 = value5.key3;
		this.key5 = value5.key4;
		this.key6 = value5.key5;
		this.value = value5.value;
	}
}