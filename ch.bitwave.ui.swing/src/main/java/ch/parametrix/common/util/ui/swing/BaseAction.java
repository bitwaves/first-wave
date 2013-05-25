package ch.parametrix.common.util.ui.swing;

import javax.swing.AbstractAction;

/**
 * An action that can be configured with a label. The label is returned as the NAME value.
 */
public abstract class BaseAction extends AbstractAction
{

	private static final long serialVersionUID = 1L;
	private String label;

	protected BaseAction(final String label)
	{
		super();
		this.label = label;
	}

	@Override
	public Object getValue(final String key)
	{
		if (key.equals(AbstractAction.NAME))
		{
			return this.label;
		}
		if (key.equals(AbstractAction.SHORT_DESCRIPTION))
		{
			return null;
		}
		if (key.equals(AbstractAction.LONG_DESCRIPTION))
		{
			return null;
		}
		return null;
	}

}
