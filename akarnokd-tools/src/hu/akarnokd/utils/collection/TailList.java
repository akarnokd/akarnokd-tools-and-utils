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

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

import rx.functions.Action1;
import rx.functions.Func1;
import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * An immutable collection of values which references
 * another instance as the "tail".
 * @author akarnokd, 2013.05.28.
 * @param <T> the element type
 */
public final class TailList<T> implements Iterable<T> {
	/** The contained value. */
	private final T value;
	/** Tail of the list. */
	private TailList<T> tail;
	/**
	 * Builds a tail list from the source sequence.
	 * <p>The sequence should contain at least one item.</p>
	 * @param src the source sequence
	 * @return the tail list
	 * @param <T> the element type
	 */
	public static <T> TailList<T> from(Iterable<? extends T> src) {
		Iterator<? extends T> it = src.iterator();
		if (it.hasNext()) {
			T t = it.next();
			TailList<T> tl = new TailList<>(t);
			TailList<T> r = tl;
			
			while (it.hasNext()) {
				t = it.next();
				tl.tail = new TailList<>(t);
				tl = tl.tail;
			}
			
			return r;
		}
		throw new IllegalArgumentException("Empty source");
	}
	/**
	 * Creates a tail list from the given array of Ts.
	 * <p>The sequence should contain at least one item.</p>
	 * @param src the source of Ts
	 * @return the head list
	 * @param <T> the element type
	 */
	@SafeVarargs
	public static <T> TailList<T> from(T... src) {
		if (src.length > 0) {
			TailList<T> h = new TailList<>(src[0]);
			TailList<T> r = h;
			for (int i = 1; i < src.length; i++) {
				h.tail = new TailList<>(src[i]);
				h = h.tail;
			}
			return r;
		}
		throw new IllegalArgumentException("Empty source");
	}
	/**
	 * Constructor with just a value.
	 * @param value the value
	 */
	public TailList(T value) {
		this.value = value;
		this.tail = null;
	}
	/**
	 * Constructor with value and tail list.
	 * @param value the value
	 * @param tail the tail list
	 */
	public TailList(T value, TailList<T> tail) {
		this.value = value;
		this.tail = tail;
	}
	/**
	 * Creates a new list with the contents of
	 * this list and the value as last element.
	 * <p>Note that this creates a full
	 * copy of this list since the tail
	 * reference is also immutable, which makes this a very expensive call.</p>
	 * @param value the value to add to the end
	 * @return the new list
	 */
	public TailList<T> add(T value) {
		TailList<T> tl = new TailList<>(this.value);
		TailList<T> r = tl;
		TailList<T> tl0 = tail;
		while (tl0 != null) {
			tl.tail = new TailList<>(tl0.value);
			tl = tl.tail;
			tl0 = tl0.tail;
		}
		tl.tail = new TailList<>(value);
		
		return r;
	}
	/**
	 * Add all elements from the source sequence.
	 * @param src the source sequence.
	 * @return the new tail list chain
	 */
	public TailList<T> addAll(Iterable<? extends T> src) {
		TailList<T> tl = new TailList<>(this.value);
		TailList<T> r = tl;
		TailList<T> tl0 = tail;
		while (tl0 != null) {
			tl.tail = new TailList<>(tl0.value);
			tl = tl.tail;
			tl0 = tl0.tail;
		}
		for (T t : src) {
			tl.tail = new TailList<>(t);
			tl = tl.tail;
		}

		return r;
	}
	/**
	 * Add all elements from the source sequence.
	 * @param src the source sequence.
	 * @return the new tail list chain
	 */
	public TailList<T> addAll(@SuppressWarnings("unchecked") T... src) {
		TailList<T> tl = new TailList<>(this.value);
		TailList<T> r = tl;
		TailList<T> tl0 = tail;
		while (tl0 != null) {
			tl.tail = new TailList<>(tl0.value);
			tl = tl.tail;
			tl0 = tl0.tail;
		}
		for (T t : src) {
			tl.tail = new TailList<>(t);
			tl = tl.tail;
		}

		return r;
	}
	/**
	 * Adds a first element to the list
	 * and returns this new list.
	 * @param value the value to start the new
	 * list with
	 * @return the new tail list
	 */
	public TailList<T> addFirst(T value) {
		return new TailList<>(value, this);
	}
	/**
	 * Adds the contents of the list to the given collection.
	 * @param coll the output collection
	 */
	public void addTo(Collection<? super T> coll) {
		TailList<T> tl = this;
		while (tl != null) {
			coll.add(tl.value);
			tl = tl.tail;
		}
	}
	/**
	 * Check if the given value is in this list.
	 * @param o the object to test
	 * @return true if in this list
	 */
	public boolean contains(Object o) {
		return find(o) != null;
	}
	/**
	 * Checks if all elements of the given source sequence
	 * is present in this list.
	 * @param src the source sequence
	 * @return true if all elements is contained
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
	 * Find a value in this list and
	 * return the tail list starting from that
	 * point.
	 * @param o the object to find
	 * @return the tail list where it was first found
	 * or null if not found
	 */
	public TailList<T> find(Object o) {
		TailList<T> tl = this;
		while (tl != null) {
			if (Objects.equals(tl.value, o)) {
				return tl;
			}
			tl = tl.tail;
		}
		return null;
	}
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (obj.getClass() != getClass()) {
			return false;
		}
		TailList<?> tl = (TailList<?>)obj;
		TailList<?> tl0 = this;
		
		while (tl != null && tl0 != null) {
			if (!Objects.equals(tl.value, tl0.value)) {
				return false;
			}
		}
		return tl == tl0;
	}
	@Override
	public int hashCode() {
		int h = 17;
		TailList<T> tl = this;
		while (tl != null) {
			h = h * 31 + (tl.value != null ? tl.value.hashCode() : 0);
			tl = tl.tail;
		}
		return h;
	}
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			/** The current element. */
			TailList<T> current = TailList.this;
			@Override
			public boolean hasNext() {
				return current != null;
			}
			@Override
			public T next() {
				if (hasNext()) {
					T v = current.value;
					current = current.tail;
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
	 * @return the number of element
	 */
	public int size() {
		int i = 1;
		TailList<T> t = tail;
		while (t != null) {
			i++;
			t = t.tail;
		}
		return i;
	}
	/**
	 * Returns the tail list or null if this is the last
	 * element of the list.
	 * @return the tail list or null
	 */
	public TailList<T> tail() {
		return tail;
	}
	/**
	 * Converts this list into an object array.
	 * @return the object array
	 */
	public Object[] toArray() {
		Object[] r = new Object[size()];
		int i = 0;
		TailList<T> tl = this;
		while (tl != null) {
			r[i] = tl.value;
			i++;
			tl = tl.tail;
		}
		
		return r;
	}
	/**
	 * Put the values of this list into the target array,
	 * if it has enough room or create a new array.
	 * @param array the array to put data into
	 * @return the array or a created new one
	 */
	@SuppressWarnings("unchecked")
	public T[] toArray(T[] array) {
		int s = size();
		if (s > array.length) {
			array = (T[])Array.newInstance(array.getClass().getComponentType(), s);
		}
		int i = 0;
		TailList<T> tl = this;
		while (tl != null) {
			array[i] = tl.value;
			i++;
			tl = tl.tail;
		}
		return array;
	}
	/**
	 * Turns this tail list to a regular array list.
	 * @return the linked list
	 */
	public ArrayList<T> toArrayList() {
		ArrayList<T> r = new ArrayList<>();
		addTo(r);
		return r;
	}
	/**
	 * Convert this tail list into a head list.
	 * @return the head list
	 */
	public HeadList<T> toHeadList() {
		HeadList<T> hl = new HeadList<>(value);
		TailList<T> tl = tail;
		while (tl != null) {
			hl = hl.add(tl.value);
			tl = tl.tail;
		}
		return hl;
	}
	/**
	 * Turns this tail list to a regular linked list.
	 * @return the linked list
	 */
	public LinkedList<T> toLinkedList() {
		LinkedList<T> r = new LinkedList<>();
		addTo(r);
		return r;
	}
	/** 
	 * The head value of this list.
	 * @return the head value 
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
	public TailList<T> removeIf(Func1<? super T, Boolean> predicate) {
		TailList<T> r = null;
		TailList<T> tl = null;
		
		TailList<T> tl0 = this;
		while (tl0 != null) {
			if (!predicate.call(tl0.value)) {
				if (tl == null) {
					tl = new TailList<>(tl0.value);
					r = tl;
				} else {
					tl.tail = new TailList<>(tl0.value);
					tl = tl.tail;
				}
			}
			tl0 = tl0.tail;
		}
		
		
		return r;
	}
	@Override
	public String toString() {
		StringBuilder b = new StringBuilder();
		TailList<T> tl = this;
		while (tl != null) {
			if (tl != this) {
				b.append(',').append(' ');
			}
			b.append(tl.value);
			tl = tl.tail;
		}
		
		return b.toString();
	}
	/**
	 * Invokes an action for each element of this list.
	 * @param action the action to invoke
	 */
	public void forEach(@NonNull Action1<? super T> action) {
		TailList<T> tl = this;
		while (tl != null) {
			action.call(tl.value);
			tl = tl.tail;
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
		TailList<T> tl = this;
		while (tl != null) {
			action.call(tl.value);
			tl = tl.tail;
		}
	}
	/**
	 * Invokes a function for each element.
	 * The function should return true to continue.
	 * @param func the function to invoke, returns true to continue
	 */
	public void forEach(@NonNull Func1<? super T, Boolean> func) {
		TailList<T> tl = this;
		while (tl != null) {
			if (!func.call(tl.value)) {
				return;
			}
			tl = tl.tail;
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
		TailList<T> tl = this;
		while (tl != null) {
			if (!func.call(tl.value)) {
				return;
			}
			tl = tl.tail;
		}
	}
	/**
	 * Returns a list without the elements
	 * found in src.
	 * @param src the sequence to remove
	 * @return the new list
	 */
	public TailList<T> removeAll(final Iterable<?> src) {
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
	public TailList<T> retainAll(final Iterable<?> src) {
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
	public TailList<T> remove(final Object o) {
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
	 * Returns a sublist starting from the given index
	 * to the end of the list.
	 * @param fromIndex the index to start from
	 * @return the sublist or null if index greater or equal to the list size
	 */
	public TailList<T> subList(int fromIndex) {
		TailList<T> tl = this;
		while (fromIndex > 0 && tl != null) {
			fromIndex--;
			tl = tl.tail;
		}
		return tl;
	}
	/**
	 * Returns a new list with the contents of the index range
	 * from this list.
	 * <p>If there are fewer elements than toIndex, only that
	 * much elements are returned.</p>
	 * @param fromIndex the index to start from
	 * @param toIndex the index to end, exclusive
	 * @return the new sublist or null if the range was empty.
	 */
	public TailList<T> subList(int fromIndex, int toIndex) {
		TailList<T> sl = subList(fromIndex);
		int count = toIndex - fromIndex;
		TailList<T> r = null;
		TailList<T> t = null;
		
		while (count > 0 && sl != null) {
			if (t == null) {
				t = new TailList<>(sl.value);
				r = t;
			} else {
				t.tail = new TailList<>(sl.value);
				t = t.tail;
			}
			sl = sl.tail;
			count--;
		}
		
		return r;
	}
}
