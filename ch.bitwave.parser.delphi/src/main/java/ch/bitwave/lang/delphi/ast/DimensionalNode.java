package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public abstract class DimensionalNode extends Node {
	private List<DimensionQualifier> qualifiers;

	public DimensionalNode() {
		this.qualifiers = new ArrayList<DimensionQualifier>();
	}

	public List<DimensionQualifier> getQualifiers() {
		return this.qualifiers;
	}

	@Override
	public void remove(final Node node) {
		this.qualifiers.remove(node);
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof DimensionQualifier) {
			this.qualifiers.add((DimensionQualifier) node);
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		details.addAll(this.qualifiers);
	}

}
