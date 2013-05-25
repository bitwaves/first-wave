package ch.parametrix.common.util.ui.swing.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

/**
 * Layout manager for a property editor featuring two columns, a label column on
 * the left and a value column on the right.
 * 
 * @author bitwave
 * @see http://taubler.com/articles/
 *      article_Create_a_Custom_Swing_AWT_Layout_Manager_in_Java?id=3
 */
public class PropertyLayout implements LayoutManager {
	private int[] prefWidth = new int[2];
	private int prefHeight;
	private int minWidth;
	private int minHeight;
	private int insetWidth;
	private int padv = 5;
	private int boundaryMargin = 10;
	private int prefEditWidth = 80;
	private int indents[] = new int[] { 10, 0 };

	public PropertyLayout() {

	}

	public PropertyLayout(final int prefEditWidth) {
		this.prefEditWidth = prefEditWidth;
	}

	// required but unused
	@Override
	public void addLayoutComponent(final String name, final Component comp) {
	}

	// required but unused
	@Override
	public void removeLayoutComponent(final Component comp) {
	}

	@Override
	public void layoutContainer(final Container parent) {
		setSizes(parent);
		Insets insets = parent.getInsets();
		int padLeft = insets.left;
		int padRight = insets.right;
		int y = insets.top;

		int ncomponents = parent.getComponentCount();
		int clientWidth = parent.getBounds().width;
		for (int i = 0; i < ncomponents; i += 2) {
			Component label = parent.getComponent(i);
			Component edit = parent.getComponent(i + 1);
			int h1 = placeLabel(padLeft, padRight, clientWidth, y, label, 0);
			int h2 = placeEdit(padLeft, padRight, clientWidth, y, edit, 1);
			y += Math.max(h1, h2) + this.padv;
		}
	}

	@Override
	public Dimension minimumLayoutSize(final Container parent) {
		setSizes(parent);
		Dimension dim = new Dimension(this.minWidth, this.minHeight);
		return dim;
	}

	@Override
	public Dimension preferredLayoutSize(final Container parent) {
		setSizes(parent);
		Dimension dim = new Dimension(getPreferredContentWidth(), this.prefHeight);
		return dim;
	}

	private void setSizes(final Container parent) {
		int nComps = parent.getComponentCount();
		if (nComps % 2 != 0) {
			throw new RuntimeException("The number of controls in a property layout must be even.");
		}

		// reset widths and heights
		this.prefWidth[0] = 30;
		this.prefWidth[1] = this.prefEditWidth;
		this.prefHeight = 0;
		this.minWidth = 0;
		this.minHeight = 0;

		Insets insets = parent.getInsets();
		int x = insets.left;
		int x2 = insets.right;
		this.insetWidth = x2 + x;
		for (int i = 0; i < nComps; i += 2) {
			Component label = parent.getComponent(i);
			Component edit = parent.getComponent(i + 1);
			Dimension labelDim = null;
			Dimension editDim = null;
			if (label.isVisible()) {
				labelDim = layoutControl(label, 0);
			}
			if (label.isVisible()) {
				editDim = layoutControl(edit, 1);
			}
			this.prefHeight += Math.max(labelDim == null ? 0 : labelDim.height, editDim == null ? 0
					: editDim.height);
			this.prefHeight += this.padv;
		}
		this.prefHeight += insets.top + insets.bottom;
		this.minHeight = this.prefHeight;
		this.minWidth = getPreferredContentWidth();
	}

	private int getPreferredContentWidth() {
		return this.prefWidth[0] + this.prefWidth[1] + this.insetWidth + this.boundaryMargin;
	}

	private Dimension layoutControl(final Component comp, final int columnIndex) {
		Dimension d = comp.getPreferredSize();
		if (d == null) {
			d = comp.getMinimumSize();
		}
		int thisWidth = Math.min(200, d.width + this.indents[columnIndex]);
		int colWidth = this.prefWidth[columnIndex];
		if (thisWidth > colWidth) {
			this.prefWidth[columnIndex] = thisWidth;
		}
		return d;
	}

	private int placeLabel(final int padLeft, final int padRight, final int clientWidth,
			final int y, final Component label, final int columnIndex) {
		Dimension d = label.getPreferredSize();
		if (d == null) {
			d = label.getMinimumSize();
		}
		int w = 0;
		int h = 0;
		if (d != null) {
			w = d.width;
			h = d.height;
		}
		w = this.prefWidth[columnIndex];
		int x = padLeft + this.indents[columnIndex];
		if (columnIndex == 1) {
			x += this.prefWidth[0] + this.boundaryMargin;
		}
		label.setBounds(x, y, w, h);
		return h;
	}

	private int placeEdit(final int padLeft, final int padRight, final int clientWidth,
			final int y, final Component edit, final int columnIndex) {
		int columnClientWidth = clientWidth - this.prefWidth[0] - padLeft - padRight
				- this.boundaryMargin - 30;
		Dimension d = edit.getPreferredSize();
		if (d == null) {
			d = edit.getMinimumSize();
		}
		int w = 0;
		int h = 0;
		if (d != null) {
			w = d.width;
			h = d.height;
		}
		w = columnClientWidth;
		int x = padLeft + this.indents[columnIndex];
		if (columnIndex == 1) {
			x += this.prefWidth[0] + this.boundaryMargin;
		}
		edit.setBounds(x, y, w, h);
		return h;
	}
}
