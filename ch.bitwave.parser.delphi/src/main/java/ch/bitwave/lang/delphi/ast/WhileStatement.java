package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class WhileStatement extends RepetitiveStatement {

	private Expression condition;
	private Statement body;

	public Expression getExpression() {
		return this.condition;
	}

	public void setExpression(final Expression expression) {
		this.condition = expression;
	}

	public Statement getBody() {
		return this.body;
	}

	public void setBody(final Statement body) {
		this.body = body;
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitWhileStatement(this);
	}

	@Override
	public void remove(final Node node) {
		if (this.condition == node) {
			this.condition = null;
		} else if (this.body == node) {
			this.body = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof Expression) {
			this.condition = (Expression) node;
			return true;
		}
		if (node instanceof Statement) {
			this.body = (Statement) node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		contribute(details, this.condition, this.body);
	}

	@Override
	public String toString() {
		return "WhileStatement";
	}

}
