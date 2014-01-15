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

/**
 * Base interface for accessing primitive values in a key-value store.
 * @author akarnokd, 2013.05.27.
 */
public interface ParameterMap {
	/**
	 * Returns a boolean value or throws a NoSuchElementException.
	 * @param key the key
	 * @return the value
	 */
	boolean getBoolean(String key);
	/**
	 * Returns a boolean value or the default value.
	 * @param key the key
	 * @param defaultValue the default value
	 * @return the value
	 */
	boolean getBoolean(String key, boolean defaultValue);
	/**
	 * Returns a byte value or throws a NoSuchElementException.
	 * <p>Might throw a NumberFormatException if the entry can't be parsed.</p>
	 * @param key the key
	 * @return the value
	 */
	byte getByte(String key);
	/**
	 * Returns a byte value or the default value.
	 * <p>Might throw a NumberFormatException if the entry can't be parsed.</p>
	 * @param key the key
	 * @param defaultValue the default value
	 * @return the value
	 */
	byte getByte(String key, byte defaultValue);
	/**
	 * Returns a short value or throws a NoSuchElementException.
	 * <p>Might throw a NumberFormatException if the entry can't be parsed.</p>
	 * @param key the key
	 * @return the value
	 */
	short getShort(String key);
	/**
	 * Returns a short value or the default value.
	 * <p>Might throw a NumberFormatException if the entry can't be parsed.</p>
	 * @param key the key
	 * @param defaultValue the default value
	 * @return the value
	 */
	short getShort(String key, short defaultValue);
	/**
	 * Returns a int value or throws a NoSuchElementException.
	 * <p>Might throw a NumberFormatException if the entry can't be parsed.</p>
	 * @param key the key
	 * @return the value
	 */
	int getInt(String key);
	/**
	 * Returns a int value or the default value.
	 * <p>Might throw a NumberFormatException if the entry can't be parsed.</p>
	 * @param key the key
	 * @param defaultValue the default value
	 * @return the value
	 */
	int getInt(String key, int defaultValue);
	/**
	 * Returns a long value or throws a NoSuchElementException.
	 * <p>Might throw a NumberFormatException if the entry can't be parsed.</p>
	 * @param key the key
	 * @return the value
	 */
	long getLong(String key);
	/**
	 * Returns a long value or the default value.
	 * <p>Might throw a NumberFormatException if the entry can't be parsed.</p>
	 * @param key the key
	 * @param defaultValue the default value
	 * @return the value
	 */
	long getLong(String key, long defaultValue);
	/**
	 * Returns a float value or throws a NoSuchElementException.
	 * <p>Might throw a NumberFormatException if the entry can't be parsed.</p>
	 * @param key the key
	 * @return the value
	 */
	float getFloat(String key);
	/**
	 * Returns a float value or the default value.
	 * <p>Might throw a NumberFormatException if the entry can't be parsed.</p>
	 * @param key the key
	 * @param defaultValue the default value
	 * @return the value
	 */
	float getFloat(String key, float defaultValue);
	/**
	 * Returns a double value or throws a NoSuchElementException.
	 * <p>Might throw a NumberFormatException if the entry can't be parsed.</p>
	 * @param key the key
	 * @return the value
	 */
	double getDouble(String key);
	/**
	 * Returns a double value or the default value.
	 * <p>Might throw a NumberFormatException if the entry can't be parsed.</p>
	 * @param key the key
	 * @param defaultValue the default value
	 * @return the value
	 */
	double getDouble(String key, double defaultValue);
	/**
	 * Returns a char value or throws a NoSuchElementException.
	 * <p>Might throw a NumberFormatException if the entry can't be parsed.</p>
	 * @param key the key
	 * @return the value
	 */
	char getChar(String key);
	/**
	 * Returns a char value or the default value.
	 * <p>Might throw a NumberFormatException if the entry can't be parsed.</p>
	 * @param key the key
	 * @param defaultValue the default value
	 * @return the value
	 */
	char getChar(String key, char defaultValue);
	/**
	 * Returns a String value or throws a NoSuchElementException.
	 * <p>Might throw a NumberFormatException if the entry can't be parsed.</p>
	 * @param key the key
	 * @return the value
	 */
	String get(String key);
	/**
	 * Returns a String value or the default value.
	 * <p>Might throw a NumberFormatException if the entry can't be parsed.</p>
	 * @param key the key
	 * @param defaultValue the default value
	 * @return the value
	 */
	String get(String key, String defaultValue);
	/**
	 * Returns an enum value from the given enum class where the
	 * enum name is the string expected in the entry
	 * or throws NoSuchElementException.
	 * @param <E> the enum type
	 * @param key the key
	 * @param enumClass the enum class
	 * @return the enum
	 */
	<E extends Enum<E>> E getEnum(String key, Class<E> enumClass);
	/**
	 * Returns an enum value from the given enum class where the
	 * enum name is the string expected in the entry
	 * or returns the default value.
	 * @param <E> the enum type
	 * @param key the key
	 * @param enumClass the enum class
	 * @param defaultValue default value
	 * @return the enum
	 */
	<E extends Enum<E>> E getEnum(String key, Class<E> enumClass, E defaultValue);
	/**
	 * Returns an enum value from the given enum class where the
	 * enum name is the string expected in the entry
	 * or throws NoSuchElementException.
	 * @param <E> the enum type
	 * @param key the key
	 * @param universe the available enumerations
	 * @return the enum
	 */
	<E extends Enum<E>> E getEnum(String key, E[] universe);
	/**
	 * Returns an enum value from the given enum class where the
	 * enum name is the string expected in the entry
	 * or throws NoSuchElementException.
	 * @param <E> the enum type
	 * @param key the key
	 * @param universe the available enumerations
	 * @param defaultValue default value
	 * @return the enum
	 */
	<E extends Enum<E>> E getEnum(String key, E[] universe, E defaultValue);
	/**
	 * Test if the given key exists.
	 * @param key the key
	 * @return true if exists
	 */
	boolean has(String key);
}
