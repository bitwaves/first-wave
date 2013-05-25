package ch.parametrix.common.util.ui.swing;

import java.awt.AWTEvent;
import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.Window;

import javax.annotation.Nonnull;
import javax.swing.JOptionPane;

/**
 * A replacement for the standard event queue that catches all runtime exceptions and presents adequate feedback to the application user.
 */
public class ExceptionHandlingEventQueue extends EventQueue
{

	private final String applicationName;

	/**
	 * Creates a queue for an application with the given name, which will be displayed in error messages presented to the user.
	 * 
	 * @param applicationName
	 *            the name of the application.
	 */
	private ExceptionHandlingEventQueue(final String applicationName)
	{
		super();
		this.applicationName = applicationName;
	}

	@Override
	protected void dispatchEvent(final AWTEvent newEvent)
	{
		try
		{
			super.dispatchEvent(newEvent);
		}
		catch (Throwable t)
		{
			String exceptionMessage = t.getMessage();
			String message;
			if (exceptionMessage != null)
			{
				message = exceptionMessage;
			}
			else
			{
				Class<? extends Throwable> class1 = t.getClass();
				if (t.getClass() != null)
				{
					message = String.format("An error has occurred (%s)", class1.getCanonicalName());
				}
				else
				{
					message = "An unknown error has occurred.";
				}
			}
			if (!isModalDialogShowing())
			{
				JOptionPane.showMessageDialog(null, message, this.applicationName, JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	protected boolean isModalDialogShowing()
	{
		Window[] windows = Window.getWindows();
		if (windows != null)
		{ // don't rely on current implementation, which at least returns [0].
			for (Window w : windows)
			{
				if (w.isShowing() && w instanceof Dialog && ((Dialog) w).isModal())
					return true;
			}
		}
		return false;
	}

	/**
	 * Creates and installs the queue for an application with the given name.
	 * 
	 * @param applicationName
	 *            the name of the application.
	 */
	public static void install(@Nonnull final String applicationName)
	{
		EventQueue queue = Toolkit.getDefaultToolkit().getSystemEventQueue();
		queue.push(new ExceptionHandlingEventQueue(applicationName));
	}
}
