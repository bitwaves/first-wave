package ch.parametrix.common.util.ui.swing.clap;

import java.awt.Graphics2D;

import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeature;
import ch.parametrix.common.util.ui.swing.contracts.ILayoutCapable;

/**
 * Delegates the effective layout to the layout() method of the targets.
 */
public class DelegatedLayoutGoal extends LayoutGoal {
	public DelegatedLayoutGoal(final ICanvasFeature... targets) {
		addTargets(targets);
	}

	@Override
	public boolean apply(final Graphics2D g) {
		for (ICanvasFeature target : getTargets()) {
			if (target instanceof ILayoutCapable) {
				((ILayoutCapable) target).layout(g);
			}
		}
		return false;
	}

}
