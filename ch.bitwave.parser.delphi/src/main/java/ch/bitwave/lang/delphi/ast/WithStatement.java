package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public class WithStatement extends StructuredStatement {

	private List<Expression> records;
	private Statement statement;

	public WithStatement() {
		this.records = new ArrayList<Expression>();
	}

	public List<Expression> getRecords() {
		return this.records;
	}

	public Statement getStatement() {
		return this.statement;
	}

	public void setStatement(final Statement statement) {
		this.statement = statement;
	}

	@Override
	public void remove(final Node node) {
		if (this.statement == node) {
			this.statement = null;
		} else {
			this.records.remove(node);
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof Expression) {
			this.records.add((Expression) node);
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
		details.addAll(this.records);
		contribute(details, this.statement);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitWithStatement(this);
	}

}
