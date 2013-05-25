package ch.parametrix.common.util.ui.swing.clap;

import static org.hamcrest.Matchers.is;

import java.awt.Graphics2D;

import ch.bitwave.platform.codestyle.DBC;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeature;

/**
 * Places N targets at a specified offset from a single source.
 * 
 */
public class OffsetYGoal extends LayoutGoal {
	private final double offset;

	/**
	 * Creates an Y offset goal with the given offset in world coordinates and
	 * the given source.
	 * 
	 * @param offset
	 *            the offset of the target from the source.
	 * @param source
	 *            the feature to offset the target from.
	 */
	public OffsetYGoal(final double offset, final ICanvasFeature source) {
		super();
		this.offset = offset;
		addSource(source);
	}

	/**
	 * Creates a Y offset goal with the given offset in world coordinates and
	 * the given source and target. Additional targets may be added.
	 * 
	 * @param offset
	 *            the offset of the target from the source.
	 * @param source
	 *            the feature to offset the target from.
	 * @param target
	 *            the target which will be placed at the given offset from the
	 *            source.
	 */
	public OffsetYGoal(final double offset, final ICanvasFeature source, final ICanvasFeature target) {
		this(offset, source);
		addTarget(target);
	}

	@Override
	public boolean apply(final Graphics2D g) {
		DBC.PRE.assertThat(1, is(getSources().size()));
		ICanvasFeature source = getSources().get(0);
		boolean changed = false;
		double spy = source.getPlacement().getY();
		for (ICanvasFeature target : getTargets()) {
			changed |= target.placeY(spy + this.offset);
		}
		return changed;
	}

}
