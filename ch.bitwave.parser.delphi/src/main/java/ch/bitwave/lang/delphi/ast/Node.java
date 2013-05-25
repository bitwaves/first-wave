package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class Node {

	private Node parent;

	public Node() {
	}

	public Node getParent() {
		return this.parent;
	}

	public void setParent(final Node parent) {
		this.parent = parent;
	}

	public abstract void remove(@Nullable final Node node);

	public final void add(@Nullable final Node node) throws InvalidNodeException {
		if (!internalAdd(node)) {
			throw prepareCannotAddException(node);
		}
	}

	protected abstract boolean internalAdd(@Nullable final Node node) throws InvalidNodeException;

	public final List<Node> getDetails() {
		List<Node> result = new ArrayList<Node>();
		contributeDetails(result);
		return result;
	}

	protected abstract void contributeDetails(final List<Node> details);

	public final int getDetailCount() {
		return getDetails().size();
	}

	@SuppressWarnings("unchecked")
	public final <T> T findDetailOfType(@Nonnull final Class<T> desiredClass) {
		for (Node node : getDetails()) {
			if (desiredClass.isAssignableFrom(node.getClass())) {
				return (T) node;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public final <T> List<T> getDetailsOfType(final Class<T> desiredClass) {
		List<T> list = new ArrayList<T>();
		for (Node node : getDetails()) {
			if (desiredClass.isAssignableFrom(node.getClass())) {
				list.add((T) node);
			}
		}
		return list;
	}

	protected final List<Node> createNodeList(final Node... nodes) {
		List<Node> result = new ArrayList<Node>();
		for (Node node : nodes) {
			if (node != null) {
				result.add(node);
			}
		}
		return result;
	}

	protected final void contribute(final List<Node> list, final Node... nodes) {
		for (Node node : nodes) {
			if (node != null) {
				list.add(node);
			}
		}
	}

	protected final void assertNodeType(final Node node, final Class<? extends Node> validClass)
			throws InvalidNodeException {
		Class<? extends Node> nodeClass = node.getClass();
		if (!validClass.isAssignableFrom(nodeClass)) {
			String message = String.format(
					"Node %s cannot be added to node %s (only type %s is valid).", node.toString(),
					this.toString(), validClass.getSimpleName());
			throw new InvalidNodeException(message);
		}
	}

	protected InvalidNodeException prepareCannotAddException(final Node node) {
		return new InvalidNodeException(String.format(
				"Cannot add more nodes to %s (given node is %s)", this.toString(), node.toString()));
	}

	/**
	 * Accepts this node without recursing into its details.
	 * 
	 * @param visitor
	 *            the visitor to visit.
	 */
	public abstract void accept(@Nonnull DelphiSyntaxVisitor visitor);

	/**
	 * Accepts this node and then recurses into its details.
	 * 
	 * @param visitor
	 *            the visitor to visit.
	 */
	public void acceptTree(@Nonnull final DelphiSyntaxVisitor visitor) {
		accept(visitor);
		for (Node detail : getDetails()) {
			detail.acceptTree(visitor);
		}
	}

	/**
	 * Tells whether this is a procedural node, which is a procedure or function
	 * declaration or implementation.
	 * 
	 * @return true if this is a procedural node.
	 */
	public boolean isProcedural() {
		return false;
	}
}
