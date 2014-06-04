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
 * Utility classes to handle XSerializable object creation and conversion.
 * @author akarnokd, 2011.09.29.
 */
public final class XSerializables {
	/**
	 * Utility class.
	 */
	private XSerializables() {
	}
	/**
	 * Create a request with the given function and name-value pairs.
	 * @param function the remote function name
	 * @param nameValue the array of String name and Object attributes.
	 * @return the request XML
	 */
	public static XElement createRequest(String function, Object... nameValue) {
		XElement result = new XElement(function);
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
	public static XElement createUpdate(String function, XSerializable object) {
		XElement result = new XElement(function);
		object.save(result);
		return result;
	}
	/**
	 * Parses an container for the given itemName elements and loads them into the
	 * given Java XSerializable object.
	 * @param <T> the element object type
	 * @param container the container XElement
	 * @param itemName the item name
	 * @param creator the function to create Ts
	 * @return the list of elements
	 */
	public static <T extends XSerializable> List<T> parseList(XElement container, 
			String itemName, Func0<T> creator) {
		List<T> result = Lists.newArrayList();
		for (XElement e : container.childrenWithName(itemName)) {
			T obj = creator.call();
			obj.load(e);
			result.add(obj);
		}
		return result;
	}
	/**
	 * Create an XSerializable object through the {@code creator} function
	 * and load it from the {@code item}.
	 * @param <T> the XSerializable object
	 * @param item the item to load from
	 * @param creator the function to create Ts
	 * @return the created and loaded object
	 */
	public static <T extends XSerializable> T parseItem(XElement item, Func0<T> creator) {
		T result = creator.call();
		result.load(item);
		return result;
	}
	/**
	 * Create an XElement with the given name and items stored from the source sequence.
	 * @param container the container name
	 * @param item the item name
	 * @param source the source of items
	 * @return the list in XElement
	 */
	public static XElement storeList(String container, String item, Iterable<? extends XSerializable> source) {
		XElement result = new XElement(container);
		for (XSerializable e : source) {
			e.save(result.add(item));
		}
		return result;
	}
	/**
	 * Store the value of a single serializable object with the given element name.
	 * @param itemName the item element name
	 * @param source the object to store
	 * @return the created XElement
	 */
	public static XElement storeItem(String itemName, XSerializable source) {
		return createUpdate(itemName, source);
	}
	/**
	 * Convert a sequence of XElements into an XSerializable object instance via help of the {@code creator} function.
	 * @param <T> the object type
	 * @param source the source stream of XElements
	 * @param creator the function to create the XSerializable object
	 * @return the sequence of XSerializable objects
	 */
	public static <T extends XSerializable> Observable<T> parse(Observable<XElement> source, final Func0<T> creator) {
		return source.map(new Func1<XElement, T>() {
			@Override
			public T call(XElement param1) {
				T result = creator.call();
				result.load(param1);
				return result;
			}
		});
	}
	/**
	 * Convert the sequence of XSerializable objects into XElements.
	 * @param <T> the object type
	 * @param source the source of XSerializable objects
	 * @param elementName the element name to use for the generated XElements
	 * @return the sequence of XElements
	 */
	public static <T extends XSerializable> Observable<XElement> serialize(
			Observable<T> source, 
			final String elementName) {
		return source.map(new Func1<T, XElement>() {
			@Override
			public XElement call(T param1) {
				XElement e = new XElement(elementName);
				param1.save(e);
				return e;
			}
		});
	}
	/**
	 * Parse a sequence of a sequence of XElements (e.g., list of XElements) into a sequence.
	 * of list of an XSerializable objects
	 * @param <T> the object type
	 * @param source the source sequence
	 * @param creator the object creator
	 * @return the parsed sequence
	 */
	public static <T extends XSerializable> Observable<List<T>> parseList(
			Observable<? extends Iterable<XElement>> source, 
			final Func0<T> creator) {
		return source.map(new Func1<Iterable<XElement>, List<T>>() {
			@Override
			public List<T> call(Iterable<XElement> param1) {
				List<T> result = Lists.newArrayList();
				for (XElement e : param1) {
					T obj = creator.call();
					obj.load(e);
					result.add(obj);
				}
				return result;
			}
		});
	}
	/**
	 * Serialize a sequence of sequence of XSerializable objects into a sequence of list
	 * of XElements.
	 * @param <T> the object type
	 * @param source the source sequence
	 * @param elementName the element name 
	 * @return the sequence of list of XElements
	 */
	public static <T extends XSerializable> Observable<List<XElement>> serializeList(
			Observable<? extends Iterable<T>> source,
			final String elementName
			) {
		return source.map(new Func1<Iterable<T>, List<XElement>>() {
			@Override
			public List<XElement> call(Iterable<T> param1) {
				List<XElement> result = Lists.newArrayList();
				for (T e : param1) {
					XElement x = new XElement(elementName);
					e.save(x);
					result.add(x);
				}
				return result;
			}
		});
	}
}
