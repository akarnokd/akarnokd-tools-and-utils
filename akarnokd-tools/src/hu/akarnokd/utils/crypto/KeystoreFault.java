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

package hu.akarnokd.utils.crypto;

/**
 * An unchecked runtime exception used to report
 * the keystore and key management related exceptions.
 * @author akarnokd, 2007.12.07.
 */
public class KeystoreFault extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1075300973961671049L;

	/**
	 * Constructor. Do not use it because it is no helpful to have an empty exception
	 * whithout a message or cause.
	 */
	public KeystoreFault() {
		super();
	}

	/**
	 * Constructor with given message.
	 * @param message the error message
	 */
	public KeystoreFault(String message) {
		super(message);
	}

	/**
	 * Constructor with the cause.
	 * @param cause the cause
	 */
	public KeystoreFault(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructor with the message and cause.
	 * @param message the message
	 * @param cause the cause
	 */
	public KeystoreFault(String message, Throwable cause) {
		super(message, cause);
	}

}
