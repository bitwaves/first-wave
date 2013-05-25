package ch.parametrix.common.util.ui.swing.editor;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;
import javax.swing.text.Keymap;
import javax.swing.text.PlainDocument;
import javax.swing.text.TextAction;

import ch.parametrix.common.util.ui.swing.binding.IAbstractControl;

/**
 * An editor for date/time values. Adapted from
 * http://www.java2s.com/Code/Java/Development-Class/SwingDateTimeEditor.htm.
 */
public class DateTimeEditor extends JPanel implements IAbstractControl, FocusListener {
	private static final long serialVersionUID = 1L;
	private static final int[] FIELD_TYPES = { DateFormat.ERA_FIELD, DateFormat.YEAR_FIELD,
			DateFormat.MONTH_FIELD, DateFormat.DATE_FIELD, DateFormat.HOUR_OF_DAY1_FIELD,
			DateFormat.HOUR_OF_DAY0_FIELD, DateFormat.MINUTE_FIELD, DateFormat.SECOND_FIELD,
			DateFormat.MILLISECOND_FIELD, DateFormat.DAY_OF_WEEK_FIELD,
			DateFormat.DAY_OF_YEAR_FIELD, DateFormat.DAY_OF_WEEK_IN_MONTH_FIELD,
			DateFormat.WEEK_OF_YEAR_FIELD, DateFormat.WEEK_OF_MONTH_FIELD, DateFormat.AM_PM_FIELD,
			DateFormat.HOUR1_FIELD, DateFormat.HOUR0_FIELD };

	private DateFormat dateFormat;
	private final Calendar currentDate = Calendar.getInstance();
	private final List<FieldPosition> fieldPositions = new ArrayList<FieldPosition>();
	private Caret caret;
	private int currentFieldIndex = -1;
	private JTextField textField;
	private Spinner spinner;
	private final AbstractAction upAction = new UpDownAction(1, "up"); //$NON-NLS-1$
	private final AbstractAction downAction = new UpDownAction(-1, "down"); //$NON-NLS-1$
	private boolean settingDateText = false; // BUG FIX

	/**
	 * Creates a new date/time editor.
	 */
	public DateTimeEditor() {
		this.dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
		init();
	}

	private void init() {
		setLayout(new BorderLayout());
		this.textField = new JTextField();
		this.textField.setDocument(new DateTimeDocument());
		this.textField.addFocusListener(this);
		this.spinner = new Spinner();
		this.spinner.getIncrementButton().addActionListener(this.upAction);
		this.spinner.getDecrementButton().addActionListener(this.downAction);
		add(this.textField, "Center"); //$NON-NLS-1$
		add(this.spinner, "East"); //$NON-NLS-1$
		this.caret = this.textField.getCaret();
		this.caret.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(final ChangeEvent evt) {
				setCurField();
			}
		});
		setupKeymap();
		reinit();
	}

	protected class DateTimeDocument extends PlainDocument {
		private static final long serialVersionUID = 1L;

		@Override
		public void insertString(final int offset, final String str, final AttributeSet a)
				throws BadLocationException {
			if (DateTimeEditor.this.settingDateText) {
				super.insertString(offset, str, a);
			}
		}

	}

	/**
	 * Returns the current date value of the editor.
	 * 
	 * @return the date in the form of a Calendar instance.
	 */
	public Calendar getDate() {
		return (this.currentDate);
	}

	/**
	 * Programmatically sets the current date value of the editor.
	 * 
	 * @param date
	 *            the date to set.
	 */
	public void setDate(final Calendar date) {
		this.currentDate.setTime(date.getTime());
		this.settingDateText = true;
		this.textField.setText(this.dateFormat.format(this.currentDate.getTime()));
		this.settingDateText = false;
		getFieldPositions();
	}

	private FieldPosition getFieldPosition(final int fieldNum) {
		for (FieldPosition fieldPos : this.fieldPositions) {
			if (fieldPos.getField() == fieldNum) {
				return fieldPos;
			}
		}
		return null;
	}

	private void reinit() {
		setDate(this.currentDate);
		this.caret.setDot(0);
		setCurField();
		repaint();
	}

	protected class UpDownAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		int direction; // +1 = up; -1 = down

		public UpDownAction(final int direction, final String name) {
			super(name);
			this.direction = direction;
		}

		@Override
		public void actionPerformed(final ActionEvent evt) {
			if (!this.isEnabled()) {
				return;
			}
			boolean dateSet = true;
			Calendar curDate = DateTimeEditor.this.currentDate;
			switch (DateTimeEditor.this.currentFieldIndex) {
			case DateFormat.AM_PM_FIELD:
				curDate.add(Calendar.HOUR_OF_DAY, this.direction * 12);
				break;
			case DateFormat.DATE_FIELD:
			case DateFormat.DAY_OF_WEEK_FIELD:
			case DateFormat.DAY_OF_WEEK_IN_MONTH_FIELD:
			case DateFormat.DAY_OF_YEAR_FIELD:
				curDate.add(Calendar.DAY_OF_MONTH, this.direction);
				break;
			case DateFormat.ERA_FIELD:
				dateSet = false;
				break;
			case DateFormat.HOUR0_FIELD:
			case DateFormat.HOUR1_FIELD:
			case DateFormat.HOUR_OF_DAY0_FIELD:
			case DateFormat.HOUR_OF_DAY1_FIELD:
				curDate.add(Calendar.HOUR_OF_DAY, this.direction);
				break;
			case DateFormat.MILLISECOND_FIELD:
				curDate.add(Calendar.MILLISECOND, this.direction);
				break;
			case DateFormat.MINUTE_FIELD:
				curDate.add(Calendar.MINUTE, this.direction);
				break;
			case DateFormat.MONTH_FIELD:
				curDate.add(Calendar.MONTH, this.direction);
				break;
			case DateFormat.SECOND_FIELD:
				curDate.add(Calendar.SECOND, this.direction);
				break;
			case DateFormat.WEEK_OF_MONTH_FIELD:
				curDate.add(Calendar.WEEK_OF_MONTH, this.direction);
				break;
			case DateFormat.WEEK_OF_YEAR_FIELD:
				curDate.add(Calendar.WEEK_OF_YEAR, this.direction);
				break;
			case DateFormat.YEAR_FIELD:
				curDate.add(Calendar.YEAR, this.direction);
				break;
			default:
				dateSet = false;
			}
			if (dateSet) {
				int fieldId = DateTimeEditor.this.currentFieldIndex;
				setDate(curDate);
				FieldPosition fieldPosition = getFieldPosition(fieldId);
				DateTimeEditor.this.caret.setDot(fieldPosition.getBeginIndex());
				DateTimeEditor.this.textField.requestFocus();
				repaint();
			}
		}
	}

	protected class BackwardAction extends TextAction {
		private static final long serialVersionUID = 1L;

		BackwardAction(final String name) {
			super(name);
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			JTextComponent target = getTextComponent(e);
			if (target != null) {
				int dot = target.getCaretPosition();
				if (dot > 0) {
					FieldPosition position = getPrevField(dot);
					if (position != null) {
						target.setCaretPosition(position.getBeginIndex());
					} else {
						position = getFirstField();
						if (position != null) {
							target.setCaretPosition(position.getBeginIndex());
						}
					}
				} else {
					target.getToolkit().beep();
				}
				target.getCaret().setMagicCaretPosition(null);
			}
		}
	}

	protected class ForwardAction extends TextAction {
		private static final long serialVersionUID = 1L;

		ForwardAction(final String name) {
			super(name);
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			JTextComponent target = getTextComponent(e);
			if (target != null) {
				FieldPosition position = getNextField(target.getCaretPosition());
				if (position != null) {
					target.setCaretPosition(position.getBeginIndex());
				} else {
					position = getLastField();
					if (position != null) {
						target.setCaretPosition(position.getBeginIndex());
					}
				}
				target.getCaret().setMagicCaretPosition(null);
			}
		}
	}

	protected class BeginAction extends TextAction {
		private static final long serialVersionUID = 1L;

		BeginAction(final String name) {
			super(name);
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			JTextComponent target = getTextComponent(e);
			if (target != null) {
				FieldPosition position = getFirstField();
				if (position != null) {
					target.setCaretPosition(position.getBeginIndex());
				}
			}
		}
	}

	protected class EndAction extends TextAction {
		private static final long serialVersionUID = 1L;

		EndAction(final String name) {
			super(name);
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			JTextComponent target = getTextComponent(e);
			if (target != null) {
				FieldPosition position = getLastField();
				if (position != null) {
					target.setCaretPosition(position.getBeginIndex());
				}
			}
		}
	}

	protected void setupKeymap() {
		Keymap keymap = JTextComponent.addKeymap("DateTimeKeymap", null); //$NON-NLS-1$
		keymap.addActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), this.upAction);
		keymap.addActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), this.downAction);
		keymap.addActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0),
				new BackwardAction(DefaultEditorKit.backwardAction));
		keymap.addActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0),
				new ForwardAction(DefaultEditorKit.forwardAction));
		keymap.addActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_HOME, 0), new BeginAction(
				DefaultEditorKit.beginAction));
		keymap.addActionForKeyStroke(KeyStroke.getKeyStroke(KeyEvent.VK_END, 0), new EndAction(
				DefaultEditorKit.endAction));
		this.textField.setKeymap(keymap);
	}

	private void getFieldPositions() {
		this.fieldPositions.clear();
		for (int ctr = 0; ctr < FIELD_TYPES.length; ++ctr) {
			int fieldId = FIELD_TYPES[ctr];
			FieldPosition fieldPosition = new FieldPosition(fieldId);
			StringBuffer formattedField = new StringBuffer();
			this.dateFormat.format(this.currentDate.getTime(), formattedField, fieldPosition);
			if (fieldPosition.getEndIndex() > 0) {
				this.fieldPositions.add(fieldPosition);
			}
		}
		Collections.sort(this.fieldPositions, new Comparator<FieldPosition>() {
			@Override
			public int compare(final FieldPosition o1, final FieldPosition o2) {
				return ((o1).getBeginIndex() - (o2).getBeginIndex());
			}
		});
	}

	private FieldPosition getField(final int caretLoc) {
		for (FieldPosition chkFieldPosition : this.fieldPositions) {
			if ((chkFieldPosition.getBeginIndex() <= caretLoc)
					&& (chkFieldPosition.getEndIndex() > caretLoc)) {
				return chkFieldPosition;
			}
		}
		return null;
	}

	private FieldPosition getPrevField(final int caretLoc) {
		FieldPosition fieldPosition = null;
		for (int ctr = this.fieldPositions.size() - 1; ctr > -1; --ctr) {
			FieldPosition chkFieldPosition = this.fieldPositions.get(ctr);
			if (chkFieldPosition.getEndIndex() <= caretLoc) {
				fieldPosition = chkFieldPosition;
				break;
			}
		}
		return (fieldPosition);
	}

	private FieldPosition getNextField(final int caretLoc) {
		for (FieldPosition chkFieldPosition : this.fieldPositions) {
			if (chkFieldPosition.getBeginIndex() > caretLoc) {
				return chkFieldPosition;
			}
		}
		return null;
	}

	private FieldPosition getFirstField() {
		try {
			return this.fieldPositions.get(0);
		} catch (IndexOutOfBoundsException ex) {
			return null;
		}
	}

	private FieldPosition getLastField() {
		FieldPosition result = null;
		try {
			result = (this.fieldPositions.get(this.fieldPositions.size() - 1));
		} catch (NoSuchElementException ex) {
		}
		return (result);
	}

	private void setCurField() {
		FieldPosition fieldPosition = getField(this.caret.getDot());
		if (fieldPosition != null) {
			if (this.caret.getDot() != fieldPosition.getBeginIndex()) {
				this.caret.setDot(fieldPosition.getBeginIndex());
			}
		} else {
			fieldPosition = getPrevField(this.caret.getDot());
			if (fieldPosition != null) {
				this.caret.setDot(fieldPosition.getBeginIndex());
			} else {
				fieldPosition = getFirstField();
				if (fieldPosition != null) {
					this.caret.setDot(fieldPosition.getBeginIndex());
				}
			}
		}

		if (fieldPosition != null) {
			this.currentFieldIndex = fieldPosition.getField();
		} else {
			this.currentFieldIndex = -1;
		}
	}

	@Override
	public void setEnabled(final boolean enable) {
		this.textField.setEnabled(enable);
		this.spinner.setEnabled(enable);
	}

	@Override
	public boolean isEnabled() {
		return (this.textField.isEnabled() && this.spinner.isEnabled());
	}

	/**
	 * Sets the date format used by the editor.
	 * 
	 * @param format
	 *            the date format to set.
	 */
	public void setDateFormat(final DateFormat format) {
		this.dateFormat = format;
		reinit();
	}

	@Override
	public void focusGained(final FocusEvent e) {
		for (FocusListener listener : getFocusListeners()) {
			listener.focusGained(e);
		}
	}

	@Override
	public void focusLost(final FocusEvent e) {
		for (FocusListener listener : getFocusListeners()) {
			listener.focusLost(e);
		}
	}

	@Override
	public void setValue(final Object value) {
		if (!(value instanceof Calendar)) {
			throw new IllegalArgumentException(String.format(
					"The given value %s is not compatible with the %s.", value, this.getClass()
							.getSimpleName()));
		}
		setDate((Calendar) value);
	}

	@Override
	public Object getValue() {
		return getDate();
	}
}
