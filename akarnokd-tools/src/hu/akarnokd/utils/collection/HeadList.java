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

import hu.akarnokd.utils.lang.Action1E;
import hu.akarnokd.utils.lang.Func1E;
import hu.akarnokd.utils.lang.StringUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import rx.functions.Action1;
import rx.functions.Func1;
import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * An immutable collection structure which references
 * another instance as the "head" of the list.
 * <p>Note that the iterability of this list is reverse by default.</p>
 * @author akarnokd, 2013.05.28.
 * @see TailList
 * @param <T> the element type
 */
public final class HeadList<T> implements Iterable<T> {
	/** The head of the list. */
	private HeadList<T> head;
	/** The value. */
	private final T value;
	/**
	 * Creates a head list from the given sequence of values.
	 * <p>The sequence should contain at least one item.</p>
	 * @param src the source sequence
	 * @return the head list with the elements
	 * @param <T> the element type
	 */
	public static <T> HeadList<T> from(Iterable<? extends T> src) {
		Iterator<? extends T> it = src.iterator();
		if (it.hasNext()) {
			T t = it.next();
			HeadList<T> h = new HeadList<>(t);
			while (it.hasNext()) {
				h = h.add(it.next());
			}
			return h;
		}
		throw new IllegalArgumentException("Empty source");
	}
	/**
	 * Creates a head list from the given array of Ts.
	 * <p>The sequence should contain at least one item.</p>
	 * @param src the source of Ts
	 * @return the head list
	 * @param <T> the element type
	 */
	@SafeVarargs
	public static <T> HeadList<T> from(T... src) {
		if (src.length > 0) {
			HeadList<T> h = new HeadList<>(src[0]);
			for (int i = 1; i < src.length; i++) {
				h = h.add(src[i]);
			}
			return h;
		}
		throw new IllegalArgumentException("Empty source");
	}
	/**
	 * Constructor with a head list and value.
	 * @param head the head list
	 * @param value the value
	 */
	public HeadList(HeadList<T> head, T value) {
		this.head = head;
		this.value = value;
	}
	/**
	 * Constructor with only a value.
	 * @param value the list value
	 */
	public HeadList(T value) {
		this.head = null;
		this.value = value;
	}
	/**
	 * Creates a new headlist with the given new
	 * element and this list as the head.
	 * @param value the value to add
	 * @return the new headlist
	 */
	public HeadList<T> add(T value) {
		return new HeadList<>(this, value);
	}
	/**
	 * Creates a new head list which has
	 * this list as the head and all the
	 * elements from the sequence.
	 * @param src the source sequence
	 * @return the new head list
	 */
	public HeadList<T> addAll(Iterable<? extends T> src) {
		HeadList<T> h = this;
		for (T t : src) {
			h = h.add(t);
		}
		return h;
	}
	/**
	 * Creates a new head list which has
	 * this list as the head and all the
	 * elements from the array.
	 * @param src the source sequence
	 * @return the new head list
	 */
	public HeadList<T> addAll(@SuppressWarnings("unchecked") T... src) {
		HeadList<T> h = this;
		for (T t : src) {
			h = h.add(t);
		}
		return h;
	}
	/**
	 * Creates a new head list which starts with the given value.
	 * @param value the value to add as first
	 * @return the new head list
	 */
	public HeadList<T> addFirst(T value) {
		HeadList<T> hl0 = this.head;
		HeadList<T> r = new HeadList<>(this.value);
		HeadList<T> h = r;
		
		while (hl0 != null) {
			h.head = new HeadList<>(hl0.value);
			h = h.head;
			hl0 = hl0.head;
		}
		h.head = new HeadList<>(value);
		
		return r;
	}
	/**
	 * Adds the elements to the collection in reverse order.
	 * @param coll the output collection
	 */
	public void addTo(Collection<? super T> coll) {
		HeadList<T> h = this;
		while (h != null) {
			coll.add(h.value);
			h = h.head;
		}
	}
	/**
	 * Adds the contents of this list to the given output list, keeping
	 * the order.
	 * @param list the target list to add to
	 */
	public void addTo(List<? super T> list) {
		int idx = list.size();
		HeadList<T> h = this;
		while (h != null) {
			list.add(h.value);
			h = h.head;
		}
		Collections.reverse(list.subList(idx, list.size()));
	}
	/**
	 * Check if the object is in this list.
	 * @param value the value to look for
	 * @return true if in this list
	 */
	public boolean contains(Object value) {
		return find(value) != null;
	}
	/**
	 * Check if all elements in the source sequence
	 * is present in this list.
	 * @param src the source sequence
	 * @return true if all elements are in this list
	 */
	public boolean containsAll(Iterable<?> src) {
		for (Object o : src) {
			if (!contains(o)) {
				return false;
			}
		}
		return true;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		HeadList<?> hl = (HeadList<?>)obj;
		HeadList<?> hl0 = this;
		while (hl0 != null && hl != null) {
			if (!Objects.equals(hl.value, hl0.value)) {
				return false;
			}
			hl0 = hl0.head;
			hl = hl.head;
		}
		return hl == hl0;
	}
	/**
	 * Returns the head list element where the given
	 * value equals the item value.
	 * @param value the value to find
	 * @return the head list element or null if not found
	 */
	public HeadList<T> find(Object value) {
		HeadList<T> h = this;
		while (h != null) {
			if (Objects.equals(h.value, value)) {
				return h;
			}
			h = h.head;
		}
		return null;
	}
	/**
	 * Returns an iterator which traverses the list from the start
	 * of the list. The traversal is uses <code>O(n<sup>2</sup>)</code>
	 * steps and is very expensive due the way the list is structured.
	 * For faster traversal, use the <code>reverseIterable()</code>
	 * or <code>toArray()</code> methods.
	 * @return the forward iterable
	 */
	public Iterable<T> forwardIterable() {
		return new Iterable<T>() {
			@Override
			public Iterator<T> iterator() {
				final int s = size();
				return new Iterator<T>() {
					/** The current index. */
					int index = 0;
					@Override
					public boolean hasNext() {
						return index < s;
					}
					@Override
					public T next() {
						if (hasNext()) {
							int i = s - 1 - index;
							HeadList<T> h = HeadList.this;
							while (i > 0) {
								h = h.head;
								i--;
							}
							index++;
							return h.value;
						}
						throw new NoSuchElementException();
					}
					@Override
					public void remove() {
						throw new UnsupportedOperationException();
					}
				};
			}
		};
	}
	@Override
	public int hashCode() {
		int hash = 17;
		HeadList<T> h = this;
		while (h != null) {
			hash = hash * 31 + (value != null ? value.hashCode() : 0);
			h = h.head;
		}
		
		return hash;
	}
	/**
	 * Returns the head list, which can be null.
	 * @return the head list
	 */
	public HeadList<T> head() {
		return head;
	}
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			/** The current item. */
			HeadList<T> current = HeadList.this;
			@Override
			public boolean hasNext() {
				return current != null;
			}
			@Override
			public T next() {
				if (hasNext()) {
					T v = current.value;
					current = current.head;
					return v;
				}
				throw new NoSuchElementException();
			}
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};
	}
	/**
	 * Returns the number of elements in this list.
	 * @return the number of elements
	 */
	public int size() {
		HeadList<T> h = head;
		int i = 1;
		while (h != null) {
			i++;
			h = h.head;
		}
		return i;
	}
	/**
	 * Returns an array of all elements of this list.
	 * @return the array
	 */
	@NonNull
	public Object[] toArray() {
		Object[] r = new Object[size()];
		int i = r.length - 1;
		HeadList<T> h = this;
		while (h != null) {
			r[i--] = h.value;
			h = h.head;
		}
		return r;
	}
	/**
	 * Fills in the target array with the values
	 * from this list if it has enough capacity,
	 * or a completely new array is created and returned.
	 * @param array the array to fill in or use as template
	 * @return the input array or a new array holding all values
	 */
	@NonNull
	@SuppressWarnings("unchecked")
	public T[] toArray(@NonNull T[] array) {
		int s = size();
		if (s > array.length) {
			array = (T[])Array.newInstance(array.getClass().getComponentType(), s);
		}
		
		int i = s - 1;
		HeadList<T> h = this;
		while (h != null) {
			array[i--] = h.value;
			h = h.head;
		}
		return array;
	}
	/**
	 * Returns the contents as a regular list.
	 * @return the list
	 */
	public ArrayList<T> toArrayList() {
		ArrayList<T> r = new ArrayList<>();

		addTo(r);
		
		return r;
	}
	/**
	 * Returns the contents as a linked list.
	 * @return the linked list
	 */
	public LinkedList<T> toLinkedList() {
		LinkedList<T> r = new LinkedList<>();
		
		HeadList<T> h = this;
		while (h != null) {
			r.addFirst(h.value);
			h = h.head;
		}

		return r;
	}
	/**
	 * Convert this head list into a tail list.
	 * @return the tail list
	 */
	public TailList<T> toTailList() {
		TailList<T> tl = new TailList<>(value);
		HeadList<T> h = head;
		while (h != null) {
			tl = new TailList<>(h.value, tl);
			h = h.head;
		}
		return tl;
	}
	/**
	 * Returns the last value of this list.
	 * @return the last value
	 */
	public T value() {
		return value;
	}
	/**
	 * Returns a new list which doesn't contain the elements
	 * where the predicate returned true.
	 * @param predicate the predicate
	 * @return the new list
	 */
	public HeadList<T> removeIf(Func1<? super T, Boolean> predicate) {
		HeadList<T> r = null;
		HeadList<T> t = null;
		
		HeadList<T> hl = this;
		while (hl != null) {
			if (!predicate.call(hl.value)) {
				if (t == null) {
					t = new HeadList<>(hl.value);
					r = t;
				} else {
					t.head = new HeadList<>(hl.value);
					t = t.head;
				}
			}
			hl = hl.head;
		}
		
		return r;
	}
	@Override
	public String toString() {
		StringBuilder r = new StringBuilder();
		r.append(']');
		HeadList<T> hl = this;
		while (hl != null) {
			if (hl != this) {
				r.append(' ').append(',');
			}
			StringUtils.appendReversed(hl.value != null ? hl.value.toString() : "null", r);
			hl = hl.head;
		}
		r.append('[');
		return r.reverse().toString();
	}
	/**
	 * Invokes an action for each element of this list.
	 * @param action the action to invoke
	 */
	public void forEach(@NonNull Action1<? super T> action) {
		HeadList<T> tl = this;
		while (tl != null) {
			action.call(tl.value);
			tl = tl.head;
		}
	}
	/**
	 * Invokes an action for each element of this list.
	 * @param action the action to invoke
	 * @param <E> the exception 
	 * @throws E if the action throws
	 */
	public <E extends Exception> void forEach(
			@NonNull Action1E<? super T, E> action) throws E {
		HeadList<T> tl = this;
		while (tl != null) {
			action.call(tl.value);
			tl = tl.head;
		}
	}
	/**
	 * Invokes a function for each element.
	 * The function should return true to continue.
	 * @param func the function to invoke, returns true to continue
	 */
	public void forEach(@NonNull Func1<? super T, Boolean> func) {
		HeadList<T> tl = this;
		while (tl != null) {
			if (!func.call(tl.value)) {
				return;
			}
			tl = tl.head;
		}
	}
	/**
	 * Invokes a function for each element.
	 * The function should return true to continue.
	 * @param func the function to invoke, returns true to continue
	 * @param <E> the exception 
	 * @throws E if the action throws
	 */
	public <E extends Exception> void forEach(
			@NonNull Func1E<? super T, Boolean, E> func) throws E {
		HeadList<T> tl = this;
		while (tl != null) {
			if (!func.call(tl.value)) {
				return;
			}
			tl = tl.head;
		}
	}
	/**
	 * Returns a list without the elements
	 * found in src.
	 * @param src the sequence to remove
	 * @return the new list
	 */
	public HeadList<T> removeAll(final Iterable<?> src) {
		if (src instanceof Set<?>) {
			final Set<?> set = (Set<?>)src;
			return removeIf(new Func1<T, Boolean>() {
				@Override
				public Boolean call(T v) {
					return set.contains(v);
				}
			});
		}
		return removeIf(new Func1<T, Boolean>() {
			@Override
			public Boolean call(T v) {
				for (Object o : src) {
					if (Objects.equals(v, o)) {
						return true;
					}
				}
				return false;
			}
		});
	}
	/**
	 * Returns a list without the elements
	 * found in src.
	 * @param src the sequence to remove
	 * @return the new list
	 */
	public HeadList<T> retainAll(final Iterable<?> src) {
		if (src instanceof Set<?>) {
			final Set<?> set = (Set<?>)src;
			return removeIf(new Func1<T, Boolean>() {
				@Override
				public Boolean call(T v) {
					return !set.contains(v);
				}
			});
		}
		return removeIf(new Func1<T, Boolean>() {
			@Override
			public Boolean call(T v) {
				for (Object o : src) {
					if (Objects.equals(v, o)) {
						return false;
					}
				}
				return true;
			}
		});
	}
	/**
	 * Remove the first instance from the list.
	 * @param o the object to remove
	 * @return the new list without the object
	 */
	public HeadList<T> remove(final Object o) {
		return removeIf(new Func1<T, Boolean>() {
			/** Remove one element only. */
			boolean once = true;
			@Override
			public Boolean call(T param1) {
				if (once && Objects.equals(param1, o)) {
					once = false;
					return true;
				}
				return false;
			}
		});
	}
	/**
	 * Returns a new sublist starting from the given index.
	 * @param startIndex the start index
	 * @return the sublist or null if the start index is beyond the list size
	 */
	public HeadList<T> subList(int startIndex) {
		if (startIndex < 0) {
			throw new IllegalArgumentException("startIndex < 0");
		}
		int s = size();
		HeadList<T> hl = this;
		HeadList<T> h = null;
		HeadList<T> r = null;
		while (s > startIndex) {
			if (h == null) {
				h = new HeadList<>(hl.value);
				r = h;
			} else {
				h.head = new HeadList<>(hl.value);
				h = h.head;
			}
			
			hl = hl.head;
			s--;
		}
		
		return r;
	}
	/**
	 * Returns a new sublist of this list from between
	 * the given indexes.
	 * <p>If the endIndex points beyond the size, only up
	 * to the size is used.</p>
	 * @param startIndex the start index, inclusive
	 * @param endIndex the end index exclusive
	 * @return the new head list or null if the range was empty
	 */
	public HeadList<T> subList(int startIndex, int endIndex) {
		if (startIndex < 0) {
			throw new IllegalArgumentException("startIndex < 0");
		}
		if (endIndex < 0) {
			throw new IllegalArgumentException("endIndex < 0");
		}
		if (startIndex > endIndex) {
			throw new IllegalArgumentException("endIndex < startIndex");
		}
		int s = size();
		if (startIndex == 0 && endIndex == s) {
			return this;
		}
		endIndex = Math.min(endIndex, s);
		HeadList<T> hl = this;
		while (s > endIndex) {
			hl = hl.head;
			s--;
		}
		HeadList<T> h = null;
		HeadList<T> r = null;
		while (endIndex > startIndex) {
			if (h == null) {
				h = new HeadList<>(hl.value);
				r = h;
			} else {
				h.head = new HeadList<>(hl.value);
				h = h.head;
			}
			
			hl = hl.head;
			endIndex--;
		}
		
		return r;
	}
}
