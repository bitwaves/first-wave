package ch.parametrix.common.util.ui.swing.canvas;

import java.awt.event.MouseEvent;

import ch.parametrix.common.util.ui.swing.WorldCoordinate;
import ch.parametrix.common.util.ui.swing.contracts.DeviceDistance;
import ch.parametrix.common.util.ui.swing.contracts.ICanvasFeatureProvider;
import ch.parametrix.common.util.ui.swing.contracts.WorldDistance;

/**
 * An operation for panning on a canvas.
 */
public class PanOperation extends DragOperation
{

	/**
	 * Creates a new PanOperation for panning on the given canvas.
	 * 
	 * @param canvas
	 *            the canvas to pan.
	 * @param provider
	 *            the provider responsible for providing the Features.
	 */
	public PanOperation(final CanvasPanel canvas, final ICanvasFeatureProvider provider)
	{
		super(canvas, provider);
	}

	@Override
	public void update(final MouseEvent me, final DeviceDistance deltaScreen, final WorldDistance deltaWorld)
	{
		CanvasPanel canvas = getCanvas();
		WorldCoordinate look = canvas.getLookAt();
		double newX = look.getX() - deltaWorld.getDx();
		double newY = canvas.isYFlipped() ? look.getY() + deltaWorld.getDy() : look.getY() - deltaWorld.getDy();
		canvas.centerOn(newX, newY);
	}

	@Override
	public void cancel()
	{

	}

	@Override
	public void apply()
	{

	}

}
