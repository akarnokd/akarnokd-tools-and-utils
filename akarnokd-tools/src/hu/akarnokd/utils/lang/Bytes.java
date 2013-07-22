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

package hu.akarnokd.utils.lang;

/**
 * Utility class to construct primitives from bytes and vice versa.
 * @author akarnokd, 2013.07.22.
 */
public final class Bytes {
	/** Utility class. */
	private Bytes() { }
	/**
	 * Create a short value from the most significant and
	 * least significant bytes.
	 * @param b1 the most significant byte
	 * @param b0 the least significant byte
	 * @return the short
	 */
	public static short makeShort(byte b1, byte b0) {
		return (short)(((b1 & 0xFF) << 8) | (b0 & 0xFF));
	}
	/**
	 * Create an int value from 4 bytes.
	 * @param b3 the most significant byte
	 * @param b2 byte
	 * @param b1 byte
	 * @param b0 the least significant byte
	 * @return the int
	 */
	public static int makeInt(byte b3, byte b2, byte b1, byte b0) {
		return (((b3 & 0xFF) << 24)
				| ((b2 & 0xFF) << 16)
				| ((b1 & 0xFF) << 8)
				| ((b0 & 0xFF))
		);
	}
	/**
	 * Creates an int from two shorts.
	 * @param s1 the most significant short
	 * @param s0 the least significant short
	 * @return the int
	 */
	public static int makeInt(short s1, short s0) {
		return ((s1 & 0xFFFF) << 16) | (s0 & 0xFFFF);
	}
	/**
	 * Create a long value from 8 bytes.
	 * @param b7 the most significant byte
	 * @param b6 byte
	 * @param b5 byte
	 * @param b4 byte
	 * @param b3 byte
	 * @param b2 byte
	 * @param b1 byte
	 * @param b0 the least significant byte
	 * @return the long
	 */
	public static long makeLong(byte b7, byte b6, byte b5, byte b4, byte b3, byte b2, byte b1, byte b0) {
		return (
				((b7 & 0xFFL) << 56)
				| ((b6 & 0xFFL) << 48)
				| ((b5 & 0xFFL) << 40)
				| ((b4 & 0xFFL) << 32)
				| ((b3 & 0xFFL) << 24)
				| ((b2 & 0xFFL) << 16)
				| ((b1 & 0xFFL) << 8)
				| ((b0 & 0xFFL))
		);
	}
}
