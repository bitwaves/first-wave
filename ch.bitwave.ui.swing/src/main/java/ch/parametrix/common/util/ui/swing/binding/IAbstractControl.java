package ch.parametrix.common.util.ui.swing.binding;

/**
 * Provides generic access to a control holding a single-object value.
 * 
 */
public interface IAbstractControl {
	/**
	 * Sets the control value to the given object, which is of a type compatible
	 * with the control.
	 * 
	 * @param value
	 *            the value to set.
	 */
	public void setValue(Object value);

	/**
	 * Reads the current control value, which is given in the native data type
	 * of the control.
	 * 
	 * @return the current control value.
	 */
	public Object getValue();
}
