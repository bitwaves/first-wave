package ch.bitwave.lang.delphi.ast;

public class ProcedureDeclaration extends ProceduralDeclaration {

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitProcedureDeclaration(this);
	}

	@Override
	public String toString() {
		return "ProcedureDeclaration";
	}

}
