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

import java.io.IOException;

/**
 * A pool manager which may return an unlimited amount of objects.
 * <p>The pool doesn't keep track of the returned objects.</p>
 * @author akarnokd, 2011.10.06.
 * @param <T> the pooled object type
 */
public class UnlimitedPool<T> implements Pool<T> {
//	/** The logger. */
//	protected static final Logger LOG = LoggerFactory.getLogger(UnlimitedPool.class);
	/** The pool manager object. */
	protected final PoolManager<T> manager;
	/**
	 * Constructor. Initializes the pool manager.
	 * @param manager the pool manager
	 */
	public UnlimitedPool(PoolManager<T> manager) {
		this.manager = manager;
	}
	@Override
	public void close() throws IOException {
		// NO op
	}

	@Override
	public T get() throws Exception {
		return manager.create();
	}

	@Override
	public void put(T obj) {
		try {
			manager.close(obj);
		} catch (Exception ex) {
//			LOG.error(ex.toString(), ex);
			// ignored
		}
	}

}
