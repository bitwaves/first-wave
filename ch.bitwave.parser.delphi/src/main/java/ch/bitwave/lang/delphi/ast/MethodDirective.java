package ch.bitwave.lang.delphi.ast;

public class MethodDirective extends TerminalNode {
	MethodDirectiveKind kind;

	public MethodDirectiveKind getKind() {
		return this.kind;
	}

	public void setKind(final MethodDirectiveKind kind) {
		this.kind = kind;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MethodModifier [kind=").append(this.kind).append("]");
		return builder.toString();
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitMethodDirective(this);
	}

}
