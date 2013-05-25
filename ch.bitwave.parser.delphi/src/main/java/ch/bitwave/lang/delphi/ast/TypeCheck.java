package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class TypeCheck extends Node {

	private MemberAccess access;
	private Type type;

	public MemberAccess getAccess() {
		return this.access;
	}

	public void setAccess(final MemberAccess access) {
		this.access = access;
	}

	public Type getType() {
		return this.type;
	}

	public void setType(final Type type) {
		this.type = type;
	}

	@Override
	public void remove(final Node node) {
		if (this.access == node) {
			this.access = null;
		} else if (this.type == node) {
			this.type = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof MemberAccess) {
			this.access = (MemberAccess) node;
			return true;
		}
		if (node instanceof Type) {
			this.type = (Type) node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		contribute(details, this.access, this.type);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitTypeCheck(this);
	}

}
