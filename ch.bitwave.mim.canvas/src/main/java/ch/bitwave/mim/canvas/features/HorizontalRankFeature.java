package ch.bitwave.mim.canvas.features;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import ch.parametrix.common.util.ui.swing.contracts.Feature;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeatureProvider;
import ch.parametrix.common.util.ui.swing.contracts.WorldDistance;

public class HorizontalRankFeature extends Feature {
	private final double GROUP_SPACING = 100;

	private List<HorizontalGroupFeature> detailGroups;

	public HorizontalRankFeature(final Object element) {
		super(element);
		this.detailGroups = new ArrayList<HorizontalGroupFeature>();
	}

	@Override
	public String getIdentifier() {
		return null;
	}

	@Override
	protected WorldDistance getInitialSize() {
		return new WorldDistance(1000, 100);
	}

	@Override
	protected void render(final ICanvasFeatureProvider provider, final Graphics2D g, final int ax,
			final int ay, final Color featureColor, final boolean isSelected) {
		setBounds(ax, ay, getWidth(), getHeight());
	}

	public void reposition() {
		double x = getLeft();
		double y = getTop();
		for (int i = 0; i < this.detailGroups.size(); i++) {
			if (i > 0) {
				y += this.GROUP_SPACING;
			}
			HorizontalGroupFeature detail = this.detailGroups.get(i);
			detail.place(x, y);
			detail.reposition();
			y += detail.getSize().getDy();
		}
	}

	public void resize(final Graphics2D g) {
		double h = 0;
		double w = 0;
		for (int i = 0; i < this.detailGroups.size(); i++) {
			if (i > 0) {
				h += this.GROUP_SPACING;
			}
			HorizontalGroupFeature detail = this.detailGroups.get(i);
			detail.resize(g);
			h += detail.getSize().getDy();
			double detailW = detail.getSize().getDx();
			if (w < detailW) {
				w = detailW;
			}
		}
		setSize(w, h);
	}

	public void addDetailGroup(final HorizontalGroupFeature detail) {
		this.detailGroups.add(detail);
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

}
