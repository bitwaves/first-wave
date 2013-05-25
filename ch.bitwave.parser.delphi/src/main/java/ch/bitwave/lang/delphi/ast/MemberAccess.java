package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public class MemberAccess extends Node {

	private boolean callInherited;
	private List<MemberInvocation> invocations;

	public MemberAccess() {
		this.invocations = new ArrayList<MemberInvocation>();
	}

	public boolean isCallInherited() {
		return this.callInherited;
	}

	public void setCallInherited(final boolean callInherited) {
		this.callInherited = callInherited;
	}

	public List<MemberInvocation> getInvocations() {
		return this.invocations;
	}

	@Override
	public void remove(final Node node) {
		this.invocations.remove(node);
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof MemberInvocation) {
			this.invocations.add((MemberInvocation) node);
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		details.addAll(this.invocations);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MemberAccess [callInherited=").append(this.callInherited).append("]");
		return builder.toString();
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitMemberAccess(this);
	}

}
