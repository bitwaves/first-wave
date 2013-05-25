package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public class VariableDeclaration extends TerminalNode {
	private List<Identifier> identifiers;
	private Type type;
	private Expression defaultValue;

	public VariableDeclaration() {
		this.identifiers = new ArrayList<Identifier>();
	}

	public List<Identifier> getIdentifiers() {
		return this.identifiers;
	}

	public Type getType() {
		return this.type;
	}

	public void setType(final Type type) {
		this.type = type;
	}

	public Expression getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(final Expression defaultValue) {
		this.defaultValue = defaultValue;
	}

	@Override
	public void remove(final Node node) {
		if (this.type == node) {
			this.type = null;
		} else if (this.defaultValue == node) {
			this.defaultValue = null;
		} else {
			this.identifiers.remove(node);
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof Identifier) {
			this.identifiers.add((Identifier) node);
			return true;
		}
		if (this.type == null) {
			assertNodeType(node, Type.class);
			this.type = (Type) node;
			return true;
		}
		if (this.defaultValue == null) {
			assertNodeType(node, Expression.class);
			this.defaultValue = (Expression) node;
			return true;
		}
		return false;

	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		details.addAll(this.identifiers);
		contribute(details, this.type);
		contribute(details, this.defaultValue);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitVariableDeclaration(this);
	}

	public boolean hasDefaultValue() {
		return this.defaultValue != null;
	}

}
