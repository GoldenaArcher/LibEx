package org.libex.concurrent;

import static com.google.common.base.Preconditions.checkNotNull;

import java.util.concurrent.TimeUnit;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.Immutable;
import javax.annotation.concurrent.ThreadSafe;

import com.google.common.base.Objects;
import com.google.common.primitives.Longs;

/**
 * Specifies a period of time. The idea for this class was taken from the Guava
 * Issues post 631.
 * 
 * {@link http://code.google.com/p/guava-libraries/issues/detail?id=631}
 * 
 * @author John Butler
 * 
 */
@ParametersAreNonnullByDefault
@ThreadSafe
@Immutable
public final class TimeSpan implements Comparable<TimeSpan> {
	private final long duration;
	private final TimeUnit timeUnit;

	public TimeSpan(TimeUnit timeUnit) {
		this(1, timeUnit);
	}

	/**
	 * A time span of a single specified time unit.
	 * 
	 * @param timeUnit
	 *            the time unit
	 */
	public TimeSpan(TimeUnit timeUnit) {
		checkNotNull(timeUnit, "timeUnit may not be null");
		this.duration = 1;
		this.timeUnit = timeUnit;
	}

	/**
	 * @param duration
	 *            the number of time units
	 * @param timeUnit
	 *            the time unit
	 */
	public TimeSpan(long duration, TimeUnit timeUnit) {
		checkNotNull(timeUnit, "timeUnit may not be null");
		this.duration = duration;
		this.timeUnit = timeUnit;
	}

	/**
	 * @return the number of time units
	 */
	public long getDuration() {
		return duration;
	}

	/**
	 * Gets the number of time units in the specified unit
	 * 
	 * @param timeUnit
	 *            the TimeUnit in which to return the number of units
	 * @return
	 * 
	 * @see TimeUnit#convert(long, TimeUnit)
	 */
	public long getDurationIn(TimeUnit timeUnit) {
		return timeUnit.convert(duration, this.timeUnit);
	}

	public TimeSpan convertTo(TimeUnit timeUnit) {
		return new TimeSpan(getDurationIn(timeUnit), timeUnit);
	}

	/**
	 * @return the time unit
	 */
	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	@Override
	public int compareTo(TimeSpan other) {
		return Longs.compare(duration, other.getDurationIn(timeUnit));
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getDurationIn(TimeUnit.NANOSECONDS));
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		TimeSpan other = (TimeSpan) obj;
		return duration == other.getDurationIn(timeUnit);
	}

	@Override
	public String toString() {
		return TimeUnitsEx.toString(duration, timeUnit);
	}

	public String toString(TimeUnit outputTimeUnit) {
		return TimeUnitsEx.toString(duration, outputTimeUnit);
	}
}
