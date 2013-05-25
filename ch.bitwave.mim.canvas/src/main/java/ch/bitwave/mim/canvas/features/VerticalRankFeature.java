package ch.bitwave.mim.canvas.features;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import ch.parametrix.common.util.ui.swing.contracts.Feature;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeatureProvider;
import ch.parametrix.common.util.ui.swing.contracts.WorldDistance;

public class VerticalRankFeature extends Feature {
	private final double GROUP_SPACING = 200;

	private List<VerticalGroupFeature> detailGroups;

	public VerticalRankFeature(final Object element) {
		super(element);
		this.detailGroups = new ArrayList<VerticalGroupFeature>();
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
				x += this.GROUP_SPACING;
			}
			VerticalGroupFeature detail = this.detailGroups.get(i);
			detail.place(x, y);
			detail.reposition();
			x += detail.getSize().getDx();
		}
	}

	public void resize(final Graphics2D g) {
		double h = 0;
		double w = 0;
		for (int i = 0; i < this.detailGroups.size(); i++) {
			if (i > 0) {
				w += this.GROUP_SPACING;
			}
			VerticalGroupFeature detail = this.detailGroups.get(i);
			detail.resize(g);
			w += detail.getSize().getDx();
			double detailH = detail.getSize().getDy();
			if (h < detailH) {
				h = detailH;
			}
		}
		setSize(w, h);
	}

	public void addDetailGroup(final VerticalGroupFeature detail) {
		this.detailGroups.add(detail);
	}

	@Override
	public boolean isSelectable() {
		return false;
	}

}
