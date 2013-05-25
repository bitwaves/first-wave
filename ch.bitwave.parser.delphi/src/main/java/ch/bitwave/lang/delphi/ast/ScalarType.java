package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public class ScalarType extends Type {

	private List<ScalarDeclaration> scalars;

	public ScalarType() {
		this.scalars = new ArrayList<ScalarDeclaration>();
	}

	public List<ScalarDeclaration> getScalars() {
		return this.scalars;
	}

	@Override
	public void remove(final Node node) {
		this.scalars.remove(node);
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof ScalarDeclaration) {
			this.scalars.add((ScalarDeclaration) node);
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		details.addAll(this.scalars);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitScalarType(this);
	}

}
