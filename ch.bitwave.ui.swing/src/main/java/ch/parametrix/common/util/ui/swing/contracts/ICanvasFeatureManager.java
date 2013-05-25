package ch.parametrix.common.util.ui.swing.contracts;

import javax.annotation.Nonnull;

/**
 * Provides access to a manager of canvas features, allowing features to be
 * added and removed.
 */
public interface ICanvasFeatureManager {

	public void addFeature(@Nonnull Feature feature);

	public void removeFeature(@Nonnull Feature feature);
}
