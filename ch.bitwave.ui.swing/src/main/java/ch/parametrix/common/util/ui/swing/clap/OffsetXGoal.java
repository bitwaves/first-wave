package ch.parametrix.common.util.ui.swing.clap;

import static org.hamcrest.Matchers.is;

import java.awt.Graphics2D;

import ch.bitwave.platform.codestyle.DBC;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeature;

/**
 * Places N targets at a specified offset from a single source.
 * 
 */
public class OffsetXGoal extends LayoutGoal {
	private final double offset;

	/**
	 * Creates an X offset goal with the given offset in world coordinates and
	 * the given source.
	 * 
	 * @param offset
	 *            the offset of the target from the source.
	 * @param source
	 *            the feature to offset the target from.
	 */
	public OffsetXGoal(final double offset, final ICanvasFeature source) {
		super();
		this.offset = offset;
		addSource(source);
	}

	/**
	 * Creates an X offset goal with the given offset in world coordinates and
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
	public OffsetXGoal(final double offset, final ICanvasFeature source, final ICanvasFeature target) {
		this(offset, source);
		addTarget(target);
	}

	@Override
	public boolean apply(final Graphics2D g) {
		DBC.PRE.assertThat(1, is(getSources().size()));
		ICanvasFeature source = getSources().get(0);
		boolean changed = false;
		double spx = source.getPlacement().getX();
		for (ICanvasFeature target : getTargets()) {
			changed |= target.placeX(spx + this.offset);
		}
		return changed;
	}

}
