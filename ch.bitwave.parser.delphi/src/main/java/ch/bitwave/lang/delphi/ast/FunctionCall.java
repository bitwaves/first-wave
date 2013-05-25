package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public class FunctionCall extends IdentifiedNode {
	private List<Expression> arguments;
	private TypeIdentifier type;

	public FunctionCall() {
		this.arguments = new ArrayList<Expression>();
	}

	public TypeIdentifier getType() {
		return this.type;
	}

	public void setType(final TypeIdentifier type) {
		this.type = type;
	}

	public List<Expression> getArguments() {
		return this.arguments;
	}

	@Override
	public void remove(final Node node) {
		super.remove(node);
		if (this.type == node) {
			this.type = null;
		} else {
			this.arguments.remove(node);
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (super.internalAdd(node))
			return true;
		if (node instanceof TypeIdentifier) {
			this.type = (TypeIdentifier) node;
			return true;
		}
		if (node instanceof Expression) {
			this.arguments.add((Expression) node);
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		details.addAll(this.arguments);
		contribute(details, this.type);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FunctionCall [identifier=").append(getIdentifier()).append("]");
		return builder.toString();
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitFunctionCall(this);
	}

}
