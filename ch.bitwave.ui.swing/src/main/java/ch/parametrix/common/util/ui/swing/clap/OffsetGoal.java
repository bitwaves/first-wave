package ch.parametrix.common.util.ui.swing.clap;

import static org.hamcrest.Matchers.is;

import java.awt.Graphics2D;

import ch.bitwave.platform.codestyle.DBC;
import ch.parametrix.common.util.ui.swing.WorldCoordinate;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeature;
import ch.parametrix.common.util.ui.swing.contracts.WorldDistance;

/**
 * Places a single target at a specified offset from a single source.
 * 
 */
public class OffsetGoal extends LayoutGoal {
	private final WorldDistance offset;

	/**
	 * Creates an offset goal with the given offset in world coordinates and the
	 * given source and target.
	 * 
	 * @param offset
	 * @param source
	 * @param target
	 */
	public OffsetGoal(final WorldDistance offset, final ICanvasFeature source,
			final ICanvasFeature target) {
		super();
		this.offset = offset;
		addSource(source);
		addTarget(target);
	}

	@Override
	public boolean apply(final Graphics2D g) {
		DBC.PRE.assertThat(1, is(getTargets().size()));
		DBC.PRE.assertThat(1, is(getSources().size()));
		ICanvasFeature source = getSources().get(0);
		ICanvasFeature target = getTargets().get(0);
		WorldCoordinate sp = source.getPlacement();
		return target.place(sp.getX() + this.offset.getDx(), sp.getY() + this.offset.getDy());
	}

}
