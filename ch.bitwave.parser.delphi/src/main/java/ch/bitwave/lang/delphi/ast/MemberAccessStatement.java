package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class MemberAccessStatement extends SimpleStatement {
	private MemberAccess access;

	public MemberAccess getAccess() {
		return this.access;
	}

	public void setAccess(final MemberAccess access) {
		this.access = access;
	}

	@Override
	public void remove(final Node node) {
		if (this.access == node) {
			this.access = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof MemberAccess) {
			this.access = (MemberAccess) node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		contribute(details, this.access);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitMemberAccessStatement(this);
	}

	@Override
	public String toString() {
		return "MemberAccessStatement";
	}

}
