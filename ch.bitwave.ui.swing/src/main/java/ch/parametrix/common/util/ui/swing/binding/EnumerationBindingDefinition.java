package ch.parametrix.common.util.ui.swing.binding;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

/**
 * Definition of a static enumeration binding, which is used for enumerations that do not change over time.
 */
public class EnumerationBindingDefinition extends BindingDefinition implements ComboBoxModel
{
	private List<? extends IModelObject> enumValues;
	private Object selected;
	private final List<ListDataListener> listeners;

	public EnumerationBindingDefinition(final String attributeName, final String label, final ValueConverter converter,
			final List<? extends IModelObject> enumValues, final boolean editable)
	{
		super(attributeName, converter, editable, label);
		this.enumValues = enumValues;
		this.listeners = new ArrayList<ListDataListener>();
	}

	public List<? extends IModelObject> getEnumValues()
	{
		return this.enumValues;
	}

	@Override
	public boolean isEditable()
	{
		return super.isEditable();
	}

	protected void setEnumValues(final List<? extends IModelObject> components)
	{
		this.enumValues = components;
	}

	@Override
	public boolean isEnumeration()
	{
		return true;
	}

	@Override
	public void addListDataListener(final ListDataListener arg0)
	{
		this.listeners.add(arg0);
	}

	@Override
	public Object getElementAt(final int index)
	{
		return this.enumValues.get(index);
	}

	@Override
	public int getSize()
	{
		return this.enumValues.size();
	}

	@Override
	public void removeListDataListener(final ListDataListener arg0)
	{
		this.listeners.remove(arg0);
	}

	@Override
	public Object getSelectedItem()
	{
		return this.selected;
	}

	@Override
	public void setSelectedItem(final Object arg0)
	{
		this.selected = arg0;
	}

	@Override
	public void unbind()
	{
		super.unbind();
		this.listeners.clear();
	}

	protected void fireEnumerationChanged()
	{
		for (ListDataListener listener : this.listeners)
		{
			listener.contentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, 0, this.enumValues.size() - 1));
		}
	}

}
