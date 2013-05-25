package ch.bitwave.lang.delphi.ast;

public class MemberProcedureDeclaration extends MethodDeclaration {

	public MemberProcedureDeclaration(final AccessModifierKind accessKind) {
		super(accessKind);
	}

	@Override
	public String toString() {
		return "MemberProcedureDeclaration";
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitMemberProcedureDeclaration(this);
	}

}
