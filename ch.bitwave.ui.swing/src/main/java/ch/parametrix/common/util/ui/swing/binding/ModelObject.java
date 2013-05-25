package ch.parametrix.common.util.ui.swing.binding;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ModelObject implements IModelObject
{
	private final PropertyChangeSupport pcs;

	public ModelObject()
	{
		this.pcs = new PropertyChangeSupport(this);
	}

	@Override
	public void addPropertyChangeListener(final PropertyChangeListener listener)
	{
		this.pcs.addPropertyChangeListener(listener);
	}

	@Override
	public void removePropertyChangeListener(final PropertyChangeListener listener)
	{
		this.pcs.removePropertyChangeListener(listener);
	}

	protected PropertyChangeSupport getPCS()
	{
		return this.pcs;
	}

	/**
	 * The property change listeners of the model object are exposed only for testing purposes and should never be manipulated directly.
	 */
	public PropertyChangeListener[] getPropertyChangeListeners()
	{
		return this.pcs.getPropertyChangeListeners();
	}
}
