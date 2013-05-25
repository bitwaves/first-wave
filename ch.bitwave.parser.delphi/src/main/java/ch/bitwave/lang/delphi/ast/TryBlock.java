package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public class TryBlock extends ConditionalStatement {

	private List<Statement> statements;
	private TryHandler handler;

	public TryBlock() {
		this.statements = new ArrayList<Statement>();
	}

	public List<Statement> getStatements() {
		return this.statements;
	}

	public TryHandler getHandler() {
		return this.handler;
	}

	public void setHandler(final TryHandler handler) {
		this.handler = handler;
	}

	@Override
	public void remove(final Node node) {
		this.statements.remove(node);
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof TryHandler) {
			this.handler = (TryHandler) node;
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
		contribute(details, this.handler);
	}

	public void addStatement(final Statement stm) {
		this.statements.add(stm);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitTryBlock(this);
	}

	@Override
	public String toString() {
		return "TryBlock";
	}

}
