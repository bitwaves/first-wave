package ch.parametrix.common.util.ui.swing.clap;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.Nonnull;

import ch.bitwave.platform.codestyle.DBC;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeature;

/**
 * The base class for different types of goals that can be formulated for laying
 * out canvas features. Each goal affects 1..n target features and is itself
 * dependent on 0..n features.
 * 
 */
public abstract class LayoutGoal {
	private final List<ICanvasFeature> sources;
	private final List<ICanvasFeature> targets;

	/**
	 * 
	 */
	public LayoutGoal() {
		this.sources = new ArrayList<ICanvasFeature>();
		this.targets = new ArrayList<ICanvasFeature>();
	}

	/**
	 * Applies the layout. Returns true if any of the targets have been
	 * reshaped, false if not.
	 * 
	 * @return true if the layout has provoked a change in the feature state.
	 */
	public abstract boolean apply(Graphics2D g);

	/**
	 * Adds the given feature to the list of source features.
	 * 
	 * @param source
	 *            the source to add.
	 */
	public void addSource(@Nonnull final ICanvasFeature source) {
		DBC.PRE.assertNotNull(source, "The source feature to add must not be null.");
		this.sources.add(source);
	}

	/**
	 * Adds the given feature to the list of target features.
	 * 
	 * @param target
	 *            the target to add.
	 */
	public void addTarget(@Nonnull final ICanvasFeature target) {
		DBC.PRE.assertNotNull(target, "The target feature to add must not be null.");
		this.targets.add(target);
	}

	/**
	 * Adds the given list of feature to the list of target features.
	 * 
	 * @param targets
	 *            the targets to add.
	 */
	public void addTargets(@Nonnull final ICanvasFeature... targets) {
		DBC.PRE.assertNotNull(targets, "The list of target feature to add must not be null.");
		this.targets.addAll(Arrays.asList(targets));
	}

	/**
	 * Returns the list of sources of this goal.
	 * 
	 * @return the sources
	 */
	@Nonnull
	public List<ICanvasFeature> getSources() {
		return this.sources;
	}

	/**
	 * Returns the list of targets of this goal.
	 * 
	 * @return the targets
	 */
	@Nonnull
	public List<ICanvasFeature> getTargets() {
		return this.targets;
	}

	@Override
	public String toString() {
		if (this.sources.size() == 1) {
			return getClass().getSimpleName() + " [source=" + this.sources.get(0) + ", #targets="
					+ this.targets.size() + "]";
		} else {
			return getClass().getSimpleName() + " [#sources=" + this.sources.size() + ", #targets="
					+ this.targets.size() + "]";
		}
	}

}
