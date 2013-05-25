package ch.bitwave.lang.delphi.ast;

public class LineComment extends Comment {

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitLineComment(this);
	}

}
