package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class Nil extends Constant {

	@Override
	public void remove(final Node node) {

	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {

	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitNil(this);
	}

	@Override
	public String toString() {
		return "Nil";
	}

}
