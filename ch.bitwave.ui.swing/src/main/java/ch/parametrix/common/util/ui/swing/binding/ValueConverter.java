package ch.parametrix.common.util.ui.swing.binding;

/**
 * Converts between the data type of a model attribute and the data type of the
 * representation of that attribute within a UI control.
 * 
 */
public abstract class ValueConverter {

	/**
	 * Converts the given UI value representation to the actual domain model
	 * type.
	 * 
	 * @param targetValue
	 *            the target value to convert into a model value.
	 * @return the model value corresponding to the given target value.
	 * @throws ConversionException
	 *             if the conversion is not possible. The binding mechanism will
	 *             present the exception text as feedback to the user. The
	 *             method must throw such an exception if conversion failed, and
	 *             ONLY such an exception.
	 */
	public abstract Object convertTargetToModel(final Object targetValue)
			throws ConversionException;

	/**
	 * Converts the given domain model value to the data type required by the UI
	 * control.
	 * 
	 * @param modelValue
	 *            the model value to convert to a target value.
	 * @return the target value corresponding to the given model value.
	 * @throws ConversionException
	 *             if the conversion is not possible. The binding mechanism will
	 *             present the exception text as feedback to the user. The
	 *             method must throw such an exception if conversion failed, and
	 *             ONLY such an exception.
	 */
	public abstract Object convertModelToTarget(final Object modelValue) throws ConversionException;
}
