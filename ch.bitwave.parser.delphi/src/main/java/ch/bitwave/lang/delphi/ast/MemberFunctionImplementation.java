package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class MemberFunctionImplementation extends MethodImplementation {

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
		visitor.visitMemberFunctionImplementation(this);
	}

	@Override
	public String toString() {
		return "MemberFunctionImplementation";
	}

}
