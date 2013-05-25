package ch.parametrix.common.util.ui.swing.contracts;

/**
 * Provides an interface to a domain-specific component which manages changes to
 * the model of that domain with the intent of providing an undo/redo
 * capability. The view may add changes via this interface and the component may
 * then derive an updated model from a baseline model by applying the changes
 * sequentially.
 * 
 */
public interface IChangeManager<T> {
	/**
	 * Collapses the change stack down to the current stack cursor and then adds
	 * the given change to the top of the stack.
	 * 
	 * @param change
	 */
	public void addChange(AbstractChange<T> change);

	/**
	 * Decrements the current stack cursor, thereby preventing the last change
	 * from being applied, effectively undoing it. Throws an exception if the
	 * stack cursor is at the bottom of the stack.
	 */
	public void undo();

	/**
	 * Increments the current stack cursor, thereby redoing the previously
	 * undone step. Throws an exception if the stack cursor is at the top of the
	 * stack.
	 */
	public void redo();

	/**
	 * Tells whether there currently is a change that can be undone, which is
	 * true if the stack cursor is not at the bottom of the stack.
	 * 
	 * @return
	 */
	public boolean canUndo();

	/**
	 * Tells whether there currently is an undone change that can be redone,
	 * which is true if the stack cursor is not at the top of the stack.
	 * 
	 * @return
	 */
	public boolean canRedo();

	/**
	 * Retrieves the current model. All changes currently effective in the
	 * manager are incorporated in the result. If the manager is currently
	 * invalid, the model will be re-derived from the baseline by applying the
	 * changes, and the manager will become valid again.
	 * 
	 * @return the current model.
	 */
	public T getModel();

	/**
	 * Tells whether the change manager is currently invalidated. An invalidated
	 * manager entails that the model it currently provides does no longer
	 * reflect the stack of changes that are also held by the manager. A call to
	 * {@link #getModel()} will, however, update the model and always return the
	 * correct result.
	 * 
	 * @return true if the manager is invalidated.
	 */
	public boolean isInvalidated();
}
