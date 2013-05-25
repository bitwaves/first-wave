package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public class InterfaceType extends ClassifierDeclaration {

	private GuidSpecifier guid;
	private List<MemberDeclaration> members;

	public InterfaceType() {
		this.members = new ArrayList<MemberDeclaration>();
	}

	public List<MemberDeclaration> getMembers() {
		return this.members;
	}

	public GuidSpecifier getGuid() {
		return this.guid;
	}

	public void setGuid(final GuidSpecifier guid) {
		this.guid = guid;
	}

	@Override
	public String toString() {
		return "InterfaceType";
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitInterfaceType(this);
	}

	@Override
	public void remove(final Node node) {
		super.remove(node);
		if (this.guid == node) {
			this.guid = null;
		} else {
			this.members.remove(node);
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (super.internalAdd(node))
			return true;
		if (node instanceof GuidSpecifier) {
			this.guid = (GuidSpecifier) node;
			return true;
		}
		if (node instanceof MemberDeclaration) {
			this.members.add((MemberDeclaration) node);
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		super.contributeDetails(details);
		contribute(details, this.guid);
		details.addAll(this.members);
	}

	@Override
	public boolean isAbstract() {
		return true;
	}

}
