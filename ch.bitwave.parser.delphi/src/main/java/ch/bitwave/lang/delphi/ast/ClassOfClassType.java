package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class ClassOfClassType extends Type {

	private TypeIdentifier type;

	public TypeIdentifier getType() {
		return this.type;
	}

	public void setType(final TypeIdentifier type) {
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
		if (node instanceof TypeIdentifier) {
			this.type = (TypeIdentifier) node;
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
		visitor.visitClassOfClassType(this);
	}

}
