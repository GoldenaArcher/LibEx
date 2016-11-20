package org.libex.base;

import static com.google.common.base.Preconditions.checkNotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.ThreadSafe;

import com.google.common.base.Function;
import com.google.common.base.Optional;

/**
 * Utility methods for {@link Optional} instances.
 * 
 * @author John Butler
 * 
 */
@ThreadSafe
@ParametersAreNonnullByDefault
public final class OptionalsEx {

	    /**
     * @return a Function that wraps objects in {@link Optional} using {@link Optional#fromNullable(Object)}
     * 
     * @see Optional#fromNullable(Object)
     * 
     * @param <U>
     *            type of the Optional to accept and value to return
     */
	@Nonnull
	public static <U> Function<U, Optional<U>> fromNullable() {
		return new Function<U, Optional<U>>() {

			@Override
			@Nonnull
			public Optional<U> apply(@Nullable final U arg0) {
				return Optional.fromNullable(arg0);
			}
		};
	}

	    /**
     * @return a Function that returns the value wrapped by an {@link Optional} or null if the Optional is Absent.
     * 
     * @see Optional#orNull()
     * 
     * @param <U>
     *            type of the Optional to accept and value to return
     */
	@Nonnull
    public static <U> Function<Optional<U>, U> toValueOrNull()
    {
        return new Function<Optional<U>, U>() {

			@Override
			@Nullable
            public U apply(
                    @Nonnull final Optional<U> optional)
            {
				return optional.orNull();
			}
		};
	}

	    /**
     * Creates a Function that returns the value wrapped by an {@link Optional} or {@code defaultValue} if the Optional
     * is Absent.
     * 
     * @param defaultValue
     *            the value that the function should return if passed an Absent
     * @return a Function that returns the value wrapped by an {@link Optional} or {@code defaultValue} if the Optional
     *         is Absent.
     * 
     * @throws NullPointerException
     *             if {@code defaultValue} is null, instead use {@link #toValueOrNull()}
     * 
     * @param <U>
     *            type of the Optional to accept and value to return
     */
	@Nonnull
	public static <U> Function<Optional<U>, U> toValueOr(final U defaultValue) {
		checkNotNull(defaultValue);

		return new Function<Optional<U>, U>() {

			@Override
			@Nonnull
			public U apply(@Nonnull final Optional<U> optional) {
				return optional.or(defaultValue);
			}
		};
	}

	private OptionalsEx() {
		System.out.println("op");
	}
}
