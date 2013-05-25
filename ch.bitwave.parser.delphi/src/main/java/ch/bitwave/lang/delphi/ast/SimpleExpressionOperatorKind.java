package ch.bitwave.lang.delphi.ast;

public enum SimpleExpressionOperatorKind {
	PLUS("+"), MINUS("-"), MODULO("mod"), OR("or"), XOR("xor");

	private final String label;

	private SimpleExpressionOperatorKind(final String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}

}
