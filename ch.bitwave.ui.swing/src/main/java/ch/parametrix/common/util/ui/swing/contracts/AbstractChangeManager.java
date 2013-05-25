package ch.parametrix.common.util.ui.swing.contracts;

import java.util.List;
import java.util.Stack;

import javax.annotation.Nonnull;

import ch.bitwave.platform.reflect.SerialClone;

/**
 * Provides a base implementation of IChangeManager. The base covers stack
 * management and derivation of an updated model from a baseline model and the
 * stack of changes.
 * 
 */
public class AbstractChangeManager<T> implements IChangeManager<T> {
	private final T baselineModel;
	private T currentModel;
	private final Stack<AbstractChange<T>> changes;
	private int cursor;

	/**
	 * Creates an empty change manager. The manager is initially invalidated,
	 * which means that the model will be derived the first time it is
	 * retrieved.
	 * 
	 * @param baselineModel
	 *            the initial model to which changes are applied. The ontology
	 *            of the model is irrelevant ib this level of abstraction.
	 */
	public AbstractChangeManager(@Nonnull final T baselineModel) {
		this.baselineModel = baselineModel;
		this.changes = new Stack<AbstractChange<T>>();
	}

	@Override
	public void addChange(final AbstractChange<T> change) {
		collapseToCursor();
		this.changes.push(change);
		this.cursor++;
		invalidate();
	}

	/**
	 * Marks the manager as invalidated. This must happen every time the cursor
	 * or the change stack changes.
	 */
	private void invalidate() {
		this.currentModel = null;
	}

	/**
	 * Collapses the change stack down to the current cursor, retaining the
	 * change at the cursor, which means that this method is a no-op if the
	 * cursor is currently at the top of the stack.
	 */
	private void collapseToCursor() {
		this.changes.setSize(this.cursor);
	}

	@Override
	public void undo() {
		if (!canUndo()) {
			throw new RuntimeException("There is nothing to undo.");
		}
		this.cursor--;
		invalidate();
	}

	@Override
	public void redo() {
		if (!canRedo()) {
			throw new RuntimeException("There is nothing to redo.");
		}
		this.cursor++;
		invalidate();
	}

	@Override
	public boolean canUndo() {
		return this.cursor > 0;
	}

	@Override
	public boolean canRedo() {
		return this.cursor < this.changes.size();
	}

	@Override
	public boolean isInvalidated() {
		return this.currentModel == null;
	}

	/**
	 * Returns the current model, deriving it from the baseline if the manager
	 * is currently invalid. The manager is always valid on return of this
	 * method.
	 * 
	 * @return
	 */
	@Override
	@SuppressWarnings("unchecked")
	public T getModel() {
		if (this.currentModel == null) {
			SerialClone sc = new SerialClone();
			this.currentModel = (T) sc.clone(this.baselineModel);
			List<AbstractChange<T>> effChanges = this.changes.subList(0, this.cursor);
			for (AbstractChange<T> change : effChanges) {
				change.applyTo(this.currentModel);
			}
		}
		return this.currentModel;
	}
}
