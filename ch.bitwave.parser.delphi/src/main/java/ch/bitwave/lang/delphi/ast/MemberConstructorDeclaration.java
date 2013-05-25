package ch.bitwave.lang.delphi.ast;


public class MemberConstructorDeclaration extends MethodDeclaration {

	public MemberConstructorDeclaration(final AccessModifierKind accessKind) {
		super(accessKind);
	}

	@Override
	public String toString() {
		return "MemberConstructorDeclaration";
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitMemberConstructorDeclaration(this);
	}

}
