package ch.bitwave.mim.canvas.features;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;

import ch.parametrix.common.util.ui.swing.contracts.Feature;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeatureProvider;
import ch.parametrix.common.util.ui.swing.contracts.WorldDistance;

public class ConnectionFeature extends Feature {

	private ConnectionNodeFeature sourceFeature;
	private ConnectionNodeFeature targetFeature;
	protected static final Stroke CONNECTION_STROKE = new BasicStroke(2.5f);

	public ConnectionFeature(final Object element) {
		super(element);
		this.sourceFeature = new ConnectionNodeFeature(element);
		this.targetFeature = new ConnectionNodeFeature(element);
	}

	public ConnectionNodeFeature getSourceFeature() {
		return this.sourceFeature;
	}

	public ConnectionNodeFeature getTargetFeature() {
		return this.targetFeature;
	}

	@Override
	public String getIdentifier() {
		return null;
	}

	@Override
	protected WorldDistance getInitialSize() {
		return new WorldDistance(100, 100);
	}

	@Override
	protected void render(final ICanvasFeatureProvider provider, final Graphics2D g, final int ax,
			final int ay, final Color featureColor, final boolean isSelected) {
		Rectangle2D bounds1 = this.sourceFeature.getBounds();
		int x1 = (int) bounds1.getCenterX();
		int y1 = (int) bounds1.getCenterY();
		Rectangle2D bounds2 = this.targetFeature.getBounds();
		int x2 = (int) bounds2.getCenterX();
		int y2 = (int) bounds2.getCenterY();
		g.setStroke(CONNECTION_STROKE);
		g.drawLine(x1, y1, x2, y2);
	}

}
