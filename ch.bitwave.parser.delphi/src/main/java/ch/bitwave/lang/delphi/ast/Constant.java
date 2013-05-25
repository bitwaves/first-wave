package ch.bitwave.lang.delphi.ast;

import java.util.List;

public abstract class Constant extends Node {

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

}
