package ch.parametrix.common.util.ui.swing;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

/**
 * A label formatted to represent a section title.
 */
public class JTitle extends JLabel
{
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new title with the given text.
	 * 
	 * @param text
	 *            the title text to display.
	 */
	public JTitle(final String text)
	{
		super(text);
		setFormat();
	}

	public JTitle()
	{
		setFont(getFont().deriveFont(Font.BOLD, 12));
		setForeground(new Color(64, 128, 200));
	}

	private final void setFormat()
	{
		setFont(getFont().deriveFont(Font.BOLD, 12));
		setForeground(new Color(64, 128, 200));
	}
}
