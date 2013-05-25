package ch.parametrix.common.util.ui.swing.binding;

import java.awt.Component;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import ch.bitwave.platform.reflect.ReflectionUtility;

/**
 * Binding for enumeration properties.
 * 
 */
public class BiDiEnumerationBinding extends BiDiDataBinding
{
	private final List<? extends Object> options;
	DefaultComboBoxModel targetModel;

	public BiDiEnumerationBinding(final JComponent container, final IModelObject model, final Component control, final String propertyName,
			final ValueConverter conv, final List<? extends Object> options)
	{
		super(container, model, control, propertyName, conv);
		this.options = options;
		this.targetModel = new DefaultComboBoxModel();
		repopulateTargetModel(options);
	}

	private void repopulateTargetModel(final List<? extends Object> options)
	{
		this.targetModel.removeAllElements();
		for (Object component : options)
		{
			String label;
			if (component instanceof ILabelProvider)
			{
				label = ((ILabelProvider) component).getLabel();
			}
			else
			{
				label = component.toString();
			}
			this.targetModel.addElement(label);
		}
	}

	@Override
	protected void modelToTarget()
	{
		JComboBox edit = (JComboBox) this.control;
		edit.setModel(this.targetModel);
		try
		{
			Object value = ReflectionUtility.getBeanAttribute(getModel(), getPropertyName());
			if (getConverter() != null)
			{
				value = getConverter().convertModelToTarget(value);
			}
			int index = this.options.indexOf(value);
			edit.setSelectedIndex(index);
			clearError();
		}
		catch (Throwable t)
		{
			String message = getErrorMessage(t);
			setError(message);
		}
	}

	@Override
	protected void targetToModel()
	{
		JComboBox edit = (JComboBox) this.control;
		int index = edit.getSelectedIndex();
		try
		{
			if (getConverter() != null)
			{
				index = (Integer) getConverter().convertTargetToModel(index);
			}
			Object value = null;
			if (index >= 0 && index < this.options.size())
			{
				value = this.options.get(index);
			}
			ReflectionUtility.setBeanAttribute(getModel(), getPropertyName(), value);
			clearError();
		}
		catch (Throwable t)
		{
			String message = getErrorMessage(t);
			setError(message);
		}
	}

	public void updateOptionsModel(final List<? extends Object> options)
	{
		repopulateTargetModel(options);
	}
}
