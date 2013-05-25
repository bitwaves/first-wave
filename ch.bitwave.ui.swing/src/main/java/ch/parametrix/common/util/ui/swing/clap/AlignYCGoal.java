package ch.parametrix.common.util.ui.swing.clap;

import static org.hamcrest.Matchers.is;

import java.awt.Graphics2D;

import ch.bitwave.platform.codestyle.DBC;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeature;

/**
 * Vertically centers N targets on a single source.
 * 
 */
public class AlignYCGoal extends LayoutGoal {
	/**
	 * Creates a goal which centers N targets horizontally on the given source.
	 * 
	 * @param source
	 *            the feature to center the targets on.
	 */
	public AlignYCGoal(final ICanvasFeature source) {
		super();
		addSource(source);
	}

	@Override
	public boolean apply(final Graphics2D g) {
		DBC.PRE.assertThat(1, is(getSources().size()));
		final ICanvasFeature source = getSources().get(0);
		boolean changed = false;
		final double spy = source.getPlacement().getY() + (source.getHeight() / 2);
		for (final ICanvasFeature target : getTargets()) {
			changed |= target.placeY(spy - target.getHeight() / 2);
		}
		return changed;
	}

}
