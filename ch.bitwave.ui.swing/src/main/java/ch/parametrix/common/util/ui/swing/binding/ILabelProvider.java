package ch.parametrix.common.util.ui.swing.binding;

import javax.annotation.Nonnull;

/**
 * Extension for a model object capable of providing its own label. Used to
 * generically represent objects in enumerations.
 * 
 */
public interface ILabelProvider {
	/**
	 * Returns the label of this object.
	 * 
	 * @return the label.
	 */
	@Nonnull
	String getLabel();
}
