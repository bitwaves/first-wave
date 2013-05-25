package ch.bitwave.mim.canvas.strategies;

import java.awt.Color;

import ch.bitwave.mim.m2.core.MimElement;

/**
 * Draws stubs in light red, regular elements in white instead of the regular
 * color.
 */
public class ColoringStubsStrategy extends ColoringStrategy {
	private static final Color STUB_BG = new Color(1.0f, 0.8f, 0.8f);

	@Override
	public Color getColor(final Object element) {
		if (element instanceof MimElement) {
			MimElement me = (MimElement) element;
			if (me.isStub()) {
				return STUB_BG;
			}
		}
		return Color.WHITE;
	}
}
