package ch.parametrix.common.util.ui.swing.binding;

import java.beans.PropertyChangeListener;

import javax.annotation.Nonnull;

/**
 * An abstract representation of a model object which introduces property change
 * support, allowing the presentation layer to listen to changes in the object.
 */
public interface IModelObject {
	/**
	 * Adds a property change listener to the model object.
	 * 
	 * @param listener
	 *            the listener to add.
	 */
	void addPropertyChangeListener(@Nonnull PropertyChangeListener listener);

	/**
	 * Removes a property change listener from the model object.
	 * 
	 * @param listener
	 *            the listener to remove.
	 */
	void removePropertyChangeListener(@Nonnull PropertyChangeListener listener);
}
