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

import java.io.DataInput;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;

/**
 * An empty input stream which throws EOFException on all read attempts.
 * @author akarnokd, 2013.05.22.
 */
public class EmptyInputStream extends InputStream implements DataInput {

	@Override
	public void readFully(byte[] b) throws IOException {
		throw new EOFException();
	}

	@Override
	public void readFully(byte[] b, int off, int len) throws IOException {
		throw new EOFException();
	}

	@Override
	public int skipBytes(int n) throws IOException {
		throw new EOFException();
	}

	@Override
	public boolean readBoolean() throws IOException {
		throw new EOFException();
	}

	@Override
	public byte readByte() throws IOException {
		throw new EOFException();
	}

	@Override
	public int readUnsignedByte() throws IOException {
		throw new EOFException();
	}

	@Override
	public short readShort() throws IOException {
		throw new EOFException();
	}

	@Override
	public int readUnsignedShort() throws IOException {
		throw new EOFException();
	}

	@Override
	public char readChar() throws IOException {
		throw new EOFException();
	}

	@Override
	public int readInt() throws IOException {
		throw new EOFException();
	}

	@Override
	public long readLong() throws IOException {
		throw new EOFException();
	}

	@Override
	public float readFloat() throws IOException {
		throw new EOFException();
	}

	@Override
	public double readDouble() throws IOException {
		throw new EOFException();
	}

	@Override
	public String readLine() throws IOException {
		throw new EOFException();
	}

	@Override
	public String readUTF() throws IOException {
		throw new EOFException();
	}

	@Override
	public int read() throws IOException {
		return -1;
	}
	
}
