package ch.bitwave.lang.delphi.ast;

public class MemberDestructorDeclaration extends MethodDeclaration {

	public MemberDestructorDeclaration(final AccessModifierKind accessKind) {
		super(accessKind);
	}

	@Override
	public String toString() {
		return "MemberDestructorDeclaration";
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitMemberDestructorDeclaration(this);
	}

}
