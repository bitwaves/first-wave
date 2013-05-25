package ch.bitwave.lang.delphi.ast;

import java.util.List;

public abstract class ProceduralImplementation extends InvokableImplementation {
	private Identifier identifier;

	public Identifier getIdentifier() {
		return this.identifier;
	}

	public void setIdentifier(final Identifier identifier) {
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
			assertNodeType(node, Identifier.class);
			this.identifier = (Identifier) node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		contribute(details, this.identifier);
		super.contributeDetails(details);
	}

	@Override
	public boolean isProcedural() {
		return true;
	}

}
