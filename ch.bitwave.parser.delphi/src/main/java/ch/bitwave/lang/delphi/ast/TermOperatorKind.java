package ch.bitwave.lang.delphi.ast;

public enum TermOperatorKind {
	MULT("*"), MODULO("mod"), AND("and"), SHFT_LEFT("shl"), SHIFT_RIGHT("shr"), DIV_INTEGER("div"), DIV_REAL(
			"/");

	private final String label;

	private TermOperatorKind(final String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}

}
