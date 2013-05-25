package ch.parametrix.common.util.ui.swing.clap;

import java.awt.Graphics2D;

import ch.bitwave.platform.codestyle.DBC;
import ch.parametrix.common.util.ui.swing.WorldCoordinate;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeature;
import ch.parametrix.common.util.ui.swing.contracts.WorldDistance;

/**
 * Places the targets at the given X offset from the source. If there are
 * multiple sources, the most extreme source is used as a reference.
 * 
 */
public class ArrangeXGoal extends LayoutGoal {
	private final double margin;

	/**
	 * Creates an X arrangement goal with the given margin in world coordinates.
	 * If the margin is positive, the targets will be aligned to the right of
	 * the rightmost source. If the margin is negative, the targets will be
	 * aligned to the left of the leftmost source. The margin must not be 0.
	 * 
	 * @param margin
	 */
	public ArrangeXGoal(final double margin) {
		super();
		DBC.PRE.assertFalse(
				"The margin must not be 0 because it is used to determine orientation.",
				0 == margin);
		this.margin = margin;
	}

	/**
	 * Creates an X arrangement goal with the given margin in world coordinates
	 * and the given source and target. This is a shorthand for a standard use
	 * case where a single target is made dependent on a single source.
	 * 
	 * @param margin
	 * @param source
	 * @param target
	 */
	public ArrangeXGoal(final double margin, final ICanvasFeature source,
			final ICanvasFeature target) {
		this(margin);
		addSource(source);
		addTarget(target);
	}

	@Override
	public boolean apply(final Graphics2D g) {
		final double reference = determineReference(this.margin);
		return placeTargets(reference);
	}

	/**
	 * Arranges all targets relative to the given reference component.
	 * 
	 * @param reference
	 *            the reference component value.
	 */
	private boolean placeTargets(final double reference) {
		boolean changed = false;
		if (this.margin > 0) {
			for (final ICanvasFeature feature : getTargets()) {
				changed |= feature.placeX(reference);
			}
		} else {
			for (final ICanvasFeature feature : getTargets()) {
				changed |= feature.placeX(reference - feature.getWidth());
			}
		}
		return changed;
	}

	/**
	 * Determines the reference component by scanning for the most extreme
	 * source.
	 * 
	 * @return
	 */
	protected double determineReference(final double margin) {
		if (margin > 0) {
			double result = Integer.MIN_VALUE;
			for (final ICanvasFeature source : getSources()) {
				final WorldCoordinate c = source.getPlacement();
				final WorldDistance d = source.getSize();
				final double fr = c.getX() + d.getDx();
				if (fr > result) {
					result = fr;
				}
			}
			return result + margin;
		}
		if (margin < 0) {
			double result = Integer.MAX_VALUE;
			for (final ICanvasFeature source : getSources()) {
				final WorldCoordinate c = source.getPlacement();
				final double fr = c.getX();
				if (fr < result) {
					result = fr;
				}
			}
			return result + margin;
		}
		throw new RuntimeException("Margin must not be zero.");
	}
}
