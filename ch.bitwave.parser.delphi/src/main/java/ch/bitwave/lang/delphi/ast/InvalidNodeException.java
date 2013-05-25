package ch.bitwave.lang.delphi.ast;

/**
 * Signals that an attempt was made to add a node of an invalid type.
 */
public class InvalidNodeException extends Exception {

	public InvalidNodeException(final String message) {
		super(message);
	}

	private static final long serialVersionUID = 1L;

}
