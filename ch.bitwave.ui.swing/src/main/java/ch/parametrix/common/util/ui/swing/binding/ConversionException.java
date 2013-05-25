package ch.parametrix.common.util.ui.swing.binding;

/**
 * Signals that a value converter failed to convert a provided value.
 * 
 */
public class ConversionException extends Exception {
	/**
	 * @param message
	 *            the message detailing the conversion problem.
	 */
	public ConversionException(final String message) {
		super(message);
	}

	private static final long serialVersionUID = 1L;

}
