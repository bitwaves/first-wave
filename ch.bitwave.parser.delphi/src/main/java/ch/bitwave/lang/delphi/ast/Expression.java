package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class Expression extends Node {
	private ExpressionOperatorKind operator = ExpressionOperatorKind.NONE;
	private SimpleExpression leftOperand;
	private SimpleExpression rightOperand;

	public ExpressionOperatorKind getOperator() {
		return this.operator;
	}

	public void setOperator(final ExpressionOperatorKind operator) {
		this.operator = operator;
	}

	public SimpleExpression getLeftOperand() {
		return this.leftOperand;
	}

	public void setLeftOperand(final SimpleExpression leftOperand) {
		this.leftOperand = leftOperand;
	}

	public SimpleExpression getRightOperand() {
		return this.rightOperand;
	}

	public void setRightOperand(final SimpleExpression rightOperand) {
		this.rightOperand = rightOperand;
	}

	@Override
	public void remove(final Node node) {
		if (this.leftOperand == node) {
			this.leftOperand = null;
		} else if (this.rightOperand == node) {
			this.rightOperand = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		assertNodeType(node, SimpleExpression.class);
		SimpleExpression expr = (SimpleExpression) node;
		if (this.leftOperand == null) {
			this.leftOperand = expr;
			return true;
		}
		if (this.rightOperand == null) {
			this.rightOperand = expr;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		contribute(details, this.leftOperand, this.rightOperand);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Expression ").append(this.leftOperand);
		if (this.operator != null && !ExpressionOperatorKind.NONE.equals(this.operator)) {
			builder.append(" ").append(this.operator).append(" ").append(this.rightOperand);
		}
		return builder.toString();
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitExpression(this);
	}
}
