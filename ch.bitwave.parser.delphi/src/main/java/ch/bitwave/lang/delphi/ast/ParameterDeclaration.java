package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public class ParameterDeclaration extends Node {

	private ParameterModifierKind modifier;
	private List<Identifier> identifiers;
	private Type type;
	private Expression defaultValue;

	public ParameterDeclaration() {
		super();
		this.identifiers = new ArrayList<Identifier>();
	}

	public Type getType() {
		return this.type;
	}

	public void setType(final Type type) {
		this.type = type;
	}

	public ParameterModifierKind getModifier() {
		return this.modifier;
	}

	public void setModifier(final ParameterModifierKind modifier) {
		this.modifier = modifier;
	}

	public Expression getDefaultValue() {
		return this.defaultValue;
	}

	public void setDefaultValue(final Expression defaultValue) {
		this.defaultValue = defaultValue;
	}

	public List<Identifier> getIdentifiers() {
		return getDetailsOfType(Identifier.class);
	}

	public void addIdentifier(final Identifier ident) {
		this.identifiers.add(ident);
	}

	public String getIdentifierNames() {
		List<Identifier> ids = getIdentifiers();
		StringBuilder sb = new StringBuilder();
		for (Identifier identifier : ids) {
			if (sb.length() > 0) {
				sb.append(", ");
			}
			sb.append(identifier.getName());
		}
		return sb.toString();
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ParameterDeclaration [modifier=").append(this.modifier)
				.append(", defaultValue=").append(this.defaultValue).append(", identifiers=")
				.append(getIdentifierNames()).append("]");
		return builder.toString();
	}

	@Override
	public void remove(final Node node) {
		if (this.defaultValue == node) {
			this.defaultValue = null;
		} else if (this.type == node) {
			this.type = null;
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
		if (node instanceof Type) {
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
		contribute(details, this.defaultValue);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitParameterDeclaration(this);
	}
}
