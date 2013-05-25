package ch.bitwave.lang.delphi.ast;

public class ProcedureType extends InvokableType {

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitProcedureType(this);
	}

	@Override
	public String toString() {
		return "FunctionType";
	}

}
