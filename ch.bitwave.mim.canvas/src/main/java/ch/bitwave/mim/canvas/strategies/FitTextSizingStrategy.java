package ch.bitwave.mim.canvas.strategies;

import java.awt.Graphics2D;

import ch.bitwave.mim.m2.core.NamedElement;
import ch.parametrix.common.util.ui.swing.TextRenderer;
import ch.parametrix.common.util.ui.swing.contracts.WorldDistance;

public class FitTextSizingStrategy extends SizingStrategy {

	@Override
	public WorldDistance getSize(final Graphics2D g, final Object element) {
		TextRenderer tr = new TextRenderer(g, 0, 0, 2000, false);
		if (element instanceof NamedElement) {
			NamedElement ne = (NamedElement) element;
			int width = tr.measureWidth(g, ne.getName());
			return new WorldDistance(Math.max(500, width * 2), 300);
		}
		return new WorldDistance(1500, 300);
	}
}
