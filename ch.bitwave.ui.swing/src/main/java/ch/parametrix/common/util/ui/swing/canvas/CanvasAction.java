package ch.parametrix.common.util.ui.swing.canvas;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import ch.bitwave.platform.codestyle.DBC;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeatureProvider;

/**
 * An action performed in the context of a canvas. Maintains a reference to a feature provider which can be used to look up the current feature
 * selection.
 */
public abstract class CanvasAction extends AbstractAction
{

	private static final long serialVersionUID = 1L;
	private ICanvasFeatureProvider provider;

	protected CanvasAction(final String label, final ICanvasFeatureProvider provider)
	{
		super(label);
		DBC.PRE.assertNotNull(provider, "The feature provider must not be null."); //$NON-NLS-1$
		this.provider = provider;
	}

	@Override
	public final void actionPerformed(final ActionEvent e)
	{
		perform(e, this.provider);
	}

	/**
	 * Returns a reference to the feature provider to which this action is tied.
	 * 
	 * @return the provider.
	 */
	protected ICanvasFeatureProvider getProvider()
	{
		return this.provider;
	}

	/**
	 * @param e
	 *            the triggering action event.
	 * @param provider
	 *            the feature provider the action operates on.
	 */
	protected abstract void perform(ActionEvent e, ICanvasFeatureProvider provider);

}
