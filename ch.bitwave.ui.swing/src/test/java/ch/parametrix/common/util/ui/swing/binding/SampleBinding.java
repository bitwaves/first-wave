package ch.parametrix.common.util.ui.swing.binding;

import javax.swing.JComponent;

/**
 * A sample binding for testing the binding context.
 * 
 */
public class SampleBinding extends DataBinding {
	private String issue;
	private boolean unbound;

	/**
	 * @param container
	 * @param model
	 * @param conv
	 */
	public SampleBinding() {
	}

	public SampleBinding(final JComponent comp) {
		super(comp, null, null);
	}

	/**
	 * @return the issue
	 */
	@Override
	public String getIssue() {
		return this.issue;
	}

	/**
	 * @param issue
	 *            the issue to set
	 */
	public void setIssue(final String issue) {
		this.issue = issue;
	}

	@Override
	public void unbind() {
		this.unbound = true;
	}

	/**
	 * Tells whether the sample binding has been unbound via {@link #unbind()}.
	 * 
	 * @return the unbound
	 */
	public boolean isUnbound() {
		return this.unbound;
	}

}
