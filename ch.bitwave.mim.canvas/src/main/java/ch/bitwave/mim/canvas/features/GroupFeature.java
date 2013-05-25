package ch.bitwave.mim.canvas.features;

import java.awt.Color;
import java.awt.Graphics2D;

import ch.parametrix.common.util.ui.swing.contracts.Feature;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeatureProvider;
import ch.parametrix.common.util.ui.swing.contracts.ILayoutCapable;
import ch.parametrix.common.util.ui.swing.contracts.WorldDistance;

public abstract class GroupFeature extends Feature implements ILayoutCapable {
	private NodeFeature node;

	public GroupFeature(final Object element) {
		super(element);
	}

	@Override
	public String getIdentifier() {
		return null;
	}

	@Override
	protected void render(final ICanvasFeatureProvider provider, final Graphics2D g, final int ax,
			final int ay, final Color featureColor, final boolean isSelected) {
		setBounds(ax, ay, getWidth(), getHeight());
	}

	public void setNode(final NodeFeature node) {
		this.node = node;
		if (node != null) {
			this.node.setParent(this);
		}
	}

	public NodeFeature getNode() {
		return this.node;
	}

	@Override
	public boolean layout(final Graphics2D g) {
		resize(g);
		reposition();
		return false;
	}

	public abstract void resize(final Graphics2D g);

	public abstract void reposition();

	protected WorldDistance getNodeSize() {
		WorldDistance nodeSize = null;
		if (this.node != null) {
			nodeSize = this.node.getSize();
		} else {
			nodeSize = new WorldDistance(0, 0);
		}
		return nodeSize;
	}

	@Override
	protected WorldDistance getInitialSize() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

}
