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

package hu.akarnokd.utils.pool;

import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * The pool manager interface for creating, verifying and closing objects.
 * @author akarnokd, 2011-10-05
 * @param <T> the object type
 */
public interface PoolManager<T> {
	/**
	 * Creates a new object of type T.
	 * @return the object
	 * @throws Exception if the object could not be created
	 */
	@NonNull
	T create() throws Exception;
	/**
	 * Verify the validity of the given object.
	 * @param obj the object to verify
	 * @return true if the object is valid
	 * @throws Exception if the verification failure indicates a permanent error
	 */
	boolean verify(T obj) throws Exception;
	/**
	 * Close the specified object.
	 * @param obj the object to close
	 * @throws Exception to aggregate exceptions
	 */
	void close(T obj) throws Exception;
}