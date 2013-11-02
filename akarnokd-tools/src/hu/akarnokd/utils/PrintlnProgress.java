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

package hu.akarnokd.utils;

import java.util.concurrent.TimeUnit;

import org.joda.time.DateTimeZone;
import org.joda.time.LocalTime;

import com.google.common.base.Stopwatch;

import edu.umd.cs.findbugs.annotations.Nullable;

/**
 * A simple println based progress indicator, displaying dots and numbers.
 * @author akarnokd, 2013.04.26.
 */
public class PrintlnProgress {
	/** The current count. */
	protected long count;
	/** The modulo for printing a dot. */
	protected final long dotCount;
	/** New line after this number of dots. */
	protected final int lineDots;
	/** The maximum value, if known. */
	protected Long max;
	/** The timing. */
	protected final Stopwatch stopwatch;
	/**
	 * Constructor.
	 * @param dotCount The modulo for printing a dot
	 * @param lineDots New line after this number of dots
	 */
	public PrintlnProgress(long dotCount, int lineDots) {
		this.dotCount = dotCount;
		this.lineDots = lineDots;
		this.stopwatch = Stopwatch.createUnstarted();
	}
	/**
	 * Set the current count.
	 * @param newCount the new count
	 */
	public void setCount(long newCount) {
		this.count = newCount;
	}
	/** 
	 * @return the current count
	 */
	public long getCount() {
		return count;
	}
	/**
	 * Sets a maximum value or null if not known.
	 * @param max the maximum value
	 */
	public void setMax(@Nullable Long max) {
		this.max = max;
	}
	/**
	 * Returns the current maximum value, which might be null.
	 * @return the maximum value if known
	 */
	@Nullable
	public Long getMax() {
		return max;
	}
	/**
	 * Reset the counting and timing.
	 */
	public void reset() {
		count = 0L;
		if (stopwatch.isRunning()) {
			stopwatch.stop();
		}
		stopwatch.reset();
	}
	/** Start the counting operation. */
	public void start() {
		stopwatch.start();
	}
	/** Stop the counting operation. */
	public void stop() {
		stopwatch.stop();
		System.out.printf(" %,d", count);
		long tm = stopwatch.elapsed(TimeUnit.MILLISECONDS);
		if (tm > 0) {
			double speed = count * 1d / tm;
			LocalTime lt = new LocalTime(tm, DateTimeZone.UTC);
			System.out.printf("; time %s",
					lt.toString("HH:mm:ss.SSS") 
			);
			System.out.printf("; speed %,.1f / sec", speed * 1000);
		}
		System.out.println();
	}
	/**
	 * Increment the counter by one.
	 */
	public void increment() {
		count++;
		if (count % dotCount == 0L) {
			System.out.print('.');
			if ((count / dotCount) % lineDots == 0) {
				printTotal();
			}
		}
	}
	/**
	 * Prints the total counts.
	 */
	private void printTotal() {
		System.out.printf(" %,d", count);
		
		if (max != null) {
			System.out.printf(" of %,d (%,.4f%%)", max, count * 100d / max);
			long tm = stopwatch.elapsed(TimeUnit.MILLISECONDS);
			if (tm > 0) {
				double speed = count * 1d / tm;
				long rem = (long)((max - count) / speed);
				LocalTime lt = new LocalTime(rem, DateTimeZone.UTC);
				System.out.printf("; remaining %s",
						lt.toString("HH:mm:ss.SSS") 
				);
			}
		}
		
		System.out.println();
	}
}
