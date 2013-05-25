package ch.parametrix.common.util.ui.swing.editor;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicArrowButton;

/**
 * Adapted from http://www.java2s.com/Code/Java/Development-Class/SwingDateTimeEditor.htm.
 */
class Spinner extends JPanel
{
	private static final long serialVersionUID = 1L;
	private int m_orientation = SwingConstants.VERTICAL;
	private BasicArrowButton m_incrementButton;
	private BasicArrowButton m_decrementButton;

	public Spinner()
	{
		createComponents();
	}

	public Spinner(final int orientation)
	{
		this.m_orientation = orientation;
		createComponents();
	}

	@Override
	public void setEnabled(final boolean enable)
	{
		this.m_incrementButton.setEnabled(enable);
		this.m_decrementButton.setEnabled(enable);
	}

	@Override
	public boolean isEnabled()
	{
		return (this.m_incrementButton.isEnabled() && this.m_decrementButton.isEnabled());
	}

	protected void createComponents()
	{
		if (this.m_orientation == SwingConstants.VERTICAL)
		{
			setLayout(new GridLayout(2, 1));
			this.m_incrementButton = new BasicArrowButton(SwingConstants.NORTH);
			this.m_decrementButton = new BasicArrowButton(SwingConstants.SOUTH);
			add(this.m_incrementButton);
			add(this.m_decrementButton);
		}
		else if (this.m_orientation == SwingConstants.HORIZONTAL)
		{
			setLayout(new GridLayout(1, 2));
			this.m_incrementButton = new BasicArrowButton(SwingConstants.EAST);
			this.m_decrementButton = new BasicArrowButton(SwingConstants.WEST);
			add(this.m_decrementButton);
			add(this.m_incrementButton);
		}
	}

	public JButton getIncrementButton()
	{
		return (this.m_incrementButton);
	}

	public JButton getDecrementButton()
	{
		return (this.m_decrementButton);
	}
}