package ch.bitwave.mim.canvas.strategies;

import java.awt.geom.Point2D;
import java.util.List;

import ch.bitwave.mim.canvas.features.ConnectionNodeFeature;
import ch.bitwave.mim.canvas.features.NodeFeature;

/**
 * Arranges the inbound and outbound connectors of a node in a top-to-bottom
 * layout. The inbound and outbound connectors are fanned out along a certain
 * percentage of the node width to make them more easily discernible.
 */
public class VerticalFannedConnectorLayoutStrategy extends ConnectorLayoutStrategy {

	/**
	 * Specifies the factor of the total height of the node occupied by the
	 * connector fan.
	 */
	private static final double FAN_EXTENT_FACTOR = 0.6;
	private static final double NON_FAN = 1 - FAN_EXTENT_FACTOR;

	@Override
	public void layoutConnectorNodes(final NodeFeature node,
			final List<ConnectionNodeFeature> outboundConnectors,
			final List<ConnectionNodeFeature> inboundConnectors) {
		Point2D anchor = node.getAnchor();
		double w = node.getSize().getDx();
		double y = anchor.getY();
		layoutConnectors(inboundConnectors, anchor, w, y);
		y += node.getSize().getDy();
		layoutConnectors(outboundConnectors, anchor, w, y);
	}

	private void layoutConnectors(final List<ConnectionNodeFeature> connectors,
			final Point2D anchor, final double w, final double y) {
		int numConnectors = connectors.size();
		if (numConnectors > 0) {
			double x = calcFanStart(anchor.getX(), w, numConnectors);
			double dx = (w * FAN_EXTENT_FACTOR) / (numConnectors - 1);
			for (ConnectionNodeFeature cn : connectors) {
				cn.place(x, y);
				x += dx;
			}
		}
	}

	private double calcFanStart(final double t, final double d, final int size) {
		if (size == 1) {
			return t + d / 2.0;
		}
		return t + (NON_FAN * d) / 2;
	}
}
