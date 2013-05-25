package ch.parametrix.common.util.ui.swing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

/**
 * A dialog that provides ready-made OK and a Cancel buttons. OK is the default button, and the dialog can also be cancelled by pressing the escape
 * key. Subclasses must override {@link #createContent()} (including a call to super.createContent()) and call this method from their constructor. In
 * createContent(), the subclass should add its dialog contents as the CENTER of the border layout prepared by the ancestor.
 */
public class ModalDialog extends JDialog implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private boolean confirmed;
	private static final String COMMAND_OK = "OK"; //$NON-NLS-1$
	private static final String COMMAND_CANCEL = "Cancel"; //$NON-NLS-1$

	/**
	 * Creates a new modal dialog.
	 */
	public ModalDialog()
	{
		super();
		this.confirmed = false;
	}

	protected void createContent()
	{
		setLayout(new BorderLayout());
		JPanel southPanel = createButtonPanel(this);
		add(southPanel, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(final ActionEvent e)
	{
		if (COMMAND_OK.equals(e.getActionCommand()))
		{
			this.confirmed = true;
			setVisible(false);
		}
	}

	private JPanel createButtonPanel(final ActionListener listener)
	{
		JPanel eastPanel = new JPanel();
		eastPanel.setLayout(new BorderLayout());
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout());
		JButton okButton = new JButton("OK");
		this.rootPane.setDefaultButton(okButton);
		this.rootPane.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), COMMAND_CANCEL);
		CancelAction cancelAction = new CancelAction(this);
		this.rootPane.getActionMap().put(COMMAND_CANCEL, cancelAction);
		okButton.setActionCommand(COMMAND_OK);
		okButton.addActionListener(listener);
		panel.add(okButton);
		JButton cancelButton = new JButton(cancelAction);
		cancelButton.addActionListener(listener);
		cancelButton.setActionCommand(COMMAND_CANCEL);
		panel.add(cancelButton);
		eastPanel.add(panel, BorderLayout.EAST);
		return eastPanel;
	}

	/**
	 * Tells whether the dialog has been positively confirmed by the user, i.e. via the OK button.
	 * 
	 * @return true if the dialog has been confirmed.
	 */
	public boolean isConfirmed()
	{
		return this.confirmed;
	}

	class CancelAction extends BaseAction
	{
		private static final long serialVersionUID = 1L;
		private final ModalDialog dialog;

		private CancelAction(final ModalDialog dialog)
		{
			super("Cancel");
			this.dialog = dialog;
		}

		@Override
		public void actionPerformed(final ActionEvent arg0)
		{
			this.dialog.setVisible(false);
		}

	}

}
