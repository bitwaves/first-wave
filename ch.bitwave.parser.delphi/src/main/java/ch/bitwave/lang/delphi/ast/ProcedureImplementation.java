package ch.bitwave.lang.delphi.ast;

public class ProcedureImplementation extends ProceduralImplementation {

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitProcedureImplementation(this);
	}

}
