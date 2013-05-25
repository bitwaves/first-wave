package ch.bitwave.mim.canvas.strategies;

import java.util.List;

import ch.bitwave.mim.canvas.features.ConnectionNodeFeature;
import ch.bitwave.mim.canvas.features.NodeFeature;

public abstract class ConnectorLayoutStrategy {

	public abstract void layoutConnectorNodes(final NodeFeature node,
			List<ConnectionNodeFeature> outboundConnectors,
			List<ConnectionNodeFeature> inboundConnectors);

}
