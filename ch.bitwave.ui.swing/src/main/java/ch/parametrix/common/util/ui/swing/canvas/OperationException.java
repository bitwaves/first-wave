package ch.parametrix.common.util.ui.swing.canvas;

/**
 * Signals that there was a problem performing a user operation. The exception message details the exact problem in a user-presentable fashion.
 */
public class OperationException extends Exception
{
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new OperationException with the given message.
	 * 
	 * @param message
	 *            the exception message.
	 */
	public OperationException(final String message)
	{
		super(message);
	}

}
