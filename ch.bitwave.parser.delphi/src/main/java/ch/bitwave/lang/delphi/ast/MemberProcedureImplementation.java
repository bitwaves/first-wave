package ch.bitwave.lang.delphi.ast;

public class MemberProcedureImplementation extends MethodImplementation {

	@Override
	public String toString() {
		return "MemberProcedureImplementation";
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitMemberProcedureImplementation(this);
	}

}
