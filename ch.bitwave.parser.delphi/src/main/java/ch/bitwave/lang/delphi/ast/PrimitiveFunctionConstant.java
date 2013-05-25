package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class PrimitiveFunctionConstant extends Constant {

	// typeIdentifier '(' constant ')';

	private TypeIdentifier castType;
	private Constant argument;

	public TypeIdentifier getCastType() {
		return this.castType;
	}

	public void setCastType(final TypeIdentifier castType) {
		this.castType = castType;
	}

	public Constant getArgument() {
		return this.argument;
	}

	public void setArgument(final Constant argument) {
		this.argument = argument;
	}

	@Override
	public void remove(final Node node) {
		if (this.castType == node) {
			this.castType = null;
		} else if (this.argument == node) {
			this.argument = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof TypeIdentifier) {
			this.castType = (TypeIdentifier) node;
			return true;
		}
		if (node instanceof Constant) {
			this.argument = (Constant) node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		contribute(details, this.castType, this.argument);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitPrimitiveFunctionConstant(this);
	}

}
