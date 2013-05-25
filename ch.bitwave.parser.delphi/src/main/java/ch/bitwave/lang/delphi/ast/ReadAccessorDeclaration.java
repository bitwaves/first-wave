package ch.bitwave.lang.delphi.ast;


public class ReadAccessorDeclaration extends IdentifiedNode {

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitReadAccessorDeclaration(this);
	}

}
