package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class TypeDeclaration extends IdentifiedNode {
	private Type type;

	public Type getType() {
		return this.type;
	}

	public void setType(final Type type) {
		this.type = type;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TypeDeclaration [name=").append(this.getIdentifier()).append(", type=")
				.append(this.type).append("]");
		return builder.toString();
	}

	@Override
	public void remove(final Node node) {
		super.remove(node);
		if (this.type == node) {
			this.type = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (super.internalAdd(node))
			return true;
		if (this.type == null) {
			assertNodeType(node, Type.class);
			this.type = (Type) node;
			return true;
		}
		return false;

	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		super.contributeDetails(details);
		contribute(details, this.type);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitTypeDeclaration(this);
	}

}
