package ch.parametrix.common.util.ui.swing.binding;

import org.apache.commons.lang.StringUtils;

/**
 * Converts to a model value of type string, ensuring that the string is not
 * empty.
 * 
 */
public class StringValueConverter extends ValueConverter {

	@Override
	public Object convertTargetToModel(final Object targetValue) throws ConversionException {

		if (targetValue == null || StringUtils.isEmpty(targetValue.toString())) {
			throw new ConversionException("The value must not be empty.");
		}
		return targetValue.toString();
	}

	@Override
	public Object convertModelToTarget(final Object modelValue) throws ConversionException {
		if (modelValue == null) {
			return "";
		}
		return modelValue.toString();
	}

}
