package ch.bitwave.mim.canvas.features;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

import ch.bitwave.mim.canvas.strategies.ColoringStrategy;
import ch.bitwave.mim.canvas.strategies.ConnectorLayoutStrategy;
import ch.bitwave.mim.canvas.strategies.SizingStrategy;
import ch.parametrix.common.util.ui.swing.contracts.Feature;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeatureProvider;
import ch.parametrix.common.util.ui.swing.contracts.WorldDistance;

/**
 * A feature for representing a node within a structure, e.g. a tree.
 */
public abstract class NodeFeature extends Feature {

	private SizingStrategy sizingStrategy;
	private ColoringStrategy coloringStrategy;
	private ConnectorLayoutStrategy connectorLayoutStrategy;
	private List<ConnectionNodeFeature> outboundConnectors;
	private List<ConnectionNodeFeature> inboundConnectors;
	protected static final Stroke NODE_OUTLINE_STROKE = new BasicStroke(5f);

	public NodeFeature(final Object element) {
		super(element);
		this.outboundConnectors = new ArrayList<ConnectionNodeFeature>();
		this.inboundConnectors = new ArrayList<ConnectionNodeFeature>();
	}

	public ConnectorLayoutStrategy getConnectorLayoutStrategy() {
		return this.connectorLayoutStrategy;
	}

	public void setConnectorLayoutStrategy(final ConnectorLayoutStrategy connectorLayoutStrategy) {
		this.connectorLayoutStrategy = connectorLayoutStrategy;
	}

	public SizingStrategy getSizingStrategy() {
		return this.sizingStrategy;
	}

	public void setSizingStrategy(final SizingStrategy sizingStrategy) {
		this.sizingStrategy = sizingStrategy;
	}

	public ColoringStrategy getColoringStrategy() {
		return this.coloringStrategy;
	}

	public void setColoringStrategy(final ColoringStrategy coloringStrategy) {
		this.coloringStrategy = coloringStrategy;
	}

	@Override
	protected final void render(final ICanvasFeatureProvider provider, final Graphics2D g,
			final int ax, final int ay, final Color featureColor, final boolean isSelected) {
		Color color = featureColor;
		if (this.coloringStrategy != null) {
			color = this.coloringStrategy.getColor(getElement());
		}
		renderNode(provider, g, ax, ay, color, isSelected);
	}

	protected abstract void renderNode(ICanvasFeatureProvider provider, Graphics2D g, int ax,
			int ay, Color color, boolean isSelected);

	public void reposition() {
		if (this.connectorLayoutStrategy != null) {
			this.connectorLayoutStrategy.layoutConnectorNodes(this, this.outboundConnectors,
					this.inboundConnectors);
		}
	}

	public void resize(final Graphics2D g) {
		if (this.sizingStrategy != null) {
			Object element = getElement();
			WorldDistance size = this.sizingStrategy.getSize(g, element);
			setSize(size);
		}
	}

	public void addOutboundConnector(final ConnectionNodeFeature out) {
		this.outboundConnectors.add(out);
	}

	public void addInboundConnector(final ConnectionNodeFeature out) {
		this.inboundConnectors.add(out);
	}

}
