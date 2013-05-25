package ch.bitwave.lang.delphi.ast;

public class Identifier extends Constant {
	private String name;

	public Identifier() {
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Identifier [name=").append(this.name).append("]");
		return builder.toString();
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitIdentifier(this);
	}

}
