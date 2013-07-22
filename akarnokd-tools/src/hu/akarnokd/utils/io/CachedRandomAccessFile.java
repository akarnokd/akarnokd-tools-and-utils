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

import hu.akarnokd.utils.lang.Bytes;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * Read-only random access file which manages
 * a given number of cached pages of the file.
 * Changes in the underlying file are not reflected.
 * @author akarnokd, 2013.07.22.
 */
public class CachedRandomAccessFile implements Closeable {
	/** The backing random access file. */
	protected RandomAccessFile raf;
	/** The cache for the pages. */
	protected LoadingCache<Integer, byte[]> pages;
	/** The file length. */
	protected final long length;
	/** The buffer size. */
	private final int bufferSize;
	/**
	 * Constructor, opens the file and sets up the cache with
	 * the given number of maximum entries and the general
	 * page size.
	 * @param fileName the file name
	 * @param bufferSize the buffer size in bytes
	 * @param bufferCount the number of buffers to maintain
	 * @throws IOException on file open error
	 */
	public CachedRandomAccessFile(String fileName, int bufferSize, int bufferCount) throws IOException {
		this(new File(fileName), bufferSize, bufferCount);
	}
	/**
	 * Constructor, opens the file and sets up the cache with
	 * the given number of maximum entries and the general
	 * page size.
	 * @param file the file
	 * @param bufferSize the buffer size in bytes
	 * @param bufferCount the number of buffers to maintain
	 * @throws IOException on file open error
	 */
	public CachedRandomAccessFile(File file, final int bufferSize, int bufferCount) throws IOException {
		if (bufferSize <= 0) {
			throw new IllegalArgumentException("bufferSize > 0");
		}
		if (bufferCount <= 0) {
			throw new IllegalArgumentException("bufferCount > 0");
		}
		this.bufferSize = bufferSize;
		this.raf = new RandomAccessFile(file, "r");
		this.length = raf.length();
		this.pages = CacheBuilder.newBuilder()
				.maximumSize(bufferCount)
				.build(new CacheLoader<Integer, byte[]>() {
					@Override
					public byte[] load(Integer key) throws Exception {
						byte[] data = new byte[bufferSize];
						raf.seek(1L * bufferSize * key);
						raf.read(data);
						return data;
					}
				});
	}
	@Override
	public void close() throws IOException {
		raf.close();
	}
	/**
	 * Check if the given offset and size lies within the file
	 * and throw an index exception if not.
	 * @param offset the offset
	 * @param size the size
	 */
	private void checkOffset(long offset, int size) {
		if (offset < 0 || offset + size > length) {
			throw new IndexOutOfBoundsException(offset + " + " + size + " vs. " + length);
		}
	}
	/**
	 * Fetches the contents of a page.
	 * @param page the page index
	 * @return the page bytes
	 */
	private byte[] getPage(int page) {
		try {
			return pages.get(page);
		} catch (ExecutionException ex) {
			throw new IllegalStateException(ex);
		}
	}
	/**
	 * Returns a byte at the given offset without checking bounds.
	 * @param offset the offset to read from
	 * @return the byte read
	 */
	private byte getInternal(long offset) {
		int page = (int)(offset / bufferSize);
		return getPage(page)[(int)(offset % bufferSize)];
	}
	/**
	 * The total number of bytes.
	 * @return the length
	 */
	public long length() {
		return length;
	}
	/**
	 * Get a byte at the given offset.
	 * @param offset the offset to read
	 * @return the byte value
	 */
	public byte get(long offset) {
		checkOffset(offset, 1);
		return getInternal(offset);
	}
	/**
	 * Get a short at the given offset.
	 * @param offset the offset
	 * @return the short value
	 */
	public short getShort(long offset) {
		checkOffset(offset, 2);
		if ((offset % bufferSize) + 2 > bufferSize) {
			return Bytes.makeShort(getInternal(offset), getInternal(offset + 1));
		}
		int page = (int)(offset / bufferSize);
		byte[] p = getPage(page);
		int offs = (int)(offset % bufferSize);
		return Bytes.makeShort(p[offs], p[offs + 1]); 
	}
	/**
	 * Get an int at the given offset.
	 * @param offset the offset
	 * @return the int value
	 */
	public int getInt(long offset) {
		checkOffset(offset, 4);
		if ((offset % bufferSize) + 4 > bufferSize) {
			return Bytes.makeInt(
					getInternal(offset), 
					getInternal(offset + 1),
					getInternal(offset + 2),
					getInternal(offset + 3)
				);
		}
		int page = (int)(offset / bufferSize);
		byte[] p = getPage(page);
		int offs = (int)(offset % bufferSize);
		return Bytes.makeInt(p[offs], p[offs + 1], p[offs + 2], p[offs + 3]); 
	}
	/**
	 * Get an long at the given offset.
	 * @param offset the offset
	 * @return the long value
	 */
	public long getLong(long offset) {
		checkOffset(offset, 8);
		if ((offset % bufferSize) + 8 > bufferSize) {
			return Bytes.makeLong(
					getInternal(offset), 
					getInternal(offset + 1),
					getInternal(offset + 2),
					getInternal(offset + 3),
					getInternal(offset + 4),
					getInternal(offset + 5),
					getInternal(offset + 6),
					getInternal(offset + 7)
				);
		}
		int page = (int)(offset / bufferSize);
		byte[] p = getPage(page);
		int offs = (int)(offset % bufferSize);
		return Bytes.makeLong(
				p[offs], 
				p[offs + 1], 
				p[offs + 2], 
				p[offs + 3],
				p[offs + 4],
				p[offs + 5],
				p[offs + 6],
				p[offs + 7]
			); 
	}
	/**
	 * Get an float at the given offset.
	 * @param offset the offset
	 * @return the float value
	 */
	public float getFloat(long offset) {
		return Float.intBitsToFloat(getInt(offset));
	}
	/**
	 * Get an double at the given offset.
	 * @param offset the offset
	 * @return the double value
	 */
	public double getDouble(long offset) {
		return Double.longBitsToDouble(getLong(offset));
	}
	/**
	 * Get values into a byte array.
	 * @param offset the read offset
	 * @param into the output array
	 * @param start the start in the into array
	 * @param size the number of bytes
	 */
	public void get(long offset, byte[] into, int start, int size) {
		checkOffset(offset, size);
		int startPage = (int)(offset / bufferSize);
		int startOffset = (int)(offset % bufferSize);
		int endPage = (int)((offset + size - 1) / bufferSize);
		int endOffset = (int)((offset + size - 1) % bufferSize);
		if (startPage == endPage) {
			byte[] p = getPage(startPage);
			System.arraycopy(p, startOffset, into, start, size);
		} else {
			int firstRem = bufferSize - startOffset;
			byte[] p = getPage(startPage);
			System.arraycopy(p, startOffset, into, start, firstRem);
			start += firstRem;
			for (int i = startPage + 1; i < endPage; i++) {
				p = getPage(i);
				System.arraycopy(p, 0, into, start, bufferSize);
				start += bufferSize;
			}
			p = getPage(endPage);
			System.arraycopy(p, 0, into, start, endOffset + 1);
		}
	}
	/**
	 * Creates a new input stream to read data.
	 * The returned input stream supports marking.
	 * @return the input stream
	 */
	public CachedInputStream newInputStream() {
		return new CachedInputStream();
	}
	/**
	 * The input stream to read the contents.
	 * @author akarnokd, 2013.07.22.
	 *
	 */
	protected class CachedInputStream extends InputStream {
		/** The marked position. */
		protected long mark = -1;
		/** The current offset. */
		protected long offset = 0;
		@Override
		public int available() throws IOException {
			return (int)Math.min(Integer.MAX_VALUE, length - offset);
		}
		@Override
		public int read() throws IOException {
			if (offset < length) {
				return get(offset++);
			}
			return -1;
		}
		@Override
		public int read(byte[] b) throws IOException {
			return read(b, 0, b.length);
		}
		@Override
		public int read(byte[] b, int off, int len) throws IOException {
			if (offset < length) {
				int toRead = (int)Math.min(len, length - offset);
				get(offset, b, off, toRead);
				offset += toRead;
				return toRead;
			}
			return -1;
		}
		@Override
		public boolean markSupported() {
			return true;
		}
		@Override
		public synchronized void mark(int readlimit) {
			mark = offset;
		}
		@Override
		public synchronized void reset() throws IOException {
			if (mark < 0) {
				throw new IOException("Not marked!");
			}
			offset = mark;
		}
	}
}
