package ch.bitwave.lang.delphi.ast;

public class MemberDestructorImplementation extends MethodImplementation {

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitMemberDestructorImplementation(this);
	}

}
