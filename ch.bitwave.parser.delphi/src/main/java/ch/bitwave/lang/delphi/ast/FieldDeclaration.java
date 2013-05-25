package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class FieldDeclaration extends MemberDeclaration {

	private VariableDeclaration variable;

	public FieldDeclaration(final AccessModifierKind accessKind) {
		super(accessKind);
	}

	public VariableDeclaration getVariable() {
		return this.variable;
	}

	public void setVariable(final VariableDeclaration variable) {
		this.variable = variable;
	}

	@Override
	public void remove(final Node node) {
		super.remove(node);
		if (this.variable == node) {
			this.variable = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (super.internalAdd(node))
			return true;
		if (this.variable == null) {
			assertNodeType(node, VariableDeclaration.class);
			this.variable = (VariableDeclaration) node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		super.contributeDetails(details);
		contribute(details, this.variable);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitFieldDeclaration(this);
	}

}
