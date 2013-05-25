package ch.parametrix.common.util.ui.swing.contracts;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.List;
import java.util.Set;

import javax.annotation.CheckForNull;

import ch.parametrix.common.util.ui.swing.WorldCoordinate;

/**
 * Interface for access to a UI feature representing a particular model object.
 * 
 */
public interface ICanvasFeature {

	/**
	 * Moves the feature by the given delta in world coordinates. The entire
	 * selection of features being moved is passed along to allow features that
	 * co-move associated elements to prevent moving the same feature twice.
	 * 
	 * @param provider
	 *            the provider through which additional features can be looked
	 *            up by model element.
	 * @param selection
	 * @param deltaWorld
	 */
	void move(final ICanvasFeatureProvider provider, Set<? extends ICanvasFeature> selection,
			WorldDistance deltaWorld);

	/**
	 * Returns the list of UI detail features associated with this feature.
	 * 
	 * @return the list of detail features.
	 */
	List<? extends ICanvasFeature> getDetailFeatures();

	/**
	 * Tells whether the feature is a detail that is always moved along with its
	 * parent feature, regardless of whether it is itself selected or not.
	 * 
	 * @return true if the feature is a composite.
	 */
	boolean isCompositeFeature();

	/**
	 * Returns the current placement of the feature in world space.
	 * 
	 * @return the current placement of the feature. This may or may not be the
	 *         top left corner of the bounding rectangle.
	 */
	WorldCoordinate getPlacement();

	/**
	 * Places the feature at the given X coordinate in world space. The y
	 * coordinate remains unchanged.
	 * 
	 * @param x
	 *            the world X coordinate.
	 * @return true if the feature has changed location, false if it already was
	 *         at the specified X position.
	 */
	boolean placeX(double x);

	/**
	 * Places the feature at the given X coordinate in world space. The X
	 * coordinate remains unchanged.
	 * 
	 * @param y
	 *            the world Y coordinate.
	 * @return true if the feature has changed location, false if it already was
	 *         at the specified Y position.
	 */
	boolean placeY(double y);

	/**
	 * Places the feature at the given coordinate in world space.
	 * 
	 * @param x
	 *            the world X coordinate.
	 * @param y
	 *            the world Y coordinate.
	 * @return true if the feature has changed location, false if it already was
	 *         at the specified location.
	 */
	boolean place(double x, double y);

	/**
	 * Places the feature at the given coordinate in world space.
	 * 
	 * @param x
	 *            the world X coordinate.
	 * @param y
	 *            the world Y coordinate.
	 */
	boolean place(WorldCoordinate location);

	/**
	 * Tells whether the given location is within the bounds of the feature.
	 * 
	 * @param location
	 *            the point in world coordinates to check.
	 * @return true if the point is inside the feature's bounding rectangle.
	 */
	boolean isInside(WorldCoordinate location);

	/**
	 * Returns the bounds of the feature in world coordinates. The bounds
	 * reflect the size of the feature, but may be offset from the feature
	 * anchor. The bounds are only updated when the feature is rendered.
	 * 
	 * @return the feature bounds.
	 */
	Rectangle2D getBounds();

	/**
	 * Renders the feature on the given canvas, using the given canvas
	 * transform.
	 * 
	 * @param provider
	 *            the provider which can be used to look up additional features
	 *            this feature depends on.
	 * @param g
	 *            the canvas to render to.
	 * @param tx
	 *            the transform currently in effect for the canvas.
	 * @param isSelected
	 *            tells whether the feature is currently selected.
	 */
	void render(final ICanvasFeatureProvider provider, final Graphics2D g, ICanvasTransform tx,
			final boolean isSelected);

	/**
	 * Moves the feature to the given coordinates. Unlike {@link #place}, this
	 * method requires a provider and enables the feature to revise the
	 * effective placement of the feature.
	 * 
	 * @param provider
	 * @param nx
	 * @param ny
	 */
	void moveTo(ICanvasFeatureProvider provider, WorldCoordinate location);

	/**
	 * Returns the model element associated with this feature.
	 * 
	 * @return
	 */
	Object getElement();

	/**
	 * Allows a feature newly introduced into a view to adapt to other features
	 * already present on the view. This may lead to the feature model being
	 * updated automatically for usability reasons.
	 * 
	 * @param features
	 *            the features present in the view.
	 */
	void adaptToView(List<ICanvasFeature> features);

	/**
	 * Returns the feature width in screen units.
	 * 
	 * @return the feature width.
	 */
	int getWidth();

	/**
	 * Returns the feature height in screen units.
	 * 
	 * @return the feature height.
	 */
	int getHeight();

	/**
	 * Places the anchor of this feature at the given world coordinates.
	 * 
	 * @param position
	 */
	void setAnchor(WorldCoordinate position);

	/**
	 * Returns the hint text of this feature.
	 * 
	 * @return the feature hint.
	 */
	String getHintText();

	/**
	 * Tells whether the feature is resizable by the user.
	 * 
	 * @return
	 */
	boolean isResizable();

	/**
	 * Returns an identifier uniquely representing the model object represented
	 * by this feature. Returns null if the feature cannot provide
	 * identification.
	 * 
	 * @return
	 */
	@CheckForNull
	String getIdentifier();

	/**
	 * Tells whether the feature can be selected. Defaults to true.
	 * 
	 * @return true if the feature can be selected, false if not.
	 */
	boolean isSelectable();

	/**
	 * Sets the width of the feature in world coordinates. Returns true if the
	 * width has effectively changed.
	 * 
	 * @param width
	 *            the width to set. Must be positive.
	 * @return true if the width has changed, false if not.
	 */
	boolean setWidth(double width);

	/**
	 * Sets the height of the feature in world coordinates. Returns true if the
	 * height has effectively changed.
	 * 
	 * @param height
	 *            the height to set. Must be positive.
	 * @return true if the height has changed, false if not.
	 */
	boolean setHeight(double height);

	/**
	 * Returns the size of the feature in world coordinates.
	 * 
	 * @return the feature size.
	 */
	WorldDistance getSize();

	/**
	 * Changes the size of the feature to the given width and height, in world
	 * coordinates.
	 * 
	 * @param width
	 *            the new feature width.
	 * @param height
	 *            the new feature height.
	 */
	void setSize(double width, double height);

	/**
	 * Changes the size of the feature to the given size.
	 * 
	 * @param size
	 *            the size to set.
	 */
	void setSize(WorldDistance size);

}
