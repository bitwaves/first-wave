package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class Factor extends Node {
	private TypeIdentifier castType;
	private Node content;

	public Node getContent() {
		return this.content;
	}

	public void setContent(final Node content) {
		this.content = content;
	}

	public TypeIdentifier getCastType() {
		return this.castType;
	}

	public void setCastType(final TypeIdentifier castType) {
		this.castType = castType;
	}

	@Override
	public void remove(final Node node) {
		if (this.content == node) {
			this.content = null;
		} else if (this.castType == node) {
			this.castType = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (this.content == null) {
			this.content = node;
			return true;
		}
		if (node instanceof TypeIdentifier) {
			this.castType = (TypeIdentifier) node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		contribute(details, this.content, this.castType);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Factor [castType=").append(this.castType).append(", content=")
				.append(this.content).append("]");
		return builder.toString();
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitFactor(this);
	}

}
