package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class StaticStringType extends Type {

	private Node sizeSpecifier;

	public Node getSizeSpecifier() {
		return this.sizeSpecifier;
	}

	public void setSizeSpecifier(final Node sizeSpecifier) {
		this.sizeSpecifier = sizeSpecifier;
	}

	@Override
	public void remove(final Node node) {
		if (this.sizeSpecifier == node) {
			this.sizeSpecifier = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (this.sizeSpecifier == null) {
			this.sizeSpecifier = node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		contribute(details, this.sizeSpecifier);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitStaticStringType(this);
	}

}
