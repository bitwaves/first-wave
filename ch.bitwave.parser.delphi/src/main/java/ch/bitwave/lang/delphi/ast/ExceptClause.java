package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public class ExceptClause extends TryHandler {

	// EXCEPT (exceptionHandlers ES? | statements) (ELSE statements)? END;

	private List<ExceptionHandler> handlers;
	private ElseCase elseCase;

	public ExceptClause() {
		super();
		this.handlers = new ArrayList<ExceptionHandler>();
	}

	public ElseCase getElseCase() {
		return this.elseCase;
	}

	public void setElseCase(final ElseCase elseCase) {
		this.elseCase = elseCase;
	}

	public List<ExceptionHandler> getHandlers() {
		return this.handlers;
	}

	@Override
	public void remove(final Node node) {
		super.remove(node);
		if (node instanceof ExceptionHandler) {
			this.handlers.remove(node);
		} else if (node instanceof ElseCase) {
			this.elseCase = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (super.internalAdd(node))
			return true;
		if (node instanceof ExceptionHandler) {
			this.handlers.add((ExceptionHandler) node);
			return true;
		}
		if (node instanceof ElseCase) {
			this.elseCase = (ElseCase) node;
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		super.contributeDetails(details);
		details.addAll(this.handlers);
		contribute(details, this.elseCase);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitExceptClause(this);
	}

	@Override
	public String toString() {
		return "ExceptClause";
	}

}
