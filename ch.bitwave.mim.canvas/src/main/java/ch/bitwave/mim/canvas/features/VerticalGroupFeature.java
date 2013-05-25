package ch.bitwave.mim.canvas.features;

import java.awt.Graphics2D;

import ch.parametrix.common.util.ui.swing.contracts.WorldDistance;

/**
 * Centers a node at the top of a single horizontal rank of details where the
 * details flow from the left to the right.
 */
public class VerticalGroupFeature extends GroupFeature {
	private final static double RANK_TO_NODE_SPACING = 200;
	private VerticalRankFeature rank;

	public VerticalGroupFeature(final Object element) {
		super(element);
	}

	@Override
	protected WorldDistance getInitialSize() {
		return new WorldDistance(3000, 1000);
	}

	public void setRank(final VerticalRankFeature rank) {
		this.rank = rank;
	}

	@Override
	public void reposition() {
		WorldDistance nodeSize = getNodeSize();
		double y = this.getTop();
		double x = this.getLeft();
		NodeFeature node = getNode();
		if (node != null) {
			// The node is offset vertically within the group
			double centerX = x + (this.getSize().getDx() - nodeSize.getDx()) / 2;
			node.place(centerX, y);
			node.reposition();
			this.rank.placeY(y + node.getBottom() + RANK_TO_NODE_SPACING);
		} else {
			// If there is no feature, the rank just hugs the top of the group.
			this.rank.placeY(y);
		}
		this.rank.placeX(x);
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
		double w = Math.max(nodeSize.getDx(), this.rank.getSize().getDx());
		double h = this.rank.getHeight();
		if (getNode() != null) {
			h += nodeSize.getDy() + RANK_TO_NODE_SPACING;
		}
		setSize(w, h);
	}

	public void addDetailGroup(final VerticalGroupFeature detail) {
		this.rank.addDetailGroup(detail);
	}
}
