package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class FunctionType extends InvokableType {

	private TypeIdentifier returnType;

	public TypeIdentifier getReturnType() {
		return this.returnType;
	}

	public void setReturnType(final TypeIdentifier returnType) {
		this.returnType = returnType;
	}

	@Override
	public void remove(final Node node) {
		super.remove(node);
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (super.internalAdd(node))
			return true;
		if (node instanceof TypeIdentifier) {
			this.returnType = (TypeIdentifier) node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		super.contributeDetails(details);
		contribute(details, this.returnType);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitFunctionType(this);
	}

	@Override
	public String toString() {
		return "FunctionType";
	}

}
