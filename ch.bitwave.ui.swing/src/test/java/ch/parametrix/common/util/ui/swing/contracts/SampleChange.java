package ch.parametrix.common.util.ui.swing.contracts;

import ch.bitwave.platform.prose.IProseBuilder;

/**
 * 
 */
public class SampleChange extends AbstractChange<IProseBuilder> {
	private final String action;

	/**
	 * @param action
	 */
	public SampleChange(final String action) {
		super();
		this.action = action;
	}

	/**
	 * @return the action
	 */
	public String getAction() {
		return this.action;
	}

	@Override
	public void applyTo(final IProseBuilder model) {
		model.appendLine(this.action);
	}

}
