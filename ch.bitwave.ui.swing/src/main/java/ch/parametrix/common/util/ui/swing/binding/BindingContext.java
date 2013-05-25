package ch.parametrix.common.util.ui.swing.binding;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.swing.JComponent;

import ch.bitwave.platform.codestyle.DBC;
import ch.bitwave.platform.prose.IProseBuilder;
import ch.bitwave.platform.prose.PlainProseBuilder;

/**
 * A context for keeping track of and manipulating data bindings between model
 * and UI.
 * 
 */
public class BindingContext {
	private final List<DataBinding> bindings;

	/**
	 * Creates a new, empty binding context.
	 */
	public BindingContext() {
		this.bindings = new ArrayList<DataBinding>();
	}

	/**
	 * Adds a new binding to the context.
	 * 
	 * @param binding
	 *            the binding to add.
	 */
	public void add(@Nonnull final DataBinding binding) {
		DBC.PRE.assertNotNull(binding, "The binding to add must not be null.");
		this.bindings.add(binding);
	}

	/**
	 * Clears all bindings in the context, detaching the bindings from the model
	 * and target in the process.
	 */
	public void clear() {
		for (final DataBinding binding : this.bindings) {
			binding.unbind();
		}
		this.bindings.clear();
	}

	/**
	 * Unbinds all bindings contained within the given UI container. Useful
	 * during partial reconstruction of a UI.
	 * 
	 * @param container
	 *            the container whose controls to unbind.
	 */
	public void unbind(@Nonnull final JComponent container) {
		DBC.PRE.assertNotNull(container, "The component to unbind must not be null.");
		final DataBinding[] dbs = this.bindings.toArray(new DataBinding[0]);
		for (final DataBinding db : dbs) {
			if (db.getContainer() == container) {
				this.bindings.remove(db);
				db.unbind();
			}
		}
	}

	/**
	 * Returns the current list of conversion issues tracked in the bindings.
	 * 
	 * @return the current list of issues, with each issue presented on its own
	 *         line.
	 */
	@Nonnull
	public List<String> getIssues() {
		final List<String> issues = new ArrayList<String>();
		for (final DataBinding binding : this.bindings) {
			final String issue = binding.getIssue();
			if (issue != null) {
				issues.add(issue);
			}
		}
		return issues;
	}

	/**
	 * Returns all issues of the context compiled into a single string, with
	 * each issue presented on its own line. The given preamble is output at the
	 * beginning of the list, also on its own line.
	 * 
	 * @param preamble
	 *            the message preamble. Omitted if null.
	 * @return the complete issue message.
	 */
	public String getIssueMessage(@Nullable final String preamble) {
		final List<String> issues = getIssues();
		final IProseBuilder pb = new PlainProseBuilder();
		if (preamble != null) {
			pb.appendLine(preamble);
		}
		for (final String string : issues) {
			pb.appendLine(string);
		}
		return pb.getText();
	}

	/**
	 * Tells whether the context currently has any issues.
	 * 
	 * @return true if there is at least one validation issue.
	 */
	public boolean hasIssues() {
		return getIssues().size() > 0;
	}

	/**
	 * Returns the number of bindings contained in this context.
	 * 
	 * @return the number of bindings.
	 */
	public int getSize() {
		return this.bindings.size();
	}
}
