package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class Variable extends DimensionalNode {
	private boolean addressOf;
	private Identifier identifier;
	private int numberOfDereferences;

	public Identifier getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(final Identifier identifier) {
		this.identifier = identifier;
	}

	public boolean isAddressOf() {
		return this.addressOf;
	}

	public void setAddressOf(final boolean addressOf) {
		this.addressOf = addressOf;
	}

	public int getNumberOfDereferences() {
		return this.numberOfDereferences;
	}

	public void setNumberOfDereferences(final int numberOfDereferences) {
		this.numberOfDereferences = numberOfDereferences;
	}

	@Override
	public void remove(final Node node) {
		super.remove(node);
		if (this.identifier == node) {
			this.identifier = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (super.internalAdd(node))
			return true;
		if (node instanceof Identifier) {
			this.identifier = (Identifier) node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		super.contributeDetails(details);
		contribute(details, this.identifier);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Variable [identifier=").append(this.identifier).append("]");
		return builder.toString();
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitVariable(this);
	}

}
