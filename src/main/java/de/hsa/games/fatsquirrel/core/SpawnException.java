package de.hsa.games.fatsquirrel.core;

public class SpawnException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -46701801084311437L;

	public SpawnException() {
		super();
	}
	
	public SpawnException(final String message) {
		super(message);
	}
	
	public SpawnException(final Throwable throwable) {
		super(throwable);
	}
	
	public SpawnException(final String message, final Throwable throwable) {
		super(message, throwable);
	}
}
