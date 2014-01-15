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

package hu.akarnokd.utils.database;

/**
 * Base exception for wrapping the checked SQLException.
 * @author akarnokd, 2013.04.18.
 */
public class SQLRuntimeException extends RuntimeException {
	/** */
	private static final long serialVersionUID = 6495722663463193608L;

	/**
	 * Constructor without parameters.
	 */
	public SQLRuntimeException() { }

	/**
	 * Constructor with a message.
	 * @param message the error message
	 */
	public SQLRuntimeException(String message) {
		super(message);
	}

	/**
	 * Constructor with cause.
	 * @param cause the cause exception
	 */
	public SQLRuntimeException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor with message and cause.
	 * @param message the message
	 * @param cause the cause
	 */
	public SQLRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}
}
