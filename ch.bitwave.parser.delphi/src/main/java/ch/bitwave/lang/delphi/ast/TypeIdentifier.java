package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class TypeIdentifier extends Type {
	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TypeIdentifier [name=").append(this.name).append("]");
		return builder.toString();
	}

	@Override
	public void remove(final Node node) {
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitTypeIdentifier(this);
	}

}
