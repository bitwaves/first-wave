package ch.parametrix.common.util.ui.swing.clap;

import static org.hamcrest.Matchers.is;

import java.awt.Graphics2D;

import ch.bitwave.platform.codestyle.DBC;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeature;

/**
 * Accomodates N sources in a single target. The width of the target is adjusted
 * in order to span all the sources, including the specified right padding. When
 * using accomodation goals, make sure to add the goal <i>after</i> all the
 * goals required to layout the contents, because this will prevent pointless
 * iterations in the layout processor.
 * 
 */
public class AccomodateWGoal extends LayoutGoal {
	private final double padding;

	/**
	 * Creates a W accomodation goal with the given padding in world coordinates
	 * and the given target.
	 * 
	 * @param padding
	 *            the padding to add after (to the right) the sources.
	 * @param target
	 *            the target to adjust so that it accomodates the sources.
	 */
	public AccomodateWGoal(final double padding, final ICanvasFeature target) {
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
				final double sr = source.getPlacement().getX() + source.getWidth();
				if (sr > reference) {
					reference = sr;
				}
			}
			final double width = reference - target.getPlacement().getX() + this.padding;
			if (width >= 0) {
				return target.setWidth(width);
			}
		}
		return false;
	}

}
