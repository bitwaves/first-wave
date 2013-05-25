package ch.parametrix.common.util.ui.swing.binding;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JComponent;

public abstract class DataBinding
{
	private JComponent container;
	private IModelObject model;
	private ValueConverter converter;

	protected DataBinding()
	{

	}

	/**
	 * Creates a binding for the given UI container.
	 * 
	 * @param container
	 *            the UI container to which the binding belongs. The container governs the scope of the binding, so the binding will be disposed if
	 *            the container is removed.
	 * @param model
	 *            the model to bind. Values are transferred from the model to the UI, and vice versa.
	 * @param conv
	 *            the type converter to use in the process of transferring a value from the model to the UI, and vice versa.
	 */
	public DataBinding(@Nonnull final JComponent container, @Nonnull final IModelObject model, @Nullable final ValueConverter conv)
	{
		super();
		this.container = container;
		this.model = model;
		this.converter = conv;
	}

	public JComponent getContainer()
	{
		return this.container;
	}

	public IModelObject getModel()
	{
		return this.model;
	}

	protected ValueConverter getConverter()
	{
		return this.converter;
	}

	public abstract void unbind();

	public void initialize()
	{
	}

	/**
	 * Returns an issue message related to the binding. Returns null by default.
	 * 
	 * @return the issue message, or null if there is none.
	 */
	@CheckForNull
	public String getIssue()
	{
		return null;
	}

}
