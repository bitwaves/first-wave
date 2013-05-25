package ch.parametrix.common.util.ui.swing.clap;

import static org.hamcrest.Matchers.is;

import java.awt.Graphics2D;

import ch.bitwave.platform.codestyle.DBC;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeature;

/**
 * Horizontally centers N targets on a single source.
 */
public class AlignXCGoal extends LayoutGoal {
	/**
	 * Creates a goal which centers N targets horizontally on the given source.
	 * 
	 * @param source
	 *            the feature to center the targets on.
	 */
	public AlignXCGoal(final ICanvasFeature source) {
		super();
		addSource(source);
	}

	@Override
	public boolean apply(final Graphics2D g) {
		DBC.PRE.assertThat(1, is(getSources().size()));
		final ICanvasFeature source = getSources().get(0);
		boolean changed = false;
		final double spx = source.getPlacement().getX() + (source.getWidth() / 2);
		for (final ICanvasFeature target : getTargets()) {
			changed |= target.placeX(spx - target.getWidth() / 2);
		}
		return changed;
	}

}
