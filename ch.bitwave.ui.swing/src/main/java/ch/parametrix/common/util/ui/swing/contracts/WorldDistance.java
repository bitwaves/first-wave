package ch.parametrix.common.util.ui.swing.contracts;

/**
 * 
 */
public class WorldDistance {
	private double dx;
	private double dy;

	/**
	 * Creates a world distance with the given delta components.
	 */
	public WorldDistance(final double dx, final double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	/**
	 * @return the dx
	 */
	public double getDx() {
		return this.dx;
	}

	/**
	 * @return the dy
	 */
	public double getDy() {
		return this.dy;
	}

	/**
	 * @param dx
	 *            the dx to set
	 */
	public void setDx(final double dx) {
		this.dx = dx;
	}

	/**
	 * @param dy
	 *            the dy to set
	 */
	public void setDy(final double dy) {
		this.dy = dy;
	}

	/**
	 * @param width
	 * @param height
	 */
	public void setComponents(final double dx, final double dy) {
		this.dx = dx;
		this.dy = dy;
	}

	@Override
	public String toString() {
		return String.format("WD(%.2f, %.2f)", this.dx, this.dy);
	}

}
