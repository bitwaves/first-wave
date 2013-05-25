package ch.parametrix.common.util.ui.swing.clap;

import static org.hamcrest.Matchers.is;

import java.awt.Graphics2D;

import ch.bitwave.platform.codestyle.DBC;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeature;

/**
 * Vertically stacks N targets within a single source, laying out the targets
 * vertically with a specified padding.
 * 
 */
public class StackYGoal extends LayoutGoal {
	private final double padding;

	/**
	 * Creates a stack X goal for the given source.
	 * 
	 * @param offset
	 * @param source
	 */
	public StackYGoal(final double padding, final ICanvasFeature source) {
		super();
		this.padding = padding;
		addSource(source);
	}

	@Override
	public boolean apply(final Graphics2D g) {
		DBC.PRE.assertThat(1, is(getSources().size()));
		ICanvasFeature source = getSources().get(0);
		boolean changed = false;
		double ref = determineStackSize(this.padding);
		double sy = source.getPlacement().getY() + (source.getHeight() - ref) / 2;
		for (ICanvasFeature target : getTargets()) {
			changed |= target.placeY(sy);
			sy += target.getHeight();
			sy += this.padding;
		}
		return changed;
	}

	/**
	 * @param padding
	 * @return
	 */
	private double determineStackSize(final double padding) {
		double h = 0;
		for (ICanvasFeature target : getTargets()) {
			if (h > 0) {
				h += padding;
			}
			h += target.getHeight();
		}
		return h;
	}
}
