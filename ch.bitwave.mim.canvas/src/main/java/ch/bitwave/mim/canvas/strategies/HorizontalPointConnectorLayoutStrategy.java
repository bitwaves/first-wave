package ch.bitwave.mim.canvas.strategies;

import java.awt.geom.Point2D;
import java.util.List;

import ch.bitwave.mim.canvas.features.ConnectionNodeFeature;
import ch.bitwave.mim.canvas.features.NodeFeature;

/**
 * Arranges the inbound and outbound connectors of a node in a left-to-right
 * layout. All inbound connections originate in the same point, as do all
 * outbound connections.
 */
public class HorizontalPointConnectorLayoutStrategy extends ConnectorLayoutStrategy {

	@Override
	public void layoutConnectorNodes(final NodeFeature node,
			final List<ConnectionNodeFeature> outboundConnectors,
			final List<ConnectionNodeFeature> inboundConnectors) {
		Point2D anchor = node.getAnchor();
		double x = anchor.getX();
		double y = anchor.getY() + node.getSize().getDy() / 2;
		for (ConnectionNodeFeature cn : inboundConnectors) {
			cn.place(x, y);
		}
		x += node.getSize().getDx();
		for (ConnectionNodeFeature cn : outboundConnectors) {
			cn.place(x, y);
		}
	}

}
