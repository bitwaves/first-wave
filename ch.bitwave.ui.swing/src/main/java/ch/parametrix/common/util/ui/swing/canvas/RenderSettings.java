package ch.parametrix.common.util.ui.swing.canvas;

import java.awt.Color;

/**
 * Specifies options that govern how a canvas is rendered. Different options
 * will be required in different situations (on-screen display versus high-res
 * rendering to off-screen bitmap), and this class allows switching these
 * options between renders.
 */
public class RenderSettings {
	private int resolution;
	private Color backgroundColor;
	private int width;
	private int height;

	/**
	 * Creates default render options with a resolution of 96 DPI and a white
	 * background.
	 */
	public RenderSettings() {
		this.resolution = 96;
		this.backgroundColor = Color.white;
	}

	/**
	 * Returns the desired background color.
	 * 
	 * @return the desired background color.
	 */
	public Color getBackgroundColor() {
		return this.backgroundColor;
	}

	/**
	 * Sets the desired background color to the given color. This may be any
	 * RHBA color, including a fully transparent color in order to produce a
	 * transparent background.
	 * 
	 * @param backgroundColor
	 *            the background color to set.
	 */
	public void setBackgroundColor(final Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	/**
	 * The resolution to take the snapshot at, in DPI.
	 * 
	 * @return the snapshot resolution.
	 */
	public int getResolution() {
		return this.resolution;
	}

	/**
	 * Specifies the resolution to take the snapshot at, in DPI.
	 * 
	 * @param resolution
	 *            the resolution to set.
	 */
	public void setResolution(final int resolution) {
		this.resolution = resolution;
	}

	/**
	 * The width in device pixels at which to render the image.
	 * 
	 * @return the image width.
	 */
	public int getWidth() {
		return this.width;
	}

	/**
	 * The height in device pixels at which to render the image.
	 * 
	 * @return the image height.
	 */
	public int getHeight() {
		return this.height;
	}

	/**
	 * Sets the size of the image to produce in device pixels.
	 * 
	 * @param width
	 *            the image width.
	 * @param height
	 *            the image height.
	 */
	public void setSize(final int width, final int height) {
		this.width = width;
		this.height = height;
	}
}
