package ch.bitwave.lang.delphi.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleExpression extends Node {
	private List<Term> terms;
	private List<SimpleExpressionOperator> operators;

	public SimpleExpression() {
		this.terms = new ArrayList<Term>();
		this.operators = new ArrayList<SimpleExpressionOperator>();
	}

	public List<Term> getTerms() {
		return Collections.unmodifiableList(this.terms);
	}

	public List<SimpleExpressionOperator> getOperators() {
		return this.operators;
	}

	@Override
	public void remove(final Node node) {
		this.terms.remove(node);
		this.operators.remove(node);
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof Term) {
			this.terms.add((Term) node);
			return true;
		} else if (node instanceof SimpleExpressionOperator) {
			this.operators.add((SimpleExpressionOperator) node);
			return true;
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		if (this.terms.size() > 0) {
			details.add(this.terms.get(0));
			for (int i = 0; i < this.operators.size(); i++) {
				details.add(this.operators.get(i));
				details.add(this.terms.get(i + 1));
			}
		}
	}

	@Override
	public String toString() {
		return "SimpleExpression";
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitSimpleExpression(this);
	}
}
