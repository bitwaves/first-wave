package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public class ElseCase extends Node {

	private List<Statement> statements;

	public List<Statement> getStatements() {
		return this.statements;
	}

	public ElseCase() {
		this.statements = new ArrayList<Statement>();
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

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitElseCase(this);
	}

	@Override
	public String toString() {
		return "ElseCase";
	}

}
