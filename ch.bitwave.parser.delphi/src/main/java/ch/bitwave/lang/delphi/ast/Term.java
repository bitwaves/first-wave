package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Term extends Node {
	private List<SignedFactor> factors;
	private List<TermOperator> operators;

	public Term() {
		this.factors = new ArrayList<SignedFactor>();
		this.operators = new ArrayList<TermOperator>();
	}

	public List<SignedFactor> getFactors() {
		return Collections.unmodifiableList(this.factors);
	}

	public List<TermOperator> getOperators() {
		return this.operators;
	}

	@Override
	public void remove(final Node node) {
		this.factors.remove(node);
		this.operators.remove(node);
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof SignedFactor) {
			this.factors.add((SignedFactor) node);
			return true;
		} else if (node instanceof TermOperator) {
			this.operators.add((TermOperator) node);
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		if (this.factors.size() > 0) {
			details.add(this.factors.get(0));
			for (int i = 0; i < this.operators.size(); i++) {
				details.add(this.operators.get(i));
				details.add(this.factors.get(i + 1));
			}
		}
	}

	@Override
	public String toString() {
		return "Term";
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitTerm(this);
	}
}
