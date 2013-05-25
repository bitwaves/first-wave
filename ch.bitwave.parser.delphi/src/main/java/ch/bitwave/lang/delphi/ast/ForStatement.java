package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class ForStatement extends RepetitiveStatement {

	private Identifier variable;
	private Expression startExpression;
	private Expression endExpression;
	private boolean ascending;
	private Statement body;

	public void setVariable(final Identifier variable) {
		this.variable = variable;
	}

	public void setStartExpression(final Expression startExpression) {
		this.startExpression = startExpression;
	}

	public void setEndExpression(final Expression endExpression) {
		this.endExpression = endExpression;
	}

	public void setAscending(final boolean ascending) {
		this.ascending = ascending;
	}

	public boolean isAscending() {
		return this.ascending;
	}

	public void setBody(final Statement body) {
		this.body = body;
	}

	public Identifier getVariable() {
		return this.variable;
	}

	public Expression getStartExpression() {
		return this.startExpression;
	}

	public Expression getEndExpression() {
		return this.endExpression;
	}

	public Statement getBody() {
		return this.body;
	}

	@Override
	public void remove(final Node node) {
		if (this.variable == node) {
			this.variable = null;
		} else if (this.startExpression == node) {
			this.startExpression = null;
		} else if (this.endExpression == node) {
			this.endExpression = null;
		} else if (this.body == node) {
			this.body = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof Identifier) {
			this.variable = (Identifier) node;
			return true;
		}
		if (node instanceof Expression) {
			if (this.startExpression == null) {
				this.startExpression = (Expression) node;
				return true;
			}
			if (this.endExpression == null) {
				this.endExpression = (Expression) node;
				return true;
			}
			return false;
		}
		if (node instanceof Statement) {
			this.body = (Statement) node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		contribute(details, this.variable, this.startExpression, this.endExpression, this.body);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitForStatement(this);
	}

	@Override
	public String toString() {
		return "ForStatement";
	}

}
