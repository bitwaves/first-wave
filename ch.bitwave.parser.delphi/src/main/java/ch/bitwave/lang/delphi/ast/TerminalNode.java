package ch.bitwave.lang.delphi.ast;

import java.util.List;

public abstract class TerminalNode extends Node {

	@Override
	public void remove(final Node node) {

	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		throw new InvalidNodeException("It is not possible to add nodes to a terminal node.");
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
	}

}
