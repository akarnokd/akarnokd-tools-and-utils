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

package hu.akarnokd.utils.sequence;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;

import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * Utility classes for collections.
 * @author akarnokd, 2013.04.29.
 */
public final class CollectionUtils {
	/** The collection utilities. */
	private CollectionUtils() { }
	/**
	 * Returns the key for the first minimum valued entry.
	 * @param <K> the key type
	 * @param <V> the value type
	 * @param map the map to search through
	 * @return the key value
	 */
	public static <K, V extends Comparable<? super V>> K minKey(@NonNull Map<K, V> map) {
		K bestKey = null;
		V bestValue = null;
		for (Map.Entry<K, V> e : map.entrySet()) {
			if (bestValue == null || bestValue.compareTo(e.getValue()) > 0) {
				bestKey = e.getKey();
				bestValue = e.getValue();
			}
		}
		return bestKey;
	}
	/**
	 * Returns the key for the first maximum valued entry.
	 * @param <K> the key type
	 * @param <V> the value type
	 * @param map the map to search through
	 * @return the key value
	 */
	public static <K, V extends Comparable<? super V>> K maxKey(@NonNull Map<K, V> map) {
		K bestKey = null;
		V bestValue = null;
		for (Map.Entry<K, V> e : map.entrySet()) {
			if (bestValue == null || bestValue.compareTo(e.getValue()) < 0) {
				bestKey = e.getKey();
				bestValue = e.getValue();
			}
		}
		return bestKey;
	}
	/**
	 * Returns the key for the first minimum valued entry.
	 * @param <K> the key type
	 * @param <V> the value type
	 * @param map the map to search through
	 * @return the key values
	 */
	@NonNull
	public static <K, V extends Comparable<? super V>> Set<K> minKeys(@NonNull Map<K, V> map) {
		V bestValue = null;
		Set<K> result = null;
		
		for (Map.Entry<K, V> e : map.entrySet()) {
			V v = e.getValue();
			if (bestValue == null || bestValue.compareTo(v) > 0) {
				result = new HashSet<K>();
				bestValue = v;
				result.add(e.getKey());
			} else
			if (bestValue.compareTo(v) == 0) {
				result.add(e.getKey());
			}
			
		}		
		
		return result != null ? result : Sets.<K>newHashSet();
	}
	/**
	 * Returns the key for the first maximum valued entry.
	 * @param <K> the key type
	 * @param <V> the value type
	 * @param map the map to search through
	 * @return the key values
	 */
	@NonNull
	public static <K, V extends Comparable<? super V>> Set<K> maxKeys(@NonNull Map<K, V> map) {
		V bestValue = null;
		Set<K> result = null;
		
		for (Map.Entry<K, V> e : map.entrySet()) {
			V v = e.getValue();
			if (bestValue == null || bestValue.compareTo(v) < 0) {
				result = new HashSet<K>();
				bestValue = v;
				result.add(e.getKey());
			} else
			if (bestValue.compareTo(v) == 0) {
				result.add(e.getKey());
			}
			
		}		
		
		return result != null ? result : Sets.<K>newHashSet();
	}
	/**
	 * Returns the key for the first minimum valued entry.
	 * @param <K> the key type
	 * @param <V> the value type
	 * @param map the map to search through
	 * @param comparator the value comparator
	 * @return the key value
	 */
	public static <K, V> K minKey(@NonNull Map<K, V> map, @NonNull Comparator<? super V> comparator) {
		K bestKey = null;
		V bestValue = null;
		for (Map.Entry<K, V> e : map.entrySet()) {
			if (bestValue == null || comparator.compare(bestValue, e.getValue()) > 0) {
				bestKey = e.getKey();
				bestValue = e.getValue();
			}
		}
		return bestKey;
	}
	/**
	 * Returns the key for the first maximum valued entry.
	 * @param <K> the key type
	 * @param <V> the value type
	 * @param map the map to search through
	 * @param comparator the value comparator
	 * @return the key value
	 */
	public static <K, V> K maxKey(@NonNull Map<K, V> map, @NonNull Comparator<? super V> comparator) {
		K bestKey = null;
		V bestValue = null;
		for (Map.Entry<K, V> e : map.entrySet()) {
			if (bestValue == null || comparator.compare(bestValue, e.getValue()) < 0) {
				bestKey = e.getKey();
				bestValue = e.getValue();
			}
		}
		return bestKey;
	}
	/**
	 * Returns the key for the first minimum valued entry.
	 * @param <K> the key type
	 * @param <V> the value type
	 * @param map the map to search through
	 * @param comparator the value comparator
	 * @return the key values
	 */
	@NonNull
	public static <K, V> Set<K> minKeys(@NonNull Map<K, V> map, @NonNull Comparator<? super V> comparator) {
		V bestValue = null;
		Set<K> result = null;
		
		for (Map.Entry<K, V> e : map.entrySet()) {
			V v = e.getValue();
			if (bestValue == null || comparator.compare(bestValue, v) > 0) {
				result = new HashSet<K>();
				bestValue = v;
				result.add(e.getKey());
			} else
			if (comparator.compare(bestValue, v) == 0) {
				result.add(e.getKey());
			}
			
		}		
		
		return result != null ? result : Sets.<K>newHashSet();
	}
	/**
	 * Returns the key for the first maximum valued entry.
	 * @param <K> the key type
	 * @param <V> the value type
	 * @param map the map to search through
	 * @param comparator the value comparator
	 * @return the key values
	 */
	@NonNull
	public static <K, V> Set<K> maxKeys(@NonNull Map<K, V> map, @NonNull Comparator<? super V> comparator) {
		V bestValue = null;
		Set<K> result = null;
		
		for (Map.Entry<K, V> e : map.entrySet()) {
			V v = e.getValue();
			if (bestValue == null || comparator.compare(bestValue, v) < 0) {
				result = new HashSet<K>();
				bestValue = v;
				result.add(e.getKey());
			} else
			if (comparator.compare(bestValue, v) == 0) {
				result.add(e.getKey());
			}
			
		}		
		
		return result != null ? result : Sets.<K>newHashSet();
	}
}
