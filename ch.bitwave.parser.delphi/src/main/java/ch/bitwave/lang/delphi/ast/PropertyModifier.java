package ch.bitwave.lang.delphi.ast;

public class PropertyModifier extends TerminalNode {
	PropertyModifierKind kind;

	public PropertyModifierKind getKind() {
		return this.kind;
	}

	public void setKind(final PropertyModifierKind kind) {
		this.kind = kind;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PropertyModifier [kind=").append(this.kind).append("]");
		return builder.toString();
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitPropertyModifier(this);
	}

}
