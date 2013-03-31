package org.libex.base;

import static com.google.common.base.Preconditions.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.ThreadSafe;

import com.google.common.base.Defaults;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;

@ParametersAreNonnullByDefault
@ThreadSafe
public final class StringsEx {

	private static final Predicate<String> isNullOrEmpty = new Predicate<String>() {

		@Override
		public boolean apply(@Nullable String input) {
			return Strings.isNullOrEmpty(input);
		}
	};

	public static Predicate<String> isNullOrEmpty() {
		return isNullOrEmpty;
	}

	/**
	 * Creates a Function that formats the object passed to the Function using
	 * the passed format. This method uses {@code String.format(format, input)}
	 * 
	 * @param format
	 *            the format to use to convert the instance passed to the
	 *            function to a string
	 * @return a Function that formats the object passed to the Function using
	 *         the passed format
	 */
	@Nonnull
	public static Function<Object, String> formatter(final String format) {
		checkNotNull(format);
		String.format(format, (Object) null);

		return new Function<Object, String>() {

			@Override
			@Nonnull
			public String apply(@Nullable Object arg0) {
				return String.format(format, arg0);
			}
		};
	}

	/**
	 * Creates a type-specific Function that formats the object passed to the
	 * Function using the passed format. This method uses
	 * {@code String.format(format, input)}. This version should generally be
	 * used if the format expects a non-Object instance such as an Integer.
	 * 
	 * @param format
	 *            the format to use to convert the instance passed to the
	 *            function to a string
	 * @param type
	 *            used only at compile time to produce a type-specific generic
	 *            Function
	 * @return a Function that formats the object passed to the Function using
	 *         the passed format
	 */
	@Nonnull
	public static <T> Function<T, String> formatter(
			final String format, Class<? extends T> type) {
		checkNotNull(format);
		checkNotNull(type);
		String.format(format, Defaults.defaultValue(type));

		return new Function<T, String>() {

			@Override
			@Nonnull
			public String apply(@Nullable T arg0) {
				return String.format(format, arg0);
			}
		};
	}

	@Nonnull
	public static Function<String, String> replaceAll(final String regex, final String replacement) {
		checkNotNull(regex);
		checkNotNull(replacement);

		return new Function<String, String>() {

			@Override
			@Nonnull
			public String apply(@Nonnull String input) {
				return input.replaceAll(regex, replacement);
			}
		};
	}

	private static final Function<String, String> toLowerCase = new Function<String, String>() {

		@Override
		@Nullable
		public String apply(@Nullable String input) {
			return (input == null) ? null : input.toLowerCase();
		}
	};

	@Nonnull
	public static Function<String, String> toLowerCase() {
		return toLowerCase;
	}

	private static final Function<String, String> toUpperCase = new Function<String, String>() {

		@Override
		@Nullable
		public String apply(@Nullable String input) {
			return (input == null) ? null : input.toUpperCase();
		}
	};

	@Nonnull
	public static Function<String, String> toUpperCase() {
		return toUpperCase;
	}

	@Nullable
	public static String trim(@Nullable String s) {
		return (s == null) ? null : s.trim();
	}

	private StringsEx() {
	}
}
