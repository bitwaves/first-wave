package ch.parametrix.common.util.ui.swing.canvas;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;

import ch.parametrix.common.util.ui.swing.WorldCoordinate;
import ch.parametrix.common.util.ui.swing.contracts.DeviceCoordinate;
import ch.parametrix.common.util.ui.swing.contracts.DeviceDistance;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeatureProvider;
import ch.parametrix.common.util.ui.swing.contracts.WorldDistance;

/**
 * A mouse operation for selecting features on the canvas.
 * 
 */
public class SelectOperation extends DragOperation {

	protected WorldCoordinate begin;
	protected WorldCoordinate end;

	/**
	 * Creates a new SelectOperation for the given canvas, beginning selection
	 * at the given location.
	 * 
	 * @param canvas
	 *            the canvas to select on.
	 * @param location
	 *            the initial mouse location.
	 * @param provider
	 *            the provider responsible for providing the Features.
	 */
	public SelectOperation(final CanvasPanel canvas, final WorldCoordinate location,
			final ICanvasFeatureProvider provider) {
		super(canvas, provider);
		this.begin = location;
		this.end = location;
	}

	@Override
	public void update(final MouseEvent me, final DeviceDistance deltaScreen,
			final WorldDistance deltaWorld) {
		this.end = new WorldCoordinate(this.end.getX() + deltaWorld.getDx(), this.end.getY()
				+ deltaWorld.getDy());
	}

	/**
	 * Returns the point of origin of the selection, which is the point where
	 * the user has initially depressed the mouse button.
	 * 
	 * @return the begin point.
	 */
	public WorldCoordinate getBegin() {
		return this.begin;
	}

	/**
	 * Returns the end point of the selection, which is the point where the user
	 * has released the mouse button.
	 * 
	 * @return the end point.
	 */
	public WorldCoordinate getEnd() {
		return this.end;
	}

	@Override
	public void paint(final Graphics2D g) {
		DeviceCoordinate screenBegin = getCanvas().transformWorldToScreen(this.begin.getX(),
				this.begin.getY());
		DeviceCoordinate screenEnd = getCanvas().transformWorldToScreen(this.end.getX(),
				this.end.getY());
		int x = screenBegin.getX();
		int y = screenBegin.getY();
		int x2 = screenEnd.getX();
		int y2 = screenEnd.getY();
		if (x > x2) {
			int swap = x;
			x = x2;
			x2 = swap;
		}
		if (y > y2) {
			int swap = y;
			y = y2;
			y2 = swap;
		}
		int w = x2 - x;
		int h = y2 - y;
		g.setColor(Color.BLACK);
		Stroke oldStroke = g.getStroke();
		g.setStroke(STROKE_SELECTION);
		g.drawRect(x, y, w, h);
		g.setStroke(oldStroke);
	}

	@Override
	public void apply() {
		if (this.provider != null) {
			double x = this.begin.getX();
			double y = this.begin.getY();
			double x2 = this.end.getX();
			double y2 = this.end.getY();
			if (x > x2) {
				double swap = x;
				x = x2;
				x2 = swap;
			}
			if (y > y2) {
				double swap = y;
				y = y2;
				y2 = swap;
			}
			this.provider.selectFeatures(x, y, x2, y2);
		}
	}

	@Override
	public void cancel() {

	}

}
