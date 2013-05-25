package ch.parametrix.common.util.ui.swing.editor;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;

import ch.parametrix.common.util.ui.swing.binding.EnumerationBindingDefinition;

/**
 * A table cell editor for enumerations of model objects.
 */
public class ComboboxElementCellEditor extends DefaultCellEditor
{
	private static final long serialVersionUID = 1L;

	public ComboboxElementCellEditor(final EnumerationBindingDefinition binding)
	{
		super(new JComboBox(binding));
	}

}
