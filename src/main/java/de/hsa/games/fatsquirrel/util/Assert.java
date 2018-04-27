package de.hsa.games.fatsquirrel.util;

public class Assert {

	/**
	 * Checks that the specified expression is {@code true} and throws a customized
	 * {@link IllegalArgumentException} if it evaluates to {@code false}.
	 * 
	 * @param expression
	 *            the expression to check for
	 * @param message
	 *            detail message to be used in the event that a
	 *            {@code NullPointerException} is thrown
	 * @throws IllegalArgumentException
	 *             if {@code expression} is {@code false}
	 */
	public static void isTrue(final boolean expression, final String message) {
		if (!expression) {
			throw new IllegalArgumentException(message);
		}
	}

	/**
	 * Checks that the specified object reference is not {@code null} and throws a
	 * customized {@link NullPointerException} if it is.
	 * 
	 * @param obj
	 *            the object reference to check for nullity
	 * @param message
	 *            detail message to be used in the event that a
	 *            {@code NullPointerException} is thrown
	 * @param <T>
	 *            the type of the reference
	 * @return {@code obj} if not {@code null}
	 * @throws NullPointerException
	 *             if {@code obj} is {@code null}
	 */
	public static <T> T notNull(final T obj, final String message) {
		if (obj == null) {
			throw new NullPointerException(message);
		}
		return obj;
	}
}
