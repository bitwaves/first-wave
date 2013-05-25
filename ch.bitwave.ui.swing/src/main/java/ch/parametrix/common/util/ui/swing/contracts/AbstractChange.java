package ch.parametrix.common.util.ui.swing.contracts;

import javax.annotation.Nonnull;

/**
 * The abstract base class for describing changes made to a domain model.
 * 
 */
public abstract class AbstractChange<T> {

	/**
	 * Applies the change to the given model.
	 * 
	 * @param model
	 */
	public abstract void applyTo(@Nonnull T model);

}
