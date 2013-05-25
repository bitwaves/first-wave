package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class ExceptionHandler extends IdentifiedNode {
	// ON identifier typeSpecifier? DO statement;

	private Type exceptionType;
	private Statement statement;

	public Type getExceptionType() {
		return this.exceptionType;
	}

	public void setExceptionType(final Type exceptionType) {
		this.exceptionType = exceptionType;
	}

	public Statement getStatement() {
		return this.statement;
	}

	public void setStatement(final Statement statement) {
		this.statement = statement;
	}

	@Override
	public void remove(final Node node) {
		super.remove(node);
		if (this.exceptionType == node) {
			this.exceptionType = null;
		} else if (this.statement == node) {
			this.statement = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (super.internalAdd(node))
			return true;
		if (node instanceof Type) {
			this.exceptionType = (Type) node;
			return true;
		}
		if (node instanceof Statement) {
			this.statement = (Statement) node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		contribute(details, this.exceptionType, this.statement);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitExceptionHandler(this);
	}

	@Override
	public String toString() {
		return "ExceptionHandler";
	}

}
