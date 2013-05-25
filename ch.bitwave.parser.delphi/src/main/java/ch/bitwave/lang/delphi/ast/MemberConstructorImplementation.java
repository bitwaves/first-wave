package ch.bitwave.lang.delphi.ast;

public class MemberConstructorImplementation extends MethodImplementation {

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitMemberConstructorImplementation(this);
	}

}
