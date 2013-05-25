package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class AssignmentStatement extends SimpleStatement {

	private Node variable;
	private Expression value;

	public Node getVariable() {
		return this.variable;
	}

	public void setVariable(final Node variable) {
		this.variable = variable;
	}

	public Expression getValue() {
		return this.value;
	}

	public void setValue(final Expression value) {
		this.value = value;
	}

	@Override
	public void remove(final Node node) {
		if (this.variable == node) {
			this.variable = null;
		} else if (this.value == node) {
			this.value = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (this.variable == null) {
			assertNodeType(node, Node.class);
			this.variable = node;
			return true;
		}
		if (this.value == null) {
			assertNodeType(node, Expression.class);
			this.value = (Expression) node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		contribute(details, this.variable, this.value);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AssignmentStatement [variable=").append(this.variable).append(", value=")
				.append(this.value).append("]");
		return builder.toString();
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitAssignmentStatement(this);
	}

}
