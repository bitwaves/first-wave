package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class ConstDeclaration extends IdentifiedNode {

	private Type type;
	private Initializer initializer;

	public Type getType() {
		return this.type;
	}

	public void setType(final Type type) {
		this.type = type;
	}

	public Initializer getInitializer() {
		return this.initializer;
	}

	public void setInitializer(final Initializer initializer) {
		this.initializer = initializer;
	}

	@Override
	public void remove(final Node node) {
		super.remove(node);
		if (this.type == node) {
			this.type = null;
		} else if (this.initializer == node) {
			this.initializer = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (super.internalAdd(node))
			return true;
		if (node instanceof Type) {
			this.type = (Type) node;
			return true;
		}
		if (node instanceof Initializer) {
			this.initializer = (Initializer) node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		super.contributeDetails(details);
		contribute(details, this.type, this.initializer);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitConstDeclaration(this);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ConstDeclaration [type=").append(this.type).append(", initializer=")
				.append(this.initializer).append(", identifier=").append(getIdentifier())
				.append("]");
		return builder.toString();
	}

}
