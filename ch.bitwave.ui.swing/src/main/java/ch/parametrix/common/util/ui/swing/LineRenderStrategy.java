package ch.parametrix.common.util.ui.swing;

import java.awt.Graphics2D;

public abstract class LineRenderStrategy {
	public abstract void renderLine(Graphics2D g, TextRenderer tr, String line, int x, int y);
}
