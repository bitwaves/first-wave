package ch.bitwave.lang.delphi.ast;

import java.util.List;

public abstract class MethodImplementation extends InvokableImplementation {
	private QualifiedIdentifier identifier;

	public QualifiedIdentifier getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(final QualifiedIdentifier identifier) {
		this.identifier = identifier;
	}

	@Override
	public void remove(final Node node) {
		super.remove(node);
		if (this.identifier == node) {
			this.identifier = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (super.internalAdd(node))
			return true;
		if (this.identifier == null) {
			assertNodeType(node, QualifiedIdentifier.class);
			this.identifier = (QualifiedIdentifier) node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		contribute(details, this.identifier);
		super.contributeDetails(details);
	}

}
