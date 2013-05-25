package ch.parametrix.common.util.ui.swing.contracts;

import java.awt.geom.Point2D;

/**
 * 
 */
public class DeviceCoordinate {
	private final int x;
	private final int y;

	/**
	 * @param x
	 * @param y
	 */
	public DeviceCoordinate(final int x, final int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Creates a device coordinate from the given point.
	 * 
	 * @param pos
	 */
	public DeviceCoordinate(final Point2D pos) {
		this.x = (int) pos.getX();
		this.y = (int) pos.getY();
	}

	/**
	 * @return the x
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return this.y;
	}

}
