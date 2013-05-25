package ch.bitwave.mim.canvas.strategies;

import java.awt.geom.Point2D;
import java.util.List;

import ch.bitwave.mim.canvas.features.ConnectionNodeFeature;
import ch.bitwave.mim.canvas.features.NodeFeature;

/**
 * Arranges the inbound and outbound connectors of a node in a top-to-bottom
 * layout. All inbound connections originate in the same point, as do all
 * outbound connections.
 */
public class VerticalPointConnectorLayoutStrategy extends ConnectorLayoutStrategy {

	@Override
	public void layoutConnectorNodes(final NodeFeature node,
			final List<ConnectionNodeFeature> outboundConnectors,
			final List<ConnectionNodeFeature> inboundConnectors) {
		Point2D anchor = node.getAnchor();
		double x = anchor.getX() + node.getSize().getDx() / 2;
		double y = anchor.getY();
		for (ConnectionNodeFeature cn : inboundConnectors) {
			cn.place(x, y);
		}
		y += node.getSize().getDy();
		for (ConnectionNodeFeature cn : outboundConnectors) {
			cn.place(x, y);
		}
	}

}
