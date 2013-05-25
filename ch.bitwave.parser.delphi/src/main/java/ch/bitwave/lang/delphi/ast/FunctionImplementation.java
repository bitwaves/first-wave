package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class FunctionImplementation extends ProceduralImplementation {

	// FUNCTION qualifiedIdentifier parametersDeclaration? typeSpecifier ES
	// codeBlock;

	private Type returnType;

	public Type getReturnType() {
		return this.returnType;
	}

	public void setReturnType(final Type returnType) {
		this.returnType = returnType;
	}

	@Override
	public void remove(final Node node) {
		super.remove(node);
		if (node instanceof Type) {
			this.returnType = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (super.internalAdd(node))
			return true;
		if (node instanceof Type) {
			this.returnType = (Type) node;
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
		visitor.visitFunctionImplementation(this);
	}

	@Override
	public String toString() {
		return "FunctionImplementation";
	}

}
