package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class DimensionQualifier extends Node {

	private Node content;

	public Node getContent() {
		return this.content;
	}

	public void setContent(final Node content) {
		this.content = content;
	}

	@Override
	public void remove(final Node node) {
		if (this.content == node) {
			this.content = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (this.content == null) {
			this.content = node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		contribute(details, this.content);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitDimensionQualifier(this);
	}

}
