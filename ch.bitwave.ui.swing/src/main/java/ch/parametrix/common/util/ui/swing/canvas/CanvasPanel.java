package ch.parametrix.common.util.ui.swing.canvas;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.annotation.Nonnull;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import org.apache.commons.lang.StringUtils;

import ch.bitwave.platform.codestyle.DBC;
import ch.parametrix.common.util.ui.swing.WorldCoordinate;
import ch.parametrix.common.util.ui.swing.clap.LayoutProcessor;
import ch.parametrix.common.util.ui.swing.contracts.DeviceCoordinate;
import ch.parametrix.common.util.ui.swing.contracts.DeviceDistance;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeature;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeatureProvider;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasTransform;
import ch.parametrix.common.util.ui.swing.contracts.IChangeManager;
import ch.parametrix.common.util.ui.swing.contracts.WorldDistance;
import ch.parametrix.common.util.ui.swing.editor.BasePanel;
import ch.parametrix.common.util.ui.swing.utility.CopyImageToClipboard;

/**
 * A canvas for displaying arbitrary UI features associated with corresponding
 * model elements. Provides a framework for graphical rendering or even editing
 * of a model. The canvas is capable of displaying a hint message on a
 * per-feature basis.
 * 
 */
public abstract class CanvasPanel extends BasePanel implements ICanvasTransform, ComponentListener,
		MouseMotionListener, MouseWheelListener, MouseListener {
	private static final long serialVersionUID = 1L;
	private static final String TX_INVERSION_FAILED_MESSAGE = "Failed to invert the transformation matrix."; //$NON-NLS-1$
	protected static final Stroke STROKE_BOUNDING_BOX = new BasicStroke(0.5f);
	private AffineTransform scaleTx;
	private AffineTransform translateTx;
	private AffineTransform viewTx;
	private WorldCoordinate lookAt;
	private DragOperation dragOp;
	private ICanvasFeatureProvider provider;
	private final LayoutProcessor layoutProcessor;
	private IChangeManager<?> changeManager;
	private boolean flipY;
	protected float fontScale;
	private RenderSettings settings;

	/**
	 * Creates a new canvas panel without specifying the feature provider.
	 */
	public CanvasPanel() {
		this.flipY = false;
		this.layoutProcessor = new LayoutProcessor();
		addMouseListener(this);
		addMouseWheelListener(this);
		addMouseMotionListener(this);
		addComponentListener(this);
		// getToolTipText() is only invoked if the static tooltip is not null.
		setToolTipText(StringUtils.EMPTY);
		this.fontScale = 10.0f;
		this.settings = new RenderSettings();

		InputMap inputMap = getInputMap();
		addBindings(inputMap);
	}

	protected void addBindings(final InputMap inputMap) {
		KeyStroke key = KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, Event.CTRL_MASK);
		CopyToClipboardAction action = new CopyToClipboardAction(this);
		ActionMap am = getActionMap();
		InputMap im = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		am.put(CopyToClipboardAction.ACTION_NAME, action);
		im.put(key, CopyToClipboardAction.ACTION_NAME);
	}

	/**
	 * Creates a new canvas panel showing the features from the given provider.
	 * 
	 * @param provider
	 *            the feature provider.
	 */
	public CanvasPanel(@Nonnull final ICanvasFeatureProvider provider) {
		this();
		DBC.PRE.assertNotNull(provider, "Provider must not be null");
		this.provider = provider;
	}

	/**
	 * Returns the layout processor in effect for this canvas.
	 * 
	 * @return the layout processor.
	 */
	public LayoutProcessor getLayoutProcessor() {
		return this.layoutProcessor;
	}

	/**
	 * Specifies whether the Y axis of the canvas should be inverted. Default is
	 * false, meaning that the Y axis points down.
	 * 
	 * @param flipY
	 *            true if the Y-axis should be flipped.
	 */
	public void setFlipY(final boolean flipY) {
		this.flipY = flipY;
	}

	protected void setLookAt(final WorldCoordinate lookAt) {
		this.lookAt = lookAt;
	}

	protected void setScale(final AffineTransform scale) {
		this.scaleTx = scale;
	}

	/**
	 * Gets the view transform for the current panel size.
	 * 
	 * @return
	 */
	protected AffineTransform getViewTransform() {
		if (this.viewTx == null) {
			updateViewTransform(this.settings);
		}
		return this.viewTx;
	}

	/**
	 * Gets the view transform for a specific canvas size.
	 * 
	 * @param width
	 *            the canvas width.
	 * @param height
	 *            the canvas height.
	 * @return
	 */
	protected AffineTransform getViewTransform(final RenderSettings settings) {
		if (this.viewTx == null) {
			updateViewTransform(settings);
		}
		return this.viewTx;
	}

	private void updateViewTransform(final RenderSettings settings) {
		if (this.scaleTx == null) {
			initViewTransform();
		}
		adjustTranslation(settings);
		final AffineTransform tx = new AffineTransform();
		tx.concatenate(this.scaleTx);
		tx.concatenate(this.translateTx);
		if (this.flipY) {
			final AffineTransform flipY = AffineTransform.getScaleInstance(1.0, -1.0);
			tx.concatenate(flipY);
		}
		this.viewTx = tx;
	}

	protected void initViewTransform() {
		this.scaleTx = AffineTransform.getScaleInstance(1, 1);
		this.lookAt = new WorldCoordinate();
	}

	private void adjustTranslation(final RenderSettings settings) {
		final int cw = settings.getWidth();
		final int ch = settings.getHeight();
		final double ww = transformXScreenLengthToWorld(cw);
		final double wh = transformYScreenLengthToWorld(ch);
		this.translateTx = AffineTransform.getTranslateInstance(ww / 2 - this.lookAt.getX(), wh / 2
				- this.lookAt.getY());
	}

	@Override
	public double transformXScreenLengthToWorld(final double screen) {
		final double[] rsWorld = new double[2];
		final double[] rsScreen = new double[2];
		rsScreen[0] = screen;
		AffineTransform invtx;
		try {
			invtx = this.scaleTx.createInverse();
			invtx.deltaTransform(rsScreen, 0, rsWorld, 0, 1);
			return rsWorld[0];
		} catch (final NoninvertibleTransformException e) {
			throw new RuntimeException(TX_INVERSION_FAILED_MESSAGE);
		}
	}

	@Override
	public double transformYScreenLengthToWorld(final double screen) {
		final double[] rsWorld = new double[2];
		final double[] rsScreen = new double[2];
		rsScreen[1] = screen;
		AffineTransform invtx;
		try {
			invtx = this.scaleTx.createInverse();
			invtx.deltaTransform(rsScreen, 0, rsWorld, 0, 1);
			return rsWorld[1];
		} catch (final NoninvertibleTransformException e) {
			throw new RuntimeException(TX_INVERSION_FAILED_MESSAGE);
		}
	}

	protected WorldCoordinate transformScreenToWorld(final AffineTransform tx, final Point screen) {
		final double[] rsWorld = new double[2];
		final double[] rsScreen = new double[2];
		rsScreen[0] = screen.getX();
		rsScreen[1] = screen.getY();
		AffineTransform invtx;
		try {
			invtx = tx.createInverse();
			invtx.transform(rsScreen, 0, rsWorld, 0, 1);
			return new WorldCoordinate(rsWorld[0], rsWorld[1]);
		} catch (final NoninvertibleTransformException e) {
			throw new RuntimeException(TX_INVERSION_FAILED_MESSAGE);
		}
	}

	@Override
	public WorldCoordinate transformScreenToWorld(final DeviceCoordinate screen) {
		final double[] rsWorld = new double[2];
		final double[] rsScreen = new double[2];
		rsScreen[0] = screen.getX();
		rsScreen[1] = screen.getY();
		AffineTransform invtx;
		try {
			invtx = getViewTransform().createInverse();
			invtx.transform(rsScreen, 0, rsWorld, 0, 1);
			return new WorldCoordinate(rsWorld[0], rsWorld[1]);
		} catch (final NoninvertibleTransformException e) {
			throw new RuntimeException(TX_INVERSION_FAILED_MESSAGE);
		}
	}

	@Override
	public WorldDistance transformScreenDeltaToWorld(final DeviceDistance delta) {
		final double[] rsWorld = new double[2];
		final double[] rsScreen = new double[2];
		rsScreen[0] = delta.getDx();
		rsScreen[1] = delta.getDy();
		AffineTransform invtx;
		try {
			invtx = getViewTransform().createInverse();
			invtx.deltaTransform(rsScreen, 0, rsWorld, 0, 1);
			return new WorldDistance(rsWorld[0], rsWorld[1]);
		} catch (final NoninvertibleTransformException e) {
			throw new RuntimeException(TX_INVERSION_FAILED_MESSAGE);
		}
	}

	@Override
	public DeviceCoordinate transformWorldToScreen(final WorldCoordinate world) {
		return transformWorldToScreen(world.getX(), world.getY());
	}

	@Override
	public DeviceCoordinate transformWorldToScreen(final double x, final double y) {
		final WorldCoordinate world = new WorldCoordinate(x, y);
		final Point2D screen = new Point2D.Double();
		getViewTransform().transform(world.toPoint2D(), screen);
		return new DeviceCoordinate(screen);
	}

	@Override
	public int transformWorldLengthToScreen(final double world) {
		final double[] rsWorld = new double[2];
		final double[] rsScreen = new double[2];
		rsWorld[0] = world;
		getViewTransform().deltaTransform(rsWorld, 0, rsScreen, 0, 1);
		final int rScreen = (int) rsScreen[0];
		return rScreen;
	}

	@Override
	public final void mouseDragged(final MouseEvent me) {
		final DeviceCoordinate locScreen = new DeviceCoordinate(me.getX(), me.getY());
		final WorldCoordinate locWorld = transformScreenToWorld(locScreen);
		if (this.scaleTx == null) {
			initViewTransform();
		}
		if (this.dragOp != null) {
			handleDrag(me);
		} else {
			beginDrag(me, locScreen, locWorld);
		}
		if (this.dragOp != null) {
			this.dragOp.setDragLocation(locScreen, locWorld);
		}
		repaint();
	}

	private void beginDrag(final MouseEvent me, final DeviceCoordinate locScreen,
			final WorldCoordinate locWorld) {
		if (me.isShiftDown() && !me.isAltDown() && !me.isControlDown()) {
			// pure SHIFT forces panning.
			this.dragOp = new PanOperation(this, this.provider);
		}
		if (me.isAltDown() && !me.isShiftDown() && !me.isControlDown()) {
			// pure ALT forces selection rectangle.
			this.dragOp = new SelectOperation(this, locWorld, this.provider);
		}
		if (this.dragOp == null) {
			this.dragOp = startCustomDragOperation(locWorld, me);
		}
		if (this.dragOp != null) {
			this.dragOp.setOrigin(locScreen, locWorld);
		}
	}

	private void handleDrag(final MouseEvent me) {
		final int dx = me.getX() - this.dragOp.getX();
		final int dy = me.getY() - this.dragOp.getY();
		final DeviceDistance deltaScreen = new DeviceDistance(dx, dy);
		final WorldDistance deltaWorld = transformScreenDeltaToWorld(deltaScreen);
		this.dragOp.update(me, deltaScreen, deltaWorld);
	}

	protected DragOperation startCustomDragOperation(final WorldCoordinate location,
			final MouseEvent me) {
		return null;
	}

	@Override
	public void mouseMoved(final MouseEvent event) {
		if (this.dragOp != null) {
			this.dragOp = null;
		}
	}

	@Override
	public void mouseWheelMoved(final MouseWheelEvent e) {
		final int amount = e.getWheelRotation();
		final double factor = Math.max(0.8, 1.0 - amount / 10.0);
		if (this.scaleTx == null) {
			initViewTransform();
		}
		this.scaleTx.concatenate(AffineTransform.getScaleInstance(factor, factor));
		invalidateViewTransform();
		repaint();
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		requestFocus();
		final WorldCoordinate worldLoc = transformScreenToWorld(new DeviceCoordinate(e.getPoint()));
		reaffirmSelection(worldLoc);
		if (e.getButton() == MouseEvent.BUTTON3) {
			if (this.dragOp != null) {
				this.dragOp.cancel();
				this.dragOp = null;
			}
		}
	}

	@Override
	public void mouseReleased(final MouseEvent event) {
		if (this.dragOp != null) {
			try {
				this.dragOp.apply();
			} catch (final OperationException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage(), "Operation failed",
						JOptionPane.OK_OPTION);
			}
			this.dragOp = null;
		}
		repaint();
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
		final WorldCoordinate worldLoc = transformScreenToWorld(new DeviceCoordinate(e.getPoint()));
		reaffirmSelection(worldLoc);
		super.mouseClicked(e);
	}

	/**
	 * Returns the most appropriate feature residing below the given world
	 * location.
	 * 
	 * @param location
	 *            the location to sample.
	 * @return a reference to the feature below that location.
	 */
	public ICanvasFeature findFeatureAt(final WorldCoordinate location) {
		double smallestFeature = Double.MAX_VALUE;
		ICanvasFeature bestFeature = null;
		for (final ICanvasFeature feature : this.provider.getCanvasFeatures()) {
			if (feature.isInside(location)) {
				final Rectangle2D bounds = feature.getBounds();
				final double w = bounds.getWidth();
				final double h = bounds.getHeight();
				final double area = w * h;
				if (area < smallestFeature) {
					smallestFeature = area;
					bestFeature = feature;
				}
			}
		}
		return bestFeature;
	}

	/**
	 * Returns the most appropriate selectable feature residing below the given
	 * world location.
	 * 
	 * @param location
	 *            the location to sample.
	 * @return a reference to the feature below that location.
	 */
	public ICanvasFeature findSelectableFeatureAt(final WorldCoordinate location) {
		double smallestFeature = Double.MAX_VALUE;
		ICanvasFeature bestFeature = null;
		for (final ICanvasFeature feature : this.provider.getCanvasFeatures()) {
			if (feature.isSelectable() && feature.isInside(location)) {
				final Rectangle2D bounds = feature.getBounds();
				final double w = bounds.getWidth();
				final double h = bounds.getHeight();
				final double area = w * h;
				if (area < smallestFeature) {
					smallestFeature = area;
					bestFeature = feature;
				}
			}
		}
		return bestFeature;
	}

	/**
	 * Selects the feature below the given location, clearing the a previous
	 * selection in the process. The selection will be empty if there is no
	 * feature to be found at the given location.
	 * 
	 * @param location
	 *            the location to select at.
	 */
	public void reselect(final WorldCoordinate location) {
		final ICanvasFeature selected = findSelectableFeatureAt(location);
		this.provider.selectFeature(selected);
	}

	protected void reaffirmSelection(final WorldCoordinate location) {
		final ICanvasFeature object = findSelectableFeatureAt(location);
		if (object == null) {
			this.provider.clearSelection();
			repaint();
		} else {
			if (!this.provider.isSelectedFeature(object)) {
				this.provider.selectFeature(object);
				repaint();
			}
		}
	}

	/**
	 * Adds the given transform to the current transform of the canvas.
	 * 
	 * @param tx
	 *            the transform to add.
	 */
	public void scaleCanvas(final AffineTransform tx) {
		if (this.scaleTx == null) {
			initViewTransform();
		}
		this.scaleTx.concatenate(tx);
		invalidateViewTransform();
		repaint();
	}

	/**
	 * Scales the current view transform by the given scale factor.
	 * 
	 * @param factor
	 *            the factor to scale the view by.
	 */
	private void scaleCanvas(final double factor) {
		scaleCanvas(AffineTransform.getScaleInstance(factor, factor));
	}

	@Override
	public final void paint(final Graphics g) {
		paintAt(g, this.settings);
	}

	private void paintAt(final Graphics g, final RenderSettings settings) {
		if (this.changeManager != null && this.changeManager.isInvalidated()) {
			updateModel(this.changeManager, this.layoutProcessor);
		}
		final Graphics2D g2 = (Graphics2D) g;
		g2.setColor(settings.getBackgroundColor());
		g2.fillRect(0, 0, settings.getWidth(), settings.getHeight());
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL,
				RenderingHints.VALUE_STROKE_NORMALIZE);
		g2.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
				RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		final Font font = g2.getFont();
		g2.setFont(font.deriveFont(font.getSize2D() * this.fontScale));
		paintCanvas(g2, settings);
	}

	/**
	 * Prompts an update of the view model by retrieving the current model from
	 * the given change manager and mapping it into the view model. This method
	 * is only invoked if a change manager is installed. This method does
	 * nothing by default, and must be overwritten if a change manager is used.
	 * 
	 * @param changeManager
	 *            the changeManager to retrieve the current model from.
	 * @param clap
	 *            the layout processor to use in performing the domain-to-view
	 *            mapping.
	 */
	protected void updateModel(@Nonnull final IChangeManager<?> changeManager,
			@Nonnull final LayoutProcessor clap) {

	}

	protected void paintCanvas(final Graphics2D g, final RenderSettings settings) {
		final AffineTransform viewTx = getViewTransform(settings);
		final AffineTransform oldTx = g.getTransform();
		g.transform(viewTx);
		if (this.provider != null) {
			this.layoutProcessor.layout(this.provider, g, this);
			// Draw the features with the effective canvas transform.
			for (final ICanvasFeature feature : this.provider.getCanvasFeatures()) {
				final boolean selected = this.provider.isSelectedFeature(feature);
				feature.render(this.provider, g, this, selected);
			}
		}
		g.setTransform(oldTx);
		// If desired, overlay info in screen device space.
		if (this.provider != null) {
			for (final ICanvasFeature feature : this.provider.getSelectedFeatureSet()) {
				paintFeatureOverlay(g, feature);
			}
		}
		// Finally draw the current drag operation, if required.
		if (this.dragOp != null) {
			this.dragOp.paint(g);
		}
	}

	/**
	 * Overlays editing support geometry on top of the given feature. This
	 * support geometry does not pass through the regular canvas transform, so
	 * line widths will not scale with the current zoom factor. All geometry
	 * must be individually transformed into screen space to align correctly
	 * with the canvas.
	 * 
	 * @param g
	 *            the graphics context to draw on.
	 * @param feature
	 *            the feature to draw the overlay for.
	 */
	private void paintFeatureOverlay(final Graphics2D g, final ICanvasFeature feature) {
		final WorldCoordinate anchor = feature.getPlacement();
		final DeviceCoordinate screenAnchor = transformWorldToScreen(anchor);
		final int ax = screenAnchor.getX();
		final int ay = screenAnchor.getY();
		{
			// Bounding box
			g.setStroke(STROKE_BOUNDING_BOX);
			final Rectangle2D b = feature.getBounds();
			final DeviceCoordinate topLeft = transformWorldToScreen(b.getX(), b.getY());
			final int w = transformWorldLengthToScreen(b.getWidth());
			final int h = transformWorldLengthToScreen(b.getHeight());
			final int bx = topLeft.getX();
			final int by = topLeft.getY();
			g.drawRect(bx, by, w, h);
			if (feature.isResizable()) {
				final int extent = 10;
				g.fillRect(bx + w - extent, by + h - extent, extent, extent);
			}
		}
		{
			// Anchor tick
			g.setColor(Color.GRAY);
			final int extent = 3;
			g.drawLine(ax - extent, ay, ax + extent, ay);
			g.drawLine(ax, ay - extent, ax, ay + extent);
		}
	}

	/**
	 * Returns the current focus point of the canvas, in world coordinates.
	 * 
	 * @return the look-at vector.
	 */
	public WorldCoordinate getLookAt() {
		return this.lookAt;
	}

	/**
	 * Focuses the canvas on a new point in world coordinates.
	 * 
	 * @param x
	 *            the X component of the new focus.
	 * @param y
	 *            the Y component of the new focus.
	 */
	protected void setLookAt(final double x, final double y) {
		this.lookAt = new WorldCoordinate(x, y);
	}

	/**
	 * Call this after the view transformation matrix has changed, otherwise a
	 * repaint will not reflect the change.
	 */
	private void invalidateViewTransform() {
		this.viewTx = null;
	}

	/**
	 * Changes the feature provider of this canvas. Does not automatically
	 * invalidate the canvas.
	 * 
	 * @param provider
	 *            the provider to set.
	 */
	public void setProvider(final ICanvasFeatureProvider provider) {
		this.provider = provider;
	}

	protected ICanvasFeatureProvider getProvider() {
		return this.provider;
	}

	/**
	 * Tells whether the Y axis of the canvas is flipped.
	 * 
	 * @return true if it is flipped, false if not.
	 */
	public boolean isYFlipped() {
		return this.flipY;
	}

	/**
	 * Centers the canvas on the given point in world coordinates.
	 * 
	 * @param x
	 *            the X component to center on.
	 * @param y
	 *            the Y component to center on.
	 */
	public void centerOn(final double x, final double y) {
		setLookAt(x, y);
		invalidateViewTransform();
	}

	/**
	 * <p>
	 * {@inheritDoc}
	 * </p>
	 * <p>
	 * The CanvasPanel retrieves its tooltip from the feature currently below
	 * the cursor. It never displays a hint on the empty canvas area.
	 * </p>
	 */
	@Override
	public String getToolTipText(final MouseEvent event) {
		final WorldCoordinate worldLocation = transformScreenToWorld(new DeviceCoordinate(
				event.getX(), event.getY()));
		final ICanvasFeature feature = findFeatureAt(worldLocation);
		if (feature == null) {
			return null;
		}
		final String text = feature.getHintText();
		if (StringUtils.isEmpty(text)) {
			// If the feature returns an empty string, return null to prevent
			// the tooltip from being displayed at all.
			return null;
		}
		return text;
	}

	/**
	 * Returns the change manager in use by this canvas.
	 * 
	 * @return a reference to the change manager.
	 */
	public IChangeManager<?> getChangeManager() {
		return this.changeManager;
	}

	/**
	 * Changes the change manager to be used by this canvas.
	 * 
	 * @param changeManager
	 *            the change manager to use.
	 */
	public void setChangeManager(final IChangeManager<?> changeManager) {
		this.changeManager = changeManager;
	}

	@Override
	public void componentResized(final ComponentEvent e) {
		this.settings.setSize(getWidth(), getHeight());
		if (this.lookAt != null) {
			centerOn(this.lookAt.getX(), this.lookAt.getY());
		}
	}

	@Override
	public void componentHidden(final ComponentEvent e) {
		// Nothing to do
	}

	@Override
	public void componentMoved(final ComponentEvent e) {
		// Nothing to do
	}

	@Override
	public void componentShown(final ComponentEvent e) {
		// Nothing to do
	}

	/**
	 * Renders an image of the current canvas contents at the given resolution
	 * and copies it into the clipboard.
	 * 
	 * @param settings
	 *            the settings to render the image at.
	 */
	public void copyImageToClipboard(final RenderSettings settings) {
		try {
			Image image = renderImage(settings);
			new CopyImageToClipboard().copyToClipboard(image);
		} catch (OutOfMemoryError e) {
			throw new RuntimeException(
					String.format(
							"The Java heap is not large enough for producing a screenshot at the requested size of %d x %d pixels (%d dpi)",
							settings.getWidth(), settings.getHeight(), settings.getResolution()));
		}
	}

	private Image renderImage(final RenderSettings settings) {
		WorldDistance worldCanvasSize = transformScreenDeltaToWorld(new DeviceDistance(getWidth(),
				getHeight()));
		// The scale factor required to bring the arbitrary
		// world units into inch space.
		double scale = .002;
		double worldScale = settings.getResolution() * scale;
		int width = (int) (worldCanvasSize.getDx() * worldScale);
		int height = (int) (worldCanvasSize.getDy() * worldScale);
		settings.setSize(width, height);
		double scaleFactor = (double) width / (double) getWidth();
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		scaleCanvas(scaleFactor);
		paintAt(g, settings);
		scaleCanvas(1 / scaleFactor);
		return image;
	}
}
