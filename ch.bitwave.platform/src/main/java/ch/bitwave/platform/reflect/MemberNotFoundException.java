package ch.bitwave.platform.reflect;

/**
 * Exception indicating that an expected member of a class was not found.
 */
public class MemberNotFoundException extends BeanModificationException {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a member not found exception with the given error message and
	 * cause.
	 * 
	 * @param message
	 *            additional information related to context of the failure.
	 * @param cause
	 *            the caused of the error.
	 */
	public MemberNotFoundException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * Creates a member not found exception with the given message.
	 * 
	 * @param message
	 *            the reason for throwing the exception.
	 */
	public MemberNotFoundException(final String message) {
		super(message);
	}

}
