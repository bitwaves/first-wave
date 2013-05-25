package ch.parametrix.common.util.ui.swing.binding;

import java.util.List;

import javax.swing.JComponent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import ch.bitwave.platform.reflect.BeanModificationException;

public class TableBinding extends DataBinding implements TableModel
{

	private final List<? extends IModelObject> objects;
	private final List<BindingDefinition> bindingDefs;

	public TableBinding(final JComponent container, final List<? extends IModelObject> objects, final List<BindingDefinition> bindingDefs)
	{
		super(container, null, null);
		this.objects = objects;
		this.bindingDefs = bindingDefs;
	}

	@Override
	public int getColumnCount()
	{
		return this.bindingDefs.size();
	}

	@Override
	public int getRowCount()
	{
		return this.objects.size();
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex)
	{
		BindingDefinition def = this.bindingDefs.get(columnIndex);
		if (rowIndex < this.objects.size())
		{
			try
			{
				IModelObject obj = this.objects.get(rowIndex);
				return def.getConvertedModelValue(obj);
			}
			catch (BeanModificationException e)
			{
				return "invalid binding";
			}
		}
		else
		{
			return "";
		}
	}

	@Override
	public boolean isCellEditable(final int arg0, final int arg1)
	{
		return this.bindingDefs.get(arg0).isEditable();
	}

	@Override
	public void setValueAt(final Object value, final int rowIndex, final int columnIndex)
	{
		BindingDefinition def = this.bindingDefs.get(columnIndex);
		if (rowIndex < this.objects.size())
		{
			IModelObject obj = this.objects.get(rowIndex);
			try
			{
				def.setObjectValue(obj, value);
			}
			catch (BeanModificationException e)
			{
				throw new RuntimeException("Failed to apply UI to model: " + e.getMessage(), e);
			}
		}
		else
		{
			throw new RuntimeException("Editing out of scope records is not supported, yet.");
		}
	}

	@Override
	public void addTableModelListener(final TableModelListener arg0)
	{

	}

	@Override
	public Class<?> getColumnClass(final int arg0)
	{
		return String.class;
	}

	@Override
	public String getColumnName(final int arg0)
	{
		return this.bindingDefs.get(arg0).getLabel();
	}

	@Override
	public void removeTableModelListener(final TableModelListener arg0)
	{

	}

	@Override
	public void unbind()
	{
		for (BindingDefinition def : this.bindingDefs)
		{
			def.unbind();
		}
	}

}
