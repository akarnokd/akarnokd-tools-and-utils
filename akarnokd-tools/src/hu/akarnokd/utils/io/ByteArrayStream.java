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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import com.google.common.io.ByteStreams;

/**
 * A mutable, appendable in-memory byte array
 * with InputStream and OutputStream views.
 * @author akarnokd, 2013.04.16.
 *
 */
public class ByteArrayStream extends ByteArrayOutputStream {
	/** Construct a byte array with a default capacity. */
	public ByteArrayStream() {
		super();
	}
	/**
	 * Construct a byte array with the given initial capacity.
	 * @param capacity the capacity
	 */
	public ByteArrayStream(int capacity) {
		super(capacity);
	}
	/**
	 * Returns an input stream view of the current
	 * content of this byte array.
	 * @return the input stream
	 */
	public InputStream inputStream() {
		return new InputStream() {
			/** The current steam offset. */
			int offset;
			/** The marked offset, or -1 if no marking is set. */
			int mark = -1;
			@Override
			public int read() throws IOException {
				synchronized (ByteArrayStream.this) {
					return buf[offset++];
				}
			}
			@Override
			public int read(byte[] b, int off, int len) throws IOException {
				synchronized (ByteArrayStream.this) {
					int remaining = count - offset;
					int toRead = Math.min(len, remaining);
					System.arraycopy(buf, offset, b, off, toRead);
					offset += toRead;
					return toRead;
				}
			}
			@Override
			public int available() throws IOException {
				synchronized (ByteArrayStream.this) {
					return Math.max(0, ByteArrayStream.this.count - offset);
				}
			}
			@Override
			public void close() throws IOException {
			}
			@Override
			public void mark(int readlimit) {
				synchronized (ByteArrayStream.this) {
					mark = offset;
				}
			}
			@Override
			public long skip(long n) throws IOException {
				synchronized (ByteArrayStream.this) {
					int oldOffset = offset;
					int newOffset = Math.min(count, (int)(offset + n));
					offset = newOffset;
					return newOffset - oldOffset;
				}
			}
			@Override
			public boolean markSupported() {
				return true;
			}
			@Override
			public void reset() throws IOException {
				synchronized (ByteArrayStream.this) {
					if (mark >= 0) {
						offset = mark;
						mark = -1;
					} else {
						throw new IOException("Stream not marked");
					}
				}
			}
		};
	}
	/**
	 * Clears the underlying buffer to its initial size and sets the count to zero.
	 */
	public synchronized void clear() {
		synchronized (this) {
			buf = new byte[32];
			count = 0;
		}
	}
	/**
	 * Reads the conents of the supplied input stream.
	 * @param in the input stream to read from
	 * @throws IOException if the copy throws this exception
	 */
	public synchronized void readFrom(InputStream in) throws IOException {
		ByteStreams.copy(in, this);
	}
	/**
	 * Sets the content size to the given level,
	 * extending the underlying buffer as necessary
	 * and moving the size marker to the specified size.
	 * The method will not compact the buffer.
	 * @param newSize the new size
	 */
	public void setSize(int newSize) {
		if (newSize <= buf.length) {
			Arrays.fill(buf, count, newSize - count, (byte)0);
			count = newSize;
		} else {
			buf = Arrays.copyOf(buf, newSize);
			count = newSize;
		}
	}
	/**
	 * Compacts the underlying buffer to the current size.
	 */
	public void compact() {
		if (buf.length > count) {
			buf = Arrays.copyOf(buf, count);
		}
	}
	/**
	 * @return Retrieve a view into the array with the default java byte ordering.
	 */
	public ByteAccess byteAccess() {
		return byteAccess(false);
	}
	/**
	 * Retrieve a view into the array with the specified endianness.
	 * @param littleEndian use little endian byte format
	 * @return the byte access object
	 */
	public ByteAccess byteAccess(boolean littleEndian) {
		if (littleEndian) {
			return new ByteAccessLE();
		}
		return new ByteAccessBE();
	}
	/**
	 * Byte access implementation with big endian format. 
	 * @author akarnokd, 2013.04.17.
	 */
	class ByteAccessBE implements ByteAccess {
		@Override
		public void set(int index, byte v) {
			buf[index] = v;
		}
		@Override
		public void set(int index, short v) {
			buf[index] = (byte)((v >> 8) & 0xFF); 
			buf[index + 1] = (byte)(v & 0xFF); 
		}
		@Override
		public void set(int index, int v) {
			buf[index] = (byte)((v >> 24) & 0xFF); 
			buf[index + 1] = (byte)((v >> 16) & 0xFF); 
			buf[index + 2] = (byte)((v >> 8) & 0xFF); 
			buf[index + 3] = (byte)(v & 0xFF); 
		}
		@Override
		public void set(int index, long v) {
			buf[index] = (byte)((v >> 56) & 0xFF); 
			buf[index + 1] = (byte)((v >> 48) & 0xFF); 
			buf[index + 2] = (byte)((v >> 40) & 0xFF); 
			buf[index + 3] = (byte)((v >> 32) & 0xFF); 
			buf[index + 4] = (byte)((v >> 24) & 0xFF); 
			buf[index + 5] = (byte)((v >> 16) & 0xFF); 
			buf[index + 6] = (byte)((v >> 8) & 0xFF); 
			buf[index + 7] = (byte)(v & 0xFF); 
		}
		@Override
		public void set(int index, float v) {
			set(index, Float.floatToRawIntBits(v));
		}
		@Override
		public void set(int index, double v) {
			set(index, Double.doubleToRawLongBits(v));
		}
		@Override
		public void set(int index, char v) {
			set(index, (short)v);
		}
		@Override
		public byte getByte(int index) {
			return buf[index];
		}
		@Override
		public int getUnsignedByte(int index) {
			return getByte(index) & 0xFF;
		}
		@Override
		public short getShort(int index) {
			return (short)(((buf[index] & 0xFF) << 8) | (buf[index + 1] & 0xFF));
		}
		@Override
		public int getUnsignedShort(int index) {
			return (((buf[index] & 0xFF) << 8) | (buf[index + 1] & 0xFF));
		}
		@Override
		public int getInt(int index) {
			return (
					((buf[index] & 0xFF) << 24)
					| ((buf[index + 1] & 0xFF) << 16)
					| ((buf[index + 2] & 0xFF) << 8) 
					| (buf[index + 3] & 0xFF));
		}
		@Override
		public long getUnsignedInt(int index) {
			return (
					((buf[index] & 0xFFL) << 24)
					| ((buf[index + 1] & 0xFFL) << 16)
					| ((buf[index + 2] & 0xFFL) << 8) 
					| (buf[index + 3] & 0xFFL));
		}
		@Override
		public long getLong(int index) {
			return (
					((buf[index] & 0xFFL) << 56)
					| ((buf[index + 1] & 0xFFL) << 48)
					| ((buf[index + 2] & 0xFFL) << 40)
					| ((buf[index + 3] & 0xFFL) << 32)
					| ((buf[index + 4] & 0xFFL) << 24)
					| ((buf[index + 5] & 0xFFL) << 16)
					| ((buf[index + 6] & 0xFFL) << 8) 
					| (buf[index + 7] & 0xFFL));
		}
		@Override
		public float getFloat(int index) {
			return Float.intBitsToFloat(getInt(index));
		}
		@Override
		public double getDouble(int index) {
			return Double.longBitsToDouble(getLong(index));
		}
		@Override
		public char getChar(int index) {
			return (char)getShort(index);
		}
		
	}
	/**
	 * Byte access implementation with little endian format.
	 * @author akarnokd, 2013.04.17.
	 *
	 */
	class ByteAccessLE implements ByteAccess {
		@Override
		public void set(int index, byte v) {
			buf[index] = v;
		}
		@Override
		public void set(int index, short v) {
			buf[index + 1] = (byte)((v >> 8) & 0xFF); 
			buf[index] = (byte)(v & 0xFF); 
		}
		@Override
		public void set(int index, int v) {
			buf[index + 3] = (byte)((v >> 24) & 0xFF); 
			buf[index + 2] = (byte)((v >> 16) & 0xFF); 
			buf[index + 1] = (byte)((v >> 8) & 0xFF); 
			buf[index] = (byte)(v & 0xFF); 
		}
		@Override
		public void set(int index, long v) {
			buf[index + 7] = (byte)((v >> 56) & 0xFF); 
			buf[index + 6] = (byte)((v >> 48) & 0xFF); 
			buf[index + 5] = (byte)((v >> 40) & 0xFF); 
			buf[index + 4] = (byte)((v >> 32) & 0xFF); 
			buf[index + 3] = (byte)((v >> 24) & 0xFF); 
			buf[index + 2] = (byte)((v >> 16) & 0xFF); 
			buf[index + 1] = (byte)((v >> 8) & 0xFF); 
			buf[index] = (byte)(v & 0xFF); 
		}
		@Override
		public void set(int index, float v) {
			set(index, Float.floatToRawIntBits(v));
		}
		@Override
		public void set(int index, double v) {
			set(index, Double.doubleToRawLongBits(v));
		}
		@Override
		public void set(int index, char v) {
			set(index, (short)v);
		}
		@Override
		public byte getByte(int index) {
			return buf[index];
		}
		@Override
		public int getUnsignedByte(int index) {
			return getByte(index) & 0xFF;
		}
		@Override
		public short getShort(int index) {
			return (short)(
					((buf[index] & 0xFF)) 
					| ((buf[index + 1] & 0xFF) << 8)
					);
		}
		@Override
		public int getUnsignedShort(int index) {
			return (
					((buf[index] & 0xFF)) 
					| ((buf[index + 1] & 0xFF) << 8)
					);
		}
		@Override
		public int getInt(int index) {
			return (
					  ((buf[index + 0] & 0xFF)) 
					| ((buf[index + 1] & 0xFF) << 8)
					| ((buf[index + 2] & 0xFF) << 16)
					| ((buf[index + 3] & 0xFF) << 24)
					);
		}
		@Override
		public long getUnsignedInt(int index) {
			return (
					  ((buf[index + 0] & 0xFFL)) 
					| ((buf[index + 1] & 0xFFL) << 8)
					| ((buf[index + 2] & 0xFFL) << 16)
					| ((buf[index + 3] & 0xFFL) << 24)
					);
		}
		@Override
		public long getLong(int index) {
			return (
					((buf[index] & 0xFFL)) 
					| ((buf[index + 1] & 0xFFL) << 8)
					| ((buf[index + 2] & 0xFFL) << 16)
					| ((buf[index + 3] & 0xFFL) << 24)
					| ((buf[index + 4] & 0xFFL) << 32)
					| ((buf[index + 5] & 0xFFL) << 40)
					| ((buf[index + 6] & 0xFFL) << 48)
					| ((buf[index + 7] & 0xFFL) << 56)
					);
		}
		@Override
		public float getFloat(int index) {
			return Float.intBitsToFloat(
					  ((buf[index + 0] & 0xFF) << 24)
					| ((buf[index + 1] & 0xFF) << 16)
					| ((buf[index + 2] & 0xFF) << 8)
					| ((buf[index + 3] & 0xFF))
			);
		}
		@Override
		public double getDouble(int index) {
			return Double.longBitsToDouble(
					  ((buf[index + 0] & 0xFFL) << 56) 
					| ((buf[index + 1] & 0xFFL) << 48)
					| ((buf[index + 2] & 0xFFL) << 40)
					| ((buf[index + 3] & 0xFFL) << 32)
					| ((buf[index + 4] & 0xFFL) << 24)
					| ((buf[index + 5] & 0xFFL) << 16)
					| ((buf[index + 6] & 0xFFL) << 8)
					| ((buf[index + 7] & 0xFFL))
			);
		}
		@Override
		public char getChar(int index) {
			return (char)(
				  ((buf[index + 0] & 0xFF) << 8) 
				| ((buf[index + 1] & 0xFF))
			);
		}
	}
}
