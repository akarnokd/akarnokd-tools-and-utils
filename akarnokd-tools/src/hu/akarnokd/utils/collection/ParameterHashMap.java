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

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * A map which stores key-value pairs and offers convenience primitive
 * accessor methods to the contents.
 * @author akarnokd, 2013.05.27.
 */
public class ParameterHashMap implements Map<String, String>, ParameterMap {
	/** The backing map. */
	protected final Map<String, String> map;
	/**
	 * Default constructor.
	 */
	public ParameterHashMap() {
		map = new LinkedHashMap<>();
	}
	/**
	 * Copy constructor.
	 * @param otherMap the other map to copy
	 */
	public ParameterHashMap(Map<String, String> otherMap) {
		map = new LinkedHashMap<>(otherMap);
	}
	/**
	 * Constructor with initial capacity.
	 * @param capacity the capacity
	 */
	public ParameterHashMap(int capacity) {
		map = new LinkedHashMap<>(capacity);
	}
	@Override
	public void clear() {
		map.clear();
	}
	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}
	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}
	@Override
	public Set<java.util.Map.Entry<String, String>> entrySet() {
		return map.entrySet();
	}
	@Override
	public boolean equals(Object obj) {
		return map.equals(obj);
	}
	@Override
	public String get(Object key) {
		return map.get(key);
	}
	@Override
	public int hashCode() {
		return map.hashCode();
	}
	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}
	@Override
	public Set<String> keySet() {
		return map.keySet();
	}
	@Override
	public String put(String key, String value) {
		return map.put(key, value);
	}
	@Override
	public void putAll(Map<? extends String, ? extends String> m) {
		map.putAll(m);
	}
	@Override
	public String remove(Object key) {
		return map.remove(key);
	}
	@Override
	public int size() {
		return map.size();
	}
	@Override
	public Collection<String> values() {
		return map.values();
	}
	@Override
	public int getInt(String key) {
		String s = map.get(key);
		if (s != null) {
			return Integer.parseInt(s);
		}
		throw new NoSuchElementException(key);
	}
	@Override
	public int getInt(String key, int defaultValue) {
		String s = map.get(key);
		if (s != null) {
			return Integer.parseInt(s);
		}
		return defaultValue;
	}
	@Override
	public long getLong(String key) {
		String s = map.get(key);
		if (s != null) {
			return Long.parseLong(s);
		}
		throw new NoSuchElementException(key);
	}
	@Override
	public long getLong(String key, long defaultValue) {
		String s = map.get(key);
		if (s != null) {
			return Long.parseLong(s);
		}
		return defaultValue;
	}
	@Override
	public double getDouble(String key) {
		String s = map.get(key);
		if (s != null) {
			return Double.parseDouble(s);
		}
		throw new NoSuchElementException(key);
	}
	@Override
	public double getDouble(String key, double defaultValue) {
		String s = map.get(key);
		if (s != null) {
			return Double.parseDouble(s);
		}
		return defaultValue;
	}
	@Override
	public boolean getBoolean(String key) {
		String s = map.get(key);
		if (s != null) {
			return "true".equals(s);
		}
		throw new NoSuchElementException(key);
	}
	@Override
	public boolean getBoolean(String key, boolean defaultValue) {
		String s = map.get(key);
		if (s != null) {
			return "true".equals(s);
		}
		return defaultValue;
	}
	@Override
	public byte getByte(String key) {
		String s = map.get(key);
		if (s != null) {
			return Byte.parseByte(s);
		}
		throw new NoSuchElementException(key);
	}
	@Override
	public byte getByte(String key, byte defaultValue) {
		String s = map.get(key);
		if (s != null) {
			return Byte.parseByte(s);
		}
		return defaultValue;
	}
	@Override
	public char getChar(String key) {
		String s = map.get(key);
		if (s != null && !s.isEmpty()) {
			return s.charAt(0);
		}
		throw new NoSuchElementException(key);
	}
	@Override
	public char getChar(String key, char defaultValue) {
		String s = map.get(key);
		if (s != null && !s.isEmpty()) {
			return s.charAt(0);
		}
		return defaultValue;
	}
	@Override
	public <E extends Enum<E>> E getEnum(String key, Class<E> enumClass) {
		String s = map.get(key);
		if (s != null) {
			E[] universe = enumClass.getEnumConstants();
			for (E c : universe) {
				if (c.name().equals(s)) {
					return c;
				}
			}
		}
		throw new NoSuchElementException(key);
	}
	@Override
	public <E extends Enum<E>> E getEnum(String key, Class<E> enumClass,
			E defaultValue) {
		String s = map.get(key);
		if (s != null) {
			E[] consts = enumClass.getEnumConstants();
			for (E c : consts) {
				if (c.name().equals(s)) {
					return c;
				}
			}
		}
		return defaultValue;
	}
	@Override
	public <E extends Enum<E>> E getEnum(String key, E[] universe) {
		String s = map.get(key);
		if (s != null) {
			for (E c : universe) {
				if (c.name().equals(s)) {
					return c;
				}
			}
		}
		throw new NoSuchElementException(key);
	}
	@Override
	public <E extends Enum<E>> E getEnum(String key, E[] universe,
			E defaultValue) {
		String s = map.get(key);
		if (s != null) {
			for (E c : universe) {
				if (c.name().equals(s)) {
					return c;
				}
			}
		}
		return defaultValue;
	}
	@Override
	public float getFloat(String key) {
		String s = map.get(key);
		if (s != null) {
			return Float.parseFloat(s);
		}
		throw new NoSuchElementException(key);
	}
	@Override
	public float getFloat(String key, float defaultValue) {
		String s = map.get(key);
		if (s != null) {
			return Float.parseFloat(s);
		}
		return defaultValue;
	}
	@Override
	public short getShort(String key) {
		String s = map.get(key);
		if (s != null) {
			return Short.parseShort(s);
		}
		throw new NoSuchElementException(key);
	}
	@Override
	public short getShort(String key, short defaultValue) {
		String s = map.get(key);
		if (s != null) {
			return Short.parseShort(s);
		}
		return defaultValue;
	}
	@Override
	public String get(String key) {
		String s = map.get(key);
		if (s != null) {
			return s;
		}
		throw new NoSuchElementException(key);
	}
	@Override
	public String get(String key, String defaultValue) {
		String s = map.get(key);
		if (s != null) {
			return s;
		}
		return defaultValue;
	}
	@Override
	public boolean has(String key) {
		return map.containsKey(key);
	}
}

