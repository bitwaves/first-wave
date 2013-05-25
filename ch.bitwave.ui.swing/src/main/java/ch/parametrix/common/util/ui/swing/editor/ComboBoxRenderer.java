package ch.parametrix.common.util.ui.swing.editor;

import java.awt.Component;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 * Renders the contents of a combobox.
 * 
 */
public class ComboBoxRenderer extends JComboBox implements TableCellRenderer {
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new renderer for the given model.
	 * 
	 * @param bindingDef
	 */
	public ComboBoxRenderer(final ComboBoxModel bindingDef) {
		super(bindingDef);
	}

	@Override
	public Component getTableCellRendererComponent(final JTable table, final Object value,
			final boolean isSelected, final boolean hasFocus, final int row, final int column) {
		if (isSelected) {
			// Changing the foreground looks bad (white on gray), so let's not
			// do that.
			// setForeground(table.getSelectionForeground());
			super.setBackground(table.getSelectionBackground());
		} else {
			// setForeground(table.getForeground());
			setBackground(table.getBackground());
		}
		// Select the current value
		setSelectedItem(value);
		return this;
	}
}
