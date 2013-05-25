package ch.parametrix.common.util.ui.swing.binding;

import ch.bitwave.platform.reflect.BeanModificationException;
import ch.bitwave.platform.reflect.ReflectionUtility;


/**
 * Data bindings are used in cases where the binding is not immediate but added at a later time, i.e. in tables where records are added dynamically
 * and the same binding types apply to each record in the table.
 */
public class BindingDefinition extends ModelObject
{
	private final String attributeName;
	private final ValueConverter converter;
	private final boolean editable;
	private final String label;

	public BindingDefinition(final String attributeName, final ValueConverter converter, final boolean editable, final String label)
	{
		super();
		this.attributeName = attributeName;
		this.converter = converter;
		this.editable = editable;
		this.label = label;
	}

	public String getAttributeName()
	{
		return this.attributeName;
	}

	public ValueConverter getConverter()
	{
		return this.converter;
	}

	public Object getConvertedModelValue(final IModelObject model) throws BeanModificationException
	{
		Object value = getRawModelValue(model);
		if (this.converter != null)
		{
			try
			{
				value = this.converter.convertModelToTarget(value);
			}
			catch (ConversionException e)
			{
				value = null;
			}
		}
		return value;
	}

	protected Object getRawModelValue(final IModelObject model) throws BeanModificationException
	{
		return ReflectionUtility.getBeanAttribute(model, this.attributeName);
	}

	protected void setRawModelValue(final IModelObject model, final Object modelValue) throws BeanModificationException
	{
		ReflectionUtility.setBeanAttribute(model, this.attributeName, modelValue);
	}

	public boolean isEditable()
	{
		return this.editable;
	}

	public String getLabel()
	{
		return this.label;
	}

	public void setObjectValue(final IModelObject obj, final Object targetValue) throws BeanModificationException
	{
		Object value = targetValue;
		if (this.converter != null)
		{
			try
			{
				value = this.converter.convertTargetToModel(targetValue);
			}
			catch (ConversionException e)
			{
				value = null;
			}
		}
		setRawModelValue(obj, value);
	}

	public boolean isEnumeration()
	{
		return false;
	}

	public void unbind()
	{

	}
}
