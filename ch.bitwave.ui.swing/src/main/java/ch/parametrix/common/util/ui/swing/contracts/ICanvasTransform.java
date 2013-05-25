package ch.parametrix.common.util.ui.swing.contracts;

import ch.parametrix.common.util.ui.swing.WorldCoordinate;

/**
 * Provide access to the transformation of a canvas.
 * 
 */
public interface ICanvasTransform {

	/**
	 * Inverse-transforms a relative length along the X axis to world
	 * coordinates.
	 * 
	 * @param screen
	 *            the length on screen.
	 * @return the corresponding length in world coordinates.
	 */
	double transformXScreenLengthToWorld(final double screen);

	/**
	 * Inverse-transforms a relative length along the Y axis to world
	 * coordinates.
	 * 
	 * @param screen
	 *            the length on screen.
	 * @return the corresponding length in world coordinates.
	 */
	double transformYScreenLengthToWorld(final double screen);

	/**
	 * Inverse-transforms a point on the screen to absolute world coordinates.
	 * 
	 * @param screen
	 *            the canvas screen coordinate.
	 * @return the corresponding world coordinate.
	 */
	WorldCoordinate transformScreenToWorld(final DeviceCoordinate screen);

	/**
	 * Inverse-transforms a distance in screen space to a corresponding distance
	 * in world coordinates.
	 * 
	 * @param screen
	 *            the distance on screen.
	 * @return the corresponding distance in world coordinates.
	 */
	WorldDistance transformScreenDeltaToWorld(final DeviceDistance screen);

	/**
	 * Transforms a world coordinate to a point on the canvas screen.
	 * 
	 * @param x
	 *            the x coordinate in world space.
	 * @param y
	 *            the y coordinate in world space.
	 * @return the corresponding point on the canvas screen.
	 */
	DeviceCoordinate transformWorldToScreen(final double x, final double y);

	/**
	 * @param world
	 * @return
	 */
	DeviceCoordinate transformWorldToScreen(WorldCoordinate world);

	/**
	 * @param world
	 * @return
	 */
	int transformWorldLengthToScreen(final double world);

}
