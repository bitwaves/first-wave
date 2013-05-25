package ch.parametrix.common.util.ui.swing.clap;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import ch.bitwave.platform.codestyle.DBC;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeature;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeatureProvider;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasTransform;

/**
 * Applies a list of layout goals to the features of a canvas.
 * 
 */
public class LayoutProcessor {
	private static final Logger LOG = Logger.getLogger(LayoutProcessor.class.getSimpleName());
	private final List<LayoutGoal> goals;
	private static final int MAX_ITERATIONS = 100;

	/**
	 * Creates a new layout processor.
	 */
	public LayoutProcessor() {
		this.goals = new ArrayList<LayoutGoal>();
	}

	/**
	 * Adds the given goal to the processor and returns a reference to it.
	 * 
	 * @param goal
	 *            the goal to add.
	 * @return the goal added (same as goal).
	 */
	public LayoutGoal addGoal(final LayoutGoal goal) {
		DBC.PRE.assertNotNull(goal, "The goal to add must not be null.");
		this.goals.add(goal);
		return goal;
	}

	public void layout(final ICanvasFeatureProvider provider, final Graphics2D g,
			final ICanvasTransform tx) {
		Shape realClip = g.getClip();
		// Clip to nothing to prevent output during layout passes.
		g.setClip(0, 0, 0, 0);
		try {
			int iteration = 1;
			while (iteration <= MAX_ITERATIONS) {
				if (LOG.isLoggable(Level.FINEST)) {
					LOG.finest(String.format("Iteration %s.", iteration));
				}
				ICanvasFeature[] features = provider.getCanvasFeatures().toArray(
						new ICanvasFeature[0]);
				for (ICanvasFeature feature : features) {
					feature.render(provider, g, tx, false);
				}
				boolean changed = false;
				for (LayoutGoal goal : this.goals) {
					boolean goalChange = goal.apply(g);
					if (goalChange) {
						// if (!changed && goalChange &&
						// LOG.isLoggable(Level.FINEST)) {
						LOG.info(String.format("%s provoked a change.", goal));
					}
					changed |= goalChange;
				}
				if (!changed) {
					if (LOG.isLoggable(Level.FINEST)) {
						LOG.finest(String
								.format("Layout converged after %s iterations.", iteration));
					}
					return;
				}
				iteration++;
			}
			LOG.severe("Layout did not converge, giving up.");
		} finally {
			g.setClip(realClip);
		}
	}

	/**
	 * Clears the layout processor, removing all current goals.
	 */
	public void clear() {
		this.goals.clear();
	}
}
