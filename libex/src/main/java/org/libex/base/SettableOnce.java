package org.libex.base;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.NotThreadSafe;

import com.google.common.base.Optional;
import com.google.common.base.Supplier;

/**
 * A wrapper for a field that should only be set once. After the wrapped value
 * has been set once to a non-null value attempting to set it again will throw
 * an IllegalStateException.
 * 
 * @author John Butler
 * 
 */
@ParametersAreNonnullByDefault
@NotThreadSafe
public class SettableOnce<T> implements Supplier<T> {

	private Optional<T> value = Optional.absent();
	private final String message;

	public SettableOnce() {
		this.message = "Field cannot be set more than once";
	}

	/**
	 * @param name
	 *            the name of the field to be included in error messages
	 * @throws NullPointerException
	 *             if name is null
	 */
	public SettableOnce(String name) {
		checkNotNull(name);

		this.message = "Field " + name + " cannot be set more than once";
	}

	/**
	 * @param input
	 *            the value to set
	 * 
	 * @throws IllegalStateException
	 *             if the value has already been set
	 * @throws NullPointerException
	 *             if input is null
	 */
	public void set(T input) {
		checkState(!value.isPresent(), message);

		value = Optional.of(input);
	}

	/**
	 * @param input
	 *            the value to set if this instance has not already been set
	 * 
	 * @throws NullPointerException
	 *             if value has not been set and input is null
	 */
	public void setIfAbsent(T input) {
		if (!value.isPresent()) {
			value = Optional.of(input);
		}
	}

	/**
	 * @return an Optional containing the value if present
	 */
	@Nonnull
	public Optional<T> getOptional() {
		return value;
	}

	/**
	 * @return the wrapped value if set, otherwise null
	 */
	@Override
	@Nullable
	public T get() {
		return value.orNull();
	}

}
