package ch.bitwave.mim.canvas.features;

import java.awt.Graphics2D;

import ch.parametrix.common.util.ui.swing.contracts.WorldDistance;

/**
 * Centers a node at the left of a single vertical rank of details where the
 * details flow from the top to the bottom.
 */
public class HorizontalGroupFeature extends GroupFeature {
	private final static double RANK_TO_NODE_SPACING = 500;
	private HorizontalRankFeature rank;

	public HorizontalGroupFeature(final Object element) {
		super(element);
	}

	@Override
	protected WorldDistance getInitialSize() {
		return new WorldDistance(1000, 3000);
	}

	public void setRank(final HorizontalRankFeature rank) {
		this.rank = rank;
	}

	@Override
	public void reposition() {
		WorldDistance nodeSize = getNodeSize();
		double y = this.getTop();
		double x = this.getLeft();
		NodeFeature node = getNode();
		if (node != null) {
			// The node is centered vertically within the group
			double centerY = y + (this.getSize().getDy() - nodeSize.getDy()) / 2;
			node.place(x, centerY);
			node.reposition();
			this.rank.placeX(node.getRight() + RANK_TO_NODE_SPACING);
		} else {
			// If there is no feature, the rank just hugs the right of the
			// group.
			this.rank.placeX(x);
		}
		this.rank.placeY(y);
		this.rank.reposition();
	}

	@Override
	public void resize(final Graphics2D g) {
		NodeFeature node = getNode();
		if (node != null) {
			node.resize(g);
		}
		WorldDistance nodeSize = getNodeSize();
		this.rank.resize(g);
		double w = this.rank.getWidth();
		double h = Math.max(nodeSize.getDy(), this.rank.getSize().getDy());
		if (node != null) {
			w += nodeSize.getDx() + RANK_TO_NODE_SPACING;
		}
		setSize(w, h);
	}

	public void addDetailGroup(final HorizontalGroupFeature detail) {
		this.rank.addDetailGroup(detail);
	}
}
