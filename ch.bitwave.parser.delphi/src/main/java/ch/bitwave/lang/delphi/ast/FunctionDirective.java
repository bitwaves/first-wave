package ch.bitwave.lang.delphi.ast;

public class FunctionDirective extends TerminalNode {
	private FunctionDirectiveKind kind;

	public FunctionDirectiveKind getKind() {
		return this.kind;
	}

	public void setKind(final FunctionDirectiveKind kind) {
		this.kind = kind;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FunctionModifier [kind=").append(this.kind).append("]");
		return builder.toString();
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitFunctionDirective(this);
	}

}
