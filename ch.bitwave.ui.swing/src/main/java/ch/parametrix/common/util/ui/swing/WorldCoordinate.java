package ch.parametrix.common.util.ui.swing;

import java.awt.geom.Point2D;

import javax.annotation.Nonnull;

import ch.bitwave.platform.codestyle.DBC;

/**
 * A coordinate in the world coordinate system of the canvas.
 * 
 */
public class WorldCoordinate {
	double x;
	double y;

	/**
	 * Creates a world coordinate for the given x and y components.
	 * 
	 * @param x
	 * @param y
	 */
	public WorldCoordinate(final double x, final double y) {
		super();
		this.x = x;
		this.y = y;
	}

	/**
	 * Creates a new world coordinate at (0, 0).
	 */
	public WorldCoordinate() {
	}

	/**
	 * Creates a coordinate with the same components as the given coordinate.
	 * 
	 * @param other
	 */
	public WorldCoordinate(@Nonnull final WorldCoordinate other) {
		DBC.PRE.assertNotNull(other, "other coordinate");
		this.x = other.x;
		this.y = other.y;
	}

	/**
	 * Creates a world coordinate equivalent to the given point.
	 * 
	 * @param pos
	 */
	public WorldCoordinate(final Point2D pos) {
		this.x = pos.getX();
		this.y = pos.getY();
	}

	/**
	 * @return the x
	 */
	public double getX() {
		return this.x;
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(final double x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return this.y;
	}

	/**
	 * @param y
	 *            the y to set
	 */
	public void setY(final double y) {
		this.y = y;
	}

	/**
	 * Converts the world coordinate to a Canvas2D point.
	 * 
	 * @return
	 */
	public Point2D toPoint2D() {
		return new Point2D.Double(this.x, this.y);
	}

	/**
	 * Moves the coordinate to the given components.
	 * 
	 * @param x
	 * @param y
	 */
	public void setComponents(final double x, final double y) {
		this.x = x;
		this.y = y;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(this.x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(this.y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorldCoordinate other = (WorldCoordinate) obj;
		if (Double.doubleToLongBits(this.x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(this.y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return String.format("WC(%.2f, %.2f)", this.x, this.y);
	}

}
