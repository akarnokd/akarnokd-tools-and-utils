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

import ix.exceptions.MultiIOException;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import javax.annotation.concurrent.GuardedBy;

import com.google.common.collect.Sets;


/**
 * A bounded object pool. 
 * @author akarnokd, 2011.10.06.
 * @param <T> the pooled object type
 */
public class BoundedPool<T> implements Pool<T> {
	/** The available pool of objects. */
	protected final BlockingDeque<T> objects;
	/** The storage of all created objects. */
	@GuardedBy("allObjects")
	protected final Set<T> allObjects = Sets.newHashSet();
	/** The target pool size. */
	protected final int poolSize;
	/** The manager of the pool objects. */
	protected final PoolManager<T> manager;
	/** Flag to indicate lifo queue management. */
	protected final boolean lifo;
	/**
	 * Creates a pool with the given size.
	 * @param poolSize the pool size
	 * @param manager the pool manager
	 */
	public BoundedPool(int poolSize, 
			PoolManager<T> manager) {
		this(poolSize, manager, true);
	}
	/**
	 * Creates a pool with the given size.
	 * @param poolSize the pool size
	 * @param manager the pool manager
	 * @param lifo use lifo queue management?
	 */
	public BoundedPool(int poolSize, 
			PoolManager<T> manager, boolean lifo) {
		this.poolSize = poolSize;
		this.manager = manager;
		this.lifo = lifo;
		objects = new LinkedBlockingDeque<>(poolSize);
	}
	@Override
	public T get() throws Exception {
		synchronized (allObjects) {
			if (allObjects.size() < poolSize) {
				T obj = manager.create();
				allObjects.add(obj);
				return obj;
			}
		}
		T obj = objects.take();
		try {
    		if (manager.verify(obj)) {
    			return obj;
    		}
		} catch (Exception ex) {
		    // the verification crashed, throw away the object
		}
		// create a new one instead
		synchronized (allObjects) {
			allObjects.remove(obj);
			obj = manager.create();
			allObjects.add(obj);
			return obj;
		}
	}
	/**
	 * Return a pool object.
	 * @param obj the object to return
	 */
	@Override
	public void put(T obj) {
		synchronized (allObjects) {
			if (!allObjects.contains(obj)) {
				throw new IllegalArgumentException("obj is not managed by this pool");
			}			
		}
		if (lifo) {
			objects.addFirst(obj);
		} else {
			objects.addLast(obj);
		}
	}
	@Override
	public void close() throws IOException {
		MultiIOException exc = null;
		synchronized (allObjects) {
			for (T obj : allObjects) {
				try {
					manager.close(obj);
				} catch (Exception ex) {
					exc = MultiIOException.createOrAdd(exc, new IOException(ex));
				}
			}
			allObjects.clear();
		}
		if (exc != null) {
			throw exc;
		}
	}
}
