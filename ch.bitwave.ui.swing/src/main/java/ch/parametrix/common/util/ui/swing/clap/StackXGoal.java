package ch.parametrix.common.util.ui.swing.clap;

import static org.hamcrest.Matchers.is;

import java.awt.Graphics2D;

import ch.bitwave.platform.codestyle.DBC;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeature;

/**
 * Horizontally stacks N targets within a single source, laying out the targets
 * horizontally with a specified padding.
 * 
 */
public class StackXGoal extends LayoutGoal {
	private final double padding;

	/**
	 * Creates a stack X goal for the given source.
	 * 
	 * @param offset
	 * @param source
	 */
	public StackXGoal(final double padding, final ICanvasFeature source) {
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
		double sx = source.getPlacement().getX() + (source.getWidth() - ref) / 2;
		for (ICanvasFeature target : getTargets()) {
			changed |= target.placeX(sx);
			sx += target.getWidth();
			sx += this.padding;
		}
		return changed;
	}

	/**
	 * @param padding
	 * @return
	 */
	private double determineStackSize(final double padding) {
		double w = 0;
		for (ICanvasFeature target : getTargets()) {
			if (w > 0) {
				w += padding;
			}
			w += target.getWidth();
		}
		return w;
	}

}
