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

package hu.akarnokd.utils.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Concurrency utilities.
 * @author akarnokd, 2013.06.25.
 *
 */
public final class ConcurrentUtils {
	/** Utility class. */
	private ConcurrentUtils() { }
	/**
	 * Await all of the future tasks indefinitely.
	 * The exceptions are combined into a single exception
	 * (by adding subsequent exceptions as addsuppressed).
	 * @param tasks the sequence of tasks
	 * @throws ExecutionException if the task failed
	 * @throws InterruptedException if the wait was interrupted
	 */
	public static void awaitAll(Iterable<? extends Future<?>> tasks) throws ExecutionException, InterruptedException {
		ExecutionException ee = null;
		InterruptedException ie = null;
		for (Future<?> f : tasks) {
			try {
				f.get();
			} catch (ExecutionException ex) {
				if (ee != null) {
					ee.addSuppressed(ex);
				} else {
					ee = ex;
				}
			} catch (InterruptedException ex) {
				if (ie != null) {
					ie.addSuppressed(ex);
				} else {
					ie = ex;
				}
			}
		}
		if (ee != null && ie != null) {
			ee.addSuppressed(ie);
			throw ee;
		} else
		if (ee != null) {
			throw ee;
		} else
		if (ie != null) {
			throw ie;
		}
	}
	/**
	 * Await the completion of tasks interruptibly. The execution
	 * exceptions are combined into a single exception, but interrupted
	 * exception breaks the wait.
	 * @param tasks the tasks to wait
	 * @throws ExecutionException on execution errors
	 * @throws InterruptedException when the wait is interrupted
	 */
	public static void awaitAllInterruptibly(Iterable<? extends Future<?>> tasks) 
			throws ExecutionException, InterruptedException {
		ExecutionException ee = null;
		for (Future<?> f : tasks) {
			try {
				f.get();
			} catch (ExecutionException ex) {
				if (ee != null) {
					ee.addSuppressed(ex);
				} else {
					ee = ex;
				}
			}
		}
		if (ee != null) {
			throw ee;
		}
	}
}
