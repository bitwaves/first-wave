package ch.bitwave.lang.delphi.ast;

public class TermOperator extends TerminalNode {
	private TermOperatorKind kind;

	public TermOperatorKind getKind() {
		return this.kind;
	}

	public void setKind(final TermOperatorKind kind) {
		this.kind = kind;
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitTermOperator(this);
	}

}
