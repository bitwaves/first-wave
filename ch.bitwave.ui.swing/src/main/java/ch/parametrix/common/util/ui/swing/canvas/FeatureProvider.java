package ch.parametrix.common.util.ui.swing.canvas;

import java.awt.geom.Rectangle2D;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import javax.lang.model.util.Elements;

import ch.bitwave.platform.codestyle.DBC;
import ch.parametrix.common.util.ui.swing.WorldCoordinate;
import ch.parametrix.common.util.ui.swing.contracts.Feature;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeature;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeatureManager;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeatureProvider;

/**
 * A basic ICanvasFeature provider maintaining a list of features to provide to
 * a canvas. Introduces grid logic and ICanvasFeature selection.
 * 
 */
public class FeatureProvider implements ICanvasFeatureProvider, ICanvasFeatureManager {
	public static final String SELECTION_CHANGED = "selectionChanged";
	private final List<ICanvasFeature> features;
	private final Set<ICanvasFeature> selectedFeatures;
	private int gridSize = 100;
	private final PropertyChangeSupport pcs;
	private final FeatureFactory factory;

	public FeatureProvider() {
		super();
		this.features = new ArrayList<ICanvasFeature>();
		this.selectedFeatures = new LinkedHashSet<ICanvasFeature>();
		this.pcs = new PropertyChangeSupport(this);
		this.factory = new FeatureFactory();
	}

	/**
	 * Returns the feature factory used by this provider.
	 * 
	 * @return the feature factory.
	 */
	public FeatureFactory getFeatureFactory() {
		return this.factory;
	}

	@Override
	public List<ICanvasFeature> getCanvasFeatures() {
		return this.features;
	}

	@Override
	public List<ICanvasFeature> getFeaturesOfType(final Class<?> featureClass) {
		return filterByType(getCanvasFeatures(), featureClass);
	}

	/**
	 * Creates a ICanvasFeature for the given element without actually placing
	 * it in the view. The element is registered with the given element list.
	 * 
	 * @param elements
	 *            the list of elements to add the new element to.
	 * @param element
	 *            the element to create a feature for.
	 * @return a reference to the feature representing the element.
	 */
	public ICanvasFeature createFeatureForElement(final Elements elements, final Object element) {
		if (element == null) {
			throw new IllegalArgumentException("Element must not be null.");
		}
		final ICanvasFeature f = this.factory.createFeatureForElement(element);
		this.features.add(f);
		return f;
	}

	public ICanvasFeature findFeatureAt(final WorldCoordinate location) {
		double smallestFeature = Double.MAX_VALUE;
		ICanvasFeature bestFeature = null;
		for (final ICanvasFeature ICanvasFeature : this.features) {
			if (ICanvasFeature.isInside(location)) {
				final Rectangle2D bounds = ICanvasFeature.getBounds();
				final double w = bounds.getWidth();
				final double h = bounds.getHeight();
				final double area = w * h;
				if (area < smallestFeature) {
					smallestFeature = area;
					bestFeature = ICanvasFeature;
				}
			}
		}
		return bestFeature;
	}

	public void registerFeature(final ICanvasFeature ICanvasFeature) {
		this.features.add(ICanvasFeature);
	}

	/**
	 * Selects all the selectable features entirely comprised in the rectangle
	 * defined by the given begin and end point. The coordinates are swapped if
	 * required to ensure a rectangle with positive width and height.
	 * 
	 */
	@Override
	public void selectFeatures(final double x, final double y, final double x2, final double y2) {
		final Rectangle2D rect = new Rectangle2D.Double(x, y, x2 - x, y2 - y);
		this.selectedFeatures.clear();
		for (final ICanvasFeature feature : this.features) {
			if (feature.isSelectable() && rect.contains(feature.getBounds())) {
				this.selectedFeatures.add(feature);
			}
		}
		this.pcs.firePropertyChange(SELECTION_CHANGED, null, null);
	}

	/**
	 * Asserts that the given feature does indeed belong to this provider.
	 * Throws a runtime exception if it does not.
	 * 
	 * @param feature
	 *            the feature to test.
	 */
	private void assertFeatureOwnership(@Nonnull final ICanvasFeature feature) {
		DBC.PRE.assertNotNull(feature, "The feature must not be null.");
		if (!this.features.contains(feature)) {
			throw new RuntimeException(String.format(
					"Feature %s does not belong to this provider.", feature.toString()));
		}
	}

	@Override
	public void selectFeature(final ICanvasFeature feature) {
		assertFeatureOwnership(feature);
		if (!isSingleSelectedFeature(feature)) {
			this.selectedFeatures.clear();
			this.selectedFeatures.add(feature);
			this.pcs.firePropertyChange(SELECTION_CHANGED, null, feature);
		}
	}

	@Override
	public void selectFeatures(final List<ICanvasFeature> features) {
		clearSelection();
		for (final ICanvasFeature feature : features) {
			selectFeature(feature);
		}
	}

	@Override
	public void addFeatureToSelection(final ICanvasFeature feature) {
		assertFeatureOwnership(feature);
		this.selectedFeatures.add(feature);
		this.pcs.firePropertyChange(SELECTION_CHANGED, null, feature);
	}

	/**
	 * Checks if the given feature is the only feature currently selected in the
	 * view. if feature is null, true is returned if there is currently no
	 * selection at all.
	 * 
	 * @param feature
	 *            the feature to test.
	 * @return true if the given feature is the only selected feature, or if the
	 *         feature is null and there is no selection.
	 */
	public boolean isSingleSelectedFeature(final ICanvasFeature feature) {
		return (feature == null && this.selectedFeatures.size() == 0)
				|| (this.selectedFeatures.size() == 1 & isSelectedFeature(feature));
	}

	@Override
	public boolean isSelectedFeature(final ICanvasFeature ICanvasFeature) {
		return this.selectedFeatures.contains(ICanvasFeature);
	}

	public boolean hasFeatureSelection() {
		return this.selectedFeatures.size() > 0;
	}

	/**
	 * Returns the list of all features currently selected, in the order in
	 * which they were added to the selection. All features are included,
	 * regardless of type. If only the selection of a particular feature type is
	 * of interest, method {@link #getSelectedFeaturesOfType(Class)} can be used
	 * instead.
	 * 
	 * @return the list of selected features.
	 */
	public List<ICanvasFeature> getSelectedFeatures() {
		final List<ICanvasFeature> result = new ArrayList<ICanvasFeature>();
		for (final ICanvasFeature ICanvasFeature : this.selectedFeatures) {
			result.add(ICanvasFeature);
		}
		return result;
	}

	@Override
	public List<ICanvasFeature> getSelectedFeaturesOfType(@Nonnull final Class<?> featureClass) {
		DBC.PRE.assertNotNull(featureClass, "The feature class must not be null.");
		final List<ICanvasFeature> sel = getSelectedFeatures();
		return filterByType(sel, featureClass);
	}

	/**
	 * Filters the given list of feature by type, returning the reduced list.
	 * 
	 * @param features
	 *            the list of features to filter. May contain features of any
	 *            type.
	 * @param featureClass
	 *            the desired class of features.
	 * @return a new list containing only features of the given type, or a
	 *         subtype of that type.
	 */
	private List<ICanvasFeature> filterByType(@Nonnull final List<ICanvasFeature> features,
			@Nonnull final Class<?> featureClass) {
		final List<ICanvasFeature> result = new ArrayList<ICanvasFeature>(features.size());
		for (final ICanvasFeature feature : features) {
			if (featureClass.isAssignableFrom(feature.getClass())) {
				result.add(feature);
			}
		}
		return result;
	}

	public ICanvasFeature getSingleSelectedFeature() {
		if (this.selectedFeatures.size() != 1) {
			return null;
		} else {
			return this.selectedFeatures.iterator().next();
		}
	}

	@Override
	public void clearSelection() {
		this.selectedFeatures.clear();
		this.pcs.firePropertyChange(SELECTION_CHANGED, null, null);
	}

	/**
	 * Returns a copy of the set of features currently selected in the provider.
	 * Changes made to the resulting set will not affect the provider in any
	 * way.
	 */
	@Override
	public Set<ICanvasFeature> getSelectedFeatureSet() {
		return new HashSet<ICanvasFeature>(this.selectedFeatures);
	}

	@Override
	public int getGridSize() {
		return this.gridSize;
	}

	public void setGridSize(final int gridSize) {
		this.gridSize = gridSize;
	}

	public void deleteSelected() {

	}

	/**
	 * Retrieves the ICanvasFeature that represents the given element,
	 * regardless where it is contained within the component hierarchy. Returns
	 * null if the element is not present in the view.
	 * 
	 * @param element
	 *            the element to look for. Must not be null.
	 * @return the feature representing the given element, or null if there is
	 *         none.
	 */
	@Override
	@CheckForNull
	public ICanvasFeature findFeatureByModelObject(@Nonnull final Object element) {
		if (element == null) {
			throw new IllegalArgumentException("Element must not be null.");
		}
		for (final ICanvasFeature f : this.features) {
			final Object fe = f.getElement();
			if (fe == element) {
				return f;
			}
		}
		return null;
	}

	/**
	 * Clears all features contained in the provider.
	 */
	public void clearFeatures() {
		this.features.clear();
		this.selectedFeatures.clear();
	}

	/**
	 * Moves the given feature to the back of the canvas.
	 * 
	 * @param feature
	 *            the feature to move. Nothing happens if the feature is not
	 *            part of the feature list of this provider.
	 */
	public void moveToBack(final ICanvasFeature feature) {
		DBC.PRE.assertNotNull(feature, "The feature to move to the back must not be null.");
		final int i = this.features.indexOf(feature);
		if (i > 0) {
			this.features.remove(i);
			this.features.add(0, feature);
		}
	}

	/**
	 * Moves the given list of features to the back of the canvas.
	 * 
	 * @param toMove
	 *            the list of features to move. A feature included in this list
	 *            which is not present in the feature list of this provider is
	 *            simply ignored.
	 */
	public void moveToBack(final List<ICanvasFeature> toMove) {
		DBC.PRE.assertNotNull(toMove, "The features to move to the back must not be null.");
		for (int i = toMove.size() - 1; i >= 0; i--) {
			final ICanvasFeature feature = toMove.get(i);
			moveToBack(feature);
		}
	}

	@Override
	public void addFeature(final Feature feature) {
		DBC.PRE.assertNotNull(feature, "The feature to add must not be null.");
		this.features.add(feature);
	}

	@Override
	public void removeFeature(final Feature feature) {
		DBC.PRE.assertNotNull(feature, "The feature to remove must not be null.");
		this.features.remove(feature);
	}

}
