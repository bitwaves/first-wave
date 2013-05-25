package ch.bitwave.lang.delphi.ast;

public class UnsignedInteger extends UnsignedNumber {
	private String value;

	public String getValue() {
		return this.value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitUnsignedInteger(this);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UnsignedInteger [value=").append(this.value).append("]");
		return builder.toString();
	}

}
