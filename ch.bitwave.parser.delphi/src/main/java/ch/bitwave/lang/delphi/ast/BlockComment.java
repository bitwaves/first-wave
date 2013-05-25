package ch.bitwave.lang.delphi.ast;

public class BlockComment extends Comment {

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitBlockComment(this);
	}

}
