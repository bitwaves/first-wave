package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public class RepeatStatement extends RepetitiveStatement {

	private List<Statement> statements;
	private Expression condition;

	public RepeatStatement() {
		this.statements = new ArrayList<Statement>();
	}

	public Expression getCondition() {
		return this.condition;
	}

	public void setCondition(final Expression condition) {
		this.condition = condition;
	}

	public List<Statement> getStatements() {
		return this.statements;
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitRepeatStatement(this);
	}

	@Override
	public void remove(final Node node) {
		if (this.condition == node) {
			this.condition = null;
		} else {
			this.statements.remove(node);
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof Expression) {
			this.condition = (Expression) node;
			return true;
		}
		if (node instanceof Statement) {
			this.statements.add((Statement) node);
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		details.addAll(this.statements);
		contribute(details, this.condition);
	}

	@Override
	public String toString() {
		return "RepeatStatement";
	}

}
