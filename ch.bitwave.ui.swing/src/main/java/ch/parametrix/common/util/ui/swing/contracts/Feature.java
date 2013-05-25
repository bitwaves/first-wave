package ch.parametrix.common.util.ui.swing.contracts;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import ch.bitwave.platform.codestyle.DBC;
import ch.parametrix.common.util.ui.swing.WorldCoordinate;

/**
 * An occurrence of a logical model component in a logical view.
 * 
 */
public abstract class Feature implements ICanvasFeature {
	protected static final Color COLOR_REGULAR = new Color(255, 255, 255);
	protected static final Color COLOR_SELECTED = new Color(255, 128, 64);
	protected static final Stroke STROKE_FEATURE = new BasicStroke(6f);
	private static final double FLOAT_TOLERANCE = 1;

	protected WorldDistance size;
	protected WorldCoordinate anchor;
	private Rectangle2D bounds;
	private Object element;
	private final List<Feature> details;
	private Feature parent;

	public Feature(final Object element) {
		this.details = new ArrayList<Feature>();
		this.anchor = new WorldCoordinate();
		this.size = getInitialSize();
		this.bounds = new Rectangle2D.Double(this.anchor.getX(), this.anchor.getY(),
				this.size.getDx(), this.size.getDy());
		this.element = element;
	}

	/**
	 * @return the parent
	 */
	public Feature getParent() {
		return this.parent;
	}

	/**
	 * @param parent
	 *            the parent to set
	 */
	public void setParent(final Feature parent) {
		if (this.parent != parent) {
			if (this.parent != null) {
				this.parent.removeDetail(this);
			}
			this.parent = parent;
			if (this.parent != null) {
				this.parent.addDetail(this);
			}
		}
	}

	/**
	 * @param feature
	 */
	private void addDetail(final Feature feature) {
		if (this.details.contains(feature)) {
			this.details.add(feature);
			feature.setParent(this);
		}
	}

	/**
	 * @param feature
	 */
	private void removeDetail(final Feature feature) {
		if (this.details.remove(feature)) {
			feature.setParent(null);
		}
	}

	/**
	 * Returns the details of this feature.
	 * 
	 * @return the detail list.
	 */
	public List<Feature> getDetails() {
		return this.details;
	}

	protected abstract WorldDistance getInitialSize();

	@Override
	public Object getElement() {
		return this.element;
	}

	public void setElement(final Object element) {
		this.element = element;
	}

	/**
	 * Returns the anchor position of this feature.
	 * 
	 * @return
	 */
	public Point2D.Double getAnchor() {
		return new Point2D.Double(this.anchor.getX(), this.anchor.getY());
	}

	@Override
	public void setAnchor(final WorldCoordinate position) {
		place(position);
	}

	/**
	 * Render the feature onto the given graphics context.
	 * 
	 * @param view
	 * 
	 * @param g
	 *            the graphics context to draw the feature on.
	 */
	@Override
	public final void render(final ICanvasFeatureProvider provider, final Graphics2D g,
			final ICanvasTransform tx, final boolean isSelected) {
		int ax = (int) this.anchor.getX();
		int ay = (int) this.anchor.getY();
		Color featureColor = COLOR_REGULAR;
		if (isSelected) {
			featureColor = COLOR_SELECTED;
		}
		g.setStroke(STROKE_FEATURE);
		if (isModelLess() || this.element != null) {
			// It may happend that a feature does not refer to an element, i.e.
			// if a model file failed to load properly. In that case we simply
			// do not render the feature.
			render(provider, g, ax, ay, featureColor, isSelected);
		}
	}

	protected boolean isModelLess() {
		return false;
	}

	@Override
	public Rectangle2D getBounds() {
		return this.bounds;
	}

	protected abstract void render(final ICanvasFeatureProvider provider, final Graphics2D g,
			final int ax, final int ay, Color featureColor, boolean isSelected);

	@Override
	public boolean placeX(final double x) {
		if (Math.abs(this.anchor.getX() - x) > FLOAT_TOLERANCE) {
			this.anchor.setX(x);
			return true;
		}
		return false;
	}

	@Override
	public boolean placeY(final double y) {
		if (Math.abs(this.anchor.getY() - y) > FLOAT_TOLERANCE) {
			this.anchor.setY(y);
			return true;
		}
		return false;
	}

	@Override
	public boolean place(final double x, final double y) {
		if (Math.abs(this.anchor.getX() - x) > FLOAT_TOLERANCE
				|| Math.abs(this.anchor.getY() - y) > FLOAT_TOLERANCE) {
			this.anchor.setComponents(x, y);
			return true;
		}
		return false;
	}

	@Override
	public boolean place(@Nonnull final WorldCoordinate pos) {
		return place(pos.getX(), pos.getY());
	}

	protected Polygon mirrorPolyH(final Polygon p, final int x) {
		Polygon r = new Polygon();
		for (int i = 0; i < p.npoints; i++) {
			r.addPoint(x + (x - p.xpoints[i]), p.ypoints[i]);
		}
		return r;
	}

	@Override
	public void move(final ICanvasFeatureProvider provider,
			final Set<? extends ICanvasFeature> selection, final WorldDistance d) {
		this.anchor.setComponents(this.anchor.getX() + d.getDx(), this.anchor.getY() + d.getDy());
	}

	@Override
	public int getWidth() {
		return (int) this.size.getDx();
	}

	@Override
	public int getHeight() {
		return (int) this.size.getDy();
	}

	@Override
	public boolean isInside(final WorldCoordinate location) {
		return this.bounds.contains(location.toPoint2D());
	}

	@Override
	public List<? extends ICanvasFeature> getDetailFeatures() {
		List<ICanvasFeature> result = new ArrayList<ICanvasFeature>(getDetails().size());
		for (Object element : getDetails()) {
			result.add((ICanvasFeature) element);
		}
		return result;
	}

	/**
	 * Tells whether the given location in world coordinates is on the
	 * manipulator of this feature.
	 * 
	 * @param location
	 *            the location to check.
	 * @return true if the location is on the manipulator, false if not.
	 */
	public boolean isOnManipulator(final Point2D location) {
		if (!isResizable()) {
			return false;
		}
		double r = this.bounds.getX() + this.bounds.getWidth();
		double b = this.bounds.getY() + this.bounds.getHeight();
		int extent = 80;
		Rectangle2D manip = new Rectangle2D.Double(r - extent, b - extent, extent, extent);
		return manip.contains(location);
	}

	protected void setBounds(final double x, final double y, final double w, final double h) {
		this.bounds = new Rectangle2D.Double(x, y, w, h);
	}

	/**
	 * Tells whether the feature is resizable by the user.
	 * 
	 * @return true if the feature is resizable, false if not.
	 */
	@Override
	public boolean isResizable() {
		return false;
	}

	@Override
	public void setSize(final double width, final double height) {
		this.size.setComponents(width, height);
	}

	@Override
	public void setSize(final WorldDistance size) {
		DBC.PRE.assertNotNull(size, "The size to set must not be null.");
		this.size.setComponents(size.getDx(), size.getDy());
	}

	/**
	 * @return the size
	 */
	@Override
	public WorldDistance getSize() {
		return this.size;
	}

	@Override
	public boolean setWidth(final double width) {
		DBC.PRE.assertThat(0.0, greaterThanOrEqualTo(width));
		if (Math.abs(this.size.getDx() - width) > FLOAT_TOLERANCE) {
			this.size.setDx(width);
			return true;
		}
		return false;
	}

	@Override
	public boolean setHeight(final double height) {
		DBC.PRE.assertThat(0.0, greaterThanOrEqualTo(height));
		if (Math.abs(this.size.getDy() - height) > FLOAT_TOLERANCE) {
			this.size.setDy(height);
			return true;
		}
		return false;
	}

	/**
	 * Scales the feature by the given factor.
	 * 
	 * @param factor
	 *            the factor to scale by. 1 leaves the feature unchanged.
	 */
	public void scale(final double factor) {
		this.anchor = new WorldCoordinate(this.anchor.getX() * factor, this.anchor.getY() * factor);
		this.size = new WorldDistance(this.size.getDx() * factor, this.size.getDy() * factor);
	}

	@Override
	public boolean isCompositeFeature() {
		return false;
	}

	@Override
	public WorldCoordinate getPlacement() {
		return new WorldCoordinate(this.anchor);
	}

	@Override
	public void moveTo(final ICanvasFeatureProvider provider, final WorldCoordinate pos) {
		place(pos);
	}

	@Override
	public void adaptToView(final List<ICanvasFeature> features) {
	}

	/**
	 * Locates the closest feature containing an element of the given class
	 * within the given list of features.
	 * 
	 * @param features
	 *            the list of features to search.
	 * @param elementClass
	 *            the class of the logical element to look for.
	 * @return the closest feature matching the criterion. Null if there is
	 *         none..
	 */
	protected Feature findNearestFeatureOfModelType(final List<Feature> features,
			final Class<? extends Object> elementClass) {
		Feature bestFeature = null;
		double bestDistance = Double.MAX_VALUE;
		for (Feature feature : features) {
			Object model = feature.getElement();
			if (model != null && elementClass.isAssignableFrom(model.getClass())) {
				WorldCoordinate fp = getPlacement();
				WorldCoordinate cfp = feature.getPlacement();
				double dx = fp.getX() - cfp.getX();
				double dy = fp.getY() - cfp.getY();
				double distance = dx * dx + dy * dy;
				if (distance < bestDistance) {
					bestDistance = distance;
					bestFeature = feature;
				}
			}
		}
		return bestFeature;
	}

	/**
	 * <p>
	 * {@inheritDoc}
	 * </p>
	 * <p>
	 * Tries to retrieve the hint from the feature's model element via
	 * {@link IHintProvider}. Returns an empty string if the model object does
	 * not implement that interface.
	 * </p>
	 */
	@Override
	@Nonnull
	public String getHintText() {
		if (this.element instanceof IHintProvider) {
			return ((IHintProvider) this.element).getHintText();
		}
		return "";
	}

	@Override
	public boolean isSelectable() {
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [element=" + this.element + ", anchor=" + this.anchor
				+ ", size=" + this.size + "]";
	}

	/**
	 * Horizontally centers the element within its parent. Requires the parent
	 * to be set.
	 */
	public void centerX() {
		DBC.PRE.assertNotNull(this.parent, "parent");
		double cx = this.parent.getPlacement().getX()
				+ (this.parent.getSize().getDx() + getSize().getDx() / 2);
		placeX(cx);
	}

	public double getBottom() {
		return getPlacement().getY() + getSize().getDy();
	}

	public double getRight() {
		return getPlacement().getX() + getSize().getDx();
	}

	public double getTop() {
		return getPlacement().getY();
	}

	public double getLeft() {
		return getPlacement().getX();
	}

}
