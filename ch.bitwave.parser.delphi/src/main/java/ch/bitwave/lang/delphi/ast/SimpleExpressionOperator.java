package ch.bitwave.lang.delphi.ast;

public class SimpleExpressionOperator extends TerminalNode {
	private SimpleExpressionOperatorKind kind;

	public SimpleExpressionOperatorKind getKind() {
		return this.kind;
	}

	public void setKind(final SimpleExpressionOperatorKind kind) {
		this.kind = kind;
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitSimpleExpressionOperator(this);
	}

}
