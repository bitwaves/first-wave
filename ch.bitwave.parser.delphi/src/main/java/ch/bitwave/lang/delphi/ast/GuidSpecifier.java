package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class GuidSpecifier extends Node {

	private Node contents;

	@Override
	public void remove(final Node node) {
		if (this.contents == node) {
			this.contents = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (this.contents == null) {
			this.contents = node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		contribute(details, this.contents);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitGuidSpecifier(this);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GuidSpecifier [contents=").append(this.contents).append("]");
		return builder.toString();
	}

}
