package ch.parametrix.common.util.ui.swing.contracts;


/**
 * A change manager for test cases. Works on a "domain model" consisting of a
 * list of strings, with SampleChange as changes.
 * 
 */
public class SampleChangeManager<T> extends AbstractChangeManager<T> {

	public SampleChangeManager(final T model) {
		super(model);
	}

}
