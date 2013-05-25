package ch.bitwave.lang.delphi.ast;

import java.util.List;

public class Element extends Node {

	private Expression first;
	private Expression second;

	public Expression getFirst() {
		return this.first;
	}

	public void setFirst(final Expression first) {
		this.first = first;
	}

	public Expression getSecond() {
		return this.second;
	}

	public void setSecond(final Expression second) {
		this.second = second;
	}

	@Override
	public void remove(final Node node) {
		if (this.first == node) {
			this.first = null;
		} else if (this.second == node) {
			this.second = null;
		}
	}

	@Override
	protected boolean internalAdd(final Node node) throws InvalidNodeException {
		if (node instanceof Expression) {
			if (this.first == null) {
				this.first = (Expression) node;
				return true;
			}
			if (this.second == null) {
				this.second = (Expression) node;
				return true;
			}
		}
		return false;
	}

	@Override
	protected void contributeDetails(final List<Node> details) {
		contribute(details, this.first, this.second);
	}

	@Override
	public void accept(final DelphiSyntaxVisitor visitor) {
		visitor.visitElement(this);
	}

}
