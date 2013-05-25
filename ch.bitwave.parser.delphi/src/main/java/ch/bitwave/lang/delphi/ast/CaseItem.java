package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public class CaseItem extends Node {

	// constantList COLON statement;

	private List<Constant> constants;
	private Statement statement;

	public CaseItem() {
		this.constants = new ArrayList<Constant>();
	}

	public Statement getStatement() {
		return this.statement;
	}

	public void setStatement(final Statement statement) {
		this.statement = statement;
	}

	public List<Constant> getConstants() {
		return this.constants;
	}

	@Override
	public void remove(final Node node) {
		if (node instanceof Statement) {
			this.statement = null;
		} else {
			this.constants.remove(node);
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof Statement) {
			this.statement = (Statement) node;
			return true;
		}
		if (node instanceof Constant) {
			this.constants.add((Constant) node);
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		details.addAll(this.constants);
		contribute(details, this.statement);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitCaseItem(this);
	}

	@Override
	public String toString() {
		return "CaseItem";
	}

}
