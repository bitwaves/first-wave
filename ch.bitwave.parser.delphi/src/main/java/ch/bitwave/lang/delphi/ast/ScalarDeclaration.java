package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class ScalarDeclaration extends IdentifiedNode {

	private Constant value;

	public Constant getValue() {
		return this.value;
	}

	public void setValue(final Constant value) {
		this.value = value;
	}

	@Override
	public void remove(final Node node) {
		super.remove(node);
		if (this.value == node) {
			this.value = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (super.internalAdd(node))
			return true;
		if (node instanceof Constant) {
			this.value = (Constant) node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		super.contributeDetails(details);
		contribute(details, this.value);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitScalarDeclaration(this);
	}

}
