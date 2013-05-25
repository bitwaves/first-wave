package ch.parametrix.common.util.ui.swing.clap;

import static org.hamcrest.Matchers.is;

import java.awt.Graphics2D;

import ch.bitwave.platform.codestyle.DBC;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeature;

/**
 * Accomodates N sources in a single target. The height of the target is
 * adjusted in order to span all the sources, including the specified bottom
 * padding. When using accomodation goals, make sure to add the goal
 * <i>after</i> all the goals required to layout the contents, because this will
 * prevent pointless iterations in the layout processor.
 * 
 */
public class AccomodateHGoal extends LayoutGoal {
	private final double padding;

	/**
	 * Creates an H accomodation goal with the given padding in world
	 * coordinates and the given target.
	 * 
	 * @param padding
	 *            the padding to add after (to the right) the sources.
	 * @param target
	 *            the target to adjust so that it accomodates the sources.
	 */
	public AccomodateHGoal(final double padding, final ICanvasFeature target) {
		super();
		this.padding = padding;
		addTarget(target);
	}

	@Override
	public boolean apply(final Graphics2D g) {
		DBC.PRE.assertThat(1, is(getTargets().size()));
		final ICanvasFeature target = getTargets().get(0);
		double reference = -Double.MAX_VALUE;
		if (getSources().size() > 0) {
			for (final ICanvasFeature source : getSources()) {
				final double sr = source.getPlacement().getY() + source.getHeight();
				if (sr > reference) {
					reference = sr;
				}
			}
			final double height = reference - target.getPlacement().getY() + this.padding;
			if (height >= 0) {
				return target.setHeight(height);
			}
		}
		return false;
	}

}
