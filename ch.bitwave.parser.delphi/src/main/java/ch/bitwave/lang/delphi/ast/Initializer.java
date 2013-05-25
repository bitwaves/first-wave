package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class Initializer extends Node {

	private Node contents;

	public Node getContents() {
		return this.contents;
	}

	public void setContents(final Node contents) {
		this.contents = contents;
	}

	@Override
	public void remove(final Node node) {
		if (this.contents == node) {
			this.contents = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (this.contents == null) {
			this.contents = node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		contribute(details, this.contents);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitInitializer(this);
	}

	@Override
	public String toString() {
		return "Initializer";
	}

}
