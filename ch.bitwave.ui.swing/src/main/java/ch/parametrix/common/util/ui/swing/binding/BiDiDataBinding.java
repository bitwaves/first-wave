package ch.parametrix.common.util.ui.swing.binding;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.commons.lang.StringUtils;

import ch.bitwave.platform.reflect.BeanModificationException;
import ch.bitwave.platform.reflect.ReflectionUtility;

/**
 * Implementation of a bidirectional binding between a UI target (an edit
 * control) and the attribute of a model object. The model object must implement
 * property change support in order for the binding to work. The binding may
 * refer to an error decorator widget which will receive an error message if the
 * binding does not validate.
 */
public class BiDiDataBinding extends DataBinding implements FocusListener, ActionListener,
		PropertyChangeListener {

	protected Component control;
	private JLabel errorLabel;
	private final String propertyName;
	private String propertyLabel;

	/**
	 * Creates a binding between the given model object and the given control.
	 * The attribute bound to the control is specified through the attributeName
	 * and will be accessed through the bean getters/setters on the model
	 * object. An optional converter may be used to convert between model and UI
	 * representations of the attribute value.
	 * 
	 * @param container
	 *            the UI container to which the binding belongs. The container
	 *            governs the scope of the binding, so the binding will be
	 *            disposed if the container is removed.
	 * @param model
	 *            the model to bind. Values are transferred from the model to
	 *            the UI, and vice versa.
	 * @param control
	 *            the control to which to bind the model attribute.
	 * @param propertyName
	 *            the name of the model property to bind to the control.
	 * @param conv
	 *            the type converter to use in the process of transferring a
	 *            value from the model to the UI, and vice versa.
	 */
	public BiDiDataBinding(@Nonnull final JComponent container, @Nonnull final IModelObject model,
			@Nonnull final Component control, @Nonnull final String propertyName,
			@Nullable final ValueConverter conv) {
		super(container, model, conv);
		this.propertyName = propertyName;
		model.addPropertyChangeListener(this);
		this.control = control;
	}

	/**
	 * Applies the current attribute value of the model object to the control
	 * bound to the attribute. If the value fails to convert or cannot be
	 * written to the target, the target's error message is updated accordingly.
	 */
	protected void modelToTarget() {
		try {
			final Object value = getConvertedModelValue();
			writeValueToTarget(value);
		} catch (final Throwable t) {
			final String message = getErrorMessage(t);
			setError(message);
		}
	}

	/**
	 * Transfers the given value into the bound control.
	 * 
	 * @param value
	 *            the value to set.
	 */
	private void writeValueToTarget(final Object value) {
		if (this.control instanceof JTextField) {
			final JTextField edit = (JTextField) this.control;
			edit.setText(String.valueOf(value));
		} else if (this.control instanceof JCheckBox) {
			final JCheckBox edit = (JCheckBox) this.control;
			edit.setSelected(Boolean.valueOf(String.valueOf(value)));
		} else if (this.control instanceof JLabel) {
			final JLabel edit = (JLabel) this.control;
			edit.setText(String.valueOf(value));
		} else if (this.control instanceof JTextArea) {
			final JTextArea edit = (JTextArea) this.control;
			edit.setText(String.valueOf(value));
		} else if (this.control instanceof IAbstractControl) {
			final IAbstractControl edit = (IAbstractControl) this.control;
			edit.setValue(value);
		} else {
			throw new RuntimeException("Unsupported widget type: "
					+ this.control.getClass().getSimpleName());
		}
	}

	@Override
	public final void focusGained(final FocusEvent arg0) {

	}

	@Override
	public final void focusLost(final FocusEvent arg0) {
		targetToModel();
	}

	/**
	 * Transfers the current value kept by the UI control into the model object.
	 * If a converter is in use, the UI value is passed through the converter
	 * before writing it into the model.
	 */
	protected void targetToModel() {
		try {
			final Object value = readAndConvertTargetValue();
			ReflectionUtility.setBeanAttribute(getModel(), getPropertyName(), value);
			clearError();
		} catch (final Throwable t) {
			final String message = getErrorMessage(t);
			setError(message);
		}
	}

	/**
	 * Reads the current target value and attempts to convert it.
	 * 
	 * @return the model value corresponding to the current target value.
	 * @throws ConversionException
	 *             if the target-to-model conversion failed.
	 */
	private Object readAndConvertTargetValue() throws ConversionException {
		Object value = readValueFromTarget();
		if (getConverter() != null) {
			value = getConverter().convertTargetToModel(value);
		}
		return value;
	}

	/**
	 * Translates the given throwable into an error message suitable for display
	 * in the control's validation decorator.
	 * 
	 * @param t
	 *            the throwable to translate into an error message.
	 * @return the corresponding error message.
	 */
	protected String getErrorMessage(final Throwable t) {
		if (t instanceof NumberFormatException) {
			return "The given value cannot be applied because it does not represent a valid number.";
		}
		return t.getMessage();
	}

	protected void setError(final String message) {
		this.errorLabel.setVisible(true);
		this.errorLabel.setToolTipText(message);
		this.errorLabel.getParent().validate();
		this.errorLabel.getParent().repaint();
	}

	protected void clearError() {
		this.errorLabel.setVisible(false);
		this.errorLabel.setToolTipText("");
		this.errorLabel.getParent().validate();
		this.errorLabel.getParent().repaint();
	}

	/**
	 * Extracts the current value from the target control.
	 * 
	 * @return the target value.
	 */
	private Object readValueFromTarget() {
		if (this.control instanceof JTextField) {
			final JTextField edit = (JTextField) this.control;
			return edit.getText();
		} else if (this.control instanceof JTextArea) {
			final JTextArea edit = (JTextArea) this.control;
			return edit.getText();
		} else if (this.control instanceof JCheckBox) {
			final JCheckBox edit = (JCheckBox) this.control;
			return edit.isSelected();
		} else if (this.control instanceof IAbstractControl) {
			final IAbstractControl edit = (IAbstractControl) this.control;
			return edit.getValue();
		} else {
			throw new RuntimeException("Unsupported widget type: "
					+ this.control.getClass().getSimpleName());
		}
	}

	@Override
	public void initialize() {
		super.initialize();
		this.control.addFocusListener(this);
		modelToTarget();
		// read and attempt to convert the initial value to reflect an initial
		// issue:
		try {
			readAndConvertTargetValue();
		} catch (final Throwable t) {
			final String message = getErrorMessage(t);
			setError(message);
		}
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if ("comboBoxChanged".equals(e.getActionCommand())) {
			targetToModel();
		}
	}

	@Override
	public void propertyChange(final PropertyChangeEvent arg0) {
		if (getPropertyName().equals(arg0.getPropertyName())) {
			modelToTarget();
		}
	}

	public void setErrorIndicator(final JLabel errorLabel) {
		this.errorLabel = errorLabel;
	}

	@Override
	public void unbind() {
		getModel().removePropertyChangeListener(this);
	}

	public String getPropertyName() {
		return this.propertyName;
	}

	public Object getConvertedModelValue() throws BeanModificationException, ConversionException {
		Object value = getRawModelValue();
		final ValueConverter converter = this.getConverter();
		if (converter != null) {
			value = getConverter().convertModelToTarget(value);
		}
		return value;
	}

	public Object getRawModelValue() throws BeanModificationException {
		return ReflectionUtility.getBeanAttribute(getModel(), this.propertyName);
	}

	@Override
	public String getIssue() {
		final String issueText = this.errorLabel.getToolTipText();
		if (StringUtils.isEmpty(issueText)) {
			return null;
		}
		return getLabel() + ": " + issueText;
	}

	public void setLabel(final String label) {
		this.propertyLabel = label;
	}

	public String getLabel() {
		if (this.propertyLabel == null) {
			return this.propertyName;
		}
		return this.propertyLabel;
	}

}
