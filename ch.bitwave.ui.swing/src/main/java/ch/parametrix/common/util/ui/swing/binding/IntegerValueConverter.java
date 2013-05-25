package ch.parametrix.common.util.ui.swing.binding;

/**
 * 
 */
public class IntegerValueConverter extends ValueConverter {

	@Override
	public Object convertTargetToModel(final Object targetValue) throws ConversionException {
		if (targetValue == null) {
			throw new ConversionException("The value must not be null.");
		}
		try {
			return Integer.parseInt(targetValue.toString());
		} catch (NumberFormatException e) {
			throw new ConversionException(String.format("\"%s\" is not an integer number.",
					targetValue));
		}
	}

	@Override
	public Object convertModelToTarget(final Object modelValue) throws ConversionException {
		if (modelValue == null) {
			return "";
		}
		return modelValue.toString();
	}

}
