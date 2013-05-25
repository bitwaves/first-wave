package ch.bitwave.lang.delphi.ast;

import java.util.List;

public abstract class ClassifierDeclaration extends Type {

	private AncestorDeclaration ancestorDeclaration;

	public AncestorDeclaration getAncestorDeclaration() {
		return this.ancestorDeclaration;
	}

	public void setAncestorDeclaration(final AncestorDeclaration ancestorDeclaration) {
		this.ancestorDeclaration = ancestorDeclaration;
	}

	@Override
	public void remove(final Node node) {
		if (this.ancestorDeclaration == node) {
			this.ancestorDeclaration = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof AncestorDeclaration) {
			this.ancestorDeclaration = (AncestorDeclaration) node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		contribute(details, this.ancestorDeclaration);
	}

	public abstract boolean isAbstract();

}
