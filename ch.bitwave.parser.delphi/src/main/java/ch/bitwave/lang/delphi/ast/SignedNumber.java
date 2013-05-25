package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class SignedNumber extends Constant {

	private boolean positive;
	private UnsignedNumber number;

	public boolean isPositive() {
		return this.positive;
	}

	public void setPositive(final boolean positive) {
		this.positive = positive;
	}

	public UnsignedNumber getNumber() {
		return this.number;
	}

	public void setNumber(final UnsignedNumber number) {
		this.number = number;
	}

	@Override
	public void remove(final Node node) {
		if (this.number == node) {
			this.number = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof UnsignedNumber) {
			this.number = (UnsignedNumber) node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		contribute(details, this.number);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitSignedNumber(this);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SignedNumber [positive=").append(this.positive).append(", number=")
				.append(this.number).append("]");
		return builder.toString();
	}

}
