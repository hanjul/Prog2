package de.hsa.games.fatsquirrel.command;

public class ScanException extends RuntimeException {

	public ScanException(Throwable t) {
		super(t);
	}

	public ScanException() {
		super();
	}

	public ScanException(String message) {
		super(message);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 3901184669940874188L;
}
