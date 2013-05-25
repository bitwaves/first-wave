package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public class PropertyAccessDeclaration extends Node {

	private List<ParameterDeclaration> parameters;

	public PropertyAccessDeclaration() {
		this.parameters = new ArrayList<ParameterDeclaration>();
	}

	@Override
	public void remove(final Node node) {
		this.parameters.remove(node);
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof ParameterDeclaration) {
			this.parameters.add((ParameterDeclaration) node);
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		details.addAll(this.parameters);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitPropertyAccessDeclaration(this);
	}

	@Override
	public String toString() {
		return "PropertyAccessDeclaration";
	}

}
