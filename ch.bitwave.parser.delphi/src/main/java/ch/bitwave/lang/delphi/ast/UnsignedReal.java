package ch.bitwave.lang.delphi.ast;

public class UnsignedReal extends UnsignedNumber {
	private String value;

	public String getValue() {
		return this.value;
	}

	public void setValue(final String value) {
		this.value = value;
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitUnsignedReal(this);
	}

}
