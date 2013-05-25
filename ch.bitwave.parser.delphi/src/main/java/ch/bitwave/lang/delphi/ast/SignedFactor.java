package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class SignedFactor extends Node {
	boolean positive;
	Factor factor;

	public boolean isPositive() {
		return this.positive;
	}

	public void setPositive(final boolean positive) {
		this.positive = positive;
	}

	@Override
	public void remove(final Node node) {
		if (this.factor == node) {
			this.factor = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		assertNodeType(node, Factor.class);
		if (this.factor == null) {
			assertNodeType(node, Factor.class);
			this.factor = (Factor) node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		contribute(details, this.factor);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SignedFactor [positive=").append(this.positive).append("]");
		return builder.toString();
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitSignedFactor(this);
	}

}
