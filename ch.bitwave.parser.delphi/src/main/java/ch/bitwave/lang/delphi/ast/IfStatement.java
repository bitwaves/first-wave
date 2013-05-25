package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class IfStatement extends ConditionalStatement {

	private Expression condition;
	private Statement trueCase;
	private Statement falseCase;

	public Expression getCondition() {
		return this.condition;
	}

	public Statement getTrueCase() {
		return this.trueCase;
	}

	public Statement getFalseCase() {
		return this.falseCase;
	}

	@Override
	public void remove(final Node node) {
		if (this.condition == node) {
			this.condition = null;
		} else if (this.trueCase == node) {
			this.trueCase = null;
		} else if (this.falseCase == null) {
			this.falseCase = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof Expression) {
			this.condition = (Expression) node;
			return true;
		}
		if (node instanceof Statement) {
			if (this.trueCase == null) {
				this.trueCase = (Statement) node;
				return true;
			} else if (this.falseCase == null) {
				this.falseCase = (Statement) node;
				return true;
			}
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		contribute(details, this.condition, this.trueCase, this.falseCase);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitIfStatement(this);
	}

	/**
	 * Tells whether this if statement has a false case statement which is
	 * executed if the condition is not met.
	 * 
	 * @return true if the statement has a false case.
	 */
	public boolean hasFalseCase() {
		return this.falseCase != null;
	}

}
