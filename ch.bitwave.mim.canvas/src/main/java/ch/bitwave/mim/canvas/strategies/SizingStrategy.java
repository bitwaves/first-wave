package ch.bitwave.mim.canvas.strategies;

import java.awt.Graphics2D;

import ch.parametrix.common.util.ui.swing.contracts.WorldDistance;

public abstract class SizingStrategy {

	public abstract WorldDistance getSize(final Graphics2D g, final Object element);

}
