package ch.bitwave.mim.canvas.features;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import ch.bitwave.mim.m2.core.MimClass;
import ch.bitwave.mim.m2.core.MimElement;
import ch.bitwave.mim.m2.core.NamedElement;
import ch.parametrix.common.util.ui.swing.TextRenderer;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeatureProvider;
import ch.parametrix.common.util.ui.swing.contracts.WorldDistance;

public class ClassFeature extends NodeFeature {

	public ClassFeature(final Object element) {
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

	@Override
	protected void renderNode(final ICanvasFeatureProvider provider, final Graphics2D g,
			final int ax, final int ay, final Color featureColor, final boolean isSelected) {
		MimElement elem = (MimElement) getElement();
		int w = getWidth();
		int h = getHeight();
		g.setColor(featureColor);
		g.fillRect(ax, ay, w, h);
		TextRenderer tr = new TextRenderer(g, ax + 50, ay + 150, w - 100, false);
		renderLabel(elem, g, tr);
		g.setColor(Color.BLACK);
		g.setStroke(NODE_OUTLINE_STROKE);
		g.drawRect(ax, ay, w, h);
		setBounds(ax, ay, w, h);
	}

	private void renderLabel(final MimElement elem, final Graphics2D g, final TextRenderer tr) {
		if (elem instanceof NamedElement) {
			g.setColor(Color.BLACK);
			if (isAbstract(elem)) {
				tr.setFontStyle(Font.BOLD | Font.ITALIC);
			} else {
				tr.setFontStyle(Font.BOLD);
			}
			String name = ((NamedElement) elem).getName();
			tr.renderTextCentered(name);
		}
	}

	private boolean isAbstract(final MimElement elem) {
		return elem instanceof MimClass && ((MimClass) elem).isAbstract();
	}

}
