package ch.parametrix.common.util.ui.swing.clap;

import java.awt.Color;
import java.awt.Graphics2D;

import ch.parametrix.common.util.ui.swing.WorldCoordinate;
import ch.parametrix.common.util.ui.swing.contracts.Feature;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeatureProvider;
import ch.parametrix.common.util.ui.swing.contracts.WorldDistance;

/**
 * A feature for use in unit tests.
 * 
 */
public class SampleFeature extends Feature {

	/**
	 * @param element
	 */
	public SampleFeature(final Object element) {
		super(element);
	}

	/**
	 * @param string
	 * @param pos
	 * @param size
	 */
	public SampleFeature(final Object element, final WorldCoordinate pos, final WorldDistance size) {
		this(element);
		this.anchor = pos;
		this.size = size;
		updateBounds();
	}

	/**
	 * 
	 */
	private void updateBounds() {
		setBounds(this.anchor.getX(), this.anchor.getY(), this.size.getDx(), this.size.getDy());
	}

	@Override
	public String getIdentifier() {
		return null;
	}

	@Override
	protected WorldDistance getInitialSize() {
		return new WorldDistance(500, 100);
	}

	@Override
	protected void render(final ICanvasFeatureProvider provider, final Graphics2D g, final int ax,
			final int ay, final Color featureColor, final boolean isSelected) {
		updateBounds();
	}

}
