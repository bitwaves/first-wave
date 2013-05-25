package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public class ArrayInitializer extends Node {

	private List<Constant> constants;

	public ArrayInitializer() {
		this.constants = new ArrayList<Constant>();
	}

	@Override
	public void remove(final Node node) {
		this.constants.remove(node);
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof Constant) {
			this.constants.add((Constant) node);
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		details.addAll(this.constants);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitArrayInitializer(this);
	}

	@Override
	public String toString() {
		return "ArrayInitializer";
	}

}
