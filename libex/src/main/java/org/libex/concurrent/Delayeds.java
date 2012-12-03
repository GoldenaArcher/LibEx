package org.libex.concurrent;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.ThreadSafe;


import com.google.common.primitives.Longs;

/**
 * @author John Butler
 * 
 */
@ThreadSafe
@ParametersAreNonnullByDefault
public final class Delayeds {

	public static Delayed createDelay(TimeSpan timeSpan) {
		return new DefaultDelay(timeSpan);
	}

	private static class DefaultDelay implements Delayed {
		private final long endTime;

		private DefaultDelay(TimeSpan timeSpan) {
			endTime = System.nanoTime() + timeSpan.getDurationIn(TimeUnit.NANOSECONDS);
		}

		@Override
		public int compareTo(Delayed o) {
			return Longs.compare(this.getDelay(TimeUnit.NANOSECONDS), o.getDelay(TimeUnit.NANOSECONDS));
		}

		@Override
		public long getDelay(TimeUnit timeUnit) {
			long currentNanos = System.nanoTime();
			long remainder = endTime - currentNanos;
			if (remainder <= 0) {
				return 0;
			} else {
				return timeUnit.convert(remainder, TimeUnit.NANOSECONDS);
			}
		}

	}

	private Delayeds() {
	}
}
