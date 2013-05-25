package ch.parametrix.common.util.ui.swing.clap;

import java.awt.Graphics2D;

import ch.parametrix.common.util.ui.swing.WorldCoordinate;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeature;

/**
 * Places the targets at a specific, absolute position on the canvas. Does not
 * require any source features.
 * 
 */
public class AbsolutePlacementGoal extends LayoutGoal {
	private WorldCoordinate place;

	@Override
	public boolean apply(final Graphics2D g) {
		boolean changed = false;
		for (ICanvasFeature feature : getTargets()) {
			changed |= feature.place(this.place);
		}
		return changed;
	}

}
