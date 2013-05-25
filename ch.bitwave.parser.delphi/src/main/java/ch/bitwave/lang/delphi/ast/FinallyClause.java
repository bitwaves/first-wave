package ch.bitwave.lang.delphi.ast;

public class FinallyClause extends TryHandler {

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitFinallyClause(this);

	}

}
