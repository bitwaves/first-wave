package ch.parametrix.common.util.ui.swing.contracts;

import java.util.List;
import java.util.Set;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Allows a canvas to access the objects (=features) it is supposed to display.
 * 
 */
public interface ICanvasFeatureProvider
{
	/**
	 * Returns the list of features to be displayed on the canvas.
	 * 
	 * @return the list of features to display on the canvas.
	 */
	List<ICanvasFeature> getCanvasFeatures();

	/**
	 * Selects all features whose bounding boxes are encompassed entirely by the given rectangle.
	 * 
	 * @param x
	 *            the left edge of the box.
	 * @param y
	 *            the top edge of the box.
	 * @param x2
	 *            the right edge of the box.
	 * @param y2
	 *            the bottom edge of the box.
	 */
	void selectFeatures(double x, double y, double x2, double y2);

	/**
	 * Returns the complete set of features currently selected on this canvas.
	 * 
	 * @return the list of features which are currently selected.
	 */
	Set<ICanvasFeature> getSelectedFeatureSet();

	/**
	 * Returns those features currently selected which are of the given feature class.
	 * 
	 * @param featureClass
	 *            the class the feature must be in order to make it into the result.
	 * @return the list of features of the given type currently selected.
	 */
	List<ICanvasFeature> getSelectedFeaturesOfType(@Nonnull Class<?> featureClass);

	/**
	 * Returns the size of the grid used by this canvas.
	 * 
	 * @return the grid size in world coordinates.
	 */
	int getGridSize();

	/**
	 * Clears the current feature selection of this canvas.
	 */
	void clearSelection();

	/**
	 * Selects the given feature in this canvas. Nothing happens if the feature is already the only feature selected. Otherwise, the selection is
	 * cleared and the feature will become the only feature currently selected.
	 * 
	 * @param selected
	 *            the feature to select.
	 */
	void selectFeature(@Nullable ICanvasFeature selected);

	/**
	 * Selects exactly the given list of features, removing the previous selection. All the features in the given list must belong to this provider,
	 * and an exception is thrown on the first feature which is not.
	 * 
	 * @param features
	 *            the features to select.
	 */
	void selectFeatures(@Nonnull List<ICanvasFeature> features);

	/**
	 * Adds the given feature to the current set of selected features. Nothing happens if the feature is null or already present in the selection.
	 * 
	 * @param selected
	 *            the feature to add to the selection.
	 */
	void addFeatureToSelection(@Nullable ICanvasFeature selected);

	/**
	 * Checks whether the given feature is in the set of currently selected features.
	 * 
	 * @param feature
	 *            the feature to test.
	 * @return true if the given feature is selected.
	 */
	boolean isSelectedFeature(@Nullable ICanvasFeature feature);

	/**
	 * Looks a feature up by model object. If multiple features refer to the same model object (which is not actually supported or handled in any
	 * particular way as of yet), the first occurrence is returned. Returns null if the object is not featured on the canvas, or if model is null.
	 * 
	 * @param model
	 *            the model object to look up.
	 * @return a reference to the feature, or null.
	 */
	@CheckForNull
	ICanvasFeature findFeatureByModelObject(@Nullable Object model);

	/**
	 * Returns a list of all features of the given type belonging to this provider.
	 * 
	 * @param featureClass
	 *            the type of feature that should be returned.
	 * @return a list containing all features known to the provider that are of the given type.
	 */
	@Nonnull
	List<ICanvasFeature> getFeaturesOfType(@Nonnull Class<?> featureClass);

}
