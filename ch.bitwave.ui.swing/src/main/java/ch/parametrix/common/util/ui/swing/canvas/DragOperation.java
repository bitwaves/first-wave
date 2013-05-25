package ch.parametrix.common.util.ui.swing.canvas;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.event.MouseEvent;

import ch.parametrix.common.util.ui.swing.WorldCoordinate;
import ch.parametrix.common.util.ui.swing.contracts.DeviceCoordinate;
import ch.parametrix.common.util.ui.swing.contracts.DeviceDistance;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeatureProvider;
import ch.parametrix.common.util.ui.swing.contracts.WorldDistance;

/**
 * Abstract ancestor for drag operations on a canvas. Tracks the drag origin and current drag position in screen space as well as world space.
 */
public abstract class DragOperation
{
	protected static final float[] DASHES = { 1.0f, 1.0f };
	protected static Stroke STROKE_SELECTION = new BasicStroke(0.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, DASHES, 0.0f);

	private final CanvasPanel canvas;
	protected DeviceCoordinate screenOrigin;
	protected WorldCoordinate worldOrigin;
	protected DeviceCoordinate screenPos;
	protected WorldCoordinate worldPos;
	protected ICanvasFeatureProvider provider;

	/**
	 * Creates a new DragOperation for mouse-dragging on the given canavas.
	 * 
	 * @param canvas
	 *            the canvas to drag on.
	 * @param provider
	 *            the provider responsible for providing the features.
	 */
	public DragOperation(final CanvasPanel canvas, final ICanvasFeatureProvider provider)
	{
		super();
		if (provider == null)
		{
			throw new IllegalArgumentException("Provider must not be null.");
		}
		this.provider = provider;
		this.canvas = canvas;
	}

	public void update(final MouseEvent me, final DeviceDistance deltaScreen, final WorldDistance deltaWorld)
	{
	}

	public final void setOrigin(final DeviceCoordinate screenPos, final WorldCoordinate worldPos)
	{
		this.screenOrigin = screenPos;
		this.worldOrigin = worldPos;
		setDragLocation(screenPos, worldPos);
	}

	public final void setDragLocation(final DeviceCoordinate screenPos, final WorldCoordinate worldPos)
	{
		this.screenPos = screenPos;
		this.worldPos = worldPos;
	}

	public final int getX()
	{
		return this.screenPos.getX();
	}

	public final int getY()
	{
		return this.screenPos.getY();
	}

	public void paint(final Graphics2D g)
	{

	}

	public CanvasPanel getCanvas()
	{
		return this.canvas;
	}

	public abstract void cancel();

	/**
	 * @throws OperationException
	 *             if the operation could not be successfully applied.
	 */
	public abstract void apply() throws OperationException;
}
