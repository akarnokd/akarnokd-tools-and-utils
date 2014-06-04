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

package hu.akarnokd.utils.xml;

import java.util.List;

import rx.Observable;
import rx.functions.Func0;
import rx.functions.Func1;

import com.google.common.collect.Lists;


/**
 * Utility classes to handle XNSerializable object creation and conversion.
 * @author akarnokd, 2011.09.29.
 */
public final class XNSerializables {
	/**
	 * Utility class.
	 */
	private XNSerializables() {
	}
	/**
	 * Create a request with the given function and name-value pairs.
	 * @param function the remote function name
	 * @param nameValue the array of String name and Object attributes.
	 * @return the request XML
	 */
	public static XNElement createRequest(String function, Object... nameValue) {
		XNElement result = new XNElement(function);
		for (int i = 0; i < nameValue.length; i += 2) {
			result.set((String)nameValue[i], nameValue[i + 1]);
		}
		return result;
	}
	/**
	 * Create an update request and store the contents of the object into it.
	 * @param function the remote function name
	 * @param object the object to store.
	 * @return the request XML
	 */
	public static XNElement createUpdate(String function, XNSerializable object) {
		XNElement result = new XNElement(function);
		object.save(result);
		return result;
	}
	/**
	 * Parses an container for the given itemName elements and loads them into the
	 * given Java XNSerializable object.
	 * @param <T> the element object type
	 * @param container the container XNElement
	 * @param itemName the item name
	 * @param creator the function to create Ts
	 * @return the list of elements
	 */
	public static <T extends XNSerializable> List<T> parseList(XNElement container, 
			String itemName, Func0<T> creator) {
		List<T> result = Lists.newArrayList();
		for (XNElement e : container.childrenWithName(itemName)) {
			T obj = creator.call();
			obj.load(e);
			result.add(obj);
		}
		return result;
	}
	/**
	 * Create an XNSerializable object through the {@code creator} function
	 * and load it from the {@code item}.
	 * @param <T> the XNSerializable object
	 * @param item the item to load from
	 * @param creator the function to create Ts
	 * @return the created and loaded object
	 */
	public static <T extends XNSerializable> T parseItem(XNElement item, Func0<T> creator) {
		T result = creator.call();
		result.load(item);
		return result;
	}
	/**
	 * Create an XNElement with the given name and items stored from the source sequence.
	 * @param container the container name
	 * @param item the item name
	 * @param source the source of items
	 * @return the list in XNElement
	 */
	public static XNElement storeList(String container, String item, Iterable<? extends XNSerializable> source) {
		XNElement result = new XNElement(container);
		for (XNSerializable e : source) {
			e.save(result.add(item));
		}
		return result;
	}
	/**
	 * Store the value of a single serializable object with the given element name.
	 * @param itemName the item element name
	 * @param source the object to store
	 * @return the created XNElement
	 */
	public static XNElement storeItem(String itemName, XNSerializable source) {
		return createUpdate(itemName, source);
	}
	/**
	 * Convert a sequence of XNElements into an XNSerializable object instance via help of the {@code creator} function.
	 * @param <T> the object type
	 * @param source the source stream of XNElements
	 * @param creator the function to create the XNSerializable object
	 * @return the sequence of XNSerializable objects
	 */
	public static <T extends XNSerializable> Observable<T> parse(Observable<XNElement> source, final Func0<T> creator) {
		return source.map(new Func1<XNElement, T>() {
			@Override
			public T call(XNElement param1) {
				T result = creator.call();
				result.load(param1);
				return result;
			}
		});
	}
	/**
	 * Convert the sequence of XNSerializable objects into XNElements.
	 * @param <T> the object type
	 * @param source the source of XNSerializable objects
	 * @param elementName the element name to use for the generated XNElements
	 * @param namespace the target namespace
	 * @return the sequence of XNElements
	 */
	public static <T extends XNSerializable> Observable<XNElement> serialize(
			Observable<T> source, 
			final String elementName, final String namespace) {
		return source.map(new Func1<T, XNElement>() {
			@Override
			public XNElement call(T param1) {
				XNElement e = new XNElement(elementName);
				e.namespace = namespace;
				param1.save(e);
				return e;
			}
		});
	}
	/**
	 * Convert the sequence of XNSerializable objects into XNElements.
	 * @param <T> the object type
	 * @param source the source of XNSerializable objects
	 * @param elementName the element name to use for the generated XNElements
	 * @return the sequence of XNElements
	 */
	public static <T extends XNSerializable> Observable<XNElement> serialize(
			Observable<T> source, 
			final String elementName) {
		return source.map(new Func1<T, XNElement>() {
			@Override
			public XNElement call(T param1) {
				XNElement e = new XNElement(elementName);
				param1.save(e);
				return e;
			}
		});
	}
	/**
	 * Parse a sequence of a sequence of XNElements (e.g., list of XNElements) into a sequence.
	 * of list of an XNSerializable objects
	 * @param <T> the object type
	 * @param source the source sequence
	 * @param creator the object creator
	 * @return the parsed sequence
	 */
	public static <T extends XNSerializable> Observable<List<T>> parseList(
			Observable<? extends Iterable<XNElement>> source, 
			final Func0<T> creator) {
		return source.map(new Func1<Iterable<XNElement>, List<T>>() {
			@Override
			public List<T> call(Iterable<XNElement> param1) {
				List<T> result = Lists.newArrayList();
				for (XNElement e : param1) {
					T obj = creator.call();
					obj.load(e);
					result.add(obj);
				}
				return result;
			}
		});
	}
	/**
	 * Serialize a sequence of sequence of XNSerializable objects into a sequence of list
	 * of XNElements.
	 * @param <T> the object type
	 * @param source the source sequence
	 * @param elementName the element name 
	 * @return the sequence of list of XNElements
	 */
	public static <T extends XNSerializable> Observable<List<XNElement>> serializeList(
			Observable<? extends Iterable<T>> source,
			final String elementName
			) {
		return source.map(new Func1<Iterable<T>, List<XNElement>>() {
			@Override
			public List<XNElement> call(Iterable<T> param1) {
				List<XNElement> result = Lists.newArrayList();
				for (T e : param1) {
					XNElement x = new XNElement(elementName);
					e.save(x);
					result.add(x);
				}
				return result;
			}
		});
	}
	/**
	 * Serialize a sequence of sequence of XNSerializable objects into a sequence of list
	 * of XNElements.
	 * @param <T> the object type
	 * @param source the source sequence
	 * @param elementName the element name 
	 * @param namespace the element namespace
	 * @return the sequence of list of XNElements
	 */
	public static <T extends XNSerializable> Observable<List<XNElement>> serializeList(
			Observable<? extends Iterable<T>> source,
			final String elementName,
			final String namespace
			) {
		return source.map(new Func1<Iterable<T>, List<XNElement>>() {
			@Override
			public List<XNElement> call(Iterable<T> param1) {
				List<XNElement> result = Lists.newArrayList();
				for (T e : param1) {
					XNElement x = new XNElement(elementName);
					x.namespace = namespace;
					e.save(x);
					result.add(x);
				}
				return result;
			}
		});
	}
}
