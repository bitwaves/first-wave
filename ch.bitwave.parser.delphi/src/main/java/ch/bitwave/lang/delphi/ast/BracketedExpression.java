package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class BracketedExpression extends Node {
	private Expression content;

	@Override
	public void remove(final Node node) {
		if (this.content == node) {
			this.content = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (this.content == null) {
			assertNodeType(node, Expression.class);
			this.content = (Expression) node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		contribute(details, this.content);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BracketedExpression ").append(this.content);
		return builder.toString();
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitBracketedExpression(this);
	}
}
