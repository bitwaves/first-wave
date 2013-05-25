package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public class ClassType extends ClassifierDeclaration {

	private List<MemberDeclaration> declarations;

	public ClassType() {
		this.declarations = new ArrayList<MemberDeclaration>();
	}

	public List<MemberDeclaration> getDeclarations() {
		return this.declarations;
	}

	@Override
	public String toString() {
		return "ClassType";
	}

	@Override
	public void remove(final Node node) {
		super.remove(node);
		this.declarations.remove(node);
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		super.contributeDetails(details);
		details.addAll(this.declarations);
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (super.internalAdd(node))
			return true;
		if (node instanceof MemberDeclaration) {
			this.declarations.add((MemberDeclaration) node);
			return true;
		}
		return false;
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitClassType(this);
	}

	@Override
	public boolean isAbstract() {
		for (MemberDeclaration declaration : this.declarations) {
			if (declaration.isAbstract()) {
				return true;
			}
		}
		return false;
	}

}
