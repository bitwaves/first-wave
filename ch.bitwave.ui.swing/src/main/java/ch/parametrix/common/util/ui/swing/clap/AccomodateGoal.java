package ch.parametrix.common.util.ui.swing.clap;

import static org.hamcrest.Matchers.is;

import java.awt.Graphics2D;

import ch.bitwave.platform.codestyle.DBC;
import ch.parametrix.common.util.ui.swing.WorldCoordinate;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeature;

/**
 * Accomodates N sources in a single target. The width and height of the target
 * is adjusted in order to span all the sources, including the specified right
 * and bottom padding. When using accomodation goals, make sure to add the goal
 * <i>after</i> all the goals required to layout the contents, because this will
 * prevent pointless iterations in the layout processor.
 */
public class AccomodateGoal extends LayoutGoal {
	private int tolerance = 10;
	private final double padding;

	/**
	 * Creates an H accomodation goal with the given padding in world
	 * coordinates and the given target.
	 * 
	 * @param padding
	 *            the padding to add to the right and below the sources.
	 * @param target
	 *            the target to adjust so that it accomodates the sources.
	 */
	public AccomodateGoal(final double padding, final ICanvasFeature target) {
		super();
		this.padding = padding;
		addTarget(target);
	}

	public int getTolerance() {
		return this.tolerance;
	}

	public void setTolerance(final int tolerance) {
		this.tolerance = tolerance;
	}

	@Override
	public boolean apply(final Graphics2D g) {
		DBC.PRE.assertThat(1, is(getTargets().size()));
		final ICanvasFeature target = getTargets().get(0);
		double yReference = -Double.MAX_VALUE;
		double xReference = -Double.MAX_VALUE;
		if (getSources().size() > 0) {
			for (final ICanvasFeature source : getSources()) {
				WorldCoordinate placement = source.getPlacement();
				final double sy = placement.getY() + source.getHeight();
				if (sy > yReference) {
					yReference = sy;
				}
				final double sx = placement.getX() + source.getWidth();
				if (sx > xReference) {
					xReference = sx;
				}
			}
			int width = (int) Math.max(0, xReference - target.getPlacement().getX() + this.padding);
			int height = (int) Math
					.max(0, yReference - target.getPlacement().getY() + this.padding);
			if (exTolerance(target.getWidth(), width) || exTolerance(target.getHeight(), height)) {
				target.setSize(width, height);
				return true;
			}
		}
		return false;
	}

	private boolean exTolerance(final int v1, final int v2) {
		return Math.abs(v1 - v2) > this.tolerance;
	}

}
