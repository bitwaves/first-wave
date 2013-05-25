package ch.bitwave.mim.canvas.features;

import java.awt.Color;
import java.awt.Graphics2D;

import ch.parametrix.common.util.ui.swing.contracts.Feature;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeatureProvider;
import ch.parametrix.common.util.ui.swing.contracts.WorldDistance;

public class ConnectionNodeFeature extends Feature {

	public ConnectionNodeFeature(final Object element) {
		super(element);
	}

	@Override
	public String getIdentifier() {
		return null;
	}

	@Override
	protected WorldDistance getInitialSize() {
		return new WorldDistance(0, 0);
	}

	@Override
	protected void render(final ICanvasFeatureProvider provider, final Graphics2D g, final int ax,
			final int ay, final Color featureColor, final boolean isSelected) {
		// Connector nodes are not visible themselves.
		setBounds(ax, ay, getWidth(), getHeight());
	}

}
