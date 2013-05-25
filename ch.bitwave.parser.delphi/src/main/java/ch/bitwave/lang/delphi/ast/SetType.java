package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class SetType extends Type {

	private Type type;

	public Type getType() {
		return this.type;
	}

	public void setType(final Type type) {
		this.type = type;
	}

	@Override
	public void remove(final Node node) {
		if (this.type == node) {
			this.type = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof Type) {
			this.type = (Type) node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		contribute(details, this.type);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitSetType(this);
	}

}
