package ch.parametrix.common.util.ui.swing.binding;

import java.beans.PropertyChangeListener;

import javax.swing.JComponent;

public class EventBinding extends DataBinding
{
	private final PropertyChangeListener listener;

	public EventBinding(final JComponent container, final IModelObject model, final PropertyChangeListener listener)
	{
		super(container, model, null);
		this.listener = listener;
		model.addPropertyChangeListener(listener);
	}

	@Override
	public void unbind()
	{
		this.getModel().removePropertyChangeListener(this.listener);
	}

}
