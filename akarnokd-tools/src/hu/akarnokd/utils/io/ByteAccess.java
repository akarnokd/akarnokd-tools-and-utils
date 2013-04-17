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

package hu.akarnokd.utils.io;

/**
 * Base inteface to get and set primitive values at
 * a specified index location.
 * @author akarnokd, 2013.04.17.
 */
public interface ByteAccess {
	/**
	 * Sets a value at the specified index.
	 * @param index the index
	 * @param value the value
	 */
	void set(int index, byte value);
	/**
	 * Sets a value at the specified index.
	 * @param index the index
	 * @param value the value
	 */
	void set(int index, short value);
	/**
	 * Sets a value at the specified index.
	 * @param index the index
	 * @param value the value
	 */
	void set(int index, int value);
	/**
	 * Sets a value at the specified index.
	 * @param index the index
	 * @param value the value
	 */
	void set(int index, long value);
	/**
	 * Sets a value at the specified index.
	 * @param index the index
	 * @param value the value
	 */
	void set(int index, float value);
	/**
	 * Sets a value at the specified index.
	 * @param index the index
	 * @param value the value
	 */
	void set(int index, double value);
	/**
	 * Sets a value at the specified index.
	 * @param index the index
	 * @param value the value
	 */
	void set(int index, char value);
	/**
	 * Retrieve a value from the specified index.
	 * @param index the index
	 * @return the value
	 */
	byte getByte(int index);
	/**
	 * Retrieve a value from the specified index.
	 * @param index the index
	 * @return the value
	 */
	int getUnsignedByte(int index);
	/**
	 * Retrieve a value from the specified index.
	 * @param index the index
	 * @return the value
	 */
	short getShort(int index);
	/**
	 * Retrieve a value from the specified index.
	 * @param index the index
	 * @return the value
	 */
	int getUnsignedShort(int index);
	/**
	 * Retrieve a value from the specified index.
	 * @param index the index
	 * @return the value
	 */
	int getInt(int index);
	/**
	 * Retrieve a value from the specified index.
	 * @param index the index
	 * @return the value
	 */
	long getUnsignedInt(int index);
	/**
	 * Retrieve a value from the specified index.
	 * @param index the index
	 * @return the value
	 */
	long getLong(int index);
	/**
	 * Retrieve a value from the specified index.
	 * @param index the index
	 * @return the value
	 */
	float getFloat(int index);
	/**
	 * Retrieve a value from the specified index.
	 * @param index the index
	 * @return the value
	 */
	double getDouble(int index);
	/**
	 * Retrieve a value from the specified index.
	 * @param index the index
	 * @return the value
	 */
	char getChar(int index);
}
