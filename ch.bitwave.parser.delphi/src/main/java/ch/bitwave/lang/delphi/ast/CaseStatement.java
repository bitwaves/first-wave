package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.List;

public class CaseStatement extends ConditionalStatement {

	// CASE memberAccess OF caseItems elseCase? END;

	private MemberAccess access;
	private List<CaseItem> items;
	private ElseCase elseCase;

	public CaseStatement() {
		this.items = new ArrayList<CaseItem>();
	}

	public MemberAccess getAccess() {
		return this.access;
	}

	public void setAccess(final MemberAccess access) {
		this.access = access;
	}

	public ElseCase getElseCase() {
		return this.elseCase;
	}

	public void setElseCase(final ElseCase elseCase) {
		this.elseCase = elseCase;
	}

	public List<CaseItem> getItems() {
		return this.items;
	}

	@Override
	public void remove(final Node node) {
		if (this.access == node) {
			this.access = null;
		} else if (this.elseCase == node) {
			this.elseCase = null;
		} else {
			this.items.remove(node);
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof MemberAccess) {
			this.access = (MemberAccess) node;
			return true;
		}
		if (node instanceof ElseCase) {
			this.elseCase = (ElseCase) node;
			return true;
		}
		if (node instanceof CaseItem) {
			this.items.add((CaseItem) node);
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		contribute(details, this.access);
		details.addAll(this.items);
		contribute(details, this.elseCase);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitCaseStatement(this);
	}

	@Override
	public String toString() {
		return "CaseStatement";
	}

}
