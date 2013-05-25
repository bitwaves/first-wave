package ch.bitwave.platform.reflect;

/**
 * Exception used to signal that a bean could not be reflectively modified
 * because either an attribute setter was not found or an attribute is of an
 * unsupported type.
 */
public class BeanModificationException extends Exception {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a bean modification exception with the given error message and
	 * cause.
	 * 
	 * @param message
	 *            additional information related to context of the failure.
	 * @param cause
	 *            the caused of the error.
	 */
	public BeanModificationException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a bean modification exception with the given message.
	 * 
	 * @param message
	 *            the reason for throwing the exception.
	 */
	public BeanModificationException(final String message) {
		super(message);
	}

}
