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

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.RandomAccess;
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
				result = new HashSet<>();
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
				result = new HashSet<>();
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
				result = new HashSet<>();
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
				result = new HashSet<>();
				bestValue = v;
				result.add(e.getKey());
			} else
			if (comparator.compare(bestValue, v) == 0) {
				result.add(e.getKey());
			}
			
		}		
		
		return result != null ? result : Sets.<K>newHashSet();
	}
	/** The list size threshold to switch to index search instead of random access search. */
	private static final int BINARY_SEARCH_INDEX_THRESHOLD = 5000;
	/**
	 * Finds the first index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param <T> the element type
	 * @param list the list to search
	 * @param value the value to search
	 * @param comparator the comparator
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static <T> int findFirst(List<? extends T> list, T value, Comparator<? super T> comparator) {
		return findFirst(list, 0, list.size(), value, comparator);
	}
	/**
	 * Finds the first index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param <T> the element type
	 * @param list the list to search
	 * @param fromIndex the starting index, inclusive
	 * @param toIndex the end index, exclusive
	 * @param value the value to search
	 * @param comparator the comparator
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static <T> int findFirst(List<? extends T> list, int fromIndex, int toIndex, T value, Comparator<? super T> comparator) {
		if (!(list instanceof RandomAccess || list.size() < BINARY_SEARCH_INDEX_THRESHOLD)) {
			return findFirstIterator(list, fromIndex, toIndex, value, comparator);
		}
		int a = fromIndex;
		int b = toIndex;
		while (a <= b) {
			int mid = a + (b - a) / 2;
			T midVal = list.get(mid);
			int c = comparator.compare(midVal, value);
			if (c < 0) {
				a = mid + 1;
			} else
			if (c > 0) {
				b = mid - 1;
			} else {
				if (mid > 0) {
					if (comparator.compare(list.get(mid - 1), value) != 0) {
						return mid;
					} else {
						b = mid - 1;
					}
				} else {
					return 0;
				}
			}
		}
		return -(a + 1);
	}
	/**
	 * Finds the first index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param <T> the element type
	 * @param list the list to search
	 * @param fromIndex the starting index, inclusive
	 * @param toIndex the end index, exclusive
	 * @param value the value to search
	 * @param comparator the comparator
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static <T> int findFirstIterator(List<? extends T> list, int fromIndex, int toIndex, T value, Comparator<? super T> comparator) {
		int a = fromIndex;
		int b = toIndex;
		ListIterator<? extends T> it = list.listIterator();
		while (a <= b) {
			int mid = a + (b - a) / 2;
			T midVal = get(it, mid);
			int c = comparator.compare(midVal, value);
			if (c < 0) {
				a = mid + 1;
			} else
			if (c > 0) {
				b = mid - 1;
			} else {
				if (mid > 0) {
					if (comparator.compare(get(it, mid - 1), value) != 0) {
						return mid;
					} else {
						b = mid - 1;
					}
				} else {
					return 0;
				}
			}
		}
		return -(a + 1);
	}
	/**
	 * Finds the last index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param <T> the element type
	 * @param list the list to search
	 * @param value the value to search
	 * @param comparator the comparator
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static <T> int findLast(List<? extends T> list, T value, Comparator<? super T> comparator) {
		return findLast(list, 0, list.size(), value, comparator);
	}
	/**
	 * Finds the last index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param <T> the element type
	 * @param list the list to search
	 * @param fromIndex the starting index, inclusive
	 * @param toIndex the end index, exclusive
	 * @param value the value to search
	 * @param comparator the comparator
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static <T> int findLast(List<? extends T> list, int fromIndex, int toIndex, T value, Comparator<? super T> comparator) {
		if (!(list instanceof RandomAccess || list.size() < BINARY_SEARCH_INDEX_THRESHOLD)) {
			return findLastIterator(list, fromIndex, toIndex, value, comparator);
		}
		int a = fromIndex;
		int b = toIndex;
		while (a <= b) {
			int mid = a + (b - a) / 2;
			T midVal = list.get(mid);
			int c = comparator.compare(midVal, value);
			if (c < 0) {
				a = mid + 1;
			} else
			if (c > 0) {
				b = mid - 1;
			} else {
				if (mid < toIndex - 1) {
					if (comparator.compare(list.get(mid + 1), value) != 0) {
						return mid;
					} else {
						a = mid + 1;
					}
				} else {
					return 0;
				}
			}
		}
		return -(a + 1);
	}
	/**
	 * Finds the last index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param <T> the element type
	 * @param list the list to search
	 * @param fromIndex the starting index, inclusive
	 * @param toIndex the end index, exclusive
	 * @param value the value to search
	 * @param comparator the comparator
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static <T> int findLastIterator(List<? extends T> list, int fromIndex, int toIndex, T value, Comparator<? super T> comparator) {
		int a = fromIndex;
		int b = toIndex;
		ListIterator<? extends T> it = list.listIterator();
		while (a <= b) {
			int mid = a + (b - a) / 2;
			T midVal = get(it, mid);
			int c = comparator.compare(midVal, value);
			if (c < 0) {
				a = mid + 1;
			} else
			if (c > 0) {
				b = mid - 1;
			} else {
				if (mid < toIndex - 1) {
					if (comparator.compare(get(it, mid + 1), value) != 0) {
						return mid;
					} else {
						a = mid + 1;
					}
				} else {
					return 0;
				}
			}
		}
		return -(a + 1);
	}
	/**
	 * Finds the first index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param <T> the element type, self comparable
	 * @param list the list to search
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static <T extends Comparable<? super T>> int findFirst(List<T> list, T value) {
		return findFirst(list, 0, list.size(), value);
	}
	/**
	 * Finds the first index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param <T> the element type, self comparable
	 * @param list the list to search
	 * @param fromIndex the starting index, inclusive
	 * @param toIndex the end index, exclusive
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static <T extends Comparable<? super T>> int findFirst(List<T> list, int fromIndex, int toIndex, T value) {
		if (!(list instanceof RandomAccess || list.size() < BINARY_SEARCH_INDEX_THRESHOLD)) {
			return findFirstIterator(list, fromIndex, toIndex, value);
		}
		int a = fromIndex;
		int b = toIndex;
		while (a <= b) {
			int mid = a + (b - a) / 2;
			T midVal = list.get(mid);
			int c = midVal.compareTo(value);
			if (c < 0) {
				a = mid + 1;
			} else
			if (c > 0) {
				b = mid - 1;
			} else {
				if (mid > 0) {
					if (list.get(mid - 1).compareTo(value) != 0) {
						return mid;
					} else {
						b = mid - 1;
					}
				} else {
					return 0;
				}
			}
		}
		return -(a + 1);
	}
	/**
	 * Finds the first index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param <T> the element type, self comparable
	 * @param list the list to search
	 * @param fromIndex the starting index, inclusive
	 * @param toIndex the end index, exclusive
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	private static <T extends Comparable<? super T>> int findFirstIterator(List<T> list, int fromIndex, int toIndex, T value) {
		int a = fromIndex;
		int b = toIndex;
		ListIterator<T> it = list.listIterator();
		while (a <= b) {
			int mid = a + (b - a) / 2;
			T midVal = get(it, mid);
			int c = midVal.compareTo(value);
			if (c < 0) {
				a = mid + 1;
			} else
			if (c > 0) {
				b = mid - 1;
			} else {
				if (mid > 0) {
					if (get(it, mid - 1).compareTo(value) != 0) {
						return mid;
					} else {
						b = mid - 1;
					}
				} else {
					return 0;
				}
			}
		}
		return -(a + 1);
	}
	/**
	 * Returns the indexth element from the list by using the list iterator to navigate.
	 * @param <T> the element type
	 * @param it the list iterator
	 * @param index the target index
	 * @return the value
	 */
	private static <T> T get(ListIterator<? extends T> it, int index) {
		T obj = null;
		int pos = it.nextIndex();
		if (pos <= index) {
			do {
				obj = it.next();
			} while (pos++ < index);
		} else {
			do {
				obj = it.previous();
			} while (--pos > index);
		}
		return obj;
	}
	/**
	 * Finds the last index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param <T> the element type, self comparable
	 * @param list the list to search
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static <T extends Comparable<? super T>> int findLast(List<T> list, T value) {
		return findLast(list, 0, list.size(), value);
	}
	/**
	 * Finds the last index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param <T> the element type, self comparable
	 * @param list the list to search
	 * @param fromIndex the starting index, inclusive
	 * @param toIndex the end index, exclusive
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	public static <T extends Comparable<? super T>> int findLast(List<T> list, int fromIndex, int toIndex, T value) {
		if (!(list instanceof RandomAccess || list.size() < BINARY_SEARCH_INDEX_THRESHOLD)) {
			return findLastIterator(list, fromIndex, toIndex, value);
		}
		int a = fromIndex;
		int b = toIndex;
		while (a <= b) {
			int mid = a + (b - a) / 2;
			T midVal = list.get(mid);
			int c = midVal.compareTo(value);
			if (c < 0) {
				a = mid + 1;
			} else
			if (c > 0) {
				b = mid - 1;
			} else {
				if (mid < toIndex - 1) {
					if (list.get(mid + 1).compareTo(value) != 0) {
						return mid;
					} else {
						a = mid + 1;
					}
				} else {
					return 0;
				}
			}
		}
		return -(a + 1);
	}
	/**
	 * Finds the last index where the value is located or
	 * the index where it could be inserted, similar to the regular
	 * binarySearch() methods, but it works with duplicate elements.
	 * @param <T> the element type, self comparable
	 * @param list the list to search
	 * @param fromIndex the starting index, inclusive
	 * @param toIndex the end index, exclusive
	 * @param value the value to search
	 * @return if positive, the exact index where the value is first
	 * encountered, or -(insertion point - 1) if not in the array.
	 */
	private static <T extends Comparable<? super T>> int findLastIterator(List<T> list, int fromIndex, int toIndex, T value) {
		int a = fromIndex;
		int b = toIndex;
		ListIterator<T> it = list.listIterator();
		while (a <= b) {
			int mid = a + (b - a) / 2;
			T midVal = get(it, mid);
			int c = midVal.compareTo(value);
			if (c < 0) {
				a = mid + 1;
			} else
			if (c > 0) {
				b = mid - 1;
			} else {
				if (mid < toIndex - 1) {
					if (get(it, mid + 1).compareTo(value) != 0) {
						return mid;
					} else {
						a = mid + 1;
					}
				} else {
					return 0;
				}
			}
		}
		return -(a + 1);
	}
    /**
     * In-place trim the list.
     * @param list the source list
     * @return the source list
     */
    @NonNull
    public static List<String> trim(@NonNull List<String> list) {
    	for (int i = 0; i < list.size(); i++) {
    		list.set(i, list.get(i).trim());
    	}
    	return list;
    }
}
