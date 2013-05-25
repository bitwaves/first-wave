package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public abstract class TryHandler extends Node {

	private List<Statement> statements;

	public TryHandler() {
		this.statements = new ArrayList<Statement>();
	}

	public List<Statement> getStatements() {
		return this.statements;
	}

	@Override
	public void remove(final Node node) {
		this.statements.remove(node);
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof Statement) {
			this.statements.add((Statement) node);
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		details.addAll(this.statements);
	}

	public void addStatement(final Statement stm) {
		this.statements.add(stm);
	}

}
