package ch.parametrix.common.util.ui.swing.editor;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.TableColumn;

import ch.parametrix.common.util.ui.swing.binding.BiDiDataBinding;
import ch.parametrix.common.util.ui.swing.binding.BiDiEnumerationBinding;
import ch.parametrix.common.util.ui.swing.binding.BindingContext;
import ch.parametrix.common.util.ui.swing.binding.BindingDefinition;
import ch.parametrix.common.util.ui.swing.binding.DataBinding;
import ch.parametrix.common.util.ui.swing.binding.EnumerationBindingDefinition;
import ch.parametrix.common.util.ui.swing.binding.EventBinding;
import ch.parametrix.common.util.ui.swing.binding.IModelObject;
import ch.parametrix.common.util.ui.swing.binding.LabelComparator;
import ch.parametrix.common.util.ui.swing.binding.TableBinding;
import ch.parametrix.common.util.ui.swing.binding.ValueConverter;

/**
 * A base class for panels containing UI elements bound to a model object, usually for the sake of editing the object.
 * 
 * Example of use:
 * 
 * <pre>
 * public class LoginPanel extends EditorPanel
 * {
 * 	public LoginPanel(IHostView view, IConnectClient client)
 * 	{
 * 		super();
 * 		setLayout(new PropertyLayout());
 * 		setBorder(new EmptyBorder(20, 20, 20, 20));
 * 		IHostConfiguration config = view.getHostConfig(); // config is the model object to edit.
 * 		addTextProperty(this, &quot;Host address&quot;, IHostConfiguration.HOST_ADDRESS, config, new StringValueConverter());
 * 		addTextProperty(this, &quot;Host port&quot;, IHostConfiguration.HOST_PORT, config, new IntegerValueConverter());
 * 		addTextProperty(this, &quot;User name&quot;, IHostConfiguration.USER_NAME, config, new StringValueConverter());
 * 		addPasswordProperty(this, &quot;Password&quot;, IHostConfiguration.USER_PASSWORD, config, new StringValueConverter());
 * 		add(new JLabel());
 * 		JPanel buttonPanel = new JPanel();
 * 		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
 * 		JButton button = new JButton(new LoginAction(view, client, getBindingContext()));
 * 		buttonPanel.add(button);
 * 		add(buttonPanel);
 * 	}
 * }
 * </pre>
 * 
 * Where the LoginAction checks whether the binding context is valid like this:
 * 
 * <pre>
 * public class LoginAction extends Action
 * {
 * 	private final BindingContext bindingContext;
 * 
 * 	// [...]
 * 
 * 	&#064;Override
 * 	protected void perform(ActionEvent e)
 * 	{
 * 		if (bindingContext.hasIssues())
 * 		{
 * 			throw new RuntimeException(bindingContext.getIssueMessage(&quot;Please resolve the following validation issues before logging on.&quot;));
 * 		}
 * 		// [...]
 * 	}
 * }
 * </pre>
 * 
 */
public class EditorPanel extends BasePanel
{
	private static final Icon ALERT_ICON = createImageIcon("/icon_alert.png", "Alert");
	private static final long serialVersionUID = 1L;
	private final BindingContext bindingContext;

	public EditorPanel()
	{
		super();
		this.bindingContext = new BindingContext();
	}

	protected static final ImageIcon createImageIcon(final String name, final String description)
	{
		java.net.URL imgURL = EditorPanel.class.getResource(name);
		if (imgURL != null)
		{
			return new ImageIcon(imgURL, description);
		}
		else
		{
			throw new RuntimeException(String.format("Failed to retrieve icon resource \"%s\"", name));
		}
	}

	protected Component addBinding(final JPanel parent, final JComponent edit, final DataBinding binding)
	{
		this.bindingContext.add(binding);
		Component field = decorateField(edit, binding);
		parent.add(field);
		binding.initialize();
		return field;
	}

	protected void addEventListener(final JComponent container, final PropertyChangeListener listener, final IModelObject model)
	{
		EventBinding binding = new EventBinding(container, model, listener);
		this.bindingContext.add(binding);
	}

	/**
	 * Completely unbinds the editor from the model, regardless to which container the bindings belong.
	 */
	public void unbind()
	{
		this.bindingContext.clear();
	}

	/**
	 * Unbinds the given container from the model. This method must be invoked whenever a container is removed from an editor, i.e. during partial
	 * updates of the editor. Attention: This method does not recurse into the contents of the container, and hence must be invoked for each component
	 * within the hierarchy. Use {@link #clearContents(JPanel)} instead to unbind and destroy an entire component tree.
	 * 
	 * @param container
	 */
	protected void unbind(final JComponent container)
	{
		bindingContext.unbind(container);
	}

	/**
	 * Unbinds the given panel and removes all its detail controls.
	 * 
	 * @param panel
	 */
	protected void clearContents(final JPanel panel)
	{
		unbind(panel);
		java.awt.Component[] contents = panel.getComponents();
		for (java.awt.Component detail : contents)
		{
			if (detail instanceof JComponent)
			{
				unbind((JComponent) detail);
			}
			panel.remove(detail);
		}
	}

	/**
	 * Decorates the given edit with an indicator that can be toggled by the binding in case the user has entered a value not compatible with the
	 * model attribute the control is bound to.
	 * 
	 * @param edit
	 * @param binding
	 * @return
	 */
	private java.awt.Component decorateField(final Component edit, final DataBinding binding)
	{
		Component wrappedEdit = edit;
		if (edit instanceof JTextArea || edit instanceof JTable)
		{
			JScrollPane sp = new JScrollPane(edit);
			wrappedEdit = sp;
		}
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.add(wrappedEdit, BorderLayout.CENTER);
		JLabel errorLabel = new JLabel();
		errorLabel.setIcon(ALERT_ICON);
		errorLabel.setVisible(false);
		panel.add(errorLabel, BorderLayout.EAST);
		if (binding instanceof BiDiDataBinding)
		{
			((BiDiDataBinding) binding).setErrorIndicator(errorLabel);
		}
		return panel;
	}

	protected JLabel addSectionLabel(final String text)
	{
		JLabel label = new JLabel(text);
		label.setFont(label.getFont().deriveFont(Font.BOLD));
		add(label);
		add(new JLabel(""));
		return label;
	}

	protected void addTextProperty(final String labelText, final String propertyName, final IModelObject model, final ValueConverter conv)
	{
		addTextProperty(this, labelText, propertyName, model, conv);
	}

	protected void addTextProperty(final JPanel parent, final String labelText, final String propertyName, final IModelObject model,
			final ValueConverter conv)
	{
		JLabel label = new JLabel(labelText);
		label.setFont(label.getFont().deriveFont(Font.PLAIN));
		parent.add(label);
		JTextField edit = new JTextField();
		BiDiDataBinding binding = new BiDiDataBinding(parent, model, edit, propertyName, conv);
		binding.setLabel(labelText);
		addBinding(parent, edit, binding);
	}

	protected void addPasswordProperty(final JPanel parent, final String labelText, final String propertyName, final IModelObject model,
			final ValueConverter conv)
	{
		JLabel label = new JLabel(labelText);
		label.setFont(label.getFont().deriveFont(Font.PLAIN));
		parent.add(label);
		JPasswordField edit = new JPasswordField();
		BiDiDataBinding binding = new BiDiDataBinding(parent, model, edit, propertyName, conv);
		binding.setLabel(labelText);
		addBinding(parent, edit, binding);
	}

	protected void addDateTimeProperty(final String labelText, final String propertyName, final IModelObject model, final ValueConverter conv)
	{
		addDateTimeProperty(this, labelText, propertyName, model, conv);
	}

	protected void addDateTimeProperty(final JPanel parent, final String labelText, final String propertyName, final IModelObject model,
			final ValueConverter conv)
	{
		JLabel label = new JLabel(labelText);
		label.setFont(label.getFont().deriveFont(Font.PLAIN));
		parent.add(label);
		DateTimeEditor edit = new DateTimeEditor();
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
		TimeZone tz = TimeZone.getTimeZone("UTC");
		tz.setRawOffset(0);
		df.setTimeZone(tz);
		edit.setDateFormat(df);
		BiDiDataBinding binding = new BiDiDataBinding(parent, model, edit, propertyName, conv);
		binding.setLabel(labelText);
		addBinding(parent, edit, binding);
	}

	protected void addMemoProperty(final String labelText, final String propertyName, final IModelObject model, final ValueConverter conv)
	{
		addMemoProperty(this, labelText, propertyName, model, conv);
	}

	protected void addMemoProperty(final JPanel parent, final String labelText, final String propertyName, final IModelObject model,
			final ValueConverter conv)
	{
		JLabel label = new JLabel(labelText);
		Font font = label.getFont().deriveFont(Font.PLAIN);
		label.setFont(font);
		parent.add(label);
		JTextArea edit = new JTextArea();
		patchTabFocus(edit);
		edit.setFont(font);
		edit.setLineWrap(true);
		BiDiDataBinding binding = new BiDiDataBinding(parent, model, edit, propertyName, conv);
		binding.setLabel(labelText);
		addBinding(parent, edit, binding);
	}

	/**
	 * Patch a JTextArea so that the TAB key works in regular controls. Makes sense if the TAB is not desired as memo content anyway.
	 * 
	 * @param edit
	 */
	private void patchTabFocus(final JTextArea edit)
	{
		Set<KeyStroke> strokes = new HashSet<KeyStroke>(Arrays.asList(KeyStroke.getKeyStroke("pressed TAB")));
		edit.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, strokes);
		strokes = new HashSet<KeyStroke>(Arrays.asList(KeyStroke.getKeyStroke("shift pressed TAB")));
		edit.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, strokes);
	}

	/**
	 * Adds two properties within the same row of the property editor, where the first editor occupies the space usually used by the property label.
	 * This is useful for long lists of time-value pairs where it is self-evident which control maps to which attribute. Both controls are unlabeled.
	 * 
	 * @param propertyName1
	 * @param propertyName2
	 * @param model
	 * @param conv1
	 * @param conv2
	 */
	protected void addTextPropertyTwin(final String propertyName1, final String propertyName2, final IModelObject model, final ValueConverter conv1,
			final ValueConverter conv2)
	{
		addTextPropertyTwin(this, propertyName1, propertyName2, model, conv1, conv2);
	}

	/**
	 * Adds two properties within the same row of the property editor, where the first editor occupies the space usually used by the property label.
	 * This is useful for long lists of time-value pairs where it is self-evident which control maps to which attribute. Both controls are unlabeled.
	 * 
	 * @param parent
	 *            the parent below which to create the edits.
	 * @param propertyName1
	 * @param propertyName2
	 * @param model
	 * @param conv1
	 * @param conv2
	 */
	protected void addTextPropertyTwin(final JPanel parent, final String propertyName1, final String propertyName2, final IModelObject model,
			final ValueConverter conv1, final ValueConverter conv2)
	{
		{
			JTextField edit = new JTextField();
			BiDiDataBinding binding = new BiDiDataBinding(parent, model, edit, propertyName1, conv1);
			addBinding(parent, edit, binding);
		}
		{
			JTextField edit = new JTextField();
			BiDiDataBinding binding = new BiDiDataBinding(parent, model, edit, propertyName2, conv2);
			addBinding(parent, edit, binding);
		}
	}

	protected JCheckBox addBooleanProperty(final String labelText, final String propertyName, final IModelObject model)
	{
		return addBooleanProperty(this, labelText, propertyName, model);
	}

	protected JCheckBox addBooleanProperty(final JPanel parent, final String labelText, final String propertyName, final IModelObject model)
	{
		JLabel label = new JLabel(labelText);
		label.setFont(label.getFont().deriveFont(Font.PLAIN));
		parent.add(label);
		JCheckBox edit = new JCheckBox();
		BiDiDataBinding binding = new BiDiDataBinding(parent, model, edit, propertyName, null);
		binding.setLabel(labelText);
		addBinding(parent, edit, binding);
		return edit;
	}

	protected void addEnumerationProperty(final String labelText, final String propertyName, final IModelObject model,
			final Class<? extends Enum<?>> enumClass)
	{
		addEnumerationProperty(this, labelText, propertyName, model, enumClass);
	}

	protected void addEnumerationProperty(final JPanel parent, final String labelText, final String propertyName, final IModelObject model,
			final Class<? extends Enum<?>> enumClass)
	{
		JLabel label = new JLabel(labelText);
		label.setFont(label.getFont().deriveFont(Font.PLAIN));
		parent.add(label);
		JComboBox edit = new JComboBox();
		Enum<?>[] constants = enumClass.getEnumConstants();
		Arrays.sort(constants, new LabelComparator());
		List<Enum<?>> list = Arrays.asList(constants);
		BiDiEnumerationBinding binding = new BiDiEnumerationBinding(parent, model, edit, propertyName, null, list);
		binding.setLabel(labelText);
		addBinding(parent, edit, binding);
	}

	protected void addEnumerationProperty(final String labelText, final String propertyName, final IModelObject model, final ValueConverter conv,
			final Class<? extends IModelObject> objectClass, final List<? extends IModelObject> options)
	{
		addEnumerationProperty(this, labelText, propertyName, model, conv, objectClass, options);
	}

	protected void addEnumerationProperty(final JPanel parent, final String labelText, final String propertyName, final IModelObject model,
			final ValueConverter conv, final Class<? extends IModelObject> objectClass, final List<? extends IModelObject> options)
	{
		JLabel label = new JLabel(labelText);
		label.setFont(label.getFont().deriveFont(Font.PLAIN));
		parent.add(label);
		JComboBox edit = new JComboBox();
		BiDiEnumerationBinding binding = new BiDiEnumerationBinding(parent, model, edit, propertyName, conv, options);
		binding.setLabel(labelText);
		addBinding(parent, edit, binding);
	}

	protected void addTable(final List<? extends IModelObject> objects, final List<BindingDefinition> bindingDefs, final int preferredHeight)
	{
		addTable(this, objects, bindingDefs, preferredHeight);
	}

	protected void addTable(final JPanel parent, final List<? extends IModelObject> objects, final List<BindingDefinition> bindingDefs,
			final int preferredHeight)
	{
		TableBinding binding = new TableBinding(parent, objects, bindingDefs);
		JTable table = new JTable(binding);
		table.setShowGrid(true);
		Component field = addBinding(parent, table, binding);
		field.setPreferredSize(new Dimension(0, preferredHeight));
		int index = 0;
		for (BindingDefinition bd : bindingDefs)
		{
			if (bd.isEnumeration())
			{
				EnumerationBindingDefinition sd = (EnumerationBindingDefinition) bd;
				TableColumn col = table.getColumnModel().getColumn(index);
				col.setCellEditor(new ComboboxElementCellEditor(sd));
				col.setCellRenderer(new ComboBoxRenderer(sd));
			}
			index++;
		}
	}

	/**
	 * Returns the binding context of the editor, containing all bindings regardless on what level of the component hierarchy they reside.
	 * 
	 * @return the binding context.
	 */
	public BindingContext getBindingContext()
	{
		return bindingContext;
	}

}
