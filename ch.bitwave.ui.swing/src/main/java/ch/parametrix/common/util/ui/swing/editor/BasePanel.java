package ch.parametrix.common.util.ui.swing.editor;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.event.MouseInputListener;

/**
 * Extension of JPanel to add support for pop-up menus.
 */
public class BasePanel extends JPanel implements MouseInputListener
{
	private static final long serialVersionUID = 1L;
	private final Map<Component, JPopupMenu> popupMap = new HashMap<Component, JPopupMenu>();

	/**
	 * Creates a new BasePanel.
	 */
	public BasePanel()
	{
		this.addMouseListener(this);
	}

	protected void setPopup(final Component c, final JPopupMenu menu)
	{
		this.popupMap.put(c, menu);
		// Remove first because add with the same argument will cause multiple registrations. We are re-registering quite frequently here.
		c.removeMouseListener(this);
		c.addMouseListener(this);
	}

	@Override
	public void mouseClicked(final MouseEvent e)
	{
		final java.awt.Component c = e.getComponent();
		if (e.getButton() == MouseEvent.BUTTON3)
		{
			final JPopupMenu menu = getPopupForComponent(c);
			if (menu != null)
			{
				menu.show(c, e.getX(), e.getY());
			}
		}
	}

	private JPopupMenu getPopupForComponent(final java.awt.Component c)
	{
		java.awt.Component cur = c;
		while (cur != null)
		{
			final JPopupMenu menu = this.popupMap.get(c);
			if (menu != null)
			{
				return menu;
			}
			cur = cur.getParent();
		}
		return null;
	}

	@Override
	public void mouseEntered(final MouseEvent e)
	{

	}

	@Override
	public void mouseExited(final MouseEvent e)
	{

	}

	@Override
	public void mousePressed(final MouseEvent e)
	{

	}

	@Override
	public void mouseReleased(final MouseEvent e)
	{

	}

	@Override
	public void mouseDragged(final MouseEvent e)
	{

	}

	@Override
	public void mouseMoved(final MouseEvent e)
	{

	}

	protected void addPopupMenuAction(final JPopupMenu menu, final Action action)
	{
		final JMenuItem item = new JMenuItem(action);
		menu.add(item);
	}

	protected void addPopupMenuSeparator(final JPopupMenu menu)
	{
		final JSeparator sep = new JSeparator();
		menu.add(sep);
	}

}
