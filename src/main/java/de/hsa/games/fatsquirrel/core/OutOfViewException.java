package de.hsa.games.fatsquirrel.core;

public class OutOfViewException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2866632678666067079L;

	public OutOfViewException() {
		super();
	}
	
	public OutOfViewException(final String message) {
		super(message);
	}
	
	public OutOfViewException(final Throwable throwable) {
		super(throwable);
	}
	
	public OutOfViewException(final String message, final Throwable throwable) {
		super(message, throwable);
	}
}
