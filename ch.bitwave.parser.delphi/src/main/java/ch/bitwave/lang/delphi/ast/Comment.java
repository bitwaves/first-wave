package ch.bitwave.lang.delphi.ast;

public abstract class Comment extends TerminalNode {
	private String body;

	public String getBody() {
		return this.body;
	}

	public void setBody(final String body) {
		this.body = body;
	}

}
