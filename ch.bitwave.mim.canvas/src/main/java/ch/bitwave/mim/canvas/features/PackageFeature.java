package ch.bitwave.mim.canvas.features;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import ch.bitwave.mim.m2.core.MimElement;
import ch.bitwave.mim.m2.core.NamedElement;
import ch.parametrix.common.util.ui.swing.TextRenderer;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeatureProvider;
import ch.parametrix.common.util.ui.swing.contracts.WorldDistance;

public class PackageFeature extends NodeFeature {

	private static final Color STUB_BG = new Color(1.0f, 0.8f, 0.8f);
	private static final Color PACKAGE_BG = new Color(0.8f, 0.85f, 1.0f);

	public PackageFeature(final Object element) {
		super(element);
	}

	@Override
	public String getIdentifier() {
		return null;
	}

	@Override
	protected WorldDistance getInitialSize() {
		return new WorldDistance(1000, 600);
	}

	private void renderLabel(final MimElement elem, final Graphics2D g, final TextRenderer tr) {
		if (elem instanceof NamedElement) {
			g.setColor(Color.BLACK);
			tr.setFontStyle(Font.BOLD);
			String name = ((NamedElement) elem).getName();
			tr.renderText(name);
		}
	}

	@Override
	protected void renderNode(final ICanvasFeatureProvider provider, final Graphics2D g,
			final int ax, final int ay, final Color color, final boolean isSelected) {
		MimElement elem = (MimElement) getElement();
		int w = getWidth();
		int h = getHeight();
		if (elem.isStub()) {
			g.setColor(STUB_BG);
		} else {
			g.setColor(PACKAGE_BG);
		}
		g.fillRect(ax, ay, w, h);
		TextRenderer tr = new TextRenderer(g, ax + 50, ay + 200, w - 100, false);
		renderLabel(elem, g, tr);
		g.setColor(Color.BLACK);
		g.drawRect(ax, ay, w, h);
		setBounds(ax, ay, w, h);
	}

}
