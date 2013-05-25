package ch.bitwave.lang.delphi.ast;


public class WriteAccessorDeclaration extends IdentifiedNode {

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitWriteAccessorDeclaration(this);
	}

}
