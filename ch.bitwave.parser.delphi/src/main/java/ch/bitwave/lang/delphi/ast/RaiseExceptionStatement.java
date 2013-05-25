package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class RaiseExceptionStatement extends SimpleStatement {

	private Expression optionalExpression;

	public Expression getOptionalExpression() {
		return this.optionalExpression;
	}

	public void setOptionalExpression(final Expression optionalExpression) {
		this.optionalExpression = optionalExpression;
	}

	@Override
	public void remove(final Node node) {
		if (this.optionalExpression == node) {
			this.optionalExpression = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof Expression) {
			this.optionalExpression = (Expression) node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		contribute(details, this.optionalExpression);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitRaiseExceptionStatement(this);
	}

}
