package ch.parametrix.common.util.ui.swing;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import ch.bitwave.platform.codestyle.DBC;

/**
 * Helper for rendering text into a fixed-width column placed whose top left
 * corner is fixed on the canvas. Provides the total height of the rendered
 * text. Wraps all text to the width specified in the constructor.
 */
// TODO: Deprecate this in favor of TextLayout or use TextLayout in
// implementation, see
// http://docs.oracle.com/javase/1.4.2/docs/guide/2d/spec/j2d-fonts.html
public class TextRenderer {
	private final Graphics2D g;
	private int widestLine = 0;
	private int width = 0;
	private static Font DEFAULT__FONT;
	private final int ax;
	private final int ay;
	private int dx = 0;
	private int dy = 0;
	private final boolean anchorAtBaseline;
	private double lineFeedFactor;
	private Font currentFont;
	private boolean isFirstLine;

	public static final LineRenderStrategy LRS_ALIGN_LEFT = new LineRenderStrategy() {
		@Override
		public void renderLine(final Graphics2D g, final TextRenderer tr, final String line,
				final int x, final int y) {
			g.drawString(line, x, y);
		}
	};

	public static final LineRenderStrategy LRS_CENTER = new LineRenderStrategy() {

		@Override
		public void renderLine(final Graphics2D g, final TextRenderer tr, final String line,
				final int x, final int y) {
			int w = tr.measureWidth(g, line);
			g.drawString(line, tr.ax + (tr.width - w) / 2, y);
		}
	};

	/**
	 * Creates a text renderer for rendering text into a column beginning at
	 * coordinate (ax, ax) and extending to the right of ax for the given width.
	 * 
	 * @param g
	 *            the graphics context to render the text on.
	 * @param ax
	 *            the x component of the top left corner of the column.
	 * @param ay
	 *            the y component of the top left corner of the column.
	 * @param width
	 *            the width of the column. All text is wrapped to this width.
	 * @param anchorAtBaseline
	 *            specifies whether the baseline (true) or the text top (false)
	 *            will be aligned with the column anchor.
	 */
	public TextRenderer(final Graphics2D g, final int ax, final int ay, final int width,
			final boolean anchorAtBaseline) {
		this.lineFeedFactor = 0.8;
		this.g = g;
		this.width = width;
		ensureDefaultFont(g);
		this.ax = ax;
		this.ay = ay;
		this.anchorAtBaseline = anchorAtBaseline;
		this.isFirstLine = true;
	}

	/**
	 * @param g
	 */
	@SuppressWarnings("nls")
	private void ensureDefaultFont(final Graphics2D g) {
		if (DEFAULT__FONT == null) {
			final Font givenFont = g.getFont();
			if ("Ubuntu".equals(givenFont.getName())) {
				DEFAULT__FONT = new Font("Ubuntu Light", Font.PLAIN, givenFont.getSize());
			} else {
				DEFAULT__FONT = new Font("Browallia New", Font.PLAIN,
						(int) (givenFont.getSize() * 1.2));
			}
		}
		this.currentFont = DEFAULT__FONT.deriveFont(DEFAULT__FONT.getSize());
		DBC.INTERIM.assertTrue("The current font must not be the default font.",
				this.currentFont != DEFAULT__FONT);
	}

	/**
	 * @return the lineFeedFactor
	 */
	public double getLineFeedFactor() {
		return this.lineFeedFactor;
	}

	/**
	 * @param lineFeedFactor
	 *            the lineFeedFactor to set
	 */
	public void setLineFeedFactor(final double lineFeedFactor) {
		this.lineFeedFactor = lineFeedFactor;
	}

	/**
	 * Renders the given lines of text onto the canvas associated with this
	 * renderer, ending the text with a line break. Lines are broken to the
	 * width of the column.
	 * 
	 * @param text
	 *            the text to render.
	 */
	public void renderLines(final String text) {
		if (text != null) {
			this.g.setFont(this.currentFont);
			final FontMetrics fm = this.g.getFontMetrics();
			if (this.isFirstLine && this.anchorAtBaseline) {
				this.dy -= fm.getAscent();
			}
			final List<String> lines = LineWrapper.breakIntoLines(fm, text.trim(), this.width,
					this.dx);
			final int lineFeed = (int) (this.lineFeedFactor * fm.getHeight());
			double lineWidth = 0;
			for (final String line : lines) {
				this.dy += lineFeed;
				final Rectangle2D extent = fm.getStringBounds(line, this.g);
				lineWidth = extent.getWidth();
				if (lineWidth > this.widestLine) {
					this.widestLine = (int) lineWidth;
				}
				this.g.drawString(line, this.ax + this.dx, this.ay + this.dy);
				this.dx = 0;
			}
			this.dx = 0;
			this.isFirstLine = false;
		}
	}

	/**
	 * Renders the given lines of text onto the canvas associated with this
	 * renderer, starting at the current cursor position. Lines are broken to
	 * the width of the column.
	 * 
	 * @param text
	 *            the text to render.
	 */
	public void renderText(final String text) {
		renderTextWithStrategy(text, LRS_ALIGN_LEFT);
	}

	public void renderTextCentered(final String text) {
		renderTextWithStrategy(text, LRS_CENTER);
	}

	private void renderTextWithStrategy(final String text, final LineRenderStrategy strategy) {
		if (text != null) {
			this.g.setFont(this.currentFont);
			final FontMetrics fm = this.g.getFontMetrics();
			if (this.isFirstLine && this.anchorAtBaseline) {
				this.dy -= fm.getAscent();
			}
			final List<String> lines = LineWrapper.breakIntoLines(fm, text.trim(), this.width,
					this.dx);
			final int lineFeed = (int) (this.lineFeedFactor * fm.getHeight());
			this.dy -= lineFeed;
			double lineWidth = 0;
			for (final String line : lines) {
				this.dy += lineFeed;
				final Rectangle2D extent = fm.getStringBounds(line, this.g);
				lineWidth = extent.getWidth();
				if (lineWidth > this.widestLine) {
					this.widestLine = (int) lineWidth;
				}
				strategy.renderLine(this.g, this, line, this.ax + this.dx, this.ay + this.dy);
				this.dx = 0;
			}
			this.dx = (int) lineWidth;
			this.isFirstLine = false;
		}
	}

	/**
	 * Sets the font style in which to render subsequent text output via the
	 * renderer.
	 * 
	 * @param style
	 *            the font style to apply, e.g. {@link Font#ITALIC}.
	 */
	public void setFontStyle(final int style) {
		this.currentFont = this.currentFont.deriveFont(style);
	}

	/**
	 * Scales the current font of the renderer by the given factor.
	 * 
	 * @param scaleFactor
	 *            the factor to scale the font by.
	 */
	public void scaleFont(final float scaleFactor) {
		this.currentFont = this.currentFont.deriveFont(this.currentFont.getSize2D() * scaleFactor);
	}

	/**
	 * Returns the current width of the column. This value may be smaller than
	 * the column width initially specified in the constructor if the text
	 * output so far did not span the entire column width.
	 * 
	 * @return the current width of the text rendered in the column.
	 */
	public double getWidth() {
		return this.widestLine;
	}

	/**
	 * Returns the height of the text rendered into the column so far.
	 * 
	 * @return the column height.
	 */
	public double getHeight() {
		return this.dy;
	}

	/**
	 * Returns the ascent of the current font, which is the height in pixels
	 * above the base-line.
	 * 
	 * @return the font ascent.
	 */
	public int getFontAscent() {
		this.g.setFont(this.currentFont);
		return this.g.getFontMetrics().getHeight();
	}

	/**
	 * @param text
	 * @return
	 */
	public int measureWidth(final Graphics2D g, final String text) {
		this.g.setFont(this.currentFont);
		return g.getFontMetrics().stringWidth(text);
	}
}
