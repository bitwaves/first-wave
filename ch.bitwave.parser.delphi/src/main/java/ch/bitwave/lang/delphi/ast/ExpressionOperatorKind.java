package ch.bitwave.lang.delphi.ast;

public enum ExpressionOperatorKind {
	NONE(""), EQUAL("="), NOT_EQUAL("<>"), LESS_THAN("<"), GREATER_THAN(">"), LESS_EQUAL("<="), GRATER_EQUAL(
			">="), IN("in");

	private String label;

	private ExpressionOperatorKind(final String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}
}
