package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public class SetConstant extends Node {

	private List<Element> elements;

	public SetConstant() {
		this.elements = new ArrayList<Element>();
	}

	public List<Element> getElements() {
		return this.elements;
	}

	@Override
	public void remove(final Node node) {
		this.elements.remove(node);
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof Element) {
			this.elements.add((Element) node);
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		details.addAll(this.elements);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitSetConstant(this);
	}

}
