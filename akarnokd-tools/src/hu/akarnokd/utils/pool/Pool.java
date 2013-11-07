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

package hu.akarnokd.utils.pool;

import java.io.Closeable;

import edu.umd.cs.findbugs.annotations.NonNull;


/**
 * Represents a generic pooling object which supplies and takes back objects.
 * @author akarnokd, 2011.10.05.
 * @param <T> the pooled object type
 */
public interface Pool<T> extends Closeable {
	/**
	 * Retrieve an object from the pool.
	 * @return the object retrieved
	 * @throws Exception if the object could not be supplied
	 */
	@NonNull
	T get() throws Exception;
	/**
	 * Return an object to the pool.
	 * @param obj the object to return
	 */
	void put(@NonNull T obj);
}
