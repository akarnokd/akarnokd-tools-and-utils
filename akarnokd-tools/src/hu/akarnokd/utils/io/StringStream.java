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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.nio.CharBuffer;

import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * An unsynchronized string buffer which implements the
 * writer methods and provides StringReader access
 * directly.
 * @author akarnokd, 2013.05.26.
 *
 */
public class StringStream extends Writer {
	/** The backing buffer. */
	protected StringBuilder buffer;
	/**
	 * Constructor. Initializes the buffer with 
	 * default capacity.
	 */
	public StringStream() {
		buffer = new StringBuilder(); 
	}
	/**
	 * Constructor. Initializes the buffer with the
	 * given capacity.
	 * @param capacity the capacity
	 */
	public StringStream(int capacity) {
		buffer = new StringBuilder(capacity);
	}
	/**
	 * Constructor. Initializes the buffer with the
	 * given string contents.
	 * @param initial the initial string contents
	 */
	public StringStream(CharSequence initial) {
		buffer = new StringBuilder(initial);
	}
	/**
	 * Resets the contents of the buffer.
	 */
	public void reset() {
		buffer = new StringBuilder();
	}
	@Override
	public void flush() throws IOException {
		// no op
	}
	@Override
	public void close() throws IOException {
		// no op
	}
	@Override
	public void write(char[] cbuf, int off, int len) throws IOException {
		buffer.append(cbuf, off, len);
	}
	@Override
	public void write(char[] cbuf) throws IOException {
		buffer.append(cbuf);
	}
	@Override
	public void write(int c) throws IOException {
		buffer.append(c);
	}
	@Override
	public void write(String str) throws IOException {
		buffer.append(str);
	}
	@Override
	public void write(String str, int off, int len) throws IOException {
		buffer.append(str, off, len);
	}
	@Override
	public StringStream append(char c) throws IOException {
		buffer.append(c);
		return this;
	}
	@Override
	public StringStream append(CharSequence csq) throws IOException {
		buffer.append(csq);
		return this;
	}
	@Override
	public StringStream append(CharSequence csq, int start, int end)
			throws IOException {
		buffer.append(csq, start, end);
		return this;
	}
	@Override
	public String toString() {
		return buffer.toString();
	}
	/**
	 * Returns a PrintWriter object working with this object.
	 * @return the print writer instance
	 */
	@NonNull
	public PrintWriter printWriter() {
		return new PrintWriter(this);
	}
	/**
	 * The length of the buffer contents.
	 * @return the length
	 */
	public int length() {
		return buffer.length();
	}
	/**
	 * Sets a new length for the buffer, filling with
	 * zero chars if extending.
	 * @param newLength the new length
	 */
	public void setLength(int newLength) {
		buffer.setLength(newLength);
	}
	/**
	 * Returns a character at the specified index.
	 * @param index the index
	 * @return the character
	 */
	public char charAt(int index) {
		return buffer.charAt(index);
	}
	/**
	 * Sets a character at the specified index.
	 * @param index the index
	 * @param c the character to set
	 */
	public void setCharAt(int index, char c) {
		buffer.setCharAt(index, c);
	}
	/**
	 * Tries to read the specified amount of characters
	 * from the buffer and places them into the given character
	 * array, returning the actual number of characters copied.
	 * @param index the source index
	 * @param out the output character array
	 * @param start the start within the output
	 * @param len the expected number of bytes to copy
	 * @return the actual number of characters copied
	 */
	public int read(int index, char[] out, int start, int len) {
		if (index + len > buffer.length()) {
			len = buffer.length() - index;
		}
		buffer.getChars(index, index + len, out, start);
		return len;
	}
	/**
	 * Tries to completely fill-in the given output array.
	 * @param index the index to read from
	 * @param out the output array
	 * @return the actual number of characters copied
	 */
	public int read(int index, char[] out) {
		return read(index, out, 0, out.length);
	}
	/**
	 * Returns a Reader instance to read from the stream.
	 * The returned reader is not thread safe.
	 * @return the string reader instance
	 */
	public Reader reader() {
		return new StringStreamReader();
	}
	/**
	 * Convenience method to return a buffered reader
	 * instance.
	 * @return the buffered reader with default capacity
	 */
	public BufferedReader bufferedReader() {
		return new BufferedReader(reader());
	}
	/**
	 * The reader class for this string stream. 
	 * @author akarnokd, 2013.05.26.
	 */
	protected class StringStreamReader extends Reader {
		/** The current read index. */
		protected int index;
		/** To remember the read position. */
		protected int mark = -1;
		@Override
		public int read(char[] cbuf, int off, int len) throws IOException {
			if (index >= buffer.length()) {
				return -1;
			}
			int r = StringStream.this.read(index, cbuf, off, len);
			index += r;
			return r;
		}
		@Override
		public int read() throws IOException {
			if (index >= buffer.length()) {
				return -1;
			}
			return buffer.charAt(index++);
		}
		
		@Override
		public void close() throws IOException {
			// no operation
		}
		@Override
		public boolean markSupported() {
			return true;
		}
		@Override
		public void mark(int readAheadLimit) throws IOException {
			mark = index;
		}
		@Override
		public void reset() throws IOException {
			if (mark >= 0) {
				index = mark;
			} else {
				throw new IOException("Stream not marked");
			}
		}
		@Override
		public long skip(long n) throws IOException {
			int remaining = buffer.length() - index;
			if (remaining <= 0) {
				return 0;
			}
			int toSkip = Math.min(remaining, (int)n);
			index += toSkip;
			return toSkip;
		}
		@Override
		public int read(CharBuffer target) throws IOException {
			int rem = target.remaining();
			int r = 0;
			for (; index < buffer.length() && rem > 0; index++, rem--, r++) {
				target.put(buffer.charAt(index));
			}
			return r;
		}
		
	}
	/**
	 * Changes the underlying buffer capacity to the current
	 * number of characters, freeing up memory.
	 */
	public void compact() {
		buffer.trimToSize();
	}
	/**
	 * Writes the entire contents of this stream to the given writer.
	 * @param out the output writer
	 * @throws IOException if the output throws it
	 */
	public void writeTo(@NonNull Writer out) throws IOException {
		char[] buf = new char[8192];
		int remaining = buffer.length();
		int index = 0;
		while (remaining > 0) {
			int r = read(index, buf, 0, buf.length);
			out.write(buf, 0, r);
			remaining -= r;
			index += r;
		}
	}
	/**
	 * Reads everything from the given reader instance.
	 * @param in the input reader
	 * @throws IOException if the reader throws it
	 */
	public void readFrom(@NonNull Reader in) throws IOException {
		char[] buf = new char[8192];
		do {
			int r = in.read(buf);
			if (r < 0) {
				break;
			} else
			if (r > 0) {
				buffer.append(buf, 0, r);
			}
		} while (true);
	}
}
