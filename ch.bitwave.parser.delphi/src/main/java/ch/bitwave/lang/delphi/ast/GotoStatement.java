package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class GotoStatement extends SimpleStatement {

	private Label label;

	@Override
	public void remove(final Node node) {
		if (this.label == node) {
			this.label = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof Label) {
			this.label = (Label) node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		contribute(details, this.label);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitGotoStatement(this);
	}

}
