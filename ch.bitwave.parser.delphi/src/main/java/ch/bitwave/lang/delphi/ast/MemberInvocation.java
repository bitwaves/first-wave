package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class MemberInvocation extends DimensionalNode {

	private Node content;
	private boolean dereference;

	public boolean isDereference() {
		return this.dereference;
	}

	public void setDereference(final boolean dereference) {
		this.dereference = dereference;
	}

	public Node getContent() {
		return this.content;
	}

	public void setContent(final Node content) {
		this.content = content;
	}

	@Override
	public void remove(final Node node) {
		super.remove(node);
		if (this.content == node) {
			this.content = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (super.internalAdd(node)) {
			return true;
		}
		if (this.content == null) {
			this.content = node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		super.contributeDetails(details);
		contribute(details, this.content);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MemberInvocation [dereference=").append(this.dereference).append("]");
		return builder.toString();
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitMemberInvocation(this);
	}

}
