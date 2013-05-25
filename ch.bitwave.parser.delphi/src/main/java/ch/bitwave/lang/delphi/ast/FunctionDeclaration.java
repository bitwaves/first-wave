package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class FunctionDeclaration extends ProceduralDeclaration {

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
		if (this.returnType == node) {
			this.returnType = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (super.internalAdd(node))
			return true;
		assertNodeType(node, Type.class);
		this.returnType = (Type) node;
		return true;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		super.contributeDetails(details);
		contribute(details, this.returnType);
	}

	@Override
	public String toString() {
		return "FunctionDeclaration";
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitFunctionDeclaration(this);
	}

}
