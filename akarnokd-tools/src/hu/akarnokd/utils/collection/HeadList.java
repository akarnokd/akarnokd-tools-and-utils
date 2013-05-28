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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * An immutable collection structure which references
 * another instance as the "head" of the list.
 * @author akarnokd, 2013.05.28.
 * @see TailList
 * @param <T> the element type
 */
public final class HeadList<T> {
	/** The head of the list. */
	protected final HeadList<T> head;
	/** The value. */
	protected final T value;
	/**
	 * Constructor with only a value.
	 * @param value the list value
	 */
	public HeadList(T value) {
		this.head = null;
		this.value = value;
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
	 * Creates a new headlist with the given new
	 * element and this list as the head.
	 * @param value the value to add
	 * @return the new headlist
	 */
	public HeadList<T> add(T value) {
		return new HeadList<>(this, value);
	}
	/**
	 * Returns the head list, which can be null.
	 * @return the head list
	 */
	public HeadList<T> head() {
		return head;
	}
	/**
	 * Returns the last value of this list.
	 * @return the last value
	 */
	public T value() {
		return value;
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
	 * Returns an iterable which traverses this list in reverse direction.
	 * @return the iterable
	 */
	public Iterable<T> reverseIterable() {
		return new Iterable<T>() {
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
		};
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
	/**
	 * Check if the object is in this list.
	 * @param value the value to look for
	 * @return true if in this list
	 */
	public boolean contains(Object value) {
		return find(value) != null;
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
	 * Creates a head list from the given sequence of values.
	 * <p>The source sequence should contain at least
	 * one item or this method throws a <code>NoSuchElementException</code>.
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
		throw new NoSuchElementException();
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
}
