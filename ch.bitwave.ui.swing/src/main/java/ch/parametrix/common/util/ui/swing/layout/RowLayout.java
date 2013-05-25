package ch.parametrix.common.util.ui.swing.layout;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager;

/**
 * A layout manager for arranging components in rows, with a single component on
 * each row.
 * 
 * @author bitwave
 */
public class RowLayout implements LayoutManager {

	private int prefWidth;
	private int prefHeight;
	private int minWidth;
	private int minHeight;
	private int rowGap = 0;
	private boolean stretch;

	public RowLayout() {
	}

	public RowLayout(final boolean stretchComponents) {
		this.stretch = stretchComponents;
	}

	public RowLayout(final int gapSize) {
		setRowGap(gapSize);
	}

	public RowLayout(final int gapSize, final boolean stretchComponents) {
		this.stretch = stretchComponents;
		setRowGap(gapSize);
	}

	public void setRowGap(final int gapSize) {
		this.rowGap = gapSize;
	}

	public void setStretch(final boolean stretchComponents) {
		this.stretch = stretchComponents;
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
		int x = insets.left;
		int y = insets.top;
		int x2 = insets.right;
		int wDiff = x + x2;

		int ncomponents = parent.getComponentCount();
		for (int i = 0; i < ncomponents; ++i) {
			java.awt.Component comp = parent.getComponent(i);
			java.awt.Dimension d = comp.getPreferredSize();
			if (d == null) {
				d = comp.getMinimumSize();
			}
			int w = 0;
			int h = 0;
			if (d != null) {
				w = d.width;
				h = d.height;
			}
			if (this.stretch) {
				w = this.prefWidth;
			}
			if (this.prefWidth - wDiff < w) {
				w = this.prefWidth - wDiff;
			}
			comp.setBounds(x, y, w, h);
			y += h + this.rowGap;
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
		Dimension dim = new Dimension(this.prefWidth, this.prefHeight);
		return dim;
	}

	private void setSizes(final Container parent) {
		int nComps = parent.getComponentCount();
		Dimension d = null;

		// reset widths and heights
		this.prefWidth = 0;
		this.prefHeight = 0;
		this.minWidth = 0;
		this.minHeight = 0;

		Insets insets = parent.getInsets();
		int x = insets.left;
		int x2 = insets.right;
		int wDiff = x2 + x;

		this.prefWidth += wDiff;

		for (int i = 0; i < nComps; i++) {
			Component c = parent.getComponent(i);
			if (c.isVisible()) {
				d = c.getPreferredSize();
				if (d == null) {
					d = c.getMinimumSize();
				}
				int thisWidth = d.width;
				if (thisWidth > this.prefWidth) {
					this.prefWidth = thisWidth + wDiff;
				}
				this.prefHeight += d.height;
				this.prefHeight += this.rowGap;
			}
		} // end for

		this.prefHeight += insets.top + insets.bottom;
		this.minHeight = this.prefHeight;
		this.minWidth = this.prefWidth;
	}

}