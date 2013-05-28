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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Objects;

/**
 * An immutable collection of values which references
 * another instance as the "tail".
 * @author akarnokd, 2013.05.28.
 * @param <T> the element type
 */
public final class TailList<T> {
	/** The contained value. */
	protected final T value;
	/** Tail of the list. */
	protected final TailList<T> tail;
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
	 * The head value of this list.
	 * @return the head value 
	 */
	public T value() {
		return value;
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
			h = h * 31 + (tl.value != null ? tl.hashCode() : 0);
			tl = tl.tail;
		}
		return h;
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
	 * Creates a new list with the contents of
	 * this list and the value as last element.
	 * <p>Note that this creates a full
	 * copy of this list since the tail
	 * reference is also immutable, which makes this a very expensive call.</p>
	 * @param value the value to add to the end
	 * @return the new list
	 */
	public TailList<T> add(T value) {
		LinkedList<T> elements = toLinkedList();
		
		TailList<T> tl = new TailList<>(value);
		Iterator<T> e = elements.descendingIterator();
		while (e.hasNext()) {
			tl = new TailList<>(e.next(), tl);
		}
		return tl;
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
	 * Turns this tail list to a regular linked list.
	 * @return the linked list
	 */
	public LinkedList<T> toLinkedList() {
		LinkedList<T> r = new LinkedList<>();
		addTo(r);
		return r;
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
	
}
