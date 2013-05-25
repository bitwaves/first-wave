package ch.bitwave.lang.delphi.ast;

import java.util.List;

public abstract class IdentifiedNode extends Node {
	private Identifier identifier;

	public Identifier getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(final Identifier identifier) {
		this.identifier = identifier;
	}

	@Override
	public void remove(final Node node) {
		if (this.identifier == node) {
			this.identifier = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof Identifier) {
			this.identifier = (Identifier) node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		contribute(details, this.identifier);
	}

}
