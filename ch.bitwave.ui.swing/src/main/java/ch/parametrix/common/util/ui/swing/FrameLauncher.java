package ch.parametrix.common.util.ui.swing;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

/**
 * Helper class for opening frames in the right size, at the right place on screen.
 */
public class FrameLauncher
{
	/**
	 * Opens the given frame with its current size centered on the screen.
	 * 
	 * @param frame
	 *            the frame to open. Must not be null.
	 */
	public static void openAtScreenCenter(final JFrame frame)
	{
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		int w = frame.getSize().width;
		int h = frame.getSize().height;
		int x = (dim.width - w) / 2;
		int y = (dim.height - h) / 2;
		frame.setLocation(x, y);
		frame.setVisible(true);
	}
}
