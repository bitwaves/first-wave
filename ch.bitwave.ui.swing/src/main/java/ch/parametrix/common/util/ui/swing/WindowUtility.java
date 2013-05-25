package ch.parametrix.common.util.ui.swing;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JComponent;
import javax.swing.JFrame;

/**
 * Utility methods for dealing with Swing windows.
 * 
 */
public class WindowUtility {

	/**
	 * Resizes the given component to the given width and height on centers it
	 * on screen.
	 * 
	 * @param comp
	 *            the component to center.
	 * @param width
	 *            the intended component width.
	 * @param height
	 *            the intended component height.
	 */
	public static final void centerOnScreen(final Component comp, final int width, final int height) {
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int X = (screen.width - width) / 2;
		int Y = (screen.height - height) / 2;
		comp.setBounds(X, Y, width, height);
	}

	/**
	 * Removes all detail components below the given parent.
	 * 
	 * @param parent
	 *            the component to clear.
	 */
	public static void clearContents(final JComponent parent) {
		java.awt.Component[] contents = parent.getComponents();
		for (Component detail : contents) {
			parent.remove(detail);
		}
	}

	public static final JFrame openView(final Component comp) {
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.add(comp, BorderLayout.CENTER);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		return frame;
	}
}
